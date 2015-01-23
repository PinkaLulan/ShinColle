package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayerMP;
import java.io.IOException;
import cpw.mods.fml.relauncher.Side;

/**Process server side packet by Jabelar
 * NO USE FOR NOW
 */
public class ProcessPacketServerSide {
 
	public ProcessPacketServerSide() {}

	public static void processPacketOnServer(ByteBuf parBB, Side parSide, EntityPlayerMP parPlayer) throws IOException {
//		if (parSide == Side.SERVER) {
//			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
//			
//			//read packet ID
//			int packetTypeID = bbis.readInt();
//			switch (packetTypeID) {
//			case 0:
//				break;
//			}
//
//			bbis.close();   
//		}
	}
	
	
}
