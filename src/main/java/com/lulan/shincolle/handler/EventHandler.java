package com.lulan.shincolle.handler;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;
import com.lulan.shincolle.worldgen.ChestLootTable;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.FogMode;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
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
	
	private static GameSettings keySet;		//CLIENT SIDE ONLY, keyset
	
	//keys
	public static int rideKeys = 0;			//CLIENT SIDE ONLY
	public static int openGUI = 0;			//CLIENT SIDE ONLY
	public static int keyCooldown = 0;		//CLIENT SIDE ONLY, press key cooldown
	
	//render view change
	public static boolean isViewChanged = false;	//CLIENT SIDE ONLY
	public static boolean isViewPlayer = false;	//CLIENT SIDE ONLY
	
	//for debug usage
	public static int debugCooldown = 0;	//CLIENT SIDE ONLY
	public static float field1 = 0F;		//CLIENT SIDE ONLY
	public static float field2 = 0F;		//CLIENT SIDE ONLY
	public static float field3 = 0F;		//CLIENT SIDE ONLY
	

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
			Entity ent = event.getSource().getSourceOfDamage();
			
	    	//由本體擊殺
	    	if (ent instanceof BasicEntityShip)
	    	{
	    		BasicEntityShip ship = (BasicEntityShip) ent;
	    		ship.addKills();
	    		ship.setStateMinor(ID.M.Morale, ship.getStateMinor(ID.M.Morale) + 2);
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
		Entity attacker = event.getSource().getEntity();				//attacker or summons
		Entity attackerSource = event.getSource().getSourceOfDamage();	//attacker or summoner
		
		//server side only
		if(target != null && !target.world.isRemote)
		{
			//prevnt fall damage to player while riding
			if (target instanceof EntityPlayer)
			{
				if (target.getRidingEntity() instanceof BasicEntityMount &&
					(event.getSource() == DamageSource.fall ||
					 event.getSource() == DamageSource.inWall))
				{
					event.setCanceled(true);
					return;
				}
				
				//immune to fire
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) target);
				if (capa.hasRing() && capa.isRingActive() && ConfigHandler.ringAbility[4] >= 0 &&
					capa.getMarriageNum() >= ConfigHandler.ringAbility[4])
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
				double dist = target.getDistanceSqToEntity(attackerSource);
				
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
						updateTeamList(event.player, capa);
						syncTeamList = true;
					}
					
					//every 128 ticks
					if ((event.player.ticksExisted & 127) == 0)
					{
						//spawn mob ship
						spawnMobShip(event.player, capa);
						
						//sync unit names
						capa.sendSyncPacket(8);
						
						//every 256 ticks
						if ((event.player.ticksExisted & 255) == 0)
						{
							//update team list
							updateTeamList(event.player, capa);
							syncTeamList = true;
						}//end every 256 ticks
					}//end every 128 ticks
				}//end every 32 ticks
				
				//spawn boss ticking
				spawnBossShip(event.player, capa);
				
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
				if (this.keyCooldown > 0) this.keyCooldown--;
				if (this.debugCooldown > 0) this.debugCooldown--;
				
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
					hasRing = checkRingInBaubles(event.player);
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
		}//end player tick phase: START
	}//end onPlayerTick
	
	@Optional.Method(modid = Reference.MOD_ID_Baubles)
	private static boolean checkRingInBaubles(EntityPlayer player)
	{
		IBaublesItemHandler bb = (IBaublesItemHandler) player.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
	
		if (bb != null)
		{
			for (int i = 0; i < bb.getSlots(); i++)
			{
				ItemStack stack = bb.getStackInSlot(i);
				if (stack != null && stack.getItem() == ModItems.MarriageRing) return true;
			}
		}
		
		return false;
	}
	
	/** update team list of pointer item */
	private static void updateTeamList(EntityPlayer player, CapaTeitoku capa)
	{
		/** update ships in pointer team list */
		//check entity is alive
		BasicEntityShip getent = null;
		
		for (int i = 0; i < 6; i++)
		{
			//get ship by UID
			getent = EntityHelper.getShipByUID(capa.getSIDCurrentTeam(i));

			//get ship
			if (getent != null)
			{
				if (TeamHelper.checkSameOwner(getent, player))
				{
					//update ship entity
					capa.addShipEntityToCurrentTeam(i, getent);
				}
				else
				{
					//owner changed, remove ship
					capa.addShipEntityToCurrentTeam(i, null);
				}
			}
			//ship lost
			else
			{
				//clear slot if no ship UID (ship UID invalid)
				if (capa.getSIDCurrentTeam(i) <= 0)
				{
					capa.addShipEntityToCurrentTeam(i, null);
				}
			}	
		}
	}
	
	/** spawn mob ticking
	 * 
	 *  ConfigHandler.mobSpawn[]
	 *  0: Max number in the world
	 *  1: Spawn prob every X ticks
	 *  2: #groups each spawn
	 *  3: #min each group
	 *  4: #max each group
	 */
	private static void spawnMobShip(EntityPlayer player, CapaTeitoku capa)
	{
		//null check
		if (player == null || capa == null || player.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}
		
		boolean canSpawn = false;
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		Biome biome = player.world.getBiomeForCoordsBody(new BlockPos(blockX, 0, blockZ));
		World w = player.world;
		Random rng = player.getRNG();
		
		//check ring
		if (ConfigHandler.checkRing)
		{
			if (capa.hasRing()) canSpawn = true;
		}
		else
		{
			canSpawn = true;
		}
		
		//check biome
		if (canSpawn)
		{
			if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER) || 
				BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.BEACH)) {}
			else
			{
				canSpawn = false;
			}
		}

		//apply spawn
		if (canSpawn)
		{
			int entNum = EntityHelper.getEntityNumber(0, w);
			
			//check ship number in the world
			if (entNum <= ConfigHandler.mobSpawn[0])
			{
				//roll spawn
				if (rng.nextInt(100) <= ConfigHandler.mobSpawn[1])
				{
					//get spawn position
					int groups = ConfigHandler.mobSpawn[2];
					int loop = 30 + groups * 30;
					
					while (groups > 0 && loop > 0)
					{
						int offX = rng.nextInt(30) + 20;
						int offZ = rng.nextInt(30) + 20;
						
						switch (rng.nextInt(4))
						{
						case 1:
							spawnX = blockX - offX;
							spawnZ = blockZ - offZ;
							break;
						case 2:
							spawnX = blockX + offX;
							spawnZ = blockZ - offZ;
							break;
						case 3:
							spawnX = blockX - offX;
							spawnZ = blockZ + offZ;
							break;
						default:
							spawnX = blockX + offX;
							spawnZ = blockZ + offZ;
							break;
						}

						IBlockState blockY = w.getBlockState(new BlockPos(spawnX, w.provider.getAverageGroundLevel() - 2, spawnZ));
						LogHelper.debug("DEBUG: spawn mob ship: group: "+groups+
								" get block: "+blockY.getBlock().getLocalizedName()+" "+spawnX+
								" "+(w.provider.getAverageGroundLevel()-2)+" "+spawnZ);
						
						//spawn on water
						if (blockY.getMaterial() == Material.WATER)
						{
							groups--;
							
							//get top water block
							spawnY = BlockHelper.getToppestWaterHeight(w, spawnX, w.provider.getAverageGroundLevel() - 3, spawnZ);
							
							//get spawn number
							int shipNum = Math.max(1, ConfigHandler.mobSpawn[3]);
							int ranMax = ConfigHandler.mobSpawn[4] - ConfigHandler.mobSpawn[3];
							
							if (ranMax > 0)
							{
								shipNum = ConfigHandler.mobSpawn[3] + rng.nextInt(ranMax + 1);
							}
							
							//spawn mob
							for (int i = 0; i < shipNum; i++)
							{
								//get random mob
				            	Entity mobToSpawn = EntityList.createEntityByName(ShipCalc.getRandomMobToSpawnName(), w);
				            	
				            	//spawn mob
				            	if (mobToSpawn instanceof BasicEntityShipHostile)
				            	{
				            		((BasicEntityShipHostile) mobToSpawn).initAttrs(rng.nextInt(10) > 7 ? 1 : 0);
				            		mobToSpawn.setPosition(spawnX + rng.nextDouble(), spawnY + 0.5D, spawnZ + rng.nextDouble());
									w.spawnEntity(mobToSpawn);
				            	}
							}
						}//end get water block
						
						loop--;
					}//end spawn while
				}//end roll spawn
			}//end check mob number
		}//end can spawn
			
	}
	
	/** spawn boss ticking */
	private static void spawnBossShip(EntityPlayer player, CapaTeitoku capa)
	{
		//null check
		if (player == null || capa == null || player.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}
				
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		Biome biome = player.world.getBiomeForCoordsBody(new BlockPos(blockX, 0, blockZ));
		
		//boss cooldown--
		if ((BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER) || 
			 BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.BEACH)) && capa.hasRing())
		{
			capa.setBossCooldown(capa.getBossCooldown() - 1);
		}
		
		//cooldown = 0, roll spawn
		if (capa.getBossCooldown() <= 0)
		{
			World w = player.world;
			Random rng = player.getRNG();
			
			capa.setBossCooldown(ConfigHandler.bossCooldown);
			
			int rolli = rng.nextInt(4);
			LogHelper.debug("DEBUG: spawn boss: roll spawn "+rolli);
			if (rolli == 0)
			{
				//尋找20次地點, 找到一個可生成地點即生成後跳出loop
				int i;
				for (i = 0; i < 20; i++)
				{
					int offX = rng.nextInt(32) + 32;
					int offZ = rng.nextInt(32) + 32;
					
					switch (rng.nextInt(4))
					{
					case 1:
						spawnX = blockX - offX;
						spawnZ = blockZ - offZ;
						break;
					case 2:
						spawnX = blockX + offX;
						spawnZ = blockZ - offZ;
						break;
					case 3:
						spawnX = blockX - offX;
						spawnZ = blockZ + offZ;
						break;
					default:
						spawnX = blockX + offX;
						spawnZ = blockZ + offZ;
						break;		
					}
					
					IBlockState blockY = w.getBlockState(new BlockPos(spawnX, w.provider.getAverageGroundLevel() - 2, spawnZ));
					
					LogHelper.debug("DEBUG: spawn boss: check block: "+blockY.getBlock().getLocalizedName()+
							" "+spawnX+" "+(w.provider.getAverageGroundLevel() - 2)+" "+spawnZ);
					//生成在水面
					if (blockY.getMaterial() == Material.WATER)
					{
						//get top water block
						spawnY = BlockHelper.getToppestWaterHeight(w, spawnX, 63, spawnZ);
						
						//check 64x64 range
						AxisAlignedBB aabb = new AxisAlignedBB(spawnX-48D, spawnY-48D, spawnZ-48D, spawnX+48D, spawnY+48D, spawnZ+48D);
						List<BasicEntityShipHostile> listBoss = w.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);
						int bossNum = 0;
						
						//check boss in list
						for(BasicEntityShipHostile mob : listBoss)
						{
							if (!mob.isNonBoss())
							{
								bossNum++;
							}
						}
						LogHelper.debug("DEBUG: spawn boss: check existed boss: "+bossNum+" all mob: "+listBoss.size());
						
						//若範圍內不到2隻boss, 則可以再生成新boss
			            if (bossNum < 2)
			            {
			            	//roll生成mob
			            	Entity mobToSpawn;
			            	
			            	//roll boss ship
			            	int j;
			            	for (j = 0; j < ConfigHandler.spawnBossNum; j++)
			            	{
			            		mobToSpawn = EntityList.createEntityByName(ShipCalc.getRandomMobToSpawnName(), w);
			            		
				            	//spawn mob
				            	if (mobToSpawn instanceof BasicEntityShipHostile)
				            	{
				            		((BasicEntityShipHostile) mobToSpawn).initAttrs(rng.nextInt(100) > 65 ? 3 : 2);
				            		mobToSpawn.setPosition(spawnX + rng.nextInt(3), spawnY + 0.5D, spawnZ + rng.nextInt(3));
				            		w.spawnEntity(mobToSpawn);
				            	}
			            	}
			            	
			            	//roll small ship
			            	for (j = 0; j < ConfigHandler.spawnMobNum; j++)
			            	{
			            		mobToSpawn = EntityList.createEntityByName(ShipCalc.getRandomMobToSpawnName(), w);
			            		
				            	//spawn mob
				            	if (mobToSpawn instanceof BasicEntityShipHostile)
				            	{
				            		((BasicEntityShipHostile) mobToSpawn).initAttrs(rng.nextInt(2));
				            		mobToSpawn.setPosition(spawnX + rng.nextInt(3), spawnY + 0.5D, spawnZ + rng.nextInt(3));
				            		w.spawnEntity(mobToSpawn);
				            	}
			            	}
			            	
			            	//發出spawn msg
			            	TextComponentTranslation spawnText = null;
			            	if (rng.nextInt(2) == 0)
			            	{
			            		spawnText = new TextComponentTranslation("chat.shincolle:bossspawn1");
			            	}
			            	else
			            	{
			            		spawnText = new TextComponentTranslation("chat.shincolle:bossspawn2");
			            	}
			            	
			            	ServerProxy.getServer().sendMessage(new TextComponentString(""+TextFormatting.YELLOW+spawnText+
			            			TextFormatting.AQUA+" "+spawnX+" "+spawnY+" "+spawnZ));
			            	
			            	LogHelper.debug("DEBUG: spawn fleet "+spawnX+" "+spawnY+" "+spawnZ);
							break;
			            }//end if nearby boss < 2	
					}//end get water block
				}//end roll 20 times
			}//end roll spawn boss
		}//end boss cooldown <= 0
	}
	
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
		this.keySet = ClientProxy.getGameSetting();
		
		//for debug usage
		if (this.debugCooldown <= 0 && ConfigHandler.debugMode)
		{
			float ctrl = 0F;
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
			{
				ctrl = 0.09F;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4))
			{
				this.field1 += 0.01F + ctrl;
				this.debugCooldown = 2;
				player.sendMessage(new TextComponentString
				(
					"f1 "+String.format("%.2f", this.field1)
				));
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1))
			{
				this.field1 -= 0.01F + ctrl;
				this.debugCooldown = 2;
				player.sendMessage(new TextComponentString
				(
					"f1 "+String.format("%.2f", this.field1)
				));
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5))
			{
				this.field2 += 0.01F + ctrl;
				this.debugCooldown = 2;
				player.sendMessage(new TextComponentString
				(
					"f2 "+String.format("%.2f", this.field2)
				));
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2))
			{
				this.field2 -= 0.01F + ctrl;
				this.debugCooldown = 2;
				player.sendMessage(new TextComponentString
				(
					"f2 "+String.format("%.2f", this.field2)
				));
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6))
			{
				this.field3 += 0.01F + ctrl;
				this.debugCooldown = 2;
				player.sendMessage(new TextComponentString
				(
					"f3 "+String.format("%.2f", this.field3)
				));
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3))
			{
				this.field3 -= 0.01F + ctrl;
				this.debugCooldown = 2;
				player.sendMessage(new TextComponentString
				(
					"f3 "+String.format("%.2f", this.field3)
				));
			}
		}
		
		//pointer item control
		ItemStack pointer = EntityHelper.getPointerInUse(player);
		
		if (pointer != null)
		{
			int meta = pointer.getMetadata();
			int getKey = -1;
			int orgCurrentItem = player.inventory.currentItem;
			
			//若按住ctrl (sprint key)
			if (keySet.keyBindSprint.isKeyDown())	//注意持續偵測類按鍵必須使用isKeyDown
			{
				//若按住hotbar 1~9, 則切換隊伍, 但是避免數字按鍵將hotbar位置改變 (固定current item)
				for (int i = 0; i < keySet.keyBindsHotbar.length; i++)
				{
					if (keySet.keyBindsHotbar[i].isPressed())
					{
						getKey = i;
						
						//儲存快捷位置到權杖, 使權杖能將快捷列回復到權杖上 (CLIENT SIDE)
						if (!pointer.hasTagCompound()) pointer.setTagCompound(new NBTTagCompound());
						pointer.getTagCompound().setBoolean("chgHB", true);
						pointer.getTagCompound().setInteger("orgHB", orgCurrentItem);
						
						break;
					}
				}
				
				LogHelper.debug("DEBUG: key input: pointer set team: "+getKey+" currItem: "+orgCurrentItem);
				//send key input packet
				if (getKey >= 0)
				{
					//change team id
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, getKey, orgCurrentItem));
				}
			}
			//change pointer mode to caress head mode (meta + 3)
			//current item must be PointerItem (NO OFFHAND!)
			else if (player.inventory.getCurrentItem() != null &&
					 player.inventory.getCurrentItem().getItem() == ModItems.PointerItem &&
					 keySet.keyBindPlayerList.isPressed())
			{
				//switch caress head mode
				switch (meta)
				{
				case 1:
				case 2:
					meta += 3;
					break;
				case 3:
				case 4:
				case 5:
					meta -= 3;
					break;
				default:
					meta = 3;
					break;
				}
				
				player.inventory.getCurrentItem().setItemDamage(meta);
				
				//send sync packet to server
				CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SyncPlayerItem, meta));
			}
		}
		
		//riding control
		if (this.keyCooldown <= 0 && player.getRidingEntity() instanceof BasicEntityMount)
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
				LogHelper.debug("DEBUG: key event: player view "+this.isViewPlayer);
				this.keyCooldown = 8;
				this.isViewPlayer = !this.isViewPlayer;
				return;
			}
			
			//持續偵測所有移動按鍵是否按住
			BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
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
				this.rideKeys = newKeys;
				this.keyCooldown = 2;
				
				//server跟client必須同時設定移動狀態, 移動顯示才會順暢, 只靠server設定移動會不連續
				mount.keyPressed = newKeys;		//set client moving key
				mount.keyTick = 10;				//continue moving for 10 ticks
				
				//send mounts move key packet
				LogHelper.debug("DEBUG: key event: mounts move key: "+Integer.toBinaryString(newKeys));
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.MountMove, this.rideKeys));
			}
		}//end is riding
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
			renderItemInFirstPerson((AbstractClientPlayer) ClientProxy.getClientPlayer(), event.getPartialTicks(),
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
					if (mount.host != null && !this.isViewChanged && !this.isViewPlayer)
					{
						ClientProxy.getMineraft().setRenderViewEntity(mount.host);
						this.isViewChanged = true;
					}
					
					//將人物視野轉動套用到ship身上
					if (this.isViewChanged)
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
				if (this.isViewChanged)
				{
					ClientProxy.getMineraft().setRenderViewEntity(ClientProxy.getClientPlayer());
					this.isViewChanged = false;
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
	
	//custom main hand renderer
	@SideOnly(Side.CLIENT)
    public static void renderItemInFirstPerson(AbstractClientPlayer player, float ptick, float pitch, EnumHand hand, float swing, @Nullable ItemStack stack, float equip)
    {
        EnumHandSide enumhandside = player.getPrimaryHand();
        
        if (!player.isInvisible())
        {
            boolean flag = enumhandside != EnumHandSide.LEFT;
            float f = flag ? 1.0F : -1.0F;
            
            //get player skin
            ClientProxy.getMineraft().getTextureManager().bindTexture(player.getLocationSkin());
            
            //draw hand
            GlStateManager.pushMatrix();
            GlStateManager.translate(f * 0.64000005F, -0.6F, -0.71999997F);
            GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
            GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
            
            if (ClientProxy.getGameSetting().keyBindUseItem.isKeyDown())
            {
            	switch (stack.getMetadata())
            	{
            	case 3:
                	GlStateManager.translate(1.3F, 4F, 0.0F);
                	GlStateManager.scale(3F, 3F, 3F);
                	GlStateManager.rotate(MathHelper.cos((player.ticksExisted + ptick) * 0.125F) * -20F - 60F, 0F, 0F, 1F);
        		break;
	            case 4:
	            	GlStateManager.rotate(70F, 0F, 1F, 0F);
	            	GlStateManager.rotate(-20F, 0F, 0F, 1F);
	            	GlStateManager.translate(-2F, 16F, 10F);
					GlStateManager.scale(12F, 12F, 12F);
					GlStateManager.rotate(MathHelper.cos((player.ticksExisted + ptick) * 0.1F) * -15F + 20F, 1F, 0F, 0F);
        		break;
        		default:
        			GlStateManager.translate(13.5F, 12.5F, 2.5F);
    				GlStateManager.scale(9F, 9F, 9F);
    				GlStateManager.rotate(MathHelper.cos((player.ticksExisted + ptick) * 0.2F) * -15F - 20F, 1F, 1F, 0F);
    			break;
            	}
            }
            
            RenderPlayer renderplayer = (RenderPlayer)ClientProxy.getMineraft().getRenderManager().getEntityRenderObject(player);
            
            GlStateManager.disableCull();

            if (flag)
            {
                renderplayer.renderRightArm(player);
            }
            else
            {
                renderplayer.renderLeftArm(player);
            }

            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }

	
}