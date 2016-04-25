package com.lulan.shincolle.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.lulan.shincolle.block.BasicBlockFluid;
import com.lulan.shincolle.block.BlockAbyssium;
import com.lulan.shincolle.block.BlockCrane;
import com.lulan.shincolle.block.BlockDesk;
import com.lulan.shincolle.block.BlockFrame;
import com.lulan.shincolle.block.BlockGrudge;
import com.lulan.shincolle.block.BlockGrudgeHeavy;
import com.lulan.shincolle.block.BlockLightAir;
import com.lulan.shincolle.block.BlockLightLiquid;
import com.lulan.shincolle.block.BlockPolymetal;
import com.lulan.shincolle.block.BlockPolymetalGravel;
import com.lulan.shincolle.block.BlockPolymetalOre;
import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.block.BlockVolBlock;
import com.lulan.shincolle.block.BlockVolCore;
import com.lulan.shincolle.block.BlockWaypoint;
import com.lulan.shincolle.block.ItemBlockGrudgeHeavy;
import com.lulan.shincolle.block.ItemBlockResourceBlock;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
public class ModBlocks {

	public static final Block BlockAbyssium = new BlockAbyssium();
	public static final Block BlockCrane = new BlockCrane();
	public static final Block BlockDesk = new BlockDesk();
	public static final Block BlockFrame = new BlockFrame();
	public static final Block BlockGrudge = new BlockGrudge();
	public static final Block BlockGrudgeHeavy = new BlockGrudgeHeavy();
	public static final Block BlockLightAir = new BlockLightAir();
	public static final Block BlockLightLiquid = new BlockLightLiquid();
	public static final Block BlockPolymetal = new BlockPolymetal();
	public static final Block BlockPolymetalGravel = new BlockPolymetalGravel();
	public static final Block BlockPolymetalOre = new BlockPolymetalOre();
	public static final Block BlockSmallShipyard = new BlockSmallShipyard();
	public static final Block BlockVolBlock = new BlockVolBlock();
	public static final Block BlockVolCore = new BlockVolCore();
	public static final Block BlockWaypoint = new BlockWaypoint();

	//fluid
	public static Fluid LightFluid;
	public static BasicBlockFluid BlockLightFluid;
	
	
	public static void init() {
		GameRegistry.registerBlock(BlockCrane, "BlockCrane");
		GameRegistry.registerBlock(BlockDesk, "BlockDesk");
		GameRegistry.registerBlock(BlockFrame, "BlockFrame");
		GameRegistry.registerBlock(BlockLightAir, "BlockLightAir");
		GameRegistry.registerBlock(BlockLightLiquid, "BlockLightLiquid");
		GameRegistry.registerBlock(BlockPolymetalOre, "BlockPolymetalOre");
		GameRegistry.registerBlock(BlockSmallShipyard, "BlockSmallShipyard");
		GameRegistry.registerBlock(BlockVolBlock, "BlockVolBlock");
		GameRegistry.registerBlock(BlockVolCore, "BlockVolCore");
		
		//fluid block
//		LightFluid = new Fluid("lightfluid");
//		FluidRegistry.registerFluid(LightFluid);
		BlockLightFluid = new BasicBlockFluid(FluidRegistry.WATER, Material.water);
//		BlockLightFluid = new BasicBlockFluid(LightFluid, Material.water);
		GameRegistry.registerBlock(BlockLightFluid, "BlockLightFluid");
//		LightFluid.setUnlocalizedName(BlockLightFluid.getUnlocalizedName());
		
		//special itemblock
		//resource
		GameRegistry.registerBlock(BlockAbyssium, ItemBlockResourceBlock.class, "BlockAbyssium");
		GameRegistry.registerBlock(BlockGrudge, ItemBlockResourceBlock.class, "BlockGrudge");
		GameRegistry.registerBlock(BlockPolymetal, ItemBlockResourceBlock.class, "BlockPolymetal");
		GameRegistry.registerBlock(BlockPolymetalGravel, ItemBlockResourceBlock.class, "BlockPolymetalGravel");
		//grudge heavy
		GameRegistry.registerBlock(BlockGrudgeHeavy, ItemBlockGrudgeHeavy.class, "BlockGrudgeHeavy");
		//waypoint
		GameRegistry.registerBlock(BlockWaypoint, ItemBlockWaypoint.class, "BlockWaypoint");
		
	}
	
	
}
