package com.lulan.shincolle.entity.carrier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;


public class EntityCarrierKaga extends BasicEntityShipCV {
	
	public EntityCarrierKaga(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipType.STANDARD_CARRIER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierKaga);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {0F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);
		
		this.launchHeight = this.height * 0.65F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
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
		return 3;
	}
	
	//增加艦載機數量計算
  	@Override
  	public void calcShipAttributes() {
  		super.calcShipAttributes();
  		
  		this.maxAircraftLight += this.getLevel() * 0.4F;
  		this.maxAircraftHeavy += this.getLevel() * 0.2F;
  	}
	
	@Override
	public void setAIList() {
		super.setAIList();

		//attack AI
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));		   //0011
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
          
//  		//client side
//  		if(worldObj.isRemote) {
//
//  		}
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
			int i = getStateEmotion(ID.S.State2) + 1;
			if(i > ID.State.EQUIP03_2) i = ID.State.NORMAL_2;
			setStateEmotion(ID.S.State2, i, true);
		}
		else {
			int i = getStateEmotion(ID.S.State) + 1;
			if(i > ID.State.EQUIP06) i = ID.State.NORMAL;
			setStateEmotion(ID.S.State, i, true);
		}
	}

	@Override
	public int getKaitaiType() {
		return 3;
	}
	
	@Override
	protected BasicEntityAirplane getAirplane(boolean isLightAirplane) {
		if(isLightAirplane) {
			return new EntityAirplaneZero(this.worldObj);
		}
		else {
			return new EntityAirplaneT(this.worldObj);
		}
	}
	


}




