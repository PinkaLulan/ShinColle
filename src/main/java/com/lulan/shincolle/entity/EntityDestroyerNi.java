package com.lulan.shincolle.entity;

import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipFlee;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipInRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityDestroyerNi extends BasicEntityShipSmall {

	public EntityDestroyerNi(World world) {
		super(world);
		this.setSize(0.8F, 1.4F);	//碰撞大小 跟模型大小無關
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityDestroyerNi.name"));
		this.ShipType = ID.ShipType.DESTROYER;
		this.ShipID = ID.S_DestroyerNI;
		this.ModelPos = new float[] {0F, 0F, 0F, 25F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		
		this.initTypeModify();
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return this.height * 1.29F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//high priority
		this.tasks.addTask(1, new EntityAIShipSit(this));	   				   //0101
		this.tasks.addTask(2, new EntityAIShipFlee(this));					   //0111
		this.tasks.addTask(3, new EntityAIShipFollowOwner(this));	   		   //0111
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));			   //0011
		
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
		this.tasks.addTask(26, new EntityAILookIdle(this));					   //0011
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
          
  		if(!worldObj.isRemote) {
  			//add aura to master every 100 ticks
  			if(this.ticksExisted % 100 == 0) {
  				EntityPlayerMP player = EntityHelper.getOnlinePlayer(this.getOwner());
  				if(getStateFlag(ID.F.IsMarried) && getStateMinor(ID.N.NumGrudge) > 0 && player != null && getDistanceSqToEntity(player) < 256D) {
  					//potion effect: id, time, level
  	  	  			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 300, getStateMinor(ID.N.ShipLevel) / 50));
  				}
  			}
  		}    
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
				}
				return true;
			}
		}
		
		super.interact(player);
		return false;
  	}

}



