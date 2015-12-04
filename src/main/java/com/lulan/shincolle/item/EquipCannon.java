package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EquipCannon extends BasicEquip {
	
	IIcon[] icons = new IIcon[3];
	
	public EquipCannon() {
		super();
		this.setUnlocalizedName("EquipCannon");
		this.types = 12;	//single = 2, twin = 7, triple = 3
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"0");
		icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"1");
		icons[2] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"2");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		switch(meta) {
		case 0:
		case 1:
			return this.icons[0];	//single cannon
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return this.icons[1];	//twin cannon
		case 9:
		case 10:
		case 11:
			return this.icons[2];	//triple cannon
		default:
			return this.icons[0];
		}
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		int meta = item.getItemDamage();
		
		switch(meta) {
		case 6:		//14 tw
		case 7:		//16 tw
		case 8:		//20 tw
		case 10:	//16 tr
			return true;
		}
		
        return false;
    }
	
	@Override
	public int getItemType() {
		return ID.ItemType.EquipCannon;
	}
	

}
