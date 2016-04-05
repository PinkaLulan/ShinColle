package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.utility.EntityHelper;

/** Command: /shipcleardrop
 * 
 *  clear all basic item entity (dropped red grudge entity)
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /shipcleardrop [range]
 *      if no range parm, default range x,z = 128, y = 256
 *      
 *  process:
 *    1. check sender is OP (server)
 *    3. clear item entity
 */
public class ShipCmdShipClearDrop extends BasicShipCommand {

	//command name list
	private static final List Aliases = new ArrayList() {{
		add("shipcleardrop");
	}};

	
    public ShipCmdShipClearDrop() {   
    }

    /** command name */
	@Override
	public String getCommandName() {
		return "shipcleardrop";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases() {
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/shipcleardrop [range]";
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
					int range = 128;
					
					//has parm
					if(cmd.length > 0) {
						range = parseInt(sender, cmd[0]);  //get range
						if(range == 0) range = 128;
					}
					
					//set item entity dead
					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(op.posX - range, op.posY - 256D, op.posZ - range, op.posX + range, op.posY + 256D, op.posZ + range);
					List<BasicEntityItem> hitent = op.worldObj.getEntitiesWithinAABB(BasicEntityItem.class, aabb);
					
					sender.addChatMessage(new ChatComponentText("Command: ShipClearDrop: remove "+hitent.size()+" item entities."));
					
		            for(BasicEntityItem i : hitent) {
		            	i.setDead();
		            }
				}
			}//is player
		}//end server side
	}
  
    
}


