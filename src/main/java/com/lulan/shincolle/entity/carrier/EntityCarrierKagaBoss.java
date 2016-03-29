package com.lulan.shincolle.entity.carrier;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostileCV;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;

public class EntityCarrierKagaBoss extends BasicEntityShipHostileCV implements IBossDisplayData {

	public EntityCarrierKagaBoss(World world) {
		super(world);
		this.setSize(1.7F, 6.5F);
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityCarrierKaga.name"));
        
        //basic attr
		this.atk = (float) ConfigHandler.scaleBossLarge[ID.ATK] * 0.9F;
        this.atkSpeed = (float) ConfigHandler.scaleBossLarge[ID.SPD] * 0.9F;
        this.atkRange = (float) ConfigHandler.scaleBossLarge[ID.HIT] * 1.2F;
        this.defValue = (float) ConfigHandler.scaleBossLarge[ID.DEF] * 0.9F;
        this.movSpeed = (float) ConfigHandler.scaleBossLarge[ID.MOV] * 0.8F;
        this.launchHeight = this.height * 0.65F;

        //AI flag
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
        
        //misc
        this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, ID.Ship.CarrierKaga+2);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ConfigHandler.scaleBossLarge[ID.HP] * 0.9F);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(atkRange + 32); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
		this.setAITargetList();
		
		//model display
		this.setStateEmotion(ID.S.State2, ID.State.EQUIP03_2, false);
		
		if(this.getRNG().nextInt(3) == 0) {
			this.setStateEmotion(ID.S.State, ID.State.EQUIP06, false);
		}
		else {
			this.setStateEmotion(ID.S.State, ID.State.EQUIP02, false);
		}
	}
	
	@Override
	protected boolean canDespawn() {
        return this.ticksExisted > 12000;
    }
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}
	
	//setup AI
	@Override
	protected void setAIList() {
		super.setAIList();

		//use range attack TODO
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));		   //0011
	}
	
	//num rensouhou++
	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
//  		//client side
//  		if(worldObj.isRemote) {
//  		}
  	}

	@Override
	public int getDamageType() {
		return ID.ShipDmgType.CARRIER;
	}
	

}



