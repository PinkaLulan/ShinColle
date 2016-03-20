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
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/** CLIENT TO SERVER: INPUT PACKETS (NO-GUI)
 *  將client端的按鍵發送到server
 *  或是發送client端command類型封包
 */
public class C2SInputPackets implements IMessage {
	
	private World world;
	private EntityPlayer player;
	private int type, worldID, entityID, value;
	private int[] value3;
	
	//packet id
	public static final class PID {
		public static final byte MountMove = 0;
		public static final byte MountGUI = 1;
		public static final byte SyncHandheld = 2;
		public static final byte CmdChOwner = 3;
		public static final byte CmdShipAttr = 4;
	}
	
	
	public C2SInputPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	/**type 0:(1 parms) mount move key input: 0:key
	 * type 1:(1 parms) mount GUI key input: 0:key
	 * type 2:(1 parms) sync current item: 0:item slot
	 * type 3:(2 parms) command: change owner: 0:owner eid, 1:ship eid
	 * type 4:(9 parms) command: set ship attrs: 0:ship id, 1:world id, 2:ship level, 3~8:bonus value
	 * 
	 */
	public C2SInputPackets(int type, int...parms) {
        this.type = type;
        
        if(parms != null && parms.length > 0) {
        	this.value3 = parms.clone();
        }
    }
	
	//接收packet方法, server side
	@Override
	public void fromBytes(ByteBuf buf) {	
		//get type and entityID
		this.type = buf.readByte();
	
		//get data
		switch(type) {
		case PID.MountMove:		//mount move key input
		case PID.MountGUI:		//mount GUI input
		case PID.SyncHandheld:	//sync current item
		case PID.CmdChOwner:    //command: change owner
		case PID.CmdShipAttr:   //command: set ship attrs
			{
				try {
					this.value = buf.readInt();  //int array length
					
					//get int array data
					if(this.value > 0) {
						this.value3 = new int[this.value];
						
						for(int i = 0; i < this.value; i++) {
							this.value3[i] = buf.readInt();
						}
					}
				}
				catch(Exception e) {
					LogHelper.info("DEBUG : C2S input packet: change owner fail: "+e);
				}
			}
			break;
		}
	}

	//發出packet方法, client side
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case PID.MountMove:		//mount move key input
		case PID.MountGUI:		//mount GUI input
		case PID.SyncHandheld:	//sync current item
		case PID.CmdChOwner:    //command: change owner
		case PID.CmdShipAttr:   //command: set ship attrs
			{
				buf.writeByte((byte)this.type);
				
				//send int array
				if(this.value3 != null) {
					//send array length
					buf.writeInt(this.value3.length);
					
					for(int geti : this.value3) {
						buf.writeInt(geti);
					}
				}
				//if array null
				else {
					buf.writeInt(0);
				}
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
			
			try {
				switch(message.type) {
				case PID.MountMove:	//mounts key input packet
					//set player's mount movement
					if(player.isRiding() && player.ridingEntity instanceof EntityMountSeat) {
						BasicEntityMount mount = ((EntityMountSeat)player.ridingEntity).host;
						
						if(mount != null) {
							BasicEntityShip ship = (BasicEntityShip) mount.getHostEntity();
							
							//check ship owner is player
							if(ship != null && EntityHelper.checkSameOwner(player, ship.getHostEntity())) {
								//set mount movement
								mount.keyPressed = message.value3[0];
							}
						}
					}
					break;
				case PID.MountGUI:	//mounts open GUI
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
				case PID.SyncHandheld:	//sync current item
					player.inventory.currentItem = message.value3[0];
					break;
				case PID.CmdChOwner:    //command: change owner
					{
						/** ship change owner
						 *    1. (done) check command sender is OP (server)
						 *    2. (done) check owner exists (server)
						 *    3. (done) send sender eid to client (s to c)
						 *    4. (done) check sender mouse over target is ship (client)
						 *    5. (done) send ship eid to server (c to s)
						 *    6. change ship's owner UUID and PlayerUID (server)
						 */
						//value3: 0:owner eid, 1:ship eid, 2:world id
						EntityPlayer owner = EntityHelper.getEntityPlayerByID(message.value3[0], message.value3[2], false);
						Entity ent = EntityHelper.getEntityByID(message.value3[1], message.value3[2], false);
						
						if(owner != null && ent instanceof BasicEntityShip) {
							//set owner
							EntityHelper.setPetPlayerUUID(owner.getUniqueID().toString(), (BasicEntityShip)ent);
							EntityHelper.setPetPlayerUID(owner, (BasicEntityShip)ent);
							LogHelper.info("DEBUG : C2S input packet: command: change owner "+owner+" "+ent);
							((BasicEntityShip)ent).sendSyncPacketAllValue();
						}
					}
					break;
				case PID.CmdShipAttr:   //command: set ship attrs
					{
						/**
						 *	  1.(done) check command sender is OP (server)
						 *    2.(done) send sender eid to client (s to c)
						 *    3.(done) check sender mouse over target is ship (client)
						 *    4.(done) send ship eid to server (c to s)
						 *    5. change ship's attributes (server)
						 */
						Entity ent = EntityHelper.getEntityByID(message.value3[0], message.value3[1], false);
						
						if(ent instanceof BasicEntityShip) {
							((BasicEntityShip) ent).setBonusPoint(0, (byte)message.value3[3]);
							((BasicEntityShip) ent).setBonusPoint(1, (byte)message.value3[4]);
							((BasicEntityShip) ent).setBonusPoint(2, (byte)message.value3[5]);
							((BasicEntityShip) ent).setBonusPoint(3, (byte)message.value3[6]);
							((BasicEntityShip) ent).setBonusPoint(4, (byte)message.value3[7]);
							((BasicEntityShip) ent).setBonusPoint(5, (byte)message.value3[8]);
							((BasicEntityShip) ent).setShipLevel(message.value3[2], true);
						}
					}
					break;
				}//end switch
			}
			catch(Exception e) {
				LogHelper.info("DEBUG : C2S input packet: handler: "+e);
			}
			
			return null; 
		}
    }
	

}



