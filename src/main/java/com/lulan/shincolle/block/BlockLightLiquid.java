package com.lulan.shincolle.block;

import java.util.Random;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntityLightBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * this is NOT custom liquid block!
 * so NO fluid registering, just extends vanilla water
 *
 */
public class BlockLightLiquid extends BlockStaticLiquid implements ITileEntityProvider, ICustomModels
{
	
	public static final String NAME = "BlockLightLiquid";
	
	
	public BlockLightLiquid()
	{
	    super(Material.WATER);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setLightOpacity(3);
		this.disableStats();
		this.setHardness(100F);
		this.setTickRandomly(false);
		this.setLightLevel(1F);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void initModel()
	{
		//在inventory中顯示的模型
        ModelLoader.setCustomModelResourceLocation(
        		Item.getItemFromBlock(this), 0,
        		new ModelResourceLocation(getRegistryName(), "inventory"));
        
		//prevent property mapping to blockstate
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(new IProperty[] {LEVEL}).build());
    }

	//name設定用方法: 將原本mc給的block名稱 去掉.之前的字串 以便另外串上mod名稱形成的字串
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
	
	//將name冠上mod名稱 用於之後給各語系檔案放上正確名稱
	//格式為tile.MOD名稱:方塊名稱.name
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.MOD_ID+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityLightBlock();
	}
	
	@Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
    }
	
	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}

	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
	
	
}