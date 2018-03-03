package com.lulan.shincolle.handler;

import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.lulan.shincolle.capability.CapaShipSavedValues;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.playerskill.ShipSkillHandler;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.InventoryHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.RenderHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;
import com.lulan.shincolle.worldgen.ChestLootTable;

import mchorse.metamorph.api.events.MorphActionEvent;
import mchorse.metamorph.api.events.MorphEvent;
import mchorse.metamorph.api.events.SpawnGhostEvent;
import mchorse.metamorph.api.morphs.EntityMorph;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.FogMode;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * after mc1.9:
 * all event bus become one bus: EVENT_BUS
 * 
 * priority=EventPriority.NORMAL 事件優先權, 同事件會由高優先權的mod先跑
 * receiveCanceled=true 使事件被其他mod取消, 本mod依然可以接收
 */
public class EventHandler
{
	
	/**
	 * register blocks
	 * @throws Exception 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void registerBlocks(RegistryEvent.Register<Block> event) throws Exception
	{
		LogHelper.info("INFO: register: blocks: side: "+FMLCommonHandler.instance().getSide());
		ModBlocks.register(event);
	}
	
	/**
	 * register items
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		LogHelper.info("INFO: register: items: side: "+FMLCommonHandler.instance().getSide());
//	    event.getRegistry().registerAll(block1, block2, ...);
	}
	
	/**
	 * register entities
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
		LogHelper.info("INFO: register: entities: side: "+FMLCommonHandler.instance().getSide());
//	    event.getRegistry().registerAll(block1, block2, ...);
	}
	
	/**
	 * register sounds
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void registerSounds(RegistryEvent.Register<SoundEvent> event)
	{
		LogHelper.info("INFO: register: sounds: side: "+FMLCommonHandler.instance().getSide());
//	    event.getRegistry().registerAll(block1, block2, ...);
	}
	
	/**
	 * register recipes
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		LogHelper.info("INFO: register: recipes: side: "+FMLCommonHandler.instance().getSide());
//	    event.getRegistry().registerAll(block1, block2, ...);
	}
	
	//change vanilla mob drop (add grudge), this is SERVER event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onDrop(LivingDropsEvent event)
	{
		Entity host = event.getEntity();

    	//mob: drop grudge
    	if (host instanceof IMob || host instanceof EntitySlime || host instanceof EntityGolem)
    	{
    		if (host.world.getGameRules().getBoolean("doMobLoot"))
    		{
        		//if config has drop rate setting
        		int numGrudge = (int) ConfigHandler.dropGrudge;
        		
        		//若設定超過1, 則掉落多個 (ex: 5.5 = 5顆)
        		if (numGrudge > 0)
        		{
        			ItemStack drop = new ItemStack(ModItems.Grudge, numGrudge);
    		        event.getDrops().add(new EntityItem(host.world, host.posX, host.posY, host.posZ, drop));
        		}
        		//值不到1, 機率掉落1個
        		else
        		{
        			if (host.world.rand.nextFloat() <= ConfigHandler.dropGrudge)
        			{
        				ItemStack drop = new ItemStack(ModItems.Grudge, 1);
    			        event.getDrops().add(new EntityItem(host.world, host.posX, host.posY, host.posZ, drop));
        			}
        		}
        		
        		//剩餘不到1的值, 改為機率掉落 (ex: 0.6 = 60%掉一顆)
        		if (host.world.rand.nextFloat() < (ConfigHandler.dropGrudge - (float)numGrudge))
        		{
    				ItemStack drop = new ItemStack(ModItems.Grudge, 1);
    		        event.getDrops().add(new EntityItem(host.world, host.posX, host.posY, host.posZ, drop));
    			}
    		}
    	}
		
	}
	
	/**
	 * BREAK BLOCK SPEED EVENT: BOTH SIDE
	 * apply ring effect: boost dig speed in water
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onGetBreakSpeed(BreakSpeed event)
	{
		if (ConfigHandler.ringAbility[2] > 0)
		{
			EntityPlayer player = event.getEntityPlayer();
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null && capa.hasRing() && capa.isRingActive() &&
				EntityHelper.checkEntityIsInLiquid(player))
			{
				int num = capa.getMarriageNum();
				if (num > ConfigHandler.ringAbility[2]) num = ConfigHandler.ringAbility[2];
				float digBoost = num * 0.2F + 1F;
				event.setNewSpeed(event.getOriginalSpeed() * 5F * digBoost);
			}
		}
	}
	
	//apply ring effect: vision in the water
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onSetLiquidFog(FogDensity event)
	{
		if (event.getEntity() != null && ConfigHandler.ringAbility[3] >= 0 &&
			EntityHelper.checkEntityIsInLiquid(event.getEntity()))
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();
			
			if (capa != null && capa.hasRing() && capa.isRingActive())
			{
				float fogDen = event.getDensity();
				
				if (fogDen >= 0.0001F)
				{
					//0 = always no fog
					if (ConfigHandler.ringAbility[3] == 0)
					{
						fogDen = 0.0001F;
					}
					//1~N = reduce fog by married number
					else
					{
						fogDen *= (float)(ConfigHandler.ringAbility[3] - capa.getMarriageNum()) / (float)ConfigHandler.ringAbility[3];
						if (fogDen < 0.0001F) fogDen = 0.0001F;
					}
					
					event.setCanceled(true);	//取消原本的fog render
		            event.setDensity(fogDen);	//重設fog濃度
		            GlStateManager.setFog(FogMode.EXP);
				}
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
	public void onEntityDeath(LivingDeathEvent event)
	{
		if (event.getEntity() != null && !event.getEntity().world.isRemote)
		{
			//若ship死亡, 則更新ship cache
	    	if (event.getEntity() instanceof BasicEntityShip)
	    	{
	    		BasicEntityShip ship = (BasicEntityShip) event.getEntity();
	    		ship.updateShipCacheDataWithoutNewID();
	    	}

			//add kills number
			Entity ent = event.getSource().getTrueSource();
			
	    	//由本體擊殺
	    	if (ent instanceof BasicEntityShip)
	    	{
	    		BasicEntityShip ship = (BasicEntityShip) ent;
	    		ship.addKills();
	    		ship.addMorale(2);
	    	}
	    	//由召喚物擊殺
	    	else if (ent instanceof IShipAttackBase)
	    	{
	    		if (((IShipAttackBase) ent).getHostEntity() instanceof BasicEntityShip)
	    		{
	    			((BasicEntityShip)((IShipAttackBase) ent).getHostEntity()).addKills();
	    		}
	    	}
		    
	    	//show attack emote
		    EntityLivingBase host = event.getEntityLiving();

		    //show dead emote
		    if (host instanceof BasicEntityShip)
		    {
		    	if (!host.world.isRemote)
		    	{
			    	((BasicEntityShip) host).applyParticleEmotion(8);
					EntityHelper.applyShipEmotesAOE(host.world, host.posX, host.posY, host.posZ, 16D, 6);
		    	}
		    }
		    //show dead emote
		    else if (host instanceof BasicEntityShipHostile)
		    {
		    	if (!host.world.isRemote)
		    	{
			    	((BasicEntityShipHostile) host).applyParticleEmotion(8);
					EntityHelper.applyShipEmotesAOEHostile(host.world, host.posX, host.posY, host.posZ, 48D, 6);
		    	}
		    }
		}
	}
	
	/**
	 * player clone event: wasDead: true: player respawn, false: back from TheEnd 
	 *  if cloned, save/load the player data
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerClone(Clone event)
	{
        //restore data when player dead or back from End
        if (event.getEntityPlayer() != null && event.getOriginal() != null)
        {
        	LogHelper.debug("DEBUG: get player clone event: "+event.getOriginal());

        	//copy data from original player to new player
        	CapaTeitoku capa1 = CapaTeitoku.getTeitokuCapability(event.getOriginal());
        	CapaTeitoku capa2 = CapaTeitoku.getTeitokuCapability(event.getEntityPlayer());
        	
        	if (capa1 != null && capa2 != null)
        	{
        		//init new player capa data
        		capa2.init(event.getEntityPlayer());
        		
        		//copy data to new player
        		NBTTagCompound nbt = capa1.saveNBTData(new NBTTagCompound());
        		capa2.loadNBTData(nbt);
        		capa2.sendSyncPacket(0);
        		
        		LogHelper.debug("DEBUG: player clone: restore player data: eid: "+event.getEntityPlayer().getEntityId()+" pid: "+capa2.getPlayerUID());
        		ServerProxy.updatePlayerID(event.getEntityPlayer());
        	}
        }
	}
	
//	/**Add ship mob spawn in squid event
//	 * FIX: add spawn limit (1 spawn within 32 blocks)
//	 */
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onSquidSpawn(LivingSpawnEvent.CheckSpawn event) {
//		if (event.entityLiving instanceof EntitySquid)
//		{
//			if (event.world.rand.nextInt((int) ConfigHandler.scaleMobSmall[6]) == 0)
//			{
//				//check 64x64 range
//				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(event.x-16D, event.y-32D, event.z-16D, event.x+16D, event.y+32D, event.z+16D);
//				List ListMob = event.world.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);
//
//				//list低於1個表示沒有找到其他boss
//	            if (ListMob.size() < 1)
//	            {
//	            	LogHelper.debug("DEBUG: spawn ship mob at "+event.x+" "+event.y+" "+event.z+" rate "+ConfigHandler.scaleMobSmall[6]);
//	            	
//	            	//get random mob
//	            	String shipname = ShipCalc.getRandomMobToSpawnName(0);
//	            	EntityLiving entityToSpawn = (EntityLiving) EntityList.createEntityByName(shipname, event.world);
//	            	
//	            	//spawn mob
//	            	if (entityToSpawn != null)
//	            	{
//	            		entityToSpawn.posX = event.x;
//						entityToSpawn.posY = event.y;
//						entityToSpawn.posZ = event.z;
//						entityToSpawn.setPosition(event.x, event.y, event.z);
//						event.world.spawnEntityInWorld(entityToSpawn);
//	            	}
//	            }//end nearby mob ship check
//			}//end squid random
//		}//end spawn squid
//	}
	
	/**world load event
	 * init MapStorage here
	 * 由於global mapstorage是所有world共通一個, 所以任意world取得都是同一個storage
	 * 若為perMapStorage, 則是不同world各有一份
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldLoad(Load event)
	{
		if (ServerProxy.initServerFile && event.getWorld() != null)
		{
			ServerProxy.initServerProxy(event.getWorld());
		}
	}
	
	/**world unload event
	 * 若已經發出過FMLServerStoppingEvent, 表示伺服器要關閉, 全部world都要unload
	 * 此時就將server world data標記要儲存回disk
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onWorldUnload(Unload event)
	{
		if (ServerProxy.saveServerFile)
		{
			ServerProxy.saveServerProxy();
		}
	}
	
//	/** on player left click attack event, BOTH SIDE EVENT
//	 *  set ship's revenge target when player attack
//	 */
//	@SubscribeEvent
//	public void onPlayerAttack(AttackEntityEvent event) {
//		if(event.entityPlayer != null && !event.entityPlayer.worldObj.isRemote) {
//			LogHelper.debug("DEBUG: get attack event: "+event.entityPlayer);
//		}
//	}
//	
//	/** log all ship on constructing */
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onEntityConstructing(EntityConstructing event)
//	{
//		if (event.getEntity() instanceof BasicEntityShip)
//		{
//			BasicEntityShip ship = (BasicEntityShip) event.getEntity();
//			LogHelper.debug("DEBUG: on entity constructing: tick: "+FMLCommonHandler.instance().getSide()+" "+ServerProxy.serverTicks+" ship: "+
//							ship);
//			ships.add(ship);
//		}
//	}
//	
//	/**
//	 * remove ship if join world success
//	 * if ship fail to join world, respawn ship later
//	 * 
//	 * FIX FOR: rider be removed when mount is in different chunk on chunk load
//	 */
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onEntityJoinWorld(EntityJoinWorldEvent event) throws Exception
//	{
//		if (event.getEntity() instanceof BasicEntityShip)
//		{
//			BasicEntityShip ship = (BasicEntityShip) event.getEntity();
//			LogHelper.debug("DEBUG: on entity join world: tick: "+FMLCommonHandler.instance().getSide()+" "+ServerProxy.serverTicks+" ship: "+
//							ship.getShipUID()+" "+ship);
//			
//			//NOTE: to avoid ConcurrentModificationException, use removeIf rather than forEach remove
//			ships.removeIf(s -> ship.equals(s));
//		}
//	}
	
	/** on entity damaged, BOTH SIDE EVENT
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEntityDamaged(LivingHurtEvent event)
	{
		if (CommonProxy.activeMetamorph) MetamorphHelper.onMorphDamagedHelper(event);
	}

	/** on entity attack event, BOTH SIDE EVENT
	 *  set ship's revenge target if player attack or be attacked
	 *  
	 *  attacker is summons:
	 *  if summoner dist < 32 blocks, revenge target = summoner
	 *  if summoner dist > 32 blocks, revenge target = summons
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEntityAttack(LivingAttackEvent event)
	{
		Entity target = event.getEntity();								//entity been attacked
		Entity attacker = event.getSource().getImmediateSource();		//summons
		Entity attackerSource = event.getSource().getTrueSource();		//summoner
		
		//server side only
		if(target != null && !target.world.isRemote)
		{
			//prevnt fall damage to player while riding
			if (target instanceof EntityPlayer)
			{
				if (target.getRidingEntity() instanceof BasicEntityMount &&
					(event.getSource() == DamageSource.FALL ||
					 event.getSource() == DamageSource.IN_WALL))
				{
					event.setCanceled(true);
					return;
				}
				
				//immune to fire
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) target);
				if (capa.hasRing() && capa.isRingActive() && ConfigHandler.ringAbility[4] >= 0 &&
					capa.getMarriageNum() >= ConfigHandler.ringAbility[4] && event.getSource().isFireDamage())
				{
					//disable fire damage
					if (target.isBurning()) target.extinguish();
					event.setCanceled(true);
					return;
				}
			}
			
			//other damage
			if (attackerSource != null)
			{
				double dist = target.getDistanceSq(attackerSource);
				
				//check attacker
				//attacker is player
				if (attackerSource instanceof EntityPlayer)
				{
					TargetHelper.setRevengeTargetAroundPlayer((EntityPlayer) attackerSource, 32D, target);
				}
				
				//check attack source
				//player been attacked
				if (target instanceof EntityPlayer)
				{
					if (dist < 1024D)
					{
						TargetHelper.setRevengeTargetAroundPlayer((EntityPlayer) target, 32D, attackerSource);
					}
					else
					{
						TargetHelper.setRevengeTargetAroundPlayer((EntityPlayer) target, 32D, attacker);
					}
				}
				//hostile ship been attacked, call for help
				else if (target instanceof BasicEntityShipHostile)
				{
					if (attackerSource instanceof BasicEntityShipHostile) return;
					
					if (dist < 1024D)
					{
						TargetHelper.setRevengeTargetAroundHostileShip((BasicEntityShipHostile) target, 64D, attackerSource);
					}
					else
					{
						TargetHelper.setRevengeTargetAroundHostileShip((BasicEntityShipHostile) target, 64D, attacker);
					}
				}
			}
		}//end server side
	}
	
	/** on entity entering chunk
	 *  apply chunk loader
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEnteringChunk(EnteringChunk event)
	{
		//get ship
		if (event.getEntity() instanceof BasicEntityShip)
		{
			BasicEntityShip ship = (BasicEntityShip) event.getEntity();
			
			//update chunk loader
			ship.updateChunkLoader();
			
			//FIX: "Wrong location!" bug
	        int i = MathHelper.floor(ship.posX / 16D);
	        int j = MathHelper.floor(ship.posZ / 16D);

	        if (i != event.getNewChunkX() || j != event.getNewChunkZ())
	        {
	        	ship.isDead = false;
	        	
	        	//stop riding
	        	ship.dismountRidingEntity();
	        }
		}
	}
	
	//edit vanilla chest content
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onChestSpawn(LootTableLoadEvent event)
	{
		if (event.getName() != null)
		{
			ChestLootTable.editLoot(event);
		}//end get chest
	}
	
	//player update tick, tick TWICE every tick (preTick + postTick) and BOTH SIDE (client + server)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			
			/** SERVER SIDE */
			if (!event.player.world.isRemote)
			{
				//null check
				if (capa == null)
				{
					LogHelper.debug("DEBUG: player ticking: waiting for capa init: "+event.player.ticksExisted+" "+capa);
					return;
				}
				//check can ticking
				else
				{
					if (capa.needInit)
					{
						capa.init(event.player);
					}
				}
				
				boolean syncTeamList = false;
				
				//every 32 ticks
				if (event.player.ticksExisted > 0 && (event.player.ticksExisted & 31) == 0)
				{
					//DEBUG TODO
//					Item.REGISTRY.forEach((k) ->
//					{
//						Item i2 = Item.getByNameOrId(k.getRegistryName().toString());
//					});
//					for (String oreName : OreDictionary.getOreNames())
//					{	//list all oreDictionary  (DEBUG)
//						LogHelper.info(oreName);
//					}
					
					/**
					 * update player UID or generate UID for new player
					 */
					if (capa.getPlayerUID() < 100)
					{
						//gerenate new UID
			        	ServerProxy.updatePlayerID(event.player);
			        	
				        //check player UID again, stop ticking if fail
				        if (capa.getPlayerUID() < 100)
				        {
				        	//update uid fail, return
				        	LogHelper.debug("DEBUG: player tick: generate new player UID fail, stop player ticking.");
				        	return;
						}
				        else
				        {
				        	//sync extProps to client
					        capa.sendSyncPacket(0);
				        	//save extProps to ServerProxy
							LogHelper.debug("DEBUG: player tick: generate new player UID, save player extProps in ServerProxy");
				        }
					}
					
					//team list fast update
					if (event.player.ticksExisted == 64)
					{
						ServerProxy.updatePlayerID(event.player);
						TeamHelper.updateTeamList(event.player, capa);
						syncTeamList = true;
					}
					
					//every 128 ticks
					if ((event.player.ticksExisted & 127) == 0)
					{
						//spawn mob ship
						EntityHelper.spawnMobShip(event.player, capa);
						
						//sync unit names
						capa.sendSyncPacket(8);
						
						//every 256 ticks
						if ((event.player.ticksExisted & 255) == 0)
						{
							//update team list
							TeamHelper.updateTeamList(event.player, capa);
							syncTeamList = true;
						}//end every 256 ticks
					}//end every 128 ticks
				}//end every 32 ticks
				
				//spawn boss ticking
				EntityHelper.spawnBossShip(event.player, capa);
				
				//ally cooldown--
				int allycd = capa.getPlayerTeamCooldown();
				if (allycd > 0) capa.setPlayerTeamCooldown(--allycd);
				
				//init ship UID
				if (!capa.initSID && (event.player.ticksExisted & 15) == 0) 
				{
					//update ship temaList
					capa.updateShipEntityBySID();
					
					//sync flag
					CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.FlagInitSID, 0, true), (EntityPlayerMP) event.player);
					LogHelper.debug("DEBUG: player tick: update team entity");
					syncTeamList = true;
				}//end init ship UID
				
				//send team list sync packet
				if (syncTeamList)
				{
					capa.sendSyncPacket(0);
				}
			}//end server side, extProps != null
			/** CLIENT SIDE */
			else if (event.player.world.isRemote)
			{
				//cd--
				if (ClientProxy.keyMountActionCD > 0) ClientProxy.keyMountActionCD--;
				if (ClientProxy.keyPlayerSkillCD > 0) ClientProxy.keyPlayerSkillCD--;
				if (ClientProxy.debugCooldown > 0) ClientProxy.debugCooldown--;
				
//				//DEBUG TODO
//				this.keySet = ClientProxy.getGameSetting();
//				LogHelper.debug("AAAA "+
//				(this.keySet.keyBindSprint.isKeyDown() ? 1 : 0)+" "+
//				(this.keySet.keyBindSprint.isKeyDown() ? 1 : 0)+" "+
//				(event.player.isSprinting() ? 1 : 0)+" "+
//				(this.keySet.keyBindSneak.isKeyDown() ? 1 : 0)+" "+
//				(this.keySet.keyBindSneak.isKeyDown() ? 1 : 0)+" "+
//				(event.player.isSneaking() ? 1 : 0));
			}
			
			/** BOTH SIDE */
			//check ring (check for first ring only) every 16 ticks
			if ((event.player.ticksExisted & 15) == 0)
			{
				boolean hasRing = false;
				ItemStack itemRing = null;
				
				//get ring itemstack
				for (int i = 0; i < event.player.inventory.getSizeInventory(); ++i)
				{
					if (event.player.inventory.getStackInSlot(i) != null && 
						event.player.inventory.getStackInSlot(i).getItem() == ModItems.MarriageRing)
					{
						hasRing = true;
						itemRing = event.player.inventory.getStackInSlot(i);
						break;
					}
				}
				
				//check ring with Baubles slots
				if (!hasRing && Loader.isModLoaded(Reference.MOD_ID_Baubles))
				{
					hasRing = InventoryHelper.checkRingInBaubles(event.player);
				}
				
				if (capa != null)
				{
					//原本有ring, 變成沒有, 則取消飛行狀態
					if (capa.hasRing() && !hasRing)
					{
						event.player.capabilities.isFlying = false;
					}
					
					//update hasRing flag
					capa.setHasRing(hasRing);
					
					if (itemRing != null && itemRing.hasTagCompound())
					{
						capa.setRingActive(itemRing.getTagCompound().getBoolean("isActive"));
					}
				}
			}//end every 16 ticks
			
			//check morph
			if (CommonProxy.activeMetamorph) MetamorphHelper.onPlayerTickHelper(event.player);
		}//end player tick phase: START
	}//end onPlayerTick
	
	/**
	 * input event, key按下+放開都會發出一次, 且每個按鍵分開發出, CLIENT SIDE ONLY
	 * isKeyDown = 該按鍵是否按著, isKeyPressed = 這次event是否為該按鍵
	 * 
	 * NOTE: 持續偵測類必須使用isKeyDown, 只需要偵測一次的使用isKeyPressed
	 * NOTE2: 該按鍵若設為滑鼠按鍵, 則只有isKeyDown會有反應!
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyInput(InputEvent event)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		GameSettings keySet = ClientProxy.getGameSetting();
		
		/* OPTool: add or remove unattackable target */
		TargetHelper.handleOPToolKeyInput();
		
		/* change pointer team id */
		EntityHelper.handlePointerKeyInput();
		
		/* ship skill command */
		ShipSkillHandler.handleShipSkillKeys(ShipSkillHandler.getShipSkillHostType(player));
		
		/** for debug usage */
		if (ClientProxy.debugCooldown <= 0 && ConfigHandler.debugMode)
		{
			float ctrl = 0F;
			boolean lalt = false;
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			{
				ctrl = 0.09F;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LMENU))
			{
				lalt = true;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4))
			{
				if (lalt)
				{
					ClientProxy.field4 += 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f4 "+String.format("%.2f", ClientProxy.field4)
					));
				}
				else
				{
					ClientProxy.field1 += 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f1 "+String.format("%.2f", ClientProxy.field1)
					));
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1))
			{
				if (lalt)
				{
					ClientProxy.field4 -= 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f4 "+String.format("%.2f", ClientProxy.field4)
					));
				}
				else
				{
					ClientProxy.field1 -= 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f1 "+String.format("%.2f", ClientProxy.field1)
					));
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5))
			{
				if (lalt)
				{
					ClientProxy.field5 += 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f5 "+String.format("%.2f", ClientProxy.field5)
					));
				}
				else
				{
					ClientProxy.field2 += 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f2 "+String.format("%.2f", ClientProxy.field2)
					));
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2))
			{
				if (lalt)
				{
					ClientProxy.field5 -= 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f5 "+String.format("%.2f", ClientProxy.field5)
					));
				}
				else
				{
					ClientProxy.field2 -= 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f2 "+String.format("%.2f", ClientProxy.field2)
					));
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6))
			{
				if (lalt)
				{
					ClientProxy.field6 += 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f6 "+String.format("%.2f", ClientProxy.field6)
					));
				}
				else
				{
					ClientProxy.field3 += 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f3 "+String.format("%.2f", ClientProxy.field3)
					));
				}
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3))
			{
				if (lalt)
				{
					ClientProxy.field6 -= 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f6 "+String.format("%.2f", ClientProxy.field6)
					));
				}
				else
				{
					ClientProxy.field3 -= 0.01F + ctrl;
					ClientProxy.debugCooldown = 2;
					player.sendMessage(new TextComponentString
					(
						"f3 "+String.format("%.2f", ClientProxy.field3)
					));
				}
			}
		}//end debug keys
	}
	
	/**
	 * PLAYER SAVE TO FILE EVENT: SERVER SIDE ONLY
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerSave(PlayerEvent.SaveToFile event)
	{
		try
		{
			//save morph data to nbt
			if (event.getEntityPlayer() != null &&
				CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
			{
				MetamorphHelper.writeToNBT(event.getEntityPlayer());
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: save morph ship data fail: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * PLAYER LOGOUT EVENT: SERVER SIDE ONLY
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerSave(PlayerLoggedOutEvent event)
	{
		try
		{
			//save morph data to nbt
			if (event.player != null &&
				CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
			{
				MetamorphHelper.writeToNBT(event.player);
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: save morph ship data fail: "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * PLAYER LOGIN EVENT: SERVER SIDE ONLY
	 * sync server config to client when player login
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (event.player instanceof EntityPlayerMP)
		{
			CommonProxy.channelE.sendTo(new S2CEntitySync(null, S2CEntitySync.PID.SyncSystem_Config), (EntityPlayerMP) event.player);
		}
	}
	
	/**
	 * player change dimension, need to update player data
	 * 
	 * NOTE: this event do not trigger when End(1) -> Overworld(0) (is EntityCloneEvent)
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerChangeDim(PlayerChangedDimensionEvent event)
	{
		/**load player extend data
		 */
		LogHelper.debug("DEBUG: player change dim: "+event.player.getName()+" "+event.player.getUniqueID());
		
		if (event.player != null && !event.player.world.isRemote)
		{
			//update player id
			ServerProxy.updatePlayerID(event.player);
			
			//send sync packet
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			capa.sendSyncPacket(0);
			
			if (CommonProxy.activeMetamorph) MetamorphHelper.onPlayerChangeDimHelper(event.player);
		}//end server side
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onRenderHand(RenderSpecificHandEvent event)
	{
		if (event.getHand() == EnumHand.MAIN_HAND && event.getItemStack() != null &&
			event.getItemStack().getItem() == ModItems.PointerItem && event.getItemStack().getMetadata() > 2)
		{
			event.setCanceled(true);
			
			//use custom hand renderer
			RenderHelper.renderItemInFirstPerson((AbstractClientPlayer) ClientProxy.getClientPlayer(), event.getPartialTicks(),
					event.getInterpolatedPitch(), event.getHand(), event.getSwingProgress(),
					event.getItemStack(), event.getEquipProgress());
		}
	}
	
	//change viewer entity when riding mounts, this is CLIENT ONLY event
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onRenderTick(RenderTickEvent event)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		
		//在render前, 依照騎乘類型調整eye height
		if (player != null)
		{
			if (event.phase == TickEvent.Phase.START)
			{
				if (player.getRidingEntity() instanceof BasicEntityMount)
				{
					BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
					
					//將view point切換到ship身上
					if (mount.host != null && !ClientProxy.isViewChanged && !ClientProxy.isViewPlayer)
					{
						ClientProxy.getMineraft().setRenderViewEntity(mount.host);
						ClientProxy.isViewChanged = true;
					}
					
					//將人物視野轉動套用到ship身上
					if (ClientProxy.isViewChanged)
					{
						Entity camera = ClientProxy.getMineraft().getRenderViewEntity();
						
						if (camera instanceof EntityLivingBase)
						{	
							((EntityLivingBase)camera).renderYawOffset = player.renderYawOffset;
							((EntityLivingBase)camera).rotationYawHead = player.rotationYawHead;
							((EntityLivingBase)camera).prevCameraPitch = player.prevCameraPitch;
							((EntityLivingBase)camera).prevRenderYawOffset = player.prevRenderYawOffset;
							((EntityLivingBase)camera).prevRotationYawHead = player.prevRotationYawHead;
							camera.rotationPitch = player.rotationPitch;
							camera.rotationYaw = player.rotationYaw;
							camera.prevRotationPitch = player.prevRotationPitch;
							camera.prevRotationYaw = player.prevRotationYaw;
						}
					}
				}//end riding ship's mounts
			}//end phase START
			else if (event.phase == TickEvent.Phase.END)
			{
				//該render tick結束後必須把視角切回玩家, 以免其他利用view entity的方法出錯
				if (ClientProxy.isViewChanged)
				{
					ClientProxy.getMineraft().setRenderViewEntity(ClientProxy.getClientPlayer());
					ClientProxy.isViewChanged = false;
				}
			}//end phase END
		}
	}
	
	/** Server tick
	 * 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onServerTick(ServerTickEvent event)
	{
		if (event.phase == Phase.END)
		{	//在server tick處理完全部事情後發動
			ServerProxy.updateServerTick();
		}
	}
	
	/**
	 * apply equip enchant in anvil
	 * 
	 * If the event is canceled, vanilla behavior will not run, and the output will be set to null.
     * If the event is not canceled, but the output is not null, it will set the output and not run vanilla behavior.
     * if the output is null, and the event is not canceled, vanilla behavior will execute.
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onAnvilUpdate(AnvilUpdateEvent event)
	{
		ItemStack equip = event.getLeft().copy();
		ItemStack book = event.getRight();
		
		if (equip != null && book != null &&
			equip.getItem() instanceof BasicEquip &&
			book.getItem() == Items.ENCHANTED_BOOK)
		{
			Map<Enchantment, Integer> equipench = EnchantmentHelper.getEnchantments(equip);
			Map<Enchantment, Integer> bookench = EnchantmentHelper.getEnchantments(book);
			int exp = equip.getRepairCost() + book.getRepairCost();
			
			//apply book enchantment to equip
			for (Enchantment ench : bookench.keySet())
            {
                if (ench != null)
                {
                	//檢查equip跟book是否有相同附魔
                    int lv1 = equipench.containsKey(ench) ? ((Integer)equipench.get(ench)).intValue() : 0;
                    int lv2 = ((Integer)bookench.get(ench)).intValue();
                    
                    //相同等級 -> LV+1, 不同等級 -> 取較高級
                    lv2 = lv1 == lv2 ? lv2 + 1 : Math.max(lv2, lv1);

                    //enchant max level limit
                    if (lv2 > ench.getMaxLevel())
                    {
                    	lv2 = ench.getMaxLevel();
                    }

                    //add enchant to ench map
                    equipench.put(ench, Integer.valueOf(lv2));
                    
                    //calc exp level
                    int rare = 0;

                    switch (ench.getRarity())
                    {
                    case COMMON:
                    	rare += 1;
                    break;
                    case UNCOMMON:
                    	rare += 2;
                    break;
                    case RARE:
                    	rare += 4;
                    break;
                    case VERY_RARE:
                    	rare += 8;
                	break;
                    }
                    
                    exp += rare * lv2;
                }
            }//end for every book ench
			
			//setoutput
			event.setCost(30);
			EnchantmentHelper.setEnchantments(equipench, equip);
			event.setOutput(equip);
			
		}//end get equip and book
	}
	
	/**
	 * render something on HUD
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event)
	{
		//render something after hotbar
		if (event.getType() == ElementType.HOTBAR)
		{
			RenderHelper.drawPlayerSkillIcon(event);
		}
	}
	
	/********************************************************
	 *                EVENT FOR INTER-MOD
	 *******************************************************/
	
	/**
	 * tweak nbt data before morphing, SERVER SIDE ONLY
	 */
	@Optional.Method(modid = Reference.MOD_ID_Metamorph)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onMorphPre(MorphEvent.Pre event)
	{
		if (event.player == null) return;
		
		//check prev morph and save ship data
		BasicEntityShip ship = MetamorphHelper.getShipMorphEntity(event.player);
		
		//prev morph is ship
		if (ship != null)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			
			if (capa != null)
			{
				//remove morph entity in capa
				capa.morphEntity = null;
				//save morph ship data
				MetamorphHelper.writeToNBT(event.player);
				//reset HP of player
				event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
			}
		}
		
		//check new morph
		if (event.player != null && !event.player.world.isRemote && event.morph instanceof EntityMorph)
		{
			EntityMorph em = (EntityMorph) event.morph;
			EntityLivingBase target = (em != null) ? em.getEntity() : null;
			
			if (target instanceof IShipMorph)
			{
				LogHelper.debug("DEBUG: Metamorph: try morph to: "+target);
				
				//set IsMorph = true to nbt tag
				NBTTagCompound nbtAll = em.getEntityData();
				
				if (nbtAll == null)
				{
					nbtAll = target.serializeNBT();
					em.setEntityData(nbtAll);
				}
				
				NBTTagCompound nbt = (NBTTagCompound) nbtAll.getTag(CapaShipSavedValues.SHIP_EXTPROP_NAME);
				
				if (nbt == null)
				{
					nbt = new NBTTagCompound();
					nbtAll.setTag(CapaShipSavedValues.SHIP_EXTPROP_NAME, nbt);
				}
				
				NBTTagCompound nbt_flags = (NBTTagCompound) nbt.getTag("ShipFlags");
						
				if (nbt_flags == null)
				{
					nbt_flags = new NBTTagCompound();
					nbt.setTag("ShipFlags", nbt_flags);
				}
				
				nbt_flags.setBoolean("IsMorph", true);
			}
		}
	}
	
	/**
	 * delete or reset some data in nbt tag after morphing, SERVER SIDE ONLY
	 */
	@Optional.Method(modid = Reference.MOD_ID_Metamorph)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onMorphPost(MorphEvent.Post event)
	{
		//cancel morph, reset HP
		if (event.isDemorphing())
		{
			event.player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
			return;
		}
		
		if (event.player != null && !event.player.world.isRemote && event.morph instanceof EntityMorph)
		{
			EntityMorph em = (EntityMorph) event.morph;
			EntityLivingBase target = (em != null) ? em.getEntity() : null;
			
			if (target instanceof BasicEntityShip)
			{
				BasicEntityShip ship = (BasicEntityShip) target;
				LogHelper.debug("DEBUG: Metamorph: morph to ship: "+ship);
				
				//set flags
				ship.setIsMorph(true);
				ship.setMorphHost(event.player);
				ship.setStateFlag(ID.F.NoFuel, false);
				ship.setStateFlag(ID.F.CanFollow, true);
				ship.setStateFlag(ID.F.CanDrop, false);
				
				//change some data in nbt tag here
			}
			else if (target instanceof BasicEntityShipHostile)
			{
				BasicEntityShipHostile ship = (BasicEntityShipHostile) target;
				LogHelper.debug("DEBUG: Metamorph: morph to ship: "+ship);
				
				//set flags
				ship.setIsMorph(true);
				ship.setMorphHost(event.player);
				ship.canDrop = false;
				
				//change some data in nbt tag here
			}
		}
	}
	
	/**
	 * change morph entity's attribute on spawn, SERVER SIDE ONLY
	 */
	@Optional.Method(modid = Reference.MOD_ID_Metamorph)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onSpawnMorphPre(SpawnGhostEvent.Pre event)
	{
		if (event.player != null && !event.player.world.isRemote && event.morph instanceof EntityMorph)
		{
			EntityMorph em = (EntityMorph) event.morph;
			EntityLivingBase target = em.getEntity(event.player.world);
			
			//disable morph ghost
			if (target instanceof BasicEntityShip)
			{
				event.morph = null;
				return;
			}
			//change other ship morph HP to 20
			else if (target instanceof IShipAttackBase)
			{
				//change some data in nbt tag
				NBTTagCompound nbtAll = em.getEntityData();
				if (nbtAll == null) return;
				NBTTagList tagList = nbtAll.getTagList("Attributes", Constants.NBT.TAG_COMPOUND);
				if (tagList == null) return;
				
				for (int i = 0; i < tagList.tagCount(); i++)
		        {
		            NBTTagCompound tags = tagList.getCompoundTagAt(i);
		            String name = tags.getString("Name");
		            
		            if (name == "generic.maxHealth")
		            {
		            	tags.setDouble("Base", 20D);
		            }
		        }
				
				//remove ship inventory
				nbtAll.removeTag("CpInv");
			}
		}//end get entity morph
	}
	
	/**
	 * add skill to morph entity, SERVER SIDE ONLY
	 */
	@Optional.Method(modid = Reference.MOD_ID_Metamorph)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onMorphAction(MorphActionEvent event)
	{
		if (event.player != null && !event.player.world.isRemote &&
			event.morph instanceof EntityMorph)
		{
			EntityMorph em = (EntityMorph) event.morph;
			EntityLivingBase target = (em != null) ? em.getEntity() : null;
			
			if (target instanceof IShipMorph)
			{
				//apply morph action
				MetamorphHelper.onMorphActionHelper(event);
			}
		}
	}

	
}