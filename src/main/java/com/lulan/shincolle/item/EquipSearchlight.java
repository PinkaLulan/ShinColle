package com.lulan.shincolle.item;

import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.reference.Enums.EnumEquipEffectSP;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**meta:
 *    0: normal drum
 */
public class EquipSearchlight extends BasicEquip
{
	
	private static final String NAME = "EquipSearchlight";
	
	
	public EquipSearchlight()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
	}
	
	@Override
	public EnumEquipEffectSP getSpecialEffect(ItemStack stack)
	{
		return EnumEquipEffectSP.SEARCHLIGHT;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return ID.EquipType.SEARCHLIGHT_LO;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.SEARCHLIGHT_LO:  //80
			return new int[] {itemRand.nextInt(4) + 4,
			  		  		  itemRand.nextInt(3) + 3,
			  		  		  itemRand.nextInt(2) + 2,
			  		  		  itemRand.nextInt(2) + 2};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {  		
		list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:searchlight"));
		super.addInformation(stack, world, list, flag);
	}
	

}