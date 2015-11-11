package com.lulan.shincolle.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EquipAirplane extends BasicEquip {
	
	IIcon[] icons = new IIcon[4];
	
	public EquipAirplane() {
		super();
		this.setUnlocalizedName("EquipAirplane");
		this.types = 18;	//T=5,F=6,B=5,R=2
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"0");
		icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"1");
		icons[2] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"2");
		icons[3] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"3");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		switch(meta) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 15:
			return this.icons[0];	//Torpedo
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 16:
			return this.icons[1];	//Fighter
		case 9:
		case 10:
		case 11:
		case 12:
		case 17:
			return this.icons[2];	//Bomber
		case 13:
		case 14:
			return this.icons[3];	//Recon
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
		case 3:		//avenger T
		case 7:		//fly-fish F
		case 8:		//hellcat F
		case 11:	//fly-fish B
		case 12:	//hell B
		case 14:	//fly-fish R
		case 15:	//T kai
		case 16:	//F kai
		case 17:	//B kai
			return true;
		}
		
        return false;
    }

}
