package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaypoint extends BasicBlockContainer {

	
	public BlockWaypoint() {
		super(Material.glass);  //不指定型態 預設即為rock
		this.setBlockName("BlockWaypoint");
		this.setStepSound(soundTypeGlass);
		this.setHardness(0F);
		this.setLightOpacity(0);
	    
	}
	
	@Override
	public int getRenderType() {
        return -1;
    }
	
	//使entity會穿過方塊
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return null;
    }
	
	@Override
	public boolean isOpaqueCube() {
        return false;
    }
	
	//使玩家可以點到方塊
	@Override
	public boolean canCollideCheck(int par1, boolean par2) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWaypoint();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}
		
		
}

