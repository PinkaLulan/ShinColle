package com.lulan.shincolle.item;

import com.lulan.shincolle.block.ICustomModels;
import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/** 基本item class
 *  
 */
abstract public class BasicItem extends Item implements ICustomModels
{
	public BasicItem()
	{
		super();
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	/** get item type number (= MAX meta number - 1) */
	public int getTypes()
	{
		return 1;
	}
	
	/** add item to creative tabs according to type value */
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (getTypes() <= 1)
		{
			list.add(new ItemStack(this));
		}
		else
		{
			for (int i = 0; i < getTypes(); i++)
			{
				list.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	//name設定用方法: 去掉.之前的字串 以便另外串上mod名稱形成的字串
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	//將name冠上mod名稱 用於之後給各語系檔案放上正確名稱
	//格式為item.MOD名稱:物品名稱.name
	@Override
	public String getTranslationKey()
	{
		return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getTranslationKey()));
	}
	
	//同getUnlocalizedName() 此為加上itemstack版本
	//格式為item.MOD名稱:物品名稱.name
	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();
		
		if (meta > 0)
		{
			return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getTranslationKey()) + meta);
		}
		else
		{
			return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getTranslationKey()));
		}		
	}

	@Override
	public Item setTranslationKey(String name)
	{
		super.setTranslationKey(name);
		this.setRegistryName(Reference.MOD_ID + ":" + name.toLowerCase());
		return this;
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
	@Override
    public void initModel()
	{
		//在inventory(背包介面)中要顯示的model, 依照meta value對應不同model
		//meta值有多個, 則依照meta值設定使用的texture
		if (getHasSubtypes())
		{
    		ModelResourceLocation[] models = new ModelResourceLocation[getTypes()];
    		
    		//宣告並設定textures位置
    		for (int i = 0; i < getTypes(); i++)
    		{
    			models[i] = new ModelResourceLocation(getRegistryName() + String.valueOf(i), "inventory");
    		}

    		//登錄全部textures
    	    ModelBakery.registerItemVariants(this, models);
    		
    	    //依照各meta值設定各自texture
    	    for (int i = 0; i < getTypes(); i++)
    		{
    	    	 ModelLoader.setCustomModelResourceLocation(this, i, models[i]);
    		}
		}
		//meta值只有一個 = texture只有一個
		else
		{
	        ModelLoader.setCustomModelResourceLocation(
	        		this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		}
    }
}