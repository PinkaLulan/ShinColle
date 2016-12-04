package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockDesk;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDesk extends BasicTileEntity
{

	private int guiFunc = 0;
	private int radar_zoomLv = 0;
	private int book_chap = 0;
	private int book_page = 0;
	
	
	public TileEntityDesk()
	{
		super();
	}
	
	@Override
	public String getRegName()
	{
		return BlockDesk.TILENAME;
	}
	
	@Override
	public byte getGuiIntID()
	{
		return ID.Gui.ADMIRALDESK;
	}
	
	@Override
	public byte getPacketID(int type)
	{
		switch (type)
		{
		case 0:
			return S2CGUIPackets.PID.TileDesk;
		}
		
		return -1;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);	//從nbt讀取方塊的xyz座標
        
        guiFunc = nbt.getInteger("guiFunc");
        radar_zoomLv = nbt.getInteger("radarZoom");
        book_chap = nbt.getInteger("bookChap");
        book_page = nbt.getInteger("bookPage");
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("guiFunc", guiFunc);
		nbt.setInteger("radarZoom", radar_zoomLv);
		nbt.setInteger("bookChap", book_chap);
		nbt.setInteger("bookPage", book_page);
		
		return nbt;
	}
	
	@Override
	public void sendSyncPacketC2S()
	{
		if (this.worldObj.isRemote)
		{
			int[] data = new int[4];
			data[0] = this.guiFunc;
			data[1] = this.book_chap;
			data[2] = this.book_page;
			data[3] = this.radar_zoomLv;
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this, C2SGUIPackets.PID.Desk_FuncSync, data));
		}
	}

	/** FIELD相關方法
	 *  使其他mod或class也能存取該tile的內部值
	 *  ex: gui container可用get/setField來更新數值
	 *  
	 *  field id:
	 *  0:gui function, 1:book chap, 2:book page, 3:radar zoom lv
	 */
	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.guiFunc;
		case 1:
			return this.book_chap;
		case 2:
			return this.book_page;
		case 3:
			return this.radar_zoomLv;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.guiFunc = value;
			break;
		case 1:
			this.book_chap = value;
			break;
		case 2:
			this.book_page = value;
			break;
		case 3:
			this.radar_zoomLv = value;
			break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 4;
	}
	
	
}
