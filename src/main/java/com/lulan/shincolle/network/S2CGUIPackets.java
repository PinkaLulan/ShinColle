package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.EntityHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : GUI SYNC PACKET
 * 用於同步GUI中, 大小超過short大小的值
 * 因sendProgressBarUpdate只能同步short, 需另外實做封包同步float, string, int
 */
public class S2CGUIPackets implements IMessage {
	
//	private BasicEntityShip entity;
	private TileEntitySmallShipyard tile1;
	private TileMultiGrudgeHeavy tile2;
	private ExtendPlayerProps props;
	private BasicEntityShip ship;
	private World world;
	private int type, recvX, recvY, recvZ, value, value2;
	
	
	public S2CGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI sync: 
	//type 0: sync shipyard
	//type 1: sync large shipyard
	public S2CGUIPackets(BasicTileEntity tile) {
		if(tile instanceof TileEntitySmallShipyard) {
			tile1 = (TileEntitySmallShipyard) tile;
			type = 0;
		}
		else if(tile instanceof TileMultiGrudgeHeavy) {
			tile2 = (TileMultiGrudgeHeavy) tile;
			type = 1;
		}
    }
	
	//type 2: player gui sync
	public S2CGUIPackets(int value, int value2) {
        this.type = 2;
        this.value = value;
        this.value2 = value2;
    }
	
	//type 3: player extend props sync (for team frame and radar GUI)
	public S2CGUIPackets(ExtendPlayerProps extProps) {
        this.type = 3;
        this.props = extProps;
    }
	
	//type 4: ship inventory sync
	public S2CGUIPackets(BasicEntityShip ship) {
        this.type = 4;
        this.ship = ship;
    }
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {
		world = ClientProxy.getClientWorld();
		
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case 0: //sync small shipyard gui
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();

				this.tile1 = (TileEntitySmallShipyard) world.getTileEntity(recvX, recvY, recvZ);
				
				if(this.tile1 != null) {
					tile1.setPowerConsumed(buf.readInt());
					tile1.setPowerRemained(buf.readInt());
					tile1.setPowerGoal(buf.readInt());
				}
				else {
					buf.clear();
				}
			}
			break;
		case 1: //sync large shipyard gui
			{
				this.recvX = buf.readInt();
				this.recvY = buf.readInt();
				this.recvZ = buf.readInt();

				this.tile2 = (TileMultiGrudgeHeavy) world.getTileEntity(recvX, recvY, recvZ);

				if(this.tile2 != null) {
					this.tile2.setPowerConsumed(buf.readInt());
					this.tile2.setPowerRemained(buf.readInt());
					this.tile2.setPowerGoal(buf.readInt());
					this.tile2.setMatStock(0, buf.readInt());
					this.tile2.setMatStock(1, buf.readInt());
					this.tile2.setMatStock(2, buf.readInt());
					this.tile2.setMatStock(3, buf.readInt());
					
					//set render entity state
					AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(recvX-1.5D, recvY-2D, recvZ-1.5D, recvX+1.5D, recvY+1D, recvZ+1.5D);
					List renderEntityList = world.getEntitiesWithinAABB(EntityRenderVortex.class, aabb);
					
		            for(int i = 0; i < renderEntityList.size(); i++) { 
		            	((EntityRenderVortex)renderEntityList.get(i)).setIsActive(tile2.isBuilding());
		            }
				}
				else {
					buf.clear();
				}
			}
			break;
		case 2: //player gui click
			{
				this.value = buf.readByte();
				this.value2 = buf.readByte();
				
				//set value
				EntityHelper.setPlayerByGUI((int)value, (int)value2);
			}
			break;
		case 3: //sync player props
			{			
				int[] extValues = new int[11];
				boolean[] shipSelected = new boolean[6];
				
				//ring
				extValues[0] = buf.readByte();	//ring active
				extValues[1] = buf.readByte();	//marriage num
				//pointer team
				extValues[2] = buf.readInt();	//team id
				extValues[3] = buf.readInt();	//team list 1
				shipSelected[0] = buf.readBoolean();
				extValues[4] = buf.readInt();	//team list 2
				shipSelected[1] = buf.readBoolean();
				extValues[5] = buf.readInt();	//team list 3
				shipSelected[2] = buf.readBoolean();
				extValues[6] = buf.readInt();	//team list 4
				shipSelected[3] = buf.readBoolean();
				extValues[7] = buf.readInt();	//team list 5
				shipSelected[4] = buf.readBoolean();
				extValues[8] = buf.readInt();	//team list 6
				shipSelected[5] = buf.readBoolean();
				//player uid
				extValues[9] = buf.readInt();	//player uid
				extValues[10] = buf.readInt();	//player team id
//				LogHelper.info("DEBUG : gui sync packet: id 3: "+extValues[0]+" "+extValues[1]+" "+extValues[2]+" "+extValues[3]+" "+extValues[4]+" "+extValues[5]+" "+extValues[6]+" "+extValues[7]);
				//set value
				EntityHelper.setPlayerExtProps(extValues);
				EntityHelper.setPlayerExtProps(shipSelected);
			}
			break;
		case 4:	//sync ship GUI
			{
				Entity getEnt = EntityHelper.getEntityByID(buf.readInt(), 0, true);
				
				if(getEnt instanceof BasicEntityShip) {
					BasicEntityShip ship = (BasicEntityShip) getEnt;
					
					ship.setStateMinor(ID.M.Kills, buf.readInt());
					ship.setStateMinor(ID.M.NumGrudge, buf.readInt());
				}
			}
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0: //sync small shipyard gui
			{
				buf.writeByte(0);
				buf.writeInt(this.tile1.xCoord);
				buf.writeInt(this.tile1.yCoord);
				buf.writeInt(this.tile1.zCoord);
				buf.writeInt(this.tile1.getPowerConsumed());
				buf.writeInt(this.tile1.getPowerRemained());
				buf.writeInt(this.tile1.getPowerGoal());
			}
			break;
		case 1: //sync large shipyard gui
			{
				buf.writeByte(1);
				buf.writeInt(this.tile2.xCoord);
				buf.writeInt(this.tile2.yCoord);
				buf.writeInt(this.tile2.zCoord);
				buf.writeInt(this.tile2.getPowerConsumed());
				buf.writeInt(this.tile2.getPowerRemained());
				buf.writeInt(this.tile2.getPowerGoal());
				buf.writeInt(this.tile2.getMatStock(0));
				buf.writeInt(this.tile2.getMatStock(1));
				buf.writeInt(this.tile2.getMatStock(2));
				buf.writeInt(this.tile2.getMatStock(3));
			}
			break;
		case 2:	//ship entity gui click
			{
				buf.writeByte(2);
				buf.writeByte(this.value);
				buf.writeByte(this.value2);
			}
			break;
		case 3:	//sync player props
			{
				buf.writeByte(3);
				
				//ring
				buf.writeByte(props.isRingActiveI());
				buf.writeByte(props.getMarriageNum());
				
				//team list
				buf.writeInt(props.getTeamId());
				for(int i = 0; i < 6; i++) {
					if(props.getTeamList(i) != null) {
						buf.writeInt(props.getTeamList(i).getEntityId());
						buf.writeBoolean(props.getTeamSelected(i));
					}
					else {
						buf.writeInt(-1);
						buf.writeBoolean(false);
					}
				}
				
				//player uid
				buf.writeInt(props.getPlayerUID());
				buf.writeInt(props.getPlayerTeamId());
			}
			break;
		case 4:	//sync ship inventory GUI: kills and grudge
			{
				buf.writeByte(4);
				buf.writeInt(ship.getEntityId());
				buf.writeInt(ship.getStateMinor(ID.M.Kills));
				buf.writeInt(ship.getStateMinor(ID.M.NumGrudge));
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


