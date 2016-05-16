package com.lulan.shincolle.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

/**SHIP SPAWN CALC <br>
 * Material Bonus Rate (for small construction)
 *		Grudge    -> HP ATK DEF SPD MOV HIT	<br>
 *      Abyssium  -> HP DEF					<br>
 *      Ammo      -> ATK SPD				<br>
 * 		Polymetal -> MOV HIT				<br><br>
 * 
 * base rate: {64,16,4,1}  					<br>
 * +3 rate: base + amount * 0.25			<br>
 * +2 rate: base + amount * 0.375			<br>
 * +1 rate: base + amount * 0.75			<br>
 * +0 rate: base + amount * 0.25			<br>
 * 
 * min consume(all 16) = {72,40,16,9}   = {52.6%, 29.2%, 11.7%, 6.6%}
 * max consume(all 64) = {96,112,52,33} = {32.8%, 38.2%, 17.7%, 11.3%}
 * 
 * 
 * Ship Build Rate: first roll -> second roll
 * 	 1. FIRST: roll ship type
 * 
 * 	 2. SECOND: roll ships of the type
 */
public class ShipCalc {
	
	private static Random rand = new Random();
	private static final float[] baseRate = new float[] {64F, 16F, 4F, 1F};
	
	//roll table
	private static List<int[]> EquipSmall = new ArrayList<int[]>();
	private static List<int[]> EquipLarge = new ArrayList<int[]>();
	
	//init roll table
	static {
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
		EquipLarge.add(new int[] {ID.Ship.CarrierWO,       500,  3});
		EquipLarge.add(new int[] {ID.Ship.BattleshipTA,    800,  2});
		EquipLarge.add(new int[] {ID.Ship.NorthernHime,    2600, 1});
		EquipLarge.add(new int[] {ID.Ship.HarbourHime,     2800, 1});
		EquipLarge.add(new int[] {ID.Ship.AirfieldHime,    3000, 1});
		EquipLarge.add(new int[] {ID.Ship.CarrierHime,     3000, 3});
		EquipLarge.add(new int[] {ID.Ship.BattleshipRE,    3800, 2});	
		EquipLarge.add(new int[] {ID.Ship.BattleshipHime,  4600, 2});
		EquipLarge.add(new int[] {ID.Ship.CarrierWD,       5000, 3});
	}
	

	//get material amount from nbt data
	private static int[] getMatAmount(ItemStack itemstack) {
		int[] matAmount = {0,0,0,0};	//grudge, abyssium, ammo, polymetal
				
		if(itemstack.hasTagCompound()) {		
			matAmount[0] = itemstack.getTagCompound().getInteger("Grudge");
			matAmount[1] = itemstack.getTagCompound().getInteger("Abyssium");
			matAmount[2] = itemstack.getTagCompound().getInteger("Ammo");
			matAmount[3] = itemstack.getTagCompound().getInteger("Polymetal");
		}
	
		LogHelper.info("DEBUG : shipcalc get matAmount : "+matAmount[0]+" "+matAmount[1]+" "+matAmount[2]+" "+matAmount[3]);
		return matAmount;
	}

	//roll ship type by total number of materials
	public static int rollShipType(ItemStack item) {
		List<int[]> shipList = new ArrayList<int[]>();
		int[] material = new int[4];
		int totalMats = 0;
		
		if(item.getItemDamage() > 1) {	//is special egg
			return item.getItemDamage() - 2;
		}
		
		//get materials amount
    	if (item.stackTagCompound != null) { 	//正常製造egg, 會有四個材料tag		
    		material[0] = item.stackTagCompound.getInteger("Grudge");
    		material[1] = item.stackTagCompound.getInteger("Abyssium");
    		material[2] = item.stackTagCompound.getInteger("Ammo");
    		material[3] = item.stackTagCompound.getInteger("Polymetal");
    		
    		totalMats = material[0] + material[1] + material[2] + material[3];
        }

    	List<int[]> shiplistOrg = null;  //raw ship list: 0:ship ID, 1:mean 2:specific material
		float te = 4000F;	//debug
		
		//get equip list by build type
		if(item.getItemDamage() == 0) {	 //small egg
			shiplistOrg = EquipSmall;
			te = 256F;
		}
		else {
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
		
		for(int[] i : shiplistOrg) {
			//get modified material type
			if(i[2] >= 0 && i[2] <= 3) {
				meanNew = i[1] - material[i[2]];
			}
			else {
				meanNew = i[1];
			}
			
			//get mean distance
			meanDist = MathHelper.abs_int(totalMats - meanNew);
			
			//mean value to prob value
			if(item.getItemDamage() == 0) {	 //small egg
				//for small build, max material = 256
				//change scale to large build resolution
				meanDist = (int) (meanDist * 15.625F); // = mat / 256 * 4000
			}
			
			prob = CalcHelper.getNormDist(meanDist);
			//add to map
			probList.put(i[0], prob);
			LogHelper.info("DEBUG: roll ship type: prob list: ID "+i[0]+" MEAN(ORG) "+i[1]+" MEAN(NEW) "+meanNew+" MEAN(P) "+(meanNew/te)+" MD "+meanDist+" PR "+prob);
		}
		
		float random = rand.nextFloat();
		float totalProb = 0F;
		float sumProb = 0.0125F;	//init value to prevent float comparison bug
		int rollresult = -1;

		//get total prob
		Iterator iter = probList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			totalProb += (Float) entry.getValue();
		}
		
		//scale random number to totalProb
		random *= totalProb;
		
		//roll ship
		iter = probList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sumProb += (Float) entry.getValue();
			LogHelper.info("DEBUG: roll ship type: random: "+random+" sum.pr "+sumProb+" total.pr "+totalProb);
			if(sumProb > random) {	//get item
				rollresult = (Integer) entry.getKey();
				LogHelper.info("DEBUG: roll ship type: get ship:"+rollresult);
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
  	public static String getEntityToSpawnName(int type) {
  		switch(type) {
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
  		case ID.Ship.HarbourHime:
  			return "shincolle.EntityHarbourHime";
  		case ID.Ship.NorthernHime:
  			return "shincolle.EntityNorthernHime";
  		case ID.Ship.CarrierWD:
  			return "shincolle.EntityCarrierWD";
  		case ID.Ship.DestroyerShimakaze:
  			return "shincolle.EntityDestroyerShimakaze";
  		case ID.Ship.DestroyerShimakaze+2000:
  			return "shincolle.EntityDestroyerShimakazeBoss";
  		case ID.Ship.BattleshipNagato:
  			return "shincolle.EntityBattleshipNGT";
  		case ID.Ship.BattleshipNagato+2000:
  			return "shincolle.EntityBattleshipNGTBoss";
  		case ID.Ship.BattleshipYamato:
  			return "shincolle.EntityBattleshipYMT";
  		case ID.Ship.BattleshipYamato+2000:
  			return "shincolle.EntityBattleshipYMTBoss";
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
  			return "shincolle.EntityCarrierKagaBoss";
  		case ID.Ship.CarrierAkagi:
  			return "shincolle.EntityCarrierAkagi";
  		case ID.Ship.CarrierAkagi+2000:
  			return "shincolle.EntityCarrierAkagiBoss";
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
  		default:
  			return "shincolle.EntityDestroyerI";
  		}	
  	}
  	
  	/** input: 0:small mob, 1:large mob, 2:small boss, 3:large boss
  	 */
  	public static String getRandomMobToSpawnName(int type)
  	{
  		switch(type)
  		{
  		case 2:  //small boss
  			switch(rand.nextInt(2))
        	{
        	default:
        		return getEntityToSpawnName(ID.Ship.DestroyerShimakaze+2000);
        	}
  		case 3:  //large boss
  			switch(rand.nextInt(4))
        	{
        	case 1:
        		return getEntityToSpawnName(ID.Ship.CarrierKaga+2000);
        	case 2:
        		return getEntityToSpawnName(ID.Ship.CarrierAkagi+2000);
        	case 3:
        		return getEntityToSpawnName(ID.Ship.BattleshipYamato+2000);
        	default:
        		return getEntityToSpawnName(ID.Ship.BattleshipNagato+2000);
        	}
  		case 1:  //large mob
  		default: //small mob
  			switch(rand.nextInt(6))
        	{
        	case 1:
        		return getEntityToSpawnName(ID.Ship.SubmarineRo500+2000);
        	case 2:
        		return getEntityToSpawnName(ID.Ship.DestroyerIkazuchi+2000);
        	case 3:
        		return getEntityToSpawnName(ID.Ship.DestroyerInazuma+2000);
        	case 4:
        		return getEntityToSpawnName(ID.Ship.DestroyerAkatsuki+2000);
        	case 5:
        		return getEntityToSpawnName(ID.Ship.DestroyerHibiki+2000);
        	default:
        		return getEntityToSpawnName(ID.Ship.SubmarineU511+2000);
        	}
  		}
  	}
  	
	
}
