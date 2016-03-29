package com.lulan.shincolle.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAircraftAttack;
import com.lulan.shincolle.utility.TargetHelper;

public class EntityAirplaneZeroHostile extends EntityAirplaneZero {

	public EntityAirplaneZeroHostile(World world) {
		super(world);
		this.setSize(1F, 1F);
	}
	
	@Override
	public void setAttrs(World world, IShipAircraftAttack host, Entity target, double launchPos) {
		this.world = world;
        this.host = host;
        this.atkTarget = target;
        
        //host is mob ship
        if(host instanceof BasicEntityShipHostile) {
        	BasicEntityShipHostile ship = (BasicEntityShipHostile) host;
        	
        	this.targetSelector = new TargetHelper.SelectorForHostile(ship);
    		this.targetSorter = new TargetHelper.Sorter(ship);
    		
            //basic attr
            this.atk = ship.getAttackDamage();
            this.def = ship.getDefValue() * 0.5F;
            this.atkSpeed = ship.getAttackSpeed();
            this.movSpeed = ship.getMoveSpeed() * 0.2F + 0.3F;
            
            //設定發射位置
            this.posX = ship.posX;
            this.posY = launchPos;
            this.posZ = ship.posZ;
            this.setPosition(this.posX, this.posY, this.posZ);
            
            //設定基本屬性
            double mhp = ship.getMaxHealth() * 0.06F;
            
    	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mhp);
    		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
    		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(ship.getAttackRange()+32D);
    		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
    		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
        
    		//AI flag
            this.numAmmoLight = 9;
            this.numAmmoHeavy = 0;
            this.useAmmoLight = true;
            this.useAmmoHeavy = false;
            this.backHome = false;
            this.canFindTarget = true;
    				
    		//設定AI
    		this.setAIList();
        }
        else {
        	return;
        }
	}
	

}
