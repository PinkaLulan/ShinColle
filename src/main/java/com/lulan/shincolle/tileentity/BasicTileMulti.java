package com.lulan.shincolle.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.block.BasicBlockMulti;

/**BASIC MULTI BLOCK TILE ENTITY
 * tut by Lomeli
 * web: https://lomeli12.net/tutorials/tutorial-how-to-make-a-simple-multiblock-structure/
 */
abstract public class BasicTileMulti extends BasicTileInventory {

	protected TileEntity mastertile = null;
	protected boolean hasMaster, isMaster;					//master or servant flag
	protected int masterX, masterY, masterZ, structType;	//struct info
	protected String customName;
	
	
	public BasicTileMulti() {
		super();
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setInteger("masterX", masterX);
        data.setInteger("masterY", masterY);
        data.setInteger("masterZ", masterZ);
        data.setInteger("structType", structType);
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        masterX = data.getInteger("masterX");
        masterY = data.getInteger("masterY");
        masterZ = data.getInteger("masterZ");
        structType = data.getInteger("structType");
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");
    }

    //getter
    public int getStructType() {
    	return this.structType;
    }
    
    public TileEntity getMaster() {
    	if(this.mastertile != null) {
    		return this.mastertile;
    	}
    	else {
    		if(hasMaster) {
            	TileEntity tile = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
        		this.setMaster(tile);
        		return this.mastertile; 
            }
    	}
    	
    	return null;
    }
    
    public boolean hasMaster() {
        return this.hasMaster;
    }
    
    public boolean isMaster() {
        return this.isMaster;
    }
    
    public int getMasterX() {
        return this.masterX;
    }
    
    public int getMasterY() {
        return this.masterY;
    }
    
    public int getMasterZ() {
        return this.masterZ;
    }

    //setter
    public void setStructType(int type, World world) {
        //set block metadata
        //type 0001: meta=1:off 2:on  type 0010: meta=3:off 4:on
    	BasicBlockMulti.updateBlockState(world, this.xCoord, this.yCoord, this.zCoord, type);
    	this.structType = type;
    }
    
    /** set mater tile, separate from setHasMaster */
    public void setMaster(TileEntity master) {
    	if(master != null) {
    		this.mastertile = master;
    		this.setMasterCoords(master.xCoord, master.yCoord, master.zCoord);
    	}
    	else {
    		this.mastertile = null;
    	}
    }
    
    /** set master flag, separate from setMaster due to tile loading order problem */
    public void setHasMaster(boolean par1) {
    	this.hasMaster = par1;
    }
    
    public void setIsMaster(boolean par1) {
    	this.isMaster = par1;
    }
    
    public void setMasterCoords(int x, int y, int z) {
    	this.masterX = x;
    	this.masterY = y;
    	this.masterZ = z;
    }
    

}
