package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.Reference;

import net.minecraft.item.ItemStack;

public class KaitaiHammer extends BasicItem {
	
	public KaitaiHammer() {
		super();
		this.setUnlocalizedName("KaitaiHammer");
		this.maxStackSize = 1;
		this.hasSubtypes = false;
		this.setMaxDamage(12);
	}
	
	//此鎚子可用於合成其他道具, 且不為消耗品, 故ContainerItem為耐久度-1的自己本身
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		int meta = stack.getItemDamage() + 1;
		
		stack.setItemDamage(meta);	//耐久度--
		
		if(meta >= this.getMaxDamage()) {	//物品達到耐久度上限, 回傳空物品
			return null;
		}
		
		return stack;
	}
	
	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
        return false;	//合成後此物品會繼續留在合成台內
    }
	
	//同getUnlocalizedName() 此為加上itemstack版本
	//格式為item.MOD名稱:物品名稱.name
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return String.format("item.%s", Reference.MOD_ID+":KaitaiHammer");
	}
	
	
	
	
	
}
