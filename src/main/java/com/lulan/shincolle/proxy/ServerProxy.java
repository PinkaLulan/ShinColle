package com.lulan.shincolle.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.server.CacheDataPlayer;
import com.lulan.shincolle.server.CacheDataShip;
import com.lulan.shincolle.server.ShinWorldData;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

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
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ServerProxy extends CommonProxy
{

	/**player custom target class map
	 * all player's target class list, save at server side
	 * for continually working if player offline
	 * 
	 * customTagetClassList
	 * <player UUID: int, player target: map<target class name hash, target class name>>
	 * 
	 * use:
	 * 1. load from disk: server start
	 * 2. save to disk: server tick (auto), set new player data
	 * 3. get: TargetHelper for target selection
	 * 4. set: client to server packet
	 * 
	 */
	private static HashMap<Integer, HashMap<Integer, String>> customTagetClass = null;
	
	/**unattackable target class map
	 * entity in this map will not hurt by ship attack
	 * 
	 * unattackableTargetClassList <class name hashcode, class name string>
	 * 
	 */
	private static HashMap<Integer, String> unattackableTargetClass = null;
	
	/**team data
	 * for team data display, owner check, etc
	 * team ID = player UID (1 player = 1 team), team keeps 1 ally and 1 banned team list
	 * if player UID exist in team data map, the player has team (enable PVP mode)
	 * if false, the player is NOT in PVP mode
	 * 
	 * mapTeamID <team ID: int, team data: TeamData>
	 * 
	 * use:
	 * 1. load from disk: server start
	 * 2. save to disk: server tick (auto), create new team
	 * 3. get: view team
	 * 4. set: create team, server tick (clear team which no ppl in team)
	 * 
	 */
	private static HashMap<Integer, TeamData> mapTeamID = null;
	
	/****** THIS IS CACHE ONLY! NO SAVE TO DISK ******
	 * 
	 * player id to entity id cache
	 * player UID cache, map for player UID -> player EID
	 * 
	 * player UID for owner checking:
	 * -1/0 = no owner
	 * 1~N = player owner
	 * -100 = hostile mob
	 * 
	 * mapPlayerID <player UID(Integer), CacheDataPlayer>
	 * 
	 * use:
	 * 1. load from disk: none
	 * 2. save to disk: none
	 * 3. get: owner check method
	 * 4. set: player login/logout event (first time)
	 */
	private static HashMap<Integer, CacheDataPlayer> mapPlayerID = null;
	
	/**ship id to entity id cache
	 * ship data cache and backup, used for commands or data recovery
	 * 
	 * mapShipID <ship UID, ShipCacheData>
	 * 
	 * use:
	 * 1. load from disk: server start
	 * 2. save to disk: server tick (auto), create new ship, ship dead event
	 * 3. get: commands
	 * 4. set: ship entity ticking (on ship UID updating)
	 */
	private static HashMap<Integer, CacheDataShip> mapShipID = null;
	
	/**next id
	 * for player UID / ship UID / team ID
	 * -1 for init
	 * 
	 * use:
	 * 1. load from disk: server start
	 * 2. save to disk: server tick (auto), server close
	 * 3. get: player login event (new player), ship entity construction (new ship), check team
	 * 4. set: player login event (new player), ship entity construction (new ship), create team
	 */
	private static int nextPlayerID = -1;
	private static int nextShipID = -1;
	
	/**server global files */
	public static World world0;
	public static MapStorage serverFile = null;
	public static ShinWorldData serverData = null;
	public static boolean initServerFile = true;	//init flag on first world loading
	public static boolean saveServerFile = false;	//save data to disk on server stopping
	
	/**server global var */
	public static final String CUSTOM_TARGET_CLASS = "CustomTargetClass";
	public static final String UNATK_TARGET_CLASS = "UnatkTargetClass";
	public static int serverTicks = 0;
	public static int updateRadarTicks = ConfigHandler.radarUpdate;

	
	//init server proxy variables, called when world loaded and initServerFile = false
	public static void initServerProxy(World world)
	{
		LogHelper.info("INFO: init server proxy data");
		
		//init data by default value
		customTagetClass = new HashMap<Integer, HashMap<Integer, String>>();
		unattackableTargetClass = new HashMap<Integer, String>();
		mapPlayerID = new HashMap<Integer, CacheDataPlayer>();
		mapShipID = new HashMap<Integer, CacheDataShip>();
		mapTeamID = new HashMap<Integer, TeamData>();
		nextPlayerID = -1;
		nextShipID = -1;
		world0 = world;
		serverFile = world.getMapStorage();	//get global map storage, not per world
		serverData = (ShinWorldData) serverFile.getOrLoadData(ShinWorldData.class, ShinWorldData.SAVEID);
		
		//load data from MapStorage
		if (serverData != null && serverData.nbtData != null)
		{
			LogHelper.info("INFO: init server proxy: get data from .dat file");
			
			//load common variable
			nextPlayerID = serverData.nbtData.getInteger(ShinWorldData.TAG_NEXTPLAYERID);
			nextShipID = serverData.nbtData.getInteger(ShinWorldData.TAG_NEXTSHIPID);
			
			
			//load unattackable list
			NBTTagList unatktag = serverData.nbtData.getTagList(UNATK_TARGET_CLASS, Constants.NBT.TAG_STRING);
			LogHelper.info("INFO: init server proxy: get unattackable target list: count: "+unatktag.tagCount());
			HashMap<Integer, String> unatklist = new HashMap<Integer, String>();
			
			for (int j = 0; j < unatktag.tagCount(); ++j)
			{
				String str = unatktag.getStringTagAt(j);

				if (str != null && str.length() > 1)
				{
					unatklist.put(str.hashCode(), str);
				}
			}
			
			unattackableTargetClass = unatklist;
			
			
			//load player data:  from server save file to playerMap
			NBTTagList list = serverData.nbtData.getTagList(ShinWorldData.TAG_PLAYERDATA, Constants.NBT.TAG_COMPOUND);
			LogHelper.info("INFO: init server proxy: get player data count: "+list.tagCount());
			
			for (int i = 0; i < list.tagCount(); i++)
			{
				NBTTagCompound getlist = list.getCompoundTagAt(i);
				int uid = getlist.getInteger(ShinWorldData.TAG_PUID);
				
				//get target class list
				//load custom target class list
				NBTTagList strListTag = getlist.getTagList(CUSTOM_TARGET_CLASS, Constants.NBT.TAG_STRING);
				HashMap<Integer, String> strList = new HashMap<Integer, String>();
				
				for (int j = 0; j < strListTag.tagCount(); ++j)
				{
					String str = strListTag.getStringTagAt(j);

					if (str != null && str.length() > 1)
					{
						strList.put(str.hashCode(), str);
					}
				}
				
				LogHelper.debug("DEBUG: init server proxy: get player data: UID "+uid+" target list size: "+strList.size());
				customTagetClass.put(uid, strList);
			}
			
			
			//load team data:  from server save file to playerMap
			NBTTagList list2 = serverData.nbtData.getTagList(ShinWorldData.TAG_TEAMDATA, Constants.NBT.TAG_COMPOUND);
			LogHelper.info("INFO: init server proxy: get team data count: "+list2.tagCount());
			
			for (int i = 0; i < list2.tagCount(); i++)
			{
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
				
				LogHelper.debug("DEBUG: init server proxy: get team data: UID "+tUID+" NAME "+tName);
				mapTeamID.put(tUID, tData);
			}
			
			
			//load ship data: from server save file to mapShipID
			NBTTagList list3 = serverData.nbtData.getTagList(ShinWorldData.TAG_SHIPDATA, Constants.NBT.TAG_COMPOUND);
			LogHelper.info("INFO: init server proxy: get ship data count: "+list3.tagCount());
			
			for (int i = 0; i < list3.tagCount(); i++)
			{
				//load data
				NBTTagCompound getlist = list3.getCompoundTagAt(i);
				int uid = getlist.getInteger(ShinWorldData.TAG_ShipUID);
				int eid = getlist.getInteger(ShinWorldData.TAG_ShipEID);
				int wid = getlist.getInteger(ShinWorldData.TAG_ShipWID);
				int cid = getlist.getInteger(ShinWorldData.TAG_ShipCID);
				boolean isDead = getlist.getBoolean(ShinWorldData.TAG_ShipDead);
				int[] pos = getlist.getIntArray(ShinWorldData.TAG_ShipPOS);
				NBTTagCompound sTag = getlist.getCompoundTag(ShinWorldData.TAG_ShipNBT);
				CacheDataShip sData = new CacheDataShip(eid, wid, cid, isDead, (double)pos[0], (double)pos[1], (double)pos[2], sTag);
			
				LogHelper.debug("DEBUG: init server proxy: get ship data: UID "+uid);
				mapShipID.put(uid, sData);
			}
			
			initServerFile = false;
		}
		//no such file, create one
		else if (serverData == null)
		{
			LogHelper.info("INFO: init server proxy: create .dat file");
			serverData = new ShinWorldData();
			serverFile.setData(ShinWorldData.SAVEID, serverData);
			initServerFile = false;
		}
	}
	
	//save server proxy variables
	public static void saveServerProxy()
	{
		LogHelper.info("INFO: save server proxy: save .dat file");
		
		if (serverData != null) serverData.markDirty();
		if (serverFile != null) serverFile.saveAllData();
		
		saveServerFile = false;
	}
	
	public static MinecraftServer getServer()
	{
		return FMLCommonHandler.instance().getMinecraftServerInstance();
	}
	
	public static WorldServer[] getServerWorld()
	{
		return getServer().worlds;
	}
	
	public static WorldServer getServerWorld(int worldID)
	{
		WorldServer[] worlds = getServerWorld();
				
		for (WorldServer getw : worlds)
		{
			if (getw.provider.getDimension() == worldID)
			{
				return getw;
			}
		}
		
		return null;
	}
	
	@Override
	public void registerKeyBindings() {}

	@Override
	public void registerRender() {}
	
	/** add/remove string in player target class list, return true = add target */
	public static boolean setPlayerTargetClass(int pid, String str)
	{
		if (str != null && str.length() > 1 && pid > 0)
		{
			//get target class list
			HashMap<Integer, String> tarList =  getPlayerTargetClass(pid);

			if (tarList != null)
			{
				String s = tarList.get(str.hashCode());
				
				//find target in map, remove target
				if (s != null)
				{
					tarList.remove(str.hashCode());
					setPlayerTargetClass(pid, tarList); //TODO 檢查是否有必要存回map, 應該不需要這行?
					return false;
				}
				
				//target not found, add target to list
				tarList.put(str.hashCode(), str);
				setPlayerTargetClass(pid, tarList); //TODO 檢查是否有必要存回map, 應該不需要這行?
			}
			//target list null, generate one for player uid
			else
			{
				tarList = new HashMap<Integer, String>();
				tarList.put(str.hashCode(), str);
				setPlayerTargetClass(pid, tarList); //TODO 檢查是否有必要存回map, 應該不需要這行?
			}
		}
		
		return true;
	}
	
	/** unattackable target list, return true = add target, false = remove target or do nothing */
	//add
	public static boolean addUnattackableTargetClass(String target)
	{
		boolean result = false;
		
		if (target != null)
		{
			int key = target.hashCode();
			
			//target in list, remove it
			if (unattackableTargetClass.containsKey(key))
			{
				unattackableTargetClass.remove(key);
			}
			//add target to list
			else
			{
				unattackableTargetClass.put(key, target);
				result = true;
			}
			
			serverData.markDirty();
		}
		
		return result;
	}
	
	//set
	public static void setUnattackableTargetClass(HashMap<Integer, String> map)
	{
		if (map != null)
		{
			unattackableTargetClass = map;
			serverData.markDirty();
		}
	}
	
	//get
	public static HashMap<Integer, String> getUnattackableTargetClass()
	{
		return unattackableTargetClass;
	}
	
	/** player target class list for attack check */
	//set
	public static void setPlayerTargetClass(int pid, HashMap<Integer, String> map)
	{
		if (pid > 0)
		{
			customTagetClass.put(pid, map);
			serverData.markDirty();
		}
	}
	
	//get
	public static HashMap<Integer, String> getPlayerTargetClass(int pid)
	{
		if (pid > 0) return customTagetClass.get(pid);
		return null;
	}
	
	/** player world data for owner check...etc */
	public static CacheDataPlayer getPlayerWorldData(int par1)
	{
		if (par1 != -1) return mapPlayerID.get(par1);
		return null;
	}
	
	//set player world data
	public static void setPlayerWorldData(int pid, CacheDataPlayer pdata)
	{
		if (pid > 0 && pdata != null)
		{
			mapPlayerID.put(pid, pdata);
		}
	}
	
	/** ship world data for owner check...etc */
	//get player world data
	public static CacheDataShip getShipWorldData(int par1)
	{
		if (par1 != -1) return mapShipID.get(par1);
		return null;
	}
	
	//set player world data
	public static void setShipWorldData(int pid, CacheDataShip pdata)
	{
		if (pid > 0 && pdata != null)
		{
			mapShipID.put(pid, pdata);
			serverData.markDirty();
		}
	}
	
	/** team data */
	public static TeamData getTeamData(int tid)
	{
		if (tid > 0) return mapTeamID.get(tid);
		
		return null;
	}
	
	public static void setTeamData(TeamData data)
	{
		if (data != null && data.getTeamID() > 0)
		{
			mapTeamID.put(data.getTeamID(), data);
			serverData.markDirty();
		}
	}
	
	public static void removeTeamData(int tid)
	{
		if (tid > 0 && mapTeamID.containsKey(tid))
		{
			mapTeamID.remove(tid);
			serverData.markDirty();
		}
	}
	
	//remove dupe team with same owner name
	private static void cleanTeamData(TeamData tdata)
	{
		if (tdata != null)
		{
			try
			{
				String owner1 = tdata.getTeamLeaderName();
				String owner2 = null;
				
				Iterator iter = mapTeamID.entrySet().iterator();
				
				while (iter.hasNext())
				{
					Map.Entry entry = (Map.Entry) iter.next();
					int pid = (Integer) entry.getKey();		//get player uid
					TeamData getdata = (TeamData) entry.getValue();		//get player uid
					
					//check owner name
					owner2 = getdata.getTeamLeaderName();
					
					if (owner1.equals(owner2))
					{
						//remove same owner team
						removeTeamData(pid);
					}
				}
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION: clean team data fail: ");
				e.printStackTrace();
				return;
			}
		}
	}
	
	public static int getNextPlayerID()
	{
		return nextPlayerID;
	}
	
	public static void setNextPlayerID(int par1)
	{
		if (serverData != null)
		{
			LogHelper.info("INFO: server proxy: set next player id "+par1);
			nextPlayerID = par1;
			serverData.markDirty();
		}
	}
	
	public static int getNextShipID()
	{
		return nextShipID;
	}
	
	public static void setNextShipID(int par1)
	{
		if (serverData != null)
		{
			LogHelper.info("INFO: server proxy: set next ship id "+par1);
			nextShipID = par1;
			serverData.markDirty();
		}
	}
	
	public static HashMap<Integer, HashMap<Integer, String>> getAllPlayerTargetClassList()
	{
		return customTagetClass;
	}
	
	public static HashMap<Integer, CacheDataPlayer> getAllPlayerWorldData()
	{
		return mapPlayerID;
	}
	
	public static HashMap<Integer, CacheDataShip> getAllShipWorldData()
	{
		return mapShipID;
	}
	
	public static HashMap<Integer, TeamData> getAllTeamWorldData()
	{
		return mapTeamID;
	}
	
	/** update player id */
	public static void updatePlayerID(EntityPlayer player)
	{
		LogHelper.info("INFO: update player: "+player.getDisplayNameString()+" "+player.getUniqueID()+" "+player.getEntityId());
		
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			int pid = capa.getPlayerUID();
			
			//update player data
			if (pid > 0)
			{
				LogHelper.info("INFO: update player: update player uid: "+pid);
				setPlayerWorldData(pid, new CacheDataPlayer(player.getEntityId(), player.dimension,
									capa.hasTeam(), player.posX, player.posY, player.posZ,
									capa.saveNBTData(new NBTTagCompound())));
				
				/** server init wrong (lost all data or file deleted)
				 *  try to generate a large next ID
				 */
				if (getNextPlayerID() <= 0 || getNextPlayerID() <= pid)
				{
					LogHelper.info("INFO: update player: find next player UID fail, shift id 100000");
					int newNextID = pid + 100000;  //shift 100000 to prevent overlap
					setNextPlayerID(newNextID);
				}
			}
			//player id < 0
			else
			{
				pid = getNextPlayerID();
				
				//set init pid value
				if (pid <= 0)
				{
					pid = 100;	//player UID init value = 100
				}
				
				LogHelper.info("INFO: update player: create player uid: "+pid);
				capa.setPlayerUID(pid);	//set player id
				setPlayerWorldData(pid, new CacheDataPlayer(player.getEntityId(), player.dimension,
									capa.hasTeam(), player.posX, player.posY, player.posZ,
									capa.saveNBTData(new NBTTagCompound())));
				setNextPlayerID(++pid);	//next id ++
			}
		}
		else
		{
			LogHelper.info("INFO: update player: fail: player extProps = null");
		}
	}
	
	/** update ship id */
	public static void updateShipID(BasicEntityShip ship)
	{
		LogHelper.info("INFO: update ship: "+ship);
		
		int uid = ship.getShipUID();
		
		//update ship data
		if (uid > 0)
		{
			LogHelper.debug("DEBUG : update ship: update ship id "+uid+" eid: "+ship.getEntityId()+" world: "+ship.world.provider.getDimension());
			CacheDataShip sdata = new CacheDataShip(ship.getEntityId(), ship.world.provider.getDimension(),
									ship.getShipClass(), ship.isDead, ship.posX, ship.posY, ship.posZ,
									ship.writeToNBT(new NBTTagCompound()));

			setShipWorldData(uid, sdata);	//cache in server proxy
			
			/** server init wrong (lost all data or file deleted)
			 *  try to generate a large next ID
			 */
			if (getNextShipID() <= 0 || getNextShipID() <= uid)
			{
				LogHelper.info("INFO: update ship: find next ship id error");
				int newNextID = uid + 100000;
				setNextShipID(newNextID);
			}
		}
		//ship id < 0, create one
		else
		{
			uid = getNextShipID();
			
			//set init value
			if (uid <= 0) uid = 100;	//ship id init value = 100
			
			LogHelper.debug("DEBUG : update ship: create sid: "+uid+" eid: "+ship.getEntityId()+" world: "+ship.world.provider.getDimension());
			ship.setShipUID(uid);
			CacheDataShip sdata = new CacheDataShip(ship.getEntityId(), ship.world.provider.getDimension(),
									ship.getShipClass(), ship.isDead, ship.posX, ship.posY, ship.posZ,
									ship.writeToNBT(new NBTTagCompound()));
	
			setShipWorldData(uid, sdata);	//cache in server proxy
			setNextShipID(++uid);	//next id ++
		}
	}
	
	/** update ship owner id */
	public static void updateShipOwnerID(BasicEntityShip ship)
	{
		Entity owner = ship.getOwner();
		
		if (owner instanceof EntityPlayer)
		{
			//get player UID from player's extend props
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) owner);
			
			if (capa != null)
			{
				int pid = capa.getPlayerUID();
				LogHelper.info("INFO: update ship: set owner id: "+pid+" on "+ship);
				ship.setPlayerUID(pid);
				ship.ownerName = ((EntityPlayer) owner).getName();
			}
		}
		else
		{
			LogHelper.info("INFO: update ship: get owner id: fail, owner offline or no owner data: "+ship);
		}
	}
	
	/**update server tick*/
	public static void updateServerTick()
	{
		//server tick +1
		serverTicks++;
		if (serverTicks > 23999) serverTicks = 0;
		
		//work after server start tick > 60
		if (serverTicks > 100)
		{
			//DEBUG TODO
//			if (serverTicks % 32 == 0)
//			{
//				LogHelper.debug("AAAAAAAAAA "+mapPlayerID.size());
//			}
			
			/**every update radar tick
			 * 1. create a map: key = player uid, value = ships eid list
			 * 2. sync map to each player
			 */
			if (serverTicks % updateRadarTicks == 0)
			{
				//if player online = can update
				boolean needUpdate = false;
				World[] allWorld = getServerWorld();
				
				for (World getw : allWorld)
				{
					if (getw.playerEntities.size() > 0)
					{
						needUpdate = true;
						break;
					}
				}
				
				if (needUpdate)
				{
					//all ship map: <Player UID, ship entity id list>
					HashMap<Integer, List<Integer>> shipListByPlayer = new HashMap<Integer, List<Integer>>();
					
					//all player entity map (for cache)
					HashMap<Integer, EntityPlayer> playerByPUID = new HashMap<Integer, EntityPlayer>();
					
					//init value: add empty list for each player
					mapPlayerID.forEach((pid, pdata) ->
					{
						List<Integer> newlist = new ArrayList();	//empty list
						
						//get player entity
						EntityPlayer pe = EntityHelper.getEntityPlayerByUID(pid);
						
						//add init value
						shipListByPlayer.put(pid, newlist);
						
						//add player entity cache, only for ONLINE player
						if (pe != null)
						{
							playerByPUID.put(pid, pe);
						}
					});
				
					//add ship to each player: get ship from mapShipID, save to shipListByPlayer
					mapShipID.forEach((sid, sdata) ->
					{
					    //get ship entity
					    Entity ent = EntityHelper.getEntityByID(sdata.entityID, sdata.worldID, false);
					    
						if (ent instanceof BasicEntityShip)
						{
							BasicEntityShip ship = (BasicEntityShip) ent;
							int pid = ship.getPlayerUID();
							EntityPlayer player = playerByPUID.get(pid);
							
							//check same dimension
							if (player != null && player.world.provider.getDimension() == ship.world.provider.getDimension())
							{
						    	//add ship's entity id to player's shipList
						    	List shipList = shipListByPlayer.get(pid);
						    	
						    	if (shipList != null)
						    	{
						    		shipList.add(sdata.entityID);
						    	}
							}
						}
					});
					
					//sync map to each ONLINE player
					shipListByPlayer.forEach((pid, value) ->
					{
						EntityPlayerMP player = (EntityPlayerMP) playerByPUID.get(pid);
						
						if (player != null)
						{
							//sync data to player
							CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.SyncShipList, value), (EntityPlayerMP) player);
						}
					});

				}//end need update
			}//end every updateTick
		}//end server start > 60 ticks
	}
	
	/** rename team */
	public static void teamRename(int tid, String tname)
	{
		//check team exist
		if (tid > 0 && tname != null && tname.length() > 1 &&
			getAllTeamWorldData().containsKey(tid))
		{
			TeamData tdata = getTeamData(tid);
			tdata.setTeamName(tname);
		}
	}
	
	/** create team */
	public static void teamCreate(EntityPlayer player, String tname)
	{
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			int pUID = capa.getPlayerUID();
			
			//check team data
			if (pUID > 0 && tname != null && tname.length() > 1 && getAllTeamWorldData() != null)
			{
				TeamData tdata = new TeamData();
				tdata.setTeamID(pUID);
				tdata.setTeamName(tname);
				tdata.setTeamLeaderName(player.getDisplayNameString());
				LogHelper.info("INFO: server proxy: create team: "+pUID+" "+tname);
				
				//remove the same owner team (for some bug, ex: UID changed)
				cleanTeamData(tdata);
				
				//save team data
				setTeamData(tdata);
				
				//update player data
				capa.setPlayerTeamCooldown(ConfigHandler.teamCooldown);
				updatePlayerID(player);
			}
		}
	}
	
	/** disband team */
	public static void teamDisband(EntityPlayer player)
	{
		if (player != null)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				int pUID = capa.getPlayerUID();
				
				//check team existed
				if (getAllTeamWorldData() != null && getAllTeamWorldData().containsKey(pUID))
				{
					LogHelper.info("INFO: server proxy: remove team: "+pUID);
					
					//save team data
					removeTeamData(pUID);
					
					//update player data
					capa.setPlayerTeamCooldown(ConfigHandler.teamCooldown);
					updatePlayerID(player);
				}
			}
		}
	}
	
	/** add ally team: add team2 as team1's ally, UNILATERAL relationship */
	public static void teamAddAlly(int tid1, int tid2)
	{
		//check team exist
		if (tid1 > 0 && tid2 > 0 && tid1 != tid2 &&
			getAllTeamWorldData().containsKey(tid1) &&
			getAllTeamWorldData().containsKey(tid2))
		{
			LogHelper.info("INFO: server proxy: add ally: "+tid1+" add "+tid2);
			TeamData tdata = getTeamData(tid1);
			tdata.addTeamAlly(tid2);
		}
	}
	
	/** remove ally team: remove team2 from team1's ally, BILATERAL relationship */
	public static void teamRemoveAlly(int tid1, int tid2)
	{
		//check team exist
		if (tid1 > 0 && tid2 > 0 &&
			getAllTeamWorldData().containsKey(tid1) &&
			getAllTeamWorldData().containsKey(tid2))
		{
			LogHelper.info("INFO: server proxy: remove ally: "+tid1+" and "+tid2);
			TeamData tdata1 = getTeamData(tid1);
			tdata1.removeTeamAlly(tid2);
			TeamData tdata2 = getTeamData(tid2);
			tdata2.removeTeamAlly(tid1);
		}
	}
	
	/** ban team: team1 ban team2, BILATERAL relationship */
	public static void teamAddBan(int tid1, int tid2)
	{
		//check team exist
		if (tid1 > 0 && tid2 > 0 && tid1 != tid2 &&
			getAllTeamWorldData().containsKey(tid1) &&
			getAllTeamWorldData().containsKey(tid2))
		{
			LogHelper.info("INFO: server proxy: ban team: "+tid1+" ban "+tid2);
			TeamData tdata1 = getTeamData(tid1);
			tdata1.addTeamBanned(tid2);
			TeamData tdata2 = getTeamData(tid2);
			tdata2.addTeamBanned(tid1);
		}
	}
	
	/** unban team: team1 unban team2, UNILATERAL relationship */
	public static void teamRemoveBan(int tid1, int tid2)
	{
		//check team exist
		if (tid1 > 0 && tid2 > 0 &&
			getAllTeamWorldData().containsKey(tid1) &&
			getAllTeamWorldData().containsKey(tid2))
		{
			LogHelper.info("INFO: server proxy: unban team: "+tid1+" unban "+tid2);
			TeamData tdata1 = getTeamData(tid1);
			tdata1.removeTeamBanned(tid2);
		}
	}

	
}
