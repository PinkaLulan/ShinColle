package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityDesk;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockDesk extends BasicBlockContainer {
	
	
	public BlockDesk() {
		super(Material.rock); //不指定型態 預設即為rock
		this.setBlockName("BlockDesk");
	    this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(1F);
	    this.setResistance(60F);
	}

	//非標準方形方塊  要傳-1表示用自己的render
	@Override
	public int getRenderType() {
		return -1;	//-1 = non standard render
	}
	
	//非標準方形方塊  設為false
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	//非標準方形方塊  設為false
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDesk();
	}
	
	/**右鍵點到方塊時呼叫此方法
	 * 參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z
	 */	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {	//client端只需要收到true
    		return true;
    	}
		else if(!player.isSneaking()) {  //server端: 按住shift不能點開方塊gui
			TileEntity entity = world.getTileEntity(x, y, z);
    		
    		if (entity != null) {	//開啟方塊GUI 參數:玩家,mod instance,gui ID,world,座標xyz
    			FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.ADMIRALDESK, world, x, y, z);
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	
	

}
