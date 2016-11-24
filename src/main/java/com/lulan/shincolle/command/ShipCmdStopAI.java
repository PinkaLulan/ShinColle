package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.utility.EntityHelper;

/** Command: /shipstopai
 * 
 *  stop all ship AI (stop onUpdate, onLivingUpdate) for debug
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /shipstopai
 *      
 *  process:
 *    1. check sender is OP (server)
 *    3. set stop flag
 */
public class ShipCmdStopAI extends BasicShipCommand {

	//command name list
	private static final List Aliases = new ArrayList() {{
		add("shipstopai");
	}};

	
    public ShipCmdStopAI() {   
    }

    /** command name */
	@Override
	public String getCommandName() {
		return "shipstopai";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases() {
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shipstopai";
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
				isOP = EntityHelper.checkOP(op);
				
				if(isOP) {
					BasicEntityShip.stopAI = !BasicEntityShip.stopAI;
					BasicEntityShipHostile.stopAI = !BasicEntityShipHostile.stopAI;
					BasicEntityMount.stopAI = !BasicEntityMount.stopAI;
					sender.addChatMessage(new ChatComponentText("Command: ShipStopAI: "+BasicEntityShip.stopAI));
				}
			}//is player
		}//end server side
	}
  
    
}



