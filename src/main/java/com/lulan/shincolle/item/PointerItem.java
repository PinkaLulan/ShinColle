package com.lulan.shincolle.item;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PointerItem extends BasicItem {

	public PointerItem() {
		super();
		this.setUnlocalizedName("PointerItem");
		this.maxStackSize = 1;
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
        return true;
    }
	
}
