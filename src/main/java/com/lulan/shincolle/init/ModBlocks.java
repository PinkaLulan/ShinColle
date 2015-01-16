package com.lulan.shincolle.init;

import net.minecraft.block.Block;

import com.lulan.shincolle.block.BasicBlock;
import com.lulan.shincolle.block.BasicBlockContainer;
import com.lulan.shincolle.block.BlockDesk;
import com.lulan.shincolle.block.BlockPolymetalOre;
import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
public class ModBlocks {

	public static final Block BlockDesk = new BlockDesk();
	public static final Block BlockPolymetalOre = new BlockPolymetalOre();
	public static final Block BlockSmallShipyard = new BlockSmallShipyard();
	
	public static void init() {
		GameRegistry.registerBlock(BlockDesk, "BlockDesk");
		GameRegistry.registerBlock(BlockPolymetalOre, "BlockPolymetalOre");
		GameRegistry.registerBlock(BlockSmallShipyard, "BlockSmallShipyard");
		
	}
}
