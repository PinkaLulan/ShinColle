package com.lulan.shincolle.entity.other;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityAirplane extends BasicEntityAirplane {
	
	public EntityAirplane(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityAirplane(World world, BasicEntityShipLarge host, EntityLivingBase target, double launchPos) {
		super(world);
		this.world = world;
        this.host = host;
        this.atkTarget = target;
        
        //basic attr
        this.atk = host.getStateFinal(ID.ATK_AL);
        this.atkSpeed = host.getStateFinal(ID.SPD);
        this.movSpeed = host.getStateFinal(ID.MOV) * 0.2F + 0.3F;
        
        //AI flag
        this.numAmmoLight = 6;
        this.numAmmoHeavy = 0;
        this.useAmmoLight = true;
        this.useAmmoHeavy = false;
        this.antiAir = host.getStateFlag(ID.F.AntiAir);
        
        //設定發射位置
        this.posX = host.posX;
        this.posY = launchPos;
        this.posZ = host.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);

	    //設定基本屬性
        double mhp = host.getLevel() + host.getStateFinal(ID.HP)*0.2D;
        
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mhp);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(host.getStateFinal(ID.HIT)+32D); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//client side particle
		if(this.worldObj.isRemote) {	
			ParticleHelper.spawnAttackParticleAt(this.posX-this.motionX*1.5D, this.posY+0.5D-this.motionY*1.5D, this.posZ-this.motionZ*1.5D, 
	          		-this.motionX*0.5D, -this.motionY*0.5D, -this.motionZ*0.5D, (byte)17);
		}
		else {
			if(!this.hasAmmoLight()) this.backHome = true;
		}
	}

	@Override
	public boolean useAmmoLight() {
		return true;
	}

	@Override
	public boolean useAmmoHeavy() {
		return false;
	}


}
