package com.lulan.shincolle.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.IShipProjectile;
import com.lulan.shincolle.entity.other.EntityShipFishingHook;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.IPacketEntity;
import com.lulan.shincolle.handler.IStateEntity;
import com.lulan.shincolle.handler.IStateEntityAdv;
import com.lulan.shincolle.handler.StateHandler;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : ENTITY SYNC PACKET
 * 用於entity的資料同步
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CEntitySync implements IMessage
{
    
    private IPacketEntity host;
    private Entity entity;
    private byte packetType;
    private int entityID, valueInt;
    private int[] valueInt1;
    private float[] valueFloat1, valueFloat2, valueFloat3, valueFloat4, valueFloat5, valueFloat6;
    private double[] valueDouble1;
    private byte[] valueByte1;
    private boolean[] valueBoolean1;
    private List valueList1;
    private Map valueMap1;
    private ItemStack[] stacks;
    
    //packet id
    public static final class PID
    {
        public static final byte SyncShip_All = 0;
        public static final byte SyncShip_Update = 1;
        public static final byte SyncShip_Riders = 2;
        public static final byte SyncShip_Buffmap = 3;
        
        public static final byte SyncEntity_PlayerUID = 51;
        public static final byte SyncEntity_PosRot = 52;
        public static final byte SyncEntity_Rot = 53;
        public static final byte SyncEntity_Motion = 54;
        public static final byte SyncEntity_Host = 55;
        public static final byte SyncEntity_CustomData = 56;
        public static final byte SyncEntity_AttackTime = 57;
        
        public static final byte SyncProjectile = 80;
        public static final byte SyncSystem_Config = 81;
    }
    
    
    public S2CEntitySync() {}    //必須要有空參數constructor, forge才能使用此class
    
    public S2CEntitySync(IPacketEntity host, byte type)
    {
        this.host = host;
        this.packetType = type;
    }
    
    /** send packet */
    @Override
    public void toBytes(ByteBuf buf)
    {
        try
        {
        
        /* send packet id */
        buf.writeByte(this.packetType);
        
        /* send entity id */
        if (this.host instanceof Entity)
        {
            //若為morph ship, 則發送player eid
            if (this.host instanceof IShipMorph && ((IShipMorph) this.host).isMorph() &&
                ((IShipMorph) this.host).getMorphHost() != null)
            {
                buf.writeInt(((IShipMorph) this.host).getMorphHost().getEntityId());
            }
            //其他entity
            else
            {
                buf.writeInt(((Entity) this.host).getEntityId());
            }
        }
        //non entity = -1
        else
        {
            buf.writeInt(-1);
        }
        
        /* send packet content */
        switch (this.packetType)
        {
        case PID.SyncShip_All:      //sync all data
        {
            PacketHelper.processSendAttrsAll((IStateEntity) this.host, buf);
        }
        break;
        case PID.SyncShip_Update:   //sync updated data
        {
            PacketHelper.processSendAttrs((IStateEntity) this.host, buf);
        }
        break;
        case PID.SyncShip_Riders:   //sync rider list
        {
            Entity ent = (Entity) this.host;
            List<Entity> list = ent.getPassengers();
            int length = list.size();
            
            //send rider list length
            buf.writeInt(length);
            
            //send rider list
            if (length > 0)
            {
                for (Entity e : list)
                {
                    buf.writeInt(e.getEntityId());
                }
            }
            
            //if mounts is BasicEntityMount, send host id
            if (ent instanceof BasicEntityMount)
            {
                buf.writeInt(((BasicEntityMount) ent).getHostEntity().getEntityId());
            }
            else
            {
                //send list length
                buf.writeInt(0);
            }
            
            //send mounts id
            if (ent.getRidingEntity() != null)
            {
                buf.writeInt(ent.getRidingEntity().getEntityId());
            }
            else
            {
                buf.writeInt(0);
            }
        }
        break;
        case PID.SyncProjectile:         //missile tpye sync
        {
            buf.writeInt(this.valueInt);
        }
        break;
        case PID.SyncEntity_AttackTime:  //sync attack time
        {
            if (this.host instanceof IStateEntity)
            {
                StateHandler sh = ((IStateEntity) this.host).getStateHandler();
                
                buf.writeInt(sh.getAttackTick());
                buf.writeInt(sh.getAttackTick2());
                buf.writeInt(sh.getAttackTick3());
            }
        }
        break;
        case PID.SyncEntity_PosRot:      //entity position sync
        {
            if (this.host instanceof EntityLivingBase)
            {
                EntityLivingBase ent = (EntityLivingBase) this.host;
                
                buf.writeDouble(ent.posX);
                buf.writeDouble(ent.posY);
                buf.writeDouble(ent.posZ);
                buf.writeFloat(ent.rotationYaw);
                buf.writeFloat(ent.rotationPitch);
                buf.writeFloat(ent.renderYawOffset);
                buf.writeFloat(ent.rotationYawHead);
            }
            else if (this.host instanceof Entity)
            {
                Entity ent = (Entity) this.host;
                
                buf.writeDouble(ent.posX);
                buf.writeDouble(ent.posY);
                buf.writeDouble(ent.posZ);
                buf.writeFloat(ent.rotationYaw);
                buf.writeFloat(ent.rotationPitch);
                buf.writeFloat(0F);
                buf.writeFloat(0F);
            }
        }
        break;
        case PID.SyncEntity_Rot:    //entity rotation sync
        {
            if (this.host instanceof EntityLivingBase)
            {
                EntityLivingBase ent = (EntityLivingBase) this.host;
                
                buf.writeFloat(ent.rotationYawHead);
                buf.writeFloat(ent.rotationYaw);
                buf.writeFloat(ent.rotationPitch);
            }
            else if (this.host instanceof Entity)
            {
                Entity ent = (Entity) this.host;
                
                buf.writeFloat(ent.rotationYaw);
                buf.writeFloat(ent.rotationYaw);
                buf.writeFloat(ent.rotationPitch);
            }
        }
        break;
        case PID.SyncEntity_Motion:    //entity motion sync
        {
            if (this.host instanceof Entity)
            {
                Entity ent = (Entity) this.host;
                
                buf.writeFloat((float) ent.motionX);
                buf.writeFloat((float) ent.motionY);
                buf.writeFloat((float) ent.motionZ);
            }
        }
        break;
        case PID.SyncEntity_CustomData:    //entity custom data
        {
            PacketHelper.sendArrayFloat(buf, this.valueFloat1);
        }
        break;
        case PID.SyncEntity_PlayerUID:    //player UID sync
        {
            boolean sendFail = true;
            
            if (this.host instanceof IShipOwner)
            {
                if (this.host instanceof TileEntity)
                {
                    sendFail = false;
                    
                    buf.writeInt(((TileEntity) this.host).getPos().getX());
                    buf.writeInt(((TileEntity) this.host).getPos().getY());
                    buf.writeInt(((TileEntity) this.host).getPos().getZ());
                    buf.writeInt(((IShipOwner) this.host).getPlayerUID());
                }
                else if (this.host instanceof Entity)
                {
                    sendFail = false;
                    
                    buf.writeInt(((Entity) this.host).getEntityId());
                    buf.writeInt(-1);
                    buf.writeInt(-1);
                    buf.writeInt(((IShipOwner) this.host).getPlayerUID());
                }
            }
            
            if (sendFail)
            {
                buf.writeInt(0);
                buf.writeInt(0);
                buf.writeInt(0);
                buf.writeInt(0);
            }
        }
        break;
        case PID.SyncSystem_Config:    //server config sync to client
        {
            PacketHelper.sendArrayInt(buf, ConfigHandler.ringAbility);
        }
        break;
        case PID.SyncShip_Buffmap:    //sync buff map
        {
            if (this.host instanceof IStateEntityAdv)
            {
                PacketHelper.sendMapInt(buf, ((IStateEntityAdv) this.host).getBuffHandler().getBuffMap());
            }
        }
        break;
        case PID.SyncEntity_Host:    //sync host
        {
            if (this.entity instanceof EntityShipFishingHook)
            {
                if (((EntityShipFishingHook) this.entity).host != null)
                {
                    buf.writeInt(((EntityShipFishingHook) this.entity).host.getEntityId());
                }
                else
                {
                    buf.writeInt(-1);
                }
            }
        }
        break;
        }
        
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION: S2C entity sync packet: send data fail: "+e);
            e.printStackTrace();
        }
    }

    /** get packet */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
        
        //get type and entityID
        this.packetType = buf.readByte();
        this.entityID = buf.readInt();
        
        switch (this.packetType)
        {
        case PID.SyncShip_All:
        {
            valueList1 = PacketHelper.processGetAttrsAll(buf);
        }
        break;
        case PID.SyncShip_Update:
        {
            valueList1 = PacketHelper.processGetAttrs(buf);
        }  
        break;
        case PID.SyncShip_Riders:  //player mount sync
            this.valueInt = buf.readInt();
            if (this.valueInt > 0) 
            {
                this.valueInt1 = PacketHelper.readIntArray(buf, this.valueInt + 2);
            }
            else
            {
                this.valueInt1 = PacketHelper.readIntArray(buf, 2);
            }
        break;
        case PID.SyncProjectile:   //missile type
            this.valueInt = buf.readInt();
        break;
        case PID.SyncEntity_AttackTime:  //sync attack time
        {
            this.valueInt1 = PacketHelper.readIntArray(buf, 3);
        }
        break;
        case PID.SyncEntity_PosRot:    //entity position
            this.valueDouble1 = PacketHelper.readDoubleArray(buf, 3);
            this.valueFloat1 = PacketHelper.readFloatArray(buf, 4);
        break;
        case PID.SyncEntity_Rot:    //entity rotation
            this.valueFloat1 = PacketHelper.readFloatArray(buf, 3);
        break;
        case PID.SyncEntity_Motion:    //entity motion
            this.valueFloat1 = PacketHelper.readFloatArray(buf, 3);
        break;
        case PID.SyncEntity_CustomData:    //entity custom data
            this.valueFloat1 = PacketHelper.readFloatArray(buf);
        break;
        case PID.SyncEntity_PlayerUID:    //player uid
            this.valueInt1 = PacketHelper.readIntArray(buf, 4);
        break;
        case PID.SyncSystem_Config:    //server config sync to client
            this.valueInt1 = PacketHelper.readIntArray(buf);
        break;
        case PID.SyncShip_Buffmap:    //sync buff map
            this.valueMap1 = PacketHelper.readMapInt(buf);
        break;
        case PID.SyncEntity_Host:    //sync host
            this.valueInt = buf.readInt();
        break;
        }//end switch
        
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION: S2C entity sync packet: get data fail: "+e);
            e.printStackTrace();
        }
    }
    
    /** process packet */
    private static void handle(S2CEntitySync msg, MessageContext ctx)
    {
        boolean getTarget = false;
        Entity entity = null;
        
        /* get host */
        if (msg.entityID > 0)
        {
            entity = EntityHelper.getEntityByID(msg.entityID, 0, true);
        }
        
        //check host existed
        switch(msg.packetType)
        {
        case PID.SyncShip_All:
        case PID.SyncShip_Update:
        case PID.SyncShip_Riders:
            if (entity instanceof IStateEntity) getTarget = true;
        break;
        case PID.SyncShip_Buffmap:
            if (entity instanceof IStateEntityAdv) getTarget = true;
        break;
        case PID.SyncProjectile:
        case PID.SyncEntity_AttackTime:
        case PID.SyncEntity_PosRot:
        case PID.SyncEntity_Rot:
        case PID.SyncEntity_Motion:
        case PID.SyncEntity_Host:
        case PID.SyncEntity_CustomData:
            if (entity != null) getTarget = true;
        break;
        case PID.SyncEntity_PlayerUID:
        case PID.SyncSystem_Config:
            getTarget = true;
        break;
        }
        
        if (!getTarget)
        {
            LogHelper.debug("EXCEPTION: S2C entity sync packet: entity is null, type: "+
                    msg.packetType+" eid: "+msg.entityID);
            return;
        }
        
        try
        {
        
        /* apply new data to host */
        switch (msg.packetType)
        {
        case PID.SyncShip_All:    //sync all attr
        {
            IStateEntity ship = getHostWithMorphChecking(entity);
            
            if (ship != null)
            {
                PacketHelper.processSetAttrsAll(ship, msg.valueList1);
            }
        }
        break;
        case PID.SyncShip_Update:  //sync updated attr
        {
            IStateEntity ship = getHostWithMorphChecking(entity);
            
            if (ship != null)
            {
                PacketHelper.processSetAttrs(ship, msg.valueList1);
            }
        }
        break;
        case PID.SyncShip_Riders: //player mount sync
        {
            //get rider to sync
            if (msg.valueInt > 0)
            {
                //set entity's riders
                for (int i = 0; i < msg.valueInt; i++)
                {
                    Entity ent = EntityHelper.getEntityByID(msg.valueInt1[i], 0, true);
                    if (ent != null) ent.startRiding(entity, true);
                }
                
                //if entity is BasicEntityMount, set host and pose
                if (entity instanceof BasicEntityMount)
                {
                    //set mounts' host entity
                    if (msg.valueInt1[msg.valueInt + 0] > 0)
                    {
                        Entity ent = EntityHelper.getEntityByID(msg.valueInt1[msg.valueInt], 0, true);
                    
                        if (ent instanceof BasicEntityShip)
                        {
                            ((BasicEntityMount) entity).host = (BasicEntityShip) ent;
                        }
                    }
                    
                    //set mount pose if riders > 1
                    if (msg.valueInt > 1)
                    {
                        ((IStateEntity) entity).getStateHandler().setNumberState(AttrNum.Emotion, 1);
                    }
                }
            }//end set entity's riders
            
            //set entity's mounts
            if (msg.valueInt1[msg.valueInt + 1] > 0)
            {
                Entity ent = EntityHelper.getEntityByID(msg.valueInt1[msg.valueInt + 1], 0, true);
                if (ent != null) entity.startRiding(ent ,true);
            }
            else
            {
                entity.dismountRidingEntity();
            }
        }
        break;
        case PID.SyncProjectile:    //missile type sync
        {
            if (entity instanceof IShipProjectile)
            {
                ((IShipProjectile) entity).setProjectileType(msg.valueInt);
            }
        }
        break;
        case PID.SyncEntity_AttackTime:
        {
            if (entity instanceof IStateEntity)
            {
                StateHandler sh = ((IStateEntity) entity).getStateHandler();
            
                sh.setAttackTick(msg.valueInt1[0]);
                sh.setAttackTick2(msg.valueInt1[1]);
                sh.setAttackTick3(msg.valueInt1[2]);
            }
        }
        break;
        case PID.SyncEntity_PosRot:    //entity position sync
        {
            if (entity instanceof EntityLivingBase)
            {
                entity.setPositionAndRotation(msg.valueDouble1[0], msg.valueDouble1[1],
                        msg.valueDouble1[2], msg.valueFloat1[0], msg.valueFloat1[1]);
                ((EntityLivingBase) entity).renderYawOffset = msg.valueFloat1[2];
                ((EntityLivingBase) entity).rotationYawHead = msg.valueFloat1[3];
            }
            else
            {
                entity.setPositionAndRotation(msg.valueDouble1[0], msg.valueDouble1[1],
                        msg.valueDouble1[2], msg.valueFloat1[0], msg.valueFloat1[1]);
            }
        }
        break;
        case PID.SyncEntity_Rot:    //entity rotation sync
        {
            if (entity instanceof EntityLivingBase)
            {
                ((EntityLivingBase) entity).rotationYawHead = msg.valueFloat1[0];
                entity.rotationYaw = msg.valueFloat1[1];
                entity.rotationPitch = msg.valueFloat1[2];
            }
            else
            {
                entity.rotationYaw = msg.valueFloat1[0];
                entity.rotationPitch = msg.valueFloat1[2];
            }
            
            //sync mounts rotate
            if (entity.getRidingEntity() instanceof BasicEntityMount)
            {
                ((BasicEntityMount) entity.getRidingEntity()).rotationYawHead = msg.valueFloat1[0];
                ((BasicEntityMount) entity.getRidingEntity()).rotationYaw = msg.valueFloat1[1];
                ((BasicEntityMount) entity.getRidingEntity()).rotationPitch = msg.valueFloat1[2];
            }
            
            //sync rider rotate
            if (entity.getPassengers().size() > 0)
            {
                for (Entity rider : entity.getPassengers())
                {
                    rider.rotationYaw = msg.valueFloat1[0];
                    rider.rotationPitch = msg.valueFloat1[2];
                    if (rider instanceof EntityLivingBase) ((EntityLivingBase) rider).rotationYawHead = msg.valueFloat1[0];
                }
            }
        }
        break;
        case PID.SyncEntity_Motion:    //entity motion sync
        {
            entity.setVelocity(msg.valueFloat1[0], msg.valueFloat1[1], msg.valueFloat1[2]);
        }
        break;
        case PID.SyncEntity_CustomData:    //entity custom data
        {
            PacketHelper.setEntityByCustomData(entity, msg.valueFloat1);
        }
        break;
        case PID.SyncEntity_PlayerUID:    //player uid sync
        {
            //host is entity
            if (msg.valueInt1[2] == -1)
            {
                entity = EntityHelper.getEntityByID(msg.valueInt1[0], 0, true);
                
                if (entity instanceof IShipOwner)
                {
                    ((IShipOwner) entity).setPlayerUID(msg.valueInt1[3]);
                }
            }
            //host is tile
            else
            {
                TileEntity tile = ClientProxy.getClientWorld().getTileEntity(new BlockPos(msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
                
                if (tile instanceof IShipOwner)
                {
                    ((IShipOwner) tile).setPlayerUID(msg.valueInt1[3]);
                }
            }
        }
        break;
        case PID.SyncSystem_Config:    //server config sync to client
        {
            if (ConfigHandler.ringAbility != null && msg.valueInt1 != null)
            {
                if (ConfigHandler.ringAbility.length != msg.valueInt1.length)
                {
                    ConfigHandler.ringAbility = new int[msg.valueInt1.length];
                }
            }
            else
            {
                ConfigHandler.ringAbility = new int[msg.valueInt1.length];
            }
            
            for (int i = 0; i < msg.valueInt1.length; i++)
            {
                ConfigHandler.ringAbility[i] = msg.valueInt1[i];
            }
        }
        break;
        case PID.SyncShip_Buffmap:    //sync buff map
        {
            ((IStateEntityAdv) entity).getBuffHandler().setBuffMap((HashMap<Integer, Integer>) msg.valueMap1);
        }
        break;
        case PID.SyncEntity_Host:    //sync host
        {
            Entity ent = EntityHelper.getEntityByID(msg.valueInt, 0, true);
            if (ent != null)
            {
                if (entity instanceof EntityShipFishingHook && ent instanceof EntityLivingBase)
                {
                    ((EntityShipFishingHook) entity).host = (EntityLivingBase) ent;
                }
            }
        }
        break;
        }//end switch
        
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION: S2C entity sync packet: set data fail: "+e);
            e.printStackTrace();
        }
    }
    
    /**
     * return ship entity by checking input entity
     */
    public static IStateEntity getHostWithMorphChecking(Entity target)
    {
        //entity is player -> ship is morph ship
        if (target instanceof EntityPlayer)
        {
            CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer)target);
            
            if (capa != null && capa.morphEntity instanceof IStateEntity)
            {
                return (IStateEntity) capa.morphEntity;
            }
            else
            {
                return null;
            }
        }
        //entity is ship
        else
        {
            return (IStateEntity) target;
        }
    }
    
    //packet handler (inner class)
    public static class Handler implements IMessageHandler<S2CEntitySync, IMessage>
    {
        //收到封包時顯示debug訊息
        @Override
        public IMessage onMessage(S2CEntitySync message, MessageContext ctx)
        {
            /**
             * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
             * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
             * 以避免多執行緒下各種並行處理問題
             */
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> S2CEntitySync.handle(message, ctx));
            return null;
        }

    }
    
    
}