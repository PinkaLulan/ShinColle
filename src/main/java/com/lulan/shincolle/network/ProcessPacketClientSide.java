package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**Process client side packet by Jabelar
 * this class is intended to be sent from server to client to keep custom entities synced
 * 
 * SYNC PACKET: for ExtendEntityProps, client should not send sync-packet back to server
 */
public class ProcessPacketClientSide { 
	
	public ProcessPacketClientSide() {}

	@SideOnly(Side.CLIENT)
	public static void processPacketOnClient(ByteBuf parBB, Side parSide) throws IOException {
		if (parSide == Side.CLIENT) {
			LogHelper.info("DEBUG : recv packet (client side)");

			World theWorld = Minecraft.getMinecraft().theWorld;
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
   
			//read packet ID
			int packetTypeID = bbis.readByte();
			switch (packetTypeID) {
			case Names.Packets.ENTITY_SYNC:
				//read entity ID
				int entityID = bbis.readInt();
				Entity foundEntity = getEntityByID(entityID, theWorld);

				if (foundEntity instanceof BasicEntityShip) {
					BasicEntityShip foundEntityShip = (BasicEntityShip)foundEntity;
					//read packet data
					foundEntityShip.ShipLevel = bbis.readShort();
					foundEntityShip.Kills = bbis.readInt();
					
					foundEntityShip.AttrBonusShort[AttrID.HP] = bbis.readShort();
					foundEntityShip.AttrBonusShort[AttrID.ATK] = bbis.readShort();
					foundEntityShip.AttrBonusShort[AttrID.DEF] = bbis.readShort();
					foundEntityShip.AttrBonusFloat[AttrID.SPD] = bbis.readFloat();
					foundEntityShip.AttrBonusFloat[AttrID.MOV] = bbis.readFloat();
					foundEntityShip.AttrBonusFloat[AttrID.HIT] = bbis.readFloat();
					
					foundEntityShip.AttrFinalShort[AttrID.HP] = bbis.readShort();
					foundEntityShip.AttrFinalShort[AttrID.ATK] = bbis.readShort();
					foundEntityShip.AttrFinalShort[AttrID.DEF] = bbis.readShort();
					foundEntityShip.AttrFinalFloat[AttrID.SPD] = bbis.readFloat();
					foundEntityShip.AttrFinalFloat[AttrID.MOV] = bbis.readFloat();
					foundEntityShip.AttrFinalFloat[AttrID.HIT] = bbis.readFloat();
					
					foundEntityShip.EntityState[AttrID.State] = bbis.readByte();
					foundEntityShip.EntityState[AttrID.Emotion] = bbis.readByte();
					foundEntityShip.EntityState[AttrID.SwimType] = bbis.readByte();
				}
				break;
			}//end switch
		bbis.close();   
		}
	}
 
	//get entity by ID
	public static Entity getEntityByID(int entityID, World world) {         
		for(Object o: world.getLoadedEntityList()) {                        
			if(((Entity)o).getEntityId() == entityID) {                                
				LogHelper.info("Found the entity by ID");                                
				return ((Entity)o);                        
			}                
		}                
		return null;        
	} 
	
	
}
