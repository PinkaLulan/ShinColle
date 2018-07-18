package com.lulan.shincolle.handler;

import java.util.EnumMap;
import java.util.EnumSet;

import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.AttrBoo;
import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.reference.Enums.AttrStr;
import com.lulan.shincolle.reference.dataclass.Attrs;
import com.lulan.shincolle.reference.dataclass.AttrsAdv;
import com.lulan.shincolle.reference.dataclass.ParticleData;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * packet for entity
 * 
 * send type:
 *   IMMEDIATELY: send packet on method call
 *   TICKING: send packet on entity's ticking method (every 1/20 sec)
 */
public class PacketHandler
{
    
    /** host object */
    protected IPacketEntity host;
    
    
    public PacketHandler(IPacketEntity host)
    {
        this.host = host;
        
        this.initFirst();
    }
    
    /** init data on construct */
    protected void initFirst()
    {
    }
    
    /** (IMMEDIATELY) send spawn particle packet */
    public static void sendParticle(IPacketEntity host, ParticleData data)
    {
        CommonProxy.getChannelParticle().sendToAllAround(new S2CSpawnParticle(data), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send attack time for model display */
    public static void sendAttackTime(IStateEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncEntity_AttackTime), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (IMMEDIATELY) send all attributes, ex: open GUI or create new ship */
    public static void sendAttrsAll(IStateEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncShip_AllMisc), PacketHelper.getTargetPoint(host, 64D));
    }
    
    /** (TICKING) send updated attributes */
    public static void sendAttrs(IStateEntity host)
    {
        CommonProxy.getChannelEntitySync().sendToAllAround(new S2CEntitySync(host, S2CEntitySync.PID.SyncShip_Update), PacketHelper.getTargetPoint(host, 64D));
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
        }//end send attrs
    }
    
    /** process packet: get all attrs */
    public static void processGetAttrsAll(IStateEntity host, ByteBuf buf)
    {
        StateHandler sh = host.getStateHandler();
        
        /* get number data */
        EnumMap<AttrNum, Number> nummap = sh.getAllNumber();
        //get size
        int dsize = buf.readInt();
        //get data
        for (int i = 0; i < dsize; i++)
        {
            //get enum key
            AttrNum enumkey = AttrNum.values()[buf.readInt()];
            Number dnum = null;
            //get data
            switch (buf.readByte())
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
            //save data into map
            nummap.put(enumkey, dnum);
        }
        
        /* get boolean data */
        EnumMap<AttrBoo, Boolean> boomap = sh.getAllBoolean();
        //get size
        dsize = buf.readInt();
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrBoo enumkey = AttrBoo.values()[buf.readInt()];
            Boolean bdata = buf.readBoolean();
            boomap.put(enumkey, bdata);
        }
        
        /* get string data */
        EnumMap<AttrStr, String> strmap = sh.getAllString();
        //get size
        dsize = buf.readInt();
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrStr enumkey = AttrStr.values()[buf.readInt()];
            String str = PacketHelper.readString(buf);
            strmap.put(enumkey, str);
        }
        
        /* get attrs data */
        if (sh instanceof AttrStateHandler)
        {
            Attrs attr = ((AttrStateHandler) sh).getAttrs();
            
            //get attrs bonus
            byte[] data1 = PacketHelper.readByteArray(buf);
            attr.setAttrsBonus(data1);
            //get attrs type
            float[] adata = PacketHelper.readFloatArray(buf);
            attr.setAttrsType(adata);
            //get attrs raw
            adata = PacketHelper.readFloatArray(buf);
            attr.setAttrsRaw(adata);
            //get attrs equip
            adata = PacketHelper.readFloatArray(buf);
            attr.setAttrsEquip(adata);
            //get attrs potion
            adata = PacketHelper.readFloatArray(buf);
            attr.setAttrsPotion(adata);
            
            /* get advanced attrs data */
            if (attr instanceof AttrsAdv)
            {
                AttrsAdv attradv = (AttrsAdv) attr;
                
                //get formation
                adata = PacketHelper.readFloatArray(buf);
                attradv.setAttrsFormation(adata);
                //get morale
                adata = PacketHelper.readFloatArray(buf);
                attradv.setAttrsMorale(adata);
                //get min MOV
                attradv.setMinMOV(buf.readFloat());
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
        }//end send attrs
    }
    
    /** process packet: get attrs */
    public static void processGetAttrs(IStateEntity host, ByteBuf buf)
    {
        StateHandler sh = host.getStateHandler();
        
        /* get number data */
        EnumMap<AttrNum, Number> nummap = sh.getAllNumber();
        
        //get size
        int dsize = buf.readInt();
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            //get enum key
            AttrNum enumkey = AttrNum.values()[buf.readInt()];
            Number dnum = null;
            
            //get data
            switch (buf.readByte())
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
            
            //save data into map
            nummap.put(enumkey, dnum);
        }
        
        /* get boolean data */
        EnumMap<AttrBoo, Boolean> boomap = sh.getAllBoolean();
        
        //get size
        dsize = buf.readInt();
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrBoo enumkey = AttrBoo.values()[buf.readInt()];
            Boolean bdata = buf.readBoolean();
            boomap.put(enumkey, bdata);
        }
        
        /* get string data */
        EnumMap<AttrStr, String> strmap = sh.getAllString();
        
        //get size
        dsize = buf.readInt();
        
        //get data
        for (int i = 0; i < dsize; i++)
        {
            AttrStr enumkey = AttrStr.values()[buf.readInt()];
            String str = PacketHelper.readString(buf);
            strmap.put(enumkey, str);
        }
        
        /* get attrs data */
        if (sh instanceof AttrStateHandler)
        {
            Attrs attr = ((AttrStateHandler) sh).getAttrs();
            
            //get attrs bonus
            if (buf.readBoolean())
            {
                byte[] data1 = PacketHelper.readByteArray(buf);
                attr.setAttrsBonus(data1);
            }
            
            //get attrs type
            float[] adata;
            if (buf.readBoolean())
            {
                adata = PacketHelper.readFloatArray(buf);
                attr.setAttrsType(adata);
            }
            
            //get attrs raw
            if (buf.readBoolean())
            {
                adata = PacketHelper.readFloatArray(buf);
                attr.setAttrsRaw(adata);
            }
            
            //get attrs equip
            if (buf.readBoolean())
            {
                adata = PacketHelper.readFloatArray(buf);
                attr.setAttrsEquip(adata);
            }
            
            //get attrs potion
            if (buf.readBoolean())
            {
                adata = PacketHelper.readFloatArray(buf);
                attr.setAttrsPotion(adata);
            }
            
            /* get advanced attrs data */
            if (attr instanceof AttrsAdv)
            {
                AttrsAdv attradv = (AttrsAdv) attr;
                
                //get formation
                if (buf.readBoolean())
                {
                    adata = PacketHelper.readFloatArray(buf);
                    attradv.setAttrsFormation(adata);
                }
                
                //get morale
                if (buf.readBoolean())
                {
                    adata = PacketHelper.readFloatArray(buf);
                    attradv.setAttrsMorale(adata);
                }
                
                //get min MOV
                if (buf.readBoolean())
                {
                    attradv.setMinMOV(buf.readFloat());
                }
            }
        }//end get attrs
    }
    
    
    

    /******************* TODO refactoring *****************************/
    

    
   
    
    /** send sync packet: sync riders */
    public void sendSyncPacketRiders()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Riders, true);
    }
 
    /** send sync packet: sync buff map */
    public void sendSyncPacketBuffMap()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Buffmap, false);
    }
    
    /** sync data for GUI display */
    public void sendSyncPacketGUI()
    {
        if (!this.world.isRemote)
        {
            if (this.getPlayerUID() > 0)
            {
                EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
                
                //owner在附近才需要sync
                if (player != null && player.dimension == this.dimension &&
                    this.getDistanceToEntity(player) < 64F)
                {
                    CommonProxy.channelG.sendTo(new S2CGUIPackets(this), player);
                }
            }
        }
    }
    
    
    /** send sync packet:
     *  type: 0: all  1: emotion  2: flag  3: minor
     *  send all: send packet to all around player
     *  sync emo: sync emotion to all around player
     */
    public void sendSyncPacket(byte type, boolean sendAll)
    {
        if (!world.isRemote)
        {
            //send to all player
            if (sendAll)
            {
                TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
                CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, type), point);
            }
            else
            {
                EntityPlayerMP player = null;
                
                //for owner, send all data
                if (this.getPlayerUID() > 0)
                {
                    player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
                }
                
                //owner在附近才需要sync
                if (player != null && this.getDistanceToEntity(player) <= 64F)
                {
                    CommonProxy.channelE.sendTo(new S2CEntitySync(this, type), player);
                }
            }
        }
    }
    
    /**
     * request server to send sync packet
     *   0: model display (StateEmotion)
     *   1: unit name
     *   2: buff map
     */
    public void sendSyncRequest(int type)
    {
        if (this.world.isRemote)
        {
            switch (type)
            {
            case 0:        //model display
                if (this.morphHost != null)
                {
                    CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.morphHost.getEntityId(), this.world.provider.getDimension()));
                }
                else
                {
                    CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.getEntityId(), this.world.provider.getDimension()));
                }
            break;
            case 1:        //unit name display
                CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_UnitName, this.getEntityId(), this.world.provider.getDimension()));
            break;
            case 2:        //buff map
                CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_Buffmap, this.getEntityId(), this.world.provider.getDimension()));
            break;
            }
        }
    }
}