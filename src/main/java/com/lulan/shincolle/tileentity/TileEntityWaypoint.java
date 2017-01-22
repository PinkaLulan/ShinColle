package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockWaypoint;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class TileEntityWaypoint extends BasicTileEntity implements ITileWaypoint, ITickable
{

	//waypoint
	private int tick, wpstay, playerUID;
	private BlockPos lastPos, nextPos;
	
	
	public TileEntityWaypoint()
	{
		super();
		this.tick = 0;
		this.wpstay = 0;
		this.playerUID = 0;
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
        playerUID = nbt.getInteger("pid");
        
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
        	LogHelper.info("EXCEPTION: TileEntityCrane load position fail, reset to BlockPos.ORIGIN");
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
		nbt.setInteger("pid", playerUID);
		
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
		
		//client side
		if (this.world.isRemote)
		{
			//valid tile
			if (this.world.getBlockState(this.pos).getBlock() != ModBlocks.BlockWaypoint)
			{
				this.invalidate();
				return;
			}
			
			//show client particle: player hold waypoint or target wrench
			EntityPlayer player = ClientProxy.getClientPlayer();
			ItemStack item = player.inventory.getCurrentItem();
			
			if (item != null && (item.getItem() instanceof ItemBlockWaypoint || item.getItem() == ModItems.TargetWrench ||
			   (item.getItem() instanceof PointerItem && item.getItemDamage() < 3)))
			{
				if (this.tick % 8 == 0)
				{
					//draw arrow mark
					ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-0.25D, pos.getZ()+0.5D, 0.2D, 9D, 0D, (byte) 25);
					
					if (this.tick % 16 == 0)
					{
						//draw next point spray
						if (this.nextPos.getY() > 0)
						{
							double dx = this.nextPos.getX() - this.pos.getX();
							double dy = this.nextPos.getY() - this.pos.getY();
							double dz = this.nextPos.getZ() - this.pos.getZ();
							dx *= 0.01D;
							dy *= 0.01D;
							dz *= 0.01D;
							
							ParticleHelper.spawnAttackParticleAt(this.pos.getX()+0.5D, this.pos.getY()+0.5D, this.pos.getZ()+0.5D, dx, dy, dz, (byte) 38);
						}
						
						if (this.tick % 32 == 0)
						{
							//draw point text
							if (this.lastPos.getY() > 0 || this.nextPos.getY() > 0)
							{
								String postext1 = "";
								String postext2 = "";
								int len1 = 0;
								int len2 = 0;
								
								postext1 = "F: " + TextFormatting.LIGHT_PURPLE + this.lastPos.getX() + ", " + this.lastPos.getY() + ", " + this.lastPos.getZ();
								len1 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext1);
								postext2 = "T: " + TextFormatting.AQUA + this.nextPos.getX() + ", " + this.nextPos.getY() + ", " + this.nextPos.getZ();
								len2 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext2);
								postext1 = postext1 + "\n" + TextFormatting.WHITE + postext2;
								len1 = len1 > len2 ? len1 : len2;
								
								ParticleHelper.spawnAttackParticleAt(postext1, this.pos.getX()+0.5D, this.pos.getY()+1.7D, this.pos.getZ()+0.5D, (byte) 0, 2, len1+1);
							}
							
							//draw circle mark
							ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-0.25D, pos.getZ()+0.5D, 0.2D, 8D, 0D, (byte) 25);
						}//end every 32 ticks
					}//end every 16 ticks
				}//end every 8 ticks
			}//end holding item
		}//end client side
		//server side
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
		}//end server side
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
			
			if (this.world.isRemote)
			{
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Waypoint_Set,
						1, this.world.provider.getDimension(), this.playerUID,
						this.pos.getX(), this.pos.getY(), this.pos.getZ(),
						this.nextPos.getX(), this.nextPos.getY(), this.nextPos.getZ()));
			}
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
			
			if (this.world.isRemote)
			{
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Waypoint_Set,
						0, this.world.provider.getDimension(), this.playerUID,
						this.pos.getX(), this.pos.getY(), this.pos.getZ(),
						this.lastPos.getX(), this.lastPos.getY(), this.lastPos.getZ()));
			}
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

	@Override
	public void setPlayerUID(int uid)
	{
		this.playerUID = uid;
		
		//sync uid to all around
		if (!this.world.isRemote)
		{
			PacketHelper.sendS2CEntitySync(0, this, this.world, this.pos, null);
		}
	}

	@Override
	public int getPlayerUID()
	{
		return this.playerUID;
	}

	@Override
	public Entity getHostEntity()
	{
		return null;
	}


}
