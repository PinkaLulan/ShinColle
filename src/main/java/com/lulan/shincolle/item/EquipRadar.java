package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.reference.ID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0:  Air Radar Mk.I
 *    1:  Air Radar Mk.II
 *    2:  Surface Radar Mk.I
 *    3:  Surface Radar Mk.II
 *    4:  Abyssal Sonar
 *    5:  Abyssal Air Radar
 *    6:  Abyssal Surface Radar
 *    7:  Abyssal Sonar Mk.II
 *    8:  Abyssal FCS + CIC
 */
public class EquipRadar extends BasicEquip
{
	
	private static final String NAME = "EquipRadar";
	
	
	public EquipRadar()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
        
        GameRegistry.register(this);
	}
	
	/** rearrange item order in creative tab */
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		//AA radar
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 5));
		//Surface radar
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
		list.add(new ItemStack(item, 1, 6));
		//sonar
		list.add(new ItemStack(item, 1, 4));
		list.add(new ItemStack(item, 1, 7));
		//other
		list.add(new ItemStack(item, 1, 8));
	}
	
	@Override
	public int getTypes()
	{
		return 9;
	}

	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			return ID.EquipType.RADAR_LO;
		case 5:
		case 6:
		case 7:
		case 8:
			return ID.EquipType.RADAR_HI;
		default:
			return 0;
		}
	}
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
		switch(this.getEquipTypeIDFromMeta(stack.getMetadata()))
		{
		case ID.EquipType.RADAR_LO:
			return 12;
		case ID.EquipType.RADAR_HI:
			return 15;
		default:
			return 9;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.RADAR_LO:  //200
			return new int[] {itemRand.nextInt(7) + 12,
	  		  		  		  itemRand.nextInt(6) + 10,
	  		  		  		  itemRand.nextInt(5) + 9,
	  		  		  		  itemRand.nextInt(4) + 7};
		case ID.EquipType.RADAR_HI:  //2000
			return new int[] {itemRand.nextInt(40) + 110,
							  itemRand.nextInt(35) + 90,
							  itemRand.nextInt(30) + 70,
							  itemRand.nextInt(25) + 50};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}

