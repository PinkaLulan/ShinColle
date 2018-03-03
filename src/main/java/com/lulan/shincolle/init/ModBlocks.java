package com.lulan.shincolle.init;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.block.BlockAbyssium;
import com.lulan.shincolle.block.BlockCrane;
import com.lulan.shincolle.block.BlockDesk;
import com.lulan.shincolle.block.BlockFrame;
import com.lulan.shincolle.block.BlockGrudge;
import com.lulan.shincolle.block.BlockGrudgeHeavy;
import com.lulan.shincolle.block.BlockGrudgeHeavyDeco;
import com.lulan.shincolle.block.BlockGrudgeXP;
import com.lulan.shincolle.block.BlockLightAir;
import com.lulan.shincolle.block.BlockLightLiquid;
import com.lulan.shincolle.block.BlockPolymetal;
import com.lulan.shincolle.block.BlockPolymetalGravel;
import com.lulan.shincolle.block.BlockPolymetalOre;
import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.block.BlockVolBlock;
import com.lulan.shincolle.block.BlockVolCore;
import com.lulan.shincolle.block.BlockWaypoint;
import com.lulan.shincolle.block.ICustomModels;
import com.lulan.shincolle.block.ItemBlockGrudgeHeavy;
import com.lulan.shincolle.block.ItemBlockResourceBlock;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntityLightBlock;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.tileentity.TileMultiPolymetal;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks
{

	//blocks
	public static BlockAbyssium BlockAbyssium;
	public static BlockCrane BlockCrane;
	public static BlockDesk BlockDesk;
	public static BlockFrame BlockFrame;
	public static BlockGrudge BlockGrudge;
	public static BlockGrudgeXP BlockGrudgeXP;
	public static BlockGrudgeHeavy BlockGrudgeHeavy;
	public static BlockGrudgeHeavyDeco BlockGrudgeHeavyDeco;
	public static BlockLightAir BlockLightAir;
	public static BlockLightLiquid BlockLightLiquid;
	public static BlockPolymetal BlockPolymetal;
	public static BlockPolymetalGravel BlockPolymetalGravel;
	public static BlockPolymetalOre BlockPolymetalOre;
	public static BlockSmallShipyard BlockSmallShipyard;
	public static BlockVolBlock BlockVolBlock;
	public static BlockVolCore BlockVolCore;
	public static BlockWaypoint BlockWaypoint;
	
	//list for blocks
	private static List<Block> ListBlocks;
	
	
	/** register block */
	public static void register(RegistryEvent.Register<Block> event) throws Exception
	{
		ListBlocks = new ArrayList();
		
		BlockAbyssium = (BlockAbyssium) initBlocks(event, BlockAbyssium.class, null, null, ItemBlockResourceBlock.class);
		BlockCrane = (BlockCrane) initBlocks(event, BlockCrane.class, TileEntityCrane.class, BlockCrane.TILENAME, null);
		BlockDesk = (BlockDesk) initBlocks(event, BlockDesk.class, TileEntityDesk.class, BlockDesk.TILENAME, null);
		BlockFrame = (BlockFrame) initBlocks(event, BlockFrame.class, null, null, null);
		BlockGrudge = (BlockGrudge) initBlocks(event, BlockGrudge.class, null, null, ItemBlockResourceBlock.class);
		BlockGrudgeXP = (BlockGrudgeXP) initBlocks(event, BlockGrudgeXP.class, null, null, null);
		BlockGrudgeHeavy = (BlockGrudgeHeavy) initBlocks(event, BlockGrudgeHeavy.class, TileMultiGrudgeHeavy.class, BlockGrudgeHeavy.TILENAME, ItemBlockGrudgeHeavy.class);
		BlockGrudgeHeavyDeco = (BlockGrudgeHeavyDeco) initBlocks(event, BlockGrudgeHeavyDeco.class, null, null, ItemBlockResourceBlock.class);
		BlockLightAir = (BlockLightAir) initBlocks(event, BlockLightAir.class, TileEntityLightBlock.class, BlockLightAir.TILENAME, null);
		BlockLightLiquid = (BlockLightLiquid) initBlocks(event, BlockLightLiquid.class, null, null, null);
		BlockPolymetal = (BlockPolymetal) initBlocks(event, BlockPolymetal.class, TileMultiPolymetal.class, BlockPolymetal.TILENAME, ItemBlockResourceBlock.class);
		BlockPolymetalGravel = (BlockPolymetalGravel) initBlocks(event, BlockPolymetalGravel.class, null, null, ItemBlockResourceBlock.class);
		BlockPolymetalOre = (BlockPolymetalOre) initBlocks(event, BlockPolymetalOre.class, null, null, null);
		BlockSmallShipyard = (BlockSmallShipyard) initBlocks(event, BlockSmallShipyard.class, TileEntitySmallShipyard.class, BlockSmallShipyard.TILENAME, null);
		BlockVolBlock = (BlockVolBlock) initBlocks(event, BlockVolBlock.class, null, null, null);
		BlockVolCore = (BlockVolCore) initBlocks(event, BlockVolCore.class, TileEntityVolCore.class, BlockVolCore.TILENAME, null);
		BlockWaypoint = (BlockWaypoint) initBlocks(event, BlockWaypoint.class, TileEntityWaypoint.class, BlockWaypoint.TILENAME, ItemBlockWaypoint.class);
	}
	
	/**
	 * create instance and add the instance to list
	 * tileClass and itemClass can be null (will register with normal tile and itemblock)
	 */
	private static Block initBlocks(RegistryEvent.Register<Block> event,
									Class<? extends Block> blockClass,
									Class<? extends TileEntity> tileClass, String tileName,
									Class<? extends ItemBlock> itemClass) throws Exception
	{
		try
		{
			//new instance
			Block b = blockClass.newInstance();
			
			//add block to list (for model init)
			ListBlocks.add(b);
			
			//register block
			event.getRegistry().register(b);
			
			//rehister itemblock
			if (itemClass != null)
			{
				itemClass.newInstance().setRegistryName(b.getRegistryName());
			}
			else
			{
				new ItemBlock(b).setRegistryName(b.getRegistryName());
			}
			
			//register tile entity
			if (tileClass != null && tileName != null)
			{
				GameRegistry.registerTileEntity(tileClass, tileName);
			}
	        
			return b;
		}
		catch (Exception e)
		{
			//建立instance失敗, 此例外必須丟出以強制中止遊戲
			LogHelper.info("EXCEPTION: block instancing fail: "+blockClass);
			e.printStackTrace();
			throw e;
		}
	}
	
	/** item model init, used in CLIENT PROXY INIT */
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
				e.printStackTrace();
				throw e;
			}
		}
    }
	
	
}