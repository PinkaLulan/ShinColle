package com.lulan.shincolle.server;

import com.lulan.shincolle.capability.CapaTeitoku;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class CacheDataPlayer
{

	public int entityID;
	public int worldID;
	public boolean hasTeam;
	public int posX;
	public int posY;
	public int posZ;
	public NBTTagCompound capaNBT;
	
	
	public CacheDataPlayer(EntityPlayer player)
	{
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			this.entityID = player.getEntityId();
			this.worldID = player.world.provider.getDimension();
			this.hasTeam = capa.hasTeam();
			this.posX = (int) player.posX;
			this.posY = (int) player.posY;
			this.posZ = (int) player.posZ;
			this.capaNBT = capa.saveNBTData(new NBTTagCompound());
		}
	}
	
	public CacheDataPlayer(int eid, int wid, boolean hasTeam, double posX, double posY, double posZ, NBTTagCompound nbt)
	{
		this.entityID = eid;
		this.worldID = wid;
		this.hasTeam = hasTeam;
		this.posX = (int) posX;
		this.posY = (int) posY;
		this.posZ = (int) posZ;
		this.capaNBT = nbt;
	}
	
	
}