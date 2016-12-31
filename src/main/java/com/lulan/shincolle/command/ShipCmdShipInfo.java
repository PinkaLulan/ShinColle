package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

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
public class ShipCmdShipInfo extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("shipinfo");
	}};

	
    public ShipCmdShipInfo() {}

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
		return "/shipinfo";
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
		
		if (!world.isRemote)
		{
			//check sender is player
			if (sender instanceof EntityPlayer)
			{
				//send sender and owner eid to client
				CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdShipInfo, ((EntityPlayer) sender).getEntityId()), (EntityPlayerMP) sender);
			}//is player
		}//end server side
	}
  
    
}