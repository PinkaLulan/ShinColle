package com.lulan.shincolle.item;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Facing;
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
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**custom spawn egg
*  read egg NBTdata to spawn different ship
*  metadata:0: all small ship, 1: all large ship 2+:specific ship for debug
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
  		list.add(new ItemStack(item, 1, ID.S_DestroyerI+2));
  		list.add(new ItemStack(item, 1, ID.S_DestroyerRO+2));
  		list.add(new ItemStack(item, 1, ID.S_DestroyerHA+2));
  		list.add(new ItemStack(item, 1, ID.S_DestroyerNI+2));
  		list.add(new ItemStack(item, 1, ID.S_HeavyCruiserRI+2));
  		list.add(new ItemStack(item, 1, ID.S_CarrierWO+2));
  		list.add(new ItemStack(item, 1, ID.S_BattleshipRE+2));
  		list.add(new ItemStack(item, 1, ID.S_DestroyerShimakaze+2));
  		list.add(new ItemStack(item, 1, ID.S_DestroyerShimakaze+202));	//BOSS entity
  	}
  	
  	/** VANILLA SPAWN METHOD edited by Jabelar
     * Spawns the creature specified by the egg's type in the location specified by 
     * the last three parameters.
     * Parameters: world, metadata, x, y, z
     */
  	private Entity spawnEntity(World parWorld, ItemStack item, double parX, double parY, double parZ) {   	
        int entityType = 0;
  		
  		if (!parWorld.isRemote) {	// never spawn entity on client side
			entityType = ShipCalc.rollShipType(item);
  			entityToSpawnName = ShipCalc.getEntityToSpawnName(entityType);
            
            if (EntityList.stringToClassMapping.containsKey(entityToSpawnName)) {
                entityToSpawn = (EntityLiving) EntityList.createEntityByName(entityToSpawnName, parWorld);
                entityToSpawn.setLocationAndAngles(parX, parY, parZ, MathHelper.wrapAngleTo180_float(parWorld.rand.nextFloat()* 360.0F), 0.0F);
                parWorld.spawnEntityInWorld(entityToSpawn);
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
  		//set init AI value and owner
  		entity.setTamed(true);
  		entity.setPathToEntity((PathEntity)null);
  		entity.setAttackTarget((EntityLivingBase)null);
  		entity.func_152115_b(player.getUniqueID().toString());	//set owner uuid
  		entity.setOwnerName(player.getDisplayName());
  		
  		//非指定ship egg, 則隨機骰屬性
		if(itemstack.getItemDamage() > 1) {
			NBTTagCompound nbt = itemstack.getTagCompound();
			
			if(nbt != null) {
				NBTTagList list = nbt.getTagList("ShipInv", 10);
				ExtendShipProps extProps = entity.getExtProps();
				int[] attrs = nbt.getIntArray("Attrs");
				
				//load inventory
				for(int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
					byte sid = item.getByte("Slot");

					if(sid >= 0 && sid < extProps.slots.length) {
						extProps.slots[sid] = ItemStack.loadItemStackFromNBT(item);
					}
				}
				
				//load bonus point
				entity.setShipLevel(attrs[0], false);
				entity.setBonusPoint(ID.HP, (byte)attrs[1]);
				entity.setBonusPoint(ID.ATK, (byte)attrs[2]);
				entity.setBonusPoint(ID.DEF, (byte)attrs[3]);
				entity.setBonusPoint(ID.SPD, (byte)attrs[4]);
				entity.setBonusPoint(ID.MOV, (byte)attrs[5]);
				entity.setBonusPoint(ID.HIT, (byte)attrs[6]);
				entity.setEntityFlagI(ID.F.IsMarried, attrs[7]);
				
				entity.calcShipAttributes(entity.getShipID());
			}
		}
		else {
			//calc HP ATK DEF SPD MOV HIT bonus point
	  		byte[] bonuspoint = new byte[6];	 
	  		bonuspoint = ShipCalc.getBonusPoints(itemstack);
	  		
	  		//set bonus point
	  		entity.setBonusPoint(ID.HP, bonuspoint[ID.HP]);
	  		entity.setBonusPoint(ID.ATK, bonuspoint[ID.ATK]);
	  		entity.setBonusPoint(ID.DEF, bonuspoint[ID.DEF]);
	  		entity.setBonusPoint(ID.SPD, bonuspoint[ID.SPD]);
	  		entity.setBonusPoint(ID.MOV, bonuspoint[ID.MOV]);
	  		entity.setBonusPoint(ID.HIT, bonuspoint[ID.HIT]);
	  		
	  		//calc ship attribute and save to nbt: hp atk def ...
	  		LogHelper.info("DEBUG : spawn egg: set ship attribute");
	  		entity.calcShipAttributes(entity.getShipID());
		}	
  	}
  	
    /** VANILLA SPAWN EGG onItemRightClick event (use item to air)
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        
    	if(world.isRemote) {	//client side
            return itemstack;
        }
        else {						//server side     
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

//                    if(world.getBlock(i, j, k) instanceof BlockLiquid) {
                    	//if creative mode = item not consume
                        if(!player.capabilities.isCreativeMode) {
                        	//cost exp if use specific egg
                            if(itemstack.getItemDamage() > 1 && itemstack.hasTagCompound()) {
                            	NBTTagCompound nbt = itemstack.getTagCompound();
                            	int costLevel = nbt.getIntArray("Attrs")[0] / 3;
                            	
                            	if(player.experienceLevel < costLevel) return itemstack;
                            	else {
                            		player.experienceLevel = player.experienceLevel - costLevel;
                            	}
                            }
                            	
                            //item -1
                            --itemstack.stackSize;
                        }
                        
                        //spawn entity in front of player (1 block)
                        if(itemstack.getItemDamage() > 200) {	//BOSS egg
                        	EntityLivingBase entity = (EntityLivingBase) spawnEntity(world, itemstack, i, j+1D, k);
                        }
                        else {									//normal egg
                        	BasicEntityShip entity = (BasicEntityShip) spawnEntity(world, itemstack, i, j+1D, k);

                            if(entity != null) {
                            	//calc bonus point, set custom name and owner name
                            	initEntityAttribute(itemstack, player, entity);
                         	
                            	//for egg with nameTag
                                if(itemstack.hasDisplayName()) {
                                    entity.setCustomNameTag(itemstack.getDisplayName());    
                                }
                            }
                        }//end spawn entity
//                    }//end position can spawn
                }//end get position

                return itemstack;
            }//end else
        }
    }
  	
    
//  	/** VANILLA SPAWN EGG onItemUse event (use item to block)
//     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
//     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
//     */
//    @Override
//    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
//        
//    	if(world.isRemote) {	//client side
//            return false;
//        }
//        else {					//server side
//        	//if creative mode = item not consume
//            if(!player.capabilities.isCreativeMode) {
//            	//cost exp if use specific egg
//                if(itemstack.getItemDamage() > 1 && itemstack.hasTagCompound()) {
//                	NBTTagCompound nbt = itemstack.getTagCompound();
//                	int costLevel = nbt.getIntArray("Attrs")[0] / 3;
//                	
//                	if(player.experienceLevel < costLevel) return false;
//                	else {
//                		player.experienceLevel = player.experienceLevel - costLevel;
//                	}
//                }
//                	
//                //item -1
//                --itemstack.stackSize;
//            }
//            
//            Block block = world.getBlock(par4, par5, par6);		//get spawn position
//            par4 += Facing.offsetsXForSide[par7];
//            par5 += Facing.offsetsYForSide[par7];
//            par6 += Facing.offsetsZForSide[par7];
//            double d0 = 0.0D;
//
//            if(par7 == 1 && block.getRenderType() == 11) {			//type11 = fence
//                d0 = 0.5D;
//            }
//            
//            //spawn entity in front of player (1 block)
//            if(itemstack.getItemDamage() > 200) {	//BOSS egg
//            	EntityLivingBase entity = (EntityLivingBase) spawnEntity(world, itemstack, par4 + 0.5D, par5 + d0, par6 + 0.5D);
//            }
//            else {									//normal egg
//            	BasicEntityShip entity = (BasicEntityShip) spawnEntity(world, itemstack, par4 + 0.5D, par5 + d0, par6 + 0.5D);
//
//                if(entity != null) {
//                	//calc bonus point, set custom name and owner name
//                	initEntityAttribute(itemstack, player, entity);
//             	
//                	//for egg with nameTag
//                    if(itemstack.hasDisplayName()) {
//                        entity.setCustomNameTag(itemstack.getDisplayName());    
//                    }
//                }
//            }
//
//            return true;
//        }
//    }
     
    //display egg information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
    	int[] material = new int[4];

    	if(itemstack.hasTagCompound()) { 	//正常製造egg, 會有四個材料tag	
    		NBTTagCompound nbt = itemstack.getTagCompound();

    		if(nbt.hasKey("Attrs")) {
    			list.add(EnumChatFormatting.AQUA + "" + I18n.format("gui.shincolle:eggText") + " " + (nbt.getIntArray("Attrs")[0]/3));
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
