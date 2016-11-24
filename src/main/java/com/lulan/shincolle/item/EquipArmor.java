package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lulan.shincolle.reference.ID;

/**meta:
 *    0: armor
 *    1: heavy armor
 */
public class EquipArmor extends BasicEquip
{
	
	private static final String NAME = "EquipArmor";
	
	
	public EquipArmor()
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
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return ID.EquipType.ARMOR_LO;
		case 1:
			return ID.EquipType.ARMOR_HI;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.ARMOR_LO:  //80
			return new int[] {itemRand.nextInt(3) + 3,
			  		  		  itemRand.nextInt(4) + 4,
			  		  		  itemRand.nextInt(2) + 2,
			  		  		  itemRand.nextInt(2) + 2};
		case ID.EquipType.ARMOR_HI:  //500
			return new int[] {itemRand.nextInt(15) + 35,
							  itemRand.nextInt(20) + 45,
							  itemRand.nextInt(10) + 25,
							  itemRand.nextInt(5) + 15};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}


