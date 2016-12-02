package com.lulan.shincolle.client.gui.inventory;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.tileentity.TileEntityDesk;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**SLOT POSITION
 * no slot
 */
public class ContainerDesk extends Container
{
	
	private TileEntityDesk tile;
	private InventoryPlayer playerInv;
	private EntityPlayer player;
	private CapaTeitoku capa;
	private int lenTemp, type, allyCD;
	private int[] valueTemp;
	
	
	/**
	 * type:
	 * 0: open gui with tile
	 * 1: open gui with radar item
	 * 2: open gui with book item
	 */
	public ContainerDesk(InventoryPlayer invPlayer, TileEntityDesk te, int type)
	{
		this.playerInv = invPlayer;
		this.tile = te;
		this.player = invPlayer.player;
		this.capa = CapaTeitoku.getTeitokuCapability(player);
		this.type = type;
		this.lenTemp = tile.getFieldCount();
		this.valueTemp = new int[this.lenTemp];
		
		//server side flag
		if (this.capa != null)
		{
			this.capa.setOpeningGUI(true);
		}
	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	/** Called when the container is closed, used for team list sync */
	@Override
    public void onContainerClosed(EntityPlayer player)
    {
    	//server side flag
		if (this.capa != null)
		{
			this.capa.setOpeningGUI(false);
		}
    }
	
	/** 禁用shift功能 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid)
	{
        return null;
    }
	
	//將container數值跟tile entity內的數值比對, 如果不同則發送更新給client使gui呈現新數值
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		//對所有開啟gui的人發送更新, 若數值有改變則發送更新封包
		for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            
            //檢查所有數值是否有改變
            int temp = 0;
            boolean update = false;
            
            if (this.allyCD != this.capa.getPlayerTeamCooldownInSec())
            {
            	listener.sendProgressBarUpdate(this, 0, this.capa.getPlayerTeamCooldownInSec());
            }
            
            for (int j = 0; j < this.lenTemp; j++)
            {
            	temp = this.tile.getField(j);
            	
            	//有部份數值需要用自訂封包來發送更新
            	if (this.valueTemp[j] != temp)
            	{
                    listener.sendProgressBarUpdate(this, j + 1, temp);
            	}
            }//end for all value temp
        }//end for all listener
		
		//更新container內的數值
		this.allyCD = this.capa.getPlayerTeamCooldownInSec();
		
		for (int k = 0; k < this.lenTemp; k++)
		{
			this.valueTemp[k] = this.tile.getField(k);
		}
            
    }
	
	//client端container接收新值
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.capa.setPlayerTeamCooldown(value * 20);  //second to tick
			break;
		default:
			this.tile.setField(id - 1, value);
			break;
		}
    }
	
	
}

