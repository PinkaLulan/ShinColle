package com.lulan.shincolle.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import java.util.Set;

/**
 * GUI config
 * tuts: http://jabelarminecraft.blogspot.tw/p/minecraft-modding-configuration-guis.html
 */
public class ConfigGuiFactory implements IModGuiFactory
{

	
	/** 此方法於minecraft建立instance後執行, 可放置一些初始化的動作 */
	@Override
	public void initialize(Minecraft mc)
	{
	}

	@Override
	public boolean hasConfigGui()
	{
		return false;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen)
	{
		return new ConfigGui(parentScreen);
	}

	/** 列出runtime時期才會抓到的東西, 通常用於mod interaction? */
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
	

}