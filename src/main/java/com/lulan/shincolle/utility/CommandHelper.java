package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lulan.shincolle.capability.CapaShipSavedValues;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.server.CacheDataShip;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * helper for commmand process
 */
public class CommandHelper
{

	public CommandHelper() {}
	
	/** command process: ship change owner
	 * 
	 *    1. (done) check command sender is OP (server)
	 *    2. (done) check owner exists (server)
	 *    3. (done) send sender eid to client (s to c)
	 *    4. check sender mouse over target is ship (client)
	 *    5. send ship eid to server (c to s)
	 *    6. change ship's owner UUID and PlayerUID (server)
	 */
	@SideOnly(Side.CLIENT)
	public static void processShipChangeOwner(int senderEID, int ownerEID)
	{
		//get sender entity
		EntityPlayer sender = EntityHelper.getEntityPlayerByID(senderEID, 0, true);
		
		if (sender != null)
		{
			//get sender's mouse over target
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(32D, 1F);
			
			if (hitObj != null && hitObj.entityHit instanceof BasicEntityShip)
			{
				//send change owner packet to server
				sender.sendMessage
				(
					new TextComponentTranslation("chat.shincolle:command.command")
					.appendSibling(new TextComponentString(" shipchangeowner: ship: "+TextFormatting.AQUA+hitObj.entityHit))
				);
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.CmdChOwner, ownerEID, hitObj.entityHit.getEntityId(), hitObj.entityHit.world.provider.getDimension()));
			}//end get target ship
			else
			{
				sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.notship"));
			}
		}
	}
	
	/** command process: show ship info */
	@SideOnly(Side.CLIENT)
	public static void processShowShipInfo(int senderEID)
	{
		//get sender entity
		EntityPlayer sender = EntityHelper.getEntityPlayerByID(senderEID, 0, true);
		
		if (sender != null)
		{
			//get sender's mouse over target
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(32D, 1F);
			
			if (hitObj != null && hitObj.entityHit instanceof BasicEntityShip)
			{
				BasicEntityShip ship = (BasicEntityShip) hitObj.entityHit;
				
				//show ship info
				sender.sendMessage
				(
					new TextComponentTranslation("chat.shincolle:command.command")
					.appendSibling(new TextComponentString(" user: "+TextFormatting.LIGHT_PURPLE+
					sender.getName()+TextFormatting.RESET+
					" UID: "+TextFormatting.AQUA+EntityHelper.getPlayerUID(sender)+TextFormatting.RESET+
					" UUID: "+TextFormatting.GOLD+sender.getUniqueID()))
				);
				sender.sendMessage(new TextComponentString("CustomName: "+TextFormatting.AQUA+ship.getCustomNameTag()));
				sender.sendMessage(new TextComponentString("EntityID: "+TextFormatting.GOLD+ship.getEntityId()));
				sender.sendMessage(new TextComponentString("UID: "+TextFormatting.GREEN+ship.getShipUID()));
				sender.sendMessage(new TextComponentString("Owner UID: "+TextFormatting.RED+ship.getPlayerUID()));
				sender.sendMessage(new TextComponentString("Owner UUID: "+TextFormatting.YELLOW+EntityHelper.getPetPlayerUUID(ship)));
				sender.sendMessage(new TextComponentString("Morale: "+TextFormatting.YELLOW+ship.getMorale()));
			}
		}
	}
	
	/** command process: set ship attrs
	 * 
	 *  cmdData: 0:sender eid, 1:ship level, 2~7:bonus value
	 * 
	 *    1. (done) check command sender is OP (server)
	 *    2. (done) send sender eid to client (s to c)
	 *    3. check sender mouse over target is ship (client)
	 *    4. send ship eid to server (c to s)
	 *    5. change ship's attributes (server)
	 */
	@SideOnly(Side.CLIENT)
	public static void processSetShipAttrs(int[] cmdData)
	{
		//get sender entity
		EntityPlayer sender = EntityHelper.getEntityPlayerByID(cmdData[0], 0, true);
		
		if (sender != null)
		{
			//get sender's mouse over target
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(16D, 1F);
			
			if (hitObj != null && hitObj.entityHit instanceof BasicEntityShip)
			{
				BasicEntityShip ship = (BasicEntityShip) hitObj.entityHit;
				
				if (cmdData.length == 8)
				{
					//show msg
					sender.sendMessage
					(
						new TextComponentTranslation("chat.shincolle:command.command")
						.appendSibling(new TextComponentString(
						" shipattrs: LV: "+TextFormatting.LIGHT_PURPLE+cmdData[1]+TextFormatting.RESET+
						" BonusValue: "+TextFormatting.RED+cmdData[2]+" "+cmdData[3]+" "+cmdData[4]+
						" "+cmdData[5]+" "+cmdData[6]+" "+cmdData[7]))
					);
					sender.sendMessage(new TextComponentString(""+TextFormatting.AQUA+ship));
					
					//send packet to server: entity id, world id, level, bonus 1~6
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.CmdShipAttr,
							ship.getEntityId(), ship.world.provider.getDimension(), cmdData[1], cmdData[2],
							cmdData[3], cmdData[4], cmdData[5], cmdData[6], cmdData[7]));
				}
				else if (cmdData.length == 2)
				{
					//show msg
					sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.command")
							.appendSibling(new TextComponentString(" shipattrs: LV: "+TextFormatting.LIGHT_PURPLE+cmdData[1])));
					sender.sendMessage(new TextComponentString(""+TextFormatting.AQUA+ship));
					
					//send packet to server: entity id, world id, level, bonus 1~6
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.CmdShipAttr,
							ship.getEntityId(), ship.world.provider.getDimension(), cmdData[1]));
				}
				
			}
		}
	}
	
	/** command process: show ship list
	 * 
	 *  cmdData: 0:command type, 1:number
	 * 
	 *    1. (done) check command sender is OP (server)
	 *    2. (done) check page number (server)
	 *    3. send ship list data to client (S2C input packet)
	 *    4. display list (client)
	 */
	public static void processSendShipList(ByteBuf buf, int page)
	{
		HashMap<Integer, CacheDataShip> map = ServerProxy.getAllShipWorldData();
		
		//force update once
		ArrayList<BasicEntityShip> ships = new ArrayList<BasicEntityShip>();
		
		map.forEach((uid, data) ->
		{	//NOTE: dont call updateCache in forEach! (ConcurrentModificationException)
			if (data != null)
			{
				Entity ent = EntityHelper.getEntityByID(data.entityID, data.worldID, false);
				
				if (ent instanceof BasicEntityShip)
				{
					ships.add((BasicEntityShip) ent);
				}
			}
		});
		
		for (BasicEntityShip s : ships)
		{
			s.updateShipCacheDataWithoutNewID();	//force update
		}
		
		//start process packet
		int size = map.size();
		int numPerPage = ConfigHandler.shipNumPerPage;
		int maxPage = (size - 1) / numPerPage + 1;
		
		//if show all ship
		if (page <= 0)
		{
			numPerPage = 30000;
			maxPage = (size - 1) / numPerPage + 1;
			page = 1;
		}
		
		//write total #page
		buf.writeInt(maxPage);
		buf.writeInt(page);
		
		/**
		 * show X ships per page, page start index = 1
		 */
		int check = (page - 1) * numPerPage;
		
		//check sending ship list size by page
		if (page < 1 || page > maxPage)	//out of page
		{
			buf.writeInt(0);
			return;
		}
		else if (page == maxPage)		//last page
		{
			buf.writeInt(size - check);
		}
		else						//within pages
		{
			buf.writeInt(numPerPage);
		}
		
		//send ship data
		int index = 0;
		int div = 0;
		page--;	//turn page 1~N to page 0~N-1
		
		for (Map.Entry<Integer, CacheDataShip> entry : map.entrySet())
		{
			div = index / numPerPage;
			index++;
			
			//get ship within page number
			if (div == page)
			{
				int uid = entry.getKey();
				int pid = 0;
				int level = -1;
				String ownerName = null;
				CacheDataShip data = entry.getValue();
				Entity entity = EntityHelper.getEntityByID(data.entityID, data.worldID, false);
				
				if (data.entityNBT != null)
				{
					NBTTagCompound nbt = (NBTTagCompound) data.entityNBT.getTag(CapaShipSavedValues.SHIP_EXTPROP_NAME);
					
					if (nbt != null)
					{
						//get owner data
						pid = nbt.getInteger("PlayerUID");
						ownerName = nbt.getString("Owner");
						
						//get ship level
						NBTTagCompound nbt2 = (NBTTagCompound) nbt.getTag("Minor");
						
						if (nbt2 != null)
						{
							level = nbt2.getInteger("Level");
						}
					}
				}
				
				//send data
				buf.writeInt(uid);
				buf.writeInt(data.worldID);
				buf.writeInt(data.classID);
				buf.writeInt(data.posX);
				buf.writeInt(data.posY);
				buf.writeInt(data.posZ);
				buf.writeInt(level);
				buf.writeInt(pid);
				buf.writeBoolean(data.isDead);
				buf.writeBoolean(entity != null);
				PacketHelper.sendString(buf, ownerName);
			}
			//not yet reach
			else if (div < page)
			{
				continue;
			}
			//out of page
			else
			{
				break;
			}
		}//end for ship cache map
	}
	
	/** command process: show ship list
	 * 
	 *  cmdData: 0:command type, 1:number
	 * 
	 *    1. (done) check command sender is OP (server)
	 *    2. (done) check page number (server)
	 *    3. (done) send ship list data to client (S2C input packet)
	 *    4. display list (client)
	 *    
	 *  daat1: 0:max page, 1:page number, 2:list size
	 *  data2: i * 7 + 0~6: ship data
	 *  data3: i * 2 + 0~1: ship data
	 *  data4: i: owner name
	 */
	@SideOnly(Side.CLIENT)
	public static void processGetShipList(int[] data1, int[] data2, boolean[] data3, String[] data4)
	{
		EntityPlayer sender = ClientProxy.getClientPlayer();
		
		//display page text
		sender.sendMessage
		(
			new TextComponentTranslation("chat.shincolle:command.command")
			.appendSibling(new TextComponentString(
			TextFormatting.DARK_GREEN + " ship: page ( " +
			TextFormatting.AQUA + data1[1] + TextFormatting.DARK_GREEN + " / " +
			data1[0] + " )"
		)));
		
		//display ship data
		if (data1[2] > 0)
		{
			for (int i = 0; i < data1[2]; i++)
			{
				String shipClassName = I18n.format("entity." + ShipCalc.getEntityToSpawnName(data2[i * 8 + 2]) + ".name");
				
				sender.sendMessage(new TextComponentString
				(
					"  UID: " + TextFormatting.AQUA + data2[i * 8 + 0] + TextFormatting.RESET +
					"  WID: " + TextFormatting.DARK_PURPLE + data2[i * 8 + 1] + TextFormatting.RESET +
					"  D/E: " + TextFormatting.RED + data3[i * 2 + 0] + TextFormatting.RESET +
					"/" + TextFormatting.LIGHT_PURPLE + data3[i * 2 + 1] + TextFormatting.RESET +
					"  Cls: " + TextFormatting.YELLOW + shipClassName + TextFormatting.RESET
				));
				
				sender.sendMessage(new TextComponentString
				(
					"       Pos( " + TextFormatting.GRAY + data2[i * 8 + 3] + " " + data2[i * 8 + 4] +
					" " + data2[i * 8 + 5] + TextFormatting.RESET + " )" +
					"  Lv: " + TextFormatting.GOLD + data2[i * 8 + 6] + TextFormatting.RESET +
					"  Owner: " + TextFormatting.GREEN + data2[i * 8 + 7] + " " + data4[i] + TextFormatting.RESET
				));
			}
		}//end has data
	}
	
	
}
