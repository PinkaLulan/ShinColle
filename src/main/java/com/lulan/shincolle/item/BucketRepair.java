package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class BucketRepair extends BasicItem
{
	
	private static final String NAME = "BucketRepair";
	
	
	public BucketRepair()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(16);
        
        GameRegistry.register(this);
	}
	

}
