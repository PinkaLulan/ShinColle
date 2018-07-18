package com.lulan.shincolle.item;

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
	}
	
	@Override
	public int getTypes()
	{
		return 2;
	}
	
	
}