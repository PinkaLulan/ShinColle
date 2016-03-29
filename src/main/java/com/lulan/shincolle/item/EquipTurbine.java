package com.lulan.shincolle.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lulan.shincolle.reference.ID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**meta:
 *    0:  Abyssal Engine
 *    1:  Improved Abyssal Turbine
 *    2:  Enhanced Abyssal Engine
 */
public class EquipTurbine extends BasicEquip {
	
	IIcon[] icons = new IIcon[1];
	
	
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
	
	@Override
	public int getEquipTypeIDFromMeta(int meta) {
		switch(meta) {
		case 0:
		case 1:
			return ID.EquipType.TURBINE_LO;
		case 2:
			return ID.EquipType.TURBINE_HI;
		default:
			return 0;
		}
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		switch(this.getEquipTypeIDFromMeta(item.getItemDamage())) {
		case ID.EquipType.TURBINE_HI:
			return true;
		default:
			return false;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta) {
		switch(this.getEquipTypeIDFromMeta(meta)) {
		case ID.EquipType.TURBINE_LO:  //1400
			return new int[] {itemRand.nextInt(35) + 90,
	  		  		  		  itemRand.nextInt(25) + 80,
	  		  		  		  itemRand.nextInt(15) + 45,
	  		  		  		  itemRand.nextInt(20) + 60};
		case ID.EquipType.TURBINE_HI:  //3200
			return new int[] {itemRand.nextInt(70) + 200,
							  itemRand.nextInt(55) + 170,
							  itemRand.nextInt(25) + 90,
							  itemRand.nextInt(40) + 130};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}


}

