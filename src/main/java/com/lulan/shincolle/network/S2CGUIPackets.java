package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : GUI SYNC PACKET
 * 用於GUI中大小超過short的值同步 / client端使用物品同步 / client端物品顯示訊息同步
 * 
 * 因sendProgressBarUpdate只能同步short, 需另外實做封包同步float, string, int
 */
public class S2CGUIPackets implements IMessage {
	
	private TileEntity tile;
	private TileEntitySmallShipyard tile1;
	private TileMultiGrudgeHeavy tile2;
	private ExtendPlayerProps props;
	private BasicEntityShip ship;
	private World world;
	private int type, entityID, recvX, recvY, recvZ, value, value2;
	private boolean flag;
	private List data;
	
	//packet id
	public static final class PID {
		public static final byte TileSmallSY = 0;
		public static final byte TileLargeSY = 1;
		public static final byte TileDesk = 2;
		public static final byte SyncPlayerProp = 3;
		public static final byte SyncShipInv = 4;
		public static final byte FlagInitSID = 5;
		public static final byte SyncShipList = 6;
		public static final byte SyncPlayerProp_TargetClass = 7;
	}
	
	
	public S2CGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI sync: 
	//sync tile entity
	public S2CGUIPackets(BasicTileEntity tile) {
		if(tile instanceof TileEntitySmallShipyard) {
			this.tile1 = (TileEntitySmallShipyard) tile;
			this.type = PID.TileSmallSY;
		}
		else if(tile instanceof TileMultiGrudgeHeavy) {
			this.tile2 = (TileMultiGrudgeHeavy) tile;
			this.type = PID.TileLargeSY;
		}
		else if(tile instanceof TileEntityDesk) {
			this.tile = tile;
			this.type = PID.TileDesk;
		}
    }
	
	//player extend props sync (for team frame and radar GUI)
	public S2CGUIPackets(ExtendPlayerProps extProps, int type) {
        this.type = type;
        this.props = extProps;
        
        switch(type) {
        case PID.SyncPlayerProp_TargetClass:
        	int uid = props.getPlayerUID();
        	this.data = ServerProxy.getPlayerTargetClassList(uid);
        	break;
        }
    }
	
	//ship inventory sync
	public S2CGUIPackets(BasicEntityShip ship) {
        this.type = PID.SyncShipInv;
        this.ship = ship;
    }
	
	//player extend props sync type 2
	public S2CGUIPackets(int type, int value, boolean flag) {
        this.type = type;
        
        switch(type) {
        case PID.FlagInitSID:
        	this.flag = flag;
        	break;
        }
    }
	
	//ship list sync
	public S2CGUIPackets(int type, List list) {
		this.type = type;
		this.data = list;
	}
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {
		world = ClientProxy.getClientWorld();
		
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case PID.TileSmallSY: //sync small shipyard gui
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();

				this.tile1 = (TileEntitySmallShipyard) world.getTileEntity(recvX, recvY, recvZ);
				
				if(this.tile1 != null) {
					tile1.setPowerConsumed(buf.readInt());
					tile1.setPowerRemained(buf.readInt());
					tile1.setPowerGoal(buf.readInt());
				}
				else {
					buf.clear();
				}
			}
			break;
		case PID.TileLargeSY: //sync large shipyard gui
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();

				this.tile2 = (TileMultiGrudgeHeavy) world.getTileEntity(recvX, recvY, recvZ);

				if(this.tile2 != null) {
					this.tile2.setPowerConsumed(buf.readInt());
					this.tile2.setPowerRemained(buf.readInt());
					this.tile2.setPowerGoal(buf.readInt());
					this.tile2.setMatStock(0, buf.readInt());
					this.tile2.setMatStock(1, buf.readInt());
					this.tile2.setMatStock(2, buf.readInt());
					this.tile2.setMatStock(3, buf.readInt());
					
					//set render entity state
					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(recvX-1.5D, recvY-2D, recvZ-1.5D, recvX+1.5D, recvY+1D, recvZ+1.5D);
					List renderEntityList = world.getEntitiesWithinAABB(EntityRenderVortex.class, aabb);
					
		            for(int i = 0; i < renderEntityList.size(); i++) { 
		            	((EntityRenderVortex)renderEntityList.get(i)).setIsActive(tile2.isBuilding());
		            }
				}
				else {
					buf.clear();
				}
			}
			break;
		case PID.TileDesk:
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();
				
				this.tile = world.getTileEntity(recvX, recvY, recvZ);
				
				if(this.tile1 != null) {
					int[] data = new int[3];
					data[0] = buf.readInt();
					data[1] = buf.readInt();
					data[2] = buf.readInt();
					EntityHelper.setTileEntityByGUI(tile, ID.B.Desk_Sync, data);
				}
				else {
					buf.clear();
				}
			}
			break;
		case PID.SyncPlayerProp: //sync player props
			{			
				int[] propValues = new int[4];
				int[] shipValues = new int[12];	//0:ship 0 eid  1: ship 0 uid  2: ship 1 eid...
				int teamID = 0;
				boolean[] shipSelected = new boolean[6];
				
				//ring
				propValues[0] = buf.readByte();	//ring active
				propValues[1] = buf.readByte();	//marriage num
				
				//player uid
				propValues[2] = buf.readInt();	//player uid
				propValues[3] = buf.readInt();	//player team id
				
				//ship team
				teamID = buf.readInt();
				shipValues[0] = buf.readInt();			//ship 0 entity ID
				shipValues[1] = buf.readInt();			//ship 0 ship UID
				shipSelected[0] = buf.readBoolean();	//ship 0 select state
				shipValues[2] = buf.readInt();			//ship 1
				shipValues[3] = buf.readInt();
				shipSelected[1] = buf.readBoolean();
				shipValues[4] = buf.readInt();			//ship 2
				shipValues[5] = buf.readInt();
				shipSelected[2] = buf.readBoolean();
				shipValues[6] = buf.readInt();			//ship 3
				shipValues[7] = buf.readInt();
				shipSelected[3] = buf.readBoolean();
				shipValues[8] = buf.readInt();			//ship 4
				shipValues[9] = buf.readInt();
				shipSelected[4] = buf.readBoolean();
				shipValues[10] = buf.readInt();			//ship 5
				shipValues[11] = buf.readInt();
				shipSelected[5] = buf.readBoolean();
				
				//set value
				EntityHelper.setPlayerExtProps(propValues);
				EntityHelper.setPlayerExtProps(teamID, shipValues, shipSelected);
			}
			break;
		case PID.SyncShipInv:	//sync ship GUI
			{
				Entity getEnt = EntityHelper.getEntityByID(buf.readInt(), 0, true);
				
				if(getEnt instanceof BasicEntityShip) {
					BasicEntityShip ship = (BasicEntityShip) getEnt;
					
					ship.setStateMinor(ID.M.Kills, buf.readInt());
					ship.setStateMinor(ID.M.NumGrudge, buf.readInt());
				}
			}
			break;
		case PID.FlagInitSID:	//sync ship GUI
			{
				this.flag = buf.readBoolean();
				
				EntityPlayer player = ClientProxy.getClientPlayer();
				ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				
				if(extProps != null) {
					extProps.setInitSID(this.flag);
				}
			}
			break;
		case PID.SyncShipList:	//sync ship list
			{
				int listLen = buf.readInt();

				if(listLen > 0) {
					EntityPlayer player = ClientProxy.getClientPlayer();
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					List<Integer> data = new ArrayList();
					
					//get list data
					for(int i = 0; i < listLen; ++i) {
						data.add(buf.readInt());
					}
					
					if(extProps != null) {
						extProps.setShipEIDList(data);
					}
				}
			}
			break;
		case PID.SyncPlayerProp_TargetClass:	//sync ship list
			{
				EntityPlayer player = ClientProxy.getClientPlayer();
				ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				List<String> data = new ArrayList();
				int listLen = buf.readInt();
	
				if(listLen > 0) {
					//get list data
					for(int i = 0; i < listLen; ++i) {
						data.add(ByteBufUtils.readUTF8String(buf));
					}
					
					if(extProps != null) {
						extProps.setTargetClass(data);
						LogHelper.info("DEBUG : S2C gui sync: get list "+data.get(0));
					}
				}
				else {
					if(extProps != null) {
						extProps.clearAllTargetClass();
						LogHelper.info("DEBUG : S2C gui sync: clear target class list ");
					}
				}
			}
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case PID.TileSmallSY: //sync small shipyard gui
			{
				buf.writeByte(this.type);
				buf.writeInt(this.tile1.xCoord);
				buf.writeInt(this.tile1.yCoord);
				buf.writeInt(this.tile1.zCoord);
				buf.writeInt(this.tile1.getPowerConsumed());
				buf.writeInt(this.tile1.getPowerRemained());
				buf.writeInt(this.tile1.getPowerGoal());
			}
			break;
		case PID.TileLargeSY: //sync large shipyard gui
			{
				buf.writeByte(this.type);
				buf.writeInt(this.tile2.xCoord);
				buf.writeInt(this.tile2.yCoord);
				buf.writeInt(this.tile2.zCoord);
				buf.writeInt(this.tile2.getPowerConsumed());
				buf.writeInt(this.tile2.getPowerRemained());
				buf.writeInt(this.tile2.getPowerGoal());
				buf.writeInt(this.tile2.getMatStock(0));
				buf.writeInt(this.tile2.getMatStock(1));
				buf.writeInt(this.tile2.getMatStock(2));
				buf.writeInt(this.tile2.getMatStock(3));
			}
			break;
		case PID.TileDesk:  //sync admiral desk
			{
				buf.writeByte(this.type);
				buf.writeInt(tile.xCoord);
				buf.writeInt(tile.yCoord);
				buf.writeInt(tile.zCoord);
				
				switch(this.type) {
				case PID.TileDesk:
					buf.writeInt(((TileEntityDesk)tile).guiFunc);
					buf.writeInt(((TileEntityDesk)tile).guiFunc);
					buf.writeInt(((TileEntityDesk)tile).guiFunc);
					break;
				}
			}
			break;
		case PID.SyncPlayerProp:	//sync player props
			{
				buf.writeByte(this.type);
				
				//ring
				buf.writeByte(props.isRingActiveI());
				buf.writeByte(props.getMarriageNum());
				
				//player uid
				buf.writeInt(props.getPlayerUID());
				buf.writeInt(props.getPlayerTeamId());
				
				//team id
				buf.writeInt(props.getTeamId());
				
				//team list
				for(int i = 0; i < 6; i++) {
					//get entity id
					if(props.getEntityOfCurrentTeam(i) != null) {
						buf.writeInt(props.getEntityOfCurrentTeam(i).getEntityId());
					}
					else {
						buf.writeInt(-1);
					}
					//get ship UID
					buf.writeInt(props.getSIDofCurrentTeam(i));
					//get select state
					buf.writeBoolean(props.getSelectStateOfCurrentTeam(i));
				}
			}
			break;
		case PID.SyncShipInv:	//sync ship inventory GUI: kills and grudge
			{
				buf.writeByte(this.type);
				buf.writeInt(ship.getEntityId());
				buf.writeInt(ship.getStateMinor(ID.M.Kills));
				buf.writeInt(ship.getStateMinor(ID.M.NumGrudge));
			}
			break;
		case PID.FlagInitSID:	//sync ship inventory GUI: kills and grudge
			{
				buf.writeByte(this.type);
				buf.writeBoolean(flag);
			}
			break;
		case PID.SyncShipList:	//sync ship list
		case PID.SyncPlayerProp_TargetClass:  //sync target class
			{
				buf.writeByte(this.type);
				
				//send list
				if(data != null) {
					//send list length
					buf.writeInt(data.size());
					
					//send list data
					Iterator iter = data.iterator();
					
					switch(this.type) {
					case PID.SyncShipList:
						while(iter.hasNext()) {
							//int list
							buf.writeInt((Integer) iter.next());
						}
						break;
					case PID.SyncPlayerProp_TargetClass:
						while(iter.hasNext()) {
							//string list
							ByteBufUtils.writeUTF8String(buf, (String) iter.next());
						}
						break;
					}
				}
				else {
					buf.writeInt(-1);
				}
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CGUIPackets, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CGUIPackets message, MessageContext ctx) {
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv GUI Click packet : type "+recvType+" button ");
			return null;
		}
    }
	

}


