package com.lulan.shincolle.block;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import com.lulan.shincolle.tileentity.TileMultiLargeShipyard;

public class BlockGrudgeHeavy extends BasicBlockMulti {
	
	private IIcon[] icons = new IIcon[2];
	
	public BlockGrudgeHeavy() {
		super(Material.sand);
		this.setBlockName("BlockGrudgeHeavy");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setStepSound(soundTypeSand);
	}	
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
		icons[1] = iconRegister.registerIcon(Reference.MOD_ID+":BlockVisible");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
	    if(meta > 0) meta = 1;
	    return icons[meta];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileMultiLargeShipyard();
	}
	

	
//	play portal sound
//	if (p_149734_5_.nextInt(100) == 0)
//    {
//        p_149734_1_.playSound((double)p_149734_2_ + 0.5D, (double)p_149734_3_ + 0.5D, (double)p_149734_4_ + 0.5D, "portal.portal", 0.5F, p_149734_5_.nextFloat() * 0.4F + 0.8F, false);
//    }
	
	
}

