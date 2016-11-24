package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.utility.EntityHelper;

/** Command: /shipkill [class id] [range]
 * 
 *  kill ship with class id and within range for debug
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /shipkill [class id] [range]
 *    2. /shipkill [class id]
 *        kill ship within range
 *      
 *  process:
 *    1. check sender is OP (server)
 *    3. kill ship
 */
public class ShipCmdKill extends BasicShipCommand
{

	//command name list
	private static final List Aliases = new ArrayList()
	{{
		add("shipkill");
	}};

	
    public ShipCmdKill() {}

    /** command name */
	@Override
	public String getCommandName()
	{
		return "shipkill";
	}
	
	/** command alias */
	@Override
	public List getCommandAliases()
	{
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/shipkill <class id> [range]";
	}

	/** command authority */
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		if (sender instanceof EntityPlayer)
		{
            return true;
	    }
		
		return false;
	}
	
	/** parms auto input method */
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] cmd)
	{
		return null;
	}

	/** set command string[int] is player name */
	@Override
	public boolean isUsernameIndex(String[] cmd, int index)
	{
		return false;
	}
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void processCommand(ICommandSender sender, String[] cmd)
	{
		World world = sender.getEntityWorld();
		EntityPlayer op = null;
		int senderEID = -1;
		boolean isOP = false;
		
		if (!world.isRemote)
		{
			//check sender is player
			if(sender instanceof EntityPlayer)
			{
				op = (EntityPlayer) sender;
				isOP = EntityHelper.checkOP(op);
				
				if (isOP)
				{
					int id = -1;
					int range = 64;
					
					//get parms
					if (cmd.length == 1)
					{
						id = parseInt(sender, cmd[0]);
					}
					else if (cmd.length == 2)
					{
						id = parseInt(sender, cmd[0]);
						range = parseInt(sender, cmd[1]);
					}
					
					//check range valid
					if (range <= 0)
					{
						sender.addChatMessage(new ChatComponentText("Command: ShipKill: wrong kill range!"));
						return;
					}
					
					//check id valid
					if (id >= 2)
					{
						sender.addChatMessage(new ChatComponentText("Command: ShipKill: kill class: "+id+" "+ShipCalc.getEntityToSpawnName(id - 2)));
					}
					else
					{
						sender.addChatMessage(new ChatComponentText("Command: ShipKill: wrong class id!"));
						return;
					}
					
					//kill ship
					//set item entity dead
					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(op.posX - range, op.posY - 256D, op.posZ - range, op.posX + range, op.posY + 256D, op.posZ + range);
					List<BasicEntityShip> hitent = op.worldObj.getEntitiesWithinAABB(BasicEntityShip.class, aabb);
					
					id -= 2;
					
		            for (BasicEntityShip i : hitent)
		            {
		            	if (i.getShipClass() == id)
		            	{
		            		i.setDead();
		            		sender.addChatMessage(new ChatComponentText("remove "+i));
		            	}
		            }
				}
			}//is player
		}//end server side
	}
  
    
}




