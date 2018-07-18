package com.lulan.shincolle.item;

import javax.annotation.Nullable;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.CommonProxy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class BucketRepair extends BasicItem
{
	
	private static final String NAME = "BucketRepair";
	
	
	public BucketRepair()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(16);
	}
	
	//start use item
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		ItemStack stack = player.getHeldItem(hand);
		
		if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill && hand == EnumHand.MAIN_HAND)
        {
            player.setActiveHand(hand);
            return new ActionResult(EnumActionResult.SUCCESS, stack);
        }
        else
        {
            return new ActionResult(EnumActionResult.PASS, stack);
        }
    }
	
	//item use animate
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }
	
	//item use duration
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
    {
        if (CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
        {
        	return 24;
        }
        else
        {
        	return 0;
        }
    }
	
	//item use result
	@Override
	@Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase host)
    {
		if (host instanceof EntityPlayer && world != null && !world.isRemote &&
			CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
		{
			EntityPlayer player = (EntityPlayer) host;
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null && capa.morphEntity instanceof BasicEntityShip)
			{
				BasicEntityShip ship = (BasicEntityShip) capa.morphEntity;
				
				if (player.getHealth() < player.getMaxHealth())
				{
					//item-1 in non-creative mode
					if (!player.capabilities.isCreativeMode)
					{
						stack.grow(-1);
		            }
					
					//1 bucket = 10% hp
	            	player.heal(player.getMaxHealth() * 0.1F + 2F);
		            
		            //airplane++
		            if (ship instanceof BasicEntityShipCV)
		            {
		            	BasicEntityShipCV ship2 = (BasicEntityShipCV) ship;
		            	ship2.setNumAircraftLight(ship2.getNumAircraftLight() + 1);
		            	ship2.setNumAircraftHeavy(ship2.getNumAircraftHeavy() + 1);
		            }
		        }
			}
		}
		
        return stack;
    }
	

}