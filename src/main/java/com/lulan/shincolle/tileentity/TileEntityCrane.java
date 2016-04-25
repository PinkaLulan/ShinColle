package com.lulan.shincolle.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.lulan.shincolle.entity.BasicEntityShip;

public class TileEntityCrane extends BasicTileEntity implements ITileWaypoint {

	//pos: lastXYZ, nextXYZ, chestXYZ
	public int lx, ly, lz, nx, ny, nz, cx, cy, cz, tick, playerUID;
	
	//crane
	public boolean isActive;		//is active
	public boolean isPaired;		//is paired
	
	//target
	public BasicEntityShip ship;
	
	
	public TileEntityCrane() {
		ship = null;
		isActive = false;
		isPaired = false;
		playerUID = 0;
		tick = 0;
		cx = -1;
		cy = -1;
		cz = -1;
		lx = -1;
		ly = -1;
		lz = -1;
		nx = -1;
		ny = -1;
		nz = -1;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        
        isActive = nbt.getBoolean("active");
        isPaired = nbt.getBoolean("paired");
        playerUID = nbt.getInteger("uid");
        cx = nbt.getInteger("cx");
        cy = nbt.getInteger("cy");
        cz = nbt.getInteger("cz");
        lx = nbt.getInteger("lx");
        ly = nbt.getInteger("ly");
        lz = nbt.getInteger("lz");
        nx = nbt.getInteger("nx");
        ny = nbt.getInteger("ny");
        nz = nbt.getInteger("nz");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
        
		nbt.setBoolean("active", isActive);
        nbt.setBoolean("paired", isPaired);
        nbt.setInteger("uid", playerUID);
        nbt.setInteger("cx", cx);
        nbt.setInteger("cy", cy);
        nbt.setInteger("cz", cz);
        nbt.setInteger("lx", lx);
        nbt.setInteger("ly", ly);
        nbt.setInteger("lz", lz);
        nbt.setInteger("nx", nx);
        nbt.setInteger("ny", ny);
        nbt.setInteger("nz", nz);
	}
	
	//set paired chest
	public void setPairedChest(int x, int y, int z) {
		if(this.worldObj.getTileEntity(x, y, z) instanceof IInventory) {
			this.cx = x;
			this.cy = y;
			this.cz = z;
			this.isPaired = true;
		}
	}
	
	//get paired chest
	public TileEntity getPairedChest() {
		if(this.isPaired) {
			TileEntity tile = this.worldObj.getTileEntity(cx, cy, cz);
			
			if(tile instanceof IInventory) {
				return tile;
			}
			else {
				//tile lost, reset
				this.isPaired = false;
				this.cx = -1;
				this.cy = -1;
				this.cz = -1;
				return null;
			}
		}
		
		return null;
	}
	
	//set data from packet data
	public void setSyncData(int[] data) {
		if(data != null) {
			this.lx = data[0];
			this.ly = data[1];
			this.lz = data[2];
			this.nx = data[3];
			this.ny = data[4];
			this.nz = data[5];
			
			setPairedChest(data[6], data[7], data[8]);
		}
	}
	
	@Override
	public void setNextWaypoint(int[] next) {
		if(next != null) {
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
	public void setLastWaypoint(int[] next) {
		if(next != null) {
			this.lx = next[0];
			this.ly = next[1];
			this.lz = next[2];
		}
	}

	@Override
	public int[] getLastWaypoint() {
		return new int[] {lx, ly, lz};
	}
	
	@Override
	public void updateEntity() {
		//server side
		if(!worldObj.isRemote) {
			//can work
			if(isActive && isPaired) {
				
			}
		}//end server side
	}
	
	
	
	
}
