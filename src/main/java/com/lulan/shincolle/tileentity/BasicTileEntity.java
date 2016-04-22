package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

abstract public class BasicTileEntity extends TileEntity {
	
	protected String customName;
	protected int syncTime;		//for sync
	
	
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
	
	//sync data for GUI display
	public void sendSyncPacket() {
		if(!this.worldObj.isRemote) {
			TargetPoint point = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 48D);
			CommonProxy.channelG.sendToAllAround(new S2CGUIPackets(this), point);
		}
	}
	
	//sync data client to server
	public void sendSyncPacketC2S() {}
	

}
