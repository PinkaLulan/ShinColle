package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**SLOT POSITION
 * S1:grudge(33,29) S2:abyssium(53,29) S3:ammo(73,29) S4:poly(93,29)
 * fuel(8,53) fuel bar(10,48 height=30) fuel color bar(176,46)
 * ship button(123,17) equip button(143,17)
 * output(134,44) player inv(8,87) action bar(8,145)
 */
public class ContainerDesk extends Container {
	
	private TileEntityDesk tile;
	private int guiFunc, bookChap, bookPage;
	
	
	public ContainerDesk(InventoryPlayer invPlayer, TileEntityDesk te) {
		this.tile = te;

	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
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
		crafting.sendProgressBarUpdate(this, 1, this.tile.guiFunc);
		crafting.sendProgressBarUpdate(this, 2, this.tile.book_chap);
		crafting.sendProgressBarUpdate(this, 3, this.tile.book_page);
	}
	
	//將container數值跟tile entity內的數值比對, 如果不同則發送更新給client使gui呈現新數值
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

//        for(Object crafter : this.crafters) {
//            ICrafting icrafting = (ICrafting) crafter;
//            
//            if(this.guiFunc != this.tile.guiFunc ||
//               this.bookChap != this.tile.book_chap ||
//               this.bookPage != this.tile.book_page) {
//                icrafting.sendProgressBarUpdate(this, 1, this.tile.guiFunc);
//                icrafting.sendProgressBarUpdate(this, 2, this.tile.book_chap);
//                icrafting.sendProgressBarUpdate(this, 3, this.tile.book_page);
//                this.guiFunc = this.tile.guiFunc;
//                this.bookChap = this.tile.book_chap;
//                this.bookPage = this.tile.book_page;
//                this.tile.sendSyncPacket();
//            }
//        }
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
		}
    }

	
}

