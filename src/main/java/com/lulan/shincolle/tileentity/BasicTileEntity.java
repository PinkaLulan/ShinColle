package com.lulan.shincolle.tileentity;

import net.minecraft.tileentity.TileEntity;

public class BasicTileEntity extends TileEntity {
	
	protected String customName;
	
	public BasicTileEntity() {
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

}
