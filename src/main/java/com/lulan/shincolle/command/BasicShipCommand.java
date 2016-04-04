package com.lulan.shincolle.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.util.ChatComponentText;

abstract public class BasicShipCommand implements ICommand {

	
	public BasicShipCommand() {
	}
	
	@Override
	public int compareTo(Object obj1) {
		return 0;
	}

	@Override
	abstract public String getCommandName();

	@Override
	abstract public String getCommandUsage(ICommandSender var1);

	@Override
	abstract public List getCommandAliases();

	/** default is SERVER side only */
	@Override
	abstract public void processCommand(ICommandSender var1, String[] str);

	@Override
	abstract public boolean canCommandSenderUseCommand(ICommandSender var1);

	@Override
	abstract public List addTabCompletionOptions(ICommandSender var1, String[] str);

	@Override
	abstract public boolean isUsernameIndex(String[] str, int var1);
	
	/** vanilla method
     *  Returns a List of strings (chosen from the given strings) which the last word in the given string array is a
     *  beginning-match for. (Tab completion).
     */
    public static List getListOfStringsMatchingLastWord(String[] cmd, String ... nameList) {
        String s1 = cmd[cmd.length - 1];
        ArrayList arraylist = new ArrayList();
        String[] astring1 = nameList;
        int i = nameList.length;

        for(int j = 0; j < i; ++j) {
            String s2 = astring1[j];

            if(doesStringStartWith(s1, s2)) {
                arraylist.add(s2);
            }
        }

        return arraylist;
    }
    
    /** vanilla method
     *  Returns true if the given substring is exactly equal to the start of the given string (case insensitive).
     */
    public static boolean doesStringStartWith(String str1, String str2) {
        return str2.regionMatches(true, 0, str1, 0, str1.length());
    }
    
    /** get integer from string */
    public static int parseInt(ICommandSender sender, String str) {
        try {
            return Integer.parseInt(str);
        }
        catch(Exception e) {
        	sender.addChatMessage(new ChatComponentText("Command: parameter is NOT number! "+e));
            return 0;
        }
    }
	
	
}
