package com.lulan.shincolle.block;

import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BasicBlock extends Block {

	//指定方塊類型block
	public BasicBlock(Material material) {
		super(material);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	//無指定類型時 預設為rock型
	public BasicBlock() {
		this(Material.rock);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
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
	
	//方塊圖示登錄
	//取出方塊名稱(不含mod名稱)作為參數丟給icon register來登錄icon
	//注意icon只在client端才需要執行
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}

}
