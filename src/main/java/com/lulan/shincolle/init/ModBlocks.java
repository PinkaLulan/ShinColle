package com.lulan.shincolle.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.block.BlockAbyssium;
import com.lulan.shincolle.block.BlockFrame;
import com.lulan.shincolle.block.BlockGrudge;
import com.lulan.shincolle.block.BlockGrudgeHeavy;
import com.lulan.shincolle.block.BlockPolymetal;
import com.lulan.shincolle.block.BlockPolymetalGravel;
import com.lulan.shincolle.block.BlockPolymetalOre;
import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.block.BlockVolBlock;
import com.lulan.shincolle.block.ICustomModels;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
public class ModBlocks
{

	//blocks
	public static Block BlockAbyssium;
	public static Block BlockCrane;
	public static Block BlockDesk;
	public static Block BlockFrame;
	public static Block BlockGrudge;
	public static Block BlockGrudgeHeavy;
	public static Block BlockLightAir;
	public static Block BlockLightLiquid;
	public static Block BlockPolymetal;
	public static Block BlockPolymetalGravel;
	public static Block BlockPolymetalOre;
	public static Block BlockSmallShipyard;
	public static Block BlockVolBlock;
	public static Block BlockVolCore;
	public static Block BlockWaypoint;

//	//fluid
//	public static Fluid LightFluid;
//	public static BasicBlockFluid BlockLightFluid;
	
	//list for blocks
	private static List<Block> ListBlocks;
	
	
	public static void init() throws Exception
	{
		ListBlocks = new ArrayList();
		
		BlockAbyssium = initBlocks(BlockAbyssium.class);
//		BlockCrane = initBlocks(BlockCrane.class);
//		BlockDesk = initBlocks(BlockDesk.class);
		BlockFrame = initBlocks(BlockFrame.class);
		BlockGrudge = initBlocks(BlockGrudge.class);
		BlockGrudgeHeavy = initBlocks(BlockGrudgeHeavy.class);
//		BlockLightAir = initBlocks(BlockLightAir.class);
//		BlockLightLiquid = initBlocks(BlockLightLiquid.class);
		BlockPolymetal = initBlocks(BlockPolymetal.class);
		BlockPolymetalGravel = initBlocks(BlockPolymetalGravel.class);
		BlockPolymetalOre = initBlocks(BlockPolymetalOre.class);
		BlockSmallShipyard = initBlocks(BlockSmallShipyard.class);
		BlockVolBlock = initBlocks(BlockVolBlock.class);
//		BlockVolCore = initBlocks(BlockVolCore.class);
//		BlockWaypoint = initBlocks(BlockWaypoint.class);

//		//fluid block
////		LightFluid = new Fluid("lightfluid");
////		FluidRegistry.registerFluid(LightFluid);
//		BlockLightFluid = new BasicBlockFluid(FluidRegistry.WATER, Material.water);
////		BlockLightFluid = new BasicBlockFluid(LightFluid, Material.water);
//		GameRegistry.registerBlock(BlockLightFluid, "BlockLightFluid");
////		LightFluid.setUnlocalizedName(BlockLightFluid.getUnlocalizedName());
//		
//		//special itemblock
//		//waypoint
//		GameRegistry.registerBlock(BlockWaypoint, ItemBlockWaypoint.class, "BlockWaypoint");
		
	}
	
	//create instance and add instance to list
	private static Block initBlocks(Class<? extends Block> blockClass) throws Exception
	{
		try
		{
			Block i = blockClass.newInstance();
			ListBlocks.add(i);
			return i;
		}
		catch (Exception e)
		{
			//建立instance失敗, 此例外必須丟出以強制中止遊戲
			LogHelper.info("EXCEPTION: block instancing fail: "+blockClass);
			throw e;
		}
	}
	
	//item model init, used in CLIENT PROXY INIT
	@SideOnly(Side.CLIENT)
    public static void initModels() throws Exception
	{
		for (Block b : ListBlocks)
		{
			try
			{
				((ICustomModels) b).initModel();
			}
			catch (Exception e)
			{
				//抓model失敗, 此例外必須丟出以強制中止遊戲
				LogHelper.info("EXCEPTION: block texture init fail: "+b);
				throw e;
			}
		}
    }
	
	
}
