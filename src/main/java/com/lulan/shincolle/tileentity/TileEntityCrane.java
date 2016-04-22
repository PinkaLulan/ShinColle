package com.lulan.shincolle.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCrane extends BasicTileEntity {

	private boolean isPaired;		//is paired
	private int cx, cy, cz;			//paired chest position
	private int delayMax, delay;	//load, unload delay
	
	
	public TileEntityCrane() {
		isPaired = false;
		cx = 0;
		cy = 0;
		cz = 0;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        
        isPaired = nbt.getBoolean("paired");
        cx = nbt.getInteger("cx");
        cy = nbt.getInteger("cy");
        cz = nbt.getInteger("cz");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
        
        nbt.setBoolean("paired", isPaired);
        nbt.setInteger("cx", cx);
        nbt.setInteger("cy", cy);
        nbt.setInteger("cz", cz);
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
	
	@Override
	public void updateEntity() {
		
	}
	
	
}
