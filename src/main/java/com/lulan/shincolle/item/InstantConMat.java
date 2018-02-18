package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class InstantConMat extends BasicItem
{
	
	private static final String NAME = "InstantConMat";
	
	public InstantConMat()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
	}


}
