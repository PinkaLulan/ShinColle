package com.lulan.shincolle.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.reference.ID;

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
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
	{
		switch(this.getEquipTypeIDFromMeta(item.getItemDamage()))
		{
		case ID.EquipType.TURBINE_HI:
			return true;
		default:
			return false;
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

