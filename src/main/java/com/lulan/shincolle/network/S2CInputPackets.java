package com.lulan.shincolle.network;

import com.lulan.shincolle.utility.CommandHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * CLIENT TO SERVER: INPUT PACKETS (NO-GUI)
 * server端針對input packet的回應 (ex: key, command)
 */
public class S2CInputPackets implements IMessage
{
	
	private World world;
	private EntityPlayer player;
	private byte packetType;
	private int[] valueInt;
	
	//packet id
	public static final class PID
	{
		public static final byte CmdChOwner = 0;
		public static final byte CmdShipInfo = 1;
		public static final byte CmdShipAttr = 2;
	}
	
	
	public S2CInputPackets() {}  //必須要有空參數constructor, forge才能使用此class

	/**type 0:(2 parms) command: change owner: 0:sender eid, 1:owner eid
	 * type 1:(1 parms) command: show ship info: 0:sender eid
	 * type 2:(8 parms) command: change ship attrs: 0:sender eid, 1:ship lv, 2~7:bonus value
	 * 
	 */
	public S2CInputPackets(byte type, int...parms)
	{
        this.packetType = type;
        this.valueInt = parms;
    }
	
	//接收packet方法, client side
	@Override
	public void fromBytes(ByteBuf buf)
	{	
		//get type and entityID
		this.packetType = buf.readByte();
	
		switch (this.packetType)
		{
		case PID.CmdChOwner:	//cmd: change owner packet
		case PID.CmdShipInfo:	//cmd: show ship info
		case PID.CmdShipAttr:	//cmd: change ship attrs
		{
			try
			{
				int length = buf.readInt();  //int array length
				
				//get data
				if (length > 0)
				{
					this.valueInt = PacketHelper.readIntArray(buf, length);
				}
			}
			catch (Exception e)
			{
				LogHelper.info("EXCEPTION : S2C input packet: get data fail: "+e);
			}
		}
		break;
		}//end switch
	}

	//發出packet方法, server side
	@Override
	public void toBytes(ByteBuf buf)
	{
		switch(this.packetType)
		{
		case PID.CmdChOwner:	//cmd: change owner packet
		case PID.CmdShipInfo:	//cmd: show ship info
		case PID.CmdShipAttr:	//cmd: change ship attrs
		{
			buf.writeByte(this.packetType);

			//send int array
			if (this.valueInt != null)
			{
				//send array length
				buf.writeInt(this.valueInt.length);
				
				for (int i : this.valueInt)
				{
					buf.writeInt(i);
				}
			}
			//if array null
			else
			{
				buf.writeInt(0);
			}
		}
		break;
		}//end switch
	}
	
	//packet handle method
	private static void handle(S2CInputPackets msg, MessageContext ctx)
	{
		switch (msg.packetType)
		{
		case PID.CmdChOwner:	//cmd: change owner packet
			CommandHelper.processShipChangeOwner(msg.valueInt[0], msg.valueInt[1]);
			break;
		case PID.CmdShipInfo:	//cmd: show ship info
			CommandHelper.processShowShipInfo(msg.valueInt[0]);
			break;
		case PID.CmdShipAttr:	//cmd: change ship attrs
			CommandHelper.processSetShipAttrs(msg.valueInt);
			break;
		}
	}

	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CInputPackets, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CInputPackets message, MessageContext ctx)
		{
			/**
			 * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
			 * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
			 * 以避免多執行緒下各種並行處理問題
			 */
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> S2CInputPackets.handle(message, ctx));
			return null;
		}
    }
	
	
}