package com.lulan.shincolle.intermod.forestry;

import com.lulan.shincolle.item.BasicItem;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 * 0: abyss comb
 * 
 */
public class ShinComb extends BasicItem
{	
	
	
	private static final String NAME = "ShinComb";
	byte types = 1;
	
	
	public ShinComb()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(false);
        
        GameRegistry.register(this);
	}

	
}