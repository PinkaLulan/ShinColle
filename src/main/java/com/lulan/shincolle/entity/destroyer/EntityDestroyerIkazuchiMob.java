package com.lulan.shincolle.entity.destroyer;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;

public class EntityDestroyerIkazuchiMob extends BasicEntityShipHostile {

	
	public EntityDestroyerIkazuchiMob(World world) {
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityDestroyerikazuchiMob.name"));
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		
        //basic attr
        this.atk = (float) ConfigHandler.scaleBossSmall[ID.ATK];
        this.atkSpeed = (float) ConfigHandler.scaleBossSmall[ID.SPD] * 1.5F;
        this.atkRange = (float) ConfigHandler.scaleBossSmall[ID.HIT] * 0.9F;
        this.defValue = (float) ConfigHandler.scaleBossSmall[ID.DEF] * 0.9F;
        this.movSpeed = (float) ConfigHandler.scaleBossSmall[ID.MOV] * 1.2F;

        //AI flag
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
        this.setStateEmotion(ID.S.State, ID.State.EQUIP02, false);
        
        //misc
        this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, ID.Ship.DestroyerIkazuchi+2);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ConfigHandler.scaleBossSmall[ID.HP] * 0.8F);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(atkRange + 32); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
		this.setAITargetList();
	}
	
	@Override
	protected boolean canDespawn() {
        return this.ticksExisted > 600;
    }
	
	@Override
	public float getEyeHeight() {
		return 1.4F;
	}
	
	//setup AI
	@Override
	protected void setAIList() {
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}
	
  	@Override
	public int getDamageType() {
		return ID.ShipDmgType.DESTROYER;
	}
  	
  	@Override
	public float getEffectEquip(int id) {
		switch(id) {
		case ID.EF_AA:  //DD vs AA,ASM effect
		case ID.EF_ASM:
			return this.atk * 0.5F;
		default:
			return 0F;
		}
	}
  	

}

