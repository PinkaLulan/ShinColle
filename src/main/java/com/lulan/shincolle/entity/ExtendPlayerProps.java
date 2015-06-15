package com.lulan.shincolle.entity;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendPlayerProps implements IExtendedEntityProperties {

	public static final String PLAYER_EXTPROP_NAME = "TeitokuExtProps";
	public EntityPlayer player;
	public World world;
	private boolean hasRing;
	private boolean isRingActive;
	private boolean isRingFlying;
	/** 0:haste 1:speed 2:jump 3:damage*/
	private int[] ringEffect;
	private int marriageNum;
	private int bossCooldown;			//spawn boss cooldown
	private BasicEntityShip[][] teamList;	//上限6格欄位, 離線不需要存, 因為entity id每次登入or伺服器重開都會換
	private boolean[][] selectState;		//ship selected, for command control target
	private int saveId;					//指示目前隊伍存到第幾個, value = 0~5
	private int teamId;					//指示目前顯示的隊伍
	    
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
		this.saveId = 0;
		this.teamId = 0;
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
		
		for(int i = 0; i < 9; i++) {
			nbtExt.setIntArray("TeamList"+i, this.getTeamListID(i));
		}
		
		nbt.setTag(PLAYER_EXTPROP_NAME, nbtExt);
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
		
		for(int i = 0; i < 9; i++) {
			this.setTeamListByID(i, nbtExt.getIntArray("TeamList"+i));
		}
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
	public int[] getTeamListID(int tid) {
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
	
	public BasicEntityShip getTeamList(int id) {
		if(id > 5) id = 0;
		return teamList[teamId][id];
	}
	
	//meta為pointer的item damage
	public BasicEntityShip[] getTeamListWithSelectState(int meta) {	//get selected ship
		BasicEntityShip[] ships = new BasicEntityShip[6];
		
		switch(meta) {
		default:	//single mode
			//return第一個找到的已選擇的ship
			for(int i = 0; i < 6; i++) {
				if(this.getTeamSelected(i)) {
					ships[0] = this.teamList[teamId][i];
					return ships;
				}
			}
			break;
		case 1:		//group mode
			//return所有已選擇的ship
			int j = 0;
			for(int i = 0; i < 6; i++) {
				if(this.getTeamSelected(i)) {
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
	
	public boolean getTeamSelected(int id) {	//get selected state
		if(id > 5) id = 0;
		return selectState[teamId][id];
	}
	
	public int getTeamId() {
		return this.teamId;
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
	public void setTeamSelected(int id, boolean par1) {
		if(id > 5) id = 0;
		selectState[teamId][id] = par1;
	}
	public void setTeamId(int par1) {
		if(par1 > 9) par1 = 0;
		this.teamId = par1;
	}
	
	/**將ship加入隊伍名單
	 * 若ship不為null, 表示要加入名單 -> 找目前非null欄位比對是否同id -> 同id表示remove該entity
	 *                                                  -> 不同id表示可新增entity
	 * 若為null, 表示清空該slot
	 * 若為client端, 表示由sync packet收到資料, 則全部照 傳入值設定
	 */
	public void setTeamList(int id, BasicEntityShip entity, boolean isClient) {
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
				int inTeam = this.checkInTeamList(entity.getEntityId());
				if(inTeam >= 0) {
					this.teamList[teamId][inTeam] = null;
					this.saveId = inTeam;
					this.setTeamSelected(inTeam, false);
					return;
				}
				
				//若無重複entity, 則挑null空位存, id指示為下一個slot
				for(int i = 0; i < 6; i++) {
					if(this.teamList[teamId][i] == null) {
						this.setTeamSelected(i, false);
						teamList[teamId][i] = entity;
						saveId = i + 1;
						if(saveId > 5) saveId = 0;
						return;
					}
				}
				
				//都沒空位, 則挑id指的位置存
				this.setTeamSelected(this.saveId, false);
				this.teamList[teamId][this.saveId] = entity;
				//id++, 且在0~5之間變動
				saveId++;
				if(saveId > 5) saveId = 0;
				return;
			}
			else {
				if(id > 5) id = 0;
				this.setTeamSelected(id, false);
				this.teamList[teamId][id] = null;
				return;
			}
		}//end server side
	}
	
	/**set whole team list (team 0~8, slot 0~5), only called at server side
	 * world id from player world, if player change world
	 * team list will be all clear (cannot find entity)
	 */
	public void setTeamListByID(int tid, int[] eid) {
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
	
	public int checkInTeamList(int eid) {
		for(int i = 0; i < 6; i++) {
			if(this.teamList[teamId][i] != null) {
				if(teamList[teamId][i].getEntityId() == eid) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	public void clearTeamSelected() {			//clear a slot
		selectState[teamId][0] = false;
		selectState[teamId][1] = false;
		selectState[teamId][2] = false;
		selectState[teamId][3] = false;
		selectState[teamId][4] = false;
		selectState[teamId][5] = false;
	}
	
	public void clearShipSlot(int id) {		//clear a slot
		teamList[teamId][id] = null;
		selectState[teamId][id] = false;
	}
	
	public void clearShipTeamAll() {		//clear all slot
		for(int i = 0; i < 6; i++) {
			teamList[teamId][i] = null;
			selectState[teamId][i] = false;
		}
	}


}
