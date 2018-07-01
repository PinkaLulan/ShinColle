package com.lulan.shincolle.intermod;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.capability.CapaShipSavedValues;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.InventoryHelper;
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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

/**
 * for mod: Metamorph
 */
public class MetamorphHelper
{
	
	
	/**
	 * check player is in ship morph and return morph entity
	 */
	public static BasicEntityShip getShipMorphEntity(EntityPlayer player)
	{
		if (player == null) return null;
		
		IMorphing im = Morphing.get(player);
		
		if (im != null && im.getCurrentMorph() instanceof EntityMorph)
        {
        	EntityMorph em = (EntityMorph) im.getCurrentMorph();
        	
        	if (em != null && em.getEntity() instanceof BasicEntityShip)
        	{
        		return (BasicEntityShip) em.getEntity();
        	}
        }
		
		return null;
	}
	
	/**
	 * check target is player and morphing to ship
	 * if eid is player, return morph entity
	 */
	public static Entity getMorphEntityByPlayerEID(int entityID, int worldID, boolean isClient)
	{
		Entity ent = EntityHelper.getEntityByID(entityID, worldID, isClient);
		
		if (CommonProxy.activeMetamorph && ent instanceof EntityPlayer)
		{
			Entity morph = getShipMorphEntity((EntityPlayer) ent);
			if (morph != null) return morph;
		}
		
		return ent;
	}
	
	/**
	 * demorph player, DO NOT call this in morph event!! (loop)
	 */
	public static void demorphPlayer(EntityPlayer player)
	{
		if (player == null) return;
		
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		if (capa == null) return;
		
		//remove morph entity in capa
		capa.morphEntity = null;
				
		//save morph ship data
		MetamorphHelper.writeToNBT(player);
		
		//reset HP of player
		player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
		
		//demorph
		MorphAPI.demorph(player);
		return;
	}
	
	/**
	 * called in event: EventHandler::onPlayerChangeDim
	 */
	public static void onPlayerChangeDimHelper(EntityPlayer player)
	{
		IMorphing im = Morphing.get(player);
        
        if (im != null)
        {
        	if (im.isMorphed())
        	{
        		demorphPlayer(player);
        		return;
        	}
        }
	}
	
	/**
	 * called in event: EventHandler::onPlayerTick
	 */
	public static void onPlayerTickHelper(EntityPlayer player)
	{
		//null check, every 32 ticks check
		if (player == null || (player.ticksExisted & 31) != 0) return;
		
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		if (capa == null) return;
		
        IMorphing im = Morphing.get(player);
        
        if (im != null && im.getCurrentMorph() instanceof EntityMorph)
        {
        	EntityMorph em = (EntityMorph) im.getCurrentMorph();
        	
        	//set morph flag
        	if (em != null && em.getEntity() instanceof IShipMorph)
        	{
        		/* server side */
        		if (!player.world.isRemote)
        		{
        			//player tick = 32, demorph (for reset morph on login)
        			if (player.ticksExisted == 32)
        			{
        				demorphPlayer(player);
                		return;
        			}
        		}
        		
        		/* both side */
        		//clear dupe morph entity, FIX: morph dupe entity bug
    			if (capa.morphEntity != null && !capa.morphEntity.equals(em.getEntity()))
    			{
    				capa.morphEntity.setDead();
    				capa.morphEntity = em.getEntity();
    				
    				IShipMorph ism = (IShipMorph) em.getEntity();
        			ism.setIsMorph(true);
        			ism.setMorphHost(player);
    			}
    			else if (capa.morphEntity == null)
    			{
    				capa.morphEntity = em.getEntity();
    				
    				IShipMorph ism = (IShipMorph) em.getEntity();
        			ism.setIsMorph(true);
        			ism.setMorphHost(player);
    			}
        	}
        }
        else
        {
        	capa.morphEntity = null;
        }
	}
	
	/**
	 * called in event: EventHandler::onEntityDamaged
	 */
	public static void onMorphDamagedHelper(LivingHurtEvent event)
	{
		//null check
		if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if (player.world == null || player.world.isRemote) return;
		
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		if (capa == null || !(capa.morphEntity instanceof BasicEntityShip)) return;
		
		BasicEntityShip ship = (BasicEntityShip) capa.morphEntity;
		boolean checkDEF = true;
		float atk = event.getAmount();
		
		//damage disabled
		if (event.getSource() == DamageSource.inWall || event.getSource() == DamageSource.cactus ||
			event.getSource() == DamageSource.fall)
		{
			event.setAmount(0F);
			return;
		}
		//damage ignore def value
		else if (event.getSource() == DamageSource.magic || event.getSource() == DamageSource.dragonBreath ||
				 event.getSource() == DamageSource.outOfWorld)
		{
			//set hurt face
	    	ship.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    		return;
		}
		
		//check attacker is potion
		float patk = BuffHelper.getPotionDamage(ship, event.getSource(), atk);
		
		if (patk > 0F)
		{
			ship.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
			event.setAmount((float) (patk * ConfigHandler.morphHPRatio));
			checkDEF = false;
			return;
		}
        
        //其他有attacker的傷害
		if (event.getSource().getEntity() != null)
		{
			Entity attacker = event.getSource().getEntity();
			
			//不會對自己造成傷害, 可免疫毒/掉落/窒息等傷害 (此為自己對自己造成傷害)
			if (attacker.equals(player))
			{
				event.setAmount(0F);
				return;
			}
			
			//若攻擊方為player, 則檢查friendly fire
			if (attacker instanceof EntityPlayer)
			{
				//若禁止friendlyFire, 則不造成傷害
				if (!ConfigHandler.friendlyFire)
				{
					event.setAmount(0F);
					return;
				}
			}
			
			//進行dodge計算
			float dist = (float) player.getDistanceSqToEntity(attacker);
			
			if (CombatHelper.canDodge(ship, dist))
			{
				event.setCanceled(true);
				return;
			}
			
			//進行def計算
			float reducedAtk = atk;
			
			if (checkDEF)
			{
				reducedAtk = CombatHelper.applyDamageReduceByDEF(player.getRNG(), ship.getAttrs(), reducedAtk);
			}
			
			//check morph damage taken modify
			if (attacker instanceof IShipAttackBase)
			{
				reducedAtk *= ConfigHandler.morphDmgTakenRatio;
			}
			
			//check resist potion
			reducedAtk = BuffHelper.applyBuffOnDamageByResist(ship, event.getSource(), reducedAtk);
			
			//check night vision potion
			reducedAtk = BuffHelper.applyBuffOnDamageByLight(ship, event.getSource(), reducedAtk);
			
			//tweak min damage
	        if (reducedAtk < 1F && reducedAtk > 0F) reducedAtk = 1F;
	        else if (reducedAtk <= 0F) reducedAtk = 0F;
	        
			//若傷害力可能致死, 則尋找物品中有無repair goddess來取消掉此攻擊
			if (reducedAtk >= (player.getHealth() - 1F))
			{
				if (decrSupplies(player.inventory, 8))
				{
					player.setHealth(player.getMaxHealth());
					player.hurtResistantTime = 120;
					ship.setStateTimer(ID.T.ImmuneTime, 120);
					
					//add repair goddess particle
					TargetPoint point = new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64D);
        			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(player, 13, 0D, 0.03D, 0D), point);
        			event.setAmount(0F);
					return;
				}
			}
			
	  		//set hurt face
	    	ship.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
	    	event.setAmount(reducedAtk);
    		return;
        }//end attacker != null
		
		return;
	}
	
	/**
	 * called in event: EventHandler::onMorphAction
	 * SERVER SIDE ONLY
	 */
	public static void onMorphActionHelper(MorphActionEvent event)
	{
		//server side
		if (!event.player.world.isRemote)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			
			//if enable Metamorph mod
			if (ConfigHandler.enableMetamorphSkill)
			{
				//show morph skill hotbar if sneaking
				if (event.player.isSneaking())
				{
					//show morph skill hotbar
					CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.FlagShowPlayerSkill, 0, true), (EntityPlayerMP) event.player);
					return;
				}
				//open morph gui
				else
				{
					openMorphGui(capa);
					return;
				}
			}
			else
			{
				//open morph gui
				openMorphGui(capa);
				return;
			}
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
	public static void acquireShipMorph(EntityPlayer player, Entity target)
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
			
			//change ship uid of morph ship
			NBTTagCompound shipData = (NBTTagCompound) tagData.getTag(CapaShipSavedValues.SHIP_EXTPROP_NAME);
			
			if (shipData == null)
			{
				shipData = new NBTTagCompound();
				tagData.setTag(CapaShipSavedValues.SHIP_EXTPROP_NAME, shipData);
			}
			
			NBTTagCompound shipMinor = (NBTTagCompound) shipData.getTag("Minor");
					
			if (shipMinor == null)
			{
				shipMinor = new NBTTagCompound();
				shipData.setTag("Minor", shipMinor);
			}
			
			//morph shipUID = org shipUID + playerUID * 1000
			shipMinor.setInteger("PlayerUID", EntityHelper.getPlayerUID(player));
			int sid = shipMinor.getInteger("ShipUID");
			shipMinor.setInteger("ShipUID", sid + EntityHelper.getPlayerUID(player) * 1000);
			shipMinor.setInteger("Level", 1);
			shipMinor.setInteger("Exp", 0);
			shipMinor.setInteger("Crane", 0);
			shipMinor.setInteger("Task", 0);
			shipMinor.setInteger("Kills", 0);
			shipMinor.setInteger("NumAmmoL", 0);
			shipMinor.setInteger("NumAmmoH", 0);
			shipMinor.setInteger("NumGrudge", 0);
			
			NBTTagCompound shipFlag = (NBTTagCompound) shipData.getTag("ShipFlags");
			
			if (shipFlag == null)
			{
				shipFlag = new NBTTagCompound();
				shipData.setTag("ShipFlags", shipFlag);
			}
			
			shipFlag.setBoolean("IsMorph", true);
			shipFlag.setBoolean("NoFuel", false);
			shipFlag.setBoolean("CanDrop", false);
			shipFlag.setBoolean("CanFollow", true);
			shipFlag.setBoolean("IsMarried", true);
			shipFlag.setBoolean("OnSight", true);
			shipFlag.setBoolean("TimeKeeper", false);
			shipFlag.setBoolean("AutoPump", false);
			shipFlag.setBoolean("PVPFirst", true);
			shipFlag.setBoolean("AA", true);
			shipFlag.setBoolean("ASM", true);
			shipFlag.setBoolean("PassiveAI", false);
			
			NBTTagCompound shipPoint = (NBTTagCompound) shipData.getTag("Point");
			
			if (shipPoint == null)
			{
				shipPoint = new NBTTagCompound();
				shipData.setTag("Point", shipPoint);
			}
			
			shipPoint.setByte("HP", (byte)0);
			shipPoint.setByte("ATK", (byte)0);
			shipPoint.setByte("DEF", (byte)0);
			shipPoint.setByte("SPD", (byte)0);
			shipPoint.setByte("MOV", (byte)0);
			shipPoint.setByte("HIT", (byte)0);
			
			//set target nbt data
			tag.setString("Name", name);
	        tag.setTag("EntityData", tagData);
	        
	        //player get morph
	        AbstractMorph morph = MorphManager.INSTANCE.morphFromNBT(tag);
	        MorphAPI.acquire(player, morph);
		}//end server side
	}
	
	/**
	 * process plaer skill on packet receiving, SERVER SIDE
	 * 
	 * data: 0: attack type, 1: targetEID or pos.X, 2: pos.Y, 3: pos.Z
	 */
	public static void handlePlayerSkillOfMorph(EntityPlayer player, int[] data)
	{
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		if (capa == null || !(capa.morphEntity instanceof BasicEntityShip)) return;
		BasicEntityShip ship = (BasicEntityShip) capa.morphEntity;
		int skill = 0;
		
		//check attack type
		switch (data[0])
		{
		case 0:	 //light
			if (!ship.getStateFlag(ID.F.AtkType_Light)) return;
			skill = ID.T.MountSkillCD1;
		break;
		case 1:  //heavy
			if (!ship.getStateFlag(ID.F.AtkType_Heavy)) return;
			skill = ID.T.MountSkillCD2;
		break;
		case 2:  //air light
			if (!ship.getStateFlag(ID.F.AtkType_AirLight)) return;
			skill = ID.T.MountSkillCD3;
		break;
		case 3:  //air heavy
			if (!ship.getStateFlag(ID.F.AtkType_AirHeavy)) return;
			skill = ID.T.MountSkillCD4;
		break;
		case 4:  //melee
			skill = ID.T.MountSkillCD5;
		break;
		default:
			return;
		}
		
		//check skill cd
		if (ship.getStateTimer(skill) > 0) return;
		
		//check target exist
		Entity target = null;
		BlockPos targetPos = null;
		float rangeSq = ship.getAttrs().getAttackRange() * ship.getAttrs().getAttackRange();
		
		if (data[2] < 0)
		{
			target = EntityHelper.getEntityByID(data[1], player.world.provider.getDimension(), false);
			
			if (target != null)
			{
				//melee attack
				if (data[0] == 4)
				{
					if (ship.getDistanceSqToEntity(target) > 6D) target = null;
				}
				//other attack
				else
				{
					if (ship.getDistanceSqToEntity(target) > rangeSq) target = null;
				}
			}
		}
		else
		{
			targetPos = new BlockPos(data[1], data[2], data[3]);
			
			if (ship.getDistanceSqToCenter(targetPos) > rangeSq)
			{
				targetPos = null;
			}
		}
		
		//no target, return
		if (target == null && targetPos == null) return;
		
		//check target isn't friendly
		if (target != null && TeamHelper.checkSameOwner(ship, target)) return;
		
		//check ammo and attack target
		switch (data[0])
		{
		case 0:  //light attack
			if (target != null)
			{
				ship.attackEntityWithAmmo(target);
				ship.setStateTimer(ID.T.MountSkillCD1, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 1));
				ship.sendSyncPacketTimer(1);
			}
		break;
		case 1:   //heavy attack
			if (target != null) ship.attackEntityWithHeavyAmmo(target);
			else if (targetPos != null) ship.attackEntityWithHeavyAmmo(targetPos);
			
			if (target != null || targetPos != null)
			{
				ship.setStateTimer(ID.T.MountSkillCD2, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 2));
				ship.sendSyncPacketTimer(1);
			}
		break;
		case 2:   //light air attack
			if (ship instanceof BasicEntityShipCV && target != null)
			{
				((BasicEntityShipCV) ship).attackEntityWithAircraft(target);
				ship.setStateTimer(ID.T.MountSkillCD3, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 3));
				ship.setStateTimer(ID.T.MountSkillCD4, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 3));
				ship.sendSyncPacketTimer(1);
			}
		break;
		case 3:   //heavy air attack
			if (ship instanceof BasicEntityShipCV && target != null)
			{
				((BasicEntityShipCV) ship).attackEntityWithHeavyAircraft(target);
				ship.setStateTimer(ID.T.MountSkillCD3, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 4));
				ship.setStateTimer(ID.T.MountSkillCD4, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 4));
				ship.sendSyncPacketTimer(1);
			}
		break;
		case 4:  //melee
			if (target != null)
			{
				ship.attackEntityAsMob(target);
				ship.setStateTimer(ID.T.MountSkillCD5, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 0));
				ship.sendSyncPacketTimer(1);
			}
		break;
		}
	}
	
	/** decr ammo in morph ship, type: 0:light, 1:heavy */
	public static boolean decrAmmoNum(BasicEntityShip morph, Enums.Ammo type, int amount)
	{
		int ammoType = type == 1 ? ID.M.NumAmmoHeavy : ID.M.NumAmmoLight;
		int remain = morph.getStateMinor(ammoType) - amount;
		
		//ammo不足
		if (remain < 0) return false;
		//ammo足夠
		else
		{
			morph.setStateMinor(ammoType, remain);
			return true;
		}
	}
	
	/** decr grudge in morph ship */
	public static void decrGrudgeNum(BasicEntityShip morph, int value)
	{
		//get grudge magnification
		float modGrudge = morph.getAttrs().getAttrsBuffed(ID.Attrs.GRUDGE);
		
		//if grudge--, check buff: hunger
		if (value > 0)
		{
			int level = BuffHelper.getPotionLevel(morph, 17);
			value *= 1 + level;
		}
		//if grudge++, check buff: grudge mod
		else if (value < 0)
		{
			value *= modGrudge;
		}
		
		//decr grudge
		morph.addGrudge(-value);
	}
	
	/** handle gui button input, SERVER SIDE */
	public static void handleGUIPacketInput(CapaTeitoku capa, int button, int value)
	{
		//null check
		if (capa == null || capa.morphEntity == null || capa.player == null) return;
		
		//entity type check
		BasicEntityShip ship = null;
		
		if (capa.morphEntity instanceof BasicEntityShip)
		{
			ship = (BasicEntityShip) capa.morphEntity;
		}
		
		//handle buttons
		switch (button)
		{
		case ID.B.MorphInv_AddAmmoL:
			if (ship != null && ship.getStateMinor(ID.M.NumAmmoLight) < 30000)
			{
				consumeSupplyItems(capa.player.inventory, ship, 1);
			}
		break;
		case ID.B.MorphInv_AddAmmoH:
			if (ship != null && ship.getStateMinor(ID.M.NumAmmoHeavy) < 30000)
			{
				consumeSupplyItems(capa.player.inventory, ship, 2);
			}
		break;
		case ID.B.MorphInv_AddGrudge:
			if (ship != null && ship.getStateMinor(ID.M.NumGrudge) < 30000)
			{
				consumeSupplyItems(capa.player.inventory, ship, 0);
			}
		break;
		}
	}
	
	/** consume items in player's inventory, type: 0:grudge, 1:light ammo, 2:heavy ammo */
	private static void consumeSupplyItems(IInventory inv, BasicEntityShip ship, int type)
	{
		int itemType = -1;  //0:grudge, 1:ammo light, 2:ammo heavy
		float addValue = 0F;
		
		//consume item
		switch (type)
		{
		case 0:  //grudge
			if (decrSupplies(inv, 4))
			{
				itemType = 0;
				addValue = Values.N.BaseGrudge;
			}
			else
			{
				if (decrSupplies(inv, 5))
				{
					itemType = 0;
					addValue = Values.N.BaseGrudge * 9F;
				}
			}
		break;
		case 1:  //ammo light
			if (decrSupplies(inv, 0))
			{
				itemType = 1;
				addValue = Values.N.BaseLightAmmo;
			}
			else
			{
				if (decrSupplies(inv, 2))
				{
					itemType = 1;
					addValue = Values.N.BaseLightAmmo * 9F;
				}
			}
		break;
		case 2:  //ammo heavy
			if (decrSupplies(inv, 1))
			{
				itemType = 2;
				addValue = Values.N.BaseHeavyAmmo;
			}
			else
			{
				if (decrSupplies(inv, 3))
				{
					itemType = 2;
					addValue = Values.N.BaseHeavyAmmo * 9F;
				}
			}
		break;
		}//end switch
		
		//check easy mode
		if (ConfigHandler.easyMode) addValue *= 10F;
		
		//add value
		switch (itemType)
		{
		case 0:  //add grudge
			ship.addGrudge((int) (addValue * ship.getAttrs().getAttrsBuffed(ID.Attrs.GRUDGE)));
		break;
		case 1:  //add ammo light
			ship.addAmmoLight((int) (addValue * ship.getAttrs().getAttrsBuffed(ID.Attrs.AMMO)));
		break;
		case 2:  //add ammo heavy
			ship.addAmmoHeavy((int) (addValue * ship.getAttrs().getAttrsBuffed(ID.Attrs.AMMO)));
		break;
		}
	}
	
	/** consume items in player's inventory */
	private static boolean decrSupplies(IInventory inv, int type)
	{
		int itemNum = 1;
		boolean noMeta = false;
		ItemStack itemType = null;
		
		//find ammo
		switch (type)
		{
		case 0:	//use 1 light ammo
			itemType = new ItemStack(ModItems.Ammo,1,0);
		break;
		case 1: //use 1 heavy ammo
			itemType = new ItemStack(ModItems.Ammo,1,2);
		break;
		case 2:	//use 1 light ammo container
			itemType = new ItemStack(ModItems.Ammo,1,1);
		break;
		case 3: //use 1 heavy ammo container
			itemType = new ItemStack(ModItems.Ammo,1,3);
		break;
		case 4: //use 1 grudge
			itemType = new ItemStack(ModItems.Grudge,1);
		break;
		case 5: //use 1 grudge block
			itemType = new ItemStack(ModBlocks.BlockGrudge,1);
		break;
		case 6: //use 1 grudge block
			itemType = new ItemStack(ModBlocks.BlockGrudgeHeavy,1);
		break;
		case 7:	//use 1 repair bucket
			itemType = new ItemStack(ModItems.BucketRepair,1);
		break;
		case 8:	//use 1 repair goddess
			itemType = new ItemStack(ModItems.RepairGoddess,1);
		break;
		}
		
		//search item in ship inventory
		ItemStack itemGet = InventoryHelper.getAndRemoveItem(inv, itemType, 1, true, false, false, null);
		
		//item not found
		if (itemGet == null) return false;
		else return true;
	}
	
	/**
	 * process data save when logout or demorph
	 */
	public static void writeToNBT(EntityPlayer player)
	{
		//null check
		if (player == null) return;
		IMorphing im = Morphing.get(player);
		
		if (im != null)
        {
			AbstractMorph am = im.getCurrentMorph();
			BasicEntityShip morph = null;
			
			//check ship is current or prev morph
			if (am instanceof EntityMorph && ((EntityMorph)am).getEntity() instanceof BasicEntityShip)
			{
				LogHelper.info("INFO: save morph ship data from current morph.");
				morph = (BasicEntityShip) ((EntityMorph)am).getEntity();
			}
			
			//save ship data to morph handler
        	if (morph != null)
        	{
        		NBTTagCompound tagData = ((EntityMorph)am).getEntityData();
    	        if (tagData == null)
    	        {
    	        	tagData = morph.serializeNBT();
    	        	((EntityMorph)am).setEntityData(tagData);
    	        }
    	        
    			NBTTagCompound shipData = (NBTTagCompound) tagData.getTag(CapaShipSavedValues.SHIP_EXTPROP_NAME);
    			if (shipData == null)
    			{
    				shipData = new NBTTagCompound();
    				tagData.setTag(CapaShipSavedValues.SHIP_EXTPROP_NAME, shipData);
    			}
    			
    			//save minor value
    			NBTTagCompound shipMinor = (NBTTagCompound) shipData.getTag("Minor");
    			if (shipMinor == null)
    			{
    				shipMinor = new NBTTagCompound();
    				shipData.setTag("Minor", shipMinor);
    			}
    			LogHelper.info("INFO: save morph ship data: LV "+morph.getLevel()+" EXP "+morph.getStateMinor(ID.M.ExpCurrent));
    			shipMinor.setInteger("Level", morph.getLevel());
    			shipMinor.setInteger("Exp", morph.getStateMinor(ID.M.ExpCurrent));
    			shipMinor.setInteger("Kills", morph.getStateMinor(ID.M.Kills));
    			shipMinor.setInteger("NumAmmoL", morph.getStateMinor(ID.M.NumAmmoLight));
    			shipMinor.setInteger("NumAmmoH", morph.getStateMinor(ID.M.NumAmmoHeavy));
    			shipMinor.setInteger("NumGrudge", morph.getStateMinor(ID.M.NumGrudge));
    			
    			//save modern level
    			NBTTagCompound shipPoint = (NBTTagCompound) shipData.getTag("Point");
    			if (shipPoint == null)
    			{
    				shipPoint = new NBTTagCompound();
    				shipData.setTag("Point", shipPoint);
    			}
    			
    			shipPoint.setByte("HP", morph.getAttrs().getAttrsBonus(ID.AttrsBase.HP));
    			shipPoint.setByte("ATK", morph.getAttrs().getAttrsBonus(ID.AttrsBase.ATK));
    			shipPoint.setByte("DEF", morph.getAttrs().getAttrsBonus(ID.AttrsBase.DEF));
    			shipPoint.setByte("SPD", morph.getAttrs().getAttrsBonus(ID.AttrsBase.SPD));
    			shipPoint.setByte("MOV", morph.getAttrs().getAttrsBonus(ID.AttrsBase.MOV));
    			shipPoint.setByte("HIT", morph.getAttrs().getAttrsBonus(ID.AttrsBase.HIT));
    			
    			NBTTagCompound shipFlag = (NBTTagCompound) shipData.getTag("ShipFlags");
    			if (shipFlag == null)
    			{
    				shipFlag = new NBTTagCompound();
    				shipData.setTag("ShipFlags", shipFlag);
    			}
    			
    			shipFlag.setBoolean("NoFuel", morph.getStateFlag(ID.F.NoFuel));
    			shipFlag.setBoolean("WedEffect", morph.getStateFlag(ID.F.UseRingEffect));
        	}
        }
	}
	
	
}