package com.lulan.shincolle.handler;

import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TargetHelper;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.FogMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**for EVENT_BUS event only <br>
 * (not for FML event)
 * 
 * priority=EventPriority.NORMAL 事件優先權, 同事件會由高優先權的mod先跑
 * receiveCanceled=true 使事件被其他mod取消, 本mod依然可以接收
 */
public class EVENT_BUS_EventHandler
{

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
    				BasicEntityItem entityItem1 = new BasicEntityItem(entity.worldObj, entity.posX, entity.posY+0.5D, entity.posZ, bossEgg);
		    		entity.worldObj.spawnEntityInWorld(entityItem1);
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
		    	BasicEntityItem entityItem2 = new BasicEntityItem(entity.worldObj, entity.posX, entity.posY+0.5D, entity.posZ, egg);
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
		    	entity.worldObj.spawnEntityInWorld(entityItem2);				//spawn entity item
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
		        event.getDrops().add(new EntityItem(host.worldObj, host.posX, host.posY, host.posZ, drop));
    		}
    		//值不到1, 機率掉落1個
    		else
    		{
    			if (host.worldObj.rand.nextFloat() <= ConfigHandler.dropGrudge)
    			{
    				ItemStack drop = new ItemStack(ModItems.Grudge, 1);
			        event.getDrops().add(new EntityItem(host.worldObj, host.posX, host.posY, host.posZ, drop));
    			}
    		}
    		
    		//剩餘不到1的值, 改為機率掉落 (ex: 0.6 = 60%掉一顆)
    		if (host.worldObj.rand.nextFloat() < (ConfigHandler.dropGrudge - (float)numGrudge))
    		{
				ItemStack drop = new ItemStack(ModItems.Grudge, 1);
		        event.getDrops().add(new EntityItem(host.worldObj, host.posX, host.posY, host.posZ, drop));
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
		Entity ent = event.getSource().getSourceOfDamage();
		
		//add kills number
	    if (ent != null)
	    {
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
	    }
	    
	    EntityLivingBase host = event.getEntityLiving();

	    //show dead emote
	    if (host instanceof BasicEntityShip)
	    {
	    	if (!host.worldObj.isRemote)
	    	{
		    	((BasicEntityShip) host).applyParticleEmotion(8);
				EntityHelper.applyShipEmotesAOE(host.worldObj, host.posX, host.posY, host.posZ, 16D, 6);
	    	}
	    }
	    //show dead emote
	    else if (host instanceof BasicEntityShipHostile)
	    {
	    	if (!host.worldObj.isRemote)
	    	{
		    	((BasicEntityShipHostile) host).applyParticleEmotion(8);
				EntityHelper.applyShipEmotesAOEHostile(host.worldObj, host.posX, host.posY, host.posZ, 48D, 6);
	    	}
	    }
	}
	
	/**
	 * player clone event: wasDead: true: player respawn, false: back from TheEnd 
	 *  if cloned, save/load the player data
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEntityClone(PlayerEvent.Clone event)
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
		if(target != null && attackerSource != null && !target.worldObj.isRemote)
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

	
}
