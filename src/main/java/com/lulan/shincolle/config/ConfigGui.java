package com.lulan.shincolle.config;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

/**
 * GUI config
 * tuts: http://jabelarminecraft.blogspot.tw/p/minecraft-modding-configuration-guis.html
 */
public class ConfigGui extends GuiConfig
{

	
	/** this constructor is NECESSARY!! GuiModList::actionPerformed will use this constructor only */
	public ConfigGui(GuiScreen parent)
	{
		this(parent, getAllCategoryList(), Reference.MOD_ID, false, false, "NOW EDITING: shincolle.cfg");
	}
	
	public ConfigGui(GuiScreen parent, List<IConfigElement> configs, String modid, boolean worldRestart, boolean mcRestart, String title)
	{
		super(parent, configs, modid, worldRestart, mcRestart, title,
			"All changes are CLIENT side only, it DO NOT affect SERVER side config file!!");
	}
	
    @Override
    protected void actionPerformed(GuiButton button)
    {
    	super.actionPerformed(button);
    }

	public static List<IConfigElement> getAllCategoryList()
	{
		//create a config element list instance
		ArrayList<IConfigElement> cfgs = new ArrayList<IConfigElement>();
		cfgs.add(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATE_GENERAL)));
		cfgs.add(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATE_SHIP)));
		cfgs.add(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATE_WORLD)));
		cfgs.add(new ConfigElement(ConfigHandler.config.getCategory(ConfigHandler.CATE_INTERMOD)));
	
		return cfgs;
	}
    
    
}