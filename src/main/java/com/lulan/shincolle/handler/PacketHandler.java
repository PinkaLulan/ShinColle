package com.lulan.shincolle.handler;

import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * packet for entity
 */
public class PacketHandler
{
    
    
    
    
    
    
    
    
    /** send sync packet: sync all data */
    public void sendSyncPacketAll()
    {
        //set update flag
        this.setUpdateFlag(ID.FlagUpdate.AttrsBonus, true);
        this.setUpdateFlag(ID.FlagUpdate.AttrsRaw, true);
        this.setUpdateFlag(ID.FlagUpdate.AttrsEquip, true);
        this.setUpdateFlag(ID.FlagUpdate.AttrsMorale, true);
        this.setUpdateFlag(ID.FlagUpdate.AttrsPotion, true);
        this.setUpdateFlag(ID.FlagUpdate.AttrsFormation, true);
        this.setUpdateFlag(ID.FlagUpdate.AttrsBuffed, true);
        
        this.sendSyncPacket(S2CEntitySync.PID.SyncShip_AllMisc, false);
        this.sendSyncPacket(S2CEntitySync.PID.SyncShip_Attrs, false);
    }
    
    /** send sync packet: attrs */
    public void sendSyncPacketAttrs()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Attrs, false);
    }
    
    /** send sync packet: attrs */
    public void sendSyncPacketMinor()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Minor, false);
    }
    
    /** send sync packet: attrs */
    public void sendSyncPacketAllMisc()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_AllMisc, false);
    }

    /** send sync packet: sync formation data */
    public void sendSyncPacketFormationValue()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Formation, false);
    }
    
    /** send sync packet: sync riders */
    public void sendSyncPacketRiders()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Riders, true);
    }
    
    /** send sync packet: sync unit name */
    public void sendSyncPacketUnitName()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_UnitName, true);
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
    
    /**
     * sync data for timer display
     * 
     * type:
     *   0: crane time
     *   1: mount skill time
     */
    public void sendSyncPacketTimer(int type)
    {
        switch (type)
        {
        case 0:
            sendSyncPacket(S2CEntitySync.PID.SyncShip_Timer, true);
        break;
        case 1:
            sendSyncPacket(S2CEntitySync.PID.SyncShip_PlayerSkillTimer, false);
        break;
        }
    }
    
    /** sync data for emotion display */
    public void sendSyncPacketEmotion()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Emo, true);
    }
    
    /** sync data for flag */
    public void sendSyncPacketFlag()
    {
        sendSyncPacket(S2CEntitySync.PID.SyncShip_Flag, true);
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