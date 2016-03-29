package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.entity.other.EntityAirplaneTakoyaki;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.item.EquipAirplane;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.BlockHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

/**LARGE SHIP = Use Aircraft
 */
abstract public class BasicEntityShipCV extends BasicEntityShip implements IShipAircraftAttack {

	protected int maxAircraftLight;		//max airplane at same time
	protected int maxAircraftHeavy;
	protected int delayAircraft = 0;	//airplane recover delay
	protected double launchHeight;		//airplane launch height

	
	public BasicEntityShipCV(World world) {
		super(world);
	}
	
	//getter
	@Override
	public int getNumAircraftLight() {
		return StateMinor[ID.M.NumAirLight];
	}
	
	@Override
	public int getNumAircraftHeavy() {
		return StateMinor[ID.M.NumAirHeavy];
	}
	
	@Override
	public boolean hasAirLight() {
		return StateMinor[ID.M.NumAirLight] > 0;
	}
	
	@Override
	public boolean hasAirHeavy() {
		return StateMinor[ID.M.NumAirHeavy] > 0;
	}
	
	@Override
	public boolean hasAmmoLight() {
		return StateMinor[ID.M.NumAmmoLight] >= 6 * StateMinor[ID.M.AmmoCon];
	}
	
	@Override
	public boolean hasAmmoHeavy() {
		return StateMinor[ID.M.NumAmmoHeavy] >= 2 * StateMinor[ID.M.AmmoCon];
	}
	
	//setter
	@Override
	public void setNumAircraftLight(int par1) {
		if(this.worldObj.isRemote) {	//client端沒有max值可以判定, 因此直接設定即可
			StateMinor[ID.M.NumAirLight] = par1;
		}
		else {
			StateMinor[ID.M.NumAirLight] = par1;
			if(getNumAircraftLight() > maxAircraftLight) StateMinor[ID.M.NumAirLight] = maxAircraftLight;
			if(getNumAircraftLight() < 0) StateMinor[ID.M.NumAirLight] = 0;
		}
	}
	
	@Override
	public void setNumAircraftHeavy(int par1) {
		if(this.worldObj.isRemote) {	//client端沒有max值可以判定, 因此直接設定即可
			StateMinor[ID.M.NumAirHeavy] = par1;
		}
		else {
			StateMinor[ID.M.NumAirHeavy] = par1;
			if(getNumAircraftHeavy() > maxAircraftHeavy) StateMinor[ID.M.NumAirHeavy] = maxAircraftHeavy;
			if(getNumAircraftHeavy() < 0) StateMinor[ID.M.NumAirHeavy] = 0;
		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		//server side
		if(!this.worldObj.isRemote) {
			//每一段時間回復一隻艦載機
			delayAircraft--;
			if(this.delayAircraft <= 0) {
				delayAircraft = (int)(1000 / (this.getStateFinal(ID.SPD)));	
				if(delayAircraft > 1000) delayAircraft = 1000;	//fix: spd還沒設完值就除 會導致delay變超大 (除以0)
				
				delayAircraft += 200;
				
				this.setNumAircraftLight(this.getNumAircraftLight()+1);
				this.setNumAircraftHeavy(this.getNumAircraftHeavy()+1);
			}
//			LogHelper.info("DEBUG : air num "+getNumAircraftLight()+" "+getNumAircraftHeavy());
		}
	}
	
	//增加艦載機數量計算
	@Override
	public void calcShipAttributes() {
		super.calcShipAttributes();
		
		//calc basic airplane
		this.maxAircraftLight = 4 + StateMinor[ID.M.ShipLevel] / 5;
		this.maxAircraftHeavy = 2 + StateMinor[ID.M.ShipLevel] / 10;
		
		//calc equip airplane
		int numair = getNumOfAircraftEquip();
		this.maxAircraftLight += (numair * 4);
		this.maxAircraftHeavy += (numair * 2);
	}
	
	//get number of aircraft equips
	public int getNumOfAircraftEquip() {
		int airNum = 0;
		
		for(int i = 0; i < ContainerShipInventory.SLOTS_SHIPINV; i++) {
			if(ExtProps.slots[i] != null &&
			   ExtProps.slots[i].getItem() instanceof EquipAirplane) airNum++;
		}
		
		return airNum;
	}
	
	/** get airplane entity: isLightAirplane: is light aircraft attack */
	protected BasicEntityAirplane getAirplane(boolean isLightAirplane) {
		if(isLightAirplane) {
			return new EntityAirplane(this.worldObj);
		}
		else {
			return new EntityAirplaneTakoyaki(this.worldObj);
		}
	}
	
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	@Override
	public boolean attackEntityWithAircraft(Entity target) {
		//clear target every attack
		this.setEntityTarget(null);
		
		//num aircraft--, number check in carrier AI
		this.setNumAircraftLight(this.getNumAircraftLight()-1);
		
		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //light ammo--
        if(!decrAmmoNum(0, 6 * this.getAmmoConsumption())) {
        	return false;
        }
        
        //experience++
  		addShipExp(8);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAir]);
        
        //發射者煙霧特效 (發射飛機不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
        
    	double summonHeight = this.posY + launchHeight;
    	
    	//check the summon block
    	if(!BlockHelper.checkBlockSafe(worldObj, (int)posX, (int)(posY+launchHeight), (int)(posZ))) {
    		summonHeight = posY + 1D;
    	}
    	
    	if(this.ridingEntity instanceof BasicEntityMount) {
    		summonHeight -= 1.5D;
    	}
    	
    	BasicEntityAirplane plane = getAirplane(true);
        plane.setAttrs(this.worldObj, this, target, summonHeight);
    	this.worldObj.spawnEntityInWorld(plane);
        return true;
	}

	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	@Override
	public boolean attackEntityWithHeavyAircraft(Entity target) {
		//clear target every attack
		this.setEntityTarget(null);
		
		//num aircraft--, number check in carrier AI
		this.setNumAircraftHeavy(this.getNumAircraftHeavy()-1);
		
		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //experience++
  		addShipExp(32);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAir]);
        
        //heavy ammo--
        if(!decrAmmoNum(1, 2 * this.getAmmoConsumption())) {
        	return false;
        }
        
        //發射者煙霧特效 (發射飛機不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
        
    	double summonHeight = this.posY + launchHeight;
    	
    	//check the summon block
    	if(!BlockHelper.checkBlockSafe(worldObj, (int)posX, (int)(posY+launchHeight), (int)(posZ))) {
    		summonHeight = posY + 0.5D;
    	}
    	
    	if(this.ridingEntity instanceof BasicEntityMount) {
    		summonHeight -= 1.5D;
    	}
    	
    	BasicEntityAirplane plane = getAirplane(false);
    	plane.setAttrs(this.worldObj, this, target, summonHeight);
    	this.worldObj.spawnEntityInWorld(plane);
        return true;
	}
	
}
