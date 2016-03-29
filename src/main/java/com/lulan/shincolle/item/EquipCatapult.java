package com.lulan.shincolle.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lulan.shincolle.reference.ID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**meta:
 *    0:  Catapult Type F MkII
 *    1:  Catapult Type H
 *    2:  Catapult Type C
 *    3:  Electromagnetic Catapult
 */
public class EquipCatapult extends BasicEquip {
	
	IIcon[] icons = new IIcon[1];
	
	
	public EquipCatapult() {
		super();
		this.setUnlocalizedName("EquipCatapult");
		this.types = 4;
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
			return ID.EquipType.CATAPULT_LO;
		case 2:
		case 3:
			return ID.EquipType.CATAPULT_HI;
		default:
			return 0;
		}
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		switch(this.getEquipTypeIDFromMeta(item.getItemDamage())) {
		case ID.EquipType.CATAPULT_HI:
			return true;
		default:
			return false;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta) {
		switch(this.getEquipTypeIDFromMeta(meta)) {
		case ID.EquipType.CATAPULT_LO:  //2800
			return new int[] {itemRand.nextInt(40) + 120,
	  		  		  		  itemRand.nextInt(50) + 150,
	  		  		  		  itemRand.nextInt(30) + 80,
	  		  		  		  itemRand.nextInt(60) + 180};
		case ID.EquipType.CATAPULT_HI:  //5000
			return new int[] {itemRand.nextInt(70) + 190,
							  itemRand.nextInt(85) + 230,
							  itemRand.nextInt(55) + 150,
							  itemRand.nextInt(90) + 250};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}

