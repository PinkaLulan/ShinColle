package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EquipTurbine extends BasicEquip {
	
	IIcon[] icons = new IIcon[4];
	
	public EquipTurbine() {
		super();
		this.setUnlocalizedName("EquipTurbine");
		this.types = 3;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    return icons[0];
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		int meta = item.getItemDamage();
		
		switch(meta) {
		case 2:		//14 tw
			return true;
		}
		
        return false;
    }
	
	@Override
	public int getItemType() {
		return ID.ItemType.EquipTurbine;
	}


}

