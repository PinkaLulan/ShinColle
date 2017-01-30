package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.Interface(iface = "baubles.api.IBauble", modid = Reference.MOD_ID_Baubles)
public class MarriageRing extends BasicItem implements IBauble
{
	
	private static final String NAME = "MarriageRing";
	
	
	public MarriageRing()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
        
        GameRegistry.register(this);
	}
	
	//activate or deactivate ring
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{	//right click to launch	
		if (!world.isRemote)
		{
			//change ring state
			if  (!stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setBoolean("isActive", false);
			}
			
			boolean isActive = stack.getTagCompound().getBoolean("isActive");
			boolean invActive = !isActive;
			stack.getTagCompound().setBoolean("isActive", invActive);
			
			//change player state
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
			
			if (capa != null)
			{
				capa.setRingActive(invActive);
				
				//disable fly
				if (!capa.isRingActive() && !player.capabilities.isCreativeMode && capa.isRingFlying())
				{
					player.capabilities.isFlying = false;
					capa.setRingFlying(false);
				}
				
				//sync ring state to client
				capa.sendSyncPacket(0);
			}
		}
		
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
	{
		if (item.hasTagCompound())
		{
			return item.getTagCompound().getBoolean("isActive");
		}
		
        return false;
    }
	
	/**
	 * ring ability:
	 * ConfigHandler.ringAbility[]: 0:
	 */
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean inUse)
	{
		//BOTH SIDE
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer owner = (EntityPlayer) entity;
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(owner);
			
			//ring effects
			if (capa != null)
			{
				//water breathing, passive
				if (ConfigHandler.ringAbility[0] >= 0 && capa.getMarriageNum() >= ConfigHandler.ringAbility[0] && (owner.ticksExisted & 127) == 0)
				{
		            if (owner.getAir() < 300)
		            {
		            	owner.setAir(300);
		            }
				}
				
				//fly mode in water, active
				if (ConfigHandler.ringAbility[1] >= 0 && capa.getMarriageNum() >= ConfigHandler.ringAbility[1])
				{
					if (EntityHelper.checkEntityIsInLiquid(owner))
					{
						//not flying and ring is active
						if (!owner.capabilities.isFlying && capa.hasRing() && capa.isRingActive())
						{
							owner.capabilities.isFlying = true;
							capa.setRingFlying(true);
						}
					}
					//cancel flying when leave water
					else
					{
						if (capa.isRingFlying() && !owner.capabilities.isCreativeMode && owner.capabilities.isFlying)
						{
							owner.capabilities.isFlying = false;
							capa.setRingFlying(false);
						}
					}
				}
			}//end extPros != null
		}//end entity = player
	}
	
	//show ability text, this is CLIENT side
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{  		
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
    		list.add(TextFormatting.AQUA + I18n.format("gui.shincolle:ringText") + " " + capa.getMarriageNum());
    	}
	}

	@Override
	@Optional.Method(modid = Reference.MOD_ID_Baubles)
	public BaubleType getBaubleType(ItemStack arg0)
	{
		return BaubleType.RING;
	}
	
	@Override
	@Optional.Method(modid = Reference.MOD_ID_Baubles)
	public void onWornTick(ItemStack stack, EntityLivingBase player)
	{
		this.onUpdate(stack, player.world, player, 0, true);
	}
	
	
}
