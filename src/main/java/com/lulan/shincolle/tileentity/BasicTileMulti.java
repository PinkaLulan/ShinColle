package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BasicBlockMulti;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**BASIC MULTI BLOCK TILE ENTITY
 * tut by Lomeli
 * web: https://lomeli12.net/tutorials/tutorial-how-to-make-a-simple-multiblock-structure/
 */
public class BasicTileMulti extends TileEntity {
	private boolean hasMaster, isMaster;				//master or servant flag
	private int masterX, masterY, masterZ, structType;	//struct info
	private String customName;
	
	public BasicTileMulti() {
        customName = "";
    }
	
	public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
    
    public boolean hasCustomName() {
        return customName != null && customName.length() > 0;
    }
    
    @Override
    public void updateEntity() {
    	super.updateEntity();
    	
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
        
        if(isMaster()) {  //已經成形, 額外儲存資料

        }
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
        
        if(isMaster()) {  //已經成形, 額外儲存資料

        }
    }

    //getter
    public int getStructType() {
    	return structType;
    }
    public boolean hasMaster() {
        return hasMaster;
    }
    public boolean isMaster() {
        return isMaster;
    }
    public int getMasterX() {
        return masterX;
    }
    public int getMasterY() {
        return masterY;
    }
    public int getMasterZ() {
        return masterZ;
    }

    //setter
    public void setStructType(int type) {
        structType = type;
        //set block metadata
        //type 0: meta=1:on 2:off  type1: meta=3:on 4:off
    	BasicBlockMulti.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord, structType * 2);
    }
    public void setHasMaster(boolean bool) {
        hasMaster = bool;
    }
    public void setIsMaster(boolean bool) {
        isMaster = bool;
    }
    public void setMasterCoords(int x, int y, int z) {
        masterX = x;
        masterY = y;
        masterZ = z;
    }
    

}
