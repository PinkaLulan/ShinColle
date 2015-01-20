package com.lulan.shincolle.proxy;

public interface IProxy {
	//key binding
	public abstract void registerKeyBindings();
	//render
	public abstract void registerRender();
	//packet channel
	public abstract void registerChannel();

}
