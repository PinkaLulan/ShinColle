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
		this.types = 15;	//T=4,F=5,B=4,R=2
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
	    if(meta < 4) {				//0~3 = Torpedo
	    	return this.icons[0];
	    }
	    else if(meta < 9) {			//4~8 = Fighter
	    	return this.icons[1];
	    }
	    else if(meta < 13) {		//9~12 = Bomber
	    	return this.icons[2];
	    }
	    else if(meta < 15) {		//13~14 = Recon
	    	return this.icons[3];
	    }
	   
	    return icons[0];
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
			return true;
		}
		
        return false;
    }

}
