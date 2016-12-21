package com.lulan.shincolle.utility;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
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
	//TODO use local lang string
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
				sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: ship: "+TextFormatting.AQUA+hitObj.entityHit));
				CommonProxy.channelG.sendToServer(new C2SInputPackets(C2SInputPackets.PID.CmdChOwner, ownerEID, hitObj.entityHit.getEntityId(), hitObj.entityHit.world.provider.getDimension()));
			}//end get target ship
			else
			{
				sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: entity is not ship!"));
			}
		}
	}
	
	/** command process: show ship info */
	//TODO use local lang string
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
				sender.sendMessage(new TextComponentString("Command: ShipInfo: User: "+TextFormatting.LIGHT_PURPLE+
						sender.getDisplayName()+TextFormatting.RESET+
						" UID: "+TextFormatting.AQUA+EntityHelper.getPlayerUID(sender)+TextFormatting.RESET+
						" UUID: "+TextFormatting.GOLD+sender.getUniqueID()));
				sender.sendMessage(new TextComponentString("Ship Name: "+TextFormatting.AQUA+ship.getCustomNameTag()));
				sender.sendMessage(new TextComponentString("Ship EntityID: "+TextFormatting.GOLD+ship.getEntityId()));
				sender.sendMessage(new TextComponentString("Ship UID: "+TextFormatting.GREEN+ship.getShipUID()));
				sender.sendMessage(new TextComponentString("Ship Owner UID: "+TextFormatting.RED+ship.getPlayerUID()));
				sender.sendMessage(new TextComponentString("Ship Owner UUID: "+TextFormatting.YELLOW+EntityHelper.getPetPlayerUUID(ship)));
				sender.sendMessage(new TextComponentString("Morale: "+TextFormatting.YELLOW+ship.getStateMinor(ID.M.Morale)));
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
	//TODO use local lang string
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
					sender.sendMessage(new TextComponentString("Command: ShipAttrs: Set ship value: LV: "+
											TextFormatting.LIGHT_PURPLE+cmdData[1]+TextFormatting.RESET+" BonusValue: "+
											TextFormatting.RED+cmdData[2]+" "+cmdData[3]+" "+cmdData[4]+" "+
											cmdData[5]+" "+cmdData[6]+" "+cmdData[7]));
					sender.sendMessage(new TextComponentString("Target Ship: "+TextFormatting.AQUA+ship));
					
					//send packet to server: entity id, world id, level, bonus 1~6
					CommonProxy.channelG.sendToServer(new C2SInputPackets(C2SInputPackets.PID.CmdShipAttr,
							ship.getEntityId(), ship.world.provider.getDimension(), cmdData[1], cmdData[2],
							cmdData[3], cmdData[4], cmdData[5], cmdData[6], cmdData[7]));
				}
				else if (cmdData.length == 2)
				{
					//show msg
					sender.sendMessage(new TextComponentString("Command: ShipAttrs: Set ship value: LV: "+
											TextFormatting.LIGHT_PURPLE+cmdData[1]));
					sender.sendMessage(new TextComponentString("Target Ship: "+TextFormatting.AQUA+ship));
					
					//send packet to server: entity id, world id, level, bonus 1~6
					CommonProxy.channelG.sendToServer(new C2SInputPackets(C2SInputPackets.PID.CmdShipAttr,
							ship.getEntityId(), ship.world.provider.getDimension(), cmdData[1]));
				}
				
			}
		}
	}
	
	
}
