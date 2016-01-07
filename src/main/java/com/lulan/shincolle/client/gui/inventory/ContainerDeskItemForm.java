package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.entity.ExtendPlayerProps;

/**SLOT POSITION
 * no slot
 */
public class ContainerDeskItemForm extends Container {
	
	private InventoryPlayer playerInv;
	private EntityPlayer player;
	private ExtendPlayerProps extProps;
	private int bookChap, bookPage;
	
	
	public ContainerDeskItemForm(InventoryPlayer invPlayer, EntityPlayer player) {
		this.playerInv = invPlayer;
		this.player = player;
		this.extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		//server side flag
		if(this.extProps != null) {
			this.extProps.setIsOpeningGUI(true);
		}
	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	/** Called when the container is closed */
    public void onContainerClosed(EntityPlayer player) {
    	ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
    	
    	//server side flag
		if(props != null) {
			props.setIsOpeningGUI(false);
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
	
//	//發送gui更新, 每次開啟方塊時呼叫一次 
//	@Override
//	public void addCraftingToCrafters (ICrafting crafting) {
//		super.addCraftingToCrafters(crafting);
//	}
	
//	//將container數值跟tile entity內的數值比對, 如果不同則發送更新給client使gui呈現新數值
//	@Override
//	public void detectAndSendChanges() {
//		super.detectAndSendChanges();
//    }

//	//client端container接收新值
//	@Override
//	@SideOnly(Side.CLIENT)
//    public void updateProgressBar(int valueType, int updatedValue) {
//		switch(valueType) {
//		case 2:
//			this.tile.book_chap = updatedValue;
//			break;
//		}
//    }

	
}


