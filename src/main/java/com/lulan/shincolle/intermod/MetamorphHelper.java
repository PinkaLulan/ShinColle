package com.lulan.shincolle.intermod;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TeamHelper;

import mchorse.metamorph.api.EntityUtils;
import mchorse.metamorph.api.MorphAPI;
import mchorse.metamorph.api.MorphManager;
import mchorse.metamorph.api.events.MorphActionEvent;
import mchorse.metamorph.api.morphs.AbstractMorph;
import mchorse.metamorph.api.morphs.EntityMorph;
import mchorse.metamorph.capabilities.morphing.IMorphing;
import mchorse.metamorph.capabilities.morphing.Morphing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

/**
 * for mod: Metamorph
 */
public class MetamorphHelper
{
	
	
	/**
	 * called in event: EventHandler::onPlayerTick
	 */
	public static void onPlayerTickHelper(EntityPlayer player, CapaTeitoku capa)
	{
		//null check, every 16 ticks check
		if (capa == null || (player.ticksExisted & 31) != 0) return;
		
        IMorphing im = Morphing.get(player);
        
        if (im != null && im.getCurrentMorph() instanceof EntityMorph)
        {
        	EntityMorph em = (EntityMorph) im.getCurrentMorph();
        	
        	if (em.getEntity() instanceof IShipMorph)
        	{
        		//server side and player tick = 32, demorph (for reset morph on login)
        		if (!player.world.isRemote && player.ticksExisted == 32)
        		{
        			MorphAPI.demorph(player);
        			return;
        		}
        		
        		//both side: set morph host
        		IShipMorph ism = (IShipMorph) em.getEntity();
        		
    			ism.setIsMorph(true);
    			ism.setMorphHost(player);
    			capa.morphEntity = em.getEntity();
        	}
        }
        else
        {
        	capa.morphEntity = null;
        }
	}
	
	/**
	 * called in event: EventHandler::onMorphAction
	 * SERVER SIDE ONLY
	 */
	public static void onMorphActionHelper(MorphActionEvent event)
	{
		LogHelper.debug("DEBUG: Metamorph: cast morph action: "+event.player.inventory.currentItem);
		
		//server side
		if (!event.player.world.isRemote)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			int ci = event.player.inventory.currentItem;
			
//			//active ship skill bar: currentItem = 0~3: skill, 4~8:inventory
//			if (ConfigHandler.enableMetamorphSkill)
//			{
//				//open morph gui
//				if (ci > 3)
//				{
//					openMorphGui(capa);
//					return;
//				}
//				//use morph skill 0~3
//				else
//				{
//					//TODO
//				}
//			}
//			else
//			{
				//open morph gui
				openMorphGui(capa);
				return;
//			}
		}
		
	}
	
	/**
	 * open morph entity gui
	 */
	public static void openMorphGui(CapaTeitoku capa)
	{
		if (capa != null && capa.player != null && !capa.player.world.isRemote &&
			capa.morphEntity instanceof BasicEntityShip)
		{
			FMLNetworkHandler.openGui(capa.player, ShinColle.instance, ID.Gui.MORPHINVENTORY, capa.player.world, 0, 0, 0);
			return;
		}
	}
	
	/**
	 * get ship morph by target wrench click
	 */
	public static void getShipMorph(EntityPlayer player, Entity target)
	{
		if (player == null) return;
		
		//server side
		if (!player.world.isRemote && target instanceof BasicEntityShip)
		{
			BasicEntityShip ship = (BasicEntityShip) target;
			
			//check owner
			if (!TeamHelper.checkSameOwner(player, ship)) return;
			
			//check morph already got by player
			String name = MorphManager.INSTANCE.morphNameFromEntity(target);
	        if (!MorphManager.INSTANCE.hasMorph(name)) return;
	        
			//get morph capa of player
	        IMorphing capability = Morphing.get(player);
	        if (capability == null) return;
	        
	        //get target morph data
	        NBTTagCompound tag = new NBTTagCompound();
	        NBTTagCompound tagData = EntityUtils.stripEntityNBT(target.serializeNBT());
	        if (tagData == null) return;
	        
	        //tweak morph HP to 20
			NBTTagList tagList = tagData.getTagList("Attributes", Constants.NBT.TAG_COMPOUND);
			if (tagList == null) return;
			
			for (int i = 0; i < tagList.tagCount(); i++)
	        {
	            NBTTagCompound tags = tagList.getCompoundTagAt(i);
	            String attrs = tags.getString("Name");
	            
	            if (attrs == "generic.maxHealth")
	            {
	            	tags.setDouble("Base", 20D);
	            }
	        }
			
			//remove ship inventory
			tagData.removeTag("CpInv");
			
			//set target nbt data
			tag.setString("Name", name);
	        tag.setTag("EntityData", tagData);
	        
	        //player get morph
	        AbstractMorph morph = MorphManager.INSTANCE.morphFromNBT(tag);
	        MorphAPI.acquire(player, morph);
		}//end server side
	}
	
	
}