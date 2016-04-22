package com.lulan.shincolle.block;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFrame extends BasicBlock {
	
	IIcon[] icons = new IIcon[5];
	Random rand = new Random();
	
	public BlockFrame() {
		super();
		this.setBlockName("BlockFrame");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(1F);
	    this.setResistance(20F);
	    this.setStepSound(soundTypeMetal);
	    this.setLightOpacity(255);
	    
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon(String.format("shincolle:BlockFrame2"));
		icons[1] = iconRegister.registerIcon(String.format("shincolle:BlockFrame3"));
		icons[2] = iconRegister.registerIcon(String.format("shincolle:BlockFrame4"));
		icons[3] = iconRegister.registerIcon(String.format("shincolle:BlockFrame5"));
		icons[4] = iconRegister.registerIcon(String.format("shincolle:BlockFrameTop"));
	}
	
	//side: 0:bottom, 1:top, 2:N, 3:S, 4:W, 5:E
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
		switch(side) {
		case 0:
		case 1:
			return icons[4];
		default:
			return icons[rand.nextInt(4)];
		}
    }
	
	
}
