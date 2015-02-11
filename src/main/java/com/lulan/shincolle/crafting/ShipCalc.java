package com.lulan.shincolle.crafting;

import java.util.Random;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**SHIP DATA <br>
 * Material Bonus: Grudge    -> HP ATK DEF SPD MOV HIT	<br>
 *                 Abyssium  -> HP DEF					<br>
 *                 Ammo      -> ATK SPD					<br>
 * 		           Polymetal -> MOV HIT					<br><br>
 * 
 * base rate: {64,16,4,1}  <br>
 * +3 rate: base + amount * 0.25				<br>
 * +2 rate: base + amount * 0.375				<br>
 * +1 rate: base + amount * 0.75				<br>
 * +0 rate: base + amount * 0.25				<br>
 * 
 * min consume(all 16) = {72,40,16,9}   = {52.6%, 29.2%, 11.7%, 6.6%}
 * max consume(all 64) = {96,112,52,33} = {32.8%, 38.2%, 17.7%, 11.3%}
 */
public class ShipCalc {
	
	private static Random rand = new Random();
	private static final float[] baseRate = new float[] {64F, 16F, 4F, 1F};
	
	//in: itemstack(with materials amount)		out: ATK HP DEF SPD MOV HIT bonus value
	public static byte[] getBonusPoints(ItemStack itemstack) {
		byte[] bonusPoint = new byte[6];		//HP ATK DEF SPD MOV HIT
		byte[] matAmount = getMatAmount(itemstack);	//0:grudge 1:abyssium 2:ammo 3:polymetal
				
		float[] rateHP = calcBonusRate((float)(matAmount[0]+matAmount[1]));	  //HP = grudge + abyssium
		float[] rateATK = calcBonusRate((float)(matAmount[0]+matAmount[2]));  //ATK = grudge + ammo
		float[] rateDEF = calcBonusRate((float)(matAmount[0]+matAmount[1]));  //DEF = grudge + abyssium
		float[] rateSPD = calcBonusRate((float)(matAmount[0]+matAmount[2]));  //SPD = grudge + ammo
		float[] rateMOV = calcBonusRate((float)(matAmount[0]+matAmount[3]));  //MOV = grudge + polymetal
		float[] rateHIT = calcBonusRate((float)(matAmount[0]+matAmount[3]));  //HIT = grudge + polymetal

		bonusPoint[0] = rollBonusValue(rateHP);
		bonusPoint[1] = rollBonusValue(rateATK);
		bonusPoint[2] = rollBonusValue(rateDEF);
		bonusPoint[3] = rollBonusValue(rateSPD);
		bonusPoint[4] = rollBonusValue(rateMOV);
		bonusPoint[5] = rollBonusValue(rateHIT);
	
		return bonusPoint;
	}

	//get material amount from nbt data
	private static byte[] getMatAmount(ItemStack itemstack) {
		byte[] matAmount = {0,0,0,0};	//grudge, abyssium, ammo, polymetal
				
		if(itemstack.hasTagCompound()) {		
			matAmount[0] = itemstack.getTagCompound().getByte("Grudge");
			matAmount[1] = itemstack.getTagCompound().getByte("Abyssium");
			matAmount[2] = itemstack.getTagCompound().getByte("Ammo");
			matAmount[3] = itemstack.getTagCompound().getByte("Polymetal");
		}
	
		LogHelper.info("DEBUG : shipcalc get matAmount : "+matAmount[0]+" "+matAmount[1]+" "+matAmount[2]+" "+matAmount[3]);
		return matAmount;
	}

	//in: material amount		out: +0~+3 rate
	private static float[] calcBonusRate(float i) {
		float[] newRate = new float[4];
		float total = 1F;	//avoid divide by zero
		
		newRate[0] = baseRate[0] + i * 0.25F;		//+0
		newRate[1] = baseRate[1] + i * 0.75F;		//+1
		newRate[2] = baseRate[2] + i * 0.375F;		//+2
		newRate[3] = baseRate[3] + i * 0.25F;		//+3
		
		total = newRate[0]+newRate[1]+newRate[2]+newRate[3];
		
		newRate[0] /= total;
		newRate[1] /= total;
		newRate[2] /= total;
		newRate[3] /= total;	
		
		LogHelper.info("DEBUG : shipcalc baseRate : "+baseRate[0]+" "+baseRate[1]+" "+baseRate[2]+" "+baseRate[3]);
		LogHelper.info("DEBUG : shipcalc newRate : "+newRate[0]+" "+newRate[1]+" "+newRate[2]+" "+newRate[3]);
		return newRate;
	}
	
	//roll +0~+3 according to rate
	private static byte rollBonusValue(float[] rate) {
		float ranNum = (float)(rand.nextInt(100) + 1)/100F;		//random 0.01~1.00
		float bonus3 = rate[0]+rate[1]+rate[2];
		float bonus2 = rate[0]+rate[1];
		float bonus1 = rate[0];
		
		LogHelper.info("DEBUG : roll bonus ranNum : "+ranNum);
		LogHelper.info("DEBUG : rate3 : "+bonus3);
		LogHelper.info("DEBUG : rate2 : "+bonus2);
		LogHelper.info("DEBUG : rate1 : "+bonus1);
		
		if(ranNum > bonus3) {
			LogHelper.info("DEBUG : get bonus point : 3");
			return 3;
		}
		else if(ranNum > bonus2) {
			LogHelper.info("DEBUG : get bonus point : 2");
			return 2;
		}
		else if(ranNum > bonus1) {
			LogHelper.info("DEBUG : get bonus point : 1");
			return 1;
		}
		else {
			LogHelper.info("DEBUG : get bonus point : 0");
			return 0;
		}
	}
	

}
