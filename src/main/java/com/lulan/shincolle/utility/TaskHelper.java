package com.lulan.shincolle.utility;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.other.EntityShipFishingHook;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * helper for cooking, mining, fishing... etc.
 */
public class TaskHelper
{
	
	
	public TaskHelper() {}
	
	/**
	 * update task
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
			onUpdateCooking(host);
		break;
		case 2:  //fishing
			onUpdateFishing(host);
		break;
		case 3:  //mining
			onUpdateMining(host);
		break;
		case 4:  //crafting
			onUpdateCrafting(host);
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
				
		//TODO
	}
	
	/**
	 * mining task:
	 * put pickaxe in mainhand (slot 22)
	 */
	public static void onUpdateMining(BasicEntityShip host)
	{
		//null check
		if (host == null) return;
				
		//TODO
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
			
			//change data value
			rod.setItemDamage(1);
			
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
				host.decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.Move] * 10);
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
					host.decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.Move]);
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
						if (host.getRNG().nextInt(8) > 6)
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
	
	
}