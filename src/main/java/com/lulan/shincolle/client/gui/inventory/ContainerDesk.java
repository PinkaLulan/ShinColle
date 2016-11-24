package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.tileentity.TileEntityDesk;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**SLOT POSITION
 * no slot
 */
public class ContainerDesk extends Container {
	
	private TileEntityDesk tile;
	private InventoryPlayer playerInv;
	private EntityPlayer player;
	private ExtendPlayerProps extProps;
	private int guiFunc, bookChap, bookPage, allyCD, type;
	
	
	public ContainerDesk(InventoryPlayer invPlayer, TileEntityDesk te, EntityPlayer player, int type) {
		this.playerInv = invPlayer;
		this.tile = te;
		this.player = player;
		this.extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		this.type = type;
		
		//server side flag
		if(this.extProps != null) {
			this.extProps.setOpeningGUI(true);
		}
	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	/** Called when the container is closed, used for team list sync */
    public void onContainerClosed(EntityPlayer player) {
    	ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
    	
    	//server side flag
		if(props != null) {
			props.setOpeningGUI(false);
		}
    }
	
	/**使container支援shift點物品的動作, 此為ContainerFurnace中直接複製過來修改
	 * shift點人物背包中的物品->判定物品類型送到指定格子, 點container中的物品->送到人物背包
	 * mergeItemStack: parm: item,start slot,end slot(此格不判定放入),是否先放到hot bar
	 *        
	 * slot id: 0~26:player inventory  27~35:hot bar        
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
        return null;
    }
	
	//發送gui更新, 每次開啟方塊時呼叫一次 
	@Override
	public void addCraftingToCrafters (ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		if(type == 0) {
			crafting.sendProgressBarUpdate(this, 1, this.tile.guiFunc);
			crafting.sendProgressBarUpdate(this, 2, this.tile.book_chap);
			crafting.sendProgressBarUpdate(this, 3, this.tile.book_page);
			crafting.sendProgressBarUpdate(this, 4, this.tile.radar_zoomLv);
			crafting.sendProgressBarUpdate(this, 5, this.extProps.getPlayerTeamCooldownInSec());
		}
	}
	
	//將container數值跟tile entity內的數值比對, 如果不同則發送更新給client使gui呈現新數值
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if(type == 0) {	
	        for(Object crafter : this.crafters) {
	            ICrafting icrafting = (ICrafting) crafter;
	            
	            if(this.allyCD != this.extProps.getPlayerTeamCooldownInSec()) {
	                icrafting.sendProgressBarUpdate(this, 5, this.extProps.getPlayerTeamCooldownInSec());
	                this.allyCD = this.extProps.getPlayerTeamCooldownInSec();
	            }
	        }
		}
    }

	//client端container接收新值
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int valueType, int updatedValue) {
		switch(valueType) {
		case 1:
			this.tile.guiFunc = updatedValue;
			break;
		case 2:
			this.tile.book_chap = updatedValue;
			break;
		case 3:
			this.tile.book_page = updatedValue;
			break;
		case 4:
			this.tile.radar_zoomLv = updatedValue;
			break;
		case 5:
			this.extProps.setPlayerTeamCooldown(updatedValue * 20);  //second to tick
			break;
		}
    }

	
}

