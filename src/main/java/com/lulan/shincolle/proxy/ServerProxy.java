package com.lulan.shincolle.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

	@Override
	public void registerKeyBindings() {
		//server端不需要處理key事件		
	}
	
	/*
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

    }

	@Override
    public void init(FMLInitializationEvent e) {
		super.init(e);

    }

	@Override
    public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);

    }
*/
}
