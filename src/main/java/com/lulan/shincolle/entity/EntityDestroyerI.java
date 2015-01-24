package com.lulan.shincolle.entity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.reference.GUIs;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityDestroyerI extends BasicEntityShip {
	
	public boolean isKisaragi;
	
	public EntityDestroyerI(World world) {
		super(world);
		this.setSize(0.9F, 1.4F);	//碰撞大小 跟模型大小無關
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle:EntityDestroyerI.name"));
		this.ShipType = AttrValues.ShipType.DESTROYER;
		this.ShipID = AttrID.DestroyerI;
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);
		LogHelper.info("DEBUG : entity class get ExtEntityProps");	
		//for attribute calc
		rand = new Random();
		this.TypeModify[AttrID.HP] = AttrValues.ModHP[AttrID.DestroyerI];
		this.TypeModify[AttrID.ATK] = AttrValues.ModATK[AttrID.DestroyerI];
		this.TypeModify[AttrID.DEF] = AttrValues.ModDEF[AttrID.DestroyerI];
		this.TypeModify[AttrID.SPD+3] = AttrValues.ModSPD[AttrID.DestroyerI];
		this.TypeModify[AttrID.MOV+3] = AttrValues.ModMOV[AttrID.DestroyerI];
		this.TypeModify[AttrID.HIT+3] = AttrValues.ModHIT[AttrID.DestroyerI];
		
		
		//AI: AI優先度, AI(AI參數)
	//	this.tasks.addTask(0, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(1, new EntityAIWatchClosest2(this, EntityPlayer.class, 3F, 2F));
	//	this.tasks.addTask(2, new EntityAIPanic(this, 2F));
	//	this.tasks.addTask(3, new EntityAILookIdle(this));
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
	
	@Override
	public boolean interact(EntityPlayer player) {	
		LogHelper.info("DEBUG : attr final hp "+this.AttrFinalShort[AttrID.HP]);
		LogHelper.info("DEBUG : attr equip hp "+this.AttrEquipShort[AttrID.HP]);
		LogHelper.info("DEBUG : attr base hp "+getEntityAttribute(SharedMonsterAttributes.maxHealth));
			
		//debug test
		ShipLevel += 1;
		setShipLevel(ShipLevel, false);
		
		//Kisaragi test
		if(isKisaragi) {
			isKisaragi = false;
		}
		else {
			isKisaragi = true;
		}
		//shift+right click時打開GUI
		LogHelper.info("DEBUG : get ownerName "+this.getOwnerName());
		if (player.isSneaking() ) {  
			int eid = this.getEntityId();
			//player.openGui vs FMLNetworkHandler ?
		//	player.openGui(ShinColle.instance, GUIs.SHIPINVENTORY, this.worldObj, eid, 0, 0);
    		FMLNetworkHandler.openGui(player, ShinColle.instance, GUIs.SHIPINVENTORY, this.worldObj, this.getEntityId(), 0, 0);
    		return true;
		}

    	return false;	
	}
	
	//get block under entity
	public String getBlockUnder(Entity entity) {
	    int blockX = MathHelper.floor_double(entity.posX);
	    int blockY = MathHelper.floor_double(entity.boundingBox.minY)-1;
	    int blockZ = MathHelper.floor_double(entity.posZ);
	    
	    BlockUnderName = entity.worldObj.getBlock(blockX, blockY, blockZ).getLocalizedName();   
	    
	    return BlockUnderName;
	}


	

}
