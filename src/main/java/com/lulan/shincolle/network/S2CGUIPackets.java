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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;


/**SERVER TO CLIENT : GUI SYNC PACKET
 * 用於GUI中大小超過short的值同步 / client端使用物品同步 / client端物品顯示訊息同步
 * 
 * 因sendProgressBarUpdate只能同步short, 需另外實做封包同步float, string, int
 */
public class S2CGUIPackets implements IMessage
{
	
	private TileEntity tile;
	private EntityPlayer player;
	private BasicEntityShip ship;
	private CapaTeitoku capa;
	private World world;
	private int type, entityID, recvX, recvY, recvZ, value, value2;
	private int[] value3;
	private boolean flag;
	private List dataList;
	private Map dataMap;
	
	//packet id
	public static final class PID
	{
		public static final byte TileSmallSY = 0;
		public static final byte TileLargeSY = 1;
		public static final byte TileDesk = 2;
		public static final byte SyncPlayerProp = 3;
		public static final byte SyncShipInv = 4;
		public static final byte FlagInitSID = 5;
		public static final byte SyncShipList = 6;  	//send loaded ship id to client
		public static final byte SyncPlayerProp_TargetClass = 7;
		public static final byte SyncPlayerProp_TeamData = 8;
		public static final byte SyncPlayerProp_Formation = 9;
		public static final byte SyncPlayerProp_ShipsAll = 10;
		public static final byte SyncPlayerProp_ShipsInTeam = 11;
		public static final byte SyncPlayerProp_ColledShip = 12;
		public static final byte SyncPlayerProp_ColledEquip = 13;
		public static final byte TileVolCore = 14;
		public static final byte TileWaypoint = 15;
		public static final byte TileCrane = 16;
	}
	
	
	public S2CGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI sync: 
	//sync tile entity
	public S2CGUIPackets(TileEntity tile)
	{
		this.tile = tile;
		
		if (tile instanceof TileEntitySmallShipyard)
		{
			this.type = PID.TileSmallSY;
		}
		else if (tile instanceof TileMultiGrudgeHeavy)
		{
			this.type = PID.TileLargeSY;
		}
//		else if (tile instanceof TileEntityDesk) TODO
//		{
//			this.tile = tile;
//			this.type = PID.TileDesk;
//		}
//		else if (tile instanceof TileEntityVolCore)
//		{
//			this.tile = tile;
//			this.type = PID.TileVolCore;
//		}
//		else if (tile instanceof TileEntityWaypoint)
//		{
//			this.tile = tile;
//			this.type = PID.TileWaypoint;
//		}
//		else if (tile instanceof TileEntityCrane)
//		{
//			this.tile = tile;
//			this.type = PID.TileCrane;
//		}
    }
	
	//sync player extend props
	public S2CGUIPackets(CapaTeitoku capa, int type)
	{
		if (capa != null)
		{
			this.type = type;
	        this.capa = capa;

	        switch (type)
	        {
	        case PID.SyncPlayerProp_TargetClass:
	        	this.dataList = PacketHelper.getStringListFromStringMap(
	        					ServerProxy.getPlayerTargetClass(
	        					this.capa.getPlayerUID()));
	        	break;
	        case PID.SyncPlayerProp_Formation:
	        	this.value3 = this.capa.getFormatID();
	        	break;
	        }
		}
    }
	
	//sync player extend props
	public S2CGUIPackets(CapaTeitoku capa, byte type, int...parms)
	{
		if (capa != null)
		{
			this.type = type;
	        this.capa = capa;
	        
	        if (parms != null && parms.length > 0)
	        {
	        	this.value3 = parms.clone();
	        }
		}
    }
	
	//sync ship
	public S2CGUIPackets(BasicEntityShip ship)
	{
        this.type = PID.SyncShipInv;
        this.ship = ship;
    }
	
	//sync int + boolean data
	public S2CGUIPackets(int type, int value, boolean flag)
	{
        this.type = type;
        
        switch (type)
        {
        case PID.FlagInitSID:
        	this.flag = flag;
        	break;
        }
    }
	
	//sync list data
	public S2CGUIPackets(int type, List list)
	{
		this.type = type;
		this.dataList = list;
	}
	
	/** sync int array data
	 * 
	 *  1. (2 parms) sync formation data: 0:teamID, 1:formatID
	 */
	public S2CGUIPackets(int type, int...parms)
	{
        this.type = type;
        
        if (parms != null && parms.length > 0)
        {
        	this.value3 = parms.clone();
        }
    }
	
	//接收packet: CLIENT端
	@Override
	public void fromBytes(ByteBuf buf)
	{
		world = ClientProxy.getClientWorld();
		
		//get type and entityID
		this.type = buf.readByte();
	
		switch (type)
		{
		case PID.TileSmallSY: //sync small shipyard gui
			{
				//read value
				int[] readi = PacketHelper.readIntArray(buf, 6);

				//get tile
				TileEntity te = world.getTileEntity(new BlockPos(readi[0], readi[1], readi[2]));
				
				//set tile
				if (te instanceof TileEntitySmallShipyard)
				{
					TileEntitySmallShipyard te2 = (TileEntitySmallShipyard) te;
					te2.setPowerConsumed(readi[3]);
					te2.setPowerRemained(readi[4]);
					te2.setPowerGoal(readi[5]);
				}
			}
			break;
		case PID.TileLargeSY: //sync large shipyard gui
			{
				//read value
				int[] readi = PacketHelper.readIntArray(buf, 10);
				
				//get tile
				TileEntity te = world.getTileEntity(new BlockPos(readi[0], readi[1], readi[2]));
				
				//set tile
				if (te instanceof TileMultiGrudgeHeavy)
				{
					TileMultiGrudgeHeavy te2 = (TileMultiGrudgeHeavy) te;
					te2.setPowerConsumed(readi[3]);
					te2.setPowerRemained(readi[4]);
					te2.setPowerGoal(readi[5]);
					te2.setMatStock(0, readi[6]);
					te2.setMatStock(1, readi[7]);
					te2.setMatStock(2, readi[8]);
					te2.setMatStock(3, readi[9]);
				}
			}
			break;
//		case PID.TileDesk:
//			{
//				this.recvX = buf.readInt();
//				this.recvY = buf.readInt();
//				this.recvZ = buf.readInt();
//				
//				this.tile = world.getTileEntity(recvX, recvY, recvZ);
//				
//				if(this.tile1 != null) {
//					int[] data = new int[3];
//					data[0] = buf.readInt();
//					data[1] = buf.readInt();
//					data[2] = buf.readInt();
//					EntityHelper.setTileEntityByGUI(tile, ID.B.Desk_Sync, data);
//				}
//				else {
//					buf.clear();
//				}
//			}
//			break;
//		case PID.TileVolCore: //sync tile volcano core
//			{
//				this.recvX = buf.readInt();
//				this.recvY = buf.readInt();
//				this.recvZ = buf.readInt();
//	
//				TileEntity te = world.getTileEntity(recvX, recvY, recvZ);
//				
//				if(te instanceof TileEntityVolCore) {
//					((TileEntityVolCore) te).setPowerRemained(buf.readInt());
//					((TileEntityVolCore) te).isActive = buf.readBoolean();
//				}
//				else {
//					buf.clear();
//				}
//			}
//			break;
//		case PID.TileWaypoint: //sync tile waypoint
//			{
//				this.recvX = buf.readInt();
//				this.recvY = buf.readInt();
//				this.recvZ = buf.readInt();
//				
//				this.value3 = new int[6];
//				this.value3[0] = buf.readInt();
//				this.value3[1] = buf.readInt();
//				this.value3[2] = buf.readInt();
//				this.value3[3] = buf.readInt();
//				this.value3[4] = buf.readInt();
//				this.value3[5] = buf.readInt();
//	
//				TileEntity te = world.getTileEntity(recvX, recvY, recvZ);
//				
//				if(te instanceof TileEntityWaypoint) {
//					((TileEntityWaypoint) te).setSyncData(this.value3);
//				}
//			}
//			break;
//		case PID.TileCrane: //sync tile crane
//			{
//				this.recvX = buf.readInt();
//				this.recvY = buf.readInt();
//				this.recvZ = buf.readInt();
//				
//				this.value3 = new int[11];
//				this.value3[0] = buf.readInt();  //lx
//				this.value3[1] = buf.readInt();  //ly
//				this.value3[2] = buf.readInt();  //lz
//				this.value3[3] = buf.readInt();  //nx
//				this.value3[4] = buf.readInt();  //ny
//				this.value3[5] = buf.readInt();  //nz
//				this.value3[6] = buf.readInt();  //cx
//				this.value3[7] = buf.readInt();  //cy
//				this.value3[8] = buf.readInt();  //cz
//				this.value3[9] = buf.readInt();  //ship eid
//				this.value3[10] = buf.readInt(); //crane mode
//				
//				boolean isActive = buf.readBoolean();
//				boolean checkMeta = buf.readBoolean();
//				boolean checkDict = buf.readBoolean();
//				boolean checkNbt = buf.readBoolean();
//	
//				TileEntity te = world.getTileEntity(recvX, recvY, recvZ);
//				Entity ent = EntityHelper.getEntityByID(value3[9], 0, true);
//				
//				if(te instanceof TileEntityCrane) {
//					TileEntityCrane t = (TileEntityCrane) te;
//					t.setSyncData(value3);
//					t.isActive = isActive;
//					t.checkMetadata = checkMeta;
//					t.checkOredict = checkDict;
//					t.checkNbt = checkNbt;
//					t.ship = (BasicEntityShip) ent;
//					t.craneMode = value3[10];
//				}
//			}
//			break;
		case PID.SyncPlayerProp: //sync player props
			{			
				int[] propValues = new int[4];
				int[] shipValues = new int[12];	//0:ship 0 eid  1: ship 0 uid  2: ship 1 eid...
				int[] formatID = new int[9];
				int teamID = 0;
				boolean[] shipSelected = new boolean[6];
				
				//ring
				propValues[0] = buf.readByte();	//ring active
				propValues[1] = buf.readInt();	//marriage num
				
				//player uid
				propValues[2] = buf.readInt();	//player uid
				
				//current team id
				teamID = buf.readInt();
				
				//formation id array
				for (int i = 0; i < 9 ; ++i)
				{
					formatID[i] = buf.readByte();
				}
				
				//ship team array
				for (int j = 0; j < 6; ++j)
				{
					shipValues[j * 2] = buf.readInt();		//ship i entity ID
					shipValues[j * 2 + 1] = buf.readInt();	//ship 0 ship UID
					shipSelected[j] = buf.readBoolean();	//ship 0 select state
				}
				
				//set value
				CapaTeitoku.setCapaDataMisc(propValues);
				CapaTeitoku.setCapaDataTeamList(teamID, formatID, shipValues, shipSelected);
			}
			break;
		case PID.SyncShipInv:	//sync ship GUI
			{
				//read value
				int[] data = PacketHelper.readIntArray(buf, 4);
				
				//set value
				Entity getEnt = EntityHelper.getEntityByID(data[0], 0, true);
				
				if (getEnt instanceof BasicEntityShip)
				{
					BasicEntityShip ship = (BasicEntityShip) getEnt;
					
					ship.setStateMinor(ID.M.Kills, data[1]);
					ship.setStateMinor(ID.M.NumGrudge, data[2]);
					ship.getCapaShipInventory().setInventoryPage(data[3]);
				}
			}
			break;
		case PID.FlagInitSID:	//ship UID init flag
			{
				this.flag = buf.readBoolean();
				
				this.player = ClientProxy.getClientPlayer();
				this.capa = CapaTeitoku.getTeitokuCapability(this.player);
				
				if (this.capa != null)
				{
					this.capa.setInitSID(this.flag);
				}
			}
			break;
		case PID.SyncShipList:				  //sync loaded ship list
		case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
		case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
			{
				int listLen = buf.readInt();

				if (listLen > 0)
				{
					this.player = ClientProxy.getClientPlayer();
					this.capa = CapaTeitoku.getTeitokuCapability(player);
					ArrayList<Integer> data = new ArrayList();
					
					//get list data
					for (int i = 0; i < listLen; ++i)
					{
						data.add(buf.readInt());
					}
					
					if (capa != null)
					{
						switch (type)
						{
						case PID.SyncShipList:				  //sync loaded ship list
							capa.setShipEIDList(data);
							break;
						case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
							capa.setColleShipList(data);
							break;
						case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
							capa.setColleEquipList(data);
							break;
						}
					}
				}
			}
			break;
		case PID.SyncPlayerProp_TargetClass:	//sync ship list
			{
				this.player = ClientProxy.getClientPlayer();
				this.capa = CapaTeitoku.getTeitokuCapability(player);
				HashMap<Integer, String> data = new HashMap();
				int listLen = buf.readInt();
	
				if (listLen > 0)
				{
					//get list data
					for (int i = 0; i < listLen; ++i)
					{
						String str = ByteBufUtils.readUTF8String(buf);
						data.put(str.hashCode(), str);
					}
					
					if (capa != null)
					{
						capa.setTargetClass(data);
					}
				}
				else
				{
					if (capa != null)
					{
						capa.clearAllTargetClass();
						LogHelper.info("DEBUG : S2C gui sync: clear target class list ");
					}
				}
			}
			break;
		case PID.SyncPlayerProp_TeamData:
			{
				this.player = ClientProxy.getClientPlayer();
				this.capa = CapaTeitoku.getTeitokuCapability(player);

				this.value = buf.readInt();
				this.capa.setPlayerTeamID(this.value);
				
				//get team data
				this.value = buf.readInt();  //get team data size
				
				HashMap<Integer, TeamData> tmap = new HashMap();
				
				for (int i = 0; i < this.value; ++i)
				{
					TeamData tdata = new TeamData();
					
					tdata.setTeamID(buf.readInt());
					
					//get team banned list
					this.dataList = PacketHelper.readListInt(buf);
					tdata.setTeamBannedList(this.dataList);
					
					//get team ally list
					this.dataList = PacketHelper.readListInt(buf);
					tdata.setTeamAllyList(this.dataList);
					
					//get team name
					String tname = PacketHelper.readString(buf);
					tdata.setTeamName(tname);
					
					//get leader name
					String lname = PacketHelper.readString(buf);
					tdata.setTeamLeaderName(lname);
					
					tmap.put(tdata.getTeamID(), tdata);
				}
//				LogHelper.info("DEBUG : S2C GUI Packet: get team data: "+tmap.size());
				capa.setPlayerTeamDataMap(tmap);
			}
			break;
		case PID.SyncPlayerProp_Formation:
			{
				this.value = buf.readInt();		//array length
				
				if (this.value > 0)
				{			//get array data
					this.value3 = new int[value];
					
					for (int i = 0; i < this.value; i++)
					{
						this.value3[i] = buf.readInt();
					}
				}
				
				this.player = ClientProxy.getClientPlayer();
				this.capa = CapaTeitoku.getTeitokuCapability(player);
				
				if (capa != null)
				{
					capa.setFormatID(this.value3);
				}
			}
			break;
		case PID.SyncPlayerProp_ShipsAll:
			{
				this.value = buf.readInt();  	//current team id
				
				this.value3 = new int[9];
				for (int i = 0; i < 9; i++)
				{
					this.value3[i] = buf.readByte();
				}
				
				this.value2 = buf.readByte();  	//has team data flag
				
				if (this.value2 > 0)
				{  	//get team data
					BasicEntityShip[][] ships = new BasicEntityShip[9][6];
					int[][] sids = new int[9][6];
					boolean[][] sels = new boolean[9][6];
					int temp;
					
					for (int k = 0; k < 9; k++)
					{
						for (int i = 0; i < 6; i++)
						{
							temp = buf.readInt();
							ships[k][i] = (BasicEntityShip) EntityHelper.getEntityByID(temp, 0, true);
							sids[k][i] = buf.readInt();
							sels[k][i] = buf.readBoolean();
						}
					}
					
					this.player = ClientProxy.getClientPlayer();
					this.capa = CapaTeitoku.getTeitokuCapability(player);
					
					if (capa != null)
					{
						capa.setPointerTeamID(value);
						capa.setFormatID(value3);
						capa.setTeamList(ships);
						capa.setSelectState(sels);
						capa.setSIDList(sids);
					}
				}
			}
			break;
		case PID.SyncPlayerProp_ShipsInTeam:
			{
				this.value = buf.readInt();  	//team id
				this.value2 = buf.readByte();	//format id
				
				int flag = buf.readByte();  	//has team data flag
				
				if (flag > 0)
				{  			//get team data
					BasicEntityShip[] ships = new BasicEntityShip[6];
					int[] sids = new int[6];
					boolean[] sels = new boolean[6];
					int temp;
					
					for (int i = 0; i < 6; i++)
					{
						temp = buf.readInt();
						ships[i] = (BasicEntityShip) EntityHelper.getEntityByID(temp, 0, true);
						sids[i] = buf.readInt();
						sels[i] = buf.readBoolean();
					}
					
					this.player = ClientProxy.getClientPlayer();
					this.capa = CapaTeitoku.getTeitokuCapability(player);
					
					if (capa != null)
					{
						capa.setFormatID(value, value2);
						capa.setTeamList(value, ships);
						capa.setSelectState(value, sels);
						capa.setSIDList(value, sids);
					}
				}
			}
			break;
		}
	}

	//發出packet: SERVER端
	@Override
	public void toBytes(ByteBuf buf)
	{
		switch (this.type)
		{
			case PID.TileSmallSY: //sync small shipyard gui
			{
				TileEntitySmallShipyard tile2 = (TileEntitySmallShipyard) tile;
				
				buf.writeByte(this.type);
				buf.writeInt(tile2.getPos().getX());
				buf.writeInt(tile2.getPos().getY());
				buf.writeInt(tile2.getPos().getZ());
				buf.writeInt(tile2.getPowerConsumed());
				buf.writeInt(tile2.getPowerRemained());
				buf.writeInt(tile2.getPowerGoal());
			}
			break;
		case PID.TileLargeSY: //sync large shipyard gui
			{
				TileMultiGrudgeHeavy tile2 = (TileMultiGrudgeHeavy) tile;
				
				buf.writeByte(this.type);
				buf.writeInt(tile2.getPos().getX());
				buf.writeInt(tile2.getPos().getY());
				buf.writeInt(tile2.getPos().getZ());
				buf.writeInt(tile2.getPowerConsumed());
				buf.writeInt(tile2.getPowerRemained());
				buf.writeInt(tile2.getPowerGoal());
				buf.writeInt(tile2.getMatStock(0));
				buf.writeInt(tile2.getMatStock(1));
				buf.writeInt(tile2.getMatStock(2));
				buf.writeInt(tile2.getMatStock(3));
			}
			break;
//		case PID.TileDesk:  //sync admiral desk
//			{
//				buf.writeByte(this.type);
//				buf.writeInt(tile.xCoord);
//				buf.writeInt(tile.yCoord);
//				buf.writeInt(tile.zCoord);
//				
//				switch(this.type) {
//				case PID.TileDesk:
//					buf.writeInt(((TileEntityDesk)tile).guiFunc);
//					buf.writeInt(((TileEntityDesk)tile).guiFunc);
//					buf.writeInt(((TileEntityDesk)tile).guiFunc);
//					break;
//				}
//			}
//			break;
//		case PID.TileVolCore: //sync tile volcano core
//			{
//				buf.writeByte(this.type);
//				buf.writeInt(this.tile.xCoord);
//				buf.writeInt(this.tile.yCoord);
//				buf.writeInt(this.tile.zCoord);
//				buf.writeInt(((TileEntityVolCore)tile).getPowerRemained());
//				buf.writeBoolean(((TileEntityVolCore)tile).isActive);
//			}
//			break;
//		case PID.TileCrane: //sync tile crane
//			{
//				TileEntityCrane te = (TileEntityCrane) tile;
//				
//				buf.writeByte(this.type);
//				buf.writeInt(this.tile.xCoord);
//				buf.writeInt(this.tile.yCoord);
//				buf.writeInt(this.tile.zCoord);
//				
//				buf.writeInt(te.lx);
//				buf.writeInt(te.ly);
//				buf.writeInt(te.lz);
//				buf.writeInt(te.nx);
//				buf.writeInt(te.ny);
//				buf.writeInt(te.nz);
//				buf.writeInt(te.cx);
//				buf.writeInt(te.cy);
//				buf.writeInt(te.cz);
//				
//				if(te.ship != null) {
//					buf.writeInt(te.ship.getEntityId());
//				}
//				else {
//					buf.writeInt(-1);
//				}
//				
//				buf.writeInt(te.craneMode);
//				
//				buf.writeBoolean(te.isActive);
//				buf.writeBoolean(te.checkMetadata);
//				buf.writeBoolean(te.checkOredict);
//				buf.writeBoolean(te.checkNbt);
//			}
//			break;
//		case PID.TileWaypoint: //sync tile waypoint
//			{
//				TileEntityWaypoint te = (TileEntityWaypoint) tile;
//				
//				buf.writeByte(this.type);
//				buf.writeInt(this.tile.xCoord);
//				buf.writeInt(this.tile.yCoord);
//				buf.writeInt(this.tile.zCoord);
//				buf.writeInt(te.lx);
//				buf.writeInt(te.ly);
//				buf.writeInt(te.lz);
//				buf.writeInt(te.nx);
//				buf.writeInt(te.ny);
//				buf.writeInt(te.nz);
//			}
//			break;
		case PID.SyncPlayerProp:	//sync player props
			{
				buf.writeByte(this.type);
				
				//ring
				buf.writeByte(capa.isRingActiveI());
				buf.writeInt(capa.getMarriageNum());
				
				//player uid
				buf.writeInt(capa.getPlayerUID());
				
				//ship team id
				buf.writeInt(capa.getPointerTeamID());
				
				//ship formation id
				int[] fid = capa.getFormatID();
				for (int j = 0; j < 9; j++)
				{
					buf.writeByte((byte) fid[j]);
				}
				
				//ship team list
				for (int i = 0; i < 6; i++)
				{
					//get entity id
					if (capa.getShipEntityCurrentTeam(i) != null)
					{
						buf.writeInt(capa.getShipEntityCurrentTeam(i).getEntityId());
					}
					else
					{
						buf.writeInt(-1);
					}
					//get ship UID
					buf.writeInt(capa.getSIDCurrentTeam(i));
					//get select state
					buf.writeBoolean(capa.getSelectStateCurrentTeam(i));
				}
			}
			break;
		case PID.SyncShipInv:	//sync ship inventory GUI: kills and grudge
			{
				buf.writeByte(this.type);
				buf.writeInt(ship.getEntityId());
				buf.writeInt(ship.getStateMinor(ID.M.Kills));
				buf.writeInt(ship.getStateMinor(ID.M.NumGrudge));
				buf.writeInt(ship.getCapaShipInventory().getInventoryPage());
			}
			break;
		case PID.FlagInitSID:	//ship UID init flag
			{
				buf.writeByte(this.type);
				buf.writeBoolean(flag);
			}
			break;
		case PID.SyncShipList:				  //send ship list to client
		case PID.SyncPlayerProp_TargetClass:  //sync target class
		case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
		case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
			{
				buf.writeByte(this.type);
				
				//send list
				if (dataList != null)
				{
					//send list length
					buf.writeInt(dataList.size());
					
					//send list data
					Iterator iter = dataList.iterator();
					
					switch (this.type)
					{
					case PID.SyncShipList:
					case PID.SyncPlayerProp_ColledShip:
					case PID.SyncPlayerProp_ColledEquip:
						//send ship id array
						while (iter.hasNext())
						{
							//int list
							buf.writeInt((Integer) iter.next());
						}
						break;
					case PID.SyncPlayerProp_TargetClass:
						LogHelper.info("DEBUG : S2C gui packet: send list size "+dataList.size());
						while (iter.hasNext())
						{
							//string list
							ByteBufUtils.writeUTF8String(buf, (String) iter.next());
						}
						break;
					}
				}
				else
				{
					buf.writeInt(-1);
				}
			}
			break;
		case PID.SyncPlayerProp_TeamData:  //sync all team data to client
			{
				buf.writeByte(this.type);
				buf.writeInt(this.capa.getPlayerTeamID());
				
				//get team data
				this.dataMap = ServerProxy.getAllTeamWorldData();
//				LogHelper.info("DEBUG : S2C GUI Packet: send team data: "+this.dataMap.size());
				//has data
				if (this.dataMap != null)
				{
					int tsize = this.dataMap.size();
					buf.writeInt(tsize);
					
					Iterator iter = this.dataMap.entrySet().iterator();
					
					while (iter.hasNext())
					{
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
				else
				{
					buf.writeInt(-1);
				}
			}
			break;
		case PID.SyncPlayerProp_Formation:    //sync formation id
			{
				buf.writeByte(this.type);
				
				//send int array
				if (this.value3 != null)
				{
					//send list length
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
			}
			break;
		case PID.SyncPlayerProp_ShipsAll:		//sync all ships in team list 0~8
			{
				buf.writeByte(this.type);
				
				//ship team id
				buf.writeInt(capa.getPointerTeamID());
				
				//ship formation id
				int[] fid = capa.getFormatID();
				for (int j = 0; j < 9; j++)
				{
					buf.writeByte((byte) fid[j]);
				}
				
				BasicEntityShip[][] ships = capa.getShipEntityAllTeams();
				int[][] sids = capa.getSID();
				boolean[][] sels = capa.getSelectStateAllTeams();
				
				if (ships != null && sids != null && sels != null)
				{
					buf.writeByte(1);  //get data flag
					
					//ship team list
					for (int k = 0; k < 9; k++)
					{
						for (int i = 0; i < 6; i++)
						{
							//get entity id
							if (ships[k][i] != null)
							{
								buf.writeInt(ships[k][i].getEntityId());
							}
							else
							{
								buf.writeInt(-1);
							}
							
							//get ship UID
							buf.writeInt(sids[k][i]);
							
							//get select state
							buf.writeBoolean(sels[k][i]);
						}//end for all ships in team
					}//end for all teams
				}
				else
				{
					buf.writeByte(-1); //no data flag
				}
			}
			break;
		case PID.SyncPlayerProp_ShipsInTeam:  //sync ships in a team
			{
				buf.writeByte(this.type);
				
				//ship team id
				buf.writeInt(this.value3[0]);
				
				//ship formation id
				buf.writeByte(this.capa.getFormatID(this.value3[0]));
				
				BasicEntityShip[] ships = capa.getShipEntityAll(this.value3[0]);
				int[] sids = capa.getSID(this.value3[0]);
				boolean[] sels = capa.getSelectState(this.value3[0]);
				
				if (ships != null && sids != null && sels != null)
				{
					buf.writeByte(1);  //get data flag
					
					//ship team list
					for (int i = 0; i < 6; i++)
					{
						//get entity id
						if (ships[i] != null)
						{
							buf.writeInt(ships[i].getEntityId());
						}
						else
						{
							buf.writeInt(-1);
						}
						
						//get ship UID
						buf.writeInt(sids[i]);
						
						//get select state
						buf.writeBoolean(sels[i]);
					}//end for all ships in team
				}
				else
				{
					buf.writeByte(-1); //no data flag
				}
			}
			break;
		}//end send packet type switch
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CGUIPackets, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CGUIPackets message, MessageContext ctx)
		{
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv GUI Click packet : type "+recvType+" button ");
			return null;
		}
    }
	
	
}