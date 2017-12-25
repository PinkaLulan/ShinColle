package com.lulan.shincolle.client.gui.inventory;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**CUSTOM SHIP INVENTORY
 * slot: S0(136,18) S1(136,36) S2(136,54) S3(136,72) S4(136,90) S5(6,108) S6~S23(8,18) 6x3
 * player inventory(44,132) hotbar(44,190)
 * S0~S5 for equip only
 */
public class ContainerMorphInventory extends Container
{
	
	public static final byte SLOTS_SHIPINV = 6;		//ship inventory start id
	
	private BasicEntityShip entity;
	private CapaTeitoku capa;
	private int lenTemp;
	private int[] valueTemp;
	private boolean needInit;
	
	
	public ContainerMorphInventory(CapaTeitoku capa, IInventory invPlayer, BasicEntityShip entity)
	{
		int i, j;
		this.capa = capa;
		this.entity = entity;
		this.lenTemp = entity.getFieldCount();
		this.valueTemp = new int[this.lenTemp];
		this.needInit = true;
		
		//equip from player's capa inventory
		for (i = 0; i < 6; i++)
		{
			this.addSlotToContainer(new SlotMorphInventory(capa, i, 144, 18 + i * 18));
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
        Slot slot = (Slot) this.inventorySlots.get(slotid);
        boolean isEquip = false;
        int slotsEnd = SLOTS_SHIPINV + 36;

        if (slot != null && slot.getHasStack())
        { 													//若slot有東西
            ItemStack itemstack1 = slot.getStack();			//itemstack1取得該slot物品
            itemstack = itemstack1.copy();					//itemstack複製一份itemstack1
            
            if (itemstack1.getItem() instanceof BasicEquip) isEquip = true;	//判定是否為equip
            
            //click equip slot
            if (slotid < SLOTS_SHIPINV)
            {
            	if (!this.mergeItemStack(itemstack1, SLOTS_SHIPINV, slotsEnd, true))
            	{	//take out equip
                	return null;
                }
            	
                slot.onSlotChange(itemstack1, itemstack); //若物品成功搬動過, 則呼叫slot change事件
            }
            //click player inventory
            else
            {
        		if (isEquip)
        		{	//把equip塞進slot 0~5
        			if (!this.mergeItemStack(itemstack1, 0, SLOTS_SHIPINV, false))
        			{
            			return null;
                    }
        		} 
        		else
        		{	//non-equip
    				return null;
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
		//check slots
		int pageTemp = this.entity.getField(27);
		
        for (int i = 0; i < this.inventorySlots.size(); ++i)
        {
            ItemStack itemNew = ((Slot)this.inventorySlots.get(i)).getStack();
            ItemStack itemBackup = (ItemStack)this.inventoryItemStacks.get(i);

            //if page number or itemstack changed, send sync packet
            if (!ItemStack.areItemStacksEqual(itemBackup, itemNew) || pageTemp != this.valueTemp[27])
            {
            	itemBackup = itemNew == null ? null : itemNew.copy();
                this.inventoryItemStacks.set(i, itemBackup);

                for (int j = 0; j < this.listeners.size(); ++j)
                {
                    ((IContainerListener)this.listeners.get(j)).sendSlotContents(this, i, itemBackup);
                }
            }
        }
        
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
                	case 28:	//show handheld
                	case 31:	//model state
                   	case 34:	//no fuel
                   		listener.sendProgressBarUpdate(this, j, temp);
               		break;
//                	case 25:
//                	case 26:
//                	case 27:	//發送自訂封包更新
//                		this.entity.sendSyncPacketGUI();
//                	break;
//            		default:	//使用vanilla方法更新, 此數值最大僅能以short發送
//                    	listener.sendProgressBarUpdate(this, j, temp);
//            		break;
                	}
            	}
            }//end for all value temp
            
            //first sync
            if (this.needInit)
            {
            	for (int j = 0; j < this.lenTemp; j++)
                {
            		temp = this.entity.getField(j);
            		
                   	switch (j)
                	{
                	case 28:	//show handheld
                	case 31:	//model state
                   	case 34:	//no fuel
                   		listener.sendProgressBarUpdate(this, j, temp);
               		break;
                	}
                }//end for all value temp
            }//end init sync
        }//end for all listener
		
		this.needInit = false;
		
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