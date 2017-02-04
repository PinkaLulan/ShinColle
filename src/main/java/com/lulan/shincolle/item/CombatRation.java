package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class CombatRation extends BasicItem implements IShipCombatRation
{
	
	private static final String NAME = "CombatRation";
	
	
	public CombatRation()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(16);
        this.setHasSubtypes(true);
        
        GameRegistry.register(this);
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
              				if (player.getDistanceSqToEntity(s) > 4D)
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
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> list, boolean par4)
    {
    	if (itemstack != null)
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
