package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Enums.EnumEquipEffectSP;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EnchantHelper;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

abstract public class BasicEquip extends BasicItem implements IShipResourceItem
{

	
	public BasicEquip()
	{
		super();
		this.setMaxStackSize(1);
	}
	
	/** get equip type ID (ID.EquipType) from meta value */
	abstract public int getEquipTypeIDFromMeta(int meta);
	
	/** get equip key for EquipMap
	 *  EquipID = EquipTypeID + EquipSubID(meta value) * 100
	 */
	public int getEquipID(int meta)
	{
		return getEquipTypeIDFromMeta(meta) + meta * 100;
	}
	
	/** texture (icon) types */
	public int getIconTypes()
	{
		return 1;
	}
	
	/** 設定meta值對應的texture id
	 *  IN: meta, OUT: texture id
	 */
	public int getIconFromDamage(int meta)
	{
		return 0;
	}
	
	/** equip special effect
	 *  used in EquipCalc class
	 *  
	 *  ex: drum, searchlight, compass ...etc
	 */
	public EnumEquipEffectSP getSpecialEffect(ItemStack stack)
	{
		return EnumEquipEffectSP.NONE;
	}
	
	@Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
        return 9;
    }
	
	/** 依照getIconFromDamage設定對應的texture */
	@SideOnly(Side.CLIENT)
	@Override
    public void initModel()
	{
		//在inventory(背包介面)中要顯示的model, 依照meta value對應不同model
		//meta值有多個, 則依照meta值設定使用的texture
		if (getHasSubtypes())
		{
    		ModelResourceLocation[] models = new ModelResourceLocation[getIconTypes()];
    		
    		//宣告並設定textures位置
    		for (int i = 0; i < getIconTypes(); i++)
    		{
    			models[i] = new ModelResourceLocation(getRegistryName() + String.valueOf(i), "inventory");
    		}

    		//登錄全部textures
    	    ModelBakery.registerItemVariants(this, models);
    		
    	    //依照各meta值設定各自texture
    	    for (int i = 0; i < getTypes(); i++)
    		{
    	    	 ModelLoader.setCustomModelResourceLocation(this, i, models[getIconFromDamage(i)]);
    		}
		}
		//meta值只有一個 = texture只有一個
		else
		{
	        ModelLoader.setCustomModelResourceLocation(
	        		this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		}

    }
	
	//display equip information
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4)
    {
    	if (stack != null && stack.getItem() != null)
    	{
    		float[] itemRaw = Values.EquipMap.get(((BasicEquip)stack.getItem()).getEquipID(stack.getItemDamage()));
    		
    		if (itemRaw != null)
        	{
    			//apply enchant effect
    			float[] itemEnch = EnchantHelper.calcEnchantEffect(stack);
    			float[] itemNewStat = EquipCalc.calcEquipStatWithEnchant(itemRaw, itemEnch);
    			
    			//draw stat value
        		if (itemNewStat[ID.EquipFinal.HP] != 0F) list.add(TextFormatting.RED + String.format("%.1f" ,itemNewStat[ID.EquipFinal.HP] * (float)ConfigHandler.scaleShip[ID.HP])+ " " + I18n.format("gui.shincolle:hp"));
        		if (itemNewStat[ID.EquipFinal.ATK_L] != 0F) list.add(TextFormatting.RED + String.format("%.1f" ,itemNewStat[ID.EquipFinal.ATK_L] * (float)ConfigHandler.scaleShip[ID.ATK])+ " " + I18n.format("gui.shincolle:firepower1"));
        		if (itemNewStat[ID.EquipFinal.ATK_H] != 0F) list.add(TextFormatting.GREEN + String.format("%.1f" ,itemNewStat[ID.EquipFinal.ATK_H] * (float)ConfigHandler.scaleShip[ID.ATK])+ " " + I18n.format("gui.shincolle:torpedo"));
        		if (itemNewStat[ID.EquipFinal.ATK_AL] != 0F) list.add(TextFormatting.RED + String.format("%.1f" ,itemNewStat[ID.EquipFinal.ATK_AL] * (float)ConfigHandler.scaleShip[ID.ATK])+ " " + I18n.format("gui.shincolle:airfirepower"));
        		if (itemNewStat[ID.EquipFinal.ATK_AH] != 0F) list.add(TextFormatting.GREEN + String.format("%.1f" ,itemNewStat[ID.EquipFinal.ATK_AH] * (float)ConfigHandler.scaleShip[ID.ATK])+ " " + I18n.format("gui.shincolle:airtorpedo"));
        		if (itemNewStat[ID.EquipFinal.DEF] != 0F) list.add(TextFormatting.WHITE + String.format("%.1f" ,itemNewStat[ID.EquipFinal.DEF] * (float)ConfigHandler.scaleShip[ID.DEF])+ "% " + I18n.format("gui.shincolle:armor"));
        		if (itemNewStat[ID.EquipFinal.SPD] != 0F) list.add(TextFormatting.WHITE + String.format("%.2f" ,itemNewStat[ID.EquipFinal.SPD] * (float)ConfigHandler.scaleShip[ID.SPD])+ " " + I18n.format("gui.shincolle:attackspeed"));
        		if (itemNewStat[ID.EquipFinal.MOV] != 0F) list.add(TextFormatting.GRAY + String.format("%.2f" ,itemNewStat[ID.EquipFinal.MOV] * (float)ConfigHandler.scaleShip[ID.MOV])+ " " + I18n.format("gui.shincolle:movespeed"));
        		if (itemNewStat[ID.EquipFinal.HIT] != 0F) list.add(TextFormatting.LIGHT_PURPLE + String.format("%.1f" ,itemNewStat[ID.EquipFinal.HIT] * (float)ConfigHandler.scaleShip[ID.HIT])+ " " + I18n.format("gui.shincolle:range"));
        		if (itemNewStat[ID.EquipFinal.CRI] != 0F) list.add(TextFormatting.AQUA + String.format("%.0f" ,itemNewStat[ID.EquipFinal.CRI]*100F)+ "% " + I18n.format("gui.shincolle:critical"));
        		if (itemNewStat[ID.EquipFinal.DHIT] != 0F) list.add(TextFormatting.YELLOW + String.format("%.0f" ,itemNewStat[ID.EquipFinal.DHIT]*100F)+ "% " + I18n.format("gui.shincolle:doublehit"));
        		if (itemNewStat[ID.EquipFinal.THIT] != 0F) list.add(TextFormatting.GOLD + String.format("%.0f" ,itemNewStat[ID.EquipFinal.THIT]*100F)+ "% " + I18n.format("gui.shincolle:triplehit"));
        		if (itemNewStat[ID.EquipFinal.MISS] != 0F) list.add(TextFormatting.RED + String.format("%.0f" ,itemNewStat[ID.EquipFinal.MISS]*100F)+ "% " + I18n.format("gui.shincolle:missreduce"));
        		if (itemNewStat[ID.EquipFinal.DODGE] != 0F) list.add(TextFormatting.GOLD + String.format("%.0f" ,itemNewStat[ID.EquipFinal.DODGE])+ "% " + I18n.format("gui.shincolle:dodge"));
        		if (itemNewStat[ID.EquipFinal.AA] != 0F) list.add(TextFormatting.YELLOW + String.format("%.1f" ,itemNewStat[ID.EquipFinal.AA])+ " " + I18n.format("gui.shincolle:antiair"));
        		if (itemNewStat[ID.EquipFinal.ASM] != 0F) list.add(TextFormatting.AQUA + String.format("%.1f" ,itemNewStat[ID.EquipFinal.ASM])+ " " + I18n.format("gui.shincolle:antiss"));
        		
        		//draw special effect
        		if (itemNewStat[ID.EquipFinal.XP] != 0F) list.add(TextFormatting.GREEN + I18n.format("gui.shincolle:equip.xp") + " " + String.format("%.0f" ,itemNewStat[ID.EquipFinal.XP]*100F) + "%");
    			if (itemNewStat[ID.EquipFinal.GRUDGE] != 0F) list.add(TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.grudge") + " " + String.format("%.0f" ,itemNewStat[ID.EquipFinal.GRUDGE]*100F) + "%");
				if (itemNewStat[ID.EquipFinal.AMMO] != 0F) list.add(TextFormatting.DARK_AQUA + I18n.format("gui.shincolle:equip.ammo") + " " + String.format("%.0f" ,itemNewStat[ID.EquipFinal.AMMO]*100F) + "%");
				if (itemNewStat[ID.EquipFinal.HPRES] != 0F) list.add(TextFormatting.DARK_GREEN + I18n.format("gui.shincolle:equip.hpres") + " " + String.format("%.0f" ,itemNewStat[ID.EquipFinal.HPRES]*100F) + "%");
        		
        		//draw equip and enchant type
				String drawstr = I18n.format("gui.shincolle:equip.enchtype") + " ";
				drawstr += itemRaw[ID.EquipData.ENCH_TYPE] == 1F ?
						TextFormatting.RED + I18n.format("gui.shincolle:equip.enchtype1") :
						itemRaw[ID.EquipData.ENCH_TYPE] == 2F ?
						TextFormatting.AQUA + I18n.format("gui.shincolle:equip.enchtype0") :
						itemRaw[ID.EquipData.ENCH_TYPE] == 3F ?
						TextFormatting.GRAY + I18n.format("gui.shincolle:equip.enchtype2") : "";
				drawstr += itemRaw[ID.EquipData.EQUIP_TYPE] == 1F ?
						"  " + TextFormatting.DARK_RED + I18n.format("gui.shincolle:notforcarrier") :
						itemRaw[ID.EquipData.EQUIP_TYPE] == 3F ?
						"  " + TextFormatting.DARK_AQUA + I18n.format("gui.shincolle:carrieronly") : "";

				list.add(drawstr);
        		
        		//draw construction info
        		if (itemRaw[ID.EquipData.DEVELOP_NUM] > 400F)
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("tile.shincolle:BlockLargeShipyard.name"));
        		}
        		else
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("tile.shincolle:BlockSmallShipyard.name"));
        		}
        		
        		String matname = null;
        		switch ((int)itemRaw[ID.EquipData.DEVELOP_MAT])
        		{
        		case 1:
        			matname = I18n.format("item.shincolle:AbyssMetal.name");
        			break;
        		case 2:
        			matname = I18n.format("item.shincolle:Ammo.name");
        			break;
        		case 3:
        			matname = I18n.format("item.shincolle:AbyssMetal1.name");
        			break;
        		default:
        			matname = I18n.format("item.shincolle:Grudge.name");
        			break;
        		}
        		
        		drawstr = TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.matstype") +
        				  TextFormatting.GRAY + " (" + matname + ") " +
        				  String.format("%.0f", itemRaw[ID.EquipData.DEVELOP_NUM]) + "  " +
        				  TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.matsrarelevel") +
        				  TextFormatting.GRAY + " " + String.format("%.0f", itemRaw[ID.EquipData.RARE_MEAN]);
        		
        		list.add(drawstr);
        	}//end get item stat
    	}//end get item
    	
    }
    
    
}
