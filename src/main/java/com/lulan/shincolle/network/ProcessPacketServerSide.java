package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.IOException;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**Process server side packet by Jabelar
 */
public class ProcessPacketServerSide {

	private static Entity FoundEntity;
	private static TileEntity FoundTE;
	private static int PacketTypeID, EntityID, Button, Value, xCoord, yCoord, zCoord;
	
 
	public ProcessPacketServerSide() {}

	public static void processPacketOnServer(ByteBuf parBB, Side parSide, EntityPlayerMP parPlayer) throws IOException {
		if (parSide == Side.SERVER) {
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
			
			//read packet ID
			PacketTypeID = bbis.readByte();
			
			switch (PacketTypeID) {
			case Names.Packets.GUI_SHIPINV: {
				//read entity ID
				EntityID = bbis.readInt();
				FoundEntity = EntityHelper.getEntityByID(EntityID, parPlayer.worldObj);
				Button = bbis.readByte();
				Value = bbis.readByte();
				//set value
				EntityHelper.setEntityByGUI((BasicEntityShip)FoundEntity, (int)Button, (int)Value);
				LogHelper.info("DEBUG : recv packet (server side): GUI click:"+Button+" "+Value+" ");
				}
				break;
			case Names.Packets.GUI_SHIPYARD: {
				//read position
				xCoord = bbis.readInt();
				yCoord = bbis.readInt();
				zCoord = bbis.readInt();
				Button = bbis.readByte();
				Value = bbis.readByte();
				//get tile entity
				FoundTE = parPlayer.worldObj.getTileEntity(xCoord, yCoord, zCoord);		
				//set value
				EntityHelper.setTileEntityByGUI(FoundTE, (int)Button, (int)Value);
				LogHelper.info("DEBUG : recv packet (server side): GUI click:"+Button+" "+Value+" ");
				}
				break;
			}

			bbis.close();   
		}
	}
	
	
}
