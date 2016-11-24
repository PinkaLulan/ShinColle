package com.lulan.shincolle.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityDesk extends BasicTileLockable {

	public int guiFunc = 0;
	
	//radar
	public int radar_zoomLv = 0;
	
	//book
	public int book_chap = 0;
	public int book_page = 0;
	
	
	public TileEntityDesk() {
		super();
	}

	public boolean canUpdate() {
        return false;
    }
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);	//從nbt讀取方塊的xyz座標
        
        guiFunc = compound.getInteger("guiFunc");
        radar_zoomLv = compound.getInteger("radarZoom");
        book_chap = compound.getInteger("bookChap");
        book_page = compound.getInteger("bookPage");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("guiFunc", guiFunc);
		compound.setInteger("radarZoom", radar_zoomLv);
		compound.setInteger("bookChap", book_chap);
		compound.setInteger("bookPage", book_page);
	}
	
	@Override
	public void sendSyncPacketC2S() {
		if(this.worldObj.isRemote) {
			int[] data = new int[4];
			data[0] = this.guiFunc;
			data[1] = this.book_chap;
			data[2] = this.book_page;
			data[3] = this.radar_zoomLv;
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this, ID.B.Desk_Sync, data));
		}
	}
	
	@Override
	public void sendSyncPacket() {
		if(!this.worldObj.isRemote) {
//			LogHelper.info("DEBUG : desk sync s2c "+this.guiFunc+" "+this.book_chap+" "+this.book_page);
			TargetPoint point = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 8D);
			CommonProxy.channelG.sendToAllAround(new S2CGUIPackets(this), point);
		}
	}
	
	public void setSyncData(int[] data) {
		if(data != null) {
//			LogHelper.info("DEBUG : desk sync: "+data[1]);
			this.guiFunc = data[0];
			this.book_chap = data[1];
			this.book_page = data[2];
			this.radar_zoomLv = data[3];
		}
	}
	

}
