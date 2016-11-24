package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

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
	public int getSpecialEffect(ItemStack stack)
	{
		return -1;
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
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> list, boolean par4)
    {
    	if (itemstack != null && itemstack.getItem() != null)
    	{
    		float[] itemStat = Values.EquipMap.get(((BasicEquip)itemstack.getItem()).getEquipID(itemstack.getItemDamage()));
        	//TODO change tooltip value by config like: max limit, factor...etc
        	if (itemStat != null)
        	{
        		if (itemStat[ID.E.HP] != 0F) list.add(TextFormatting.RED + String.valueOf(itemStat[ID.E.HP])+ " " + I18n.format("gui.shincolle:hp"));
        		if (itemStat[ID.E.ATK_L] != 0F) list.add(TextFormatting.RED + String.valueOf(itemStat[ID.E.ATK_L])+ " " + I18n.format("gui.shincolle:firepower1"));
        		if (itemStat[ID.E.ATK_H] != 0F) list.add(TextFormatting.GREEN + String.valueOf(itemStat[ID.E.ATK_H])+ " " + I18n.format("gui.shincolle:torpedo"));
        		if (itemStat[ID.E.ATK_AL] != 0F) list.add(TextFormatting.RED + String.valueOf(itemStat[ID.E.ATK_AL])+ " " + I18n.format("gui.shincolle:airfirepower"));
        		if (itemStat[ID.E.ATK_AH] != 0F) list.add(TextFormatting.GREEN + String.valueOf(itemStat[ID.E.ATK_AH])+ " " + I18n.format("gui.shincolle:airtorpedo"));
        		if (itemStat[ID.E.DEF] != 0F) list.add(TextFormatting.WHITE + String.format("%.0f",itemStat[ID.E.DEF])+ "% " + I18n.format("gui.shincolle:armor"));
        		if (itemStat[ID.E.SPD] != 0F) list.add(TextFormatting.WHITE + String.valueOf(itemStat[ID.E.SPD])+ " " + I18n.format("gui.shincolle:attackspeed"));
        		if (itemStat[ID.E.MOV] != 0F) list.add(TextFormatting.GRAY + String.valueOf(itemStat[ID.E.MOV])+ " " + I18n.format("gui.shincolle:movespeed"));
        		if (itemStat[ID.E.HIT] != 0F) list.add(TextFormatting.LIGHT_PURPLE + String.valueOf(itemStat[ID.E.HIT])+ " " + I18n.format("gui.shincolle:range"));
        		if (itemStat[ID.E.CRI] != 0F) list.add(TextFormatting.AQUA + String.format("%.0f",itemStat[ID.E.CRI]*100F)+ "% " + I18n.format("gui.shincolle:critical"));
        		if (itemStat[ID.E.DHIT] != 0F) list.add(TextFormatting.YELLOW + String.format("%.0f",itemStat[ID.E.DHIT]*100F)+ "% " + I18n.format("gui.shincolle:doublehit"));
        		if (itemStat[ID.E.THIT] != 0F) list.add(TextFormatting.GOLD + String.format("%.0f",itemStat[ID.E.THIT]*100F)+ "% " + I18n.format("gui.shincolle:triplehit"));
        		if (itemStat[ID.E.MISS] != 0F) list.add(TextFormatting.RED + String.format("%.0f",itemStat[ID.E.MISS]*100F)+ "% " + I18n.format("gui.shincolle:missreduce"));
        		if (itemStat[ID.E.DODGE] != 0F) list.add(TextFormatting.GOLD + String.format("%.0f",itemStat[ID.E.DODGE])+ "% " + I18n.format("gui.shincolle:dodge"));
        		if (itemStat[ID.E.AA] != 0F) list.add(TextFormatting.YELLOW + String.valueOf(itemStat[ID.E.AA])+ " " + I18n.format("gui.shincolle:antiair"));
        		if (itemStat[ID.E.ASM] != 0F) list.add(TextFormatting.AQUA + String.valueOf(itemStat[ID.E.ASM])+ " " + I18n.format("gui.shincolle:antiss"));
        		
        		if (itemStat[ID.E.LEVEL] == 1F)
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("gui.shincolle:notforcarrier"));
        		}
        		
        		if (itemStat[ID.E.LEVEL] == 3F)
        		{
        			list.add(TextFormatting.DARK_AQUA + I18n.format("gui.shincolle:carrieronly"));
        		}
        		
        		list.add(" ");
        		
        		//show construction info
        		if (itemStat[ID.E.DEVELOP_NUM] > 400F)
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("tile.shincolle:BlockLargeShipyard.name"));
        		}
        		else
        		{
        			list.add(TextFormatting.DARK_RED + I18n.format("tile.shincolle:BlockSmallShipyard.name"));
        		}
        		
        		String matname = null;
        		switch ((int)itemStat[ID.E.DEVELOP_MAT])
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
        		
        		list.add(TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.matstype") + TextFormatting.GRAY + " (" + matname + ") " + String.format("%.0f",itemStat[ID.E.DEVELOP_NUM]));
        		list.add(TextFormatting.DARK_PURPLE + I18n.format("gui.shincolle:equip.matsrarelevel") + TextFormatting.GRAY + " " + String.format("%.0f",itemStat[ID.E.RARE_MEAN]));
        	}//end get item stat
    	}//end get item
    	
    }
    
    
}
