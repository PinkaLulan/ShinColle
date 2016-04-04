package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.EntityHelper;

/** Command: /shipattrs
 * 
 *  used on ship entity
 *  change ship attributes
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /shipattrs <ship level> <6 bonus value>
 *      
 *  process:
 *    1. check command sender is OP (server)
 *    2. send sender eid to client (s to c)
 *    3. check sender mouse over target is ship (client)
 *    4. send ship eid to server (c to s)
 *    5. change ship's attributes (server)
 */
public class ShipCmdShipAttrs extends BasicShipCommand {

	//command name list
	private static final List Aliases = new ArrayList() {{
		add("shipattrs");
	}};

	
    public ShipCmdShipAttrs() {   
    }

    /** command name */
	@Override
	public String getCommandName() {
		return "shipattrs";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases() {
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shipattrs <ship level> <level: HP> <level: ATK> <level: DEF> <level: ATK Speed> <level: Move Speed> <level: Hit Range>";
	}

	/** command authority */
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		if(sender instanceof EntityPlayer){
            return true;
	    } 

		return false;
	}
	
	/** parms auto input method */
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] cmd) {
		return null;
	}

	/** set command string[int] is player name */
	@Override
	public boolean isUsernameIndex(String[] cmd, int index) {
		return false;
	}
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void processCommand(ICommandSender sender, String[] cmd) {
		World world = sender.getEntityWorld();
		EntityPlayer op = null;
		int senderEID = -1;
		boolean isOP = false;
		
		if(!world.isRemote) {
			//check sender is player
			if(sender instanceof EntityPlayer) {
				op = (EntityPlayer) sender;
				senderEID = op.getEntityId();
				isOP = EntityHelper.checkOP(op);
				
				//need owner parm
				if(cmd.length != 7) {
					sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
					sender.addChatMessage(new ChatComponentText("Command: ShipAttrs: invalid parameter! (required 7 parms)"));
					return;
				}
				//has owner parm and sender is OP
				else if(isOP) {
					//get data
					int[] data = new int[7];
					for(int i = 0; i < 7; i++) {
						data[i] = parseInt(sender, cmd[i]);
					}
					
					//check data
					if(data[0] <= 0 || data[0] > 150) {
						sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
						sender.addChatMessage(new ChatComponentText("Command: ShipAttrs: invalid ship level! (1~150)"));
						return;
					}
					
					if(data[1] < 0 || data[2] < 0 || data[3] < 0 || data[4] < 0 || data[5] < 0 || data[6] < 0 ||
					   data[1] > 100 || data[2] > 100 || data[3] > 100 || data[4] > 100 || data[5] > 100 || data[6] > 100) {
						sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
						sender.addChatMessage(new ChatComponentText("Command: ShipAttrs: invalid bonus level! (0~100)"));
						return;
					}
					
					//send data to client
					CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdShipAttr, senderEID, data[0], data[1], data[2], data[3], data[4], data[5], data[6]), (EntityPlayerMP) op);
				}//is OP
				else {
					sender.addChatMessage(new ChatComponentText("Command: ShipAttrs: sender is not OP!"));
				}
			}//is player
		}//end server side
	}
  
    
}


