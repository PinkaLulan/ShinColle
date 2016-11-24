package com.lulan.shincolle.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.reference.ID;

/**meta:
 *    0:  3-Inch Single High-Angle Gun Mount
 *    1:  5-Inch Single High-Angle Gun Mount
 *    2:  12.7mm Abyssal Machine Gun
 *    3:  20mm Abyssal Machine Gun
 *    4:  40mm Abyssal Twin Autocannon Mount
 *    5:  40mm Abyssal Quad Autocannon Mount
 *    6:  4-Inch Twin Dual Purpose Gun Mount + CIC
 */
public class EquipMachinegun extends BasicEquip
{
	
	private static final String NAME = "EquipMachinegun";
	
	
	public EquipMachinegun()
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
		return 7;
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
			return ID.EquipType.GUN_LO;
		case 4:
		case 5:
		case 6:
			return ID.EquipType.GUN_HI;
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
		case ID.EquipType.GUN_HI:
			return true;
		}
		
        return false;
    }
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.GUN_LO:  //100
			return new int[] {itemRand.nextInt(3) + 4,
	  		  		  		  itemRand.nextInt(4) + 7,
	  		  		  		  itemRand.nextInt(5) + 8,
	  		  		  		  itemRand.nextInt(2) + 4};
		case ID.EquipType.GUN_HI:  //800
			return new int[] {itemRand.nextInt(20) + 30,
							  itemRand.nextInt(25) + 40,
							  itemRand.nextInt(30) + 50,
							  itemRand.nextInt(15) + 20};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}
