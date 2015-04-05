package com.lulan.shincolle.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIWander;
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
import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipFlee;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipInRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityCarrierWo extends BasicEntityShipLarge {
	
	public EntityCarrierWo(World world) {
		super(world);
		this.setSize(0.8F, 1.7F);
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityCarrierWo.name"));
		this.ShipType = ID.ShipType.STANDARD_CARRIER;
		this.ShipID = ID.S_CarrierWO;
		this.ModelPos = new float[] {0F, 15F, 0F, 30F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		this.launchHeight = this.height * 1.1D;
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return this.height * 1.06F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 3;
	}
	
	public void setAIList() {
		super.setAIList();
		
		//high priority
		this.tasks.addTask(1, new EntityAIShipSit(this));	   				   //0101
		this.tasks.addTask(2, new EntityAIShipFlee(this));					   //0111
		this.tasks.addTask(3, new EntityAIShipFollowOwner(this));	   		   //0111
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));		   //0011
		
		//use melee attack
		if(this.getStateFlag(ID.F.UseMelee)) {
			this.tasks.addTask(12, new EntityAIShipAttackOnCollide(this, 1D, true));   //0011
			this.tasks.addTask(13, new EntityAIMoveTowardsTarget(this, 1D, 48F));  //0001
		}
		
		//idle AI
		//moving
		this.tasks.addTask(21, new EntityAIOpenDoor(this, true));			   //0000
		this.tasks.addTask(23, new EntityAIShipFloating(this));				   //0101
		this.tasks.addTask(24, new EntityAIShipWatchClosest(this, EntityPlayer.class, 6F, 0.1F)); //0010
		this.tasks.addTask(25, new EntityAIWander(this, 0.8D));				   //0001
		this.tasks.addTask(25, new EntityAILookIdle(this));					   //0011

	}
    
    //增加艦載機數量計算
  	@Override
  	public void calcShipAttributes(byte id) {
  		super.calcShipAttributes(id);
  		
  		this.maxAircraftLight += 10;
  		this.maxAircraftHeavy += 8;
  	}
      
    @Override
    public void onLivingUpdate() {
    	//check client side
    	if(this.worldObj.isRemote) {
    		if(this.ticksExisted % 5 ==  0) {
    			//若顯示裝備時, 則生成眼睛煙霧特效 (client only)
    			if(getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
    				//set origin position
    				float[] eyePosL = new float[] {0.7F, 0.3F, 1F};
    				float[] eyePosR = new float[] {0.7F, 0.3F, -1F};
    				float radYaw = 0F;
    				float radPitch = 0F;
    				float sinPitch = 0F;
    				
    				//get rotate degree (rad)
    				radYaw = this.rotationYawHead / 57.2957F;
    				radPitch = this.rotationPitch / 57.2957F;
    				sinPitch = MathHelper.sin(radPitch);

    				//低頭或抬頭, 眼睛位置移動 (由於頭部旋轉中心跟特效旋轉中心不同, 故要加上此offset)
    				if(radPitch > 0) {	//低頭: Z位置往前最多1.5, Y位置往下最多1
    					eyePosL[0] = eyePosL[0]+sinPitch*1.5F;
    					eyePosR[0] = eyePosR[0]+sinPitch*1.5F;
    					eyePosL[1] = eyePosL[1]-sinPitch;
    					eyePosR[1] = eyePosR[1]-sinPitch;
    				}
    				else {				//抬頭: Z位置往後最多2.5, Y位置往下最多0.5
    					eyePosL[0] = eyePosL[0]+sinPitch*2.5F;
    					eyePosR[0] = eyePosR[0]+sinPitch*2.5F;
    					eyePosL[1] = eyePosL[1]+sinPitch*0.5F;
    					eyePosR[1] = eyePosR[1]+sinPitch*0.5F;
    				}
    				
    				//坐下位置計算
    				if(this.isSitting()) {
    					eyePosL = new float[] {0F, 0.3F, -0.2F};
        				eyePosR = new float[] {0.7F, 0F, -2F};
    				}
    				
    				//側歪頭位置計算, 歪頭只會修改Y高度跟X位置
    				if(getStateEmotion(ID.S.Emotion2) == 1 && !this.isSitting()) {
    					float[] tiltLeft = ParticleHelper.rotateParticleByAxis(eyePosL[2], eyePosL[1], -0.24F, 1F);
    					float[] tiltRight = ParticleHelper.rotateParticleByAxis(eyePosR[2], eyePosR[1], -0.24F, 1F);
    					eyePosL[2] = tiltLeft[0];
    					eyePosL[1] = tiltLeft[1];
    					eyePosR[2] = tiltRight[0];
    					eyePosR[1] = tiltRight[1];
    				}

    				//依照新位置, 繼續旋轉Y軸
    				eyePosL = ParticleHelper.rotateParticleByYaw(eyePosL[0], eyePosL[1], eyePosL[2], radYaw, -radPitch, 0.5F);
    				eyePosR = ParticleHelper.rotateParticleByYaw(eyePosR[0], eyePosR[1], eyePosR[2], radYaw, -radPitch, 0.5F);		
    				
    				//旋轉完三軸, 生成特效
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosL[2], this.posY+2.5D+eyePosL[1], this.posZ+eyePosL[0], 
                    		0D, 0.05D, 0D, (byte)16);
    				
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosR[2], this.posY+2.5D+eyePosR[1], this.posZ+eyePosR[0], 
                    		0D, 0.05D, 0D, (byte)16);   	
    			}			
    		}	
    	}//end client
    	
    	super.onLivingUpdate();
    }
    
    @Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				switch(getStateEmotion(ID.S.State)) {
				case ID.State.NORMAL:
					setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
					break;
				case ID.State.EQUIP00:
					setStateEmotion(ID.S.State, ID.State.NORMAL, true);
					break;
				default:
					setStateEmotion(ID.S.State, ID.State.NORMAL, true);
					break;
				}
				return true;
			}
		}
		
		super.interact(player);
		return false;
  	}
    
    @Override
	public int getKaitaiType() {
		return 1;
	}
	

}


