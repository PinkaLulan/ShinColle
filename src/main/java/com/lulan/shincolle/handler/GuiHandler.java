package com.lulan.shincolle.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.client.gui.GuiDesk;
import com.lulan.shincolle.client.gui.GuiDeskItemForm;
import com.lulan.shincolle.client.gui.GuiLargeShipyard;
import com.lulan.shincolle.client.gui.GuiShipInventory;
import com.lulan.shincolle.client.gui.GuiSmallShipyard;
import com.lulan.shincolle.client.gui.inventory.ContainerDesk;
import com.lulan.shincolle.client.gui.inventory.ContainerDeskItemForm;
import com.lulan.shincolle.client.gui.inventory.ContainerLargeShipyard;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.client.gui.inventory.ContainerSmallShipyard;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile;
		Entity entity;
		
		switch(guiId) {		//判定gui種類
		case ID.G.SMALLSHIPYARD:	//GUI small shipyard
			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if((tile != null) && (tile instanceof TileEntitySmallShipyard)) {  //server取得container
				//sync tile when gui opened
				((TileEntitySmallShipyard)tile).sendSyncPacket();
				
				return new ContainerSmallShipyard(player.inventory, (TileEntitySmallShipyard) tile);
			}
			break;
		case ID.G.SHIPINVENTORY:	//GUI ship inventory
			entity = world.getEntityByID(x);	//entity id存在x座標參數上
            if((entity != null) && (entity instanceof BasicEntityShip)){
            	//sync tile when gui opened
            	((BasicEntityShip)entity).sendSyncPacket();
				
            	return new ContainerShipInventory(player.inventory,(BasicEntityShip)entity);
			}
            break;
		case ID.G.LARGESHIPYARD:	//GUI large shipyard
			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if((tile != null && tile instanceof TileMultiGrudgeHeavy)) {  //server取得container
				//sync tile when gui opened
				((TileMultiGrudgeHeavy)tile).sendSyncPacket();
				
				return new ContainerLargeShipyard(player.inventory, (TileMultiGrudgeHeavy) tile);
			}
			break;
		case ID.G.ADMIRALDESK:	   //GUI admiral desk
			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if((tile != null && tile instanceof TileEntityDesk)) {  //server取得container
				
				//sync tile when gui opened
				((TileEntityDesk)tile).sendSyncPacket();
				
				//sync team list to client
				ExtendPlayerProps props = EntityHelper.getExtendPlayerProps(player);
				CommonProxy.channelG.sendTo(new S2CGUIPackets(props, S2CGUIPackets.PID.SyncPlayerProp_TargetClass), (EntityPlayerMP) player);
				CommonProxy.channelG.sendTo(new S2CGUIPackets(props, S2CGUIPackets.PID.SyncPlayerProp_TeamData), (EntityPlayerMP) player);
				
				return new ContainerDesk(player.inventory, (TileEntityDesk) tile, player);
			}
			break;
		case ID.G.ADMIRALDESK_ITEM:  //GUI admiral radar/book
			return new ContainerDeskItemForm(player.inventory, player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile;
		Entity entity;
		
		switch(guiId) {		//判定gui種類
		case ID.G.SMALLSHIPYARD:	//GUI small shipyard
			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if ((tile!=null) && (tile instanceof TileEntitySmallShipyard)) {  //client取得gui
				return new GuiSmallShipyard(player.inventory, (TileEntitySmallShipyard) tile);
			}
			return null;
		case ID.G.SHIPINVENTORY:	//GUI ship inventory
			entity = world.getEntityByID(x);	//entity id存在x座標參數上
            if((entity!=null) && (entity instanceof BasicEntityShip)){
				return new GuiShipInventory(player.inventory,(BasicEntityShip)entity);
			}
			return null;
		case ID.G.LARGESHIPYARD:	//GUI large shipyard
			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if((tile != null && tile instanceof TileMultiGrudgeHeavy)) {  //server取得container
				return new GuiLargeShipyard(player.inventory, (TileMultiGrudgeHeavy) tile);
			}
			return null;
		case ID.G.ADMIRALDESK:	//GUI large shipyard
			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
			if((tile != null && tile instanceof TileEntityDesk)) {  //server取得container
				return new GuiDesk(player.inventory, (TileEntityDesk) tile);
			}
			return null;
		case ID.G.ADMIRALDESK_ITEM:  //GUI admiral radar/book
			return new GuiDeskItemForm(player.inventory, x);
		}
	
		return null;
	}

}
