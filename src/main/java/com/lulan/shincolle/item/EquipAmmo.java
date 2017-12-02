package com.lulan.shincolle.item;

import java.util.HashMap;
import java.util.Map;

import com.lulan.shincolle.reference.ID;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**meta:
 *    0: Type 91 Armor Piercing Shell 九一式徹甲彈
 *    1: Type 1 Armor Piercing Shell 一式徹甲彈
 *    2: Anti-Air Dispersal Shell 對空散彈
 *    3: Type 3 Shell 三式彈
 *    4: Depleted Uranium Shell 貧化鈾彈
 *    5: Gravity Shell 重力彈
 *    6: Anti-Gravity Shell 反重力彈
 *    7: Enchant Shell 附魔彈
 */
public class EquipAmmo extends BasicEquip implements IShipEffectItem
{
	
	private static final String NAME = "EquipAmmo";
	
	
	public EquipAmmo()
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
		return 8;
	}
	
	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
		case 2:
			return ID.EquipType.AMMO_LO;
		case 1:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return ID.EquipType.AMMO_HI;
		default:
			return 0;
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
		case 5:
			return 5;
		default:
			return 0;
		}
	}

	@Override
	public int getMissileMoveType(int meta)
	{
		return -1;
	}

	@Override
	public int getMissileSpeedLevel(int meta)
	{
		return 0;
	}
	
	
}