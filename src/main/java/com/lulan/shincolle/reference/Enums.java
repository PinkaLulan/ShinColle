package com.lulan.shincolle.reference;

public class Enums
{
	
	/** color enum for gui
	 */
	public static enum EnumColors
	{
		//color value
		WHITE(16777215),
		YELLOW(16776960),
		ORANGE(16753920),
		RED_LIGHT(16724787),   //RED1 (1.7.10)
		GRAY_DARK(3158064),    //GRAY1 (1.7.10)
		BLACK(0),
		RED_DARK(11141120),    //RED2 (1.7.10)
		GRAY_LIGHT(11184810),  //GRAY2 (1.7.10)
		PINK(15515845),
		CYAN(65535),
		PURPLE_LIGHT(16581630),
		GRAY_MIDDLE(4210752),
		GRAY_DARK_HP(16119285),
		YELLOW_DARK_HP(13421568),
		ORANGE_DARK_HP(16747520),
		RED_DARK_HP(13107200);
		
		private final int colorValue;
		
		
		private EnumColors(int value)
		{
			this.colorValue = value;
		}

		public int getValue()
		{
			return colorValue;
		}
		
	}//end Colors
	
	public static enum EnumPathType
	{
	    BLOCKED,
	    OPEN,
	    FLUID,
	    OPENABLE,	//gate, wood door, trap door...
	    FENCE
	    
	}//end path type
	
    
}
