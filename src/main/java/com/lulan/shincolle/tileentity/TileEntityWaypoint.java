package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityWaypoint extends BasicTileEntity implements ITileWaypoint
{

	//waypoint
	public int lx, ly, lz, nx, ny, nz, tick, wpstay;
	
	
	public TileEntityWaypoint()
	{
		super();
		this.lx = -1;
		this.ly = -1;
		this.lz = -1;
		this.nx = -1;
		this.ny = -1;
		this.nz = -1;
		this.tick = 0;
		this.wpstay = 0;
	}
	
	@Override
	public void updateEntity() {
		tick++;
		
		//show client particle
		if(this.worldObj.isRemote) {
			//player hold waypoint or target wrench
			EntityPlayer player = ClientProxy.getClientPlayer();
			ItemStack item = player.inventory.getCurrentItem();
			
			if(item != null && (item.getItem() instanceof ItemBlockWaypoint || item.getItem() == ModItems.TargetWrench ||
			   (item.getItem() instanceof PointerItem && item.getItemDamage() < 3))) {
				if(this.tick % 8 == 0){
					ParticleHelper.spawnAttackParticleAt(xCoord+0.5D, yCoord-0.25D, zCoord+0.5D, 0.2D, 9D, 0D, (byte) 25);
					
					if(this.tick % 16 == 0) {
						//next point mark
						if(this.ny > 0) {
							double dx = nx - this.xCoord;
							double dy = ny - this.yCoord;
							double dz = nz - this.zCoord;
							dx *= 0.01D;
							dy *= 0.01D;
							dz *= 0.01D;
									
							ParticleHelper.spawnAttackParticleAt(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, dx, dy, dz, (byte) 38);
						}
						
						if(this.tick % 32 == 0) {
							//circle mark
							ParticleHelper.spawnAttackParticleAt(xCoord+0.5D, yCoord-0.25D, zCoord+0.5D, 0.2D, 8D, 0D, (byte) 25);
						}//end every 32 ticks
					}//end every 16 ticks
				}//end every 8 ticks
			}//end holding item
		}//end client side
		else {
			//sync waypoint
			if(this.ly > 0 || this.ny > 0) {
				if(this.tick >= 256) {
					this.tick = 0;
					this.sendSyncPacket();
				}
			}
		}
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound compound)
	{
        super.readFromNBT(compound);	//從nbt讀取方塊的xyz座標
        
        lx = compound.getInteger("LastX");
        ly = compound.getInteger("LastY");
        lz = compound.getInteger("LastZ");
        nx = compound.getInteger("NextX");
        ny = compound.getInteger("NextY");
        nz = compound.getInteger("NextZ");
        wpstay = compound.getInteger("wpstay");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		compound.setInteger("LastX", lx);
		compound.setInteger("LastY", ly);
		compound.setInteger("LastZ", lz);
		compound.setInteger("NextX", nx);
		compound.setInteger("NextY", ny);
		compound.setInteger("NextZ", nz);
		compound.setInteger("wpstay", wpstay);
	}
	
	@Override
	public void sendSyncPacket()
	{
		if (!this.worldObj.isRemote)
		{
			TargetPoint point = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64D);
			CommonProxy.channelG.sendToAllAround(new S2CGUIPackets(this), point);
		}
	}
	
	public void setSyncData(int[] data)
	{
		if (data != null)
		{
			this.lx = data[0];
			this.ly = data[1];
			this.lz = data[2];
			this.nx = data[3];
			this.ny = data[4];
			this.nz = data[5];
		}
	}

	@Override
	public void setNextWaypoint(int[] next)
	{
		if (next != null)
		{
			this.nx = next[0];
			this.ny = next[1];
			this.nz = next[2];
		}
	}

	@Override
	public int[] getNextWaypoint() {
		return new int[] {nx, ny, nz};
	}

	@Override
	public void setLastWaypoint(int[] next)
	{
		if(next != null) {
			this.lx = next[0];
			this.ly = next[1];
			this.lz = next[2];
		}
	}

	@Override
	public int[] getLastWaypoint()
	{
		return new int[] {lx, ly, lz};
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
