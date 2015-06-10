package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.mounts.EntityMountSeat;
import com.lulan.shincolle.handler.FML_COMMON_EventHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**CLIENT TO SERVER: KEY INPUT PACKETS
 * 將client端的按鍵發送到server
 */
public class C2SInputPackets implements IMessage {
	
	private World world;
	private EntityPlayer player;
	private int type, worldID, entityID, value, value2;
	private boolean openGUI;
	
	
	public C2SInputPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//type 0: mount key input
	public C2SInputPackets(int type) {
        this.type = type;
    }
	
	//type 1:
	public C2SInputPackets(int type, Entity entity, int value, int value2) {
        this.type = type;
        this.player = (EntityPlayer) entity;
        this.worldID = player.worldObj.provider.dimensionId;
        this.value = value;
        this.value2 = value2;
    }
	
	//接收packet方法, server side
	@Override
	public void fromBytes(ByteBuf buf) {	
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case 0:	//ship entity gui click
			{
				this.value = buf.readInt();
				this.openGUI = buf.readBoolean();
			}
			break;
		case 1:	//
			{
//				this.entityID = buf.readInt();	//player id
//				this.worldID = buf.readInt();	//world id
//				this.value = buf.readInt();		//ship id
//				this.value2 = buf.readInt();	//no use
//				
//				this.player = (EntityPlayer) EntityHelper.getEntityByID(entityID, worldID, false);
			}
			break;
		}
	}

	//發出packet方法, client side
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0:	//ship entity gui click
			{
				buf.writeByte(0);
				buf.writeInt(FML_COMMON_EventHandler.rideKeys);
				buf.writeBoolean(FML_COMMON_EventHandler.openGUI);
			}
			break;
		case 1:	//
			{
//				buf.writeByte(1);
//				buf.writeInt(this.player.getEntityId());
//				buf.writeInt(this.worldID);
//				buf.writeInt(this.value);
//				buf.writeInt(this.value2);
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<C2SInputPackets, IMessage> {
		//收到封包時顯示debug訊息, server side
		@Override
		public IMessage onMessage(C2SInputPackets message, MessageContext ctx) {		
			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			
			switch(message.type) {
			case 0:	//mounts key input packet
				LogHelper.info(String.format("DEBUG : client key input: %s from %s", message.value, player.getDisplayName()));
				//set player's mount movement
				if(player.isRiding() && player.ridingEntity instanceof EntityMountSeat) {
					BasicEntityMount mount = ((EntityMountSeat)player.ridingEntity).host;
					
					if(mount != null) {
						BasicEntityShip ship = (BasicEntityShip) mount.getOwner();
						
						//check ship owner is player
						if(ship != null && EntityHelper.checkSameOwner(player, ship.getOwner())) {
							//set mount movement
							mount.keyPressed = message.value;
							
							//open ship GUI
							if(message.openGUI) {
								if(mount.getOwner() != null) {
									FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.SHIPINVENTORY, player.worldObj, mount.getOwner().getEntityId(), 0, 0);
								}
							}
						}
					}
				}
				break;
			case 1:	//
				
				break;
			}//end switch
			
			return null;
		}
    }
	

}



