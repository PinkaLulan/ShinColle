package com.lulan.shincolle.reference;

public class AttrValues {
	/**SHIP ARRAY ID
	 * 0:DeI   1:DeRO  2:DeHA  3:DeNI  4:LCHO  5:LCHE  6:LCTO  7:LCTSU
	 * 8:TCCHI 9:HCRI  10:HCNE 11:CaNU 12:CaWO 13:BaRU 14:BaTA 15:BaRE
	 * 16:TrWA 17:SuKA 18:SuYO 19:SuSO 20:CaH  21:AFH  22:ArmH 23:AncH
	 * 24:EscF 25:FloF 26:BaH  27:DeH  28:HbH  29:IsH  30:MidH 31:NorH 32:SouH
	 */
	
	public static final byte[] BaseHP = 
		{20, 40, 40, 40, 40, 40, 40, 40,
		 40, 40, 40, 40, 40, 40, 40, 40,
		 40, 40, 40, 40, 40, 40, 40, 40,
		 40, 40, 40, 40, 40, 40, 40, 40, 40 };
	
	public static final byte[] BaseATK = 
		{4, 4, 4, 4, 4, 4, 4, 4,
		 4, 4, 4, 4, 4, 4, 4, 4,
		 4, 4, 4, 4, 4, 4, 4, 4,
		 4, 4, 4, 4, 4, 4, 4, 4, 4};

	public static final byte[] BaseDEF = 
		{0, 0, 0, 0, 0, 0, 0, 0,
		 0, 0, 0, 0, 0, 0, 0, 0,
		 0, 0, 0, 0, 0, 0, 0, 0,
		 0, 0, 0, 0, 0, 0, 0, 0, 0};

	public static final float[] BaseSPD = 
		{0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F,
		 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F,
		 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F,
		 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F, 0.4F};

	public static final float[] BaseMOV = 
		{0.36F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F,
		 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F,
		 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F,
		 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F, 0.3F};

	public static final float[] BaseHIT = 
		{5F, 10F, 10F, 10F, 10F, 10F, 10F, 10F,
		 10F, 10F, 10F, 10F, 10F, 10F, 10F, 10F,
		 10F, 10F, 10F, 10F, 10F, 10F, 10F, 10F,
		 10F, 10F, 10F, 10F, 10F, 10F, 10F, 10F, 10f};
	
	public static final float[] ModHP = 
		{0.3F, 1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F, 1f};
	
	public static final float[] ModATK = 
		{0.25F, 1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,    1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,    1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,    1F, 1F, 1F, 1F, 1F, 1F, 1F, 1f};
	
	public static final float[] ModDEF = 
		{0.3F, 1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F, 1f};
	
	public static final float[] ModSPD = 
		{0.5F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F, 1f};
	
	public static final float[] ModMOV = 
		{1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F, 1f};
	
	public static final float[] ModHIT = 
		{0.25F, 1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F,
		 1F,   1F, 1F, 1F, 1F, 1F, 1F, 1F, 1f};
	
	/**EQUIP ARRAY ID
	 * 0:5SigC 1:6SigC 2:5TwnC 3:6TwnC 4:12.5TwnC 5:14TwnC 6:16TwnC 
	 * 7:8TriC 8:16TriC
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
		public static final byte NORMAL = 0;			//µLªí±¡
		public static final byte BLINK = 1;				//¯w²´
		public static final byte T_T = 2;				//sad
		public static final byte O_O = 3;				//...
	}
	
	public static final class ShipType {				//for GUI display
		public static final byte DESTROYER = 0;			//ÅX³v	ÅX³vÄ¥
		public static final byte LIGHT_CRUISER = 1;		//»´¨µ	»´¨µ¬vÄ¥
		public static final byte HEAVY_CRUISER = 2;		//­«¨µ	­«¨µ¬vÄ¥
		public static final byte TORPEDO_CRUISER = 3;	//¹p¨µ 	­«¹p¸Ë¨µ¬vÄ¥
		public static final byte LIGHT_CARRIER = 4;		//»´¥À	»´¯èªÅ¥ÀÄ¥
		public static final byte STANDARD_CARRIER = 5;	//¯è		¯èªÅ¥ÀÄ¥
		public static final byte BATTLESHIP	= 6;		//¾Ô		¾ÔÄ¥
		public static final byte TRANSPORT = 7;			//¸Éµ¹	¿é°eÄ¥
		public static final byte SUBMARINE = 8;			//¼ç		¼ç¤ô¸¥
		public static final byte ONI = 9;				//°­		°­¯Å
		public static final byte HIME = 10;				//®V		®V¯Å
		public static final byte FORTRESS = 11;			//¯B		¯B´å­n¶ë/Å@½Ã­n¶ë		
	}
	
	public static final class State {
		public static final byte NORMAL = 0;			//´¶³q
		public static final byte NORMAL_ATK = 1;		//´¶³q §ðÀ»ª¬ºA
		public static final byte MINOR = 10;			//¤p¯}
		public static final byte MINOR_ATK = 11;
		public static final byte MODERATE = 20;			//¤¤¯}
		public static final byte MODERATE_ATK = 21;
		public static final byte HEAVY = 30;			//¤j¯}
		public static final byte HEAVY_ATK = 31;
	}
	
	
	
}
