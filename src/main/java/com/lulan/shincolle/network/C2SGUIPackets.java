package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.utility.EntityHelper;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**CLIENT TO SERVER : GUI INPUT PACKETS
 * 用於將GUI的操作發送到server
 */
public class C2SGUIPackets implements IMessage {
	
	private World world;
	private BasicEntityShip entity;
	private BasicTileEntity tile;
	private EntityPlayer player;
	private int entityID, worldID, type, button, value1, value2;
	private int[] value3;
	private String str;
	
	//packet id
	public static final class PID {
		//pointer
		public static final byte AddTeam = -1;
		public static final byte AttackTarget = -2;
		public static final byte OpenShipGUI = -3;
		public static final byte SetSitting = -4;
		public static final byte SyncPlayerItem = -5;
		public static final byte GuardEntity = -6;
		public static final byte ClearTeam = -7;
		public static final byte SetShipTeamID = -8;
		public static final byte SetMove = -9;
		public static final byte SetSelect = -10;
		public static final byte SetTarClass = -12;
		//tile entity
		public static final byte TileEntitySync = -11;
	}
	
	
	public C2SGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI click: 
	//type 0: ship entity gui click
	public C2SGUIPackets(BasicEntityShip entity, int button, int value1) {
        this.entity = entity;
        this.worldID = entity.worldObj.provider.dimensionId;
        this.type = 0;
        this.button = button;
        this.value1 = value1;
    }
	
	//type 1: tile entity gui click
	public C2SGUIPackets(BasicTileEntity tile, int button, int value1, int value2) {
        this.tile = tile;
        this.worldID = tile.getWorldObj().provider.dimensionId;
        this.type = 1;
        this.button = button;
        this.value1 = value1;
        this.value2 = value2;
    }
	
	//tile entity gui click
	public C2SGUIPackets(BasicTileEntity tile, int value1, int[] value3) {
        this.tile = tile;
        this.worldID = tile.getWorldObj().provider.dimensionId;
        this.type = PID.TileEntitySync;
        this.value1 = value1;
        this.value3 = value3;
    }
	
	/**
	 * type 3: (1 parm) add team: 0:entity id<br>
	 * type 4: (2 parm) attack target: 0:meta 1:target id<br>
	 * type 5: (5 parm) move: 0:meta 1:guard type 2:posX 3:posY 4:posZ<br>
	 * type 6: (2 parm) set select: 0:meta 1:ship UID<br>
	 * type 7: (2 parm) set sitting: 0:meta 1:entity id<br>
	 * type 8: (1 parm) open ship GUI: 0:entity id<br>
	 * type 9: (1 parm) sync player item: 0:meta<br>
	 * type 10:(3 parm) guard entity: 0:meta 1:guard type 2:target id<br>
	 * type 11:(1 parm) clear team: 0:always 0<br>
	 * type 12:(2 parm) set team id: 0:team id 1:prev currentItem id<br>
	 * 
	 */
	public C2SGUIPackets(EntityPlayer player, int type, int...parms) {
        this.player = player;
        this.worldID = player.worldObj.provider.dimensionId;
        this.type = type;
        
        if(parms != null) {
        	this.value3 = parms.clone();
        }
    }
	
	/**
	 * type 13:(1 parm) add/remove target class: 0:class name
	 * 
	 */
	public C2SGUIPackets(EntityPlayer player, int type, String str) {
		this.player = player;
		this.worldID = player.worldObj.provider.dimensionId;
        this.type = type;
        this.str = str;
	}
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {	
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case 0:	//ship entity gui click
			{
				entityID = buf.readInt();
				worldID = buf.readInt();
				button = buf.readByte();
				value1 = buf.readByte();

				//get entity
				entity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, worldID, false);
				
				//set value
				EntityHelper.setEntityByGUI(entity, button, value1);
			}
			break;
		case 1: //tile entity gui click
			{
				this.value3 = new int[3];
				
				this.worldID = buf.readInt();
				this.value3[0] = buf.readInt();	//x
				this.value3[1] = buf.readInt();	//y
				this.value3[2] = buf.readInt();	//z
				this.button = buf.readByte();
				this.value1 = buf.readByte();
				this.value2 = buf.readByte();
				
				//get tile
				world = DimensionManager.getWorld(worldID);
				
				if(world != null) {
					this.tile = (BasicTileEntity) world.getTileEntity(value3[0], value3[1], value3[2]);
				}
				
				//set value
				EntityHelper.setTileEntityByGUI(tile, button, value1, value2);
			}
			break;
		case PID.AddTeam: //add team, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//entity id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = null;
				
				//get player
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					BasicEntityShip teamship = null;
					
					if(extProps != null) {
						//get ship
						if(value1 > 0) {
							getEnt2 = EntityHelper.getEntityByID(value1, worldID, false);
						
						}
						
						//點到的是ship entity, 則add team
						if(getEnt2 instanceof BasicEntityShip) {
							extProps.addEntityToTeam(0, (BasicEntityShip) getEnt2, false);
						}
						//其他entity or null, 則視為清空該team slot (表示entity可能抓錯或找不到)
						else {
							extProps.addEntityToTeam(0, null, false);
						}
						
						//sync team list to client
						CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps, S2CGUIPackets.PID.SyncPlayerProp), (EntityPlayerMP) player);
					}
				}
			}
			break;
		case PID.AttackTarget: //attack, 2 parms
			{
				this.value3 = new int[6];
				
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//meta
				this.value2 = buf.readInt();	//target id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(value2, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					EntityHelper.applyTeamAttack(player, value1, getEnt2);
				}
			}
			break;
		case PID.SetMove: //move, 5 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				
				//0:meta 1:guard type 2:posX 3:posY 4:posZ
				this.value3 = new int[5];
				for(int i = 0; i < 5; ++i) {
					this.value3[i] = buf.readInt();
				}
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					EntityHelper.applyTeamMove(getEnt, value3);
				}
			}
			break;
		case PID.SetSelect: //select, 2 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//meta
				this.value2 = buf.readInt();	//ship UID
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					EntityHelper.applyTeamSelect(player, value1, value2);
				}
			}
			break;
		case PID.SetSitting: //sit, 2 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//meta
				this.value2 = buf.readInt();	//ship UID

				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					EntityHelper.applyTeamSit(player, value1, value2);
				}
			}
			break;
		case PID.OpenShipGUI:	//open ship GUI, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//entity id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(value1, worldID, false);
				
				if(getEnt != null && getEnt2 instanceof BasicEntityShip) {
					this.player = getEnt;
					this.entity = (BasicEntityShip) getEnt2;
					FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.SHIPINVENTORY, player.worldObj, value1, 0, 0);
				}
			}
			break;
		case PID.SyncPlayerItem:	//sync pointer item, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//item meta
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					
					//if sync pointer, check pointer meta
					if(this.player.inventory.getCurrentItem() != null &&
					   this.player.inventory.getCurrentItem().getItem() instanceof PointerItem) {
						
						//sync item damage value
						this.player.inventory.getCurrentItem().setItemDamage(value1);
						
						//change focus target if pointer mode = 0
						ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
						
						if(extProps != null) {
							//is single mode, set focus ships to only 1 ship
							if(value1 == 0) {
								//reset focus ship
								extProps.clearSelectStateOfCurrentTeam();
								//set focus ship on first ship in team list
								for(int j = 0; j < 6; j++) {
									if(extProps.getEntityOfCurrentTeam(j) != null) {
										//focus ship j
										extProps.setSelectStateOfCurrentTeam(j, true);
										//sync team list
										CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps, S2CGUIPackets.PID.SyncPlayerProp), (EntityPlayerMP) player);
										break;
									}
								}
							}//end meta = 0
						}//end props != null
					}//end pointer sync
				}
			}
			break;
		case PID.GuardEntity:	//guard entity, 3 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				
				//0:meta 1:guard type 2:target id
				this.value3 = new int[3];
				for(int i = 0; i < 3; ++i) {
					this.value3[i] = buf.readInt();
				}
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(value3[2], worldID, false);
				
				if(getEnt != null && getEnt2 != null) {
					this.player = getEnt;
					EntityHelper.applyTeamGuard(player, getEnt2, value3[0], value3[1]);
				}
			}
			break;
		case PID.ClearTeam:	//clear team, 1 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//no use (for team id, NYI)
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						extProps.clearAllShipOfCurrentTeam();
						
						//sync team list
						CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps, S2CGUIPackets.PID.SyncPlayerProp), (EntityPlayerMP) player);
					}
				}
			}
			break;
		case PID.SetShipTeamID: //set team id, 2 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//team id
				this.value2 = buf.readInt();	//org current item
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					this.player.inventory.currentItem = this.value2;
	
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						extProps.setTeamId(this.value1);
						
						//send sync packet to client
						//sync team list
						CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps, S2CGUIPackets.PID.SyncPlayerProp), (EntityPlayerMP) player);
					}
				}
			}
			break;
		case PID.TileEntitySync:  //sync tile entity
			{
				this.worldID = buf.readInt();
				int[] tilepos = new int[3];
				tilepos[0] = buf.readInt();
				tilepos[1] = buf.readInt();
				tilepos[2] = buf.readInt();
				this.value1 = buf.readInt();
				
				//get tile
				world = DimensionManager.getWorld(worldID);
				if(world != null) {
					this.tile = (BasicTileEntity) world.getTileEntity(tilepos[0], tilepos[1], tilepos[2]);
				}
				
				//set tile
				switch(this.value1) {
				case ID.B.Desk_Sync:  //admiral desk sync
					this.value3 = new int[4];
					for(int i = 0; i < 4; ++i) {
						this.value3[i] = buf.readInt();
					}
					EntityHelper.setTileEntityByGUI(tile, value1, value3);
					break;
				}
			}
			break;
		case PID.SetTarClass:   //set target class
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.str = ByteBufUtils.readUTF8String(buf);
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						int uid = extProps.getPlayerUID();
						
						if(uid > 0) {
							ServerProxy.setPlayerTargetClassList(uid, this.str);
							
							//send sync packet to client
							//sync team list
							CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps, S2CGUIPackets.PID.SyncPlayerProp_TargetClass), (EntityPlayerMP) player);
						}
					}
				}
			}
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0:	//ship entity gui click
			{
				buf.writeByte(0);
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.worldID);
				buf.writeByte(this.button);
				buf.writeByte(this.value1);
			}
			break;
		case 1:	//shipyard gui click
			{
				buf.writeByte(1);
				buf.writeInt(this.worldID);
				buf.writeInt(this.tile.xCoord);
				buf.writeInt(this.tile.yCoord);
				buf.writeInt(this.tile.zCoord);
				buf.writeByte(this.button);
				buf.writeByte(this.value1);
				buf.writeByte(this.value2);
			}
			break;
		case PID.AddTeam:
		case PID.AttackTarget:
		case PID.ClearTeam:
		case PID.GuardEntity:
		case PID.OpenShipGUI:
		case PID.SetMove:
		case PID.SetSelect:
		case PID.SetShipTeamID:
		case PID.SetSitting:
		case PID.SyncPlayerItem:
			{
				buf.writeByte(this.type);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);
				
				for(int val : value3) {
					buf.writeInt(val);
				}
			}
			break;
		case PID.TileEntitySync:
			{
				buf.writeByte(this.type);
				buf.writeInt(this.worldID);
				buf.writeInt(this.tile.xCoord);
				buf.writeInt(this.tile.yCoord);
				buf.writeInt(this.tile.zCoord);
				buf.writeInt(this.value1);
				
				for(int val : value3) {
					buf.writeInt(val);
				}
			}
			break;
		case PID.SetTarClass:
			{
				buf.writeByte(this.type);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);
				ByteBufUtils.writeUTF8String(buf, str);
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<C2SGUIPackets, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(C2SGUIPackets message, MessageContext ctx) {
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv GUI Click packet : type "+recvType+" button ");
			return null;
		}
    }
	

}


