package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

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
public class ShipCmdChangeShipOwner extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("shipchangeowner");
		add("shipch");
	}};

	
    public ShipCmdChangeShipOwner() {}

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
		return "/shipchangeowner <player name>";
	}

	/** command authority */
	/** check command permission level */
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
		if (sender instanceof EntityPlayer)
		{
            return true;
	    }
		
        return false;
    }
	
	/** parms auto input method */
	/** parms auto input method */
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
		return getListOfStringsMatchingLastWord(args, ServerProxy.getServer().getOnlinePlayerNames());
    }

	/** set command string[int] is player name */
	@Override
	public boolean isUsernameIndex(String[] cmd, int index)
	{
		return index == 0;
	}
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		EntityPlayer op = null;
		EntityPlayer owner = null;
		String uuid = null;
		int senderEID = -1;
		int ownerEID = -1;
		boolean isOP = false;
		
		if (!world.isRemote)
		{
			//check sender is player
			if (sender instanceof EntityPlayer)
			{
				op = (EntityPlayer) sender;
				senderEID = op.getEntityId();
				isOP = EntityHelper.checkOP(op);
				
				//valid parms
				if (args.length < 1)
				{
					sender.sendMessage(new TextComponentString(getUsage(sender)));
					sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: player parameter is null!"));
					return;
				}
				//has owner parm and sender is OP
				else if (isOP)
				{
					//get owner
					owner = EntityHelper.getEntityPlayerByName(args[0]);
					
					if (owner != null)
					{
						ownerEID = owner.getEntityId();
						int pid = EntityHelper.getPlayerUID(owner);
						
						if (pid > 0)
						{
							sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: owner: "+TextFormatting.AQUA+owner+" "+TextFormatting.LIGHT_PURPLE+owner.getUniqueID()));
							//send sender and owner eid to client
							CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdChOwner, senderEID, ownerEID), (EntityPlayerMP) op);
						}//owner pid is legal
						else
						{
							sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: player UID is illegal: "+TextFormatting.AQUA+pid));
						}
					}//get owner
					else
					{
						sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: player not found: "+TextFormatting.AQUA+args[0]));
					}
				}//is OP
				else
				{
					sender.sendMessage(new TextComponentString("Command: ShipChangeOwner: sender is not OP!"));
				}
			}//is player
		}//end server side
	}
  
    
}