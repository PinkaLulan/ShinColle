package com.lulan.shincolle.inventory;

import com.lulan.shincolle.crafting.SmallRecipes;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

/**SLOT POSITION
 * S1:grudge(33,29) S2:abyssium(53,29) S3:ammo(73,29) S4:poly(93,29)
 * fuel(8,53) fuel bar(10,48 height=30) fuel color bar(176,46) 
 * arrow(113,29 len=24) arrow color bar(176,0)
 * output(145,29) player inv(8,87) action bar(8,145) 
 */
public class ContainerSmallShipyard extends Container {
	
	private TileEntitySmallShipyard te;
	private int guiConsumedPower;
	private int guiRemainedPower;
	private int guiGoalPower;
	private String guiBuildTime;
	
	
	public ContainerSmallShipyard(InventoryPlayer invPlayer, TileEntitySmallShipyard teSmallShipyard) {
		this.te = teSmallShipyard;
		guiConsumedPower = 0;
		guiRemainedPower = 0;
			
		this.addSlotToContainer(new SlotSmallShipyard(teSmallShipyard, 0, 33, 29));  //grudge
		this.addSlotToContainer(new SlotSmallShipyard(teSmallShipyard, 1, 53, 29));  //abyssium
		this.addSlotToContainer(new SlotSmallShipyard(teSmallShipyard, 2, 73, 29));  //ammo
		this.addSlotToContainer(new SlotSmallShipyard(teSmallShipyard, 3, 93, 29));  //poly
		this.addSlotToContainer(new SlotSmallShipyard(teSmallShipyard, 4, 8, 53));   //fuel
		this.addSlotToContainer(new SlotSmallShipyard(teSmallShipyard, 5, 145, 29)); //output
		
		//player inventory
		for(int i=0; i<3; i++) {
			for(int j=0; j<9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j+i*9+9, 8+j*18, 87+i*18));
			}
		}
		
		//player action bar (hot bar)
		for(int i=0; i<9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8+i*18, 145));
		}
	}
	
	//發送更新gui進度條更新, 比detectAndSendChanges還要優先(在此放置init方法等)
	@Override
	public void addCraftingToCrafters (ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.te.consumedPower);	 //發送新值更新build進度條
		crafting.sendProgressBarUpdate(this, 1, this.te.remainedPower);  //發送新值更新fuel進度條
	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUseableByPlayer(player);
	}
	
	/**使container支援shift點物品的動作, 此為ContainerFurnace中直接複製過來修改
	 * shift點人物背包中的物品->判定物品類型送到指定格子, 點container中的物品->送到人物背包
	 * mergeItemStack: parm: item,start slot,end slot(此格不判定放入),是否先放到hot bar
	 * slot id: 0:grudge 1:abyssium 2:ammo 3:polymetal 4:fuel 5:output
	 *          6~32:player inventory 33~41:hot bar
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotid);
        byte itemID = -1;

        if(slot != null && slot.getHasStack()) { 			//若slot有東西
            ItemStack itemstack1 = slot.getStack();			//itemstack1取得該slot物品
            itemstack = itemstack1.copy();					//itemstack複製一份itemstack1
            itemID = SmallRecipes.getMaterialID(itemstack1);//取得物品種類(對應slot id)

            if(slotid == 5) {  //若點擊output slot時
            	//將output slot的物品嘗試跟player inventory or hot bar的slot合併, 不能合併則傳回null
            	if(!this.mergeItemStack(itemstack1, 6, 42, true)) {
                	return null;
                }
                slot.onSlotChange(itemstack1, itemstack); //若物品成功搬動過, 則呼叫slot change事件
            }
            //如果是點擊player inventory slot時, 判定是否能丟進input slot
            else if(slotid > 5) {        	          	
            	if(itemID >= 0) {  //item ID: -1:other 0~3:material 4:fuel
            		if(!this.mergeItemStack(itemstack1, itemID, itemID+1, false)) { //嘗試塞進slot 0~4中
            			return null;
                    }
                }
            	//若物品不是可用材料, 且物品在player inventory, 則改放到hot bar
                else if (slotid > 5 && slotid < 33) {
                	if (!this.mergeItemStack(itemstack1, 33, 42, false)) {
                        return null;
                    }
                }
            	//若物品不是可用材料, 且物品在hot bar, 則改放到player inventory
                else if (slotid > 32 && !this.mergeItemStack(itemstack1, 6, 33, false)) {
                    return null;
                }
            }
            //如果是點擊slot 0~4, 則改放到player inventory or hot bar
            else if (!this.mergeItemStack(itemstack1, 6, 42, false)) {
                return null;
            }

            //如果物品都放完了, 則設成null清空該物品
            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else { //還沒放完, 先跑一次slot update
                slot.onSlotChanged();
            }

            //如果itemstack的數量跟原先的數量相同, 表示都不能移動物品
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            //最後再發送一次slot update
            slot.onPickupFromSlot(player, itemstack1);
        }
        return itemstack;	//物品移動完成, 回傳剩下的物品
    }
	
	//將container數值跟tile entity內的數值比對, 如果不同則發送更新使gui呈現新數值
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

        for(Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if(this.guiConsumedPower != this.te.consumedPower) {  	//更新build進度條
                icrafting.sendProgressBarUpdate(this, 0, this.te.consumedPower);              
            }
            
            if(this.guiGoalPower != this.te.goalPower) {  			//更新build進度條
                 icrafting.sendProgressBarUpdate(this, 1, this.te.goalPower);               
             }

            if(this.guiRemainedPower != this.te.remainedPower) {  	//更新fuel存量條
                icrafting.sendProgressBarUpdate(this, 2, this.te.remainedPower);
            }
        }
        //將container值設為tile entity目前值
        this.guiGoalPower = this.te.goalPower;
        this.guiConsumedPower = this.te.consumedPower;
        this.guiRemainedPower = this.te.remainedPower;
    }

	//client端container接收新值
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int valueType, int updatedValue) {
        
		switch(valueType) {
		case 0: 
			this.te.consumedPower = updatedValue;
			break;
		case 1:
			this.te.goalPower = updatedValue;
			break;
		case 2:
			this.te.remainedPower = updatedValue;
			break;
		}
    }

	
}
