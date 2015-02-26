package com.lulan.shincolle.entity;

import com.lulan.shincolle.ai.EntityAIShipAircraftAttack;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.client.particle.EntityFXMiss;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAirplane extends BasicEntityAirplane {
	
	public EntityAirplane(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityAirplane(World world, BasicEntityShip host, EntityLivingBase target, double launchPos) {
		super(world);
		this.world = world;
        this.hostEntity = host;
        this.targetEntity = target;
        
        //basic attr
        this.atk = host.getStateFinal(ID.ATK_AL);
        this.atkSpeed = host.getStateFinal(ID.SPD);
        //AI flag
        this.numAmmoLight = 6;
        this.numAmmoHeavy = 0;
        this.useAmmoLight = true;
        this.useAmmoHeavy = false;
        //設定發射位置
        this.posX = host.posX;
        this.posY = launchPos;
        this.posZ = host.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);

	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(host.getStateFinal(ID.HP)*0.1D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(host.getStateFinal(ID.MOV)*0.2D + 0.45D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
	
	//setup AI
  	protected void setAIList() {
  		this.clearAITasks();
  		this.clearAITargetTasks();

  		this.getNavigator().setEnterDoors(true);
  		this.getNavigator().setAvoidsWater(false);
  		this.getNavigator().setCanSwim(true);
  		
  		this.tasks.addTask(1, new EntityAIShipAircraftAttack(this));
		this.setAttackTarget(targetEntity);
  	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//client side particle
		if(this.worldObj.isRemote) {	
			if(this.ticksExisted % 2 == 0) {
				EntityFX particleSpray = new EntityFXSpray(worldObj, 
	          		this.posX-this.motionX*1.5D, this.posY+0.5D-this.motionY*1.5D, this.posZ-this.motionZ*1.5D, 
	          		-this.motionX*0.5D, -this.motionY*0.5D, -this.motionZ*0.5D,
	          		0.2F, 1.0F, 0.6F, 0.7F);
				Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray);
			}
		}
	}

}
