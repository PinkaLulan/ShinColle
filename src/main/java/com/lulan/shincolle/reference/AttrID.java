package com.lulan.shincolle.reference;

/**SHIP DATA <br><br>
 * attribute array (no map for performance) <br><br>
 * Attr Short: 0:ShipBonusHP 1:ShipBonusATK 2:ShipBonusDEF <br>
 * Attr Float: 0:ShipBonusSPD 1:ShipBonusMOV 2:ShipBonusHIT <br>
 * EntityState: 0:State 1:Emotion 2:SwimType <br>
 * 
 */
public class AttrID {

	//Attr Short
	public static final byte HP = 0;
	public static final byte ATK = 1;
	public static final byte DEF = 2;
	//Attr Float
	public static final byte SPD = 0;
	public static final byte MOV = 1;
	public static final byte HIT = 2;
	//EntityState
	public static final byte State = 0;
	public static final byte Emotion = 1;
	public static final byte SwimType = 2;
		
}
