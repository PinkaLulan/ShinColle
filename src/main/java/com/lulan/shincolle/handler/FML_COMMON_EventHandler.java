package com.lulan.shincolle.handler;

import java.util.List;

import com.lulan.shincolle.entity.EntityDestroyerShimakazeBoss;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.renderentity.BasicRenderEntity;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class FML_COMMON_EventHandler {

	//player update tick, tick TWICE every tick (preTick + postTick) and BOTH SIDE (client + server)
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if(event.phase == Phase.START) {
			ExtendPlayerProps extProps = (ExtendPlayerProps) event.player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);

			//spawn boss in ocean biome, server side
			if(extProps != null && !event.player.worldObj.isRemote) {
				int blockX = (int) event.player.posX;
				int blockZ = (int) event.player.posZ;
				int spawnX, spawnY, spawnZ = 0;
				BiomeGenBase biome = event.player.worldObj.getBiomeGenForCoords(blockX, blockZ);	
				
				//cooldown--
				if(BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN) && extProps.hasRing()) {
					extProps.setBossCooldown(extProps.getBossCooldown() - 1);
					LogHelper.info("DEBUG : spawn boss: count cd "+extProps.getBossCooldown());
				}
				
				//cooldown = 0, roll spawn
				if(extProps.getBossCooldown() <= 0) {
					extProps.setBossCooldown(4800);
					
					int rolli = event.player.getRNG().nextInt(5);
					LogHelper.info("DEBUG : spawn boss: roll spawn "+rolli);
					if(rolli == 0) {
						//尋找10次地點, 找到一個可生成地點即生成後跳出loop
						for(int i = 0; i < 10; i++) {
							int offX = event.player.getRNG().nextInt(32) + 32;
							int offZ = event.player.getRNG().nextInt(32) + 32;
							
							switch(event.player.getRNG().nextInt(4)) {
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

							spawnY = 64 - event.player.getRNG().nextInt(4);
							
							Block blockY = event.player.worldObj.getBlock(spawnX, spawnY, spawnZ);
							
							LogHelper.info("DEBUG : spawn boss: get block "+blockY.getLocalizedName()+" "+spawnX+" "+spawnY+" "+spawnZ);
							//生成在水面
							if(blockY == Blocks.water) {
								//check 64x64 range
								AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(spawnX-64D, spawnY-64D, spawnZ-64D, spawnX+64D, spawnY+64D, spawnZ+64D);
								List renderEntityList = event.player.worldObj.getEntitiesWithinAABB(EntityDestroyerShimakazeBoss.class, aabb);

								LogHelper.info("DEBUG : spawn boss: check list "+renderEntityList.size());
								
								//list低於1個表示沒有找到其他boss
					            if(renderEntityList.size() < 1) {
					            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
					            			new ChatComponentText(
					            					EnumChatFormatting.YELLOW+
					            					StatCollector.translateToLocal("chat.shincolle:bossshimakaze")+
					            					EnumChatFormatting.AQUA+
					            					" "+spawnX+" "+spawnY+" "+spawnZ));
					            	LogHelper.info("DEBUG : SPAWN BOSS "+" "+spawnX+" "+spawnY+" "+spawnZ);
									EntityDestroyerShimakazeBoss boss = new EntityDestroyerShimakazeBoss(event.player.worldObj);
									boss.setPosition(spawnX, spawnY, spawnZ);
									event.player.worldObj.spawnEntityInWorld(boss);
									break;
					            }	
							}
						}
					}//end roll spawn boss
				}	
			}
			
			//check ring item (check for first found ring only) every 20 ticks
			if(event.player.ticksExisted % 20 == 0) {
				boolean hasRing = false;
				
				for(int i = 0; i < 36; ++i) {
					if(event.player.inventory.getStackInSlot(i) != null && 
					   event.player.inventory.getStackInSlot(i).getItem() == ModItems.MarriageRing) {
						hasRing = true;
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
				}
			}
		}	
	}//end onPlayerTick


}