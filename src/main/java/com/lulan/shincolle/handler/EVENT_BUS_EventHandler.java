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
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.EntityRensouhouBoss;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**for EVENT_BUS event only <br>
 * (not for FML event)
 */
public class EVENT_BUS_EventHandler {

	//change vanilla mob drop (add grudge), this is SERVER event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventDrop(LivingDropsEvent event) {
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
	    	
	    	if(!(event.entity instanceof EntityRensouhouBoss)) {
	    		ItemStack drop = new ItemStack(ModItems.Grudge, 1);
		        event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, drop));
	    	}
	    }
	    
	    //ship drop egg, if canDrop is true, drop spawn egg and set canDrop to false
	    if(event.entity instanceof BasicEntityShip) {
	    	BasicEntityShip entity = (BasicEntityShip)event.entity;
	    	
	    	if(entity.getStateFlag(ID.F.CanDrop)) {
	    		//set flag to false to prevent multiple drop from unknown bug
	    		entity.setStateFlag(ID.F.CanDrop, false);
	    		
	    		//drop ship item
	    		ItemStack item = new ItemStack(ModItems.ShipSpawnEgg, 1, entity.getShipID()+2);
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
		    	
		    	//save nbt and spawn entity item
		    	nbt.setTag("ShipInv", list);	//save inventory data to nbt
		    	nbt.setIntArray("Attrs", attrs);	//save attributes data to nbt
		    	entityItem2.getEntityItem().setTagCompound(nbt);	  //save nbt to entity item
		    	event.entity.worldObj.spawnEntityInWorld(entityItem2);	//spawn entity item
	    	}
	    }
	}
	
	//apply ring effect: boost dig speed in water
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventGetBreakSpeed(PlayerEvent.BreakSpeed event) {
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
	public void eventSetLiquidFog(EntityViewRenderEvent.FogDensity event) {
		if(event.entity.isInsideOfMaterial(Material.lava) ||
		   event.entity.isInsideOfMaterial(Material.water)){
			
			ExtendPlayerProps extProps = (ExtendPlayerProps) event.entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null && extProps.isRingActive()) {
				float fogDen = 0.1F - (float)extProps.getMarriageNum() * 0.02F;
				
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
	
	//add kills number
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventDeath(LivingDeathEvent event) {
		Entity ent = event.source.getSourceOfDamage();
		
	    if(ent != null) {
	    	if(ent instanceof BasicEntityShip) {	//本體擊殺
	    		((BasicEntityShip)ent).addKills();
	    	}
	    	else if(ent instanceof IShipAttackBase) {	//其他召喚物擊殺
	    		if(((IShipAttackBase) ent).getOwner() != null &&
	    		   ((IShipAttackBase) ent).getOwner() instanceof BasicEntityShip) {
	    			((BasicEntityShip)((IShipAttackBase) ent).getOwner()).addKills();
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
	    		
	    		//save nbt to commonproxy variable
	    		CommonProxy.storeEntityData(player.getUniqueID().toString(), nbt);
	    	}
	    }
	}
	
	//add extend props to entity
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
	    //ship ext props
		if(event.entity instanceof BasicEntityShip && event.entity.getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME) == null) {
	    	LogHelper.info("DEBUG : on ship constructing");
	        event.entity.registerExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME, new ExtendShipProps());
	    }

		//player ext props
		if(event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME) == null) {
			LogHelper.info("DEBUG : on player constructing");
			EntityPlayer player = (EntityPlayer) event.entity;	
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				//restore player data from commonproxy variable
		        NBTTagCompound nbt = CommonProxy.getEntityData(player.getUniqueID().toString());
		        
		    	if(nbt != null) {
		        	LogHelper.info("DEBUG : player constructing: restore player data (EVENT BUS event)");		        	
		        	extProps.loadNBTData(nbt);
		        }
			}
			else {
				LogHelper.info("DEBUG : add player extend props");
				player.registerExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME, new ExtendPlayerProps());
			}
		}
	}
	
	/**Add ship mob spawn in squid event
	 * FIX: add spawn limit (1 spawn within 32 blocks)
	 */
	@SubscribeEvent
	public void onSquidSpawn(LivingSpawnEvent.CheckSpawn event) {
		if(event.entityLiving instanceof EntitySquid) {
			if(event.world.rand.nextInt((int)ConfigHandler.scaleMobU511[ID.SpawnPerSquid]) == 0) {
				//check 64x64 range
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(event.x-16D, event.y-32D, event.z-16D, event.x+16D, event.y+32D, event.z+16D);
				List ListMob = event.world.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);

				//list低於1個表示沒有找到其他boss
	            if(ListMob.size() < 1) {
	            	LogHelper.info("DEBUG : spawn ship mob at "+event.x+" "+event.y+" "+event.z+" rate "+ConfigHandler.scaleMobU511[ID.SpawnPerSquid]);
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
	

	
}
