package com.lulan.shincolle.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityProjectileStatic;
import com.lulan.shincolle.handler.AttrStateHandler;
import com.lulan.shincolle.handler.IPacketEntity;
import com.lulan.shincolle.handler.IStateEntity;
import com.lulan.shincolle.handler.StateHandler;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CReactPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.AttrBoo;
import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.reference.Enums.AttrStr;
import com.lulan.shincolle.reference.Enums.ParType;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.dataclass.Attrs;
import com.lulan.shincolle.reference.dataclass.AttrsAdv;
import com.lulan.shincolle.reference.dataclass.ParticleData;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * packet helper
 * 
 * send type:
 *   IMMEDIATELY: send packet on method call
 *   TICKING: send packet on entity's ticking method call
 */
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
    
    /** send float list data
     *  stream: 0:size 1~N:data
     */
    public static void sendListFloat(ByteBuf buf, List<Float> data)
    {
        if (data != null)
        {
            Iterator iter = data.iterator();
            
            //send size
            buf.writeInt(data.size());
            
            //send data
            while (iter.hasNext())
            {
                buf.writeFloat((Float) iter.next());
            }
        }
        else
        {
            buf.writeInt(-1);
        }
    }
    
    /** send boolean list data
     *  stream: 0:size 1~N:data
     */
    public static void sendListBoolean(ByteBuf buf, List<Boolean> data)
    {
        if (data != null)
        {
            Iterator iter = data.iterator();
            
            //send size
            buf.writeInt(data.size());
            
            //send data
            while (iter.hasNext())
            {
                buf.writeBoolean((Boolean) iter.next());
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
        List<Integer> getlist = new ArrayList<Integer>();
        
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
    
    /** get float list data */
    public static List<Float> readListFloat(ByteBuf buf)
    {
        List<Float> getlist = new ArrayList<Float>();
        
        //get size
        int size = buf.readInt();
        
        //get data
        if (size > 0)
        {
            for (int i = 0; i < size; ++i)
            {
                getlist.add(buf.readFloat());
            }
        }
        
        return getlist;
    }
    
    /** get boolean list data */
    public static List<Boolean> readListBoolean(ByteBuf buf)
    {
        List<Boolean> getlist = new ArrayList<Boolean>();
        
        //get size
        int size = buf.readInt();
        
        //get data
        if (size > 0)
        {
            for (int i = 0; i < size; ++i)
            {
                getlist.add(buf.readBoolean());
            }
        }
        
        return getlist;
    }
    
    /** get string list data */
    public static List<String> readListString(ByteBuf buf)
    {
        List<String> getlist = new ArrayList<String>();
        
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
    
    /** get double array data, read array length first */
    public static double[] readDoubleArray(ByteBuf buf)
    {
        int length = buf.readInt();
        if (length > 0) return readDoubleArray(buf, length);
        return new double[0];
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
    
    /** get int array data, read array length first */
    public static int[] readIntArray(ByteBuf buf)
    {
        int length = buf.readInt();
        if (length > 0) return readIntArray(buf, length);
        return new int[0];
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
    
    /** get float array data, read array length first */
    public static float[] readFloatArray(ByteBuf buf)
    {
        int length = buf.readInt();
        if (length > 0) return readFloatArray(buf, length);
        return new float[0];
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
    
    /** get boolean array data, read array length first */
    public static boolean[] readBooleanArray(ByteBuf buf)
    {
        int length = buf.readInt();
        if (length > 0) return readBooleanArray(buf, length);
        return new boolean[0];
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
    
    /** get byte array data, read array length first */
    public static byte[] readByteArray(ByteBuf buf)
    {
        int length = buf.readInt();
        if (length > 0) return readByteArray(buf, length);
        return new byte[0];
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
    
    /** send flare packet */
    public static void sendFlare(IPacketEntity host, BlockPos target)
    {
        CommonProxy.getChannelRequest().sendToAllAround(new S2CReactPackets(S2CReactPackets.PID.FlareEffect, target.getX(), target.getY(), target.getZ()), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send spawn particle packet, SERVER SIDE ONLY */
    @SideOnly(Side.SERVER)
    public static void sendParticle(IPacketEntity host, ParticleData data)
    {
        CommonProxy.getChannelParticle().sendToAllAround(new S2CSpawnParticle(data), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send attack time for model display, SERVER SIDE ONLY */
    @SideOnly(Side.SERVER)
    public static void sendAttackTime(IStateEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncEntity_AttackTime), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send all attributes, ex: open GUI or create new ship, SERVER SIDE ONLY */
    @SideOnly(Side.SERVER)
    public static void sendAttrsAll(IStateEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncShip_All), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (TICKING) send updated attributes, SERVER SIDE ONLY */
    @SideOnly(Side.SERVER)
    public static void sendAttrs(IStateEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncShip_Update), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send rider entity id, SERVER SIDE ONLY */
    @SideOnly(Side.SERVER)
    public static void sendRiders(IPacketEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncShip_Riders), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send buff map, SERVER SIDE ONLY */
    @SideOnly(Side.SERVER)
    public static void sendBuffMap(IPacketEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncShip_Buffmap), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /**
     * (IMMEDIATELY) request data sync type 1, CLIENT SIDE ONLY
     * 
     * 0: model display
     * 1: unit name
     * 2: buff map
     * 
     */
    @SideOnly(Side.CLIENT)
    public static void sendSyncRequest(IStateEntity host, int type)
    {
        switch (type)
        {
        case 0:   //model display
            if (host instanceof IShipMorph)
            {
                IShipMorph mhost = (IShipMorph) host;
                if (mhost.isMorph() && mhost.getMorphHost() != null)
                {
                    CommonProxy.getChannelRequest().sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, mhost.getMorphHost().getEntityId(), mhost.getMorphHost().world.provider.getDimension()));
                }
            }
            else if (host instanceof Entity)
            {
                CommonProxy.getChannelRequest().sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, ((Entity) host).getEntityId(), ((Entity) host).world.provider.getDimension()));
            }
        break;
        case 1:   //unit name
            CommonProxy.getChannelRequest().sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_UnitName, ((Entity) host).getEntityId(), ((Entity) host).world.provider.getDimension()));
        break;
        case 2:   //buff map
            CommonProxy.getChannelRequest().sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_Buffmap, ((Entity) host).getEntityId(), ((Entity) host).world.provider.getDimension()));
        break;
        }
    }
    
    /** process packet: send all attrs */
    public static void processSendAttrsAll(IStateEntity host, ByteBuf buf)
    {
        StateHandler sh = host.getStateHandler();
        
        /* send number data */
        EnumMap<AttrNum, Number> nummap = sh.getAllNumber();
        //send size
        buf.writeInt(nummap.size());
        //send data
        nummap.forEach((k, v) -> 
        {
            //send enum key
            buf.writeInt(k.ordinal());
            //send number value
            if (v instanceof Integer)
            {
                buf.writeByte(0);
                buf.writeInt(v.intValue());
            }
            else if (v instanceof Float)
            {
                buf.writeByte(1);
                buf.writeFloat(v.floatValue());
            }
            else if (v instanceof Byte)
            {
                buf.writeByte(2);
                buf.writeByte(v.byteValue());
            }
            else if (v instanceof Short)
            {
                buf.writeByte(3);
                buf.writeShort(v.shortValue());
            }
            else if (v instanceof Double)
            {
                buf.writeByte(4);
                buf.writeDouble(v.doubleValue());
            }
            else if (v instanceof Long)
            {
                buf.writeByte(5);
                buf.writeLong(v.longValue());
            }
            else  //other number type = treat as float
            {
                buf.writeByte(1);
                buf.writeFloat(v.floatValue());
            }
        });  //end send number data
        
        /* send boolean data */
        EnumMap<AttrBoo, Boolean> boomap = sh.getAllBoolean();
        //send size
        buf.writeInt(boomap.size());
        //send data
        boomap.forEach((k, v) -> 
        {
            //send enum key
            buf.writeInt(k.ordinal());
            //send value
            buf.writeBoolean(v);
        });  //end send boolean data
        
        /* send vanilla data */
        buf.writeBoolean(sh.isSitting());
        buf.writeBoolean(sh.isSneaking());
        buf.writeBoolean(sh.isSprinting());
        
        /* send string data */
        EnumMap<AttrStr, String> strmap = sh.getAllString();
        //send size
        buf.writeInt(strmap.size());
        //send data
        strmap.forEach((k, v) -> 
        {
            //send enum key
            buf.writeInt(k.ordinal());
            //send value
            PacketHelper.sendString(buf, v);
        });  //end send string data
        
        /* send attrs data */
        if (sh instanceof AttrStateHandler)
        {
            buf.writeBoolean(true);  //flag for AttrStateHandler
            Attrs attr = ((AttrStateHandler) sh).getAttrs();
            
            //send attrs bonus
            byte[] data1 = attr.getAttrsBonus();
            PacketHelper.sendArrayByte(buf, data1);
            //send attrs type
            float[] adata = attr.getAttrsType();
            PacketHelper.sendArrayFloat(buf, adata);
            //send attrs raw
            adata = attr.getAttrsRaw();
            PacketHelper.sendArrayFloat(buf, adata);
            //send attrs equip
            adata = attr.getAttrsEquip();
            PacketHelper.sendArrayFloat(buf, adata);
            //send attrs potion
            adata = attr.getAttrsPotion();
            PacketHelper.sendArrayFloat(buf, adata);
            
            /* send advanced attrs data */
            if (attr instanceof AttrsAdv)
            {
                buf.writeBoolean(true);  //flag for AttrsAdv
                AttrsAdv attradv = (AttrsAdv) attr;
                
                //send formation
                adata = attradv.getAttrsFormation();
                PacketHelper.sendArrayFloat(buf, adata);
                //send morale
                adata = attradv.getAttrsMorale();
                PacketHelper.sendArrayFloat(buf, adata);
                //send min MOV
                buf.writeFloat(attradv.getMinMOV());
            }
            else
            {
                buf.writeBoolean(false);  //flag for AttrsAdv
            }
        }//end send attrs
        else
        {
            buf.writeBoolean(false);  //flag for AttrStateHandler
        }
    }
    
    /** process packet: get packet data and save as temp */
    public static ArrayList processGetAttrsAll(ByteBuf buf)
    {
        ArrayList datatemp = new ArrayList();
        
        /* get number data */
        //get size
        int dsize = buf.readInt();
        datatemp.add(dsize);
        //get data
        for (int i = 0; i < dsize; i++)
        {
            //get enum key
            int intenumkey = buf.readInt();
            datatemp.add(intenumkey);
            AttrNum enumkey = AttrNum.values()[intenumkey];
            Number dnum = null;
            //get data
            byte switchTemp = buf.readByte();
            datatemp.add(switchTemp);
            switch (switchTemp)
            {
            case 0:  //int
                dnum = new Integer(buf.readInt());
            break;
            case 2:  //byte
                dnum = new Byte(buf.readByte());
            break;
            case 3:  //short
                dnum = new Short(buf.readShort());
            break;
            case 4:  //double
                dnum = new Double(buf.readDouble());
            break;
            case 5:  //long
                dnum = new Long(buf.readLong());
            break;
            case 1:  //float or other
            default:
                dnum = new Float(buf.readFloat());
            break;
            }
            //save data into list
            datatemp.add(dnum);
        }
        
        /* get boolean data */
        //get size
        datatemp.add(buf.readInt());
        //get data
        for (int i = 0; i < dsize; i++)
        {
            datatemp.add(buf.readInt());
            datatemp.add(buf.readBoolean());
        }
        
        /* get vanilla data */
        datatemp.add(buf.readBoolean());
        datatemp.add(buf.readBoolean());
        datatemp.add(buf.readBoolean());
        
        /* get string data */
        //get size
        datatemp.add(buf.readInt());
        //get data
        for (int i = 0; i < dsize; i++)
        {
            datatemp.add(buf.readInt());
            datatemp.add(PacketHelper.readString(buf));
        }
        
        /* get attrs data */
        boolean flag = buf.readBoolean();
        datatemp.add(flag);
        if (flag)
        {
            //get attrs bonus
            byte[] data1 = PacketHelper.readByteArray(buf);
            datatemp.add(data1);
            //get attrs type
            float[] adata = PacketHelper.readFloatArray(buf);
            datatemp.add(adata);
            //get attrs raw
            adata = PacketHelper.readFloatArray(buf);
            datatemp.add(adata);
            //get attrs equip
            adata = PacketHelper.readFloatArray(buf);
            datatemp.add(adata);
            //get attrs potion
            adata = PacketHelper.readFloatArray(buf);
            datatemp.add(adata);
            
            /* get advanced attrs data */
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                //get formation
                adata = PacketHelper.readFloatArray(buf);
                datatemp.add(adata);
                //get morale
                adata = PacketHelper.readFloatArray(buf);
                datatemp.add(adata);
                //get min MOV
                datatemp.add(buf.readFloat());
            }
        }//end get attrs
        
        return datatemp;
    }
    
    /** process packet: set all attrs from packet data temp */
    public static void processSetAttrsAll(IStateEntity host, List data)
    {
        StateHandler sh = host.getStateHandler();
        int listid = 0;
        
        /* get number data */
        EnumMap<AttrNum, Number> nummap = sh.getAllNumber();
        //get size
        int dsize = (Integer) data.get(listid++);
        //get data
        for (int i = 0; i < dsize; i++)
        {
            //get enum key
            AttrNum enumkey = AttrNum.values()[(Integer) data.get(listid++)];
            Number dnum = null;
            //get data
            switch ((Byte) data.get(listid++))
            {
            case 0:  //int
                dnum = (Integer) data.get(listid++);
            break;
            case 2:  //byte
                dnum = (Byte) data.get(listid++);
            break;
            case 3:  //short
                dnum = (Short) data.get(listid++);
            break;
            case 4:  //double
                dnum = (Double) data.get(listid++);
            break;
            case 5:  //long
                dnum = (Long) data.get(listid++);
            break;
            case 1:  //float or other
            default:
                dnum = (Float) data.get(listid++);
            break;
            }
            //save data into map
            nummap.put(enumkey, dnum);
        }
        
        /* get boolean data */
        EnumMap<AttrBoo, Boolean> boomap = sh.getAllBoolean();
        //get size
        dsize = (Integer) data.get(listid++);
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrBoo enumkey = AttrBoo.values()[(Integer) data.get(listid++)];
            Boolean bdata = (Boolean) data.get(listid++);
            boomap.put(enumkey, bdata);
        }
        
        /* get vanilla data */
        sh.setIsSitting((Boolean) data.get(listid++));
        sh.setIsSneaking((Boolean) data.get(listid++));
        sh.setIsSprinting((Boolean) data.get(listid++));
        
        /* get string data */
        EnumMap<AttrStr, String> strmap = sh.getAllString();
        //get size
        dsize = (Integer) data.get(listid++);
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrStr enumkey = AttrStr.values()[(Integer) data.get(listid++)];
            String str = (String) data.get(listid++);
            strmap.put(enumkey, str);
        }
        
        /* get attrs data */
        if ((Boolean) data.get(listid++))
        {
            Attrs attr = ((AttrStateHandler) sh).getAttrs();
            
            //get attrs bonus
            attr.setAttrsBonus((byte[]) data.get(listid++));
            //get attrs type
            attr.setAttrsType((float[]) data.get(listid++));
            //get attrs raw
            attr.setAttrsRaw((float[]) data.get(listid++));
            //get attrs equip
            attr.setAttrsEquip((float[]) data.get(listid++));
            //get attrs potion
            attr.setAttrsPotion((float[]) data.get(listid++));
            
            /* get advanced attrs data */
            if ((Boolean) data.get(listid++))
            {
                AttrsAdv attradv = (AttrsAdv) attr;
                
                //get formation
                attradv.setAttrsFormation((float[]) data.get(listid++));
                //get morale
                attradv.setAttrsMorale((float[]) data.get(listid++));
                //get min MOV
                attradv.setMinMOV((Float) data.get(listid++));
            }
        }//end get attrs
    }
    
    /** process packet: send attrs */
    public static void processSendAttrs(IStateEntity host, ByteBuf buf)
    {
        StateHandler sh = host.getStateHandler();
        
        /* send number data */
        final EnumMap<AttrNum, Number> nummap = sh.getAllNumber();
        final EnumSet<AttrNum> numset = sh.getSyncNumber();
        
        //send size
        buf.writeInt(numset.size());
        
        //send data
        numset.forEach((k) -> 
        {
            //send enum key
            buf.writeInt(k.ordinal());
            
            //send number value
            Number v = nummap.get(k);
            
            if (v instanceof Integer)
            {
                buf.writeByte(0);
                buf.writeInt(v.intValue());
            }
            else if (v instanceof Float)
            {
                buf.writeByte(1);
                buf.writeFloat(v.floatValue());
            }
            else if (v instanceof Byte)
            {
                buf.writeByte(2);
                buf.writeByte(v.byteValue());
            }
            else if (v instanceof Short)
            {
                buf.writeByte(3);
                buf.writeShort(v.shortValue());
            }
            else if (v instanceof Double)
            {
                buf.writeByte(4);
                buf.writeDouble(v.doubleValue());
            }
            else if (v instanceof Long)
            {
                buf.writeByte(5);
                buf.writeLong(v.longValue());
            }
            else  //other number type = treat as float
            {
                buf.writeByte(1);
                buf.writeFloat(v.floatValue());
            }
        });  //end send number data
        
        /* send boolean data */
        final EnumMap<AttrBoo, Boolean> boomap = sh.getAllBoolean();
        final EnumSet<AttrBoo> booset = sh.getSyncFlag();
        
        //send size
        buf.writeInt(booset.size());
        
        //send data
        booset.forEach((k) -> 
        {
            //send enum key
            buf.writeInt(k.ordinal());
            
            //send value
            buf.writeBoolean(boomap.get(k));
        });  //end send boolean data
        
        /* send vanilla data */
        if (booset.contains(AttrBoo.Vanilla))
        {
            buf.writeBoolean(true);
            buf.writeBoolean(sh.isSitting());
            buf.writeBoolean(sh.isSneaking());
            buf.writeBoolean(sh.isSprinting());
        }
        else
        {
            buf.writeBoolean(false);
        }
        
        /* send string data */
        final EnumMap<AttrStr, String> strmap = sh.getAllString();
        final EnumSet<AttrStr> strset = sh.getSyncString();
        
        //send size
        buf.writeInt(strset.size());
        
        //send data
        strset.forEach((k) -> 
        {
            //send enum key
            buf.writeInt(k.ordinal());
            
            //send value
            PacketHelper.sendString(buf, strmap.get(k));
        });  //end send string data
        
        /* send attrs data */
        if (sh instanceof AttrStateHandler)
        {
            buf.writeBoolean(true);  //flag for AttrStateHandler
            Attrs attr = ((AttrStateHandler) sh).getAttrs();
            
            //send attrs bonus
            buf.writeBoolean(attr.syncBonus);
            
            if (attr.syncBonus)
            {
                byte[] data1 = attr.getAttrsBonus();
                PacketHelper.sendArrayByte(buf, data1);
            }
            
            //send attrs type
            buf.writeBoolean(attr.syncType);
            float[] adata;
            
            if (attr.syncType)
            {
                adata = attr.getAttrsType();
                PacketHelper.sendArrayFloat(buf, adata);
            }
            
            //send attrs raw
            buf.writeBoolean(attr.syncRaw);
            
            if (attr.syncRaw)
            {
                adata = attr.getAttrsRaw();
                PacketHelper.sendArrayFloat(buf, adata);
            }
            
            //send attrs equip
            buf.writeBoolean(attr.syncEquip);
            
            if (attr.syncEquip)
            {
                adata = attr.getAttrsEquip();
                PacketHelper.sendArrayFloat(buf, adata);
            }
            
            //send attrs potion
            buf.writeBoolean(attr.syncPotion);
            
            if (attr.syncPotion)
            {
                adata = attr.getAttrsPotion();
                PacketHelper.sendArrayFloat(buf, adata);
            }
            
            /* send advanced attrs data */
            if (attr instanceof AttrsAdv)
            {
                buf.writeBoolean(true);  //flag for AttrsAdv
                AttrsAdv attradv = (AttrsAdv) attr;
                
                //send formation
                buf.writeBoolean(attradv.syncFormation);
                
                if (attradv.syncFormation)
                {
                    adata = attradv.getAttrsFormation();
                    PacketHelper.sendArrayFloat(buf, adata);
                }
                
                //send morale
                buf.writeBoolean(attradv.syncMorale);
                
                if (attradv.syncMorale)
                {
                    adata = attradv.getAttrsMorale();
                    PacketHelper.sendArrayFloat(buf, adata);
                }
                
                //send min MOV
                buf.writeBoolean(attradv.syncMinMOV);
                
                if (attradv.syncMinMOV)
                {
                    buf.writeFloat(attradv.getMinMOV());
                }
            }
            else
            {
                buf.writeBoolean(false);
            }
        }//end send attrs
        else
        {
            buf.writeBoolean(false);  //flag for AttrStateHandler
        }
    }
    
    /** process packet: get attrs */
    public static ArrayList processGetAttrs(ByteBuf buf)
    {
        ArrayList datatemp = new ArrayList();
        
        /* get number data */
        //get size
        int dsize = buf.readInt();
        datatemp.add(dsize);
        //get data
        for (int i = 0; i < dsize; i++)
        {
            //get enum key
            int tempKey = buf.readInt();
            datatemp.add(tempKey);
            AttrNum enumkey = AttrNum.values()[tempKey];
            Number dnum = null;
            
            //get data
            byte tempSwitch = buf.readByte();
            datatemp.add(tempSwitch);
            switch (tempSwitch)
            {
            case 0:  //int
                dnum = new Integer(buf.readInt());
            break;
            case 2:  //byte
                dnum = new Byte(buf.readByte());
            break;
            case 3:  //short
                dnum = new Short(buf.readShort());
            break;
            case 4:  //double
                dnum = new Double(buf.readDouble());
            break;
            case 5:  //long
                dnum = new Long(buf.readLong());
            break;
            case 1:  //float or other
            default:
                dnum = new Float(buf.readFloat());
            break;
            }
            
            //save data into list
            datatemp.add(dnum);
        }
        
        /* get boolean data */
        //get size
        dsize = buf.readInt();
        datatemp.add(dsize);
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            datatemp.add(buf.readInt());
            datatemp.add(buf.readBoolean());
        }
        
        /* get vanilla data */
        boolean flag = buf.readBoolean();
        datatemp.add(buf.readBoolean());
        
        if (flag)
        {
            datatemp.add(buf.readBoolean());
            datatemp.add(buf.readBoolean());
            datatemp.add(buf.readBoolean());
        }
        
        /* get string data */
        //get size
        dsize = buf.readInt();
        datatemp.add(dsize);
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            datatemp.add(buf.readInt());
            datatemp.add(PacketHelper.readString(buf));
        }
        
        /* get attrs data */
        flag = buf.readBoolean();
        datatemp.add(flag);
        
        if (flag)
        {
            //get attrs bonus
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                datatemp.add(PacketHelper.readByteArray(buf));
            }
            
            //get attrs type
            float[] adata;
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                datatemp.add(PacketHelper.readFloatArray(buf));
            }
            
            //get attrs raw
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                datatemp.add(PacketHelper.readFloatArray(buf));
            }
            
            //get attrs equip
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                datatemp.add(PacketHelper.readFloatArray(buf));
            }
            
            //get attrs potion
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                datatemp.add(PacketHelper.readFloatArray(buf));
            }
            
            /* get advanced attrs data */
            flag = buf.readBoolean();
            datatemp.add(flag);
            if (flag)
            {
                //get formation
                flag = buf.readBoolean();
                datatemp.add(flag);
                if (flag)
                {
                    datatemp.add(PacketHelper.readFloatArray(buf));
                }
                
                //get morale
                flag = buf.readBoolean();
                datatemp.add(flag);
                if (flag)
                {
                    datatemp.add(PacketHelper.readFloatArray(buf));
                }
                
                //get min MOV
                flag = buf.readBoolean();
                datatemp.add(flag);
                if (flag)
                {
                    datatemp.add(buf.readFloat());
                }
            }
        }//end get attrs
        
        return datatemp;
    }
    
    /** process packet: set attrs from packet data */
    public static void processSetAttrs(IStateEntity host, List data)
    {
        StateHandler sh = host.getStateHandler();
        int listid = 0;
        
        /* get number data */
        EnumMap<AttrNum, Number> nummap = sh.getAllNumber();
        
        //get size
        int dsize = (Integer) data.get(listid++);
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            //get enum key
            AttrNum enumkey = AttrNum.values()[(Integer) data.get(listid++)];
            Number dnum = null;
            
            //get data
            switch ((Byte) data.get(listid++))
            {
            case 0:  //int
                dnum = (Integer) data.get(listid++);
            break;
            case 2:  //byte
                dnum = (Byte) data.get(listid++);
            break;
            case 3:  //short
                dnum = (Short) data.get(listid++);
            break;
            case 4:  //double
                dnum = (Double) data.get(listid++);
            break;
            case 5:  //long
                dnum = (Long) data.get(listid++);
            break;
            case 1:  //float or other
            default:
                dnum = (Float) data.get(listid++);
            break;
            }
            
            //save data into map
            nummap.put(enumkey, dnum);
        }
        
        /* get boolean data */
        EnumMap<AttrBoo, Boolean> boomap = sh.getAllBoolean();
        
        //get size
        dsize = (Integer) data.get(listid++);
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrBoo enumkey = AttrBoo.values()[(Integer) data.get(listid++)];
            boomap.put(enumkey, (Boolean) data.get(listid++));
        }
        
        /* get vanilla data */
        if ((Boolean) data.get(listid++))
        {
            sh.setIsSitting((Boolean) data.get(listid++));
            sh.setIsSneaking((Boolean) data.get(listid++));
            sh.setIsSprinting((Boolean) data.get(listid++));
        }
        
        /* get string data */
        EnumMap<AttrStr, String> strmap = sh.getAllString();
        
        //get size
        dsize = (Integer) data.get(listid++);
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrStr enumkey = AttrStr.values()[(Integer) data.get(listid++)];
            strmap.put(enumkey, (String) data.get(listid++));
        }
        
        /* get attrs data */
        if ((Boolean) data.get(listid++))
        {
            Attrs attr = ((AttrStateHandler) sh).getAttrs();
            
            //get attrs bonus
            if ((Boolean) data.get(listid++))
            {
                attr.setAttrsBonus((byte[]) data.get(listid++));
            }
            
            //get attrs type
            if ((Boolean) data.get(listid++))
            {
                attr.setAttrsType((float[]) data.get(listid++));
            }
            
            //get attrs raw
            if ((Boolean) data.get(listid++))
            {
                attr.setAttrsRaw((float[]) data.get(listid++));
            }
            
            //get attrs equip
            if ((Boolean) data.get(listid++))
            {
                attr.setAttrsEquip((float[]) data.get(listid++));
            }
            
            //get attrs potion
            if ((Boolean) data.get(listid++))
            {
                attr.setAttrsPotion((float[]) data.get(listid++));
            }
            
            /* get advanced attrs data */
            if ((Boolean) data.get(listid++))
            {
                AttrsAdv attradv = (AttrsAdv) attr;
                
                //get formation
                if ((Boolean) data.get(listid++))
                {
                    attradv.setAttrsFormation((float[]) data.get(listid++));
                }
                
                //get morale
                if ((Boolean) data.get(listid++))
                {
                    attradv.setAttrsMorale((float[]) data.get(listid++));
                }
                
                //get min MOV
                if ((Boolean) data.get(listid++))
                {
                    attradv.setMinMOV((Float) data.get(listid++));
                }
            }
        }//end get attrs
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
            case ID.B.ShipInv_NoFuel:
                entity.setStateFlagI(ID.F.NoFuel, value);
            break;
            case ID.B.ShipInv_Sit:
                entity.setSitting(!entity.isSitting());
                entity.sendSyncPacketEmotion();
            break;
            case ID.B.ShipInv_EmoFlag1:
                entity.setStateEmotion(ID.S.Emotion, value, true);
            break;
            case ID.B.ShipInv_EmoFlag2:
                entity.setStateEmotion(ID.S.Emotion4, value, true);
            break;
            }
        }
        else
        {
            LogHelper.debug("DEBUG: set entity by GUI fail, entity null");
        }
    }
    
    /** process custom data packet */
    public static void setEntityByCustomData(Entity entity, float[] value)
    {
        if (value == null || value.length <= 0) return;
        
        switch ((int) value[0])
        {
        case 0:   //abyss missile
        {
            EntityAbyssMissile ent = (EntityAbyssMissile) entity;
            ent.setProjectileType((int) value[1]);
            ent.moveType = (int) value[2];
            ent.velX = value[3];
            ent.velY = value[4];
            ent.velZ = value[5];
            ent.vel0 = value[6];
            ent.accY1 = value[7];
            ent.accY2 = value[8];
            ent.invisibleTicks = (int) value[9];
        }
        break;
        case 1:   //static
        {
            EntityProjectileStatic ent = (EntityProjectileStatic) entity;
            ent.setProjectileType((int) value[1]);
            ent.lifeLength = (int) value[2];
            ent.pullForce = value[3];
            ent.range = value[4];
        }
        break;
        case 2:   //torpedo move
        {
            EntityAbyssMissile ent = (EntityAbyssMissile) entity;
            ent.setProjectileType((int) value[1]);
            ent.moveType = (int) value[2];
            ent.velX = value[3];
            ent.velY = value[4];
            ent.velZ = value[5];
            ent.startMove = value[6] > 0F ? true : false;
            ent.vel0 = value[7];
            ent.accY1 = value[8];
            ent.accY2 = value[9];
        }
        break;
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
            case ID.B.Shipyard_Type:        //build type
                tile2.setBuildType(value);
            break;
            case ID.B.Shipyard_InvMode:        //select inventory mode
                tile2.setInvMode(value);
            break;
            case ID.B.Shipyard_SelectMat:    //select material
                tile2.setSelectMat(value);
            break;
            case ID.B.Shipyard_INCDEC:        //material inc,dec
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
    
    /**
     * send nearby entity item list to player, SERVER SIDE
     */
    public static void syncEntityItemListToClient(EntityPlayer player)
    {
        if (player.world != null)
        {
            //get entity item nearby player
            float[] data = null;    //k:x, k+1:y, k+2:z
            List<BasicEntityItem> getlist = player.world.getEntitiesWithinAABB(BasicEntityItem.class, player.getEntityBoundingBox().expand(128D, 256D, 128D));
            
            if (getlist.size() > 0)
            {
                data = new float[getlist.size() * 3];
                BasicEntityItem ei = null;
                
                for (int i = 0; i < getlist.size(); i++)
                {
                    ei = getlist.get(i);
                    data[i * 3] = (float) ei.posX;
                    data[i * 3 + 1] = (float) ei.posY;
                    data[i * 3 + 2] = (float) ei.posZ;
                }
            }
            else
            {
                data = new float[] {0F};
            }
            
            //send list to player
            CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.SyncGUI_EntityItemList, data), (EntityPlayerMP) player);
        }
    }
    
    /** get TargetPoint for packet */
    public static TargetPoint getTargetPoint(Object host, double range)
    {
        if (host instanceof Entity)
        {
            Entity ent = (Entity) host;
            return new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, range);
        }
        else if (host instanceof TileEntity)
        {
            TileEntity ent = (TileEntity) host;
            return new TargetPoint(ent.getWorld().provider.getDimension(), ent.getPos().getX(), ent.getPos().getY(), ent.getPos().getZ(), range);
        }
        
        return new TargetPoint(0, 0D, 0D, 0D, range);
    }
    
    /** process particle packet FROM (recieve) */
    public static ParticleData readParticleData(ByteBuf buf) throws Exception
    {
        //get type
        int type = buf.readInt();
        ParticleData data = new ParticleData(ParType.values()[type]);
        //get int list
        data.setIntData((ArrayList<Integer>) PacketHelper.readListInt(buf));
        //get float list
        data.setFloatData((ArrayList<Float>) PacketHelper.readListFloat(buf));
        //get string list
        data.setStringData((ArrayList<String>) PacketHelper.readListString(buf));
        //get boolean list
        data.setBooleanData((ArrayList<Boolean>) PacketHelper.readListBoolean(buf));
        
        return data;
    }
    
    /** process particle packet TO (send) */
    public static void sendParticleData(ByteBuf buf, ParticleData data) throws Exception
    {
        //send type
        buf.writeInt(data.getType().ordinal());
        //send int list
        PacketHelper.sendListInt(buf, data.getIntData());
        //send float list
        PacketHelper.sendListFloat(buf, data.getFloatData());
        //send string list
        PacketHelper.sendListString(buf, data.getStringData());
        //send boolean list
        PacketHelper.sendListBoolean(buf, data.getBooleanData());
    }
    
    
}