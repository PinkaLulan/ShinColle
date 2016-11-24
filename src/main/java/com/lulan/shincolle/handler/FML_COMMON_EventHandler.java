package com.lulan.shincolle.handler;

import java.util.List;
import java.util.Random;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FML_COMMON_EventHandler
{
	
	private GameSettings keySet;
	
	//keys: W S A D J
	public int rideKeys = 0;
	public int openGUI = 0;
	public int keyCooldown = 0;		//press key cooldown, count down in player tick
	
	//render view change
	private boolean isViewChanged = false;
	private boolean isViewPlayer = false;
	

	//player update tick, tick TWICE every tick (preTick + postTick) and BOTH SIDE (client + server)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.START)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			
			/** SERVER SIDE */
			if (capa != null && !event.player.worldObj.isRemote)
			{
				boolean syncTeamList = false;

				//every 32 ticks
				if (event.player.ticksExisted > 0 && event.player.ticksExisted % 32 == 0)
				{
					/** check player UID
					 *  FIX: PlayerUID become -1 when changing body with SyncMod.
					 */
					if (capa.getPlayerUID() < 100)
					{
						//player data lost? restore player data from ServerProxy variable
				        NBTTagCompound nbt = ServerProxy.getPlayerData(event.player.getUniqueID().toString());
				        
				        if (nbt != null)
				        {
				        	capa.loadNBTData(nbt);  //get data from nbt to player's extProps
				        	LogHelper.info("DEBUG : player tick: restore player data: eid: "+event.player.getEntityId()+" pid: "+capa.getPlayerUID()+" uuid: "+event.player.getUniqueID().toString());
				        }
				        
				        //check player UID again, if no UID, set new UID for player
				        if (capa.getPlayerUID() < 100)
				        {
				        	//get new UID
				        	ServerProxy.updatePlayerID(event.player);
							
							//save extProps to ServerProxy
							LogHelper.info("DEBUG : player tick: generate new player UID, save player extProps in ServerProxy");
				    		nbt = new NBTTagCompound();
				    		capa.saveNBTData(nbt);  //get data from player's extProps
				    		ServerProxy.setPlayerData(event.player.getUniqueID().toString(), nbt);
				        }
				        
				        //sync extProps to client
				        capa.sendSyncPacket(0);
					}
					
					//team list fast update
					if (event.player.ticksExisted == 64)
					{
						updateTeamList(event.player, capa);
						syncTeamList = true;
					}
					
//					//TODO for DEBUG only
//					ItemStack hitem = event.player.inventory.getCurrentItem();
//					if(hitem != null && hitem.getItem() instanceof BasicEquip) {
//						try {
//							int[] rvalue = ((BasicEquip)hitem.getItem()).getRecycleValue(hitem.getItemDamage());
//							LogHelper.info("DEBUG : player tick: AAAAAAAAAAAAA "+rvalue[0]+" "+rvalue[1]+" "+rvalue[2]+" "+rvalue[3]);
//						}
//						catch(Exception e) {
//							LogHelper.info("DEBUG : player tick: BBBBBBBBBBBBB "+event.player.dimension);
//						}
//					}
					
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
				
				//init ship UID
				if (!capa.getInitSID() && event.player.ticksExisted % 16 == 0) 
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
			else if (event.player.worldObj.isRemote)
			{	//key cd--
				if (this.keyCooldown > 0)
				{
					this.keyCooldown--;
				}
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
				
//				LogHelper.info("DEBUG : "+event.player.worldObj.isRemote+" "+event.player.getUniqueID());
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
			getent = EntityHelper.getShipBySID(capa.getSIDCurrentTeam(i));

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
		if (player == null || capa == null || player.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}
		
		boolean canSpawn = false;
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		Biome biome = player.worldObj.getBiomeGenForCoords(new BlockPos(blockX, 0, blockZ));
		World w = player.worldObj;
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
									w.spawnEntityInWorld(entityToSpawn);
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
		if (player == null || capa == null || player.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			return;
		}
				
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		Biome biome = player.worldObj.getBiomeGenForCoords(new BlockPos(blockX, 0, blockZ));
		World w = player.worldObj;
		Random rng = player.getRNG();
		
		//ally cooldown--
		int allycd = capa.getPlayerTeamCooldown();
		
		if (allycd > 0)
		{
			capa.setPlayerTeamCooldown(--allycd);
		}
		
		//boss cooldown--
		if ((BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER) || 
			 BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.BEACH)) && capa.hasRing())
		{
			capa.setBossCooldown(capa.getBossCooldown() - 1);
		}
		
		//cooldown = 0, roll spawn
		if (capa.getBossCooldown() <= 0)
		{
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
				            		w.spawnEntityInWorld(entityToSpawn);
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
			            	
			            	ServerProxy.getServer().addChatMessage(new TextComponentString(""+TextFormatting.YELLOW+spawnText+
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
	 * getIsKeyPressed = 該按鍵是否按著, isPressed = 這次event是否為該按鍵
	 * 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyInput(InputEvent event)
	{
		EntityPlayer player = ClientProxy.getClientPlayer();
		this.keySet = ClientProxy.getGameSetting();
		
		//pointer item control
		if (event instanceof KeyInputEvent && player.inventory.getCurrentItem() != null &&
			player.inventory.getCurrentItem().getItem() instanceof PointerItem)
		{
			//get pointer item
			ItemStack pointer = player.inventory.getCurrentItem();
			int meta = pointer.getItemDamage();
			
			//ctrl + xx
			int getKey = -1;
			int orgCurrentItem = player.inventory.currentItem;
			
			//若按住ctrl (sprint key)
			if (keySet.keyBindSprint.isPressed())
			{
				//若按住數字按鍵 1~9, 則切換隊伍, 但是避免數字按鍵將hotbar位置改變 (固定current item)
				for (int i = 0; i < keySet.keyBindsHotbar.length; i++)
				{
					if (keySet.keyBindsHotbar[i].isPressed())
					{
						getKey = i;
						
						//儲存快捷位置到權杖, 使權杖能將快捷列回復到權杖上 (CLIENT ONLY)
						if (!pointer.hasTagCompound())
						{
							pointer.setTagCompound(new NBTTagCompound());
						}
						pointer.getTagCompound().setBoolean("chgHB", true);
						pointer.getTagCompound().setInteger("orgHB", orgCurrentItem);
						
						break;
					}
				}
				
				LogHelper.info("DEBUG : key input: pointer set team: "+getKey+" currItem: "+orgCurrentItem);
				//send key input packet
				if (getKey >= 0)
				{
					//change team id
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, getKey, orgCurrentItem));
				}
			}
			//change pointer mode to caress head mode (meta + 3)
			else if (keySet.keyBindPlayerList.isPressed())
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

		//riding control
		if (player.getRidingEntity() instanceof BasicEntityMount)
		{
			int newKeys = 0;
			BasicEntityMount mount = (BasicEntityMount) player.getRidingEntity();
			
			//change renderer viewer, accept KEYBOARD or MOUSE input
			if (keySet.keyBindPickBlock.isPressed() && this.keyCooldown <= 0)
			{
				LogHelper.info("DEBUG : key event: player view "+this.isViewPlayer);
				this.keyCooldown = 5;
				this.isViewPlayer = !this.isViewPlayer;
			}
			
			//move and open GUI is KEYBOARD ONLY
			if (event instanceof KeyInputEvent)
			{
				//forward
				if (keySet.keyBindForward.isPressed())
				{
					LogHelper.info("DEBUG : key event: press W");
					newKeys = newKeys | 1;
				}
				
				//back
				if (keySet.keyBindBack.isPressed())
				{
					LogHelper.info("DEBUG : key event: press S");
					newKeys = newKeys | 2;
				}
				
				//left
				if (keySet.keyBindLeft.isPressed())
				{
					LogHelper.info("DEBUG : key event: press A");
					newKeys = newKeys | 4;
				}
				
				//right
				if (keySet.keyBindRight.isPressed())
				{
					LogHelper.info("DEBUG : key event: press D");
					newKeys = newKeys | 8;
				}
				
				//jump
				if (keySet.keyBindJump.isPressed())
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
					if (keySet.keyBindInventory.isPressed())
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
		LogHelper.info("DEBUG : player login: "+event.player.getDisplayName()+" "+event.player.getUniqueID());
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
		
		if (capa != null)
		{
			if (!event.player.worldObj.isRemote)
			{
				/** update player id */
				ServerProxy.updatePlayerID(event.player);
				
				/** save player extend data in server proxy cache */
				LogHelper.info("DEBUG : player login: save player extProps in ServerProxy");
	    		NBTTagCompound nbt = new NBTTagCompound();
	    		capa.saveNBTData(nbt);
	    		ServerProxy.setPlayerData(event.player.getUniqueID().toString(), nbt);
	    		
	    		//sync packet
	    		capa.sendSyncPacket(0);
			}//end server side
		}//end player extProps not null
	}
	
	//player loggout, NO USE in singleplayer
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerLogout(PlayerLoggedOutEvent event)
	{
    	LogHelper.info("DEBUG : player logout: "+event.player.getDisplayName()+" "+event.player.getUniqueID());
    	CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
    	
    	/** save player extend data in server proxy */
    	if (capa != null)
    	{
    		LogHelper.info("DEBUG : player logout: save player extProps in ServerProxy");
    		//save player nbt data
    		NBTTagCompound nbt = new NBTTagCompound();
    		
    		capa.saveNBTData(nbt);
    		ServerProxy.setPlayerData(event.player.getUniqueID().toString(), nbt);
    	}
	}
	
	/**
	 * player change dimension, need to update player data
	 * 
	 * NOTE: this event do not trigger when End(1) -> Overworld(0) 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerChangeDim(PlayerChangedDimensionEvent event)
	{
		/**load player extend data
		 */
		LogHelper.info("DEBUG : player change dim: "+event.player.getDisplayName()+" "+event.player.getUniqueID());
		
		if (event.player != null && !event.player.worldObj.isRemote)
		{
			/** update player id */
			ServerProxy.updatePlayerID(event.player);
		}//end server side
	}
	
	//change viewer entity when riding mounts, this is CLIENT ONLY event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void renderTick(TickEvent.RenderTickEvent event)
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
    public void serverTick(TickEvent.ServerTickEvent event)
	{
		if (event.phase == Phase.END)
		{	//在server tick處理完全部事情後發動
			ServerProxy.updateServerTick();
		}
	}
	
	
}