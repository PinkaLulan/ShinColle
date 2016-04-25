package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import com.lulan.shincolle.reference.ID;

/**meta:
 *    0: normal drum
 */
public class EquipSearchlight extends BasicEquip {
	
	IIcon[] icons = new IIcon[1];
	
	
	public EquipSearchlight() {
		super();
		this.setUnlocalizedName("EquipSearchlight");
		this.types = 1;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    return icons[0];
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta) {
		switch(meta) {
		case 0:
			return ID.EquipType.SEARCHLIGHT_LO;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta) {
		switch(this.getEquipTypeIDFromMeta(meta)) {
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
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  		
		list.add(EnumChatFormatting.GRAY + I18n.format("gui.shincolle:searchlight"));
		super.addInformation(itemstack, player, list, par4);
	}
	

}





