package com.lulan.shincolle.entity.other;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityFloatingFort extends BasicEntityAirplane {
	
	public EntityFloatingFort(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityFloatingFort(World world, BasicEntityShipLarge host, EntityLivingBase target, double launchPos) {
		super(world);
		this.world = world;
        this.host = host;
        this.targetEntity = target;
        
        //basic attr
        this.atk = host.getStateFinal(ID.ATK_H) * 0.5F;
        this.atkSpeed = host.getStateFinal(ID.SPD);
        this.movSpeed = 0.35F;
        
        //AI flag
        this.numAmmoLight = 0;
        this.numAmmoHeavy = 1;
        this.useAmmoLight = false;
        this.useAmmoHeavy = true;
        
        //設定發射位置
        this.posX = host.posX;
        this.posY = launchPos;
        this.posZ = host.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(host.getStateFinal(ID.HP)*0.05D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(host.getStateFinal(ID.HIT)+32D); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.5D);
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
		
		this.setAttackTarget(targetEntity);
	}
	
	@Override
	public void onUpdate() {
		//client side
		if(this.worldObj.isRemote) {
			if(this.ticksExisted % 2 == 0) {
				ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+0.2D, this.posZ, 
			      		-this.motionX*0.5D, 0.07D, -this.motionZ*0.5D, (byte)29);
			}
		}
		//server side
		else {
			//目標消失或死亡, 直接移除此entity
			if(this.targetEntity == null || !this.targetEntity.isEntityAlive() || this.ticksExisted >= 500) {
				this.setDead();
				return;
			}

			//持續向目標移動
			updateAttackAI();
		}
		
		super.onUpdate();
	}
	
	//attack AI: move and call onImpact
	private void updateAttackAI() {
		if(this.targetEntity != null) {  //for lots of NPE issue-.-
            //目標距離計算
            float distX = (float) (targetEntity.posX - this.posX);
            float distY = (float) (targetEntity.posY + 1F - this.posY);
            float distZ = (float) (targetEntity.posZ - this.posZ);	
            float distSq = distX*distX + distY*distY + distZ*distZ;

            //每30 tick找一次路徑, 直到距離目標X格內
        	if(this.ticksExisted % 20 == 0) {
	        	if(distSq > 4F) {	//距離約2格
		        	this.getShipNavigate().tryMoveToEntityLiving(targetEntity, 1D);
	        	}
        	}
        	
        	if(distSq <= 6F) {
        		this.getShipNavigate().clearPathEntity();
        		this.onImpact();
        	}
    	}//end attack target != null
	}

	//觸發爆炸攻擊
	private void onImpact() {
		boolean isTargetHurt = false;
		//get attack value
		float atk2 = this.atk;
		//set knockback value (testing)
		float kbValue = 0.08F;
		
		//calc miss chance, if not miss, calc cri/multi hit
		//計算範圍爆炸傷害: 判定bounding box內是否有可以吃傷害的entity
        EntityLivingBase hitEntity = null;
        AxisAlignedBB impactBox = this.boundingBox.expand(4.5D, 4.5D, 4.5D); 
        List hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
        
        //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
        if(hitList != null && !hitList.isEmpty()) {
            for(int i = 0; i < hitList.size(); ++i) {
            	atk2 = this.atk;
            	hitEntity = (EntityLivingBase)hitList.get(i);
            	
            	//目標可以被碰撞, 且目標不同主人, 則判定可傷害
            	if(hitEntity.canBeCollidedWith() && !EntityHelper.checkSameOwner(this.host, hitEntity)) {

        			//calc critical, only for type:ship
            		if(host != null && (this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_CRI))) {
            			atk2 *= 3F;
                		//spawn critical particle
                		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
                    	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host, 11, false), point);
                	}
            		
            		//若攻擊到玩家, 則傷害減為25%, 且最大傷害固定為TNT傷害 (non-owner)
                	if(hitEntity instanceof EntityPlayer) {
                		atk2 *= 0.25F;
                		
                		if(atk2 > 59F) {
                			atk2 = 59F;	//same with TNT
                		}
                		
                		//check friendly fire
                		if(!EntityHelper.doFriendlyFire(this.host, (EntityPlayer) hitEntity)) {
                			atk2 = 0F;
                		}
                	}
                	
            		//對entity造成傷害
                	if(host != null) {
                		isTargetHurt = hitEntity.attackEntityFrom(DamageSource.causeMobDamage(host).setExplosion(), atk2);
                	}
                	else {
                		isTargetHurt = hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this).setExplosion(), atk2);
                	}
            		//if attack success
            	    if(isTargetHurt) {
            	    	//calc kb effect
            	        if(this.kbValue > 0) {
            	        	hitEntity.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
            	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
            	            motionX *= 0.6D;
            	            motionZ *= 0.6D;
            	        }             	 
            	    }
            	}//end can be collided with
            }//end hit target list for loop
        }//end hit target list != null

        //send packet to client for display partical effect
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 2, false), point);
        
        this.setDead();
	}
	
	@Override
	public boolean useAmmoLight() {
		return false;
	}

	@Override
	public boolean useAmmoHeavy() {
		return true;
	}


}

