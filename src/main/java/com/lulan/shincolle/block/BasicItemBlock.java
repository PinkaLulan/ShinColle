package com.lulan.shincolle.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BasicItemBlock extends ItemBlock
{
	
	
	public BasicItemBlock(Block block)
	{
		super(block);
	}
	
	/** 依照meta值設定要使用的texture
	 * 
	 *  無設定sub type的物品只會設置一個texture
	 *  而有設sub type的物品則依照每個meta值都各設定一個texture
	 *  
	 *  注意
	 *  meta值跟texture非1對1情況時需override此方法並修改成自訂的texture對應
	 */
	@SideOnly(Side.CLIENT)
    public void initModel()
	{
        ModelLoader.setCustomModelResourceLocation(
        		this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
	
	
}