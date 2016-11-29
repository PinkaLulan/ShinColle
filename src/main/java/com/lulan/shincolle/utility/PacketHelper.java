package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketHelper
{

	public PacketHelper() {}
	
	/** send integer list data
	 *  stream: 0:size 1~N:data
	 */
	public static void sendListInt(ByteBuf buf, List data)
	{
		if (data != null)
		{
			Iterator iter = data.iterator();
			
			//send size
			buf.writeInt(data.size());
			
			//send data
			while (iter.hasNext())
			{
				buf.writeInt((Integer) iter.next());
			}
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/** send string list data
	 *  steam: 0:size 1~N:string
	 */
	public static void sendListString(ByteBuf buf, List data)
	{
		if (data != null)
		{
			Iterator iter = data.iterator();
			
			//send size
			buf.writeInt(data.size());
			
			//send data
			while (iter.hasNext())
			{
				ByteBufUtils.writeUTF8String(buf, (String) iter.next());
			}
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/** get int list data */
	public static List<Integer> readListInt(ByteBuf buf)
	{
		List<Integer> getlist = new ArrayList();
		
		//get size
		int size = buf.readInt();
		
		//get data
		if (size > 0)
		{
			for (int i = 0; i < size; ++i)
			{
				getlist.add(buf.readInt());
			}
		}
		
		return getlist;
	}
	
	/** get string list data */
	public static List<String> readListString(ByteBuf buf)
	{
		List<String> getlist = new ArrayList();
		
		//get size
		int size = buf.readInt();
		
		//get data
		if (size > 0)
		{
			for (int i = 0; i < size; ++i)
			{
				getlist.add(ByteBufUtils.readUTF8String(buf));
			}
		}
		
		return getlist;
	}
	
	/** send string data */
	public static void sendString(ByteBuf buf, String str)
	{
		if (str != null)
		{
			buf.writeInt(1);  //has string
			ByteBufUtils.writeUTF8String(buf, str);
		}
		else
		{
			buf.writeInt(-1);  //no string
		}
	}
	
	/** get string data */
	public static String readString(ByteBuf buf)
	{
		String str = null;
		int flag = buf.readInt();
		
		if (flag > 0)
		{
			str = ByteBufUtils.readUTF8String(buf);
		}
		
		return str;
	}
	
	/** get float array data */
	public static double[] readDoubleArray(ByteBuf buf, int length)
	{
		double[] array = new double[length];
		
		for (int i = 0; i < length; i++)
		{
			array[i] = buf.readDouble();
		}
		
		return array;
	}
	
	/** get int array data */
	public static int[] readIntArray(ByteBuf buf, int length)
	{
		int[] array = new int[length];
		
		for (int i = 0; i < length; i++)
		{
			array[i] = buf.readInt();
		}
		
		return array;
	}
	
	/** get float array data */
	public static float[] readFloatArray(ByteBuf buf, int length)
	{
		float[] array = new float[length];
		
		for (int i = 0; i < length; i++)
		{
			array[i] = buf.readFloat();
		}
		
		return array;
	}
	
	/** get float array data */
	public static boolean[] readBooleanArray(ByteBuf buf, int length)
	{
		boolean[] array = new boolean[length];
		
		for (int i = 0; i < length; i++)
		{
			array[i] = buf.readBoolean();
		}
		
		return array;
	}
	
	/** get byte array data */
	public static byte[] readByteArray(ByteBuf buf, int length)
	{
		byte[] array = new byte[length];
		
		for (int i = 0; i < length; i++)
		{
			array[i] = buf.readByte();
		}
		
		return array;
	}
	
	/** discard key, turn string data to list */
	public static ArrayList<String> getStringListFromStringMap(Map map)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		if (map != null)
		{
			map.forEach((k, v) ->
			{
				list.add((String) v);
			});
		}
	
		return list;
	}
	
	/** process GUI click */
	public static void setEntityByGUI(BasicEntityShip entity, int button, int value)
	{
		if (entity != null)
		{
			switch (button)
			{
			case ID.B.ShipInv_Melee:
				entity.setStateFlagI(ID.F.UseMelee, value);
				break;
			case ID.B.ShipInv_AmmoLight:
				entity.setStateFlagI(ID.F.UseAmmoLight, value);
				break;
			case ID.B.ShipInv_AmmoHeavy:
				entity.setStateFlagI(ID.F.UseAmmoHeavy, value);
				break;
			case ID.B.ShipInv_AirLight:
				entity.setStateFlagI(ID.F.UseAirLight, value);
				break;
			case ID.B.ShipInv_AirHeavy:
				entity.setStateFlagI(ID.F.UseAirHeavy, value);
				break;
			case ID.B.ShipInv_FollowMin:
				entity.setStateMinor(ID.M.FollowMin, value);
				
				//if min > max, max = min+1
				if (entity.getStateMinor(ID.M.FollowMin) >= entity.getStateMinor(ID.M.FollowMax))
				{
					entity.setStateMinor(ID.M.FollowMax, value + 1);
				}
				break;
			case ID.B.ShipInv_FollowMax:
				entity.setStateMinor(ID.M.FollowMax, value);
				
				//if max < min, min = max-1
				if (entity.getStateMinor(ID.M.FollowMax) <= entity.getStateMinor(ID.M.FollowMin))
				{
					entity.setStateMinor(ID.M.FollowMin, value - 1);
				}
				break;
			case ID.B.ShipInv_FleeHP:
				entity.setStateMinor(ID.M.FleeHP, value);
				break;
			case ID.B.ShipInv_TarAI:
				entity.setStateFlagI(ID.F.PassiveAI, value);
				break;
			case ID.B.ShipInv_AuraEffect:
				entity.setStateFlagI(ID.F.UseRingEffect, value);
				break;
			case ID.B.ShipInv_OnSightAI:
				entity.setStateFlagI(ID.F.OnSightChase, value);
				break;
			case ID.B.ShipInv_PVPAI:
				entity.setStateFlagI(ID.F.PVPFirst, value);
				break;
			case ID.B.ShipInv_AAAI:
				entity.setStateFlagI(ID.F.AntiAir, value);
				break;
			case ID.B.ShipInv_ASMAI:
				entity.setStateFlagI(ID.F.AntiSS, value);
				break;
			case ID.B.ShipInv_TIMEKEEPAI:
				entity.setStateFlagI(ID.F.TimeKeeper, value);
				break;
			case ID.B.ShipInv_InvPage:
				entity.getCapaShipInventory().setInventoryPage(value);
				break;
			case ID.B.ShipInv_PickitemAI:
				entity.setStateFlagI(ID.F.PickItem, value);
				break;
			case ID.B.ShipInv_WpStay:
				entity.setStateMinor(ID.M.WpStay, value);
				break;
			}
		}
		else
		{
			LogHelper.info("DEBUG : set entity by GUI fail, entity null");
		}
	}
	
	
}
