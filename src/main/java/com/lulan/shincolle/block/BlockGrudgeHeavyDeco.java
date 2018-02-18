package com.lulan.shincolle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class BlockGrudgeHeavyDeco extends BasicBlock
{

	public static final String NAME = "BlockGrudgeHeavyDeco";
	
	
	public BlockGrudgeHeavyDeco()
	{
		super(Material.SAND);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setResistance(300F);
	    this.setSoundType(SoundType.SAND);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockResourceBlock(this), this.getRegistryName());
	}
	
	@Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon)
    {
		return true;
    }
	
	
}