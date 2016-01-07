package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

/**Extend Entity NBT data
 * IExtendedEntityProperties穦NBT穝tag: SHIP_EXTPROP_NAME
 * IInventory糤肂珇逆, tag: ShipInv
 */
public class ExtendShipProps implements IExtendedEntityProperties, IInventory {

	public static final String SHIP_EXTPROP_NAME = "ShipExtProps";
	public static final String tagName = "ShipInv";	//ship inventory nbt tag
    public ItemStack[] slots = new ItemStack[ContainerShipInventory.SLOTS_PLAYERINV];
    public BasicEntityShip entity;
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
		//save nbt data
		NBTTagCompound nbtExt = new NBTTagCompound();
		NBTTagCompound nbtExt_add0 = new NBTTagCompound();
		NBTTagCompound nbtExt_add1 = new NBTTagCompound();
		NBTTagCompound nbtExt_add2 = new NBTTagCompound();
		NBTTagCompound nbtExt_add3 = new NBTTagCompound();
		NBTTagCompound nbtExt_add4 = new NBTTagCompound();
		NBTTagCompound nbtExt_add5 = new NBTTagCompound();
		NBTTagCompound nbtExt_add6 = new NBTTagCompound();

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
		nbtExt_add0.setString("tagName", this.entity.getCustomNameTag());
//		//save AttrFinal
//		nbtExt.setTag("Final", nbtExt_add1);
//		nbtExt_add1.setFloat("HP", this.entity.getStateFinal(ID.HP));
//		nbtExt_add1.setFloat("ATK", this.entity.getStateFinal(ID.ATK));
//		nbtExt_add1.setFloat("DEF", this.entity.getStateFinal(ID.DEF));
//		nbtExt_add1.setFloat("SPD", this.entity.getStateFinal(ID.SPD));
//		nbtExt_add1.setFloat("MOV", this.entity.getStateFinal(ID.MOV));
//		nbtExt_add1.setFloat("HIT", this.entity.getStateFinal(ID.HIT));
//		nbtExt_add1.setFloat("ATK_H", this.entity.getStateFinal(ID.ATK_H));
//		nbtExt_add1.setFloat("ATK_AL", this.entity.getStateFinal(ID.ATK_AL));
//		nbtExt_add1.setFloat("ATK_AH", this.entity.getStateFinal(ID.ATK_AH));
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
//		//save EntityFlag
//		nbtExt.setTag("Equip", nbtExt_add5);
//		nbtExt_add5.setFloat("Cri", this.entity.getEffectEquip(ID.EF_CRI));
//		nbtExt_add5.setFloat("DHit", this.entity.getEffectEquip(ID.EF_DHIT));
//		nbtExt_add5.setFloat("THit", this.entity.getEffectEquip(ID.EF_THIT));
//		nbtExt_add5.setFloat("Miss", this.entity.getEffectEquip(ID.EF_MISS));
//		nbtExt_add5.setFloat("AA", this.entity.getEffectEquip(ID.EF_AA));
//		nbtExt_add5.setFloat("ASM", this.entity.getEffectEquip(ID.EF_ASM));
//		nbtExt_add5.setFloat("Dodge", this.entity.getEffectEquip(ID.EF_DODGE));
		//save Formation
		nbtExt.setTag("Formation", nbtExt_add6);
		nbtExt_add5.setFloat("ATK_L", this.entity.getEffectFormation(ID.Formation.ATK_L));
		nbtExt_add5.setFloat("ATK_H", this.entity.getEffectFormation(ID.Formation.ATK_H));
		nbtExt_add5.setFloat("ATK_AL", this.entity.getEffectFormation(ID.Formation.ATK_AL));
		nbtExt_add5.setFloat("ATK_AH", this.entity.getEffectFormation(ID.Formation.ATK_AH));
		nbtExt_add5.setFloat("DEF", this.entity.getEffectFormation(ID.Formation.DEF));
		nbtExt_add5.setFloat("MOV", this.entity.getEffectFormation(ID.Formation.MOV));
		nbtExt_add5.setFloat("MISS", this.entity.getEffectFormation(ID.Formation.MISS));
		nbtExt_add5.setFloat("DODGE", this.entity.getEffectFormation(ID.Formation.DODGE));
		nbtExt_add5.setFloat("CRI", this.entity.getEffectFormation(ID.Formation.CRI));
		nbtExt_add5.setFloat("DHIT", this.entity.getEffectFormation(ID.Formation.DHIT));
		nbtExt_add5.setFloat("THIT", this.entity.getEffectFormation(ID.Formation.THIT));
		nbtExt_add5.setFloat("AA", this.entity.getEffectFormation(ID.Formation.AA));
		nbtExt_add5.setFloat("ASM", this.entity.getEffectFormation(ID.Formation.ASM));
		
		//save inventory
		NBTTagList list = new NBTTagList();
		nbt.setTag(tagName, list);
		
		for(int i=0; i<slots.length; i++) {
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
		entity.setNameTag(nbt_load.getString("tagName"));
//		//load final state
//		nbt_load = (NBTTagCompound) nbt_tag.getTag("Final");
//		entity.setStateFinal(ID.HP, nbt_load.getFloat("HP"));
//		entity.setStateFinal(ID.ATK, nbt_load.getFloat("ATK"));
//		entity.setStateFinal(ID.DEF, nbt_load.getFloat("DEF"));
//		entity.setStateFinal(ID.SPD, nbt_load.getFloat("SPD"));
//		entity.setStateFinal(ID.MOV, nbt_load.getFloat("MOV"));
//		entity.setStateFinal(ID.HIT, nbt_load.getFloat("HIT"));
//		entity.setStateFinal(ID.HIT, nbt_load.getFloat("ATK_H"));
//		entity.setStateFinal(ID.HIT, nbt_load.getFloat("ATK_AL"));
//		entity.setStateFinal(ID.HIT, nbt_load.getFloat("ATK_AH"));
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
//		//load effect
//		nbt_load = (NBTTagCompound) nbt_tag.getTag("Equip");
//		entity.setEffectEquip(ID.EF_CRI, nbt_load.getFloat("Cri"));
//		entity.setEffectEquip(ID.EF_DHIT, nbt_load.getFloat("DHit"));
//		entity.setEffectEquip(ID.EF_THIT, nbt_load.getFloat("THit"));
//		entity.setEffectEquip(ID.EF_MISS, nbt_load.getFloat("Miss"));
//		entity.setEffectEquip(ID.EF_AA, nbt_load.getFloat("AA"));
//		entity.setEffectEquip(ID.EF_ASM, nbt_load.getFloat("ASM"));
//		entity.setEffectEquip(ID.EF_DODGE, nbt_load.getFloat("Dodge"));
		//save Formation
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Formation");
		entity.setEffectFormation(ID.Formation.ATK_L, nbt_load.getFloat("ATK_L"));
		entity.setEffectFormation(ID.Formation.ATK_H, nbt_load.getFloat("ATK_H"));
		entity.setEffectFormation(ID.Formation.ATK_AL, nbt_load.getFloat("ATK_AL"));
		entity.setEffectFormation(ID.Formation.ATK_AH, nbt_load.getFloat("ATK_AH"));
		entity.setEffectFormation(ID.Formation.DEF, nbt_load.getFloat("DEF"));
		entity.setEffectFormation(ID.Formation.MOV, nbt_load.getFloat("MOV"));
		entity.setEffectFormation(ID.Formation.MISS, nbt_load.getFloat("MISS"));
		entity.setEffectFormation(ID.Formation.DODGE, nbt_load.getFloat("DODGE"));
		entity.setEffectFormation(ID.Formation.CRI, nbt_load.getFloat("CRI"));
		entity.setEffectFormation(ID.Formation.DHIT, nbt_load.getFloat("DHIT"));
		entity.setEffectFormation(ID.Formation.THIT, nbt_load.getFloat("THIT"));
		entity.setEffectFormation(ID.Formation.AA, nbt_load.getFloat("AA"));
		entity.setEffectFormation(ID.Formation.ASM, nbt_load.getFloat("ASM"));
		
		//load inventory
		NBTTagList list = nbt.getTagList(tagName, 10);	//tagListずtagCompound, 腹=10

		for(int i=0; i<list.tagCount(); i++) {
			NBTTagCompound item = list.getCompoundTagAt(i);
			byte sid = item.getByte("Slot");

			if(sid>=0 && sid<slots.length) {
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
