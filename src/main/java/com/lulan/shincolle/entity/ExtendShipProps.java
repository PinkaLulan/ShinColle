package com.lulan.shincolle.entity;

import com.lulan.shincolle.inventory.ContainerShipInventory;
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
	public static final String tagName = "ShipInv";	//ship inventory nbt tag
    public ItemStack[] slots = new ItemStack[ContainerShipInventory.SLOTS_TOTAL];
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
		NBTTagCompound nbtExt_add4 = new NBTTagCompound();

		//save values to NBT
		nbtExt.setShort("Level", this.entity.ShipLevel);
		nbtExt.setInteger("Kills", this.entity.Kills);
		//save AttrEquip
		nbtExt.setTag("Equip", nbtExt_add1);
		nbtExt_add1.setShort("HP", this.entity.AttrEquipShort[AttrID.HP]);
		nbtExt_add1.setShort("ATK", this.entity.AttrEquipShort[AttrID.ATK]);
		nbtExt_add1.setShort("DEF", this.entity.AttrEquipShort[AttrID.DEF]);
		nbtExt_add1.setFloat("SPD", this.entity.AttrEquipFloat[AttrID.SPD]);
		nbtExt_add1.setFloat("MOV", this.entity.AttrEquipFloat[AttrID.MOV]);
		nbtExt_add1.setFloat("HIT", this.entity.AttrEquipFloat[AttrID.HIT]);
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
		//save BonusPoint
		nbtExt.setTag("Point", nbtExt_add4);	
		nbtExt_add4.setByte("HP", this.entity.BonusPoint[0]);
		nbtExt_add4.setByte("ATK", this.entity.BonusPoint[1]);
		nbtExt_add4.setByte("DEF", this.entity.BonusPoint[2]);
		nbtExt_add4.setByte("SPD", this.entity.BonusPoint[3]);
		nbtExt_add4.setByte("MOV", this.entity.BonusPoint[4]);
		nbtExt_add4.setByte("HIT", this.entity.BonusPoint[5]);
		
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
		//load Attr Equip
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Equip");
		entity.AttrEquipShort[AttrID.HP] = nbt_load.getShort("HP");
		entity.AttrEquipShort[AttrID.ATK] = nbt_load.getShort("ATK");
		entity.AttrEquipShort[AttrID.DEF] = nbt_load.getShort("DEF");
		entity.AttrEquipFloat[AttrID.SPD] = nbt_load.getFloat("SPD");
		entity.AttrEquipFloat[AttrID.MOV] = nbt_load.getFloat("MOV");
		entity.AttrEquipFloat[AttrID.HIT] = nbt_load.getFloat("HIT");
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
		entity.EntityState[AttrID.State] = nbt_load.getByte("State");
		entity.EntityState[AttrID.Emotion] = nbt_load.getByte("Emotion");
		entity.EntityState[AttrID.SwimType] = nbt_load.getByte("SwimType");
		//load bonus point
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Point");
		entity.BonusPoint[0] = nbt_load.getByte("HP");
		entity.BonusPoint[1] = nbt_load.getByte("ATK");
		entity.BonusPoint[2] = nbt_load.getByte("DEF");
		entity.BonusPoint[3] = nbt_load.getByte("SPD");
		entity.BonusPoint[4] = nbt_load.getByte("MOV");
		entity.BonusPoint[5] = nbt_load.getByte("HIT");
		
		//load inventory
		NBTTagList list = nbt.getTagList(tagName, 10);

		for(int i=0; i<list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
			byte sid = item.getByte("Slot");

			if (sid>=0 && sid<slots.length) {
				slots[sid] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		
		//set new value and send sync nbt packet to client
		if(!world.isRemote) {
			entity.setAttrEquip();
		}
		LogHelper.info("DEBUG : ExtEntityProps set ship attribute");	
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
		if(!world.isRemote) {
			this.entity.setAttrEquip();  //update equip and attribute value
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


}
