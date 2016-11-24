package com.lulan.shincolle.intermod.forestry;

import net.minecraft.util.IIcon;

import com.lulan.shincolle.item.BasicItem;

/**meta:
 * 0: abyss comb
 * 
 */
public class ShinComb extends BasicItem {	
	
	byte types = 1;
	IIcon[] icons = new IIcon[1];
	
	
	public ShinComb() {
		super();
		this.setUnlocalizedName("ShinComb");
		this.setHasSubtypes(false);
	}
	
//	@Override
//	public void getSubItems(Item item, CreativeTabs tab, List list) {
//		for (int i=0; i<types; i++) {
//			list.add(new ItemStack(item, 1, i));
//		}
//	}
//	
//	@Override
//	public void registerIcons(IIconRegister iconRegister) {	
//		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"0");
//		icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"1");
//	}
//	
//	@Override
//	public IIcon getIconFromDamage(int meta) {
//		if(meta >= types) meta = types - 1;
//	    return this.icons[meta];
//	}

	
}

