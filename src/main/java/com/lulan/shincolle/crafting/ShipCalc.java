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
		EquipSmall.add(new int[] {ID.ShipClass.DestroyerI,      80,   0});
		EquipSmall.add(new int[] {ID.ShipClass.DestroyerRO,     90,   0});
		EquipSmall.add(new int[] {ID.ShipClass.DestroyerHA,     100,  0});
		EquipSmall.add(new int[] {ID.ShipClass.DestroyerNI,     110,  0});
		EquipSmall.add(new int[] {ID.ShipClass.TransportWA,     120,  1});
		EquipSmall.add(new int[] {ID.ShipClass.SubmarineKA,     140,  2});
		EquipSmall.add(new int[] {ID.ShipClass.SubmarineYO,     160,  2});
		EquipSmall.add(new int[] {ID.ShipClass.SubmarineSO,     180,  2});
		EquipSmall.add(new int[] {ID.ShipClass.HeavyCruiserRI,  200,  2});
		EquipSmall.add(new int[] {ID.ShipClass.HeavyCruiserNE,  256,  2});
		
		//large build
		EquipLarge.add(new int[] {ID.ShipClass.DestroyerHime,   500,  0});
		EquipLarge.add(new int[] {ID.ShipClass.CarrierWO,       650,  3});
		EquipLarge.add(new int[] {ID.ShipClass.BattleshipTA,    800,  2});
		EquipLarge.add(new int[] {ID.ShipClass.BattleshipRU,    800,  2});
		EquipLarge.add(new int[] {ID.ShipClass.HeavyCruiserHime,2000, 2});
		EquipLarge.add(new int[] {ID.ShipClass.NorthernHime,    2600, 1});
		EquipLarge.add(new int[] {ID.ShipClass.IsolatedHime,    2700, 1});
		EquipLarge.add(new int[] {ID.ShipClass.HarbourHime,     2800, 1});
		EquipLarge.add(new int[] {ID.ShipClass.AirfieldHime,    3000, 1});
		EquipLarge.add(new int[] {ID.ShipClass.CarrierHime,     3000, 3});
		EquipLarge.add(new int[] {ID.ShipClass.SubmarineHime,   3500, 2});
		EquipLarge.add(new int[] {ID.ShipClass.BattleshipRE,    3800, 2});
		EquipLarge.add(new int[] {ID.ShipClass.BattleshipHime,  4600, 2});
		EquipLarge.add(new int[] {ID.ShipClass.MidwayHime,      4800, 1});
		EquipLarge.add(new int[] {ID.ShipClass.CarrierWD,       5000, 3});
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
  		case ID.ShipClass.DestroyerI:
  			return "shincolle.EntityDestroyerI";
  		case ID.ShipClass.DestroyerRO:
  			return "shincolle.EntityDestroyerRo";
  		case ID.ShipClass.DestroyerHA:
  			return "shincolle.EntityDestroyerHa";
  		case ID.ShipClass.DestroyerNI:
  			return "shincolle.EntityDestroyerNi";
  		case ID.ShipClass.HeavyCruiserRI:
  			return "shincolle.EntityHeavyCruiserRi";
  		case ID.ShipClass.HeavyCruiserNE:
  			return "shincolle.EntityHeavyCruiserNe";
  		case ID.ShipClass.CarrierWO:
  			return "shincolle.EntityCarrierWo";
  		case ID.ShipClass.BattleshipRU:
  			return "shincolle.EntityBattleshipRu";
  		case ID.ShipClass.BattleshipTA:
  			return "shincolle.EntityBattleshipTa";
  		case ID.ShipClass.BattleshipRE:
  			return "shincolle.EntityBattleshipRe";
  		case ID.ShipClass.TransportWA:
  			return "shincolle.EntityTransportWa";
  		case ID.ShipClass.SubmarineKA:
  			return "shincolle.EntitySubmKa";
  		case ID.ShipClass.SubmarineYO:
  			return "shincolle.EntitySubmYo";
  		case ID.ShipClass.SubmarineSO:
  			return "shincolle.EntitySubmSo";
  		case ID.ShipClass.AirfieldHime:
  			return "shincolle.EntityAirfieldHime";
  		case ID.ShipClass.CarrierHime:
  			return "shincolle.EntityCarrierHime";
  		case ID.ShipClass.BattleshipHime:
  			return "shincolle.EntityBattleshipHime";
  		case ID.ShipClass.DestroyerHime:
  			return "shincolle.EntityDestroyerHime";	
  		case ID.ShipClass.HarbourHime:
  			return "shincolle.EntityHarbourHime";
  		case ID.ShipClass.IsolatedHime:
  			return "shincolle.EntityIsolatedHime";
  		case ID.ShipClass.MidwayHime:
  			return "shincolle.EntityMidwayHime";
  		case ID.ShipClass.NorthernHime:
  			return "shincolle.EntityNorthernHime";
  		case ID.ShipClass.SubmarineHime:
  			return "shincolle.EntitySubmHime";
  		case ID.ShipClass.CarrierWD:
  			return "shincolle.EntityCarrierWD";
  		case ID.ShipClass.DestroyerShimakaze:
  			return "shincolle.EntityDestroyerShimakaze";
  		case ID.ShipClass.DestroyerShimakaze+2000:
  			return "shincolle.EntityDestroyerShimakazeMob";
  		case ID.ShipClass.BattleshipNagato:
  			return "shincolle.EntityBattleshipNGT";
  		case ID.ShipClass.BattleshipNagato+2000:
  			return "shincolle.EntityBattleshipNGTMob";
  		case ID.ShipClass.BattleshipYamato:
  			return "shincolle.EntityBattleshipYMT";
  		case ID.ShipClass.BattleshipYamato+2000:
  			return "shincolle.EntityBattleshipYMTMob";
  		case ID.ShipClass.SubmarineU511:
  			return "shincolle.EntitySubmU511";
  		case ID.ShipClass.SubmarineU511+2000:
  			return "shincolle.EntitySubmU511Mob";
  		case ID.ShipClass.SubmarineRo500:
  			return "shincolle.EntitySubmRo500";
  		case ID.ShipClass.SubmarineRo500+2000:
  			return "shincolle.EntitySubmRo500Mob";
  		case ID.ShipClass.CarrierKaga:
  			return "shincolle.EntityCarrierKaga";
  		case ID.ShipClass.CarrierKaga+2000:
  			return "shincolle.EntityCarrierKagaMob";
  		case ID.ShipClass.CarrierAkagi:
  			return "shincolle.EntityCarrierAkagi";
  		case ID.ShipClass.CarrierAkagi+2000:
  			return "shincolle.EntityCarrierAkagiMob";
  		case ID.ShipClass.DestroyerAkatsuki:
  			return "shincolle.EntityDestroyerAkatsuki";
  		case ID.ShipClass.DestroyerAkatsuki+2000:
  			return "shincolle.EntityDestroyerAkatsukiMob";
  		case ID.ShipClass.DestroyerHibiki:
  			return "shincolle.EntityDestroyerHibiki";
  		case ID.ShipClass.DestroyerHibiki+2000:
  			return "shincolle.EntityDestroyerHibikiMob";
  		case ID.ShipClass.DestroyerIkazuchi:
  			return "shincolle.EntityDestroyerIkazuchi";
  		case ID.ShipClass.DestroyerIkazuchi+2000:
  			return "shincolle.EntityDestroyerIkazuchiMob";
  		case ID.ShipClass.DestroyerInazuma:
  			return "shincolle.EntityDestroyerInazuma";
  		case ID.ShipClass.DestroyerInazuma+2000:
  			return "shincolle.EntityDestroyerInazumaMob";
  		case ID.ShipClass.LightCruiserTenryuu:
  			return "shincolle.EntityCruiserTenryuu";
  		case ID.ShipClass.LightCruiserTenryuu+2000:
  			return "shincolle.EntityCruiserTenryuuMob";
  		case ID.ShipClass.LightCruiserTatsuta:
  			return "shincolle.EntityCruiserTatsuta";
  		case ID.ShipClass.LightCruiserTatsuta+2000:
  			return "shincolle.EntityCruiserTatsutaMob";
  		case ID.ShipClass.HeavyCruiserAtago:
  			return "shincolle.EntityCruiserAtago";
  		case ID.ShipClass.HeavyCruiserAtago+2000:
  			return "shincolle.EntityCruiserAtagoMob";
  		case ID.ShipClass.HeavyCruiserTakao:
  			return "shincolle.EntityCruiserTakao";
  		case ID.ShipClass.HeavyCruiserTakao+2000:
  			return "shincolle.EntityCruiserTakaoMob";
  		case ID.ShipClass.HeavyCruiserHime:
  			return "shincolle.EntityCAHime";
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
  				return getEntityToSpawnName(ID.ShipClass.BattleshipYamato+2000);
  			default:
  				return getEntityToSpawnName(ID.ShipClass.BattleshipNagato+2000);
  			}
  		}
  		//30% for rare level
  		else if (ran1 > 45)
  		{
  			switch (rand.nextInt(6))
  			{
  			case 1:
  				return getEntityToSpawnName(ID.ShipClass.CarrierKaga+2000);
  			case 2:
  				return getEntityToSpawnName(ID.ShipClass.LightCruiserTenryuu+2000);
  			case 3:
  				return getEntityToSpawnName(ID.ShipClass.LightCruiserTatsuta+2000);
  			case 4:
  				return getEntityToSpawnName(ID.ShipClass.HeavyCruiserAtago+2000);
  			case 5:
  				return getEntityToSpawnName(ID.ShipClass.HeavyCruiserTakao+2000);
  			default:
  				return getEntityToSpawnName(ID.ShipClass.CarrierAkagi+2000);
  			}
  		}
  		//45% for common level
  		else
  		{
  			switch (rand.nextInt(7))
  			{
  			case 1:
  				return getEntityToSpawnName(ID.ShipClass.DestroyerHibiki+2000);
  			case 2:
  				return getEntityToSpawnName(ID.ShipClass.DestroyerIkazuchi+2000);
  			case 3:
  				return getEntityToSpawnName(ID.ShipClass.DestroyerInazuma+2000);
  			case 4:
  				return getEntityToSpawnName(ID.ShipClass.DestroyerShimakaze+2000);
  			case 5:
  				return getEntityToSpawnName(ID.ShipClass.SubmarineU511+2000);
  			case 6:
  				return getEntityToSpawnName(ID.ShipClass.SubmarineRo500+2000);
  			default:
  				return getEntityToSpawnName(ID.ShipClass.DestroyerAkatsuki+2000);
  			}
  		}
  	}
  	
  	/** get ship recycle items */
  	public static ItemStack[] getKaitaiItems(int shipID)
  	{
  		ItemStack[] amount = new ItemStack[4];

  		switch(shipID)
  		{
  		case ID.ShipClass.DestroyerI:
  		case ID.ShipClass.DestroyerRO:
  		case ID.ShipClass.DestroyerHA:
  		case ID.ShipClass.DestroyerNI:
  		case ID.ShipClass.LightCruiserHO:
  		case ID.ShipClass.LightCruiserHE:
  		case ID.ShipClass.LightCruiserTO:
  		case ID.ShipClass.LightCruiserTSU:
  		case ID.ShipClass.TorpedoCruiserCHI:
  		case ID.ShipClass.HeavyCruiserRI:
  		case ID.ShipClass.HeavyCruiserNE:
  		case ID.ShipClass.LightCarrierNU:
  		case ID.ShipClass.TransportWA:
  		case ID.ShipClass.SubmarineKA:
  		case ID.ShipClass.SubmarineYO:
  		case ID.ShipClass.SubmarineSO:
  			amount[0] = new ItemStack(ModItems.Grudge, 12 + rand.nextInt(8));
  			amount[1] = new ItemStack(ModItems.AbyssMetal, 12 + rand.nextInt(8), 0);
  			amount[2] = new ItemStack(ModItems.Ammo, 12 + rand.nextInt(8), 0);
  			amount[3] = new ItemStack(ModItems.AbyssMetal, 12 + rand.nextInt(8), 1);
        	break;
  		case ID.ShipClass.CarrierWO:
  		case ID.ShipClass.BattleshipRU:
  		case ID.ShipClass.BattleshipTA:
  		case ID.ShipClass.BattleshipRE:
  		case ID.ShipClass.CarrierHime:
  		case ID.ShipClass.AirfieldHime:
  		case ID.ShipClass.ArmoredCarrierHime:
  		case ID.ShipClass.AnchorageHime:
  		case ID.ShipClass.HarbourWD:
  		case ID.ShipClass.AnchorageWD:
  		case ID.ShipClass.BattleshipHime:
  		case ID.ShipClass.DestroyerHime:
  		case ID.ShipClass.HarbourHime:
  		case ID.ShipClass.IsolatedHime:
  		case ID.ShipClass.MidwayHime:
  		case ID.ShipClass.NorthernHime:
  		case ID.ShipClass.SouthernHime:
  		case ID.ShipClass.CarrierWD:
  		case ID.ShipClass.LightCruiserDemon:
  		case ID.ShipClass.BattleshipWD:
  		case ID.ShipClass.SeaplaneHime:
  		case ID.ShipClass.AirdefenseHime:
  		case ID.ShipClass.PTImp:
  		case ID.ShipClass.LightCruiserHime:
  		case ID.ShipClass.SubmarineHime:
  		case ID.ShipClass.DestroyerWD:
  		case ID.ShipClass.HeavyCruiserHime:
  		case ID.ShipClass.SupplyDepotHime:
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
  		case ID.ShipClass.SubmarineU511:
  		case ID.ShipClass.SubmarineRo500:
  		case ID.ShipClass.DestroyerAkatsuki:
  		case ID.ShipClass.DestroyerHibiki:
  		case ID.ShipClass.DestroyerIkazuchi:
  		case ID.ShipClass.DestroyerInazuma:
  		case ID.ShipClass.DestroyerShimakaze:
  		case ID.ShipClass.Raiden:
  			amount[0] = new ItemStack(ModItems.Grudge, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1));
  			amount[1] = new ItemStack(ModItems.AbyssMetal, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1), 0);
  			amount[2] = new ItemStack(ModItems.Ammo, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1), 0);
  			amount[3] = new ItemStack(ModItems.AbyssMetal, ConfigHandler.kaitaiAmountSmall + rand.nextInt((int)(ConfigHandler.kaitaiAmountSmall * 0.25F) + 1), 1);
  			break;
  		case ID.ShipClass.HeavyCruiserAtago:
  		case ID.ShipClass.HeavyCruiserTakao:
  		case ID.ShipClass.LightCruiserTenryuu:
  		case ID.ShipClass.LightCruiserTatsuta:
  			amount[0] = new ItemStack(ModBlocks.BlockGrudge, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1));
  			amount[1] = new ItemStack(ModBlocks.BlockAbyssium, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1));
  			amount[2] = new ItemStack(ModItems.Ammo, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1), 1);
  			amount[3] = new ItemStack(ModBlocks.BlockPolymetal, ConfigHandler.kaitaiAmountLarge + rand.nextInt((int)(ConfigHandler.kaitaiAmountLarge * 0.25F) + 1));
  			break;
  		case ID.ShipClass.BattleshipNagato:
  		case ID.ShipClass.BattleshipYamato:
  		case ID.ShipClass.CarrierKaga:
  		case ID.ShipClass.CarrierAkagi:
  			amount[0] = new ItemStack(ModBlocks.BlockGrudge, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1));
  			amount[1] = new ItemStack(ModBlocks.BlockAbyssium, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1));
  			amount[2] = new ItemStack(ModItems.Ammo, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1), 1);
        	amount[3] = new ItemStack(ModBlocks.BlockPolymetal, ConfigHandler.kaitaiAmountLarge + rand.nextInt(ConfigHandler.kaitaiAmountLarge + 1));
  			break;
  		}
  		
  		return amount;
  	}
  	
	
}