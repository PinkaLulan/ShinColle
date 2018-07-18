package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * basic tile entity
 */
abstract public class BasicTileEntity extends TileEntity
{
	
	protected int syncTime;
	
	
	public BasicTileEntity()
	{
		this.syncTime = 0;
    }
	
	/**
	 * get block meta for render, if tile is item, return meta = -1
	 * used only in TESR with special custom model
	 */
	public int getRenderMetadata()
	{
		if (this.world == null || this.pos == BlockPos.ORIGIN)
		{
			return -1;
		}
		else
		{
			return this.getBlockMetadata();
		}
	}
	
	/** sync data for GUI display */
	public void sendSyncPacket()
	{
		if (!this.world.isRemote && this.getPacketID(0) >= 0)
		{
			TargetPoint point = new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64D);
			CommonProxy.channelG.sendToAllAround(new S2CGUIPackets(this, this.getPacketID(0)), point);
		}
	}
	
	/** sync data client to server */
	public void sendSyncPacketC2S() {}
	
	/** dont refresh tile entity!!! */
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return false;
    }

	/** gui id from Enums.GuiID */
	public byte getGuiIntID()
	{
		return -1;
	}
	
	/**
	 * sync packet ID
	 * 
	 * type:
	 *   0:server to client GUI packet
	 * 
	 */
	public byte getPacketID(int type)
	{
		return -1;
	}
	
	/** get registered name */
	abstract public String getRegName();

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString("tile." + Reference.MOD_ID + ":" + getRegName());
	}

	/** FIELD相關方法
	 *  使其他mod或class也能存取該tile的內部值
	 *  ex: gui container可用get/setField來更新數值
	 */
	public int getField(int id)
	{
		return 0;
	}

	public void setField(int id, int value) {}

	public int getFieldCount()
	{
		return 0;
	}

	public void clear() {}
	

}