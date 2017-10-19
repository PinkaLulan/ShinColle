package com.lulan.shincolle.utility;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
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
//			onUpdateCooking(host);
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
	 * moved item amount per action is same with item amount in mainhand
	 * 
	 * need checking input/output/fuel slot side
	 */
	public static void onUpdateCooking(BasicEntityShip host)
	{
		//null check
		if (host == null) return;
		
		//get guard position
		BlockPos gpos = new BlockPos(host.getGuardedPos(0), host.getGuardedPos(1), host.getGuardedPos(2));
		
		//check guard type
		if (host.getGuardedPos(4) != 1) return;
		
		//check dimension
		if (host.world.provider.getDimension() != host.getGuardedPos(3)) return;
		
		//check held item
		ItemStack stack = host.getHeldItemMainhand();
		if (stack == null) return;
		
		//check target is furnace
		TileEntity te = host.world.getTileEntity(gpos);
		
		if (te instanceof ISidedInventory)
		{
			ISidedInventory furnace = (ISidedInventory) te;
			
			//check recipe
			if (!canItemStackSmelt(stack)) return;
			
			//check stacks in inventory except offhand slot
			ItemStack targetStack = null;
			CapaShipInventory inv = host.getCapaShipInventory();
			
			for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < inv.getSizeInventoryPaged(); i++)
			{
				//except offhand slot
				if (i != 23)
				{
					targetStack = inv.getStackInSlotWithoutPaging(i);
					
					if (stack.isItemEqual(targetStack)) break;
					targetStack = null;
				}
			}
			
			//get target item, put it into furnace
			//TODO NYI: add in/out slot setting for individual ship
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