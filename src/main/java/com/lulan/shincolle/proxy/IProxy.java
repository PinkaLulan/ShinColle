package com.lulan.shincolle.proxy;

public interface IProxy
{
	
	//key binding
	public void registerKeyBindings();
	
	//render
	public void registerRender() throws Exception;
	
	//packet channel
	public void registerChannel();
	
	//capability
	public void registerCapability();


}
