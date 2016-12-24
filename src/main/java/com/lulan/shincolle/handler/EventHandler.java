package com.lulan.shincolle.handler;

import java.util.List;
import java.util.Random;

import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;
import com.lulan.shincolle.worldgen.ChestLootTable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.FogMode;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
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
	
	private GameSettings keySet;
	
	//keys
	public int rideKeys = 0;
	public int openGUI = 0;
	public int keyCooldown = 0;		//press key cooldown, count down in player tick
	
	//render view change
	private boolean isViewChanged = false;
	private boolean isViewPlayer = false;
	

	//change vanilla mob drop (add grudge), this is SERVER event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onDrop(LivingDropsEvent event)
	{
		Entity host = event.getEntity();
		
		//hostile ship: drop ship egg
    	if (host instanceof BasicEntityShipHostile)
    	{
    		BasicEntityShipHostile entity = (BasicEntityShipHostile) event.getEntity();
    		
    		if (entity.canDrop)
    		{
    			//set drop flag to false
    			entity.canDrop = false;
    			
    			ItemStack bossEgg = entity.getDropEgg();
    			
    			if (bossEgg != null)
    			{
    				BasicEntityItem entityItem1 = new BasicEntityItem(entity.world, entity.posX, entity.posY+0.5D, entity.posZ, bossEgg);
		    		entity.world.spawnEntity(entityItem1);
    			}
    		}	
    	}
    	//friendly ship: drop ship egg
    	else if(host instanceof BasicEntityShip)
		{
	    	BasicEntityShip entity = (BasicEntityShip) host;
	    	
	    	if (entity.getStateFlag(ID.F.CanDrop))
	    	{
	    		//set flag to false to prevent multiple drop from unknown bug
	    		entity.setStateFlag(ID.F.CanDrop, false);
	    		
	    		//save ship attributes to ship spawn egg
	    		ItemStack egg = new ItemStack(ModItems.ShipSpawnEgg, 1, entity.getShipClass()+2);
		    	BasicEntityItem entityItem2 = new BasicEntityItem(entity.world, entity.posX, entity.posY+0.5D, entity.posZ, egg);
		    	NBTTagCompound nbt = new NBTTagCompound();
		    	CapaShipInventory shipInv = entity.getCapaShipInventory();
		    	String ownerUUID = EntityHelper.getPetPlayerUUID(entity);
		    	String name = EntityHelper.getOwnerName(entity);
		    	
				//get ship bonus point
		    	int[] attrs = new int[8];
		    	
		    	if (entity.getLevel() > 1) attrs[0] = entity.getLevel() - 1;	//decrease level 1
		    	else attrs[0] = 1;
		    	
		    	attrs[1] = entity.getBonusPoint(ID.HP);
		    	attrs[2] = entity.getBonusPoint(ID.ATK);
		    	attrs[3] = entity.getBonusPoint(ID.DEF);
		    	attrs[4] = entity.getBonusPoint(ID.SPD);
		    	attrs[5] = entity.getBonusPoint(ID.MOV);
		    	attrs[6] = entity.getBonusPoint(ID.HIT);
		    	attrs[7] = entity.getStateFlagI(ID.F.IsMarried);
		    	
		    	//save ship attributes
		    	nbt.setString("owner", ownerUUID);									//owner UUID
		    	nbt.setString("ownername", name);									//owner name
		    	nbt.setInteger("PlayerID", entity.getStateMinor(ID.M.PlayerUID));	//owner UID
		    	nbt.setTag(CapaInventory.InvName, shipInv.serializeNBT());			//inventory data
		    	nbt.setIntArray("Attrs", attrs);									//bonus point
		    	nbt.setInteger("ShipID", entity.getStateMinor(ID.M.ShipUID));		//ship UID
		    	nbt.setString("customname", entity.getCustomNameTag());				//custom name
		    	
		    	entityItem2.getEntityItem().setTagCompound(nbt);	  				//save nbt to entity item
		    	entity.world.spawnEntity(entityItem2);								//spawn entity item
	    	}
	    }
    	//mob: drop grudge
    	else if (host instanceof IMob || host instanceof EntitySlime || host instanceof EntityGolem)
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
	
	//apply ring effect: boost dig speed in water
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onGetBreakSpeed(PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player = event.getEntityPlayer();
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null && EntityHelper.checkEntityIsInLiquid(player))
		{
			int digBoost = capa.getDigSpeedBoost() + 1;
			if (digBoost > 20) digBoost = 20;
			
			event.setNewSpeed(event.getOriginalSpeed() * digBoost);
		}		
	}
	
	//apply ring effect: vision in the water
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onSetLiquidFog(EntityViewRenderEvent.FogDensity event)
	{
		EntityPlayer player = (event.getEntity() instanceof EntityPlayer ? (EntityPlayer) event.getEntity() : null);
		
		if (player != null &&
			(player.isInsideOfMaterial(Material.LAVA) ||
			 player.isInsideOfMaterial(Material.WATER)))
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null && capa.isRingActive())
			{
				float fogDen = 0.1F - capa.getMarriageNum() * 0.01F;
				if (fogDen < 0.001F) fogDen = 0.001F;
				
				event.setCanceled(true);	//取消原本的fog render
	            event.setDensity(fogDen);	//重設fog濃度
	            GlStateManager.setFog(FogMode.EXP);
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
	public void onPlayerClone(PlayerEvent.Clone event)
	{
        //restore data when player dead or back from End
        if (event.getEntityPlayer() != null && event.getOriginal() != null)
        {
        	LogHelper.info("DEBUG : get player clone event: "+event.getOriginal());

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
        		
        		LogHelper.info("DEBUG : player clone: restore player data: eid: "+event.getEntityPlayer().getEntityId()+" pid: "+capa2.getPlayerUID());
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
//	            	LogHelper.info("DEBUG : spawn ship mob at "+event.x+" "+event.y+" "+event.z+" rate "+ConfigHandler.scaleMobSmall[6]);
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
	public void onWorldLoad(WorldEvent.Load event)
	{
		if (!ServerProxy.initServerFile && event.getWorld() != null)
		{
			ServerProxy.initServerProxy(event.getWorld());
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
		if(target != null && attackerSource != null && !target.world.isRemote)
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
			
		}//end server side
	}
	
	/** on entity entering chunk
	 *  apply chunk loader
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEnteringChunk(EntityEvent.EnteringChunk event)
	{
		//get ship
		if (event.getEntity() instanceof BasicEntityShip)
		{
			((BasicEntityShip) event.getEntity()).updateChunkLoader();
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
				if (event.player.ticksExisted > 0 && event.player.ticksExisted % 32 == 0)
				{
					//DEBUG TODO
//					Item.REGISTRY.forEach((k) ->
//					{
//						Item i2 = Item.getByNameOrId(k.getRegistryName().toString());
//					});
					
					/**
					 * generate player UID
					 */
					if (capa.getPlayerUID() < 100)
					{
						//get new UID
			        	ServerProxy.updatePlayerID(event.player);
			        	
				        //check player UID again, stop ticking if fail
				        if (capa.getPlayerUID() < 100)
				        {
				        	//update uid fail, return
				        	LogHelper.debug("DEBUG : player tick: generate new player UID fail, stop player ticking.");
				        	return;
						}
				        else
				        {
				        	//sync extProps to client
					        capa.sendSyncPacket(0);
				        	//save extProps to ServerProxy
							LogHelper.info("DEBUG : player tick: generate new player UID, save player extProps in ServerProxy");
				        }
					}
					
					//team list fast update
					if (event.player.ticksExisted == 64)
					{
						updateTeamList(event.player, capa);
						syncTeamList = true;
					}
					
					//every 128 ticks
					if (event.player.ticksExisted % 128 == 0)
					{
						//spawn mob ship
						spawnMobShip(event.player, capa);
						
						/** check player entity id and update player data to ServerProxy cache */
						//get server cache
						int peid = EntityHelper.getPlayerEID(capa.getPlayerUID());
						
						//if entity id is different, update new eid
						if (peid != event.player.getEntityId())
						{
							LogHelper.info("DEBUG : player tick: player entity id changed, update new eid: "+peid+" to "+event.player.getEntityId());
							ServerProxy.updatePlayerID(event.player);
						}
						
						//every 256 ticks
						if (event.player.ticksExisted % 256 == 0)
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
				if (!capa.initSID && event.player.ticksExisted % 16 == 0) 
				{
					//update ship temaList
					capa.updateShipEntityBySID();
					
					//sync flag
					CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.FlagInitSID, 0, true), (EntityPlayerMP) event.player);
					LogHelper.info("DEBUG : player tick: update team entity");
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
				if (this.keyCooldown > 0) this.keyCooldown--;	//key cd--
				
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
			//check ring (check for first ring only) every 32 ticks
			if (event.player.ticksExisted % 32 == 0)
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
			}//end player per 32 ticks
		}//end player tick phase: START
	}//end onPlayerTick
	
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
	 *  1: Spawn prob every 256 ticks
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
					int loop = 30 + groups * 10;
					
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
						LogHelper.info("DEBUG : spawn mob ship: group: "+groups+
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
				            	String shipname = ShipCalc.getRandomMobToSpawnName(0);
				            	EntityLiving entityToSpawn = (EntityLiving) EntityList.createEntityByName(shipname, w);
				            	
				            	//spawn mob
				            	if (entityToSpawn != null)
				            	{
									entityToSpawn.setPosition(spawnX + rng.nextDouble(), spawnY + 0.5D, spawnZ + rng.nextDouble());
									w.spawnEntity(entityToSpawn);
				            	}
				            	LogHelper.info("DEBUG : spawn mob ship: #total: "+(entNum++)+" group: "+groups+" #ship: "+i+" "+spawnY+" "+shipname);
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
			LogHelper.info("DEBUG : spawn boss: roll spawn "+rolli);
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
					
					LogHelper.info("DEBUG : spawn boss: check block: "+blockY.getBlock().getLocalizedName()+
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
						LogHelper.info("DEBUG : spawn boss: check existed boss: "+bossNum+" all mob: "+listBoss.size());
						
						//若範圍內不到2隻boss, 則可以再生成新boss
			            if (bossNum < 2)
			            {
			            	//roll生成mob
			            	int maxShipNum = ConfigHandler.spawnBossNum + ConfigHandler.spawnMobNum;
			            	String[] spawnName = new String[maxShipNum];
			            	
			            	//roll boss ship
			            	for (i = 0; i < ConfigHandler.spawnBossNum; i++)
			            	{
			            		spawnName[i] = ShipCalc.getRandomMobToSpawnName(rng.nextInt(2) + 2);
			            	}
			            	
			            	//roll small ship
			            	for (i = ConfigHandler.spawnBossNum; i < maxShipNum; i++)
			            	{
			            		spawnName[i] = ShipCalc.getRandomMobToSpawnName(rng.nextInt(2));
			            	}
			            	
			            	//set mob position and spawn to the world
			            	for (String name : spawnName)
			            	{
			            		//get mob entity
				            	EntityLiving entityToSpawn = (EntityLiving) EntityList.createEntityByName(name, w);
				            	
				            	//spawn mob
				            	if (entityToSpawn != null)
				            	{
				            		entityToSpawn.setPosition(spawnX + rng.nextInt(3), spawnY + 0.5D, spawnZ + rng.nextInt(3));
				            		w.spawnEntity(entityToSpawn);
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
			            	
			            	LogHelper.info("DEBUG : spawn fleet "+spawnX+" "+spawnY+" "+spawnZ);
							break;
			            }//end if nearby boss < 2	
					}//end get water block
				}//end roll 20 times
			}//end roll spawn boss
		}//end boss cooldown <= 0
	}
	
	/**get input, 按下+放開都會發出一次, 且每個按鍵分開發出, CLIENT side only event
	 * getIsKeyPressed = 該按鍵是否按著, isKeyDown = 這次event是否為該按鍵
	 * 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyInput(InputEvent event)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		this.keySet = ClientProxy.getGameSetting();
		
		//pointer item control
		if (event instanceof KeyInputEvent)
		{
			//get pointer item
			ItemStack pointer = EntityHelper.getPointerInUse(player);
			
			if (pointer != null)
			{
				int meta = pointer.getMetadata();
				
				//ctrl + xx
				int getKey = -1;
				int orgCurrentItem = player.inventory.currentItem;
				
				//若按住ctrl (sprint key)
				if (keySet.keyBindSprint.isKeyDown())
				{
					//若按住數字按鍵 1~9, 則切換隊伍, 但是避免數字按鍵將hotbar位置改變 (固定current item)
					for (int i = 0; i < keySet.keyBindsHotbar.length; i++)
					{
						if (keySet.keyBindsHotbar[i].isKeyDown())
						{
							getKey = i;
							
							//儲存快捷位置到權杖, 使權杖能將快捷列回復到權杖上 (CLIENT ONLY)
							if (!pointer.hasTagCompound())
							{
								pointer.setTagCompound(new NBTTagCompound());
							}
							
							//set hotbar changed flag
							pointer.getTagCompound().setBoolean("chgHB", true);
							pointer.getTagCompound().setInteger("orgHB", orgCurrentItem);
							
							break;
						}
					}
					
					LogHelper.debug("DEBUG : key input: pointer set team: "+getKey+" currItem: "+orgCurrentItem);
					//send key input packet
					if (getKey >= 0)
					{
						//change team id
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, getKey, orgCurrentItem));
					}
				}
				//change pointer mode to caress head mode (meta + 3)
				else if (keySet.keyBindPlayerList.isKeyDown())
				{
					//switch caress head mode
					switch (meta)
					{
					case 1:
						meta = 4;
						break;
					case 2:
						meta = 5;
						break;
					case 3:
						meta = 0;
						break;
					case 4:
						meta = 1;
						break;
					case 5:
						meta = 2;
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
		}

		//riding control
		if (player.getRidingEntity() instanceof BasicEntityMount)
		{
			int newKeys = 0;
			BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
			
			//change renderer viewer, accept KEYBOARD or MOUSE input
			if (keySet.keyBindPickBlock.isKeyDown() && this.keyCooldown <= 0)
			{
				LogHelper.info("DEBUG : key event: player view "+this.isViewPlayer);
				this.keyCooldown = 5;
				this.isViewPlayer = !this.isViewPlayer;
			}
			
			//move and open GUI is KEYBOARD ONLY
			if (event instanceof KeyInputEvent)
			{
				//forward
				if (keySet.keyBindForward.isKeyDown())
				{
					LogHelper.info("DEBUG : key event: press W");
					newKeys = newKeys | 1;
				}
				
				//back
				if (keySet.keyBindBack.isKeyDown())
				{
					LogHelper.info("DEBUG : key event: press S");
					newKeys = newKeys | 2;
				}
				
				//left
				if (keySet.keyBindLeft.isKeyDown())
				{
					LogHelper.info("DEBUG : key event: press A");
					newKeys = newKeys | 4;
				}
				
				//right
				if (keySet.keyBindRight.isKeyDown())
				{
					LogHelper.info("DEBUG : key event: press D");
					newKeys = newKeys | 8;
				}
				
				//jump
				if (keySet.keyBindJump.isKeyDown())
				{
					LogHelper.info("DEBUG : key event: jump");
					newKeys = newKeys | 16;
				}
				
				//server跟client同時設定, 移動顯示才會順暢, 只靠server設定移動會不連續
				if (mount != null)
				{
					//set key for packet
					this.rideKeys = newKeys;
					
					//set client key
					mount.keyPressed = newKeys;
	
					//open ship GUI
					if (keySet.keyBindInventory.isKeyDown())
					{
						CommonProxy.channelG.sendToServer(new C2SInputPackets(1, this.openGUI));
					}
					//set move key
					else
					{
						CommonProxy.channelG.sendToServer(new C2SInputPackets(0, this.rideKeys));
					}
				}
			}//is key event
		}//end is riding
	}//end key event

	/**player login, called after extProps loaded, SERVER ONLY event
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		/**load player extend data
		 */
		LogHelper.info("DEBUG : player login: "+event.player.getDisplayNameString()+" "+event.player.world.isRemote+" "+event.player.getUniqueID());
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
		
		if (capa != null && capa.needInit)
		{
			capa.init(event.player);
		}//end player extProps not null
	}
	
//	//player loggout, NO USE in singleplayer
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onPlayerLogout(PlayerLoggedOutEvent event)
//	{
//	}
	
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
		LogHelper.info("DEBUG : player change dim: "+event.player.getName()+" "+event.player.getUniqueID());
		
		if (event.player != null && !event.player.world.isRemote)
		{
			//update player id
			ServerProxy.updatePlayerID(event.player);
			
			//send sync packet
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			capa.sendSyncPacket(0);
		}//end server side
	}
	
	//change viewer entity when riding mounts, this is CLIENT ONLY event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onRenderTick(TickEvent.RenderTickEvent event)
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
				}//end riding SEAT
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
    public void onServerTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase == Phase.END)
		{	//在server tick處理完全部事情後發動
			ServerProxy.updateServerTick();
		}
	}

	
}
