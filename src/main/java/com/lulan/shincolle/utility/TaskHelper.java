package com.lulan.shincolle.utility;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

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
		break;
		case 3:  //mining
		break;
		case 4:  //crafting
		break;
		}
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
		
		//check recipe
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
		
		//remove same slots
		fuSlots = CalcHelper.arrayRemoveAll(fuSlots, inSlots);
		outSlots = CalcHelper.arrayRemoveAll(outSlots, inSlots);
		outSlots = CalcHelper.arrayRemoveAll(outSlots, fuSlots);
		
		//TODO fuel slot - input, out slot - input - fuel
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
				
				if (ouputStack != null)
				{
					//dont take out input and fuel item
					if (ouputStack.isItemEqual(mainstack) || ouputStack.isItemEqual(offstack))
					{
						ouputStack = null;
						continue;
					}
					
					outID = id;
					break;
				}
			}
			
			//get output item
			if (ouputStack != null)
			{
				moved = InventoryHelper.moveItemstackToInv(inv, ouputStack, null);
				swing = swing || moved;
				
				//if moved, check stacksize
				if (moved && ouputStack.stackSize <= 0)
				{
					furnace.setInventorySlotContents(outID, null);
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
	
	
}