package com.lulan.shincolle.item;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**custom spawn egg
*  read egg NBTdata to spawn different ship
*  
*  metadata: 0: random small ship, 1: random large ship 2+:specific ship
**/
public class ShipSpawnEgg extends Item {
	
	Random rand;
	
    private IIcon[] iconEgg = new IIcon[3];	//egg icon
	
    private EntityLiving entityToSpawn = null;
    private String entityToSpawnName = null;

    
    public ShipSpawnEgg() {
        super();
        this.setHasSubtypes(true);	//true for enable metadata
        this.setCreativeTab(CreativeTabSC.SC_TAB);
        this.maxStackSize = 1;
        rand = new Random();
    }
  	
    //format: item.MOD_ID:EGG_NAME.name
    //item name for different metadata
  	@Override
  	public String getUnlocalizedName(ItemStack itemstack) {
  		int metaid = itemstack.getItemDamage();		//get metadata
  		
  		switch(metaid) {
  		case 0:	  //small ship
  			return String.format("item."+Reference.MOD_ID+":smallegg");
  		case 1:   //large ship
  			return String.format("item."+Reference.MOD_ID+":largeegg");
  		default:  //spec ship egg
  			return String.format("item."+Reference.MOD_ID+":shipegg"+metaid);
  		}		
  	}
  	
  	//egg icon register
  	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {  		
  		for (int i=0; i<3; i++) {
  	        iconEgg[i] = iconRegister.registerIcon(Reference.MOD_ID + ":shipegg_" + i);
  	    }
	}
  	
  	//egg icon from metadata
  	@Override
  	@SideOnly(Side.CLIENT)
  	public IIcon getIconFromDamage(int meta) {
  	    if (meta > 2) meta = 2;		//(meta>=2) -> debug egg
  	    
  	    return iconEgg[meta];
  	}
  	
  	//for list all same id items
  	@Override
  	public void getSubItems(Item item, CreativeTabs tab, List list) {
  		list.add(new ItemStack(item, 1, 0));
  		list.add(new ItemStack(item, 1, 1));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerI+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerRO+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerHA+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerNI+2));
  		list.add(new ItemStack(item, 1, ID.Ship.HeavyCruiserRI+2));
  		list.add(new ItemStack(item, 1, ID.Ship.HeavyCruiserNE+2));
  		list.add(new ItemStack(item, 1, ID.Ship.CarrierWO+2));
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipTA+2));
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipRE+2));
  		list.add(new ItemStack(item, 1, ID.Ship.TransportWA+2));
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineKA+2));
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineYO+2));
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineSO+2));
  		list.add(new ItemStack(item, 1, ID.Ship.AirfieldHime+2));
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipHime+2));
  		list.add(new ItemStack(item, 1, ID.Ship.HarbourHime+2));
  		list.add(new ItemStack(item, 1, ID.Ship.NorthernHime+2));
  		list.add(new ItemStack(item, 1, ID.Ship.CarrierWD+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerAkatsuki+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerAkatsuki+2002));	//mob entity
//  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerHibiki+2));
//  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerHibiki+2002));		//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerIkazuchi+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerIkazuchi+2002));	//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerInazuma+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerInazuma+2002));	//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerShimakaze+2));
  		list.add(new ItemStack(item, 1, ID.Ship.DestroyerShimakaze+2002));	//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipNagato+2));
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipNagato+2002));	//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipYamato+2));
  		list.add(new ItemStack(item, 1, ID.Ship.BattleshipYamato+2002));	//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineU511+2));
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineU511+2002));		//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineRo500+2));
  		list.add(new ItemStack(item, 1, ID.Ship.SubmarineRo500+2002));		//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.CarrierKaga+2));
  		list.add(new ItemStack(item, 1, ID.Ship.CarrierKaga+2002));			//mob entity
  		list.add(new ItemStack(item, 1, ID.Ship.CarrierAkagi+2));
  		list.add(new ItemStack(item, 1, ID.Ship.CarrierAkagi+2002));		//mob entity
  		
  	}
  	
  	/** VANILLA SPAWN METHOD edited by Jabelar
     * Spawns the creature specified by the egg's type in the location specified by 
     * the last three parameters.
     * Parameters: world, metadata, x, y, z
     */
  	private Entity spawnEntity(EntityPlayer player, ItemStack item, double parX, double parY, double parZ, boolean checkPlayer) {   	
        int entityType = 0;
  		
        //server side
    	if(player != null && !player.worldObj.isRemote) {
    		//check player is real player
    		if(checkPlayer) {
    			int uid = EntityHelper.getPlayerUID(player);
        		if(uid <= 0) return null;
    		}
    		
			entityType = ShipCalc.rollShipType(item);
  			entityToSpawnName = ShipCalc.getEntityToSpawnName(entityType);
            LogHelper.info("DEBUG : spawn entity: "+entityToSpawnName);
            
            if(EntityList.stringToClassMapping.containsKey(entityToSpawnName)) {
                entityToSpawn = (EntityLiving) EntityList.createEntityByName(entityToSpawnName, player.worldObj);
                entityToSpawn.setLocationAndAngles(parX, parY, parZ, MathHelper.wrapAngleTo180_float(player.worldObj.rand.nextFloat()* 360.0F), 0.0F);
                player.worldObj.spawnEntityInWorld(entityToSpawn);
                entityToSpawn.onSpawnWithEgg((IEntityLivingData)null);	//for vanilla random spawn, disable
                entityToSpawn.playLivingSound();
            }
            else {
                LogHelper.info("Entity not found "+entityToSpawnName);	//debug
            }
        }
        
        return entityToSpawn;
    }
  	
  	/**CALC ENTITY RANDOM BONUS ATTRIBUTE
  	 * calc materials amount and random gen the bonus attributes
	 * @parm spawn egg item, player, entity
	 */
  	private void initEntityAttribute(ItemStack itemstack, EntityPlayer player, BasicEntityShip entity) {
  		LogHelper.info("DEBUG : init ship states");
  		//set init AI value and owner
  		entity.setTamed(true);
  		entity.setPathToEntity(null);
  		entity.setEntityTarget(null);
  		
  		//指定ship egg, 讀取nbt來給屬性
		if(itemstack.getItemDamage() > 1) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			
			if(nbt != null) {
				NBTTagList list = nbt.getTagList("ShipInv", 10);
				ExtendShipProps extProps = entity.getExtProps();
				int[] attrs = nbt.getIntArray("Attrs");
				
				try {
					//load inventory
					for(int i = 0; i < list.tagCount(); i++) {
						NBTTagCompound item = list.getCompoundTagAt(i);
						byte sid = item.getByte("Slot");

						if(sid >= 0 && sid < extProps.slots.length) {
							extProps.slots[sid] = ItemStack.loadItemStackFromNBT(item);
						}
					}
				}
				catch(Exception e) {
					LogHelper.info("EXCEPTION : init ship inventory fail: "+e);
				}
				
				try {
					//load bonus point
					entity.setBonusPoint(ID.HP, (byte)attrs[1]);
					entity.setBonusPoint(ID.ATK, (byte)attrs[2]);
					entity.setBonusPoint(ID.DEF, (byte)attrs[3]);
					entity.setBonusPoint(ID.SPD, (byte)attrs[4]);
					entity.setBonusPoint(ID.MOV, (byte)attrs[5]);
					entity.setBonusPoint(ID.HIT, (byte)attrs[6]);
					entity.setEntityFlagI(ID.F.IsMarried, attrs[7]);

					//load ship level
					entity.setShipLevel(attrs[0], true);
				}
				catch(Exception e) {
					LogHelper.info("EXCEPTION : init ship attrs fail: "+e);
				}
				
				//set custom name
				String customname = nbt.getString("customname");
				
				if(customname != null && customname.length() > 0) {
					entity.setNameTag(customname);
				}
				
				/** OWNER SETTING
		    	 *  1. check player UID first (after rv.22)
		    	 *  2. if (1) fail, check player UUID string (before rv.22)
		    	 */
				
				/** set owner by player's UUID (before rv.22) */
				String ownerid = nbt.getString("owner");
				
				if(ownerid != null && ownerid.length() > 5) {
					entity.func_152115_b(ownerid);
				}
				
				/** set owner by player's UID (after rv.22) */
				int pid = nbt.getInteger("PlayerID");	//player uid
				int sid = nbt.getInteger("ShipID");		//ship uid
				
				if(pid > 0) {
					entity.setStateMinor(ID.M.PlayerUID, pid);
				}
				
				if(sid > 0) {
					entity.setStateMinor(ID.M.ShipUID, sid);
				}

				EntityHelper.setPetPlayerUUID(pid, entity);	//set owner uuid
			}
			else {
				entity.setShipLevel(1, true);
				
				//set owner
				EntityHelper.setPetPlayerUUID(player.getUniqueID().toString(), entity);
				EntityHelper.setPetPlayerUID(player, entity);
			}
		}
		//非指定ship egg, 則隨機骰屬性
		else {
			LogHelper.info("DEBUG : new spawn egg (random)");
			
			//set owner
			EntityHelper.setPetPlayerUUID(player.getUniqueID().toString(), entity);
			EntityHelper.setPetPlayerUID(player, entity);
	  		
	  		//init bonus point to zero
	  		entity.setBonusPoint(ID.HP, (byte)0);
	  		entity.setBonusPoint(ID.ATK, (byte)0);
	  		entity.setBonusPoint(ID.DEF, (byte)0);
	  		entity.setBonusPoint(ID.SPD, (byte)0);
	  		entity.setBonusPoint(ID.MOV, (byte)0);
	  		entity.setBonusPoint(ID.HIT, (byte)0);
	  		
	  		//calc ship attribute and save to nbt: hp atk def ...
	  		entity.setShipLevel(1, true);
		}	
  	}
  	
    /** VANILLA SPAWN EGG onItemRightClick event (use item to air)
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        
    	//client side
    	if(world.isRemote) {
            return itemstack;
        }
    	//server side
        else {
            MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null) {
                return itemstack;
            }
            else {
                if(movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if(!world.canMineBlock(player, i, j, k)) {
                        return itemstack;
                    }

                    if(!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemstack)) {
                        return itemstack;
                    }

                	//if creative mode = item not consume
                    if(!player.capabilities.isCreativeMode) {
                    	//cost exp if use specific egg
                        if(itemstack.getItemDamage() > 1 && itemstack.hasTagCompound()) {
                        	NBTTagCompound nbt = itemstack.getTagCompound();
                        	int costLevel = nbt.getIntArray("Attrs")[0] / 3;
                        	
                        	if(player.experienceLevel < costLevel) {
                        		player.addChatMessage(new ChatComponentText("LEVEL is too LOW!"));
                        		return itemstack;
                        	}
                        	else {
                        		player.addExperienceLevel(-costLevel);
                        	}
                        }
                        	
                        //item -1
                        --itemstack.stackSize;
                    }
                    
                    //spawn entity in front of player (1 block)
                    if(itemstack.getItemDamage() > 2000) {	//BOSS egg
                    	LogHelper.info("DEBUG : use boss egg");
                    	spawnEntity(player, itemstack, i, j+1D, k, false);
                    }
                    else {									//normal egg
                    	LogHelper.info("DEBUG : use normal egg");
                    	BasicEntityShip entity = (BasicEntityShip) spawnEntity(player, itemstack, i, j+1D, k, true);

                        if(entity != null) {
                        	//for egg with nameTag
                            if(itemstack.hasDisplayName()) {
                                entity.setCustomNameTag(itemstack.getDisplayName());    
                            }
                            
                        	//calc bonus point, set custom name and owner name
                        	initEntityAttribute(itemstack, player, entity);
                        	
                        	//send sync packet
                        	entity.sendSyncPacketAllValue();
                        }
                    }//end spawn entity
                }//end get position

                return itemstack;
            }//end else
        }
    }
  	
    //display egg information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
    	int[] material = new int[4];

    	if(itemstack.hasTagCompound()) { 	//正常製造egg, 會有四個材料tag	
    		NBTTagCompound nbt = itemstack.getTagCompound();

    		if(nbt.hasKey("Attrs")) {
    			list.add(EnumChatFormatting.AQUA + "" + I18n.format("gui.shincolle:eggText") + " " + (nbt.getIntArray("Attrs")[0]/3));
    			list.add(EnumChatFormatting.WHITE + "" + nbt.getString("customname"));
    			list.add(EnumChatFormatting.RED + "" + nbt.getString("ownername"));
    		}
    		else {
    			material[0] = itemstack.stackTagCompound.getInteger("Grudge");
        		material[1] = itemstack.stackTagCompound.getInteger("Abyssium");
        		material[2] = itemstack.stackTagCompound.getInteger("Ammo");
        		material[3] = itemstack.stackTagCompound.getInteger("Polymetal");
        		
        		list.add(EnumChatFormatting.WHITE + "" + material[0] + " " + I18n.format("item.shincolle:Grudge.name"));
                list.add(EnumChatFormatting.RED + "" + material[1] + " " + I18n.format("item.shincolle:AbyssMetal.name"));
                list.add(EnumChatFormatting.GREEN + "" + material[2] + " " + I18n.format("item.shincolle:Ammo.name"));
                list.add(EnumChatFormatting.AQUA + "" + material[3] + " " + I18n.format("item.shincolle:AbyssMetal1.name"));
    		}
        }
    }
    
   
}
