package com.lulan.shincolle.capability;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * teitoku data capability
 * tut: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
 *
 * capability data本體
 * 
 * TODO: remove this capa, move to ServerProxy world data
 */
public class CapaTeitoku implements ICapaTeitoku, IInventory
{
	public static final String CAPA_KEY = "TeitokuExtProps";
	public static final String INV_KEY = "CpInv";
	public EntityPlayer player;
	public String playerName;
	
	//temp var
	public boolean needInit = false;	//player setting flag
	public boolean initSID = false;		//ship UID init flag: false = not init
	public boolean isOpeningGUI;		//in using GUI
	
	//player data
	private boolean hasRing;
	private boolean hasTeam;
	private boolean isRingActive;
	private boolean isRingFlying;
	private int marriageNum;
	private int bossCooldown;			//spawn boss cooldown
	private int teamCooldown;			//recreate team cooldown
	
	/**ship team list
	 * 9 teams, 1 team = 6 ships
	 * save ship entity id
	 */
	private BasicEntityShip[][] teamList;	//total 9 teams, 1 team = 6 ships
	private boolean[][] selectState;		//ship selected, for command control target
	private int[][] sidList;				//ship UID
	private int[] formatID;					//ship formation ID
	private int saveId;						//current ship/empty slot, value = 0~5
	private int teamId;						//current team
	private ArrayList<Integer> listShipEID;			//all loaded ships' entity id list for radar
	private ArrayList<Integer> listColleShip;		//all obtained ships list
	private ArrayList<Integer> listColleEquip;		//all obtained equipment list
	private String[] unitNames;				//team names
	
	//player id
	private int playerUID;
	
	//player team: for client side GUI only, do not use these at server side
	private HashMap<Integer, TeamData> mapTeamData;
	private ArrayList<TeamData> listTeamData;
	
	//target selector
	private HashMap<Integer, String> targetClassMap;	//temp for client side, used in GUI
	
	//inter-mod
	public ItemStackHandler itemHandler;
	public EntityLivingBase morphEntity;
	public int[] shipMinor;		//0:grudge, 1:lightAmmo, 2:heavyAmmo
	
	
	/**
	 * 注意constructor會在entity之前就先new, 外加會接著跑一次loadNBTData
	 * 因此在constructor中就必須先new好各array類變數的空間, 以免NPE
	 * 
	 * playerUID跟playerName只能放在第二段init方法中執行
	 */
	public CapaTeitoku()
	{
		this.playerName = "";
		this.isOpeningGUI = false;
		this.hasRing = false;
		this.hasTeam = false;
		this.isRingActive = false;
		this.isRingFlying = false;
		this.marriageNum = 0;
		this.bossCooldown = ConfigHandler.bossCooldown;
		this.teamCooldown = 20;
		this.teamList = new BasicEntityShip[9][6];
		this.unitNames = new String[9];
		this.selectState = new boolean[9][6];
		this.initSID = false;
		this.sidList = new int[9][6];
		this.formatID = new int[9];
		this.saveId = 0;
		this.teamId = 0;
		this.listShipEID = new ArrayList<Integer>();
		this.listColleShip = new ArrayList<Integer>();
		this.listColleEquip = new ArrayList<Integer>();
		this.playerUID = -1;
		this.mapTeamData = new HashMap<Integer, TeamData>();
		this.listTeamData = new ArrayList<TeamData>();
		this.targetClassMap = new HashMap<Integer, String>();
		
		//inter-mod
		this.morphEntity = null;
		this.itemHandler = new ItemStackHandler(this.getSizeInventory());
		this.shipMinor = new int[3];
		
		//need init
		this.needInit = true;
	}

	//第二段init: init capability on player login
	public void init(EntityPlayer player)
	{
		LogHelper.debugHighLevel("DEBUG: init player capability data.");
		
		this.player = player;
		this.playerName = player.getName();
		
		//init done
		this.needInit = false;
	}
	
	@Override
	public NBTTagCompound saveNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound nbtExt = new NBTTagCompound();
		
		nbtExt.setBoolean("hasRing", hasRing);
		nbtExt.setBoolean("RingOn", isRingActive);
		nbtExt.setBoolean("RingFly", isRingFlying);
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
			e.printStackTrace();
		}
		
		/**save team list by ship UID
		 * entity id will change after entity reconstruction
		 * a way to keep team list is saving ship UID
		 */
		//每次儲存sid前先更新過一次
		if (this.initSID)
		{	//update id AFTER sid is inited
			LogHelper.debug("DEBUG: save player ExtNBT: update ship UID from teamList");
			this.updateSID();
		}
		
		for (int i = 0; i < 9; i++)
		{
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
		
		//save team names
		for (int i = 0; i < 9; i++)
		{
			if (this.unitNames[i] != null && this.unitNames[i].length() > 1)
			{
				nbtExt.setString("uname"+i, this.unitNames[i]);
			}
			else
			{
				nbtExt.setString("uname"+i, " ");
			}
		}
		
		/** inter-mod */
		//save ship inventory
		if (this.itemHandler == null) this.itemHandler = new ItemStackHandler(this.getSizeInventory());
		nbtExt.setTag(CapaTeitoku.INV_KEY, this.itemHandler.serializeNBT());
		
		//save ship minor data
		if (this.shipMinor != null)
		{
			nbtExt.setInteger("NumGrudge", this.shipMinor[0]);
			nbtExt.setInteger("NumAmmoL", this.shipMinor[1]);
			nbtExt.setInteger("NumAmmoH", this.shipMinor[2]);
		}
		
		nbt.setTag(CAPA_KEY, nbtExt);
		LogHelper.debug("DEBUG : save player ExtNBT data on: "+this.player);

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
			//check player UID tag in 'nbt', not 'nbtExt'
			if (!nbt.hasKey("PlayerUID"))
			{
				LogHelper.debug("DEBUG: player loadNBTData: fail, data is null "+nbt+" "+nbtExt);
				return;
			}
			else
			{
				LogHelper.debug("DEBUG: player loadNBTData: get data without tag name");
				nbtExt = nbt;
			}
		}
		
		//load data
		LogHelper.debug("DEBUG: player loadNBTData: get data "+nbt+" "+nbtExt);
		try
		{
			hasRing = nbtExt.getBoolean("hasRing");
			isRingActive = nbtExt.getBoolean("RingOn");
			isRingFlying = nbtExt.getBoolean("RingFly");
			formatID = nbtExt.getIntArray("FormatID");
			marriageNum = nbtExt.getInteger("MarriageNum");
			bossCooldown = nbtExt.getInteger("BossCD");
			playerUID = nbtExt.getInteger("PlayerUID");
			teamCooldown = nbtExt.getInteger("TeamCD");
			
			//load colle list
			int[] arrtemp = nbtExt.getIntArray("ColleShip");
			this.listColleShip = CalcHelper.intArrayToList(arrtemp);
			arrtemp = nbtExt.getIntArray("ColleEquip");
			this.listColleEquip = CalcHelper.intArrayToList(arrtemp);
			
			//load ship id and select state list
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
			
			//load team names
			for (int i = 0; i < 9; i++)
			{
				if (this.unitNames == null) this.unitNames = new String[9];
				
				this.unitNames[i] = nbtExt.getString("uname"+i);
			}
			
			/** inter-mod */
			//load ship inventory
	        if (nbtExt.hasKey(CapaTeitoku.INV_KEY))
	        {
	        	if (this.itemHandler == null) this.itemHandler = new ItemStackHandler(this.getSizeInventory());
	        	this.itemHandler.deserializeNBT((NBTTagCompound) nbtExt.getTag(CapaTeitoku.INV_KEY));
        	}
	        
	        //load ship minor data
	        if (this.shipMinor == null) this.shipMinor = new int[3];
	        this.shipMinor[0] = nbtExt.getInteger("NumGrudge");
	        this.shipMinor[1] = nbtExt.getInteger("NumAmmoL");
	        this.shipMinor[2] = nbtExt.getInteger("NumAmmoH");
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: player loadNBTData: load fail: "+e);
			e.printStackTrace();
		}
		
		LogHelper.debug("DEBUG: load player ExtNBT data on: "+this.player);
	}
	
	//getter
	public boolean isRingActive()
	{
		return isRingActive;
	}
	
	public boolean isRingFlying()
	{
		return isRingFlying;
	}
	
	public boolean hasRing()
	{
		return hasRing;
	}
	
	//check has team from server team cache
	public boolean hasTeam()
	{
		//server side
		if (player != null && !player.world.isRemote)
		{
			if (ServerProxy.getTeamData(this.playerUID) != null) return true;
		}
		
		//client side
		return this.hasTeam;
	}
	
	public int getMarriageNum()
	{
		return marriageNum;
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
			LogHelper.info("EXCEPTION: get ship entity from extProps fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: get all ship entity from extProps fail: "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<BasicEntityShip> getShipEntityAllList(int tid)
	{
		ArrayList<BasicEntityShip> list = new ArrayList<BasicEntityShip>();
		
		if (teamList != null && teamList[tid] != null)
		{
			for (BasicEntityShip s : teamList[tid])
			{
				list.add(s);
			}
		}
		
		return list;
	}
	
	public BasicEntityShip[][] getShipEntityAllTeams()
	{
		try
		{
			return teamList;
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: get all ship entity from extProps fail: "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	/** get ships for pointer by pointer's mode: 0:single, 1:group, 2:formation */
	public ArrayList<BasicEntityShip> getShipEntityByMode(int mode)
	{
		//meta為pointer的item damage
		ArrayList<BasicEntityShip> ships = new ArrayList<BasicEntityShip>();
		boolean shouldSync = false;
		
		switch (mode)
		{
		case 1:		//group mode
			//return所有已選擇的ship
			for (int i = 0; i < 6; i++)
			{
				if (this.getSelectStateCurrentTeam(i) && this.teamList[teamId][i] != null)
				{
					ships.add(this.teamList[teamId][i]);
				}
			}
		break;
		case 2:		//formation mode
			//return整個team
			for (int i = 0; i < 6; i++)
			{
				if (this.teamList[teamId][i] != null)
				{
					ships.add(this.teamList[teamId][i]);
				}
			}
		break;
		default:	//single mode
			//return第一個找到的已選擇的ship
			for (int i = 0; i < 6; i++)
			{
				if (this.getSelectStateCurrentTeam(i) && this.teamList[teamId][i] != null)
				{
					ships.add(this.teamList[teamId][i]);
					break;
				}
			}
		break;
		}//end switch
		
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
			LogHelper.info("EXCEPTION: get ship select state fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: get ship select state fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: get ship ID fail: "+e);
			e.printStackTrace();
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
	
	public int getCurrentTeamID()
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
	
	public HashMap<Integer, String> getTargetClassMap()
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
			LogHelper.info("EXCEPTION: get formation id fail: "+e);
			e.printStackTrace();
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
		boolean getnoship = true;
		
		try
		{
			for (BasicEntityShip ship : this.teamList[teamID])
			{
				if (ship != null)
				{
					getnoship = false;
					temp = ship.getAttrs().getMoveSpeed();
					if (temp < mov) mov = temp;
				}
			}
			
			if (getnoship)
			{
				LogHelper.debug("DEBUG: get min move speed: no ship in team");
				return 0F;
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: get entity MOV from extProps fail: "+e);
			e.printStackTrace();
			return 0F;
		}
		
		return mov;
	}
	
	public String[] getUnitName()
	{
		if (this.unitNames != null) return this.unitNames;
		return new String[9];
	}
	
	public String getUnitName(int tid)
	{
		if (this.unitNames != null) return this.unitNames[tid];
		return "";
	}
	
	//setter
	public void setRingActive(boolean par1)
	{
		isRingActive = par1;
	}
	
	public void setRingFlying(boolean par1)
	{
		isRingFlying = par1;
	}
	
	public void setHasRing(boolean par1)
	{
		hasRing = par1;
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
			LogHelper.info("EXCEPTION: set ship ID fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: set current team ship ID fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: set ship select state fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION : set current team select state fail.");
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: set ship team formation id fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: set current team formation id fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: set ship team entity fail: "+e);
			e.printStackTrace();
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
			LogHelper.info("EXCEPTION: set ship team select state fail: "+e);
			e.printStackTrace();
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
	
	public void setHasTeam(boolean par1)
	{
		this.hasTeam = par1;
	}
	
	public void setUnitName(String[] str)
	{
		this.unitNames = str;
	}
	
	public void setUnitName(int tid, String str)
	{
		if (str == null) this.unitNames[tid] = "";
		else this.unitNames[tid] = str;
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
	public int[] checkIsInFormation(int shipID)
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
		
		//ship not found, reset team id to -1
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
			LogHelper.info("EXCEPTION: check ship in team fail: "+e);
			e.printStackTrace();
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
						this.teamList[i][j] = EntityHelper.getShipByUID(sidList[i][j]);
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
		if (this.player.world.isRemote)
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
		if (this.player.world.isRemote)
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
	 *  7:all musc int value
	 *  8:unit name
	 */
	public void sendSyncPacket(int type)
	{
		if (player.world != null && !player.world.isRemote)
		{
			switch (type)
			{
			case 0:
				//check all ship entity before sync
				this.updateShipEntityBySID();
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
			case 7:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_Misc), (EntityPlayerMP) player);
			break;
			case 8:
				CommonProxy.channelG.sendTo(new S2CGUIPackets(this, S2CGUIPackets.PID.SyncPlayerProp_UnitName), (EntityPlayerMP) player);
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
		if (player.world != null && !player.world.isRemote)
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
		if(this.teamList[tid][posA] != null) this.teamList[tid][posA].setUpdateFlag(ID.FlagUpdate.FormationBuff, true);
		if(this.teamList[tid][posB] != null) this.teamList[tid][posB].setUpdateFlag(ID.FlagUpdate.FormationBuff, true);
	
		ArrayList<BasicEntityShip> ships = new ArrayList<BasicEntityShip>();
		
		for (int i = 0; i < this.teamList[tid].length; i++)
		{
			if (this.teamList[tid][i] != null) ships.add(this.teamList[tid][i]);
		}
		
		if (ships.size() > 4)
		{
			//update formation guard position
			FormationHelper.applyFormationMoving(ships, getFormatID(tid), (int)ships.get(0).posX, (int)ships.get(0).posY, (int)ships.get(0).posZ, false);
		}
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
	
	/** init and set player capability data by packets, CLIENT SIDE ONLY
	 * 
	 *  value: 0:ring active, 1:has team flag, 2:current team id, 3:marriage num
	 *         4:player uid, 5:team cooldown
	 */
	public static void setCapaDataMisc(int[] value)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		CapaTeitoku capa = getTeitokuCapability(player);
				
		if (capa != null)
		{
			if (capa.needInit) capa.init(player);
			
			capa.isRingActive = value[0] > 0 ? true : false;
			capa.hasTeam = value[1] > 0 ? true : false;
			capa.teamId = value[2];
			capa.marriageNum = value[3];
			capa.playerUID = value[4];
			capa.teamCooldown = value[5];
			
			//disable fly if non-active
			if (!capa.isRingActive && !player.capabilities.isCreativeMode && capa.isRingFlying)
			{
				LogHelper.debug("DEBUG: cancel fly by right click");
				player.capabilities.isFlying = false;
				capa.isRingFlying = false;
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
	
	//get ship team id, ship can exist in multiple teams
	public ArrayList<Integer> getShipTeamIDArray(int shipUID)
	{
		ArrayList<Integer> tid = new ArrayList<Integer>();
		
		if (this.sidList != null)
		{
			for (int i = 0; i < 9; i++)
			{
				for (int j = 0; j < 6; j++)
				{
					if (this.sidList[i][j] == shipUID)
					{
						tid.add(i);
						break;
					}
				}
			}
		}
		
		return tid;
	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public int getSizeInventory()
	{
		return 6;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.itemHandler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int id, int count)
	{
		try
		{
  			if (id >= 0 && id < itemHandler.getSlots() &&
  				!itemHandler.getStackInSlot(id).isEmpty() && count > 0)
  	        {
  	            ItemStack itemstack = itemHandler.getStackInSlot(id).splitStack(count);

  	            if (itemHandler.getStackInSlot(id).getCount() == 0)
  	            {
  	            	itemHandler.setStackInSlot(id, ItemStack.EMPTY);
  	            }

  	            return itemstack;
  	        }
  	        else
  	        {
  	            return ItemStack.EMPTY;
  	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int id, ItemStack stack)
	{
		if (itemHandler != null && itemHandler.getSlots() > id)
		{
  			itemHandler.setStackInSlot(id, stack);
			
			//若手上物品超過該格子限制數量, 則只能放進限制數量
	  		if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit())
	  		{
	  			stack.setCount(getInventoryStackLimit());
	  		}
	  		
	  		//check item in equip slot
			if (this.player != null && this.player.world != null && !this.player.world.isRemote &&
				id < 6 && this.morphEntity instanceof BasicEntityShip)
			{
				((BasicEntityShip)this.morphEntity).calcShipAttributes(2, true);  //update equip and attribute value
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
	}
	
	/**
	 * ticking method for player skill
	 */
	public void onPlayerSkillTick()
	{
		//TODO
	}
	
	
}