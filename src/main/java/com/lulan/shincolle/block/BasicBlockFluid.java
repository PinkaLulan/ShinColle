package com.lulan.shincolle.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.lulan.shincolle.reference.Reference;
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
		this.setBlockName("BlockBasicFluid");
		this.setLightOpacity(3);
		this.disableStats();
		this.setHardness(100F);
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
//		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
		stillIcon = register.registerIcon("water_still");
		flowingIcon = register.registerIcon("water_flow");
    }
	
	@Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if(world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
    }
	
	@Override
	public boolean isSourceBlock(IBlockAccess world, int x, int y, int z)
    {
        return false;
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
	
	//name設定用方法: 將原本mc給的block名稱 去掉.之前的字串 以便另外串上mod名稱形成的字串
	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
	
	//將name冠上mod名稱 用於之後給各語系檔案放上正確名稱
	//格式為tile.MOD名稱:方塊名稱.name
	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.MOD_ID+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	

}
