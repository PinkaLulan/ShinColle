package com.lulan.shincolle.entity.battleship;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

/**特殊heavy attack:
 * 用StateEmotion[ID.S.Phase]來儲存攻擊階段
 * Phase 1:集氣 2:爆氣 3:集氣 
 */
public class EntityBattleshipYMT extends BasicEntityShipSmall {
	
	public EntityBattleshipYMT(World world) {
		super(world);
		this.setSize(0.8F, 2.1F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipYamato);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(30);
		
		this.initTypeModify();
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.7375F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();

		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
  		//client side
  		if(worldObj.isRemote) {
  			if(this.ticksExisted % 4 == 0) {
  				if(getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && !isSitting() && !getStateFlag(ID.F.NoFuel)) {
  					double smokeY = posY + 1.75D;
  					
  					//計算煙霧位置
  	  				float[] partPos = ParticleHelper.rotateXZByAxis(-0.55F, 0F, (this.renderYawOffset % 360) * Values.N.RAD_MUL, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}
  			}
  			
  			if(this.ticksExisted % 16 == 0) {
  				if(getStateEmotion(ID.S.Phase) > 0) {
  					//spawn beam charge lightning
    	        	ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 16, 1D, (byte)4);
  				}
  			}
  		}
  	}
  	
  	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				this.setShipOutfit(player.isSneaking());
				return true;
			}
		}
		
		super.interact(player);
		return false;
  	}
  	
  	/** Yamato Cannon
  	 *  phase: 0:charge, 1:attack
  	 *  
  	 *  summon a beam entity, fly directly to target
  	 *  create beam particle between target and host
  	 */
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
  		//get attack value
		float atk = CalcHelper.calcDamageBySpecialEffect(this, target, StateFinal[ID.ATK_H], 3);
		
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)(target.posY + target.height * 0.5F);
		float tarZ = (float)target.posZ;
		float[] distVec = new float[4];
		
		distVec[0] = tarX - (float)this.posX;
		distVec[1] = tarY - (float)this.posY;
		distVec[2] = tarZ - (float)this.posZ;
		distVec[3] = MathHelper.sqrt_float(distVec[0]*distVec[0] + distVec[1]*distVec[1] + distVec[2]*distVec[2]);
        if(distVec[3] < 0.001F) distVec[3] = 0.001F; //prevent large dXYZ
        
        distVec[0] = distVec[0] / distVec[3];
        distVec[1] = distVec[1] / distVec[3];
        distVec[2] = distVec[2] / distVec[3];
		
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
		
		//heavy ammo--
        if(!decrAmmoNum(1, this.getAmmoConsumption())) {
        	return false;
        }

        //play attack effect
        applySoundAtAttacker(2, target);
        
        //check phase
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
        if(getStateEmotion(ID.S.Phase) > 0) {  //spawn beam particle & entity
        	//shot sound
        	this.playSound(Reference.MOD_ID+":ship-yamato-shot", ConfigHandler.fireVolume, 1F);
        	
        	//spawn beam entity
            EntityProjectileBeam beam = new EntityProjectileBeam(this.worldObj, this, 0, distVec[0], distVec[1], distVec[2], atk, 0.12F);
            this.worldObj.spawnEntityInWorld(beam);
            
            //spawn beam particle
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, beam, distVec[0], distVec[1], distVec[2], 1, true), point);
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        	return true;
        }
        else {
        	//charge sound
        	this.playSound(Reference.MOD_ID+":ship-yamato-ready", ConfigHandler.fireVolume, 1F);
        	
			//cannon charging particle
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 7, 1D, 0, 0), point);
        	
        	this.setStateEmotion(ID.S.Phase, 1, true);
        }
        
        //show emotes
      	applyEmotesReaction(3);
        
      	if(ConfigHandler.canFlare) {
			flareTarget(target);
		}
        return false;
	}

	@Override
	public int getKaitaiType() {
		return 3;
	}
	
	@Override
	public double getMountedYOffset() {
		if(this.isSitting()) {
			if(getStateEmotion(ID.S.State) > ID.State.NORMAL) {
				return (double)this.height * 0.4F;
			}
			else {
				if(getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
					return (double)this.height * -0.1F;
	  			}
	  			else {
	  				return (double)this.height * 0.3F;
	  			}
			}
  		}
  		else {
  			return (double)this.height * 0.8F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		if(isSneaking) {
			switch(getStateEmotion(ID.S.State2)) {
			case ID.State.NORMAL_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
				break;
			case ID.State.EQUIP00_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP01_2, true);
				break;
			case ID.State.EQUIP01_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP02_2, true);
				break;
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
				break;
			}
		}
		else {
			switch(getStateEmotion(ID.S.State)) {
			case ID.State.NORMAL:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
				break;
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
				break;
			case ID.State.EQUIP01:
				setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
				break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
				break;
			}
		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, float[] vec) {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch(type) {
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 1D, 1D, 1.5D), point);
  			break;
  		case 2:  //heavy cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 3:  //light aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 4:  //heavy aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			break;
  		}
  	}
	
	@Override
	public void applySoundAtAttacker(int type, Entity target) {
  		switch(type) {
  		case 1:  //light cannon
  			//fire sound
  			playSound(Reference.MOD_ID+":ship-firesmall", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        
  			//entity sound
  	        if(this.getRNG().nextInt(10) > 7) {
  	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 2:  //heavy cannon
  	        //entity sound
  	        if(this.getRNG().nextInt(10) > 7) {
  	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 3:  //light aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", ConfigHandler.fireVolume * 0.5F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
  		case 4:  //heavy aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", ConfigHandler.fireVolume * 0.5F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
		default: //melee
			if(this.getRNG().nextInt(10) > 6) {
	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        }
			break;
  		}
  	}


}



