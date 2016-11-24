package com.lulan.shincolle.network;

import java.util.ArrayList;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;
import com.lulan.shincolle.utility.TileEntityHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**CLIENT TO SERVER : GUI INPUT PACKETS
 * 用於將GUI的操作發送到server
 */
public class C2SGUIPackets implements IMessage
{
	
	private World world;
	private TileEntity tile;
	private BasicEntityShip entity;
	private EntityPlayer player;
	private int entityID, worldID, type, button, value1, value2;
	private int[] value3;
	private String str;
	
	//packet id
	public static final class PID
	{
		//simple gui button
		public static final byte ShipBtn = 0;
		public static final byte TileBtn = 1;
		
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
		public static final byte HitHeight = -24;
		
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
	public C2SGUIPackets(BasicEntityShip entity, int button, int value1)
	{
        this.entity = entity;
        this.worldID = entity.worldObj.provider.getDimension();
        this.type = PID.ShipBtn;
        this.button = button;
        this.value1 = value1;
    }
	
	/** GUI click: 
	 *  type 0: tile entity gui click
	 */
	public C2SGUIPackets(TileEntity tile, int button, int value1, int value2)
	{
        this.tile = tile;
        this.worldID = tile.getWorld().provider.getDimension();
        this.type = PID.TileBtn;
        this.button = button;
        this.value1 = value1;
        this.value2 = value2;
    }
	
	/** GUI click: 
	 *  type 0: tile entity gui click 2
	 */
	public C2SGUIPackets(TileEntity tile, int value1, int[] value3)
	{
        this.tile = tile;
        this.worldID = tile.getWorld().provider.getDimension();
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
	 * type 17:(3 parm) hit height by pointer: 0:target id, 1:height, 2:angle
	 */
	public C2SGUIPackets(EntityPlayer player, int type, int...parms)
	{
        this.player = player;
        this.worldID = player.worldObj.provider.getDimension();
        this.type = type;
        
        if (parms != null && parms.length > 0)
        {
        	this.value3 = parms.clone();
        }
    }
	
	/**
	 * type 0: add/remove target class: 0:class name
	 * type 1: desk: create team
	 * type 2: add/remove unattackable class: 0:class name
	 * 
	 */
	public C2SGUIPackets(EntityPlayer player, int type, String str)
	{
		this.player = player;
		this.worldID = player.worldObj.provider.getDimension();
        this.type = type;
        this.str = str;
	}
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf)
	{	
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type)
		{
		case PID.ShipBtn:	//ship entity gui click
			{
				entityID = buf.readInt();
				worldID = buf.readInt();
				button = buf.readByte();
				value1 = buf.readByte();
	
				//get entity
				entity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, worldID, false);
				
				//set value
				PacketHelper.setEntityByGUI(entity, button, value1);
			}
			break;
		case PID.TileBtn:	//tile entity gui click
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
				world = ServerProxy.getServerWorld(worldID);
				
				if (world != null)
				{
					//set value
					TileEntityHelper.setTileEntityByGUI(world.getTileEntity(new BlockPos(value3[0], value3[1], value3[2])), button, value1, value2);
				}
			}
			break;
		case PID.AddTeam: //add team, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//entity id
				
				this.player = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
				BasicEntityShip teamship = null;
				Entity getEnt2 = null;
				
				if (capa != null)
				{
					//get ship
					if (value1 > 0)
					{
						getEnt2 = EntityHelper.getEntityByID(value1, worldID, false);
					}
					
					//點到的是ship entity, 則add team
					if (getEnt2 instanceof BasicEntityShip)
					{
						//add ship to team
						capa.addShipEntity(0, (BasicEntityShip) getEnt2, false);
						//reset formation id
						capa.setFormatIDCurrentTeam(0);
					}
					//其他entity or null, 則視為清空該team slot (表示entity可能抓錯或找不到)
					else
					{
						capa.addShipEntity(0, null, false);
					}
					
					//sync team list to client
					capa.sendSyncPacket(0);
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
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(this.value2, this.worldID, false);
				
				if (this.player != null)
				{
					//非禁止攻擊目標 or 不同主人 or 敵對/中立陣營才能攻擊
					if (!TargetHelper.checkUnattackTargetList(getEnt2) &&
						!TeamHelper.checkSameOwner(this.player, getEnt2) &&
						!TeamHelper.checkIsAlly(this.player, getEnt2))
					{
						FormationHelper.applyTeamAttack(this.player, this.value1, getEnt2);
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
				for (int i = 0; i < 5; ++i)
				{
					this.value3[i] = buf.readInt();
				}
				
				EntityPlayer getEnt = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);

				if (getEnt != null)
				{
					FormationHelper.applyTeamMove(getEnt, this.value3);
				}
			}
			break;
		case PID.SetSelect: //select, 2 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//meta
				this.value2 = buf.readInt();	//ship UID
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				
				if (this.player != null)
				{
					FormationHelper.applyTeamSelect(this.player, this.value1, this.value2);
				}
			}
			break;
		case PID.SetSitting: //sit, 2 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//meta
				this.value2 = buf.readInt();	//ship UID

				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				
				if (this.player != null)
				{
					FormationHelper.applyTeamSit(this.player, this.value1, this.value2);
				}
			}
			break;
		case PID.OpenShipGUI:	//open ship GUI, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//entity id
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(this.value1, this.worldID, false);
				
				if (this.player != null && getEnt2 instanceof BasicEntityShip)
				{
					FMLNetworkHandler.openGui(this.player, ShinColle.instance, ID.Gui.SHIPINVENTORY, this.player.worldObj, this.value1, 0, 0);
				}
			}
			break;
		case PID.SyncPlayerItem:	//sync pointer item, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//item meta
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				
				if (this.player != null)
				{
					//if sync pointer, check pointer meta
					if (this.player.inventory.getCurrentItem() != null &&
						this.player.inventory.getCurrentItem().getItem() instanceof PointerItem)
					{
						
						//sync item damage value
						int oldmeta = this.player.inventory.getCurrentItem().getItemDamage();
						this.player.inventory.getCurrentItem().setItemDamage(this.value1);
						
						//mode changed, reset focus target
						if (MathHelper.abs_int(this.value1 - oldmeta) != 3)
						{
							CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
							
							if (capa != null)
							{
								//is single mode, set focus ships to only 1 ship
								if (this.value1 == 0)
								{
									//reset focus ship
									capa.clearSelectStateCurrentTeam();
									
									//set focus ship on first ship in team list
									for (int j = 0; j < 6; j++)
									{
										if (capa.getShipEntityCurrentTeam(j) != null)
										{
											//focus ship j
											capa.setSelectStateCurrentTeam(j, true);
											//sync team list
											capa.sendSyncPacket(0);
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
				this.value3 = PacketHelper.readIntArray(buf, 3);  //0:meta 1:guard type 2:target id
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				Entity getEnt2 = EntityHelper.getEntityByID(this.value3[2], this.worldID, false);
				
				if (this.player != null && getEnt2 != null)
				{
					FormationHelper.applyTeamGuard(this.player, getEnt2, this.value3[0], this.value3[1]);
				}
			}
			break;
		case PID.ClearTeam:		//clear team, 1 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//no use (for team id, NYI)
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
					
				if (capa != null)
				{
					//clear formation, ships will update formation data every X ticks
					capa.setFormatIDCurrentTeam(0);
					
					//clear team
					capa.removeShipCurrentTeam();
					
					//sync team list
					capa.sendSyncPacket(0);
				}
			}
			break;
		case PID.SetShipTeamID: //set team id, 2 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//team id
				this.value2 = buf.readInt();	//org current item
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
				
				if (capa != null)
				{
					//set current item id
					if (this.value2 >= 0) this.player.inventory.currentItem = this.value2;
					
					capa.setPointerTeamID(this.value1);
					
					//send sync packet to client
					capa.sendSyncPacket(0);
				}
			}
			break;
		case PID.TileEntitySync:  //sync tile entity
			{
				this.worldID = buf.readInt();
				int[] tilepos = PacketHelper.readIntArray(buf, 3);
				this.value1 = buf.readInt();
				
				//get tile
				this.world = ServerProxy.getServerWorld(worldID);
				
				if (world != null)
				{
					this.tile = this.world.getTileEntity(new BlockPos(tilepos[0], tilepos[1], tilepos[2]));
				}
				
				//set tile
				switch (this.value1)
				{
				case ID.B.Desk_Sync:  //admiral desk sync
					this.value3 = PacketHelper.readIntArray(buf, 4);
					PacketHelper.setTileEntityByGUI(this.tile, this.value1, this.value3);
					break;
				}
			}
			break;
		case PID.SetTarClass:   //set target class
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.str = PacketHelper.readString(buf);
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
				
				if (capa != null)
				{
					int uid = capa.getPlayerUID();
					
					if (uid > 0)
					{
						if (ServerProxy.setPlayerTargetClass(uid, this.str))
						{
							this.player.addChatMessage(new TextComponentString(TextFormatting.AQUA+"ADD: "+TextFormatting.YELLOW+this.str));
						}
						else
						{
							this.player.addChatMessage(new TextComponentString(TextFormatting.RED+"REMOVE: "+TextFormatting.YELLOW+this.str));
						}
						
						//send sync packet to client
						capa.sendSyncPacket(2);
					}
				}
			}
			break;
		case PID.Desk_Create:
		case PID.Desk_Rename:
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.str = PacketHelper.readString(buf);
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
				
				if (capa != null)
				{
					int uid = capa.getPlayerUID();
					
					if (uid > 0)
					{
						switch (this.type)
						{
						case PID.Desk_Create:
							ServerProxy.teamCreate(this.player, this.str);
							break;
						case PID.Desk_Rename:
							ServerProxy.teamRename(uid, this.str);
							break;
						}
						
						//sync team data
						capa.sendSyncPacket(3);
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
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
				
				if (capa != null)
				{
					int uid = capa.getPlayerUID();
					
					if (uid > 0)
					{
						switch(type)
						{
						case PID.Desk_Disband:
							ServerProxy.teamDisband(this.player);
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
						ArrayList<EntityPlayer> plist = EntityHelper.getEntityPlayerUsingGUI();
						
						for (EntityPlayer p : plist)
						{
							capa.sendSyncPacket(3);
						}
					}//player UID != null
				}//extProps != null
			}
			break;
		case PID.SetFormation:	//set current team formation, 1 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//formation id
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				
				//set current team formation id
				FormationHelper.setFormationID(this.player, this.value1);
			}
			break;
		case PID.OpenItemGUI:	//open item GUI, 1 parm
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value1 = buf.readInt();	//item type
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);

				if (this.player != null)
				{
					switch(this.value1)
					{
					case 0:  //formation
						FMLNetworkHandler.openGui(this.player, ShinColle.instance, ID.Gui.FORMATION, this.player.worldObj, 0, 0, 0);
						break;
					}
				}
			}
			break;
		case PID.SwapShip:	//clear team, 1 parms
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.value3 = PacketHelper.readIntArray(buf, 3);  //team id, swap A, swap B
				
				this.player = EntityHelper.getEntityPlayerByID(this.entityID, this.worldID, false);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(this.player);
				
				if (capa != null)
				{
					//swap ship
					capa.swapShip(this.value3[0], this.value3[1], this.value3[2]);
					capa.syncShips(this.value3[0]);
				}
			}
			break;
		case PID.SetOPTarClass:   //set unattackable list
			{
				this.entityID = buf.readInt();
				this.worldID = buf.readInt();
				this.str = PacketHelper.readString(buf);
				
				/** process:
				 *  1.(done) get mouseover entity (client)
				 *  2.(done) send player eid and entity to server (c 2 s)
				 *  3. check player is OP (server)
				 *  4. add/remove entity to list (server)
				 */
				 this.player = EntityHelper.getEntityPlayerByID(entityID, worldID, false);
				
				//check OP
				if (EntityHelper.checkOP(this.player))
				{
					//add target to list	
					if (ServerProxy.addUnattackableTargetClass(this.str))
					{
						this.player.addChatMessage(new TextComponentString("Target Wrench: ADD entity: "+this.str));
					}
					else
					{
						this.player.addChatMessage(new TextComponentString("Target Wrench: REMOVE entity: "+this.str));
					}
				}
				else
				{
					this.player.addChatMessage(new TextComponentString("Target Wrench: This item is OP ONLY!"));
				}
			}
			break;
		case PID.HitHeight:
			{
				this.entityID = buf.readInt();  //player eid, NO USE
				this.worldID = buf.readInt();   //world id
				this.entityID = buf.readInt();  //entitty id
				this.value1 = buf.readInt();    //hit height
				this.value2 = buf.readInt();    //hit angle
				
				Entity getEnt = EntityHelper.getEntityByID(this.entityID, this.worldID, false);
				
				if (getEnt instanceof BasicEntityShip)
				{
					((BasicEntityShip) getEnt).setHitHeight(this.value1);
					((BasicEntityShip) getEnt).setHitAngle(this.value2);
				}
			}
			break;
		}//end packet type switch
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf)
	{
		switch (this.type)
		{
		case PID.ShipBtn:	//ship entity gui click
			{
				buf.writeByte(0);
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.worldID);
				buf.writeByte(this.button);
				buf.writeByte(this.value1);
			}
			break;
		case PID.TileBtn:	//tile entity gui click
			{
				buf.writeByte(1);
				buf.writeInt(this.worldID);
				buf.writeInt(this.tile.getPos().getX());
				buf.writeInt(this.tile.getPos().getY());
				buf.writeInt(this.tile.getPos().getZ());
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
		case PID.HitHeight:
			{
				buf.writeByte(this.type);
				buf.writeInt(this.player.getEntityId());
				buf.writeInt(this.worldID);
				
				for (int val : value3)
				{
					buf.writeInt(val);
				}
			}
			break;
		case PID.TileEntitySync:
			{
				buf.writeByte(this.type);
				buf.writeInt(this.worldID);
				buf.writeInt(this.tile.getPos().getX());
				buf.writeInt(this.tile.getPos().getY());
				buf.writeInt(this.tile.getPos().getZ());
				buf.writeInt(this.value1);
				
				for (int val : value3)
				{
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
	public static class Handler implements IMessageHandler<C2SGUIPackets, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(C2SGUIPackets message, MessageContext ctx)
		{
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv GUI Click packet : type "+recvType+" button ");
			return null;
		}
    }
	

}


