package com.lulan.shincolle.entity.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityMountCaH extends BasicEntityMountLarge
{
	
    public EntityMountCaH(World world)
    {
		super(world);
		this.setSize(1.9F, 1.8F);
		this.isImmuneToFire = true;
		this.ridePos = new float[] {0.0F, 0F, 0.5F};
	}
    
    public EntityMountCaH(World world, BasicEntityShip host)
    {
		super(world);
        this.host = host;
        this.isImmuneToFire = true;
        this.ridePos = new float[] {0.0F, 0F, 0.5F};
        
        //basic attr
        this.atkRange = host.getStateFinal(ID.HIT);
        this.movSpeed = host.getStateFinal(ID.MOV);
        
        //AI flag
        this.StateEmotion = 0;
        this.StateEmotion2 = 0;
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
           
        //設定位置
        this.posX = host.posX;
        this.posY = host.posY;
        this.posZ = host.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
 
	    //設定基本屬性
        setupAttrs();
        
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
    
    @Override
	public float getEyeHeight()
    {
		return 1.7F;
	}
    
    @Override
    public double getMountedYOffset()
    {
    	return this.height;
    }

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.CARRIER;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(10, new EntityAIShipCarrierAttack(this));
	}
	
	//change melee damage to 100%
  	@Override
  	public boolean attackEntityAsMob(Entity target) {
  	//get attack value
  		float atk = host.getStateFinal(ID.ATK_AL);
  				
  		//experience++
  		host.addShipExp(ConfigHandler.expGain[0]);
  		
  		//attack time
  		host.setCombatTick(this.ticksExisted);

  	    //play entity attack sound
  	    if(this.getRNG().nextInt(10) > 8) {
  	    	this.playSound(Reference.MOD_ID+":ship-waka_attack", ConfigHandler.volumeShip * 0.5F, 1F);
  	    }
  	    
  	    //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this.host, 
        		(float)target.posX, (float)target.posY+target.height*0.2F, (float)target.posZ, 1F, atk, 0F, true, -1F);
        this.worldObj.spawnEntityInWorld(missile);

  	    //show emotes
  	    if(host != null) {
  	    	host.applyEmotesReaction(3);
  	    	
  	    	if(ConfigHandler.canFlare) {
  				host.flareTarget(target);
  			}
  	    }
  	  
  	    return true;
  	}
	

}






