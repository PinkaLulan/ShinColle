package com.lulan.shincolle.entity;

import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.reference.ID;
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
		NBTTagCompound nbtExt_add0 = new NBTTagCompound();
		NBTTagCompound nbtExt_add1 = new NBTTagCompound();
		NBTTagCompound nbtExt_add2 = new NBTTagCompound();
		NBTTagCompound nbtExt_add3 = new NBTTagCompound();
		NBTTagCompound nbtExt_add4 = new NBTTagCompound();

		//save values to NBT
		nbtExt.setTag("Minor", nbtExt_add0);
		nbtExt_add0.setInteger("Level", this.entity.getStateMinor(ID.N.ShipLevel));
		nbtExt_add0.setInteger("Kills", this.entity.getStateMinor(ID.N.Kills));
		nbtExt_add0.setInteger("Exp", this.entity.getStateMinor(ID.N.ExpCurrent));
		nbtExt_add0.setInteger("NumAmmoL", this.entity.getStateMinor(ID.N.NumAmmoLight));
		nbtExt_add0.setInteger("NumAmmoH", this.entity.getStateMinor(ID.N.NumAmmoHeavy));
		nbtExt_add0.setInteger("NumGrudge", this.entity.getStateMinor(ID.N.NumGrudge));
		nbtExt_add0.setInteger("NumAirL", this.entity.getStateMinor(ID.N.NumAirLight));
		nbtExt_add0.setInteger("NumAirH", this.entity.getStateMinor(ID.N.NumAirHeavy));
		//save AttrFinal
		nbtExt.setTag("Final", nbtExt_add1);
		nbtExt_add1.setFloat("HP", this.entity.getStateFinal(ID.HP));
		nbtExt_add1.setFloat("ATK", this.entity.getStateFinal(ID.ATK));
		nbtExt_add1.setFloat("DEF", this.entity.getStateFinal(ID.DEF));
		nbtExt_add1.setFloat("SPD", this.entity.getStateFinal(ID.SPD));
		nbtExt_add1.setFloat("MOV", this.entity.getStateFinal(ID.MOV));
		nbtExt_add1.setFloat("HIT", this.entity.getStateFinal(ID.HIT));
		nbtExt_add1.setFloat("ATK_H", this.entity.getStateFinal(ID.ATK_H));
		nbtExt_add1.setFloat("ATK_AL", this.entity.getStateFinal(ID.ATK_AL));
		nbtExt_add1.setFloat("ATK_AH", this.entity.getStateFinal(ID.ATK_AH));
		//save EntityState
		nbtExt.setTag("Emotion", nbtExt_add2);	
		nbtExt_add2.setByte("State", this.entity.getStateEmotion(ID.S.State));
		nbtExt_add2.setByte("Emotion", this.entity.getStateEmotion(ID.S.Emotion));
		nbtExt_add2.setByte("Emotion2", this.entity.getStateEmotion(ID.S.Emotion2));
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

		//load minor state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Minor");
		entity.setStateMinor(ID.N.ShipLevel, nbt_load.getShort("Level"));
		entity.setStateMinor(ID.N.Kills, nbt_load.getShort("Kills"));
		entity.setStateMinor(ID.N.ExpCurrent, nbt_load.getShort("Exp"));
		entity.setStateMinor(ID.N.NumAmmoLight, nbt_load.getShort("NumAmmoL"));
		entity.setStateMinor(ID.N.NumAmmoHeavy, nbt_load.getShort("NumAmmoH"));
		entity.setStateMinor(ID.N.NumGrudge, nbt_load.getShort("NumGrudge"));
		entity.setStateMinor(ID.N.NumAirLight, nbt_load.getShort("NumAirL"));
		entity.setStateMinor(ID.N.NumAirHeavy, nbt_load.getShort("NumAirH"));
		//load final state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Final");
		entity.setStateFinal(ID.HP, nbt_load.getFloat("HP"));
		entity.setStateFinal(ID.ATK, nbt_load.getFloat("ATK"));
		entity.setStateFinal(ID.DEF, nbt_load.getFloat("DEF"));
		entity.setStateFinal(ID.SPD, nbt_load.getFloat("SPD"));
		entity.setStateFinal(ID.MOV, nbt_load.getFloat("MOV"));
		entity.setStateFinal(ID.HIT, nbt_load.getFloat("HIT"));
		entity.setStateFinal(ID.HIT, nbt_load.getFloat("ATK_H"));
		entity.setStateFinal(ID.HIT, nbt_load.getFloat("ATK_AL"));
		entity.setStateFinal(ID.HIT, nbt_load.getFloat("ATK_AH"));
		//load emotion state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Emotion");
		entity.setStateEmotion(ID.S.State, nbt_load.getByte("State"), false);
		entity.setStateEmotion(ID.S.Emotion, nbt_load.getByte("Emotion"), false);
		entity.setStateEmotion(ID.S.Emotion2, nbt_load.getByte("Emotion2"), false);
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

		//load inventory
		NBTTagList list = nbt.getTagList(tagName, 10);

		for(int i=0; i<list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
			byte sid = item.getByte("Slot");

			if (sid>=0 && sid<slots.length) {
				slots[sid] = ItemStack.loadItemStackFromNBT(item);
			}
		}
		
		//calc equip and attribute
		entity.setExpNext();	//for gui display
		entity.calcEquipAndUpdateState();
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
			this.entity.calcEquipAndUpdateState();;  //update equip and attribute value
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
