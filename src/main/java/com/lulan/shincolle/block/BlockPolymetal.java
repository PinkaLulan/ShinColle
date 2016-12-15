package com.lulan.shincolle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.tileentity.TileMultiPolymetal;

public class BlockPolymetal extends BasicBlockMulti
{	
	
	public static final String NAME = "BlockPolymetal";
	public static final String TILENAME = "TileMultiPolymetal";

	
	public BlockPolymetal()
	{
		super(Material.WATER);
		this.setSoundType(SoundType.METAL);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockResourceBlock(this), this.getRegistryName());
        GameRegistry.registerTileEntity(TileMultiPolymetal.class, TILENAME);
        
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileMultiPolymetal();
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel()
	{
		super.initModel();
		
		//prevent property mapping to blockstate
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(MBS).build());
	}
	
	//can drop items in inventory
	@Override
	public boolean canDropInventory(IBlockState state)
	{
		return false;
	}
	
	//cancel water block side rendering
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
		IBlockState sideState = world.getBlockState(pos.offset(face));
		
		if (sideState != null && state.getValue(MBS) > 0 &&
			(sideState.getMaterial() == Material.WATER || sideState.getMaterial() == Material.LAVA))
		{
			return true;
		}
		
        return state.isOpaqueCube();
    }
	
	
}
