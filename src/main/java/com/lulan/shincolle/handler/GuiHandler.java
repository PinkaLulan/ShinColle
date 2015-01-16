package com.lulan.shincolle.handler;

import com.lulan.shincolle.client.gui.ContainerSmallShipyard;
import com.lulan.shincolle.client.gui.GuiSmallShipyard;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public static final int guiIDSmallShipyard = 0;
	

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null) {	//確定抓到entity才開ui 以免噴出NPE
			switch(ID) {		//判定gui種類
			case guiIDSmallShipyard:	//GUI small shipyard
				if (entity instanceof TileEntitySmallShipyard) {  //server取得container
					return new ContainerSmallShipyard(player.inventory, (TileEntitySmallShipyard) entity);
				}
				return null;
			}
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null) {	//確定抓到entity才開ui 以免噴出NPE
			switch(ID) {		//判定gui種類
			case guiIDSmallShipyard:	//GUI small shipyard
				if (entity instanceof TileEntitySmallShipyard) {  //client取得gui
					return new GuiSmallShipyard(player.inventory, (TileEntitySmallShipyard) entity);
				}
				return null;
			}
		}
		
		return null;
	}

}
