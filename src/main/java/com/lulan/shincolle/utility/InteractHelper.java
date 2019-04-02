package com.lulan.shincolle.utility;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.item.IShipCombatRation;
import com.lulan.shincolle.item.IShipFoodItem;
import com.lulan.shincolle.item.OwnerPaper;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

/**
 * metods for interact with ship
 */
public class InteractHelper
{
	
	
	public InteractHelper() {}
	
	/** modern kit interact method */
	public static boolean interactModernKit(BasicEntityShip ship, EntityPlayer player, ItemStack stack)
	{
		//add 1 random bonus
		if (ship.getAttrs().addAttrsBonusRandom(ship.getRNG()))
		{
			if (!player.capabilities.isCreativeMode)
			{
                stack.shrink(1);
                
                if (stack.getCount() <= 0)
                {	//物品用完時要設定為null清空該slot
                	player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                }
            }
			
			//set happy emotion
			ship.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
			
			//recalc attrs
			ship.calcShipAttributes(1, true);
			
			//play marriage sound
			ship.playSound(ship.getCustomSound(4, ship), ConfigHandler.volumeShip, 1F);
			return true;
		}
		
		return false;
	}
    
    /** pointer interact method */
	public static boolean interactPointer(BasicEntityShip ship, EntityPlayer player, ItemStack stack)
	{
		//set ai target
		ship.setAITarget(player);
		
		//is owner
		if (TeamHelper.checkSameOwner(player, ship) && !ship.getStateFlag(ID.F.NoFuel))
		{
			if (ship.getMorale() < ID.Morale.L_Excited * 1.3F)
			{
				ship.addMorale(ConfigHandler.baseCaressMorale);
			}

			//show emotes
			ship.applyEmotesReaction(0);
		}
		//not owner or no fuel
		else
		{
			ship.applyEmotesReaction(1);
		}
		
		//clear ai target
		ship.setAITarget(null);
		
		return true;
	}
    
    /** bucket interact method */
	public static boolean interactBucket(BasicEntityShip ship, EntityPlayer player, ItemStack stack)
	{
		//hp不到max hp時可以使用bucket
		if (ship.getHealth() < ship.getMaxHealth())
		{
			if (!player.capabilities.isCreativeMode)
			{  //item-1 in non-creative mode
				stack.shrink(1);
				
				if (stack.getCount() <= 0)
	            {  
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
	            }
            }

            if (ship instanceof BasicEntityShipSmall)
            {
            	ship.heal(ship.getMaxHealth() * 0.1F + 5F);	//1 bucket = 10% hp for small ship
            }
            else
            {
            	ship.heal(ship.getMaxHealth() * 0.05F + 10F);	//1 bucket = 5% hp for large ship
            }
            
            //airplane++
            if (ship instanceof BasicEntityShipCV)
            {
            	BasicEntityShipCV ship2 = (BasicEntityShipCV) ship;
            	ship2.setNumAircraftLight(ship2.getNumAircraftLight() + 1);
            	ship2.setNumAircraftHeavy(ship2.getNumAircraftHeavy() + 1);
            }
            
            return true;
        }
		
		return false;
	}
    
    /** wedding ring interact method */
	public static boolean interactWeddingRing(BasicEntityShip ship, EntityPlayer player, ItemStack stack)
	{
		//stack-1 in non-creative mode
		if (!player.capabilities.isCreativeMode)
		{
            stack.shrink(1);
        }

		//set marriage flag
        ship.setStateFlag(ID.F.IsMarried, true);
        
        //player marriage num +1
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		if (capa != null)
		{
			capa.setMarriageNum(capa.getMarriageNum() + 1);
		}
        
        //play hearts effect
        TargetPoint point = new TargetPoint(ship.dimension, ship.posX, ship.posY, ship.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(ship, 3, false), point);
        
		//play marriage sound
		ship.playSound(ship.getCustomSound(4, ship), ConfigHandler.volumeShip, 1F);
		
		//add morale
		ship.setMorale(16000);
		
		//set shy emotion
		ship.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
        
        //add 3 random bonus point
        for (int i = 0; i < 3; ++i)
        {
        	ship.getAttrs().addAttrsBonusRandom(ship.getRNG());
        }
        
        //update attrs
        ship.calcShipAttributes(31, true);
        
        //物品用完時要設定為null清空該slot
        if (stack.getCount() <= 0)
        {
        	player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        }
        
        return true;
	}
	
    /** kattai hammer interact method */
	public static boolean interactKaitaiHammer(BasicEntityShip ship, EntityPlayer player, ItemStack stack)
	{
		//創造模式不消耗物品
        if (!player.capabilities.isCreativeMode)
        {
        	//damage +1 in non-creative mode
        	stack.setItemDamage(stack.getItemDamage() + 1);
            
            //set item amount
            ItemStack[] items = ShipCalc.getKaitaiItems(ship.getShipClass());
                        
            EntityItem entityItem0 = new EntityItem(ship.world, ship.posX+0.5D, ship.posY+0.8D, ship.posZ+0.5D, items[0]);
            EntityItem entityItem1 = new EntityItem(ship.world, ship.posX+0.5D, ship.posY+0.8D, ship.posZ-0.5D, items[1]);
            EntityItem entityItem2 = new EntityItem(ship.world, ship.posX-0.5D, ship.posY+0.8D, ship.posZ+0.5D, items[2]);
            EntityItem entityItem3 = new EntityItem(ship.world, ship.posX-0.5D, ship.posY+0.8D, ship.posZ-0.5D, items[3]);

            ship.world.spawnEntity(entityItem0);
            ship.world.spawnEntity(entityItem1);
            ship.world.spawnEntity(entityItem2);
            ship.world.spawnEntity(entityItem3);
            
            //drop inventory item
        	for (int i = 0; i < ship.getCapaShipInventory().getSlots(); i++)
        	{
				ItemStack invitem = ship.getCapaShipInventory().getStackInSlot(i);

				if (!invitem.isEmpty())
				{
					//設定要隨機噴出的range
					float f = ship.getRNG().nextFloat() * 0.8F + 0.1F;
					float f1 = ship.getRNG().nextFloat() * 0.8F + 0.1F;
					float f2 = ship.getRNG().nextFloat() * 0.8F + 0.1F;

					while (invitem.getCount() > 0)
					{
						int j = ship.getRNG().nextInt(21) + 10;
						//如果物品超過一個隨機數量, 會分更多疊噴出
						if (j > invitem.getCount())
						{  
							j = invitem.getCount();
						}

						invitem.shrink(j);
						
						//將item做成entity, 生成到世界上
						EntityItem item = new EntityItem(ship.world, ship.posX+f, ship.posY+f1, ship.posZ+f2, new ItemStack(invitem.getItem(), j, invitem.getItemDamage()));
						
						//如果有NBT tag, 也要複製到物品上
						if (invitem.hasTagCompound())
						{
							item.getItem().setTagCompound(invitem.getTagCompound().copy());
						}
						
						ship.world.spawnEntity(item);	//生成item entity
					}
				}
			}
            
        	//kaitai sound
        	ship.playSound(ModSounds.SHIP_KAITAI, ConfigHandler.volumeShip, getSoundPitch(ship));
        	ship.playSound(BasicEntityShip.getCustomSound(3, ship), ConfigHandler.volumeShip, getSoundPitch(ship));
        }
        
        //物品用完時要設定為null清空該slot
        if (stack.getItemDamage() >= stack.getMaxDamage())
        {  //物品耐久度用完時要設定為null清空該slot
        	player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        }
        
        //show emotes
        ship.applyParticleEmotion(8);
		
		//emotes AOE
		EntityHelper.applyShipEmotesAOE(ship.world, ship.posX, ship.posY, ship.posZ, 10D, 6);
         
        ship.setDead();
        
        return true;
	}
	
	/** sound pitch for ship */
    public static float getSoundPitch(BasicEntityShip ship)
    {
        return (ship.getRNG().nextFloat() - ship.getRNG().nextFloat()) * 0.1F + 1F;
    }
	
	/** change owner method:
	 *  1. check owner paper has 2 signs
	 *  2. check owner is A or B
	 *  3. get player entity
	 *  4. change ship's player UID
	 *  5. change ship's owner UUID
	 */
    public static boolean interactOwnerPaper(BasicEntityShip ship, EntityPlayer player, ItemStack itemstack)
	{
		NBTTagCompound nbt = itemstack.getTagCompound();
		boolean changeOwner = false;
		
		if (nbt != null)
		{
			int ida = nbt.getInteger(OwnerPaper.SignIDA);
			int idb = nbt.getInteger(OwnerPaper.SignIDB);
			int idtarget = -1;	//target player uid
			
			//1. check 2 signs
			if (ida > 0 && idb > 0)
			{
				//2. check owner is A or B
				if (ida == ship.getPlayerUID())
				{	//A is owner
					idtarget = idb;
				}
				else
				{	//B is owner
					idtarget = ida;
				}
				
				//3. check player online
				EntityPlayer target = EntityHelper.getEntityPlayerByUID(idtarget);
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(target);
				
				if (capa != null)
				{
					//4. change ship's player UID
					ship.setPlayerUID(idtarget);
					
					//5. change ship's owner UUID
					ship.setOwnerId(target.getUniqueID());
					
					//6. change ship's owner name
					ship.ownerName = target.getName();
					
					LogHelper.debug("DEBUG: change owner: from: pid "+ship.getPlayerUID()+" uuid "+ship.getOwner().getUniqueID());
					LogHelper.debug("DEBUG: change owner: to: pid "+idtarget+" uuid "+target.getUniqueID().toString());
					changeOwner = true;
					
					//send sync packet
					ship.sendSyncPacket(S2CEntitySync.PID.SyncShip_ID, true);
					
					//set cry emotion
					ship.setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, true);
				}//end target != null
			}//end item has 2 signs
		}//end item has nbt
		
		if (changeOwner)
		{
			//play marriage sound
			ship.playSound(BasicEntityShip.getCustomSound(4, ship), ConfigHandler.volumeShip, 1F);
	        
			player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
			return true;
		}
		
		return false;
	}
	
	/** feed
	 * 
	 *  1. morale++
	 *  2. show emotion
	 *  3. sometimes reject food
	 *  4. feed max morale = 4800
	 */
    public static boolean interactFeed(BasicEntityShip ship, EntityPlayer player, ItemStack itemstack)
	{
		Item i = itemstack.getItem();
		int meta = itemstack.getItemDamage();
		int type = 0;
		int mfood = 1;
		int addgrudge = 0;
		int addammo = 0;
		int addammoh = 0;
		int addsatur = 0;
		
		//max 4800 or max saturation, reject food
		if (ship.getFoodSaturation() >= ship.getFoodSaturationMax())
		{
			if (ship.getEmotesTick() <= 0)
			{
				ship.setEmotesTick(40);
				switch (ship.getRNG().nextInt(4))
				{
				case 1:
					ship.applyParticleEmotion(2);  //panic
				break;
				case 2:
					ship.applyParticleEmotion(32);  //hmm
				break;
				case 3:
					ship.applyParticleEmotion(0);  //drop
				break;
				default:
					ship.applyParticleEmotion(11);  //??
				break;
				}
			}
			
			return false;
		}
		
		//is food
		if (i instanceof ItemFood)
		{
			type = 1;
			ItemFood f = (ItemFood) i;
			float fv = f.getHealAmount(itemstack);			//food value
			float sv = f.getSaturationModifier(itemstack);  //saturation value
			if(fv < 1F) fv = 1F;
			mfood = (int)((fv + ship.getRNG().nextInt((int)fv + 5)) * sv * 20F);
			addgrudge = mfood;
		}//end itemfood
		//is ship food
		else if (i instanceof IShipFoodItem)
		{
			type = 2;
			int foodv = (int) ((IShipFoodItem) i).getFoodValue(meta);
			mfood = foodv + ship.getRNG().nextInt(foodv + 1);
			
			switch (((IShipFoodItem) i).getSpecialEffect(meta))
			{
			case 1:  //grudge
				addgrudge = 300 + ship.getRNG().nextInt(500);
				break;
			case 2:  //abyssium
				ship.heal(ship.getMaxHealth() * 0.05F + 1F);
				break;
			case 3:  //ammo
				switch (meta)
				{
				case 0:
					addammo = 30 + ship.getRNG().nextInt(10);
					break;
				case 1:
					addammo = 270 + ship.getRNG().nextInt(90);
					break;
				case 2:
					addammoh = 15 + ship.getRNG().nextInt(5);
					break;
				case 3:
					addammoh = 135 + ship.getRNG().nextInt(45);
					break;
				}
				break;
			case 4:  //polymetal
				//add airplane if CV
				if (ship instanceof BasicEntityShipCV && ship.getRNG().nextInt(10) > 4)
				{
					((BasicEntityShipCV)ship).setNumAircraftLight(((BasicEntityShipCV)ship).getNumAircraftLight()+1);
					((BasicEntityShipCV)ship).setNumAircraftHeavy(((BasicEntityShipCV)ship).getNumAircraftHeavy()+1);
				}
				break;
			case 5:  //toy plane
				//add airplane if CV
				if (ship instanceof BasicEntityShipCV)
				{
					((BasicEntityShipCV)ship).setNumAircraftLight(((BasicEntityShipCV)ship).getNumAircraftLight()+ship.getRNG().nextInt(3)+1);
					((BasicEntityShipCV)ship).setNumAircraftHeavy(((BasicEntityShipCV)ship).getNumAircraftHeavy()+ship.getRNG().nextInt(3)+1);
				}
				break;
			case 6:  //combat ration
				addsatur = 3;
				addgrudge = mfood;
				mfood = 0;
				
				//add morale to happy (3900 ~ 5100)
				mfood = ((IShipCombatRation) i).getMoraleValue(meta);
				
				//if ice cream, clean debuffs
				if (meta == 4 || meta == 5)
				{
					BuffHelper.removeDebuffs(ship);
				}
				
				break;
			}
		}//end ship food
		//is potion
		else if (i instanceof ItemPotion)
		{
			type = 3;
			mfood = -100;	//ship hate potion
			addgrudge = Values.N.BaseGrudge;
		}//end potion
		
		//can feed
		if (type > 0)
		{
			//set happy emotion
			ship.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
			
			//play sound
			if (ship.getStateTimer(ID.T.SoundTime) <= 0)
	    	{
				ship.setStateTimer(ID.T.SoundTime, 20 + ship.getRNG().nextInt(20));
				ship.playSound(BasicEntityShip.getCustomSound(7, ship), ConfigHandler.volumeShip, getSoundPitch(ship));
	    	}
			
			//apply potion effect
			List<PotionEffect> pbuffs = PotionUtils.getEffectsFromStack(itemstack);
			
			if (pbuffs.size() >0)
			{
				//apply buff/debuff potion
	            for (PotionEffect pe : pbuffs)
	            {
	                ship.addPotionEffect(new PotionEffect(pe));
	            }
	            
	            //apply instant potion
				float hp1p = ship.getMaxHealth() * 0.01F;
	            if (hp1p < 1F) hp1p = 1F;
	            
	            //apply heal
	            int lvPotion = BuffHelper.checkPotionHeal(pbuffs);
				if (lvPotion > 0) ship.heal((hp1p * 2F + 2F) * lvPotion);
				
	            //apply damage
				lvPotion = BuffHelper.checkPotionDamage(pbuffs);
				if (lvPotion > 0) ship.attackEntityFrom(DamageSource.MAGIC, (hp1p * 2F + 2F) * lvPotion);
	            
				//update potion buff
	    		ship.calcShipAttributes(8, true);
			}
			
			//morale++
			ship.addMorale(mfood);
			
			//saturation++
			ship.setFoodSaturation(ship.getFoodSaturation() + 1 + addsatur);
			
			//misc++
			ship.addGrudge((int) (addgrudge * ship.getAttrs().getAttrsBuffed(ID.Attrs.GRUDGE)));
			ship.addAmmoLight((int) (addammo * ship.getAttrs().getAttrsBuffed(ID.Attrs.AMMO)));
			ship.addAmmoHeavy((int) (addammoh * ship.getAttrs().getAttrsBuffed(ID.Attrs.AMMO)));
			
			//item--
			if (player != null)
			{
				if (!player.capabilities.isCreativeMode)
				{
		            if (itemstack.getCount() - 1 <= 0)
		            {
		            	//update slot
		            	itemstack = itemstack.getItem().getContainerItem(itemstack);
		            	player.inventory.setInventorySlotContents(player.inventory.currentItem, itemstack);
		            }
		        }
			}
			
			//show eat emotion
			if (ship.getEmotesTick() <= 0)
			{
				ship.setEmotesTick(40);
				
				switch (ship.getRNG().nextInt(3))
				{
				case 1:
					ship.applyParticleEmotion(9);  //hungry
				break;
				case 2:
					ship.applyParticleEmotion(30); //pif
				break;
				default:
					ship.applyParticleEmotion(1);  //heart
				break;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	
}