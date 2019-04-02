package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


public class BlockVolBlock extends BasicBlock
{

	public static final String NAME = "BlockVolBlock";
	
	
	public BlockVolBlock()
	{
		super(Material.SAND);
		this.setTranslationKey(NAME);
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setResistance(200F);
	}
	
	@Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
		return true;
    }
	
	
}
