package com.lulan.shincolle.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.reference.Reference;

abstract public class BasicBlockFalling extends BlockFalling implements ICustomModels
{

	
	//無指定類型時 預設為sand型
	public BasicBlockFalling()
	{
		this(Material.SAND);
	}
		
	public BasicBlockFalling(Material material)
	{
		super(material);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	//name設定用方法: 將原本mc給的block名稱 去掉.之前的字串 以便另外串上mod名稱形成的字串
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	//將name冠上mod名稱 用於之後給各語系檔案放上正確名稱
	//格式為tile.MOD名稱:方塊名稱.name
	@Override
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
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
	
	
}
