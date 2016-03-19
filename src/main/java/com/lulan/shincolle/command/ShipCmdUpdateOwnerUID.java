package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

/** Command: /shipupdateowneruid
 * 
 *  search all loaded ship entities in all worlds
 *  check the owner UUID is same and change the ship's owner to command sender
 *  
 *  authority: all player or OP only
 *  
 *  type:
 *    1. /shipupdateowneruid
 *      for all player, owner is sender
 *      
 *    2. /shipupdateowneruid "player name"
 *      for OP only, owner is "player name"
 */
public class ShipCmdUpdateOwnerUID extends BasicShipCommand {

	//command name list
	private static final List Aliases = new ArrayList() {{
		add("shipupdateowneruid");
	}};

	
    public ShipCmdUpdateOwnerUID() {   
    }

    /** command name */
	@Override
	public String getCommandName() {
		return "shipupdateowneruid";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases() {
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shipupdateowneruid [player name (OP only)]";
	}

	/** command authority */
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if(sender instanceof EntityPlayer){
            return true;
	    } 
	    else {
	    	sender.addChatMessage(new ChatComponentText("Command: ShipUpdateOwnerUID: sender is not player!"));
	    	return false;
	    }
	}
	
	/** parms auto input method */
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] cmd) {
		return getListOfStringsMatchingLastWord(cmd, MinecraftServer.getServer().getAllUsernames());
	}

	/** set command string[int] is player name */
	@Override
	public boolean isUsernameIndex(String[] cmd, int index) {
		return cmd.length > 0 && index == 0;
	}
	
	/** command process */
	@Override
	public void processCommand(ICommandSender sender, String[] cmd) {
		World world = sender.getEntityWorld();
		EntityPlayer player = null;
		String uuid = null;
		int pid = -1;
		boolean isOP = false;
		
		//client
		if(world.isRemote) {
//			LogHelper.info("DEBUG : cmd client "+sender+" "+cmd);
		}
		//server
		else {
			/** command type:
			 *  1: command with 0 parm
			 *     used by all player
			 *     owner = sender
			 *  
			 *  2: command with 1 parm
			 *     used by OP
			 *     owner = parm 1
			 */
			//check sender is OP
			if(sender instanceof EntityPlayer) {
				player = (EntityPlayer) sender;
				isOP = EntityHelper.checkOP(player);
				
				//type 1 command
				if(cmd.length == 0) {
					sender.addChatMessage(new ChatComponentText("Command: ShipUpdateOwnerUID: get player "+EnumChatFormatting.AQUA+player));
					//check all loaded ship's owner uuid
					updateShipOwner(player);
				}
				//type 2 command
				else {
					if(isOP) {
						player = EntityHelper.getEntityPlayerByName(cmd[0]);
						
						if(player != null) {
							sender.addChatMessage(new ChatComponentText("Command: ShipUpdateOwnerUID: get player "+EnumChatFormatting.AQUA+player));
							//check all loaded ship's owner uuid
							updateShipOwner(player);
						}
					}
					else {
						sender.addChatMessage(new ChatComponentText("Command: ShipUpdateOwnerUID: this command is OP only!"));
						return;
					}
				}
			}
		}//end server side
	}
	
	/** update owner, SERVER side only */
	private void updateShipOwner(EntityPlayer player) {
		//get player uuid
		String uuid = player.getUniqueID().toString();
		int pid = EntityHelper.getPlayerUID(player);
		player.addChatMessage(new ChatComponentText("Command: ShipUpdateOwnerUID: owner: "+EnumChatFormatting.AQUA+pid+" "+EnumChatFormatting.LIGHT_PURPLE+uuid));
		
		if(uuid != null && uuid.length() > 3) {
			/** 1. check all loaded ships' owner uuid
			 *  2. change ship's owner uuid
			 */
			World[] worlds = ServerProxy.getServerWorld();

			try {
				//check all worlds
				for(World w : worlds) {
					//check all ships in the world
					for(Object obj : w.loadedEntityList) {
						if(obj instanceof BasicEntityShip) {
							BasicEntityShip ship = (BasicEntityShip) obj;
							
							//check owner uuid
							if(EntityHelper.getPetPlayerUUID(ship).equals(uuid)) {
								//set new owner uid
								EntityHelper.setPetPlayerUID(pid, ship);
								player.addChatMessage(new ChatComponentText("get ship: "+EnumChatFormatting.GOLD+ship));
								ship.sendSyncPacketAllValue();
							}
						}
					}//end check entity
				}//end check world
			}
			catch(Exception e) {
				LogHelper.info("DEBUG : Command: ShipUpdateOwnerUID: change ship's owner fail: "+e);
			}
		}
	}
  
    
}
