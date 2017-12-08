package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.config.ConfigMining;
import com.lulan.shincolle.config.ConfigMining.ItemEntry;
import com.lulan.shincolle.crafting.InventoryCraftingFake;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.other.EntityShipFishingHook;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicItem;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.util.Constants;

/**
 * helper for cooking, mining, fishing... etc.
 */
public class TaskHelper
{
	
	
	public TaskHelper() {}
	
	/**
	 * update task, called every 8 ticks
	 * 
	 * StateMinor[ID.M.Task]:
	 *   task ID
	 * StateMinor[ID.M.TaskSide]:
	 *   0~5 bit for input side
	 *   6~11 bit for output side
	 *   12~17 bit for fuel side
	 *   18~20 bit for metadata, ore dict, nbt tag check
	 */
	public static void onUpdateTask(BasicEntityShip host)
	{
		int taskid = host.getStateMinor(ID.M.Task);
		
		switch (taskid)
		{
		case 1:  //cooking
			if (ConfigHandler.enableTask[0]) onUpdateCooking(host);
		break;
		case 2:  //fishing
			if (ConfigHandler.enableTask[1]) onUpdateFishing(host);
		break;
		case 3:  //mining
			if (ConfigHandler.enableTask[2]) onUpdateMining(host);
		break;
		case 4:  //crafting
			if (ConfigHandler.enableTask[3]) onUpdateCrafting(host);
		break;
		}
	}
	
	/**
	 * crafting task:
	 * craft itemstack in mainhand (slot 22)
	 */
	public static void onUpdateCrafting(BasicEntityShip host)
	{
		//null check
		if (host == null) return;
		
		//check held item is recipe paper
		CapaShipInventory invShip = host.getCapaShipInventory();
		ItemStack paper = host.getHeldItemMainhand();
		if (paper == null || paper.getItem() != ModItems.RecipePaper) return;
		
		//check slot 12~20 is empty (for materials)
		for (int i = 12; i <= 20; i++)
		{
			if (invShip.getStackInSlot(i) != null)
			{
				if (host.getRNG().nextInt(9) == 0)
				{
					//apply emote
					switch (host.getRNG().nextInt(4))
					{
					case 1:
						host.applyParticleEmotion(22);  //X
					break;
					case 2:
						host.applyParticleEmotion(5);   //...
					break;
					case 3:
						host.applyParticleEmotion(28);  //一口一
					break;
					default:
						host.applyParticleEmotion(32);  //尷尬
					break;
					}
				}
				
				return;
			}
		}
		
		//check guard position
		BlockPos pos = new BlockPos(host.getGuardedPos(0), host.getGuardedPos(1), host.getGuardedPos(2));
		if (pos == null || pos.getY() <= 0) return;
		
		//check guard type
		if (host.getGuardedPos(4) != 1) return;
		
		//check dimension
		if (host.world.provider.getDimension() != host.getGuardedPos(3)) return;
		
		//check guard position is waypoint
		TileEntity te = host.world.getTileEntity(pos);
		if (!(te instanceof TileEntityWaypoint)) return;
		
		//check wapoint has paired chest
		pos = ((TileEntityWaypoint) te).getPairedChest();
		if (pos == null || pos.getY() <= 0) return;
		
		//check paired chest is IInventory
		te = host.world.getTileEntity(pos);
		if (!(te instanceof IInventory)) return;
		IInventory chest = (IInventory) te;
		
		//check distance
		if (host.getDistanceSq(pos) > 25D)
		{
			//too far away, move to guard (waypoint) position
			host.getShipNavigate().tryMoveToXYZ(host.getGuardedPos(0), host.getGuardedPos(1), host.getGuardedPos(2), 1D);
			return;
		}
		
		//check recipe is valid
		InventoryCraftingFake recipe = new InventoryCraftingFake(3, 3);
		ItemStack result = null;
		
		if (paper.hasTagCompound())
        {
			//get recipe
        	NBTTagCompound nbt = paper.getTagCompound();
        	NBTTagList tagList = nbt.getTagList("Recipe", Constants.NBT.TAG_COMPOUND);
        	
            for (int i = 0; i < 9; i++)
            {
                NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
                int slot = itemTags.getInteger("Slot");

                if (slot >= 0 && slot < 9)
                {
                	recipe.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTags));
                }
            }
            
            //get result
            result = CraftingManager.getInstance().findMatchingRecipe(recipe, host.world);
            
            //check result exist
            if (result == null) return;
        }
		else
		{
			return;
		}
		
		/** start crafting */
		int maxCraft = host.getLevel() / 20 + 1;	//max item per 8 ticks
		ItemStack tempStack = null;
		int taskSide = host.getStateMinor(ID.M.TaskSide);
		boolean checkMetadata = (taskSide & Values.N.Pow2[18]) == Values.N.Pow2[18];
		boolean checkOredict = (taskSide & Values.N.Pow2[19]) == Values.N.Pow2[19];
		boolean checkNbt = (taskSide & Values.N.Pow2[20]) == Values.N.Pow2[20];
		InventoryCraftingFake recipeTemp = new InventoryCraftingFake(3, 3);
		ItemStack resultTemp = null;
		boolean canAddExp = false;
		int maxtimes = maxCraft;  //備用while跳出判定, 避免情況太複雜而無法達到跳出條件
		
		while (maxCraft > 0)
		{
			maxtimes--;
			if (maxtimes <= 0) break;
			
			//check slot 12~20 is empty (for materials)
			for (int i = 12; i <= 20; i++)
			{
				if (invShip.getStackInSlot(i) != null)
				{
					if (host.getRNG().nextInt(8) == 0)
					{
						//apply emote
						switch (host.getRNG().nextInt(4))
						{
						case 1:
							host.applyParticleEmotion(22);  //X
						break;
						case 2:
							host.applyParticleEmotion(5);   //...
						break;
						case 3:
							host.applyParticleEmotion(28);  //一口一
						break;
						default:
							host.applyParticleEmotion(32);  //尷尬
						break;
						}
					}
					
					maxCraft = 0;
				}
			}
			
			if (maxCraft <= 0) break;
			
			//move materials from chest to ship's inventory slot 12~20
			for (int i = 0; i < 9; i++)
			{
				tempStack = recipe.getStackInSlot(i);
				
				if (tempStack == null)
				{
					recipeTemp.setInventorySlotContents(i, null);
					continue;
				}
				
				invShip.setInventorySlotContents(i + 12, InventoryHelper.getAndRemoveItem(chest, tempStack, 1, checkMetadata, checkNbt, checkOredict, null));
				recipeTemp.setInventorySlotContents(i, invShip.getStackInSlot(i + 12));
			}
			
			//check recipeTemp valid
            resultTemp = CraftingManager.getInstance().findMatchingRecipe(recipeTemp, host.world);

            //get crafting result
            if (resultTemp != null)
            {
            	//check result is same with recipe result
            	if (!InventoryHelper.matchTargetItem(resultTemp, result, checkMetadata, checkNbt, checkOredict))
            	{
            		//not same, return
            		break;
            	}
            	
            	//crafting number -1
            	maxCraft--;
            	canAddExp = true;
            	
            	//move result to chest or drop on ground
            	if (!InventoryHelper.moveItemstackToInv(chest, resultTemp, null) || resultTemp.stackSize > 0)
            	{
            		//put stack on ground
            		EntityItem entityitem = new EntityItem(host.world, host.posX, host.posY, host.posZ, resultTemp);
            		entityitem.motionX = host.getRNG().nextGaussian() * 0.08D;
    	            entityitem.motionY = host.getRNG().nextGaussian() * 0.05D + 0.2D;
    	            entityitem.motionZ = host.getRNG().nextGaussian() * 0.08D;
    	            host.world.spawnEntity(entityitem);
            	}
            	
            	//clear material slot in ship inventory
            	for (int i = 0; i < 9; i++)
            	{
            		invShip.setInventorySlotContents(i + 12, null);
            	}
            	
            	//move remaining item to chest (bucket, bottle...)
            	ItemStack[] remainStacks = CraftingManager.getInstance().getRemainingItems(recipeTemp, host.world);
            	
                for (int i = 0; i < remainStacks.length; ++i)
                {
                    if (remainStacks[i] != null)
                    {
                    	//move to chest
                    	if (!InventoryHelper.moveItemstackToInv(chest, remainStacks[i], null) && remainStacks[i].stackSize > 0)
                    	{
                    		//move failed, drop on ground
                    		EntityItem entityitem = new EntityItem(host.world, host.posX, host.posY, host.posZ, remainStacks[i]);
                    		entityitem.motionX = host.getRNG().nextGaussian() * 0.08D;
            	            entityitem.motionY = host.getRNG().nextGaussian() * 0.05D + 0.2D;
            	            entityitem.motionZ = host.getRNG().nextGaussian() * 0.08D;
                            host.world.spawnEntity(entityitem);
                    	}
                    }
                }//end move remaining item
            }//end move result item
            //no result item, return
            else
            {
            	break;
            }
		}//end while crafting
		
		//add exp and consume grudge
		if (canAddExp)
		{
			//add exp and consume grudge
			host.addShipExp(ConfigHandler.expGainTask[3]);
			host.decrGrudgeNum(ConfigHandler.consumeGrudgeTask[3]);
			host.addMorale(-10);
			
			//swing arm
			host.swingArm(EnumHand.MAIN_HAND);
			
			//apply emotion
			if (host.getRNG().nextInt(5) == 0)
			{
				switch (host.getRNG().nextInt(5))
				{
				case 1:
					host.applyParticleEmotion(2);  //噴汗
				break;
				case 2:
					host.applyParticleEmotion(7);  //note
				break;
				case 3:
					host.applyParticleEmotion(13);  //點頭
				break;
				case 4:
					host.applyParticleEmotion(30);  //pif
				break;
				default:
					host.applyParticleEmotion(21);  //O
				break;
				}
			}
		}//end add exp
	}
	
	/**
	 * mining task:
	 * put pickaxe in mainhand (slot 22)
	 * generate ores per X ticks
	 * pos.Y and ship level determine mining result (configurable)
	 */
	public static void onUpdateMining(BasicEntityShip host)
	{
		//null check
		if (host == null) return;
		
		//check held item is pickaxe
		ItemStack pickaxe = host.getHeldItemMainhand();
		if (pickaxe == null || !isToolEffective(pickaxe, 0, 0)) return;
		
		//check not in moving
		if (MathHelper.abs((float) host.motionX) > 0.1F ||
			MathHelper.abs((float) host.motionZ) > 0.1F ||
			host.motionY > 0.1F)
		{
			return;
		}
		else
		{
			//random move
			if ((host.getTickExisted() & 63) == 0)
			{
				host.getShipNavigate().tryMoveToXYZ(host.posX + host.getRNG().nextInt(9) - 4,
						host.posY + host.getRNG().nextInt(5) - 2,
						host.posZ + host.getRNG().nextInt(9) - 4, 1D);
				return;
			}
		}
		
		//swing arm and emotes
		if (host.getRNG().nextInt(5) > 2)
		{
			//swing arm
			host.swingArm(EnumHand.MAIN_HAND);
			
			if (host.getRNG().nextInt(10) > 8)
			{
				//apply emote
				switch (host.getRNG().nextInt(5))
				{
				case 2:
					host.applyParticleEmotion(11);  //find
				break;
				case 3:
					host.applyParticleEmotion(5);   //...
				break;
				case 4:
					host.applyParticleEmotion(30);  //pif
				break;
				default:
					host.applyParticleEmotion(0);   //sweat
				break;
				}
			}
		}
		
		//finish mining
		if ((host.ticksExisted & 31) == 0 && host.ticksExisted - host.getStateTimer(ID.T.TaskTime) > ConfigHandler.tickMining[0] + host.getRNG().nextInt(ConfigHandler.tickMining[1]))
		{
			int stone = 0;
			boolean canMine = false;
			BlockPos pos = null;
			IBlockState state = null;
			
			//check nearby solid block > N
			for (int dy = -3; dy < 5; dy++)
			{
				for (int dx = -3; dx < 4; dx++)
				{
					for (int dz = -3; dz < 4; dz++)
					{
						pos = new BlockPos(host.posX + dx, host.posY + dy, host.posZ + dz);
						state = host.world.getBlockState(pos);
						
						if (state.getMaterial() == Material.ROCK) stone++;
						if (stone > 120) canMine = true;
					}
					
					if (canMine) break;
				}
				
				if (canMine) break;
			}//end for all blocks around
			
			//check can mine
			if (!canMine) return;
			
			//generate mining result
			generateMiningResult(host);
			
			//add exp and consume grudge
			host.addShipExp(ConfigHandler.expGainTask[2]);
			host.decrGrudgeNum(ConfigHandler.consumeGrudgeTask[2]);
			host.addMorale(-200);
			
			//apply emote
			switch (host.getRNG().nextInt(5))
			{
			case 1:
				host.applyParticleEmotion(11);  //find
			break;
			case 2:
				host.applyParticleEmotion(14);  //+_+
			break;
			case 3:
				host.applyParticleEmotion(4);   //!
			break;
			case 4:
				host.applyParticleEmotion(30);  //pif
			break;
			default:
				host.applyParticleEmotion(0);   //sweat
			break;
			}
			
			//swing arm
			host.swingArm(EnumHand.MAIN_HAND);
			
			//set timer
			host.setStateTimer(ID.T.TaskTime, host.ticksExisted);
		}
	}
	
	/**
	 * fishing task:
	 * put fishing rod in mainhand (slot 22)
	 * detect water block with depth >= 3 blocks
	 */
	public static void onUpdateFishing(BasicEntityShip host)
	{
		//null check
		if (host == null) return;
		
		//check held item is fishing rod
		ItemStack rod = host.getHeldItemMainhand();
		if (rod == null || rod.getItem() != Items.FISHING_ROD) return;
		
		//check guard position
		BlockPos pos = new BlockPos(host.getGuardedPos(0), host.getGuardedPos(1), host.getGuardedPos(2));
		if (pos == null || pos.getY() <= 0) return;
		
		//check guard type
		if (host.getGuardedPos(4) != 1) return;
		
		//check dimension
		if (host.world.provider.getDimension() != host.getGuardedPos(3)) return;
		
		//move to guard point
		if (host.getDistanceSq(pos) > 10D)
		{
			host.getShipNavigate().tryMoveToXYZ(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 1D);
			return;
		}
		
		//not in moving
		if (MathHelper.abs((float) host.motionX) > 0.1F ||
			MathHelper.abs((float) host.motionZ) > 0.1F ||
			host.motionY > 0.1F) return;
		
		//check water block
		pos = BlockHelper.getNearbyLiquid(host, false, true, 5, 3);
		if (pos == null) return;
		
		//get pool, if no hook -> use fishing rod
		if (host.fishHook == null)
		{
			//swing arm
			host.swingArm(EnumHand.MAIN_HAND);
			
			//put fishing hook
			EntityShipFishingHook hook = new EntityShipFishingHook(host.world, host);
			hook.setPosition(pos.getX() + 0.1D + host.getRNG().nextDouble() * 0.8D,
							 pos.getY() + 1D,
							 pos.getZ() + 0.1D + host.getRNG().nextDouble() * 0.8D);
            host.world.spawnEntity(hook);
            host.fishHook = hook;
            
            //apply emote
            switch (host.getRNG().nextInt(4))
			{
			case 1:
				host.applyParticleEmotion(14);  //+_+
			break;
			case 2:
				host.applyParticleEmotion(7);  //note
			break;
			case 3:
				host.applyParticleEmotion(11);  //find
			break;
			default:
				host.applyParticleEmotion(30);  //pif
			break;
			}
            
            return;
		}
		//get hook, wait random time
		else
		{
			if (!host.fishHook.isDead && host.fishHook.ticksExisted > ConfigHandler.tickFishing[0] + host.getRNG().nextInt(ConfigHandler.tickFishing[1]))
			{
				//generate itemstack
				generateFishingResult(host);
				
				//clear fishing hook
				host.fishHook.setDead();
				
				//add exp and consume grudge
				host.addShipExp(ConfigHandler.expGainTask[1]);
				host.decrGrudgeNum(ConfigHandler.consumeGrudgeTask[1]);
				host.addMorale(300);
				
				//apply emote
				switch (host.getRNG().nextInt(5))
				{
				case 1:
					host.applyParticleEmotion(1);  //heart
				break;
				case 2:
					host.applyParticleEmotion(7);  //note
				break;
				case 3:
					host.applyParticleEmotion(16);  //haha
				break;
				case 4:
					host.applyParticleEmotion(30);  //pif
				break;
				default:
					host.applyParticleEmotion(0);  //sweat
				break;
				}
				
				//swing arm
				host.swingArm(EnumHand.MAIN_HAND);
			}//end hook time out
			
			//fishing time out
			if (host.fishHook != null && host.fishHook.ticksExisted > ConfigHandler.tickFishing[0] + ConfigHandler.tickFishing[1])
			{
				//clear fishing hook
				host.fishHook.setDead();
			}//end fishing time out
		}//end get hook
	}
	
	/**
	 * cooking task:
	 * smelt itemstack in mainhand (slot 22) (option: put fuel in offhand (slot 23))
	 */
	public static void onUpdateCooking(BasicEntityShip host)
	{
		//null check
		if (host == null) return;
		
		//check held item
		ItemStack mainstack = host.getHeldItemMainhand();
		ItemStack offstack = host.getHeldItemOffhand();
		if (mainstack == null) return;
		
		//check guard position
		BlockPos pos = new BlockPos(host.getGuardedPos(0), host.getGuardedPos(1), host.getGuardedPos(2));
		if (pos == null || pos.getY() <= 0) return;
		
		//check guard type
		if (host.getGuardedPos(4) != 1) return;
		
		//check dimension
		if (host.world.provider.getDimension() != host.getGuardedPos(3)) return;
		
		//check guard position is waypoint
		TileEntity te = host.world.getTileEntity(pos);
		if (!(te instanceof TileEntityWaypoint)) return;
		
		//check wapoint has paired chest
		pos = ((TileEntityWaypoint) te).getPairedChest();
		if (pos == null || pos.getY() <= 0) return;
		
		//check paired chest is ISidedInventory (NOT for IInventory!!)
		te = host.world.getTileEntity(pos);
		if (!(te instanceof ISidedInventory)) return;
		
		/** start cooking */
		//get furnace tile
		ISidedInventory furnace = (ISidedInventory) te;
		
		//check distance
		if (host.getDistanceSq(pos) > 25D)
		{
			//too far away, move to guard (waypoint) position
			host.getShipNavigate().tryMoveToXYZ(host.getGuardedPos(0), host.getGuardedPos(1), host.getGuardedPos(2), 1D);
			return;
		}
		
		//check smelt recipe
        ItemStack resultStack = FurnaceRecipes.instance().getSmeltingResult(mainstack);
        if (resultStack == null) return;
		
		if (!canItemStackSmelt(mainstack)) return;
		
		ItemStack targetStack = null;
		ItemStack fuelStack = null;
		ItemStack ouputStack = null;
		CapaShipInventory inv = host.getCapaShipInventory();
		int taskSide = host.getStateMinor(ID.M.TaskSide);
		boolean checkMetadata = (taskSide & Values.N.Pow2[18]) == Values.N.Pow2[18];
		boolean checkOredict = (taskSide & Values.N.Pow2[19]) == Values.N.Pow2[19];
		boolean checkNbt = (taskSide & Values.N.Pow2[20]) == Values.N.Pow2[20];
		int[] exceptSlots = new int[] {22, 23};  //dont check main, offhand slot
		
		//check stacks in inventory except main/offhand slot
		int targetID = InventoryHelper.matchTargetItemExceptSlots(inv, mainstack, checkMetadata, checkNbt, checkOredict, exceptSlots);
		
		//get target stack
		if (targetID >= 0) targetStack = inv.getStackInSlot(targetID);
		
		//get fuel stack
		int fuelID = -1;
		
		if (offstack != null)
		{
			fuelID = InventoryHelper.matchTargetItemExceptSlots(inv, offstack, checkMetadata, checkNbt, checkOredict, exceptSlots);
			if (fuelID >= 0) fuelStack = inv.getStackInSlot(fuelID);
		}
		
		//get target item, put it into furnace
		//get slots
		int[] inSlots = InventoryHelper.getSlotsFromSide(furnace, targetStack, taskSide, 0);
		int[] outSlots = InventoryHelper.getSlotsFromSide(furnace, null, taskSide, 1);
		int[] fuSlots = InventoryHelper.getSlotsFromSide(furnace, fuelStack, taskSide, 2);
		
		boolean moved = false;
		boolean swing = false;
		
		//put target stack into slots
		if (inSlots.length > 0)
		{
			moved = InventoryHelper.moveItemstackToInv(furnace, targetStack, inSlots);
			swing = swing || moved;
			
			//if moved, check stacksize
			if (moved && targetStack.stackSize <= 0)
			{
				inv.setInventorySlotWithPageCheck(targetID, null);
			}
		}//end put target stack
		
		//put fuel stack into slots
		if (fuSlots.length > 0 && fuelStack != null)
		{
			moved = InventoryHelper.moveItemstackToInv(furnace, fuelStack, fuSlots);
			swing = swing || moved;
			
			//if moved, check stacksize
			if (moved && fuelStack.stackSize <= 0)
			{
				inv.setInventorySlotWithPageCheck(fuelID, null);
			}
		}//end put fuel stack
		
		//take item from output slots
		if (outSlots.length > 0)
		{
			int outID = -1;
			
			//take 1 stack at a time
			for (int id : outSlots)
			{
				ouputStack = furnace.getStackInSlot(id);
				
				//dont take out input and fuel item
				if (ouputStack != null && InventoryHelper.matchTargetItem(ouputStack, resultStack, checkMetadata, checkNbt, checkOredict))
				{
					outID = id;
					break;
				}
				
				ouputStack = null;
			}
			
			//get output item
			if (ouputStack != null)
			{
				moved = InventoryHelper.moveItemstackToInv(inv, ouputStack, null);
				swing = swing || moved;
				
				//if moved, check stacksize
				if (moved)
				{
					//remove itemstack
					if (ouputStack.stackSize <= 0)
					{
						furnace.setInventorySlotContents(outID, null);
					}
					
					//add exp and consume grudge
					host.addShipExp(ConfigHandler.expGainTask[0]);
					host.decrGrudgeNum(ConfigHandler.consumeGrudgeTask[0]);
					host.addMorale(100);
					
					//generate coal by level and apply emotion
					float failChance = (float)(ConfigHandler.maxLevel - host.getLevel()) / (float)ConfigHandler.maxLevel * 0.2F + 0.05F;
					
					if (host.getRNG().nextFloat() < failChance)
					{
						ItemStack coal = new ItemStack(Items.COAL, 1, 1);  //charcoal
			            EntityItem entityitem = new EntityItem(host.world, pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D, coal);

			            entityitem.motionX = host.getRNG().nextGaussian() * 0.05D;
			            entityitem.motionY = host.getRNG().nextGaussian() * 0.05D + 0.2D;
			            entityitem.motionZ = host.getRNG().nextGaussian() * 0.05D;
			            host.world.spawnEntity(entityitem);
			            host.applyEmotesReaction(6);  //shock!!
					}
					else
					{
						if (host.getRNG().nextInt(7) == 0)
						{
							switch (host.getRNG().nextInt(5))
							{
							case 1:
								host.applyParticleEmotion(1);  //heart
							break;
							case 2:
								host.applyParticleEmotion(7);  //note
							break;
							case 3:
								host.applyParticleEmotion(16);  //haha
							break;
							case 4:
								host.applyParticleEmotion(30);  //pif
							break;
							default:
								host.applyParticleEmotion(0);  //sweat
							break;
							}
						}
					}//end emotion
				}
			}
		}//end take out item
		
		//apply hand move on ship
		if (swing)
		{
			//swing arm
			host.swingArm(EnumHand.MAIN_HAND);
		}
	}
	
	/** check itemstack has smelt recipe */
	public static boolean canItemStackSmelt(ItemStack stack)
    {
		//null check
        if (stack == null) return false;
        else
        {
        	//check smelt recipe
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(stack);
            if (itemstack == null) return false;
            return true;
        }
    }
	
	/** get loot list by world id, biome id, ship level, height and tool level */
	public static List<ItemEntry> getMiningLootList(int worldid, int biomeid, int lvShip, int lvHeight, int lvTool)
	{
		//get loot map
		HashMap<Integer, ArrayList<ItemEntry>> map1 = ConfigMining.MININGMAP.get(worldid);
		HashMap<Integer, ArrayList<ItemEntry>> map2 = ConfigMining.MININGMAP.get(ConfigMining.GeneralWorldID);
		ArrayList<ItemEntry> tempList = new ArrayList<ItemEntry>();
		ArrayList<ItemEntry> resultList = new ArrayList<ItemEntry>();
		
		//get loot list
		if (map1 != null)
		{
			ArrayList<ItemEntry> list1 = map1.get(biomeid);
			ArrayList<ItemEntry> list2 = map1.get(ConfigMining.GeneralBiomeID);
			
			if (list1 != null) tempList.addAll(list1);
			if (list2 != null) tempList.addAll(list2);
		}
		
		if (map2 != null)
		{
			ArrayList<ItemEntry> list1 = map2.get(biomeid);
			ArrayList<ItemEntry> list2 = map2.get(ConfigMining.GeneralBiomeID);
			
			if (list1 != null) tempList.addAll(list1);
			if (list2 != null) tempList.addAll(list2);
		}
		
		//check ship level, height and tool level
		for (ItemEntry item : tempList)
		{
			if (lvShip >= item.lvShip && lvHeight <= item.lvHeight && lvTool >= item.lvTool)
			{
				resultList.add(item);
			}
		}
		
		return resultList;
	}
	
	/** generate mining result, put itemstacks into inventory or on ground */
	public static void generateMiningResult(EntityLivingBase host)
	{
		//check host type
		if (host instanceof BasicEntityShip)
		{
			BasicEntityShip ship = (BasicEntityShip) host;
			ItemStack pickaxe = ship.getHeldItemMainhand();
			if (pickaxe == null) return;
			
			//get mining loot map
			List<ItemEntry> list1 = getMiningLootList(ship.world.provider.getDimension(),
					Biome.getIdForBiome(ship.world.getBiome(ship.getPosition())), ship.getLevel(),
					(int)ship.posY, pickaxe.getItem().getHarvestLevel(pickaxe, "pickaxe", null, null));

			if (list1 == null || list1.size() <= 0) return;

			//create cumulative value list
			List<Integer> list2 = new ArrayList<Integer>();
			list2.add(list1.get(0).weight);
			
			for (int i = 1; i < list1.size(); i++)
			{
//				LogHelper.debug("DDDDD "+i+" "+list2.get(i - 1)+" "+list1.get(i).itemName+" "+list1.get(i).itemMeta+" "
//						+list1.get(i).min+" "+list1.get(i).max+" "
//						+list1.get(i).enchant+" ");
				list2.add(list2.get(i - 1) + list1.get(i).weight);
			}
			
			//roll mining result
			int roll = ship.getRNG().nextInt(list2.get(list2.size() - 1));
			int result = 0;
			
			for (int i = 0; i < list2.size(); i++)
			{
				if (roll <= list2.get(i))
				{
					result = i;
					break;
				}
			}
			
			//get item
			ItemEntry ie = list1.get(result);
			Item item = Item.getByNameOrId(ie.itemName);
			if (item == null) return;
			
			//specific item meta value
			int metadata = ie.itemMeta;
			
			if (ie.itemMeta <= 0)
			{
				if (item instanceof BasicItem)
				{
					metadata = ship.getRNG().nextInt(((BasicItem) item).getTypes());
				}
				else
				{
					metadata = 0;
				}
			}
			
			//random stacksize
			int stacksize = ie.min;
			int fortlv = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, pickaxe);
			int lucklv = BuffHelper.getPotionLevel(host, 26);
			
			if (ie.max > ie.min)
			{
				stacksize = ie.min + ship.getRNG().nextInt(ie.max - ie.min + 1);
			}
			
			if (ie.enchant > 0F)
			{
				stacksize = (int) (stacksize * (1 + (fortlv + lucklv) * ie.enchant));
			}
			
			//create itemstack
			ItemStack stack = new ItemStack(item, stacksize, metadata);
			
			//put stack into ship's inventory
			boolean moved = InventoryHelper.moveItemstackToInv(ship.getCapaShipInventory(), stack, null);
        	
			//put stack on ground
        	if (!moved || stack.stackSize > 0)
        	{
        		EntityItem entityitem = new EntityItem(ship.world, ship.posX, ship.posY, ship.posZ, stack);
        		entityitem.motionX = host.getRNG().nextGaussian() * 0.08D;
	            entityitem.motionY = host.getRNG().nextGaussian() * 0.05D + 0.2D;
	            entityitem.motionZ = host.getRNG().nextGaussian() * 0.08D;
	            ship.world.spawnEntity(entityitem);
        	}
		}//end host is ship
	}
	
	/** generate fishing result, put itemstacks into inventory or on ground */
	public static void generateFishingResult(EntityLivingBase host)
	{
		float luck = 0F;
		WorldServer world = null;
		
		//check host type
		if (host instanceof BasicEntityShip)
		{
			world = (WorldServer) host.world;
			BasicEntityShip ship = (BasicEntityShip) host;
			
			//get max sea luck level on fishing rod
			int lv1 = EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, ship.getHeldItemMainhand());
			int lv2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.LUCK_OF_THE_SEA, ship.getHeldItemOffhand());
			luck = Math.max(lv1, lv2);
			
			//get luck potion on host
			lv1 = BuffHelper.getPotionLevel(host, 26);
			luck += lv1;
			
			//add level modify
			luck += ship.getLevel() / ConfigHandler.maxLevel * 1.5F;
		}
		else if (host instanceof IShipAttackBase)
		{
			world = (WorldServer) host.world;
		}
		else
		{
			return;
		}
		
		//get fishing loot table
		LootContext.Builder lootcontext$builder = new LootContext.Builder(world);
        lootcontext$builder.withLuck(luck);
        
        //generate itemstack
        for (ItemStack itemstack : world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(host.getRNG(), lootcontext$builder.build()))
        {
        	boolean moved = false;
        	
        	//put stack into ship's inventory
        	if (host instanceof BasicEntityShip)
        	{
        		moved = InventoryHelper.moveItemstackToInv(((BasicEntityShip) host).getCapaShipInventory(), itemstack, null);
        	}
        	
        	//put stack on ground
        	if (!moved || itemstack.stackSize > 0)
        	{
        		EntityItem entityitem = new EntityItem(world, host.posX, host.posY, host.posZ, itemstack);
        		entityitem.motionX = host.getRNG().nextGaussian() * 0.08D;
	            entityitem.motionY = host.getRNG().nextGaussian() * 0.05D + 0.2D;
	            entityitem.motionZ = host.getRNG().nextGaussian() * 0.08D;
                world.spawnEntity(entityitem);
        	}
        }
	}
	
	/**
	 * check tool is suitable for target
	 * 
	 * tool: tool type string
	 * targetType: 0:pickaxe, 1:shovel, 2:axe
	 * targetLevel: 0:wood/gold, 1:stone, 2:iron, 3:diamond
	 * 
	 */
	public static boolean isToolEffective(ItemStack stack, int targetType, int targetLevel)
	{
		if (stack == null) return false;
		
		String type = "pickaxe";
		
		switch (targetType)
		{
		case 1:
			type = "shovel";
		break;
		case 2:
			type = "axe";
		break;
		}
		
		return stack.getItem().getHarvestLevel(stack, type, null, null) >= targetLevel;
	}
	
	
}