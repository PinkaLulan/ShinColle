package com.lulan.shincolle.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.server.ShinWorldData;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

public class ServerProxy extends CommonProxy {
	
	/**player extended data
	 * all player extended data cache, delete when server close
	 * 
	 * extendedPlayerData <player UUID: int, player NBT data: NBTTagCompound>
	 * 
	 * use:
	 * 1. load from file: none
	 * 2. save to file: none
	 * 3. get: player respawn event
	 * 4. set: player login/logout/death/saveNBTData event
	 */
	private static Map<String, NBTTagCompound> extendedPlayerData = null;
	
	/**player custom target class map
	 * all player's target class list, save at server side
	 * for continually working if player offline
	 * 
	 * customTagetClassList <player UUID: int, player target: list<String>>
	 * 
	 * use:
	 * 1. load from file: server start
	 * 2. save to file: server tick (auto), set new player data
	 * 3. get: TargetHelper for target selection
	 * 4. set: by client to server packet
	 * 
	 */
	private static Map<Integer, List<String>> customTagetClassList = null;
	
	/**unattackable target list
	 * entity in this list will not hurt by ship attack
	 * 
	 * unattackableTargetClassList List<class name>
	 * 
	 */
	private static List<String> unattackableTargetClassList = null;
	
	/**team data cache
	 * for team data display, owner check, etc
	 * team ID = player UID (1 player = 1 team for now), team keeps 1 ally and 1 banned team list
	 * 
	 * mapTeamID <team ID: int, team data: TeamData>
	 * 
	 * use:
	 * 1. load from file: server start
	 * 2. save to file: server tick (auto), create new team
	 * 3. get: view team
	 * 4. set: create team, server tick (clear team which no ppl in team)
	 * 
	 */
	private static Map<Integer, TeamData> mapTeamID = null;
	
	/**player id cache
	 * for owner/team check, use map for data cache, save to .dat file when server close
	 * entity建立時會讀取此map來取得owner id, 並存在自己的nbt中, 以後就直接以該entity存的owner id來判斷owner
	 * 
	 * player UID in other entity (owner check):
	 * -1/0 = no owner
	 * 1~N = player owner
	 * -100 = hostile mob
	 * 
	 * team id:
	 * -1/0 = not in pvp mode
	 * 1~N = pvp mode: team 1~N
	 * 
	 * mapPlayerID <player UID(Integer), player data(int[])>
	 * player data = 0:player entity id(int), 1:player team id(int) 2:world id(int)
	 * player team id = 0:no team, >0:in team
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
	 * ship data = 0:ship entity id(int) 1:world id(int)
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
	
	/**server global files */
	public static World world0;
	public static MapStorage serverFile = null;
	public static ShinWorldData serverData = null;
	public static boolean initServerFile = false;	//when open a save or world -> reset to false
	public static boolean savedServerFile = false;
	
	/**server global var */
	public static final String CUSTOM_TARGET_CLASS = "CustomTargetClass";
	public static final String UNATK_TARGET_CLASS = "UnatkTargetClass";
	public static int serverTicks = 0;
	public static int updateRadarTicks = ConfigHandler.radarUpdate;

	
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
		customTagetClassList = new HashMap<Integer, List<String>>();
		unattackableTargetClassList = new ArrayList<String>();
		mapPlayerID = new HashMap<Integer, int[]>();
		mapShipID = new HashMap<Integer, int[]>();
		mapTeamID = new HashMap<Integer, TeamData>();
		nextPlayerID = -1;
		nextShipID = -1;
		world0 = world;
		serverFile = world.mapStorage;
		serverData = (ShinWorldData) serverFile.loadData(ShinWorldData.class, ShinWorldData.SAVEID);
		
		//init data by MapStorage data
		if(serverData != null && serverData.nbtData != null) {
			LogHelper.info("DEBUG : init server proxy: get data from .dat file");
			//load common variable
			setNextPlayerID(serverData.nbtData.getInteger(ShinWorldData.TAG_NEXTPLAYERID));
			setNextShipID(serverData.nbtData.getInteger(ShinWorldData.TAG_NEXTSHIPID));
			
			
			//load unattackable list
			NBTTagList unatktag = serverData.nbtData.getTagList(UNATK_TARGET_CLASS, Constants.NBT.TAG_COMPOUND);
			LogHelper.info("DEBUG : init server proxy: get unattackable target list: count: "+unatktag.tagCount());
			List<String> unatklist = new ArrayList();
			
			for(int j = 0; j < unatktag.tagCount(); ++j) {
				String str = unatktag.getStringTagAt(j);

				if(str != null && str.length() > 1) {
					unatklist.add(str);
				}
			}
			
			setUnattackableTargetClassList(unatklist);
			
			
			//load player data:  from server save file to playerMap
			NBTTagList list = serverData.nbtData.getTagList(ShinWorldData.TAG_PLAYERDATA, Constants.NBT.TAG_COMPOUND);
			LogHelper.info("DEBUG : init server proxy: get player data count: "+list.tagCount());
			
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound getlist = list.getCompoundTagAt(i);
				int uid = getlist.getInteger(ShinWorldData.TAG_PUID);
				int[] data = getlist.getIntArray(ShinWorldData.TAG_PDATA);
				
				//get target class list
				//load custom target class list
				NBTTagList strListTag = getlist.getTagList(CUSTOM_TARGET_CLASS, Constants.NBT.TAG_STRING);
				List<String> strList = new ArrayList();
				
				for(int j = 0; j < strListTag.tagCount(); ++j) {
					String str = strListTag.getStringTagAt(j);

					if(str != null && str.length() > 1) {
						strList.add(str);
					}
				}
				
				LogHelper.info("DEBUG : init server proxy: get player data: UID "+uid+" DATA "+data[0]+" "+data[1]+" "+data[2]+" list size: "+strList.size()+" tag size: "+strListTag.tagCount());
				setPlayerWorldData(uid, data);
				setPlayerTargetClassList(uid, strList);
			}
			
			
			//load team data:  from server save file to playerMap
			NBTTagList list2 = serverData.nbtData.getTagList(ShinWorldData.TAG_TEAMDATA, Constants.NBT.TAG_COMPOUND);
			LogHelper.info("DEBUG : init server proxy: get team data count: "+list2.tagCount());
			for(int i = 0; i < list2.tagCount(); i++) {
				//get team data
				NBTTagCompound getlist = list2.getCompoundTagAt(i);
				int tUID = getlist.getInteger(ShinWorldData.TAG_TUID);
				String tName = getlist.getString(ShinWorldData.TAG_TNAME);
				String tLName = getlist.getString(ShinWorldData.TAG_TLNAME);
				int[] tBan = getlist.getIntArray(ShinWorldData.TAG_TBAN);
				List<Integer> tList1 = CalcHelper.intArrayToList(tBan);
				int[] tAlly = getlist.getIntArray(ShinWorldData.TAG_TALLY);
				List<Integer> tList2 = CalcHelper.intArrayToList(tAlly);
				
				//form team data
				TeamData tData = new TeamData();
				tData.setTeamID(tUID);
				tData.setTeamName(tName);
				tData.setTeamLeaderName(tLName);
				tData.setTeamBannedList(tList1);
				tData.setTeamAllyList(tList2);
				
				LogHelper.info("DEBUG : init server proxy: get team data: UID "+tUID+" NAME "+tName);
				setTeamData(tData);
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
	
	//force save server file when world unload (server close)
	public static void saveServerDataToFile() {
		if(serverData != null) serverData.markDirty();
		if(serverFile != null) serverFile.saveAllData();
		
		savedServerFile = true;
	}
	
	public static MinecraftServer getServer() {
		return MinecraftServer.getServer();
	}
	
	public static WorldServer[] getServerWorld() {
		return MinecraftServer.getServer().worldServers;
	}
	
	public static WorldServer getServerWorldByWorldID(int worldID) {
		WorldServer[] worlds = getServerWorld();
				
		for(WorldServer getw : worlds) {
			if(getw.provider.dimensionId == worldID) {
				return getw;
			}
		}
		return null;
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
		serverData.markDirty();
	}

	//get nbt data in map
	public static NBTTagCompound getPlayerData(String name) {
		return extendedPlayerData.get(name);
	}
	
	/** add/remove string in player target class list */
	public static void setPlayerTargetClassList(int pid, String str) {
		if(str != null && str.length() > 1 && pid > 0) {
			//get target class list
			List<String> tarList =  getPlayerTargetClassList(pid);
			
			if(tarList != null) {
				//check str exist in list
				for(String s : tarList) {
					//find target in list, remove target
					if(str.equals(s)) {
						tarList.remove(s);
						setPlayerTargetClassList(pid, tarList);
						return;
					}
				}
				
				//target not found, add target to list
				tarList.add(str);
				setPlayerTargetClassList(pid, tarList);
				serverData.markDirty();
			}
		}
	}
	
	/** unattackable target list, return true = add target, false = remove target or do nothing */
	//add
	public static boolean addUnattackableTargetClassList(String target) {
		boolean result = false;
		
		if(target != null) {
			//target in list, remove it
			if(unattackableTargetClassList.contains(target)) {
				unattackableTargetClassList.remove(target);
			}
			//add target to list
			else {
				unattackableTargetClassList.add(target);
				result = true;
			}
			
			serverData.markDirty();
		}
		
		return result;
	}
	
	//set
	public static void setUnattackableTargetClassList(List<String> data) {
		if(data != null) {
			unattackableTargetClassList = data;
		}
	}
	
	//get
	public static List<String> getUnattackableTargetClassList() {
		return unattackableTargetClassList;
	}
	
	/** player target class list for attack check */
	//set
	public static void setPlayerTargetClassList(int pid, List<String> list) {
		if(pid > 0) {
			customTagetClassList.put(pid, list);
			serverData.markDirty();
		}
	}
	
	//get
	public static List<String> getPlayerTargetClassList(int pid) {
		if(pid > 0) return customTagetClassList.get(pid);
		return null;
	}
	
	/** player world data for owner check...etc */
	//get player world data
	public static int[] getPlayerWorldData(int par1) {
//		LogHelper.info("DEBUG : Server Proxy: get player data");
		if(par1 != -1) return mapPlayerID.get(par1);
		return null;
	}
	
	//set player world data
	public static void setPlayerWorldData(int pid, int[] pdata) {
//		LogHelper.info("DEBUG : Server Proxy: set player data");
		if(pid > 0 && pdata != null) {
			mapPlayerID.put(pid, pdata);
			serverData.markDirty();
		}
	}
	
	/** ship world data for owner check...etc */
	//get player world data
	public static int[] getShipWorldData(int par1) {
//		LogHelper.info("DEBUG : Server Proxy: get ship data");
		if(par1 != -1) return mapShipID.get(par1);
		return null;
	}
	
	//set player world data
	public static void setShipWorldData(int pid, int[] pdata) {
//		LogHelper.info("DEBUG : Server Proxy: set ship data");
		if(pid > 0 && pdata != null) {
			mapShipID.put(pid, pdata);
			serverData.markDirty();
		}
	}
	
	/** team data */
	public static TeamData getTeamData(int tid) {
		if(tid > 0) return mapTeamID.get(tid);
		return null;
	}
	
	public static void setTeamData(TeamData data) {
		if(data != null && data.getTeamID() > 0) {
			mapTeamID.put(data.getTeamID(), data);
			serverData.markDirty();
		}
	}
	
	public static void removeTeamData(int tid) {
		if(tid > 0 && mapTeamID.containsKey(tid)) {
			mapTeamID.remove(tid);
			serverData.markDirty();
		}
	}
	
	public static int getNextPlayerID() {
		return nextPlayerID;
	}
	
	public static void setNextPlayerID(int par1) {
		if(serverData != null) {
			LogHelper.info("DEBUG : server proxy: set next player id "+par1);
			nextPlayerID = par1;
			serverData.markDirty();
		}
	}
	
	public static int getNextShipID() {
		return nextShipID;
	}
	
	public static void setNextShipID(int par1) {
		if(serverData != null) {
			LogHelper.info("DEBUG : server proxy: set next ship id "+par1);
			nextShipID = par1;
			serverData.markDirty();
		}
	}
	
	public static Map<Integer, List<String>> getAllPlayerTargetClassList() {
		return customTagetClassList;
	}
	
	public static Map<Integer, int[]> getAllPlayerWorldData() {
		return mapPlayerID;
	}
	
	public static Map<Integer, int[]> getAllShipWorldData() {
		return mapShipID;
	}
	
	public static Map<Integer, TeamData> getAllTeamWorldData() {
		return mapTeamID;
	}
	
	/** update player id */
	public static void updatePlayerID(EntityPlayer player) {
		LogHelper.info("DEBUG : update player: "+player.getDisplayName()+" "+player.getUniqueID());
		
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			int pid = extProps.getPlayerUID();
			int[] pdata = new int[3];
			
			pdata[0] = player.getEntityId();
			pdata[1] = extProps.getPlayerTeamID();
			pdata[2] = player.worldObj.provider.dimensionId;
			
			//update player data
			if(pid > 0) {
				LogHelper.info("DEBUG : update player: update player id "+pid+" eid: "+pdata[0]+" team: "+pdata[1]+" world: "+pdata[2]);
				setPlayerWorldData(pid, pdata);
				
				/** server init wrong (lost all data or file deleted)
				 *  try to generate a large next ID
				 */
				if(getNextPlayerID() <= 0 || getNextPlayerID() <= pid) {
					LogHelper.info("DEBUG : update player: find next player UID fail, shift id 100000");
					int newNextID = pid + 100000;  //shift 100000 to prevent overlap
					setNextPlayerID(newNextID);
				}
			}
			//player id < 0, load from create one
			else {
				pid = getNextPlayerID();
				//set init pid value
				if(pid <= 0) {
					pid = 100;	//player UID init value = 100
				}
				
				LogHelper.info("DEBUG : update player: create pid: "+pid+" eid: "+pdata[0]+" world: "+pdata[2]);
				extProps.setPlayerUID(pid);	//set player id
				setPlayerWorldData(pid, pdata);	//cache in server proxy
				setNextPlayerID(++pid);	//next id ++
			}
			
			serverData.markDirty();
		}
		else {
			LogHelper.info("DEBUG : update player: fail: player extProps = null");
		}
	}
	
	/** update ship id */
	public static void updateShipID(BasicEntityShip ship) {
		LogHelper.info("DEBUG : update ship: "+ship);
		
		int sid = ship.getShipUID();
		int[] sdata = new int[2];
		
		sdata[0] = ship.getEntityId();
		sdata[1] = ship.worldObj.provider.dimensionId;
		
		//update ship data
		if(sid > 0) {
			LogHelper.info("DEBUG : update ship: update ship id "+sid+" eid: "+sdata[0]+" world: "+sdata[1]);
			setShipWorldData(sid, sdata);	//cache in server proxy
			
			/** server init wrong (lost all data or file deleted)
			 *  try to generate a large next ID
			 */
			if(getNextShipID() <= 0 || getNextShipID() <= sid) {
				LogHelper.info("DEBUG : update ship: find next ship id error");
				int newNextID = sid + 100000;
				setNextShipID(newNextID);
			}
		}
		//ship id < 0, create one
		else {
			sid = getNextShipID();
			//set init value
			if(sid <= 0) {
				sid = 100;	//ship id init value = 100
			}
			
			LogHelper.info("DEBUG : update ship: create sid: "+sid+" eid: "+sdata[0]+" world: "+sdata[1]);
			ship.setShipUID(sid);
			setShipWorldData(sid, sdata);	//cache in server proxy
			setNextShipID(++sid);	//next id ++
			
			//init ship value for some reason
			ship.setStateFlag(ID.F.OnSightChase, true);  //enable onSight
		}
		
		serverData.markDirty();
	}
	
	/** update ship owner id */
	public static void updateShipOwnerID(BasicEntityShip ship) {
		Entity owner = ship.getOwner();
		
		if(owner instanceof EntityPlayer) {
			//get player UID from player's extend props
			ExtendPlayerProps extProps = (ExtendPlayerProps) owner.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				int pid = extProps.getPlayerUID();
				LogHelper.info("DEBUG : update ship: set owner id: "+pid+" on "+ship);
				ship.setPlayerUID(pid);
			}
		}
		else {
			LogHelper.info("DEBUG : update ship: get owner id: fail, owner offline or no owner data: "+ship);
		}
	}
	
	/**update server tick*/
	public static void updateServerTick() {
		//server tick +1
		serverTicks++;
		if(serverTicks > 23999) serverTicks = 0;
		
		//work after server start tick > 60
		if(serverTicks > 100) {
			if(serverTicks % 64 == 0) {
//				//server init fail, try again
//				if(!initServerFile) {
//					LogHelper.info("DEBUG : server proxy tick: init fail, try init again");
//					//backup cache, try init again and recovery cache
//					Map extPropBK = extendedPlayerData;
//					Map shipBK = mapShipID;
//					
//					initServerProxy(world0);
//					
//					extendedPlayerData = extPropBK;
//					mapShipID = shipBK;
//				}
				
				///////////////////////DEBUG
//				List<String> getlist = getPlayerTargetClassList(100);
//				if(getlist != null) {
//					for(String s : getlist) {
//						LogHelper.info("DEBUG : server proxy tick: tar list "+s);
//					}
//				}
//				serverData = (ShinWorldData) serverFile.loadData(ShinWorldData.class, ShinWorldData.SAVEID);
//				NBTTagList list = serverData.nbtData.getTagList(ShinWorldData.TAG_PLAYERDATA, Constants.NBT.TAG_COMPOUND);
//				NBTTagCompound getlist = list.getCompoundTagAt(1);
//				int uid = getlist.getInteger(ShinWorldData.TAG_TEAMDATA);
//				LogHelper.info("DEBUG : server proxy tick: ");
//				if(serverData != null && serverData.nbtData != null) {
//					NBTTagList list2 = serverData.nbtData.getTagList(ShinWorldData.TAG_TEAMDATA, Constants.NBT.TAG_COMPOUND);
//					LogHelper.info("DEBUG : server proxy tick: "+serverData.isDirty()+" "+mapTeamID.size()+" "+list2);
//				}
			}
			
			/**every update radar tick
			 * 1. create a map: key = player uid, value = ships eid
			 * 2. sync map to each player
			 */
			if(serverTicks % updateRadarTicks == 0) {
				//check player online
				boolean needUpdate = false;
				World[] allWorld = getServerWorld();
				for(World getw : allWorld) {
					if(getw.playerEntities.size() > 0) {
						needUpdate = true;
						break;
					}
				}
				
				if(needUpdate) {
					//all ship map: <Player UID, ship entity id list>
					Map<Integer, List<Integer>> allShipMapByPlayer = new HashMap<Integer, List<Integer>>();
//					LogHelper.info("dEBUG : update ship list");
					//init value
					Iterator iter = mapPlayerID.entrySet().iterator();
					while(iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						int sid = (Integer) entry.getKey();		//get player uid
						List<Integer> value = new ArrayList();	//new empty list
						//add init value
						allShipMapByPlayer.put(sid, value);
					}
				
					//build allShipMapByPlayer: get ship from mapShipID, assign to allShipMapByPlayer
					iter = mapShipID.entrySet().iterator();
					while(iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
					    int sid = (Integer) entry.getKey();		//get ship uid
					    int[] sdata = (int[]) entry.getValue();	//get ship data
					    
					    //get ship's owner's uid
					    int puid = -1;
					    BasicEntityShip ship = EntityHelper.getShipBySID(sid);	//get ship entity
					    if(ship != null) puid = ship.getPlayerUID();			//get ship's owner uid
					    
					    int[] pdata = getPlayerWorldData(puid);	//get owner's data
					    
					    //檢查是否在同world, 相同world的ship才加入list
					    if(sdata != null && pdata != null && sdata[1] == pdata[2]) {
					    	//add ship's entity id to player's shipList
					    	List shipList = allShipMapByPlayer.get(puid);
					    	if(shipList != null) {
					    		shipList.add(sdata[0]);
					    		allShipMapByPlayer.put(puid, shipList);
					    	}
					    }
					}//end build up allShipMapByPlayer
					
					//sync map to each player
					iter = allShipMapByPlayer.entrySet().iterator();
					while(iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						int sid = (Integer) entry.getKey();		//get player uid
						List<Integer> value = (List<Integer>) entry.getValue();	//get data
						EntityPlayerMP player = (EntityPlayerMP)EntityHelper.getEntityPlayerByUID(sid);
						
						if(player != null) {
							//sync data to player
							CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.SyncShipList, value), (EntityPlayerMP) player);
						}
					}
				}//end need update
			}
		}//end server start > 60 ticks
	}
	
	/** rename team */
	public static void teamRename(int tid, String tname) {
		//check team exist
		if(tid > 0 && tname != null && tname.length() > 1 &&
		   getAllTeamWorldData().containsKey(tid)) {
			TeamData tdata = getTeamData(tid);
			tdata.setTeamName(tname);
		}
	}
	
	/** create team */
	public static void teamCreate(EntityPlayer player, String tname) {
		if(player != null) {
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				int pUID = extProps.getPlayerUID();
				
				//check team data
				if(pUID > 0 && tname != null && tname.length() > 1 && getAllTeamWorldData() != null) {
					TeamData tdata = new TeamData();
					tdata.setTeamID(pUID);
					tdata.setTeamName(tname);
					tdata.setTeamLeaderName(player.getDisplayName());
					LogHelper.info("DEBUG : server proxy: create team: "+pUID+" "+tname);
					
					//save team data
					setTeamData(tdata);
					
					//update player data
					extProps.setPlayerTeamID(pUID);
					extProps.setPlayerTeamCooldown(ConfigHandler.teamCooldown);
					updatePlayerID(player);
					
//					//DEBUG generate random team
//					TeamData tdata2 = new TeamData(player.getRNG().nextInt(999),"sa"+player.getRNG().nextInt(999),"sa"+player.getRNG().nextInt(999));
//					TeamData tdata3 = new TeamData(player.getRNG().nextInt(999),"sad4"+player.getRNG().nextInt(999),"sad4"+player.getRNG().nextInt(999));
//					TeamData tdata4 = new TeamData(player.getRNG().nextInt(999),"sa7"+player.getRNG().nextInt(999),"sa7"+player.getRNG().nextInt(999));
//					TeamData tdata5 = new TeamData(player.getRNG().nextInt(999),"ff"+player.getRNG().nextInt(999),"ff"+player.getRNG().nextInt(999));
//					TeamData tdata6 = new TeamData(player.getRNG().nextInt(999),"sht"+player.getRNG().nextInt(999),"sht"+player.getRNG().nextInt(999));
//					TeamData tdata7 = new TeamData(player.getRNG().nextInt(999),"dss"+player.getRNG().nextInt(999),"dss"+player.getRNG().nextInt(999));
//					TeamData tdata8 = new TeamData(player.getRNG().nextInt(999),"ss"+player.getRNG().nextInt(999),"ss"+player.getRNG().nextInt(999));
//					
//					setTeamData(tdata2);
//					setTeamData(tdata3);
//					setTeamData(tdata4);
//					setTeamData(tdata5);
//					setTeamData(tdata6);
//					setTeamData(tdata7);
//					setTeamData(tdata8);
				}
			}
		}
	}
	
	/** disband team */
	public static void teamDisband(EntityPlayer player) {
		if(player != null) {
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				int pUID = extProps.getPlayerUID();
				
				//check team existed
				if(getAllTeamWorldData() != null && getAllTeamWorldData().containsKey(pUID)) {
					LogHelper.info("DEBUG : server proxy: remove team: "+pUID);
					
					//save team data
					removeTeamData(pUID);
					
					//update player data
					extProps.setPlayerTeamID(0);
					extProps.setPlayerTeamCooldown(ConfigHandler.teamCooldown);
					updatePlayerID(player);
				}
			}
		}
	}
	
	/** add ally team: add team2 as team1's ally, UNILATERAL relationship */
	public static void teamAddAlly(int tid1, int tid2) {
		//check team exist
		if(tid1 > 0 && tid2 > 0 && tid1 != tid2 &&
		   getAllTeamWorldData().containsKey(tid1) &&
		   getAllTeamWorldData().containsKey(tid2)) {
			LogHelper.info("DEBUG : server proxy: add ally: "+tid1+" add "+tid2);
			TeamData tdata = getTeamData(tid1);
			tdata.addTeamAlly(tid2);
		}
	}
	
	/** remove ally team: remove team2 from team1's ally, BILATERAL relationship */
	public static void teamRemoveAlly(int tid1, int tid2) {
		//check team exist
		if(tid1 > 0 && tid2 > 0 &&
		   getAllTeamWorldData().containsKey(tid1) &&
		   getAllTeamWorldData().containsKey(tid2)) {
			LogHelper.info("DEBUG : server proxy: remove ally: "+tid1+" and "+tid2);
			TeamData tdata1 = getTeamData(tid1);
			tdata1.removeTeamAlly(tid2);
			TeamData tdata2 = getTeamData(tid2);
			tdata2.removeTeamAlly(tid1);
		}
	}
	
	/** ban team: team1 ban team2, BILATERAL relationship */
	public static void teamAddBan(int tid1, int tid2) {
		//check team exist
		if(tid1 > 0 && tid2 > 0 && tid1 != tid2 &&
		   getAllTeamWorldData().containsKey(tid1) &&
		   getAllTeamWorldData().containsKey(tid2)) {
			LogHelper.info("DEBUG : server proxy: ban team: "+tid1+" ban "+tid2);
			TeamData tdata1 = getTeamData(tid1);
			tdata1.addTeamBanned(tid2);
			TeamData tdata2 = getTeamData(tid2);
			tdata2.addTeamBanned(tid1);
		}
	}
	
	/** unban team: team1 unban team2, UNILATERAL relationship */
	public static void teamRemoveBan(int tid1, int tid2) {
		//check team exist
		if(tid1 > 0 && tid2 > 0 &&
		   getAllTeamWorldData().containsKey(tid1) &&
		   getAllTeamWorldData().containsKey(tid2)) {
			LogHelper.info("DEBUG : server proxy: unban team: "+tid1+" unban "+tid2);
			TeamData tdata1 = getTeamData(tid1);
			tdata1.removeTeamBanned(tid2);
		}
	}

}
