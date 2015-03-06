package com.lulan.shincolle.client.gui;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerShipInventory extends Container {
	
	public ContainerShipInventory(InventoryPlayer invPlayer,BasicEntityShip entity1) {
			
		//this.addSlotToContainer(new SlotSmallShipyard(invPlayer.player, teSmallShipyard, 0, 33, 29));  //grudge
			
		//player inventory
		for(int i=0; i<3; i++) {
			for(int j=0; j<9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j+i*9+9, 8+j*18, 155+i*18));
			}
		}
		
		//player action bar (hot bar)
		for(int i=0; i<9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8+i*18, 213));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
