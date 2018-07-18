package com.lulan.shincolle.block;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntityLightBlock;

import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLightAir extends BlockAir implements ITileEntityProvider, ICustomModels
{
	
	public static final String NAME = "BlockLightAir";
	public static final String TILENAME = "TileEntityLightBlock";

	
	public BlockLightAir()
	{
	    super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
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
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityLightBlock();
	}
	
	
}