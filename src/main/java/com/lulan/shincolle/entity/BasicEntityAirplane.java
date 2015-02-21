package com.lulan.shincolle.entity;

import java.util.UUID;

import com.lulan.shincolle.client.particle.EntityFXMiss;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BasicEntityAirplane extends EntityLiving {

	protected BasicEntityShip hostEntity;  	//host target
	protected EntityLivingBase targetEntity;	//onImpact target (for entity)
	protected World world;
    
    //attributes
    public float atk;				//damage
    public float atkSpeed;			//attack speed
    public float kbValue;			//knockback value
    
    //AI flag
    public int numAmmoLight;
    public int numAmmoHeavy;
    public boolean useAmmoLight;
    public boolean useAmmoHeavy;
	
    public BasicEntityAirplane(World world) {
        super(world);
    }
    
    @Override
	public boolean isAIEnabled() {
		return true;
	}
  	
    //clear AI
  	protected void clearAITasks() {
  	   tasks.taskEntries.clear();
  	}
  	
  	//clear target AI
  	protected void clearAITargetTasks() {
  	   targetTasks.taskEntries.clear();
  	}

    //禁止任何掉落計算
    protected void fall(float world) {}
    protected void updateFallState(double par1, boolean par2) {}
    public boolean isOnLadder() {
        return false;
    }
    
    public EntityLivingBase getOwner() {
        return this.hostEntity;
    }
    
    public EntityLivingBase getTarget() {
        return this.targetEntity;
    }

    //移動計算, 去除gravity部份
    public void moveEntityWithHeading(float movX, float movZ) {
        if(this.isInWater() || this.handleLavaMovement()) {
            this.moveFlying(movX, movZ, 0.04F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8D;
            this.motionY *= 0.8D;
            this.motionZ *= 0.8D;
        }
        else {
            float f2 = 0.91F;

            if(this.onGround) {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            this.moveFlying(movX, movZ, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;

            if(this.onGround) {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f2;
            this.motionY *= (double)f2;
            this.motionZ *= (double)f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

        if(f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//server side
		if(!this.worldObj.isRemote) {
			//前幾秒直線往目標移動
			if(this.ticksExisted < 30 && this.getAttackTarget() != null) {
				double distX = this.getAttackTarget().posX - this.posX;
				double distZ = this.getAttackTarget().posZ - this.posZ;
				double distSqrt = MathHelper.sqrt_double(distX*distX + distZ*distZ);
				
				this.motionX = distX / distSqrt * 0.3D;
				this.motionZ = distZ / distSqrt * 0.3D;
				this.motionY = 0.05D;		
			}
			
			//超過30秒或者無攻擊目標, 自動消失
			if(this.ticksExisted > 600) {
				this.setDead();
			}
			
			//攻擊目標消失, 重設目標為host的目標
			if(this.getAttackTarget() == null && this.hostEntity != null) {
				this.setAttackTarget(this.hostEntity.getAttackTarget());
			}
			
			if(this.isInWater() && this.ticksExisted % 100 == 0) {
				this.setAir(300);
			}
		}
		
		//計算面向角度 (both side)
		if(this.ticksExisted < 30) {
			float[] degree = EntityHelper.getLookDegree(posX - prevPosX, posY - prevPosY, posZ - prevPosZ);
			this.rotationYaw = degree[0];
			this.rotationPitch = degree[1];
		}	
	}

	//light attack
	public boolean attackEntityWithAmmo(Entity target) {
		float atkLight = this.atk;
		float kbValue = 0.03F;

		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-machinegun", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //attack particle
        TargetPoint point0 = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 8), point0);
		
        //calc miss chance
        if(this.rand.nextInt(8) == 0) {	//12.5% miss
        	atkLight = 0;	//still attack, but no damage
        	//spawn miss particle
    		EntityFX particleMiss = new EntityFXMiss(worldObj, 
    		          this.hostEntity.posX, this.hostEntity.posY+this.hostEntity.height, this.hostEntity.posZ, 1F);	    
    		Minecraft.getMinecraft().effectRenderer.addEffect(particleMiss);
        }
//atkLight = 0;       
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atkLight);

	    //if attack success
	    if(isTargetHurt) {
	    	//calc kb effect
	        if(kbValue > 0) {
	            target.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
	        }
	        
        	//send packet to client for display partical effect  
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(target, 0), point1);
        }
	    
	    //消耗彈藥計算
  		if(numAmmoLight > 0) {
  			numAmmoLight--;
  			
  			if(numAmmoLight <= 0) {
  				this.setDead();
  			}
  		}

	    return isTargetHurt;
	}

	public boolean attackEntityWithHeavyAmmo(Entity target) {
		//get attack value
		float atkHeavy = this.atk * 5F;
		//set knockback value (testing)
		float kbValue = 0.08F;
//atkHeavy=0;
		//play cannon fire sound at attacker
        this.playSound(Reference.MOD_ID+":ship-fireheavy", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //calc miss chance
        if(this.rand.nextInt(10) == 0) {	//10% miss
        	atkHeavy = 0;	//still attack, but no damage
        	//spawn miss particle
    		EntityFX particleMiss = new EntityFXMiss(worldObj, 
    		          this.hostEntity.posX, this.hostEntity.posY+this.hostEntity.height, this.hostEntity.posZ, 1F);	    
    		Minecraft.getMinecraft().effectRenderer.addEffect(particleMiss);
        }

        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
        		target.posX, target.posY+target.height*0.2D, target.posZ, this.posY-0.8D, atkHeavy, kbValue, true);
        this.worldObj.spawnEntityInWorld(missile);
        
        //消耗彈藥計算
  		if(numAmmoHeavy > 0) {
  			numAmmoHeavy--;
  			
  			if(numAmmoHeavy <= 0) {
  				this.setDead();
  			}
  		}
  		
        return true;
	}

}

