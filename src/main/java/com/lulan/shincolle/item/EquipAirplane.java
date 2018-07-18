package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**meta:
 *    0:  Torpedo Bomber Mk.I
 *    1:  Torpedo Bomber Mk.II
 *    2:  Torpedo Bomber Mk.III
 *    3:  Avenger Torpedo Bomber
 *    4:  Fighter Mk.I
 *    5:  Fighter Mk.II
 *    6:  Fighter Mk.III
 *    7:  Flying-Fish Fighter
 *    8:  Hellcat Fighter
 *    9:  Dive Bomber Mk.I
 *    10: Dive Bomber Mk.II
 *    11: Flying-Fish Dive Bomber
 *    12: Hell Diver
 *    13: Recon Plane
 *    14: Flying-Fish Recon Plane
 *    15: Avenger Torpedo Bomber Kai
 *    16: Hellcat Fighter Kai
 *    17: Hell Diver Kai
 *    18: Abyssal Cat Fighter (Bombing)
 *    19: Abyssal Liberation Land-based Dive Bomber
 *    20: Abyssal Liberation Land-based Dive Bomber Ace
 *    21: Abyssal Bearcat Fighter
 */
public class EquipAirplane extends BasicEquip
{
	
	private static final String NAME = "EquipAirplane";
	
	
	public EquipAirplane()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
	}
	
	/** rearrange item order in creative tab */
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
		//T
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
		list.add(new ItemStack(this, 1, 3));
		list.add(new ItemStack(this, 1, 15));
		//F
		list.add(new ItemStack(this, 1, 4));
		list.add(new ItemStack(this, 1, 5));
		list.add(new ItemStack(this, 1, 6));
		list.add(new ItemStack(this, 1, 7));
		list.add(new ItemStack(this, 1, 8));
		list.add(new ItemStack(this, 1, 18));
		list.add(new ItemStack(this, 1, 16));
		list.add(new ItemStack(this, 1, 21));
		//B
		list.add(new ItemStack(this, 1, 9));
		list.add(new ItemStack(this, 1, 10));
		list.add(new ItemStack(this, 1, 11));
		list.add(new ItemStack(this, 1, 12));
		list.add(new ItemStack(this, 1, 17));
		list.add(new ItemStack(this, 1, 19));
		list.add(new ItemStack(this, 1, 20));
		//R
		list.add(new ItemStack(this, 1, 13));
		list.add(new ItemStack(this, 1, 14));
	}
	
	/** T=5, F=8, B=7, R=2 */
	@Override
	public int getTypes()
	{
		return 22;
	}
	
	@Override
	public int getIconTypes()
	{
		return 4;
	}

	@Override
	public int getEquipTypeIDFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
		case 1:
		case 2:
			return ID.EquipType.AIR_T_LO;
		case 3:
		case 15:
			return ID.EquipType.AIR_T_HI;
		case 4:
		case 5:
		case 6:
			return ID.EquipType.AIR_F_LO;
		case 7:
		case 8:
		case 16:
		case 18:
		case 21:
			return ID.EquipType.AIR_F_HI;
		case 9:
		case 10:
			return ID.EquipType.AIR_B_LO;
		case 11:
		case 12:
		case 17:
		case 19:
		case 20:
			return ID.EquipType.AIR_B_HI;
		case 13:
			return ID.EquipType.AIR_R_LO;
		case 14:
			return ID.EquipType.AIR_R_HI;
		default:
			return 0;
		}
	}

	@Override
	public int getIconFromDamage(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.AIR_T_LO:
		case ID.EquipType.AIR_T_HI:
			return 0;	//Torpedo
		case ID.EquipType.AIR_F_LO:
		case ID.EquipType.AIR_F_HI:
			return 1;	//Fighter
		case ID.EquipType.AIR_B_LO:
		case ID.EquipType.AIR_B_HI:
			return 2;	//Bomber
		case ID.EquipType.AIR_R_LO:
		case ID.EquipType.AIR_R_HI:
			return 3;	//Recon
		default:
			return 0;
		}
	}
	
	@Override
    public int getItemEnchantability(ItemStack stack)
    {
		switch(this.getEquipTypeIDFromMeta(stack.getMetadata()))
		{
		case ID.EquipType.AIR_T_LO:
		case ID.EquipType.AIR_F_LO:
		case ID.EquipType.AIR_B_LO:
		case ID.EquipType.AIR_R_LO:
			return 18;
		case ID.EquipType.AIR_T_HI:
		case ID.EquipType.AIR_F_HI:
		case ID.EquipType.AIR_B_HI:
		case ID.EquipType.AIR_R_HI:
			return 25;
		default:
			return 9;
		}
    }

	@Override
	public int[] getResourceValue(int meta)
	{
		switch(this.getEquipTypeIDFromMeta(meta))
		{
		case ID.EquipType.AIR_T_LO:
		case ID.EquipType.AIR_F_LO:
		case ID.EquipType.AIR_B_LO:  //2400
			return new int[] {itemRand.nextInt(20) + 80,
					  		  itemRand.nextInt(30) + 100,
					  		  itemRand.nextInt(40) + 120,
					  		  itemRand.nextInt(50) + 150};
		case ID.EquipType.AIR_T_HI:
		case ID.EquipType.AIR_F_HI:
		case ID.EquipType.AIR_B_HI:  //3800
			return new int[] {itemRand.nextInt(50) + 130,
			  		  		  itemRand.nextInt(60) + 170,
			  		  		  itemRand.nextInt(70) + 210,
			  		  		  itemRand.nextInt(75) + 230};
		case ID.EquipType.AIR_R_LO:  //256
			return new int[] {itemRand.nextInt(12) + 3,
							  itemRand.nextInt(14) + 5,
							  itemRand.nextInt(14) + 5,
							  itemRand.nextInt(16) + 11};
		case ID.EquipType.AIR_R_HI:  //1000
			return new int[] {itemRand.nextInt(10) + 40,
							  itemRand.nextInt(15) + 50,
							  itemRand.nextInt(20) + 60,
							  itemRand.nextInt(25) + 80};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	

}