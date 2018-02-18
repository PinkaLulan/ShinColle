package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.reference.Enums.EnumEquipEffectSP;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0: normal drum
 */
public class EquipCompass extends BasicEquip
{
	
	private static final String NAME = "EquipCompass";
	
	
	public EquipCompass()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
	}
	
	@Override
	public EnumEquipEffectSP getSpecialEffect(ItemStack stack)
	{
		return EnumEquipEffectSP.COMPASS;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return ID.EquipType.COMPASS_LO;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.COMPASS_LO:  //90
			return new int[] {itemRand.nextInt(5) + 5,
			  		  		  itemRand.nextInt(3) + 4,
			  		  		  itemRand.nextInt(2) + 2,
			  		  		  itemRand.nextInt(2) + 2};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{  		
		list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:compass"));
		super.addInformation(itemstack, player, list, par4);
	}
	

}






