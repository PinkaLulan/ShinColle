package com.lulan.shincolle.block;

import com.lulan.shincolle.client.render.block.RenderDesk;
import com.lulan.shincolle.tileentity.TileEntityDesk;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDesk extends BasicBlockFacingContainer
{
	
	public static final String NAME = "BlockDesk";
	public static final String TILENAME = "TileEntityDesk";
	
	
	public BlockDesk()
	{
	    super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHardness(1F);
		this.setResistance(60F);
	    this.setHarvestLevel("pickaxe", 0);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityDesk();
	}
	
	//can drop items in inventory
	@Override
	public boolean canDropInventory(IBlockState state)
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel()
	{
		super.initModel();
		
		//prevent property mapping to blockstate
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(new IProperty[] {FACING}).build());
				
        //register tile entity render
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDesk.class, new RenderDesk());
    
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	

}