package com.lulan.shincolle.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.lulan.shincolle.reference.ID;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

/**meta:
 *    0: Type 91 Armor Piercing Shell 九一式徹甲彈
 *    1: Type 1 Armor Piercing Shell 一式徹甲彈
 *    2: Anti-Air Dispersal Shell 對空散彈
 *    3: Type 3 Shell 三式彈
 *    4: Depleted Uranium Shell 貧化鈾彈
 *    5: Gravity Shell 重力彈
 *    6: Anti-Gravity Shell 反重力彈
 *    7: Enchant Shell 附魔彈
 *    8: Cluster Bomb 集束炸彈
 */
public class EquipAmmo extends BasicEquip implements IShipEffectItem
{
	
	public static final String NAME = "EquipAmmo";
	public static final String PID = "PID";		//nbt tag name for Enchant Shell
	public static final String PLEVEL = "PLV";
	public static final String PTIME = "PTick";
	public static final String PCHANCE = "PChance";
	public static final String PLIST = "PList";
	
	
	public EquipAmmo()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
	}
	
	@Override
	public int getTypes()
	{
		return 9;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 1:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return ID.EquipType.AMMO_HI;
		default:
			return ID.EquipType.AMMO_LO;
		}
	}
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
		switch(this.getEquipTypeIDFromMeta(stack.getMetadata()))
		{
		case ID.EquipType.AMMO_LO:
			return 12;
		case ID.EquipType.AMMO_HI:
			return 25;
		default:
			return 12;
		}
    }
	
	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.AMMO_LO:  //120
			return new int[] {itemRand.nextInt(3) + 4,
	  		  		  		  itemRand.nextInt(4) + 7,
	  		  		  		  itemRand.nextInt(5) + 9,
	  		  		  		  itemRand.nextInt(2) + 4};
		case ID.EquipType.AMMO_HI:  //1000
			return new int[] {itemRand.nextInt(25) + 35,
							  itemRand.nextInt(30) + 45,
							  itemRand.nextInt(40) + 70,
							  itemRand.nextInt(20) + 40};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}

	@Override
	public Map<Integer, int[]> getEffectOnAttack(int meta)
	{
		HashMap<Integer, int[]> emap = new HashMap<Integer, int[]>();
		
		switch (meta)
		{
		case 0:  //type 91
			emap.put(19, new int[] {0, 120, 50});
		break;
		case 1:  //type 1
			emap.put(19, new int[] {1, 120, 70});
		break;
		case 3:  //type 3
			emap.put(9, new int[] {0, 120, 50});
		break;
		case 4:  //DU
			emap.put(20, new int[] {0, 100, 25});
		break;
		case 6:  //anti-grav
			emap.put(25, new int[] {0, 100, 50});
		break;
		}
		
		return emap;
	}

	@Override
	public int getMissileType(int meta)
	{
		switch (meta)
		{
		case 5:  //black hole
			return 5;
		case 8:  //cluster bomb
			return 3;
		default:
			return 0;
		}
	}

	@Override
	public int getMissileMoveType(int meta)
	{
		switch (meta)
		{
		case 8:  //cluster bomb
			return 1;
		default:
			return -1;
		}
	}

	@Override
	public int getMissileSpeedLevel(int meta)
	{
		return 0;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
		super.addInformation(stack, world, list, flag);
		
		switch (stack.getMetadata())
		{
		case 5:  //gravity
		{
			list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:equip.gravity"));
		}
		break;
		case 7:  //enchant
		{
			if (stack.hasTagCompound())
			{
				NBTTagCompound nbt = stack.getTagCompound();
		  		NBTTagList nbtlist = nbt.getTagList(EquipAmmo.PLIST, Constants.NBT.TAG_COMPOUND);
		  		int pid = 0;
		  		int plv = 0;
		  		int ptime = 0;
		  		int pchance = 0;
		  		NBTTagCompound nbtX = null;
		  		String name = null;
		  		
		  		for (int i = 0; i < nbtlist.tagCount(); i++)
		        {
		            nbtX = nbtlist.getCompoundTagAt(i);
		            pid = nbtX.getInteger(EquipAmmo.PID);
		            Potion pt = Potion.getPotionById(pid);
		            
		            if (pt != null)
		            {
		            	String s1 = I18n.format(pt.getName()).trim();
			            plv = nbtX.getInteger(EquipAmmo.PLEVEL) + 1;
			            ptime = nbtX.getInteger(EquipAmmo.PTIME) / 20;
			            pchance = nbtX.getInteger(EquipAmmo.PCHANCE);
			            list.add(I18n.format("gui.shincolle:equip.enchantshell", pchance, s1, plv, ptime));
		            }
		        }
			}
		}
		break;
		case 8:  //cluster
		{
			list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:equip.cluster"));
		}
		break;
		}
		
		//show other effect
		Map<Integer, int[]> emap = getEffectOnAttack(stack.getMetadata());
		
		if (emap != null && emap.size() > 0)
		{
			emap.forEach((pid, pdata) ->
			{
				Potion pt = Potion.getPotionById(pid);
				
				if (pt != null)
	            {
					String s1 = I18n.format(pt.getName()).trim();
					list.add(I18n.format("gui.shincolle:equip.enchantshell", pdata[2], s1, pdata[0]+1, pdata[1]/20));
	            }
			});
		}
    }
	
}