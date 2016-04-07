package com.lulan.shincolle.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAircraftAttack;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityAirplane extends BasicEntityAirplane {
	
	public EntityAirplane(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	@Override
	public void setAttrs(World world, IShipAircraftAttack host, Entity target, double launchPos) {
		this.world = world;
        this.host = host;
        this.atkTarget = target;
        
        if(host instanceof BasicEntityShip) {
        	BasicEntityShip ship = (BasicEntityShip) host;
        	
        	this.targetSelector = new TargetHelper.Selector(ship);
    		this.targetSorter = new TargetHelper.Sorter(ship);
    		
            //basic attr
            this.atk = ship.getAttackBaseDamage(3, target);
            this.def = ship.getStateFinal(ID.DEF) * 0.5F;
            this.atkSpeed = ship.getStateFinal(ID.SPD);
            this.movSpeed = ship.getStateFinal(ID.MOV) * 0.2F + 0.3F;
            
            //設定發射位置
            this.posX = ship.posX;
            this.posY = launchPos;
            this.posZ = ship.posZ;
            this.setPosition(this.posX, this.posY, this.posZ);
            
            double mhp = ship.getLevel() + ship.getStateFinal(ID.HP)*0.1D;
            
    	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mhp);
    		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
    		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(ship.getStateFinal(ID.HIT)+64D); //此為找目標, 路徑的範圍
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
        //not ship
        else {
        	return;
        }
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//client side particle
		if(this.worldObj.isRemote) {	
			applyFlyParticle();
		}
		//server side
		else {
			if(!this.hasAmmoLight()) {
				this.backHome = true;
				this.canFindTarget = false;
				this.setEntityTarget(null);
			}
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float atk) {
		//33% dodge heavy damage
		if(atk > this.getMaxHealth() * 0.5F && this.getRNG().nextInt(3) == 0) {
			//spawn miss particle
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 34, false), point);
			return false;
		}
        
        return super.attackEntityFrom(source, atk);
    }
	
	protected void applyFlyParticle() {
		ParticleHelper.spawnAttackParticleAt(this.posX-this.motionX*1.5D, this.posY+0.5D-this.motionY*1.5D, this.posZ-this.motionZ*1.5D, 
          		-this.motionX*0.5D, -this.motionY*0.5D, -this.motionZ*0.5D, (byte)17);
	}


}
