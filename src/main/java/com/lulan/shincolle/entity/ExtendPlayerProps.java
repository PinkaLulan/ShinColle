package com.lulan.shincolle.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

public class ExtendPlayerProps implements IExtendedEntityProperties {

	public static final String PLAYER_EXTPROP_NAME = "TeitokuExtProps";
	public EntityPlayer player;
	public World world;
	
	//wedding ring
	private boolean hasRing;
	private boolean isRingActive;
	private boolean isRingFlying;
	private int[] ringEffect;			//0:haste 1:speed 2:jump 3:damage
	private int marriageNum;
	private int bossCooldown;			//spawn boss cooldown
	
	/**ship team list
	 * 9 teams, 1 team = 6 ships
	 * save ship entity id
	 */
	private BasicEntityShip[][] teamList;	//total 9 teams, 1 team = 6 ships
	private boolean[][] selectState;		//ship selected, for command control target
	private boolean initSID = false;		//ship UID init flag: false = not init
	private int[][] sidList;				//ship UID
	private int saveId;						//current ship/empty slot, value = 0~5
	private int teamId;						//current team
	private List<Integer> shipEIDList;		//all loaded ships' entity id list for radar
	
	//player id
	private int playerUID;
	
	//player team var: for display only, set when player login
	private int allyCooldown;
	private int playerTeamID;
	private Map<Integer, TeamData> mapTeamData;
	private List<TeamData> listTeamData;
	
	//target selector
	private List<String> targetClassList;	//list temp for client side, used in GUI


	@Override
	public void init(Entity entity, World world) {
		this.world = world;
		this.player = (EntityPlayer) entity;
		this.hasRing = false;
		this.isRingActive = false;
		this.isRingFlying = false;
		this.ringEffect = new int[] {0, 0, 0, 0};
		this.marriageNum = 0;
		this.bossCooldown = ConfigHandler.bossCooldown;
		this.teamList = new BasicEntityShip[9][6];
		this.selectState = new boolean[9][6];
		this.sidList = new int[9][6];
		this.shipEIDList = new ArrayList();
		this.targetClassList = new ArrayList();
		this.initSID = false;
		this.saveId = 0;
		this.teamId = 0;
		this.playerUID = -1;
		
		//team
		this.playerTeamID = 0;
		this.allyCooldown = ConfigHandler.allyCooldown;
		this.mapTeamData = new HashMap();
		this.listTeamData = new ArrayList();
		
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		NBTTagCompound nbtExt = new NBTTagCompound();
		
		nbtExt.setBoolean("hasRing", hasRing);
		nbtExt.setBoolean("RingOn", isRingActive);
		nbtExt.setBoolean("RingFly", isRingFlying);
		nbtExt.setIntArray("RingEffect", ringEffect);
		nbtExt.setInteger("MarriageNum", marriageNum);
		nbtExt.setInteger("BossCD", bossCooldown);
		nbtExt.setInteger("PlayerUID", playerUID);
		nbtExt.setInteger("AllyCD", allyCooldown);
		
		/**save team list by ship UID
		 * entity id will change after entity reconstruction
		 * a way to keep team list is saving ship UID
		 */
		if(this.initSID) {	//update id AFTER sid is inited
			LogHelper.info("DEBUG : save player ExtNBT: update ship UID from teamList");
			this.updateSID();
		}
		
		for(int i = 0; i < 9; i++) {
			LogHelper.info("DEBUG : save player ExtNBT: "+this.getSIDofTeam(i)[0]);
			nbtExt.setIntArray("TeamList"+i, this.getSIDofTeam(i));
			nbtExt.setByteArray("SelectState"+i, this.getSelectStateOfTeam(i));
		}
		
		//save custom target class list
		if(this.targetClassList != null) {
			NBTTagList list = new NBTTagList();
			nbtExt.setTag("CustomTargetClass", list);
			
			for(String getc : targetClassList) {
				NBTTagString str = new NBTTagString(getc);
				list.appendTag(str);
			}
		}
		
		nbt.setTag(PLAYER_EXTPROP_NAME, nbtExt);
		LogHelper.info("DEBUG : save player ExtNBT data on id: "+player.getEntityId());
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		NBTTagCompound nbtExt = (NBTTagCompound) nbt.getTag(PLAYER_EXTPROP_NAME);
		
		hasRing = nbtExt.getBoolean("hasRing");
		isRingActive = nbtExt.getBoolean("RingOn");
		isRingFlying = nbtExt.getBoolean("RingFly");
		ringEffect = nbtExt.getIntArray("RingEffect");
		marriageNum = nbtExt.getInteger("MarriageNum");
		bossCooldown = nbtExt.getInteger("BossCD");
		playerUID = nbtExt.getInteger("PlayerUID");
		
		//team
		allyCooldown = nbtExt.getInteger("AllyCD");
		
		/**load team list by ship UID
		 * get entity by ship UID, SERVER SIDE ONLY
		 * 
		 * player entity can be loaded between ship entities,
		 * the teamList have to sync after all entity loaded (not here)
		 */
		if(!this.world.isRemote) {
			for(int i = 0; i < 9; ++i) {
				byte[] byteSelect = nbtExt.getByteArray("SelectState"+i);
				int[] sid = nbtExt.getIntArray("TeamList"+i);
						
				if(sid != null && sid.length > 5) {  //null check for new player
					for(int j = 0; j < 6; ++j) {
						//set select state
						this.selectState[i][j] = byteSelect[j] == 1 ? true : false;
						//set ship UID
						this.sidList[i][j] = sid[j];
					}
				}
			}
		}
		
		LogHelper.info("DEBUG : load player ExtNBT data on id: "+player.getEntityId()+" client? "+this.world.isRemote);
	}
	
	//getter
	public boolean isRingActive() {
		return isRingActive;
	}
	
	public int isRingActiveI() {
		return isRingActive ? 1 : 0;
	}
	
	public boolean isRingFlying() {
		return isRingFlying;
	}
	
	public boolean hasRing() {
		return hasRing;
	}
	
	public int getRingEffect(int id) {
		return ringEffect[id];
	}
	
	public int getMarriageNum() {
		return marriageNum;
	}
	
	public int getDigSpeedBoost() {
		return isRingActive ? marriageNum : 0;
	}
	
	public int getBossCooldown() {
		return this.bossCooldown;
	}
	
	public int[] getEntityIDofTeam(int tid) {
		int[] eid = new int[6];
		
		for(int i = 0; i < 6; i++) {
			if(teamList[tid][i] != null) {
				eid[i] = teamList[tid][i].getEntityId();
			}
			else {
				eid[i] = -1;
			}
		}
		
		return eid;
	}
	
	public BasicEntityShip getEntityOfCurrentTeam(int id) {
		if(id > 5) id = 0;
		return teamList[teamId][id];
	}
	
	public BasicEntityShip[] getEntityOfCurrentMode(int mode) {	//meta為pointer的item damage
		BasicEntityShip[] ships = new BasicEntityShip[6];
		
		switch(mode) {
		default:	//single mode
			//return第一個找到的已選擇的ship
			for(int i = 0; i < 6; i++) {
				if(this.getSelectStateOfCurrentTeam(i)) {
					ships[0] = this.teamList[teamId][i];
					return ships;
				}
			}
			break;
		case 1:		//group mode
			//return所有已選擇的ship
			int j = 0;
			for(int i = 0; i < 6; i++) {
				if(this.getSelectStateOfCurrentTeam(i)) {
					ships[j] = this.teamList[teamId][i];
					j++;
				}
			}
			break;
		case 2:		//formation mode
			//return整個team
			return this.teamList[teamId];
		}
		
		return ships;
	}
	
	public byte[] getSelectStateOfTeam(int tid) {	//get selected state (byte array)
		byte[] byteState = new byte[6];
		
		if(tid > 5) tid = 0;
		
		if(selectState[tid] != null) {
			for(int i = 0; i < 6; ++i) {
				byteState[i] = selectState[tid][i] ? (byte)1 : (byte)0;
			}
		}
		
		return byteState;
	}
	
	public boolean getSelectStateOfCurrentTeam(int id) {	//get selected state
		if(id > 5) id = 0;
		return selectState[teamId][id];
	}
	
	public int[] getSIDofTeam(int tid) {	//get all ship UID in a team
		if(sidList != null && sidList[tid] != null) {
			return sidList[tid];
		}
		
		return null;
	}
	
	public int getSIDofCurrentTeam(int id) {	//get current team ship UID
		if(id > 5) id = 0;
		return sidList[teamId][id];
	}
	
	public int getTeamId() {
		return this.teamId;
	}
	
	public int getPlayerUID() {
		return this.playerUID;
	}
	
	public int getPlayerTeamID() {
		return this.playerTeamID;
	}
	
	public String getPlayerTeamName() {
		if(this.playerTeamID > 0) {
			TeamData tdata = this.mapTeamData.get(this.playerTeamID);
			
			if(tdata != null) return tdata.getTeamName();
		}
		return null;
	}
	
	public List<Integer> getPlayerTeamMember() {
		if(this.playerTeamID > 0) {
			TeamData tdata = this.mapTeamData.get(this.playerTeamID);
			
			if(tdata != null) return tdata.getTeamMemberUID();
		}
		return null;
	}
	
	public List<Integer> getPlayerTeamAlly() {
		if(this.playerTeamID > 0) {
			TeamData tdata = this.mapTeamData.get(this.playerTeamID);
			
			if(tdata != null) return tdata.getTeamAlly();
		}
		return null;
	}
	
	public Map<Integer, TeamData> getAllTeamData() {  //for client GUI display
		return this.mapTeamData;
	}
	
	public List<TeamData> getAllTeamDataList() {  //for client GUI display
		return this.listTeamData;
	}
	
	public TeamData getTeamData(int par1) {  //for client GUI display
		return this.mapTeamData.get(par1);
	}
	
	public int getAllyCooldown() {
		return this.allyCooldown;
	}
	
	public boolean getInitSID() {
		return this.initSID;
	}
	
	public List<Integer> getShipEIDList() {
		return this.shipEIDList;
	}
	
	public List<String> getTargetClassList() {
		return this.targetClassList;
	}
	
	//setter
	public void setRingActive(boolean par1) {
		isRingActive = par1;
	}
	
	public void setRingActiveI(int par1) {
		if(par1 == 0) {
			isRingActive = false;
		}
		else {
			isRingActive = true;
		}
	}
	
	public void setRingFlying(boolean par1) {
		isRingFlying = par1;
	}
	
	public void setHasRing(boolean par1) {
		hasRing = par1;
	}
	
	public void setRingEffect(int id, int par1) {
		ringEffect[id] = par1;
	}
	
	public void setMarriageNum(int par1) {
		marriageNum = par1;
	}
	
	public void setBossCooldown(int par1) {
		this.bossCooldown = par1;
	}
	
	//set ship UID (for CLIENT SIDE sync only)
	public void setSIDofCurrentTeam(int id, int sid) {
		if(id > 5) id = 0;
		sidList[teamId][id] = sid;
	}
	
	public void setSelectStateOfCurrentTeam(int id, boolean par1) {
		if(id > 5) id = 0;
		selectState[teamId][id] = par1;
	}
	
	public void setTeamId(int par1) {
		if(par1 > 9) par1 = 0;
		this.teamId = par1;
	}
	
	public void setPlayerUID(int par1) {
		this.playerUID = par1;
	}
	
	public void setPlayerTeamID(int par1) {
		this.playerTeamID = par1;
	}
	
	public void setAllTeamData(Map<Integer, TeamData> par1) {
		if(this.mapTeamData != null && this.mapTeamData.size() > 0) {
			this.mapTeamData = par1;
			
			//create team list
			this.listTeamData = new ArrayList();
			
			Iterator iter = this.mapTeamData.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
			    TeamData data = (TeamData) entry.getValue();
			    this.listTeamData.add(data);
			}
		}
	}
	
	public void setAllyCooldown(int par1) {
		this.allyCooldown = par1;
	}
	
	public void setInitSID(boolean par1) {
		this.initSID = par1;
	}
	
	public void setShipEIDList(List<Integer> list) {
		this.shipEIDList = list;	//shallow copy, ref only
	}
	
	//set target class by string list
	public void setTargetClass(List<String> list) {
		if(list != null) {
			this.targetClassList = list;
		}
	}
	
	//set target class by class string
	public void setTargetClass(String str) {
		if(str != null && str.length() > 1) {
			//check str exist in list
			for(String s : this.targetClassList) {
				//find target in list, remove target
				if(str.equals(s)) {
					this.targetClassList.remove(s);
					return;
				}
			}
			
			//target not found, add target to list
			this.targetClassList.add(str);
		}
	}
	
	//add ship entity to slot
	public void addEntityToCurrentTeam(int slot, BasicEntityShip entity) {
		if(slot > 5) return;
		
		if(entity == null) {	//clear slot
			this.teamList[teamId][slot] = null;
			this.sidList[teamId][slot] = -1;
			this.selectState[teamId][slot] = false;
		}
		else {	//do not change select state here
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
	public void addEntityToTeam(int slot, BasicEntityShip entity, boolean useTeamID) {
		boolean canAdd = false;
		
		//client 收到sync packets
		if(useTeamID) {
			if(slot > 5) slot = 0;
			addEntityToCurrentTeam(slot, entity);
			return;
		}
		else {
			//entity不為null才找欄位存
			if(entity != null) {
//				//debug: show team
//				for(int k = 0; k < 6; k++) {
//					LogHelper.info("DEBUG : team list (before add) "+this.saveId+" "+k+" "+this.teamList[k]);
//				}
				
				//若entity非自己所屬, 則不能add team
				if(player != null && !EntityHelper.checkSameOwner(player, entity)) {
					return;
				}
				
				//找有無重複ship, 有的話則清除該ship, id指到該slot
				int inTeam = this.checkIsInCurrentTeam(entity.getShipUID());
				if(inTeam >= 0) {
					addEntityToCurrentTeam(inTeam, null);
					this.saveId = inTeam;
					this.setSelectStateOfCurrentTeam(inTeam, false);
					return;
				}
				
				//若無重複entity, 則挑null空位存, id指示為下一個slot
				for(int i = 0; i < 6; i++) {
					if(this.teamList[teamId][i] == null) {
						this.setSelectStateOfCurrentTeam(i, false);
						addEntityToCurrentTeam(i, entity);
						this.saveId = i + 1;
						if(saveId > 5) saveId = 0;
						return;
					}
				}
				
				//都沒空位, 則挑id指的位置存
				this.setSelectStateOfCurrentTeam(this.saveId, false);
				addEntityToCurrentTeam(saveId, entity);
				//id++, 且在0~5之間變動
				saveId++;
				if(saveId > 5) saveId = 0;
				return;
			}
			else {
				if(slot > 5) slot = 0;
				this.setSelectStateOfCurrentTeam(slot, false);
				addEntityToCurrentTeam(slot, null);
				return;
			}
		}//end server side
	}
	
	//check target is in current team, return slot id
	public int checkIsInCurrentTeam(int sid) {
		for(int i = 0; i < 6; i++) {
			if(this.teamList[teamId][i] != null) {
				if(teamList[teamId][i].getShipUID() == sid) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	//clear select state of a team
	public void clearSelectStateOfCurrentTeam() {
		selectState[teamId][0] = false;
		selectState[teamId][1] = false;
		selectState[teamId][2] = false;
		selectState[teamId][3] = false;
		selectState[teamId][4] = false;
		selectState[teamId][5] = false;
	}
	
	//clear a ship slot
	public void clearOneShipOfCurrentTeam(int id) {
		teamList[teamId][id] = null;
		sidList[teamId][id] = -1;
		selectState[teamId][id] = false;
	}
	
	//clear all slot
	public void clearAllShipOfCurrentTeam() {
		for(int i = 0; i < 6; i++) {
			teamList[teamId][i] = null;
			sidList[teamId][i] = -1;
			selectState[teamId][i] = false;
		}
	}
	
	//clear all target class
	public void clearAllTargetClass() {
		this.targetClassList = new ArrayList();
	}
	
	//get all ship UID in a team from entity (for init)
	public void updateSID() {
		if(sidList != null && teamList != null) {
			//get ship sid
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 6; j++) {
					if(this.teamList[i][j] != null) {
						this.sidList[i][j] = this.teamList[i][j].getShipUID();
					}
//					else {
//						this.sidList[i][j] = -1;
//					}
				}
			}
		}
	}
	
	//get ship entity by SID (for init)
	public void updateShipEntityBySID() {
		if(this.sidList != null) {
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 6; j++) {
					if(this.sidList[i][j] > 0) {
						this.teamList[i][j] = EntityHelper.getShipBySID(sidList[i][j]);
					}
					else {
						this.teamList[i][j] = null;
					}
				}
			}
			
			this.initSID = true;
		}
	}
	
	//check is leader: member 0 = player
	public boolean isTeamLeader() {
		if(this.getPlayerTeamID() > 0 && 
		   this.getPlayerTeamMember() != null &&
		   this.getPlayerTeamMember().size() > 0 && 
		   this.getPlayerTeamMember().get(0) == this.playerUID) {
			return true;
		}
		
		return false;
	}
	
	//check team is player's ally
	public boolean isTeamAlly(int par1) {
		//no team (id 0) = friendly to everyteam
		if(par1 == 0) return true;
		
		//for client side
		if(this.world.isRemote) {
			if(this.getPlayerTeamID() > 0 &&
			   this.getPlayerTeamMember() != null &&
			   this.getPlayerTeamMember().size() > 0) {
				//check team ally id
				for(int geti : this.getPlayerTeamAlly()) {
					if(par1 == geti) return true;
				}
			}
		}
		//for server side
		else {
			TeamData tdata = ServerProxy.getTeamData(EntityHelper.getPlayerTID(this.getPlayerUID()));
		
			if(tdata != null) {
				tdata.isTeamAlly(par1);
			}
		}
		
		return false;
	}


}
