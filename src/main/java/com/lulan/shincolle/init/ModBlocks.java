package com.lulan.shincolle.init;

import com.lulan.shincolle.block.*;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.*;
import com.lulan.shincolle.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks
{

	//blocks
	public static final Block BlockAbyssium = null;
	public static final Block BlockCrane = null;
	public static final Block BlockDesk = null;
	public static final Block BlockFrame = null;
	public static final Block BlockGrudge = null;
	public static final Block BlockGrudgeXP = null;
	public static final Block BlockGrudgeHeavy = null;
	public static final Block BlockGrudgeHeavyDeco = null;
	public static final Block BlockLightAir = null;
	public static final Block BlockLightLiquid = null;
	public static final Block BlockPolymetal = null;
	public static final Block BlockPolymetalGravel = null;
	public static final Block BlockPolymetalOre = null;
	public static final Block BlockSmallShipyard = null;
	public static final Block BlockVolBlock = null;
	public static final Block BlockVolCore = null;
	public static final Block BlockWaypoint = null;

	//list for blocks
	private static List<Block> ListBlocks;

	private static Block[] BLOCKS;

	static
	{
        ListBlocks = new ArrayList();

		try
		{
			BLOCKS = new Block[]
					{
							initBlocks(BlockAbyssium.class),
							initBlocks(BlockCrane.class),
			                initBlocks(BlockDesk.class),
	                		initBlocks(BlockFrame.class),
	                		initBlocks(BlockGrudge.class),
	                		initBlocks(BlockGrudgeXP.class),
	                		initBlocks(BlockGrudgeHeavy.class),
	                		initBlocks(BlockGrudgeHeavyDeco.class),
		                	initBlocks(BlockLightAir.class),
		                	initBlocks(BlockLightLiquid.class),
		                	initBlocks(BlockPolymetal.class),
		                	initBlocks(BlockPolymetalGravel.class),
		                	initBlocks(BlockPolymetalOre.class),
		                	initBlocks(BlockSmallShipyard.class),
		                	initBlocks(BlockVolBlock.class),
		                	initBlocks(BlockVolCore.class),
		                	initBlocks(BlockWaypoint.class)
					};
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BLOCKS);
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event)
	{
		for(Block block : BLOCKS)
		{
		    if(block instanceof BlockAbyssium || block instanceof BlockGrudge || block instanceof BlockPolymetal || block instanceof BlockPolymetalGravel || block instanceof BlockGrudgeHeavyDeco)
            {
                event.getRegistry().register(new ItemBlockResourceBlock(block).setRegistryName(block.getRegistryName()));
            } else if(block instanceof BlockWaypoint)
            {
                event.getRegistry().register(new ItemBlockWaypoint(block).setRegistryName(block.getRegistryName()));
            } else if(block instanceof BlockGrudgeHeavy)
            {
                event.getRegistry().register(new ItemBlockGrudgeHeavy(block).setRegistryName(block.getRegistryName()));
            } else
            {
                event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
            }
		}
	}

	public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityWaypoint.class, new ResourceLocation(Reference.MOD_ID, "TileEntityWaypoint"));
        GameRegistry.registerTileEntity(TileEntityVolCore.class, new ResourceLocation(Reference.MOD_ID, "TileEntityVolCore"));
        GameRegistry.registerTileEntity(TileEntitySmallShipyard.class, new ResourceLocation(Reference.MOD_ID, "TileEntitySmallShipyard"));
        GameRegistry.registerTileEntity(TileMultiPolymetal.class, new ResourceLocation(Reference.MOD_ID, "TileMultiPolymetal"));
        GameRegistry.registerTileEntity(TileEntityLightBlock.class, new ResourceLocation(Reference.MOD_ID, "TileEntityLightBlock"));
        GameRegistry.registerTileEntity(TileEntityDesk.class, new ResourceLocation(Reference.MOD_ID, "TileEntityDesk"));
        GameRegistry.registerTileEntity(TileEntityCrane.class, new ResourceLocation(Reference.MOD_ID, "TileEntityCrane"));
        GameRegistry.registerTileEntity(TileMultiGrudgeHeavy.class, new ResourceLocation(Reference.MOD_ID, "TileMultiLargeShipyard"));
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
			e.printStackTrace();
			throw e;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void loadBlockModels(ModelRegistryEvent event)
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
				e.printStackTrace();
				throw e;
			}
		}
	}
}