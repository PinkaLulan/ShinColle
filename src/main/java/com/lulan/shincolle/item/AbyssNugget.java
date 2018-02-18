package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

/** metal nugget
 * 
 */
public class AbyssNugget extends BasicItem
{
	
	private static final String NAME = "AbyssNugget";
	
	
	public AbyssNugget()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
        
        GameRegistry.register(this);
	}
	
	@Override
	public int getTypes()
	{
		return 2;
	}
	
	
}
