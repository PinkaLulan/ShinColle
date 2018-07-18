package com.lulan.shincolle.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.lulan.shincolle.reference.ID;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**meta:
 *    0:  21inch Torpedo Mk.I
 *    1:  21inch Torpedo Mk.II
 *    2:  22inch Torpedo Mk.II
 *    3:  Cuttlefish Torpedo
 *    4:  High-Speed Torpedo
 *    5:  High-speed Abyssal Torpedo Mod.2
 *    6:  Abyssal Ambush Torpedo
 */
public class EquipTorpedo extends BasicEquip implements IShipEffectItem
{
	
	private static final String NAME = "EquipTorpedo";
	
	
	public EquipTorpedo()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
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
			return ID.EquipType.TORPEDO_LO;
		case 3:
		case 4:
		case 5:
		case 6:
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

	@Override
	public Map<Integer, int[]> getEffectOnAttack(int meta)
	{
		return null;
	}

	//specific missile type by meta
	@Override
	public int getMissileType(int meta)
	{
		return -1;
	}
	
	//specific move type by meta
	@Override
	public int getMissileMoveType(int meta)
	{
		return -1;
	}

	@Override
	public int getMissileSpeedLevel(int meta)
	{
		switch (meta)
		{
		case 3:
		case 4:
			return 1;
		case 5:
			return 2;
		case 6:
			return 3;
		default:
			return 0;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
		super.addInformation(stack, world, list, flag);
		
		int level = getMissileSpeedLevel(stack.getMetadata());
		
		if (level != 0)
		{
			list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:equip.torpedospeed", level));
		}
    }
	
	
}