package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.utility.EntityHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
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
	private int entityID, worldID, type, button, value, value2, posX, posY, posZ;
	
	//packet id
	public static final class PID {
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
	}
	
	
	public C2SGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI click: 
	//type 0: ship entity gui click
	public C2SGUIPackets(BasicEntityShip entity, int button, int value) {
        this.entity = entity;
        this.worldID = entity.worldObj.provider.dimensionId;
        this.type = 0;
        this.button = button;
        this.value = value;
    }
	
	//type 1: shipyard gui click
	public C2SGUIPackets(BasicTileEntity tile, int button, int value, int value2) {
        this.tile = tile;
        this.worldID = tile.getWorldObj().provider.dimensionId;
        this.type = 1;
        this.button = button;
        this.value = value;
        this.value2 = value2;
    }
	
	/**type 2: player gui click: button = button id, value = button value
	 * type 3: pointer click (add team): button = -1, value = team slot, value2 = entity id
	 * type 4: pointer click (attack target): button = -2, value = meta, value2 = target id
	 * type 7: pointer click (set sitting): button = -4, value = meta, value2 = entity id
	 * type 8: open ship GUI: button = -3, value2 = entity id
	 * type 9: sync player item: button = -5, value = meta
	 * type 10: guard entity: button = -6, value = meta, value2 = guarded id
	 * type 11: clear team: button = -7
	 * type 12: set team id: button = -8, value = teamID
	 * 
	 */
	public C2SGUIPackets(EntityPlayer player, int packetType2, int value, int value2) {
        this.player = player;
        this.worldID = player.worldObj.provider.dimensionId;
        this.button = packetType2;
        this.value = value;
        this.value2 = value2;
        
        //button type
        switch(packetType2) {
        default:	//no use
        	this.type = 2;
        	break;
        case PID.AddTeam:	//pointer click: add team
        	this.type = 3;
        	break;
        case PID.AttackTarget:	//pointer click: attack target
        	this.type = 4;
        	break;
        case PID.OpenShipGUI:	//open ship GUI
        	this.type = 8;
        	break;
        case PID.SetSitting:	//pointer click: set sitting
        	this.type = 7;
        	break;
        case PID.SyncPlayerItem:	//sync player item
        	this.type = 9;
        	break;
        case PID.GuardEntity:	//guard entity
        	this.type = 10;
        	break;
        case PID.ClearTeam:	//clear team
        	this.type = 11;
        	break;
        case PID.SetShipTeamID:	//set team id
        	this.type = 12;
        	break;
        }
    }
	
	/**type 5: pointer click (move): value = meta, value2 = side, posXYZ = move target 
	 * type 6: pointer click (set select): value = select, value2 = entity id, posX = meta
	 */
	public C2SGUIPackets(EntityPlayer player, int packetType2, int value, int value2, int posX, int posY, int posZ) {
        this.player = player;
        this.worldID = player.worldObj.provider.dimensionId;
        this.button = packetType2;
        this.value = value;
        this.value2 = value2;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        
        switch(packetType2) {
        default:	//unknow packet
        	this.type = -1;
        	break;
        case PID.SetMove:	//pointer right click: move
        	this.type = 5;
        	break;
        case PID.SetSelect:	//pointer click: set ship selected
        	this.type = 6;
        	break;
        }
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
				value = buf.readByte();
				
				//get entity
				entity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, worldID, false);
				
				//set value
				EntityHelper.setEntityByGUI(entity, (int)button, (int)value);
			}
			break;
		case 1: //shipyard gui click
			{
				this.worldID = buf.readInt();
				this.posX = buf.readInt();
				this.posY = buf.readInt();
				this.posZ = buf.readInt();
				this.button = buf.readByte();
				this.value = buf.readByte();
				this.value2 = buf.readByte();
				
				//get tile
				world = DimensionManager.getWorld(worldID);
				
				if(world != null) {
					this.tile = (BasicTileEntity) world.getTileEntity(posX, posY, posZ);
				}
				
				//set value
				EntityHelper.setTileEntityByGUI(tile, (int)button, (int)value, (int)value2);
			}
			break;
		case 2: //NO USE
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();
				this.value = buf.readInt();
				this.value2 = buf.readInt();

			}
			break;
		case 3: //pointer click: add team
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//no use, always 0 (team index)
				this.value2 = buf.readInt();	//entity id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = null;

				if(value2 >= 0) {
					getEnt2 = EntityHelper.getEntityByID(value2, worldID, false);
				}
				
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					BasicEntityShip teamship = null;
					
					if(extProps != null) {
						//點到的是ship entity, 則add team
						if(getEnt2 instanceof BasicEntityShip) {
							extProps.addEntityToTeam(value, (BasicEntityShip) getEnt2, false);
						}
						//其他entity or null, 則視為清空該team slot
						else {
							extProps.addEntityToTeam(value, null, false);
						}
						
						//sync team list to client
						CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps), (EntityPlayerMP) player);
					}
				}
			}
			break;
		case 4: //pointer click: attack target
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//meta
				this.value2 = buf.readInt();	//target id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(value2, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					EntityHelper.applyTeamAttack(player, value, getEnt2);
				}
			}
			break;
		case 5: //pointer click: moving
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//meta
				this.value2 = buf.readInt();	//block side
				this.posX = buf.readInt();		//tar X
				this.posY = buf.readInt();		//tar Y
				this.posZ = buf.readInt();		//tar Z
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					EntityHelper.applyTeamMove(player, value, value2, posX, posY, posZ);
				}
			}
			break;
		case 6: //pointer click: set select
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//select state
				this.value2 = buf.readInt();	//entity id
				this.posX = buf.readInt();		//pointer meta
				this.posY = buf.readInt();		//no use
				this.posZ = buf.readInt();		//no use
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					boolean select = (value > 0 ? true : false);

					EntityHelper.applyTeamSelect(player, posX, value2, select);
				}
			}
			break;
		case 7: //pointer click: set sitting
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//meta
				this.value2 = buf.readInt();	//entity id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					EntityHelper.applyTeamSit(player, value, value2);
				}
			}
			break;
		case 8:	//open ship GUI
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//no use
				this.value2 = buf.readInt();	//entity id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(value2, worldID, false);
				
				if(getEnt != null && getEnt2 instanceof BasicEntityShip) {
					this.player = getEnt;
					this.entity = (BasicEntityShip) getEnt2;
					FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.SHIPINVENTORY, player.worldObj, entity.getEntityId(), 0, 0);
				}
			}
			break;
		case 9:	//sync pointer item
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//item meta
				this.value2 = buf.readInt();	//no use
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					
					//if sync pointer, check pointer meta
					if(this.player.inventory.getCurrentItem() != null &&
					   this.player.inventory.getCurrentItem().getItem() instanceof PointerItem) {
						
						//sync item damage value
						this.player.inventory.getCurrentItem().setItemDamage(value);
						
						//change focus target if pointer mode = 0
						ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
						
						if(extProps != null) {
							//is single mode, set focus ships to only 1 ship
							if(value == 0) {
								//reset focus ship
								extProps.clearSelectStateOfCurrentTeam();
								//set focus ship at first ship
								for(int j = 0; j < 6; j++) {
									if(extProps.getEntityOfCurrentTeam(j) != null) {
										//focus ship j
										extProps.setSelectStateOfCurrentTeam(j, true);
										//sync team list
										CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps), (EntityPlayerMP) player);
										break;
									}
								}
							}
						}
					}//end pointer sync
				}
			}
			break;
		case 10:	//guard entity
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//meta
				this.value2 = buf.readInt();	//entity id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(value2, worldID, false);
				
				if(getEnt != null && getEnt2 != null) {
					this.player = getEnt;
					EntityHelper.applyTeamGuard(player, value, getEnt2);
				}
			}
			break;
		case 11:	//clear team
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						extProps.clearSelectStateOfCurrentTeam();
						extProps.clearAllTeam();
						
						//sync team list
						CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps), (EntityPlayerMP) player);
					}
				}
			}
			break;
		case 12: //pointer click: set select
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.button = buf.readInt();	//no use
				this.value = buf.readInt();		//team id
				this.value2 = buf.readInt();	//org current item
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					this.player.inventory.currentItem = this.value2;
	
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						extProps.setTeamId(this.value);
						
						//send sync packet to client
						//sync team list
						CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps), (EntityPlayerMP) player);
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
				buf.writeByte(this.value);
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
				buf.writeByte(this.value);
				buf.writeByte(this.value2);
			}
			break;
		case 2:	//ship entity gui click
		case 3:	//pointer click: add team
		case 4:	//pointer click: attack target
		case 7:	//pointer click: set sitting
		case 8:	//open ship GUI
		case 9:	//sync player item
		case 10://guard entity
		case 12://set team id
			{
				buf.writeByte(this.type);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);
				buf.writeInt(this.button);
				buf.writeInt(this.value);
				buf.writeInt(this.value2);
			}
			break;
		case 5:	//pointer click: moving
		case 6:	//pointer click: set select
			{
				buf.writeByte(this.type);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);
				buf.writeInt(this.button);
				buf.writeInt(this.value);
				buf.writeInt(this.value2);
				buf.writeInt(this.posX);
				buf.writeInt(this.posY);
				buf.writeInt(this.posZ);
			}
			break;
		case 11://clear team
			{
				buf.writeByte(11);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);
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


