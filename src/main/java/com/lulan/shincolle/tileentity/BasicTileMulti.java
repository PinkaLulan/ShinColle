package com.lulan.shincolle.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.lulan.shincolle.block.BasicBlockMulti;

/**BASIC MULTI BLOCK TILE ENTITY
 * tut by Lomeli
 * web: https://lomeli12.net/tutorials/tutorial-how-to-make-a-simple-multiblock-structure/
 */
abstract public class BasicTileMulti extends BasicTileEntity {

	protected boolean hasMaster, isMaster;					//master or servant flag
	protected int masterX, masterY, masterZ, structType;	//struct info
	protected String customName;
	
	
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
    public void setStructType(int type, World world) {
        //set block metadata
        //type 0001: meta=1:off 2:on  type 0010: meta=3:off 4:on
    	BasicBlockMulti.updateBlockState(world, this.xCoord, this.yCoord, this.zCoord, type);
    	structType = type;
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
