package com.lulan.shincolle.entity.cruiser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.reference.ID;

public class EntityHeavyCruiserNe extends BasicEntityShipSmall {
	
	public EntityHeavyCruiserNe(World world) {
		super(world);
		this.setSize(0.6F, 1.2F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.HeavyCruiserNE);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.ModelPos = new float[] {0F, 10F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1F;
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
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));			   //0011
	}
	
	//NE級額外增加屬性
	@Override
	public void calcShipAttributes() {
		EffectEquip[ID.EF_CRI] = EffectEquip[ID.EF_CRI] + 0.15F;
		
		super.calcShipAttributes();	
	}

    @Override
    public void onLivingUpdate() {
    	//check server side
    	if(!this.worldObj.isRemote) {
    		//check every 5 sec
    		if(this.ticksExisted % 100 == 0) {
	    		//apply potion effect in the night
	        	if(!this.worldObj.isDaytime() && this.getStateFlag(ID.F.UseRingEffect)) {	
        			this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 300, 1));
        			this.addPotionEffect(new PotionEffect(Potion.jump.id, 300, 2));
        		}
        	}
    	}
    	super.onLivingUpdate();
    }
    
    @Override
	public int getKaitaiType() {
		return 0;
	}
	
    @Override
	public double getMountedYOffset() {
    	if(this.isSitting()) {
  			return (double)this.height * -0.15F;
  		}
  		else {
  			return (double)this.height * 0.15F;
  		}
	}


}


