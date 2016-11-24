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

/** Command: /shipinfo
 * 
 *  used on ship entity
 *  change target ship's owner to parms
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /shipinfo
 *      target is mouseover entity
 *      
 *  process:
 *    1. send sender eid to client (s to c)
 *    2. check sender mouse over target is ship (client)
 *    3. show ship info
 */
public class ShipCmdShipInfo extends BasicShipCommand {

	//command name list
	private static final List Aliases = new ArrayList() {{
		add("shipinfo");
	}};

	
    public ShipCmdShipInfo() {   
    }

    /** command name */
	@Override
	public String getCommandName() {
		return "shipinfo";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases() {
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shipinfo";
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
		
		if(!world.isRemote) {
			//check sender is player
			if(sender instanceof EntityPlayer) {
				//send sender and owner eid to client
				CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdShipInfo, ((EntityPlayer) sender).getEntityId()), (EntityPlayerMP) sender);
			}//is player
		}//end server side
	}
  
    
}

