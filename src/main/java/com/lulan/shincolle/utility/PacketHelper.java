package com.lulan.shincolle.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class PacketHelper
{

	public PacketHelper() {}
	
	/**
	 * send array
	 * 0:size, 1~N:data
	 */
	public static void sendArrayByte(ByteBuf buf, byte[] data)
	{
		if (data != null)
		{
			//send size
			buf.writeInt(data.length);
			
			//send data
			for (byte value : data) buf.writeByte(value);
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/**
	 * send array
	 * 0:size, 1~N:data
	 */
	public static void sendArrayBoolean(ByteBuf buf, boolean[] data)
	{
		if (data != null)
		{
			//send size
			buf.writeInt(data.length);
			
			//send data
			for (boolean value : data) buf.writeBoolean(value);
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/**
	 * send array
	 * 0:size, 1~N:data
	 */
	public static void sendArrayInt(ByteBuf buf, int[] data)
	{
		if (data != null)
		{
			//send size
			buf.writeInt(data.length);
			
			//send data
			for (int value : data) buf.writeInt(value);
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/**
	 * send array
	 * 0:size, 1~N:data
	 */
	public static void sendArrayFloat(ByteBuf buf, float[] data)
	{
		if (data != null)
		{
			//send size
			buf.writeInt(data.length);
			
			//send data
			for (float value : data) buf.writeFloat(value);
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/**
	 * send array
	 * 0:size, 1~N:data
	 */
	public static void sendArrayDouble(ByteBuf buf, double[] data)
	{
		if (data != null)
		{
			//send size
			buf.writeInt(data.length);
			
			//send data
			for (double value : data) buf.writeDouble(value);
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/**
	 * send integer map
	 * 0:size, 1~N:data: key1, value1, key2, value2, ...
	 */
	public static void sendMapInt(ByteBuf buf, Map<Integer, Integer> data)
	{
		if (data != null)
		{
			Iterator iter = data.entrySet().iterator();
			
			//send size
			buf.writeInt(data.size());
			
			//send data
			while (iter.hasNext())
			{
				Map.Entry<Integer, Integer> entry = (Entry<Integer, Integer>) iter.next();
				buf.writeInt(entry.getKey());
				buf.writeInt(entry.getValue());
			}
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/** send integer list data
	 *  stream: 0:size 1~N:data
	 */
	public static void sendListInt(ByteBuf buf, List<Integer> data)
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
	 *  
	 *  NOTE: 不能發送null string, 否則會跳一堆NPE (只會貼log 不會crash)
	 */
	public static void sendListString(ByteBuf buf, List<String> data)
	{
		if (data != null)
		{
			Iterator iter = data.iterator();
			
			//send size
			buf.writeInt(data.size());
			
			//send data
			while (iter.hasNext())
			{
				String str = (String) iter.next();
				
				if (str == null)
				{
					ByteBufUtils.writeUTF8String(buf, "");
				}
				else
				{
					ByteBufUtils.writeUTF8String(buf, str);
				}
			}
		}
		else
		{
			buf.writeInt(-1);
		}
	}
	
	/** get int map data */
	public static Map<Integer, Integer> readMapInt(ByteBuf buf)
	{
		Map<Integer, Integer> getMap = new HashMap<Integer, Integer>();
		
		//get size
		int size = buf.readInt();
		
		//get data
		if (size > 0)
		{
			for (int i = 0; i < size; ++i)
			{
				getMap.put(buf.readInt(), buf.readInt());
			}
		}
		
		return getMap;
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
	
	/** get double array data */
	public static double[] readDoubleArray(ByteBuf buf)
	{
		int length = buf.readInt();
		double[] array = new double[length];
		
		if (length > 0)
		{
			array = readDoubleArray(buf, length);
		}
		
		return array;
	}
	
	/** get double array data */
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
	public static int[] readIntArray(ByteBuf buf)
	{
		int length = buf.readInt();
		int[] array = new int[length];
		
		if (length > 0)
		{
			array = readIntArray(buf, length);
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
	public static float[] readFloatArray(ByteBuf buf)
	{
		int length = buf.readInt();
		float[] array = new float[length];
		
		if (length > 0)
		{
			array = readFloatArray(buf, length);
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
	
	/** get boolean array data */
	public static boolean[] readBooleanArray(ByteBuf buf)
	{
		int length = buf.readInt();
		boolean[] array = new boolean[length];
		
		if (length > 0)
		{
			array = readBooleanArray(buf, length);
		}
		
		return array;
	}
	
	/** get boolean array data */
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
	public static byte[] readByteArray(ByteBuf buf)
	{
		int length = buf.readInt();
		byte[] array = new byte[length];
		
		if (length > 0)
		{
			array = readByteArray(buf, length);
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
			case ID.B.ShipInv_ShowHeld:
				entity.setStateFlagI(ID.F.ShowHeldItem, value);
			break;
			case ID.B.ShipInv_AutoCR:
				entity.setStateMinor(ID.M.UseCombatRation, value);
			break;
			case ID.B.ShipInv_AutoPump:
				entity.setStateFlagI(ID.F.AutoPump, value);
			break;
			case ID.B.ShipInv_ModelState01:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[0], false);
			break;
			case ID.B.ShipInv_ModelState02:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[1], false);
			break;
			case ID.B.ShipInv_ModelState03:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[2], false);
			break;
			case ID.B.ShipInv_ModelState04:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[3], false);
			break;
			case ID.B.ShipInv_ModelState05:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[4], false);
			break;
			case ID.B.ShipInv_ModelState06:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[5], false);
			break;
			case ID.B.ShipInv_ModelState07:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[6], false);
			break;
			case ID.B.ShipInv_ModelState08:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[7], false);
			break;
			case ID.B.ShipInv_ModelState09:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[8], false);
			break;
			case ID.B.ShipInv_ModelState10:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[9], false);
			break;
			case ID.B.ShipInv_ModelState11:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[10], false);
			break;
			case ID.B.ShipInv_ModelState12:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[11], false);
			break;
			case ID.B.ShipInv_ModelState13:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[12], false);
			break;
			case ID.B.ShipInv_ModelState14:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[13], false);
			break;
			case ID.B.ShipInv_ModelState15:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[14], false);
			break;
			case ID.B.ShipInv_ModelState16:
				entity.setStateEmotion(ID.S.State, entity.getStateEmotion(ID.S.State) ^ Values.N.Pow2[15], false);
			break;
			case ID.B.ShipInv_Task:
				entity.setStateMinor(ID.M.Task, value);
			break;
			case ID.B.ShipInv_TaskSide:
				entity.setStateMinor(ID.M.TaskSide, value);
			break;
			}
		}
		else
		{
			LogHelper.debug("DEBUG: set entity by GUI fail, entity null");
		}
	}
	
	/**
	 * process tile GUI click
	 */
	public static void setTileEntityByGUI(TileEntity tile, int button, int value, int value2)
	{
		if (tile instanceof TileEntitySmallShipyard)
		{
			TileEntitySmallShipyard tile2 = (TileEntitySmallShipyard) tile;
			tile2.setBuildType(value);
			
			//set build record
			if (value == ID.Build.EQUIP_LOOP || value == ID.Build.SHIP_LOOP)
			{
				int[] getMat = new int[] {0,0,0,0};
				
				for (int i = 0; i < 4; i++)
				{
					if (tile2.getStackInSlot(i) != null)
					{
						getMat[i] = tile2.getStackInSlot(i).stackSize;
					}
				}
				
				tile2.setBuildRecord(getMat);
			}
			
			return;
		}
		else if (tile instanceof TileMultiGrudgeHeavy)
		{
			TileMultiGrudgeHeavy tile2 = (TileMultiGrudgeHeavy) tile;
			
			switch (button)
			{
			case ID.B.Shipyard_Type:		//build type
				tile2.setBuildType(value);
			break;
			case ID.B.Shipyard_InvMode:		//select inventory mode
				tile2.setInvMode(value);
			break;
			case ID.B.Shipyard_SelectMat:	//select material
				tile2.setSelectMat(value);
			break;
			case ID.B.Shipyard_INCDEC:		//material inc,dec
				TileEntityHelper.setLargeShipyardBuildMats((TileMultiGrudgeHeavy) tile, button, value, value2);
			break;
			}//end switch
		}
		else if (tile instanceof TileEntityCrane)
		{
			TileEntityCrane tile2 = (TileEntityCrane) tile;
			
			switch (button)
			{
			case ID.B.Crane_Load:
				tile2.setField(6, value);
			break;
			case ID.B.Crane_Unload:
				tile2.setField(7, value);
			break;
			case ID.B.Crane_Power:
				tile2.setField(2, value);
				
				//power off, clear ship
				if (value == 0)
				{
					tile2.setShip(null);
				}
			break;
			case ID.B.Crane_Meta:
				tile2.setField(3, value);
			break;
			case ID.B.Crane_Dict:
				tile2.setField(4, value);
			break;
			case ID.B.Crane_Mode:
				tile2.setField(5, value);
			break;
			case ID.B.Crane_Nbt:
				tile2.setField(8, value);
			break;
			case ID.B.Crane_Red:
				if (value > 2) value = 0;
				tile2.setField(10, value);
			break;
			case ID.B.Crane_Liquid:
				if (value > 2) value = 0;
				tile2.setField(12, value);
			break;
			case ID.B.Crane_Energy:
				if (value > 2) value = 0;
				tile2.setField(13, value);
			break;
			}//end switch
		}
		else if (tile instanceof TileEntityVolCore)
		{
			TileEntityVolCore tile2 = (TileEntityVolCore) tile;
			
			switch (button)
			{
			case ID.B.VolCore_Power:
				tile2.setField(0, value);
			break;
			}//end switch
		}
		else
		{
			LogHelper.debug("DEBUG: set tile entity by GUI fail: tile: "+tile);
		}	
	}
	
	/**
	 * send S2CEntitySync packet
	 * 
	 * type 0:  SyncEntity_PlayerUID
	 * 
	 */
	public static void sendS2CEntitySync(int type, Object host, World world, BlockPos targetPos, EntityPlayer targetPlayer)
	{
		if (!world.isRemote)
		{
			TargetPoint point = null;
			if (targetPos != null) point = new TargetPoint(world.provider.getDimension(), targetPos.getX(), targetPos.getY(), targetPos.getZ(), 64D);
			
			switch (type)
			{
			case 0:
				if (point != null)
				{
					CommonProxy.channelE.sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncEntity_PlayerUID), point);
				}
				else if (targetPlayer != null)
				{
					CommonProxy.channelE.sendTo(new S2CEntitySync(host, S2CEntitySync.PID.SyncEntity_PlayerUID), (EntityPlayerMP) targetPlayer);
				}
			break;
			}
		}
	}
	
	/** send itemstack, copy from PacketBuffer.class */
	public static void sendItemStack(ByteBuf buf, ItemStack stack)
	{
		if (stack == null)
        {
			buf.writeShort(-1);
        }
        else
        {
        	buf.writeShort(Item.getIdFromItem(stack.getItem()));
        	buf.writeByte(stack.stackSize);
        	buf.writeShort(stack.getMetadata());
            NBTTagCompound nbttagcompound = null;

            if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
            {
                nbttagcompound = stack.getItem().getNBTShareTag(stack);
            }
            
            writeCompoundTag(buf, nbttagcompound);
        }
	}
	
	/** read itemstack, copy from PacketBuffer.class */
	public static ItemStack readItemStack(ByteBuf buf)
    {
        ItemStack itemstack = null;
        int i = buf.readShort();

        if (i >= 0)
        {
            int j = buf.readByte();
            int k = buf.readShort();
            itemstack = new ItemStack(Item.getItemById(i), j, k);
            itemstack.setTagCompound(readCompoundTag(buf));
        }

        return itemstack;
    }
	
    /** Writes a compressed NBTTagCompound to this buffer, copy from PacketBuffer.class */
    public static void writeCompoundTag(ByteBuf buf, @Nullable NBTTagCompound nbt)
    {
        if (nbt == null)
        {
        	buf.writeByte(0);
        }
        else
        {
            try
            {
                CompressedStreamTools.write(nbt, new ByteBufOutputStream(buf));
            }
            catch (IOException e)
            {
            	LogHelper.info("EXCEPTION: NBT packet: send data fail: "+e);
				e.printStackTrace();
				return;
            }
        }
    }

    /** Reads a compressed NBTTagCompound from this buffer, copy from PacketBuffer.class */
    @Nullable
    public static NBTTagCompound readCompoundTag(ByteBuf buf)
    {
        int i = buf.readerIndex();
        byte b0 = buf.readByte();

        if (b0 == 0)
        {
            return null;
        }
        else
        {
        	buf.readerIndex(i);

            try
            {
                return CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(2097152L));
            }
            catch (IOException e)
            {
            	LogHelper.info("EXCEPTION: NBT packet: get data fail: "+e);
				e.printStackTrace();
				return null;
            }
        }
    }
	
	
}