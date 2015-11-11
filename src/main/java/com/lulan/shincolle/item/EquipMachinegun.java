package com.lulan.shincolle.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EquipMachinegun extends BasicEquip {
	
	IIcon[] icons = new IIcon[1];
	
	public EquipMachinegun() {
		super();
		this.setUnlocalizedName("EquipMachinegun");
		this.types = 7;	//machine = 7
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"0");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[0];
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		int meta = item.getItemDamage();
		
		switch(meta) {
		case 5:	//40mm mach
		case 6:	//4ich+CIC
			return true;
		}
		
        return false;
    }

}
