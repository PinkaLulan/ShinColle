package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockWaypoint;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityWaypoint extends BasicTileEntity implements ITileWaypoint, ITickable
{

	//waypoint
	private int tick, wpstay;
	private BlockPos lastPos, nextPos;
	
	
	public TileEntityWaypoint()
	{
		super();
		this.tick = 0;
		this.wpstay = 0;
		this.lastPos = BlockPos.ORIGIN;
		this.nextPos = BlockPos.ORIGIN;
	}
	
	@Override
	public String getRegName()
	{
		return BlockWaypoint.TILENAME;
	}
	
	@Override
	public byte getPacketID(int type)
	{
		switch (type)
		{
		case 0:
			return S2CGUIPackets.PID.TileWaypoint;
		}
		
		return -1;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);	//從nbt讀取方塊的xyz座標
        
        wpstay = nbt.getInteger("wpstay");

        //load pos
        try
        {
            int[] pos =  nbt.getIntArray("lastPos");
            this.lastPos = new BlockPos(pos[0], pos[1], pos[2]);
            
            pos =  nbt.getIntArray("nextPos");
            this.nextPos = new BlockPos(pos[0], pos[1], pos[2]);
        }
        catch (Exception e)
        {
        	LogHelper.info("EXCEPTION: TileEntityCrane load position fail: "+e);
        	this.lastPos = BlockPos.ORIGIN;
        	this.nextPos = BlockPos.ORIGIN;
        }
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("wpstay", wpstay);
		
        //save pos
        if (this.lastPos != null && this.nextPos != null)
        {
        	nbt.setIntArray("lastPos", new int[] {this.lastPos.getX(), this.lastPos.getY(), this.lastPos.getZ()});
        	nbt.setIntArray("nextPos", new int[] {this.nextPos.getX(), this.nextPos.getY(), this.nextPos.getZ()});
        }
        else
        {
        	nbt.setIntArray("lastPos", new int[] {0, 0, 0});
        	nbt.setIntArray("nextPos", new int[] {0, 0, 0});
        }
		
		return nbt;
	}
	
	@Override
	public void update()
	{
		tick++;
		
		//show client particle
		if (this.worldObj.isRemote)
		{
			//player hold waypoint or target wrench
			EntityPlayer player = ClientProxy.getClientPlayer();
			ItemStack item = player.inventory.getCurrentItem();
			
			if (item != null && (item.getItem() instanceof ItemBlockWaypoint || item.getItem() == ModItems.TargetWrench ||
			   (item.getItem() instanceof PointerItem && item.getItemDamage() < 3)))
			{
				if (this.tick % 8 == 0)
				{
					ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-0.25D, pos.getZ()+0.5D, 0.2D, 9D, 0D, (byte) 25);
					
					if (this.tick % 16 == 0)
					{
						//next point mark
						if (this.nextPos.getY() > 0)
						{
							double dx = this.nextPos.getX() - this.pos.getX();
							double dy = this.nextPos.getY() - this.pos.getY();
							double dz = this.nextPos.getZ() - this.pos.getZ();
							dx *= 0.01D;
							dy *= 0.01D;
							dz *= 0.01D;
									
							ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, dx, dy, dz, (byte) 38);
						}
						
						if (this.tick % 32 == 0)
						{
							//circle mark
							ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-0.25D, pos.getZ()+0.5D, 0.2D, 8D, 0D, (byte) 25);
						}//end every 32 ticks
					}//end every 16 ticks
				}//end every 8 ticks
			}//end holding item
		}//end client side
		else
		{
			//sync waypoint
			if (this.lastPos.getY() > 0 || this.nextPos.getY() > 0)
			{
				if (this.tick >= 128)
				{
					this.tick = 0;
					this.sendSyncPacket();
				}
			}
		}
	}
	
	public void setSyncData(int[] data)
	{
		if (data != null && data.length == 6)
		{
			this.lastPos = new BlockPos(data[0], data[1], data[2]);
			this.nextPos = new BlockPos(data[3], data[4], data[5]);
		}
	}

	@Override
	public void setNextWaypoint(BlockPos pos)
	{
		if (pos != null)
		{
			this.nextPos = pos;
		}
	}

	@Override
	public BlockPos getNextWaypoint()
	{
		return this.nextPos;
	}
	
	@Override
	public void setLastWaypoint(BlockPos pos)
	{
		if (pos != null)
		{
			this.lastPos = pos;
		}
	}

	@Override
	public BlockPos getLastWaypoint()
	{
		return this.lastPos;
	}

	@Override
	public void setWpStayTime(int wpstay)
	{
		this.wpstay = wpstay;
	}

	@Override
	public int getWpStayTime()
	{
		return BasicEntityShip.wpStayTime2Ticks(wpstay);
	}
	
	public void nextWpStayTime()
	{
		this.wpstay++;
		if (this.wpstay > 16) this.wpstay = 0;
	}
	

}
