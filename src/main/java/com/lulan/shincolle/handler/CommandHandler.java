package com.lulan.shincolle.handler;

import com.lulan.shincolle.command.ShipCmdChangeShipOwner;
import com.lulan.shincolle.command.ShipCmdEmotes;
import com.lulan.shincolle.command.ShipCmdKill;
import com.lulan.shincolle.command.ShipCmdShipAttrs;
import com.lulan.shincolle.command.ShipCmdShipClearDrop;
import com.lulan.shincolle.command.ShipCmdShipInfo;
import com.lulan.shincolle.command.ShipCmdStopAI;
import com.lulan.shincolle.command.ShipCmdUpdateOwnerUID;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

/** command register class
 * 
 *  guide: http://www.minecraftforge.net/wiki/Server_Command
 */
public class CommandHandler {

	public CommandHandler() {}
	
	public static void init(FMLServerStartingEvent event) {
		event.registerServerCommand(new ShipCmdChangeShipOwner());
		event.registerServerCommand(new ShipCmdEmotes());
		event.registerServerCommand(new ShipCmdKill());
		event.registerServerCommand(new ShipCmdShipAttrs());
		event.registerServerCommand(new ShipCmdShipInfo());
		event.registerServerCommand(new ShipCmdShipClearDrop());
		event.registerServerCommand(new ShipCmdStopAI());
		event.registerServerCommand(new ShipCmdUpdateOwnerUID());
		
	}
	
	
}
