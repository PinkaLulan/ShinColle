package com.lulan.shincolle.reference;

public class AttrValues {
	/**SHIP ARRAY ID
	 * 0:DeI   1:DeRO  2:DeHA  3:DeNI  4:LCHO  5:LCHE  6:LCTO  7:LCTSU
	 * 8:TCCHI 9:HCRI  10:HCNE 11:CaNU 12:CaWO 13:BaRU 14:BaTA 15:BaRE
	 * 16:TrWA 17:SuKA 18:SuYO 19:SuSO 20:ACH  21:AFH  22:ArmH 23:AncH
	 * 24:EscF 25:FloF 26:BaH  27:DeH  28:HbH  29:IsD  30:MidH 31:NorH 
	 * 32:SouH 33:ACWD
	 */
	
	public static final float[] BaseHP = 
		{20F,  22F,  24F,  28F,  33F,  36F,  39F,  48F,
		 48F,  58F,  62F,  65F,  85F,  90F,  84F,  90F,
		 70F,  50F,  44F,  35F,  175F, 250F, 135F, 150F,
		 33F,  44F,  200F, 95F,  250F, 225F, 300F, 250F, 
		 175F, 195F};
	
	public static final float[] BaseATK = 
		{3F,   4F,   3F,   4F,   10F,  14F,  12F,  17F,
		 23F,  21F,  19F,  25F,  40F,  40F,  33F,  50F,
		 1F,   40F,  45F,  50F,  0F,   0F,   0F,   0F,
		 0F,   0F,   65F,  25F,  0F,   0F,   0F,   0F,
		 55F,  0F};

	public static final float[] BaseDEF = 
		{5F,   6F,   7F,   9F,   12F,  15F,  17F,  19F,
		 22F,  24F,  25F,  20F,  21F,  25F,  26F,  28F,
		 10F,  7F,   8F,   10F,  40F,  35F,  30F,  28F,
		 12F,  14F,  45F,  25F,  40F,  35F,  50F,  35F, 
		 38F,  50F};

	public static final float[] BaseSPD = 
		{0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,  0.4F,
		 0.4F,  0.4F};

	public static final float[] BaseMOV = 
		{0.5F,  0.5F,  0.5F,  0.5F,  0.45F, 0.45F, 0.45F, 0.45F,
		 0.42F, 0.42F, 0.42F, 0.32F, 0.4F,  0.34F, 0.36F, 0.36F,
		 0.3F,  0.3F,  0.3F,  0.28F, 0.45F, 0.3F,  0.42F, 0.3F,
		 0.5F,  0.5F,  0.42F, 0.5F,  0.2F,  0.22F, 0.2F,  0.25F,
		 0.3F,  0.42F};

	public static final float[] BaseHIT = 
		{6F,    6F,    6F,    6F,    12F,   12F,   12F,   12F,
		 14F,   14F,   14F,   20F,   24F,   16F,   16F,   18F,
		 9F,    9F,    9F,    9F,    26F,   36F,   26F,   32F,
		 8F,    8F,    24F,   16F,   40F,   32F,   44F,   36F,
		 36F,   28F};
	
	public static final float[] ModHP = 
		{0.3F,  0.32F, 0.34F, 0.36F, 0.38F, 0.4F,  0.42F, 0.44F,
		 0.46F, 0.48F, 0.5F,  0.52F, 0.65F, 0.7F,  0.65F, 0.7F,
		 0.6F,  0.35F, 0.33F, 0.3F,  1F,    1.4F,  0.9F,  0.95F,
		 0.4F,  0.4F,  1.1F,  0.7F,  1.5F,  1.3F,  1.6F,  1.35F,
		 1F,    1.05F};
	
	public static final float[] ModATK = 
		{0.25F, 0.28F, 0.25F, 0.28F, 0.3F,  0.35F, 0.32F, 0.38F,
		 0.44F, 0.42F, 0.4F,  0.4F,  0.5F,  0.7F,  0.6F,  0.8F,
		 0.25F, 1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 0.5F,  0.5F,  1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F};
	
	public static final float[] ModDEF = 
		{0.5F,  0.52F, 0.54F, 0.56F, 0.6F,  0.62F, 0.64F, 0.66F,
		 0.7F,  0.7F,  0.7F,  0.7F,  0.7F,  0.9F,  0.9F,  0.9F,
		 0.5F,  0.54F, 0.52F, 0.5F,  1F,    1F,    1F,    1F,
		 0.5F,  0.5F,  1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F};
	
	public static final float[] ModSPD = 
		{0.5F,  0.5F,  0.5F,  0.5F,  0.6F,  0.6F,  0.6F,  0.6F,
		 0.7F,  0.7F,  0.7F,  0.7F,  0.8F,  1F,    1F,    1F,
		 0.5F,  0.7F,  0.7F,  0.7F,  1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F};
	
	public static final float[] ModMOV = 
		{1F,    1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F,    1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F};
	
	public static final float[] ModHIT = 
		{0.25F, 0.25F, 0.25F, 0.25F, 0.4F,  0.4F,  0.4F,  0.4F,
		 0.5F,  0.5F,  0.5F,  0.8F,  1F,    1F,    1F,    1F,
		 0.5F,  0.8F,  0.8F,  0.8F,  1F,    1F,    1F,    1F,
		 0.6F,  0.6F,  1F,    1F,    1F,    1F,    1F,    1F,
		 1F,    1F};
	
	/**EQUIP ARRAY ID
	 * 0:5SigC 1:6SigC 2:5TwnC 3:6TwnC 4:12.5TwnC 5:14TwnC 6:16TwnC 7:8TriC 8:16TriC
	 */
	public static final float[] EquipHP = 
		{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
	
	public static final float[] EquipATK = 
		{2F, 3F, 3F, 4F, 8F, 12F, 14F, 9F, 18F};
	
	public static final float[] EquipDEF = 
		{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
	
	public static final float[] EquipSPD = 
		{0F, 0F, 0.2F, 0.4F, 0.2F, 0.2F, 0.2F, 0.4F, 0.4F};
	
	public static final float[] EquipMOV = 
		{0F, 0F, -0.04F, -0.04F, -0.08F, -0.08F, -0.08F, -0.12F, -0.12F};
	
	public static final float[] EquipHIT = 
		{1F, 1F, 1F, 1F, 2F, 3F, 4F, 3F, 4F};
	
	
	public static final class Emotion {
		public static final byte NORMAL = 0;			//無表情
		public static final byte BLINK = 1;				//眨眼
		public static final byte T_T = 2;				//sad
		public static final byte O_O = 3;				//...
		public static final byte BORED = 4;				//坐下時隨機抽的無聊表情
		public static final byte HUNGRY = 5;			//no grudge
	}
	
	public static final class ShipType {				//for GUI display
		public static final byte DESTROYER = 0;			//驅逐	驅逐艦
		public static final byte LIGHT_CRUISER = 1;		//輕巡	輕巡洋艦
		public static final byte HEAVY_CRUISER = 2;		//重巡	重巡洋艦
		public static final byte TORPEDO_CRUISER = 3;	//雷巡 	重雷裝巡洋艦
		public static final byte LIGHT_CARRIER = 4;		//輕母	輕航空母艦
		public static final byte STANDARD_CARRIER = 5;	//航		航空母艦
		public static final byte BATTLESHIP	= 6;		//戰		戰艦
		public static final byte TRANSPORT = 7;			//補給	輸送艦
		public static final byte SUBMARINE = 8;			//潛		潛水艇
		public static final byte ONI = 9;				//鬼		鬼級
		public static final byte HIME = 10;				//姬		姬級
		public static final byte FORTRESS = 11;			//浮		浮游要塞/護衛要塞		
	}
	
	public static final class State {
		public static final byte NORMAL = 0;			//普通
		public static final byte EQUIP = 20;			//艤裝狀態
		public static final byte NORMAL_MINOR = 1;		//小破
		public static final byte EQUIP_MINOR = 21;
		public static final byte NORMAL_MODERATE = 2;	//中破
		public static final byte EQUIP_MODERATE = 22;
		public static final byte NORMAL_HEAVY = 3;		//大破
		public static final byte EQUIP_HEAVY = 23;
	}
	
	
	
}
