package com.lulan.shincolle.block;

import net.minecraft.block.BlockAir;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntityLightBlock;

public class BlockLightAir extends BlockAir implements ITileEntityProvider {

	
	public BlockLightAir() {
		this.setBlockName("BlockLightAir");
		this.setTickRandomly(false);
		this.setLightLevel(1F);
		
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

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLightBlock(0, 120);
	}


	
}
