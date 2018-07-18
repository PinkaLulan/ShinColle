package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockWaypoint;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class TileEntityWaypoint extends BasicTileEntity implements ITileWaypoint, ITickable
{

	//waypoint
	private int tick, wpstay, playerUID;
	private BlockPos lastPos, nextPos, chestPos;
	public EntityPlayer owner;
	
	
	public TileEntityWaypoint()
	{
		super();
		this.tick = 0;
		this.wpstay = 0;
		this.playerUID = 0;
		this.lastPos = BlockPos.ORIGIN;
		this.nextPos = BlockPos.ORIGIN;
		this.chestPos = BlockPos.ORIGIN;
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
        int[] pos =  nbt.getIntArray("lastPos");
        if (pos == null || pos.length != 3) this.lastPos = BlockPos.ORIGIN;
        else this.lastPos = new BlockPos(pos[0], pos[1], pos[2]);
        
        pos =  nbt.getIntArray("nextPos");
        if (pos == null || pos.length != 3) this.nextPos = BlockPos.ORIGIN;
        else this.nextPos = new BlockPos(pos[0], pos[1], pos[2]);
        
        pos =  nbt.getIntArray("chestPos");
        if (pos == null || pos.length != 3) this.chestPos = BlockPos.ORIGIN;
        else this.chestPos = new BlockPos(pos[0], pos[1], pos[2]);
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("wpstay", wpstay);
		nbt.setInteger("pid", playerUID);
		
        //save pos
		if (this.lastPos != null) nbt.setIntArray("lastPos", new int[] {this.lastPos.getX(), this.lastPos.getY(), this.lastPos.getZ()});
		else nbt.setIntArray("lastPos", new int[] {0, 0, 0});
		
		if (this.nextPos != null) nbt.setIntArray("nextPos", new int[] {this.nextPos.getX(), this.nextPos.getY(), this.nextPos.getZ()});
		else nbt.setIntArray("nextPos", new int[] {0, 0, 0});
		
		if (this.chestPos != null) nbt.setIntArray("chestPos", new int[] {this.chestPos.getX(), this.chestPos.getY(), this.chestPos.getZ()});
		else nbt.setIntArray("chestPos", new int[] {0, 0, 0});
		
		return nbt;
	}
	
	@Override
	public void update()
	{
		//both side
		this.tick++;
		
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
				//every 8 ticks
				if ((this.tick & 7) == 0)
				{
					//draw arrow mark
					ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-0.25D, pos.getZ()+0.5D, 0.2D, 9D, 0D, (byte) 25);
					
					//every 16 ticks
					if ((this.tick & 15) == 0)
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
						
						//paired chest mark
						if (this.chestPos.getY() > 0)
						{
							double dx = this.chestPos.getX() - this.pos.getX();
							double dy = this.chestPos.getY() - this.pos.getY();
							double dz = this.chestPos.getZ() - this.pos.getZ();
							dx *= 0.01D;
							dy *= 0.01D;
							dz *= 0.01D;

							ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D,
																							dx, dy, dz, (byte) 39);
						}
						
						//every 32 ticks
						if ((this.tick & 31) == 0)
						{
							//draw point text
							if (this.lastPos.getY() > 0 || this.nextPos.getY() > 0 || this.chestPos.getY() > 0)
							{
								String name = "";
								String postext1 = "";
								String postext2 = "";
								String postext3 = "";
								int len0 = 0;
								int len1 = 0;
								int len2 = 0;
								int len3 = 0;
								
								if (this.owner != null)
								{
									name = TextFormatting.GREEN + this.owner.getName();
									len0 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(name);
								}
								
								postext1 = "F: " + TextFormatting.LIGHT_PURPLE + this.lastPos.getX() + ", " + this.lastPos.getY() + ", " + this.lastPos.getZ();
								len1 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext1);
								len1 = len1 > len0 ? len1 : len0;
								postext2 = "T: " + TextFormatting.AQUA + this.nextPos.getX() + ", " + this.nextPos.getY() + ", " + this.nextPos.getZ();
								len2 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext2);
								if (len1 < len2) len1 = len2;
								postext3 = "C: " + TextFormatting.YELLOW + this.chestPos.getX() + ", " + this.chestPos.getY() + ", " + this.chestPos.getZ();
								len3 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext3);
								if (len1 < len3) len1 = len3;
								postext1 = name + "\n" + TextFormatting.WHITE + postext1 + "\n" + TextFormatting.WHITE + postext2 + "\n" + TextFormatting.WHITE + postext3;
								
								ParticleHelper.spawnAttackParticleAt(postext1, this.pos.getX()+0.5D, this.pos.getY()+1.9D, this.pos.getZ()+0.5D, (byte) 0, 4, len1+1);
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
			//get owner entity
			if ((this.tick & 15) == 0 && this.owner == null && this.playerUID > 0)
			{
				this.owner = EntityHelper.getEntityPlayerByUID(this.playerUID);
			}
			
			//sync waypoint
			if (this.playerUID > 0 || this.lastPos.getY() > 0 || this.nextPos.getY() > 0 || this.chestPos.getY() > 0)
			{
				if (this.tick > 128)
				{
					this.tick = 0;
					this.sendSyncPacket();
				}
			}
		}//end server side
	}

	@Override
	public void setNextWaypoint(BlockPos pos)
	{
		if (pos != null)
		{
			this.nextPos = pos;
			
			//sync to client
			if (!this.world.isRemote) this.sendSyncPacket();
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
			
			//sync to client
			if (!this.world.isRemote) this.sendSyncPacket();
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
	
	@Override
	public void setPairedChest(BlockPos pos)
	{
		if (pos != null)
		{
			TileEntity tile = this.world.getTileEntity(pos);
			
			if (tile instanceof IInventory)
			{
				this.chestPos = pos;
			}
			
			//sync to client
			if (!this.world.isRemote) this.sendSyncPacket();
		}
	}

	@Override
	public BlockPos getPairedChest()
	{
		return this.chestPos;
	}


}