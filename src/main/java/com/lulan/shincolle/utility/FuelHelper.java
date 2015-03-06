package com.lulan.shincolle.utility;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelHelper {
	
	public static int getItemFuelValue(ItemStack itemstack) {
		
		if(itemstack != null) {
			Item item = itemstack.getItem();
			
			if(item == Items.coal) return 1600;
//			
//			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
//				Block block = Block.getBlockFromItem(item);
//				
//					if(block == Blocks.sapling) return 100;
//					if(block == Blocks.coal_block) return 14400;
//					
//				}
//			
//				if(item == Items.coal) return 1600;
//				if(item == Items.stick) return 100;
//				if(item == Items.lava_bucket) return 20000;
//				
//				if(item == Items.blaze_rod) return 2400;
//			}
//			return GameRegistry.getFuelValue(itemstack);
		}

		return 0;
	}

}
