package com.lulan.shincolle.network;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**SERVER TO CLIENT : GUI SYNC PACKET
 * 用於同步GUI中, 大小超過short大小的值
 * 因sendProgressBarUpdate只能同步short, 需另外實做封包同步float, string, int
 */
public class S2CGUIPackets implements IMessage {
	
//	private BasicEntityShip sendEntity;
//	private BasicEntityShip recvEntity;
	private TileEntitySmallShipyard sendTile1;
	private TileEntitySmallShipyard recvTile1;
	private TileMultiGrudgeHeavy sendTile2;
	private TileMultiGrudgeHeavy recvTile2;
	private int sendType, recvType, recvX, recvY, recvZ;
	
	
	public S2CGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI sync: 
	//type 0: sync shipyard
	public S2CGUIPackets(BasicTileEntity tile) {  
		if(tile instanceof TileEntitySmallShipyard) {
			this.sendTile1 = (TileEntitySmallShipyard) tile;
			this.sendType = 0;
		}
		else if(tile instanceof TileMultiGrudgeHeavy) {
			this.sendTile2 = (TileMultiGrudgeHeavy) tile;
			this.sendType = 1;
		}
    }
	
//	//type 1: sync ship inventory
//	public S2CGUIPackets(BasicEntityShip entity, int button, int value, int value2) {
//		this.sendType = 2;
//  }
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {
		//get server world
		World clientWorld = ClientProxy.getClientWorld();
		
		//get type and entityID
		this.recvType = buf.readByte();
	
		switch(recvType) {
		case 0: //sync small shipyard gui
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();
				
				this.recvTile1 = (TileEntitySmallShipyard) clientWorld.getTileEntity(recvX, recvY, recvZ);
				
				if(this.recvTile1 != null) {
					this.recvTile1.setPowerConsumed(buf.readInt());
					this.recvTile1.setPowerRemained(buf.readInt());
					this.recvTile1.setPowerGoal(buf.readInt());
				}	
			}
			break;
		case 1: //sync large shipyard gui
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();
				
				this.recvTile2 = (TileMultiGrudgeHeavy) clientWorld.getTileEntity(recvX, recvY, recvZ);
				
				if(this.recvTile2 != null) {
					this.recvTile2.setPowerConsumed(buf.readInt());
					this.recvTile2.setPowerRemained(buf.readInt());
					this.recvTile2.setPowerGoal(buf.readInt());
					this.recvTile2.setMatStock(0, buf.readInt());
					this.recvTile2.setMatStock(1, buf.readInt());
					this.recvTile2.setMatStock(2, buf.readInt());
					this.recvTile2.setMatStock(3, buf.readInt());
					
					//set render entity state
					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(recvX-1.5D, recvY-2D, recvZ-1.5D, recvX+1.5D, recvY+1D, recvZ+1.5D);
					List renderEntityList = clientWorld.getEntitiesWithinAABB(EntityRenderVortex.class, aabb);
					
		            for(int i = 0; i < renderEntityList.size(); i++) { 
//		            	LogHelper.info("DEBUG : set render entity state (Packet class) "+this.recvTile2.isBuilding()+" "+renderEntityList.get(i)+recvX+" "+recvY+" "+recvZ);
		            	((EntityRenderVortex)renderEntityList.get(i)).setIsActive(this.recvTile2.isBuilding());
		            }
				}	
			}
		break;
		case 2:	//sync ship inventory
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.sendType) {
		case 0: //sync small shipyard gui
			{
				buf.writeByte(0);
				buf.writeInt(this.sendTile1.xCoord);
				buf.writeInt(this.sendTile1.yCoord);
				buf.writeInt(this.sendTile1.zCoord);
				buf.writeInt(this.sendTile1.getPowerConsumed());
				buf.writeInt(this.sendTile1.getPowerRemained());
				buf.writeInt(this.sendTile1.getPowerGoal());
			}
			break;
		case 1: //sync large shipyard gui
			{
				buf.writeByte(1);
				buf.writeInt(this.sendTile2.xCoord);
				buf.writeInt(this.sendTile2.yCoord);
				buf.writeInt(this.sendTile2.zCoord);
				buf.writeInt(this.sendTile2.getPowerConsumed());
				buf.writeInt(this.sendTile2.getPowerRemained());
				buf.writeInt(this.sendTile2.getPowerGoal());
				buf.writeInt(this.sendTile2.getMatStock(0));
				buf.writeInt(this.sendTile2.getMatStock(1));
				buf.writeInt(this.sendTile2.getMatStock(2));
				buf.writeInt(this.sendTile2.getMatStock(3));
			}
		break;
		case 2:	//sync ship inventory
			{
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CGUIPackets, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CGUIPackets message, MessageContext ctx) {
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv GUI Click packet : type "+recvType+" button ");
			return null;
		}
    }
	

}


