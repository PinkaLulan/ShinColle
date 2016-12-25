package com.lulan.shincolle.capability;

import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;



/** inventory capability for ships
 *
 */
public class CapaShipInventory extends CapaInventory<BasicEntityShip> implements IInventory
{

	public static final int SlotPages = 3;
	public static final int SlotMax = 6 + 18 * 3;   	//6 equip + 18 inv * 3 page
	private int inventoryPage = 0;						//current inventory page id
	
	
	public CapaShipInventory(int size, BasicEntityShip host)
	{
		super(size, host);
		
	}
	
//    @Override
//    public void deserializeNBT(NBTTagCompound nbt)
//    {
//    	super.deserializeNBT(nbt);
//    	
//    	//load 1.7.10 old inventory to new capability inventory
//    	if (nbt.hasKey(ShipInvName, Constants.NBT.TAG_COMPOUND))
//    	{
//    		NBTTagList list = nbt.getTagList(ShipInvName, Constants.NBT.TAG_COMPOUND);
//
//    		for (int i = 0; i < list.tagCount(); i++)
//    		{
//    			NBTTagCompound item = list.getCompoundTagAt(i);
//    			byte sid = item.getByte("Slot");
//
//    			if (sid >= 0 && sid < this.getSlots())
//    			{
//    				stacks[sid] = ItemStack.loadItemStackFromNBT(item);
//    			}
//    		}
//    	}
//    }
	
	public int getInventoryPage()
	{
		return this.inventoryPage;
	}
	
	public void setInventoryPage(int par1)
	{
		if (par1 >= 0 && par1 < 3)
		{
			this.inventoryPage = par1;
		}
		else
		{
			this.inventoryPage = 0;
		}
		
		this.hostObj.sendGUISyncPacket();
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public int getSizeInventory()
	{
		return ContainerShipInventory.SLOTS_PLAYERINV;
	}
	
	//inventory size including all enabled pages
	public int getSizeInventoryPaged()
	{
		if (hostObj != null)
		{
			return ContainerShipInventory.SLOTS_PLAYERINV + hostObj.getStateMinor(ID.M.InvSize) * 18;
		}
		
		return ContainerShipInventory.SLOTS_PLAYERINV;
	}
	
	@Override
	public ItemStack getStackInSlot(int i)
	{
		validateSlotIndex(i);
		
		//access within page
		if (i < getSizeInventory())
		{
			//get equip slot
			if (i < ContainerShipInventory.SLOTS_SHIPINV)
			{
				return this.stacks[i];
			}
			//get inventory slot
			else
			{
				return stacks[i + this.inventoryPage * 18];
			}
		}
		//access across pages
		else
		{
			return stacks[i];
		}
	}
	
	public ItemStack getStackInSlotWithoutPaging(int i)
	{
		validateSlotIndex(i);
		return stacks[i];
	}

	/** note:
	 *  這裡id是已經有page轉換過, 最大值為this.stacks.length
	 */
	@Override
	public ItemStack decrStackSize(int id, int count)
	{
  		try
		{
  			if (id >= 0 && id < getSlots() && getStackInSlot(id) != null && count > 0)
  	        {
  	            ItemStack itemstack = getStackInSlot(id).splitStack(count);

  	            if (getStackInSlot(id).stackSize == 0)
  	            {
  	            	setStackInSlot(id, null);
  	            }

  	            return itemstack;
  	        }
  	        else
  	        {
  	            return null;
  	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	//NO USE: 用於GUI關閉時移除特定slot中的物品, 使其掉落出來, 目前沒有需要此方法
	@Override
	public ItemStack removeStackFromSlot(int id)
	{
		return null;
	}

	/** note:
	 *  這裡的id是page轉換前, 範圍最大到24 (6裝備格+一頁18格)
	 *  若id傳入大小超過24格, 則視為對全部page存取, 此時範圍最大到this.stacks.length
	 */
	@Override
	public void setInventorySlotContents(int id, ItemStack stack)
	{
		//access within page
		if (id < getSizeInventory())
		{
			//equip slot
			if (id < ContainerShipInventory.SLOTS_SHIPINV)
			{
				setStackInSlot(id, stack);
			}
			//inv slot
			else
			{
				setStackInSlot(id + this.inventoryPage * 18, stack);
			}
		}
		//access across pages
		else
		{
			setStackInSlot(id, stack);
		}
		
		//若手上物品超過該格子限制數量, 則只能放進限制數量
		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
		
		//change equip slot
		if (!this.hostObj.world.isRemote && id < 6)
		{
			this.hostObj.calcEquipAndUpdateState();  //update equip and attribute value
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public void clear()
	{
	}
	
	/** check slot id with inventory page */
	public boolean isSlotAvailable(int slotid)
	{
		//has 0~1 page
		if (this.hostObj.getInventoryPageSize() < 2)
		{
			if (slotid >= 42)
			{
				return false;
			}
			//has 0 page
			else if (this.hostObj.getInventoryPageSize() < 1)
			{
				if (slotid >= 24)
				{
  					return false;
  				}
  			}
		}//end page check
		
		return true;
	}
	
	/** get first empty slots */
  	public int getFirstSlotForItem()
  	{
  		for (int i = 6; i < this.getSlots(); i++)
  		{
  			//stop loop if no available slot
  			if (!isSlotAvailable(i)) return -1;
  			
  			//get empty slot
  			if (getStackInSlot(i) == null) return i;
  		}
  		
  		return -1;
  	}
  	
  	//get first item with same metadata and has space for stack
    private int getFirstSlotStackable(ItemStack stack)
    {
        for (int i = 6; i < this.getSlots(); ++i)
        {
        	//stop loop if no available slot
  			if (!isSlotAvailable(i)) return -1;
        	
  			//check same item, same meta, same nbt tag and has stack space
  			ItemStack slotstack = getStackInSlot(i);
  			
            if (slotstack != null && slotstack.getItem() == stack.getItem() &&
            	slotstack.isStackable() && slotstack.stackSize < slotstack.getMaxStackSize() &&
            	slotstack.stackSize < this.getInventoryStackLimit() &&
                (!slotstack.getHasSubtypes() || slotstack.getItemDamage() == stack.getItemDamage()) &&
                 ItemStack.areItemStackTagsEqual(slotstack, stack))
            {
                return i;
            }
        }

        return -1;
    }
  	
  	/** vanilla method
     *  This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     *  leftover items.
     */
    private int storePartialItemStack(ItemStack stack)
    {
        Item item = stack.getItem();
        int i = stack.stackSize;
        int j;

        //non stackable item
        if (stack.getMaxStackSize() == 1)
        {
        	//check empty slot
            j = getFirstSlotForItem();

            //no space
            if (j < 0)
            {
                return i;
            }
            else
            {
            	setStackInSlot(j, ItemStack.copyItemStack(stack));
                return 0;
            }
        }
        //stackable item
        else
        {
        	//check exists stack
            j = getFirstSlotStackable(stack);

            //no same stack, check empty slot
            if (j < 0)
            {
                j = getFirstSlotForItem();
            }

            //no empty slot, return
            if (j < 0)
            {
                return i;
            }
            //get empty slot
            else
            {
            	//add item to slot
            	ItemStack slotstack = getStackInSlot(j);
            	
                if (slotstack == null)
                {
                	ItemStack copyitem = new ItemStack(item, 0, stack.getItemDamage());
                	
                    if (stack.hasTagCompound())
                    {
                    	copyitem.setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                    }
                    
                	setStackInSlot(j, copyitem);
                }

                //calc leftover stack size
                int k = i;

                //check the item max stack size
                slotstack = getStackInSlot(j);
                
                if (i > slotstack.getMaxStackSize() - slotstack.stackSize)
                {
                    k = slotstack.getMaxStackSize() - slotstack.stackSize;
                }

                //check the slot max stack size
                if (k > this.getInventoryStackLimit() - slotstack.stackSize)
                {
                    k = this.getInventoryStackLimit() - slotstack.stackSize;
                }

                //no space for item
                if (k == 0)
                {
                    return i;
                }
                //get space for item
                else
                {
                    i -= k;
                    slotstack.stackSize += k;
                    slotstack.animationsToGo = 5;
                    return i;
                }
            }
        }
    }
  	
  	/** add itemstack to ship inventory */
  	public boolean addItemStackToInventory(final ItemStack stack)
  	{
        if (stack != null && stack.stackSize != 0 && stack.getItem() != null)
        {
            try
            {
                int i;

                //item with meta != 0
                if (stack.isItemDamaged())
                {
                    i = this.getFirstSlotForItem();

                    //add item to slot
                    if (i >= 0)
                    {
                    	ItemStack copyitem = ItemStack.copyItemStack(stack);
                    	copyitem.animationsToGo = 5;
                    	setStackInSlot(i, copyitem);
                        stack.stackSize = 0;
                        return true;
                    }
                    //add fail
                    else
                    {
                        return false;
                    }
                }
                //item without meta value
                else
                {
                	//add item to slot with stackable check
                    do
                    {
                        i = stack.stackSize;
                        stack.stackSize = this.storePartialItemStack(stack);
                    }
                    while (stack.stackSize > 0 && stack.stackSize < i);
                    
                    return stack.stackSize < i;
                }
            }
            catch(Exception e)
            {
            	LogHelper.info("EXCEPTION : add item to ship's inventory fail: "+e+" "+stack);
            	return false;
            }
        }
        else
        {
            return false;
        }
    }
    
  	/**
  	 * get/setField為GUI container更新用
  	 * 使資料可以只用相同方法取值, 不用每個資料用各自方法取值
  	 * 方便for loop撰寫
  	 * 
  	 */
	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	
}
