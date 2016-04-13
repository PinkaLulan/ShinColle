package com.lulan.shincolle.block;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockVolBlock extends BasicBlock {
	
	public BlockVolBlock() {
		super();
		this.setBlockName("BlockVolBlock");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setResistance(200F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(String.format("shincolle:BlockVolCore"));
	}
	
	
}
