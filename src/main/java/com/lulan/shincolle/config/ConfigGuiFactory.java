package com.lulan.shincolle.config;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

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

	/** 回傳gui config class */
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return ConfigGui.class;
	}

	/** 列出runtime時期才會抓到的東西, 通常用於mod interaction? */
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	/** 自訂config按鈕時才使用此方法, 否則會用預設按鈕 */
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement elem)
	{
		return null;
	}
	

}