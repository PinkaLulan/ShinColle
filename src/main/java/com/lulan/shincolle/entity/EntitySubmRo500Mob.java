package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntitySubmRo500Mob extends BasicEntityShipHostile {

	public EntitySubmRo500Mob(World world) {
		super(world);
		this.setSize(0.7F, 1.4F);
      
        //basic attr
        this.atk = (float) ConfigHandler.scaleMobU511[ID.ATK];
        this.atkSpeed = (float) ConfigHandler.scaleMobU511[ID.SPD];
        this.atkRange = (float) ConfigHandler.scaleMobU511[ID.HIT];
        this.defValue = (float) ConfigHandler.scaleMobU511[ID.DEF];
        this.movSpeed = (float) ConfigHandler.scaleMobU511[ID.MOV];
        this.stepHeight = 1F;

        //AI flag
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
        this.StateEmotion = new byte[] {ID.State.EQUIP02, 0, 0, 0, 0, 0};
        
        //misc
        this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, ID.S_SubmarineRo500+2);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ConfigHandler.scaleMobU511[ID.HP]);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(atkRange + 16); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
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
		return this.height * 1.2F;
	}
	
	//平常音效新增: garuru
	@Override
	protected String getLivingSound() {
		if(rand.nextInt(8) == 0) {
			return Reference.MOD_ID+":ship-garuru";
		}
		else {
			return super.getLivingSound();
		}
    }
	
	//chance drop
	@Override
	public ItemStack getDropEgg() {
		return this.rand.nextInt(5) == 0 ? this.dropItem : null;
	}
	
	//setup AI
	@Override
	protected void setAIList() {
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));			   //0011
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 1D, 40F));   //0001
	}
	
	//set invisible
	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
  		if(!worldObj.isRemote) {
  			//add aura to master every N ticks
  			if(this.ticksExisted % 200 == 0) {
  				if(this.rand.nextInt(2) == 0) {
  					this.addPotionEffect(new PotionEffect(Potion.invisibility.id, 200));
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
	
	//招喚高速魚雷
  	@Override
  	public boolean attackEntityWithAmmo(Entity target) {
  		//get attack value
  		float atk = this.atk;
  		
  		//set knockback value (testing)
  		float kbValue = 0.05F;
  		//飛彈是否採用直射
  		boolean isDirect = false;
  		//計算目標距離
  		float tarX = (float)target.posX;	//for miss chance calc
  		float tarY = (float)target.posY;
  		float tarZ = (float)target.posZ;
  		float distX = tarX - (float)this.posX;
  		float distY = tarY - (float)this.posY;
  		float distZ = tarZ - (float)this.posZ;
  		float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
  		float launchPos = (float)posY + height * 0.7F;
          
  		//超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
  		if((distX*distX+distY*distY+distZ*distZ) < 36F) {
  			isDirect = true;
  		}
  		if(getShipDepth() > 0D) {
          	isDirect = true;
          	launchPos = (float)posY;
  		}
  	
  		//play cannon fire sound at attacker
  		this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  		//play entity attack sound
  		if(this.getRNG().nextInt(10) > 7) {
          	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  		}
          
  		//calc miss chance, miss: add random offset(0~6) to missile target 
  		if(this.rand.nextFloat() < 0.2F) {
  			tarX = tarX - 3F + this.rand.nextFloat() * 6F;
  			tarY = tarY + this.rand.nextFloat() * 3F;
  			tarZ = tarZ - 3F + this.rand.nextFloat() * 6F;
  			//spawn miss particle
  			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
  		}

  		//spawn missile
  		EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
          		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect, 0.08F);
  		this.worldObj.spawnEntityInWorld(missile);
          
  		return true;
	}
  	
  	//一般魚雷
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {	
		//get attack value
		float atkHeavy = this.atk * 4F;
		
		//set knockback value (testing)
		float kbValue = 0.08F;
		//飛彈是否採用直射
		boolean isDirect = false;
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float distX = tarX - (float)this.posX;
		float distY = tarY - (float)this.posY;
		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        float launchPos = (float)posY + height * 0.5F;
        
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if((distX*distX+distY*distY+distZ*distZ) < 36F || this.getShipDepth() > 0D) {
        	isDirect = true;
        }
	
		//play cannon fire sound at attacker
        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.getRNG().nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        if(this.rand.nextFloat() < 0.25F) {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }
        
        //發射者煙霧特效 (不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);

        //spawn missile
        EntityAbyssMissile missile1 = new EntityAbyssMissile(this.worldObj, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atkHeavy, kbValue, isDirect, -1F);    
        this.worldObj.spawnEntityInWorld(missile1);
        
        return true;
	}

}





