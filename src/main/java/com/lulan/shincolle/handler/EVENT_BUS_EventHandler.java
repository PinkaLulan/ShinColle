package com.lulan.shincolle.handler;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.other.EntityRensouhouBoss;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TargetHelper;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**for EVENT_BUS event only <br>
 * (not for FML event)
 * 
 * priority=EventPriority.NORMAL 事件優先權, 同事件會由高優先權的mod先跑
 * receiveCanceled=true 使事件被其他mod取消, 本mod依然可以接收
 */
public class EVENT_BUS_EventHandler {

	//change vanilla mob drop (add grudge), this is SERVER event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onDrop(LivingDropsEvent event) {
	    //mob drop grudge
		if(event.entity instanceof EntityMob || event.entity instanceof EntitySlime) {
	    	if(event.entity instanceof BasicEntityShipHostile) {
	    		BasicEntityShipHostile entity = (BasicEntityShipHostile)event.entity;
	    		
	    		if(entity.canDrop) {
	    			//set drop flag to false
	    			entity.canDrop = false;
	    			
	    			ItemStack bossEgg = ((BasicEntityShipHostile)event.entity).getDropEgg();
	    			
	    			if(bossEgg != null) {
	    				BasicEntityItem entityItem1 = new BasicEntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY+0.5D, event.entity.posZ, bossEgg);
			    		LogHelper.info("DEBUG : ship mob drop "+entityItem1.posX+" "+entityItem1.posY+" "+entityItem1.posZ);
			    		event.entity.worldObj.spawnEntityInWorld(entityItem1);
	    			}
	    		}	
	    	}
	    	
	    	//drop grudge
	    	if(!(event.entity instanceof EntityRensouhouBoss)) {
	    		//if config has drop rate setting
	    		int numGrudge = (int) ConfigHandler.dropGrudge;
//	    		LogHelper.info("DEBUG : drop grudge "+numGrudge+" "+ConfigHandler.dropGrudge);
	    		//若設定超過1, 則掉落多個 (ex: 5.5 = 5顆)
	    		if(numGrudge > 0) {
	    			ItemStack drop = new ItemStack(ModItems.Grudge, numGrudge);
			        event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, drop));
	    		}
	    		//值不到1, 機率掉落1個
	    		else {
	    			if(event.entity.worldObj.rand.nextFloat() <= ConfigHandler.dropGrudge) {
	    				ItemStack drop = new ItemStack(ModItems.Grudge, 1);
				        event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, drop));
	    			}
	    		}
	    		
	    		//剩餘不到1的值, 改為機率掉落
	    		if(event.entity.worldObj.rand.nextFloat() < (ConfigHandler.dropGrudge - numGrudge)) {
    				ItemStack drop = new ItemStack(ModItems.Grudge, 1);
			        event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, drop));
    			}
	    	}
	    }
	    
	    //ship drop egg, if canDrop is true, drop spawn egg and set canDrop to false
	    if(event.entity instanceof BasicEntityShip) {
	    	BasicEntityShip entity = (BasicEntityShip)event.entity;
	    	
	    	if(entity.getStateFlag(ID.F.CanDrop)) {
	    		//set flag to false to prevent multiple drop from unknown bug
	    		entity.setStateFlag(ID.F.CanDrop, false);
	    		
	    		//drop ship item
	    		ItemStack item = new ItemStack(ModItems.ShipSpawnEgg, 1, entity.getShipClass()+2);
		    	BasicEntityItem entityItem2 = new BasicEntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY+0.5D, event.entity.posZ, item);
		    	NBTTagCompound nbt = new NBTTagCompound();
		    	ExtendShipProps extProps = entity.getExtProps();
		    	
		    	//get inventory data
				NBTTagList list = new NBTTagList();
				for(int i = 0; i < extProps.slots.length; i++) {
					if(extProps.slots[i] != null) {
						NBTTagCompound item2 = new NBTTagCompound();
						item2.setByte("Slot", (byte)i);
						extProps.slots[i].writeToNBT(item2);
						list.appendTag(item2);
					}
				}
				
				//get attributes data
		    	int[] attrs = new int[8];
		    	
		    	if(entity.getLevel() > 1) attrs[0] = entity.getLevel() - 1;	//decrease level 1
		    	else attrs[0] = 1;
		    	
		    	attrs[1] = entity.getBonusPoint(ID.HP);
		    	attrs[2] = entity.getBonusPoint(ID.ATK);
		    	attrs[3] = entity.getBonusPoint(ID.DEF);
		    	attrs[4] = entity.getBonusPoint(ID.SPD);
		    	attrs[5] = entity.getBonusPoint(ID.MOV);
		    	attrs[6] = entity.getBonusPoint(ID.HIT);
		    	attrs[7] = entity.getStateFlagI(ID.F.IsMarried);
		    	
		    	/** OWNER SETTING
		    	 *  1. check player UID first
		    	 *  2. if (1) fail, check player UUID string
		    	 */
		    	
		    	//save owner UUID
		    	String ownerUUID = EntityHelper.getPetPlayerUUID(entity);
		    	nbt.setString("owner", ownerUUID);
		    	
		    	//save owner UID & name
		    	EntityPlayer owner = EntityHelper.getEntityPlayerByUID(entity.getStateMinor(ID.M.PlayerUID));
		    	
		    	if(owner != null) {
		    		nbt.setString("ownername", owner.getDisplayName());
		    	}
		    	
		    	nbt.setTag("ShipInv", list);		//save inventory data to nbt
		    	nbt.setIntArray("Attrs", attrs);	//save attributes data to nbt
		    	nbt.setInteger("PlayerID", entity.getStateMinor(ID.M.PlayerUID));
		    	nbt.setInteger("ShipID", entity.getStateMinor(ID.M.ShipUID));
		    	nbt.setString("customname", entity.getCustomNameTag());
		    	
		    	entityItem2.getEntityItem().setTagCompound(nbt);	  //save nbt to entity item
		    	event.entity.worldObj.spawnEntityInWorld(entityItem2);	//spawn entity item
	    	}
	    }
	}
	
	//apply ring effect: boost dig speed in water
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onGetBreakSpeed(PlayerEvent.BreakSpeed event) {
		ExtendPlayerProps extProps = (ExtendPlayerProps) event.entityPlayer.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null && (event.entityPlayer.isInWater() || event.entityPlayer.handleLavaMovement())) {
			int digBoost = extProps.getDigSpeedBoost() + 1;
			//boost speed
			event.newSpeed = event.originalSpeed * digBoost;
		}		
	}
	
	//apply ring effect: vision in the water
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onSetLiquidFog(EntityViewRenderEvent.FogDensity event) {
		if(event.entity.isInsideOfMaterial(Material.lava) ||
		   event.entity.isInsideOfMaterial(Material.water)){
			
			ExtendPlayerProps extProps = (ExtendPlayerProps) event.entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null && extProps.isRingActive()) {
				float fogDen = 0.1F - extProps.getMarriageNum() * 0.02F;
				
				if(fogDen < 0.01F) fogDen = 0.001F;
				
				event.setCanceled(true);	//取消原本的fog render
	            event.density = fogDen;		//重設fog濃度
	            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			}   
            
//			float fogStart = 0F;	//for linear fog
//			float fogEnd = 20F;		//for linear fog
//            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
//            GL11.glFogf(GL11.GL_FOG_START, fogStart);	//fog start distance, only for linear fog
//            GL11.glFogf(GL11.GL_FOG_END, fogEnd);		//fog end distance, only for linear fog
		}
	}
	
	/** entity death event */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEntityDeath(LivingDeathEvent event) {
		Entity ent = event.source.getSourceOfDamage();
		
		//add kills number
	    if(ent != null) {
	    	if(ent instanceof BasicEntityShip) {	//本體擊殺
	    		BasicEntityShip ship = (BasicEntityShip) ent;
	    		ship.addKills();
	    		ship.setStateMinor(ID.M.Morale, ship.getStateMinor(ID.M.Morale) + 2);
	    	}
	    	else if(ent instanceof IShipAttackBase) {	//其他召喚物擊殺
	    		if(((IShipAttackBase) ent).getHostEntity() != null &&
	    		   ((IShipAttackBase) ent).getHostEntity() instanceof BasicEntityShip) {
	    			((BasicEntityShip)((IShipAttackBase) ent).getHostEntity()).addKills();
	    		}
	    	}
	    }
	    
	    //save player ext data
	    if(event.entityLiving instanceof EntityPlayer) {
	    	EntityPlayer player = (EntityPlayer) event.entityLiving;
	    	ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
	    	
	    	LogHelper.info("DEBUG : player death: save player data: "+player.getDisplayName()+" "+player.getUniqueID());
	    	
	    	if(extProps != null) {
	    		LogHelper.info("DEBUG : player death: get player extProps");
	    		//save player nbt data
	    		NBTTagCompound nbt = new NBTTagCompound();
	    		extProps.saveNBTData(nbt);
	    		
	    		//save nbt to ServerProxy variable
	    		ServerProxy.setPlayerData(player.getUniqueID().toString(), nbt);
	    	}
	    }
	    else if(event.entityLiving instanceof BasicEntityShip) {
	    	if(!event.entityLiving.worldObj.isRemote) {
	    		//show emotes
		    	((BasicEntityShip)event.entityLiving).applyParticleEmotion(8);
				//emotes AOE
				EntityHelper.applyShipEmotesAOE(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, 16D, 6);
	    	}
	    }
	    else if(event.entityLiving instanceof BasicEntityShipHostile) {
	    	if(!event.entityLiving.worldObj.isRemote) {
	    		//show emotes
		    	((BasicEntityShipHostile)event.entityLiving).applyParticleEmotion(8);
				//emotes AOE
				EntityHelper.applyShipEmotesAOEHostile(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, 48D, 6);
	    	}
	    }
	}
	
	//add extend props to entity
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEntityConstructing(EntityConstructing event) {
	    //ship ext props
		if(event.entity instanceof BasicEntityShip && event.entity.getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME) == null) {
	    	LogHelper.info("DEBUG : entity constructing: on ship constructing "+event.entity.getEntityId());
	        event.entity.registerExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME, new ExtendShipProps());
		}

		//player ext props
		if(event.entity instanceof EntityPlayer && !(event.entity instanceof FakePlayer) &&
		   event.entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME) == null) {
			LogHelper.info("DEBUG : entity constructing: on player constructing "+event.entity.getEntityId()+" "+event.entity.getClass().getSimpleName());
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME, new ExtendPlayerProps());
		}
	}
	
	/**Add ship mob spawn in squid event
	 * FIX: add spawn limit (1 spawn within 32 blocks)
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onSquidSpawn(LivingSpawnEvent.CheckSpawn event) {
		if(event.entityLiving instanceof EntitySquid) {
			if(event.world.rand.nextInt((int)ConfigHandler.scaleMobSubm[6]) == 0) {
				//check 64x64 range
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(event.x-32D, event.y-32D, event.z-32D, event.x+32D, event.y+32D, event.z+32D);
				List ListMob = event.world.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);

				//list低於1個表示沒有找到其他boss
	            if(ListMob.size() < 1) {
	            	LogHelper.info("DEBUG : spawn ship mob at "+event.x+" "+event.y+" "+event.z+" rate "+ConfigHandler.scaleMobSubm[6]);
	            	EntityLiving entityToSpawn;
	            	//50%:U511 50%:Ro500
	            	if(event.world.rand.nextInt(2) == 0) {
	            		entityToSpawn = (EntityLiving) EntityList.createEntityByName("shincolle.EntitySubmU511Mob", event.world);
					}
	            	else {
	            		entityToSpawn = (EntityLiving) EntityList.createEntityByName("shincolle.EntitySubmRo500Mob", event.world);
	            	}
	            	
					entityToSpawn.posX = event.x;
					entityToSpawn.posY = event.y;
					entityToSpawn.posZ = event.z;
					entityToSpawn.setPosition(event.x, event.y, event.z);
					event.world.spawnEntityInWorld(entityToSpawn);
	            }	
			}
		}
	}
	
	/**world load event
	 * init MapStorage here
	 * 由於global mapstorage不管在world都是讀取同一個handler, 所以不檢查world id, 隨便一個world皆可
	 * 若為perMapStorage, 則是不同world各有一份
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldLoad(WorldEvent.Load event) {
		LogHelper.info("DEBUG : on world load: "+event.world.provider.dimensionId);
		
		if(event.world != null && !ServerProxy.initServerFile) {
			ServerProxy.initServerProxy(event.world);
		}
	}
	
//	/**world unload event
//	 * save ship team list here, for SINGLEPLAYER ONLY
//	 * for multiplayer: PlayerLoggedOutEvent
//	 * 
//	 * logout event只會在多人遊戲下發出, 單機遊戲必須使用此world unload event
//	 */
//	@SubscribeEvent
//	public void onWorldUnload(WorldEvent.Unload event) {
//		LogHelper.info("DEBUG : on world unload: "+event.world.provider.dimensionId+" phase "+event.getPhase());
//		
//		//save world file, mainly for single player
//		if(!ServerProxy.savedServerFile) {  //if file not saved
//			ServerProxy.saveServerProxy();
//			LogHelper.info("DEBUG : on world unload: save world data to disk");
//		}
//	}
	
//	/** on player left click attack event, BOTH SIDE EVENT
//	 *  set ship's revenge target when player attack
//	 */
//	@SubscribeEvent
//	public void onPlayerAttack(AttackEntityEvent event) {
//		if(event.entityPlayer != null && !event.entityPlayer.worldObj.isRemote) {
//			LogHelper.info("DEBUG : get attack event: "+event.entityPlayer);
//		}
//	}
	
	/** on entity attack event, BOTH SIDE EVENT
	 *  set ship's revenge target if player attack or be attacked
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEntityAttack(LivingAttackEvent event) {
		//server side only
		if(event.entity != null && !event.entity.worldObj.isRemote) {
			//attacker is player
			if(event.source.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				//get player's ship within 20 blocks
				TargetHelper.setRevengeTargetAroundPlayer(player, 32D, event.entity);
//				LogHelper.info("DEBUG : attack event: "+player+" "+event.entity);
			}//end get player
			
			//player is attacked
			if(event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				//get player's ship within 20 blocks
				TargetHelper.setRevengeTargetAroundPlayer(player, 32D, event.source.getEntity());
//				LogHelper.info("DEBUG : attack event: "+player+" "+event.source.getEntity());
			}
			
			//hostile ship is attacked, call for help
			if(event.entity instanceof BasicEntityShipHostile) {
				if(event.source.getEntity() instanceof BasicEntityShipHostile) return;
				
				TargetHelper.setRevengeTargetAroundHostileShip((BasicEntityShipHostile) event.entity, 64D, event.source.getEntity());
			}
			
		}//end server side
	}

	
}
