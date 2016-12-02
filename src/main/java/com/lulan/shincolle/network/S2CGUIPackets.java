package com.lulan.shincolle.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


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
	private List dataList;
	private Map dataMap;
	private byte packetType;
	private int entityID, valueInt;
	private boolean valueBoolean;
	private int[] valueInt1;
	private byte[] valueByte1;
	private boolean[] valueBoolean1;
	private int[][] valueInt2, valueInt2a;
	private boolean[][] valueBoolean2;
	
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
		public static final byte SyncPlayerProp_Misc = 17;
	}
	
	
	public S2CGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI sync: 
	//sync tile entity
	public S2CGUIPackets(TileEntity tile, byte type)
	{
		this.tile = tile;
		this.packetType = type;
    }
	
	//sync player extend props
	public S2CGUIPackets(CapaTeitoku capa, byte type)
	{
		if (capa != null)
		{
			this.packetType = type;
	        this.capa = capa;

	        switch (type)
	        {
	        case PID.SyncPlayerProp_TargetClass:
	        	this.dataList = PacketHelper.getStringListFromStringMap(ServerProxy.getPlayerTargetClass(
	        																this.capa.getPlayerUID()));
	        	break;
	        case PID.SyncPlayerProp_Formation:
	        	this.valueInt1 = this.capa.getFormatID();
	        	break;
	        }
		}
    }
	
	//sync player extend props
	public S2CGUIPackets(CapaTeitoku capa, byte type, int...parms)
	{
		if (capa != null)
		{
			this.packetType = type;
	        this.capa = capa;
	        
	        if (parms != null && parms.length > 0)
	        {
	        	this.valueInt1 = parms;
	        }
		}
    }
	
	//sync ship
	public S2CGUIPackets(BasicEntityShip ship)
	{
        this.packetType = PID.SyncShipInv;
        this.ship = ship;
    }
	
	//sync int + boolean data
	public S2CGUIPackets(byte type, int value, boolean flag)
	{
        this.packetType = type;
        this.valueInt = value;
        this.valueBoolean = flag;
    }
	
	//sync list data
	public S2CGUIPackets(byte type, List list)
	{
		this.packetType = type;
		this.dataList = list;
	}
	
	/** sync int array data
	 * 
	 *  1. (2 parms) sync formation data: 0:teamID, 1:formatID
	 */
	public S2CGUIPackets(byte type, int...parms)
	{
        this.packetType = type;
        
        if (parms != null && parms.length > 0)
        {
        	this.valueInt1 = parms;
        }
    }
	
	//接收packet: CLIENT端
	@Override
	public void fromBytes(ByteBuf buf)
	{
		//get type and entityID
		this.packetType = buf.readByte();
	
		switch (this.packetType)
		{
		case PID.TileSmallSY:	//sync small shipyard gui
			this.valueInt1 = PacketHelper.readIntArray(buf, 6);
		break;
		case PID.TileLargeSY:	//sync large shipyard gui
			this.valueInt1 = PacketHelper.readIntArray(buf, 10);
		break;
		case PID.TileDesk:		//sync tile desk
			this.valueInt1 = PacketHelper.readIntArray(buf, 7);
		break;
		case PID.TileVolCore:	//sync tile volcano core
			this.valueInt1 = PacketHelper.readIntArray(buf, 5);
		break;
		case PID.TileWaypoint:	//sync tile waypoint
			this.valueInt1 = PacketHelper.readIntArray(buf, 9);
		break;
		case PID.TileCrane:		//sync tile crane
			this.valueInt1 = PacketHelper.readIntArray(buf, 14);
			this.valueByte1 = PacketHelper.readByteArray(buf, 4);
		break;
		case PID.SyncShipInv:	//sync ship GUI
			this.valueInt1 = PacketHelper.readIntArray(buf, 4);
		break;
		case PID.SyncPlayerProp: //sync player props
			this.valueInt1 = new int[5+9+12];
			this.valueBoolean1 = new boolean[6];
			
			//int 0~4: misc data
			this.valueInt1[0] = buf.readByte();	//0: ring active
			this.valueInt1[1] = buf.readByte();	//1: has team flag
			this.valueInt1[2] = buf.readInt();	//2: marriage num
			this.valueInt1[3] = buf.readInt();	//3: player uid
			this.valueInt1[4] = buf.readByte();	//4: current team id
			
			//int 5~13: formation id
			for (int i = 0; i < 9 ; ++i)
			{
				this.valueInt1[i + 5] = buf.readByte();
			}
			
			//int 14~25: ship team eid+uid, boolean 0~6: select state
			for (int j = 0; j < 6; ++j)
			{
				this.valueInt1[j * 2 + 14] = buf.readInt();	//ship i entity ID
				this.valueInt1[j * 2 + 15] = buf.readInt();	//ship i ship UID
				this.valueBoolean1[j] = buf.readBoolean();	//ship i select state
			}
		break;
		case PID.SyncPlayerProp_Misc: //sync player props misc data
			this.valueInt1 = new int[5];
			
			//int 0~4: misc data
			this.valueInt1[0] = buf.readByte();	//0: ring active
			this.valueInt1[1] = buf.readByte();	//1: has team flag
			this.valueInt1[2] = buf.readInt();	//2: marriage num
			this.valueInt1[3] = buf.readInt();	//3: player uid
			this.valueInt1[4] = buf.readByte();	//4: current team id
		break;
		case PID.FlagInitSID:	//ship UID init flag
			this.valueBoolean = buf.readBoolean();
		break;
		case PID.SyncShipList:				  //sync loaded ship list
		case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
		case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
			this.valueInt = buf.readInt();
			this.dataList = new ArrayList();
			
			if (this.valueInt > 0)
			{
				//get list data
				for (int i = 0; i < this.valueInt; ++i)
				{
					this.dataList.add(buf.readInt());
				}
			}
		break;
		case PID.SyncPlayerProp_TargetClass:	//sync ship list
		{
			this.valueInt = buf.readInt();
			String str;
			this.dataMap = new HashMap();

			if (this.valueInt > 0)
			{
				//get list data
				for (int i = 0; i < this.valueInt; ++i)
				{
					str = ByteBufUtils.readUTF8String(buf);
					this.dataMap.put(str.hashCode(), str);
				}
			}
		}
		break;
		case PID.SyncPlayerProp_TeamData:
		{
			this.valueInt = buf.readInt();	//team size
			this.dataMap = new HashMap();
			
			for (int i = 0; i < this.valueInt; ++i)
			{
				TeamData tdata = new TeamData();
				List list;
				String name;
				
				tdata.setTeamID(buf.readInt());
				
				//get team banned list
				list = PacketHelper.readListInt(buf);
				tdata.setTeamBannedList(list);
				
				//get team ally list
				list = PacketHelper.readListInt(buf);
				tdata.setTeamAllyList(list);
				
				//get team name
				name = PacketHelper.readString(buf);
				tdata.setTeamName(name);
				
				//get leader name
				name = PacketHelper.readString(buf);
				tdata.setTeamLeaderName(name);
				
				this.dataMap.put(tdata.getTeamID(), tdata);
			}
		}
		break;
		case PID.SyncPlayerProp_Formation:
			this.valueByte1 = PacketHelper.readByteArray(buf, 9);
		break;
		case PID.SyncPlayerProp_ShipsAll:
			this.valueInt1 = new int[1+9+1];
			
			//int 0: current team id
			this.valueInt1[0] = buf.readInt();
			
			//int 1~9: formation id
			for (int i = 0; i < 9; i++)
			{
				this.valueInt1[i + 1] = buf.readByte();
			}
			
			//has team data flag
			this.valueInt1[10] = buf.readByte();
			
			//get team data
			if (this.valueInt1[10] > 0)
			{
				this.valueInt2 = new int[9][6];
				this.valueInt2a = new int[9][6];
				this.valueBoolean2 = new boolean[9][6];
				
				for (int k = 0; k < 9; k++)
				{
					for (int i = 0; i < 6; i++)
					{
						this.valueInt2[k][i] = buf.readInt();
						this.valueInt2a[k][i] = buf.readInt();
						this.valueBoolean2[k][i] = buf.readBoolean();
					}
				}
			}
		break;
		case PID.SyncPlayerProp_ShipsInTeam:
			this.valueInt1 = new int[3+12];
			this.valueBoolean1 = new boolean[6];
			
			this.valueInt1[0] = buf.readInt();	//int 0: team id
			this.valueInt1[1] = buf.readByte();	//int 1: format id
			this.valueInt1[2] = buf.readByte();	//int 2: has team data flag
			
			//get team data
			if (this.valueInt1[2] > 0)
			{
				//int 3~14: ship eid+uid, boolean 0~6: select state
				for (int i = 0; i < 6; i++)
				{
					this.valueInt1[i * 2 + 3] = buf.readInt();
					this.valueInt1[i * 2 + 4] = buf.readInt();
					this.valueBoolean1[i] = buf.readBoolean();
				}
			}
		break;
		}//end switch
	}

	//發出packet: SERVER端
	@Override
	public void toBytes(ByteBuf buf)
	{
		//send packet type
		buf.writeByte(this.packetType);
		
		switch (this.packetType)
		{
		case PID.TileSmallSY: //sync small shipyard gui
		{
			TileEntitySmallShipyard tile2 = (TileEntitySmallShipyard) this.tile;
			
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
			TileMultiGrudgeHeavy tile2 = (TileMultiGrudgeHeavy) this.tile;
			
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
		case PID.TileDesk:  //sync admiral desk
		{
			TileEntityDesk tile2 = (TileEntityDesk) this.tile;
			
			buf.writeInt(tile2.getPos().getX());
			buf.writeInt(tile2.getPos().getY());
			buf.writeInt(tile2.getPos().getZ());
			buf.writeInt(tile2.getField(0));
			buf.writeInt(tile2.getField(1));
			buf.writeInt(tile2.getField(2));
			buf.writeInt(tile2.getField(3));
		}
		break;
		case PID.TileVolCore: //sync tile volcano core
		{
			TileEntityVolCore tile2 = (TileEntityVolCore) this.tile;
			
			buf.writeInt(tile2.getPos().getX());
			buf.writeInt(tile2.getPos().getY());
			buf.writeInt(tile2.getPos().getZ());
			buf.writeInt(tile2.getPowerRemained());
			buf.writeInt(tile2.getField(0));
		}
		break;
		case PID.TileCrane: //sync tile crane
		{
			TileEntityCrane tile2 = (TileEntityCrane) this.tile;
			
			buf.writeInt(tile2.getPos().getX());
			buf.writeInt(tile2.getPos().getY());
			buf.writeInt(tile2.getPos().getZ());
			buf.writeInt(tile2.getLastWaypoint().getX());
			buf.writeInt(tile2.getLastWaypoint().getY());
			buf.writeInt(tile2.getLastWaypoint().getZ());
			buf.writeInt(tile2.getNextWaypoint().getX());
			buf.writeInt(tile2.getNextWaypoint().getY());
			buf.writeInt(tile2.getNextWaypoint().getZ());
			buf.writeInt(tile2.getChestWaypoint().getX());
			buf.writeInt(tile2.getChestWaypoint().getY());
			buf.writeInt(tile2.getChestWaypoint().getZ());
			
			if (tile2.getShip() != null)
			{
				buf.writeInt(tile2.getShip().getEntityId());
			}
			else
			{
				buf.writeInt(-1);
			}
			
			buf.writeInt(tile2.getField(5));
			
			buf.writeByte(tile2.getField(2));
			buf.writeByte(tile2.getField(3));
			buf.writeByte(tile2.getField(4));
			buf.writeByte(tile2.getField(8));
		}
		break;
		case PID.TileWaypoint: //sync tile waypoint
		{
			TileEntityWaypoint tile2 = (TileEntityWaypoint) this.tile;
			
			buf.writeInt(tile2.getPos().getX());
			buf.writeInt(tile2.getPos().getY());
			buf.writeInt(tile2.getPos().getZ());
			buf.writeInt(tile2.getLastWaypoint().getX());
			buf.writeInt(tile2.getLastWaypoint().getY());
			buf.writeInt(tile2.getLastWaypoint().getZ());
			buf.writeInt(tile2.getNextWaypoint().getX());
			buf.writeInt(tile2.getNextWaypoint().getY());
			buf.writeInt(tile2.getNextWaypoint().getZ());
		}
		break;
		case PID.SyncPlayerProp:	//sync player props
		{
			//misc data
			buf.writeByte(capa.isRingActive() ? 1 : 0);
			buf.writeByte(capa.hasTeam() ? 1 : 0);
			buf.writeInt(capa.getMarriageNum());
			buf.writeInt(capa.getPlayerUID());
			buf.writeByte(capa.getPointerTeamID());
			
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
		case PID.SyncPlayerProp_Misc:	//sync player props misc data
		{
			//misc data
			buf.writeByte(capa.isRingActive() ? 1 : 0);
			buf.writeByte(capa.hasTeam() ? 1 : 0);
			buf.writeInt(capa.getMarriageNum());
			buf.writeInt(capa.getPlayerUID());
			buf.writeByte(capa.getPointerTeamID());
		}
		break;
		case PID.SyncShipInv:	//sync ship inventory GUI: kills and grudge
			buf.writeInt(ship.getEntityId());
			buf.writeInt(ship.getStateMinor(ID.M.Kills));
			buf.writeInt(ship.getStateMinor(ID.M.NumGrudge));
			buf.writeInt(ship.getCapaShipInventory().getInventoryPage());
		break;
		case PID.FlagInitSID:	//ship UID init flag
			buf.writeBoolean(this.valueBoolean);
		break;
		case PID.SyncShipList:				  //send ship list to client
		case PID.SyncPlayerProp_TargetClass:  //sync target class
		case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
		case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
		{
			//send list
			if (this.dataList != null)
			{
				//send list length
				buf.writeInt(this.dataList.size());
				
				//send list data
				Iterator iter = this.dataList.iterator();
				
				switch (this.packetType)
				{
				case PID.SyncShipList:
				case PID.SyncPlayerProp_ColledShip:
				case PID.SyncPlayerProp_ColledEquip:
					while (iter.hasNext())
					{
						buf.writeInt((int) iter.next());
					}
				break;
				case PID.SyncPlayerProp_TargetClass:
					while (iter.hasNext())
					{
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
			//get team data
			this.dataMap = ServerProxy.getAllTeamWorldData();
			
			//has data
			if (this.dataMap != null)
			{
				//send map size
				buf.writeInt(this.dataMap.size());
				
				//send map data
				this.dataMap.forEach((k, v) ->
				{
					int tid = (Integer) k;
					TeamData tdata = (TeamData) v;
					
					//send team id
					buf.writeInt(tid);

					//send team banned list
					PacketHelper.sendListInt(buf, tdata.getTeamBannedList());
					
					//send team ally list
					PacketHelper.sendListInt(buf, tdata.getTeamAllyList());
					
					//send team name
					PacketHelper.sendString(buf, tdata.getTeamName());
					
					//send leader name
					PacketHelper.sendString(buf, tdata.getTeamLeaderName());
				});
			}
			//no data
			else
			{
				buf.writeInt(-1);
			}
		}
		break;
		case PID.SyncPlayerProp_Formation:    //sync formation id
			//send formation id (int turn to byte)
			for (int i : this.valueInt1)
			{
				buf.writeByte((byte) i);
			}
		break;
		case PID.SyncPlayerProp_ShipsAll:		//sync all ships in team list 0~8
		{
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
			//ship team id
			buf.writeInt(this.valueInt1[0]);
			
			//ship formation id
			buf.writeByte(this.capa.getFormatID(this.valueInt1[0]));
			
			BasicEntityShip[] ships = capa.getShipEntityAll(this.valueInt1[0]);
			int[] sids = capa.getSID(this.valueInt1[0]);
			boolean[] sels = capa.getSelectState(this.valueInt1[0]);
			
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
	
	//packet handle method
	private static void handle(S2CGUIPackets msg, MessageContext ctx)
	{
		World world = ClientProxy.getClientWorld();
		
		switch (msg.packetType)
		{
		case PID.TileSmallSY: //sync small shipyard gui
		{
			//get tile
			TileEntity tile = world.getTileEntity(new BlockPos(
					msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
			
			//set tile
			if (tile instanceof TileEntitySmallShipyard)
			{
				TileEntitySmallShipyard tile2 = (TileEntitySmallShipyard) tile;
				tile2.setPowerConsumed(msg.valueInt1[3]);
				tile2.setPowerRemained(msg.valueInt1[4]);
				tile2.setPowerGoal(msg.valueInt1[5]);
			}
		}
		break;
		case PID.TileLargeSY: //sync large shipyard gui
		{
			//get tile
			TileEntity tile = world.getTileEntity(new BlockPos(
					msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
			
			//set tile
			if (tile instanceof TileMultiGrudgeHeavy)
			{
				TileMultiGrudgeHeavy tile2 = (TileMultiGrudgeHeavy) tile;
				tile2.setPowerConsumed(msg.valueInt1[3]);
				tile2.setPowerRemained(msg.valueInt1[4]);
				tile2.setPowerGoal(msg.valueInt1[5]);
				tile2.setMatStock(0, msg.valueInt1[6]);
				tile2.setMatStock(1, msg.valueInt1[7]);
				tile2.setMatStock(2, msg.valueInt1[8]);
				tile2.setMatStock(3, msg.valueInt1[9]);
			}
		}
		break;
		case PID.TileDesk:
		{
			//get tile
			TileEntity tile = world.getTileEntity(new BlockPos(
					msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
			
			if (tile instanceof TileEntityDesk)
			{
				int[] data = new int[4];
				for (int i = 0; i < 4; i++)
				{
					data[i] = msg.valueInt1[i + 3];
				}
				((TileEntityDesk) tile).setSyncData(data);
			}
		}
		break;
		case PID.TileVolCore: //sync tile volcano core
		{
			//get tile
			TileEntity tile = world.getTileEntity(new BlockPos(
					msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
			
			if (tile instanceof TileEntityVolCore)
			{
				((TileEntityVolCore) tile).setPowerRemained(msg.valueInt1[3]);
				((TileEntityVolCore) tile).setField(0, msg.valueInt1[4]);
			}
		}
		break;
		case PID.TileWaypoint: //sync tile waypoint
		{
			//get tile
			TileEntity tile = world.getTileEntity(new BlockPos(
					msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
			
			if (tile instanceof TileEntityWaypoint)
			{
				int[] data = new int[6];
				for (int i = 0; i < 6; i++)
				{
					data[i] = msg.valueInt1[i + 3];
				}
				((TileEntityWaypoint) tile).setSyncData(data);
			}
		}
		break;
		case PID.TileCrane: //sync tile crane
		{
			//get tile
			TileEntity tile = world.getTileEntity(new BlockPos(
					msg.valueInt1[0], msg.valueInt1[1], msg.valueInt1[2]));
			
			//get entity
			Entity entity = EntityHelper.getEntityByID(msg.valueInt1[12], 0, true);
			
			if (tile instanceof TileEntityCrane && entity instanceof BasicEntityShip)
			{
				TileEntityCrane tile2 = (TileEntityCrane) tile;
				int[] data = new int[9];
				for (int i = 0; i < 9; i++)
				{
					data[i] = msg.valueInt1[i + 3];
				}
				
				tile2.setSyncData(data);
				tile2.setShip((BasicEntityShip) entity);
				tile2.setField(2, msg.valueByte1[0]);
				tile2.setField(3, msg.valueByte1[1]);
				tile2.setField(4, msg.valueByte1[2]);
				tile2.setField(8, msg.valueByte1[3]);
				tile2.setField(5, msg.valueInt1[13]);
			}
		}
		break;
		case PID.SyncPlayerProp: //sync player props
		{
			int[] misc = new int[5];
			for (int i = 0; i < 5; i++)
			{
				misc[i] = msg.valueInt1[i];
			}
			
			int[] formatID = new int[9];
			for (int i = 0; i < 9; i++)
			{
				formatID[i] = msg.valueInt1[i + 5];
			}

			int[] shipList = new int[12];
			for (int i = 0; i < 6; i++)
			{
				shipList[i * 2] = msg.valueInt1[i * 2 + 14];
				shipList[i * 2 + 1] = msg.valueInt1[i * 2 + 15];
			}
			
			//set value
			CapaTeitoku.setCapaDataMisc(misc);
			CapaTeitoku.setCapaDataTeamList(msg.valueInt1[4], formatID, shipList, msg.valueBoolean1);
		}
		break;
		case PID.SyncPlayerProp_Misc: //sync player props
		{
			//set value
			CapaTeitoku.setCapaDataMisc(msg.valueInt1);
		}
		break;
		case PID.SyncShipInv:	//sync ship GUI
		{
			//set value
			Entity entity = EntityHelper.getEntityByID(msg.valueInt1[0], 0, true);
			
			if (entity instanceof BasicEntityShip)
			{
				BasicEntityShip ship = (BasicEntityShip) entity;
				
				ship.setStateMinor(ID.M.Kills, msg.valueInt1[1]);
				ship.setStateMinor(ID.M.NumGrudge, msg.valueInt1[2]);
				ship.getCapaShipInventory().setInventoryPage(msg.valueInt1[3]);
			}
		}
		break;
		case PID.FlagInitSID:	//ship UID init flag
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();
			
			if (capa != null)
			{
				capa.setInitSID(msg.valueBoolean);
			}
		}
		break;
		case PID.SyncShipList:				  //sync loaded ship list
		case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
		case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
		{
			if (msg.valueInt > 0)
			{
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();

				if (capa != null)
				{
					switch (msg.packetType)
					{
					case PID.SyncShipList:				  //sync loaded ship list
						capa.setShipEIDList((ArrayList<Integer>) msg.dataList);
						break;
					case PID.SyncPlayerProp_ColledShip:   //sync colled ship list
						capa.setColleShipList((ArrayList<Integer>) msg.dataList);
						break;
					case PID.SyncPlayerProp_ColledEquip:  //sync colled equip list
						capa.setColleEquipList((ArrayList<Integer>) msg.dataList);
						break;
					}
				}
			}
		}
		break;
		case PID.SyncPlayerProp_TargetClass:	//sync ship list
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();

			if (msg.valueInt > 0)
			{
				if (capa != null)
				{
					capa.setTargetClass((HashMap<Integer, String>) msg.dataMap);
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
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();

			if (capa != null)
			{
				capa.setPlayerTeamDataMap((HashMap<Integer, TeamData>) msg.dataMap);
			}
		}
		break;
		case PID.SyncPlayerProp_Formation:
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();
			
			if (capa != null)
			{
				int[] formatID = new int[9];
				for (int i = 0; i < 9; i++)
				{
					formatID[i] = msg.valueByte1[i];
				}
				
				capa.setFormatID(formatID);
			}
		}
		break;
		case PID.SyncPlayerProp_ShipsAll:
		{
			//has team
			if (msg.valueInt1[10] > 0)
			{
				int[] formatID = new int[9];
				for (int i = 0; i < 9; i++)
				{
					formatID[i] = msg.valueInt1[i + 1];
				}
				
				//get ships array
				BasicEntityShip[][] ships = new BasicEntityShip[9][6];
				for (int j = 0; j < 9; j++)
				{
					for (int k = 0; k < 6; k++)
					{
						if (msg.valueInt2[j][k] > 0)
						{
							Entity ent = EntityHelper.getEntityByID(msg.valueInt2[j][k], 0, true);
							if (ent instanceof BasicEntityShip) ships[j][k] = (BasicEntityShip) ent;
						}
					}
					
				}
				
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();
				
				if (capa != null)
				{
					capa.setPointerTeamID(msg.valueInt1[0]);
					capa.setFormatID(formatID);
					capa.setTeamList(ships);
					capa.setSelectState(msg.valueBoolean2);
					capa.setSIDList(msg.valueInt2a);
				}
			}
		}
		break;
		case PID.SyncPlayerProp_ShipsInTeam:
		{
			if (msg.valueInt1[2] > 0)
			{
				BasicEntityShip[] ships = new BasicEntityShip[6];
				int[] uids = new int[6];
				
				for (int i = 0; i < 6; i++)
				{
					//get ship entity
					Entity ent = EntityHelper.getEntityByID(msg.valueInt1[i * 2 + 3], 0, true);
					if (ent instanceof BasicEntityShip) ships[i] = (BasicEntityShip) ent;
					
					//get ship uid
					uids[i] = msg.valueInt1[i * 2 + 4];
				}
				
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();
				
				if (capa != null)
				{
					capa.setFormatID(msg.valueInt1[0], msg.valueInt1[1]);
					capa.setTeamList(msg.valueInt1[0], ships);
					capa.setSelectState(msg.valueInt1[0], msg.valueBoolean1);
					capa.setSIDList(msg.valueInt1[0], uids);
				}
			}
		}
		break;
		}//end switch
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CGUIPackets, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CGUIPackets message, MessageContext ctx)
		{
			/**
			 * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
			 * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
			 * 以避免多執行緒下各種並行處理問題
			 */
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> S2CGUIPackets.handle(message, ctx));
			return null;
		}
    }
	
	
}