package com.lulan.shincolle.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**custom spawn egg
*  read egg NBTdata to spawn different ship
*  
*  metadata: 0: random small ship, 1: random large ship 2+:specific ship
**/
public class ShipSpawnEgg extends BasicItem
{
	
	private static final String NAME = "ShipSpawnEgg";
	private static List<Integer> shipList = new ArrayList<Integer>();

    
    public ShipSpawnEgg()
    {
    	super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        
        GameRegistry.register(this);
        
        //init egg list
        initEggList();
    }
    
    /** IGNORE this method!! */
    public int getTypes()
	{
		return 0;
	}
	
	/** IGNORE this method!! */
	public int getIconTypes()
	{
		return 3;
	}

  	/** 0:small egg  1:large egg  2:custom egg */
	public int getIconFromDamage(int meta)
	{
		return meta > 2 ? 2 : meta;
	}
	
	/** 依照getIconFromDamage設定對應的texture */
	@SideOnly(Side.CLIENT)
	@Override
    public void initModel()
	{
		ModelResourceLocation[] models = new ModelResourceLocation[3];
		
		//宣告並設定textures位置
		for (int i = 0; i < 3; i++)
		{
			models[i] = new ModelResourceLocation(getRegistryName() + String.valueOf(i), "inventory");
		}

		//登錄全部textures
	    ModelBakery.registerItemVariants(this, models);
		
	    //依照各meta值設定各自texture
	    for (int meta : shipList)
		{
	    	if (meta >= 0 && meta < 2)
	    	{
	    		ModelLoader.setCustomModelResourceLocation(this, meta, models[meta]);
	    	}
	    	else
	    	{
	    		ModelLoader.setCustomModelResourceLocation(this, meta, models[2]);
	    	}
		}
    }
  	
    //format: item.MOD_ID:EGG_NAME.name
    //item name for different metadata
  	@Override
  	public String getUnlocalizedName(ItemStack itemstack)
  	{
  		int metaid = itemstack.getItemDamage();		//get metadata
  		
  		switch(metaid)
  		{
  		case 0:	  //small ship
  			return String.format("item." + Reference.MOD_ID + ":smallegg");
  		case 1:   //large ship
  			return String.format("item." + Reference.MOD_ID + ":largeegg");
  		default:  //spec ship egg
  			return String.format("item." + Reference.MOD_ID + ":shipegg" + metaid);
  		}		
  	}
  	
  	/** add egg to list
  	 *  and turn ship id to item meta value
  	 */
  	private void initEggList()
  	{
  		shipList.add(0);  //small egg
  		shipList.add(1);  //large egg
		shipList.add(ID.Ship.DestroyerI+2);
  		shipList.add(ID.Ship.DestroyerRO+2);
  		shipList.add(ID.Ship.DestroyerHA+2);
  		shipList.add(ID.Ship.DestroyerNI+2);
  		shipList.add(ID.Ship.HeavyCruiserRI+2);
  		shipList.add(ID.Ship.HeavyCruiserNE+2);
  		shipList.add(ID.Ship.CarrierWO+2);
  		shipList.add(ID.Ship.BattleshipRU+2);
  		shipList.add(ID.Ship.BattleshipTA+2);
  		shipList.add(ID.Ship.BattleshipRE+2);
  		shipList.add(ID.Ship.TransportWA+2);
  		shipList.add(ID.Ship.SubmarineKA+2);
  		shipList.add(ID.Ship.SubmarineYO+2);
  		shipList.add(ID.Ship.SubmarineSO+2);
  		shipList.add(ID.Ship.CarrierHime+2);
  		shipList.add(ID.Ship.AirfieldHime+2);
  		shipList.add(ID.Ship.BattleshipHime+2);
  		shipList.add(ID.Ship.HarbourHime+2);
  		shipList.add(ID.Ship.NorthernHime+2);
  		shipList.add(ID.Ship.CarrierWD+2);
  		shipList.add(ID.Ship.BattleshipNagato+2);
  		shipList.add(ID.Ship.BattleshipNagato+2002);	//hostile entity
  		shipList.add(ID.Ship.BattleshipYamato+2);
  		shipList.add(ID.Ship.BattleshipYamato+2002);	//hostile entity
  		shipList.add(ID.Ship.CarrierKaga+2);
  		shipList.add(ID.Ship.CarrierKaga+2002);			//hostile entity
  		shipList.add(ID.Ship.CarrierAkagi+2);
  		shipList.add(ID.Ship.CarrierAkagi+2002);		//hostile entity
  		shipList.add(ID.Ship.DestroyerAkatsuki+2);
  		shipList.add(ID.Ship.DestroyerAkatsuki+2002);	//hostile entity
  		shipList.add(ID.Ship.DestroyerHibiki+2);
  		shipList.add(ID.Ship.DestroyerHibiki+2002);		//hostile entity
  		shipList.add(ID.Ship.DestroyerIkazuchi+2);
  		shipList.add(ID.Ship.DestroyerIkazuchi+2002);	//hostile entity
  		shipList.add(ID.Ship.DestroyerInazuma+2);
  		shipList.add(ID.Ship.DestroyerInazuma+2002);	//hostile entity
  		shipList.add(ID.Ship.DestroyerShimakaze+2);
  		shipList.add(ID.Ship.DestroyerShimakaze+2002);	//hostile entity
  		shipList.add(ID.Ship.LightCruiserTenryuu+2);
  		shipList.add(ID.Ship.LightCruiserTenryuu+2002);	//hostile entity
  		shipList.add(ID.Ship.LightCruiserTatsuta+2);
  		shipList.add(ID.Ship.LightCruiserTatsuta+2002);	//hostile entity
  		shipList.add(ID.Ship.SubmarineU511+2);
  		shipList.add(ID.Ship.SubmarineU511+2002);		//hostile entity
  		shipList.add(ID.Ship.SubmarineRo500+2);
  		shipList.add(ID.Ship.SubmarineRo500+2002);		//hostile entity
  	}
  	
  	//for list all same id items
  	@Override
  	public void getSubItems(Item item, CreativeTabs tab, List list)
  	{
  		for (int meta : shipList)
  		{
  			list.add(new ItemStack(item, 1, meta));
  		}
  	}
  	
  	/** VANILLA SPAWN METHOD edited by Jabelar
     * Spawns the creature specified by the egg's type in the location specified by 
     * the last three parameters.
     * Parameters: world, metadata, x, y, z
     */
  	private Entity getSpawnEntity(EntityPlayer player, ItemStack item, BlockPos pos, boolean checkPlayer)
  	{   	
        //server side
    	if (player != null && !player.world.isRemote)
    	{
    		//check player is real player
    		if (checkPlayer)
    		{
    			int uid = EntityHelper.getPlayerUID(player);
        		if (uid <= 0) return null;
    		}
    		
    		//get ship type
    		String entityName = ShipCalc.getEntityToSpawnName(ShipCalc.rollShipType(item));
            LogHelper.debug("DEBUG: ShipSpawnEgg: spawn entity: "+entityName);
            
            if (EntityList.NAME_TO_CLASS.containsKey(entityName))
            {
            	Entity ent = EntityList.createEntityByName(entityName, player.world);
            	ent.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(player.world.rand.nextFloat()* 360F), 0F);
            	
            	return ent;
            }
            else
            {
                LogHelper.debug("DEBUG: ShipSpawnEgg: entity not found: "+entityName);
            }
        }
        
        return null;
    }
  	
  	/**CALC ENTITY RANDOM BONUS ATTRIBUTE
  	 * calc materials amount and random gen the bonus attributes
	 * @parm spawn egg item, player, entity
	 */
  	private void initEntityAttribute(ItemStack stack, EntityPlayer player, BasicEntityShip entity)
  	{
  		LogHelper.debug("DEBUG: init ship states");
  		//set init AI value and owner
  		entity.setTamed(true);
  		entity.setEntityTarget(null);
  		
  		//指定ship egg, 讀取nbt來給屬性
		if (stack.getItemDamage() > 1)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			
			if (nbt != null)
			{
				CapaShipInventory capa = entity.getCapaShipInventory();
				
				//load inventory
		        if (nbt.hasKey(CapaShipInventory.InvName))
		        {
		        	capa.deserializeNBT((NBTTagCompound) nbt.getTag(CapaShipInventory.InvName));
		        }
				
				//load ship attributes
				int[] attrs = nbt.getIntArray("Attrs");
				int[] attrs2 = nbt.getIntArray("Attrs2");
				byte[] flags = nbt.getByteArray("Flags");
				
				try
				{
					if (flags.length >= 15)
					{
						entity.setStateFlag(ID.F.IsMarried, flags[0] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.UseMelee, flags[1] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.UseAmmoLight, flags[2] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.UseAmmoHeavy, flags[3] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.UseAirLight, flags[4] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.UseAirHeavy, flags[5] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.UseRingEffect, flags[6] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.OnSightChase, flags[7] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.PVPFirst, flags[8] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.AntiAir, flags[9] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.AntiSS, flags[10] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.PassiveAI, flags[11] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.TimeKeeper, flags[12] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.PickItem, flags[13] > 0 ? true : false);
				    	entity.setStateFlag(ID.F.ShowHeldItem, flags[14] > 0 ? true : false);
					}
					
					if (attrs2.length >= 7)
					{
						entity.setStateEmotion(ID.S.State, attrs2[0], false);
						entity.setStateEmotion(ID.S.State2, attrs2[1], false);
				    	entity.setStateMinor(ID.M.FollowMin, attrs2[2]);
				    	entity.setStateMinor(ID.M.FollowMax, attrs2[3]);
				    	entity.setStateMinor(ID.M.FleeHP, attrs2[4]);
				    	entity.setStateMinor(ID.M.WpStay, attrs2[5]);
				    	entity.setStateMinor(ID.M.UseCombatRation, attrs2[6]);
					}
					
					if (attrs.length >= 7)
					{
						entity.setBonusPoint(ID.HP, (byte) attrs[1]);
						entity.setBonusPoint(ID.ATK, (byte) attrs[2]);
						entity.setBonusPoint(ID.DEF, (byte) attrs[3]);
						entity.setBonusPoint(ID.SPD, (byte) attrs[4]);
						entity.setBonusPoint(ID.MOV, (byte) attrs[5]);
						entity.setBonusPoint(ID.HIT, (byte) attrs[6]);
				    	
				    	//最後一行為設定等級, 並重新計算所有屬性數值
				    	entity.setShipLevel(attrs[0], true);
					}
				}
				catch (Exception e)
				{
					LogHelper.info("EXCEPTION: init ship attrs fail: "+e);
					e.printStackTrace();
				}
				
				//set custom name
				String customname = nbt.getString("customname");
				
				if (customname != null && customname.length() > 0)
				{
					entity.setNameTag(customname);
				}
				
				/** OWNER SETTING
		    	 *  1. check player UID first (after rv.22)
		    	 *  2. if (1) fail, check player UUID string (before rv.22)
		    	 */
				
				/** set owner by player's UID (after rv.22) */
				int pid = nbt.getInteger("PlayerID");	//player uid
				int sid = nbt.getInteger("ShipID");		//ship uid
				
				if (pid > 0)
				{
					entity.setStateMinor(ID.M.PlayerUID, pid);
				}
				
				if (sid > 0)
				{
					entity.setStateMinor(ID.M.ShipUID, sid);
				}
				
				/** set owner by player's UUID (before rv.22) */
				String ownerid = nbt.getString("owner");
				
				//get owner
				if (ownerid != null && ownerid.length() > 5)
				{
					entity.setOwnerId(UUID.fromString(ownerid));
				}
				//get no owner from uuid string, get owner from pid 
				else
				{
					EntityHelper.setPetPlayerUUID(pid, entity);
				}
			}
			//new ship
			else
			{
				entity.setShipLevel(1, true);
				
				//set owner
				EntityHelper.setPetPlayerUUID(player.getUniqueID(), entity);
				EntityHelper.setPetPlayerUID(player, entity);
			}
		}//end item meta > 1
		//非指定ship egg, 則隨機骰屬性
		else
		{
			LogHelper.debug("DEBUG: new spawn egg (random)");
			
			//set owner
			EntityHelper.setPetPlayerUUID(player.getUniqueID(), entity);
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
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
    	//client side
    	if (world.isRemote)
    	{
            return new ActionResult(EnumActionResult.SUCCESS, stack);
        }
    	//server side
        else
        {
        	//do ray trace
            RayTraceResult hitObj = this.rayTrace(world, player, true);

            //hit air
            if (hitObj == null)
            {
                return new ActionResult(EnumActionResult.PASS, stack);
            }
            //hit object
            else
            {
            	//hit block
                if (hitObj.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                	BlockPos hitPos = hitObj.getBlockPos();

                	//block can't be changed (ex: protected spawn area)
                    if (!world.isBlockModifiable(player, hitPos))
                    {
                        return new ActionResult(EnumActionResult.PASS, stack);
                    }

                    //player can't edit block
                    if(!player.canPlayerEdit(hitPos, hitObj.sideHit, stack))
                    {
                        return new ActionResult(EnumActionResult.PASS, stack);
                    }

                    //itemstack-- & exp-- & spawn entity
                	//if creative mode = item not consume
                    if (!player.capabilities.isCreativeMode)
                    {
                    	//cost exp if use specific egg
                        if (stack.getItemDamage() > 1 && stack.hasTagCompound())
                        {
                        	NBTTagCompound nbt = stack.getTagCompound();
                        	int costLevel = nbt.getIntArray("Attrs")[0] / 3;
                        	
                        	if (player.experienceLevel < costLevel)
                        	{
                        		player.sendMessage(new TextComponentTranslation("chat.shincolle:levelfail"));
                        		return new ActionResult(EnumActionResult.FAIL, stack);
                        	}
                        	else
                        	{
                        		player.addExperienceLevel(-costLevel);
                        	}
                        }
                        	
                        //item -1
                        --stack.stackSize;
                    }
                    
                    //spawn entity in front of player (1 block)
                    //if boss egg
                    if (stack.getItemDamage() > 2000)
                    {
                    	LogHelper.debug("DEBUG: use boss egg");
                    	BasicEntityShipHostile ship = (BasicEntityShipHostile) getSpawnEntity(player, stack, hitPos.up(), false);
                        
                    	if (ship != null)
                    	{
                    		ship.initAttrs(player.getRNG().nextInt(4));
                        	player.world.spawnEntity(ship);
                        	ship.playLivingSound();
                    	}
                    }
                    //normal egg
                    else
                    {
                    	LogHelper.debug("DEBUG: use normal egg");
                    	BasicEntityShip ship = (BasicEntityShip) getSpawnEntity(player, stack, hitPos.up(), true);
                        
                        if (ship != null)
                        {
                        	player.world.spawnEntity(ship);
                            ship.playLivingSound();
                            
                        	//for egg with nameTag
                            if (stack.hasDisplayName())
                            {
                            	ship.setNameTag(stack.getDisplayName());    
                            }
                            
                        	//calc bonus point, set custom name and owner name
                        	this.initEntityAttribute(stack, player, ship);
                        	
                        	//send sync packet
                        	ship.sendSyncPacketAllValue();
                        }
                    }//end spawn entity
                }//end get position

                return new ActionResult(EnumActionResult.SUCCESS, stack);
            }//end else
        }
    }
  	
    //display egg information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {
    	int[] material = new int[4];

    	if (itemstack.hasTagCompound())
    	{ 	//正常製造egg, 會有四個材料tag	
    		NBTTagCompound nbt = itemstack.getTagCompound();

    		if (nbt.hasKey("Attrs"))
    		{
    			list.add(TextFormatting.AQUA + "" + I18n.format("gui.shincolle:eggText") + " " + (nbt.getIntArray("Attrs")[0]/3));
    			list.add(TextFormatting.WHITE + "" + nbt.getString("customname"));
    			list.add(TextFormatting.RED + "" + nbt.getString("ownername"));
    		}
    		else
    		{
    			material[0] = itemstack.getTagCompound().getInteger("Grudge");
        		material[1] = itemstack.getTagCompound().getInteger("Abyssium");
        		material[2] = itemstack.getTagCompound().getInteger("Ammo");
        		material[3] = itemstack.getTagCompound().getInteger("Polymetal");
        		
        		list.add(TextFormatting.WHITE + "" + material[0] + " " + I18n.format("item.shincolle:Grudge.name"));
                list.add(TextFormatting.RED + "" + material[1] + " " + I18n.format("item.shincolle:AbyssMetal.name"));
                list.add(TextFormatting.GREEN + "" + material[2] + " " + I18n.format("item.shincolle:Ammo.name"));
                list.add(TextFormatting.AQUA + "" + material[3] + " " + I18n.format("item.shincolle:AbyssMetal1.name"));
    		}
        }
    }
    
    
}
