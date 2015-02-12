package com.lulan.shincolle.block;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGrudgeHeavy extends BasicBlockMulti {
	public BlockGrudgeHeavy() {
		super(Material.sand);
		this.setBlockName("BlockGrudgeHeavy");
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(1F);
	    this.setLightLevel(1F);
	    this.setStepSound(soundTypeSand);
		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMultiGrudgeHeavy();
	}

	
	
//	play portal sound
//	if (p_149734_5_.nextInt(100) == 0)
//    {
//        p_149734_1_.playSound((double)p_149734_2_ + 0.5D, (double)p_149734_3_ + 0.5D, (double)p_149734_4_ + 0.5D, "portal.portal", 0.5F, p_149734_5_.nextFloat() * 0.4F + 0.8F, false);
//    }
}
