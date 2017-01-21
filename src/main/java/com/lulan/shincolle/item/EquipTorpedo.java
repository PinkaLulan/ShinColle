package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0:  21inch Torpedo Mk.I
 *    1:  21inch Torpedo Mk.II
 *    2:  22inch Torpedo Mk.II
 *    3:  Cuttlefish Torpedo
 *    4:  High-Speed Torpedo
 *    5:  High-speed Abyssal Torpedo Mod.2
 */
public class EquipTorpedo extends BasicEquip
{
	
	private static final String NAME = "EquipTorpedo";
	
	
	public EquipTorpedo()
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
		return 6;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
		case 1:
		case 2:
			return ID.EquipType.TORPEDO_LO;
		case 3:
		case 4:
		case 5:
			return ID.EquipType.TORPEDO_HI;
		default:
			return 0;
		}
	}
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
		switch (this.getEquipTypeIDFromMeta(stack.getMetadata()))
		{
		case ID.EquipType.TORPEDO_LO:
			return 16;
		case ID.EquipType.TORPEDO_HI:
			return 22;
		default:
			return 9;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.TORPEDO_LO:  //160
			return new int[] {itemRand.nextInt(4) + 8,
	  		  		  		  itemRand.nextInt(5) + 8,
	  		  		  		  itemRand.nextInt(6) + 12,
	  		  		  		  itemRand.nextInt(4) + 5};
		case ID.EquipType.TORPEDO_HI:  //1200
			return new int[] {itemRand.nextInt(20) + 60,
							  itemRand.nextInt(25) + 70,
							  itemRand.nextInt(30) + 80,
							  itemRand.nextInt(15) + 45};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}

