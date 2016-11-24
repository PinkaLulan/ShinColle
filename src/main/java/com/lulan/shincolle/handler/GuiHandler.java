package com.lulan.shincolle.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import com.lulan.shincolle.client.gui.GuiLargeShipyard;
import com.lulan.shincolle.client.gui.GuiSmallShipyard;
import com.lulan.shincolle.client.gui.inventory.ContainerLargeShipyard;
import com.lulan.shincolle.client.gui.inventory.ContainerSmallShipyard;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;


public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = null;
		Entity entity = null;
		
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
			break;
//		case ID.G.SHIPINVENTORY:	//GUI ship inventory
//			entity = world.getEntityByID(x);	//entity id存在x座標參數上
//			
//            if(entity != null && entity instanceof BasicEntityShip){
//            	//get ship class id and register to player data for ship list recording
//            	int cid = ((BasicEntityShip)entity).getShipClass();
//            	EntityHelper.addPlayerColledShip(cid, player);
//            	
//            	//sync ship when gui opened
//            	((BasicEntityShip)entity).sendSyncPacketAllValue();
//				
//            	return new ContainerShipInventory(player.inventory,(BasicEntityShip)entity);
//			}
//            break;
		case ID.Gui.LARGESHIPYARD:	//GUI large shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileMultiGrudgeHeavy)
			{
				//sync tile when gui opened
				((TileMultiGrudgeHeavy) tile).sendSyncPacket();
				
				return new ContainerLargeShipyard(player.inventory, (TileMultiGrudgeHeavy) tile);
			}
			break;
//		case ID.G.ADMIRALDESK:		//GUI admiral desk
//			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
//			
//			//sync data to client
//			props = EntityHelper.getExtendPlayerProps(player);
//			
//			if(props != null) {
//				props.sendSyncPacket(2);
//				props.sendSyncPacket(3);
//				props.sendSyncPacket(5);
//				props.sendSyncPacket(6);
//			}
//			
//			//open GUI with TileEntity
//			if(tile != null && tile instanceof TileEntityDesk) {  //server取得container
//				//sync tile when gui opened
//				((TileEntityDesk)tile).sendSyncPacket();
//				
//				return new ContainerDesk(player.inventory, (TileEntityDesk) tile, player, 0);
//			}
//			//open GUI with item
//			else {
//				return new ContainerDesk(player.inventory, null, player, x);
//			}
//		case ID.G.FORMATION:  		//GUI formation
//			//send sync packet
//			props = EntityHelper.getExtendPlayerProps(player);
//			props.sendSyncPacket(4);
//			
//			return new ContainerFormation(player.inventory, player);
//		case ID.G.VOLCORE:	//GUI volcano core
//			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
//			
//			if(tile != null && tile instanceof TileEntityVolCore) {  //server取得container
//				//sync tile when gui opened
//				((TileEntityVolCore)tile).sendSyncPacket();
//				
//				return new ContainerVolCore(player.inventory, (TileEntityVolCore) tile);
//			}
//			break;
//		case ID.G.CRANE:	//GUI crane
//			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
//			
//			if(tile != null && tile instanceof TileEntityCrane) {  //server取得container
//				//sync tile when gui opened
//				((TileEntityCrane)tile).sendSyncPacket();
//				
//				return new ContainerCrane(player.inventory, (TileEntityCrane) tile);
//			}
//			break;
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
//		case ID.G.SHIPINVENTORY:	//GUI ship inventory
//			entity = world.getEntityByID(x);	//entity id存在x座標參數上
//			
//            if(entity != null && entity instanceof BasicEntityShip) {
//				return new GuiShipInventory(player.inventory,(BasicEntityShip)entity);
//			}
//			return null;
		case ID.Gui.LARGESHIPYARD:	//GUI large shipyard
			tile = world.getTileEntity(new BlockPos(x, y, z));
			
			if (tile instanceof TileMultiGrudgeHeavy)
			{
				return new GuiLargeShipyard(player.inventory, (TileMultiGrudgeHeavy) tile);
			}
			return null;
//		case ID.G.ADMIRALDESK:	//GUI large shipyard
//			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
//			
//			//open GUI with TileEntity
//			if(tile != null && tile instanceof TileEntityDesk) {  //server取得container
//				return new GuiDesk(player.inventory, (TileEntityDesk) tile, 0);
//			}
//			//open GUI with item
//			else {
//				return new GuiDesk(player.inventory, null, x);
//			}
//		case ID.G.FORMATION:	//GUI formation
//			return new GuiFormation(player.inventory);
//		case ID.G.VOLCORE:		//GUI volcano core
//			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
//			
//			if (tile != null && tile instanceof TileEntityVolCore) {  //client取得gui
//				return new GuiVolCore(player.inventory, (TileEntityVolCore) tile);
//			}
//			return null;
//		case ID.G.CRANE:		//GUI crane
//			tile = world.getTileEntity(x, y, z);  //確定抓到entity才開ui 以免噴出NPE
//			
//			if (tile != null && tile instanceof TileEntityCrane) {  //client取得gui
//				return new GuiCrane(player.inventory, (TileEntityCrane) tile);
//			}
//			return null;
		}
	
		return null;
	}

}
