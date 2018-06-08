package com.lulan.shincolle.utility;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import com.lulan.shincolle.capability.CapaInventoryExtend;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.oredict.OreDictionary;

/**
 * itemstack and slots helper
 */
public class InventoryHelper
{
	
	
	public InventoryHelper() {}
	
	/**
	 * check inventory has more items than temp setting
	 * 
	 * if excess TRUE (excess mode)
	 *   targetStacks is null = return true
	 *   targetStacks isn't null = check specified itemstack in inventory
	 *   return TRUE if all target amount is more than temp setting
	 *   
	 * if excess FALSE (remain mode)
	 */
	public static boolean checkInventoryAmount(IInventory inv, ItemStack[] tempStacks, boolean[] modeStacks, boolean checkMetadata, boolean checkNbt, boolean checkOredict, boolean excess)
	{
		if (inv == null) return true;
		
		boolean noTempItem = true;
		int[] targetAmount = new int[9];
		
		//null cehck
		if (tempStacks == null || tempStacks.length != 9)
		{
			return true;
		}
		else
		{
			//check itemstack temp setting
			for (int i = 0; i < 9; i++)
			{
				//if temp stack existed
				if (tempStacks[i] != null)
				{
					noTempItem = false;
					
					//ignore NOT mode item
					if (!modeStacks[i])
					{
						//calc the total number of target item
						targetAmount[i] = calcItemStackAmount(inv, tempStacks[i], checkMetadata, checkNbt, checkOredict);
					}
				}
				
				//if no specified itemstack, return true
				if (i == 8 && noTempItem)
				{
					return true;
				}//end temp all null
			}//end loop all temp slots
		}
		
		for (int i = 0; i < 9; i++)
		{
			//EXCESS MODE: all item amount must GREATER or EQUAL to temp setting
			if (excess)
			{
				if (tempStacks[i] != null && !modeStacks[i])
				{
					if (targetAmount[i] < tempStacks[i].stackSize) return false;
				}
			}
			//REMAIN MODE: all item amount must LESSER or EQUAL to temp setting
			else
			{
				if (tempStacks[i] != null && !modeStacks[i])
				{
					if (targetAmount[i] > tempStacks[i].stackSize) return false;
				}
			}
		}
		
		return true;
	}
	
	/** inventory wrapper for {@link #checkInventoryFluidContainer(IItemHandler inv, FluidStack targetFluid, boolean checkFull)} */
	public static boolean checkInventoryFluidContainerFromObj(Object inv, FluidStack targetFluid, boolean checkFull)
	{
		//null check
		if (inv == null || targetFluid == null) return true;
		
		//check IItemhandler
		if (inv instanceof ICapabilityProvider && ((ICapabilityProvider) inv).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			return checkInventoryFluidContainer(((ICapabilityProvider) inv).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), targetFluid, checkFull);
		}
		//check vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			return checkInventoryFluidContainer(VanillaDoubleChestItemHandler.get((TileEntityChest) inv), targetFluid, checkFull);
		}
		//check other IInventory
		else if (inv instanceof IInventory)
		{
			return checkInventoryFluidContainer(new InvWrapper(((IInventory) inv)), targetFluid, checkFull);
		}
		
		return true;
	}
	
	/**
	 * check fluid container in the inventory is full or empty<br>
	 * 
	 * @param inv IItemHandler of inventory
	 * @param targetFluid target want to check
	 * @param checkFull
	 *   true: check all container is full or not container
	 *   false: check all container is empty or not container
	 */
	public static boolean checkInventoryFluidContainer(IItemHandler inv, FluidStack targetFluid, boolean checkFull)
	{
		if (inv == null || targetFluid == null) return true;
		
		//if inventory is ship inv, skip equip slots
		int i = 0;
		if (inv instanceof CapaInventoryExtend) i = CapaInventoryExtend.SLOTS_EQUIP;
		
		for (; i < inv.getSlots(); i++)
		{
			//check all slots are full (checkFull = true) or empty (checkFull = false)
			if (!checkFluidContainer(inv.getStackInSlot(i), targetFluid, checkFull))
				return false;
		}
		
		return true;
	}
	
	/**
	 * if checkFull = 
	 *   TRUE: return FALSE if itemstack can accept target fluid
	 *   FALSE: return FALSE if itemstack can not accept target fluid
	 */
	public static boolean checkFluidContainer(@Nonnull ItemStack stack, FluidStack targetFluid, boolean checkFull)
	{
		if (!stack.isEmpty())
		{
			//if item has fluid capability
			if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
			{
				IFluidHandler fh = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				if (fh == null) return true;	//is same with non fluid container
				
				IFluidTankProperties[] tanks = fh.getTankProperties();
				if (tanks == null) return true;	//is same with non fluid container
				
				//check fluid amount in all tanks
				for (IFluidTankProperties tank : tanks)
				{
					FluidStack fstack = tank.getContents();
					
					//check container is full
					if (checkFull)
					{
						//if container can be filled = it's not full
						if (fstack == null ||
							tank.canFill() && fstack.amount < tank.getCapacity() &&
							(targetFluid == null || targetFluid.equals(fstack)))
						{
							return false;
						}
					}
					//check container is empty
					else
					{
						if (fstack != null && tank.canDrain() && fstack.amount > 0)
						{
							return false;
						}
					}
				}
			}//end get fluid capa
		}
		
		return true;
	}
	
	/** inventory wrapper for {@link #checkInventoryFull(IItemHandler inv)} */
	public static boolean checkInventoryFullFromObj(Object inv)
	{
		//null check
		if (inv == null) return true;
		
		//check IItemhandler
		if (inv instanceof ICapabilityProvider && ((ICapabilityProvider) inv).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			return checkInventoryFull(((ICapabilityProvider) inv).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		}
		//check vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			return checkInventoryFull(VanillaDoubleChestItemHandler.get((TileEntityChest) inv));
		}
		//check other IInventory
		else if (inv instanceof IInventory)
		{
			return checkInventoryFull(new InvWrapper(((IInventory) inv)));
		}
		
		return true;
	}
	
	/**
	 * check inventory is full
	 * 
	 * @return true if no empty slot or inventory is null
	 */
	public static boolean checkInventoryFull(IItemHandler inv)
	{
		//null check
		if (inv == null) return true;
		
		//get any empty slot = false
		int startSlot = 0;
		if (inv instanceof CapaInventoryExtend) startSlot = CapaInventoryExtend.SLOTS_EQUIP;
		
		if (getFirstSlotEmpty(inv, startSlot, inv.getSlots()) >= 0) return false;
		
		return true;
	}
	
	/** inventory wrapper for {@link #checkInventoryEmpty(IItemHandler, ItemStack[], boolean[], boolean, boolean, boolean)} */
	public static boolean checkInventoryEmptyFromObj(Object inv, @Nonnull NonNullList<ItemStack> tempStacks, @Nonnull boolean[] modeStacks, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		//null check
		if (inv == null) return true;
		
		//check IItemhandler
		if (inv instanceof ICapabilityProvider && ((ICapabilityProvider) inv).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
		{
			return checkInventoryEmpty(((ICapabilityProvider) inv).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), tempStacks, modeStacks, checkMetadata, checkNbt, checkOredict);
		}
		//check vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			return checkInventoryEmpty(VanillaDoubleChestItemHandler.get((TileEntityChest) inv), tempStacks, modeStacks, checkMetadata, checkNbt, checkOredict);
		}
		//check other IInventory
		else if (inv instanceof IInventory)
		{
			return checkInventoryEmpty(new InvWrapper(((IInventory) inv)), tempStacks, modeStacks, checkMetadata, checkNbt, checkOredict);
		}
		
		return true;
	}
	
	/**
	 * check inventory is empty or no specified item
	 * 
	 * @param tempStacks
	 *   if null = check all slots empty;
	 *   if not null = check no specified itemstack in inventory
	 * 
	 * @param modeStacks
	 *   TRUE: itemstack is NOT mode (ignore or as empty);
	 *   FALSE: itemstack is NORMAL mode (have to match);
	 *   
	 * @return true if inventory is empty or no specified item
	 */
	public static boolean checkInventoryEmpty(IItemHandler inv, @Nonnull NonNullList<ItemStack> tempStacks, @Nonnull boolean[] modeStacks, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		//null check
		if (inv == null || tempStacks.size() != modeStacks.length) return true;
		
		boolean noTempItem = true;
		
		//check specified itemstack in inventory
		for (int i = 0; i < tempStacks.size(); i++)
		{
			if (!tempStacks.get(i).isEmpty())
			{
				noTempItem = false;
				
				//ignore NOT mode item
				if (!modeStacks[i])
				{
					//stack found, chest is not empty
					if (matchTargetItem(inv, tempStacks.get(i), checkMetadata, checkNbt, checkOredict))
						return false;
				}
			}
			
			//if no specified itemstack, check all slot is null
			if (i == (tempStacks.size() - 1) && noTempItem)
			{
				return isSlotEmpty(inv, inv instanceof CapaInventoryExtend ? CapaInventoryExtend.SLOTS_EQUIP : 0, inv.getSlots());
			}//end temp all null
		}//end loop all temp slots
		
		return true;
	}
	
	/** check slots are all empty (excluding endSlot) */
	public static boolean isSlotEmpty(IItemHandler inv, int startSlot, int endSlot)
	{
		for (int i = startSlot; i < endSlot; i++)
		{
			if (!inv.getStackInSlot(i).isEmpty()) return false;
		}
		
		return true;
	}
	
	/** get first empty slot (excluding endSlot) */
  	public static int getFirstSlotEmpty(@Nonnull IItemHandler inv, int startSlot, int endSlot)
  	{
  		for (int i = startSlot; i < endSlot; i++)
  		{
  			if (inv.getStackInSlot(i).isEmpty()) return i;
  		}
  		
  		return -1;
  	}
  	
  	/** get first stackable slot (excluding endSlot) */
  	public static int getFirstSlotStackable(@Nonnull IItemHandler inv, @Nonnull ItemStack stack, int startSlot, int endSlot)
    {
  		//null check
  		if (stack.isEmpty()) return -1;
  		
  		//loop all slots
        for (int i = startSlot; i < endSlot; i++)
        {
  			ItemStack slotstack = inv.getStackInSlot(i);
  			
  			//check same item, same meta, same nbt tag and has free space
            if (!slotstack.isEmpty() && slotstack.getItem() == stack.getItem() &&
            	slotstack.isStackable() && stack.getCount() <= (getStackLimit(inv, i, stack) - slotstack.getCount()) &&
                slotstack.getItemDamage() == stack.getItemDamage() &&
                ItemStack.areItemStackTagsEqual(slotstack, stack))
            {
                return i;
            }
        }

        return -1;
    }
  	
  	/** get max stacksize of the slot */
  	public static int getStackLimit(@Nonnull IItemHandler inv, int slot, @Nonnull ItemStack stack)
    {
        return Math.min(inv.getSlotLimit(slot), stack.getMaxStackSize());
    }
	
	/**
	 * check inventory has temp stack, return TRUE if item found
	 */
	public static boolean matchTargetItem(@Nonnull IItemHandler inv, @Nonnull ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		//inventory is ship inv
		if(inv instanceof CapaInventoryExtend)
		{
			CapaInventoryExtend shipInv = (CapaInventoryExtend) inv;
			
			//get any empty slot = false
			for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < shipInv.getSizeInventoryPaged(); i++)
			{
				if (matchTargetItem(shipInv.getStackInSlotWithPageCheck(i), temp, checkMetadata, checkNbt, checkOredict)) return true;
			}
		}
		//inventory is vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			//check main chest
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (matchTargetItem(inv.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) return true;
			}
			
			//check adj chest
			TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) inv);
			
			if (chest2 != null)
			{
				for (int i = 0; i < chest2.getSizeInventory(); i++)
				{
					if (matchTargetItem(chest2.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) return true;
				}
			}
		}
		//other inventory
		else
		{
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (matchTargetItem(inv.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) return true;
			}
		}
		
		return false;
	}
	
	/**
	 * check target stack in inventory except xx slots
	 * exceptSlots: int[] {5, 7, 8} = dont check slot 5, 7, 8
	 * return slot id, -1 if itemstack not found
	 */
	public static int matchTargetItemExceptSlots(IInventory inv, ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict, int[] exceptSlots)
	{
		if (temp == null) return -1;
		
		//inventory is ship inv
		if (inv instanceof CapaInventoryExtend)
		{
			CapaInventoryExtend shipInv = (CapaInventoryExtend) inv;
			
			//get any empty slot = false
			for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < shipInv.getSizeInventoryPaged(); i++)
			{
				if (CalcHelper.checkIntNotInArray(i, exceptSlots))
				{
					if (matchTargetItem(shipInv.getStackInSlotWithPageCheck(i), temp, checkMetadata, checkNbt, checkOredict)) return i;
				}
			}
		}
		//other inventory
		else
		{
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (CalcHelper.checkIntNotInArray(i, exceptSlots))
				{
					if (matchTargetItem(inv.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) return i;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * calculate the total "STACK NUMBER" of target item, not item amount!!
	 * 
	 * return TRUE if total stack number = temp stackSize
	 */
	public static int calcItemStackAmount(IItemHandler inv, ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		int targetAmount = 0;
		
		//inventory is ship inv
		if(inv instanceof CapaInventoryExtend)
		{
			CapaInventoryExtend shipInv = (CapaInventoryExtend) inv;
			
			//get any empty slot = false
			for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < shipInv.getSizeInventoryPaged(); i++)
			{
				if (matchTargetItem(shipInv.getStackInSlotWithPageCheck(i), temp, checkMetadata, checkNbt, checkOredict)) targetAmount++;
			}
		}
		//inventory is vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			//check main chest
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (matchTargetItem(inv.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) targetAmount++;
			}
			
			//check adj chest
			TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) inv);
			
			if (chest2 != null)
			{
				for (int i = 0; i < chest2.getSizeInventory(); i++)
				{
					if (matchTargetItem(chest2.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) targetAmount++;
				}
			}
		}
		//other inventory
		else
		{
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (matchTargetItem(inv.getStackInSlot(i), temp, checkMetadata, checkNbt, checkOredict)) targetAmount++;
			}
		}
		
		return targetAmount;
	}
	
	/**
	 * get and remove temp item from inventory, return item
	 */
	public static ItemStack getAndRemoveItem(IInventory inv, ItemStack temp, int number, boolean checkMetadata, boolean checkNbt, boolean checkOredict, int[] exceptSlots)
	{
		if (temp == null || number <= 0) return null;
		if (number > 64) number = 64;
		
		ItemStack getItem = null;
		ItemStack getTemp = null;
		boolean searchItem = true;
		int slotid = -1;
		
		//if vanilla chest
		if (inv instanceof TileEntityChest)
		{
			while (searchItem)
			{
				slotid = matchTargetItemExceptSlots(inv, temp, checkMetadata, checkNbt, checkOredict, exceptSlots);
				
				//item not found, search adj chest
				if (slotid < 0)
				{
					TileEntityChest adjChest = TileEntityHelper.getAdjChest((TileEntityChest) inv);
					
					if (adjChest != null)
					{
						slotid = matchTargetItemExceptSlots(adjChest, temp, checkMetadata, checkNbt, checkOredict, exceptSlots);
						
						//item not found in adj chest, return
						if (slotid < 0)
						{
							return getItem;
						}
						
						//get item in chest
						getTemp = adjChest.getStackInSlot(slotid);
						
						//got something before, check stack size
						if (getItem != null)
						{
							if (number > 0)
							{
								int minsize = Math.min(getTemp.stackSize, number);
								number -= minsize;
								getTemp.stackSize -= minsize;
								getItem.stackSize += minsize;
								
								//clear slot if size <= 0
								if (getTemp.stackSize <= 0)
								{
									adjChest.setInventorySlotContents(slotid, null);
								}
							}
							else
							{
								return getItem;
							}
						}
						//get new item
						else
						{
							//set new item as temp
							temp = getTemp.copy();
							checkMetadata = true;
							checkNbt = true;
							checkOredict = true;
							
							//take item from chest
							if (number > 0)
							{
								int minsize = Math.min(getTemp.stackSize, number);
								number -= minsize;
								getTemp.stackSize -= minsize;
								getItem = getTemp.copy();
								getItem.stackSize = minsize;
								
								//clear slot if size <= 0
								if (getTemp.stackSize <= 0)
								{
									adjChest.setInventorySlotContents(slotid, null);
								}
							}
							else
							{
								return getItem;
							}
						}
					}//end get adj chest
					//no adj chest, return
					else
					{
						return getItem;
					}
				}//end check adj chest
				else
				{
					getTemp = inv.getStackInSlot(slotid);
					
					//got something before, check stack size
					if (getItem != null)
					{
						if (number > 0)
						{
							int minsize = Math.min(getTemp.stackSize, number);
							number -= minsize;
							getTemp.stackSize -= minsize;
							getItem.stackSize += minsize;
							
							//clear slot if size <= 0
							if (getTemp.stackSize <= 0)
							{
								inv.setInventorySlotContents(slotid, null);
							}
						}
						else
						{
							return getItem;
						}
					}
					//get new item
					else
					{
						//set new item as temp
						temp = getTemp.copy();
						checkMetadata = true;
						checkNbt = true;
						checkOredict = true;
						
						//take item from chest
						if (number > 0)
						{
							int minsize = Math.min(getTemp.stackSize, number);
							number -= minsize;
							getTemp.stackSize -= minsize;
							getItem = getTemp.copy();
							getItem.stackSize = minsize;
							
							//clear slot if size <= 0
							if (getTemp.stackSize <= 0)
							{
								inv.setInventorySlotContents(slotid, null);
							}
						}
						else
						{
							return getItem;
						}
					}//end is new item
				}//end get item from inv
				
				if (number <= 0) return getItem;
			}//end while search item
		}
		//other inventory
		else
		{
			while (searchItem)
			{
				slotid = matchTargetItemExceptSlots(inv, temp, checkMetadata, checkNbt, checkOredict, exceptSlots);
				
				//item not found, return
				if (slotid < 0)
				{
					return getItem;
				}
				else
				{
					getTemp = inv.getStackInSlot(slotid);
					
					//got something before, check stack size
					if (getItem != null)
					{
						if (number > 0)
						{
							int minsize = Math.min(getTemp.stackSize, number);
							number -= minsize;
							getTemp.stackSize -= minsize;
							getItem.stackSize += minsize;
							
							//clear slot if size <= 0
							if (getTemp.stackSize <= 0)
							{
								inv.setInventorySlotContents(slotid, null);
							}
						}
						else
						{
							return getItem;
						}
					}
					//get new item
					else
					{
						//set new item as temp
						temp = getTemp.copy();
						checkMetadata = true;
						checkNbt = true;
						checkOredict = true;
						
						//take item from chest
						if (number > 0)
						{
							int minsize = Math.min(getTemp.stackSize, number);
							number -= minsize;
							getTemp.stackSize -= minsize;
							getItem = getTemp.copy();
							getItem.stackSize = minsize;
							
							//clear slot if size <= 0
							if (getTemp.stackSize <= 0)
							{
								inv.setInventorySlotContents(slotid, null);
							}
						}
						else
						{
							return getItem;
						}
					}//end is new item
				}//end get item from inv
				
				if (number <= 0) return getItem;
			}//end while search item
		}
		
		return getItem;
	}
	
	/** check target stack is same with temp stack */
	public static boolean matchTargetItem(ItemStack target, ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		//null check
		if ((temp == ItemStack.EMPTY && target == ItemStack.EMPTY) ||
			(temp == null && target == null))
		{
			return true;
		}
		else if (temp != null && target != null &&
				 temp != ItemStack.EMPTY && target != ItemStack.EMPTY)
		{
			//check item type
			if (target.getItem() == temp.getItem())
			{
				//check both nbt and meta
				if (checkNbt && checkMetadata)
				{
					if (ItemStack.areItemStackTagsEqual(target, temp) &&
						target.getItemDamage() == temp.getItemDamage()) return true;
				}
				//check nbt only
				else if (checkNbt)
				{
					if (ItemStack.areItemStackTagsEqual(target, temp)) return true;
				}
				//check meta only
				else if (checkMetadata)
				{
					if (target.getItemDamage() == temp.getItemDamage()) return true;
				}
				//dont check nbt and meta
				else
				{
					return true;
				}
			}
			//is not same item, try forge ore dict
			else
			{
				//check ore dict
				if (checkOredict)
				{
					HashSet<Integer> set1 = CalcHelper.intArrayToSet(OreDictionary.getOreIDs(target));
					HashSet<Integer> set2 = CalcHelper.intArrayToSet(OreDictionary.getOreIDs(temp));
					
					//intersection
					set1.retainAll(set2);
					
					//match all ids
					if (set1.size() > 0) return true;
				}
			}
		}//end null check
		
		return false;
	}
	
	/**
	 * get slots by insert side, item stack and ship's side setting
	 * 
	 * stack: target item, null = ignore item
	 * 
	 * side: TaskSide from ship
	 *       0~5 bit for input side: Down, Up, N, S, W, E
	 *       6~11 bit for output side
	 *       12~17 bit for fuel side
	 *       18~20 bit for metadata, ore dict, nbt tag check
	 *       
	 * type: 0:input, 1:output, 2:fuel
	 * 
	 * return available slots, all slots from each side will be combined to one array
	 */
	public static int[] getSlotsFromSide(ISidedInventory te, ItemStack stack, int side, int type)
	{
		//null check
		if (te == null) return new int[] {};
		
		//get side
		int padbit = type * 6;
		int tarbit = 0;
		int[] slotstemp = null;
		Set<Integer> slots = new HashSet<Integer>();
		
		//loop all sides
		for (int i = 0; i < 6; i++)
		{
			EnumFacing face = EnumFacing.getFront(i);  //id: D U N S W E
			tarbit = i + padbit;
			
			//if side must be checked
			if ((side & Values.N.Pow2[tarbit]) == Values.N.Pow2[tarbit])
			{
				//get slots by side
				slotstemp = te.getSlotsForFace(face);
				
				//no slot for that side, skip
				if (slotstemp == null || slotstemp.length <= 0) continue;
				
				//get slots, check stack can be inserted into slot
				for (int j = 0; j < slotstemp.length; j++)
				{
					//check input or fuel
					if (type != 1)
					{
						if (stack == null || te.canInsertItem(slotstemp[j], stack, face))
						{
							slots.add(slotstemp[j]);
						}
					}
					//check output
					else
					{
						if (stack == null || te.canExtractItem(slotstemp[j], stack, face))
						{
							slots.add(slotstemp[j]);
						}
					}
				}
			}
		}
		
		//return int array
		if (slots.size() > 0)
		{
			return CalcHelper.intSetToArray(slots);
		}
		else
		{
			return new int[] {};
		}
	}
	
	/**
	 * move itemstack to inv with inv type checking and specified slots
	 * slots: specified slots, if null = all slots
	 * return true if item moved
	 */
	public static boolean moveItemstackToInv(IInventory inv, ItemStack moveitem, int[] toSlots)
	{
		boolean moved = false;
		
		//move item to inv
		if (moveitem != null)
		{
			//invTo is ship inv
			if (inv instanceof CapaInventoryExtend)
			{
				moved = mergeItemStack(inv, moveitem, toSlots);
			}
			//invTo is vanilla chest
			else if (inv instanceof TileEntityChest)
			{
				TileEntityChest chest = (TileEntityChest) inv;
				TileEntityChest chest2 = null;
				
				//move to main chest
				moved = mergeItemStack(chest, moveitem, toSlots);
				
				//move fail, check adj chest
				if (!moved)
				{
					//get adj chest
					chest2 = TileEntityHelper.getAdjChest(chest);
					
					//move to adj chest
					if (chest2 != null) moved = mergeItemStack(chest2, moveitem, toSlots);
				}//end move to adj chest
			}
			//other normal inv
			else
			{
				moved = mergeItemStack(inv, moveitem, toSlots);
			}
			
		}//end move item
		
		return moved;
	}
	
	/**
	 * merge itemstack to slots
	 * slots: specified slots, if null = all slots
	 */
	public static boolean mergeItemStack(IInventory inv, ItemStack itemstack, int[] slots)
	{
		ItemStack slotstack;
		boolean movedItem = false;
        int k = 0;
        int j = 0;
        int startid = 0;
        int maxSize = inv.getSizeInventory();

        //init slots id
        if (slots == null)
        {
        	if(inv instanceof CapaInventoryExtend)
            {
            	//start at inv slots
            	startid = ContainerShipInventory.SLOTS_SHIPINV;
            	
            	//get slot size by pages
            	maxSize = ((CapaInventoryExtend) inv).getSizeInventoryPaged();
            }
        }
        else
        {
        	startid = 0;
        	maxSize = slots.length;
        }

        //is stackable item
        if (itemstack.isStackable())
        {
        	k = startid;
        	
        	//loop all slots until stacksize = 0
            while (itemstack.stackSize > 0 && k < maxSize)
            {
            	//calc slot id
            	if (slots != null) j = slots[k];
            	else j = k;
            	
            	//get stack in slot
				if (inv instanceof CapaInventoryExtend) slotstack = ((CapaInventoryExtend) inv).getStackInSlotWithPageCheck(j);
				else slotstack = inv.getStackInSlot(j);

                //if same item, merge to slot
                if (slotstack != null && slotstack.getItem() == itemstack.getItem() &&
                	(!itemstack.getHasSubtypes() || itemstack.getItemDamage() == slotstack.getItemDamage()) &&
                   ItemStack.areItemStackTagsEqual(itemstack, slotstack))
                {
                    int l = slotstack.stackSize + itemstack.stackSize;

                    //merge: total size < max size
                    if (l <= itemstack.getMaxStackSize())
                    {
                        itemstack.stackSize = 0;
                        slotstack.stackSize = l;
                        movedItem = true;
                    }
                    //merge: move item to slot stack
                    else if (slotstack.stackSize < itemstack.getMaxStackSize())
                    {
                        itemstack.stackSize -= itemstack.getMaxStackSize() - slotstack.stackSize;
                        slotstack.stackSize = itemstack.getMaxStackSize();
                        movedItem = true;
                    }
                }

                //next slot
                ++k;
            }//end loop all slots
        }//end is stackable

        //no stack can merge, find empty slot
        if (itemstack.stackSize > 0)
        {
        	k = startid;

        	//loop all slots to find empty slot
            while (k < maxSize)
            {
            	//calc slot id
            	if (slots != null) j = slots[k];
            	else j = k;
            	
            	//get stack in slot
				if (inv instanceof CapaInventoryExtend) slotstack = ((CapaInventoryExtend) inv).getStackInSlotWithPageCheck(j);
				else slotstack = inv.getStackInSlot(j);

                //find empty slot
                if (slotstack == null)
                {
					if (inv instanceof CapaInventoryExtend) ((CapaInventoryExtend) inv).setInventorySlotWithPageCheck(j, itemstack.copy());
					else inv.setInventorySlotContents(j, itemstack.copy());
                    itemstack.stackSize = 0;
                    movedItem = true;
                    break;
                }

                //next slot
                ++k;
            }//end loop all slots
        }

        return movedItem;
    }
	
	@Optional.Method(modid = Reference.MOD_ID_Baubles)
	public static boolean checkRingInBaubles(EntityPlayer player)
	{
		IBaublesItemHandler bb = (IBaublesItemHandler) player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
	
		if (bb != null)
		{
			for (int i = 0; i < bb.getSlots(); i++)
			{
				ItemStack stack = bb.getStackInSlot(i);
				if (stack != null && stack.getItem() == ModItems.MarriageRing) return true;
			}
		}
		
		return false;
	}
	
  	/**
  	 * check itemstack in ship inventory simply
  	 */
  	public static boolean checkItemInShipInventory(CapaInventoryExtend inv, Item item, int meta, int minSlot, int maxSlot)
  	{
  		if (inv != null)
  		{
  			for (int i = minSlot; i < maxSlot; i++)
  			{
  				ItemStack stack = inv.getStackInSlotWithPageCheck(i);
  				
  				if (stack != null && stack.getItem() == item && stack.getItemDamage() == meta)
				{
  					return true;
				}
  			}
  		}
  		
  		return false;
  	}
  	
  	/**
  	 * fill fluid container in ship inventory simply
  	 */
  	public static boolean tryFillContainer(CapaInventoryExtend inv, FluidStack fs)
  	{
  		if (fs == null) return false;
  		
  		ItemStack stack;
  		int amountMoved = 0;
  		
  		//try fill all container in inventory
  		for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < inv.getSizeInventoryPaged(); i++)
  		{
  			stack = inv.getStackInSlotWithPageCheck(i);
  			
  			//only for container with stackSize = 1
  			if (stack != null && stack.stackSize == 1)
  			{
  				//check item has fluid capa
  				if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
  				{
  					IFluidHandler fluid = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
  					amountMoved += fluid.fill(fs, true);
  				}//end get capa
  				else if (stack.getItem() instanceof IFluidContainerItem)
  				{
  					amountMoved = ((IFluidContainerItem) stack.getItem()).fill(stack, fs, true);
  				}
  				
  				//if fill success
  				if (amountMoved > 0)
  				{
  					fs.amount -= amountMoved;
  					if (fs.amount <= 0) break;
  				}
  			}//end get item
  		}//end for all slots
  		
  		if (amountMoved > 0 && inv.getHost() != null)
  		{
  			//add exp to transport ship
  			if (inv.getHost().getShipType() == ID.ShipType.TRANSPORT)
  			{
  				inv.getHost().addShipExp(ConfigHandler.expGain[6]);
  			}
  			
  			//apply particle
  			TargetPoint tp = new TargetPoint(inv.getHost().dimension, inv.getHost().posX, inv.getHost().posY, inv.getHost().posZ, 48D);
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(inv.getHost(), 21, 0D, 0.1D, 0D), tp);
  			
  			return true;
  		}
  		
  		return false;
  	}
  	
  	/**
  	 * put itemstack into inventory or drop on ground, return false if drop on ground
  	 */
  	public static boolean moveItemstackToInv(Entity host, IInventory inv, ItemStack moveitem, int[] toSlots)
  	{
  		if (host == null || host.world == null || inv == null) return false;
  		
  		//put stack into ship's inventory
		boolean moved = moveItemstackToInv(inv, moveitem, null);
    	
		//put stack on ground
    	if (!moved || moveitem.stackSize > 0)
    	{
    		dropItemOnGround(host, moveitem);
    	}
    	
    	return true;
  	}
  	
  	/**
  	 * drop item on ground
  	 */
  	public static void dropItemOnGround(Entity host, ItemStack stack)
  	{
  	    dropItemOnGround(host.world, stack, host.posX, host.posY, host.posZ);
  	}
  	
  	/**
     * drop item on ground
     */
    public static void dropItemOnGround(World w, ItemStack stack, double x, double y, double z)
    {
        float f = w.rand.nextFloat() * 0.8F + 0.1F;
        float f1 = w.rand.nextFloat() * 0.8F + 0.1F;
        float f2 = w.rand.nextFloat() * 0.8F + 0.1F;

        while (!stack.isEmpty())
        {
            EntityItem entityitem = new EntityItem(w, x + (double)f, y + (double)f1, z + (double)f2, stack);
            entityitem.motionX = w.rand.nextGaussian() * 0.07D;
            entityitem.motionY = w.rand.nextGaussian() * 0.25D;
            entityitem.motionZ = w.rand.nextGaussian() * 0.07D;
            w.spawnEntity(entityitem);
        }
    }
  	
  	/**
  	 * insert item into target inventory, ref: {mcf}.items.wrapper.SidedInvWrapper
  	 */
    @Override
    @Nonnull
    public static ItemStack insertItem(IItemHandler fromInv, IItemHandler toInv, int slot, @Nonnull ItemStack stack, boolean simulate)
    {
    	//null check
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        ItemStack stackInSlot = toInv.getStackInSlot(slot);

        int m;
        if (!stackInSlot.isEmpty())
        {
            if (stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), getSlotLimit(slot)))
                return stack;

            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot))
                return stack;

            if (!getInv().isItemValidForSlot(slot, stack))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot)) - stackInSlot.getCount();

            if (stack.getCount() <= m)
            {
                if (!simulate)
                {
                    ItemStack copy = stack.copy();
                    copy.grow(stackInSlot.getCount());
                    getInv().setInventorySlotContents(slot, copy);
                    getInv().markDirty();
                }

                return ItemStack.EMPTY;
            }
            else
            {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate)
                {
                    ItemStack copy = stack.splitStack(m);
                    copy.grow(stackInSlot.getCount());
                    getInv().setInventorySlotContents(slot, copy);
                    getInv().markDirty();
                    return stack;
                }
                else
                {
                    stack.shrink(m);
                    return stack;
                }
            }
        }
        else
        {
            if (!getInv().isItemValidForSlot(slot, stack))
                return stack;

            m = Math.min(stack.getMaxStackSize(), getSlotLimit(slot));
            if (m < stack.getCount())
            {
                // copy the stack to not modify the original one
                stack = stack.copy();
                if (!simulate)
                {
                    getInv().setInventorySlotContents(slot, stack.splitStack(m));
                    getInv().markDirty();
                    return stack;
                }
                else
                {
                    stack.shrink(m);
                    return stack;
                }
            }
            else
            {
                if (!simulate)
                {
                    getInv().setInventorySlotContents(slot, stack);
                    getInv().markDirty();
                }
                return ItemStack.EMPTY;
            }
        }

    }
	
	
}