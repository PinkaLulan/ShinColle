package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/** CLIENT TO SERVER: INPUT PACKETS (NO-GUI)
 *  server端針對input packet的回應
 */
public class S2CInputPackets implements IMessage {
	
	private World world;
	private EntityPlayer player;
	private int type, worldID, entityID, value, value2;
	private int[] value3;
	
	//packet id
	public static final class PID {
		public static final byte CmdChOwner = 0;
		public static final byte CmdShipInfo = 1;
	}
	
	
	public S2CInputPackets() {}  //必須要有空參數constructor, forge才能使用此class

	/**type 0:(2 parms) command: change owner: 0:sender eid, 1:owner eid
	 * 
	 */
	public S2CInputPackets(int type, int...parms) {
        this.type = type;
        
        if(parms != null && parms.length > 0) {
        	this.value3 = parms.clone();
        }
    }
	
	//接收packet方法, client side
	@Override
	public void fromBytes(ByteBuf buf) {	
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case PID.CmdChOwner:	//cmd: change owner packet
		case PID.CmdShipInfo:	//cmd: show ship info
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
					LogHelper.info("DEBUG : S2C input packet: get data fail: "+e);
				}
			}
			break;
		}
	}

	//發出packet方法, server side
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case PID.CmdChOwner:	//cmd: change owner packet
		case PID.CmdShipInfo:	//cmd: show ship info
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
	public static class Handler implements IMessageHandler<S2CInputPackets, IMessage> {
		//收到封包時顯示debug訊息, client side
		@Override
		public IMessage onMessage(S2CInputPackets message, MessageContext ctx) {		
			switch(message.type) {
			case PID.CmdChOwner:	//cmd: change owner packet
				//ship change owner process
				EntityHelper.processShipChangeOwner(message.value3[0], message.value3[1]);
				break;
			case PID.CmdShipInfo:	//cmd: show ship info
				//ship change owner process
				EntityHelper.processShowShipInfo(message.value3[0]);
				break;
			}
			
			return null;
		}
    }
	

}




