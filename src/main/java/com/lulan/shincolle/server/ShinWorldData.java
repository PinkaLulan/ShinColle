package com.lulan.shincolle.server;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.WorldSavedData;

import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.NBTHelper;

/**伺服器端資料
 * 儲存player id等, 伺服器端判定用資料
 * 此class用來處理ServerProxy跟MapStorage之間的資料存取動作
 *
 * tut tag: diesieben07, worldsaveddata
 */
public class ShinWorldData extends WorldSavedData {

	public static final String SAVEID = Reference.MOD_ID;
	public static final String TAG_NEXTPLAYERID = "nextPlayerID";
	public static final String TAG_NEXTSHIPID = "nextShipID";
	public static final String TAG_PLAYERDATA = "playerData";
	public static final String TAG_TEAMDATA = "teamData";
	public static final String TAG_PUID = "pUID";
	public static final String TAG_PDATA = "pData";
	public static final String TAG_TUID = "tUID";
	public static final String TAG_TNAME = "tName";
	public static final String TAG_TLNAME = "tLName";
	public static final String TAG_TBAN = "tBan";
	public static final String TAG_TALLY = "tAlly";
	
	//data
	public static NBTTagCompound nbtData;

	
	public ShinWorldData() {
		super(SAVEID);
	}
	
	public ShinWorldData(String saveid) {
		super(saveid);
	}

	/**read server save file
	 * from save file to ServerProxy map
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		//get data
		nbtData = (NBTTagCompound) nbt.copy();
		LogHelper.info("DEBUG : world data: load NBT: "+nbtData.toString());
	}//end read nbt

	/**write server save file
	 * from ServerProxy map to save file
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		/** save common variable */
		nbt.setInteger(TAG_NEXTPLAYERID, ServerProxy.getNextPlayerID());
		nbt.setInteger(TAG_NEXTSHIPID, ServerProxy.getNextShipID());

		//unattackable list
	    List<String> strList = ServerProxy.getUnattackableTargetClassList();
	    
	    if(strList != null) {
	    	NBTTagList tagList = new NBTTagList();
			LogHelper.info("DEBUG : save world data: save unattackable target list: size: "+strList.size());
			
			for(String getc : strList) {
				NBTTagString str = new NBTTagString(getc);
				tagList.appendTag(str);
			}
			
			nbt.setTag(ServerProxy.UNATK_TARGET_CLASS, tagList);
	    }
	    
		/** save player data:  from playerMap to server save file */
		NBTTagList list = new NBTTagList();
		Iterator iter = ServerProxy.getAllPlayerWorldData().entrySet().iterator();
		
	    //save each player data
		while(iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    int uid = (Integer) entry.getKey();
		    int[] data0 = (int[]) entry.getValue();
		    
		    NBTTagCompound save = new NBTTagCompound();
		    save.setInteger(TAG_PUID, uid);
		    save.setIntArray(TAG_PDATA, data0);
		    LogHelper.info("DEBUG : save world data: save id "+uid+" data: "+data0[0]);
		    
		    //save target class list
		    strList = ServerProxy.getPlayerTargetClassList(uid);
			if(strList != null) {
				NBTTagList tagList = new NBTTagList();
				LogHelper.info("DEBUG : save world data: save id "+uid+" target list size: "+strList.size());
				
				for(String getc : strList) {
					NBTTagString str = new NBTTagString(getc);
					tagList.appendTag(str);
				}
				
				save.setTag(ServerProxy.CUSTOM_TARGET_CLASS, tagList);
			}
			
		    list.appendTag(save);	//將save加入到list中, 不檢查是否有重複的tag, 而是新增一個tag
		}
		nbt.setTag(TAG_PLAYERDATA, list);	//將list加入到nbt中
		
		
		/** save team data */
		list = new NBTTagList();
		iter = ServerProxy.getAllTeamWorldData().entrySet().iterator();
		
		while(iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next();
		    int uid = (Integer) entry.getKey();
		    TeamData data = (TeamData) entry.getValue();
		    
		    NBTTagCompound save = new NBTTagCompound();
		    save.setInteger(TAG_TUID, uid);
		    save.setString(TAG_TNAME, data.getTeamName());
		    save.setString(TAG_TLNAME, data.getTeamLeaderName());
		    
		    NBTHelper.saveIntListToNBT(save, TAG_TBAN, data.getTeamBannedList());
		    NBTHelper.saveIntListToNBT(save, TAG_TALLY, data.getTeamAllyList());
		    
		    list.appendTag(save);	//將save加入到list中, 不檢查是否有重複的tag, 而是新增一個tag
		}
		nbt.setTag(TAG_TEAMDATA, list);	//將list加入到nbt中
	}//end write nbt
	
	
}
