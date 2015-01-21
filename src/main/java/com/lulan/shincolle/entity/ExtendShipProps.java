package com.lulan.shincolle.entity;

import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**Extend Entity NBT data
 * IExtendedEntityProperties穦NBT穝tag: SHIP_EXTPROP_NAME
 * IInventory糤肂珇逆, tag: ShipInv
 */
public class ExtendShipProps implements IExtendedEntityProperties, IInventory {

	public static final String SHIP_EXTPROP_NAME = "ShipExtProps";
	private static final String tagName = "ShipInv";	//ship inventory nbt tag
    private ItemStack[] slots = new ItemStack[23];		//0~4:equip 5~22:inventory
    private BasicEntityShip entity;
    private World world;

  
	//init extend entity prop
	@Override
	public void init(Entity entity, World world) {
		this.world = world;
		this.entity = (BasicEntityShip) entity;

	}
	
	//save extend entity prop
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		NBTTagCompound nbtExt = new NBTTagCompound();		
		NBTTagCompound nbtExt_add1 = new NBTTagCompound();
		NBTTagCompound nbtExt_add2 = new NBTTagCompound();
		NBTTagCompound nbtExt_add3 = new NBTTagCompound();

		//save values to NBT
		nbtExt.setShort("Level", this.entity.ShipLevel);
		nbtExt.setInteger("Kills", this.entity.Kills);
		//save AttrBonus
		nbtExt.setTag("Bonus", nbtExt_add1);
		nbtExt_add1.setShort("HP", this.entity.AttrBonusShort[AttrID.HP]);
		nbtExt_add1.setShort("ATK", this.entity.AttrBonusShort[AttrID.ATK]);
		nbtExt_add1.setShort("DEF", this.entity.AttrBonusShort[AttrID.DEF]);
		nbtExt_add1.setFloat("SPD", this.entity.AttrBonusFloat[AttrID.SPD]);
		nbtExt_add1.setFloat("MOV", this.entity.AttrBonusFloat[AttrID.MOV]);
		nbtExt_add1.setFloat("HIT", this.entity.AttrBonusFloat[AttrID.HIT]);
		//save AttrFinal
		nbtExt.setTag("Final", nbtExt_add2);
		nbtExt_add2.setShort("HP", this.entity.AttrFinalShort[AttrID.HP]);
		nbtExt_add2.setShort("ATK", this.entity.AttrFinalShort[AttrID.ATK]);
		nbtExt_add2.setShort("DEF", this.entity.AttrFinalShort[AttrID.DEF]);
		nbtExt_add2.setFloat("SPD", this.entity.AttrFinalFloat[AttrID.SPD]);
		nbtExt_add2.setFloat("MOV", this.entity.AttrFinalFloat[AttrID.MOV]);
		nbtExt_add2.setFloat("HIT", this.entity.AttrFinalFloat[AttrID.HIT]);
		//save EntityState
		nbtExt.setTag("State", nbtExt_add3);	
		nbtExt_add3.setByte("State", this.entity.EntityState[AttrID.State]);
		nbtExt_add3.setByte("Emotion", this.entity.EntityState[AttrID.Emotion]);
		nbtExt_add3.setByte("SwimType", this.entity.EntityState[AttrID.SwimType]);
		
		//save inventory
		NBTTagList list = new NBTTagList();
		nbt.setTag(tagName, list);	//slots戈场tag: ShipInv
		
		for(int i=0; i<slots.length; i++) {
			if (slots[i] != null) {
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
		
		//load values to entity
		entity.ShipLevel = nbt_tag.getShort("Level");
		entity.Kills = nbt_tag.getShort("Kills");
		//load Attr Bonus
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Bonus");
		entity.AttrBonusShort[AttrID.HP] = nbt_load.getShort("HP");
		entity.AttrBonusShort[AttrID.ATK] = nbt_load.getShort("ATK");
		entity.AttrBonusShort[AttrID.DEF] = nbt_load.getShort("DEF");
		entity.AttrBonusFloat[AttrID.SPD] = nbt_load.getFloat("SPD");
		entity.AttrBonusFloat[AttrID.MOV] = nbt_load.getFloat("MOV");
		entity.AttrBonusFloat[AttrID.HIT] = nbt_load.getFloat("HIT");
		//load Attr Final
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Final");
		entity.AttrFinalShort[AttrID.HP] = nbt_load.getShort("HP");
		entity.AttrFinalShort[AttrID.ATK] = nbt_load.getShort("ATK");
		entity.AttrFinalShort[AttrID.DEF] = nbt_load.getShort("DEF");
		entity.AttrFinalFloat[AttrID.SPD] = nbt_load.getFloat("SPD");
		entity.AttrFinalFloat[AttrID.MOV] = nbt_load.getFloat("MOV");
		entity.AttrFinalFloat[AttrID.HIT] = nbt_load.getFloat("HIT");
		//load entity state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("State");
		entity.EntityState[AttrID.State] = nbt_tag.getByte("State");
		entity.EntityState[AttrID.Emotion] = nbt_tag.getByte("Emotion");
		entity.EntityState[AttrID.SwimType] = nbt_tag.getByte("SwimType");
		
		//load inventory
		NBTTagList list = nbt.getTagList(tagName, 10);

		for(int i=0; i<list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
			byte sid = item.getByte("Slot");

			if (sid>=0 && sid<slots.length) {
				slots[sid] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		
		//send sync nbt packet to client
		entity.sendSyncPacket();
		LogHelper.info("DEBUG : load entity ExtNBT data on id: "+entity.getEntityId());
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) {
            if (itemStack.stackSize <= j) {			  //璝计秖<=j
                setInventorySlotContents(i, null);	  //玥赣slot睲
            }
            else {									  //璝计秖 >j
                itemStack = itemStack.splitStack(j);  //赣itemstack计秖-j
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(i, null);//场, slot睲
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
		slots[i] = itemstack;
		//璝も珇禬筁赣计秖, 玥秈计秖
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
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
	public void markDirty() {}	//no need for entity

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	

}
