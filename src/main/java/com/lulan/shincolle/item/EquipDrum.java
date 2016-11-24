package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lulan.shincolle.reference.ID;

/**meta:
 *    0: normal drum
 */
public class EquipDrum extends BasicEquip
{
	
	private static final String NAME = "EquipDrum";
	
	
	public EquipDrum()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
	}
	
	public int getSpecialEffect(ItemStack stack)
	{
		return 0;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return ID.EquipType.DRUM_LO;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.DRUM_LO:  //120
			return new int[] {itemRand.nextInt(4) + 5,
			  		  		  itemRand.nextInt(5) + 9,
			  		  		  itemRand.nextInt(4) + 4,
			  		  		  itemRand.nextInt(3) + 3};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{  		
		list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:drum"));
		super.addInformation(itemstack, player, list, par4);
	}
	

}



