package com.lulan.shincolle.network;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.ITileWaypoint;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.TeamHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/** CLIENT TO SERVER: INPUT PACKETS (NO-GUI)
 *  將client端的按鍵發送到server
 *  或是發送client端command類型封包
 */
public class C2SInputPackets implements IMessage
{
	
	private World world;
	private EntityPlayer player;
	private byte packetID;
	private int worldID, entityID, value;
	private int[] value3;
	
	//packet id
	public static final class PID
	{
		public static final byte MountMove = 0;
		public static final byte MountGUI = 1;
		public static final byte SyncHandheld = 2;
		public static final byte CmdChOwner = 3;
		public static final byte CmdShipAttr = 4;
		public static final byte Request_SyncModel = 5;
		public static final byte Waypoint_Set = 6;
		public static final byte Request_Riding = 7;
	}
	
	
	public C2SInputPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	/**type 0:(0 parms) mount move key input: 0:key
	 * type 1:(1 parms) mount GUI key input: 0:key
	 * type 2:(1 parms) sync current item: 0:item slot
	 * type 3:(2 parms) command: change owner: 0:owner eid, 1:ship eid
	 * type 4:(9 parms) command: set ship attrs: 0:ship id, 1:world id, 2:ship level, 3~8:bonus value
	 * type 5:(2 parms) request server to sync model parms: 0:world id, 1:entity id
	 * type 6:(9 parms) setting waypoint 
	 * type 7:(2 parms) request server to sync riding: 0:world id, 1:ship eid
	 */
	public C2SInputPackets(byte id, int...parms)
	{
        this.packetID = id;
        this.value3 = parms;
    }
	
	//接收packet方法, server side
	@Override
	public void fromBytes(ByteBuf buf)
	{	
		//get type and entityID
		this.packetID = buf.readByte();
	
		//get data
		switch (this.packetID)
		{
		case PID.MountMove:			//mount move key input
		case PID.MountGUI:			//mount GUI input
		case PID.SyncHandheld:		//sync current item
		case PID.CmdChOwner:    	//command: change owner
		case PID.CmdShipAttr:   	//command: set ship attrs
		case PID.Request_SyncModel:	//request model display sync
		case PID.Request_Riding:	//request riding
		case PID.Waypoint_Set:		//waypoint pairing packet
			try
			{
				this.value = buf.readInt();  //int array length
				
				//get int array data
				if (this.value > 0)
				{
					this.value3 = PacketHelper.readIntArray(buf, this.value);
				}
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION: C2S input packet: ");
				e.printStackTrace();
			}
		break;
		}
	}

	//發出packet方法, client side
	@Override
	public void toBytes(ByteBuf buf)
	{
		//send packet id
		buf.writeByte(this.packetID);
		
		switch (this.packetID)
		{
		case PID.MountMove:		//mount move key input
		case PID.MountGUI:		//mount GUI input
		case PID.SyncHandheld:	//sync current item
		case PID.CmdChOwner:    //command: change owner
		case PID.CmdShipAttr:   //command: set ship attrs
		case PID.Request_SyncModel:	//request model display sync
		case PID.Request_Riding:	//request riding
		case PID.Waypoint_Set:		//waypoint pairing packet
			//send int array
			if (this.value3 != null)
			{
				//send array length
				buf.writeInt(this.value3.length);
				
				for (int geti : this.value3)
				{
					buf.writeInt(geti);
				}
			}
			//if array null
			else
			{
				buf.writeInt(0);
			}
		break;
		}
	}
	
	//packet handle method
	private static void handle(C2SInputPackets msg, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		Entity entity;
		
		try
		{
			switch (msg.packetID)
			{
			case PID.MountMove:	//mounts key input packet
				//set player's mount movement
				if (player.isRiding() && player.getRidingEntity() instanceof BasicEntityMount)
				{
					BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
					BasicEntityShip ship = (BasicEntityShip) mount.getHostEntity();
					
					//check ship owner is player
					if (ship != null && TeamHelper.checkSameOwner(player, ship))
					{
						//set mount movement
						mount.keyPressed = msg.value3[0];
						mount.keyTick = 10;
					}
				}
			break;
			case PID.MountGUI:	//mounts open GUI
				//set player's mount movement
				if (player.isRiding() && player.getRidingEntity() instanceof BasicEntityMount)
				{
					BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
					BasicEntityShip ship = (BasicEntityShip) mount.getHostEntity();
					
					//check ship owner is player
					if (ship != null && TeamHelper.checkSameOwner(player, ship))
					{
						//open ship GUI
						FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.SHIPINVENTORY, player.world, mount.getHostEntity().getEntityId(), 0, 0);
					}
				}
			break;
			case PID.SyncHandheld:	//sync current item
				player.inventory.currentItem = msg.value3[0];
			break;
			case PID.CmdChOwner:    //command: change owner
				/** ship change owner
				 *    1. (done) check command sender is OP (server)
				 *    2. (done) check owner exists (server)
				 *    3. (done) send sender eid to client (s to c)
				 *    4. (done) check sender mouse over target is ship (client)
				 *    5. (done) send ship eid to server (c to s)
				 *    6. change ship's owner UUID and PlayerUID (server)
				 */
				//value3: 0:owner eid, 1:ship eid, 2:world id
				player = (EntityPlayerMP) EntityHelper.getEntityPlayerByID(msg.value3[0], msg.value3[2], false);
				entity = EntityHelper.getEntityByID(msg.value3[1], msg.value3[2], false);
				
				if (player != null && entity instanceof BasicEntityShip)
				{
					//set owner
					EntityHelper.setPetPlayerUUID(player.getUniqueID(), (BasicEntityShip) entity);
					EntityHelper.setPetPlayerUID(player, (BasicEntityShip) entity);
					((BasicEntityShip) entity).ownerName = player.getName();
					//sync
					LogHelper.debug("DEBUG : C2S input packet: command: change owner "+player+" "+entity);
					((BasicEntityShip) entity).sendSyncPacketAllValue();
				}
			break;
			case PID.CmdShipAttr:   //command: set ship attrs
				/**
				 *	  1.(done) check command sender is OP (server)
				 *    2.(done) send sender eid to client (s to c)
				 *    3.(done) check sender mouse over target is ship (client)
				 *    4.(done) send ship eid to server (c to s)
				 *    5. change ship's attributes (server)
				 */
				entity = EntityHelper.getEntityByID(msg.value3[0], msg.value3[1], false);
				
				if (entity instanceof BasicEntityShip)
				{
					BasicEntityShip ship = (BasicEntityShip) entity;
					
					if (msg.value3.length == 9)
					{
						ship.setBonusPoint(0, (byte)msg.value3[3]);
						ship.setBonusPoint(1, (byte)msg.value3[4]);
						ship.setBonusPoint(2, (byte)msg.value3[5]);
						ship.setBonusPoint(3, (byte)msg.value3[6]);
						ship.setBonusPoint(4, (byte)msg.value3[7]);
						ship.setBonusPoint(5, (byte)msg.value3[8]);
						ship.setShipLevel(msg.value3[2], true);
					}
					else if (msg.value3.length == 3)
					{
						ship.setShipLevel(msg.value3[2], true);
					}
				}
			break;
			case PID.Request_SyncModel:  //request model display sync
				entity = EntityHelper.getEntityByID(msg.value3[0], msg.value3[1], false);
				
				if (entity instanceof BasicEntityShip)
				{
					((BasicEntityShip) entity).sendSyncPacketEmotion();
				}
				else if (entity instanceof BasicEntityShipHostile)
				{
					((BasicEntityShipHostile) entity).sendSyncPacket(0);
				}
				else if (entity instanceof BasicEntitySummon)
				{
					((BasicEntitySummon) entity).sendSyncPacket(0);
				}
			break;
			case PID.Request_Riding:
			{
				entity = EntityHelper.getEntityByID(msg.value3[0], msg.value3[1], false);
				
				if (entity instanceof BasicEntityShip)
				{
					BasicEntityShip ship = (BasicEntityShip) entity;
					
					if (TeamHelper.checkSameOwner(player, ship))
					{
						ship.setEntitySit(false);
						ship.startRiding(player, true);
						ship.getShipNavigate().clearPathEntity();
						ship.sendSyncPacketRiders();
					}
				}
			}
			break;
			case PID.Waypoint_Set:		//waypoint pairing packet
			{
				/**
				 * waypoint pairing packet:
				 * data: 0:type, 1: worldID, 2:playerUID, 3~5:host xyz, 6~8:target xyz
				 * type: 0:set last, 1:set next, 2:set chest
				 */
				World w = ServerProxy.getServerWorld(msg.value3[1]);
				
				if (w != null)
				{
					BlockPos p1 = new BlockPos(msg.value3[3], msg.value3[4], msg.value3[5]);
					BlockPos p2 = new BlockPos(msg.value3[6], msg.value3[7], msg.value3[8]);
					TileEntity tile1 = w.getTileEntity(p1);
					TileEntity tile2 = w.getTileEntity(p2);
					
					switch (msg.value3[0])
					{
					case 0:	//set last
						if (tile1 instanceof ITileWaypoint && tile2 instanceof ITileWaypoint)
						{
							//check host tile is same owner
							if (((ITileWaypoint)tile1).getPlayerUID() == msg.value3[2])
							{
								((ITileWaypoint)tile1).setLastWaypoint(p2);
							}
						}
					break;
					case 1:	//set next
						if (tile1 instanceof ITileWaypoint && tile2 instanceof ITileWaypoint)
						{
							//check host tile is same owner
							if (((ITileWaypoint)tile1).getPlayerUID() == msg.value3[2])
							{
								((ITileWaypoint)tile1).setNextWaypoint(p2);
							}
						}
					break;
					case 2:	//set chest
						if (tile1 instanceof TileEntityCrane && tile2 instanceof IInventory)
						{
							//check host tile is same owner
							if (((TileEntityCrane)tile1).getPlayerUID() == msg.value3[2])
							{
								((TileEntityCrane)tile1).setPairedChest(p2, false);
							}
						}
					break;
					}
				}
			}
			break;
			}//end switch
		}
		catch (Exception e)
		{
			LogHelper.debug("EXCEPTION : C2S input packet: handler: "+e);
		}
		
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<C2SInputPackets, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(C2SInputPackets message, MessageContext ctx)
		{
			/**
			 * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
			 * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
			 * 以避免多執行緒下各種並行處理問題
			 */
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> C2SInputPackets.handle(message, ctx));
			return null;
		}
    }
	
	
}
