package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

/** Command: /shipchangeowner
 * 
 *  used on ship entity
 *  change target ship's owner to parms
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /shipchangeowner <player name>
 *      owner is "player name"
 *      
 *  process:
 *    1. check command sender is OP (server)
 *    2. check owner exists (server)
 *    3. send sender eid to client (s to c)
 *    4. check sender mouse over target is ship (client)
 *    5. send ship eid to server (c to s)
 *    6. change ship's owner UUID and PlayerUID (server)
 */
public class ShipCmdChangeShipOwner extends BasicShipCommand {

	//command name list
	private static final List Aliases = new ArrayList() {{
		add("shipchangeowner");
	}};

	
    public ShipCmdChangeShipOwner() {   
    }

    /** command name */
	@Override
	public String getCommandName() {
		return "shipchangeowner";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases() {
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shipchangeowner <player name>";
	}

	/** command authority */
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if(sender instanceof EntityPlayer){
            return true;
	    } 
	    else {
	    	sender.addChatMessage(new ChatComponentText("Command: ShipChangeOwner: sender is not player!"));
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
		return true;
	}
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void processCommand(ICommandSender sender, String[] cmd) {
		World world = sender.getEntityWorld();
		EntityPlayer op = null;
		EntityPlayer owner = null;
		String uuid = null;
		int senderEID = -1;
		int ownerEID = -1;
		boolean isOP = false;
		
		//client
		if(world.isRemote) {
//			LogHelper.info("DEBUG : cmd client "+sender+" "+cmd);
		}
		//server
		else {
			//check sender is player
			if(sender instanceof EntityPlayer) {
				op = (EntityPlayer) sender;
				senderEID = op.getEntityId();
				isOP = EntityHelper.checkOP(op);
				
				//need owner parm
				if(cmd.length < 1) {
					sender.addChatMessage(new ChatComponentText("Command: ShipChangeOwner: player parameter is null!"));
					return;
				}
				//has owner parm and sender is OP
				else if(isOP) {
					//get owner
					owner = EntityHelper.getEntityPlayerByName(cmd[0]);
					
					if(owner != null) {
						ownerEID = owner.getEntityId();
						int pid = EntityHelper.getPlayerUID(owner);
						
						if(pid > 0) {
							sender.addChatMessage(new ChatComponentText("Command: ShipChangeOwner: owner: "+EnumChatFormatting.AQUA+owner+" "+EnumChatFormatting.LIGHT_PURPLE+owner.getUniqueID()));
							//send sender and owner eid to client
							CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdChOwner, senderEID, ownerEID), (EntityPlayerMP) op);
						}//owner pid is legal
						else {
							sender.addChatMessage(new ChatComponentText("Command: ShipChangeOwner: player UID is illegal: "+EnumChatFormatting.AQUA+pid));
						}
					}//get owner
					else {
						sender.addChatMessage(new ChatComponentText("Command: ShipChangeOwner: player not found: "+EnumChatFormatting.AQUA+cmd[0]));
					}
				}//is OP
				else {
					sender.addChatMessage(new ChatComponentText("Command: ShipChangeOwner: sender is not OP!"));
				}
			}//is player
		}//end server side
	}
  
    
}

