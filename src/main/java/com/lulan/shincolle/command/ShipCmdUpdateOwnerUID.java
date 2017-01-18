package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/** Command: /shipupdateowneruid
 * 
 *  search all loaded ship entities in all worlds
 *  check the owner UUID is same and change the ship's owner to command sender
 *  
 *  authority: all player or OP only
 *  
 *  type:
 *    1. /shipupdateowneruid
 *      for all player, owner is sender
 *      
 *    2. /shipupdateowneruid "player name"
 *      for OP only, owner is "player name"
 */
public class ShipCmdUpdateOwnerUID extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("shipupdateowneruid");
	}};

	
    public ShipCmdUpdateOwnerUID() {}

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
		return "/shipupdateowneruid [player name (OP only)]";
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
		return cmd.length > 0 && index == 0;
	}
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		EntityPlayer player = null;
		String uuid = null;
		int pid = -1;
		boolean isOP = false;

		if (!world.isRemote)
		{
			/** command type:
			 *  1: command with 0 parm
			 *     used by all player
			 *     owner = sender
			 *  
			 *  2: command with 1 parm
			 *     used by OP
			 *     owner = parm 1
			 */
			//check sender is OP
			if (sender instanceof EntityPlayer)
			{
				player = (EntityPlayer) sender;
				isOP = EntityHelper.checkOP(player);
				
				//type 1 command
				if (args.length == 0)
				{
					sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.command")
						.appendSibling(new TextComponentString(" shipupdateowneruid: get player: "+TextFormatting.AQUA+player)));
					//check all loaded ship's owner uuid
					updateShipOwner(player);
				}
				//type 2 command
				else
				{
					if (isOP)
					{
						player = EntityHelper.getEntityPlayerByName(args[0]);
						
						if (player != null)
						{
							sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.command")
								.appendSibling(new TextComponentString(" shipupdateiwneruid: get player "+TextFormatting.AQUA+player)));
							//check all loaded ship's owner uuid
							updateShipOwner(player);
						}
					}
					else
					{
						sender.sendMessage(new TextComponentTranslation("chat.shincolle:command.notop"));
						return;
					}
				}
			}
		}//end server side
	}
	
	/** update owner, SERVER side only */
	private void updateShipOwner(EntityPlayer player)
	{
		//get player uuid
		String uuid = player.getUniqueID().toString();
		String name = player.getName();
		int pid = EntityHelper.getPlayerUID(player);
		player.sendMessage(new TextComponentTranslation("chat.shincolle:command.command")
				.appendSibling(new TextComponentString(" shipupdateowneruid: owner: "+TextFormatting.AQUA+pid+" "+TextFormatting.LIGHT_PURPLE+uuid)));
		
		if (uuid != null && uuid.length() > 3)
		{
			/** 1. check all loaded ships' owner uuid
			 *  2. change ship's owner uuid
			 */
			World[] worlds = ServerProxy.getServerWorld();

			try
			{
				//check all worlds
				for (World w : worlds)
				{
					//check all loaded ships in the world
					for (Entity ent : w.loadedEntityList)
					{
						if (ent instanceof BasicEntityShip)
						{
							BasicEntityShip ship = (BasicEntityShip) ent;
							
							//check owner uuid
							if (EntityHelper.getPetPlayerUUID(ship).equals(uuid))
							{
								//set new owner uid
								EntityHelper.setPetPlayerUID(pid, ship);
								ship.ownerName = name;
								player.sendMessage(new TextComponentString("get ship: "+TextFormatting.GOLD+ship));
								ship.sendSyncPacketAllValue();
							}
						}
					}//end check entity
				}//end check world
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION: Command: ShipUpdateOwnerUID: change ship's owner fail: "+e);
				e.printStackTrace();
			}
		}
	}
  
    
}