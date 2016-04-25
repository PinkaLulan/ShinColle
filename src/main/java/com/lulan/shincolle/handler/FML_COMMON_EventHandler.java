package com.lulan.shincolle.handler;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGTBoss;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMTBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierAkagiBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierKagaBoss;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakazeBoss;
import com.lulan.shincolle.entity.mounts.EntityMountSeat;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmU511Mob;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FML_COMMON_EventHandler {
	
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
	public void onPlayerTick(PlayerTickEvent event) {
		if(event.phase == Phase.START) {
			ExtendPlayerProps extProps = (ExtendPlayerProps) event.player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null && !event.player.worldObj.isRemote) {
				boolean syncTeamList = false;

				//every 32 ticks
				if(event.player.ticksExisted > 0 && event.player.ticksExisted % 32 == 0) {
					/** check player UID
					 *  FIX: PlayerUID become -1 when changing body with SyncMod.
					 */
					if(extProps.getPlayerUID() < 100) {
						//player data lost? restore player data from ServerProxy variable
				        NBTTagCompound nbt = ServerProxy.getPlayerData(event.player.getUniqueID().toString());
				        
				        if(nbt != null) {
				        	extProps.loadNBTData(nbt);  //get data from nbt to player's extProps
				        	LogHelper.info("DEBUG : player tick: restore player data: eid: "+event.player.getEntityId()+" pid: "+extProps.getPlayerUID()+" uuid: "+event.player.getUniqueID().toString());
				        }
				        
				        //check player UID again, if no UID, set new UID for player
				        if(extProps.getPlayerUID() < 100) {
				        	//get new UID
				        	ServerProxy.updatePlayerID(event.player);
							
							//save extProps to ServerProxy
							LogHelper.info("DEBUG : player tick: generate new player UID, save player extProps in ServerProxy");
				    		nbt = new NBTTagCompound();
				    		extProps.saveNBTData(nbt);  //get data from player's extProps
				    		ServerProxy.setPlayerData(event.player.getUniqueID().toString(), nbt);
				        }
				        
				        //sync extProps to client
			        	extProps.sendSyncPacket(0);
					}
					
					//team list fast update
					if(event.player.ticksExisted == 64) {
						updateTeamList(event.player, extProps);
						syncTeamList = true;
					}
					
//					//TODO DEBUG
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
					if(event.player.ticksExisted % 128 == 0) {
						/** check player entity id and update player data to ServerProxy cache */
						//get server cache
						int peid = EntityHelper.getPlayerEID(extProps.getPlayerUID());
						
						//if entity id is different, update new eid
						if(peid != event.player.getEntityId()) {
							LogHelper.info("DEBUG : player tick: player entity id changed, update new eid: "+peid+" to "+event.player.getEntityId());
							ServerProxy.updatePlayerID(event.player);
						}
						
						//every 256 ticks
						if(event.player.ticksExisted % 256 == 0) {
							updateTeamList(event.player, extProps);
							syncTeamList = true;
						}//end every 256 ticks
					}//end every 128 ticks
				}//end every 32 ticks
				
				//spawn boss ticking
				spawnBoss(event.player, extProps);
				
				//init ship UID
				if(!extProps.getInitSID() && event.player.ticksExisted % 16 == 0) {
					//update ship temaList
					extProps.updateShipEntityBySID();
					//sync flag
					CommonProxy.channelG.sendTo(new S2CGUIPackets(S2CGUIPackets.PID.FlagInitSID, 0, true), (EntityPlayerMP) event.player);
					LogHelper.info("DEBUG : player tick: update team entity");
					syncTeamList = true;
				}//end init ship UID
				
				//send team list sync packet
				if(syncTeamList) {
					extProps.sendSyncPacket(0);
				}
			}//end server side, extProps != null
			
			//count down key cooldown
			if(event.player.worldObj.isRemote) {
				if(this.keyCooldown > 0) {
					this.keyCooldown--;
				}
			}
			
			//check ring item (check for first found ring only) every 32 ticks
			if(event.player.ticksExisted % 32 == 0) {
				boolean hasRing = false;
				ItemStack itemRing = null;
				
				for(int i = 0; i < event.player.inventory.getSizeInventory(); ++i) {
					if(event.player.inventory.getStackInSlot(i) != null && 
					   event.player.inventory.getStackInSlot(i).getItem() == ModItems.MarriageRing) {
						hasRing = true;
						itemRing = event.player.inventory.getStackInSlot(i);
						break;
					}
				}
				
				if(extProps != null) {
					//原本有ring, 變成沒有, 則取消飛行狀態
					if(extProps.hasRing() && !hasRing) {
						event.player.capabilities.isFlying = false;
					}
					
					//update hasRing flag
					extProps.setHasRing(hasRing);
					
					if(itemRing != null) {
						if(itemRing.hasTagCompound() && itemRing.getTagCompound().getBoolean("isActive")) {
							extProps.setRingActive(true);
						}
					}
				}
				
//				LogHelper.info("DEBUG : "+event.player.worldObj.isRemote+" "+event.player.getUniqueID());
			}//end player per 32 ticks
		}//end player tick phase: START
	}//end onPlayerTick
	
	/** update team list of pointer item */
	private void updateTeamList(EntityPlayer player, ExtendPlayerProps extProps) {
		/** update ships in pointer team list */
		//check entity is alive
		BasicEntityShip getent = null;
		for(int i = 0; i < 6; i++) {
			//get ship by UID
			getent = EntityHelper.getShipBySID(extProps.getSIDCurrentTeam(i));

			//get ship
			if(getent != null) {
				if(EntityHelper.checkSameOwner(getent, player)) {
					//update ship entity
					extProps.addShipEntityToCurrentTeam(i, getent);
				}
				else {
					//owner changed, remove ship
					extProps.addShipEntityToCurrentTeam(i, null);
				}
			}
			//ship lost
			else {
				//clear slot if no ship UID (ship UID invalid)
				if(extProps.getSIDCurrentTeam(i) <= 0) {
					extProps.addShipEntityToCurrentTeam(i, null);
				}
			}	
		}
	}
	
	/** TODO rewrite boss spawn methods
	 * add different probs each boss?
	 */
	/** spawn boss ticking */
	private void spawnBoss(EntityPlayer player, ExtendPlayerProps extProps) {
		int blockX = (int) player.posX;
		int blockZ = (int) player.posZ;
		int spawnX, spawnY, spawnZ = 0;
		BiomeGenBase biome = player.worldObj.getBiomeGenForCoords(blockX, blockZ);
		World w = player.worldObj;
		Random rng = player.getRNG();
		
		//ally cooldown--
		int allycd = extProps.getPlayerTeamCooldown();
		if(allycd > 0) {
			extProps.setPlayerTeamCooldown(--allycd);
		}
		
		//boss cooldown--
		if((BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.WATER) || 
			BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.BEACH) ||
			BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.RIVER) ||
			BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN)) && extProps.hasRing()) {
			extProps.setBossCooldown(extProps.getBossCooldown() - 1);
		}
		
		//cooldown = 0, roll spawn
		if(extProps.getBossCooldown() <= 0) {
			extProps.setBossCooldown(ConfigHandler.bossCooldown);
			
			int rolli = rng.nextInt(4);
			LogHelper.info("DEBUG : spawn boss: roll spawn "+rolli);
			if(rolli == 0) {
				//尋找20次地點, 找到一個可生成地點即生成後跳出loop
				int i;
				for(i = 0; i < 20; i++) {
					int offX = rng.nextInt(32) + 32;
					int offZ = rng.nextInt(32) + 32;
					
					switch(rng.nextInt(4)) {
					case 0:
						spawnX = blockX + offX;
						spawnZ = blockZ + offZ;
						break;
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

					spawnY = 64 - rng.nextInt(4);
					
					Block blockY = w.getBlock(spawnX, spawnY, spawnZ);
					
					LogHelper.info("DEBUG : spawn boss: get block "+blockY.getLocalizedName()+" "+spawnX+" "+spawnY+" "+spawnZ);
					//生成在水面
					if(blockY.getMaterial() == Material.water) {
						//check 64x64 range
						AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(spawnX-64D, spawnY-64D, spawnZ-64D, spawnX+64D, spawnY+64D, spawnZ+64D);
						List<BasicEntityShipHostile> listBoss = w.getEntitiesWithinAABB(BasicEntityShipHostile.class, aabb);
						int bossNum = 0;
						
						//check boss in list
						if(listBoss.size() > 0) {
							for(BasicEntityShipHostile mob : listBoss) {
								if(mob instanceof IBossDisplayData) {
									bossNum++;
								}
							}
						}
						LogHelper.info("DEBUG : spawn boss: check existed boss: "+bossNum+" all mob: "+listBoss.size());
						
						//若範圍內不到2隻boss, 則可以再生成新boss
			            if(bossNum < 2) {
			            	/**艦隊組成:
			            	 * boss x2 + small ship x4
			            	 */
			            	//roll生成mob
			            	int maxShipNum = ConfigHandler.spawnBossNum + ConfigHandler.spawnMobNum;
			            	int[] spawnList = new int[maxShipNum];
			            	
			            	//roll boss ship
			            	for(i = 0; i < ConfigHandler.spawnBossNum; i++) {
			            		spawnList[i] = rng.nextInt(5);	//boss
			            	}
			            	
			            	//roll small ship
			            	for(i = ConfigHandler.spawnBossNum; i < maxShipNum; i++) {
			            		spawnList[i] = rng.nextInt(2);	//small ship
			            	}
			            	
			            	//new生成mob
			            	BasicEntityShipHostile[] spawnMobs = new BasicEntityShipHostile[maxShipNum];
			            	
			            	//new bosses
			            	for(i = 0; i < ConfigHandler.spawnBossNum; i++) {
			            		switch(spawnList[i]) {
			            		case 1:
			            			spawnMobs[i] = new EntityDestroyerShimakazeBoss(w);
			            			break;
			            		case 2:
			            			spawnMobs[i] = new EntityBattleshipYMTBoss(w);
			            			break;
			            		case 3:
			            			spawnMobs[i] = new EntityCarrierKagaBoss(w);
			            			break;
			            		case 4:
			            			spawnMobs[i] = new EntityCarrierAkagiBoss(w);
			            			break;
			            		default:
			            			spawnMobs[i] = new EntityBattleshipNGTBoss(w);
			            			break;
			            		}
			            	}	
			            	
			            	//new mobs
			            	for(i = ConfigHandler.spawnBossNum; i < maxShipNum; i++) {
			            		switch(spawnList[i]) {
			            		case 1:
			            			spawnMobs[i] = new EntitySubmRo500Mob(w);
			            			break;
			            		default:
			            			spawnMobs[i] = new EntitySubmU511Mob(w);
			            			break;
			            		}
			            	}
			            	
			            	//set mob position and spawn to the world
			            	for(i = 0; i < spawnMobs.length; i++) {
			            		spawnMobs[i].setPosition(spawnX + rng.nextInt(2), spawnY, spawnZ + rng.nextInt(2));
			            		w.spawnEntityInWorld(spawnMobs[i]);
			            	}
			            	
			            	//發出spawn msg
			            	String spawnText = null;
			            	if(rng.nextInt(2) == 0) {
			            		spawnText = StatCollector.translateToLocal("chat.shincolle:bossspawn1");
			            	}
			            	else {
			            		spawnText = StatCollector.translateToLocal("chat.shincolle:bossspawn2");
			            	}
			            	
			            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
			            			new ChatComponentText(EnumChatFormatting.YELLOW+spawnText+
			            					EnumChatFormatting.AQUA+" "+spawnX+" "+spawnY+" "+spawnZ));
			            	
			            	LogHelper.info("DEBUG : spawn fleet "+spawnX+" "+spawnY+" "+spawnZ);
							break;
			            }	
					}
				}
			}//end roll spawn boss
		}//end boss cooldown <= 0
	}
	
	//restore player extProps data, this is SERVER side
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		LogHelper.info("DEBUG : get player respawn event "+event.player.getDisplayName()+" "+event.player.getUniqueID());
    	
        //restore player data from ServerProxy variable
        NBTTagCompound nbt = ServerProxy.getPlayerData(event.player.getUniqueID().toString());
        
        if(nbt != null) {
        	ExtendPlayerProps extProps = (ExtendPlayerProps) event.player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
        	extProps.loadNBTData(nbt);
        	LogHelper.info("DEBUG : player respawn: restore player data: eid: "+event.player.getEntityId()+" pid: "+extProps.getPlayerUID());
        	
        	//sync extProps state to client
        	extProps.sendSyncPacket(0);
        }
	}//end onPlayerRespawn
	
	//
	
	/**get input, 按下+放開都會發出一次, 且每個按鍵分開發出, CLIENT side only event
	 * getIsKeyPressed = 該按鍵是否按著, isPressed = 這次event是否為該按鍵
	 * 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onKeyInput(InputEvent event) {
		EntityPlayer player = ClientProxy.getClientPlayer();
		this.keySet = ClientProxy.getGameSetting();
		
		//pointer item control
		if(event instanceof KeyInputEvent && player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() instanceof PointerItem) {
			//get pointer item
			ItemStack pointer = player.inventory.getCurrentItem();
			int meta = pointer.getItemDamage();
			
			//ctrl + xx
			int getKey = -1;
			int orgCurrentItem = player.inventory.currentItem;
			
			if(keySet.keyBindSprint.getIsKeyPressed()) {
				//check hotbar key 0~8
				for(int i = 0; i < keySet.keyBindsHotbar.length; i++) {
					if(keySet.keyBindsHotbar[i].getIsKeyPressed()) {
						getKey = i;
						
						//儲存快捷位置到權杖, 使權杖能將快捷列回復到權杖上 (CLIENT ONLY)
						if(!pointer.hasTagCompound()) {
							pointer.setTagCompound(new NBTTagCompound());
						}
						pointer.getTagCompound().setBoolean("chgHB", true);
						pointer.getTagCompound().setInteger("orgHB", orgCurrentItem);
						
						break;
					}
				}
				
				LogHelper.info("DEBUG : key input: pointer set team: "+getKey+" currItem: "+orgCurrentItem);
				//send key input packet
				if(getKey >= 0) {
					//change team id
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, getKey, orgCurrentItem));
				}
				//change pointer mode to caress head mode (meta+3)
				else {
					//switch caress head mode
					switch(meta) {
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
		if(player.isRiding() && player.ridingEntity instanceof EntityMountSeat) {
			int newKeys = 0;
			
			//view change, accept KEYBOARD or MOUSE input
			if(keySet.keyBindPickBlock.getIsKeyPressed() && this.keyCooldown <= 0) {
				LogHelper.info("DEBUG : key event: player view "+this.isViewPlayer);
				this.keyCooldown = 5;
				this.isViewPlayer = !this.isViewPlayer;
			}
			
			//move and open GUI is KEYBOARD ONLY
			if(event instanceof KeyInputEvent) {
				//forward
				if(keySet.keyBindForward.getIsKeyPressed()) {
					LogHelper.info("DEBUG : key event: press W");
					newKeys = newKeys | 1;
				}
				
				//back
				if(keySet.keyBindBack.getIsKeyPressed()) {
					LogHelper.info("DEBUG : key event: press S");
					newKeys = newKeys | 2;
				}
				
				//left
				if(keySet.keyBindLeft.getIsKeyPressed()) {
					LogHelper.info("DEBUG : key event: press A");
					newKeys = newKeys | 4;
				}
				
				//right
				if(keySet.keyBindRight.getIsKeyPressed()) {
					LogHelper.info("DEBUG : key event: press D");
					newKeys = newKeys | 8;
				}
				
				//jump
				if(keySet.keyBindJump.isPressed()) {
					LogHelper.info("DEBUG : key event: jump");
					newKeys = newKeys | 16;
				}
				
				//server跟client同時設定, 移動顯示才會順暢, 只靠server設定移動會不連續
				BasicEntityMount mount = ((EntityMountSeat)player.ridingEntity).host;
	
				if(mount != null) {
					//set key for packet
					this.rideKeys = newKeys;
					
					//set client key
					((EntityMountSeat) player.ridingEntity).host.keyPressed = newKeys;
	
					//open ship GUI
					if(keySet.keyBindInventory.isPressed()) {
						CommonProxy.channelG.sendToServer(new C2SInputPackets(1, this.openGUI));
					}
					//set move key
					else {
						CommonProxy.channelG.sendToServer(new C2SInputPackets(0, this.rideKeys));
					}
				}
			}//is key event
		}//end is riding
	}//end key event

	/**player login, called after extProps loaded, SERVER ONLY event
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		/**load player extend data
		 */
		LogHelper.info("DEBUG : player login: "+event.player.getDisplayName()+" "+event.player.getUniqueID());
		ExtendPlayerProps extProps = (ExtendPlayerProps) event.player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			if(!event.player.worldObj.isRemote) {
				/** update player id */
				ServerProxy.updatePlayerID(event.player);
				
				/** save player extend data in server proxy */
				LogHelper.info("DEBUG : player login: save player extProps in ServerProxy");
	    		NBTTagCompound nbt = new NBTTagCompound();
	    		extProps.saveNBTData(nbt);
	    		ServerProxy.setPlayerData(event.player.getUniqueID().toString(), nbt);
	    		
	    		//sync extProps state to client
	    		extProps.sendSyncPacket(0);
			}//end server side
		}//end player extProps not null
	}
	
	//player loggout, NO USE in singleplayer
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
		ExtendPlayerProps extProps = (ExtendPlayerProps) event.player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
    	
    	LogHelper.info("DEBUG : player logout: "+event.player.getDisplayName()+" "+event.player.getUniqueID());
    	
    	/** save player extend data in server proxy */
    	if(extProps != null) {
    		LogHelper.info("DEBUG : player logout: save player extProps in ServerProxy");
    		//save player nbt data
    		NBTTagCompound nbt = new NBTTagCompound();
    		
    		extProps.saveNBTData(nbt);
    		ServerProxy.setPlayerData(event.player.getUniqueID().toString(), nbt);
    	}
	}
	
	/**player change dimension, need to update player data
	 * 
	 * TODO BUG: no trigger when End(1) -> Overworld(0) 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onPlayerChangeDim(PlayerEvent.PlayerChangedDimensionEvent event) {
		/**load player extend data
		 */
		LogHelper.info("DEBUG : player change dim: "+event.player.getDisplayName()+" "+event.player.getUniqueID());
		
		if(event.player != null && !event.player.worldObj.isRemote) {
			/** update player id */
			ServerProxy.updatePlayerID(event.player);
		}//end server side
	}
	
	//change eye height when riding mounts, this is CLIENT ONLY event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void renderTick(TickEvent.RenderTickEvent event) {
		EntityPlayer player = ClientProxy.getClientPlayer();
		
		//在render前, 依照騎乘類型調整eye height
		if(player != null) {
			if(event.phase == TickEvent.Phase.START) {
				if(player.isRiding() && player.ridingEntity instanceof EntityMountSeat) {
					EntityMountSeat seat = (EntityMountSeat) player.ridingEntity;
					
					if(seat.host != null) {
						//將view point切換到ship身上
						if(seat.host.host != null && !this.isViewChanged && !this.isViewPlayer) {
							ClientProxy.getMineraft().renderViewEntity = seat.host.host;
							this.isViewChanged = true;
						}
						
						//將人物視野轉動套用到ship身上
						if(this.isViewChanged) {
							EntityLivingBase camera = ClientProxy.getMineraft().renderViewEntity;
							
							if(camera != null) {	
								camera.renderYawOffset = player.renderYawOffset;
								camera.rotationPitch = player.rotationPitch;
								camera.rotationYaw = player.rotationYaw;
								camera.rotationYawHead = player.rotationYawHead;
								camera.prevCameraPitch = player.prevCameraPitch;
								camera.prevRenderYawOffset = player.prevRenderYawOffset;
								camera.prevRotationPitch = player.prevRotationPitch;
								camera.prevRotationYaw = player.prevRotationYaw;
								camera.prevRotationYawHead = player.prevRotationYawHead;
							}
						}
						
					}//end host != null
				}//end riding SEAT
			}//end phase START
			else if(event.phase == TickEvent.Phase.END) {
				//該render tick結束後必須把視角切回玩家, 以免其他利用view entity的方法出錯
				if(this.isViewChanged) {
					ClientProxy.getMineraft().renderViewEntity = ClientProxy.getClientPlayer();
					this.isViewChanged = false;
				}
				
			}//end phase END
		}
	}
	
	/** Server tick
	 * 
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void serverTick(TickEvent.ServerTickEvent event) {
		if(event.phase == Phase.END) {	//在server tick處理完全部事情後發動
			ServerProxy.updateServerTick();
		}
	}

	
	
}