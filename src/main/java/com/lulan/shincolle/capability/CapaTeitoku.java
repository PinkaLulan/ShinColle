package com.lulan.shincolle.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

/**
 * teitoku data capability
 * tut: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
 *
 * capability data本體
 */
public class CapaTeitoku implements ICapaTeitoku
{
	public static final String CAPA_KEY = "TeitokuExtProps";
	public EntityPlayer player;
	public String playerName;
	
	//temp var
	private boolean isOpeningGUI;		//in using GUI
	
	//player data
	private boolean hasRing;
	private boolean isRingActive;
	private boolean isRingFlying;
	private int[] ringEffect;			//0:haste 1:speed 2:jump 3:damage
	private int marriageNum;
	private int bossCooldown;			//spawn boss cooldown
	private int teamCooldown;			//recreate team cooldown
	
	/**ship team list
	 * 9 teams, 1 team = 6 ships
	 * save ship entity id
	 */
	private BasicEntityShip[][] teamList;	//total 9 teams, 1 team = 6 ships
	private boolean[][] selectState;		//ship selected, for command control target
	private boolean initSID = false;		//ship UID init flag: false = not init
	private int[][] sidList;				//ship UID
	private int[] formatID;					//ship formation ID
	private int saveId;						//current ship/empty slot, value = 0~5
	private int teamId;						//current team
	private ArrayList<Integer> listShipEID;		//all loaded ships' entity id list for radar
	private ArrayList<Integer> listColleShip;		//all obtained ships list
	private ArrayList<Integer> listColleEquip;		//all obtained equipment list
	
	//player id
	private int playerUID;
	
	//player team: for client side GUI only, do not use these at server side
	private HashMap<Integer, TeamData> mapTeamData;
	private ArrayList<TeamData> listTeamData;
	
	//target selector
	private HashMap<Integer, String> targetClassMap;	//temp for client side, used in GUI


	@Override
	public void init(EntityPlayer entity, World world)
	{
		this.player = entity;
		this.playerName = entity.getDisplayNameString();
		this.hasRing = false;
		this.isRingActive = false;
		this.isRingFlying = false;
		this.ringEffect = new int[] {0, 0, 0, 0};
		this.marriageNum = 0;
		this.bossCooldown = ConfigHandler.bossCooldown;
		this.teamList = new BasicEntityShip[9][6];
		this.selectState = new boolean[9][6];
		this.sidList = new int[9][6];
		this.formatID = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		this.listShipEID = new ArrayList();
		this.listColleShip = new ArrayList();
		this.listColleEquip = new ArrayList();
		this.targetClassMap = new HashMap();
		this.initSID = false;
		this.saveId = 0;
		this.teamId = 0;
		this.playerUID = -1;
		this.isOpeningGUI = false;
		
		//team
		this.teamCooldown = 20;
		this.mapTeamData = new HashMap();
		this.listTeamData = new ArrayList();
		
	}
	
	@Override
	public NBTTagCompound saveNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound nbtExt = new NBTTagCompound();
		
		nbtExt.setBoolean("hasRing", hasRing);
		nbtExt.setBoolean("RingOn", isRingActive);
		nbtExt.setBoolean("RingFly", isRingFlying);
		nbtExt.setIntArray("RingEffect", ringEffect);
		nbtExt.setIntArray("FormatID", formatID);
		nbtExt.setInteger("MarriageNum", marriageNum);
		nbtExt.setInteger("BossCD", bossCooldown);
		nbtExt.setInteger("PlayerUID", playerUID);
		nbtExt.setInteger("TeamCD", teamCooldown);
		
		//save player name
		try
		{
			nbtExt.setString("PlayerName", playerName);
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: player name is empty string.");
		}
		
		/**save team list by ship UID
		 * entity id will change after entity reconstruction
		 * a way to keep team list is saving ship UID
		 */
		if (this.initSID)
		{	//update id AFTER sid is inited
			LogHelper.info("DEBUG : save player ExtNBT: update ship UID from teamList");
			this.updateSID();
		}
		
		for (int i = 0; i < 9; i++)
		{
			LogHelper.info("DEBUG : save player ExtNBT: "+this.getSID(i)[0]);
			nbtExt.setIntArray("TeamList"+i, this.getSID(i));
			nbtExt.setByteArray("SelectState"+i, this.getSelectStateByte(i));
		}
		
		//save colle list
		int[] arrtemp = CalcHelper.intListToArray(this.listColleShip);
		nbtExt.setIntArray("ColleShip", arrtemp);
		arrtemp = CalcHelper.intListToArray(this.listColleEquip);
		nbtExt.setIntArray("ColleEquip", arrtemp);
		
		//save custom target class list
		if (this.targetClassMap != null)
		{
			NBTTagList list = new NBTTagList();
			nbtExt.setTag("CustomTargetClass", list);
			
			targetClassMap.forEach((k,v) ->
			{
				NBTTagString str = new NBTTagString(v);
				list.appendTag(str);
			});
		}
		
		nbt.setTag(CAPA_KEY, nbtExt);
		LogHelper.infoDebugMode("DEBUG : save player ExtNBT data on id: "+player.getEntityId());
		
		//backup data in ServerProxy
		LogHelper.infoDebugMode("DEBUG : player saveNBTData: backup player extProps in ServerProxy");
		ServerProxy.setPlayerData(player.getUniqueID().toString(), nbtExt);
		
		return nbt;
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound nbtExt = (NBTTagCompound) nbt.getTag(CAPA_KEY);
		
		/** FIX: check empty player data for iChun:SyncMod
		 *       sometimes the input nbt data has content but no tag name
		 *       nbtExt will be null because find no data with the tag name
		 */
		if (nbtExt == null)
		{
			//check data without data tag
			if (!nbt.hasKey("PlayerUID"))
			{
				LogHelper.info("DEBUG : player loadNBTData: fail, data is null "+nbt+" "+nbtExt);
				return;
			}
			else
			{
				LogHelper.info("DEBUG : player loadNBTData: get data without tag name");
				nbtExt = nbt;
			}
		}
		
		//load data
		LogHelper.info("DEBUG : player loadNBTData: get data "+nbt+" "+nbtExt);
		try
		{
			hasRing = nbtExt.getBoolean("hasRing");
			isRingActive = nbtExt.getBoolean("RingOn");
			isRingFlying = nbtExt.getBoolean("RingFly");
			ringEffect = nbtExt.getIntArray("RingEffect");
			formatID = nbtExt.getIntArray("FormatID");
			marriageNum = nbtExt.getInteger("MarriageNum");
			bossCooldown = nbtExt.getInteger("BossCD");
			playerUID = nbtExt.getInteger("PlayerUID");
			teamCooldown = nbtExt.getInteger("TeamCD");
//			playerName = nbtExt.getString("PlayerName");  //player name is SAVE ONLY
			
			//load colle list
			int[] arrtemp = nbtExt.getIntArray("ColleShip");
			this.listColleShip = CalcHelper.intArrayToList(arrtemp);
			arrtemp = nbtExt.getIntArray("ColleEquip");
			this.listColleEquip = CalcHelper.intArrayToList(arrtemp);
			
			/**load team list by ship UID
			 * get entity by ship UID, SERVER SIDE ONLY
			 * 
			 * player entity can be loaded between ship entities,
			 * the teamList have to sync after all entity loaded (not here)
			 */
			if (!this.player.worldObj.isRemote)
			{
				for (int i = 0; i < 9; ++i)
				{
					byte[] byteSelect = nbtExt.getByteArray("SelectState"+i);
					int[] sid = nbtExt.getIntArray("TeamList"+i);
							
					if (sid != null && sid.length > 5)
					{  //null check for new player
						for (int j = 0; j < 6; ++j)
						{
							//set select state
							this.selectState[i][j] = byteSelect[j] == 1 ? true : false;
							//set ship UID
							this.sidList[i][j] = sid[j];
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			LogHelper.info("DEBUG : player loadNBTData: load fail: "+e);
		}
		
		LogHelper.info("DEBUG : load player ExtNBT data on id: "+player.getEntityId()+" client? "+this.player.worldObj.isRemote);
	}
	
	//getter
	public boolean isRingActive()
	{
		return isRingActive;
	}
	
	public int isRingActiveI()
	{
		return isRingActive ? 1 : 0;
	}
	
	public boolean isRingFlying()
	{
		return isRingFlying;
	}
	
	public boolean hasRing()
	{
		return hasRing;
	}
	
	//check has team from server team cache, SERVER ONLY
	public boolean hasTeam()
	{
		if (player != null && !player.worldObj.isRemote)
		{
			if (ServerProxy.getTeamData(this.playerUID) != null) return true;
		}
		
		return false;
	}
	
	public int getRingEffect(int id)
	{
		return ringEffect[id];
	}
	
	public int getMarriageNum()
	{
		return marriageNum;
	}
	
	public int getDigSpeedBoost()
	{
		return isRingActive ? marriageNum : 0;
	}
	
	public int getBossCooldown()
	{
		return this.bossCooldown;
	}
	
	public int[] getEntityIDofTeam(int tid)
	{
		int[] eid = new int[6];
		
		for (int i = 0; i < 6; i++)
		{
			if (teamList[tid][i] != null)
			{
				eid[i] = teamList[tid][i].getEntityId();
			}
			else
			{
				eid[i] = -1;
			}
		}
		
		return eid;
	}
	
	public BasicEntityShip getShipEntityCurrentTeam(int id)
	{
		return getShipEntity(this.teamId, id);
	}
	
	public BasicEntityShip getShipEntity(int tid, int id)
	{
		try
		{
			return teamList[tid][id];
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get ship entity from extProps fail: "+e);
			return null;
		}
	}
	
	public BasicEntityShip[] getShipEntityAll(int tid)
	{
		try
		{
			return teamList[tid];
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get all ship entity from extProps fail: "+e);
			return null;
		}
	}
	
	public BasicEntityShip[][] getShipEntityAllTeams()
	{
		try
		{
			return teamList;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get all ship entity from extProps fail: "+e);
			return null;
		}
	}
	
	/** get ships for pointer by pointer's mode: 0:single, 1:group, 2:formation */
	public BasicEntityShip[] getShipEntityByMode(int mode)
	{	//meta為pointer的item damage
		BasicEntityShip[] ships = new BasicEntityShip[6];
		boolean shouldSync = false;
		
		//check same owner here again, some ship may have changed owner
		for (int k = 0; k < 6; k++)
		{
			if (this.teamList[teamId][k] != null &&
				!TeamHelper.checkSameOwner(this.teamList[teamId][k], player))
			{
				addShipEntityToCurrentTeam(k, null);  //clear ship
				shouldSync = true;
			}
		}
		
		if (shouldSync)
		{
			sendSyncPacket(0);
		}
		
		switch (mode)
		{
		case 1:		//group mode
			//return所有已選擇的ship
			int j = 0;
			for (int i = 0; i < 6; i++)
			{
				if (this.getSelectStateCurrentTeam(i))
				{
					ships[j] = this.teamList[teamId][i];
					j++;
				}
			}
			break;
		case 2:		//formation mode
			//return整個team
			return this.teamList[teamId];
		default:	//single mode
			//return第一個找到的已選擇的ship
			for (int i = 0; i < 6; i++)
			{
				if (this.getSelectStateCurrentTeam(i))
				{
					ships[0] = this.teamList[teamId][i];
					return ships;
				}
			}
			break;
		}
		
		return ships;
	}
	
	//get selected state (byte array)
	public byte[] getSelectStateByte(int tid)
	{
		byte[] byteState = new byte[6];
		
		if (tid > 5) tid = 0;
		
		if (selectState[tid] != null)
		{
			for (int i = 0; i < 6; ++i)
			{
				byteState[i] = selectState[tid][i] ? (byte)1 : (byte)0;
			}
		}
		
		return byteState;
	}
	
	public boolean[][] getSelectStateAllTeams()
	{
		return selectState;
	}
	
	//get selected state
	public boolean[] getSelectState(int tid)
	{
		try
		{
			return selectState[tid];
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get ship select state fail: "+e);
			return new boolean[6];
		}
	}
	
	//get selected state
	public boolean getSelectState(int tid, int pos)
	{
		try
		{
			return selectState[tid][pos];
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get ship select state fail: "+e);
			return false;
		}
	}
	
	//get selected state
	public boolean getSelectStateCurrentTeam(int id)
	{
		if (id > 5) id = 0;
		return selectState[teamId][id];
	}
	
	public int getSID(int tid, int pos)
	{
		try
		{
			return this.sidList[tid][pos];
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get ship ID fail: "+e);
			return -1;
		}
	}
	
	//get all ship UID in a team
	public int[] getSID(int tid)
	{
		if (sidList != null && sidList[tid] != null)
		{
			return sidList[tid];
		}
		
		return null;
	}
	
	public int[][] getSID()
	{
		return this.sidList;
	}
	
	//get current team ship UID
	public int getSIDCurrentTeam(int id)
	{
		if (id > 5) id = 0;
		return sidList[teamId][id];
	}
	
	public int getPointerTeamID()
	{
		return this.teamId;
	}
	
	public int getPlayerUID()
	{
		return this.playerUID;
	}
	
	public String getPlayerTeamName()
	{
		if (this.playerUID > 0)
		{
			TeamData tdata = this.mapTeamData.get(this.playerUID);
			
			if (tdata != null) return tdata.getTeamName();
		}
		return null;
	}
	
	public List<Integer> getPlayerTeamBannedList()
	{
		if (this.playerUID > 0)
		{
			TeamData tdata = this.mapTeamData.get(this.playerUID);
			
			if (tdata != null) return tdata.getTeamBannedList();
		}
		return null;
	}
	
	public List<Integer> getPlayerTeamAllyList()
	{
		if (this.playerUID > 0)
		{
			TeamData tdata = this.mapTeamData.get(this.playerUID);
			
			if (tdata != null) return tdata.getTeamAllyList();
		}
		return null;
	}
	
	//for client GUI display
	public Map<Integer, TeamData> getPlayerTeamDataMap()
	{
		return this.mapTeamData;
	}
	
	//for client GUI display
	public List<TeamData> getPlayerTeamDataList()
	{
		return this.listTeamData;
	}
	
	//for client GUI display
	public TeamData getPlayerTeamData(int par1)
	{
		return this.mapTeamData.get(par1);
	}
	
	public int getPlayerTeamCooldown()
	{
		return this.teamCooldown;
	}
	
	public int getPlayerTeamCooldownInSec()
	{
		return (int)((float)this.teamCooldown * 0.05F);
	}
	
	public boolean getInitSID()
	{
		return this.initSID;
	}
	
	public ArrayList<Integer> getShipEIDList()
	{
		return this.listShipEID;
	}
	
	public ArrayList<Integer> getColleShipList()
	{
		return this.listColleShip;
	}
	
	public ArrayList<Integer> getColleEquipList()
	{
		return this.listColleEquip;
	}
	
	public HashMap<Integer, String> getTargetClassList()
	{
		return this.targetClassMap;
	}
	
	public boolean isOpeningGUI()
	{
		return this.isOpeningGUI;
	}
	
	public int[] getFormatID()
	{
		//null check
		if (this.formatID == null || this.formatID.length != 9)
		{
			this.formatID = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		}
		
		return this.formatID;
	}
	
	public int getFormatID(int teamID)
	{
		try
		{
			return this.formatID[teamID];
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get formation id fail: "+e);
			return 0;
		}
	}
	
	public int getFormatIDCurrentTeam()
	{
		return getFormatID(this.teamId);
	}
	
	//get number of ship in the team
	public int getNumberOfShip(int teamID)
	{
		int num = 0;
		
		if (this.teamList != null)
		{
			for (int i = 0; i < 6; ++i)
			{
				if (this.teamList[teamID][i] != null &&
					this.teamList[teamID][i].isEntityAlive()) num++;
			}
		}
		
		return num;
	}
	
	/** get formation position in team, mainly for diamond formation
	 *  return 0~5 or -1: ship not found
	 */
	public int getFormationPos(int teamID, int sid)
	{
		int pos = 0;
		
		for (int i = 0; i < 6; i++)
		{
			if (teamList[teamID][i] != null)
			{
				if (sidList[teamID][i] == sid)
				{
					return pos;
				}
				
				pos++;
			}
		}
		
		return -1;  //ship not found!!
	}
	
	/** get min move speed WITHOUT BUFF in team */
	public float getMinMOVInTeam(int teamID)
	{
		float mov = 10F;
		float temp = 0F;
		
		try
		{
			for (BasicEntityShip ship : this.teamList[teamID])
			{
				if (ship != null)
				{
					temp = ship.getStateFinalBU(ID.MOV);
					if (temp < mov) mov = temp;
				}
			}
			
			if (mov >= 10F)
			{
				LogHelper.info("DEBUG : get min move speed: no ship in team");
				mov = 0F;  //get no ship in team, bug?
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : get entity MOV from extProps fail: "+e);
			return 0F;
		}
		
		return mov;
	}
	
	//setter
	public void setRingActive(boolean par1)
	{
		isRingActive = par1;
	}
	
	public void setRingActiveI(int par1)
	{
		if (par1 == 0)
		{
			isRingActive = false;
		}
		else
		{
			isRingActive = true;
		}
	}
	
	public void setRingFlying(boolean par1)
	{
		isRingFlying = par1;
	}
	
	public void setHasRing(boolean par1)
	{
		hasRing = par1;
	}
	
	public void setRingEffect(int id, int par1)
	{
		ringEffect[id] = par1;
	}
	
	public void setMarriageNum(int par1)
	{
		marriageNum = par1;
	}
	
	public void setBossCooldown(int par1)
	{
		bossCooldown = par1;
	}
	
	public void setSID(int tid, int pos, int sid)
	{
		try
		{
			sidList[tid][pos] = sid;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set ship ID fail: "+e);
		}
	}
	
	//set ship UID (for CLIENT SIDE sync only)
	public void setSIDCurrentTeam(int pos, int sid)
	{
		try
		{
			sidList[teamId][pos] = sid;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set current team ship ID fail: "+e);
		}
	}
	
	//get selected state
	public void setSelectState(int tid, int pos, boolean par1)
	{
		try
		{
			selectState[tid][pos] = par1;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set ship select state fail: "+e);
		}
	}
	
	public void setSelectStateCurrentTeam(int id, boolean par1)
	{
		try
		{
			selectState[teamId][id] = par1;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set current team select state fail: "+e);
		}
	}
	
	public void setPointerTeamID(int par1)
	{
		if(par1 > 9) par1 = 0;
		this.teamId = par1;
	}
	
	public void setPlayerUID(int par1)
	{
		this.playerUID = par1;
	}
	
	public void setPlayerTeamDataMap(HashMap<Integer, TeamData> par1)
	{
		//set team data
		if (par1 != null)
		{
			this.mapTeamData = par1;
			
			//copy team data to list form
			this.listTeamData = new ArrayList();
			
			this.mapTeamData.forEach((k, v) ->
			{
				this.listTeamData.add(v);
			});
		}
		//clear team data
		else
		{
			this.mapTeamData = new HashMap();
			this.listTeamData = new ArrayList();
		}
	}
	
	public void setPlayerTeamCooldown(int par1)
	{
		this.teamCooldown = par1;
	}
	
	public void setInitSID(boolean par1)
	{
		this.initSID = par1;
	}
	
	public void setShipEIDList(ArrayList<Integer> list)
	{
		this.listShipEID = list;	//shallow copy, ref only
	}
	
	public void setColleShipList(ArrayList<Integer> list)
	{
		this.listColleShip = list;
	}
	
	public void setColleEquipList(ArrayList<Integer> list)
	{
		this.listColleEquip = list;
	}
	
	public void setColleShip(int shipID)
	{
		if (!this.listColleShip.contains(shipID))
		{
			this.listColleShip.add(shipID);
		}
	}
	
	public void setColleEquip(int equipID) {
		if (!this.listColleEquip.contains(equipID))
		{
			this.listColleEquip.add(equipID);
		}
	}
	
	//set target class by string list
	public void setTargetClass(HashMap<Integer, String> map)
	{
		if (map != null)
		{
			this.targetClassMap = map;
		}
	}
	
	//add target class by class name or remove it if existed in map
	public void setTargetClass(String str)
	{
		if (str != null && str.length() > 1)
		{
			int key = str.hashCode();
			String s = this.targetClassMap.get(key);
			
			//target found, remove target
			if (s != null && str.equals(s))
			{
				this.targetClassMap.remove(key);
				return;
			}
			
			//target not found, add target
			this.targetClassMap.put(key, str);
		}
	}
	
	public void setOpeningGUI(boolean par1)
	{
		this.isOpeningGUI = par1;
	}
	
	public void setFormatID(int[] par1)
	{
		this.formatID = par1;
	}
	
	public void setFormatID(int tid, int fid)
	{
		try
		{
			this.formatID[tid] = fid;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set ship team formation id fail: "+e);
		}
	}
	
	public void setFormatIDCurrentTeam(int fid)
	{
		try
		{
			this.formatID[teamId] = fid;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set current team formation id fail: "+e);
		}
	}
	
	public void setTeamList(BasicEntityShip[][] par1)
	{
		this.teamList = par1;
	}
	
	public void setSelectState(boolean[][] par1)
	{
		this.selectState = par1;
	}
	
	public void setSIDList(int[][] par1)
	{
		this.sidList = par1;
	}
	
	public void setTeamList(int tid, BasicEntityShip[] par1)
	{
		try
		{
			this.teamList[tid] = par1;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set ship team entity fail: "+e);
		}
	}
	
	public void setSelectState(int tid, boolean[] par1)
	{
		try
		{
			this.selectState[tid] = par1;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set ship team select state fail: "+e);
		}
	}
	
	public void setSIDList(int tid, int[] par1)
	{
		try
		{
			this.sidList[tid] = par1;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : set ship team SID list fail: "+e);
		}
	}
	
	/** add ship entity to slot and update SID list */
	public void addShipEntityToCurrentTeam(int slot, BasicEntityShip entity)
	{
		if (slot > 5) return;
		
		if (entity == null)
		{	//clear slot
			this.teamList[teamId][slot] = null;
			this.sidList[teamId][slot] = -1;
			this.selectState[teamId][slot] = false;
		}
		else
		{	//do not change select state here
			this.teamList[teamId][slot] = entity;
			this.sidList[teamId][slot] = entity.getShipUID();
		}
	}
	
	/**將ship加入隊伍名單
	 * 若entity != null, 表示要加入名單 -> 找目前非null欄位比對是否同id -> 同id表示remove該entity
	 *                                                        -> 不同id表示可新增entity
	 * 若entity = null, 表示清空該slot
	 * 
	 * 若useTeamID = true, 表示不考慮是否重複之類的, 強制插入到slot的位置
	 */
	public void addShipEntity(int slot, BasicEntityShip entity, boolean forceAdd)
	{
		boolean canAdd = false;
		
		//client 收到sync packets
		if (forceAdd)
		{
			if(slot > 5) slot = 0;
			addShipEntityToCurrentTeam(slot, entity);
			return;
		}
		else
		{
			//entity不為null才找欄位存
			if(entity != null)
			{
//				//debug: show team
//				for (int k = 0; k < 6; k++)
//				{
//					LogHelper.info("DEBUG : team list (before add) "+this.saveId+" "+k+" "+this.teamList[k]);
//				}
				
				//若entity非自己所屬, 則不能add team
				if (player != null && !TeamHelper.checkSameOwner(player, entity))
				{
					return;
				}
				
				//找有無重複ship, 有的話則清除該ship, id指到該slot
				int inTeam = this.checkIsInCurrentTeam(entity.getShipUID());
				if (inTeam >= 0)
				{
					addShipEntityToCurrentTeam(inTeam, null);
					this.saveId = inTeam;
					this.setSelectStateCurrentTeam(inTeam, false);
					
					return;
				}
				
				//若無重複entity, 則挑null空位存, id指示為下一個slot
				for (int i = 0; i < 6; i++)
				{
					if (this.teamList[teamId][i] == null)
					{
						this.setSelectStateCurrentTeam(i, false);
						addShipEntityToCurrentTeam(i, entity);
						
						this.saveId = i + 1;
						if (saveId > 5) saveId = 0;
						
						return;
					}
				}
				
				//都沒空位, 則挑id指的位置存
				this.setSelectStateCurrentTeam(this.saveId, false);
				addShipEntityToCurrentTeam(saveId, entity);
				
				//id++, 且在0~5之間變動
				saveId++;
				if (saveId > 5) saveId = 0;
				
				return;
			}
			else
			{
				if (slot > 5) slot = 0;
				
				this.setSelectStateCurrentTeam(slot, false);
				addShipEntityToCurrentTeam(slot, null);
				
				return;
			}
		}//end server side
	}
	
	/** check target is in team with #ship > 4 and formation type > 0
	 * 
	 *  return {team id, slot id}*/
	public int[] checkIsInFormationTeam(int shipID)
	{
		int[] val = new int[] {-1, -1};
		
		if (shipID > 0)
		{
			for (int i = 0; i < 9; i++)
			{
				//team is in formation
				if (this.getFormatID(i) > 0)
				{
					//val: 0:#ship, 1:slot id
					val = checkIsInTeam(shipID, i);
					
					//ship in team and #ship > 4
					if (val[0] > 4 && val[1] >= 0)
					{
						//set val[0] = team id, val[1] = slot id
						val[0] = i;
						break;
					}
				}
			}
		}
		
		//get no ship, reset team id to -1
		if (val[1] < 0) val[0] = -1;
		
		return val;
	}
	
	/** check target is in current team, return slot id, return slot id */
	public int checkIsInCurrentTeam(int shipID)
	{
		return checkIsInTeam(shipID, this.teamId)[1];
	}
	
	/** check target is in team
	 *  return {team id, slot id} */
	public int[] checkIsInTeam(int shipID)
	{
		int[] val = new int[] {-1, -1};
		
		if (shipID > 0)
		{
			for (int i = 0; i < 9; i++)
			{
				val[1] = checkIsInTeam(shipID, i)[1];  //get slot id
				
				if (val[1] > 0)
				{  //get ship in team i, return
					val[0] = i;
					break;
				}
				else
				{  //not in team, reset slot id to -1
					val[1] = -1;
				}
			}
		}
		
		return val;
	}
	
	/** check target is in team, return slot id, ONLY CHECK LOADED SHIP
	 *  return {#ship, slot id} */
	public int[] checkIsInTeam(int shipID, int teamID)
	{
		int[] val = new int[] {0, -1};  //slot id, #ships
		
		try
		{
			for (int i = 0; i < 6; i++)
			{
				if (this.teamList[teamID][i] != null && this.teamList[teamID][i].isEntityAlive())
				{
					//number++
					val[0] += 1;
					
					//check is target ship
					if (teamList[teamID][i].getShipUID() == shipID)
					{
						val[1] = i;
					}
				}
				else
				{
					//clear dead entity
					this.teamList[teamID][i] = null;
				}
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : check ship in team fail: "+e);
		}
		
		return val;
	}
	
	//clear select state of a team
	public void clearSelectStateCurrentTeam()
	{
		selectState[teamId][0] = false;
		selectState[teamId][1] = false;
		selectState[teamId][2] = false;
		selectState[teamId][3] = false;
		selectState[teamId][4] = false;
		selectState[teamId][5] = false;
	}
	
	//clear a ship slot
	public void removeShipCurrentTeam(int id)
	{
		teamList[teamId][id] = null;
		sidList[teamId][id] = -1;
		selectState[teamId][id] = false;
	}
	
	//clear all slot
	public void removeShipCurrentTeam()
	{
		for (int i = 0; i < 6; i++)
		{
			teamList[teamId][i] = null;
			sidList[teamId][i] = -1;
			selectState[teamId][i] = false;
		}
	}
	
	//clear all target class
	public void clearAllTargetClass()
	{
		this.targetClassMap.clear();
	}
	
	/** update SID of loaded ship, ONLY FOR INIT */
	public void updateSID()
	{
		if (sidList != null && teamList != null)
		{
			//get ship sid
			for (int i = 0; i < 9; i++)
			{
				for (int j = 0; j < 6; j++)
				{
					if (this.teamList[i][j] != null)
					{
						this.sidList[i][j] = this.teamList[i][j].getShipUID();
					}
				}
			}
		}
	}
	
	/** get entity by SID, ONLY FOR INIT */
	public void updateShipEntityBySID()
	{
		if (this.sidList != null)
		{
			for (int i = 0; i < 9; i++)
			{
				for (int j = 0; j < 6; j++)
				{
					if (this.sidList[i][j] > 0)
					{
						this.teamList[i][j] = EntityHelper.getShipBySID(sidList[i][j]);
					}
					else
					{
						this.teamList[i][j] = null;
					}
				}
			}
			
			this.initSID = true;
		}
	}
	
	/** check team is ally, CLIENT SIDE ONLY for GUI display */
	public boolean isTeamAlly(int par1)
	{
		//no team (id 0) = friendly to everyteam
		if (par1 == 0) return true;
		
		//for client side
		if (this.player.worldObj.isRemote)
		{
			if (this.getPlayerTeamAllyList() != null)
			{
				//check team ally id
				if (this.getPlayerTeamAllyList().contains(par1)) return true;
			}
		}
		
		return false;
	}
	
	/** check team is enemy, CLIENT SIDE ONLY for GUI display */
	public boolean isTeamBanned(int par1)
	{
		//no team (id 0) = friendly to everyteam
		if (par1 == 0) return false;
		
		//for client side
		if (this.player.worldObj.isRemote)
		{
			if (this.getPlayerTeamBannedList() != null)
			{
				//check team ally id
				if (this.getPlayerTeamBannedList().contains(par1)) return true;
			}
		}
		
		return false;
	}
	
	/** sync props data to client<br>
	 *  0:ring state and current team data<br>
	 *  1:formation data<br>
	 *  2:target class list<br>
	 *  3:team data<br>
	 *  4:all ships in team list<br>
	 *  5:collected ship list<br>
	 *  6:collected equip list<br>
	 */
	public void sendSyncPacket(int type)
	{
		if(player.worldObj != null && !player.worldObj.isRemote)
		{
			switch (type)
			{
			case 0:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp), (EntityPlayerMP) player);
				break;
			case 1:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_Formation), (EntityPlayerMP) player);
				break;
			case 2:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_TargetClass), (EntityPlayerMP) player);
				break;
			case 3:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_TeamData), (EntityPlayerMP) player);
				break;
			case 4:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_ShipsAll), (EntityPlayerMP) player);
				break;
			case 5:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.SyncPlayerProp_ColledShip, this.getColleShipList()), (EntityPlayerMP) player);
				break;
			case 6:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.SyncPlayerProp_ColledEquip, this.getColleEquipList()), (EntityPlayerMP) player);
				break;
			}
		}
	}
	
	/** sync all ships in team list to client */
	public void syncShips()
	{
		sendSyncPacket(4);
	}
	
	/** sync ships in a team to client */
	public void syncShips(int teamID)
	{
		if (player.worldObj != null && !player.worldObj.isRemote)
		{
			CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_ShipsInTeam, teamID), (EntityPlayerMP) player);
		}
	}
	
	/** swap ship position in team */
	public void swapShip(int tid, int posA, int posB)
	{
		//number check
		if (tid < 0 || tid > 8 || posA < 0 || posA > 5 || posB < 0 || posB > 5) return;
		
		BasicEntityShip shipA = this.teamList[tid][posA];
		int sidA = this.sidList[tid][posA];
		boolean selA = this.selectState[tid][posA];
		
		//swap ship
		this.teamList[tid][posA] = this.teamList[tid][posB];
		this.sidList[tid][posA] = this.sidList[tid][posB];
		this.selectState[tid][posA] = this.selectState[tid][posB];
		
		this.teamList[tid][posB] = shipA;
		this.sidList[tid][posB] = sidA;
		this.selectState[tid][posB] = selA;
		
		//set ship formation update
		if(this.teamList[tid][posA] != null) this.teamList[tid][posA].setUpdateFlag(ID.FU.FormationBuff, true);
		if(this.teamList[tid][posB] != null) this.teamList[tid][posB].setUpdateFlag(ID.FU.FormationBuff, true);
	
		//update formation guard position
		FormationHelper.applyFormationMoving(this.teamList[tid], getFormatID(tid));
	}
	
	/** get player extend props by player eid */
	public static CapaTeitoku getTeitokuCapability(int entityID, int worldID, boolean isClient)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByID(entityID, worldID, isClient);
		return getTeitokuCapability(player);
	}
	
	/** get player extend props by player UID */
	public static CapaTeitoku getTeitokuCapability(int pid)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByUID(pid);
		return getTeitokuCapability(player);
	}
	
	/** get client player's teitoku capability */
	public static CapaTeitoku getTeitokuCapabilityClientOnly()
	{
		return getTeitokuCapability(ClientProxy.getClientPlayer());
	}
	
	/** get teitoku capability */
	public static CapaTeitoku getTeitokuCapability(EntityPlayer player)
	{
		if (player == null) return null;
		
		ICapaTeitoku capa = player.getCapability(CapaTeitokuProvider.TeitokuCapability, null);
		return (CapaTeitoku) capa;
	}
	
	/** set player capability data by packets, CLIENT SIDE ONLY */
	public static void setCapaDataMisc(int[] value)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		CapaTeitoku capa = getTeitokuCapability(player);
				
		if (capa != null)
		{
			capa.setRingActiveI(value[0]);
			capa.setMarriageNum(value[1]);
			capa.setPlayerUID(value[2]);
			
			//disable fly if non-active
			if (!capa.isRingActive() && !player.capabilities.isCreativeMode && capa.isRingFlying())
			{
				LogHelper.info("DEBUG : cancel fly by right click");
				player.capabilities.isFlying = false;
				capa.setRingFlying(false);
			}
		}
	}
	
	/** set player team list by packets, CLIENT SIDE ONLY */
	public static void setCapaDataTeamList(int teamid, int[] formatID, int[] teamlist, boolean[] selstate)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		CapaTeitoku capa = getTeitokuCapability(player);
		
		if (capa != null)
		{
			//set current team
			capa.setPointerTeamID(teamid);
			
			//set formation id
			capa.setFormatID(formatID);
			
			//set team selected
			for (int i = 0; i < 6; i++)
			{
				//set select state
				capa.setSelectStateCurrentTeam(i, selstate[i]);
				
				//set ship entity
				if (teamlist[i * 2] <= 0)
				{
					capa.addShipEntity(i, null, true);
				}
				else
				{
					Entity getEnt = EntityHelper.getEntityByID(teamlist[i * 2], 0, true);
					
					if (getEnt instanceof BasicEntityShip)
					{
						capa.addShipEntity(i, (BasicEntityShip) getEnt, true);
					}
					else
					{
						capa.addShipEntity(i, null, true);
					}
				}
				
				//set ship UID
				capa.setSIDCurrentTeam(i, teamlist[i * 2 + 1]);
				
				/**NOTE:
				 * client端可能接收到不同world的entity, 導致getEntityByID結果為null
				 * 此時會讓teamList存null, 但是sidList有存ship UID
				 * pointer item可以藉此將該slot標記為ship lost
				 * 藉此保留該slot直到切換到相同world為止
				 * 
				 * server端可正確找到entity, 以上只針對client端的狀況說明
				 */
			}//end for loop
		}//end props != null
	}
	
	
}