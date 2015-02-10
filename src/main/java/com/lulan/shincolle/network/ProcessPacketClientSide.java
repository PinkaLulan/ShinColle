package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

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
	//for entity sync
	private static int packetTypeID;
	private static int entityID;
	private static Entity foundEntity;
	//for particle position
	private static byte particleType;
	private static float posX;
	private static float posY;
	private static float posZ;
	private static float lookX;
	private static float lookY;
	private static float lookZ;
	
	public ProcessPacketClientSide() {}

	@SideOnly(Side.CLIENT)
	public static void processPacketOnClient(ByteBuf parBB, Side parSide) throws IOException {
			
		if (parSide == Side.CLIENT) {
			LogHelper.info("DEBUG : recv packet (client side)");

			World theWorld = Minecraft.getMinecraft().theWorld;
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
   
			//read packet ID
			packetTypeID = bbis.readByte();
			
			switch (packetTypeID) {
			case Names.Packets.ENTITY_SYNC:  //entity sync packet
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = EntityHelper.getEntityByID(entityID, theWorld);

				if (foundEntity instanceof BasicEntityShip) {
					LogHelper.info("DEBUG : client All Sync done");
					BasicEntityShip foundEntityShip = (BasicEntityShip)foundEntity;
					//read packet data
					foundEntityShip.setShipLevel(bbis.readShort(), false);
					foundEntityShip.setKills(bbis.readInt());
					foundEntityShip.setExpCurrent(bbis.readInt());
					foundEntityShip.setNumAmmoLight(bbis.readInt());
					foundEntityShip.setNumAmmoHeavy(bbis.readInt());
					foundEntityShip.setNumGrudge(bbis.readInt());
					
					foundEntityShip.setFinalState(AttrID.HP, bbis.readFloat());
					foundEntityShip.setFinalState(AttrID.ATK, bbis.readFloat());
					foundEntityShip.setFinalState(AttrID.DEF, bbis.readFloat());
					foundEntityShip.setFinalState(AttrID.SPD, bbis.readFloat());
					foundEntityShip.setFinalState(AttrID.MOV, bbis.readFloat());
					foundEntityShip.setFinalState(AttrID.HIT, bbis.readFloat());
					
					foundEntityShip.setEntityState(bbis.readByte(), false);
					foundEntityShip.setEntityEmotion(bbis.readByte(), false);
					foundEntityShip.setEntitySwimType(bbis.readByte(), false);					
					
					foundEntityShip.setBonusPoint(AttrID.HP, bbis.readByte());
					foundEntityShip.setBonusPoint(AttrID.ATK, bbis.readByte());
					foundEntityShip.setBonusPoint(AttrID.DEF, bbis.readByte());
					foundEntityShip.setBonusPoint(AttrID.SPD, bbis.readByte());
					foundEntityShip.setBonusPoint(AttrID.MOV, bbis.readByte());
					foundEntityShip.setBonusPoint(AttrID.HIT, bbis.readByte());
					
					foundEntityShip.setEntityFlag(AttrID.F_CanFloatUp, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_IsMarried, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_UseAmmoLight, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_UseAmmoHeavy, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_NoFuel, bbis.readBoolean());
				}
				break;
				
			case Names.Packets.STATE_SYNC:  //entity sync packet
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = EntityHelper.getEntityByID(entityID, theWorld);

				if (foundEntity instanceof BasicEntityShip) {
					LogHelper.info("DEBUG : client State Sync done");
					BasicEntityShip foundEntityShip = (BasicEntityShip)foundEntity;
					//read packet data	
					foundEntityShip.setEntityState(bbis.readByte(), false);
					foundEntityShip.setEntityEmotion(bbis.readByte(), false);
					foundEntityShip.setEntitySwimType(bbis.readByte(), false);
				}
				break;
				
			case Names.Packets.FLAG_SYNC:  //entity sync packet
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = EntityHelper.getEntityByID(entityID, theWorld);

				if (foundEntity instanceof BasicEntityShip) {
					LogHelper.info("DEBUG : client Flag Sync done");
					BasicEntityShip foundEntityShip = (BasicEntityShip)foundEntity;
					//read packet data
					foundEntityShip.setEntityFlag(AttrID.F_CanFloatUp, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_IsMarried, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_UseAmmoLight, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_UseAmmoHeavy, bbis.readBoolean());
					foundEntityShip.setEntityFlag(AttrID.F_NoFuel, bbis.readBoolean());					
				}
				break;
				
			case Names.Packets.PARTICLE_ATK:  //attack particle
				//read entity ID
				entityID = bbis.readInt();
				foundEntity = EntityHelper.getEntityByID(entityID, theWorld);
				//read particle type
				particleType = bbis.readByte();
				//spawn particle
				ParticleHelper.spawnAttackParticle(foundEntity, particleType);			
				break;
				
			case Names.Packets.PARTICLE_ATK2:  //attack particle at custom position
				//read entity id
				entityID = bbis.readInt();
				foundEntity = EntityHelper.getEntityByID(entityID, theWorld);
				//read position + look vector
				posX = bbis.readFloat();
				posY = bbis.readFloat();
				posZ = bbis.readFloat();
				lookX = bbis.readFloat();
				lookY = bbis.readFloat();
				lookZ = bbis.readFloat();
				//read particle type
				particleType = bbis.readByte();
				//spawn particle
				ParticleHelper.spawnAttackParticleCustomVector(foundEntity, (double)posX, (double)posY, (double)posZ, (double)lookX, (double)lookY, (double)lookZ, particleType);			
				break;
				
			}//end switch
		bbis.close();   
		}
	}


}
