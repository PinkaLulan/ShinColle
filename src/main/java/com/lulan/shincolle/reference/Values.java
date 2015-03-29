package com.lulan.shincolle.reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Values {
	/**SHIP ARRAY ID
	 * 0:DeI   1:DeRO  2:DeHA  3:DeNI  4:LCHO  5:LCHE  6:LCTO  7:LCTSU
	 * 8:TCCHI 9:HCRI  10:HCNE 11:CaNU 12:CaWO 13:BaRU 14:BaTA 15:BaRE
	 * 16:TrWA 17:SuKA 18:SuYO 19:SuSO 20:ACH  21:AFH  22:ArmH 23:AncH
	 * 24:EscF 25:FloF 26:BaH  27:DeH  28:HbH  29:IsD  30:MidH 31:NorH 
	 * 32:SouH 33:ACWD 34:LCD  35:BaWD 36:DeSh 
	 */
	public static final float[] BaseHP = 
		{20F,  22F,  24F,  28F,  33F,  36F,  39F,  48F,
		 48F,  58F,  62F,  65F,  85F,  90F,  84F,  90F,
		 70F,  50F,  44F,  35F,  175F, 250F, 135F, 150F,
		 33F,  44F,  200F, 95F,  250F, 225F, 300F, 250F, 
		 175F, 195F, 1F,   1F,   23F};
	
	public static final float[] BaseATK = 
		{3F,   4F,   3F,   4F,   6F,   9F,   7F,   12F,
		 16F,  14F,  13F,  16F,  19F,  24F,  19F,  28F,
		 0F,   0F,   0F,   0F,   0F,   0F,   0F,   0F,
		 0F,   0F,   0F,   0F,   0F,   0F,   0F,   0F,
		 0F,   0F,   0F,   0F,   9F};

	public static final float[] BaseDEF = 
		{5F,   6F,   7F,   9F,   12F,  15F,  17F,  19F,
		 22F,  24F,  25F,  20F,  21F,  23F,  24F,  26F,
		 10F,  7F,   8F,   10F,  40F,  35F,  30F,  28F,
		 12F,  14F,  45F,  25F,  40F,  35F,  50F,  35F, 
		 38F,  50F,  0F,   0F,   12F};

	public static final float[] BaseSPD = 
		{0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F};

	public static final float[] BaseMOV = 
		{0.5F,  0.5F,  0.5F,  0.5F,  0.45F, 0.45F, 0.45F, 0.45F,
		 0.42F, 0.42F, 0.42F, 0.32F, 0.4F,  0.32F, 0.36F, 0.36F,
		 0.3F,  0.3F,  0.3F,  0.28F, 0.45F, 0.3F,  0.42F, 0.3F,
		 0.5F,  0.5F,  0.42F, 0.5F,  0.2F,  0.22F, 0.2F,  0.25F,
		 0.3F,  0.42F, 0.45F, 0.42F, 0.6F};

	public static final float[] BaseHIT =
		{6F,    6F,    6F,    6F,    9F,    9F,    9F,    9F,
		 11F,   11F,   11F,   16F,   20F,   13F,   13F,   13F,
		 6F,    6F,    6F,    6F,    24F,   30F,   24F,   28F,
		 6F,    6F,    20F,   11F,   32F,   26F,   34F,   30F,
		 28F,   28F,   0F,    0F,    9F};
	
	public static final float[] ModHP =
		{0.3F,  0.32F, 0.34F, 0.36F, 0.38F, 0.4F,  0.42F, 0.44F,
		 0.46F, 0.48F, 0.5F,  0.52F, 0.65F, 0.7F,  0.65F, 0.7F,
		 0.6F,  0.35F, 0.33F, 0.3F,  1F,    1.2F,  0.9F,  0.95F,
		 0.4F,  0.4F,  1.1F,  0.7F,  1.3F,  1.3F,  1.4F,  1.15F,
		 1F,    1.05F, 0F,    0F,    0.3F};
	
	public static final float[] ModATK = 
		{0.25F, 0.28F, 0.25F, 0.28F, 0.3F,  0.35F, 0.32F, 0.38F,
		 0.44F, 0.42F, 0.4F,  0.4F,  0.5F,  0.7F,  0.6F,  0.8F,
		 0.25F, 1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 0.5F,  0.5F,  1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    0F,    0F,    0.5F};
	
	public static final float[] ModDEF = 
		{0.5F,  0.52F, 0.54F, 0.56F, 0.6F,  0.62F, 0.64F, 0.66F,
		 0.7F,  0.7F,  0.7F,  0.7F,  0.7F,  0.9F,  0.9F,  0.9F,
		 0.5F,  0.54F, 0.52F, 0.5F,  1F,    1F,    1F,    1F,
		 0.5F,  0.5F,  1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    0.6F};
	
	public static final float[] ModSPD = 
		{0.5F,  0.5F,  0.5F,  0.5F,  0.6F,  0.6F,  0.6F,  0.6F,
		 0.7F,  0.7F,  0.7F,  0.7F,  0.8F,  1F,    1F,    1F,
		 0.5F,  0.7F,  0.7F,  0.7F,  1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    1F};
	
	public static final float[] ModMOV = 	//mark for exists ship
		{1F,    1F,    1F,    1F,    0F,    0F,    0F,    0F,
		 0F,    1F,    0F,    0F,    1F,    0F,    0F,    1F,
		 0F,    0F,    0F,    0F,    0F,    0F,    0F,    0F,
		 0F,    0F,    0F,    0F,    0F,    0F,    0F,    0F,
		 0F,    0F,    0F,    0F,    1F};
	
	public static final float[] ModHIT = 
		{0.25F, 0.25F, 0.25F, 0.25F, 0.4F,  0.4F,  0.4F,  0.4F,
		 0.5F,  0.5F,  0.5F,  0.8F,  1F,    1F,    1F,    1F,
		 0.5F,  0.8F,  0.8F,  0.8F,  1F,    1F,    1F,    1F,
		 0.6F,  0.6F,  1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    0.4F};
	
	/**EQUIP MAP
	 * equip state:
	 *		 0:equip type 1:hp 2:L.atk 3:H.atk 4:L.plane atk 5:H.plane atk 6:def 
	 *       7:spd 8:mov 9:hit 10:critical 11:double hit 12:triple hit 13:miss reduce
	 *       14:rare level 15:rare rate
	 * 
	 * equip type: 0:unused
	 *       1:cannon, torpedo
	 *       2:aircraft-R, engine, armor, radar
	 *       3:aircraft-T/F/B
	 */
	public static final Map<Byte, float[]> EquipMap = Collections.unmodifiableMap(
	new HashMap<Byte, float[]>() {{
	//single cannon
	put(ID.E_CANNON_SINGLE_5, new float[]{1F, 0F, 2F, 0F, 0F, 0F, 0F, 0F, 0F, 1F, 0F, 0F, 0F, 0F, ID.EquipType.CANNON_SI, 150F});
	put(ID.E_CANNON_SINGLE_6, new float[]{1F, 0F, 3F, 0F, 0F, 0F, 0F, 0F, 0F, 2F, 0F, 0F, 0F, 0F, ID.EquipType.CANNON_SI, 150F});
	//twin cannon
	put(ID.E_CANNON_TWIN_5,   new float[]{1F, 0F, 4F, 0F, 0F, 0F, 0F, 0.2F, -0.02F, 1.5F, 0F, 0.05F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 250});
	put(ID.E_CANNON_TWIN_6,   new float[]{1F, 0F, 5F, 0F, 0F, 0F, 0F, 0.4F, -0.03F, 2.0F, 0F, 0.05F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 200});
	put(ID.E_CANNON_TWIN_5DP, new float[]{1F, 0F, 5F, 0F, 0F, 0F, 0F, 0.25F, -0.04F, 2.0F, 0F, 0.06F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 180});
	put(ID.E_CANNON_TWIN_125, new float[]{1F, 0F, 8F, 0F, 0F, 0F, 0F, 0.2F, -0.06F, 3.0F, 0F, 0.06F, 0F, 0F, ID.EquipType.CANNON_TW_LO, 160});
	put(ID.E_CANNON_TWIN_14,  new float[]{1F, 0F, 11F, 0F, 0F, 0F, 0F, 0.2F, -0.08F, 4.0F, 0F, 0.07F, 0F, 0F, ID.EquipType.CANNON_TW_HI, 120});
	put(ID.E_CANNON_TWIN_16,  new float[]{1F, 0F, 15F, 0F, 0F, 0F, 0F, 0.2F, -0.1F, 4.5F, 0F, 0.08F, 0F, 0F, ID.EquipType.CANNON_TW_HI, 80});
	put(ID.E_CANNON_TWIN_20,  new float[]{1F, 0F, 20F, 0F, 0F, 0F, 0F, 0.2F, -0.15F, 5.5F, 0F, 0.10F, 0F, 0F, ID.EquipType.CANNON_TW_HI, 50});
	//triple cannon
	put(ID.E_CANNON_TRI_8,    new float[]{1F, 0F, 9F, 0F, 0F, 0F, 0F, 0.4F, -0.1F, 3.0F, 0F, 0.06F, 0.06F, 0F, ID.EquipType.CANNON_TR, 80});
	put(ID.E_CANNON_TRI_16,   new float[]{1F, 0F, 17F, 0F, 0F, 0F, 0F, 0.4F, -0.2F, 4.5F, 0F, 0.08F, 0.08F, 0F, ID.EquipType.CANNON_TR, 50});
	//torpedo
	put(ID.E_TORPEDO_21MK1,      new float[]{1F, 0F, 0F, 12F, 0F, 0F, 0F, 0.2F, -0.04F, 0F, 0.06F, 0F, 0F, 0F, ID.EquipType.TORPEDO_LO, 150});
	put(ID.E_TORPEDO_21MK2,      new float[]{1F, 0F, 0F, 20F, 0F, 0F, 0F, 0.25F, -0.05F, 0F, 0.08F, 0F, 0F, 0F, ID.EquipType.TORPEDO_LO, 130});
	put(ID.E_TORPEDO_22MK1,      new float[]{1F, 0F, 0F, 50F, 0F, 0F, 0F, 0.3F, -0.06F, 0F, 0.10F, 0F, 0F, 0F, ID.EquipType.TORPEDO_LO, 110});
	put(ID.E_TORPEDO_CUTTLEFISH, new float[]{1F, 0F, 0F, 80F, 0F, 0F, 0F, 0.35F, -0.08F, 4.0F, 0.12F, 0F, 0F, 0F, ID.EquipType.TORPEDO_HI, 80});
	put(ID.E_TORPEDO_HIGHSPEED,  new float[]{1F, 0F, 0F, 80F, 0F, 0F, 0F, 0.4F, -0.1F, 5.0F, 0.14F, 0F, 0F, 0F, ID.EquipType.TORPEDO_HI, 50});
	//Torpedo aircraft
	put(ID.E_AIRCRAFT_TMK1,     new float[]{3F, 0F, 0F, 0F, 2F, 15F, 0F, 0F, -0.08F, 6.0F, 0.06F, 0F, 0F, 0F, ID.EquipType.AIR_T_LO, 180});
	put(ID.E_AIRCRAFT_TMK2,     new float[]{3F, 0F, 0F, 0F, 6F, 30F, 0F, 0F, -0.1F, 6.0F, 0.08F, 0F, 0F, 0F, ID.EquipType.AIR_T_LO, 150});
	put(ID.E_AIRCRAFT_TMK3,     new float[]{3F, 0F, 0F, 0F, 11F, 60F, 0F, 0F, -0.14F, 7.0F, 0.10F, 0F, 0F, 0F, ID.EquipType.AIR_T_LO, 130});
	put(ID.E_AIRCRAFT_TAVENGER, new float[]{3F, 0F, 0F, 0F, 8F, 100F, 0F, 0.25F, -0.2F, 9.0F, 0.12F, 0F, 0F, 0F, ID.EquipType.AIR_T_HI, 80});
	//Fighter aircraft
	put(ID.E_AIRCRAFT_FMK1,     new float[]{3F, 0F, 0F, 0F, 3F, 0F, 0F, 0.3F, -0.04F, 5.0F, 0F, 0F, 0F, 0F, ID.EquipType.AIR_F_LO, 180});
	put(ID.E_AIRCRAFT_FMK2,     new float[]{3F, 0F, 0F, 0F, 5F, 0F, 0F, 0.35F, -0.06F, 5.0F, 0F, 0F, 0F, 0F, ID.EquipType.AIR_F_LO, 150});
	put(ID.E_AIRCRAFT_FMK3,     new float[]{3F, 0F, 0F, 0F, 8F, 0F, 0F, 0.4F, -0.08F, 6.0F, 0F, 0F, 0F, 0F, ID.EquipType.AIR_F_LO, 130});
	put(ID.E_AIRCRAFT_FFLYFISH, new float[]{3F, 0F, 0F, 0F, 12F, 0F, 0F, 0.45F, -0.1F, 6.0F, 0F, 0F, 0F, 0.01F, ID.EquipType.AIR_F_HI, 100});
	put(ID.E_AIRCRAFT_FHELLCAT, new float[]{3F, 0F, 0F, 0F, 10F, 0F, 0F, 0.55F, -0.12F, 8.0F, 0F, 0F, 0F, 0.02F, ID.EquipType.AIR_F_HI, 80});
	//Bomber aircraft
	put(ID.E_AIRCRAFT_BMK1,     new float[]{3F, 0F, 0F, 0F, 12F, 0F, 0F, 0.0F, -0.06F, 3.0F, 0.12F, 0F, 0F, 0F, ID.EquipType.AIR_B_LO, 150});
	put(ID.E_AIRCRAFT_BMK2,     new float[]{3F, 0F, 0F, 0F, 18F, 0F, 0F, 0.0F, -0.08F, 3.0F, 0.16F, 0F, 0F, 0F, ID.EquipType.AIR_B_LO, 130});
	put(ID.E_AIRCRAFT_BFLYFISH, new float[]{3F, 0F, 0F, 0F, 24F, 0F, 0F, 0.0F, -0.11F, 4.0F, 0.20F, 0F, 0F, 0F, ID.EquipType.AIR_B_HI, 100});
	put(ID.E_AIRCRAFT_BHELL,    new float[]{3F, 0F, 0F, 0F, 21F, 0F, 0F, 0.25F, -0.15F, 5.0F, 0.24F, 0F, 0F, 0F, ID.EquipType.AIR_B_HI, 80});
	//Recon aircraft
	put(ID.E_AIRCRAFT_R,        new float[]{2F, 0F, 2F, 2F, 2F, 2F, 0F, 0.2F, -0.1F, 8.0F, 0.05F, 0F, 0F, 0.04F, ID.EquipType.AIR_R_LO, 120});
	put(ID.E_AIRCRAFT_RFLYFISH, new float[]{2F, 0F, 4F, 4F, 4F, 4F, 0F, 0.3F, -0.15F, 12.0F, 0.10F, 0F, 0F, 0.08F, ID.EquipType.AIR_R_HI, 90});
	//radar
	put(ID.E_RADAR_AIRMK1,   new float[]{2F, 0F, 2F, 0F, 2F, 0F, 5F, 0F, -0.04F, 5.0F, 0.03F, 0F, 0F, 0.04F, ID.EquipType.RADAR_LO, 180});
	put(ID.E_RADAR_AIRMK2,   new float[]{2F, 0F, 4F, 0F, 4F, 0F, 8F, 0F, -0.06F, 8.0F, 0.06F, 0F, 0F, 0.08F, ID.EquipType.RADAR_LO, 150});
	put(ID.E_RADAR_SURMK1,   new float[]{2F, 0F, 0F, 8F, 0F, 8F, 5F, 0F, -0.04F, 5.0F, 0.03F, 0F, 0F, 0.04F, ID.EquipType.RADAR_LO, 180});
	put(ID.E_RADAR_SURMK2,   new float[]{2F, 0F, 0F, 16F, 0F, 16F, 8F, 0F, -0.06F, 8.0F, 0.06F, 0F, 0F, 0.08F, ID.EquipType.RADAR_LO, 150});
	put(ID.E_RADAR_SONAR,    new float[]{2F, 0F, 2F, 4F, 2F, 4F, 3F, 0F, -0.02F, 2.0F, 0.02F, 0F, 0F, 0.03F, ID.EquipType.RADAR_LO, 180});
	put(ID.E_RADAR_AIRABYSS, new float[]{2F, 0F, 6F, 0F, 6F, 0F, 15F, 0F, -0.08F, 12.0F, 0.09F, 0F, 0F, 0.12F, ID.EquipType.RADAR_HI, 130});
	put(ID.E_RADAR_SURABYSS, new float[]{2F, 0F, 0F, 20F, 0F, 20F, 15F, 0F, -0.08F, 12.0F, 0.09F, 0F, 0F, 0.12F, ID.EquipType.RADAR_HI, 130});	
	put(ID.E_RADAR_SONARMK2, new float[]{2F, 0F, 4F, 10F, 4F, 10F, 6F, 0F, -0.04F, 3.0F, 0.04F, 0F, 0F, 0.06F, ID.EquipType.RADAR_HI, 130});
	//turbine
	put(ID.E_TURBINE,     new float[]{2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.15F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.TURBINE_LO, 130});
	put(ID.E_TURBINE_IMP, new float[]{2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.2F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.TURBINE_LO, 110});
	put(ID.E_TURBINE_ENH, new float[]{2F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0.25F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.TURBINE_HI, 80});
	//armor
	put(ID.E_ARMOR,     new float[]{2F, 30F, 0F, 0F, 0F, 0F, 5F, 0F, -0.1F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.ARMOR_LO, 350});
	put(ID.E_ARMOR_ENH, new float[]{2F, 60F, 0F, 0F, 0F, 0F, 10F, 0F, -0.2F, 0F, 0F, 0F, 0F, 0F, ID.EquipType.ARMOR_HI, 300});
	}}
	);
	
}
