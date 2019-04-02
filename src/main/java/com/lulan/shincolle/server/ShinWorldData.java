package com.lulan.shincolle.server;

import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;

/**伺服器端資料
 * 儲存player id等, 伺服器端判定用資料
 * 此class用來處理ServerProxy跟MapStorage之間的資料存取動作
 *
 * tut tag: diesieben07, worldsaveddata
 */
public class ShinWorldData extends WorldSavedData
{

	public static final String SAVEID = Reference.MOD_ID;
	
	//global data tag name
	public static final String TAG_NEXTPLAYERID = "nextPlayerID";
	public static final String TAG_NEXTSHIPID = "nextShipID";
	public static final String TAG_PLAYERDATA = "playerData";
	public static final String TAG_TEAMDATA = "teamData";
	public static final String TAG_SHIPDATA = "shipData";
	
	//team data tag name
	public static final String TAG_PUID = "pUID";
	public static final String TAG_TUID = "tUID";
	public static final String TAG_TNAME = "tName";
	public static final String TAG_TLNAME = "tLName";
	public static final String TAG_TBAN = "tBan";
	public static final String TAG_TALLY = "tAlly";
	
	//ship data tag name
	public static final String TAG_ShipUID = "sUID";
	public static final String TAG_ShipEID = "sEID";
	public static final String TAG_ShipWID = "sWID";
	public static final String TAG_ShipCID = "sCID";
	public static final String TAG_ShipPOS = "sPOS";
	public static final String TAG_ShipDead = "sDead";
	public static final String TAG_ShipNBT = "sNBT";
	
	//data
	public static NBTTagCompound nbtData;

	
	public ShinWorldData()
	{
		super(SAVEID);
	}
	
	public ShinWorldData(String saveid)
	{
		super(saveid);
	}

	/**read server save file
	 * from save file to ServerProxy map
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		LogHelper.debug("DEBUG: load world data from disk.");
		
		nbtData = (NBTTagCompound) nbt.copy();
	}

	/**write server save file
	 * from ServerProxy map to save file
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		LogHelper.debug("DEBUG: save world data to disk.");
		
		/** save common variable */
		nbt.setInteger(TAG_NEXTPLAYERID, ServerProxy.getNextPlayerID());
		nbt.setInteger(TAG_NEXTSHIPID, ServerProxy.getNextShipID());
		
		/** save unattackable list */
	    HashMap<Integer, String> map1 = ServerProxy.getUnattackableTargetClass();
	    
	    if (map1 != null)
	    {
	    	NBTTagList tagList = new NBTTagList();
			LogHelper.debug("DEBUG: save world data: save unattackable target list: size: "+map1.size());
			
			map1.forEach((key, str) ->
			{
				tagList.appendTag(new NBTTagString(str));
			});
			
			nbt.setTag(ServerProxy.UNATK_TARGET_CLASS, tagList);
	    }
	    
		/** save player custom target data */
		final NBTTagList list2 = new NBTTagList();
		HashMap<Integer, HashMap<Integer, String>> map2 = ServerProxy.getAllPlayerTargetClassList();
		
		if (map2 != null)
		{
			map2.forEach((uid, data) ->
			{
			    LogHelper.debug("DEBUG: save world data: save player custom target: uid: "+uid+" size: "+data.size());
			    NBTTagCompound tag = new NBTTagCompound();
			    tag.setInteger(TAG_PUID, uid);
			    
			    //save custom target class list
				NBTTagList tagList = new NBTTagList();
				
				data.forEach((key, str) ->
				{
					tagList.appendTag(new NBTTagString(str));
				});
				
				tag.setTag(ServerProxy.CUSTOM_TARGET_CLASS, tagList);
				
			    list2.appendTag(tag);	//將save加入到list中, 不檢查是否有重複的tag, 而是新增一個tag
			});
		}
		nbt.setTag(TAG_PLAYERDATA, list2);	//將list加入到nbt中
		
		
		/** save team data */
		final NBTTagList list3 = new NBTTagList();
		HashMap<Integer, TeamData> map3 = ServerProxy.getAllTeamWorldData();
		
		if (map3 != null)
		{
			map3.forEach((uid, tdata) ->
			{
				LogHelper.debug("DEBUG: save world data: save team: tid: "+uid);
				
			    NBTTagCompound tag = new NBTTagCompound();
			    tag.setInteger(TAG_TUID, uid);
			    tag.setString(TAG_TNAME, tdata.getTeamName());
			    tag.setString(TAG_TLNAME, tdata.getTeamLeaderName());
			    
			    NBTHelper.saveIntListToNBT(tag, TAG_TBAN, tdata.getTeamBannedList());
			    NBTHelper.saveIntListToNBT(tag, TAG_TALLY, tdata.getTeamAllyList());
			    
			    list3.appendTag(tag);	//將save加入到list中, 不檢查是否有重複的tag, 而是新增一個tag
			});
		}
		nbt.setTag(TAG_TEAMDATA, list3);	//將list加入到nbt中
		
		
		/** save ship data */
		final NBTTagList list4 = new NBTTagList();
		HashMap<Integer, CacheDataShip> map4 = ServerProxy.getAllShipWorldData();
		
		if (map4 != null)
		{
			map4.forEach((uid, sdata) ->
			{
				LogHelper.debug("DEBUG: save world data: save ship: sid: "+uid+" cid: "+sdata.classID);
				
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger(TAG_ShipUID, uid);
				tag.setInteger(TAG_ShipEID, sdata.entityID);
				tag.setInteger(TAG_ShipWID, sdata.worldID);
				tag.setInteger(TAG_ShipCID, sdata.classID);
				tag.setBoolean(TAG_ShipDead, sdata.isDead);
				tag.setIntArray(TAG_ShipPOS, new int[] {sdata.posX, sdata.posY, sdata.posZ});
				tag.setTag(TAG_ShipNBT, sdata.entityNBT);
				
				list4.appendTag(tag);
			});
		}
		nbt.setTag(TAG_SHIPDATA, list4);	//將list加入到nbt中
		
		return nbt;
	}//end write nbt
	
	
}