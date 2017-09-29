package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
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
public class ShipCmdKill extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("shipkill");
	}};

	
    public ShipCmdKill() {}

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
		return "/shipkill <class id> [range]";
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
			if(sender instanceof EntityPlayer)
			{
				op = (EntityPlayer) sender;
				isOP = EntityHelper.checkOP(op);
				
				if (isOP)
				{
					int id = -1;
					int range = 64;
					
					//get parms
					if (args.length == 1)
					{
						id = parseInt(args[0]);
					}
					else if (args.length == 2)
					{
						id = parseInt(args[0]);
						range = parseInt(args[1]);
					}
					
					//check range valid
					if (range <= 0)
					{
						sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.wrongrange").appendText(" ( > 0 )"));
						return;
					}
					
					//check id valid
					if (id >= 2)
					{
						sender.sendMessage
						(
							new TextComponentTranslation("chat.shincolle:command.command")
							.appendSibling(new TextComponentString(" shipkill: CID: "+id+" "+ShipCalc.getEntityToSpawnName(id - 2)
						)));
					}
					else
					{
						sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.wrongcid").appendText(" "+id));
						return;
					}
					
					//kill ship
					//set item entity dead
					AxisAlignedBB aabb = new AxisAlignedBB(op.posX - range, -256D, op.posZ - range, op.posX + range, 512D, op.posZ + range);
					id -= 2;
					
					//kill friendly ship (id < 2000)
					if (id < 2000)
					{
						List<BasicEntityShip> hitent = op.world.getEntitiesWithinAABB(BasicEntityShip.class, aabb);
						
			            for (BasicEntityShip i : hitent)
			            {
			            	if (i.getShipClass() == id)
			            	{
			            		i.setDead();
			            		sender.sendMessage(new TextComponentString("remove "+i));
			            	}
			            }
					}
					//kill mob ship (id >= 2000)
					else
					{
						id -= 2000;
						List<BasicEntityShipHostile> hitent = op.world.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);
						
			            for (BasicEntityShipHostile i : hitent)
			            {
			            	if (i.getShipClass() == id)
			            	{
			            		i.setDead();
			            		sender.sendMessage(new TextComponentString("remove "+i));
			            	}
			            }
					}
				}
			}//is player
		}//end server side
	}
  
    
}