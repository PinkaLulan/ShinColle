package com.lulan.shincolle.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lulan.shincolle.reference.ID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**meta:
 *    0:  21inch Torpedo Mk.I
 *    1:  21inch Torpedo Mk.II
 *    2:  22inch Torpedo Mk.II
 *    3:  Cuttlefish Torpedo
 *    4:  High-Speed Torpedo
 */
public class EquipTorpedo extends BasicEquip {
	
	IIcon[] icons = new IIcon[1];
	
	
	public EquipTorpedo() {
		super();
		this.setUnlocalizedName("EquipTorpedo");
		this.types = 5;
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
		case 2:
			return ID.EquipType.TORPEDO_LO;
		case 3:
		case 4:
			return ID.EquipType.TORPEDO_HI;
		default:
			return 0;
		}
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		switch(this.getEquipTypeIDFromMeta(item.getItemDamage())) {
		case ID.EquipType.TORPEDO_HI:
			return true;
		default:
			return false;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta) {
		switch(this.getEquipTypeIDFromMeta(meta)) {
		case ID.EquipType.TORPEDO_LO:  //160
			return new int[] {itemRand.nextInt(4) + 8,
	  		  		  		  itemRand.nextInt(5) + 8,
	  		  		  		  itemRand.nextInt(6) + 12,
	  		  		  		  itemRand.nextInt(4) + 5};
		case ID.EquipType.TORPEDO_HI:  //1200
			return new int[] {itemRand.nextInt(20) + 60,
							  itemRand.nextInt(25) + 70,
							  itemRand.nextInt(30) + 80,
							  itemRand.nextInt(15) + 45};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}

