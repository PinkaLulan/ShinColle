package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ContainerCrane extends Container {
	
	//for shift item
	private static final int SLOT_LOAD = 0;
	private static final int SLOT_UNLOAD = 9;
	private static final int SLOT_PLAYERINV = 18;
	private static final int SLOT_HOTBAR = 45;
	private static final int SLOT_ALL = 54;
		
	private TileEntityCrane tile;
	private int craneMode, craneTime, shipEID, itemMode;
	private boolean isActive, checkMeta, checkDict, checkNbt, enabLoad, enabUnload; 
	
	
	public ContainerCrane(InventoryPlayer invPlayer, TileEntityCrane tile) {
		this.tile = tile;

		int i, j;
		//tile inventory
		for(i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotCrane(tile, i, 8+i*18, 57));
		}
		
		for(i = 0; i < 9; i++) {
			this.addSlotToContainer(new SlotCrane(tile, i+9, 8+i*18, 88));
		}
		
		//player inventory
		for(i = 0; i < 3; i++) {
			for(j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j+i*9+9, 8+j*18, 111+i*18));
			}
		}
		
		//player hot bar
		for(i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8+i*18, 169));
		}
	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	/** 禁用shift功能 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
		return null;
    }
	
	//發送更新gui進度條更新, 比detectAndSendChanges還要優先
	@Override
	public void addCraftingToCrafters (ICrafting crafting) {
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.tile.isActive ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 1, this.tile.checkMetadata ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 2, this.tile.checkOredict ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 3, this.tile.craneMode);
		crafting.sendProgressBarUpdate(this, 4, this.tile.enabLoad ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 5, this.tile.enabUnload ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 6, this.tile.checkNbt ? 1 : 0);
		crafting.sendProgressBarUpdate(this, 7, this.tile.itemMode);
	}
	
	//將container數值跟tile entity內的數值比對, 如果不同則發送更新給client使gui呈現新數值
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for(Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            
            //check target ship
            int eid = this.tile.ship == null ? -1 : this.tile.ship.getEntityId();
            if(this.shipEID != eid) {
                this.shipEID = eid;
                this.tile.sendSyncPacket();
            }
            
            if(tile.ship != null) {
            	if(this.craneTime != (int) (tile.ship.getStateTimer(ID.T.CraneTime) * 0.05F)) {
                    this.craneTime = (int) (tile.ship.getStateTimer(ID.T.CraneTime) * 0.05F);
                    this.tile.ship.sendSyncPacketTimer();
                }
            }
            
            //check other value
            if(this.isActive != this.tile.isActive) {
                this.isActive = this.tile.isActive;
                icrafting.sendProgressBarUpdate(this, 0, isActive ? 1 : 0);
            }
            if(this.checkMeta != this.tile.checkMetadata) {
                this.checkMeta = this.tile.checkMetadata;
                icrafting.sendProgressBarUpdate(this, 1, checkMeta ? 1 : 0);
            }
            if(this.checkDict != this.tile.checkOredict) {
                this.checkDict = this.tile.checkOredict;
                icrafting.sendProgressBarUpdate(this, 2, checkDict ? 1 : 0);
            }
            if(this.craneMode != this.tile.craneMode) {
                this.craneMode = this.tile.craneMode;
                icrafting.sendProgressBarUpdate(this, 3, craneMode);
            }
            if(this.enabLoad != this.tile.enabLoad) {
                this.enabLoad = this.tile.enabLoad;
                icrafting.sendProgressBarUpdate(this, 4, enabLoad ? 1 : 0);
            }
            if(this.enabUnload != this.tile.enabUnload) {
                this.enabUnload = this.tile.enabUnload;
                icrafting.sendProgressBarUpdate(this, 5, enabUnload ? 1 : 0);
            }
            if(this.checkNbt != this.tile.checkNbt) {
                this.checkNbt = this.tile.checkNbt;
                icrafting.sendProgressBarUpdate(this, 6, checkNbt ? 1 : 0);
            }
            if(this.itemMode != this.tile.itemMode) {
                this.itemMode = this.tile.itemMode;
                icrafting.sendProgressBarUpdate(this, 7, itemMode);
            }
        } 
    }

	//client端container接收新值
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int valueType, int updatedValue) {
		World world = ClientProxy.getClientWorld();
		TileEntityCrane tile = (TileEntityCrane) world.getTileEntity(this.tile.xCoord, this.tile.yCoord, this.tile.zCoord);
		
		if(tile != null) {
			switch(valueType) {
			case 0:
				tile.isActive = updatedValue == 0 ? false : true;
				break;
			case 1:
				tile.checkMetadata = updatedValue == 0 ? false : true;
				break;
			case 2:
				tile.checkOredict = updatedValue == 0 ? false : true;
				break;
			case 3:
				tile.craneMode = updatedValue;
				break;
			case 4:
				tile.enabLoad = updatedValue == 0 ? false : true;
				break;
			case 5:
				tile.enabUnload = updatedValue == 0 ? false : true;
				break;
			case 6:
				tile.checkNbt = updatedValue == 0 ? false : true;
				break;
			case 7:
				tile.itemMode = updatedValue;
				break;
			}
		}
    }
	
	/** 紀錄itemstack到crane裝卸載列表中, 不影響玩家物品欄 */
	@Override
	public ItemStack slotClick(int slotID, int mouseKey, int type, EntityPlayer player) {
        ItemStack itemstack = player.inventory.getItemStack();
        
        if(slotID >= 0 && slotID < 18) {
        	Slot slot = (Slot) this.inventorySlots.get(slotID);
        	
        	//left click with item
        	if(itemstack != null && mouseKey == 0) {
        		ItemStack itemstack2 = itemstack.copy();
        		itemstack2.stackSize = 1;
        		slot.putStack(itemstack2);
        		tile.setItemMode(slotID, false);
        	}
        	else if(itemstack != null && mouseKey > 0) {
        		ItemStack itemstack2 = itemstack.copy();
        		itemstack2.stackSize = 1;
        		slot.putStack(itemstack2);
        		tile.setItemMode(slotID, true);
        	}
        	else {
        		slot.putStack(null);
        		tile.setItemMode(slotID, false);
        	}
        	
        	detectAndSendChanges();
        	return null;
        }

        return super.slotClick(slotID, mouseKey, type, player);
    }

	
}

