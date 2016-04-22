package com.lulan.shincolle.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.lulan.shincolle.tileentity.TileEntityLightBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicBlockFluid extends BlockFluidClassic implements ITileEntityProvider {

	@SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
	
	
	public BasicBlockFluid(Fluid fluid, Material material) {
		super(fluid, material);
		this.setLightOpacity(3);
		this.disableStats();
		this.setHardness(100.0F);
		this.setTickRandomly(false);
		this.setLightLevel(1F);
		
	}
	
	@Override
    public int getRenderType() {
        return 4;
    }
	
	@Override
    public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon("water_still");
		flowingIcon = register.registerIcon("water_flow");
    }
	
	@Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if(world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
    	if(world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
    	return super.displaceIfPossible(world, x, y, z);
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLightBlock(1, 120);
	}
	

}
