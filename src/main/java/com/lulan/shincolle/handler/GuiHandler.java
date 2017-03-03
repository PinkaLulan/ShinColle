package com.lulan.shincolle.handler;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.client.gui.GuiCrane;
import com.lulan.shincolle.client.gui.GuiDesk;
import com.lulan.shincolle.client.gui.GuiFormation;
import com.lulan.shincolle.client.gui.GuiLargeShipyard;
import com.lulan.shincolle.client.gui.GuiShipInventory;
import com.lulan.shincolle.client.gui.GuiSmallShipyard;
import com.lulan.shincolle.client.gui.GuiVolCore;
import com.lulan.shincolle.client.gui.inventory.ContainerCrane;
import com.lulan.shincolle.client.gui.inventory.ContainerDesk;
import com.lulan.shincolle.client.gui.inventory.ContainerFormation;
import com.lulan.shincolle.client.gui.inventory.ContainerLargeShipyard;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.client.gui.inventory.ContainerSmallShipyard;
import com.lulan.shincolle.client.gui.inventory.ContainerVolCore;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = null;
		Entity entity = null;
		CapaTeitoku capa;
		
		//判定gui種類
		switch (guiId)
		{
		case ID.Gui.SMALLSHIPYARD:	//GUI small shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileEntitySmallShipyard)
			{
				//sync tile when gui opened
				((TileEntitySmallShipyard) tile).sendSyncPacket();
				
				return new ContainerSmallShipyard(player.inventory, (TileEntitySmallShipyard) tile);
			}
			return null;
		case ID.Gui.SHIPINVENTORY:	//GUI ship inventory
			entity = world.getEntityByID(x);	//entity id存在x座標參數上
			
            if (entity instanceof BasicEntityShip)
            {
            	//get ship class id and register to player data for ship list recording
            	int cid = ((BasicEntityShip) entity).getShipClass();
            	EntityHelper.addPlayerColledShip(cid, player);
            	
            	//sync ship when gui opened
            	((BasicEntityShip) entity).sendSyncPacketAllValue();
				
            	return new ContainerShipInventory(player.inventory,(BasicEntityShip) entity);
			}
            return null;
		case ID.Gui.LARGESHIPYARD:	//GUI large shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileMultiGrudgeHeavy)
			{
				//sync tile when gui opened
				((TileMultiGrudgeHeavy) tile).sendSyncPacket();
				
				return new ContainerLargeShipyard(player.inventory, (TileMultiGrudgeHeavy) tile);
			}
			return null;
		case ID.Gui.ADMIRALDESK:		//GUI admiral desk
			tile = world.getTileEntity(new BlockPos(x, y, z));  //確定抓到entity才開ui 以免噴出NPE
			
			//sync data to client
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				capa.sendSyncPacket(7);
				capa.sendSyncPacket(2);
				capa.sendSyncPacket(3);
				capa.sendSyncPacket(5);
				capa.sendSyncPacket(6);
				capa.sendSyncPacket(8);
			}
			
			//open GUI with TileEntity
			if (tile instanceof TileEntityDesk)
			{
				//sync tile when gui opened
				((TileEntityDesk) tile).sendSyncPacket();
				
				return new ContainerDesk(player, (TileEntityDesk) tile, 0);
			}
			//open GUI with item
			else
			{
				return new ContainerDesk(player, null, x);
			}
		case ID.Gui.FORMATION:  		//GUI formation
			//send sync packet
			capa = CapaTeitoku.getTeitokuCapability(player);
			capa.sendSyncPacket(4);
			
			return new ContainerFormation();
		case ID.Gui.VOLCORE:	//GUI volcano core
			tile = world.getTileEntity(new BlockPos(x, y, z));  //確定抓到entity才開ui 以免噴出NPE
			
			if (tile instanceof TileEntityVolCore)
			{
				//sync tile when gui opened
				((TileEntityVolCore) tile).sendSyncPacket();
				
				return new ContainerVolCore(player.inventory, (TileEntityVolCore) tile);
			}
			return null;
		case ID.Gui.CRANE:	//GUI crane
			tile = world.getTileEntity(new BlockPos(x, y, z));  //確定抓到entity才開ui 以免噴出NPE
			
			if (tile instanceof TileEntityCrane)
			{
				//sync tile when gui opened
				((TileEntityCrane) tile).sendSyncPacket();
				
				return new ContainerCrane(player.inventory, (TileEntityCrane) tile);
			}
			return null;
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = null;
		Entity entity = null;
		
		//判定gui種類
		switch(guiId)
		{
		case ID.Gui.SMALLSHIPYARD:	//GUI small shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileEntitySmallShipyard)
			{
				return new GuiSmallShipyard(player.inventory, (TileEntitySmallShipyard) tile);
			}
			return null;
		case ID.Gui.SHIPINVENTORY:	//GUI ship inventory
			entity = world.getEntityByID(x);	//entity id存在x座標參數上
			
            if(entity instanceof BasicEntityShip)
            {
				return new GuiShipInventory(player.inventory,(BasicEntityShip) entity);
			}
			return null;
		case ID.Gui.LARGESHIPYARD:	//GUI large shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileMultiGrudgeHeavy)
			{
				return new GuiLargeShipyard(player.inventory, (TileMultiGrudgeHeavy) tile);
			}
			return null;
		case ID.Gui.ADMIRALDESK:	//GUI large shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			//open GUI with TileEntity
			if (tile instanceof TileEntityDesk)
			{
				return new GuiDesk(player, (TileEntityDesk) tile, 0);
			}
			//open GUI with item
			else
			{
				return new GuiDesk(player, null, x);
			}
		case ID.Gui.FORMATION:	//GUI formation
			return new GuiFormation(player);
		case ID.Gui.VOLCORE:		//GUI volcano core
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileEntityVolCore)
			{
				return new GuiVolCore(player.inventory, (TileEntityVolCore) tile);
			}
			return null;
		case ID.Gui.CRANE:		//GUI crane
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileEntityCrane)
			{
				return new GuiCrane(player.inventory, (TileEntityCrane) tile);
			}
			return null;
		}
	
		return null;
	}
	
	
}
