package com.lulan.shincolle.client.gui.inventory;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.item.BasicEquip;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**CUSTOM SHIP INVENTORY
 * slot: S0(136,18) S1(136,36) S2(136,54) S3(136,72) S4(136,90) S5(6,108) S6~S23(8,18) 6x3
 * player inventory(44,132) hotbar(44,190)
 * S0~S5 for equip only
 */
public class ContainerShipInventory extends Container
{
	
	private BasicEntityShip entity;
	public static final byte SLOTS_PLAYERINV = 24;  //player inventory start id
	public static final byte SLOTS_SHIPINV = 6;		//ship inventory start id
	private int lenTemp;
	private int[] valueTemp;
	
	
	public ContainerShipInventory(InventoryPlayer invPlayer, BasicEntityShip entity)
	{
		int i, j;
		this.entity = entity;
		this.lenTemp = entity.getFieldCount();
		this.valueTemp = new int[this.lenTemp];
		
		//ship equip = 0~5
		for (i = 0; i < 6; i++)
		{
			this.addSlotToContainer(new SlotShipInventory(entity.getCapaShipInventory(), i, 144, 18 + i * 18));
		}
		
		//ship inventory = 6~24
		for (i = 0; i < 6; i++)
		{
			for (j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new SlotShipInventory(entity.getCapaShipInventory(), j + i * 3 + 6, 8 + j * 18, 18 + i * 18));
			}
		}
		
		//player inventory
		for (i = 0; i < 3; i++)
		{
			for (j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 132 + i * 18));
			}
		}
		
		//player action bar (hot bar)
		for (i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 190));
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	/**使container支援shift點物品的動作
	 * shift點人物背包中的物品->判定物品類型送到指定格子, 點container中的物品->送到人物背包
	 * mergeItemStack: parm: item,start slot,end slot(此格不判定放入),是否由hotbar開始判定
	 * slot id: 0~4:equip  5~22:ship inventory 
	 *          23~49:player inventory  50~58:hot bar
	 *          
	 * Click: slot 0~5   (Equip)   -> put in slot 6~59 (ShipInv & Player)
	 *        slot 6~23  (ShipInv) -> if equip -> slot 0~5 (Equip)
	 *                             -> if other -> slot 24~59 (Player)
	 *        slot 24~59 (Player)  -> if equip -> slot 0~5 (Equip)
	 *        					   -> if other -> slot 6~23 (ShipInv)
	 *        
	 * Equip slot check in SlotShipInventory.class 
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid)
	{
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotid);
        boolean isEquip = false;
        int slotsEnd = SLOTS_PLAYERINV + 36;

        if (slot != null && slot.getHasStack())
        { 													//若slot有東西
            ItemStack itemstack1 = slot.getStack();			//itemstack1取得該slot物品
            itemstack = itemstack1.copy();					//itemstack複製一份itemstack1
            
            if (itemstack1.getItem() instanceof BasicEquip) isEquip = true;	//判定是否為equip

            if (slotid < SLOTS_SHIPINV)
            {  		//click equip slot
            	if (!this.mergeItemStack(itemstack1, SLOTS_SHIPINV, slotsEnd, true))
            	{	//take out equip
                	return null;
                }	
                slot.onSlotChange(itemstack1, itemstack); //若物品成功搬動過, 則呼叫slot change事件
            }
            else
            {							//slot is ship or player inventory (5~58)
            	if (slotid < SLOTS_PLAYERINV)
            	{						//if ship inventory (0~23)
            		if (isEquip)
            		{					//把equip塞進slot 0~4, 塞不下則放player inventory (24~58)
            			if (!this.mergeItemStack(itemstack1, 0, SLOTS_SHIPINV, false))
            			{
                			if (!this.mergeItemStack(itemstack1, SLOTS_PLAYERINV, slotsEnd, true))
                			{
                				return null;
                			}			
                        }
            		}  
            		else
            		{					//non-equip, put into player inventory (23~58)
            			if (!this.mergeItemStack(itemstack1, SLOTS_PLAYERINV, slotsEnd, true))
            			{
            				return null;
            			}
            		}
            	}
            	else
            	{						//if player inventory (23~58)
            		if (isEquip)
            		{					//把equip塞進slot 0~4, 塞不下則放ship inventory (5~22)
            			if (!this.mergeItemStack(itemstack1, 0, SLOTS_SHIPINV, false))
            			{
                			if (!this.mergeItemStack(itemstack1, SLOTS_SHIPINV, SLOTS_PLAYERINV, true))
                			{
                				return null;
                			}			
                        }
            		} 
            		else
            		{					//non-equip, put into ship inventory (5~22)
            			if (!this.mergeItemStack(itemstack1, SLOTS_SHIPINV, SLOTS_PLAYERINV, false))
            			{
            				return null;
            			}
            		}
            	}
            }

            //如果物品都放完了, 則設成null清空該物品
            if (itemstack1.stackSize <= 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {	//還沒放完, 先跑一次slot update
                slot.onSlotChanged();
            }

            //如果itemstack的數量跟原先的數量相同, 表示都不能移動物品
            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }
            
            //最後再發送一次slot update
            slot.onPickupFromSlot(player, itemstack1);
        }
        
        return itemstack;	//物品移動完成, 回傳剩下的物品
    }
	
	//偵測數值是否改變, 有改變時發送更新(此為server端偵測)
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		int getValue;
		float getValueF;
		
		//對所有開啟gui的人發送更新, 若數值有改變則發送更新封包
		for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener listener = (IContainerListener) this.listeners.get(i);
            
            //檢查所有數值是否有改變
            int temp = 0;
            boolean update = false;
            
            for (int j = 0; j < this.lenTemp; j++)
            {
            	temp = this.entity.getField(j);
            	
            	//有部份數值需要用自訂封包來發送更新
            	if (this.valueTemp[j] != temp)
            	{
                   	switch (j)
                	{
                	case 25:
                	case 26:
                	case 27:	//發送自訂封包更新
                		this.entity.sendGUISyncPacket();
                		break;
            		default:	//使用vanilla方法更新
                    	listener.sendProgressBarUpdate(this, j, temp);
            			break;
                	}
            	}
            }//end for all value temp
        }//end for all listener
		
		//更新container內的數值
		for (int k = 0; k < this.lenTemp; k++)
		{
			this.valueTemp[k] = this.entity.getField(k);
		}
		
    }
	
	//client端container接收新值
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
	{     
		this.entity.setField(id, value);
    }
	
	
}
