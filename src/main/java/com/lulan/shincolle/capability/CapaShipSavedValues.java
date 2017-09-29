package com.lulan.shincolle.capability;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.NBTHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;


/**
 * ship NBT values handler
 * this is NOT a really capability, just a value handler
 */
public class CapaShipSavedValues
{
	
	public static final String SHIP_EXTPROP_NAME = "ShipExtProps";
	public static final String tagName = "ShipInv";	//ship inventory nbt tag
    private int inventoryPage = 0;

    
	//save extend entity prop
	public static NBTTagCompound saveNBTData(NBTTagCompound nbt, BasicEntityShip ship)
	{
		//save nbt data
		NBTTagCompound nbtExt = new NBTTagCompound();
		NBTTagCompound nbtExt_add0 = new NBTTagCompound();
		NBTTagCompound nbtExt_add2 = new NBTTagCompound();
		NBTTagCompound nbtExt_add3 = new NBTTagCompound();
		NBTTagCompound nbtExt_add4 = new NBTTagCompound();
		NBTTagCompound nbtExt_add5 = new NBTTagCompound();

		//save values to NBT
		nbtExt.setTag("Minor", nbtExt_add0);
		nbtExt_add0.setInteger("Level", ship.getStateMinor(ID.M.ShipLevel));
		nbtExt_add0.setInteger("Kills", ship.getStateMinor(ID.M.Kills));
		nbtExt_add0.setInteger("Exp", ship.getStateMinor(ID.M.ExpCurrent));
		nbtExt_add0.setInteger("NumAmmoL", ship.getStateMinor(ID.M.NumAmmoLight));
		nbtExt_add0.setInteger("NumAmmoH", ship.getStateMinor(ID.M.NumAmmoHeavy));
		nbtExt_add0.setInteger("NumGrudge", ship.getStateMinor(ID.M.NumGrudge));
		nbtExt_add0.setInteger("NumAirL", ship.getStateMinor(ID.M.NumAirLight));
		nbtExt_add0.setInteger("NumAirH", ship.getStateMinor(ID.M.NumAirHeavy));
		nbtExt_add0.setInteger("FMin", ship.getStateMinor(ID.M.FollowMin));
		nbtExt_add0.setInteger("FMax", ship.getStateMinor(ID.M.FollowMax));
		nbtExt_add0.setInteger("FHP", ship.getStateMinor(ID.M.FleeHP));
		nbtExt_add0.setInteger("GuardX", ship.getStateMinor(ID.M.GuardX));
		nbtExt_add0.setInteger("GuardY", ship.getStateMinor(ID.M.GuardY));
		nbtExt_add0.setInteger("GuardZ", ship.getStateMinor(ID.M.GuardZ));
		nbtExt_add0.setInteger("GuardDim", ship.getStateMinor(ID.M.GuardDim));
		nbtExt_add0.setInteger("GuardID", ship.getStateMinor(ID.M.GuardID));
		nbtExt_add0.setInteger("GuardType", ship.getStateMinor(ID.M.GuardType));
		nbtExt_add0.setInteger("PlayerUID", ship.getStateMinor(ID.M.PlayerUID));
		nbtExt_add0.setInteger("ShipUID", ship.getStateMinor(ID.M.ShipUID));
		nbtExt_add0.setInteger("FType", ship.getStateMinor(ID.M.FormatType));
		nbtExt_add0.setInteger("FPos", ship.getStateMinor(ID.M.FormatPos));
		nbtExt_add0.setInteger("Morale", ship.getStateMinor(ID.M.Morale));
		nbtExt_add0.setInteger("Food", ship.getStateMinor(ID.M.Food));
		nbtExt_add0.setInteger("Crane", ship.getStateMinor(ID.M.CraneState));
		nbtExt_add0.setInteger("WpStay", ship.getStateMinor(ID.M.WpStay));
		nbtExt_add0.setInteger("AutoCR", ship.getStateMinor(ID.M.UseCombatRation));
		nbtExt_add0.setString("tagName", ship.getCustomNameTag());
		
		//save EntityState
		nbtExt.setTag("Emotion", nbtExt_add2);	
		nbtExt_add2.setByte("State", ship.getStateEmotion(ID.S.State));
		nbtExt_add2.setByte("State2", ship.getStateEmotion(ID.S.State2));
		nbtExt_add2.setByte("Emotion", ship.getStateEmotion(ID.S.Emotion));
		nbtExt_add2.setByte("Emotion2", ship.getStateEmotion(ID.S.Emotion2));
		nbtExt_add2.setByte("Phase", ship.getStateEmotion(ID.S.Phase));
		
		//save BonusPoint
		nbtExt.setTag("Point", nbtExt_add3);	
		nbtExt_add3.setByte("HP", ship.getAttrs().getAttrsBonus(ID.AttrsBase.HP));
		nbtExt_add3.setByte("ATK", ship.getAttrs().getAttrsBonus(ID.AttrsBase.ATK));
		nbtExt_add3.setByte("DEF", ship.getAttrs().getAttrsBonus(ID.AttrsBase.DEF));
		nbtExt_add3.setByte("SPD", ship.getAttrs().getAttrsBonus(ID.AttrsBase.SPD));
		nbtExt_add3.setByte("MOV", ship.getAttrs().getAttrsBonus(ID.AttrsBase.MOV));
		nbtExt_add3.setByte("HIT", ship.getAttrs().getAttrsBonus(ID.AttrsBase.HIT));
		
		//save EntityFlag
		nbtExt.setTag("ShipFlags", nbtExt_add4);
		nbtExt_add4.setBoolean("CanFloat", ship.getStateFlag(ID.F.CanFloatUp));
		nbtExt_add4.setBoolean("IsMarried", ship.getStateFlag(ID.F.IsMarried));
		nbtExt_add4.setBoolean("NoFuel", ship.getStateFlag(ID.F.NoFuel));
		nbtExt_add4.setBoolean("Melee", ship.getStateFlag(ID.F.UseMelee));
		nbtExt_add4.setBoolean("AmmoL", ship.getStateFlag(ID.F.UseAmmoLight));
		nbtExt_add4.setBoolean("AmmoH", ship.getStateFlag(ID.F.UseAmmoHeavy));
		nbtExt_add4.setBoolean("AirL", ship.getStateFlag(ID.F.UseAirLight));
		nbtExt_add4.setBoolean("AirH", ship.getStateFlag(ID.F.UseAirHeavy));
		nbtExt_add4.setBoolean("WedEffect", ship.getStateFlag(ID.F.UseRingEffect));
		nbtExt_add4.setBoolean("CanDrop", ship.getStateFlag(ID.F.CanDrop));
		nbtExt_add4.setBoolean("CanFollow", ship.getStateFlag(ID.F.CanFollow));
		nbtExt_add4.setBoolean("OnSight", ship.getStateFlag(ID.F.OnSightChase));
		nbtExt_add4.setBoolean("PVPFirst", ship.getStateFlag(ID.F.PVPFirst));
		nbtExt_add4.setBoolean("AA", ship.getStateFlag(ID.F.AntiAir));
		nbtExt_add4.setBoolean("ASM", ship.getStateFlag(ID.F.AntiSS));
		nbtExt_add4.setBoolean("PassiveAI", ship.getStateFlag(ID.F.PassiveAI));
		nbtExt_add4.setBoolean("TimeKeeper", ship.getStateFlag(ID.F.TimeKeeper));
		nbtExt_add4.setBoolean("PickItem", ship.getStateFlag(ID.F.PickItem));
		nbtExt_add4.setBoolean("HeldItem", ship.getStateFlag(ID.F.ShowHeldItem));
		nbtExt_add4.setBoolean("AutoPump", ship.getStateFlag(ID.F.AutoPump));
		
		//save timer
		nbtExt.setTag("Timer", nbtExt_add5);
		nbtExt_add5.setInteger("Crane", ship.getStateTimer(ID.T.CraneTime));
		
		//save owner name
		if (ship.ownerName != null && ship.ownerName.length() > 0)
		{
			nbtExt.setString("Owner", ship.ownerName);
		}
		
		//save unit name
		nbtExt = NBTHelper.saveStringTagArrayList(nbtExt, "uname", ship.unitNames);
		
		nbt.setTag(SHIP_EXTPROP_NAME, nbtExt);
		
		LogHelper.debug("DEBUG: save entity ExtNBT data on id: " + ship.getEntityId());
		return nbt;
	}

	//load extend entity prop
	public static void loadNBTData(NBTTagCompound nbt, BasicEntityShip ship)
	{
		NBTTagCompound nbt_tag = (NBTTagCompound) nbt.getTag(SHIP_EXTPROP_NAME);
		NBTTagCompound nbt_load = new NBTTagCompound();
		
		//load minor state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Minor");
		ship.setStateMinor(ID.M.ShipLevel, nbt_load.getInteger("Level"));
		ship.setStateMinor(ID.M.Kills, nbt_load.getInteger("Kills"));
		ship.setStateMinor(ID.M.ExpCurrent, nbt_load.getInteger("Exp"));
		ship.setStateMinor(ID.M.NumAmmoLight, nbt_load.getInteger("NumAmmoL"));
		ship.setStateMinor(ID.M.NumAmmoHeavy, nbt_load.getInteger("NumAmmoH"));
		ship.setStateMinor(ID.M.NumGrudge, nbt_load.getInteger("NumGrudge"));
		ship.setStateMinor(ID.M.NumAirLight, nbt_load.getInteger("NumAirL"));
		ship.setStateMinor(ID.M.NumAirHeavy, nbt_load.getInteger("NumAirH"));
		ship.setStateMinor(ID.M.FollowMin, nbt_load.getInteger("FMin"));
		ship.setStateMinor(ID.M.FollowMax, nbt_load.getInteger("FMax"));
		ship.setStateMinor(ID.M.FleeHP, nbt_load.getInteger("FHP"));
		ship.setStateMinor(ID.M.GuardX, nbt_load.getInteger("GuardX"));
		ship.setStateMinor(ID.M.GuardY, nbt_load.getInteger("GuardY"));
		ship.setStateMinor(ID.M.GuardZ, nbt_load.getInteger("GuardZ"));
		ship.setStateMinor(ID.M.GuardDim, nbt_load.getInteger("GuardDim"));
		ship.setStateMinor(ID.M.GuardID, nbt_load.getInteger("GuardID"));
		ship.setStateMinor(ID.M.GuardType, nbt_load.getInteger("GuardType"));
		ship.setStateMinor(ID.M.PlayerUID, nbt_load.getInteger("PlayerUID"));
		ship.setStateMinor(ID.M.ShipUID, nbt_load.getInteger("ShipUID"));
		ship.setStateMinor(ID.M.FormatType, nbt_load.getInteger("FType"));
		ship.setStateMinor(ID.M.FormatPos, nbt_load.getInteger("FPos"));
		ship.setStateMinor(ID.M.Morale, nbt_load.getInteger("Morale"));
		ship.setStateMinor(ID.M.Food, nbt_load.getInteger("Food"));
		ship.setStateMinor(ID.M.CraneState, nbt_load.getInteger("Crane"));
		ship.setStateMinor(ID.M.WpStay, nbt_load.getInteger("WpStay"));
		ship.setStateMinor(ID.M.UseCombatRation, nbt_load.getInteger("AutoCR"));
		ship.setNameTag(nbt_load.getString("tagName"));
		
		//load emotion state
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Emotion");
		ship.setStateEmotion(ID.S.State, nbt_load.getByte("State"), false);
		ship.setStateEmotion(ID.S.State2, nbt_load.getByte("State2"), false);
		ship.setStateEmotion(ID.S.Emotion, nbt_load.getByte("Emotion"), false);
		ship.setStateEmotion(ID.S.Emotion2, nbt_load.getByte("Emotion2"), false);
		ship.setStateEmotion(ID.S.Phase, nbt_load.getByte("Phase"), false);
		
		//load bonus point
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Point");
		Attrs attrs = ship.getAttrs();
		attrs.setAttrsBonus(ID.AttrsBase.HP, nbt_load.getByte("HP"));
		attrs.setAttrsBonus(ID.AttrsBase.ATK, nbt_load.getByte("ATK"));
		attrs.setAttrsBonus(ID.AttrsBase.DEF, nbt_load.getByte("DEF"));
		attrs.setAttrsBonus(ID.AttrsBase.SPD, nbt_load.getByte("SPD"));
		attrs.setAttrsBonus(ID.AttrsBase.MOV, nbt_load.getByte("MOV"));
		attrs.setAttrsBonus(ID.AttrsBase.HIT, nbt_load.getByte("HIT"));
		
		//load flags
		nbt_load = (NBTTagCompound) nbt_tag.getTag("ShipFlags");
		ship.setStateFlag(ID.F.CanFloatUp, nbt_load.getBoolean("CanFloat"));
		ship.setStateFlag(ID.F.IsMarried, nbt_load.getBoolean("IsMarried"));
		ship.setStateFlag(ID.F.NoFuel, nbt_load.getBoolean("NoFuel"));
		ship.setStateFlag(ID.F.UseMelee, nbt_load.getBoolean("Melee"));
		ship.setStateFlag(ID.F.UseAmmoLight, nbt_load.getBoolean("AmmoL"));
		ship.setStateFlag(ID.F.UseAmmoHeavy, nbt_load.getBoolean("AmmoH"));
		ship.setStateFlag(ID.F.UseAirLight, nbt_load.getBoolean("AirL"));
		ship.setStateFlag(ID.F.UseAirHeavy, nbt_load.getBoolean("AirH"));
		ship.setStateFlag(ID.F.UseRingEffect, nbt_load.getBoolean("WedEffect"));
		ship.setStateFlag(ID.F.CanDrop, nbt_load.getBoolean("CanDrop"));
		ship.setStateFlag(ID.F.CanFollow, nbt_load.getBoolean("CanFollow"));
		ship.setStateFlag(ID.F.OnSightChase, nbt_load.getBoolean("OnSight"));
		ship.setStateFlag(ID.F.PVPFirst, nbt_load.getBoolean("PVPFirst"));
		ship.setStateFlag(ID.F.AntiAir, nbt_load.getBoolean("AA"));
		ship.setStateFlag(ID.F.AntiSS, nbt_load.getBoolean("ASM"));
		ship.setStateFlag(ID.F.PassiveAI, nbt_load.getBoolean("PassiveAI"));
		ship.setStateFlag(ID.F.TimeKeeper, nbt_load.getBoolean("TimeKeeper"));
		ship.setStateFlag(ID.F.PickItem, nbt_load.getBoolean("PickItem"));
		ship.setStateFlag(ID.F.ShowHeldItem, nbt_load.getBoolean("HeldItem"));
		ship.setStateFlag(ID.F.AutoPump, nbt_load.getBoolean("AutoPump"));
		
		//load timer
		nbt_load = (NBTTagCompound) nbt_tag.getTag("Timer");
		ship.setStateTimer(ID.T.CraneTime, nbt_load.getInteger("Crane"));
		
		//load owner name
		String name = nbt_tag.getString("Owner");
		if (name != null && name.length() > 0)
		{
			ship.ownerName = name;
		}
		else
		{
			//load owner name fail, get owner again
			Entity owner = ship.getHostEntity();
			
			if (owner instanceof EntityPlayer)
			{
				ship.ownerName = ((EntityPlayer) owner).getName();
			}
		}
		
		//load unit name
		ship.unitNames = NBTHelper.loadStringTagArrayList(nbt_tag, "uname");
		
		//calc equip and attribute
		ship.setExpNext();	//for gui display
		ship.calcShipAttributes(31, true);	//re-calc attributes and send sync packet
		
		LogHelper.debug("DEBUG : load entity ExtNBT data on id: " + ship.getEntityId());
	}
	
	
}