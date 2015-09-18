package com.lulan.shincolle.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.server.ShinWorldData;
import com.lulan.shincolle.utility.LogHelper;

public class ServerProxy extends CommonProxy {
	
	/**player extended data
	 * all player extended data cache, delete when server close
	 * 
	 * extendedEntityData <player UUID, player NBT data>
	 * 
	 * use:
	 * 1. load from file: none
	 * 2. save to file: none
	 * 3. get: player respawn event
	 * 4. set: player login/logout/death event
	 */
	private static Map<String, NBTTagCompound> extendedPlayerData = null;
	
	/**player id cache
	 * for owner/team check, use map for data cache, save to .dat file when server close
	 * entity建立時會讀取此map來取得owner id, 並存在自己的nbt中, 以後就直接以該entity存的owner id來判斷owner
	 * 
	 * -1/0 = no owner
	 * 100~N = player
	 * -100 = hostile mob
	 * 
	 * mapPlayerID <player UID(Integer), player data(int[])>
	 * player data = 0:player entity id(int), 1:player team id(int)
	 * 
	 * use:
	 * 1. load from file: player login event
	 * 2. save to file: server tick (auto), set new player data
	 * 3. get: owner/team check method
	 * 4. set: player login event (first time)
	 */
	private static Map<Integer, int[]> mapPlayerID = null;
	
	/**ship id cache
	 * for pointer command, UID維持固定, entity id每次建立entity時更新
	 * delete when server close
	 * 
	 * mapShipID <ship UID, ship data>
	 * ship data = 0:ship entity id(int)
	 * 
	 * use:
	 * 1. load from file: none
	 * 2. save to file: none
	 * 3. get: pointer command method
	 * 4. set: ship entity construction (when loading ExtendShipProp)
	 */
	private static Map<Integer, int[]> mapShipID = null;
	
	/**next id
	 * for player UID / ship UID / team ID
	 * -1 for init
	 * 
	 * use:
	 * 1. load from file: server start
	 * 2. save to file: server tick (auto), server close
	 * 3. get: player login event (new player), ship entity construction (new ship), check team
	 * 4. set: player login event (new player), ship entity construction (new ship), create team
	 */
	private static int nextPlayerID = -1;
	private static int nextShipID = -1;
	private static int nextTeamID = -1;
	
	/**server global files
	 */
	private static MapStorage serverFile = null;
	private static ShinWorldData serverData = null;
	public static boolean initServerFile = false;	//when open a save or world -> reset to false
	

	//init server proxy variables, called when world loaded and initServerFile = false
	public static void initServerProxy(World world) {
		LogHelper.info("DEBUG : init server proxy");
		
		//clear last load
		serverFile = null;
		serverData = null;
		extendedPlayerData = null;
		mapPlayerID = null;
		mapShipID = null;
		
		//init data by default value
		extendedPlayerData = new HashMap<String, NBTTagCompound>();
		mapPlayerID = new HashMap<Integer, int[]>();
		mapShipID = new HashMap<Integer, int[]>();
		nextPlayerID = -1;
		nextShipID = -1;
		nextTeamID = -1;
		serverFile = world.mapStorage;
		serverData = (ShinWorldData) serverFile.loadData(ShinWorldData.class, ShinWorldData.SAVEID);
		
		//init data by MapStorage data
		if(serverData != null && serverData.nbtData != null) {
			LogHelper.info("DEBUG : init server proxy: get data from .dat file");
			//load common variable
			setNextPlayerID(serverData.nbtData.getInteger(serverData.TAG_NEXTPLAYERID));
			setNextShipID(serverData.nbtData.getInteger(serverData.TAG_NEXTSHIPID));
			setNextTeamID(serverData.nbtData.getInteger(serverData.TAG_NEXTTEAMID));
			
			//load player data:  from server save file to playerMap
			NBTTagList list = serverData.nbtData.getTagList(serverData.TAG_PLAYERDATA, 9);
			
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound getlist = list.getCompoundTagAt(i);
				int uid = getlist.getInteger(serverData.TAG_PUID);
				int[] data = getlist.getIntArray(serverData.TAG_PDATA);
				
				setPlayerWorldData(uid, data);
			}
			
			initServerFile = true;
		}
		//no such file, create one
		else if(serverData == null) {
			LogHelper.info("DEBUG : init server proxy: create .dat file");
			serverData = new ShinWorldData();
			serverFile.setData(ShinWorldData.SAVEID, serverData);
			initServerFile = true;
		}
	}
	
	public static MinecraftServer getServer() {
		return MinecraftServer.getServer();
	}
	
	public static WorldServer[] getServerWorld() {
		return MinecraftServer.getServer().worldServers;
	}
	
	@Override
	public void registerKeyBindings() {		
	}

	@Override
	public void registerRender() {		
	}
	
	/** save player data for resuming data after player death */
	//map set: UUID, nbt data
	public static void setPlayerData(String uuid, NBTTagCompound nbt) {
		extendedPlayerData.put(uuid, nbt);
	}

	//get nbt data in map
	public static NBTTagCompound getPlayerData(String name) {
		return extendedPlayerData.get(name);
	}	
	
	/** player world data for owner check...etc */
	//get player world data
	public static int[] getPlayerWorldData(int par1) {
//		LogHelper.info("DEBUG : Server Proxy: get player data");
		return mapPlayerID.get(par1);
	}
	
	//set player world data
	public static void setPlayerWorldData(int pid, int[] pdata) {
//		LogHelper.info("DEBUG : Server Proxy: set player data");
		if(pid > 0 && pdata != null) {
			mapPlayerID.put(pid, pdata);
		}
	}
	
	//check mapPlayerID is empty
	public static boolean isPlayerWorldDataEmpty() {
		return mapPlayerID.isEmpty();
	}
	
	/** ship world data for owner check...etc */
	//get player world data
	public static int[] getShipWorldData(int par1) {
//		LogHelper.info("DEBUG : Server Proxy: get ship data");
		return mapShipID.get(par1);
	}
	
	//set player world data
	public static void setShipWorldData(int pid, int[] pdata) {
//		LogHelper.info("DEBUG : Server Proxy: set ship data");
		if(pid > 0 && pdata != null) {
			mapShipID.put(pid, pdata);
		}
	}
	
	public static int getNextPlayerID() {
		return nextPlayerID;
	}
	
	public static void setNextPlayerID(int par1) {
		if(serverData != null) {
			nextPlayerID = par1;
			serverData.markDirty();
		}
	}
	
	public static int getNextShipID() {
		return nextShipID;
	}
	
	public static void setNextShipID(int par1) {
		if(serverData != null) {
			nextShipID = par1;
			serverData.markDirty();
		}
	}
	
	public static int getNextTeamID() {
		return nextTeamID;
	}
	
	public static void setNextTeamID(int par1) {
		if(serverData != null) {
			nextTeamID = par1;
			serverData.markDirty();
		}
	}
	
	public static Map<Integer, int[]> getAllPlayerWorldData() {
		return mapPlayerID;
	}
	
	public static Map<Integer, int[]> getAllShipWorldData() {
		return mapShipID;
	}
	
	/** update player id */
	public static void updatePlayerID(EntityPlayer player) {
		LogHelper.info("DEBUG : update player: "+player.getDisplayName()+" "+player.getUniqueID());
		
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			int pid = extProps.getPlayerUID();
			int[] pdata = new int[2];
			
			pdata[0] = player.getEntityId();
			pdata[1] = extProps.getPlayerTeamId();
			
			//update player data
			if(pid > 0) {
				LogHelper.info("DEBUG : update player: update player id "+pid+" eid: "+pdata[0]);
				setPlayerWorldData(pid, pdata);
			}
			//player id < 0, create one
			else {
				pid = getNextPlayerID();
				//pid isn't init, set init value
				if(pid <= 0) {
					pid = 100;	//player id init value = 100
				}
				
				LogHelper.info("DEBUG : update player: create pid: "+pid+" eid: "+pdata[0]);
				extProps.setPlayerUID(pid);	//set player id
				setPlayerWorldData(pid, pdata);	//cache in server proxy
				setNextPlayerID(++pid);	//next id ++
			}
		}
		else {
			LogHelper.info("DEBUG : update player: fail: player extProps = null");
		}
	}
	
	/** update ship id */
	public static void updateShipID(BasicEntityShip ship) {
		LogHelper.info("DEBUG : update entity id to ServerProxy");
		
		int sid = ship.getShipUID();
		int[] sdata = new int[1];
		
		sdata[0] = ship.getEntityId();
		
		//update ship data
		if(sid > 0) {
			LogHelper.info("DEBUG : update ship: update ship id "+sid+" eid: "+sdata[0]);
			setShipWorldData(sid, sdata);	//cache in server proxy
		}
		//ship id < 0, create one
		else {
			sid = getNextShipID();
			//set init value
			if(sid <= 0) {
				sid = 100;	//ship id init value = 100
			}
			
			LogHelper.info("DEBUG : update ship: create sid: "+sid+" eid: "+sdata[0]);
			ship.setShipUID(sid);
			setShipWorldData(sid, sdata);	//cache in server proxy
			setNextShipID(++sid);	//next id ++
		}
	}
	
	/** update ship owner id */
	public static void updateShipOwnerID(BasicEntityShip ship) {
		Entity owner = ship.getOwner();
		
		LogHelper.info("DEBUG : update ship: get owner id");
		if(owner instanceof EntityPlayer) {
			//get player UID from player's extend props
			ExtendPlayerProps extProps = (ExtendPlayerProps) owner.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				int pid = extProps.getPlayerUID();
				LogHelper.info("DEBUG : update ship: set owner id: "+pid);
				ship.setPlayerUID(pid);
			}
		}
	}

	

}
