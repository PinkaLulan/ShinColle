package com.lulan.shincolle.utility;

import java.util.List;

import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.team.TeamData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;

/**
 * helper for owner/team/ally/friendly/hostile checking
 */
public class TeamHelper
{

	
	public TeamHelper() {}
	
	/** get player team data by player UID, SERVER SIDE ONLY */
	public static TeamData getTeamDataByUID(int uid)
	{
		if (uid > 0)
		{
			return ServerProxy.getTeamData(uid);
		}
		
		return null;
	}

	/**check host's owner is EntityPlayer, for mod interact */
	public static boolean checkOwnerIsPlayer(Entity ent)
	{
		Entity getOwner = null;
		
		if (ent != null)
		{
			if (EntityHelper.getPlayerUID(ent) > 0)
			{
				return true;
			}
			else if (ent instanceof IEntityOwnable)
			{
				getOwner = ((IEntityOwnable) ent).getOwner();
				return (getOwner instanceof EntityPlayer);
			}
		}
		
		return false;
	}
	
	/** check friendly fire (false = no damage) */
	public static boolean doFriendlyFire(IShipOwner attacker, Entity target)
	{
		if (attacker != null && target != null)
		{
			int ida = attacker.getPlayerUID();	//attacker's owner id
			int idb = EntityHelper.getPlayerUID(target);
			
			//enable friendly fire
			if (ConfigHandler.friendlyFire)
			{
				//attacker is normal ship or hostile mob ship
				if (ida > 0 || ida < -1)
				{
					//check is same owner
					if (ida == idb) return false;
				}
			}
			//no friendly fire
			else
			{
				//hostile vs hostile ship = no damage
				if (ida < -1 && idb < -1 && ida == idb)
				{
					return false;
				}
				
				//normal ship can NOT hurt player
				if (ida >= -1 && target instanceof EntityPlayer)
				{
					return false;
				}
				
				//no damage to ally ship
				if (checkIsAlly(ida, idb))
				{
					return false;
				}
			}
		}
		
		//default setting: can damage
		return true;
	}
	
	/** check target entity is host's ally, SERVER SIDE ONLY */
	public static boolean checkIsAlly(Entity host, Entity target)
	{
		if (host != null && target != null)
		{
			int hostID = EntityHelper.getPlayerUID(host);
			int tarID = EntityHelper.getPlayerUID(target);
			
			return checkIsAlly(hostID, tarID);
		}
		
		return false;
	}
	
	/** check target entity is host's ally, SERVER SIDE ONLY */
	public static boolean checkIsAlly(int hostPID, int tarPID)
	{
		//mob vs mob
		if (hostPID < -1 && tarPID < -1)
		{
			return true;
		}
		
		//player vs mob
		if ((hostPID < -1 && tarPID > 0) || (hostPID > 0 && tarPID < -1))
		{
			return false;
		}
		
		//player vs player
		if (hostPID > 0 && tarPID > 0)
		{
			//is same owner
			if (hostPID == tarPID) return true;
			
			//not same owner, check team
			TeamData hostTeam = getTeamDataByUID(hostPID);
			TeamData tarTeam = getTeamDataByUID(tarPID);
			
			//host has team
			if (hostTeam != null && tarTeam != null)
			{
				List alist = hostTeam.getTeamAllyList();
				return alist.contains(tarTeam.getTeamID());
			}
		}
		
		return false;
	}
	
	/** check target entity is host's enemy, SERVER SIDE ONLY */
	public static boolean checkIsBanned(Entity host, Entity target)
	{
		if (host != null && target != null)
		{
			int hostID = EntityHelper.getPlayerUID(host);
			int tarID = EntityHelper.getPlayerUID(target);
			
			return checkIsBanned(hostID, tarID);
		}
		
		return false;
	}
	
	/** check target entity is in banned team, SERVER SIDE ONLY */
	public static boolean checkIsBanned(int hostPID, int tarPID)
	{
		//mob vs mob
		if (hostPID < -1 && tarPID < -1)
		{
			return false;
		}
		
		//player vs mob
		if ((hostPID < -1 && tarPID > 0) || (hostPID > 0 && tarPID < -1))
		{
			return true;
		}
		
		//player vs player
		if (hostPID > 0 && tarPID > 0)
		{
			TeamData hostTeam = getTeamDataByUID(hostPID);
			TeamData tarTeam = getTeamDataByUID(tarPID);
			
			//host has team
			if (hostTeam != null && tarTeam != null)
			{
				List alist = hostTeam.getTeamBannedList();
				return alist.contains(tarTeam.getTeamID());
			}
		}
		
		return false;
	}
	
	/** check is same owner for ship (host's owner == target's owner) */
	public static boolean checkSameOwner(Entity enta, Entity entb)
	{
		int ida = EntityHelper.getPlayerUID(enta);
		int idb = EntityHelper.getPlayerUID(entb);

		//ida, idb != 0(other entity) or -1(ownerless ship)
		if ((ida > 0 || ida < -1) && (idb > 0 || idb < -1))
		{
			return (ida == idb);
		}
		
		return false;
	}
	
	
}