package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/** Command: /shipemotes
 * 
 *  show emotes
 *  
 *  authority: all player
 *  
 *  type:
 *    1. /shipemotes [emotes]
 *      if no parm, show random emotes
 *      
 */
public class ShipCmdEmotes extends CommandBase
{

	//command name list
	private static final List Aliases = new ArrayList()
	{{
		add("em");
		add("emo");
		add("emote");
		add("emotes");
	}};
	
	//emotes name array
	public static final String[] EmoNameArray = new String[]
	{
		"0", "swt", "drop",
		"1", "lv", "love", "heart",
		"2", "swt2", "wah", "panic",
		"3", "?",
		"4", "!",
		"5", "...",
		"6", "an", "anger", "angry",
		"7", "note", "ho",
		"8", "sob", "cry", "sad",
		"9", "spit", "rice", "hungry",
		"10", "spin", "dizzy",
		"11", "find", "??",
		"12", "omg", "shock",
		"13", "ok", "nod",
		"14", "fsh", "flash", "+_+",
		"15", "kiss", "kis",
		"16", "lol", "ha", "heh",
		"17", "gg", "giggle",
		"18", "sigh",
		"19", "meh", "lick",
		"20", "orz", "otl",
		"21", "o", "oh", "yes",
		"22", "x", "no",
		"23", "!?", "surprised",
		"24", "rock", "bawi",
		"25", "paper", "bo",
		"26", "scissors", "gawi", "ya", "yeah",
		"27", "-w-",
		"28", "-o-",
		"29", "blink", "wink",
		"30", "pif",
		"31", "shy", "shine",
		"32", "hmm",
		"33", ":p",
		"34", "lll"
	};
	
	//emotes name to emoID map <name hash code, emotes ID>
	public static final Map<Integer, Integer> EmoNameToID = new HashMap<Integer, Integer>()
	{{
		put("0".hashCode(), 0);put("swt".hashCode(), 0);put("drop".hashCode(), 0);
		put("1".hashCode(), 1);put("lv".hashCode(), 1);put("love".hashCode(), 1);put("heart".hashCode(), 1);
		put("2".hashCode(), 2);put("swt2".hashCode(), 2);put("wah".hashCode(), 2);put("panic".hashCode(), 2);
		put("3".hashCode(), 3);put("?".hashCode(), 3);
		put("4".hashCode(), 4);put("!".hashCode(), 4);
		put("5".hashCode(), 5);put("...".hashCode(), 5);
		put("6".hashCode(), 6);put("an".hashCode(), 6);put("anger".hashCode(), 6);put("angry".hashCode(), 6);
		put("7".hashCode(), 7);put("note".hashCode(), 7);put("ho".hashCode(), 7);
		put("8".hashCode(), 8);put("sob".hashCode(), 8);put("cry".hashCode(), 8);put("sad".hashCode(), 8);
		put("9".hashCode(), 9);put("spit".hashCode(), 9);put("rice".hashCode(), 9);put("hungry".hashCode(), 9);
		put("10".hashCode(), 10);put("spin".hashCode(), 10);put("dizzy".hashCode(), 10);
		put("11".hashCode(), 11);put("find".hashCode(), 11);put("??".hashCode(), 11);
		put("12".hashCode(), 12);put("omg".hashCode(), 12);put("shock".hashCode(), 12);
		put("13".hashCode(), 13);put("ok".hashCode(), 13);put("nod".hashCode(), 13);
		put("14".hashCode(), 14);put("fsh".hashCode(), 14);put("flash".hashCode(), 14);put("+_+".hashCode(), 14);
		put("15".hashCode(), 15);put("kiss".hashCode(), 15);put("kis".hashCode(), 15);
		put("16".hashCode(), 16);put("lol".hashCode(), 16);put("ha".hashCode(), 16);put("heh".hashCode(), 16);
		put("17".hashCode(), 17);put("gg".hashCode(), 17);put("giggle".hashCode(), 17);
		put("18".hashCode(), 18);put("sigh".hashCode(), 18);
		put("19".hashCode(), 19);put("meh".hashCode(), 19);put("lick".hashCode(), 19);
		put("20".hashCode(), 20);put("orz".hashCode(), 20);put("otl".hashCode(), 20);
		put("21".hashCode(), 21);put("o".hashCode(), 21);put("oh".hashCode(), 21);put("yes".hashCode(), 21);
		put("22".hashCode(), 22);put("x".hashCode(), 22);put("no".hashCode(), 22);
		put("23".hashCode(), 23);put("!?".hashCode(), 23);put("surprised".hashCode(), 23);
		put("24".hashCode(), 24);put("rock".hashCode(), 24);put("bawi".hashCode(), 24);
		put("25".hashCode(), 25);put("paper".hashCode(), 25);put("bo".hashCode(), 25);
		put("26".hashCode(), 26);put("scissors".hashCode(), 26);put("gawi".hashCode(), 26);put("ya".hashCode(), 26);put("yeah".hashCode(), 26);
		put("27".hashCode(), 27);put("-w-".hashCode(), 27);
		put("28".hashCode(), 28);put("-o-".hashCode(), 28);
		put("29".hashCode(), 29);put("blink".hashCode(), 29);put("wink".hashCode(), 29);
		put("30".hashCode(), 30);put("pif".hashCode(), 30);
		put("31".hashCode(), 31);put("shine".hashCode(), 31);put("shy".hashCode(), 31);
		put("32".hashCode(), 32);put("hmm".hashCode(), 32);
		put("33".hashCode(), 33);put(":p".hashCode(), 33);
		put("34".hashCode(), 34);put("lll".hashCode(), 34);
	}};
	
	
    public ShipCmdEmotes() {}

    /** command name */
	@Override
	public String getName()
	{
		return "shipemotes";
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
		String cmdhelp = "/shipemotes [";
		
		for (String s : EmoNameArray)
		{
			cmdhelp += s + ", ";
		}
		
		cmdhelp += "]";
		
		return cmdhelp;
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
        return getListOfStringsMatchingLastWord(args, EmoNameArray);
    }
	
	/** command process, SERVER SIDE ONLY */
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] cmd) throws CommandException
	{
		World world = sender.getEntityWorld();
		
		//get emotes id
		int emo = 0;
		
		//check emotes name
		if (cmd != null && cmd.length > 0)
		{
			emo = nameToEmoID(cmd[0]);
		}
		//random emotes
		else
		{
			emo = world.rand.nextInt(30);
		}
		
		//get position
		float px = 0F;
		float py = 0F;
		float pz = 0F;
		float height = 1F;
		int entityType = 0;  //0:none, 1:EntityLivingBase, 2:block
		
		//tweak emote height
		//sender is entity
		if (sender instanceof EntityLivingBase)
		{
			EntityLivingBase host = (EntityLivingBase) sender;
			
			height = host.height * 0.25F;
			entityType = 1;
			
			px = (float) host.posX;
			py = (float) host.posY;
			pz = (float) host.posZ;
			
			if (sender instanceof EntityPlayer)
			{
				height = host.height * 0.65F;
			}
		}
		//sender is block (command block)
		else if (sender instanceof CommandBlockBaseLogic)
		{
			height = 0.5F;
			entityType = 2;
			
			px = (float) sender.getPosition().getX() + 0.5F;
			py = (float) sender.getPosition().getY();
			pz = (float) sender.getPosition().getZ() + 0.5F;
		}
		//other sender
		else
		{
			height = 0.5F;
			entityType = 0;
			
			px = (float) sender.getPositionVector().xCoord;
			py = (float) sender.getPositionVector().yCoord;
			pz = (float) sender.getPositionVector().zCoord;
		}

		if (sender instanceof Entity)
		{
			TargetPoint point = new TargetPoint(world.provider.getDimension(), px, py, pz, 64D);
	      	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle((Entity) sender, 36, height, entityType, emo), point);
		}
		else
		{
			TargetPoint point = new TargetPoint(world.provider.getDimension(), px, py, pz, 64D);
	      	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(36, px, py, pz, height, entityType, emo), point);
		}
	}
	
	/** string to emotes ID */
	public static int nameToEmoID(String str)
	{
		try
		{
			if (str != null)
			{
				return EmoNameToID.get(str.hashCode());
			}
		}
		catch (Exception e)
		{
			return 0;
		}
		
		return 0;
	}

	
}