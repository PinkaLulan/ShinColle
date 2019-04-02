package com.lulan.shincolle.item;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.InteractHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


public class CombatRation extends BasicItem implements IShipCombatRation
{
	
	private static final String NAME = "CombatRation";
	
	
	public CombatRation()
	{
		super();
		this.setTranslationKey(NAME);
		this.setMaxStackSize(16);
        this.setHasSubtypes(true);
	}
	
	@Override
	public int getTypes()
	{
		return 6;
	}

	@Override
	public float getFoodValue(int meta)
	{
		switch (meta)
		{
		case 1:
			return 3600F;
		case 2:
			return 1200F;
		case 3:
			return 3900F;
		case 4:
			return 100F;
		case 5:
			return 900F;
		default:
			return 900F;
		}
	}

	@Override
	public float getSaturationValue(int meta)
	{
		switch (meta)
		{
		default:
			return 10F;
		}
	}

	@Override
	public int getSpecialEffect(int meta)
	{
		return 6;
	}

	@Override
	public int getMoraleValue(int meta)
	{
		switch (meta)
		{
		case 1:
			return 1800;
		case 2:
			return 1600;
		case 3:
			return 2000;
		case 4:
			return 3000;
		case 5:
			return 4000;
		default:
			return 1400;
		}
	}
	
	//start use item
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill && hand == EnumHand.MAIN_HAND)
        {
            player.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItemMainhand());
        }
        else
        {
            return new ActionResult(EnumActionResult.FAIL, player.getHeldItem(hand));
        }
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.EAT;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
        {
        	return 60;
        }
        else
        {
        	return 0;
        }
    }
	
	@Override
	@Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase host)
    {
		if (host instanceof EntityPlayer && world != null && !world.isRemote &&
			CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) host);
			
			if (capa != null && capa.morphEntity instanceof BasicEntityShip)
			{
				InteractHelper.interactFeed((BasicEntityShip) capa.morphEntity, (EntityPlayer) host, stack);
			}
		}
		
        return stack;
    }
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean inUse)
    {
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			
			//server side
			if (!world.isRemote)
			{
				if (inUse && (player.ticksExisted & 15) == 0)
				{
					//get nearby ship
		  			List<BasicEntityShip> slist = world.getEntitiesWithinAABB(BasicEntityShip.class, player.getEntityBoundingBox().expand(8D, 6D, 8D));

	              	for (BasicEntityShip s : slist)
	              	{
	              		if (s != null && s.isEntityAlive() && !s.getStateFlag(ID.F.NoFuel) &&
	              			!s.isSitting() && !s.isRiding())
	              		{
              				if (player.getDistanceSq(s) > 4D)
	              			{
              					s.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
	              				s.getShipNavigate().tryMoveToEntityLiving(player, 0.75D);
	              				
	              				if (player.getRNG().nextInt(5) == 0)
		              			{
		              				switch (player.getRNG().nextInt(3))
		            				{
		            				case 1:
		            					s.applyParticleEmotion(9);  //hungry
		            					break;
		            				case 2:
		            					s.applyParticleEmotion(30); //pif
		            					break;
		            				default:
		            					s.applyParticleEmotion(1);  //heart
		            					break;
		            				}
		              			}
	              			}
	              			
	              			s.getLookHelper().setLookPosition(player.posX, player.posY+2D, player.posZ, 50F, 50F);
	              		}
		  			}//end for ship
				}//end inUse
			}//end server side
		}
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag par4)
    {
    	if (!itemstack.isEmpty())
    	{
    		int meta = itemstack.getItemDamage();
    		String str = I18n.format("gui.shincolle:combatration"+meta);
    		String[] strs =  CalcHelper.stringConvNewlineToArray(str);
    		
    		for (String s : strs)
    		{
    			list.add(s);
    		}
    		
    		list.add(TextFormatting.LIGHT_PURPLE+"+"+getMoraleValue(meta)+" "+I18n.format("gui.shincolle:combatration"));
    		list.add(TextFormatting.RED+"+"+(int)getFoodValue(meta)+"~"+(int)getFoodValue(meta)*2+" "+
    				I18n.format("item.shincolle:Grudge.name"));
    	}
    }
	
	
}