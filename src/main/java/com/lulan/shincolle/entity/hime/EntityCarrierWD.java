package com.lulan.shincolle.entity.hime;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.mounts.EntityMountCaWD;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityCarrierWD extends BasicEntityShipCV {
	
	public EntityCarrierWD(World world) {
		super(world);
		this.setSize(0.7F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DEMON);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierWD);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {-6F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		launchHeight = this.height * 1.2F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(24);
	}
	
	@Override
	public float getEyeHeight() {
		return 1.7375F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 3;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
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
		
		return super.interact(player);
  	}
	
	@Override
	public int getKaitaiType() {
		return 1;
	}
	
	//change light cannon particle
    @Override
    protected void applyParticleAtAttacker(int type, Entity target, float[] vec) {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch(type) {
  		case 1:  //light cannon
  	        double shotHeight = 1.85D;
  	        
  	        if(this.isRiding() && this.ridingEntity instanceof BasicEntityMount) {
  	        	shotHeight = 0.8D;
  	        }
  	        
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, target, shotHeight, 0D, 0D, 0, true), point);
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

    //change light cannon sound
    @Override
    protected void applySoundAtAttacker(int type, Entity target) {
  		switch(type) {
  		case 1:  //light cannon
  			//fire sound
  	        playSound(Reference.MOD_ID+":ship-laser", 0.2F, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        
  			//entity sound
  			if(this.rand.nextInt(10) > 7) {
  	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 2:  //heavy cannon
  			//fire sound
  	        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

  	        //entity sound
  	        if(this.getRNG().nextInt(10) > 7) {
  	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 3:  //light aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
  		case 4:  //heavy aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
		default: //melee
			if(this.getRNG().nextInt(10) > 6) {
	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        }
			break;
  		}
  	}
  	
  	//禁用重型攻擊
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
  		return false;
  	}
	
  	//避免跟rider2碰撞
  	@Override
	public boolean canBePushed() {
        return this.ridingEntity == null;
    }
  	
  	//true if use mounts
  	@Override
  	public boolean canSummonMounts() {
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity() {
		return new EntityMountCaWD(worldObj, this);
	}
  	
  	@Override
  	public float[] getModelPos() {
  		if(this.isRiding()) {
  			ModelPos[1] = -15F;
  		}
  		else {
  			ModelPos[1] = 15F;
  		}
  		
		return ModelPos;
	}
  	
  	//被騎乘的高度
  	@Override
	public double getMountedYOffset() {
  		if(this.isSitting()) {
  			return (double)this.height * 0.2F;
  		}
  		else {
  			return (double)this.height * 0.62F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		//切換裝備顯示
		if(isSneaking) {
			switch(getStateEmotion(ID.S.State2)) {
			default:
			case ID.State.NORMAL_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
				break;
			case ID.State.EQUIP00_2:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
				break;
			}
		}
		//切換是否騎乘座騎
		else {
			switch(getStateEmotion(ID.S.State)) {
			default:
			case ID.State.NORMAL:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
				break;
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
				this.setPositionAndUpdate(posX, posY + 2D, posZ);
				break;
			}
		}
	}


}




