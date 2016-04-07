package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.PacketHelper;

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
		public static final byte SetFormation = -20;
		public static final byte OpenItemGUI = -21;
		public static final byte SwapShip = -22;
		public static final byte SetOPTarClass = -23;
		
		//tile entity
		public static final byte TileEntitySync = -11;
		
		//desk function
		public static final byte Desk_Create = -13;
		public static final byte Desk_Rename = -14;
		public static final byte Desk_Ally = -15;
		public static final byte Desk_Break = -16;
		public static final byte Desk_Ban = -17;
		public static final byte Desk_Unban = -18;
		public static final byte Desk_Disband = -19;
	}
	
	
	public C2SGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	/** GUI click: 
	 *  type 0: ship entity gui click
	 */
	public C2SGUIPackets(BasicEntityShip entity, int button, int value1) {
        this.entity = entity;
        this.worldID = entity.worldObj.provider.dimensionId;
        this.type = 0;
        this.button = button;
        this.value1 = value1;
    }
	
	/** GUI click: 
	 *  type 0: tile entity gui click
	 */
	public C2SGUIPackets(BasicTileEntity tile, int button, int value1, int value2) {
        this.tile = tile;
        this.worldID = tile.getWorldObj().provider.dimensionId;
        this.type = 1;
        this.button = button;
        this.value1 = value1;
        this.value2 = value2;
    }
	
	/** GUI click: 
	 *  type 0: tile entity gui click 2
	 */
	public C2SGUIPackets(BasicTileEntity tile, int value1, int[] value3) {
        this.tile = tile;
        this.worldID = tile.getWorldObj().provider.dimensionId;
        this.type = PID.TileEntitySync;
        this.value1 = value1;
        this.value3 = value3;
    }
	
	/**
	 * type 0: (1 parm) add team: 0:entity id<br>
	 * type 1: (2 parm) attack target: 0:meta 1:target id<br>
	 * type 2: (5 parm) move: 0:meta 1:guard type 2:posX 3:posY 4:posZ<br>
	 * type 3: (2 parm) set select: 0:meta 1:ship UID<br>
	 * type 4: (2 parm) set sitting: 0:meta 1:entity id<br>
	 * type 5: (1 parm) open ship GUI: 0:entity id<br>
	 * type 6: (1 parm) sync player item: 0:meta<br>
	 * type 7: (3 parm) guard entity: 0:meta 1:guard type 2:target id<br>
	 * type 8: (1 parm) clear team: 0:always 0<br>
	 * type 9: (2 parm) set team id: 0:team id 1:prev currentItem id<br>
	 * type 10:(1 parm) disband playerTeam: 0:no use<br>
	 * type 11:(1 parm) add ally: 0:team id<br>
	 * type 12:(1 parm) break ally: 0:team id<br>
	 * type 13:(1 parm) add banned team: 0:team id<br>
	 * type 14:(1 parm) remove banned team: 0:team id<br>
	 * type 15:(1 parm) change formation: 0:formation id<br>
	 * type 16:(3 parm) sawp ship position: 0:team id 1:posA 2:posB
	 */
	public C2SGUIPackets(EntityPlayer player, int type, int...parms) {
        this.player = player;
        this.worldID = player.worldObj.provider.dimensionId;
        this.type = type;
        
        if(parms != null && parms.length > 0) {
        	this.value3 = parms.clone();
        }
    }
	
	/**
	 * type 0: add/remove target class: 0:class name
	 * type 1: desk: create team
	 * type 2: add/remove unattackable class: 0:class name
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
							//add ship to team
							extProps.addShipEntity(0, (BasicEntityShip) getEnt2, false);
							//reset formation id
							extProps.setFormatIDCurrentTeam(0);
						}
						//其他entity or null, 則視為清空該team slot (表示entity可能抓錯或找不到)
						else {
							extProps.addShipEntity(0, null, false);
						}
						
						//sync team list to client
						extProps.sendSyncPacket(0);
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
					//非禁止攻擊目標 or 不同主人 or 敵對/中立陣營才能攻擊
					if(EntityHelper.checkAttackable(getEnt2) &&
					   !EntityHelper.checkSameOwner(getEnt, getEnt2) &&
					   !EntityHelper.checkIsAlly(getEnt, getEnt2)) {
						this.player = getEnt;
						EntityHelper.applyTeamAttack(player, value1, getEnt2);
					}
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
						int oldmeta = this.player.inventory.getCurrentItem().getItemDamage();
						this.player.inventory.getCurrentItem().setItemDamage(value1);
						
						//mode changed, reset focus target
						if(MathHelper.abs_int(value1 - oldmeta) != 3) {
							ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
							
							if(extProps != null) {
								//is single mode, set focus ships to only 1 ship
								if(value1 == 0) {
									//reset focus ship
									extProps.clearSelectStateCurrentTeam();
									//set focus ship on first ship in team list
									for(int j = 0; j < 6; j++) {
										if(extProps.getShipEntityCurrentTeam(j) != null) {
											//focus ship j
											extProps.setSelectStateCurrentTeam(j, true);
											//sync team list
											extProps.sendSyncPacket(0);
											break;
										}
									}
								}//end meta = 0
							}//end props != null
						}
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
						//clear formation, ships will update formation data every X ticks
						extProps.setFormatIDCurrentTeam(0);
						
						//clear team
						extProps.removeShipCurrentTeam();
						
						//sync team list
						extProps.sendSyncPacket(0);
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
					
					//set current item id
					if(this.value2 >= 0) this.player.inventory.currentItem = this.value2;
	
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						extProps.setPointerTeamID(this.value1);
						
						//send sync packet to client
						extProps.sendSyncPacket(0);
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
				this.str = PacketHelper.getString(buf);
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);

					if(extProps != null) {
						int uid = extProps.getPlayerUID();
						
						if(uid > 0) {
							if(ServerProxy.setPlayerTargetClassList(uid, this.str)) {
								player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA+"ADD: "+EnumChatFormatting.YELLOW+this.str));
							}
							else {
								player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"REMOVE: "+EnumChatFormatting.YELLOW+this.str));
							}
							
							//send sync packet to client
							extProps.sendSyncPacket(2);
						}
					}
				}
			}
			break;
		case PID.Desk_Create:
		case PID.Desk_Rename:
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.str = PacketHelper.getString(buf);
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						int uid = extProps.getPlayerUID();
						
						if(uid > 0) {
							switch(this.type) {
							case PID.Desk_Create:
								ServerProxy.teamCreate(this.player, this.str);
								break;
							case PID.Desk_Rename:
								ServerProxy.teamRename(uid, this.str);
								break;
							}
							
							//sync team data
							extProps.sendSyncPacket(3);
						}
					}
				}
			}
			break;
		case PID.Desk_Disband:
		case PID.Desk_Ally:
		case PID.Desk_Break:
		case PID.Desk_Ban:
		case PID.Desk_Unban:
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();  //team id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						int uid = extProps.getPlayerUID();
						
						if(uid > 0) {
							switch(type) {
							case PID.Desk_Disband:
								ServerProxy.teamDisband(player);
								break;
							case PID.Desk_Ally:
								ServerProxy.teamAddAlly(uid, this.value1);
								break;
							case PID.Desk_Break:
								ServerProxy.teamRemoveAlly(uid, this.value1);
								break;
							case PID.Desk_Ban:
								ServerProxy.teamAddBan(uid, this.value1);
								break;
							case PID.Desk_Unban:
								ServerProxy.teamRemoveBan(uid, this.value1);
								break;
							}
							
							//sync team data to all player using desk GUI
							List<EntityPlayer> plist = EntityHelper.getEntityPlayerUsingGUI();
							for(EntityPlayer p : plist) {
								extProps.sendSyncPacket(3);
							}
						}//player UID != null
					}//extProps != null
				}//get player
			}
			break;
		case PID.SetFormation:	//clear team, 1 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//formation id
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(extProps != null) {
						//set current team formation id
						FormationHelper.setFormationID(extProps, this.value1);
					}
				}
			}
			break;
		case PID.OpenItemGUI:	//open item GUI, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//item type
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					
					switch(this.value1) {
					case 0:  //formation
						FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.FORMATION, world, 0, 0, 0);
						break;
					}
				}
			}
			break;
		case PID.SwapShip:	//clear team, 1 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();

				this.value3 = new int[3];
				this.value3[0] = buf.readInt();  //team id
				this.value3[1] = buf.readInt();  //pos A
				this.value3[2] = buf.readInt();  //pos B
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					this.player = getEnt;
					ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(props != null) {
						//swap ship
						props.swapShip(value3[0], value3[1], value3[2]);
						props.syncShips(value3[0]);
					}
				}
			}
			break;
		case PID.SetOPTarClass:   //set unattackable list
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.str = PacketHelper.getString(buf);
				
				/** process:
				 *  1.(done) get mouseover entity (client)
				 *  2.(done) send player eid and entity to server (c 2 s)
				 *  3. check player is OP (server)
				 *  4. add/remove entity to list (server)
				 */
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				if(getEnt != null) {
					//check OP
					if(EntityHelper.checkOP(getEnt)) {
						//add target to list	
						if(ServerProxy.addUnattackableTargetClassList(this.str)) {
							getEnt.addChatMessage(new ChatComponentText("Target Wrench: ADD entity: "+this.str));
						}
						else {
							getEnt.addChatMessage(new ChatComponentText("Target Wrench: REMOVE entity: "+this.str));
						}
					}
					else {
						getEnt.addChatMessage(new ChatComponentText("Target Wrench: This item is OP ONLY!"));
					}
				}//end get player
			}
			break;
		}//end packet type switch
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
		case PID.Desk_Disband:
		case PID.Desk_Ally:
		case PID.Desk_Break:
		case PID.Desk_Ban:
		case PID.Desk_Unban:
		case PID.SetFormation:
		case PID.OpenItemGUI:
		case PID.SwapShip:
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
		case PID.SetOPTarClass:
		case PID.Desk_Create:
		case PID.Desk_Rename:
			{
				buf.writeByte(this.type);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);

				//send string
				PacketHelper.sendString(buf, this.str);
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


