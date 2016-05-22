package com.lulan.shincolle.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//基本item class 定義各種item基本方法
abstract public class BasicItem extends Item
{
	
	public BasicItem()
	{
		super();	//先跑一次原本的item初始化 下面才繼續設定詳細item數值 如堆疊數等
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	//name設定用方法: 去掉.之前的字串 以便另外串上mod名稱形成的字串
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
	
	//將name冠上mod名稱 用於之後給各語系檔案放上正確名稱
	//格式為item.MOD名稱:物品名稱.name
	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", Reference.MOD_ID+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	//同getUnlocalizedName() 此為加上itemstack版本
	//格式為item.MOD名稱:物品名稱.name
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		
		if (meta > 0)
		{
			return String.format("item.%s%s", Reference.MOD_ID+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName())+meta);
		}
		else
		{
			return String.format("item.%s%s", Reference.MOD_ID+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
		}		
	}
	
	//物品圖示登錄
	//取出物品名稱(不含mod名稱)作為參數丟給icon register來登錄icon
	//注意icon只在client端才需要執行
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
	}


}
