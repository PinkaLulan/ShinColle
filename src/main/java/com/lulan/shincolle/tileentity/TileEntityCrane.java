package com.lulan.shincolle.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

public class TileEntityCrane extends BasicTileInventory implements ITileWaypoint {

	//pos: lastXYZ, nextXYZ, chestXYZ
	public int lx, ly, lz, nx, ny, nz, cx, cy, cz, tick, playerUID, partDelay, itemMode;
	
	//crane
	public boolean isActive, isPaired, checkMetadata, checkOredict, checkNbt, enabLoad, enabUnload;
	/** wait mode:
	 *  0: no wait, no item to trans => stop craning
	 *  1: wait forever until inventory full
	 *  2~5: none
	 *  6~15: wait N-5 min
	 *  16~19: wait 15+(N-16)*5 min
	 *  20~22: wait 40+(N-20)*19 min
	 *  23~25: wait 120+(N-23)*60 min
	 */
	public int craneMode;  //mode: 0:no wait, 1:wait forever, 2~5: NYI, 6~N:wait X-5 min
	
	//target
	public BasicEntityShip ship;
	private IInventory chest;
	
	
	public TileEntityCrane() {
		slots = new ItemStack[18];
		ship = null;
		chest = null;
		isActive = false;
		isPaired = false;
		enabLoad = true;
		enabUnload = true;
		checkMetadata = false;
		checkOredict = false;
		checkNbt = false;
		craneMode = 0;
		playerUID = 0;
		tick = 0;
		partDelay = 0;
		itemMode = 0;
		cx = -1;
		cy = -1;
		cz = -1;
		lx = -1;
		ly = -1;
		lz = -1;
		nx = -1;
		ny = -1;
		nz = -1;
		
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        
        //load slots
        NBTTagList list = nbt.getTagList("Items", 10);
        
        for(int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound item = list.getCompoundTagAt(i);
            byte sid = item.getByte("Slot");
            
            if(sid >= 0 && sid < slots.length) {
            	slots[sid] = ItemStack.loadItemStackFromNBT(item);
            }
        }
        
        //load values
        isActive = nbt.getBoolean("active");
        isPaired = nbt.getBoolean("paired");
        enabLoad = nbt.getBoolean("load");
        enabUnload = nbt.getBoolean("unload");
        checkMetadata = nbt.getBoolean("meta");
        checkOredict = nbt.getBoolean("dict");
        checkNbt = nbt.getBoolean("nbt");
        craneMode = nbt.getInteger("mode");
        playerUID = nbt.getInteger("uid");
        itemMode = nbt.getInteger("imode");
        cx = nbt.getInteger("cx");
        cy = nbt.getInteger("cy");
        cz = nbt.getInteger("cz");
        lx = nbt.getInteger("lx");
        ly = nbt.getInteger("ly");
        lz = nbt.getInteger("lz");
        nx = nbt.getInteger("nx");
        ny = nbt.getInteger("ny");
        nz = nbt.getInteger("nz");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		//save items
		NBTTagList list = new NBTTagList();
		nbt.setTag("Items", list);
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				slots[i].writeToNBT(item);
				list.appendTag(item);
			}
		}
        
		//save values
		nbt.setBoolean("active", isActive);
        nbt.setBoolean("paired", isPaired);
        nbt.setBoolean("load", enabLoad);
        nbt.setBoolean("unload", enabUnload);
        nbt.setBoolean("meta", checkMetadata);
        nbt.setBoolean("dict", checkOredict);
        nbt.setBoolean("nbt", checkNbt);
        nbt.setInteger("mode", craneMode);
        nbt.setInteger("uid", playerUID);
        nbt.setInteger("imode", itemMode);
        nbt.setInteger("cx", cx);
        nbt.setInteger("cy", cy);
        nbt.setInteger("cz", cz);
        nbt.setInteger("lx", lx);
        nbt.setInteger("ly", ly);
        nbt.setInteger("lz", lz);
        nbt.setInteger("nx", nx);
        nbt.setInteger("ny", ny);
        nbt.setInteger("nz", nz);
	}
	
	//set paired chest
	public void setPairedChest(int x, int y, int z) {
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		
		if(tile instanceof IInventory) {
			this.cx = x;
			this.cy = y;
			this.cz = z;
			this.isPaired = true;
			this.chest = (IInventory) tile;
		}
		else {
			clearPairedChest();
		}
	}
	
	//get paired chest
	public void checkPairedChest() {
		if(isPaired) {
			//get chest if no chest tile entity
			if(chest == null) {
				TileEntity tile = worldObj.getTileEntity(cx, cy, cz);
				
				if(tile instanceof IInventory) {
					this.chest = (IInventory) tile;
				}
			}
			
			//check chest valid
			if(chest instanceof IInventory && !((TileEntity)chest).isInvalid()) {
				return;
			}
			
			//tile lost, reset
			clearPairedChest();
			sendSyncPacket();
		}
	}
	
	public void clearPairedChest() {
		chest = null;
		isPaired = false;
		cx = -1;
		cy = -1;
		cz = -1;
	}
	
	//set data from packet data
	public void setSyncData(int[] data) {
		if(data != null) {
			this.lx = data[0];
			this.ly = data[1];
			this.lz = data[2];
			this.nx = data[3];
			this.ny = data[4];
			this.nz = data[5];
			
			setPairedChest(data[6], data[7], data[8]);
		}
	}
	
	@Override
	public void setNextWaypoint(int[] next) {
		if(next != null) {
			this.nx = next[0];
			this.ny = next[1];
			this.nz = next[2];
		}
	}

	@Override
	public int[] getNextWaypoint() {
		return new int[] {nx, ny, nz};
	}

	@Override
	public void setLastWaypoint(int[] next) {
		if(next != null) {
			this.lx = next[0];
			this.ly = next[1];
			this.lz = next[2];
		}
	}

	@Override
	public int[] getLastWaypoint() {
		return new int[] {lx, ly, lz};
	}
	
	@Override
	public void updateEntity() {
		//server side
		if(!worldObj.isRemote) {
			boolean update = false;
			tick++;
			
			//can work
			if(isActive && isPaired) {
				//check every 16 ticks
				if(tick > 64 && tick % 16 == 0) {
					//check chest and ship
					checkPairedChest();
					checkCraningShip();
					
					if(chest != null && ship != null) {
						boolean movedLoad = false;
						boolean movedUnload = false;
						boolean endCraning = false;
						int waitTime = getWaitTimeInMin(craneMode) * 1200;
						
						try {
							//check item for loading
							if(enabLoad) {
								movedLoad = applyItemTransfer(true);
							}
							
							//check item for unloading
							if(enabUnload) {
								movedUnload = applyItemTransfer(false);
							}
							
							//check craning ending
							switch(craneMode) {
							case 0:  //no wait
								//no load and no unload, end craning
								if(!movedLoad && !movedUnload) {
									endCraning = true;
								}
								break;
							case 1:  //wait forever
								if(checkWaitForever()) {
									endCraning = true;
								}
								break;
							default: //wait X min
								int t = ship.getStateTimer(ID.T.CraneTime);
								
								if(t > waitTime) {
									endCraning = true;
								}
								break;
							}
							
							//craning end
							if(endCraning) {
								//set crane state
								ship.setStateMinor(ID.M.CraneState, 0);
								ship.setStateTimer(ID.T.CraneTime, 0);
								
								//set next waypoint
			  	  				if(EntityHelper.applyNextWaypoint(this, ship, false, 0))
			  	  				{
			  	  					//set follow dist
			  	  					ship.setStateMinor(ID.M.FollowMin, 2);
			  	  				}
			  	  				
			  	  				//player sound
			  	  				this.ship.playSound(Reference.MOD_ID+":ship-bell", ConfigHandler.volumeShip * 1.5F, this.ship.getRNG().nextFloat() * 0.3F + 1F);
			  	  				
			  	  				//clear ship
			  	  				ship = null;
							}
						}
						catch(Exception e) {
							LogHelper.info("EXCEPTION : ship loading/unloading fail: "+e);
							e.printStackTrace();
							return;
						}
					}
				}//end 16 ticks
			}//is active
			
			if(tick > 510) {
				tick = 0;
				
				if(ly > 0 || ny > 0 || cy > 0) {
					update = true;
				}
			}
			
			//need update
			if(update) {
				sendSyncPacket();
			}
		}//end server side
		//client side
		else {
			tick++;
			if(partDelay > 0) partDelay--;
			
			//craning particle
			if(this.isActive && this.ship != null && partDelay <= 0) {
				partDelay = 128;
				
				double len = this.yCoord - this.ship.posY - 1D;
				if(len < 1D) len = 1D;
				
				ParticleHelper.spawnAttackParticleAt(xCoord+0.5D, yCoord-1D, zCoord+0.5D, len, 0D, 0.25D, (byte) 40);
			}
				
			//check every 16 ticks
			if(this.tick % 16 == 0) {
				//player hold waypoint or target wrench
				EntityPlayer player = ClientProxy.getClientPlayer();
				ItemStack item = player.inventory.getCurrentItem();
				
				//if holding pointer, wrench, waypoint
				if(item != null && (item.getItem() instanceof ItemBlockWaypoint || item.getItem() == ModItems.TargetWrench ||
				   (item.getItem() instanceof PointerItem && item.getItemDamage() < 3))) {
					
					//next point mark
					if(this.ny > 0) {
						double dx = nx - this.xCoord;
						double dy = ny - this.yCoord;
						double dz = nz - this.zCoord;
						dx *= 0.01D;
						dy *= 0.01D;
						dz *= 0.01D;
								
						ParticleHelper.spawnAttackParticleAt(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, dx, dy, dz, (byte) 38);
					}
					
					//paired chest mark
					if(this.cy > 0) {
						double dx = cx - this.xCoord;
						double dy = cy - this.yCoord;
						double dz = cz - this.zCoord;
						dx *= 0.01D;
						dy *= 0.01D;
						dz *= 0.01D;

						ParticleHelper.spawnAttackParticleAt(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, dx, dy, dz, (byte) 39);
					}
				}//end holding item
			}//end every 16 ticks
		}//end client side
	}
	
	/** check wait forever ending
	 * 
	 *  1. loading: wait until ship's inventory full
	 *  2. unloading: wait until no specified item can unload
	 */
	private boolean checkWaitForever()
	{
		boolean doneLoad = true;
		boolean doneUnload = true;
		boolean allNull = true;
		int i;
		
		if (ship != null)
		{
			//check loading condition: ship's inventory is full
			if (enabLoad)
			{
				doneLoad = checkInventoryFull(ship.getExtProps());
			}
			
			//check unloading condition: ship have no specified item
			if (enabUnload)
			{
				for (i = 0; i < 9; i++)
				{
					ItemStack temp = getItemstackTemp(i, false);
					
					if (temp != null)
					{
						allNull = false;
						
						if (!this.getItemMode(i + 9))
						{
							//check items in ship inventory
							ExtendShipProps inv = ship.getExtProps();
							int slotid = matchTempItem(inv, temp);
							
							//get item
							if(slotid > 0) {
								doneUnload = false;
								break;
							}
						}
					}
					
					//if all temp slot are null = get any item except NotMode item
					if (i == 8 && allNull)
					{
						//check items in ship inventory
						ExtendShipProps inv = ship.getExtProps();
						int slotid = matchAnyItemExceptNotModeItem(inv, false);
						
						//get item
						if (slotid > 0)
						{
							doneUnload = false;
						}
					}//end temp all null
				}//end loop all temp slots
			}//end enable unload
		}//end get ship
		
		return doneLoad && doneUnload;
	}
	
	//check inventory is full
	private boolean checkInventoryFull(IInventory inv) {
		ItemStack item = null;
		int i = 0;
		
		//check inv type
		if(inv instanceof ExtendShipProps) {
			((ExtendShipProps) inv).setInventoryPage(0);
			
			//get any empty slot = false
			for(i = ContainerShipInventory.SLOTS_SHIPINV; i < ((ExtendShipProps)inv).getSizeInventoryPaged(); i++) {
				if(inv.getStackInSlot(i) == null) {
					return false;
				}
			}
		}
		//invTo is vanilla chest
		else if(inv instanceof TileEntityChest) {
			//check main chest
			for(i = 0; i < inv.getSizeInventory(); i++) {
				if(inv.getStackInSlot(i) == null) {
					return false;
				}
			}
			
			//check adj chest
			TileEntityChest chest2 = getAdjChest((TileEntityChest) inv);
			
			if(chest2 != null) {
				for(i = 0; i < chest2.getSizeInventory(); i++) {
					if(chest2.getStackInSlot(i) == null) {
						return false;
					}
				}
			}
		}
		else {
			for(i = 0; i < inv.getSizeInventory(); i++) {
				if(inv.getStackInSlot(i) == null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/** item loading / unloading
	 * 
	 *  return true = item moved; false = no item can move
	 */
	private boolean applyItemTransfer(boolean isLoading)
	{
		IInventory invFrom = null;
		IInventory invTo = null;
		ItemStack tempitem = null;
		ItemStack moveitem = null;
		boolean allNull = true;  //all temp slots are null = move all items
		boolean moved = false;
		int i, j;
		int slotid = -1;
		
		/** get target slot */
		//check temp slots
		for (i = 0; i < 9; i++)
		{
			//set inv
			if (isLoading)
			{
				invFrom = chest;
				invTo = ship.getExtProps();
			}
			else
			{
				invTo = chest;
				invFrom = ship.getExtProps();
			}
			
			//get load item type
			tempitem = getItemstackTemp(i, isLoading);
			
			//temp != null
			if (tempitem != null)
			{
				allNull = false;
				
				//check target item exist in invFrom
				slotid = matchTempItem(invFrom, tempitem);
				
				//check target item in adj chest if no item in main chest
				if (slotid < 0 && invFrom instanceof TileEntityChest)
				{
					TileEntityChest chest2 = getAdjChest((TileEntityChest) invFrom);
					
					if (chest2 != null)
					{
						invFrom = chest2;
						slotid = matchTempItem(invFrom, tempitem);
					}
				}
				
				/** move target item */
				if (slotid >= 0)
				{
					//move item
					moveitem = invFrom.getStackInSlot(slotid);
					moved = moveItemstackToInv(invTo, moveitem);
					
					//check item size
					if (moved && moveitem.stackSize <= 0)
					{
						invFrom.setInventorySlotContents(slotid, null);
					}
					
					//end moving item (1 itemstack per method call)
					if (moved) break;
				}
			}//end temp != null
			else
			{
				//all slots are null
				if (i == 8 && allNull)
				{
					slotid = matchAnyItemExceptNotModeItem(invFrom, isLoading);
					
					//check target item in adj chest if no item in main chest
					if (slotid < 0 && invFrom instanceof TileEntityChest)
					{
						TileEntityChest chest2 = getAdjChest((TileEntityChest) invFrom);
						
						if (chest2 != null)
						{
							invFrom = chest2;
							slotid = matchAnyItemExceptNotModeItem(invFrom, isLoading);
						}
					}
					
					/** move target item */
					if (slotid >= 0)
					{
						//move item
						moveitem = invFrom.getStackInSlot(slotid);
						moved = moveItemstackToInv(invTo, moveitem);
						
						//check item size
						if (moved && moveitem.stackSize <= 0)
						{
							invFrom.setInventorySlotContents(slotid, null);
						}
					}
				}
			}//end temp is null
		}//end all temp slots
		
		return moved;
	}
	
	//move itemstack to inv with inv type checking, return true = item moved
	private boolean moveItemstackToInv(IInventory inv, ItemStack moveitem) {
		boolean moved = false;
		
		//move item to inv
		if(moveitem != null) {
			
			//invTo is ship inv
			if(inv instanceof ExtendShipProps) {
				moved = mergeItemStack(inv, moveitem);
			}
			//invTo is vanilla chest
			else if(inv instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest) inv;
				TileEntityChest chest2 = null;
				
				//move to main chest
				moved = mergeItemStack(chest, moveitem);
				
				//move fail, check adj chest
				if(!moved) {
					//get adj chest
					chest2 = getAdjChest(chest);
					
					//move to adj chest
					if(chest2 != null) moved = mergeItemStack(chest2, moveitem);
				}//end move to adj chest
			}
			//other normal inv
			else {
				moved = mergeItemStack(inv, moveitem);
			}
			
		}//end move item
		
		return moved;
	}
	
	//get adj chest for TileEntityChest
	private TileEntityChest getAdjChest(TileEntityChest chest) {
		TileEntityChest chest2 = null;
		
		if(chest != null && !chest.isInvalid()) {
			//check adj chest valid
			chest.checkForAdjacentChests();
			
			//get adj chest
			chest2 = chest.adjacentChestXNeg;
			if(chest2 == null) {
				chest2 = chest.adjacentChestXPos;
				if(chest2 == null) {
					chest2 = chest.adjacentChestZNeg;
					if(chest2 == null) chest2 = chest.adjacentChestZPos;
				}
			}
		}
		
		if(chest2 != null && chest2.isInvalid()) return null;
		
		return chest2;
	}
	
	//get itemstack temp from loading or unloading slots
	private ItemStack getItemstackTemp(int i, boolean isLoadingTemp)
	{
		//check slot is notMode
		if (this.getItemMode(isLoadingTemp ? i : i + 9))
		{
			return null;
		}
		
		//get loading temp
		if (isLoadingTemp)
		{
			return slots[i];
		}
		//get unloading temp
		else
		{
			return slots[i+9];
		}
	}
	
	//merge itemstack to slot
	private boolean mergeItemStack(IInventory inv, ItemStack itemstack) {
		ItemStack slotstack;
		boolean movedItem = false;
        int k = 0;
        int startid = 0;
        int maxSize = inv.getSizeInventory();

        //init slots for ship inventory
        if(inv instanceof ExtendShipProps) {
        	//set access page to 0
        	((ExtendShipProps) inv).setInventoryPage(0);
        	
        	//start at inv slots
        	startid = ContainerShipInventory.SLOTS_SHIPINV;
        	
        	//get slot size by pages
        	maxSize = ((ExtendShipProps) inv).getSizeInventoryPaged();
        }

        //is stackable item
        if(itemstack.isStackable()) {
        	k = startid;
        	
        	//loop all slots until stacksize = 0
            while(itemstack.stackSize > 0 && k < maxSize) {
            	slotstack = inv.getStackInSlot(k);

                //is same item, merge to slot
                if(slotstack != null && slotstack.getItem() == itemstack.getItem() &&
                   (!itemstack.getHasSubtypes() || itemstack.getItemDamage() == slotstack.getItemDamage()) &&
                   ItemStack.areItemStackTagsEqual(itemstack, slotstack)) {
                	
                    int l = slotstack.stackSize + itemstack.stackSize;

                    //merge: total size < max size
                    if(l <= itemstack.getMaxStackSize()) {
                        itemstack.stackSize = 0;
                        slotstack.stackSize = l;
                        movedItem = true;
                    }
                    //merge: move item to slot stack
                    else if(slotstack.stackSize < itemstack.getMaxStackSize()) {
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
        if(itemstack.stackSize > 0) {
        	k = startid;

        	//loop all slots to find empty slot
            while(k < maxSize) {
                slotstack = inv.getStackInSlot(k);

                //find empty slot
                if(slotstack == null) {
                	inv.setInventorySlotContents(k, itemstack.copy());
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
	
	//get slot id in inventory that match target item
	private int matchTempItem(IInventory inv, ItemStack target)
	{
		ItemStack getitem = null;
		int slotid = 0;
		int startid = 0;
		int maxSize = inv.getSizeInventory();
		
		//init slots for ship inventory
        if (inv instanceof ExtendShipProps)
        {
        	//set access page to 0
        	((ExtendShipProps) inv).setInventoryPage(0);
        	
        	//start at inv slots
        	startid = ContainerShipInventory.SLOTS_SHIPINV;
        	
        	//get max size by page
        	maxSize = ((ExtendShipProps) inv).getSizeInventoryPaged();
        }
		
		//match taget item
		if (target != null)
		{
			//check slots
			for (slotid = startid; slotid < maxSize; slotid++)
			{
				getitem = inv.getStackInSlot(slotid);
				
				if (getitem != null)
				{
					//check item type
					if (getitem.getItem() == target.getItem())
					{
						//check both nbt and meta
						if (checkNbt && checkMetadata)
						{
							if (ItemStack.areItemStackTagsEqual(getitem, target) &&
								getitem.getItemDamage() == target.getItemDamage())
							{
								return slotid;
							}
						}
						//check nbt only
						else if (checkNbt)
						{
							if (ItemStack.areItemStackTagsEqual(getitem, target)) return slotid;
						}
						//check meta only
						else if (checkMetadata)
						{
							if(getitem.getItemDamage() == target.getItemDamage()) return slotid;
						}
						//dont check nbt and meta
						else
						{
							return slotid;
						}
					}
					else
					{
						//check ore dict
						if (checkOredict)
						{
							int[] a = OreDictionary.getOreIDs(target);
							int[] b = OreDictionary.getOreIDs(getitem);
							
							if(a.length > 0 && b.length > 0 && a[0] == b[0]) {
								return slotid;
							}
						}
					}
				}//end get chest item
			}//for all slots in chest
		}
		//temp null, get any item
		else
		{
			for (slotid = startid; slotid < maxSize; slotid++)
			{
				getitem = inv.getStackInSlot(slotid);
				
				if (getitem != null)
				{
					return slotid;
				}
			}
		}
		
		return -1;
	}
	
	//get any item except NotMode item
	private int matchAnyItemExceptNotModeItem(IInventory inv, boolean isLoading)
	{
		ItemStack getitem = null;
		int slotid = 0;
		int startid = 0;
		int maxSize = inv.getSizeInventory();
		
		//init slots for ship inventory
        if (inv instanceof ExtendShipProps)
        {
        	//set access page to 0
        	((ExtendShipProps) inv).setInventoryPage(0);
        	
        	//start at inv slots
        	startid = ContainerShipInventory.SLOTS_SHIPINV;
        	
        	//get max size by page
        	maxSize = ((ExtendShipProps) inv).getSizeInventoryPaged();
        }
        
		for (slotid = startid; slotid < maxSize; slotid++)
		{
			getitem = inv.getStackInSlot(slotid);
			
			if (getitem != null)
			{
				if (checkNotModeItem(slotid, getitem, isLoading) >= 0)
				{
					return slotid;
				}
			}
		}

		return -1;
	}	
	//if item is in NotMode slot, return -1
	private int checkNotModeItem(int slotid, ItemStack item, boolean isLoading)
	{
		ItemStack temp = null;
		
		for (int i = 0; i < 9; i++)
		{
			if (getItemMode(isLoading ? i : i + 9))
			{
				temp = slots[isLoading ? i : i + 9];
				
				if (temp != null)
				{
					//check item type
					if (item.getItem() == temp.getItem())
					{
						//check both nbt and meta
						if (checkNbt && checkMetadata)
						{
							if (ItemStack.areItemStackTagsEqual(item, temp) &&
								item.getItemDamage() == temp.getItemDamage())
							{
								return -1;
							}
						}
						//check nbt only
						else if (checkNbt)
						{
							if (ItemStack.areItemStackTagsEqual(item, temp)) return -1;
						}
						//check meta only
						else if (checkMetadata)
						{
							if (item.getItemDamage() == temp.getItemDamage()) return -1;
						}
						//dont check nbt and meta
						else
						{
							return -1;
						}
					}
					else
					{
						//check ore dict
						if (checkOredict)
						{
							int[] a = OreDictionary.getOreIDs(item);
							int[] b = OreDictionary.getOreIDs(temp);
							
							if (a.length > 0 && b.length > 0 && a[0] == b[0])
							{
								return -1;
							}
						}
					}
				}
			}
		}
		
		//pass checking, return slot id
		return slotid;
	}
	
	//check ship under crane waiting for craning
	private void checkCraningShip() {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord - 7D, yCoord - 6D, zCoord - 7D, xCoord + 7D, yCoord + 6D, zCoord + 7D);
        List<BasicEntityShip> slist = this.worldObj.getEntitiesWithinAABB(BasicEntityShip.class, box);

        if (slist != null && !slist.isEmpty())
        {
        	//get craning ship
        	for (BasicEntityShip s : slist)
        	{
        		if (s.getStateMinor(ID.M.CraneState) == 2 &&
        		   s.getGuardedPos(0) == xCoord &&
        		   s.getGuardedPos(1) == yCoord &&
        		   s.getGuardedPos(2) == zCoord)
        		{
        			this.ship = s;
        			this.ship.getExtProps().setInventoryPage(0);  //set show page to 0
        			this.ship.getShipNavigate().tryMoveToXYZ(xCoord+0.5D, yCoord-2D, zCoord+0.5D, 0.5D);
        			this.sendSyncPacket();
        			return;
        		}
        	}
        	
        	//no craning ship, get waiting ship
        	for (BasicEntityShip s : slist)
        	{
        		if(s.getStateMinor(ID.M.CraneState) == 1 &&
         		   s.getGuardedPos(0) == xCoord &&
         		   s.getGuardedPos(1) == yCoord &&
         		   s.getGuardedPos(2) == zCoord)
        		{
        			//set ship is craning
         			this.ship = s;
         			this.ship.setStateMinor(ID.M.CraneState, 2);
         			this.ship.getExtProps().setInventoryPage(0);  //set show page to 0
         			this.ship.getShipNavigate().tryMoveToXYZ(xCoord+0.5D, yCoord-2D, zCoord+0.5D, 0.5D);
         			this.sendSyncPacket();
         			return;
         		}
        	}
        }
        else
        {
        	this.ship = null;
        	this.sendSyncPacket();
        }
	}

	@Override
	public int getFuelSlotMin() {
		return -1;
	}

	@Override
	public int getFuelSlotMax() {
		return -1;
	}
	
	@Override
  	public ItemStack getStackInSlot(int i) {
  		return this.slots[i];
  	}
	
	@Override
  	public ItemStack decrStackSize(int i, int j) {
		return null;
	}
	
	@Override
  	public void setInventorySlotContents(int i, ItemStack itemstack) {
  		slots[i] = itemstack;
  		
  		if(itemstack != null) {
  			itemstack.stackSize = 1;
  		}	
  	}
	
	//每格可放的最大數量上限
  	@Override
  	public int getInventoryStackLimit() {
  		return 0;
  	}
  	
  	//使用管線/漏斗輸入時呼叫, 不適用於手動置入
  	@Override
  	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
  		return false;
  	}
  	
  	//get waiting time (min)
  	public static int getWaitTimeInMin(int mode) {
  		if(mode >= 6 && mode <= 15) {
			return mode - 5;
		}
		else if(mode >= 16 && mode <= 19) {
			return (mode - 16) * 5 + 15;
		}
		else if(mode >= 20 && mode <= 22) {
			return (mode - 20) * 10 + 40;
		}
		else if(mode >= 23 && mode <= 25) {
			return (mode - 23) * 60 + 120;
		}
		else {
			return 0;
		}
  	}

	@Override
	public void setWpStayTime(int time) {}

	@Override
	public int getWpStayTime()
	{
		return 0;
	}
	
	//set slot is NOT loading/unloading mode
	public void setItemMode(int slotID, boolean notMode)
	{
		int slot = 1 << (slotID - 1);
		
		//set bit 1
		if (notMode)
		{
			itemMode = itemMode | slot;
		}
		//set bit 0
		else
		{
			itemMode = itemMode & ~slot;
		}
	}
	
	//check slot is notMode
	public boolean getItemMode(int slotID)
	{
		return ((itemMode >> (slotID - 1)) & 1) == 1 ? true : false;
	}
	
	
	
	
}
