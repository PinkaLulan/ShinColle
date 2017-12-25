package com.lulan.shincolle.shipskill;

import java.util.ArrayList;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

public class ShipSkillHandler
{
	
	
	/**
	 * get ship skill host type
	 *   -1: no skill
	 *   0: skill of mounts
	 *   1: skill of morph
	 */
	public static int getShipSkillHostType(EntityPlayer player)
	{
		if (player.getRidingEntity() instanceof BasicEntityMount) return 0;
		
		//client side
		if (player.world.isRemote)
		{
			if (ClientProxy.activeMorphSkills) return 1;
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
		}
	}
	
	/**
	 * handle keys for mounts
	 */
	protected static void handleSkillKeysMounts()
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
		GameSettings keySet = ClientProxy.getGameSetting();
		
		//keys for movement
		if (ClientProxy.keyMountCD <= 0)
		{
			//open ship GUI while riding, NO SUPPORT FOR MOUSE!
			if (keySet.keyBindInventory.isPressed())
			{
				LogHelper.debug("DEBUG: key event: open ship GUI");
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountGUI));
				return;
			}
			
			//change renderer viewer, support for mouse keys
			if (keySet.keyBindPickBlock.isPressed() || keySet.keyBindPickBlock.isKeyDown())
			{
				LogHelper.debug("DEBUG: key event: player view "+ClientProxy.isViewPlayer);
				ClientProxy.keyMountCD = 8;
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
				ClientProxy.keyMountCD = 2;
				
				//server跟client必須同時設定移動狀態, 移動顯示才會順暢, 只靠server設定移動會不連續
				mount.keyPressed = newKeys;		//set client moving key
				mount.keyTick = 10;				//continue moving for 10 ticks
				
				//send mounts move key packet
				LogHelper.debug("DEBUG: key event: mounts move key: "+Integer.toBinaryString(newKeys));
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountMove, ClientProxy.rideKeys));
			}
		}//end keys for movement
		
		//keys for skill
		if (ClientProxy.keyMountSkillCD <= 0 && mount.getAttrs() != null)
		{
			int getKey = -1;
			
			if (keySet.keyBindsHotbar[0].isKeyDown()) getKey = 0;
			else if (keySet.keyBindsHotbar[1].isKeyDown()) getKey = 1;
			else if (keySet.keyBindsHotbar[2].isKeyDown()) getKey = 2;
			else if (keySet.keyBindsHotbar[3].isKeyDown()) getKey = 3;
			
			if (getKey < 0) return;
			
			float range = mount.getAttrs().getAttackRange();
			ClientProxy.keyMountSkillCD = 2;
			
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
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 0, target.getEntityId(), -1, -1));
				}
			}
			else if (getKey == 1)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 1, target.getEntityId(), -1, -1));
				}
				//hit block
				else if (targetPos != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 1, targetPos[0], targetPos[1], targetPos[2]));
				}
			}
			else if (getKey == 2)
			{
				//hit entity only
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 2, target.getEntityId(), -1, -1));
				}
			}
			else if (getKey == 3)
			{
				//hit entity
				if (target != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 3, target.getEntityId(), -1, -1));
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
		//TODO
//		EntityPlayer player = ClientProxy.getClientPlayer();
//		GameSettings keySet = ClientProxy.getGameSetting();
//		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
//		
//		//keys for skill, the cooldown share with mounts skill
//		if (ClientProxy.keyMountSkillCD <= 0 && capa != null)
//		{
//			int getKey = -1;
//			
//			if (keySet.keyBindsHotbar[0].isKeyDown()) getKey = 0;
//			else if (keySet.keyBindsHotbar[1].isKeyDown()) getKey = 1;
//			else if (keySet.keyBindsHotbar[2].isKeyDown()) getKey = 2;
//			else if (keySet.keyBindsHotbar[3].isKeyDown()) getKey = 3;
//			
//			if (getKey < 0) return;
//			
//			float range = mount.getAttrs().getAttackRange();
//			ClientProxy.keyMountSkillCD = 2;
//			
//			//create exclude entity list
//			ArrayList<Entity> exlist = new ArrayList<Entity>();
//			exlist.add(player);
//			exlist.add(mount);
//			exlist.add(mount.getHostEntity());
//			
//			//get skill target
//			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(range, 1F, exlist);
//			Entity target = null;
//			int[] targetPos = null;
//			
//			//get skill target: entity
//			if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.ENTITY)
//			{
//				target = hitObj.entityHit;
//				//在目標上畫出標記
//				ParticleHelper.spawnAttackParticleAtEntity(target, 0.3D, 5D, 0D, (byte)2);
//			}
//			
//			//get skill target: block
//			if (target == null) 
//			{
//				if (mount.getShipDepth() >= 3D)
//				{
//					hitObj = BlockHelper.getPlayerMouseOverBlockThroughWater(range, 1F);
//				}
//				else
//				{
//					hitObj = BlockHelper.getPlayerMouseOverBlockOnWater(range, 1F);
//				}
//				
//				if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.BLOCK)
//				{
//					targetPos = new int[] {hitObj.getBlockPos().getX(), hitObj.getBlockPos().getY(), hitObj.getBlockPos().getZ()};
//					//在目標上畫出標記
//					ParticleHelper.spawnAttackParticleAt(targetPos[0]+0.5D, targetPos[1], targetPos[2]+0.5D, 0.3D, 5D, 0D, (byte)25);
//				}
//			}
//			
//			//fire only 1 key at a time
//			if (getKey == 0)
//			{
//				//hit entity only
//				if (target != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 0, target.getEntityId(), -1, -1));
//				}
//			}
//			else if (getKey == 1)
//			{
//				//hit entity
//				if (target != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 1, target.getEntityId(), -1, -1));
//				}
//				//hit block
//				else if (targetPos != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 1, targetPos[0], targetPos[1], targetPos[2]));
//				}
//			}
//			else if (getKey == 2)
//			{
//				//hit entity only
//				if (target != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 2, target.getEntityId(), -1, -1));
//				}
//			}
//			else if (getKey == 3)
//			{
//				//hit entity
//				if (target != null)
//				{
//					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 3, target.getEntityId(), -1, -1));
//				}
////				//hit block
////				else if (targetPos != null)
////				{
////					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountSkill, 3, targetPos[0], targetPos[1], targetPos[2]));
////				}
//			}
//		}
	}
	
	
}