package com.lulan.shincolle.tileentity;

import java.util.List;

import com.lulan.shincolle.block.BlockCrane;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

/**
 *  redMode:
 *    redstone mode: 0:no signal, 1:emit one pulse on ending, 2:NYI
 *
 */
public class TileEntityCrane extends BasicTileInventory implements ITileWaypoint, ITickable
{

	//pos: lastXYZ, nextXYZ, chestXYZ
	private int tick, playerUID, partDelay, itemMode, redMode, redTick;
	private BlockPos lastPos, nextPos, chestPos;
	
	//crane
	private boolean isActive, isPaired, checkMetadata, checkOredict, checkNbt, enabLoad, enabUnload;
	/** wait mode:
	 *  0: no wait, no item to trans => stop craning
	 *  1: wait forever until inventory full
	 *  2~5: none
	 *  6~15: wait N-5 min
	 *  16~19: wait 15+(N-16)*5 min
	 *  20~22: wait 40+(N-20)*19 min
	 *  23~25: wait 120+(N-23)*60 min
	 */
	private int craneMode;  //mode: 0:no wait, 1:wait forever, 2~5: NYI, 6~N:wait X-5 min
	private static final int[] NOSLOT = new int[] {}; 
	
	//target
	private BasicEntityShip ship;
	private IInventory chest;
	
	
	public TileEntityCrane()
	{
		super();
		
		//0~8: loading items, 9~17: unloading items
		this.itemHandler = new CapaInventory(18, this);
		this.ship = null;
		this.chest = null;
		this.isActive = false;
		this.isPaired = false;
		this.enabLoad = true;
		this.enabUnload = true;
		this.checkMetadata = false;
		this.checkOredict = false;
		this.checkNbt = false;
		this.craneMode = 0;
		this.playerUID = 0;
		this.tick = 0;
		this.partDelay = 0;
		this.itemMode = 0;
		this.redMode = 0;
		this.redTick = 0;
		this.lastPos = BlockPos.ORIGIN;
		this.nextPos = BlockPos.ORIGIN;
		this.chestPos = BlockPos.ORIGIN;
	}
	
	@Override
	public String getRegName()
	{
		return BlockCrane.TILENAME;
	}
	
	@Override
	public int getGuiIntID()
	{
		return ID.Gui.CRANE;
	}
	
	//依照輸出入口設定, 決定漏斗等裝置如何輸出入物品到特定slot中
	//注意: 此設定必須跟getCapability相同以免出現bug
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return NOSLOT;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);
        
        //load values
        this.isActive = nbt.getBoolean("active");
        this.isPaired = nbt.getBoolean("paired");
        this.enabLoad = nbt.getBoolean("load");
        this.enabUnload = nbt.getBoolean("unload");
        this.checkMetadata = nbt.getBoolean("meta");
        this.checkOredict = nbt.getBoolean("dict");
        this.checkNbt = nbt.getBoolean("nbt");
        this.craneMode = nbt.getInteger("mode");
        this.playerUID = nbt.getInteger("uid");
        this.itemMode = nbt.getInteger("imode");
        this.redMode = nbt.getInteger("rmode");
        
        //load pos
        try
        {
            int[] pos =  nbt.getIntArray("chestPos");
            this.chestPos = new BlockPos(pos[0], pos[1], pos[2]);
            
            pos =  nbt.getIntArray("lastPos");
            this.lastPos = new BlockPos(pos[0], pos[1], pos[2]);
            
            pos =  nbt.getIntArray("nextPos");
            this.nextPos = new BlockPos(pos[0], pos[1], pos[2]);
        }
        catch (Exception e)
        {
        	LogHelper.info("EXCEPTION: TileEntityCrane load position fail: "+e);
        	this.chestPos = BlockPos.ORIGIN;
        	this.lastPos = BlockPos.ORIGIN;
        	this.nextPos = BlockPos.ORIGIN;
        }
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
        
		//save values
		nbt.setBoolean("active", this.isActive);
        nbt.setBoolean("paired", this.isPaired);
        nbt.setBoolean("load", this.enabLoad);
        nbt.setBoolean("unload", this.enabUnload);
        nbt.setBoolean("meta", this.checkMetadata);
        nbt.setBoolean("dict", this.checkOredict);
        nbt.setBoolean("nbt", this.checkNbt);
        nbt.setInteger("mode", this.craneMode);
        nbt.setInteger("uid", this.playerUID);
        nbt.setInteger("imode", this.itemMode);
        nbt.setInteger("rmode", this.redMode);

        //save pos
        if (this.lastPos != null && this.nextPos != null && this.chestPos != null)
        {
        	nbt.setIntArray("chestPos", new int[] {this.chestPos.getX(), this.chestPos.getY(), this.chestPos.getZ()});
        	nbt.setIntArray("lastPos", new int[] {this.lastPos.getX(), this.lastPos.getY(), this.lastPos.getZ()});
        	nbt.setIntArray("nextPos", new int[] {this.nextPos.getX(), this.nextPos.getY(), this.nextPos.getZ()});
        }
        else
        {
        	nbt.setIntArray("chestPos", new int[] {0, 0, 0});
        	nbt.setIntArray("lastPos", new int[] {0, 0, 0});
        	nbt.setIntArray("nextPos", new int[] {0, 0, 0});
        }
        
        return nbt;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}
	
	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack item, EnumFacing face)
	{
		return false;
	}
	
	//set paired chest
	public void setPairedChest(BlockPos pos)
	{
		TileEntity tile = this.worldObj.getTileEntity(pos);
		
		if (tile instanceof IInventory)
		{
			this.chestPos = pos;
			this.isPaired = true;
			this.chest = (IInventory) tile;
		}
		else
		{
			clearPairedChest();
		}
	}
	
	//get paired chest
	public void checkPairedChest()
	{
		if (this.isPaired)
		{
			//get chest if no chest tile entity
			if (this.chest == null)
			{
				TileEntity tile = this.worldObj.getTileEntity(this.chestPos);
				
				if (tile instanceof IInventory)
				{
					chest = (IInventory) tile;
				}
			}
			
			//check chest valid
			if (this.chest instanceof IInventory && !((TileEntity) this.chest).isInvalid())
			{
				return;
			}
			
			//tile lost, reset
			clearPairedChest();
			sendSyncPacket();
		}
	}
	
	public void clearPairedChest()
	{
		this.chest = null;
		this.isPaired = false;
		this.chestPos = BlockPos.ORIGIN;
	}
	
	//set data from packet data
	public void setSyncData(int[] data)
	{
		if (data != null)
		{
			this.lastPos = new BlockPos(data[0], data[1], data[2]);
			this.nextPos = new BlockPos(data[3], data[4], data[5]);
			setPairedChest(new BlockPos(data[6], data[7], data[8]));
		}
	}
	
	@Override
	public void setNextWaypoint(BlockPos pos)
	{
		if (pos != null)
		{
			this.nextPos = pos;
		}
	}

	@Override
	public BlockPos getNextWaypoint()
	{
		return this.nextPos;
	}

	@Override
	public void setLastWaypoint(BlockPos pos)
	{
		if (pos != null)
		{
			this.lastPos = pos;
		}
	}

	@Override
	public BlockPos getLastWaypoint()
	{
		return this.lastPos;
	}
	
	@Override
	public void update()
	{
		//server side
		if (!this.worldObj.isRemote)
		{
			boolean update = false;
			this.tick++;
			
			//redstone signal
			if (this.redTick > 0)
			{
				this.redTick--;
				if (this.redTick <= 0)
				{
					this.worldObj.notifyNeighborsOfStateChange(this.pos, ModBlocks.BlockCrane);
				}
			}

			//can work
			if (this.isActive && this.isPaired)
			{
				//check every 16 ticks
				if (this.tick > 64 && this.tick % 16 == 0)
				{
					//check chest and ship
					checkPairedChest();
					checkCraningShip();
					
					//set redstone tick
					if (this.redMode == 1 && this.ship != null)
					{
						this.redTick = 17;
						this.worldObj.notifyNeighborsOfStateChange(this.pos, ModBlocks.BlockCrane);
					}
					
					if (this.chest != null && ship != null)
					{
						boolean movedLoad = false;
						boolean movedUnload = false;
						boolean endCraning = false;
						int waitTime = getWaitTimeInMin(this.craneMode) * 1200;
						
						try
						{
							//check item for loading
							if (this.enabLoad)
							{
								movedLoad = applyItemTransfer(true);
							}
							
							//check item for unloading
							if (this.enabUnload)
							{
								movedUnload = applyItemTransfer(false);
							}
							
							//check craning ending
							switch (this.craneMode)
							{
							case 0:  //no wait
								//no load and no unload, end craning
								if (!movedLoad && !movedUnload)
								{
									endCraning = true;
								}
								break;
							case 1:  //wait forever
								if (checkWaitForever())
								{
									endCraning = true;
								}
								break;
							default: //wait X min
								int t = this.ship.getStateTimer(ID.T.CraneTime);
								
								if (t > waitTime)
								{
									endCraning = true;
								}
								break;
							}
							
							//craning end
							if (endCraning)
							{
								//emit redstone signal
								if (this.redMode == 2)
								{
									this.redTick = 1;
									this.worldObj.notifyNeighborsOfStateChange(this.pos, ModBlocks.BlockCrane);
								}
								
								//set crane state
								this.ship.setStateMinor(ID.M.CraneState, 0);
								this.ship.setStateTimer(ID.T.CraneTime, 0);
								
								//set next waypoint
			  	  				if (EntityHelper.applyNextWaypoint(this, this.ship, false, 0))
			  	  				{
			  	  					//set follow dist
			  	  					this.ship.setStateMinor(ID.M.FollowMin, 2);
			  	  				}
			  	  				
//			  	  				//TODO sound event
//			  	  				//player sound
//			  	  				this.ship.playSound(Reference.MOD_ID+":ship-bell", ConfigHandler.volumeShip * 1.5F, this.ship.getRNG().nextFloat() * 0.3F + 1F);
			  	  				
			  	  				//clear ship
			  	  				this.ship = null;
							}
						}
						catch (Exception e)
						{
							LogHelper.info("EXCEPTION : ship loading/unloading fail: "+e);
							e.printStackTrace();
							return;
						}
					}
				}//end 16 ticks
			}//is active
			
			//update checking
			if (this.tick > 510)
			{
				this.tick = 0;
				
				//valid position
				if (this.chestPos.getY() > 0 || this.lastPos.getY() > 0 || this.nextPos.getY() > 0)
				{
					update = true;
				}
			}
			
			//can update
			if (update)
			{
				sendSyncPacket();
			}
		}//end server side
		//client side
		else
		{
			this.tick++;
			if (this.partDelay > 0) this.partDelay--;
			
			//craning particle
			if (this.isActive && this.ship != null && this.partDelay <= 0)
			{
				this.partDelay = 128;
				
				double len = this.pos.getY() - this.ship.posY - 1D;
				if (len < 1D) len = 1D;
				
				ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-1D, pos.getZ()+0.5D,
																			len, 0D, 0.25D, (byte) 40);
			}
				
			//check every 16 ticks
			if (this.tick % 16 == 0)
			{
				//player hold waypoint or target wrench
				EntityPlayer player = ClientProxy.getClientPlayer();
				ItemStack item = player.inventory.getCurrentItem();
				
				//if holding pointer, wrench, waypoint
				if (item != null && (item.getItem() instanceof ItemBlockWaypoint || item.getItem() == ModItems.TargetWrench ||
					(item.getItem() instanceof PointerItem && item.getItemDamage() < 3)))
				{
					//next point mark
					if (this.nextPos.getY() > 0)
					{
						double dx = this.nextPos.getX() - this.pos.getX();
						double dy = this.nextPos.getY() - this.pos.getY();
						double dz = this.nextPos.getZ() - this.pos.getZ();
						dx *= 0.01D;
						dy *= 0.01D;
						dz *= 0.01D;
								
						ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D,
																						dx, dy, dz, (byte) 38);
					}
					
					//paired chest mark
					if (this.chestPos.getY() > 0)
					{
						double dx = this.chestPos.getX() - this.pos.getX();
						double dy = this.chestPos.getY() - this.pos.getY();
						double dz = this.chestPos.getZ() - this.pos.getZ();
						dx *= 0.01D;
						dy *= 0.01D;
						dz *= 0.01D;

						ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D,
																						dx, dy, dz, (byte) 39);
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
		
		if (this.ship != null)
		{
			//check loading condition: ship's inventory is full
			if (this.enabLoad)
			{
				doneLoad = checkInventoryFull(this.ship.getCapaShipInventory());
			}
			
			//check unloading condition: ship have no specified item
			if (this.enabUnload)
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
							CapaShipInventory inv = this.ship.getCapaShipInventory();
							int slotid = matchTempItem(inv, temp);
							
							//get item
							if (slotid > 0)
							{
								doneUnload = false;
								break;
							}
						}
					}
					
					//if all temp slot are null = get any item except NotMode item
					if (i == 8 && allNull)
					{
						//check items in ship inventory
						CapaShipInventory inv = this.ship.getCapaShipInventory();
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
	private boolean checkInventoryFull(IInventory inv)
	{
		ItemStack item = null;
		int i = 0;
		
		//check inv type
		if(inv instanceof CapaShipInventory)
		{
			CapaShipInventory shipInv = (CapaShipInventory) inv;
			int pageSize = shipInv.getSizeInventoryPaged();
			shipInv.setInventoryPage(0);	//TODO current page跟gui show page應該分開
			
			//get any empty slot = false
			for (i = ContainerShipInventory.SLOTS_SHIPINV; i < pageSize; i++)
			{
				if (shipInv.getStackInSlot(i) == null) return false;
			}
		}
		//invTo is vanilla chest
		else if (inv instanceof TileEntityChest)
		{
			//check main chest
			for (i = 0; i < inv.getSizeInventory(); i++)
			{
				if (inv.getStackInSlot(i) == null) return false;
			}
			
			//check adj chest
			TileEntityChest chest2 = getAdjChest((TileEntityChest) inv);
			
			if (chest2 != null)
			{
				for (i = 0; i < chest2.getSizeInventory(); i++)
				{
					if (chest2.getStackInSlot(i) == null) return false;
				}
			}
		}
		else
		{
			for (i = 0; i < inv.getSizeInventory(); i++)
			{
				if (inv.getStackInSlot(i) == null) return false;
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
				invTo = ship.getCapaShipInventory();
			}
			else
			{
				invTo = chest;
				invFrom = ship.getCapaShipInventory();
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
	private boolean moveItemstackToInv(IInventory inv, ItemStack moveitem)
	{
		boolean moved = false;
		
		//move item to inv
		if (moveitem != null)
		{
			//invTo is ship inv
			if (inv instanceof CapaShipInventory)
			{
				moved = mergeItemStack(inv, moveitem);
			}
			//invTo is vanilla chest
			else if (inv instanceof TileEntityChest)
			{
				TileEntityChest chest = (TileEntityChest) inv;
				TileEntityChest chest2 = null;
				
				//move to main chest
				moved = mergeItemStack(chest, moveitem);
				
				//move fail, check adj chest
				if (!moved)
				{
					//get adj chest
					chest2 = getAdjChest(chest);
					
					//move to adj chest
					if (chest2 != null) moved = mergeItemStack(chest2, moveitem);
				}//end move to adj chest
			}
			//other normal inv
			else
			{
				moved = mergeItemStack(inv, moveitem);
			}
			
		}//end move item
		
		return moved;
	}
	
	//get adj chest for TileEntityChest
	private TileEntityChest getAdjChest(TileEntityChest chest)
	{
		TileEntityChest chest2 = null;
		
		if (chest != null && !chest.isInvalid())
		{
			//check adj chest valid
			chest.checkForAdjacentChests();
			
			//get adj chest
			chest2 = chest.adjacentChestXNeg;
			if (chest2 == null)
			{
				chest2 = chest.adjacentChestXPos;
				if (chest2 == null)
				{
					chest2 = chest.adjacentChestZNeg;
					if (chest2 == null) chest2 = chest.adjacentChestZPos;
				}
			}
		}
		
		if (chest2 != null && chest2.isInvalid()) return null;
		
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
			return this.itemHandler.getStackInSlot(i);
		}
		//get unloading temp
		else
		{
			return this.itemHandler.getStackInSlot(i + 9);
		}
	}
	
	//merge itemstack to slot
	private boolean mergeItemStack(IInventory inv, ItemStack itemstack)
	{
		ItemStack slotstack;
		boolean movedItem = false;
        int k = 0;
        int startid = 0;
        int maxSize = inv.getSizeInventory();

        //init slots for ship inventory
        if(inv instanceof CapaShipInventory)
        {
        	//set access page to 0
        	((CapaShipInventory) inv).setInventoryPage(0);
        	
        	//start at inv slots
        	startid = ContainerShipInventory.SLOTS_SHIPINV;
        	
        	//get slot size by pages
        	maxSize = ((CapaShipInventory) inv).getSizeInventoryPaged();
        }

        //is stackable item
        if (itemstack.isStackable())
        {
        	k = startid;
        	
        	//loop all slots until stacksize = 0
            while (itemstack.stackSize > 0 && k < maxSize)
            {
            	slotstack = inv.getStackInSlot(k);

                //is same item, merge to slot
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
                slotstack = inv.getStackInSlot(k);

                //find empty slot
                if (slotstack == null)
                {
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
        if (inv instanceof CapaShipInventory)
        {
        	//set access page to 0
        	((CapaShipInventory) inv).setInventoryPage(0);
        	
        	//start at inv slots
        	startid = ContainerShipInventory.SLOTS_SHIPINV;
        	
        	//get max size by page
        	maxSize = ((CapaShipInventory) inv).getSizeInventoryPaged();
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
							if (getitem.getItemDamage() == target.getItemDamage()) return slotid;
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
							
							if (a.length > 0 && b.length > 0 && a[0] == b[0]) return slotid;
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
        if (inv instanceof CapaShipInventory)
        {
        	//set access page to 0
        	((CapaShipInventory) inv).setInventoryPage(0);
        	
        	//start at inv slots
        	startid = ContainerShipInventory.SLOTS_SHIPINV;
        	
        	//get max size by page
        	maxSize = ((CapaShipInventory) inv).getSizeInventoryPaged();
        }
        
		for (slotid = startid; slotid < maxSize; slotid++)
		{
			getitem = inv.getStackInSlot(slotid);
			
			if (getitem != null)
			{
				if (checkNotModeItem(slotid, getitem, isLoading) >= 0) return slotid;
			}
		}

		return -1;
	}	
	//if item is in NotMode slot, return -1
	private int checkNotModeItem(int slotid, ItemStack item, boolean isLoading)
	{
		ItemStack temp = null;
		int slotStart = isLoading ? 0 : 9;
		int slotEnd = isLoading ? 9 : 18;
		
		for (int i = slotStart; i < slotEnd; i++)
		{
			if (getItemMode(i))
			{
				temp = this.itemHandler.getStackInSlot(i);
				
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
	private void checkCraningShip()
	{
		AxisAlignedBB box = new AxisAlignedBB(pos.getX() - 7D, pos.getY() - 6D, pos.getZ() - 7D,
											  pos.getX() + 7D, pos.getY() + 6D, pos.getZ() + 7D);
        List<BasicEntityShip> slist = this.worldObj.getEntitiesWithinAABB(BasicEntityShip.class, box);

        if (slist != null && !slist.isEmpty())
        {
        	//get craning ship
        	for (BasicEntityShip s : slist)
        	{
        		if (s.getStateMinor(ID.M.CraneState) == 2 &&
        			s.getGuardedPos(0) == pos.getX() &&
        			s.getGuardedPos(1) == pos.getY() &&
        			s.getGuardedPos(2) == pos.getZ())
        		{
        			this.ship = s;
        			this.ship.getCapaShipInventory().setInventoryPage(0);  //set show page to 0
        			this.ship.getShipNavigate().tryMoveToXYZ(pos.getX()+0.5D, pos.getY()-2D, pos.getZ()+0.5D, 0.5D);
        			this.sendSyncPacket();
        			return;
        		}
        	}
        	
        	//no craning ship, get waiting ship
        	for (BasicEntityShip s : slist)
        	{
        		if(s.getStateMinor(ID.M.CraneState) == 1 &&
         		   s.getGuardedPos(0) == pos.getX() &&
         		   s.getGuardedPos(1) == pos.getY() &&
         		   s.getGuardedPos(2) == pos.getZ())
        		{
        			//set ship is craning
         			this.ship = s;
         			this.ship.setStateMinor(ID.M.CraneState, 2);
         			this.ship.getCapaShipInventory().setInventoryPage(0);  //set show page to 0
         			this.ship.getShipNavigate().tryMoveToXYZ(pos.getX()+0.5D, pos.getY()-2D, pos.getZ()+0.5D, 0.5D);
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
  	public ItemStack getStackInSlot(int i)
	{
  		return this.itemHandler.getStackInSlot(i);
  	}
	
	@Override
  	public ItemStack decrStackSize(int i, int j)
	{
		return null;
	}
	
	@Override
  	public void setInventorySlotContents(int i, ItemStack stack)
	{
  		this.itemHandler.setStackInSlot(i, stack);
  		
  		if (stack != null)
  		{
  			stack.stackSize = 1;
  		}	
  	}
	
	//每格可放的最大數量上限
  	@Override
  	public int getInventoryStackLimit()
  	{
  		return 0;
  	}
  	
  	//使用管線/漏斗輸入時呼叫, 不適用於手動置入
  	@Override
  	public boolean canInsertItem(int id, ItemStack stack, EnumFacing side)
  	{
  		return false;
  	}
  	
  	//get waiting time (min)
  	public static int getWaitTimeInMin(int mode)
  	{
  		if (mode >= 6 && mode <= 15)
  		{
			return mode - 5;
		}
		else if (mode >= 16 && mode <= 19)
		{
			return (mode - 16) * 5 + 15;
		}
		else if (mode >= 20 && mode <= 22)
		{
			return (mode - 20) * 10 + 40;
		}
		else if (mode >= 23 && mode <= 25)
		{
			return (mode - 23) * 60 + 120;
		}
		else
		{
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
			this.itemMode = this.itemMode | slot;
		}
		//set bit 0
		else
		{
			this.itemMode = this.itemMode & ~slot;
		}
	}
	
	//check slot is notMode
	public boolean getItemMode(int slotID)
	{
		return ((itemMode >> (slotID - 1)) & 1) == 1 ? true : false;
	}
	
	//getter, setter
	public int getRedMode()
	{
		return this.redMode;
	}
	
	public int getRedTick()
	{
		return this.redTick;
	}
	
	
}
