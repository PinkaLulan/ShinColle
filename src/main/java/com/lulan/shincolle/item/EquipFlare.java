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
public class EquipFlare extends BasicEquip
{
	
	private static final String NAME = "EquipFlare";
	
	
	public EquipFlare()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
	}
	
	@Override
	public EnumEquipEffectSP getSpecialEffect(ItemStack stack)
	{
		return EnumEquipEffectSP.FLARE;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return ID.EquipType.FLARE_LO;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.FLARE_LO:  //80
			return new int[] {itemRand.nextInt(2) + 2,
			  		  		  itemRand.nextInt(3) + 3,
			  		  		  itemRand.nextInt(4) + 4,
			  		  		  itemRand.nextInt(2) + 2};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{  		
		list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:flare"));
		super.addInformation(itemstack, player, list, par4);
	}
	

}




