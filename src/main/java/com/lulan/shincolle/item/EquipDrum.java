package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.EnumEquipEffectSP;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EnchantHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0: item drum
 *    1: fluid drum
 *    2: EU drum (battery)
 *    
 *    drum state in BasicEntityShip: saved in StateMinor[ID.M.DrumState]
 *    
 *    fluid transport:
 *    trans rate (mb/t) = 20 + ShipLV * 0.1 * (BaseRate + EnchantRate * #Enchant)
 *      
 *    EU transport:
 *    trans rate (EU/t) = ShipLV * (BaseRate + EnchantRate * #Enchant)  
 *    
 */
public class EquipDrum extends BasicEquip
{
	
	private static final String NAME = "EquipDrum";
	
	
	public EquipDrum()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHasSubtypes(true);
        
        GameRegistry.register(this);
	}
	
	@Override
	public int getTypes()
	{
		return 3;
	}
	
	@Override
	public int getIconTypes()
	{
		return 3;
	}
	
	@Override
	public EnumEquipEffectSP getSpecialEffect(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		
		switch (meta)
		{
		case 1:		//liquid tank
			return EnumEquipEffectSP.DRUM_LIQUID;
		case 2:		//EU storage
			return EnumEquipEffectSP.DRUM_EU;
		default:	//item storage
			return EnumEquipEffectSP.DRUM;
		}
	}
	
	@Override
	public int getIconFromDamage(int meta)
	{
		switch(meta)
		{
		case 0:
		case 1:
		case 2:
			return meta;
		default:
			return 0;
		}
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
		case 1:
		case 2:
			return ID.EquipType.DRUM_LO;
		default:
			return 0;
		}
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.DRUM_LO:  //120
			return new int[] {itemRand.nextInt(4) + 5,
			  		  		  itemRand.nextInt(5) + 9,
			  		  		  itemRand.nextInt(4) + 4,
			  		  		  itemRand.nextInt(3) + 3};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		switch (stack.getItemDamage())
		{
		case 1:
		{
			list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:drum1"));
			
			//show fluid rate
			int num = EnchantHelper.calcEnchantNumber(stack) * ConfigHandler.drumLiquid[1];
			if (num > 0) list.add(TextFormatting.AQUA + I18n.format("gui.shincolle:equip.rateliq") + " " + num + " mB/t");
		}
		break;
		case 2:
		{
			if (CommonProxy.activeIC2) list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:drum2a"));
			else list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:drum2b"));
			
			//show EU rate
			int num = EnchantHelper.calcEnchantNumber(stack) * ConfigHandler.drumEU[1];
			if (num > 0) list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:equip.rateeu") + " " + num + " EU/t");
		}
		break;
		default:
			list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:drum"));
		break;
		}
		
		super.addInformation(stack, player, list, par4);
	}
	

}



