package com.lulan.shincolle.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;

/** HARD CODED VALUES
 *
 */
public class Values {
	
	
	/** numbers */
	public static final class N {
		public static final float RAD_DIV = 57.295779513F;
		public static final float RAD_MUL = 0.0174532925F;
		
		public static final int BaseGrudge = 300;
		public static final int BaseLightAmmo = 30;
		public static final int BaseHeavyAmmo = 15;
	}
	
	/** color code */
	public static class Color {
		//normal
		public static final int BLACK = 0;
		public static final int CYAN = 65535;
		public static final int GRAY = 3158064;
		public static final int ORANGE = 16753920;
		public static final int PINK = 15515845;
		public static final int RED = 16724787;
		public static final int WHITE = 16777215;
		public static final int YELLOW = 16776960;
		//dark
		public static final int DARK_RED = 11141120;
		//light
		public static final int LIGHT_GRAY = 11184810;
		public static final int LIGHT_PURPLE = 16581630;
		public static final int LIGHT_RED = 16724787;
	}
	
	
	/**SHIP ATTRIBUTES MAP
	 * index by ID.ShipAttr
	 */
	public static final Map<Short, float[]> ShipAttrMap = Collections.unmodifiableMap(new HashMap<Short, float[]>() {{
		//destroyer                                  HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT     
		put(ID.Ship.DestroyerI,         new float[] {20F,  3F,  5F,  1.0F, 0.5F,  6F,  0.3F,  0.25F, 0.11F, 0.5F,  1F,    0.4F});
		put(ID.Ship.DestroyerRO,        new float[] {22F,  4F,  6F,  1.0F, 0.5F,  6F,  0.32F, 0.28F, 0.12F, 0.5F,  1F,    0.4F});
		put(ID.Ship.DestroyerHA,        new float[] {24F,  3F,  7F,  1.0F, 0.5F,  6F,  0.34F, 0.25F, 0.13F, 0.5F,  1F,    0.4F});
		put(ID.Ship.DestroyerNI,        new float[] {28F,  4F,  9F,  1.0F, 0.5F,  6F,  0.36F, 0.28F, 0.15F, 0.5F,  1F,    0.4F});
		//cruiser
//		put(ID.Ship.LightCruiserHO,     new float[] {33F,  6F,  11F, 1.0F, 0.45F, 8F,  0.38F, 0.3F,  0.17F, 0.53F, 0.9F,  0.5F});
//		put(ID.Ship.LightCruiserHE,     new float[] {36F,  9F,  13F, 1.0F, 0.45F, 8F,  0.4F,  0.35F, 0.18F, 0.53F, 0.9F,  0.5F});
//		put(ID.Ship.LightCruiserTO,     new float[] {39F,  8F,  15F, 1.0F, 0.45F, 8F,  0.42F, 0.32F, 0.19F, 0.53F, 0.9F,  0.5F});
//		put(ID.Ship.LightCruiserTSU,    new float[] {48F,  12F, 16F, 1.0F, 0.45F, 8F,  0.44F, 0.38F, 0.20F, 0.53F, 0.9F,  0.5F});
//		put(ID.Ship.TorpedoCruiserCHI,  new float[] {48F,  16F, 18F, 1.0F, 0.42F, 9F,  0.46F, 0.44F, 0.21F, 0.56F, 0.84F, 0.5F});
		put(ID.Ship.HeavyCruiserRI,     new float[] {58F,  14F, 18F, 1.0F, 0.42F, 9F,  0.48F, 0.4F,  0.21F, 0.56F, 0.84F, 0.5F});
		put(ID.Ship.HeavyCruiserNE,     new float[] {62F,  15F, 19F, 1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
		//carrier                                    HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.LightCarrierNU,     new float[] {65F,  20F, 20F, 0.7F, 0.32F, 14F, 0.52F, 0.45F, 0.22F, 0.5F,  0.64F, 0.6F});
		put(ID.Ship.CarrierWO,          new float[] {85F,  25F, 21F, 1.0F, 0.36F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		//battleship
//		put(ID.Ship.BattleshipRU,       new float[] {90F,  30F, 25F, 1.0F, 0.32F, 10F, 0.7F,  0.63F, 0.25F, 0.65F, 0.64F, 0.5F});
		put(ID.Ship.BattleshipTA,       new float[] {84F,  19F, 23F, 1.2F, 0.42F, 10F, 0.65F, 0.55F, 0.24F, 0.7F,  0.84F, 0.5F});
		put(ID.Ship.BattleshipRE,       new float[] {120F, 27F, 25F, 1.1F, 0.36F, 12F, 0.8F,  0.6F,  0.25F, 0.63F, 0.72F, 0.5F});
		//transport
		put(ID.Ship.TransportWA,        new float[] {90F,  3F,  10F, 1.0F, 0.3F,  8F,  0.7F,  0.25F, 0.16F, 0.35F, 0.6F,  0.3F});
		//submarine
		put(ID.Ship.SubmarineKA,        new float[] {40F,  28F, 9F,  0.8F, 0.3F,  5F,  0.35F, 0.67F, 0.14F, 0.7F,  0.6F,  0.3F});
		put(ID.Ship.SubmarineYO,        new float[] {36F,  30F, 10F, 0.8F, 0.3F,  5F,  0.33F, 0.7F,  0.16F, 0.7F,  0.6F,  0.3F});
		put(ID.Ship.SubmarineSO,        new float[] {34F,  38F, 12F, 0.8F, 0.28F, 5.5F,0.3F,  0.8F,  0.18F, 0.7F,  0.6F,  0.3F});
		//demon                                      HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.IsolatedDemon,      new float[] {225F, 13F, 34F, 0.9F, 0.22F, 24F, 1.3F,  0.4F,  0.29F, 0.6F,  0.44F, 0.8F});
//		put(ID.Ship.LightCruiserDemon,  new float[] {130F, 30F, 25F, 1.0F, 0.45F, 13F, 0.8F,  0.65F, 0.25F, 0.6F,  0.9F,  0.55F});
		//hime                                       HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.AirdefenseHime,     new float[] {120F, 30F, 35F, 1.0F, 0.5F,  12F, 0.8F,  0.7F,  0.3F,  0.6F,  1F,    0.5F});
		put(ID.Ship.AirfieldHime,       new float[] {240F, 16F, 32F, 1.0F, 0.3F,  26F, 1.2F,  0.45F, 0.28F, 0.6F,  0.6F,  0.8F});
//		put(ID.Ship.AnchorageHime,      new float[] {150F, 19F, 32F, 0.9F, 0.3F,  23F, 0.95F, 0.5F,  0.28F, 0.6F,  0.6F,  0.8F});
//		put(ID.Ship.ArmoredCarrierHime, new float[] {200F, 34F, 35F, 1.0F, 0.42F, 20F, 0.9F,  0.73F, 0.3F,  0.63F, 0.84F, 0.7F});
		put(ID.Ship.BattleshipHime,     new float[] {220F, 42F, 40F, 1.0F, 0.4F,  16F, 1.0F,  0.8F,  0.32F, 0.73F, 0.8F,  0.6F});
//		put(ID.Ship.CarrierHime,        new float[] {180F, 40F, 32F, 1.0F, 0.45F, 22F, 0.85F, 0.77F, 0.3F,  0.65F, 0.9F,  0.7F});
//		put(ID.Ship.DestroyerHime,      new float[] {90F,  22F, 20F, 1.0F, 0.5F,  12F, 0.55F, 0.5F,  0.22F, 0.6F,  1F,    0.5F});
		put(ID.Ship.HarbourHime,        new float[] {260F, 14F, 36F, 0.8F, 0.2F,  24F, 1.35F, 0.4F,  0.3F,  0.6F,  0.4F,  0.8F});
//		put(ID.Ship.MidwayHime,         new float[] {350F, 22F, 45F, 0.8F, 0.25F, 30F, 1.5F,  0.5F,  0.34F, 0.6F,  0.4F,  0.8F});
		put(ID.Ship.NorthernHime,       new float[] {210F, 13F, 30F, 0.8F, 0.32F, 22F, 1.15F, 0.35F, 0.27F, 0.6F,  0.64F, 0.8F});
//		put(ID.Ship.SeaplaneHime,       new float[] {170F, 24F, 25F, 1.0F, 0.45F, 18F, 1F,    0.6F,  0.25F, 0.63F, 0.9F,  0.65F});
//		put(ID.Ship.SouthernHime,       new float[] {170F, 35F, 34F, 1.0F, 0.3F,  20F, 1F,    0.73F, 0.29F, 0.63F, 0.6F,  0.7F});
//		put(ID.Ship.SubmarineHime,      new float[] {75F,  45F, 18F, 0.9F, 0.3F,  10F, 0.65F, 0.85F, 0.2F,  0.6F,  0.6F,  0.4F});
//		put(ID.Ship.LightCruiserHime,   new float[] {160F, 32F, 26F, 1.0F, 0.45F, 14F, 0.82F, 0.7F,  0.25F, 0.6F,  0.9F,  0.55F});
//		put(ID.Ship.HeavyCruiserHime,   new float[] {180F, 35F, 28F, 1.0F, 0.45F, 15F, 0.85F, 0.75F, 0.25F, 0.6F,  0.9F,  0.6F});
//		put(ID.Ship.SupplyDepotHime,    new float[] {320F, 16F, 38F, 0.8F, 0.18F, 26F, 1.4F,  0.42F, 0.32F, 0.6F,  0.4F,  0.8F});
		//	
		//water demon                                HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.AnchorageWD,        new float[] {230F, 28F, 35F, 1.0F, 0.35F, 26F, 1.35F, 0.6F,  0.3F,  0.7F,  0.7F,  1F});
//		put(ID.Ship.BattleshipWD,       new float[] {280F, 50F, 45F, 1.0F, 0.42F, 21F, 1.1F,  1F,    0.34F, 0.85F, 0.84F, 0.7F});
		put(ID.Ship.CarrierWD,          new float[] {190F, 45F, 40F, 1.0F, 0.42F, 25F, 1F,    0.95F, 0.32F, 0.75F, 0.84F, 0.8F});
//		put(ID.Ship.HarbourWD,          new float[] {300F, 35F, 45F, 1.0F, 0.35F, 29F, 1.5F,  0.63F, 0.34F, 0.7F,  0.7F,  1F});
//		put(ID.Ship.DestroyerWD,        new float[] {150F, 23F, 25F, 1.0F, 0.55F, 15F, 0.9F,  0.8F,  0.3F,  0.7F,  1F,    0.6F});
		//hostile ship                               HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
		//destroyer
		put(ID.Ship.DestroyerAkatsuki,  new float[] {32F,  9F,  9F,  1.0F, 0.5F,  11F, 0.32f, 0.38F, 0.12F, 0.5F,  1F,    0.5F});
		put(ID.Ship.DestroyerHibiki,    new float[] {40F,  7F,  11F, 1.0F, 0.5F,  10F, 0.38f, 0.36F, 0.14F, 0.5F,  1F,    0.48F});
		put(ID.Ship.DestroyerIkazuchi,  new float[] {30F,  5F,  9F,  1.0F, 0.5F,  9F,  0.3f,  0.32F, 0.12F, 0.5F,  1F,    0.46F});
		put(ID.Ship.DestroyerInazuma,   new float[] {30F,  5F,  9F,  1.0F, 0.5F,  9F,  0.3f,  0.32F, 0.12F, 0.5F,  1F,    0.46F});
		put(ID.Ship.DestroyerShimakaze, new float[] {38F,  11F, 12F, 1.0F, 0.6F,  9F,  0.35F, 0.4F,  0.16F, 0.55F, 1.2F,  0.46F});
		//battleship
		put(ID.Ship.BattleshipNagato,   new float[] {135F, 40F, 26F, 1.0F, 0.32F, 14F, 0.85F, 0.8F,  0.25F, 0.63F, 0.64F, 0.6F});
		put(ID.Ship.BattleshipYamato,   new float[] {150F, 55F, 36F, 1.0F, 0.3F,  20F, 1F,    1F,    0.3F,  0.7F,  0.6F,  0.7F});
		//submarine
		put(ID.Ship.SubmarineU511,      new float[] {28F,  30F, 7F,  0.8F, 0.3F,  7F,  0.3F,  0.7F,  0.13F, 0.7F,  0.6F,  0.4F});
		put(ID.Ship.SubmarineRo500,     new float[] {32F,  32F, 10F, 0.8F, 0.3F,  8F,  0.33F, 0.75F, 0.16F, 0.7F,  0.6F,  0.4F});
		//carrier
		put(ID.Ship.CarrierKaga,        new float[] {70F,  22F, 21F, 1.0F, 0.34F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		put(ID.Ship.CarrierAkagi,       new float[] {75F,  22F, 20F, 1.0F, 0.32F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		
	}});
	
	/** SHIP LEASH HEIGHT
	 * 
	 *  map <ship id(short), data(float[])>
	 *  
	 *  data: 0:Width, 1:Ride, 2:RideSit, 3:Sit, 4:Stand
	 */
	public static final Map<Short, float[]> ShipLeashHeight = Collections.unmodifiableMap(new HashMap<Short, float[]>() {{
		//destroyer
		put(ID.Ship.DestroyerI,         new float[] {0.8F, 0.8F, 0.8F, 0.8F, 0.8F});
		put(ID.Ship.DestroyerRO,		new float[] {1.2F, 0.8F, 0.8F, 0.8F, 0.7F});
		put(ID.Ship.DestroyerHA,		new float[] {1.5F, 0.8F, 0.8F, 0.8F, 0.8F});
		put(ID.Ship.DestroyerNI,		new float[] {0.5F, 0.9F, 0.9F, 0.9F, 0.9F});
		//cruiser
//		put(ID.Ship.LightCruiserHO,		new float[] {});
//		put(ID.Ship.LightCruiserHE,		new float[] {});
//		put(ID.Ship.LightCruiserTO,		new float[] {});
//		put(ID.Ship.LightCruiserTSU,	new float[] {});
//		put(ID.Ship.TorpedoCruiserCHI,	new float[] {});
		put(ID.Ship.HeavyCruiserRI,		new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
		put(ID.Ship.HeavyCruiserNE,		new float[] {0.5F, 1.1F, 1.1F, 1.1F, 0.85F});
		//carrier
//		put(ID.Ship.LightCarrierNU,		new float[] {});
		put(ID.Ship.CarrierWO,			new float[] {0.1F, 0.2F, 0.2F, 0.2F, 0.15F});
		//battleship
		put(ID.Ship.BattleshipRU,		new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.15F});
		put(ID.Ship.BattleshipTA,		new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.15F});
		put(ID.Ship.BattleshipRE,		new float[] {0.1F, 0.45F, 0.45F, 0.65F, 0.45F});
		//transport
		put(ID.Ship.TransportWA,		new float[] {0.2F, 0.8F, 0.8F, 0.8F, 0.3F});
		//submarine
		put(ID.Ship.SubmarineKA,		new float[] {0.2F, 0.7F, 0.7F, 0.7F, 0.2F});
		put(ID.Ship.SubmarineYO,		new float[] {0.2F, 0.7F, 0.7F, 0.7F, 0.2F});
		put(ID.Ship.SubmarineSO,		new float[] {0.2F, 0.7F, 0.7F, 0.7F, 0.2F});
		//hime
//		put(ID.Ship.CarrierHime,		new float[] {});
		put(ID.Ship.AirfieldHime,		new float[] {0.1F, 1.2F, 1.5F, 0.7F, 0.2F});
//		put(ID.Ship.ArmoredCarrierHime,	new float[] {});
//		put(ID.Ship.AnchorageHime,		new float[] {});
//		put(ID.Ship.LightCruiserDemon,	new float[] {});
//		put(ID.Ship.AirdefenseHime,		new float[] {});
		put(ID.Ship.BattleshipHime,		new float[] {0.1F, 0.3F, 0.8F, 0.7F, 0.1F});
//		put(ID.Ship.DestroyerHime,		new float[] {});
		put(ID.Ship.HarbourHime,		new float[] {0F, 1.5F, 2.1F, 1.1F, 0.15F});
//		put(ID.Ship.IsolatedDemon,		new float[] {});
//		put(ID.Ship.MidwayHime,			new float[] {});
		put(ID.Ship.NorthernHime,		new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.5F});
//		put(ID.Ship.SouthernHime,		new float[] {});
//		put(ID.Ship.SeaplaneHime,		new float[] {});
		put(ID.Ship.CarrierWD,			new float[] {0.1F, 0.95F, 0.85F, 0.9F, 0.15F});
//		put(ID.Ship.BattleshipWD,		new float[] {});
//		put(ID.Ship.AnchorageWD,		new float[] {});
//		put(ID.Ship.HarbourWD,			new float[] {});
//		put(ID.Ship.DestroyerWD,		new float[] {});
//		put(ID.Ship.LightCruiserHime,	new float[] {});
//		put(ID.Ship.HeavyCruiserHime,	new float[] {});
//		put(ID.Ship.SubmarineHime,		new float[] {});
//		put(ID.Ship.SupplyDepotHime,	new float[] {});
		
		//hostile ship
		//DD
		put(ID.Ship.DestroyerAkatsuki,	new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put(ID.Ship.DestroyerHibiki,	new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put(ID.Ship.DestroyerIkazuchi,	new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put(ID.Ship.DestroyerInazuma,	new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put(ID.Ship.DestroyerShimakaze,	new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.4F});
		//BB
		put(ID.Ship.BattleshipNagato,	new float[] {0.1F, 0.95F, 0.95F, 0.95F, 0.1F});
		put(ID.Ship.BattleshipYamato,	new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.15F});
		//SS
		put(ID.Ship.SubmarineU511,		new float[] {0.1F, 1F, 1F, 1F, 0.35F});
		put(ID.Ship.SubmarineRo500,		new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.35F});
		//CV
		put(ID.Ship.CarrierKaga,		new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		put(ID.Ship.CarrierAkagi,		new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		
	}});
	
	
	/** SHIP NAME ICON MAP
	 * 
	 *  map <ship id(short), int[]: 0:icon file id, 1:icon X, 2:icon Y>
	 *  
	 *  font: MS Mincho
	 *  size: 12
	 */
	public static final Map<Short, int[]> ShipNameIconMap = Collections.unmodifiableMap(new HashMap<Short, int[]>() {{
		//destroyer
		put(ID.Ship.DestroyerI,         new int[] {0, 128, 0});
		put(ID.Ship.DestroyerRO,		new int[] {0, 139, 0});
		put(ID.Ship.DestroyerHA,		new int[] {0, 150, 0});
		put(ID.Ship.DestroyerNI,		new int[] {0, 161, 0});
		//cruiser
		put(ID.Ship.LightCruiserHO,		new int[] {0, 172, 0});
		put(ID.Ship.LightCruiserHE,		new int[] {0, 183, 0});
		put(ID.Ship.LightCruiserTO,		new int[] {0, 194, 0});
		put(ID.Ship.LightCruiserTSU,	new int[] {0, 205, 0});
		put(ID.Ship.TorpedoCruiserCHI,	new int[] {0, 216, 0});
		put(ID.Ship.HeavyCruiserRI,		new int[] {0, 227, 0});
		put(ID.Ship.HeavyCruiserNE,		new int[] {0, 238, 0});
		//carrier
		put(ID.Ship.LightCarrierNU,		new int[] {0, 128, 60});
		put(ID.Ship.CarrierWO,			new int[] {0, 139, 60});
		//battleship
		put(ID.Ship.BattleshipRU,		new int[] {0, 150, 60});
		put(ID.Ship.BattleshipTA,		new int[] {0, 161, 60});
		put(ID.Ship.BattleshipRE,		new int[] {0, 172, 60});
		//transport
		put(ID.Ship.TransportWA,		new int[] {0, 183, 60});
		//submarine
		put(ID.Ship.SubmarineKA,		new int[] {0, 194, 60});
		put(ID.Ship.SubmarineYO,		new int[] {0, 205, 60});
		put(ID.Ship.SubmarineSO,		new int[] {0, 216, 60});
		//hime
		put(ID.Ship.CarrierHime,		new int[] {0, 227, 60});
		put(ID.Ship.AirfieldHime,		new int[] {0, 238, 60});
		put(ID.Ship.ArmoredCarrierHime,	new int[] {0, 128, 120});
		put(ID.Ship.AnchorageHime,		new int[] {0, 139, 120});
		put(ID.Ship.LightCruiserDemon,	new int[] {0, 150, 120});
		put(ID.Ship.AirdefenseHime,		new int[] {0, 161, 120});
		put(ID.Ship.BattleshipHime,		new int[] {0, 172, 120});
		put(ID.Ship.DestroyerHime,		new int[] {0, 183, 120});
		put(ID.Ship.HarbourHime,		new int[] {0, 194, 120});
		put(ID.Ship.IsolatedDemon,		new int[] {0, 205, 120});
		put(ID.Ship.MidwayHime,			new int[] {0, 216, 120});
		put(ID.Ship.NorthernHime,		new int[] {0, 227, 120});
		put(ID.Ship.SouthernHime,		new int[] {0, 238, 120});
		
		put(ID.Ship.SeaplaneHime,		new int[] {0, 128, 180});
		put(ID.Ship.CarrierWD,			new int[] {0, 139, 180});
		put(ID.Ship.BattleshipWD,		new int[] {0, 150, 180});
		put(ID.Ship.AnchorageWD,		new int[] {0, 161, 180});
		put(ID.Ship.HarbourWD,			new int[] {0, 172, 180});
		put(ID.Ship.DestroyerWD,		new int[] {0, 183, 180});
		put(ID.Ship.LightCruiserHime,	new int[] {0, 194, 180});
		put(ID.Ship.HeavyCruiserHime,	new int[] {0, 205, 180});
		put(ID.Ship.SubmarineHime,		new int[] {0, 216, 180});
		put(ID.Ship.SupplyDepotHime,	new int[] {0, 227, 180});
//		put(ID.Ship.,		new int[] {0, 238, 180});  //empty for now
		
		//hostile ship
		put(ID.Ship.DestroyerShimakaze,	new int[] {0, 0,  120});
		put(ID.Ship.BattleshipNagato,	new int[] {0, 11, 120});
		put(ID.Ship.SubmarineU511,		new int[] {0, 22, 120});
		put(ID.Ship.SubmarineRo500,		new int[] {0, 33, 120});
		put(ID.Ship.BattleshipYamato,	new int[] {0, 44, 120});
		put(ID.Ship.CarrierKaga,		new int[] {0, 55, 120});
		put(ID.Ship.CarrierAkagi,		new int[] {0, 66, 120});
		put(ID.Ship.DestroyerAkatsuki,	new int[] {0, 77, 120});
		put(ID.Ship.DestroyerHibiki,	new int[] {0, 88, 120});
		put(ID.Ship.DestroyerIkazuchi,	new int[] {0, 99, 120});
		put(ID.Ship.DestroyerInazuma,	new int[] {0, 110,120});
		
		put(ID.Ship.Raiden,				new int[] {0, 0,  180});
		put(ID.Ship.LightCruiserTenryuu,new int[] {0, 11, 180});
		
	}});
	
	/**SHIP LIST for guidebook
	 * 
	 * index by page number
	 */
	public static final List<Integer> ShipBookList = Collections.unmodifiableList(new ArrayList<Integer>() {{
		add((int)ID.Ship.DestroyerI);
		add((int)ID.Ship.DestroyerRO);
		add((int)ID.Ship.DestroyerHA);
		add((int)ID.Ship.DestroyerNI);
		
		add((int)ID.Ship.HeavyCruiserRI);
		add((int)ID.Ship.HeavyCruiserNE);
		
		add((int)ID.Ship.CarrierWO);
		
		add((int)ID.Ship.BattleshipTA);
		add((int)ID.Ship.BattleshipRE);
		
		add((int)ID.Ship.AirfieldHime);
		add((int)ID.Ship.BattleshipHime);
		add((int)ID.Ship.HarbourHime);
		add((int)ID.Ship.NorthernHime);
		add((int)ID.Ship.CarrierWD);
		
		add((int)ID.Ship.TransportWA);
		add((int)ID.Ship.SubmarineKA);
		add((int)ID.Ship.SubmarineYO);
		add((int)ID.Ship.SubmarineSO);
	}});
	
	/**ENEMY LIST for guidebook
	 * 
	 * index by page number
	 */
	public static final List<Integer> EnemyBookList = Collections.unmodifiableList(new ArrayList<Integer>() {{
		add((int)ID.Ship.DestroyerShimakaze);
		add((int)ID.Ship.BattleshipNagato);
		add((int)ID.Ship.SubmarineU511);
		add((int)ID.Ship.SubmarineRo500);
		
		add((int)ID.Ship.BattleshipYamato);
		add((int)ID.Ship.CarrierKaga);
		add((int)ID.Ship.CarrierAkagi);
		
		add((int)ID.Ship.DestroyerAkatsuki);
		add((int)ID.Ship.DestroyerHibiki);
		add((int)ID.Ship.DestroyerIkazuchi);
		add((int)ID.Ship.DestroyerInazuma);
	}});
	
	
	/** SHIP TYPE ICON MAP
	 * 
	 *  map <ship id(short), int[]: 0:icon X, 1:icon Y>
	 *  
	 *  font: MS Mincho
	 *  size: 12
	 */
	public static final Map<Byte, int[]> ShipTypeIconMap = Collections.unmodifiableMap(new HashMap<Byte, int[]>() {{
		put(ID.ShipType.TRANSPORT,			new int[] {12, 74});
		put(ID.ShipType.DESTROYER,			new int[] {41, 0});
		put(ID.ShipType.LIGHT_CRUISER,		new int[] {41, 29});
		put(ID.ShipType.HEAVY_CRUISER,		new int[] {41, 58});
		put(ID.ShipType.TORPEDO_CRUISER,	new int[] {41, 87});
		put(ID.ShipType.BATTLESHIP,			new int[] {70, 0});
		put(ID.ShipType.STANDARD_CARRIER,	new int[] {70, 29});
		put(ID.ShipType.LIGHT_CARRIER,		new int[] {70, 58});
		put(ID.ShipType.HIME,				new int[] {70, 87});
		put(ID.ShipType.SUBMARINE,			new int[] {99, 0});
//		put(ID.ShipType.,	new int[] {99, 29});  //empty for now
		put(ID.ShipType.DEMON,				new int[] {99, 58});
//		put(ID.ShipType.,	new int[] {99, 87});  //empty for now
	}});
	
	
	/**damage modifier array [damage type id][target type id]
	 * index by ID.ShipDmgType
	 * CARRIER  AVIATION  BATTLESHIP  CRUISER  DESTROYER   SUBMARINE  AIRPLANE
	 */
	public static final float[][] ModDmgDay = 		//for day battle
		{{0.5F,  0.5F,  0.5F,  0.5F,  1F,    0F,    0.75F},
		 {1F,    1F,    1F,    1.25F, 1.5F,  0.5F,  0.75F},
		 {1.25F, 1.25F, 1F,    1.5F,  2F,    0F,    0.5F},
		 {1F,    1F,    1F,    1F,    1.25F, 1.25F, 1F},
		 {0.5F,  0.5F,  0.5F,  0.5F,  1F,    2F,    1.5F},
		 {1.5F,  1.5F,  1.25F, 1.25F, 1.5F,  1.5F,  0F},
		 {1.5F,  1.5F,  1.5F,  1.75F, 2F,    0.5F,  2F}
		};
	public static final float[][] ModDmgNight = 	//for night battle
		{{0.25F, 0.25F, 0.25F, 0.25F, 0.5F,  0F,    0.5F},
		 {0.75F, 0.75F, 0.75F, 1F,    1F,    0.25F, 0.5F},
		 {1F,    1F,    0.75F, 1.25F, 1.75F, 0F,    0.25F},
		 {0.75F, 0.75F, 0.75F, 0.75F, 1F,    1.25F, 0.75F},
		 {0.5F,  0.5F,  0.5F,  0.5F,  0.75F, 1.75F, 1.25F},
		 {1.5F,  1.5F,  1.25F, 1.25F, 1.5F,  1.5F,  0F},
		 {0.5F,  0.5F,  0.5F,  0.75F, 0.75F, 0.25F, 1F}
		};
	
	
	/**EQUIP MAP
	 * index by ID.E
	 * 
	 * EquipID = EquipType + EquipSubID * 100
	 * 
	 * equip type:
	 * 		 0:unused
	 *       1:cannon, torpedo
	 *       2:aircraft-R, engine, armor, radar
	 *       3:aircraft-T/F/B
	 *       
	 * note: 新增裝備要記得在LargeRecipe新增回收價格
	 */
	public static final Map<Integer, float[]> EquipMap = Collections.unmodifiableMap(new HashMap<Integer, float[]>() {{
		//single cannon                                                                             Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_5 * 100,     new float[]{1F, 0F,  2F,  0F,  0F,  0F,  0F, 0.1F, -0.01F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CANNON_SI,    1000F,  0F,  128,  2});
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_6 * 100,     new float[]{1F, 0F,  3F,  0F,  0F,  0F,  0F, 0.1F, -0.02F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CANNON_SI,    4000F,  0F,  128,  2});
		//twin cannon                            
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5 * 100,    new float[]{1F, 0F,  4F,  0F,  0F,  0F,  0F, 0.2F, -0.04F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_LO, 1000F,  0F,  320,  2});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_6 * 100,    new float[]{1F, 0F,  5F,  0F,  0F,  0F,  0F, 0.4F, -0.06F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_LO, 2000F,  0F,  320,  2});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5DP * 100,  new float[]{1F, 0F,  5F,  0F,  0F,  0F,  0F, 0.25F,-0.08F, 1F,   0F,    0.06F, 0F,    0F,    24F, 0F,  ID.EquipType.CANNON_TW_LO, 3200F,  0F,  320,  2});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_125 * 100,  new float[]{1F, 0F,  8F,  0F,  0F,  0F,  0F, 0.2F, -0.1F,  1.5F, 0F,    0.06F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_LO, 4000F,  0F,  320,  2});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_14 * 100,   new float[]{1F, 0F,  11F, 0F,  0F,  0F,  0F, 0.2F, -0.12F, 2F,   0F,    0.07F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_HI, 1000F,  0F,  1600, 2});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_16 * 100,   new float[]{1F, 0F,  15F, 0F,  0F,  0F,  0F, 0.2F, -0.15F, 2F,   0F,    0.08F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_HI, 2400F,  0F,  1600, 2});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_20 * 100,   new float[]{1F, 0F,  20F, 0F,  0F,  0F,  0F, 0.2F, -0.2F,  2.5F, 0F,    0.10F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_HI, 4000F,  0F,  1600, 2});
		//triple cannon
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_8 * 100,        new float[]{1F, 0F,  9F,  0F,  0F,  0F,  0F, 0.4F, -0.2F,  1.5F, 0F,    0.06F, 0.06F, 0F,    0F,  0F,  ID.EquipType.CANNON_TR,    1000F,  0F,  4400, 2});
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_16 * 100,       new float[]{1F, 0F,  17F, 0F,  0F,  0F,  0F, 0.4F, -0.25F, 2F,   0F,    0.08F, 0.08F, 0F,    0F,  0F,  ID.EquipType.CANNON_TR,    2400F,  0F,  4400, 2});
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_FG_15 * 100,        new float[]{1F, 220F,6F,  0F,  0F,  0F,  8F, 0.2F, -0.25F, 2F,   0F,    0.04F, 0.04F, 0F,    0F,  0F,  ID.EquipType.CANNON_TR,    4000F,  0F,  4400, 2});
		//machine gun
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_3 * 100,          		new float[]{2F, 0F,  1F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    12F,  0F, ID.EquipType.GUN_LO,       1000F,  0F,  100,  2});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_5 * 100,          		new float[]{2F, 0F,  2F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    24F,  0F, ID.EquipType.GUN_LO,       2000F,  0F,  100,  2});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_12 * 100,     		new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    36F,  0F, ID.EquipType.GUN_LO,       3200F,  0F,  100,  2});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_20 * 100,      	new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    48F, 0F,  ID.EquipType.GUN_LO,       4000F,  0F,  100,  2});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_40 * 100,        	new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.02F, 0F,   0.01F, 0F,    0F,    0F,    60F, 0F,  ID.EquipType.GUN_HI,       1000F,  0F,  800,  2});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_QUAD_40 * 100,        	new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.04F, 0F,   0.02F, 0F,    0F,    0F,    72F, 0F,  ID.EquipType.GUN_HI,       2400F,  0F,  800,  2});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_4_CIC * 100,     	new float[]{2F, 0F,  3F,  0F,  0F,  0F,  0F, 0F,   -0.06F, 2F,   0.04F, 0F,    0F,    0.06F, 84F, 0F,  ID.EquipType.GUN_HI,       4000F,  0F,  800,  2});
		//torpedo                                                                                   Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK1 * 100,      new float[]{1F, 0F,  0F,  6F,  0F,  0F,  0F, 0F,   -0.05F, 0F,   0.03F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_LO,   1000F,  0F,  160,  2});
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK2 * 100,      new float[]{1F, 0F,  0F,  12F, 0F,  0F,  0F, 0F,   -0.08F, 0.5F, 0.05F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_LO,   2400F,  0F,  160,  2});
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_22MK1 * 100,      new float[]{1F, 0F,  0F,  28F, 0F,  0F,  0F, 0.1F, -0.11F, 0.5F, 0.07F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_LO,   4000F,  0F,  160,  2});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_CUTTLEFISH * 100, new float[]{1F, 0F,  0F,  40F, 0F,  0F,  0F, 0.1F, -0.14F, 1F,   0.09F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_HI,   1000F,  0F,  1200, 2});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED * 100,  new float[]{1F, 0F,  0F,  50F, 0F,  0F,  0F, 0.15F,-0.18F, 1.5F, 0.11F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_HI,   4000F,  0F,  1200, 2});
		//Torpedo aircraft
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK1 * 100,      	new float[]{3F, 0F,  0F,  0F,  4F,  16F, 0F, 0F,   -0.06F, 2F,   0F,    0F,    0F,    0F,    3F,  4F,  ID.EquipType.AIR_T_LO,     1000F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK2 * 100,      	new float[]{3F, 0F,  0F,  0F,  8F,  32F, 0F, 0F,   -0.08F, 2F,   0F,    0F,    0F,    0F,    6F,  8F,  ID.EquipType.AIR_T_LO,     2400F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK3 * 100,      	new float[]{3F, 0F,  0F,  0F,  12F, 54F, 0F, 0F,   -0.11F, 2.5F, 0F,    0F,    0F,    0F,    9F,  12F, ID.EquipType.AIR_T_LO,     4000F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGER * 100,  	new float[]{3F, 0F,  0F,  0F,  10F, 90F, 0F, 0F,   -0.15F, 2.5F, 0F,    0F,    0F,    0F,    15F, 16F, ID.EquipType.AIR_T_HI,     1000F,  0F,  3800, 3});
		put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGERK * 100, 	new float[]{3F, 0F,  0F,  0F,  12F, 128F,0F, 0.1F, -0.18F, 3F,   0F,    0F,    0F,    0F,    20F, 24F, ID.EquipType.AIR_T_HI,     4000F,  0F,  3800, 3});
		//Fighter aircraft
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK1 * 100,      	new float[]{3F, 0F,  0F,  0F,  8F,  0F,  0F, 0F,   -0.04F, 4F,   0F,    0F,    0F,    0F,    30F, 0F,  ID.EquipType.AIR_F_LO,     1000F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK2 * 100,      	new float[]{3F, 0F,  0F,  0F,  12F, 0F,  0F, 0F,   -0.06F, 4F,   0F,    0F,    0F,    0F,    50F, 0F,  ID.EquipType.AIR_F_LO,     2400F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK3 * 100,      	new float[]{3F, 0F,  0F,  0F,  18F, 0F,  0F, 0.05F,-0.08F, 4.5F, 0F,    0F,    0F,    0F,    70F, 0F,  ID.EquipType.AIR_F_LO,     4000F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FFLYFISH * 100,  	new float[]{3F, 0F,  0F,  0F,  24F, 0F,  0F, 0.05F,-0.1F,  4.5F, 0F,    0F,    0F,    0.01F, 90F, 0F,  ID.EquipType.AIR_F_HI,     1000F,  0F,  3800, 3});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCAT * 100,  	new float[]{3F, 0F,  0F,  0F,  28F, 0F,  0F, 0.1F, -0.12F, 5F,   0F,    0F,    0F,    0.02F, 110F,0F,  ID.EquipType.AIR_F_HI,     2400F,  0F,  3800, 3});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATK * 100, 	new float[]{3F, 0F,  0F,  0F,  36F, 0F,  0F, 0.15F,-0.15F, 5F,   0F,    0F,    0F,    0.04F, 130F,0F,  ID.EquipType.AIR_F_HI,     4000F,  0F,  3800, 3});
		//Bomber aircraft                                                                           Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK1 * 100,      	new float[]{3F, 0F,  0F,  0F,  3F,  9F,  0F, 0F,   -0.06F, 1.5F, 0.04F, 0F,    0F,    0F,    2F,  2F,  ID.EquipType.AIR_B_LO,     1000F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK2 * 100,      	new float[]{3F, 0F,  0F,  0F,  6F,  18F, 0F, 0F,   -0.1F,  2F,   0.06F, 0F,    0F,    0F,    3F,  4F,  ID.EquipType.AIR_B_LO,     4000F,  0F,  2400, 3});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BFLYFISH * 100,  	new float[]{3F, 0F,  0F,  0F,  10F, 30F, 0F, 0F,   -0.15F, 2F,   0.08F, 0F,    0F,    0F,    4F,  8F,  ID.EquipType.AIR_B_HI,     1000F,  0F,  3800, 3});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELL * 100,     	new float[]{3F, 0F,  0F,  0F,  8F,  24F, 0F, 0F,   -0.2F,  2.5F, 0.10F, 0F,    0F,    0F,    8F,  12F, ID.EquipType.AIR_B_HI,     2400F,  0F,  3800, 3});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELLK * 100,    	new float[]{3F, 0F,  0F,  0F,  10F, 30F, 0F, 0.1F, -0.25F, 2.5F, 0.15F, 0F,    0F,    0F,    12F, 16F, ID.EquipType.AIR_B_HI,     4000F,  0F,  3800, 3});
		//Recon aircraft
		put((int)ID.EquipType.AIR_R_LO + (int)ID.EquipSubID.AIRCRAFT_R * 100,         	new float[]{2F, 0F,  2F,  2F,  2F,  2F,  0F, 0F,   -0.08F, 3.5F, 0.03F, 0F,    0F,    0.04F, 8F,  4F,  ID.EquipType.AIR_R_LO,     200F,   0F,  256,  3});
		put((int)ID.EquipType.AIR_R_HI + (int)ID.EquipSubID.AIRCRAFT_RFLYFISH * 100,  	new float[]{2F, 0F,  4F,  4F,  4F,  4F,  0F, 0F,   -0.15F, 5.5F, 0.05F, 0F,    0F,    0.08F, 12F, 8F,  ID.EquipType.AIR_R_HI,     200F,   0F,  1000, 3});
		//radar
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK1 * 100,       	new float[]{2F, 0F,  2F,  0F,  2F,  0F,  3F, 0F,   -0.04F, 1F,   0F,    0F,    0F,    0.04F, 14F, 0F,  ID.EquipType.RADAR_LO,     1000F,  2F,  200,  0});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK2 * 100,       	new float[]{2F, 0F,  4F,  0F,  4F,  0F,  6F, 0F,   -0.06F, 2F,   0.03F, 0F,    0F,    0.08F, 28F, 0F,  ID.EquipType.RADAR_LO,     1800F,  2F,  200,  0});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK1 * 100,       	new float[]{2F, 0F,  0F,  6F,  0F,  8F,  3F, 0F,   -0.04F, 1F,   0F,    0F,    0F,    0.04F, 0F,  0F,  ID.EquipType.RADAR_LO,     2600F,  2F,  200,  0});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK2 * 100,       	new float[]{2F, 0F,  0F,  12F, 0F,  16F, 6F, 0F,   -0.06F, 2F,   0.03F, 0F,    0F,    0.08F, 0F,  0F,  ID.EquipType.RADAR_LO,     3400F,  2F,  200,  0});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SONAR * 100,        	new float[]{2F, 0F,  2F,  4F,  2F,  4F,  3F, 0F,   -0.02F, 1F,   0F,    0F,    0F,    0.03F, 0F,  16F, ID.EquipType.RADAR_LO,     4000F,  3F,  200,  0});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_AIRABYSS * 100,     	new float[]{2F, 0F,  6F,  0F,  6F,  0F,  10F,0F,   -0.08F, 2F,   0.04F, 0F,    0F,    0.12F, 40F, 0F,  ID.EquipType.RADAR_HI,     1000F,  4F,  2000, 0});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SURABYSS * 100,     	new float[]{2F, 0F,  0F,  14F, 0F,  20F, 10F,0F,   -0.08F, 2F,   0.04F, 0F,    0F,    0.12F, 0F,  0F,  ID.EquipType.RADAR_HI,     2000F,  4F,  2000, 0});	
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SONARMK2 * 100,     	new float[]{2F, 0F,  4F,  8F,  4F,  10F, 6F, 0F,   -0.04F, 1F,   0.02F, 0F,    0F,    0.06F, 0F,  32F, ID.EquipType.RADAR_HI,     3200F,  4F,  2000, 0});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_FCSCIC * 100,       	new float[]{2F, 0F,  6F,  18F, 6F,  18F, 10F,0F,   -0.12F, 2F,   0.04F, 0F,    0F,    0.1F,  40F, 40F, ID.EquipType.RADAR_HI,     4000F,  6F,  2000, 0});
		//turbine                                                                                   Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE * 100,            new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   0.15F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.TURBINE_LO,   1000F,  6F,  1400, 0});
		put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE_IMP * 100,        new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   0.2F,   0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.TURBINE_LO,   4000F,  8F,  1400, 0});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_ENH * 100,        new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   0.25F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.TURBINE_HI,   2000F,  10F, 3200, 0});
		//armor
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR * 100,              	new float[]{2F, 100F,0F,  0F,  0F,  0F,  8F, 0F,   -0.1F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.ARMOR_LO,     2000F,  0F,  80,   1});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ENH * 100,          	new float[]{2F, 320F,0F,  0F,  0F,  0F,  16F,0F,   -0.2F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.ARMOR_HI,     2000F,  0F,  500,  1});
		//catapult
		put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_F * 100,        new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 0.5F, -0.1F,  2F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_LO,  1000F,  0F,  2800, 3});
		put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_H * 100,        new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 0.8F, -0.18F, 4F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_LO,  2000F,  0F,  2800, 3});
		put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_C * 100,        new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 1.2F, -0.26F, 6F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_HI,  3200F,  0F,  5000, 3});
		put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_E * 100,        new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 2.0F, -0.34F, 8F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_HI,  4000F,  0F,  5000, 3});
		//drum
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_N * 100,        		new float[]{2F, 0,   0F,  0F,  0F,  0F,  0F, 0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.DRUM_LO,  	  2000F,  0F,  120,  1});
		//compass
		put((int)ID.EquipType.COMPASS_LO + (int)ID.EquipSubID.COMPASS * 100,        	new float[]{2F, 0,   0F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 1F,   0F,    0F,    0F,    0.05F, 0F,  0F,  ID.EquipType.COMPASS_LO,   2000F,  0F,  90,   0});
		//flare
		put((int)ID.EquipType.FLARE_LO + (int)ID.EquipSubID.FLARE * 100,        		new float[]{1F, 0,   0F,  0F,  0F,  0F,  0F, 0F,   -0.02F, 0F,   0.04F, 0F,    0F,    0.04F, 0F,  0F,  ID.EquipType.FLARE_LO,  	  2000F,  0F,  80,   2});
		//searchlight                                                                               Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put((int)ID.EquipType.SEARCHLIGHT_LO + (int)ID.EquipSubID.SEARCHLIGHT * 100,    new float[]{2F, 0,   0F,  0F,  0F,  0F,  -6F,0F,   -0.02F, 0F,   0.06F, 0F,    0F,    0.08F, 0F,  0F,  ID.EquipType.SEARCHLIGHT_LO,2000F, -6F, 80,   0});
				
	}});
	
	
	//zero buff
	public static final float[] zeros13 = new float[] {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
		

	/** FORMATION BUFFS MAP
	 * 
	 *  attr id: ID.Formation
	 *  put id: formatID * 10 + slotID
	 *  
	 *  ex: formation 2, slot 4 = 24
	 */
	public static final Map<Integer, float[]> FormationBuffsMap = Collections.unmodifiableMap(new HashMap<Integer, float[]>() {{
		//Line Ahead          ATK_L ATK_H ATK_AL ATK_AH DEF   MOV    MISS DODGE CRI  DHIT THIT AA    ASM
		put(10,  new float[] {75F,  75F,  30F,   30F,   -70F, 0.08F, 75F, -30F, 75F, 50F, 50F, -70F, -70F});
		put(11,  new float[] {50F,  50F,  20F,   20F,   -60F, 0.08F, 50F, -25F, 50F, 45F, 45F, -64F, -64F});
		put(12,  new float[] {45F,  45F,  15F,   15F,   -50F, 0.08F, 45F, -20F, 45F, 40F, 40F, -58F, -58F});
		put(13,  new float[] {40F,  40F,  10F,   10F,   -40F, 0.08F, 40F, -15F, 40F, 35F, 35F, -52F, -52F});
		put(14,  new float[] {35F,  35F,  5F,    5F,    -30F, 0.08F, 35F, -10F, 35F, 30F, 30F, -46F, -46F});
		put(15,  new float[] {30F,  30F,  0F,    0F,    -20F, 0.08F, 30F, -5F,  30F, 25F, 25F, -40F, -40F});
		//Double Line         ATK_L ATK_H ATK_AL ATK_AH DEF   MOV    MISS DODGE CRI  DHIT THIT AA    ASM
		put(20,  new float[] {35F,  25F,  15F,   15F,   25F,  0F,    30F, -25F, 30F, 25F, 25F, 0F,   0F});
		put(21,  new float[] {35F,  25F,  15F,   15F,   25F,  0F,    30F, -25F, 30F, 25F, 25F, 0F,   0F});
		put(22,  new float[] {35F,  25F,  10F,   10F,   -25F, 0F,    20F, 15F,  20F, 25F, 25F, 0F,   0F});
		put(23,  new float[] {35F,  25F,  10F,   10F,   -25F, 0F,    20F, 15F,  20F, 25F, 25F, 0F,   0F});
		put(24,  new float[] {35F,  25F,  10F,   10F,   -25F, 0F,    20F, 15F,  20F, 25F, 25F, 0F,   0F});
		put(25,  new float[] {35F,  25F,  10F,   10F,   -25F, 0F,    20F, 15F,  20F, 25F, 25F, 0F,   0F});
		//Diamond             ATK_L ATK_H ATK_AL ATK_AH DEF   MOV    MISS DODGE CRI  DHIT THIT AA    ASM
		put(30,  new float[] {-50F, -70F, 100F,  100F,  50F,  -0.1F, 0F,  -50F, 0F,  0F,  0F,  75F,  0F});
		put(31,  new float[] {0F,   0F,   20F,   20F,   25F,  -0.1F, 10F, 0F,   10F, 0F,  0F,  100F, 75F});
		put(32,  new float[] {0F,   0F,   20F,   20F,   25F,  -0.1F, 10F, 0F,   10F, 0F,  0F,  100F, 75F});
		put(33,  new float[] {0F,   0F,   20F,   20F,   25F,  -0.1F, 10F, 0F,   10F, 0F,  0F,  100F, 75F});
		put(34,  new float[] {0F,   0F,   20F,   20F,   25F,  -0.1F, 10F, 0F,   10F, 0F,  0F,  100F, 75F});
		put(35,  new float[] {-50F, -70F, 100F,  100F,  50F,  -0.1F, 0F,  -50F, 0F,  0F,  0F,  75F,  0F});
		//Echelon             ATK_L ATK_H ATK_AL ATK_AH DEF   MOV    MISS DODGE CRI  DHIT THIT AA    ASM
		put(40,  new float[] {20F,  30F,  0F,    0F,    -25F, 0.15F, 25F, -25F, 25F, 25F, 25F, -25F, 25F});
		put(41,  new float[] {15F,  25F,  0F,    0F,    -15F, 0.15F, 20F, -15F, 20F, 20F, 20F, -25F, 25F});
		put(42,  new float[] {10F,  20F,  0F,    0F,    -5F,  0.15F, 15F, -5F,  15F, 15F, 15F, -25F, 25F});
		put(43,  new float[] {5F,   15F,  0F,    0F,    5F,   0.15F, 10F, 5F,   10F, 10F, 10F, -25F, 25F});
		put(44,  new float[] {0F,   10F,  0F,    0F,    15F,  0.15F, 5F,  15F,  5F,  5F,  5F,  -25F, 25F});
		put(45,  new float[] {-5F,  5F,   0F,    0F,    25F,  0.15F, 0F,  25F,  0F,  0F,  0F,  -25F, 25F});
		//Line Abreast        ATK_L ATK_H ATK_AL ATK_AH DEF   MOV    MISS DODGE CRI  DHIT THIT AA    ASM
		put(50,  new float[] {-20F, -25F, -10F,  -10F,  25F,  0.05F, 15F, 75F,  15F, 0F,  0F, -30F,  100F});
		put(51,  new float[] {-20F, -25F, -10F,  -10F,  25F,  0.05F, 15F, 75F,  15F, 0F,  0F, -30F,  100F});
		put(52,  new float[] {-20F, -25F, -10F,  -10F,  25F,  0.05F, 15F, 75F,  15F, 0F,  0F, -30F,  100F});
		put(53,  new float[] {-20F, -25F, -10F,  -10F,  25F,  0.05F, 15F, 75F,  15F, 0F,  0F, -30F,  100F});
		put(54,  new float[] {-20F, -25F, -10F,  -10F,  25F,  0.05F, 15F, 75F,  15F, 0F,  0F, -30F,  100F});
		put(55,  new float[] {-20F, -25F, -10F,  -10F,  25F,  0.05F, 15F, 75F,  15F, 0F,  0F, -30F,  100F});
		//zero buff for formation 0
		put(0,  zeros13);
		put(1,  zeros13);
		put(2,  zeros13);
		put(3,  zeros13);
		put(4,  zeros13);
		put(5,  zeros13);
	}});
	
	
	/** ITEMSTACK MAP FOR ICON
	 *  
	 */
	public static final Map<Byte, ItemStack> ItemIconMap = Collections.unmodifiableMap(new HashMap<Byte, ItemStack>() {{
		put(ID.Icon.IronIG,     new ItemStack(Items.iron_ingot));
		put(ID.Icon.Grudge,     new ItemStack(ModItems.Grudge));
		put(ID.Icon.GrudgeB,    new ItemStack(ModBlocks.BlockGrudge));
		put(ID.Icon.GrudgeBH,   new ItemStack(ModBlocks.BlockGrudgeHeavy));
		put(ID.Icon.AbyssIG,    new ItemStack(ModItems.AbyssMetal, 1, 0));
		put(ID.Icon.AbyssB,     new ItemStack(ModBlocks.BlockAbyssium));
		put(ID.Icon.PolymIG,    new ItemStack(ModItems.AbyssMetal, 1, 1));
		put(ID.Icon.PolymOre,   new ItemStack(ModBlocks.BlockPolymetalOre));
		put(ID.Icon.PolymB,     new ItemStack(ModBlocks.BlockPolymetal));
		put(ID.Icon.PolymBG,    new ItemStack(ModBlocks.BlockPolymetalGravel));
		put(ID.Icon.Gunpowder,  new ItemStack(Items.gunpowder));
		put(ID.Icon.Blazepowder,new ItemStack(Items.blaze_powder));
		put(ID.Icon.AmmoL,      new ItemStack(ModItems.Ammo, 1, 0));
		put(ID.Icon.AmmoLC,     new ItemStack(ModItems.Ammo, 1, 1));
		put(ID.Icon.AmmoH,      new ItemStack(ModItems.Ammo, 1, 2));
		put(ID.Icon.AmmoHC,     new ItemStack(ModItems.Ammo, 1, 3));
		put(ID.Icon.RpBucket,   new ItemStack(ModItems.BucketRepair));
		put(ID.Icon.LaBucket,   new ItemStack(Items.lava_bucket));
		put(ID.Icon.NStar,      new ItemStack(Items.nether_star));
		put(ID.Icon.Ring,       new ItemStack(ModItems.MarriageRing));
		put(ID.Icon.Paper,      new ItemStack(Items.paper));
		put(ID.Icon.OwnPaper,   new ItemStack(ModItems.OwnerPaper));
		put(ID.Icon.Stick,      new ItemStack(Items.stick));
		put(ID.Icon.KHammer,    new ItemStack(ModItems.KaitaiHammer));
		put(ID.Icon.ModTool,    new ItemStack(ModItems.ModernKit));
		put(ID.Icon.SpawnEgg0,  new ItemStack(ModItems.ShipSpawnEgg, 1, 0));
		put(ID.Icon.SpawnEgg1,  new ItemStack(ModItems.ShipSpawnEgg, 1, 1));
		put(ID.Icon.SpawnEgg2,  new ItemStack(ModItems.ShipSpawnEgg, 1, 2));
		put(ID.Icon.InstantMat, new ItemStack(ModItems.InstantConMat));
		put(ID.Icon.DiamondB,   new ItemStack(Blocks.diamond_block));
		put(ID.Icon.RpGod,      new ItemStack(ModItems.RepairGoddess));
		put(ID.Icon.Pointer,    new ItemStack(ModItems.PointerItem));
		put(ID.Icon.ModelZF,    new ItemStack(ModItems.ToyAirplane));
		put(ID.Icon.Desk,       new ItemStack(ModBlocks.BlockDesk));
		put(ID.Icon.DeskBook,   new ItemStack(ModItems.DeskItemBook));
		put(ID.Icon.DeskRadar,  new ItemStack(ModItems.DeskItemRadar));
		put(ID.Icon.WriteBook,  new ItemStack(Items.writable_book));
		put(ID.Icon.Compass,    new ItemStack(Items.compass));
		put(ID.Icon.ObsidianB,  new ItemStack(Blocks.obsidian));
		put(ID.Icon.WoolB,      new ItemStack(Blocks.wool));
		put(ID.Icon.SmallSY,    new ItemStack(ModBlocks.BlockSmallShipyard));
		put(ID.Icon.Wrench,     new ItemStack(ModItems.TargetWrench));
		put(ID.Icon.VolCore,    new ItemStack(ModBlocks.BlockVolCore));
		put(ID.Icon.VolBlock,   new ItemStack(ModBlocks.BlockVolBlock));
		put(ID.Icon.Frame,      new ItemStack(ModBlocks.BlockFrame));
		put(ID.Icon.Waypoint,   new ItemStack(ModBlocks.BlockWaypoint));
		put(ID.Icon.Crane,      new ItemStack(ModBlocks.BlockCrane));
		put(ID.Icon.Piston,     new ItemStack(Blocks.piston));
	}});
	
	
	/** BOOK CONTENT MAP
	 *  map<int, List<int[]>> = <ChapPage#, Content List<Content Array>>
	 *  
	 *  ChapPage#: ex: 1020 = chap 1, page 20
	 *                 2018 = chap 2, page 18
	 *                 
	 *  Content List: list of content in int array               
	 *  
	 *  Content Array: 0:c.type  1:page pos  2:posX  3:posY  4...N:parms
	 *    c.type: 0:text  1:picture  2:item icon
	 *    page pos: 0:left  1:right
	 *    posX: if picture: x pixels offset
	 *    posY: if picture: y pixels offset
	 *    parms: 
	 *      for picture: 4:picID  5:picU  6:picV  7:sizeX  8:sizeY
	 *      for item icon: 4:iconID
	 *    
	 */
	public static final Map<Integer, List> BookList = Collections.unmodifiableMap(new HashMap<Integer, List>() {{
		//chap 0: introduction
		//page 0
		put(0, Arrays.asList(   new int[] {0, 0, 0, 0},
							    new int[] {0, 1, 0, 0}));
		//page 1
		put(1, Arrays.asList(   new int[] {0, 0, 0, 0},
							    new int[] {0, 1, 0, 0}));
		
		//chap 1: resources
		//page 0: grudge
		put(1000, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, 76, 0, 0, 0, 100, 56},
								new int[] {2, 0, 13, -3, ID.Icon.Grudge},
								new int[] {2, 0, 43, -3, ID.Icon.GrudgeB},
								new int[] {2, 0, 73, -3, ID.Icon.GrudgeBH}));
		//page 1: abyssium
		put(1001, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3, 17,  ID.Icon.Grudge},
								new int[] {2, 0, 23, 17, ID.Icon.IronIG},
								new int[] {2, 0, 81, 17, ID.Icon.AbyssIG}));
		//page 2: polymetal
		put(1002, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 0, 56, 100, 56},
								new int[] {1, 1, 0, 73, 0, 0, 112, 100, 59},
								new int[] {2, 0, 5, 52,  ID.Icon.PolymOre},
								new int[] {2, 0, 30, 52, ID.Icon.PolymIG},
								new int[] {2, 0, 55, 52, ID.Icon.PolymB},
								new int[] {2, 0, 80, 52, ID.Icon.PolymBG}));
		//page 3: ammo
		put(1003, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.IronIG},
								new int[] {2, 0, 23, -3, ID.Icon.IronIG},
								new int[] {2, 0, 43, -3, ID.Icon.IronIG},
								new int[] {2, 0, 3,  17, ID.Icon.IronIG},
								new int[] {2, 0, 23, 17, ID.Icon.Grudge},
								new int[] {2, 0, 43, 17, ID.Icon.IronIG},
								new int[] {2, 0, 3,  37, ID.Icon.IronIG},
								new int[] {2, 0, 23, 37, ID.Icon.Gunpowder},
								new int[] {2, 0, 43, 37, ID.Icon.IronIG},
								new int[] {2, 0, 81, 17, ID.Icon.AmmoL},
								new int[] {2, 1, 3, 110, ID.Icon.AmmoL},
								new int[] {2, 1, 28, 110,ID.Icon.AmmoLC},
								new int[] {2, 1, 53, 110,ID.Icon.AmmoH},
								new int[] {2, 1, 78, 110,ID.Icon.AmmoHC}));
		//page 4: repair bucket
		put(1004, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  17, ID.Icon.LaBucket},
								new int[] {2, 0, 23, 17, ID.Icon.Grudge},
								new int[] {2, 0, 81, 17, ID.Icon.RpBucket}));
		//page 5: ring
		put(1005, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, -3, ID.Icon.NStar},
								new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 3,  37, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, 37, ID.Icon.AbyssIG},
								new int[] {2, 0, 81, 17, ID.Icon.Ring}));
		//page 6: ownership paper
		put(1006, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  17, ID.Icon.Paper},
								new int[] {2, 0, 23, 17, ID.Icon.Grudge},
								new int[] {2, 0, 81, 17, ID.Icon.OwnPaper}));
		//page 7: hammer
		put(1007, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Icon.Stick},
								new int[] {2, 0, 81, 17, ID.Icon.KHammer}));
		//page 8: modernization toolkit
		put(1008, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.KHammer},
								new int[] {2, 0, 23, -3, ID.Icon.SpawnEgg2},
								new int[] {2, 0, 43, -3, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 3,  17, ID.Icon.SpawnEgg2},
								new int[] {2, 0, 23, 17, ID.Icon.SpawnEgg2},
								new int[] {2, 0, 43, 17, ID.Icon.SpawnEgg1},
								new int[] {2, 0, 3,  37, ID.Icon.SpawnEgg2},
								new int[] {2, 0, 23, 37, ID.Icon.SpawnEgg1},
								new int[] {2, 0, 43, 37, ID.Icon.SpawnEgg2},
								new int[] {2, 0, 81, 17, ID.Icon.ModTool}));
		//page 9: instant mats
		put(1009, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  17, ID.Icon.KHammer},
								new int[] {2, 0, 23, 17, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 81, 17, ID.Icon.InstantMat}));
		//page 10: abyssal goddess
		put(1010, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.GrudgeB},
								new int[] {2, 0, 23, -3, ID.Icon.GrudgeBH},
								new int[] {2, 0, 43, -3, ID.Icon.GrudgeB},
								new int[] {2, 0, 3,  17, ID.Icon.GrudgeBH},
								new int[] {2, 0, 23, 17, ID.Icon.DiamondB},
								new int[] {2, 0, 43, 17, ID.Icon.GrudgeBH},
								new int[] {2, 0, 3,  37, ID.Icon.GrudgeB},
								new int[] {2, 0, 23, 37, ID.Icon.GrudgeBH},
								new int[] {2, 0, 43, 37, ID.Icon.GrudgeB},
								new int[] {2, 0, 81, 17, ID.Icon.RpGod}));
		//page 11: pointer
		put(1011, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  37, ID.Icon.PolymIG},
								new int[] {2, 0, 23, 17, ID.Icon.PolymIG},
								new int[] {2, 0, 43, -3, ID.Icon.GrudgeB},
								new int[] {2, 0, 81, 17, ID.Icon.Pointer}));
		//page 12: pointer
		put(1012, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 13: pointer
		put(1013, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 14: model zero fighter
		put(1014, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 23, -3, ID.Icon.PolymIG},
								new int[] {2, 0, 3,  17, ID.Icon.PolymIG},
								new int[] {2, 0, 23, 17, ID.Icon.PolymIG},
								new int[] {2, 0, 43, 17, ID.Icon.PolymIG},
								new int[] {2, 0, 23, 37, ID.Icon.PolymIG},
								new int[] {2, 0, 81, 17, ID.Icon.ModelZF}));
		//page 15: desk item book
		put(1015, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.Grudge},
								new int[] {2, 0, 23, -3, ID.Icon.Grudge},
								new int[] {2, 0, 43, -3, ID.Icon.Grudge},
								new int[] {2, 0, 3,  17, ID.Icon.Grudge},
								new int[] {2, 0, 23, 17, ID.Icon.WriteBook},
								new int[] {2, 0, 43, 17, ID.Icon.Grudge},
								new int[] {2, 0, 3,  37, ID.Icon.Grudge},
								new int[] {2, 0, 23, 37, ID.Icon.Grudge},
								new int[] {2, 0, 43, 37, ID.Icon.Grudge},
								new int[] {2, 0, 81, 17, ID.Icon.DeskBook}));
		//page 16: desk item radar
		put(1016, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.Grudge},
								new int[] {2, 0, 23, -3, ID.Icon.Grudge},
								new int[] {2, 0, 43, -3, ID.Icon.Grudge},
								new int[] {2, 0, 3,  17, ID.Icon.Grudge},
								new int[] {2, 0, 23, 17, ID.Icon.Compass},
								new int[] {2, 0, 43, 17, ID.Icon.Grudge},
								new int[] {2, 0, 3,  37, ID.Icon.Grudge},
								new int[] {2, 0, 23, 37, ID.Icon.Grudge},
								new int[] {2, 0, 43, 37, ID.Icon.Grudge},
								new int[] {2, 0, 81, 17, ID.Icon.DeskRadar}));
		//page 17: desk
		put(1017, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.DeskRadar},
								new int[] {2, 0, 23, -3, ID.Icon.DeskBook},
								new int[] {2, 0, 43, -3, ID.Icon.WoolB},
								new int[] {2, 0, 3,  17, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, 17, ID.Icon.ObsidianB},
								new int[] {2, 0, 43, 17, ID.Icon.ObsidianB},
								new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
								new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
								new int[] {2, 0, 81, 17, ID.Icon.Desk}));
		//page 18: wrench
		put(1018, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Icon.AbyssIG},
								new int[] {2, 0, 81, 17, ID.Icon.Wrench}));
		//page 19: volcano block
		put(1019, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, -3, ID.Icon.GrudgeBH},
								new int[] {2, 0, 43, -3, ID.Icon.ObsidianB},
								new int[] {2, 0, 3,  17, ID.Icon.GrudgeBH},
								new int[] {2, 0, 23, 17, ID.Icon.LaBucket},
								new int[] {2, 0, 43, 17, ID.Icon.GrudgeBH},
								new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, 37, ID.Icon.GrudgeBH},
								new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
								new int[] {2, 0, 81, 17, ID.Icon.VolBlock}));
		//page 20: volcano core
		put(1020, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, -3, ID.Icon.VolBlock},
								new int[] {2, 0, 43, -3, ID.Icon.ObsidianB},
								new int[] {2, 0, 3,  17, ID.Icon.VolBlock},
								new int[] {2, 0, 23, 17, ID.Icon.LaBucket},
								new int[] {2, 0, 43, 17, ID.Icon.VolBlock},
								new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, 37, ID.Icon.VolBlock},
								new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
								new int[] {2, 0, 81, 17, ID.Icon.VolCore}));
		//page 21: frame
		put(1021, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 17, ID.Icon.ObsidianB},
								new int[] {2, 0, 3,  37, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, 37, ID.Icon.AbyssIG},
								new int[] {2, 0, 81, 17, ID.Icon.Frame}));
		//page 22: waypoint
		put(1022, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 23, 17, ID.Icon.Grudge},
								new int[] {2, 0, 23, 37, ID.Icon.Stick},
								new int[] {2, 0, 81, 17, ID.Icon.Waypoint}));
		//page 23: crane
		put(1023, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, -3, ID.Icon.AbyssIG},
								new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 17, ID.Icon.GrudgeB},
								new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 3,  37, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Icon.Piston},
								new int[] {2, 0, 43, 37, ID.Icon.AbyssIG},
								new int[] {2, 0, 81, 17, ID.Icon.Crane}));
		//chap 2: construction
		//page 0: vent
		put(2000, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {1, 1, 0, -6, 0, 100, 134, 100, 46},
								new int[] {2, 0, 3,  -3, ID.Icon.Grudge},
								new int[] {2, 0, 23, -3, ID.Icon.LaBucket},
								new int[] {2, 0, 43, -3, ID.Icon.Grudge},
								new int[] {2, 0, 3,  17, ID.Icon.LaBucket},
								new int[] {2, 0, 23, 17, ID.Icon.ObsidianB},
								new int[] {2, 0, 43, 17, ID.Icon.LaBucket},
								new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, 37, ID.Icon.ObsidianB},
								new int[] {2, 0, 43, 37, ID.Icon.ObsidianB},
								new int[] {2, 0, 81, 17, ID.Icon.SmallSY}));
		//page 1: vent
		put(2001, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 25, -12, 0, 0, 230, 50, 26}));
		//page 2: vent
		put(2002, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 25, -12, 0, 50, 230, 50, 26}));
		//page 3: vortex
		put(2003, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, 60, 0, 100, 180, 100, 65},
								new int[] {1, 1, -7, -18, 0, 200, 0, 38, 38},
								new int[] {1, 1, 31, -18, 0, 200, 38, 38, 38},
								new int[] {1, 1, 69, -18, 0, 200, 76, 38, 38}));
		//page 4: vortex
		put(2004, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//chap 3:
		//page 13: morale
		put(3013, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, 120, 0, 100, 245, 100, 11}));
		
	}});
	
	
	
	
}
