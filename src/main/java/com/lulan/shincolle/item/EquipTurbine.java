package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0:  Abyssal Engine
 *    1:  Improved Abyssal Turbine
 *    2:  Enhanced Abyssal Engine
 */
public class EquipTurbine extends BasicEquip
{
	
	private static final String NAME = "EquipTurbine";
	
	
	public EquipTurbine()
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
		return 3;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
		case 1:
			return ID.EquipType.TURBINE_LO;
		case 2:
			return ID.EquipType.TURBINE_HI;
		default:
			return 0;
		}
	}
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
		switch(this.getEquipTypeIDFromMeta(stack.getMetadata()))
		{
		case ID.EquipType.TURBINE_LO:
			return 18;
		case ID.EquipType.TURBINE_HI:
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
		case ID.EquipType.TURBINE_LO:  //1400
			return new int[] {itemRand.nextInt(35) + 90,
	  		  		  		  itemRand.nextInt(25) + 80,
	  		  		  		  itemRand.nextInt(15) + 45,
	  		  		  		  itemRand.nextInt(20) + 60};
		case ID.EquipType.TURBINE_HI:  //3200
			return new int[] {itemRand.nextInt(70) + 200,
							  itemRand.nextInt(55) + 170,
							  itemRand.nextInt(25) + 90,
							  itemRand.nextInt(40) + 130};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}


}

