package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

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
public class ShipCmdShipAttrs extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("shipattrs");
	}};

	
    public ShipCmdShipAttrs() {}

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
		return "/shipattrs <ship level> [<Bonus:HP> <Bonus:ATK> <Bonus:DEF> <Bonus:ATK Speed> <Bonus:Move Speed> <Bonus:Hit Range>]";
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
				senderEID = op.getEntityId();
				isOP = EntityHelper.checkOP(op);
				
				//need owner parm
				if (args.length != 7 && args.length != 1)
				{
					sender.sendMessage(new TextComponentString(getUsage(sender)));
					sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.wrongparanum").appendText(" (1 or 7)"));
					return;
				}
				//has owner parm and sender is OP
				else if (isOP)
				{
					if (args.length == 1)
					{
						//get data
						int level = parseInt(args[0]);
						
						//check data
						if (level <= 0 || level > 150)
						{
							sender.sendMessage(new TextComponentString(getUsage(sender)));
							sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.wrongpararange").appendText(" (LV =  1 ~ 150)"));
							return;
						}
						
						//send data to client
						CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdShipAttr, senderEID, level), (EntityPlayerMP) op);
					}
					else
					{
						//get data
						int[] data = new int[7];
						for (int i = 0; i < 7; i++)
						{
							data[i] = parseInt(args[i]);
						}
						
						//check data
						if (data[0] <= 0 || data[0] > 150)
						{
							sender.sendMessage(new TextComponentString(getUsage(sender)));
							sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.wrongpararange").appendText(" (LV = 1 ~ 150)"));
							return;
						}
						
						if (data[1] < 0 || data[2] < 0 || data[3] < 0 || data[4] < 0 || data[5] < 0 || data[6] < 0 ||
							data[1] > 100 || data[2] > 100 || data[3] > 100 || data[4] > 100 || data[5] > 100 || data[6] > 100)
						{
							sender.sendMessage(new TextComponentString(getUsage(sender)));
							sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.wrongpararange").appendText(" (Bonus = 1 ~ 100)"));
							return;
						}
						
						//send data to client
						CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdShipAttr, senderEID, data[0], data[1], data[2], data[3], data[4], data[5], data[6]), (EntityPlayerMP) op);
					}
				}//is OP
				else
				{
					sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.notop"));
				}
			}//is player
		}//end server side
	}
  
    
}