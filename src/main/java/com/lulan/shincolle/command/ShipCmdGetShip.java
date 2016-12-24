package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.proxy.ServerProxy.ShipCacheData;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/** Command: /ship
 * 
 *  show server ship cache: ship uid, eid, dead flag, name, ship type, location
 *  and spawn ship if ship is dead or teleport to commander
 *  
 *  authority: OP only
 *  
 *  type:
 *    1. /ship list [page num]
 *      show ship list
 *      
 *    2. /ship get <uid>
 *      spawn or teleport ship
 *      commander must go to the ship location/world within 64 blocks to load the chunk
 *      
 *    3. /ship del <uid>
 *      delete ship in cache
 *      ship will be added to cache after few ticks if ship existed in the world
 *      or on ship dead/load event
 *      
 *  process:
 *    1. check command sender is OP (server)
 *    
 *    for /ship <get|del>:
 *    2. check uid exists (server)
 *    3. get or create ship entity (server)
 *    4. teleport or spawn ship entity (server)
 *    
 *    for /ship list:
 *    2. check page number (server)
 *    3. send ship list data to client (S2C input packet)
 *    4. display list (client)
 *
 */
public class ShipCmdGetShip extends CommandBase
{

	//command name list
	private static final ArrayList<String> Aliases = new ArrayList()
	{{
		add("ship");
	}};
	
	//emotes name array
	public static final String[] StrArg1 = new String[]
	{
		"list", "get", "del"
	};
	
	public ShipCmdGetShip () {}
	
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

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/ship <list|get|del> <page number|ship UID>";
	}
	
	/** check command permission level */
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }
	
	/** parms auto input method */
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
		if (args != null && args.length == 1) return getListOfStringsMatchingLastWord(args, StrArg1);
		return Collections.<String>emptyList();
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		World world = sender.getEntityWorld();
		
		if (args.length > 0 && sender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) sender;
			
			//check player is OP
			if (!EntityHelper.checkOP(player)) return;
			
			//check command type
			int cmdType = -1;
			
			for (int i = 0; i < StrArg1.length; i++)
			{
				if (args[0].equals(StrArg1[i]))
				{
					cmdType = i;
					break;
				}
			}
			
			//apply command
			switch (cmdType)
			{
			case 0:	//list
			{
				//get page number
				int showPage = 1;
				
				if (args.length > 1)
				{
					showPage = parseInt(args[1]);
				}
				
				//show list by page number
				CommonProxy.channelG.sendTo(new S2CInputPackets(S2CInputPackets.PID.CmdShipList, 0, showPage), (EntityPlayerMP) sender);
			}
			break;
			case 1:	//get
			{
				//get ship data
				int uid = parseInt(args[1]);
				if (uid <= 0) return;
				
				ShipCacheData data = ServerProxy.getShipWorldData(uid);
				
				if (data != null)
				{
					if (data.worldID != sender.getEntityWorld().provider.getDimension())
					{
						sender.sendMessage(new TextComponentString
						(
							TextFormatting.RED + "Command: Ship: Wrong world!"
						));
						return;
					}
					
					//check dist
					Vec3d pos1 = sender.getPositionVector();
					float dx = (float) pos1.xCoord - data.posX;
					float dy = (float) pos1.yCoord - data.posY;
					float dz = (float) pos1.zCoord - data.posZ;
					float dist = dx * dx + dy * dy + dz * dz;
					
					if (dist < 4096F)
					{
						//check entity
						Entity ent = EntityHelper.getEntityByID(data.entityID, data.worldID, false);
						
						//get ship
						if (ent instanceof BasicEntityShip && !ent.isDead)
						{
							BasicEntityShip ship = (BasicEntityShip) ent;
							
							//dismount
							ship.dismountRidingEntity();
							//clear attack target
							ship.setAttackTarget(null);
							ship.setEntityTarget(null);
							//clear move
							ship.motionX = 0D;
							ship.motionY = 0D;
							ship.motionZ = 0D;
							//teleport ship
							ship.setPosition(pos1.xCoord, pos1.yCoord + 0.5D, pos1.zCoord);
							//set guard pos
							ship.setSitting(false);
							ship.setGuardedEntity(null);
							ship.setGuardedPos((int)pos1.xCoord, (int)(pos1.yCoord + 0.5D), (int)pos1.zCoord, ship.world.provider.getDimension(), 1);
							ship.setStateFlag(ID.F.CanFollow, false);
							//update ship cache
							ship.updateShipCacheDataWithoutNewID();
							ship.sendSyncPacketAllValue();
							//send text
							sender.sendMessage(new TextComponentString
							(
								TextFormatting.GREEN + "Command: Ship: Get ship: " + uid
							));
							return;
						}
						//entity not exist
						else
						{
							try
							{
								String name = ShipCalc.getEntityToSpawnName(data.classID);
								
								//create new ship entity
								if (EntityList.NAME_TO_CLASS.containsKey(name))
					            {
									BasicEntityShip ship = (BasicEntityShip) EntityList.createEntityByName(name, player.world);
					                
									if (ship != null)
									{
										//read entity nbt data
										ship.readFromNBT(data.entityNBT);
										//set alive
										ship.setHealth(ship.getMaxHealth());
										ship.isDead = false;
										ship.deathTime = 0;
										//clear motion
										ship.motionX = 0D;
										ship.motionY = 0D;
										ship.motionZ = 0D;
						                //set pos
										ship.setPosition(pos1.xCoord, pos1.yCoord + 0.5D, pos1.zCoord);
						                //spawn entity
						                player.world.spawnEntity(ship);
						                //update ship cache
										ship.updateShipCacheDataWithoutNewID();
						                //send text
										sender.sendMessage(new TextComponentString
										(
											TextFormatting.GREEN + "Command: Ship: Spawn ship: " + uid
										));
										return;
									}
					            }
								
								//spawn fail
								throw new Exception();
							}
							catch (Exception e)
							{
				            	sender.sendMessage(new TextComponentString
								(
									TextFormatting.RED + "Command: Ship: Spawn ship fail: Unknown ship class ID."
								));
								return;
							}
						}
					}
					//too far away
					else
					{
						sender.sendMessage(new TextComponentString
						(
							TextFormatting.RED + "Command: Ship: Ship is too far away! ( > 64 blocks )"
						));
						return;
					}
				}
				
				//uid not exist
				sender.sendMessage(new TextComponentString
				(
					TextFormatting.RED + "Command: Ship: UID does not exist!"
				));
			}
			break;
			case 2:	//del
			{
				//get ship data
				int uid = parseInt(args[1]);
				if (uid <= 0) return;
				
				ShipCacheData data = ServerProxy.getShipWorldData(uid);
				
				//data exist
				if (data != null)
				{
					//delete data
					ServerProxy.getAllShipWorldData().remove(uid);
					
					sender.sendMessage(new TextComponentString
					(
						TextFormatting.GREEN + "Command: Ship: Delete UID: " + uid
					));
					return;
				}
				
				//uid not exist
				sender.sendMessage(new TextComponentString
				(
					TextFormatting.RED + "Command: Ship: UID does not exist!"
				));
			}
			break;
			}//end switch
		}
		
	}
	
	
}