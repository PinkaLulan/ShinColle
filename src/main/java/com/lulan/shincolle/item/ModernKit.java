package com.lulan.shincolle.item;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.InteractHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ModernKit extends BasicItem
{
	
	private static final String NAME = "ModernKit";
	
	public ModernKit()
	{
		super();
		this.setTranslationKey(NAME);
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, World world, List list, ITooltipFlag par4)
    {  	
    	list.add(TextFormatting.GOLD + I18n.format("gui.shincolle:modernkit"));
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
        	return 80;
        }
        else
        {
        	return 0;
        }
    }
    
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase host)
    {
		if (host instanceof EntityPlayer && world != null && !world.isRemote &&
			CommonProxy.activeMetamorph && ConfigHandler.enableMetamorphSkill)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) host);
			
			if (capa != null && capa.morphEntity instanceof BasicEntityShip)
			{
				InteractHelper.interactModernKit((BasicEntityShip) capa.morphEntity, (EntityPlayer) host, stack);
			}
		}
		
        return stack;
    }
	
    
}