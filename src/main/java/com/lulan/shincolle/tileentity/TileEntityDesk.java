package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityDesk extends BasicTileEntity {

	public int guiFunc = 0;
	
	//radar
	public int radar_zoomLv = 0;
	
	//book
	public int book_chap = 0;
	public int book_page = 0;
	
	
	public TileEntityDesk() {
		this.slots = new ItemStack[10];
	}
	
	@Override
	public int getFuelSlotMin() {
		return 0;
	}

	@Override
	public int getFuelSlotMax() {
		return 0;
	}

	//GUI顯示的名稱, 有custom name則用, 不然就用預設名稱
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container."+Reference.MOD_ID+":BlockDesk";
	}
	
	//是否可以右鍵點開方塊
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		//由於會有多個tile entity副本, 要先確認座標相同的副本才能使用
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		else {	//確認player要在該tile entity 8格內, 以免超出讀取範圍 or 產生其他不明bug
			return player.getDistanceSq(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D) <= 64;
		}
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
