package com.lulan.shincolle.handler;

import com.lulan.shincolle.command.ShipCmdEmotes;
import com.lulan.shincolle.command.ShipCmdGetShip;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/** command register class
 * 
 *  guide: http://www.minecraftforge.net/wiki/Server_Command
 */
public class CommandHandler
{

	public CommandHandler() {}
	
	public static void init(FMLServerStartingEvent event)
	{
//		event.registerServerCommand(new ShipCmdChangeShipOwner());
		event.registerServerCommand(new ShipCmdEmotes());
		event.registerServerCommand(new ShipCmdGetShip());
//		event.registerServerCommand(new ShipCmdKill());
//		event.registerServerCommand(new ShipCmdShipAttrs());
//		event.registerServerCommand(new ShipCmdShipInfo());
//		event.registerServerCommand(new ShipCmdShipClearDrop());
//		event.registerServerCommand(new ShipCmdStopAI());
//		event.registerServerCommand(new ShipCmdUpdateOwnerUID());
		
	}
	
	
}
