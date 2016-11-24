package com.lulan.shincolle.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrane extends BasicBlockContainer {

	IIcon[] icons = new IIcon[3];
	
	
	public BlockCrane() {
		super(); //不指定型態 預設即為rock
		this.setBlockName("BlockCrane");
	    this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(1F);
	    this.setResistance(10F);
	    this.setStepSound(soundTypeMetal);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon(String.format("shincolle:BlockCraneBtm"));
		icons[1] = iconRegister.registerIcon(String.format("shincolle:BlockCraneSide"));
		icons[2] = iconRegister.registerIcon(String.format("shincolle:BlockCraneTop"));
	}
	
	//side: 0:bottom, 1:top, 2:N, 3:S, 4:W, 5:E
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
		switch(side) {
		case 0:
			return icons[0];
		case 1:
			return icons[2];
		default:
			return icons[1];
		}
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCrane();
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
    			FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.CRANE, world, x, y, z);
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
	
	@Override
	public boolean canProvidePower()
    {
        return true;
    }
	
	@Override
	public int isProvidingStrongPower(IBlockAccess block, int x, int y, int z, int face)
    {
        return isProvidingWeakPower(block, x, y, z, face);
    }
	
	@Override
	public int isProvidingWeakPower(IBlockAccess block, int x, int y, int z, int face)
    {
		TileEntity tile = block.getTileEntity(x, y, z);
        
        if (tile instanceof TileEntityCrane)
        {
        	TileEntityCrane crane = (TileEntityCrane) tile;
        	if (crane.redMode > 0 && crane.redTick > 0) return 15;
        }
        
        return 0;
    }
	
	
}
