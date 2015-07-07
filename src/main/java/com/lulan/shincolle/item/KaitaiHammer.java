package com.lulan.shincolle.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

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
	
	//左鍵用於自己的棲艦, 可使該棲艦一擊死亡 (轉回物品型態, 等級-1)
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		//entity is ship
		if(entity instanceof BasicEntityShip) {
			//player is owner
			if(EntityHelper.checkSameOwner(player, entity)) {
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), ((BasicEntityShip) entity).getMaxHealth() * 1.01F);
				
				//item meta+1
				int meta = stack.getItemDamage()+1;
				
				if(meta >= stack.getMaxDamage()) {
					//destroy the hammer
					if(player.inventory.getCurrentItem() != null && 
					   player.inventory.getCurrentItem().getItem() == ModItems.KaitaiHammer) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
				}
				else {
					stack.setItemDamage(meta);
				}
			}
		}
		
        return false;
    }
	
	
	
	
	
}
