package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.ITileWaypoint;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PointerItem extends BasicItem
{

	private static final String NAME = "PointerItem";
	private boolean formatFlag = false;
	private int formatAddID = 0;
	private int formatCD = 0;
	
	
	public PointerItem()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setFull3D();
        
        GameRegistry.register(this);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return String.format("item.%s", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void initModel()
	{
		//在inventory(背包介面)中要顯示的model, 依照meta value對應不同model
		//meta值有多個, 則依照meta值設定使用的texture
		ModelResourceLocation[] models = new ModelResourceLocation[4];
		
		//宣告並設定textures位置: 有4種貼圖
		for (int i = 0; i < 4; i++)
		{
			models[i] = new ModelResourceLocation(getRegistryName() + String.valueOf(i), "inventory");
		}

		//登錄全部textures
	    ModelBakery.registerItemVariants(this, models);
		
	    //依照各meta值設定各自texture: 有6種meta值
	    ModelLoader.setCustomModelResourceLocation(this, 0, models[0]);
	    ModelLoader.setCustomModelResourceLocation(this, 1, models[1]);
	    ModelLoader.setCustomModelResourceLocation(this, 2, models[2]);
	    ModelLoader.setCustomModelResourceLocation(this, 3, models[3]);
	    ModelLoader.setCustomModelResourceLocation(this, 4, models[3]);
	    ModelLoader.setCustomModelResourceLocation(this, 5, models[3]);
    }

	/** add only 1 type of item to creative tab */
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item));
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
	{
        return true;
    }
	
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
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack item)
	{
//		LogHelper.debug("DEBUG: pointer swing (left click) "+entityLiving);
		int meta = item.getItemDamage();
		
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			
			//玩家左鍵使用此武器時 (client side only)
			if (entity.world.isRemote)
			{
				GameSettings keySet = ClientProxy.getGameSetting();
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
				RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
				
				//hit entity
				if (hitObj != null)
				{
					LogHelper.debug("DEBUG: pointer left click: ENTITY "+hitObj.entityHit);
					
//					//DEBUG
//					if(hitObj.entityHit != null) {
//						Class<? extends Entity> tarClass = hitObj.entityHit.getClass();
//						String tarName = (String) EntityList.classToStringMapping.get(tarClass);
//						tarClass = (Class<? extends Entity>) EntityList.stringToClassMapping.get(tarName);
//						LogHelper.debug("DEBUG: ppppppp: "+tarClass.getSimpleName().equals("EntityItemFrame")+"   "+tarClass.getSimpleName());
//						Iterator iter = EntityList.classToStringMapping.entrySet().iterator();
//						while(iter.hasNext()) {
//							Map.Entry getc = (Entry) iter.next();
//							Object key = getc.getKey();
//							Object val = getc.getValue();
//							LogHelper.debug("DEBUG: pointer left click:  "+key);
//						}
//					}
					
					//若為ship or mounts
					if (hitObj.entityHit instanceof BasicEntityShip || hitObj.entityHit instanceof BasicEntityMount)
					{
						BasicEntityShip ship = null;
						
						//get ship entity
						if (hitObj.entityHit instanceof BasicEntityShip)
						{
							ship = (BasicEntityShip)hitObj.entityHit;
							
							int hitHeight = CalcHelper.getEntityHitHeightByClientPlayer(ship);
							int hitAngle = CalcHelper.getEntityHitSideByClientPlayer(ship);
							
							//send hit height packet
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.HitHeight, ship.getEntityId(), hitHeight, hitAngle));
						}
						else
						{
							ship = (BasicEntityShip) ((BasicEntityMount)hitObj.entityHit).getHostEntity();
						}
						
						//null check
						if (ship == null) return false;
						
						//是主人: 左鍵: add team/set focus 蹲下左鍵:remove team
						if (TeamHelper.checkSameOwner(player, ship) && capa != null)
						{
							//check is in team
							int i = capa.checkIsInCurrentTeam(ship.getShipUID());
							
							//蹲下左鍵: remove team if in team
							if (keySet.keyBindSneak.isKeyDown())
							{
								//if in team
								if (i >= 0)
								{
									LogHelper.debug("DEBUG: pointer remove team: "+ship);
									//if single mode, set other ship focus
									if (meta == 0)
									{
										for (int j = 0; j < 6; j++)
										{
											if (j != i && capa.getShipEntityCurrentTeam(j) != null)
											{
												//focus ship j
												CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetSelect, meta, capa.getShipEntityCurrentTeam(j).getShipUID()));
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
							else
							{
								//in team, set focus
								if (i >= 0)
								{
									LogHelper.debug("DEBUG: pointer set focus: "+hitObj.entityHit);
									CommonProxy.channelG.sendToServer(new C2SGUIPackets(player , C2SGUIPackets.PID.SetSelect, meta, ship.getShipUID()));
								}
								//not in team, add team
								else
								{
									LogHelper.debug("DEBUG: pointer add team: "+hitObj.entityHit);
									CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AddTeam, ship.getEntityId()));
								
									//若single mode, 則每add一隻就設該隻為focus
									if (meta == 0)
									{
										CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetSelect, meta, ship.getShipUID()));
									}
								}
								
								return true;
							}
						}
//						//ship類非主人
//						else
//						{
//							//功能未定
//						}
					}
					//其他類entity
					else
					{
						if (hitObj.entityHit != null)
						{
							String tarName = hitObj.entityHit.getClass().getSimpleName();
							LogHelper.debug("DEBUG: pointer get target class: "+tarName);
							player.sendMessage(new TextComponentTranslation("chat.shincolle:pointer.settargetclass", "  "+tarName));
							//send sync packet to server
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetTarClass, tarName));
							return true;
						}
					}
				}//end hit != null
				
				//click on air
				//蹲下左鍵 vs block or 非自己的寵物, 則切換pointer模式
				if (keySet.keyBindSneak.isKeyDown())
				{
					//sneak+sprint: clear team
					if (keySet.keyBindSprint.isKeyDown())
					{
						LogHelper.debug("DEBUG: pointer clear all focus");
						//send sync packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.ClearTeam, 0));
						return true;
					}
					//sneak only: change pointer mode
					else
					{
						//meta++ and cancel caress head mode
						switch(meta)
						{
						case 1:
						case 4:
							meta = 2;
							break;
						case 2:
						case 5:
							meta = 0;
							break;
						default:
							meta = 1;
							break;
						}

						item.setItemDamage(meta);
						
						//send sync packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SyncPlayerItem, meta));
						return true;
					}
				}
				
				//press SPRINT (CTRL) ONLY
				if (keySet.keyBindSprint.isKeyDown())
				{
					//in formation mode: change formation
					if (meta == 2)
					{
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
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		int meta = stack.getMetadata();
		
		if (meta > 2) return new ActionResult(EnumActionResult.SUCCESS, stack);
		
		//client side
		if (world.isRemote)
		{
			GameSettings keySet = ClientProxy.getGameSetting();  //get pressed key
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
			
			//get entity
			if (hitObj != null && hitObj.typeOfHit == RayTraceResult.Type.ENTITY)
			{
//				LogHelper.debug("DEBUG: pointer right click: ENTITY "+hitObj.entityHit.getClass().getSimpleName());
				
				//right + sprint: entity: guard entity(move only)
				if (keySet.keyBindSprint.isKeyDown())
				{
					//set guard entity (move only: type = 0)
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.GuardEntity, meta, 0, hitObj.entityHit.getEntityId()));
					return new ActionResult(EnumActionResult.SUCCESS, stack);
				}
				
				//若為ship or mounts
				if (hitObj.entityHit instanceof BasicEntityShip || hitObj.entityHit instanceof BasicEntityMount)
				{
					BasicEntityShip ship = null;
					
					//get ship entity
					if (hitObj.entityHit instanceof BasicEntityShip)
					{
						ship = (BasicEntityShip)hitObj.entityHit;
						int hitHeight = CalcHelper.getEntityHitHeightByClientPlayer(ship);
						int hitAngle = CalcHelper.getEntityHitSideByClientPlayer(ship);
						
						//send hit height packet
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.HitHeight, ship.getEntityId(), hitHeight, hitAngle));
					}
					else
					{
						ship = (BasicEntityShip) ((BasicEntityMount)hitObj.entityHit).getHostEntity();
					}
					
					//null check
					if (ship == null) return new ActionResult(EnumActionResult.PASS, stack);
					
					//是主人: 右鍵: set sitting
					if (TeamHelper.checkSameOwner(player, ship))
					{
						//蹲下右鍵: open GUI
						if (player.isSneaking())
						{
							//send GUI packet
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.OpenShipGUI, ship.getEntityId()));
						}
						//右鍵
						else
						{
							//對座騎: 若4格內則上座騎(BasicEntityMount.class內判定)
							if (hitObj.entityHit instanceof BasicEntityMount)
							{
								if (player.getDistanceSqToEntity(hitObj.entityHit) <= 16D)
								{
									return new ActionResult(EnumActionResult.SUCCESS, stack);
								}
							}
							
							//send sit packet
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetSitting, meta, ship.getShipUID()));
						}
						
						return new ActionResult(EnumActionResult.SUCCESS, stack);
					}
					//ship類非主人
					else
					{
						//檢查friendly fire, 判定要attack還是要move
						if (ConfigHandler.friendlyFire)
						{
							//attack target
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AttackTarget, meta, hitObj.entityHit.getEntityId()));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 5D, 0D, (byte)2);
						}
						else
						{
							//移動到該ship旁邊
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, 1, (int)hitObj.entityHit.posX, (int)hitObj.entityHit.posY, (int)hitObj.entityHit.posZ));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 4D, 0D, (byte)2);
						}
					}
				}
				//其他類entity
				else
				{
					if (hitObj.entityHit instanceof EntityPlayer)
					{
						//檢查friendly fire, 判定要attack還是要move
						if (ConfigHandler.friendlyFire)
						{
							//attack target
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AttackTarget, meta, hitObj.entityHit.getEntityId()));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 5D, 0D, (byte)2);
						}
						else
						{
							//移動到該PLAYER旁邊
							CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, 1, (int)hitObj.entityHit.posX, (int)hitObj.entityHit.posY, (int)hitObj.entityHit.posZ));
							//在目標上畫出標記
							ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 4D, 0D, (byte)2);
						}
					}
					else
					{
						//attack target
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.AttackTarget, meta, hitObj.entityHit.getEntityId()));
						//在目標上畫出標記
						ParticleHelper.spawnAttackParticleAtEntity(hitObj.entityHit, 0.3D, 5D, 0D, (byte)2);
					}
				}
			}//end hitObj = entity
			//若沒抓到entity, 則用getPlayerMouseOverBlock抓block
			else
			{
				//若按住shift, 則開啟formation GUI
				if (keySet.keyBindSneak.isKeyDown())
				{
					//send GUI packet
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.OpenItemGUI, 0));
					return new ActionResult(EnumActionResult.SUCCESS, stack);
				}
	    			
				RayTraceResult hitObj2 = BlockHelper.getPlayerMouseOverBlock(64D, 1F);

				if (hitObj2 != null)
				{
					//抓到的是block
					if (hitObj2.typeOfHit == RayTraceResult.Type.BLOCK)
					{
						/**hit side (適合移動位置): 0:下方(y-1) 1:上方(y+1) 2:北方(z-1) 3:南方(z+1) 4:西方(x-1) 5:東方(x+1)*/
						int x = hitObj2.getBlockPos().getX();
						int y = hitObj2.getBlockPos().getY();
						int z = hitObj2.getBlockPos().getZ();
						
						//check target is waypoint
						IBlockState state = world.getBlockState(hitObj2.getBlockPos());
						TileEntity tile = world.getTileEntity(hitObj2.getBlockPos());
						
						//if not waypoint, tweak target position
						if (!BlockHelper.checkBlockIsLiquid(state) && !(tile instanceof ITileWaypoint))
						{
							switch (hitObj2.sideHit.getIndex())
							{
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
						}
						
						LogHelper.debug("DEBUG: pointer right click: BLOCK: side: "+hitObj2.sideHit+" xyz: "+x+" "+y+" "+z);
						
						int guardType = 1;
						
						//right + sprint: entity: guard (move only)
						if (keySet.keyBindSprint.isKeyDown())
						{
							//set guard entity (move only: type = 0)
							guardType = 0;
						}

						//move to xyz
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, guardType, x, y, z));
						
						//在目標上畫出標記
						ParticleHelper.spawnAttackParticleAt(x+0.5D, y, z+0.5D, 0.3D, 4D, 0D, (byte)25);
					}
//					//抓到entity (非預期狀況, 正常應該不會再抓到entity)
//					else if (hitObj2.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
//					{
//						LogHelper.debug("DEBUG: pointer right click: ENTITY (method 2) "+hitObj2.entityHit.getClass().getSimpleName());
//						//move to entity
//						//移動到該ship旁邊
//						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetMove, meta, 1, (int)hitObj2.entityHit.posX, (int)hitObj2.entityHit.posY, (int)hitObj2.entityHit.posZ));
//						//在目標上畫出標記
//						ParticleHelper.spawnAttackParticleAt(hitObj2.entityHit.posX+0.5D, hitObj2.entityHit.posY, hitObj2.entityHit.posZ+0.5D, 0.3D, 4D, 0D, (byte)25);
//					}
					else
					{
						LogHelper.debug("DEBUG: pointer right click: MISS");
					}
				}//end hitObj2 = block
			}//end hitObj2 != null
		}
		
		return new ActionResult(EnumActionResult.PASS, stack);
    }
	
	//left click on entity
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
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
	public void onUpdate(ItemStack item, World world, Entity player, int slot, boolean inUse)
	{
		if (world.isRemote)
		{
			//count format command cd, CLIENT SIDE ONLY
			if (this.formatFlag)
			{
				//cd--
				this.formatCD--;
				
				//cd = 0, send format packet
				if(this.formatCD <= 0 && player instanceof EntityPlayer)
				{
					CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer)player);
					
					if (capa != null)
					{
						//calc formatID
						int fid = capa.getFormatID()[capa.getCurrentTeamID()] + this.formatAddID;
						fid %= 6;
						
						((EntityPlayer)player).sendMessage(new TextComponentString(
								I18n.format("chat.shincolle:pointer.changeformation") + " " +
								I18n.format("gui.shincolle:formation.format" + fid)));
						
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
			if (!inUse)
			{
				if (item.hasTagCompound() && item.getTagCompound().getBoolean("chgHB"))
				{
					int orgCurrentItem = item.getTagCompound().getInteger("orgHB");
					LogHelper.debug("DEBUG: change hotbar "+((EntityPlayer)player).inventory.currentItem+" to "+orgCurrentItem);
					
					((EntityPlayer)player).inventory.currentItem = orgCurrentItem;
					CommonProxy.channelG.sendToServer(new C2SInputPackets(C2SInputPackets.PID.SyncHandheld, orgCurrentItem));
					item.getTagCompound().setBoolean("chgHB", false);
				}
			}
		}//end client side
		
		//show team mark
		if (player instanceof EntityPlayer &&
			EntityHelper.getPointerInUse((EntityPlayer) player) != null &&
			item.getMetadata() < 3 ||
			ConfigHandler.alwaysShowTeamParticle)
		{
			if (world.isRemote)
			{
				if (player.ticksExisted % 32 == 0)
				{
					//顯示隊伍圈圈, 選擇圈圈, 可控制圈圈等
					CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer)player);
					BasicEntityShip teamship = null;
					boolean select = false;
					int meta = item.getItemDamage();
					int type = 0;
					
					if (capa != null)
					{
						for (int i = 0; i < 6; i++)
						{
							teamship = capa.getShipEntityCurrentTeam(i);
							
//								//debug
//								if(player.ticksExisted % 40 == 0) {
//									LogHelper.debug("DEBUG: pointer: show team "+i+" "+extProps.getTeamSelected(i)+" "+teamship);
//								}
							
							if (teamship != null)
							{
								select = capa.getSelectStateCurrentTeam(i);
								
								//若是控制目標, 則顯示為pointer顏色
								if (select)
								{
									switch (meta)
									{
									default:	//default mode
										type = 1;
										break;
									case 1:		//group mode
									case 4:
										type = 2;
										break;
									case 2:		//formation mode
									case 5:
										type = 3;
										break;
									}
								}
								//非控制目標, 都顯示為綠色, formation mode保持黃色
								else
								{
									switch (meta)
									{
									default:	//default mode
										type = 0;
										break;
									case 2:		//formation mode
									case 5:
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
		}//end inUse
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {  	
    	CapaTeitoku capa = CapaTeitoku.getTeitokuCapabilityClientOnly();
		
    	if (capa != null)
    	{
    		if (capa.needInit) return;  //for mod interact: disable addInfo if no capa
    		
    		String str1, str2;
    		String str3 = null;
    		
    		//draw control mode and formation text
    		int fid = capa.getFormatID()[capa.getCurrentTeamID()];
    		
    		if (fid >= 0)
    		{
    			str3 = TextFormatting.GOLD + I18n.format("gui.shincolle:formation.format"+fid);
    		}
    		
    		switch (itemstack.getItemDamage())
    		{
        	case 1:
        		str1 = TextFormatting.RED+I18n.format("gui.shincolle:pointer1")+" : "+str3;
        		str2 = TextFormatting.GRAY+I18n.format("gui.shincolle:pointer3");
        		break;
        	case 2:
        		str1 = TextFormatting.GOLD+I18n.format("gui.shincolle:pointer2")+" : "+str3;
        		str2 = TextFormatting.GRAY+I18n.format("gui.shincolle:pointer3");
        		break;
    		default:
    			str1 = TextFormatting.AQUA+I18n.format("gui.shincolle:pointer0")+" : "+str3;
    			str2 = TextFormatting.GRAY+I18n.format("gui.shincolle:pointer3");
    			break;
        	}
        	
        	list.add(str1);
			list.add(str2);
			
			//draw current team id
    		list.add(TextFormatting.YELLOW+""+TextFormatting.UNDERLINE + 
    				String.format("%s %d", I18n.format("gui.shincolle:pointer4"), capa.getCurrentTeamID()+1));
    		
    		//draw current team ship name
    		BasicEntityShip ship = null;
    		String name = null;
    		int level = 0;
    		int j = 1;
    		
    		for (int i = 0; i < 6; i++)
    		{
    			//get entity
    			ship = capa.getShipEntityCurrentTeam(i);
    			
    			if (ship != null)
    			{
    				//get level
    				level = ship.getStateMinor(ID.M.ShipLevel);
    				
	    			//get nam
	    			if (ship.getCustomNameTag() != null && ship.getCustomNameTag().length() > 0)
	    			{
	    				name = ship.getCustomNameTag();
	    			}
	    			else
	    			{
	    				name = I18n.format("entity.shincolle."+ship.getClass().getSimpleName()+".name");
	    			}
	    			
	    			//add info string
    				if (capa.getSelectStateCurrentTeam(i))
    				{
    					list.add(TextFormatting.WHITE + String.format("%d: %s - Lv %d", j, name, level));
	    			}
	    			else
	    			{
	    				list.add(TextFormatting.GRAY + String.format("%d: %s - Lv %d", j, name, level));
	    			}
	    			
	    			j++;
    			}
    			//ship is null, check ship UID
    			else
    			{
    				//check ship UID
    				if (capa.getSIDCurrentTeam(i) > 0)
    				{
    					list.add(TextFormatting.DARK_RED+""+TextFormatting.OBFUSCATED+I18n.format("gui.shincolle:formation.nosignal"));
    					j++;
    				}
    			}//end ship is null
    		}//end teamList loop
    	}
    }
    
    
}
