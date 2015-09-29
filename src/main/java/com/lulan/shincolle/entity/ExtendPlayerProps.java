package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.lulan.shincolle.handler.ConfigHandler;
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
	
	/**team list
	 * 9 teams, 1 team = 6 ships
	 * save ship entity id
	 */
	private BasicEntityShip[][] teamList;	//total 9 teams, 1 team = 6 ships
	private boolean[][] selectState;		//ship selected, for command control target
	private boolean initSID = false;		//ship UID init flag: false = not init
	private int[][] sidList;				//ship UID
	private int saveId;						//current ship/empty slot, value = 0~5
	private int teamId;						//current team
	
	//player id
	private int playerUID;
	private int playerTeamID;


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
		this.initSID = false;
		this.saveId = 0;
		this.teamId = 0;
		this.playerUID = -1;
		this.playerTeamID = 0;
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
		nbtExt.setInteger("TeamID", playerTeamID);
		
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
		playerTeamID = nbtExt.getInteger("TeamID");
		
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
				LogHelper.info("DEBUG : load player ExtNBT: "+sid[0]);
						
				for(int j = 0; j < 6; ++j) {
					//set select state
					this.selectState[i][j] = byteSelect[j] == 1 ? true : false;
					//set ship UID
					this.sidList[i][j] = sid[j];
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
	
	public int getPlayerTeamId() {
		return this.playerTeamID;
	}
	
	public boolean getInitSID() {
		return this.initSID;
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
	
	public void setPlayerTeamId(int par1) {
		this.playerTeamID = par1;
	}
	
	public void setInitSID(boolean par1) {
		this.initSID = par1;
	}
	
	/**將ship加入隊伍名單
	 * 若ship不為null, 表示要加入名單 -> 找目前非null欄位比對是否同id -> 同id表示remove該entity
	 *                                                  -> 不同id表示可新增entity
	 * 若為null, 表示清空該slot
	 * 若為client端, 表示由sync packet收到資料, 則全部照 傳入值設定
	 */
	public void addEntityToTeam(int id, BasicEntityShip entity, boolean isClient) {
		boolean canAdd = false;
		
		//client 收到sync packets
		if(isClient) {
			if(id > 5) id = 0;
			this.teamList[teamId][id] = entity;
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
				int inTeam = this.checkIsInCurrentTeam(entity.getEntityId());
				if(inTeam >= 0) {
					this.teamList[teamId][inTeam] = null;
					this.saveId = inTeam;
					this.setSelectStateOfCurrentTeam(inTeam, false);
					return;
				}
				
				//若無重複entity, 則挑null空位存, id指示為下一個slot
				for(int i = 0; i < 6; i++) {
					if(this.teamList[teamId][i] == null) {
						this.setSelectStateOfCurrentTeam(i, false);
						teamList[teamId][i] = entity;
						saveId = i + 1;
						if(saveId > 5) saveId = 0;
						return;
					}
				}
				
				//都沒空位, 則挑id指的位置存
				this.setSelectStateOfCurrentTeam(this.saveId, false);
				this.teamList[teamId][this.saveId] = entity;
				//id++, 且在0~5之間變動
				saveId++;
				if(saveId > 5) saveId = 0;
				return;
			}
			else {
				if(id > 5) id = 0;
				this.setSelectStateOfCurrentTeam(id, false);
				this.teamList[teamId][id] = null;
				return;
			}
		}//end server side
	}
	
	/**set whole team list (team 0~8, slot 0~5), only called at server side
	 * world id from player world, if player change world
	 * team list will be all clear (cannot find entity)
	 */
	public void addEntityToTeamByID(int tid, int[] eid) {
		Entity getEnt = null;
		
		for(int i = 0; i < 6; i++) {
			if(eid[i] > 0) {
				getEnt = EntityHelper.getEntityByID(eid[i], world.provider.dimensionId, false);
			
				if(getEnt instanceof BasicEntityShip) {
					teamList[tid][i] = (BasicEntityShip) getEnt;
				}
			}
			else {
				teamList[tid][i] = null;
			}
		}
	}
	
	public int checkIsInCurrentTeam(int eid) {
		for(int i = 0; i < 6; i++) {
			if(this.teamList[teamId][i] != null) {
				if(teamList[teamId][i].getEntityId() == eid) {
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
	public void clearShipOfCurrentTeam(int id) {
		teamList[teamId][id] = null;
		selectState[teamId][id] = false;
	}
	
	//clear all slot
	public void clearAllTeam() {
		for(int i = 0; i < 6; i++) {
			teamList[teamId][i] = null;
			selectState[teamId][i] = false;
		}
	}
	
	//get all ship UID in a team from entity
	public void updateSID() {
		if(sidList != null && teamList != null) {
			//get ship sid
			for(int i = 0; i < 9; i++) {
				for(int j = 0; j < 6; j++) {
					if(this.teamList[i][j] != null) {
						this.sidList[i][j] = this.teamList[i][j].getShipUID();
					}
					else {
						this.sidList[i][j] = -1;
					}
				}
			}
		}
	}
	
	//get ship entity by SID, called
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


}
