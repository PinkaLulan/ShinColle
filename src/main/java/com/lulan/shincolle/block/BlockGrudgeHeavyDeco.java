package com.lulan.shincolle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


public class BlockGrudgeHeavyDeco extends BasicBlock
{

	public static final String NAME = "BlockGrudgeHeavyDeco";
	
	
	public BlockGrudgeHeavyDeco()
	{
		super(Material.SAND);
		this.setTranslationKey(NAME);
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setResistance(300F);
	    this.setSoundType(SoundType.SAND);
	}
	
	@Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
		return true;
    }
	
	
}