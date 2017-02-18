package com.lulan.shincolle.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**SHIP SPAWN CALC
 * 
 * Ship Build Rate: first roll -> second roll
 * 	 1. FIRST: roll ship type
 * 
 * 	 2. SECOND: roll ships of the type
 */
public class ShipCalc
{
	
	private static Random rand = new Random();
	private static final float[] baseRate = new float[] {64F, 16F, 4F, 1F};
	
	//roll table
	private static List<int[]> EquipSmall = new ArrayList<int[]>();
	private static List<int[]> EquipLarge = new ArrayList<int[]>();
	
	//init roll table
	static
	{
		/**roll table: 0:ship id, 1:material mean, 2:modified material type
		 * material mean: material amount correspond to normal dist, high = need more materials
		 * modified material type: mat can increase build rate: -1:none 0:grudge 1:metal 2:ammo 3:poly
		 */
		//small build
		EquipSmall.add(new int[] {ID.Ship.DestroyerI,      80,   0});
		EquipSmall.add(new int[] {ID.Ship.DestroyerRO,     90,   0});
		EquipSmall.add(new int[] {ID.Ship.DestroyerHA,     100,  0});
		EquipSmall.add(new int[] {ID.Ship.DestroyerNI,     110,  0});
		EquipSmall.add(new int[] {ID.Ship.TransportWA,     120,  1});
		EquipSmall.add(new int[] {ID.Ship.SubmarineKA,     140,  2});
		EquipSmall.add(new int[] {ID.Ship.SubmarineYO,     160,  2});
		EquipSmall.add(new int[] {ID.Ship.SubmarineSO,     180,  2});
		EquipSmall.add(new int[] {ID.Ship.HeavyCruiserRI,  200,  2});
		EquipSmall.add(new int[] {ID.Ship.HeavyCruiserNE,  256,  2});
		
		//large build
		EquipLarge.add(new int[] {ID.Ship.DestroyerHime,   500,  0});
		EquipLarge.add(new int[] {ID.Ship.CarrierWO,       650,  3});
		EquipLarge.add(new int[] {ID.Ship.BattleshipTA,    800,  2});
		EquipLarge.add(new int[] {ID.Ship.BattleshipRU,    800,  2});
		EquipLarge.add(new int[] {ID.Ship.NorthernHime,    2600, 1});
		EquipLarge.add(new int[] {ID.Ship.HarbourHime,     2800, 1});
		EquipLarge.add(new int[] {ID.Ship.AirfieldHime,    3000, 1});
		EquipLarge.add(new int[] {ID.Ship.CarrierHime,     3000, 3});
		EquipLarge.add(new int[] {ID.Ship.BattleshipRE,    3800, 2});	
		EquipLarge.add(new int[] {ID.Ship.BattleshipHime,  4600, 2});
		EquipLarge.add(new int[] {ID.Ship.CarrierWD,       5000, 3});
	}
	

	//get material amount from nbt data
	private static int[] getMatAmount(ItemStack itemstack)
	{
		int[] matAmount = {0,0,0,0};	//grudge, abyssium, ammo, polymetal
				
		if (itemstack.hasTagCompound())
		{		
			matAmount[0] = itemstack.getTagCompound().getInteger("Grudge");
			matAmount[1] = itemstack.getTagCompound().getInteger("Abyssium");
			matAmount[2] = itemstack.getTagCompound().getInteger("Ammo");
			matAmount[3] = itemstack.getTagCompound().getInteger("Polymetal");
		}
	
		LogHelper.debug("DEBUG: shipcalc get matAmount : "+matAmount[0]+" "+matAmount[1]+" "+matAmount[2]+" "+matAmount[3]);
		return matAmount;
	}

	//roll ship type by total number of materials
	public static int rollShipType(ItemStack item)
	{
		List<int[]> shipList = new ArrayList<int[]>();
		int[] material = new int[4];
		int totalMats = 0;
		
		if (item.getItemDamage() > 1)
		{	//is special egg
			return item.getItemDamage() - 2;
		}
		
		//get materials amount
    	if (item.hasTagCompound())
    	{ 	//正常製造egg, 會有四個材料tag		
    		material[0] = item.getTagCompound().getInteger("Grudge");
    		material[1] = item.getTagCompound().getInteger("Abyssium");
    		material[2] = item.getTagCompound().getInteger("Ammo");
    		material[3] = item.getTagCompound().getInteger("Polymetal");
    		
    		totalMats = material[0] + material[1] + material[2] + material[3];
        }

    	List<int[]> shiplistOrg = null;  //raw ship list: 0:ship ID, 1:mean 2:specific material
		float te = 4000F;	//for gui info calc
		
		//get equip list by build type
		if (item.getItemDamage() == 0)
		{	//small egg
			shiplistOrg = EquipSmall;
			te = 256F;
		}
		else
		{
			shiplistOrg = EquipLarge;
		}
		
		/**roll ship type
		 * 0. tweak roll list by mat.amount: specific material decrease the mean value
		 * 1. get prob of ships in roll list
		 * 2. roll 0~1 to get ship
		 * 3. return ship id
		 */
		//prob list: map<ship ID, prob parameter>
		Map<Integer, Float> probList = new HashMap<Integer, Float>();
		int meanNew = 0;
		int meanDist = 0;
		float prob = 0F;
		
		for (int[] i : shiplistOrg)
		{
			//get modified material type
			if (i[2] >= 0 && i[2] <= 3)
			{
				meanNew = i[1] - material[i[2]];
			}
			else
			{
				meanNew = i[1];
			}
			
			//get mean distance
			meanDist = MathHelper.abs(totalMats - meanNew);
			
			//mean value to prob value
			if (item.getItemDamage() == 0)
			{	//small egg
				//for small build, max material = 256
				//change scale to large build resolution
				meanDist = (int) (meanDist * 15.625F); // = mat / 256 * 4000
			}
			
			prob = CalcHelper.getNormDist(meanDist);
			//add to map
			probList.put(i[0], prob);
			LogHelper.debug("DEBUG: roll ship type: prob list: ID "+i[0]+" MEAN(ORG) "+i[1]+" MEAN(NEW) "+meanNew+" MEAN(P) "+(meanNew/te)+" MD "+meanDist+" PR "+prob);
		}
		
		float random = rand.nextFloat();
		float totalProb = 0F;
		float sumProb = 0.0125F;	//init value to prevent float comparison bug
		int rollresult = -1;

		//get total prob
		Iterator iter = probList.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			totalProb += (Float) entry.getValue();
		}
		
		//scale random number to totalProb
		random *= totalProb;
		
		//roll ship
		iter = probList.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			sumProb += (Float) entry.getValue();
			LogHelper.debug("DEBUG: roll ship type: random: "+random+" sum.pr "+sumProb+" total.pr "+totalProb);
			
			if (sumProb > random)
			{	//get item
				rollresult = (Integer) entry.getKey();
				LogHelper.debug("DEBUG: roll ship type: get ship:"+rollresult);
				break;
			}
		}

		return rollresult;
	}
	
	/** Get entity name from metadata
  	 *  (entity name from ModEntity.class)
  	 *  small egg: all non-hime ship
  	 *  large egg: all ship
  	 *  specific egg: specific ship
  	 */
  	public static String getEntityToSpawnName(int type)
  	{
  		switch(type)
  		{
  		case ID.Ship.DestroyerI:
  			return "shincolle.EntityDestroyerI";
  		case ID.Ship.DestroyerRO:
  			return "shincolle.EntityDestroyerRo";
  		case ID.Ship.DestroyerHA:
  			return "shincolle.EntityDestroyerHa";
  		case ID.Ship.DestroyerNI:
  			return "shincolle.EntityDestroyerNi";
  		case ID.Ship.HeavyCruiserRI:
  			return "shincolle.EntityHeavyCruiserRi";
  		case ID.Ship.HeavyCruiserNE:
  			return "shincolle.EntityHeavyCruiserNe";
  		case ID.Ship.CarrierWO:
  			return "shincolle.EntityCarrierWo";
  		case ID.Ship.BattleshipRU:
  			return "shincolle.EntityBattleshipRu";
  		case ID.Ship.BattleshipTA:
  			return "shincolle.EntityBattleshipTa";
  		case ID.Ship.BattleshipRE:
  			return "shincolle.EntityBattleshipRe";
  		case ID.Ship.TransportWA:
  			return "shincolle.EntityTransportWa";
  		case ID.Ship.SubmarineKA:
  			return "shincolle.EntitySubmKa";
  		case ID.Ship.SubmarineYO:
  			return "shincolle.EntitySubmYo";
  		case ID.Ship.SubmarineSO:
  			return "shincolle.EntitySubmSo";
  		case ID.Ship.AirfieldHime:
  			return "shincolle.EntityAirfieldHime";
  		case ID.Ship.CarrierHime:
  			return "shincolle.EntityCarrierHime";
  		case ID.Ship.BattleshipHime:
  			return "shincolle.EntityBattleshipHime";
  		case ID.Ship.DestroyerHime:
  			return "shincolle.EntityDestroyerHime";	
  		case ID.Ship.HarbourHime:
  			return "shincolle.EntityHarbourHime";
  		case ID.Ship.NorthernHime:
  			return "shincolle.EntityNorthernHime";
  		case ID.Ship.CarrierWD:
  			return "shincolle.EntityCarrierWD";
  		case ID.Ship.DestroyerShimakaze:
  			return "shincolle.EntityDestroyerShimakaze";
  		case ID.Ship.DestroyerShimakaze+2000:
  			return "shincolle.EntityDestroyerShimakazeMob";
  		case ID.Ship.BattleshipNagato:
  			return "shincolle.EntityBattleshipNGT";
  		case ID.Ship.BattleshipNagato+2000:
  			return "shincolle.EntityBattleshipNGTMob";
  		case ID.Ship.BattleshipYamato:
  			return "shincolle.EntityBattleshipYMT";
  		case ID.Ship.BattleshipYamato+2000:
  			return "shincolle.EntityBattleshipYMTMob";
  		case ID.Ship.SubmarineU511:
  			return "shincolle.EntitySubmU511";
  		case ID.Ship.SubmarineU511+2000:
  			return "shincolle.EntitySubmU511Mob";
  		case ID.Ship.SubmarineRo500:
  			return "shincolle.EntitySubmRo500";
  		case ID.Ship.SubmarineRo500+2000:
  			return "shincolle.EntitySubmRo500Mob";
  		case ID.Ship.CarrierKaga:
  			return "shincolle.EntityCarrierKaga";
  		case ID.Ship.CarrierKaga+2000:
  			return "shincolle.EntityCarrierKagaMob";
  		case ID.Ship.CarrierAkagi:
  			return "shincolle.EntityCarrierAkagi";
  		case ID.Ship.CarrierAkagi+2000:
  			return "shincolle.EntityCarrierAkagiMob";
  		case ID.Ship.DestroyerAkatsuki:
  			return "shincolle.EntityDestroyerAkatsuki";
  		case ID.Ship.DestroyerAkatsuki+2000:
  			return "shincolle.EntityDestroyerAkatsukiMob";
  		case ID.Ship.DestroyerHibiki:
  			return "shincolle.EntityDestroyerHibiki";
  		case ID.Ship.DestroyerHibiki+2000:
  			return "shincolle.EntityDestroyerHibikiMob";
  		case ID.Ship.DestroyerIkazuchi:
  			return "shincolle.EntityDestroyerIkazuchi";
  		case ID.Ship.DestroyerIkazuchi+2000:
  			return "shincolle.EntityDestroyerIkazuchiMob";
  		case ID.Ship.DestroyerInazuma:
  			return "shincolle.EntityDestroyerInazuma";
  		case ID.Ship.DestroyerInazuma+2000:
  			return "shincolle.EntityDestroyerInazumaMob";
  		case ID.Ship.LightCruiserTenryuu:
  			return "shincolle.EntityCruiserTenryuu";
  		case ID.Ship.LightCruiserTenryuu+2000:
  			return "shincolle.EntityCruiserTenryuuMob";
  		case ID.Ship.LightCruiserTatsuta:
  			return "shincolle.EntityCruiserTatsuta";
  		case ID.Ship.LightCruiserTatsuta+2000:
  			return "shincolle.EntityCruiserTatsutaMob";
  		case ID.Ship.HeavyCruiserAtago:
  			return "shincolle.EntityCruiserAtago";
  		case ID.Ship.HeavyCruiserAtago+2000:
  			return "shincolle.EntityCruiserAtagoMob";
  		case ID.Ship.HeavyCruiserTakao:
  			return "shincolle.EntityCruiserTakao";
  		case ID.Ship.HeavyCruiserTakao+2000:
  			return "shincolle.EntityCruiserTakaoMob";
  		default:
  			return "shincolle.EntityDestroyerI";
  		}	
  	}
  	
  	/**
  	 * spawn ship mob name
  	 * rare level is calculated here
  	 * 
  	 * double roll:
  	 *   1. roll for ship rare level
  	 *   2. roll for ship name
  	 */
  	public static String getRandomMobToSpawnName()
  	{
  		int ran1 = rand.nextInt(100);
  		
  		//25% for super rare level
  		if (ran1 > 75)
  		{
  			switch (rand.nextInt(2))
  			{
  			case 1:
  				return getEntityToSpawnName(ID.Ship.BattleshipYamato+2000);
  			default:
  				return getEntityToSpawnName(ID.Ship.BattleshipNagato+2000);
  			}
  		}
  		//30% for rare level
  		else if (ran1 > 45)
  		{
  			switch (rand.nextInt(6))
  			{
  			case 1:
  				return getEntityToSpawnName(ID.Ship.CarrierKaga+2000);
  			case 2:
  				return getEntityToSpawnName(ID.Ship.LightCruiserTenryuu+2000);
  			case 3:
  				return getEntityToSpawnName(ID.Ship.LightCruiserTatsuta+2000);
  			case 4:
  				return getEntityToSpawnName(ID.Ship.HeavyCruiserAtago+2000);
//  			case 5:
//  				return getEntityToSpawnName(ID.Ship.HeavyCruiserTakao+2000);
  			default:
  				return getEntityToSpawnName(ID.Ship.CarrierAkagi+2000);
  			}
  		}
  		//45% for common level
  		else
  		{
  			switch (rand.nextInt(7))
  			{
  			case 1:
  				return getEntityToSpawnName(ID.Ship.DestroyerHibiki+2000);
  			case 2:
  				return getEntityToSpawnName(ID.Ship.DestroyerIkazuchi+2000);
  			case 3:
  				return getEntityToSpawnName(ID.Ship.DestroyerInazuma+2000);
  			case 4:
  				return getEntityToSpawnName(ID.Ship.DestroyerShimakaze+2000);
  			case 5:
  				return getEntityToSpawnName(ID.Ship.SubmarineU511+2000);
  			case 6:
  				return getEntityToSpawnName(ID.Ship.SubmarineRo500+2000);
  			default:
  				return getEntityToSpawnName(ID.Ship.DestroyerAkatsuki+2000);
  			}
  		}
  	}
  	
  	/** get ship recycle items */
  	public static ItemStack[] getKaitaiItems(int shipID)
  	{
  		ItemStack[] amount = new ItemStack[4];

  		switch(shipID)
  		{
  		case ID.Ship.DestroyerI:
  		case ID.Ship.DestroyerRO:
  		case ID.Ship.DestroyerHA:
  		case ID.Ship.DestroyerNI:
  		case ID.Ship.LightCruiserHO:
  		case ID.Ship.LightCruiserHE:
  		case ID.Ship.LightCruiserTO:
  		case ID.Ship.LightCruiserTSU:
  		case ID.Ship.TorpedoCruiserCHI:
  		case ID.Ship.HeavyCruiserRI:
  		case ID.Ship.HeavyCruiserNE:
  		case ID.Ship.LightCarrierNU:
  		case ID.Ship.TransportWA:
  		case ID.Ship.SubmarineKA:
  		case ID.Ship.SubmarineYO:
  		case ID.Ship.SubmarineSO:
  			amount[0] = new ItemStack(ModItems.Grudge, 12 + rand.nextInt(8));
  			amount[1] = new ItemStack(ModItems.AbyssMetal, 12 + rand.nextInt(8), 0);
  			amount[2] = new ItemStack(ModItems.Ammo, 12 + rand.nextInt(8), 0);
  			amount[3] = new ItemStack(ModItems.AbyssMetal, 12 + rand.nextInt(8), 1);
        	break;
  		case ID.Ship.CarrierWO:
  		case ID.Ship.BattleshipRU:
  		case ID.Ship.BattleshipTA:
  		case ID.Ship.BattleshipRE:
  		case ID.Ship.CarrierHime:
  		case ID.Ship.AirfieldHime:
  		case ID.Ship.ArmoredCarrierHime:
  		case ID.Ship.AnchorageHime:
  		case ID.Ship.HarbourWD:
  		case ID.Ship.AnchorageWD:
  		case ID.Ship.BattleshipHime:
  		case ID.Ship.DestroyerHime:
  		case ID.Ship.HarbourHime:
  		case ID.Ship.IsolatedDemon:
  		case ID.Ship.MidwayHime:
  		case ID.Ship.NorthernHime:
  		case ID.Ship.SouthernHime:
  		case ID.Ship.CarrierWD:
  		case ID.Ship.LightCruiserDemon:
  		case ID.Ship.BattleshipWD:
  		case ID.Ship.SeaplaneHime:
  		case ID.Ship.AirdefenseHime:
  		case ID.Ship.PTImp:
  		case ID.Ship.LightCruiserHime:
  		case ID.Ship.SubmarineHime:
  		case ID.Ship.DestroyerWD:
  		case ID.Ship.HeavyCruiserHime:
  		case ID.Ship.SupplyDepotHime:
  			//easy mode: reduce amount to prevent resources dupe
  			if (ConfigHandler.easyMode)
  			{
  				amount[0] = new ItemStack(ModBlocks.BlockGrudge, 1);
  				amount[1] = new ItemStack(ModBlocks.BlockAbyssium, 1);
  				amount[2] = new ItemStack(ModItems.Ammo, 1, 1);
  				amount[3] = new ItemStack(ModBlocks.BlockPolymetal, 1);
        	}
        	else
        	{
        		amount[0] = new ItemStack(ModBlocks.BlockGrudge, 10 + rand.nextInt(3));
        		amount[1] = new ItemStack(ModBlocks.BlockAbyssium, 10 + rand.nextInt(3));
        		amount[2] = new ItemStack(ModItems.Ammo, 10 + rand.nextInt(3), 1);
        		amount[3] = new ItemStack(ModBlocks.BlockPolymetal, 10 + rand.nextInt(3));
        	}
  			break;
  		case ID.Ship.SubmarineU511:
  		case ID.Ship.SubmarineRo500:
  		case ID.Ship.DestroyerAkatsuki:
  		case ID.Ship.DestroyerHibiki:
  		case ID.Ship.DestroyerIkazuchi:
  		case ID.Ship.DestroyerInazuma:
  		case ID.Ship.DestroyerShimakaze:
  		case ID.Ship.Raiden:
  			amount[0] = new ItemStack(ModItems.Grudge, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1));
  			amount[1] = new ItemStack(ModItems.AbyssMetal, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1), 0);
  			amount[2] = new ItemStack(ModItems.Ammo, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1), 0);
  			amount[3] = new ItemStack(ModItems.AbyssMetal, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1), 1);
  			break;
  		case ID.Ship.HeavyCruiserAtago:
  		case ID.Ship.HeavyCruiserTakao:
  		case ID.Ship.LightCruiserTenryuu:
  		case ID.Ship.LightCruiserTatsuta:
  			amount[0] = new ItemStack(ModBlocks.BlockGrudge, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1));
  			amount[1] = new ItemStack(ModBlocks.BlockAbyssium, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1));
  			amount[2] = new ItemStack(ModItems.Ammo, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1), 1);
  			amount[3] = new ItemStack(ModBlocks.BlockPolymetal, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1));
  			break;
  		case ID.Ship.BattleshipNagato:
  		case ID.Ship.BattleshipYamato:
  		case ID.Ship.CarrierKaga:
  		case ID.Ship.CarrierAkagi:
  			amount[0] = new ItemStack(ModBlocks.BlockGrudge, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1));
  			amount[1] = new ItemStack(ModBlocks.BlockAbyssium, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1));
  			amount[2] = new ItemStack(ModItems.Ammo, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1), 1);
        	amount[3] = new ItemStack(ModBlocks.BlockPolymetal, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1));
  			break;
  		}
  		
  		return amount;
  	}
  	
	
}