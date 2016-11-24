package com.lulan.shincolle.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;

abstract public class BasicTileLockable extends TileEntityLockable
{
	
	protected int syncTime;
	
	
	public BasicTileLockable()
	{
		this.syncTime = 0;
    }
	
	/** get block meta for render, if tile is item, return meta = -1
	 *  used only in TESR with special custom model
	 */
	public int getRenderMetadata()
	{
		if (this.worldObj == null || this.pos == BlockPos.ORIGIN)
		{
			return -1;
		}
		else
		{
			return this.getBlockMetadata();
		}
	}
	
	//sync data for GUI display TODO
	public void sendSyncPacket()
	{
		if (!this.worldObj.isRemote)
		{
			TargetPoint point = new TargetPoint(worldObj.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64D);
			CommonProxy.channelG.sendToAllAround(new S2CGUIPackets(this), point);
		}
	}
	
	//sync data client to server TODO
	public void sendSyncPacketC2S() {}
	
	//dont refresh tile entity!!!
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return false;
    }
	
	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return null;
	}
	
	@Override
	public String getGuiID()
	{
		return null;
	}
	
	/** gui id from Enums.GuiID */
	public int getGuiIntID()
	{
		return -1;
	}
	

}
