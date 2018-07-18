package com.lulan.shincolle.item;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Enums.EnumEquipEffectSP;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EnchantHelper;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
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
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
    	if (stack != null && stack.getItem() != null)
    	{
    		//check tooltip flags
            if (stack.hasTagCompound())
            {
            	NBTTagCompound nbt = stack.getTagCompound();
            	
            	//has no flag, add new flag
            	if (!nbt.hasKey("HideFlags", 99))
            	{
            		nbt.setInteger("HideFlags", 1);
            	}
            	
            	//has flags, change flag by player key input
            	int hideflag = nbt.getInteger("HideFlags");
            	
            	if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) ||
            		Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))	//if press CTRL, show enchantments
            	{
            		hideflag = 0;
            	}
            	else											//hide enchantments by default
            	{
            		hideflag = 1;
            	}
            	
            	nbt.setInteger("HideFlags", hideflag);
            }
    		
            float[] main = Values.EquipAttrsMain.get(((BasicEquip)stack.getItem()).getEquipID(stack.getItemDamage()));
            int[] misc = Values.EquipAttrsMisc.get(((BasicEquip)stack.getItem()).getEquipID(stack.getItemDamage()));
    		
            if (main != null && misc != null)
        	{
    			//apply enchant effect
    			main = EquipCalc.calcEquipStatWithEnchant(misc[ID.EquipMisc.EQUIP_TYPE], main, EnchantHelper.calcEnchantEffect(stack));
    			
    			//draw stat value
        		if (main[ID.Attrs.HP] != 0F) list.add(TextFormatting.RED + String.format("%.1f", main[ID.Attrs.HP] * (float)ConfigHandler.scaleShip[ID.AttrsBase.HP])+ " " + I18n.format("gui.shincolle:hp"));
        		if (main[ID.Attrs.ATK_L] != 0F) list.add(TextFormatting.RED + String.format("%.1f", main[ID.Attrs.ATK_L] * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK])+ " " + I18n.format("gui.shincolle:firepower1"));
        		if (main[ID.Attrs.ATK_H] != 0F) list.add(TextFormatting.GREEN + String.format("%.1f", main[ID.Attrs.ATK_H] * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK])+ " " + I18n.format("gui.shincolle:torpedo"));
        		if (main[ID.Attrs.ATK_AL] != 0F) list.add(TextFormatting.RED + String.format("%.1f", main[ID.Attrs.ATK_AL] * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK])+ " " + I18n.format("gui.shincolle:airfirepower"));
        		if (main[ID.Attrs.ATK_AH] != 0F) list.add(TextFormatting.GREEN + String.format("%.1f", main[ID.Attrs.ATK_AH] * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK])+ " " + I18n.format("gui.shincolle:airtorpedo"));
        		if (main[ID.Attrs.DEF] != 0F) list.add(TextFormatting.WHITE + String.format("%.1f", main[ID.Attrs.DEF] * 100F * (float)ConfigHandler.scaleShip[ID.AttrsBase.DEF])+ "% " + I18n.format("gui.shincolle:armor"));
        		if (main[ID.Attrs.SPD] != 0F) list.add(TextFormatting.WHITE + String.format("%.2f", main[ID.Attrs.SPD] * (float)ConfigHandler.scaleShip[ID.AttrsBase.SPD])+ " " + I18n.format("gui.shincolle:attackspeed"));
        		if (main[ID.Attrs.MOV] != 0F) list.add(TextFormatting.GRAY + String.format("%.2f", main[ID.Attrs.MOV] * (float)ConfigHandler.scaleShip[ID.AttrsBase.MOV])+ " " + I18n.format("gui.shincolle:movespeed"));
        		if (main[ID.Attrs.HIT] != 0F) list.add(TextFormatting.LIGHT_PURPLE + String.format("%.1f", main[ID.Attrs.HIT] * (float)ConfigHandler.scaleShip[ID.AttrsBase.HIT])+ " " + I18n.format("gui.shincolle:range"));
        		if (main[ID.Attrs.CRI] != 0F) list.add(TextFormatting.AQUA + String.format("%.0f", main[ID.Attrs.CRI] * 100F)+ "% " + I18n.format("gui.shincolle:critical"));
        		if (main[ID.Attrs.DHIT] != 0F) list.add(TextFormatting.YELLOW + String.format("%.0f", main[ID.Attrs.DHIT] * 100F)+ "% " + I18n.format("gui.shincolle:doublehit"));
        		if (main[ID.Attrs.THIT] != 0F) list.add(TextFormatting.GOLD + String.format("%.0f", main[ID.Attrs.THIT] * 100F)+ "% " + I18n.format("gui.shincolle:triplehit"));
        		if (main[ID.Attrs.MISS] != 0F) list.add(TextFormatting.RED + String.format("%.0f", main[ID.Attrs.MISS] * 100F)+ "% " + I18n.format("gui.shincolle:missreduce"));
        		if (main[ID.Attrs.DODGE] != 0F) list.add(TextFormatting.GOLD + String.format("%.0f", main[ID.Attrs.DODGE] * 100F)+ "% " + I18n.format("gui.shincolle:dodge"));
        		if (main[ID.Attrs.AA] != 0F) list.add(TextFormatting.YELLOW + String.format("%.1f", main[ID.Attrs.AA])+ " " + I18n.format("gui.shincolle:antiair"));
        		if (main[ID.Attrs.ASM] != 0F) list.add(TextFormatting.AQUA + String.format("%.1f", main[ID.Attrs.ASM])+ " " + I18n.format("gui.shincolle:antiss"));
        		if (main[ID.Attrs.XP] != 0F) list.add(TextFormatting.GREEN + I18n.format("gui.shincolle:equip.xp") + " " + String.format("%.0f", main[ID.Attrs.XP] * 100F) + "%");
    			if (main[ID.Attrs.GRUDGE] != 0F) list.add(TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.grudge") + " " + String.format("%.0f", main[ID.Attrs.GRUDGE] * 100F) + "%");
				if (main[ID.Attrs.AMMO] != 0F) list.add(TextFormatting.DARK_AQUA + I18n.format("gui.shincolle:equip.ammo") + " " + String.format("%.0f", main[ID.Attrs.AMMO] * 100F) + "%");
				if (main[ID.Attrs.HPRES] != 0F) list.add(TextFormatting.DARK_GREEN + I18n.format("gui.shincolle:equip.hpres") + " " + String.format("%.0f", main[ID.Attrs.HPRES] * 100F) + "%");
				if (main[ID.Attrs.KB] != 0F) list.add(TextFormatting.DARK_RED + I18n.format("gui.shincolle:equip.kb") + " " + String.format("%.0f", main[ID.Attrs.KB] * 100F) + "%");
        		
        		//draw equip and enchant type
				String drawstr = I18n.format("gui.shincolle:equip.enchtype") + " ";
				drawstr += misc[ID.EquipMisc.ENCH_TYPE] == 1F ?
						TextFormatting.RED + I18n.format("gui.shincolle:equip.enchtype1") :
							misc[ID.EquipMisc.ENCH_TYPE] == 2F ?
						TextFormatting.AQUA + I18n.format("gui.shincolle:equip.enchtype0") :
							misc[ID.EquipMisc.ENCH_TYPE] == 3F ?
						TextFormatting.GRAY + I18n.format("gui.shincolle:equip.enchtype2") : "";
				drawstr += misc[ID.EquipMisc.EQUIP_TYPE] == 1F ?
						"  " + TextFormatting.DARK_RED + I18n.format("gui.shincolle:notforcarrier") :
							misc[ID.EquipMisc.EQUIP_TYPE] == 3F ?
						"  " + TextFormatting.DARK_AQUA + I18n.format("gui.shincolle:carrieronly") : "";

				list.add(drawstr);
        		
        		//draw construction info
        		if (misc[ID.EquipMisc.DEVELOP_NUM] > 400)
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("tile.shincolle:BlockLargeShipyard.name"));
        		}
        		else
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("tile.shincolle:BlockSmallShipyard.name"));
        		}
        		
        		String matname = null;
        		switch (misc[ID.EquipMisc.DEVELOP_MAT])
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
        				  String.format("%.0f", (float)misc[ID.EquipMisc.DEVELOP_NUM]) + "  " +
        				  TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.matsrarelevel") +
        				  TextFormatting.GRAY + " " + String.format("%.0f", (float)misc[ID.EquipMisc.RARE_MEAN]);
        		
        		list.add(drawstr);
        	}//end get item stat
    	}//end get item
    }
    
    
}