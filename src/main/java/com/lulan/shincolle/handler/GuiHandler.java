package com.lulan.shincolle.handler;

import com.lulan.shincolle.client.gui.GuiShipInventory;
import com.lulan.shincolle.client.gui.GuiSmallShipyard;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.EntityDestroyerI;
import com.lulan.shincolle.inventory.ContainerShipInventory;
import com.lulan.shincolle.inventory.ContainerSmallShipyard;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.reference.GUIs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {	

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	
		switch(ID) {		//判定gui種類
		case GUIs.SMALLSHIPYARD:	//GUI small shipyard
			TileEntity entity0 = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if ((entity0!=null) && (entity0 instanceof TileEntitySmallShipyard)) {  //server取得container
				return new ContainerSmallShipyard(player.inventory, (TileEntitySmallShipyard) entity0);
			}
			return null;
		case GUIs.SHIPINVENTORY:	//GUI ship inventory
			Entity entity1 = world.getEntityByID(x);	//entity id存在x座標參數上
            if((entity1!=null) && (entity1 instanceof BasicEntityShip)){
				return new ContainerShipInventory(player.inventory,(BasicEntityShip)entity1);
			}
			return null;
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	
		switch(ID) {		//判定gui種類
		case GUIs.SMALLSHIPYARD:	//GUI small shipyard
			TileEntity entity0 = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if ((entity0!=null) && (entity0 instanceof TileEntitySmallShipyard)) {  //client取得gui
				return new GuiSmallShipyard(player.inventory, (TileEntitySmallShipyard) entity0);
			}
			return null;
		case GUIs.SHIPINVENTORY:	//GUI ship inventory
			Entity entity1 = world.getEntityByID(x);	//entity id存在x座標參數上
            if((entity1!=null) && (entity1 instanceof BasicEntityShip)){
				return new GuiShipInventory(player.inventory,(BasicEntityShip)entity1);
			}
			return null;
		}
	
		return null;
	}

}
