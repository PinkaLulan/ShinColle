package com.lulan.shincolle.client.gui;

import java.util.List;

import com.lulan.shincolle.handler.ConfigurationHandler;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;

public class ModGuiConfig extends GuiConfig {

	public ModGuiConfig(GuiScreen parentScreen) {
		
		//參數依序為: 設定檔父頁面,要設定的參數,mod id,是否需要重啟動地圖,是否需要重啟動MC,標題
		super(parentScreen, 
				new ConfigElement(ConfigurationHandler.configuration.getCategory("ship setting")).getChildElements(),
				Reference.MOD_ID, 
				false,
				false, 
				"Ship Setting");
		// TODO Auto-generated constructor stub
	}

}
