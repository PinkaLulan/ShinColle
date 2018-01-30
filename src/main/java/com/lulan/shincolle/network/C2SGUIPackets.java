package com.lulan.shincolle.network;

import java.util.ArrayList;
import java.util.HashMap;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**CLIENT TO SERVER : GUI INPUT PACKETS
 * 用於將GUI的操作發送到server
 */
public class C2SGUIPackets implements IMessage
{
	
	private Entity entity;
	private TileEntity tile;
	private byte packetType;
	private int[] valueInt;
	private byte[] valueByte;
	private String valueStr;
	
	//packet id
	public static final class PID
	{
		//simple gui button
		public static final byte ShipBtn = 0;
		public static final byte TileBtn = 1;
		public static final byte MorphBtn = 2;
		public static final byte MorphBtn2 = 3;
		//pointer gui
		public static final byte AddTeam = 20;
		public static final byte AttackTarget = 21;
		public static final byte OpenShipGUI = 22;
		public static final byte SetSitting = 23;
		public static final byte SyncPlayerItem = 24;
		public static final byte GuardEntity = 25;
		public static final byte ClearTeam = 26;
		public static final byte SetShipTeamID = 27;
		public static final byte SetMove = 28;
		public static final byte SetSelect = 29;
		public static final byte SetTarClass = 30;
		public static final byte SetFormation = 31;
		public static final byte OpenItemGUI = 32;
		public static final byte SwapShip = 33;
		public static final byte NO_USE = 34;		//no use
		public static final byte HitHeight = 35;
		public static final byte SetUnitName = 36;
		//op tool
		public static final byte SetUnatkClass = 50;
		public static final byte ShowUnatkClass = 51;
		//desk gui
		public static final byte Desk_Create = 70;
		public static final byte Desk_Rename = 71;
		public static final byte Desk_Ally = 72;
		public static final byte Desk_Break = 73;
		public static final byte Desk_Ban = 74;
		public static final byte Desk_Unban = 75;
		public static final byte Desk_Disband = 76;
		public static final byte Desk_FuncSync = 77;
	}
	
	
	public C2SGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	/**
	 * entity GUI click:
	 * type 0  (2 parm): ship entity gui click: 0:button, 1:value
	 * type 20 (1 parm): add team: 0:entity id<br>
	 * type 21 (2 parm) attack target: 0:meta 1:target id<br>
	 * type 22 (1 parm) open ship GUI: 0:entity id<br>
	 * type 23 (2 parm) set sitting: 0:meta 1:entity id<br>
	 * type 24 (1 parm) sync player item: 0:meta<br>
	 * type 25 (3 parm) guard entity: 0:meta 1:guard type 2:target id<br>
	 * type 26 (1 parm) clear team: 0:always 0<br>
	 * type 27 (2 parm) set team id: 0:team id 1:prev currentItem id<br>
	 * type 28 (5 parm) move: 0:meta 1:guard type 2:posX 3:posY 4:posZ<br>
	 * type 29 (2 parm) set ship selected: 0:meta 1:ship UID<br>
	 * type 31 (1 parm) change formation: 0:formation id<br>
	 * type 32 (1 parm) open item GUI: 0:formation id<br>
	 * type 33 (3 parm) sawp ship position: 0:team id 1:posA 2:posB
	 * type 35 (3 parm) hit height by pointer: 0:target id, 1:height, 2:angle
	 * type 72 (1 parm) add ally: 0:team id<br>
	 * type 73 (1 parm) break ally: 0:team id<br>
	 * type 74 (1 parm) add banned team: 0:team id<br>
	 * type 75 (1 parm) remove banned team: 0:team id<br>
	 * type 76 (1 parm) disband playerTeam: 0:no use<br>
	 */
	public C2SGUIPackets(Entity entity, byte type, int...parms)
	{
		this.packetType = type;
		this.valueInt = parms;
		this.entity = entity;
	}
	
	/**
	 * tile GUI click:
	 * type 1  (X parms): tile entity gui click: 0:button, 1:value1, 2:value2, ...
	 */
	public C2SGUIPackets(TileEntity tile, byte type, int...parms)
	{
		this.packetType = type;
		this.valueInt = parms;
		this.tile = tile;
	}
	
	/**
	 * type 30 add/remove target class: 0:class name
	 * type 34 add/remove unattackable class: 0:class name
	 * type 36 set unit name
	 * type 70 desk create team
	 * type 71 desk rename team
	 */
	public C2SGUIPackets(EntityPlayer player, byte type, String str)
	{
		this.packetType = type;
		this.valueStr = str;
		this.entity = player;
	}
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf)
	{	
		//get type and entityID
		this.packetType = buf.readByte();
	
		switch(this.packetType)
		{
		case PID.ShipBtn:	//ship entity gui click
		case PID.MorphBtn:	//morph entity gui click
		case PID.MorphBtn2:	//morph entity gui click type 2
			this.valueInt = PacketHelper.readIntArray(buf, 4);
		break;
		case PID.TileBtn:	//tile entity gui click
			this.valueInt = PacketHelper.readIntArray(buf, 4);
			this.valueByte = PacketHelper.readByteArray(buf, 3);
		break;
		case PID.Desk_FuncSync:	//tile entity gui sync
			this.valueInt = PacketHelper.readIntArray(buf, 5);
			this.valueByte = PacketHelper.readByteArray(buf, this.valueInt[4]);
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
		case PID.ShowUnatkClass:
			int length = buf.readInt();
			this.valueInt = PacketHelper.readIntArray(buf, length);
		break;
		case PID.SetTarClass:
		case PID.SetUnitName:
		case PID.Desk_Create:
		case PID.Desk_Rename:
		case PID.SetUnatkClass:
			this.valueInt = PacketHelper.readIntArray(buf, 2);
			this.valueStr = PacketHelper.readString(buf);
		break;
		}//end packet type switch
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf)
	{
		//send packet type
		buf.writeByte(this.packetType);
		
		//send packet content
		switch (this.packetType)
		{
		case PID.ShipBtn:	//ship entity gui click
		case PID.MorphBtn:	//morph entity gui click
		case PID.MorphBtn2:	//morph entity gui click type 2
		{
			buf.writeInt(this.entity.getEntityId());
			buf.writeInt(this.entity.world.provider.getDimension());
			buf.writeInt(this.valueInt[0]);
			buf.writeInt(this.valueInt[1]);
		}
		break;
		case PID.TileBtn:	//tile entity gui click
		{
			buf.writeInt(this.tile.getWorld().provider.getDimension());
			buf.writeInt(this.tile.getPos().getX());
			buf.writeInt(this.tile.getPos().getY());
			buf.writeInt(this.tile.getPos().getZ());
			buf.writeByte(this.valueInt[0]);
			buf.writeByte(this.valueInt[1]);
			buf.writeByte(this.valueInt[2]);
		}
		break;
		case PID.Desk_FuncSync:	//tile entity gui sync
		{
			buf.writeInt(this.tile.getWorld().provider.getDimension());
			buf.writeInt(this.tile.getPos().getX());
			buf.writeInt(this.tile.getPos().getY());
			buf.writeInt(this.tile.getPos().getZ());
			buf.writeInt(this.valueInt.length);
			
			for (int val : this.valueInt)
			{
				buf.writeByte(val);
			}
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
		case PID.ShowUnatkClass:
		{
			int length = 2;
			
			if (this.valueInt != null)
			{
				length += this.valueInt.length;
			}
			
			buf.writeInt(length);
			buf.writeInt(this.entity.getEntityId());
			buf.writeInt(this.entity.world.provider.getDimension());
			
			if (this.valueInt != null)
			{
				for (int val : this.valueInt)
				{
					buf.writeInt(val);
				}
			}
		}
		break;
		case PID.SetTarClass:
		case PID.SetUnatkClass:
		case PID.SetUnitName:
		case PID.Desk_Create:
		case PID.Desk_Rename:
		{
			buf.writeInt(this.entity.getEntityId());
			buf.writeInt(this.entity.world.provider.getDimension());
			PacketHelper.sendString(buf, this.valueStr);
		}
		break;
		}
	}
	
	//packet handle method
	private static void handle(C2SGUIPackets msg, MessageContext ctx)
	{
		Entity entity;
		EntityPlayer player;
		World world;
		CapaTeitoku capa;
		BasicEntityShip ship;
		
		switch(msg.packetType)
		{
		case PID.ShipBtn:	//ship entity gui click
		{
			entity = EntityHelper.getEntityByID(msg.valueInt[0], msg.valueInt[1], false);
			
			if (entity instanceof BasicEntityShip)
			{
				PacketHelper.setEntityByGUI((BasicEntityShip) entity, msg.valueInt[2], msg.valueInt[3]);
			}
		}
		break;
		case PID.MorphBtn:	//morph entity gui click
		{
			capa = CapaTeitoku.getTeitokuCapability(msg.valueInt[0], msg.valueInt[1], false);
			
			if (capa != null && capa.morphEntity instanceof BasicEntityShip)
			{
				PacketHelper.setEntityByGUI((BasicEntityShip) capa.morphEntity, msg.valueInt[2], msg.valueInt[3]);
			}
		}
		break;
		case PID.MorphBtn2:	//morph entity gui click type 2
		{
			capa = CapaTeitoku.getTeitokuCapability(msg.valueInt[0], msg.valueInt[1], false);
			
			if (capa != null)
			{
				MetamorphHelper.handleGUIPacketInput(capa, msg.valueInt[2], msg.valueInt[3]);
			}
		}
		break;
		case PID.TileBtn:	//tile entity gui click
		{
			world = ServerProxy.getServerWorld(msg.valueInt[0]);
			
			if (world != null)
			{
				PacketHelper.setTileEntityByGUI(world.getTileEntity(new BlockPos(
									msg.valueInt[1], msg.valueInt[2], msg.valueInt[3])),
									msg.valueByte[0], msg.valueByte[1], msg.valueByte[2]);
			}
		}
		break;
		case PID.Desk_FuncSync:	//tile entity gui sync
		{
			world = ServerProxy.getServerWorld(msg.valueInt[0]);
			
			if (world != null)
			{
				TileEntity tile = world.getTileEntity(new BlockPos(msg.valueInt[1], msg.valueInt[2], msg.valueInt[3]));
				
				if (tile instanceof TileEntityDesk)
				{
					TileEntityDesk tile2 = (TileEntityDesk) tile;
					
					tile2.setField(0, msg.valueByte[0]);
					tile2.setField(1, msg.valueByte[1]);
					tile2.setField(2, msg.valueByte[2]);
					tile2.setField(3, msg.valueByte[3]);
				}
			}
		}
		break;
		case PID.AddTeam: //add team, 1 parm
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				//get ship
				entity = EntityHelper.getEntityByID(msg.valueInt[2], msg.valueInt[1], false);
				
				//點到的是ship entity, 則add team
				if (entity instanceof BasicEntityShip)
				{
					//add ship to team
					capa.addShipEntity(0, (BasicEntityShip) entity, false);
					//reset formation id
					capa.setFormatIDCurrentTeam(0);
					//sync team name
					EntityHelper.updateNameTag((BasicEntityShip) entity);
					((BasicEntityShip) entity).sendSyncPacketUnitName();
				}
				//其他entity or null, 則視為清空該team slot (表示entity可能抓錯或找不到)
				else
				{
					capa.addShipEntity(0, null, false);
					capa.setFormatIDCurrentTeam(0);
				}
				
				//sync team list to client
				capa.sendSyncPacket(0);
			}
		}
		break;
		case PID.AttackTarget: //attack, 2 parms
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			entity = EntityHelper.getEntityByID(msg.valueInt[3], msg.valueInt[1], false);
			
			if (entity != null && player != null)
			{
				//非禁止攻擊目標 or 不同主人 or 敵對/中立陣營才能攻擊
				if (!TargetHelper.isEntityInvulnerable(entity) &&
					!TeamHelper.checkSameOwner(player, entity) &&
					!TeamHelper.checkIsAlly(player, entity))
				{
					FormationHelper.applyTeamAttack(player, msg.valueInt[2], entity);
				}
			}
		}
		break;
		case PID.SetMove: //move, 5 parms
			FormationHelper.applyTeamMove(msg.valueInt);
		break;
		case PID.SetSelect: //select, 2 parms
			FormationHelper.applyTeamSelect(msg.valueInt);
		break;
		case PID.SetSitting: //sit, 2 parms
			FormationHelper.applyTeamSit(msg.valueInt);
		break;
		case PID.GuardEntity:	//guard entity, 3 parms
			FormationHelper.applyTeamGuard(msg.valueInt);
		break;
		case PID.OpenShipGUI:	//open ship GUI, 1 parm
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			entity = EntityHelper.getEntityByID(msg.valueInt[2], msg.valueInt[1], false);
			
			if (player != null && entity instanceof BasicEntityShip)
			{
				FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.SHIPINVENTORY, player.world, msg.valueInt[2], 0, 0);
			}
		}
		break;
		case PID.SyncPlayerItem:	//sync pointer item, 1 parm
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			
			if (player != null)
			{
				ItemStack pointer = EntityHelper.getPointerInUse(player);
				
				//if sync pointer, check pointer meta
				if (pointer != null)
				{
					//sync item damage value
					int oldmeta = pointer.getItemDamage();
					pointer.setItemDamage(msg.valueInt[2]);
					
					//mode changed != 3, reset focus target
					if (MathHelper.abs(msg.valueInt[2] - oldmeta) % 3 != 0)
					{
						capa = CapaTeitoku.getTeitokuCapability(player);
						
						if (capa != null)
						{
							//is single mode, set focus ships to only 1 ship
							if (msg.valueInt[2] == 0)
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
						}//end capa != null
					}//end change mode < 3
				}//end pointer sync
			}//end get player
		}
		break;
		case PID.ClearTeam:		//clear team, 1 parms
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
				
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
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				//set current item id
				if (msg.valueInt[3] >= 0) player.inventory.currentItem = msg.valueInt[3];
				capa.setPointerTeamID(msg.valueInt[2]);
				//send sync packet to client
				capa.sendSyncPacket(0);
			}
		}
		break;
		case PID.SetTarClass:   //set target class
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				int uid = capa.getPlayerUID();
				
				if (uid > 0)
				{
					if (ServerProxy.setPlayerTargetClass(uid, msg.valueStr))
					{
						player.sendMessage(new TextComponentString(TextFormatting.AQUA+"ADD: "+TextFormatting.YELLOW+msg.valueStr));
					}
					else
					{
						player.sendMessage(new TextComponentString(TextFormatting.RED+"REMOVE: "+TextFormatting.YELLOW+msg.valueStr));
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
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				int uid = capa.getPlayerUID();
				
				if (uid > 0)
				{
					switch (msg.packetType)
					{
					case PID.Desk_Create:
						ServerProxy.teamCreate(player, msg.valueStr);
					break;
					case PID.Desk_Rename:
						ServerProxy.teamRename(uid, msg.valueStr);
					break;
					}
					
					//sync team data
					capa.sendSyncPacket(7);
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
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				int uid = capa.getPlayerUID();
				
				if (uid > 0)
				{
					switch (msg.packetType)
					{
					case PID.Desk_Disband:
						ServerProxy.teamDisband(player);
					break;
					case PID.Desk_Ally:
						ServerProxy.teamAddAlly(uid, msg.valueInt[2]);
					break;
					case PID.Desk_Break:
						ServerProxy.teamRemoveAlly(uid, msg.valueInt[2]);
					break;
					case PID.Desk_Ban:
						ServerProxy.teamAddBan(uid, msg.valueInt[2]);
					break;
					case PID.Desk_Unban:
						ServerProxy.teamRemoveBan(uid, msg.valueInt[2]);
					break;
					}
					
					//sync team data to all player using desk GUI
					ArrayList<EntityPlayer> plist = EntityHelper.getEntityPlayerUsingGUI();
					
					for (EntityPlayer p : plist)
					{
						capa = CapaTeitoku.getTeitokuCapability(p);
						
						if (capa != null)
						{
							capa.sendSyncPacket(7);
							capa.sendSyncPacket(3);
						}
					}
				}//player UID != null
			}//extProps != null
		}
		break;
		case PID.SetFormation:	//set current team formation, 1 parms
		{
			FormationHelper.setFormationID(msg.valueInt);
		}
		break;
		case PID.OpenItemGUI:	//open item GUI, 1 parm
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);

			if (player != null)
			{
				switch (msg.valueInt[2])
				{
				case 0:  //formation
					FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.FORMATION, player.world, 0, 0, 0);
				break;
				}
			}
		}
		break;
		case PID.SwapShip:	//clear team, 1 parms
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				//swap ship
				capa.swapShip(msg.valueInt[2], msg.valueInt[3], msg.valueInt[4]);
				capa.syncShips(msg.valueInt[2]);
			}
		}
		break;
		case PID.SetUnatkClass:   //set unattackable list
		{
			/** process:
			 *  1.(done) get mouseover entity (client)
			 *  2.(done) send player eid and entity to server (c 2 s)
			 *  3. check player is OP (server)
			 *  4. add/remove entity to list (server)
			 */
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			
			//check OP
			if (EntityHelper.checkOP(player))
			{
				//add target to list	
				if (ServerProxy.addUnattackableTargetClass(msg.valueStr))
				{
					player.sendMessage(new TextComponentTranslation("chat.shincolle:optool.add").appendText(" "+msg.valueStr));
				}
				else
				{
					player.sendMessage(new TextComponentTranslation("chat.shincolle:optool.remove").appendText(" "+msg.valueStr));
				}
			}
		}
		break;
		case PID.HitHeight:
		{
			entity = EntityHelper.getEntityByID(msg.valueInt[2], msg.valueInt[1], false);
			
			if (entity instanceof BasicEntityShip)
			{
				((BasicEntityShip) entity).setHitHeight(msg.valueInt[3]);
				((BasicEntityShip) entity).setHitAngle(msg.valueInt[4]);
			}
		}
		break;
		case PID.ShowUnatkClass:
		{
			player = EntityHelper.getEntityPlayerByID(msg.valueInt[0], msg.valueInt[1], false);
			
			if (player != null)
			{
				HashMap<Integer, String> tarlist = ServerProxy.getUnattackableTargetClass();
				TextComponentTranslation text = new TextComponentTranslation("chat.shincolle:optool.show");
				
				text.getStyle().setColor(TextFormatting.GOLD);
				player.sendMessage(text);
				
				tarlist.forEach((k, v) ->
				{
					player.sendMessage(new TextComponentString(TextFormatting.AQUA+v));
				});
			}
		}
		break;
		case PID.SetUnitName:
		{
			player = ctx.getServerHandler().playerEntity;
			capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				capa.setUnitName(capa.getCurrentTeamID(), msg.valueStr);
				
				//sync name string to client capa
				capa.sendSyncPacket(8);
				
				//sync name string to each ship
				BasicEntityShip[] ships = capa.getShipEntityAll(capa.getCurrentTeamID());
				
				for (BasicEntityShip s : ships)
				{
					if (s == null) continue;
					EntityHelper.updateNameTag(s);
					s.sendSyncRequest(1);
				}
			}
		}
		break;
		}//end packet type switch
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<C2SGUIPackets, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(C2SGUIPackets message, MessageContext ctx)
		{
			/**
			 * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
			 * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
			 * 以避免多執行緒下各種並行處理問題
			 */
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> C2SGUIPackets.handle(message, ctx));
			return null;
		}
		
    }
	

}