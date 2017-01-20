package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0:  Catapult Type F MkII
 *    1:  Catapult Type H
 *    2:  Catapult Type C
 *    3:  Electromagnetic Catapult
 */
public class EquipCatapult extends BasicEquip
{
	
	private static final String NAME = "EquipCatapult";
	
	
	public EquipCatapult()
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
		return 4;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta) {
		case 0:
		case 1:
			return ID.EquipType.CATAPULT_LO;
		case 2:
		case 3:
			return ID.EquipType.CATAPULT_HI;
		default:
			return 0;
		}
	}
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
		switch(this.getEquipTypeIDFromMeta(stack.getMetadata()))
		{
		case ID.EquipType.CATAPULT_LO:
			return 18;
		case ID.EquipType.CATAPULT_HI:
			return 25;
		default:
			return 9;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.CATAPULT_LO:  //2800
			return new int[] {itemRand.nextInt(40) + 120,
	  		  		  		  itemRand.nextInt(50) + 150,
	  		  		  		  itemRand.nextInt(30) + 80,
	  		  		  		  itemRand.nextInt(60) + 180};
		case ID.EquipType.CATAPULT_HI:  //5000
			return new int[] {itemRand.nextInt(70) + 190,
							  itemRand.nextInt(85) + 230,
							  itemRand.nextInt(55) + 150,
							  itemRand.nextInt(90) + 250};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}

