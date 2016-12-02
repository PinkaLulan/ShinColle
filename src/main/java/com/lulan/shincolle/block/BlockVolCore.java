package com.lulan.shincolle.block;

import com.lulan.shincolle.tileentity.TileEntityVolCore;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockVolCore extends BasicBlockContainer
{
	
	public static final String NAME = "BlockVolCore";
	public static final String TILENAME = "TileEntityVolCore";

	
	public BlockVolCore()
	{
	    super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHardness(6F);
		this.setResistance(600F);
	    this.setHarvestLevel("pickaxe", 0);
	    this.setLightLevel(1F);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), this.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityVolCore.class, TILENAME);	    
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityVolCore();
	}
		
		
}
