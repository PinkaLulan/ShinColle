package com.lulan.shincolle.entity.hostile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipBoss;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBattleshipNGTBoss extends BasicEntityShipBoss {

	public EntityBattleshipNGTBoss(World world) {
		super(world);
		this.setSize(1.5F, 7F);
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityBattleshipNGTBoss.name"));
        
        //basic attr
		this.atk = (float) ConfigHandler.scaleBossNGT[ID.ATK];
        this.atkSpeed = (float) ConfigHandler.scaleBossNGT[ID.SPD];
        this.atkRange = (float) ConfigHandler.scaleBossNGT[ID.HIT];
        this.defValue = (float) ConfigHandler.scaleBossNGT[ID.DEF];
        this.movSpeed = (float) ConfigHandler.scaleBossNGT[ID.MOV];

        //AI flag
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
        
        //misc
        this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, ID.S_BattleshipNagato+2);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ConfigHandler.scaleBossNGT[ID.HP]);
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

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));			   //0011
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 1D, 40F));   //0001
	}
	
	//num rensouhou++
	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();

  		//client side
  		if(worldObj.isRemote) {
  			if(this.ticksExisted % 5 == 0) {
  				if(getStateEmotion(ID.S.Phase) == 1 || getStateEmotion(ID.S.Phase) == 3) {
   	  				//生成氣彈特效
  	  				ParticleHelper.spawnAttackParticleAtEntity(this, 0.3D, 2D, 0D, (byte)1);
  				}
			
  				if(getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
  					//計算煙霧位置
  	  				float[] partPos = ParticleHelper.rotateXZByAxis(-1.8F, 0F, (this.renderYawOffset % 360) / 57.2957F, 1F);	
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+5.5D, posZ+partPos[0], 0D, 0D, 0D, (byte)24);
  				}	
  			}
  		}
  	}
	
	@Override
	protected boolean interact(EntityPlayer player) {
		//use kaitai hammer to kill boss (creative mode only)
		if(!this.worldObj.isRemote && player.capabilities.isCreativeMode) {
			if(player.inventory.getCurrentItem() != null && 
			   player.inventory.getCurrentItem().getItem() == ModItems.KaitaiHammer) {
				this.setDead();
			}
		}
		
        return false;
    }
	
	//修改煙霧位置
  	@Override
  	public boolean attackEntityWithAmmo(Entity target) {
  		//get attack value
		float atk = this.atk;
		//set knockback value (testing)
		float kbValue = 0.05F;
		//update entity look at vector (for particle spawn)
        //此方法比getLook還正確 (client sync問題)
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
      
        //發射者煙霧特效
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY+3.5D, this.posZ, distX, 2.8D, distZ, true), point);

		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-firesmall", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.rand.nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }

        //calc miss chance, if not miss, calc cri/multi hit   
        if(this.rand.nextFloat() < 0.2F) {
        	atk = 0;	//still attack, but no damage
        	//spawn miss particle
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }
        else {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if(this.rand.nextFloat() < 0.15F) {
        		atk *= 1.5F;
        		//spawn critical particle
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
        	}
        	else {
        		//calc double hit
            	if(this.rand.nextFloat() < 0.15F) {
            		atk *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, false), point);
            	}
            	else {
            		//calc double hit
                	if(this.rand.nextFloat() < 0.15F) {
                		atk *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, false), point);
                	}
            	}
        	}
        }
        
        //vs player = 25% dmg
  		if(target instanceof EntityPlayer) {
  			atk *= 0.25F;
  			
    		if(atk > 159F) {
    			atk = 159F;	//same with TNT
    		}
  		}
  		
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);

	    //if attack success
	    if(isTargetHurt) {
	    	//calc kb effect
	        if(kbValue > 0) {
	            target.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
	            motionX *= 0.6D;
	            motionZ *= 0.6D;
	        }
	        
        	//display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }

	    return isTargetHurt;
	}
  	
  	//TYPE 91 AP FIST
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {	
  		//get attack value
		float atk1 = this.atk * 4F;
		float atk2 = this.atk;
		
		//set knockback value (testing)
		float kbValue = 0.15F;
		
		boolean isTargetHurt = false;

		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float distX = tarX - (float)this.posX;
		float distY = tarY - (float)this.posY;
		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        float dX = distX / distSqrt;
        float dY = distY / distSqrt;
        float dZ = distZ / distSqrt;
	
		//play cannon fire sound at attacker
		int atkPhase = getStateEmotion(ID.S.Phase);
		
        switch(atkPhase) {
        case 0:
        case 2:
        	this.playSound(Reference.MOD_ID+":ship-ap_phase1", ConfigHandler.fireVolume, 1F);
        	break;
        case 1:
        	this.playSound(Reference.MOD_ID+":ship-ap_phase2", ConfigHandler.fireVolume, 1F);
        	break;
        case 3:
        	this.playSound(Reference.MOD_ID+":ship-ap_attack", ConfigHandler.fireVolume, 1F);
        	break;
    	default:
    		this.playSound(Reference.MOD_ID+":ship-ap_phase1", ConfigHandler.fireVolume, 1F);
    		break;
        }
        
        //play entity attack sound
        if(this.getRNG().nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //phase++
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        atkPhase++;
      
        if(atkPhase > 3) {	//攻擊準備完成, 計算攻擊傷害
        	//display hit particle on target
	        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 21, posX, posY, posZ, target.posX, target.posY, target.posZ, true), point);
        	
        	//calc miss chance, miss: atk1 = 0, atk2 = 50%
            if(this.rand.nextFloat() < 0.2F) {	//MISS
            	atk1 = 0F;
            	atk2 *= 0.5F;
            	//spawn miss particle
            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
            }
            else if(this.rand.nextFloat() < 0.15F) {	//CRI
        		atk1 *= 1.5F;
        		atk2 *= 1.5F;
        		//spawn critical particle
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
            }
            
            //vs player = 25% dmg
      		if(target instanceof EntityPlayer) {
      			atk1 *= 0.25F;
      			atk2 *= 0.25F;
      			
        		if(atk2 > 40F) {
        			atk1 = 160F;	//charge creeper
        			atk2 = 40F;		//TNT
        		}
      		}
      		
      		//對本體造成atk1傷害
      		isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk1);
      		
      		this.motionX = 0D;
  			this.motionY = 0D;
  			this.motionZ = 0D;
  			this.posX = tarX+dX*2F;
  			this.posY = tarY;
  			this.posZ = tarZ+dZ*2F;
  			this.setPosition(posX, posY, posZ);
      		
      		//對範圍造成atk2傷害
            EntityLivingBase hitEntity = null;
            AxisAlignedBB impactBox = this.boundingBox.expand(4.5D, 4.5D, 4.5D); 
            List hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
            float atkTemp = atk2;
            
            //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
            if(hitList != null && !hitList.isEmpty()) {
                for(int i=0; i<hitList.size(); ++i) {
                	atkTemp = atk2;
                	hitEntity = (EntityLivingBase)hitList.get(i);
                	
                	//目標不能是自己 or 主人
                	if(hitEntity != this && hitEntity.canBeCollidedWith() && EntityHelper.checkNotSameEntityID(this, hitEntity)) {
                		//calc miss and cri
                		if(this.rand.nextFloat() < 0.2F) {	//MISS
                        	atkTemp *= 0.5F;
                        }
                        else if(this.rand.nextFloat() < 0.15F) {	//CRI
                    		atkTemp *= 1.5F;
                        }
                		
                		//若攻擊到同陣營entity (ex: owner), 則傷害設為0 (但是依然觸發擊飛特效)
                		if(EntityHelper.checkSameOwner(this, hitEntity)) {
                			atkTemp = 0F;
                    	}
                		
                		//若攻擊到玩家, 最大傷害固定為TNT傷害 (non-owner)
                    	if(hitEntity instanceof EntityPlayer) {
                    		atkTemp *= 0.25F;
                    		
                    		if(atkTemp > 59F) {
                    			atkTemp = 59F;	//same with TNT
                    		}
                    	}

                		//if attack success
                	    if(hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), atkTemp)) {
                	    	//calc kb effect
                	        if(kbValue > 0) {
                	        	hitEntity.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
                	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
                	            motionX *= 0.6D;
                	            motionZ *= 0.6D;
                	        }             	 
                	    }
                	}//end can be collided with
                }//end hit target list for loop
            }//end hit target list != null
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        }
        else {
        	if(atkPhase == 2) {
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 23, this.posX, this.posY, this.posZ, 3D, 0.3D, 0D, true), point);
        	}
        	else {
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 22, this.posX, this.posY, this.posZ, 3D, 3D, 0D, true), point);
        	}
    		
        	this.setStateEmotion(ID.S.Phase, atkPhase, true);
        }
        
        return isTargetHurt;
	}

}

