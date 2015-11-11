package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.mounts.EntityMountSeat;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
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
	private int type, worldID, entityID, value;
	
	
	public C2SInputPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//type 0: mount move key input
	//type 1: mount GUI key input
	//type 2: sync current item
	public C2SInputPackets(int type, int value) {
        this.type = type;
        this.value = value;
    }
	
	//接收packet方法, server side
	@Override
	public void fromBytes(ByteBuf buf) {	
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case 0:	//mount move key input
		case 1:	//mount GUI input
		case 2:	//sync current item
			{
				this.value = buf.readInt();
			}
			break;
		}
	}

	//發出packet方法, client side
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0:	//mount move key input
		case 1:	//mount GUI input
		case 2:	//sync current item
			{
				buf.writeByte((byte)this.type);
				buf.writeInt(this.value);
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
//			LogHelper.info("DEBUG : get input packet");
			switch(message.type) {
			case 0:	//mounts key input packet
				//set player's mount movement
				if(player.isRiding() && player.ridingEntity instanceof EntityMountSeat) {
					BasicEntityMount mount = ((EntityMountSeat)player.ridingEntity).host;
					
					if(mount != null) {
						BasicEntityShip ship = (BasicEntityShip) mount.getHostEntity();
						
						//check ship owner is player
						if(ship != null && EntityHelper.checkSameOwner(player, ship.getHostEntity())) {
							//set mount movement
							mount.keyPressed = message.value;
						}
					}
				}
				break;
			case 1:	//mounts GUI input packet
				//set player's mount movement
				if(player.isRiding() && player.ridingEntity instanceof EntityMountSeat) {
					BasicEntityMount mount = ((EntityMountSeat)player.ridingEntity).host;
					
					if(mount != null) {
						BasicEntityShip ship = (BasicEntityShip) mount.getHostEntity();
						
						//check ship owner is player
						if(ship != null && EntityHelper.checkSameOwner(player, ship.getHostEntity())) {
							//open ship GUI
							if(mount.getHostEntity() != null) {
								FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.SHIPINVENTORY, player.worldObj, mount.getHostEntity().getEntityId(), 0, 0);
							}
						}
					}
				}
				break;
			case 2:	//sync current item
				player.inventory.currentItem = message.value;
				break;
			}//end switch
			
			return null;
		}
    }
	

}



