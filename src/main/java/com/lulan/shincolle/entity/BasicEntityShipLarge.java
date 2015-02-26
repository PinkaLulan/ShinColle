package com.lulan.shincolle.entity;

import com.lulan.shincolle.client.particle.EntityFXMiss;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

abstract public class BasicEntityShipLarge extends BasicEntityShip {

	protected int maxAircraftLight;		//max airplane at same time
	protected int maxAircraftHeavy;
	protected int delayAircraft = 0;		//airplane recover delay
	protected double launchHeight;		//airplane launch height

	
	public BasicEntityShipLarge(World world) {
		super(world);
	}
	
	//getter
	public int getNumAircraftLight() {
		return StateMinor[ID.NumAirLight];
	}
	public int getNumAircraftHeavy() {
		return StateMinor[ID.NumAirHeavy];
	}
	public boolean hasAirLight() {
		return StateMinor[ID.NumAirLight] > 0;
	}
	public boolean hasAirHeavy() {
		return StateMinor[ID.NumAirHeavy] > 0;
	}
	
	//setter
	public void setNumAircraftLight(int par1) {
		if(this.worldObj.isRemote) {	//client端沒有max值可以判定, 因此直接設定即可
			StateMinor[ID.NumAirLight] = par1;
		}
		else {
			StateMinor[ID.NumAirLight] = par1;
			if(getNumAircraftLight() > maxAircraftLight) StateMinor[ID.NumAirLight] = maxAircraftLight;
			if(getNumAircraftLight() < 0) StateMinor[ID.NumAirLight] = 0;
		}
	}
	public void setNumAircraftHeavy(int par1) {
		if(this.worldObj.isRemote) {	//client端沒有max值可以判定, 因此直接設定即可
			StateMinor[ID.NumAirHeavy] = par1;
		}
		else {
			StateMinor[ID.NumAirHeavy] = par1;
			if(getNumAircraftHeavy() > maxAircraftHeavy) StateMinor[ID.NumAirHeavy] = maxAircraftHeavy;
			if(getNumAircraftHeavy() < 0) StateMinor[ID.NumAirHeavy] = 0;
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
				delayAircraft = (int)(100F / (this.getStateFinal(ID.SPD)));
				this.setNumAircraftLight(this.getNumAircraftLight()+1);
				this.setNumAircraftHeavy(this.getNumAircraftHeavy()+1);
			}
//			LogHelper.info("DEBUG : air num "+getNumAircraftLight()+" "+getNumAircraftHeavy());
		}
	}
	
	//增加艦載機數量計算
	@Override
	public void calcShipAttributes(byte id) {
		super.calcShipAttributes(id);
		
		this.maxAircraftLight = 4 + StateMinor[ID.ShipLevel] / 5;
		this.maxAircraftHeavy = 2 + StateMinor[ID.ShipLevel] / 10;
	}
	
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	public boolean attackEntityWithAircraft(Entity target) {
//		LogHelper.info("DEBUG : launch LIGHT aircraft"+target);
		//clear target every attack
		this.setAttackTarget(null);
		
		//num aircraft--, number check in carrier AI
		this.setNumAircraftLight(this.getNumAircraftLight()-1);
		
		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //experience++
  		addShipExp(8);
  		
  		//grudge--
  		decrGrudgeNum(2);
        
        //light ammo -1
        if(!decrAmmoNum(4)) {		//not enough ammo
        	return false;
        }
        
        //spawn airplane
        if(target instanceof EntityLivingBase) {
        	EntityAirplane plane = new EntityAirplane(this.worldObj, this, (EntityLivingBase)target, this.posY+launchHeight);
            this.worldObj.spawnEntityInWorld(plane);
            return true;
        }
        return false;
	}

	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	public boolean attackEntityWithHeavyAircraft(Entity target) {
//		LogHelper.info("DEBUG : launch HEAVY aircraft"+target);
		//clear target every attack
		this.setAttackTarget(null);
		
		//num aircraft--, number check in carrier AI
		this.setNumAircraftHeavy(this.getNumAircraftHeavy()-1);
		
		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //experience++
  		addShipExp(32);
  		
  		//grudge--
  		decrGrudgeNum(3);
        
        //light ammo -1
        if(!decrAmmoNum(5)) {		//not enough ammo
        	return false;
        }
        
        //spawn airplane
        if(target instanceof EntityLivingBase) {
        	EntityAirplaneTakoyaki plane = new EntityAirplaneTakoyaki(this.worldObj, this, (EntityLivingBase)target, this.posY+this.launchHeight);
            this.worldObj.spawnEntityInWorld(plane);
            return true;
        }
        return false;
	}
	
}
