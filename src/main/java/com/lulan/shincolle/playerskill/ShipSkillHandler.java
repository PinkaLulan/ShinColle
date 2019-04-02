package com.lulan.shincolle.playerskill;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.*;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.util.ArrayList;

public class ShipSkillHandler
{
	
	
	public ShipSkillHandler() {}
	
	/**
	 * get ship skill host type
	 *   -1: no skill
	 *   0: skill of mounts
	 *   1: skill of morph
	 *   2: skill of rider
	 */
	public static int getShipSkillHostType(EntityPlayer player)
	{
		if (player.getRidingEntity() instanceof BasicEntityMount) return 0;
		else if (player.getPassengers().size() > 0 && player.getPassengers().get(0) instanceof BasicEntityShip) return 2;
		
		//client side
		if (player.world.isRemote)
		{
			if (ConfigHandler.enableMetamorphSkill) return 1;
		}
		
		return -1;
	}
	
	/**
	 * handle ship skill keys
	 *   type: 0:skill of mounts, 1:skill of morph
	 */
	public static void handleShipSkillKeys(int type)
	{
		switch (type)
		{
		case 0:
			handleSkillKeysMounts();
		break;
		case 1:
			handleSkillKeysMorph();
		break;
		case 2:
			handleSkillKeysRider();
		break;
		}
	}
	
	/**
	 * handle keys for mounts
	 */
	protected static void handleSkillKeysMounts()
	{
		GameSettings keySet = ClientProxy.getGameSetting();
		EntityPlayer player = ClientProxy.getClientPlayer();
		BasicEntityMount mount = null;
		
		if (player.getRidingEntity() instanceof BasicEntityMount)
		{
			mount = (BasicEntityMount) player.getRidingEntity();
		}
		else
		{
			return;
		}
		
		//keys for movement
		if (ClientProxy.keyMountActionCD <= 0)
		{
			//open ship GUI while riding
			if (keySet.keyBindInventory.isPressed() || keySet.keyBindPickBlock.isKeyDown())
			{
				LogHelper.debug("DEBUG: key event: open ship GUI");
				ClientProxy.keyMountActionCD = 8;
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountGUI));
				return;
			}
			
			//change renderer viewer
			if (keySet.keyBindPickBlock.isPressed() || keySet.keyBindPickBlock.isKeyDown())
			{
				LogHelper.debug("DEBUG: key event: player view "+ClientProxy.isViewPlayer);
				ClientProxy.keyMountActionCD = 8;
				ClientProxy.isViewPlayer = !ClientProxy.isViewPlayer;
				return;
			}
			
			//持續偵測所有移動按鍵是否按住
			int newKeys = 0;
			//forward
			if (keySet.keyBindForward.isKeyDown()) newKeys = newKeys | 1;
			//back
			if (keySet.keyBindBack.isKeyDown()) newKeys = newKeys | 2;
			//left
			if (keySet.keyBindLeft.isKeyDown()) newKeys = newKeys | 4;
			//right
			if (keySet.keyBindRight.isKeyDown()) newKeys = newKeys | 8;
			//jump
			if (keySet.keyBindJump.isKeyDown() && (mount.onGround || EntityHelper.checkEntityIsInLiquid(mount))) newKeys = newKeys | 16;
			
			if (newKeys > 0)
			{
				//set key for packet
				ClientProxy.rideKeys = newKeys;
				ClientProxy.keyMountActionCD = 2;
				
				//server跟client必須同時設定移動狀態, 移動顯示才會順暢, 只靠server設定移動會不連續
				mount.keyPressed = newKeys;		//set client moving key
				mount.keyTick = 10;				//continue moving for 10 ticks
				
				//send mounts move key packet
				LogHelper.debug("DEBUG: key event: mounts move key: "+Integer.toBinaryString(newKeys));
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountMove, ClientProxy.rideKeys));
			}
		}//end keys for movement
		
		//keys for skill
		if (ClientProxy.keyPlayerSkillCD <= 0 && mount.getAttrs() != null)
		{
			int getKey = -1;
			
			if (keySet.keyBindsHotbar[0].isKeyDown()) getKey = 0;
			else if (keySet.keyBindsHotbar[1].isKeyDown()) getKey = 1;
			else if (keySet.keyBindsHotbar[2].isKeyDown()) getKey = 2;
			else if (keySet.keyBindsHotbar[3].isKeyDown()) getKey = 3;
			
			if (getKey < 0) return;
			
			float range = mount.getAttrs().getAttackRange();
			ClientProxy.keyPlayerSkillCD = 2;
			
			//create exclude entity list
			ArrayList<Entity> exlist = new ArrayList<Entity>();
			exlist.add(player);
			exlist.add(mount);
			exlist.add(mount.getHostEntity());
			
			//get skill target
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(range, 1F, exlist);
			Entity target = null;
			int[] targetPos = null;
			
			//get skill target: entity
			if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.ENTITY)
			{
				target = hitObj.entityHit;
				//在目標上畫出標記
				ParticleHelper.spawnAttackParticleAtEntity(target, 0.3D, 5D, 0D, (byte)2);
			}
			
			//get skill target: block
			if (target == null) 
			{
				if (mount.getShipDepth() >= 3D)
				{
					hitObj = BlockHelper.getPlayerMouseOverBlockThroughWater(range, 1F);
				}
				else
				{
					hitObj = BlockHelper.getPlayerMouseOverBlockOnWater(range, 1F);
				}
				
				if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					targetPos = new int[] {hitObj.getBlockPos().getX(), hitObj.getBlockPos().getY(), hitObj.getBlockPos().getZ()};
					//在目標上畫出標記
					ParticleHelper.spawnAttackParticleAt(targetPos[0]+0.5D, targetPos[1], targetPos[2]+0.5D, 0.3D, 5D, 0D, (byte)25);
				}
			}
			
			//fire only 1 key at a time
			if (getKey == 0)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 0, target.getEntityId(), -1, -1));
				}
			}
			else if (getKey == 1)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 1, target.getEntityId(), -1, -1));
				}
				//hit block
				else if (targetPos != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 1, targetPos[0], targetPos[1], targetPos[2]));
				}
			}
			else if (getKey == 2)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 2, target.getEntityId(), -1, -1));
				}
			}
			else if (getKey == 3)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 3, target.getEntityId(), -1, -1));
				}
//				//hit block
//				else if (targetPos != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 3, targetPos[0], targetPos[1], targetPos[2]));
//				}
			}
		}//end key for skill
	}
	
	/**
	 * handle keys for morph
	 */
	public static void handleSkillKeysMorph()
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		GameSettings keySet = ClientProxy.getGameSetting();
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		BasicEntityShip ship = null;
		
		if (capa != null && capa.morphEntity instanceof BasicEntityShip && ClientProxy.showMorphSkills) ship = (BasicEntityShip) capa.morphEntity;
		
		//keys for skill
		if (ClientProxy.keyPlayerSkillCD <= 0 && ship != null)
		{
			int getKey = -1;
			
			if (keySet.keyBindsHotbar[0].isKeyDown()) getKey = 0;
			else if (keySet.keyBindsHotbar[1].isKeyDown()) getKey = 1;
			else if (keySet.keyBindsHotbar[2].isKeyDown()) getKey = 2;
			else if (keySet.keyBindsHotbar[3].isKeyDown()) getKey = 3;
			else if (keySet.keyBindsHotbar[4].isKeyDown()) getKey = 4;
			
			if (getKey < 0) return;
			
			float range = ship.getAttrs().getAttackRange();
			if (range < 2F) range = 2F;
			ClientProxy.keyPlayerSkillCD = 4;
			
			//create exclude entity list
			ArrayList<Entity> exlist = new ArrayList<Entity>();
			exlist.add(player);
			exlist.add(ship);
			
			//get skill target
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(range, 1F, exlist);
			Entity target = null;
			int[] targetPos = null;
			
			//get skill target: entity
			if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.ENTITY)
			{
				target = hitObj.entityHit;
				//在目標上畫出標記
				ParticleHelper.spawnAttackParticleAtEntity(target, 0.3D, 5D, 0D, (byte)2);
			}
			
			//get skill target: block
			if (target == null) 
			{
				if (ship.getShipDepth() > 2D)
				{
					hitObj = BlockHelper.getPlayerMouseOverBlockThroughWater(range, 1F);
				}
				else
				{
					hitObj = BlockHelper.getPlayerMouseOverBlockOnWater(range, 1F);
				}
				
				if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					targetPos = new int[] {hitObj.getBlockPos().getX(), hitObj.getBlockPos().getY(), hitObj.getBlockPos().getZ()};
					//在目標上畫出標記
					ParticleHelper.spawnAttackParticleAt(targetPos[0]+0.5D, targetPos[1], targetPos[2]+0.5D, 0.3D, 5D, 0D, (byte)25);
				}
			}
			
			//fire only 1 key at a time
			//light attack
			if (getKey == 0)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 0, target.getEntityId(), -1, -1));
				}
			}
			//heavy attack
			else if (getKey == 1)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 1, target.getEntityId(), -1, -1));
				}
				//hit block
				else if (targetPos != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 1, targetPos[0], targetPos[1], targetPos[2]));
				}
			}
			//air light attack
			else if (getKey == 2)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 2, target.getEntityId(), -1, -1));
				}
			}
			//air heavy attack
			else if (getKey == 3)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 3, target.getEntityId(), -1, -1));
				}
//				//hit block
//				else if (targetPos != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 3, targetPos[0], targetPos[1], targetPos[2]));
//				}
			}
			//melee attack
			else if (getKey == 4)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 4, target.getEntityId(), -1, -1));
				}
			}
		}//end key for skill
	}
	
	/**
	 * handle keys for mounts
	 */
	protected static void handleSkillKeysRider()
	{
		GameSettings keySet = ClientProxy.getGameSetting();
		EntityPlayer player = ClientProxy.getClientPlayer();
		BasicEntityShip ship = null;
		
		if (player.getPassengers().size() > 0 && player.getPassengers().get(0) instanceof BasicEntityShip)
		{
			ship = (BasicEntityShip) player.getPassengers().get(0);
		}
		else
		{
			return;
		}
		
		//keys for GUI
		if (ClientProxy.keyMountActionCD <= 0)
		{
			//open ship GUI while riding
			if (keySet.keyBindInventory.isPressed() || keySet.keyBindPickBlock.isKeyDown())
			{
				LogHelper.debug("DEBUG: key event: open ship GUI");
				ClientProxy.keyMountActionCD = 8;
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountGUI));
				return;
			}
		}
		
		//keys for skill
		if (ClientProxy.keyPlayerSkillCD <= 0 && ship.getAttrs() != null)
		{
			int getKey = -1;
			
			if (keySet.keyBindsHotbar[0].isKeyDown()) getKey = 0;
			else if (keySet.keyBindsHotbar[1].isKeyDown()) getKey = 1;
			else if (keySet.keyBindsHotbar[2].isKeyDown()) getKey = 2;
			else if (keySet.keyBindsHotbar[3].isKeyDown()) getKey = 3;
			
			if (getKey < 0) return;
			
			float range = ship.getAttrs().getAttackRange();
			ClientProxy.keyPlayerSkillCD = 2;
			
			//create exclude entity list
			ArrayList<Entity> exlist = new ArrayList<Entity>();
			exlist.add(player);
			exlist.add(ship);
			exlist.add(ship.getHostEntity());
			
			//get skill target
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(range, 1F, exlist);
			Entity target = null;
			int[] targetPos = null;
			
			//get skill target: entity
			if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.ENTITY)
			{
				target = hitObj.entityHit;
				//在目標上畫出標記
				ParticleHelper.spawnAttackParticleAtEntity(target, 0.3D, 5D, 0D, (byte)2);
			}
			
			//get skill target: block
			if (target == null) 
			{
				if (ship.getShipDepth() >= 3D)
				{
					hitObj = BlockHelper.getPlayerMouseOverBlockThroughWater(range, 1F);
				}
				else
				{
					hitObj = BlockHelper.getPlayerMouseOverBlockOnWater(range, 1F);
				}
				
				if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					targetPos = new int[] {hitObj.getBlockPos().getX(), hitObj.getBlockPos().getY(), hitObj.getBlockPos().getZ()};
					//在目標上畫出標記
					ParticleHelper.spawnAttackParticleAt(targetPos[0]+0.5D, targetPos[1], targetPos[2]+0.5D, 0.3D, 5D, 0D, (byte)25);
				}
			}
			
			//fire only 1 key at a time
			if (getKey == 0)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 0, target.getEntityId(), -1, -1));
				}
			}
			else if (getKey == 1)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 1, target.getEntityId(), -1, -1));
				}
				//hit block
				else if (targetPos != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 1, targetPos[0], targetPos[1], targetPos[2]));
				}
			}
			else if (getKey == 2)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 2, target.getEntityId(), -1, -1));
				}
			}
			else if (getKey == 3)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.PlayerSkill, 3, target.getEntityId(), -1, -1));
				}
//				//hit block
//				else if (targetPos != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 3, targetPos[0], targetPos[1], targetPos[2]));
//				}
			}
		}//end key for skill
	}
	
	/**
	 * process player skill on packet receiving, SERVER SIDE
	 * 
	 * data: 0: attack type, 1: targetEID or pos.X, 2: pos.Y, 3: pos.Z
	 */
	public static void handlePlayerSkill(EntityPlayer player, int[] data)
	{
		BasicEntityMount mount = null;
		BasicEntityShip ship = null;
		
		//if player on ship mounts
		if (player.getRidingEntity() instanceof BasicEntityMount)
		{
			mount = (BasicEntityMount) player.getRidingEntity();
			ship = (BasicEntityShip) mount.getHostEntity();
			castPlayerSkill(ship, player, data);
			return;
		}
		//if ship riding player
		else if (player.getPassengers().size() > 0 && player.getPassengers().get(0) instanceof BasicEntityShip)
		{
			ship = (BasicEntityShip) player.getPassengers().get(0);
			castPlayerSkill(ship, player, data);
			return;
		}
		//if player is morph ship
		else if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
		{
			MetamorphHelper.handlePlayerSkillOfMorph(player, data);
			return;
		}
	}

	/**
	 * cast player skill
	 * data: 0: attack type, 1: targetEID or pos.X, 2: pos.Y, 3: pos.Z
	 */
	public static void castPlayerSkill(BasicEntityShip ship, EntityPlayer player, int[] data)
	{
		//check ship owner is player
		if (ship != null && TeamHelper.checkSameOwner(player, ship))
		{
			int skill = 0;
			
			//check attack type
			switch (data[0])
			{
			case 0:
				if (!ship.getStateFlag(ID.F.AtkType_Light)) return;
				skill = ID.T.MountSkillCD1;
			break;
			case 1:
				if (!ship.getStateFlag(ID.F.AtkType_Heavy)) return;
				skill = ID.T.MountSkillCD2;
			break;
			case 2:
				if (!ship.getStateFlag(ID.F.AtkType_AirLight)) return;
				skill = ID.T.MountSkillCD3;
			break;
			case 3:
				if (!ship.getStateFlag(ID.F.AtkType_AirHeavy)) return;
				skill = ID.T.MountSkillCD4;
			break;
			default:
				return;
			}
			
			//check skill cd
			if (ship.getStateTimer(skill) > 0) return;
			
			//check target exist
			Entity target = null;
			BlockPos targetPos = null;
			float rangeSq = ship.getAttrs().getAttackRange() * ship.getAttrs().getAttackRange();
			
			if (data[2] < 0)
			{
				target = EntityHelper.getEntityByID(data[1], player.world.provider.getDimension(), false);
				
				if (target != null && ship.getDistanceSq(target) > rangeSq)
				{
					target = null;
				}
			}
			else
			{
				targetPos = new BlockPos(data[1], data[2], data[3]);
				
				if (ship.getDistanceSqToCenter(targetPos) > rangeSq)
				{
					targetPos = null;
				}
			}
			
			//no target, return
			if (target == null && targetPos == null) return;
			
			//check target isn't friendly
			if (target != null && TeamHelper.checkSameOwner(ship, target)) return;
			
			//check ammo and attack target
			switch (data[0])
			{
			case 0:  //light attack
				if (target != null)
				{
					ship.attackEntityWithAmmo(target);
					ship.setStateTimer(ID.T.MountSkillCD1, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 1));
					ship.sendSyncPacketTimer(1);
				}
			break;
			case 1:   //heavy attack
				if (target != null) ship.attackEntityWithHeavyAmmo(target);
				else if (targetPos != null) ship.attackEntityWithHeavyAmmo(targetPos);
				
				if (target != null || targetPos != null)
				{
					ship.setStateTimer(ID.T.MountSkillCD2, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 2));
					ship.sendSyncPacketTimer(1);
				}
			break;
			case 2:   //light air attack
				if (ship instanceof BasicEntityShipCV && target != null)
				{
					((BasicEntityShipCV) ship).attackEntityWithAircraft(target);
					ship.setStateTimer(ID.T.MountSkillCD3, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 3));
					ship.setStateTimer(ID.T.MountSkillCD4, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 3));
					ship.sendSyncPacketTimer(1);
				}
			break;
			case 3:   //heavy air attack
				if (ship instanceof BasicEntityShipCV && target != null)
				{
					((BasicEntityShipCV) ship).attackEntityWithHeavyAircraft(target);
					ship.setStateTimer(ID.T.MountSkillCD3, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 4));
					ship.setStateTimer(ID.T.MountSkillCD4, CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 4));
					ship.sendSyncPacketTimer(1);
				}
			break;
			}
		}
	}
	
	
}