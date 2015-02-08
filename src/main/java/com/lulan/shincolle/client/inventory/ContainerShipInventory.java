package com.lulan.shincolle.client.inventory;

import com.lulan.shincolle.crafting.SmallRecipes;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**CUSTOM SHIP INVENTORY
 * slot: S0(66,18) S1(66,36) S2(66,54) S3(66,72) S4(66,90) S5(6,108) S6~S23(8,18) 6x3
 * player inventory(44,132) hotbar(44,190)
 * S0~S5 for equip only
 */
public class ContainerShipInventory extends Container {
	
	private BasicEntityShip entity;
	public static final byte SLOTS_TOTAL = 24;
	public static final byte SLOTS_EQUIP = 6;
	public static final byte SLOTS_INVENTORY = 18;
	private int GuiKills;
	private int GuiExpCurrent;
	private int GuiNumAmmo;
	private int GuiNumAmmoHeavy;
	private int GuiNumGrudge;
	private int ButtonAmmoLight;
	private int ButtonAmmoHeavy;
	
	public ContainerShipInventory(InventoryPlayer invPlayer, BasicEntityShip entity1) {
		int i,j;	//loop index
		this.entity = entity1;
		
		//ship equip = 0~5
		for(i=0; i<6; i++) {
			this.addSlotToContainer(new SlotShipInventory(entity1.getExtProps(), i, 66, 18+i*18));
		}
		
		//ship inventory = 6~23
		for(i=0; i<6; i++) {
			for(j=0; j<3; j++) {
				this.addSlotToContainer(new SlotShipInventory(entity1.getExtProps(), j+i*3+6, 8+j*18, 18+i*18));
			}
		}
		
		//player inventory
		for(i=0; i<3; i++) {
			for(j=0; j<9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j+i*9+9, 44+j*18, 132+i*18));
			}
		}
		
		//player action bar (hot bar)
		for(i=0; i<9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 44+i*18, 190));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}
	
	/**使container支援shift點物品的動作
	 * shift點人物背包中的物品->判定物品類型送到指定格子, 點container中的物品->送到人物背包
	 * mergeItemStack: parm: item,start slot,end slot(此格不判定放入),是否由hotbar開始判定
	 * slot id: 0~4:equip  5~22:ship inventory 
	 *          23~49:player inventory  50~58:hot bar
	 *          
	 * Click: slot 0~5   (Equip)   -> put in slot 5~58 (ShipInv & Player)
	 *        slot 6~23  (ShipInv) -> if equip -> slot 0~4 (Equip)
	 *                             -> if other -> slot 23~58 (Player)
	 *        slot 24~59 (Player)  -> if equip -> slot 0~4 (Equip)
	 *        					   -> if other -> slot 5~22 (ShipInv)
	 *        
	 * Equip slot check in SlotShipInventory.class 
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotid);
        boolean isEquip = false;

        if(slot != null && slot.getHasStack()) { 			//若slot有東西
            ItemStack itemstack1 = slot.getStack();			//itemstack1取得該slot物品
            itemstack = itemstack1.copy();					//itemstack複製一份itemstack1
            
            if(itemstack1.getItem() instanceof BasicEquip) isEquip = true;	//判定是否為equip

            if(slotid < 6) {  		//click equip slot
            	if(!this.mergeItemStack(itemstack1, 6, 60, true)) { //take out equip
                	return null;
                }	
                slot.onSlotChange(itemstack1, itemstack); //若物品成功搬動過, 則呼叫slot change事件
            }
            else {					//slot is ship or player inventory (5~58)
            	if(slotid < 24) {	//if ship inventory (5~22)
            		if(isEquip) {	//把equip塞進slot 0~4, 塞不下則放player inventory (23~58)
            			if(!this.mergeItemStack(itemstack1, 0, 6, false)) {
                			if(!this.mergeItemStack(itemstack1, 24, 60, true)) {
                				return null;
                			}			
                        }
            		}  
            		else {			//non-equip, put into player inventory (23~58)
            			if(!this.mergeItemStack(itemstack1, 24, 60, true)) {
            				return null;
            			}
            		}
            	}
            	else {				//if player inventory (23~58)
            		if(isEquip) {	//把equip塞進slot 0~4, 塞不下則放ship inventory (5~22)
            			if(!this.mergeItemStack(itemstack1, 0, 6, false)) {
                			if(!this.mergeItemStack(itemstack1, 6, 24, true)) {
                				return null;
                			}			
                        }
            		} 
            		else {			//non-equip, put into ship inventory (5~22)
            			if(!this.mergeItemStack(itemstack1, 6, 24, false)) {
            				return null;
            			}
            		}
            	}
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
	
	//發送更新gui進度條更新, 比detectAndSendChanges還要優先(在此放置init方法等)
	@Override
	public void addCraftingToCrafters (ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.entity.getKills());
		crafting.sendProgressBarUpdate(this, 1, this.entity.getExpCurrent());
		crafting.sendProgressBarUpdate(this, 2, this.entity.getNumAmmoLight());
		crafting.sendProgressBarUpdate(this, 3, this.entity.getNumAmmoHeavy());
		crafting.sendProgressBarUpdate(this, 4, this.entity.getNumGrudge());
		LogHelper.info("DEBUG : crafting update UseAL "+this.entity.getEntityFlagI(AttrID.F_UseAmmoLight));
		crafting.sendProgressBarUpdate(this, 5, this.entity.getEntityFlagI(AttrID.F_UseAmmoLight));
		crafting.sendProgressBarUpdate(this, 6, this.entity.getEntityFlagI(AttrID.F_UseAmmoHeavy));
	}
	
	//偵測數值是否改變, 有改變時發送更新(此為server端偵測)
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		int getValue;
		
        for(Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            getValue = this.entity.getKills();
            if(this.GuiKills != getValue) {
                icrafting.sendProgressBarUpdate(this, 0, getValue);
                this.GuiKills = getValue;
            }   
            getValue = this.entity.getExpCurrent();
            if(this.GuiExpCurrent != getValue) {
                 icrafting.sendProgressBarUpdate(this, 1, getValue);
                 this.GuiExpCurrent = getValue;
            }
            getValue = this.entity.getNumAmmoLight();
            if(this.GuiNumAmmo != getValue) {
                icrafting.sendProgressBarUpdate(this, 2, getValue);
                this.GuiNumAmmo = getValue;
            }
            getValue = this.entity.getNumAmmoHeavy();
            if(this.GuiNumAmmoHeavy != getValue) {
                icrafting.sendProgressBarUpdate(this, 3, getValue);
                this.GuiNumAmmoHeavy = getValue;
            }
            getValue = this.entity.getNumGrudge();
            if(this.GuiNumGrudge != getValue) {
                icrafting.sendProgressBarUpdate(this, 4, getValue);
                this.GuiNumGrudge = getValue;
            }
            getValue = this.entity.getEntityFlagI(AttrID.F_UseAmmoLight);
            if(this.ButtonAmmoLight != getValue) {
                icrafting.sendProgressBarUpdate(this, 5, getValue);
                this.ButtonAmmoLight = getValue;
            }
            getValue = this.entity.getEntityFlagI(AttrID.F_UseAmmoHeavy);
            if(this.ButtonAmmoHeavy != getValue) {
                icrafting.sendProgressBarUpdate(this, 6, getValue);
                this.ButtonAmmoHeavy = getValue;
            }
        }
    }
	
	//client端container接收新值
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int valueType, int updatedValue) {     
		switch(valueType) {
		case 0: 
			LogHelper.info("DEBUG : client container set KILL"+updatedValue);
			this.entity.setKills(updatedValue);
			break;
		case 1:
			LogHelper.info("DEBUG : client container set EXP"+updatedValue);
			this.entity.setExpCurrent(updatedValue);
			break;
		case 2:
			LogHelper.info("DEBUG : client container set NAL"+updatedValue);
			this.entity.setNumAmmoLight(updatedValue);
			break;
		case 3:
			LogHelper.info("DEBUG : client container set NAH"+updatedValue);
			this.entity.setNumAmmoHeavy(updatedValue);
			break;
		case 4:
			LogHelper.info("DEBUG : client container set NG"+updatedValue);
			this.entity.setNumGrudge(updatedValue);
			break;
		case 5:
			LogHelper.info("DEBUG : client container set UseAL"+updatedValue);
			this.entity.setEntityFlagI(AttrID.F_UseAmmoLight, updatedValue);
			break;
		case 6:
			LogHelper.info("DEBUG : client container set UseAH"+updatedValue);
			this.entity.setEntityFlagI(AttrID.F_UseAmmoHeavy, updatedValue);
			break;
		}
    }

}
