package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

/**Extend Entity NBT data
 * IExtendedEntityProperties會在NBT加上新的tag: SHIP_EXTPROP_NAME
 * IInventory為增加額外物品欄, tag: ShipInv
 */
public class ExtendShipProps implements IExtendedEntityProperties, IInventory {

	public static final String SHIP_EXTPROP_NAME = "ShipExtProps";
	public static final String tagName = "ShipInv";	//ship inventory nbt tag
	public static final int SlotPages = 3;
	public static final int SlotMax = 6 + 18 * 3;   //6 equip + 18 inv * 3 page
    public ItemStack[] slots = new ItemStack[SlotMax];
    public BasicEntityShip entity;
    private World world;
    private int inventoryPage = 0;

  
	//init extend entity prop
	@Override
	public void init(Entity entity, World world) {
		this.world = world;
		this.entity = (BasicEntityShip) entity;
		
	}
	
	//save extend entity prop
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		//save nbt data
		NBTTagCompound nbtExt = new NBTTagCompound();
		NBTTagCompound nbtExt_add0 = new NBTTagCompound();
		NBTTagCompound nbtExt_add2 = new NBTTagCompound();
		NBTTagCompound nbtExt_add3 = new NBTTagCompound();
		NBTTagCompound nbtExt_add4 = new NBTTagCompound();
		NBTTagCompound nbtExt_add5 = new NBTTagCompound();

		//save values to NBT
		nbtExt.setTag("Minor", nbtExt_add0);
		nbtExt_add0.setInteger("Level", this.entity.getStateMinor(ID.M.ShipLevel));
		nbtExt_add0.setInteger("Kills", this.entity.getStateMinor(ID.M.Kills));
		nbtExt_add0.setInteger("Exp", this.entity.getStateMinor(ID.M.ExpCurrent));
		nbtExt_add0.setInteger("NumAmmoL", this.entity.getStateMinor(ID.M.NumAmmoLight));
		nbtExt_add0.setInteger("NumAmmoH", this.entity.getStateMinor(ID.M.NumAmmoHeavy));
		nbtExt_add0.setInteger("NumGrudge", this.entity.getStateMinor(ID.M.NumGrudge));
		nbtExt_add0.setInteger("NumAirL", this.entity.getStateMinor(ID.M.NumAirLight));
		nbtExt_add0.setInteger("NumAirH", this.entity.getStateMinor(ID.M.NumAirHeavy));
		nbtExt_add0.setInteger("FMin", this.entity.getStateMinor(ID.M.FollowMin));
		nbtExt_add0.setInteger("FMax", this.entity.getStateMinor(ID.M.FollowMax));
		nbtExt_add0.setInteger("FHP", this.entity.getStateMinor(ID.M.FleeHP));
		nbtExt_add0.setInteger("GuardX", this.entity.getStateMinor(ID.M.GuardX));
		nbtExt_add0.setInteger("GuardY", this.entity.getStateMinor(ID.M.GuardY));
		nbtExt_add0.setInteger("GuardZ", this.entity.getStateMinor(ID.M.GuardZ));
		nbtExt_add0.setInteger("GuardDim", this.entity.getStateMinor(ID.M.GuardDim));
		nbtExt_add0.setInteger("GuardID", this.entity.getStateMinor(ID.M.GuardID));
		nbtExt_add0.setInteger("GuardType", this.entity.getStateMinor(ID.M.GuardType));
		nbtExt_add0.setInteger("PlayerUID", this.entity.getStateMinor(ID.M.PlayerUID));
		nbtExt_add0.setInteger("ShipUID", this.entity.getStateMinor(ID.M.ShipUID));
		nbtExt_add0.setInteger("FType", this.entity.getStateMinor(ID.M.FormatType));
		nbtExt_add0.setInteger("FPos", this.entity.getStateMinor(ID.M.FormatPos));
		nbtExt_add0.setInteger("Morale", this.entity.getStateMinor(ID.M.Morale));
		nbtExt_add0.setInteger("Food", this.entity.getStateMinor(ID.M.Food));
		nbtExt_add0.setInteger("Crane", this.entity.getStateMinor(ID.M.CraneState));
		nbtExt_add0.setInteger("WpStay", this.entity.getStateMinor(ID.M.WpStay));
		nbtExt_add0.setString("tagName", this.entity.getCustomNameTag());
		//save EntityState
		nbtExt.setTag("Emotion", nbtExt_add2);	
		nbtExt_add2.setByte("State", this.entity.getStateEmotion(ID.S.State));
		nbtExt_add2.setByte("State2", this.entity.getStateEmotion(ID.S.State2));
		nbtExt_add2.setByte("Emotion", this.entity.getStateEmotion(ID.S.Emotion));
		nbtExt_add2.setByte("Emotion2", this.entity.getStateEmotion(ID.S.Emotion2));
		nbtExt_add2.setByte("Phase", this.entity.getStateEmotion(ID.S.Phase));
		//save BonusPoint
		nbtExt.setTag("Point", nbtExt_add3);	
		nbtExt_add3.setByte("HP", this.entity.getBonusPoint(ID.HP));
		nbtExt_add3.setByte("ATK", this.entity.getBonusPoint(ID.ATK));
		nbtExt_add3.setByte("DEF", this.entity.getBonusPoint(ID.DEF));
		nbtExt_add3.setByte("SPD", this.entity.getBonusPoint(ID.SPD));
		nbtExt_add3.setByte("MOV", this.entity.getBonusPoint(ID.MOV));
		nbtExt_add3.setByte("HIT", this.entity.getBonusPoint(ID.HIT));
		//save EntityFlag
		nbtExt.setTag("ShipFlags", nbtExt_add4);
		nbtExt_add4.setBoolean("CanFloat", this.entity.getStateFlag(ID.F.CanFloatUp));
		nbtExt_add4.setBoolean("IsMarried", this.entity.getStateFlag(ID.F.IsMarried));
		nbtExt_add4.setBoolean("NoFuel", this.entity.getStateFlag(ID.F.NoFuel));
		nbtExt_add4.setBoolean("Melee", this.entity.getStateFlag(ID.F.UseMelee));
		nbtExt_add4.setBoolean("AmmoL", this.entity.getStateFlag(ID.F.UseAmmoLight));
		nbtExt_add4.setBoolean("AmmoH", this.entity.getStateFlag(ID.F.UseAmmoHeavy));
		nbtExt_add4.setBoolean("AirL", this.entity.getStateFlag(ID.F.UseAirLight));
		nbtExt_add4.setBoolean("AirH", this.entity.getStateFlag(ID.F.UseAirHeavy));
		nbtExt_add4.setBoolean("WedEffect", this.entity.getStateFlag(ID.F.UseRingEffect));
		nbtExt_add4.setBoolean("CanDrop", this.entity.getStateFlag(ID.F.CanDrop));
		nbtExt_add4.setBoolean("CanFollow", this.entity.getStateFlag(ID.F.CanFollow));
		nbtExt_add4.setBoolean("OnSight", this.entity.getStateFlag(ID.F.OnSightChase));
		nbtExt_add4.setBoolean("PVPFirst", this.entity.getStateFlag(ID.F.PVPFirst));
		nbtExt_add4.setBoolean("AA", this.entity.getStateFlag(ID.F.AntiAir));
		nbtExt_add4.setBoolean("ASM", this.entity.getStateFlag(ID.F.AntiSS));
		nbtExt_add4.setBoolean("PassiveAI", this.entity.getStateFlag(ID.F.PassiveAI));
		nbtExt_add4.setBoolean("TimeKeeper", this.entity.getStateFlag(ID.F.TimeKeeper));
		nbtExt_add4.setBoolean("PickItem", this.entity.getStateFlag(ID.F.PickItem));
		//save values to NBT
		nbtExt.setTag("Timer", nbtExt_add5);
		nbtExt_add5.setInteger("Crane", this.entity.getStateTimer(ID.T.CraneTime));
				
		//save inventory
		NBTTagList list = new NBTTagList();
		nbt.setTag(tagName, list);
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				slots[i].writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		nbt.setTag(SHIP_EXTPROP_NAME, nbtExt);
		
		LogHelper.info("DEBUG : save entity ExtNBT data on id: "+entity.getEntityId());
	}

	//load extend entity prop
	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		NBTTagCompound nbt_tag = (NBTTagCompound) nbt.getTag(SHIP_EXTPROP_NAME);
		NBTTagCompound nbt_load = new NBTTagCompound();
		
		//load minor state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Minor");
		entity.setStateMinor(ID.M.ShipLevel, nbt_load.getInteger("Level"));
		entity.setStateMinor(ID.M.Kills, nbt_load.getInteger("Kills"));
		entity.setStateMinor(ID.M.ExpCurrent, nbt_load.getInteger("Exp"));
		entity.setStateMinor(ID.M.NumAmmoLight, nbt_load.getInteger("NumAmmoL"));
		entity.setStateMinor(ID.M.NumAmmoHeavy, nbt_load.getInteger("NumAmmoH"));
		entity.setStateMinor(ID.M.NumGrudge, nbt_load.getInteger("NumGrudge"));
		entity.setStateMinor(ID.M.NumAirLight, nbt_load.getInteger("NumAirL"));
		entity.setStateMinor(ID.M.NumAirHeavy, nbt_load.getInteger("NumAirH"));
		entity.setStateMinor(ID.M.FollowMin, nbt_load.getInteger("FMin"));
		entity.setStateMinor(ID.M.FollowMax, nbt_load.getInteger("FMax"));
		entity.setStateMinor(ID.M.FleeHP, nbt_load.getInteger("FHP"));
		entity.setStateMinor(ID.M.GuardX, nbt_load.getInteger("GuardX"));
		entity.setStateMinor(ID.M.GuardY, nbt_load.getInteger("GuardY"));
		entity.setStateMinor(ID.M.GuardZ, nbt_load.getInteger("GuardZ"));
		entity.setStateMinor(ID.M.GuardDim, nbt_load.getInteger("GuardDim"));
		entity.setStateMinor(ID.M.GuardID, nbt_load.getInteger("GuardID"));
		entity.setStateMinor(ID.M.GuardType, nbt_load.getInteger("GuardType"));
		entity.setStateMinor(ID.M.PlayerUID, nbt_load.getInteger("PlayerUID"));
		entity.setStateMinor(ID.M.ShipUID, nbt_load.getInteger("ShipUID"));
		entity.setStateMinor(ID.M.FormatType, nbt_load.getInteger("FType"));
		entity.setStateMinor(ID.M.FormatPos, nbt_load.getInteger("FPos"));
		entity.setStateMinor(ID.M.Morale, nbt_load.getInteger("Morale"));
		entity.setStateMinor(ID.M.Food, nbt_load.getInteger("Food"));
		entity.setStateMinor(ID.M.CraneState, nbt_load.getInteger("Crane"));
		entity.setStateMinor(ID.M.WpStay, nbt_load.getInteger("WpStay"));
		entity.setNameTag(nbt_load.getString("tagName"));
		//load emotion state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Emotion");
		entity.setStateEmotion(ID.S.State, nbt_load.getByte("State"), false);
		entity.setStateEmotion(ID.S.State2, nbt_load.getByte("State2"), false);
		entity.setStateEmotion(ID.S.Emotion, nbt_load.getByte("Emotion"), false);
		entity.setStateEmotion(ID.S.Emotion2, nbt_load.getByte("Emotion2"), false);
		entity.setStateEmotion(ID.S.Phase, nbt_load.getByte("Phase"), false);
		//load bonus point
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Point");
		entity.setBonusPoint(ID.HP, nbt_load.getByte("HP"));
		entity.setBonusPoint(ID.ATK, nbt_load.getByte("ATK"));
		entity.setBonusPoint(ID.DEF, nbt_load.getByte("DEF"));
		entity.setBonusPoint(ID.SPD, nbt_load.getByte("SPD"));
		entity.setBonusPoint(ID.MOV, nbt_load.getByte("MOV"));
		entity.setBonusPoint(ID.HIT, nbt_load.getByte("HIT"));
		//load flags
		nbt_load = (NBTTagCompound) nbt_tag.getTag("ShipFlags");
		entity.setStateFlag(ID.F.CanFloatUp, nbt_load.getBoolean("CanFloat"));
		entity.setStateFlag(ID.F.IsMarried, nbt_load.getBoolean("IsMarried"));
		entity.setStateFlag(ID.F.NoFuel, nbt_load.getBoolean("NoFuel"));
		entity.setStateFlag(ID.F.UseMelee, nbt_load.getBoolean("Melee"));
		entity.setStateFlag(ID.F.UseAmmoLight, nbt_load.getBoolean("AmmoL"));
		entity.setStateFlag(ID.F.UseAmmoHeavy, nbt_load.getBoolean("AmmoH"));
		entity.setStateFlag(ID.F.UseAirLight, nbt_load.getBoolean("AirL"));
		entity.setStateFlag(ID.F.UseAirHeavy, nbt_load.getBoolean("AirH"));
		entity.setStateFlag(ID.F.UseRingEffect, nbt_load.getBoolean("WedEffect"));
		entity.setStateFlag(ID.F.CanDrop, nbt_load.getBoolean("CanDrop"));
		entity.setStateFlag(ID.F.CanFollow, nbt_load.getBoolean("CanFollow"));
		entity.setStateFlag(ID.F.OnSightChase, nbt_load.getBoolean("OnSight"));
		entity.setStateFlag(ID.F.PVPFirst, nbt_load.getBoolean("PVPFirst"));
		entity.setStateFlag(ID.F.AntiAir, nbt_load.getBoolean("AA"));
		entity.setStateFlag(ID.F.AntiSS, nbt_load.getBoolean("ASM"));
		entity.setStateFlag(ID.F.PassiveAI, nbt_load.getBoolean("PassiveAI"));
		entity.setStateFlag(ID.F.TimeKeeper, nbt_load.getBoolean("TimeKeeper"));
		entity.setStateFlag(ID.F.PickItem, nbt_load.getBoolean("PickItem"));
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Timer");
		entity.setStateTimer(ID.T.CraneTime, nbt_load.getInteger("Crane"));

		//load inventory
		NBTTagList list = nbt.getTagList(tagName, 10);	//tagList內為tagCompound, 代號=10

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound item = list.getCompoundTagAt(i);
			byte sid = item.getByte("Slot");

			if(sid >= 0 && sid < slots.length) {
				slots[sid] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		
		//calc equip and attribute
		entity.setExpNext();	//for gui display
		entity.calcEquipAndUpdateState();	//re-calc attributes and send sync packet
		
		LogHelper.info("DEBUG : load entity ExtNBT data on id: "+entity.getEntityId());
	}

	@Override
	public int getSizeInventory() {
		return ContainerShipInventory.SLOTS_PLAYERINV;
	}
	
	//inventory size including all enabled pages
	public int getSizeInventoryPaged() {
		if(entity != null) {
			return ContainerShipInventory.SLOTS_PLAYERINV + entity.getStateMinor(ID.M.InvSize) * 18;
		}
		
		return ContainerShipInventory.SLOTS_PLAYERINV;
	}

	/** input: slot id in gui (0~23)
	 *  output: slot item in slots[] (0~60)
	 */
	@Override
	public ItemStack getStackInSlot(int i) {
		//access within page
		if(i < getSizeInventory()) {
			//get equip slot
			if(i < ContainerShipInventory.SLOTS_SHIPINV) {
				return slots[i];
			}
			//get inventory slot
			else {
				return slots[i + this.inventoryPage * 18];
			}
		}
		//access across pages
		else {
			return slots[i];
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) {
            if (itemStack.stackSize <= j) {			  //若數量<=j個
                setInventorySlotContents(i, null);	  //則該slot清空
            }
            else {									  //若數量 >j個
                itemStack = itemStack.splitStack(j);  //該itemstack數量-j
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(i, null);//全部拿光, slot清空
                }
            }
        }
        return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) {
            setInventorySlotContents(i, null);
        }
        return itemStack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		//access within page
		if(i < getSizeInventory()) {
			//equip slot
			if(i < ContainerShipInventory.SLOTS_SHIPINV) {
				slots[i] = itemstack;
			}
			//inv slot
			else {
				slots[i + this.inventoryPage * 18] = itemstack;
			}
		}
		//access across pages
		else {
			slots[i] = itemstack;
		}
		
		//若手上物品超過該格子限制數量, 則只能放進限制數量
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		
		//change equip slot
		if(!world.isRemote && i < 6) {
			this.entity.calcEquipAndUpdateState();  //update equip and attribute value
		}
	}

	@Override
	public String getInventoryName() {
		return "Ship Inventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}
	
	@Override
	public void markDirty() {}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	
	public int getInventoryPage() {
		return this.inventoryPage;
	}
	
	public void setInventoryPage(int par1) {
		if(par1 >= 0 && par1 < 3) {
			this.inventoryPage = par1;
		}
		else {
			this.inventoryPage = 0;
		}
		
		this.entity.sendGUISyncPacket();
	}
	
	/** check slot id with inventory page */
	public boolean isSlotAvailable(int slotid) {
		//has 0~1 page
		if(this.entity.getInventoryPageSize() < 2) {
			if(slotid >= 42) {
				return false;
			}
			//has 0 page
			else if(this.entity.getInventoryPageSize() < 1) {
				if(slotid >= 24) {
  					return false;
  				}
  			}
		}//end page check
		
		return true;
	}
	
	/** get first empty slots */
  	public int getFirstSlotForItem() {
  		for(int i = 6; i < this.slots.length; i++) {
  			//stop loop if no available slot
  			if(!isSlotAvailable(i)) return -1;
  			
  			//get empty slot
  			if(this.slots[i] == null) return i;
  		}
  		
  		return -1;
  	}
  	
  	//get first item with same metadata and has space for stack
    private int getFirstSlotStackable(ItemStack itemstack) {
        for(int i = 6; i < this.slots.length; ++i) {
        	//stop loop if no available slot
  			if(!isSlotAvailable(i)) return -1;
        	
  			//check same item, same meta, same nbt tag and has stack space
            if(this.slots[i] != null && this.slots[i].getItem() == itemstack.getItem() &&
               this.slots[i].isStackable() && this.slots[i].stackSize < this.slots[i].getMaxStackSize() &&
               this.slots[i].stackSize < this.getInventoryStackLimit() &&
               (!this.slots[i].getHasSubtypes() || this.slots[i].getItemDamage() == itemstack.getItemDamage()) &&
               ItemStack.areItemStackTagsEqual(this.slots[i], itemstack)) {
                return i;
            }
        }

        return -1;
    }
  	
  	/** vanilla method
     *  This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     *  leftover items.
     */
    private int storePartialItemStack(ItemStack itemstack) {
        Item item = itemstack.getItem();
        int i = itemstack.stackSize;
        int j;

        //non stackable item
        if(itemstack.getMaxStackSize() == 1) {
        	//check empty slot
            j = getFirstSlotForItem();

            //no space
            if(j < 0) {
                return i;
            }
            else {
                this.slots[j] = ItemStack.copyItemStack(itemstack);
                return 0;
            }
        }
        //stackable item
        else {
        	//check exists stack
            j = getFirstSlotStackable(itemstack);

            //no same stack, check empty slot
            if(j < 0) {
                j = getFirstSlotForItem();
            }

            //no empty slot, return
            if(j < 0) {
                return i;
            }
            //get empty slot
            else {
            	//add item to slot
                if(this.slots[j] == null) {
                    this.slots[j] = new ItemStack(item, 0, itemstack.getItemDamage());

                    if(itemstack.hasTagCompound()) {
                        this.slots[j].setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }

                //calc leftover stack size
                int k = i;

                //check the item max stack size
                if(i > this.slots[j].getMaxStackSize() - this.slots[j].stackSize) {
                    k = this.slots[j].getMaxStackSize() - this.slots[j].stackSize;
                }

                //check the slot max stack size
                if(k > this.getInventoryStackLimit() - this.slots[j].stackSize) {
                    k = this.getInventoryStackLimit() - this.slots[j].stackSize;
                }

                //no space for item
                if(k == 0) {
                    return i;
                }
                //get space for item
                else {
                    i -= k;
                    this.slots[j].stackSize += k;
                    this.slots[j].animationsToGo = 5;
                    return i;
                }
            }
        }
    }
  	
  	/** add itemstack to ship inventory */
  	public boolean addItemStackToInventory(final ItemStack itemstack) {
        if(itemstack != null && itemstack.stackSize != 0 && itemstack.getItem() != null) {
            try {
                int i;

                //item with meta != 0
                if(itemstack.isItemDamaged()) {
                    i = this.getFirstSlotForItem();

                    //add item to slot
                    if(i >= 0) {
                        this.slots[i] = ItemStack.copyItemStack(itemstack);
                        this.slots[i].animationsToGo = 5;
                        itemstack.stackSize = 0;
                        return true;
                    }
                    //add fail
                    else {
                        return false;
                    }
                }
                //item without meta value
                else {
                	//add item to slot with stackable check
                    do {
                        i = itemstack.stackSize;
                        itemstack.stackSize = this.storePartialItemStack(itemstack);
                    }
                    while(itemstack.stackSize > 0 && itemstack.stackSize < i);
                    
                    return itemstack.stackSize < i;
                }
            }
            catch(Exception e) {
            	LogHelper.log("EXCEPTION : add item to ship's inventory fail: "+e+" "+itemstack);
            	return false;
            }
        }
        else {
            return false;
        }
    }


}
