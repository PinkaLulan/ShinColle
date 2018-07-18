package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

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
public class ShipCmdShipClearDrop extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("shipcleardrop");
	}};

	
    public ShipCmdShipClearDrop() {}

    /** command name */
	@Override
	public String getName()
	{
		return Aliases.get(0);
	}
	
	/** command alias */
	@Override
	public List<String> getAliases()
	{
		return this.Aliases;
	}

	/** command guide text */
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/shipcleardrop [range]";
	}

	/** command authority */
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
		if (sender instanceof EntityPlayer)
		{
            return true;
	    }
		
		return false;
	}
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		EntityPlayer op = null;
		int senderEID = -1;
		boolean isOP = false;
		
		if (!world.isRemote)
		{
			//check sender is player
			if (sender instanceof EntityPlayer)
			{
				op = (EntityPlayer) sender;
				isOP = EntityHelper.checkOP(op);
				
				if (isOP)
				{
					int range = 128;
					
					//has parm
					if (args.length > 0)
					{
						range = parseInt(args[0]);  //get range
						if (range == 0) range = 128;
					}
					
					//set item entity dead
					AxisAlignedBB aabb = new AxisAlignedBB(op.posX - range, 0D, op.posZ - range, op.posX + range, 256D, op.posZ + range);
					List<BasicEntityItem> hitent = op.world.getEntitiesWithinAABB(BasicEntityItem.class, aabb);
					
					sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.command")
							.appendSibling(new TextComponentString(" shipcleardrop: remove "+hitent.size()+" item entities.")));
					
		            for (BasicEntityItem i : hitent)
		            {
		            	i.setDead();
		            }
				}
			}//is player
		}//end server side
	}
  
    
}