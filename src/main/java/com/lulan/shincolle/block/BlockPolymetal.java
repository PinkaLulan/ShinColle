package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.tileentity.TileMultiPolymetal;

public class BlockPolymetal extends BasicBlockMulti {	
	public BlockPolymetal() {
		super(Material.iron);
		this.setBlockName("BlockPolymetal");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    this.setStepSound(soundTypeMetal);		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileMultiPolymetal();
	}
	

}
