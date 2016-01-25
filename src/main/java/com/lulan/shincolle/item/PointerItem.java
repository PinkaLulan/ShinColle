package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PointerItem extends BasicItem {

	IIcon[] icons = new IIcon[3];
	private boolean formatFlag = false;
	private int formatAddID = 0;
	private int formatCD = 0;
	
	
	public PointerItem() {
		super();
		this.setUnlocalizedName("PointerItem");
		this.maxStackSize = 1;
		this.setHasSubtypes(true);
		this.setFull3D();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return String.format("item.%s", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"0");
		icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"1");
		icons[2] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"2");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		if(meta > 2) meta = 0;
		return icons[meta];
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
        return true;
    }
	
	/**left click (swing item)
	 * 左鍵棲艦 = 是主人就加入隊伍, 已經在隊伍則設為focus
	 * 蹲下左鍵 = 切換物品模式 or 移除已經在隊伍中的ship
	 */
	/**left click:<br>
	 * left:<br>
	 *   air:				-<br>
	 *   block:				-<br>
	 *   ship(owner):		add team/set focus<br>
	 *   ship(friend):		-<br>
	 *   ship(hostile):		-<br>
	 *   player(friend):	-<br>
	 *   player(hostile):	-<br>
	 *   other:				set target class<br>
	 *<br>  
	 * left + sprint:<br>
	 *   air:				change formaion<br>
	 *   block:				-<br>
	 *   ship(owner):		-<br>
	 *   ship(friend):		-<br>
	 *   ship(hostile):		-<br>
	 *   player(friend):	-<br>
	 *   player(hostile):	-<br>
	 *   other:				-<br>
	 *<br> 
	 * left + sneak:<br>
	 *   air:				change mode<br>
	 *   block:				-<br>
	 *   ship(owner):		remove a ship in the team<br>
	 *   ship(friend):		-<br>
	 *   ship(hostile):		-<br>
	 *   player(friend):	-<br>
	 *   player(hostile):	-<br>
	 *   other:				-<br>
	 *<br>
	 * left + sprint + sneak:<br>
	 *   air:				clear team<br>
	 *   block:				-<br>
	 *   ship(owner):		-<br>
	 *   ship(friend):		-<br>
	 *   ship(hostile):		-<br>
	 *   player(friend):	-<br>
	 *   player(hostile):	-<br>
	 *   other:				-<br>
	 *<br>
	 */
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack item) {
//		LogHelper.info("DEBUG : pointer swing (left click) "+entityLiving);
		int meta = item.getItemDamage();
		
		EntityPlayer player = null;
		if(entityLiving instanceof EntityPlayer) {
			player = (EntityPlayer) entityLiving;
			
			//玩家左鍵使用此武器時 (client side only)
			if(entityLiving.worldObj.isRemote) {
				GameSettings keySet = ClientProxy.getGameSetting();
				ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
				MovingObjectPosition hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
				
				//hit entity
				if(hitObj != null) {
					LogHelper.info("DEBUG : pointer left click: ENTITY "+hitObj.entityHit);
					
//					//DEBUG
//					if(hitObj.entityHit != null) {
//						Class<? extends Entity> tarClass = hitObj.entityHit.getClass();
//						String tarName = (String) EntityList.classToStringMapping.get(tarClass);
//						tarClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(tarName);
//						LogHelper.info("DEBUG : ppppppp: "+tarClass.getSimpleName().equals("EntityItemFrame")+"   "+tarClass.getSimpleName());
//						Iterator iter = EntityList.classToStringMapping.entrySet().iterator();
//						while(iter.hasNext()) {
//							Map.Entry getc = (Entry) iter.next();
//							Object key = getc.getKey();
//							Object val = getc.getValue();
//							LogHelper.info("DEBUG : pointer left click:  "+key);
//						}
//					}
					
					//若為ship or mounts
					if(hitObj.entityHit instanceof BasicEntityShip || hitObj.entityHit instanceof BasicEntityMount) {
						BasicEntityShip ship = null;
						//get ship entity
						if(hitObj.entityHit instanceof BasicEntityShip) {
							ship = (BasicEntityShip)hitObj.entityHit;
						}
						else {
							ship = (BasicEntityShip) ((BasicEntityMount)hitObj.entityHit).getHostEntity();
						}
						
						//null check
						if(ship == null) return false;
						
						//是主人: 左鍵: add team/set focus 蹲下左鍵:remove team
						if(EntityHelper.checkSameOwner(player, ship) && props != null) {
							//check is in team
							int i = props.checkIsInCurrentTeam(ship.getShipUID());
							
							//蹲下左鍵: remove team if in team
							if(player.isSneaking()) {
								//if in team
								if(i >= 0) {
									LogHelper.info("DEBUG : pointer remove team: "+ship);
									//if single mode, set other ship focus
									if(meta == 0) {
										for(int j = 0; j < 6; j++) {
											if(j != i && props.getShipEntityCurrentTeam(j) != null) {
												//focus ship j
												CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetSelect, meta, props.getShipEntityCurrentTeam(j).getShipUID()));
												break;
											}
										}
									}
									
									//its already in team, remove ship
									CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AddTeam, ship.getEntityId()));
									return true;
								}
							}
							//左鍵: add team or set focus if in team
							else {
								//in team, set focus
								if(i >= 0) {
									LogHelper.info("DEBUG : pointer set focus: "+hitObj.entityHit);
									CommonProxy.channelG.sendToServer(new C2SGUIPackets(player , C2SGUIPackets.PID.SetSelect, meta, ship.getShipUID()));
								}
								//not in team, add team
								else {
									LogHelper.info("DEBUG : pointer add team: "+hitObj.entityHit);
									CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AddTeam, ship.getEntityId()));
								
									//若single mode, 則每add一隻就設該隻為focus
									if(meta == 0) {
										CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetSelect, meta, ship.getShipUID()));
									}
								}
								return true;
							}
						}
//						//ship類非主人
//						else {
//							//功能未定
//						}
					}
					//其他類entity
					else {
						if(hitObj.entityHit != null) {
							String tarName = hitObj.entityHit.getClass().getSimpleName();
							LogHelper.info("DEBUG : pointer add class: "+tarName);
							player.addChatMessage(new ChatComponentText(I18n.format("chat.shincolle:pointer.addtargetclass", "  "+tarName)));
							//send sync packet to server
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetTarClass, tarName));
							return true;
						}
					}
				}//end hit != null
				
				//click on air
				//蹲下左鍵 vs block or 非自己的寵物, 則切換pointer模式
				if(keySet.keyBindSneak.getIsKeyPressed()) {
					//sneak+sprint: clear team
					if(keySet.keyBindSprint.getIsKeyPressed()) {
						LogHelper.info("DEBUG : pointer clear all focus");
						//send sync packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.ClearTeam, 0));
						return true;
					}
					//sneak only: change pointer mode
					else {
						meta++;
						if(meta > 2) meta = 0;
						item.setItemDamage(meta);
						
						//send sync packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SyncPlayerItem, meta));
						return true;
					}
				}
				
				//press SPRINT (CTRL) ONLY
				if(keySet.keyBindSprint.getIsKeyPressed()) {
					//in formation mode: change formation
					if(meta == 2) {
						this.formatFlag = true;
						this.formatAddID++;  //format id++
						this.formatCD = 20;  //set cd
						
						return false;
					}
				}
			}//end client side
		}//end player not null
		
        return true;	//both side
    }
	
	/**right click:<br>
	 * right:<br>
	 *   air:				-<br>
	 *   block:				guard(move&attack)<br>
	 *   ship(owner):		sit<br>
	 *   ship(friend):		guard(move&attack)<br>
	 *   ship(hostile):		attack<br>
	 *   player(friend):	guard(move&attack)<br>
	 *   player(hostile):	attack<br>
	 *   other:				attack<br>
	 *<br>  
	 * right + sprint:<br>
	 *   air:				guard(move only)<br>
	 *   block:				guard(move only)<br>
	 *   ship(owner):		guard(move only)<br>
	 *   ship(friend):		guard(move only)<br>
	 *   ship(hostile):		guard(move only)<br>
	 *   player(friend):	guard(move only)<br>
	 *   player(hostile):	guard(move only)<br>
	 *   other:				guard(move only)<br>
	 *<br> 
	 * right + sneak:<br>
	 *   air:				Formation GUI<br>
	 *   block:				-<br>
	 *   ship(owner):		Ship GUI<br>
	 *   ship(friend):		-<br>
	 *   ship(hostile):		-<br>
	 *   player(friend):	-<br>
	 *   player(hostile):	-<br>
	 *   other:				-<br>
	 *<br>
	 * right + sprint + sneak:<br>
	 *   air:				-<br>
	 *   block:				-<br>
	 *   ship(owner):		-<br>
	 *   ship(friend):		-<br>
	 *   ship(hostile):		-<br>
	 *   player(friend):	-<br>
	 *   player(hostile):	-<br>
	 *   other:				-<br>
	 *<br>
	 */
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		int meta = item.getItemDamage();
		
		//client side
		if(world.isRemote) {
			GameSettings keySet = ClientProxy.getGameSetting();  //get pressed key
			ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			MovingObjectPosition hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
			
			//get entity
			if(hitObj != null && hitObj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
				LogHelper.info("DEBUG : pointer right click: ENTITY "+hitObj.entityHit.getClass().getSimpleName());
				
				//right + sprint: entity: guard entity(move only)
				if(keySet.keyBindSprint.getIsKeyPressed()) {
					//set guard entity (move only: type = 0)
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.GuardEntity, meta, 0, hitObj.entityHit.getEntityId()));
					return item;
				}
				
				//若為ship or mounts
				if(hitObj.entityHit instanceof BasicEntityShip || hitObj.entityHit instanceof BasicEntityMount) {
					BasicEntityShip ship = null;
					//get ship entity
					if(hitObj.entityHit instanceof BasicEntityShip) {
						ship = (BasicEntityShip)hitObj.entityHit;
					}
					else {
						ship = (BasicEntityShip) ((BasicEntityMount)hitObj.entityHit).getHostEntity();
					}
					//null check
					if(ship == null) return item;
					
					//是主人: 右鍵: set sitting
					if(EntityHelper.checkSameOwner(player, ship)) {
						//蹲下右鍵: open GUI
						if(player.isSneaking()) {
							//send GUI packet
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.OpenShipGUI, ship.getEntityId()));
						}
						//右鍵: set sitting
						else {
							//send sit packet
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetSitting, meta, ship.getShipUID()));
						}
						return item;
					}
					//ship類非主人
					else {
						//檢查friendly fire, 判定要attack還是要move
						if(ConfigHandler.friendlyFire) {
							//attack target
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AttackTarget, meta, hitObj.entityHit.getEntityId()));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 5D, 0D, (byte)2);
						}
						else {
							//移動到該ship旁邊
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, 1, (int)hitObj.entityHit.posX, (int)hitObj.entityHit.posY, (int)hitObj.entityHit.posZ));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 4D, 0D, (byte)2);
						}
					}
				}
				//其他類entity
				else {
					if(hitObj.entityHit instanceof EntityPlayer) {
						//檢查friendly fire, 判定要attack還是要move
						if(ConfigHandler.friendlyFire) {
							//attack target
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AttackTarget, meta, hitObj.entityHit.getEntityId()));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 5D, 0D, (byte)2);
						}
						else {
							//移動到該PLAYER旁邊
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, 1, (int)hitObj.entityHit.posX, (int)hitObj.entityHit.posY, (int)hitObj.entityHit.posZ));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 4D, 0D, (byte)2);
						}
					}
					else {
						//attack target
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AttackTarget, meta, hitObj.entityHit.getEntityId()));
						//在目標上畫出標記
						ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 5D, 0D, (byte)2);
					}
				}
			}//end hitObj = entity
			//若沒抓到entity, 則用getPlayerMouseOverBlock抓block
			else {
				//若按住shift, 則開啟formation GUI
				if(keySet.keyBindSneak.getIsKeyPressed()) {
					//send GUI packet
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.OpenItemGUI, 0));
					return item;
				}
	    			
				MovingObjectPosition hitObj2 = BlockHelper.getPlayerMouseOverBlock(64D, 1F);

				if(hitObj2 != null) {
					//抓到的是block
					if(hitObj2.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
						/**hit side (適合移動位置): 0:下方(y-1) 1:上方(y+1) 2:北方(z-1) 3:南方(z+1) 4:西方(x-1) 5:東方(x+1)*/
						int x = hitObj2.blockX;
						int y = hitObj2.blockY;
						int z = hitObj2.blockZ;
						
						switch(hitObj2.sideHit) {
						default:
							y--;
							break;
						case 1:
							y++;
							break;
						case 2:
							z--;
							break;
						case 3:
							z++;
							break;
						case 4:
							x--;
							break;
						case 5:
							x++;
							break;
						}
						
						LogHelper.info("DEBUG : pointer right click: BLOCK: side: "+hitObj2.sideHit+" xyz: "+x+" "+y+" "+z);
						
						int guardType = 1;
						
						//right + sprint: entity: guard (move only)
						if(keySet.keyBindSprint.getIsKeyPressed()) {
							//set guard entity (move only: type = 0)
							guardType = 0;
						}

						//move to xyz
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, guardType, x, y, z));
						
						//在目標上畫出標記
						ParticleHelper.spawnAttackParticleAt(x+0.5D, y, z+0.5D, 0.3D, 4D, 0D, (byte)25);
					}
//					//抓到entity (非預期狀況, 正常應該不會再抓到entity)
//					else if(hitObj2.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY){
//						LogHelper.info("DEBUG : pointer right click: ENTITY (method 2) "+hitObj2.entityHit.getClass().getSimpleName());
//						//move to entity
//						//移動到該ship旁邊
//						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, 1, (int)hitObj2.entityHit.posX, (int)hitObj2.entityHit.posY, (int)hitObj2.entityHit.posZ));
//						//在目標上畫出標記
//						ParticleHelper.spawnAttackParticleAt(hitObj2.entityHit.posX+0.5D, hitObj2.entityHit.posY, hitObj2.entityHit.posZ+0.5D, 0.3D, 4D, 0D, (byte)25);
//					}
					else {
						LogHelper.info("DEBUG : pointer right click: MISS");
					}
				}//end hitObj2 = block
			}//end hitObj2 != null
		}
		
		return item;
    }
	
	//按住物品時
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		LogHelper.info("DEBUG : using pointer "+count);
    }

	//right click on solid block
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		LogHelper.info("DEBUG : use pointer "+world.isRemote+" "+player.getDisplayName()+" "+x+" "+y+" "+z+" "+side+" "+hitx+" "+hity+" "+hitz);
		return false;
    }
	
	//left click on entity
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		return true;	//prevent this item to attack entity
    }
	
	/**偵測目前玩家指著的東西
	 * 方法1: player.rayTrace(距離, ticks) 藉由player頭所朝向的方向抓出碰到的東西, 自訂距離, 不抓液體方塊
	 * 方法2: ClientProxy.getMineraft().renderViewEntity 列出所有在client player畫面中出現的entity, 僅抓entity
	 * 方法3: ClientProxy.getMineraft().objectMouseOver 列出所有滑鼠游標指到的東西, 只限近距離, 不抓液體方塊
	 * 方法4: ItemStack.getMovingObjectPositionFromPlayer 列出游標指到的東西, 只限近距離, 可抓液體方塊
	 * 方法5: 自訂func_147447_a 自行修改參數, 不限近距離且可以抓液體方塊 (以上方法全都使用func_147447_a方法)
 	 */
	@Override
	public void onUpdate(ItemStack item, World world, Entity player, int slot, boolean inUse) {
		if(world.isRemote) {
			//count format command cd, CLIENT SIDE ONLY
			if(this.formatFlag) {
				//cd--
				this.formatCD--;
				
				//cd = 0, send format packet
				if(this.formatCD <= 0 && player instanceof EntityPlayer) {
					ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
					
					if(props != null) {
						//calc formatID
						int fid = props.getFormatID()[props.getPointerTeamID()] + this.formatAddID;
						fid %= 6;
						
						String fstr = I18n.format("gui.shincolle:formation.format"+fid);
						((EntityPlayer)player).addChatMessage(new ChatComponentText(I18n.format("chat.shincolle:pointer.changeformation")+" "+fstr));
						
						//send formation packet
						CommonProxy.channelG.sendToServer(new C2SGUIPackets((EntityPlayer) player, C2SGUIPackets.PID.SetFormation, fid));

						//reset flag
						this.formatCD = 0;
						this.formatAddID = 0;
						this.formatFlag = false;
					}
				}//end format CD
			}//end format flag
			
			//restore hotbar position
			if(!inUse) {
				if(item.hasTagCompound() && item.getTagCompound().getBoolean("chgHB")) {
					int orgCurrentItem = item.getTagCompound().getInteger("orgHB");
					LogHelper.info("DEBUG : change hotbar "+((EntityPlayer)player).inventory.currentItem+" to "+orgCurrentItem);
					
					if(((EntityPlayer)player).inventory.currentItem != orgCurrentItem) {
						((EntityPlayer)player).inventory.currentItem = orgCurrentItem;
						CommonProxy.channelG.sendToServer(new C2SInputPackets(2, orgCurrentItem));
						item.getTagCompound().setBoolean("chgHB", false);
					}
				}
			}
		}
		
		//show team mark
		if(inUse || ConfigHandler.alwaysShowTeamParticle) {
			if(player instanceof EntityPlayer) {
				//client side
				if(world.isRemote) {
					if(player.ticksExisted % 32 == 0) {
						//抓視線上的東西 (debug)
//						MovingObjectPosition hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
//						if(hitObj != null) {
//							if(hitObj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
//								LogHelper.info("DEBUG : hit BLOCK "+world.getBlock(hitObj.blockX, hitObj.blockY, hitObj.blockZ).getLocalizedName()+" "+hitObj.blockX+" "+hitObj.blockY+" "+hitObj.blockZ);
//								float[] look = EntityHelper.getLookDegree(hitObj.blockX-player.posX, hitObj.blockY-player.posY, hitObj.blockZ-player.posZ, true);
//								LogHelper.info("DEBUG : look "+look[0]+" "+look[1]);
//							}
//							else if(hitObj.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
//								LogHelper.info("DEBUG : hit ENTITY "+hitObj.entityHit.getClass().getSimpleName());
//							}
//							else {
//								LogHelper.info("DEBUG : hit MISS ");
//							}
//						}
						
						//顯示隊伍圈圈, 選擇圈圈, 可控制圈圈等
						ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
						BasicEntityShip teamship = null;
						boolean select = false;
						int meta = item.getItemDamage();
						int type = 0;
						
						if(extProps != null) {
							for(int i = 0; i < 6; i++) {
								teamship = extProps.getShipEntityCurrentTeam(i);
								
//								//debug
//								if(player.ticksExisted % 40 == 0) {
//									LogHelper.info("DEBUG : pointer: show team "+i+" "+extProps.getTeamSelected(i)+" "+teamship);
//								}
								
								if(teamship != null) {
									select = extProps.getSelectStateCurrentTeam(i);
									
									//若是控制目標, 則顯示為pointer顏色
									if(select) {
										switch(meta) {
										default:	//default mode
											type = 1;
											break;
										case 1:		//group mode
											type = 2;
											break;
										case 2:		//formation mode
											type = 3;
											break;
										}
									}
									//非控制目標, 都顯示為綠色, formation mode保持黃色
									else {
										switch(meta) {
										default:	//default mode
											type = 0;
											break;
										case 2:		//formation mode
											type = 3;
											break;
										}
									}
									
									//在該ship上顯示隊伍圈圈
									ParticleHelper.spawnAttackParticleAtEntity(teamship, 0.3D, type, 0D, (byte)2);
								}
							}//end team list for loop
						}
					}//end every 5 ticks
				}//end client side
			}//end is player
		}//end inUse
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  	
    	ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
    	
    	if(props != null) {
    		String str1, str2;
    		String str3 = null;
    		
    		//draw control mode and formation text
    		int fid = props.getFormatID()[props.getPointerTeamID()];
    		if(fid >= 0) {
    			str3 = EnumChatFormatting.GOLD + I18n.format("gui.shincolle:formation.format"+fid);
    		}
    		
    		switch(itemstack.getItemDamage()) {
        	case 1:
        		str1 = EnumChatFormatting.RED+I18n.format("gui.shincolle:pointer1")+" : "+str3;
        		str2 = EnumChatFormatting.GRAY+I18n.format("gui.shincolle:pointer3");
        		break;
        	case 2:
        		str1 = EnumChatFormatting.GOLD+I18n.format("gui.shincolle:pointer2")+" : "+str3;
        		str2 = EnumChatFormatting.GRAY+I18n.format("gui.shincolle:pointer3");
        		break;
    		default:
    			str1 = EnumChatFormatting.AQUA+I18n.format("gui.shincolle:pointer0")+" : "+str3;
    			str2 = EnumChatFormatting.GRAY+I18n.format("gui.shincolle:pointer3");
    			break;
        	}
        	
        	list.add(str1);
			list.add(str2);
			
			//draw current team id
    		list.add(EnumChatFormatting.YELLOW+""+EnumChatFormatting.UNDERLINE + 
    				String.format("%s %d", I18n.format("gui.shincolle:pointer4"), props.getPointerTeamID()+1));
    	
    		//draw current team ship name
    		BasicEntityShip ship = null;
    		String name = null;
    		int level = 0;
    		int j = 1;
    		for(int i = 0; i < 6; i++) {
    			//get entity
    			ship = props.getShipEntityCurrentTeam(i);
    			
    			if(ship != null) {
    				//get level
    				level = ship.getStateMinor(ID.M.ShipLevel);
    				
	    			//get name
	    			if(ship.getCustomNameTag() != null && ship.getCustomNameTag().length() > 0) {
	    				name = ship.getCustomNameTag();
	    			}
	    			else {
	    				name = I18n.format("entity.shincolle."+ship.getClass().getSimpleName()+".name");
	    			}
	    			
	    			//add info string
    				if(props.getSelectStateCurrentTeam(i)) {
    					list.add(EnumChatFormatting.WHITE + String.format("%d: %s - Lv %d", j, name, level));
	    			}
	    			else {
	    				list.add(EnumChatFormatting.GRAY + String.format("%d: %s - Lv %d", j, name, level));
	    			}
	    			
	    			j++;
    			}
    			//ship is null, check ship UID
    			else {
    				//check ship UID
    				if(props.getSIDCurrentTeam(i) > 0) {
    					list.add(EnumChatFormatting.DARK_RED+""+EnumChatFormatting.OBFUSCATED+I18n.format("gui.shincolle:formation.nosignal"));
    					j++;
    				}
    			}//end ship is null
    		}//end teamList loop
    	}
    }
	
	
    
    
	
}
