package com.lulan.shincolle.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipInRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.reference.GUIs;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityCarrierWo extends BasicEntitySmallShip {
	
	public EntityCarrierWo(World world) {
		super(world);
		this.setSize(0.9F, 2.7F);
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle:EntityCarrierWo.name"));
		this.ShipType = AttrValues.ShipType.STANDARD_CARRIER;
		this.ShipID = AttrID.CarrierWO;
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.8F;
	}
	
	public void setAIList() {
		super.setAIList();
		
//		//floating on water
//		this.tasks.addTask(1, new EntityAIShipSit(this, this.getOwner()));	   //0101
//		this.tasks.addTask(2, new EntityAIShipFollowOwner(this, 7F, 12F));	   //0011
//		
//		//use range attack (light)
//		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));			   //0011
//		
//		//use melee attack
//		this.tasks.addTask(12, new EntityAIAttackOnCollide(this, 1D, true));   //0011
//		this.tasks.addTask(13, new EntityAIMoveTowardsTarget(this, 1D, 64F));  //0001
//		
//		//idle AI
//		//moving
//		this.tasks.addTask(21, new EntityAIOpenDoor(this, true));			   //0000
//		
//		this.tasks.addTask(23, new EntityAIShipFloating(this));				   //0110
//		this.tasks.addTask(24, new EntityAIWatchClosest(this, EntityPlayer.class, 5F));	  //0010
//		this.tasks.addTask(24, new EntityAIWatchClosest(this, BasicEntityShip.class, 7F));//0010
//		this.tasks.addTask(25, new EntityAIWander(this, 0.8D));				   //0001
//		this.tasks.addTask(25, new EntityAILookIdle(this));					   //0011
		
/* 		//switch AI method
		this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        ItemStack itemstack = this.getHeldItem();

        if (itemstack != null && itemstack.getItem() == Items.bow)
        {
            this.tasks.addTask(4, this.aiArrowAttack);
        }
        else
        {
            this.tasks.addTask(4, this.aiAttackOnCollide);
        }*/
	}
	
	public void setAITargetList() {	
		//target AI
	//NYI:	this.targetTasks.addTask(1, new EntityAIOwnerPointTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(3, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIShipInRangeTarget(this, 0.4F, 1));
	}
	
	//平常音效
	protected String getLivingSound() {
        return Reference.MOD_ID+":ship-say";
    }
	
	//受傷音效
    protected String getHurtSound() {
    	
        return Reference.MOD_ID+":ship-hurt";
    }

    //死亡音效
    protected String getDeathSound() {
    	return Reference.MOD_ID+":ship-death";
    }

    //音效大小
    protected float getSoundVolume() {
        return 0.4F;
    }
    
    //this method work in server side
    @Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {		
    	//set hurt face
    	if(this.getEntityState(AttrID.Emotion) != AttrValues.Emotion.O_O) {
    		this.setEntityEmotion(AttrValues.Emotion.O_O, true);
    	}	
  	
    	return super.attackEntityFrom(attacker, atk);
    }
      
    @Override
    public void onLivingUpdate() {
    	//check server side
    	if(this.worldObj.isRemote) {
    		if(this.ticksExisted % 5 ==  0) {
    			//若顯示裝備時, 則生成眼睛煙霧特效 (client only)
    			if(getEntityState(AttrID.State) >= AttrValues.State.EQUIP) {
    				float radYaw = this.rotationYaw / 57.2957F;
    				float offX = MathHelper.cos(radYaw) * 0.55F;
    				float offZ = MathHelper.sin(radYaw) * 0.55F;
//    				LogHelper.info("DEBUG : cos Yaw = "+offX+" "+offZ);
    				EntityFX particleSprayL = new EntityFXSpray(worldObj, 
                    		this.posX+offX, this.posY+2.7D, this.posZ+offZ, 
                    		0D, 0.05D, 0D,
                    		1F, 0F, 0F, 0.7F);
                	Minecraft.getMinecraft().effectRenderer.addEffect(particleSprayL);
                	EntityFX particleSprayR = new EntityFXSpray(worldObj, 
                    		this.posX-offX, this.posY+2.7D, this.posZ-offZ, 
                    		0D, 0.05D, 0D,
                    		1F, 0F, 0F, 0.7F);
                	Minecraft.getMinecraft().effectRenderer.addEffect(particleSprayR);
                	
                	
    			}			
    		}	
    	}
    	
    	super.onLivingUpdate();
    }
	

}


