package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.network.C2SGUIPackets.PID;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;

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
	private EntityPlayer player;
	private ExtendPlayerProps props;
	private BasicEntityShip ship;
	private World world;
	private int type, entityID, recvX, recvY, recvZ, value, value2;
	private int[] value3;
	private boolean flag;
	private List dataList;
	private Map dataMap;
	
	//packet id
	public static final class PID {
		public static final byte TileSmallSY = 0;
		public static final byte TileLargeSY = 1;
		public static final byte TileDesk = 2;
		public static final byte SyncPlayerProp = 3;
		public static final byte SyncShipInv = 4;
		public static final byte FlagInitSID = 5;
		public static final byte SyncShipList = 6;  //send loaded ship id to client
		public static final byte SyncPlayerProp_TargetClass = 7;
		public static final byte SyncPlayerProp_TeamData = 8;
		public static final byte SyncPlayerProp_Formation = 9;
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
	
	//sync player extend props
	public S2CGUIPackets(ExtendPlayerProps extProps, int type) {
		this.type = type;
        this.props = extProps;
        
        switch(type) {
        case PID.SyncPlayerProp_TargetClass:
        	this.dataList = ServerProxy.getPlayerTargetClassList(props.getPlayerUID());
        	break;
        case PID.SyncPlayerProp_Formation:
        	this.value3 = this.props.getFormatID();
        	break;
        }
    }
	
	//sync ship
	public S2CGUIPackets(BasicEntityShip ship) {
        this.type = PID.SyncShipInv;
        this.ship = ship;
    }
	
	//sync int + boolean data
	public S2CGUIPackets(int type, int value, boolean flag) {
        this.type = type;
        
        switch(type) {
        case PID.FlagInitSID:
        	this.flag = flag;
        	break;
        }
    }
	
	//sync list data
	public S2CGUIPackets(int type, List list) {
		this.type = type;
		this.dataList = list;
	}
	
	/**sync int array data
	 * 
	 * 1. (2 parms) sync formation data: 0:teamID, 1:formatID
	 */
	public S2CGUIPackets(int type, int...parms) {
        this.type = type;
        
        if(parms != null && parms.length > 0) {
        	this.value3 = parms.clone();
        }
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
				int[] formatID = new int[9];
				int teamID = 0;
				boolean[] shipSelected = new boolean[6];
				
				//ring
				propValues[0] = buf.readByte();	//ring active
				propValues[1] = buf.readByte();	//marriage num
				
				//player uid
				propValues[2] = buf.readInt();	//player uid
				
				//current team id
				teamID = buf.readInt();
				
				//formation id array
				for(int i = 0; i < 9 ; ++i) {
					formatID[i] = buf.readByte();
				}
				
				//ship team array
				for(int j = 0; j < 6; ++j) {
					shipValues[j * 2] = buf.readInt();		//ship i entity ID
					shipValues[j * 2 + 1] = buf.readInt();	//ship 0 ship UID
					shipSelected[j] = buf.readBoolean();	//ship 0 select state
				}
				
				//set value
				EntityHelper.setPlayerExtProps(propValues);
				EntityHelper.setPlayerExtProps(teamID, formatID, shipValues, shipSelected);
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
				
				this.player = ClientProxy.getClientPlayer();
				this.props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				
				if(props != null) {
					props.setInitSID(this.flag);
				}
			}
			break;
		case PID.SyncShipList:	//sync ship list
			{
				int listLen = buf.readInt();

				if(listLen > 0) {
					this.player = ClientProxy.getClientPlayer();
					this.props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					List<Integer> data = new ArrayList();
					
					//get list data
					for(int i = 0; i < listLen; ++i) {
						data.add(buf.readInt());
					}
					
					if(props != null) {
						props.setShipEIDList(data);
					}
				}
			}
			break;
		case PID.SyncPlayerProp_TargetClass:	//sync ship list
			{
				this.player = ClientProxy.getClientPlayer();
				this.props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				List<String> data = new ArrayList();
				int listLen = buf.readInt();
	
				if(listLen > 0) {
					//get list data
					for(int i = 0; i < listLen; ++i) {
						data.add(ByteBufUtils.readUTF8String(buf));
//						LogHelper.info("DEBUG : S2C gui packet: get "+data.get(i));
					}
					
					if(props != null) {
						props.setTargetClass(data);
//						LogHelper.info("DEBUG : S2C gui sync: get list size "+data.size());
					}
				}
				else {
					if(props != null) {
						props.clearAllTargetClass();
						LogHelper.info("DEBUG : S2C gui sync: clear target class list ");
					}
				}
			}
			break;
		case PID.SyncPlayerProp_TeamData:
			{
				this.player = ClientProxy.getClientPlayer();
				this.props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				
				this.value = buf.readInt();
				props.setPlayerTeamID(this.value);
				
				//get team data
				this.value = buf.readInt();  //get team data size
				
				Map<Integer, TeamData> tmap = new HashMap();
				
				for(int i = 0; i < this.value; ++i) {
					TeamData tdata = new TeamData();
					
					tdata.setTeamID(buf.readInt());
					
					//get team banned list
					this.dataList = PacketHelper.getListInt(buf);
					tdata.setTeamBannedList(this.dataList);
					
					//get team ally list
					this.dataList = PacketHelper.getListInt(buf);
					tdata.setTeamAllyList(this.dataList);
					
					//get team name
					String tname = PacketHelper.getString(buf);
					tdata.setTeamName(tname);
					
					//get leader name
					String lname = PacketHelper.getString(buf);
					tdata.setTeamLeaderName(lname);
					
					tmap.put(tdata.getTeamID(), tdata);
				}
//				LogHelper.info("DEBUG : S2C GUI Packet: get team data: "+tmap.size());
				props.setAllTeamData(tmap);
			}
			break;
		case PID.SyncPlayerProp_Formation:
			{
				this.value = buf.readInt();		//array length
				
				if(this.value > 0) {			//get array data
					this.value3 = new int[value];
					
					for(int i = 0; i < this.value; i++) {
						this.value3[i] = buf.readInt();
					}
				}
				
				this.player = ClientProxy.getClientPlayer();
				this.props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				
				if(props != null) {
					props.setFormatID(this.value3);
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
				
				//ship team id
				buf.writeInt(props.getCurrentTeamID());
				
				//ship formation id
				int[] fid = props.getFormatID();
				for(int j = 0; j < 9; j++) {
					buf.writeByte((byte) fid[j]);
				}
				
				//ship team list
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
		case PID.SyncShipList:				  //send ship list to client
		case PID.SyncPlayerProp_TargetClass:  //sync target class
			{
				buf.writeByte(this.type);
				
				//send list
				if(dataList != null) {
					//send list length
					buf.writeInt(dataList.size());
					
					//send list data
					Iterator iter = dataList.iterator();
					
					switch(this.type) {
					case PID.SyncShipList:
						//send ship id array
						while(iter.hasNext()) {
							//int list
							buf.writeInt((Integer) iter.next());
						}
						break;
					case PID.SyncPlayerProp_TargetClass:
						LogHelper.info("DEBUG : S2C gui packet: send list size "+dataList.size());
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
		case PID.SyncPlayerProp_TeamData:  //sync all team data to client
			{
				buf.writeByte(this.type);
				buf.writeInt(this.props.getPlayerTeamID());
				
				//get team data
				this.dataMap = ServerProxy.getAllTeamWorldData();
//				LogHelper.info("DEBUG : S2C GUI Packet: send team data: "+this.dataMap.size());
				//has data
				if(this.dataMap != null) {
					int tsize = this.dataMap.size();
					buf.writeInt(tsize);
					
					Iterator iter = this.dataMap.entrySet().iterator();
					
					while(iter.hasNext()) {
						Map.Entry ent = (Entry) iter.next();
						int tid = (Integer) ent.getKey();
						TeamData tdata = (TeamData) ent.getValue();
						
						buf.writeInt(tid);

						//send team banned list
						PacketHelper.sendListInt(buf, tdata.getTeamBannedList());
						
						//send team ally list
						PacketHelper.sendListInt(buf, tdata.getTeamAllyList());
						
						//send team name
						PacketHelper.sendString(buf, tdata.getTeamName());
						
						//send leader name
						PacketHelper.sendString(buf, tdata.getTeamLeaderName());
					}
				}
				//no data
				else {
					buf.writeInt(-1);
				}
			}
			break;
		case PID.SyncPlayerProp_Formation:    //sync formation id
			{
				buf.writeByte(this.type);
				
				//send int array
				if(this.value3 != null) {
					//send list length
					buf.writeInt(this.value3.length);
					
					for(int geti : this.value3) {
						buf.writeInt(geti);
					}
				}
				//if array null
				else {
					buf.writeInt(0);
				}
			}
			break;
		}//end send packet type switch
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


