package com.lulan.shincolle.utility;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

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
				if (!tempStacks[i].isEmpty())
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
				if (!tempStacks[i].isEmpty() && !modeStacks[i])
				{
					if (targetAmount[i] < tempStacks[i].getCount()) return false;
				}
			}
			//REMAIN MODE: all item amount must LESSER or EQUAL to temp setting
			else
			{
				if (!tempStacks[i].isEmpty() && !modeStacks[i])
				{
					if (targetAmount[i] > tempStacks[i].getCount()) return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * check all itemstack in the inventory is full, empty or not fluid container
	 * 
	 * checkFull:
	 *   true: check all container is full or not container
	 *   false: check all container is empty or not container
	 */
	public static boolean checkFluidContainer(IInventory inv, FluidStack targetFluid, boolean checkFull)
	{
		if (inv == null) return true;
		
		//inventory is ship inv
		if(inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			
			for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < shipInv.getSizeInventoryPaged(); i++)
			{
				//check all slots are full
				if (checkFull)
				{
					if (!checkFluidContainer(shipInv.getStackInSlotWithPageCheck(i), targetFluid, true))
						return false;
				}
				//check all slots are empty
				else
				{
					if (!checkFluidContainer(shipInv.getStackInSlotWithPageCheck(i), targetFluid, false))
						return false;
				}
			}
		}
		//inventory is vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			//check main chest
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				//check all slots are full
				if (checkFull)
				{
					if (!checkFluidContainer(inv.getStackInSlot(i), targetFluid, true))
						return false;
				}
				//check all slots are empty
				else
				{
					if (!checkFluidContainer(inv.getStackInSlot(i), targetFluid, false))
						return false;
				}
			}
			
			//check adj chest
			TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) inv);
			
			if (chest2 != null)
			{
				for (int i = 0; i < chest2.getSizeInventory(); i++)
				{
					//check all slots are full
					if (checkFull)
					{
						if (!checkFluidContainer(chest2.getStackInSlot(i), targetFluid, true))
							return false;
					}
					//check all slots are empty
					else
					{
						if (!checkFluidContainer(chest2.getStackInSlot(i), targetFluid, false))
							return false;
					}
				}
			}
		}
		//other inventory
		else
		{
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				//check all slots are full
				if (checkFull)
				{
					if (!checkFluidContainer(inv.getStackInSlot(i), targetFluid, true))
						return false;
				}
				//check all slots are empty
				else
				{
					if (!checkFluidContainer(inv.getStackInSlot(i), targetFluid, false))
						return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * checkFull = TRUE:  return FALSE if itemstack can accept target fluid
	 *           = FALSE: return FALSE if itemstack can not accept target fluid
	 */
	public static boolean checkFluidContainer(ItemStack stack, FluidStack targetFluid, boolean checkFull)
	{
		if (!stack.isEmpty())
		{
			//if item has fluid capability
			if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
			{
				IFluidHandler fh = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
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
	
	/** check inventory is full */
	public static boolean checkInventoryFull(IInventory inv)
	{
		if (inv == null) return true;
		
		int i;
		
		//inventory is ship inv
		if(inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			
			//get any empty slot = false
			for (i = ContainerShipInventory.SLOTS_SHIPINV; i < shipInv.getSizeInventoryPaged(); i++)
			{
				if (shipInv.getStackInSlotWithPageCheck(i).isEmpty()) return false;
			}
		}
		//inventory is vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			//check main chest
			for (i = 0; i < inv.getSizeInventory(); i++)
			{
				if (inv.getStackInSlot(i).isEmpty()) return false;
			}
			
			//check adj chest
			TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) inv);
			
			if (chest2 != null)
			{
				for (i = 0; i < chest2.getSizeInventory(); i++)
				{
					if (chest2.getStackInSlot(i).isEmpty()) return false;
				}
			}
		}
		//other inventory
		else
		{
			for (i = 0; i < inv.getSizeInventory(); i++)
			{
				if (inv.getStackInSlot(i).isEmpty()) return false;
			}
		}
		
		return true;
	}
	
	/**
	 * check inventory is empty
	 * 
	 * if targetStacks is null = check all slots is null
	 * if targetStacks isn't null = check no specified itemstack in inventory
	 * 
	 * modeStacks = TRUE: itemstack is NOT mode
	 *              FALSE: itemstack is NORMAL mode
	 */
	public static boolean checkInventoryEmpty(IInventory inv, ItemStack[] tempStacks, boolean[] modeStacks, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		if (inv == null) return true;
		
		boolean noTempItem = true;
		
		//null cehck
		if (tempStacks == null || tempStacks.length != 9 || modeStacks == null || modeStacks.length != 9)
		{
			return isAllSlotEmpty(inv, tempStacks, modeStacks, checkMetadata, checkNbt, checkOredict);
		}
		//check specified itemstack in inventory
		else
		{
			for (int i = 0; i < 9; i++)
			{
				if (!tempStacks[i].isEmpty())
				{
					noTempItem = false;
					
					//ignore NOT mode item
					if (!modeStacks[i])
					{
						//stack found, chest is not empty
						if (matchTargetItem(inv, tempStacks[i], checkMetadata, checkNbt, checkOredict))
							return false;
					}
				}
				
				//if no specified itemstack, check all slot is null
				if (i == 8 && noTempItem)
				{
					return isAllSlotEmpty(inv, tempStacks, modeStacks, checkMetadata, checkNbt, checkOredict);
				}//end temp all null
			}//end loop all temp slots
		}
		
		return true;
	}
	
	/**
	 * return TRUE if all slot is null or NOT mode item
	 */
	public static boolean isAllSlotEmpty(IInventory inv, ItemStack[] targetStacks, boolean[] modeStacks, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		//inventory is ship inv
		if(inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			
			//get any empty slot = false
			for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < shipInv.getSizeInventoryPaged(); i++)
			{
				if (!shipInv.getStackInSlotWithPageCheck(i).isEmpty()) return false;
			}
		}
		//inventory is vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			//check main chest
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (!inv.getStackInSlot(i).isEmpty()) return false;
			}
			
			//check adj chest
			TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) inv);
			
			if (chest2 != null)
			{
				for (int i = 0; i < chest2.getSizeInventory(); i++)
				{
					if (!chest2.getStackInSlot(i).isEmpty()) return false;
				}
			}
		}
		//other inventory
		else
		{
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				if (!inv.getStackInSlot(i).isEmpty()) return false;
			}
		}
		
		return true;
	}
	
	/**
	 * calculate the total "STACK NUMBER" of target item, not item amount!!
	 * 
	 * return TRUE if total stack number = temp getCount()
	 */
	public static int calcItemStackAmount(IInventory inv, ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		int targetAmount = 0;
		
		//inventory is ship inv
		if(inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			
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
	 * check inventory has temp stack, return TRUE if item found
	 */
	public static boolean matchTargetItem(IInventory inv, ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		//inventory is ship inv
		if(inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			
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
		if (temp.isEmpty()) return -1;
		
		//inventory is ship inv
		if (inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			
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
	 * get and remove temp item from inventory, return item
	 */
	public static ItemStack getAndRemoveItem(IInventory inv, ItemStack temp, int number, boolean checkMetadata, boolean checkNbt, boolean checkOredict, int[] exceptSlots)
	{
		if (temp.isEmpty() || number <= 0) return ItemStack.EMPTY;
		if (number > 64) number = 64;
		
		ItemStack getItem = ItemStack.EMPTY;
		ItemStack getTemp;
		boolean searchItem = true;
		int slotid;
		
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
						if (!getItem.isEmpty())
						{
							if (number > 0)
							{
								int minsize = Math.min(getTemp.getCount(), number);
								number -= minsize;
								getTemp.shrink(minsize);
								getItem.grow(minsize);
								
								//clear slot if size <= 0
								if (getTemp.getCount() <= 0)
								{
									adjChest.setInventorySlotContents(slotid, ItemStack.EMPTY);
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
								int minsize = Math.min(getTemp.getCount(), number);
								number -= minsize;
								getTemp.shrink(minsize);
								getItem = getTemp.copy();
								getItem.setCount(minsize);
								
								//clear slot if size <= 0
								if (getTemp.getCount() <= 0)
								{
									adjChest.setInventorySlotContents(slotid, ItemStack.EMPTY);
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
					if (!getItem.isEmpty())
					{
						if (number > 0)
						{
							int minsize = Math.min(getTemp.getCount(), number);
							number -= minsize;
							getTemp.shrink(minsize);
							getItem.grow(minsize);
							
							//clear slot if size <= 0
							if (getTemp.getCount() <= 0)
							{
								inv.setInventorySlotContents(slotid, ItemStack.EMPTY);
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
							int minsize = Math.min(getTemp.getCount(), number);
							number -= minsize;
							getTemp.shrink(minsize);
							getItem = getTemp.copy();
							getItem.grow(minsize);
							
							//clear slot if size <= 0
							if (getTemp.getCount() <= 0)
							{
								inv.setInventorySlotContents(slotid, ItemStack.EMPTY);
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
					if (!getItem.isEmpty())
					{
						if (number > 0)
						{
							int minsize = Math.min(getTemp.getCount(), number);
							number -= minsize;
							getTemp.shrink(minsize);
							getItem.grow(minsize);
							
							//clear slot if size <= 0
							if (getTemp.getCount() <= 0)
							{
								inv.setInventorySlotContents(slotid, ItemStack.EMPTY);
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
							int minsize = Math.min(getTemp.getCount(), number);
							number -= minsize;
							getTemp.shrink(minsize);
							getItem = getTemp.copy();
							getItem.grow(minsize);
							
							//clear slot if size <= 0
							if (getTemp.getCount() <= 0)
							{
								inv.setInventorySlotContents(slotid, ItemStack.EMPTY);
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
	
	/**
	 * check target stack is same with temp stack
	 */
	public static boolean matchTargetItem(ItemStack target, ItemStack temp, boolean checkMetadata, boolean checkNbt, boolean checkOredict)
	{
		if (!temp.isEmpty() && !target.isEmpty())
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
					int[] a = OreDictionary.getOreIDs(target);
					int[] b = OreDictionary.getOreIDs(temp);
					
					if (a.length > 0 && b.length > 0 && a[0] == b[0])
					{
						return true;
					}
				}
			}
		}
		else if (temp.isEmpty() && target.isEmpty())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * check slot is NOT mode, return TRUE = NOT MODE
	 * 
	 * itemMode: right most bits are slots mode: 1 = NOT MODE, 0 = NORMAL MODE
	 *     bits: 17 16 15 ... 3 2 1 0 = (18 slots)
	 */
	public static boolean getItemMode(int slotID, int stackMode)
	{
		return ((stackMode >> slotID) & 1) == 1 ? true : false;
	}
	
	/** set item mode, return new stackMode (INT 32 bits = max 32 slots) */
	public static int setItemMode(int slotID, int stackMode, boolean notMode)
	{
		int slot = 1 << slotID;
		
		//set bit to 1
		if (notMode)
		{
			stackMode = stackMode | slot;
		}
		//set bit to 0
		else
		{
			stackMode = stackMode & (~slot);
		}
		
		return stackMode;
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
		int tarbit;
		int[] slotstemp;
		Set<Integer> slots = new HashSet<>();
		
		//loop all sides
		for (int i = 0; i < 6; i++)
		{
			EnumFacing face = EnumFacing.byIndex(i);  //id: D U N S W E
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
						if (stack.isEmpty() || te.canInsertItem(slotstemp[j], stack, face))
						{
							slots.add(slotstemp[j]);
						}
					}
					//check output
					else
					{
						if (stack.isEmpty() || te.canExtractItem(slotstemp[j], stack, face))
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
		if (!moveitem.isEmpty())
		{
			//invTo is ship inv
			if (inv instanceof CapaShipInventory)
			{
				moved = mergeItemStack(inv, moveitem, toSlots);
			}
			//invTo is vanilla chest
			else if (inv instanceof TileEntityChest)
			{
				TileEntityChest chest = (TileEntityChest) inv;
				TileEntityChest chest2;
				
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
        int k;
        int j;
        int startid = 0;
        int maxSize = inv.getSizeInventory();

        //init slots id
        if (slots == null)
        {
        	if(inv instanceof CapaShipInventory)
            {
            	//start at inv slots
            	startid = ContainerShipInventory.SLOTS_SHIPINV;
            	
            	//get slot size by pages
            	maxSize = ((CapaShipInventory) inv).getSizeInventoryPaged();
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
            while (itemstack.getCount() > 0 && k < maxSize)
            {
            	//calc slot id
            	if (slots != null) j = slots[k];
            	else j = k;
            	
            	//get stack in slot
				if (inv instanceof CapaShipInventory) slotstack = ((CapaShipInventory) inv).getStackInSlotWithPageCheck(j);
				else slotstack = inv.getStackInSlot(j);

                //if same item, merge to slot
                if (!slotstack.isEmpty() && slotstack.getItem() == itemstack.getItem() &&
                	(!itemstack.getHasSubtypes() || itemstack.getItemDamage() == slotstack.getItemDamage()) &&
                   ItemStack.areItemStackTagsEqual(itemstack, slotstack))
                {
                    int l = slotstack.getCount() + itemstack.getCount();

                    //merge: total size < max size
                    if (l <= itemstack.getMaxStackSize())
                    {
                        itemstack.setCount(0);
                        slotstack.setCount(l);
                        movedItem = true;
                    }
                    //merge: move item to slot stack
                    else if (slotstack.getCount() < itemstack.getMaxStackSize())
                    {
                        itemstack.shrink(itemstack.getMaxStackSize() - slotstack.getCount());
                        slotstack.setCount(itemstack.getMaxStackSize());
                        movedItem = true;
                    }
                }

                //next slot
                ++k;
            }//end loop all slots
        }//end is stackable

        //no stack can merge, find empty slot
        if (itemstack.getCount() > 0)
        {
        	k = startid;

        	//loop all slots to find empty slot
            while (k < maxSize)
            {
            	//calc slot id
            	if (slots != null) j = slots[k];
            	else j = k;
            	
            	//get stack in slot
				if (inv instanceof CapaShipInventory) slotstack = ((CapaShipInventory) inv).getStackInSlotWithPageCheck(j);
				else slotstack = inv.getStackInSlot(j);

                //find empty slot
                if (slotstack.isEmpty())
                {
					if (inv instanceof CapaShipInventory) ((CapaShipInventory) inv).setInventorySlotWithPageCheck(j, itemstack.copy());
					else inv.setInventorySlotContents(j, itemstack.copy());
                    itemstack.setCount(0);
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
				if (!stack.isEmpty() && stack.getItem() == ModItems.MarriageRing) return true;
			}
		}
		
		return false;
	}
	
  	/**
  	 * check itemstack in ship inventory simply
  	 */
  	public static boolean checkItemInShipInventory(CapaShipInventory inv, Item item, int meta, int minSlot, int maxSlot)
  	{
  		if (inv != null)
  		{
  			for (int i = minSlot; i < maxSlot; i++)
  			{
  				ItemStack stack = inv.getStackInSlotWithPageCheck(i);
  				
  				if (!stack.isEmpty() && stack.getItem() == item && stack.getItemDamage() == meta)
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
  	public static boolean tryFillContainer(CapaShipInventory inv, FluidStack fs)
  	{
  		if (fs == null) return false;
  		
  		ItemStack stack;
  		int amountMoved = 0;
  		
  		//try fill all container in inventory
  		for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < inv.getSizeInventoryPaged(); i++)
  		{
  			stack = inv.getStackInSlotWithPageCheck(i);
  			
  			//only for container with getCount() = 1
  			if (!stack.isEmpty() && stack.getCount() == 1)
  			{
  				//check item has fluid capa
  				if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
  				{
  					IFluidHandler fluid = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
  					amountMoved += fluid.fill(fs, true);
  				}//end get capa
  				
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
    	if (!moved || moveitem.getCount() > 0)
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
  		EntityItem entityitem = new EntityItem(host.world, host.posX, host.posY, host.posZ, stack);
		entityitem.motionX = host.world.rand.nextGaussian() * 0.08D;
        entityitem.motionY = host.world.rand.nextGaussian() * 0.05D + 0.2D;
        entityitem.motionZ = host.world.rand.nextGaussian() * 0.08D;
        host.world.spawnEntity(entityitem);
  	}
	
	
}