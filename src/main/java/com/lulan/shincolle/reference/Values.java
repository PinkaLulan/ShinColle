package com.lulan.shincolle.reference;

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
	
	
	//numbers
	public static final class N {
		public static final float RAD_DIV = 57.295779513F;
		public static final float RAD_MUL = 0.0174532925F;
	}
	
	
	//color code
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
		put(ID.Ship.DestroyerI,         new float[] {20F,  3F,  5F,  0.8F, 0.5F,  6F,  0.3F,  0.25F, 0.11F, 0.5F,  1F,    0.4F});
		put(ID.Ship.DestroyerRO,        new float[] {22F,  4F,  6F,  0.8F, 0.5F,  6F,  0.32F, 0.28F, 0.12F, 0.5F,  1F,    0.4F});
		put(ID.Ship.DestroyerHA,        new float[] {24F,  3F,  7F,  0.8F, 0.5F,  6F,  0.34F, 0.25F, 0.13F, 0.5F,  1F,    0.4F});
		put(ID.Ship.DestroyerNI,        new float[] {28F,  4F,  9F,  0.8F, 0.5F,  6F,  0.36F, 0.28F, 0.15F, 0.5F,  1F,    0.4F});
		//cruiser
//		put(ID.Ship.LightCruiserHO,     new float[] {33F,  6F,  11F, 0.8F, 0.45F, 8F,  0.38F, 0.3F,  0.17F, 0.53F, 0.9F,  0.45F});
//		put(ID.Ship.LightCruiserHE,     new float[] {36F,  9F,  13F, 0.8F, 0.45F, 8F,  0.4F,  0.35F, 0.18F, 0.53F, 0.9F,  0.45F});
//		put(ID.Ship.LightCruiserTO,     new float[] {39F,  8F,  15F, 0.8F, 0.45F, 8F,  0.42F, 0.32F, 0.19F, 0.53F, 0.9F,  0.45F});
//		put(ID.Ship.LightCruiserTSU,    new float[] {48F,  12F, 16F, 0.8F, 0.45F, 8F,  0.44F, 0.38F, 0.20F, 0.53F, 0.9F,  0.45F});
//		put(ID.Ship.TorpedoCruiserCHI,  new float[] {48F,  16F, 18F, 0.8F, 0.42F, 9F,  0.46F, 0.44F, 0.21F, 0.56F, 0.84F, 0.45F});
		put(ID.Ship.HeavyCruiserRI,     new float[] {58F,  14F, 18F, 0.8F, 0.42F, 9F,  0.48F, 0.4F,  0.21F, 0.56F, 0.84F, 0.45F});
		put(ID.Ship.HeavyCruiserNE,     new float[] {62F,  15F, 19F, 0.8F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.45F});
		//carrier                                    HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.LightCarrierNU,     new float[] {65F,  20F, 20F, 0.4F, 0.32F, 14F, 0.52F, 0.45F, 0.22F, 0.5F,  0.64F, 0.6F});
		put(ID.Ship.CarrierWO,          new float[] {85F,  25F, 21F, 0.8F, 0.36F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		//battleship
//		put(ID.Ship.BattleshipRU,       new float[] {90F,  30F, 25F, 0.4F, 0.32F, 10F, 0.7F,  0.63F, 0.25F, 0.65F, 0.64F, 0.5F});
		put(ID.Ship.BattleshipTA,       new float[] {84F,  19F, 23F, 1.0F, 0.42F, 10F, 0.65F, 0.55F, 0.24F, 0.7F,  0.84F, 0.5F});
		put(ID.Ship.BattleshipRE,       new float[] {120F, 25F, 25F, 0.9F, 0.36F, 12F, 0.8F,  0.6F,  0.25F, 0.63F, 0.72F, 0.5F});
		//transport
//		put(ID.Ship.TransportWA,        new float[] {70F,  2F,  10F, 0.8F, 0.3F,  5F,  0.6F,  0.25F, 0.16F, 0.5F,  0.6F,  0.3F});
		//submarine
//		put(ID.Ship.SubmarineKA,        new float[] {40F,  23F, 9F,  0.6F, 0.3F,  5F,  0.35F, 0.63F, 0.14F, 0.7F,  0.6F,  0.3F});
//		put(ID.Ship.SubmarineYO,        new float[] {36F,  26F, 10F, 0.6F, 0.3F,  5F,  0.33F, 0.7F,  0.16F, 0.7F,  0.6F,  0.3F});
//		put(ID.Ship.SubmarineSO,        new float[] {34F,  35F, 12F, 0.6F, 0.28F, 5.5F,0.3F,  0.78F, 0.18F, 0.7F,  0.6F,  0.3F});
		//demon                                      HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.IsolatedDemon,      new float[] {225F, 13F, 34F, 0.7F, 0.22F, 24F, 1.3F,  0.4F,  0.29F, 0.6F,  0.44F, 0.8F});
//		put(ID.Ship.LightCruiserDemon,  new float[] {130F, 30F, 25F, 0.8F, 0.45F, 13F, 0.8F,  0.65F, 0.25F, 0.6F,  0.9F,  0.55F});
		//hime                                       HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.AirdefenseHime,     new float[] {120F, 30F, 35F, 0.8F, 0.5F,  12F, 0.8F,  0.7F,  0.3F,  0.6F,  1F,    0.5F});
		put(ID.Ship.AirfieldHime,       new float[] {240F, 16F, 32F, 0.8F, 0.3F,  26F, 1.2F,  0.45F, 0.28F, 0.6F,  0.6F,  0.8F});
//		put(ID.Ship.AnchorageHime,      new float[] {150F, 19F, 32F, 0.7F, 0.3F,  23F, 0.95F, 0.5F,  0.28F, 0.6F,  0.6F,  0.8F});
//		put(ID.Ship.ArmoredCarrierHime, new float[] {200F, 34F, 35F, 0.8F, 0.42F, 20F, 0.9F,  0.73F, 0.3F,  0.63F, 0.84F, 0.7F});
		put(ID.Ship.BattleshipHime,     new float[] {220F, 42F, 40F, 0.8F, 0.4F,  16F, 1.0F,  0.8F,  0.32F, 0.73F, 0.8F,  0.6F});
//		put(ID.Ship.CarrierHime,        new float[] {180F, 40F, 32F, 0.8F, 0.45F, 22F, 0.85F, 0.77F, 0.3F,  0.65F, 0.9F,  0.7F});
//		put(ID.Ship.DestroyerHime,      new float[] {90F,  22F, 20F, 0.8F, 0.5F,  12F, 0.55F, 0.5F,  0.22F, 0.6F,  1F,    0.5F});
		put(ID.Ship.HarbourHime,        new float[] {260F, 14F, 36F, 0.6F, 0.2F,  24F, 1.35F, 0.4F,  0.3F,  0.6F,  0.4F,  0.8F});
//		put(ID.Ship.MidwayHime,         new float[] {350F, 22F, 45F, 0.6F, 0.25F, 30F, 1.5F,  0.5F,  0.34F, 0.6F,  0.4F,  0.8F});
		put(ID.Ship.NorthernHime,       new float[] {210F, 13F, 30F, 0.6F, 0.32F, 22F, 1.15F, 0.35F, 0.27F, 0.6F,  0.64F, 0.8F});
//		put(ID.Ship.SeaplaneHime,       new float[] {170F, 24F, 25F, 0.8F, 0.45F, 18F, 1F,    0.6F,  0.25F, 0.63F, 0.9F,  0.65F});
//		put(ID.Ship.SouthernHime,       new float[] {170F, 35F, 34F, 0.8F, 0.3F,  20F, 1F,    0.73F, 0.29F, 0.63F, 0.6F,  0.7F});
//		put(ID.Ship.SubmarineHime,      new float[] {75F,  45F, 18F, 0.7F, 0.3F,  10F, 0.65F, 0.85F, 0.2F,  0.6F,  0.6F,  0.4F});
//		put(ID.Ship.LightCruiserHime,   new float[] {160F, 32F, 26F, 0.8F, 0.45F, 14F, 0.82F, 0.7F,  0.25F, 0.6F,  0.9F,  0.55F});
//		put(ID.Ship.HeavyCruiserHime,   new float[] {180F, 35F, 28F, 0.8F, 0.45F, 15F, 0.85F, 0.75F, 0.25F, 0.6F,  0.9F,  0.6F});
//		put(ID.Ship.SupplyDepotHime,    new float[] {320F, 16F, 38F, 0.6F, 0.18F, 26F, 1.4F,  0.42F, 0.32F, 0.6F,  0.4F,  0.8F});
		//	
		//water demon                                HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put(ID.Ship.AnchorageWD,        new float[] {230F, 28F, 35F, 0.8F, 0.35F, 26F, 1.35F, 0.6F,  0.3F,  0.7F,  0.7F,  1F});
//		put(ID.Ship.BattleshipWD,       new float[] {280F, 50F, 45F, 0.8F, 0.42F, 21F, 1.1F,  1F,    0.34F, 0.85F, 0.84F, 0.7F});
		put(ID.Ship.CarrierWD,          new float[] {190F, 45F, 40F, 0.8F, 0.42F, 25F, 1F,    0.95F, 0.32F, 0.75F, 0.84F, 0.8F});
//		put(ID.Ship.HarbourWD,          new float[] {300F, 35F, 45F, 0.8F, 0.35F, 29F, 1.5F,  0.63F, 0.34F, 0.7F,  0.7F,  1F});
//		put(ID.Ship.DestroyerWD,        new float[] {150F, 23F, 25F, 0.8F, 0.55F, 15F, 0.9F,  0.8F,  0.3F,  0.7F,  1F,    0.6F});
		//hostile ship                               HP    ATK  DEF  SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
		put(ID.Ship.DestroyerShimakaze, new float[] {38F,  11F, 12F, 0.8F, 0.6F,  9F,  0.35F, 0.38F, 0.16F, 0.55F, 1.2F,  0.45F});
		put(ID.Ship.BattleshipNagato,   new float[] {135F, 40F, 26F, 0.8F, 0.32F, 14F, 0.85F, 0.8F,  0.25F, 0.63F, 0.64F, 0.6F});
		put(ID.Ship.BattleshipYamato,   new float[] {150F, 55F, 36F, 0.8F, 0.3F,  20F, 1F,    1F,    0.3F,  0.7F,  0.6F,  0.7F});
		put(ID.Ship.SubmarineU511,      new float[] {28F,  30F, 7F,  0.6F, 0.3F,  7F,  0.3F,  0.7F,  0.13F, 0.7F,  0.6F,  0.4F});
		put(ID.Ship.SubmarineRo500,     new float[] {32F,  32F, 10F, 0.6F, 0.3F,  8F,  0.33F, 0.75F, 0.16F, 0.7F,  0.6F,  0.4F});
		put(ID.Ship.CarrierKaga,        new float[] {70F,  22F, 21F, 0.8F, 0.34F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		put(ID.Ship.CarrierAkagi,       new float[] {75F,  22F, 20F, 0.8F, 0.32F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});

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
	 * equip type:
	 * 		 0:unused
	 *       1:cannon, torpedo
	 *       2:aircraft-R, engine, armor, radar
	 *       3:aircraft-T/F/B
	 *       
	 * note: 新增裝備要記得在LargeRecipe新增回收價格
	 */
	public static final Map<Byte, float[]> EquipMap = Collections.unmodifiableMap(new HashMap<Byte, float[]>() {{
		//single cannon                          Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put(ID.E_CANNON_SINGLE_5,    new float[]{1F, 0F,  2F,  0F,  0F,  0F,  0F, 0.1F, -0.01F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CANNON_SI,    1000F, 0F});
		put(ID.E_CANNON_SINGLE_6,    new float[]{1F, 0F,  3F,  0F,  0F,  0F,  0F, 0.1F, -0.02F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CANNON_SI,    4000F, 0F});
		//twin cannon                            
		put(ID.E_CANNON_TWIN_5,      new float[]{1F, 0F,  4F,  0F,  0F,  0F,  0F, 0.2F, -0.04F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_LO, 1000F, 0F});
		put(ID.E_CANNON_TWIN_6,      new float[]{1F, 0F,  5F,  0F,  0F,  0F,  0F, 0.4F, -0.06F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_LO, 2000F, 0F});
		put(ID.E_CANNON_TWIN_5DP,    new float[]{1F, 0F,  5F,  0F,  0F,  0F,  0F, 0.25F,-0.08F, 1F,   0F,    0.06F, 0F,    0F,    12F, 0F,  ID.EquipType.CANNON_TW_LO, 3200F, 0F});
		put(ID.E_CANNON_TWIN_125,    new float[]{1F, 0F,  8F,  0F,  0F,  0F,  0F, 0.2F, -0.1F,  1.5F, 0F,    0.06F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_LO, 4000F, 0F});
		put(ID.E_CANNON_TWIN_14,     new float[]{1F, 0F,  11F, 0F,  0F,  0F,  0F, 0.2F, -0.12F, 2F,   0F,    0.07F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_HI, 1000F, 0F});
		put(ID.E_CANNON_TWIN_16,     new float[]{1F, 0F,  15F, 0F,  0F,  0F,  0F, 0.2F, -0.15F, 2F,   0F,    0.08F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_HI, 2400F, 0F});
		put(ID.E_CANNON_TWIN_20,     new float[]{1F, 0F,  20F, 0F,  0F,  0F,  0F, 0.2F, -0.2F,  2.5F, 0F,    0.10F, 0F,    0F,    0F,  0F,  ID.EquipType.CANNON_TW_HI, 4000F, 0F});
		//triple cannon
		put(ID.E_CANNON_TRI_8,       new float[]{1F, 0F,  9F,  0F,  0F,  0F,  0F, 0.4F, -0.2F,  1.5F, 0F,    0.06F, 0.06F, 0F,    0F,  0F,  ID.EquipType.CANNON_TR,    1000F, 0F});
		put(ID.E_CANNON_TRI_16,      new float[]{1F, 0F,  17F, 0F,  0F,  0F,  0F, 0.4F, -0.25F, 2F,   0F,    0.08F, 0.08F, 0F,    0F,  0F,  ID.EquipType.CANNON_TR,    2400F, 0F});
		put(ID.E_CANNON_FG_15,       new float[]{1F, 220F,6F,  0F,  0F,  0F,  8F, 0.2F, -0.25F, 2F,   0F,    0.04F, 0.04F, 0F,    0F,  0F,  ID.EquipType.CANNON_TR,    4000F, 0F});
		//machine gun
		put(ID.E_GUN_HA_3,           new float[]{2F, 0F,  1F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    3F,  0F,  ID.EquipType.GUN_LO,       1000F, 0F});
		put(ID.E_GUN_HA_5,           new float[]{2F, 0F,  2F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    5F,  0F,  ID.EquipType.GUN_LO,       2000F, 0F});
		put(ID.E_GUN_SINGLE_12,      new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    8F,  0F,  ID.EquipType.GUN_LO,       3200F, 0F});
		put(ID.E_GUN_SINGLE_20,      new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.01F, 0F,   0F,    0F,    0F,    0F,    16F, 0F,  ID.EquipType.GUN_LO,       4000F, 0F});
		put(ID.E_GUN_TWIN_40,        new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.02F, 0F,   0.01F, 0F,    0F,    0F,    24F, 0F,  ID.EquipType.GUN_HI,       1000F, 0F});
		put(ID.E_GUN_QUAD_40,        new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   -0.04F, 0F,   0.02F, 0F,    0F,    0F,    32F, 0F,  ID.EquipType.GUN_HI,       2400F, 0F});
		put(ID.E_GUN_TWIN_4_CIC,     new float[]{2F, 0F,  3F,  0F,  0F,  0F,  0F, 0F,   -0.06F, 2F,   0.04F, 0F,    0F,    0.06F, 48F, 0F,  ID.EquipType.GUN_HI,       4000F, 0F});
		//torpedo                                Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put(ID.E_TORPEDO_21MK1,      new float[]{1F, 0F,  0F,  6F,  0F,  0F,  0F, 0F,   -0.05F, 0F,   0.03F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_LO,   1000F, 0F});
		put(ID.E_TORPEDO_21MK2,      new float[]{1F, 0F,  0F,  12F, 0F,  0F,  0F, 0F,   -0.08F, 0.5F, 0.05F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_LO,   2400F, 0F});
		put(ID.E_TORPEDO_22MK1,      new float[]{1F, 0F,  0F,  28F, 0F,  0F,  0F, 0.1F, -0.11F, 0.5F, 0.07F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_LO,   4000F, 0F});
		put(ID.E_TORPEDO_CUTTLEFISH, new float[]{1F, 0F,  0F,  40F, 0F,  0F,  0F, 0.1F, -0.14F, 1F,   0.09F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_HI,   1000F, 0F});
		put(ID.E_TORPEDO_HIGHSPEED,  new float[]{1F, 0F,  0F,  50F, 0F,  0F,  0F, 0.15F,-0.18F, 1.5F, 0.11F, 0F,    0F,    0F,    0F,  0F,  ID.EquipType.TORPEDO_HI,   4000F, 0F});
		//Torpedo aircraft
		put(ID.E_AIRCRAFT_TMK1,      new float[]{3F, 0F,  0F,  0F,  4F,  16F, 0F, 0F,   -0.06F, 2F,   0F,    0F,    0F,    0F,    2F,  4F,  ID.EquipType.AIR_T_LO,     250F,  0F});
		put(ID.E_AIRCRAFT_TMK2,      new float[]{3F, 0F,  0F,  0F,  8F,  32F, 0F, 0F,   -0.08F, 2F,   0F,    0F,    0F,    0F,    4F,  8F,  ID.EquipType.AIR_T_LO,     600F,  0F});
		put(ID.E_AIRCRAFT_TMK3,      new float[]{3F, 0F,  0F,  0F,  12F, 54F, 0F, 0F,   -0.11F, 2.5F, 0F,    0F,    0F,    0F,    8F,  12F, ID.EquipType.AIR_T_LO,     1000F, 0F});
		put(ID.E_AIRCRAFT_TAVENGER,  new float[]{3F, 0F,  0F,  0F,  10F, 90F, 0F, 0F,   -0.15F, 2.5F, 0F,    0F,    0F,    0F,    12F, 16F, ID.EquipType.AIR_T_HI,     250F,  0F});
		put(ID.E_AIRCRAFT_TAVENGERK, new float[]{3F, 0F,  0F,  0F,  12F, 128F,0F, 0.1F, -0.18F, 3F,   0F,    0F,    0F,    0F,    16F, 24F, ID.EquipType.AIR_T_HI,     1000F, 0F});
		//Fighter aircraft
		put(ID.E_AIRCRAFT_FMK1,      new float[]{3F, 0F,  0F,  0F,  8F,  0F,  0F, 0F,   -0.04F, 4F,   0F,    0F,    0F,    0F,    9F,  0F,  ID.EquipType.AIR_F_LO,     250F,  0F});
		put(ID.E_AIRCRAFT_FMK2,      new float[]{3F, 0F,  0F,  0F,  12F, 0F,  0F, 0F,   -0.06F, 4F,   0F,    0F,    0F,    0F,    18F, 0F,  ID.EquipType.AIR_F_LO,     600F,  0F});
		put(ID.E_AIRCRAFT_FMK3,      new float[]{3F, 0F,  0F,  0F,  18F, 0F,  0F, 0.05F,-0.08F, 4.5F, 0F,    0F,    0F,    0F,    27F, 0F,  ID.EquipType.AIR_F_LO,     1000F, 0F});
		put(ID.E_AIRCRAFT_FFLYFISH,  new float[]{3F, 0F,  0F,  0F,  24F, 0F,  0F, 0.05F,-0.1F,  4.5F, 0F,    0F,    0F,    0.01F, 36F, 0F,  ID.EquipType.AIR_F_HI,     250F,  0F});
		put(ID.E_AIRCRAFT_FHELLCAT,  new float[]{3F, 0F,  0F,  0F,  28F, 0F,  0F, 0.1F, -0.12F, 5F,   0F,    0F,    0F,    0.02F, 40F, 0F,  ID.EquipType.AIR_F_HI,     600F,  0F});
		put(ID.E_AIRCRAFT_FHELLCATK, new float[]{3F, 0F,  0F,  0F,  36F, 0F,  0F, 0.15F,-0.15F, 5F,   0F,    0F,    0F,    0.04F, 48F, 0F,  ID.EquipType.AIR_F_HI,     1000F, 0F});
		//Bomber aircraft                        Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put(ID.E_AIRCRAFT_BMK1,      new float[]{3F, 0F,  0F,  0F,  3F,  9F,  0F, 0F,   -0.08F, 2F,   0.04F, 0F,    0F,    0F,    1F,  2F,  ID.EquipType.AIR_B_LO,     250F,  0F});
		put(ID.E_AIRCRAFT_BMK2,      new float[]{3F, 0F,  0F,  0F,  6F,  18F, 0F, 0F,   -0.1F,  2F,   0.06F, 0F,    0F,    0F,    2F,  4F,  ID.EquipType.AIR_B_LO,     1000F, 0F});
		put(ID.E_AIRCRAFT_BFLYFISH,  new float[]{3F, 0F,  0F,  0F,  10F, 30F, 0F, 0F,   -0.15F, 2.5F, 0.08F, 0F,    0F,    0F,    4F,  8F,  ID.EquipType.AIR_B_HI,     250F,  0F});
		put(ID.E_AIRCRAFT_BHELL,     new float[]{3F, 0F,  0F,  0F,  8F,  24F, 0F, 0F,   -0.2F,  2.5F, 0.10F, 0F,    0F,    0F,    8F,  12F, ID.EquipType.AIR_B_HI,     600F,  0F});
		put(ID.E_AIRCRAFT_BHELLK,    new float[]{3F, 0F,  0F,  0F,  10F, 30F, 0F, 0.1F, -0.24F, 3F,   0.12F, 0F,    0F,    0F,    12F, 16F, ID.EquipType.AIR_B_HI,     1000F, 0F});
		//Recon aircraft
		put(ID.E_AIRCRAFT_R,         new float[]{2F, 0F,  2F,  2F,  2F,  2F,  0F, 0F,   -0.08F, 3.5F, 0.03F, 0F,    0F,    0.04F, 8F,  4F,  ID.EquipType.AIR_R_LO,     500F,  0F});
		put(ID.E_AIRCRAFT_RFLYFISH,  new float[]{2F, 0F,  4F,  4F,  4F,  4F,  0F, 0F,   -0.15F, 5.5F, 0.05F, 0F,    0F,    0.08F, 12F, 8F,  ID.EquipType.AIR_R_HI,     500F,  0F});
		//radar
		put(ID.E_RADAR_AIRMK1,       new float[]{2F, 0F,  2F,  0F,  2F,  0F,  3F, 0F,   -0.04F, 1F,   0F,    0F,    0F,    0.04F, 8F,  0F,  ID.EquipType.RADAR_LO,     250F,  2F});
		put(ID.E_RADAR_AIRMK2,       new float[]{2F, 0F,  4F,  0F,  4F,  0F,  6F, 0F,   -0.06F, 2F,   0.03F, 0F,    0F,    0.08F, 16F, 0F,  ID.EquipType.RADAR_LO,     450F,  2F});
		put(ID.E_RADAR_SURMK1,       new float[]{2F, 0F,  0F,  6F,  0F,  8F,  3F, 0F,   -0.04F, 1F,   0F,    0F,    0F,    0.04F, 0F,  0F,  ID.EquipType.RADAR_LO,     650F,  2F});
		put(ID.E_RADAR_SURMK2,       new float[]{2F, 0F,  0F,  12F, 0F,  16F, 6F, 0F,   -0.06F, 2F,   0.03F, 0F,    0F,    0.08F, 0F,  0F,  ID.EquipType.RADAR_LO,     850F,  2F});
		put(ID.E_RADAR_SONAR,        new float[]{2F, 0F,  2F,  4F,  2F,  4F,  3F, 0F,   -0.02F, 1F,   0F,    0F,    0F,    0.03F, 0F,  16F, ID.EquipType.RADAR_LO,     1000F, 3F});
		put(ID.E_RADAR_AIRABYSS,     new float[]{2F, 0F,  6F,  0F,  6F,  0F,  10F,0F,   -0.08F, 2F,   0.04F, 0F,    0F,    0.12F, 32F, 0F,  ID.EquipType.RADAR_HI,     250F,  4F});
		put(ID.E_RADAR_SURABYSS,     new float[]{2F, 0F,  0F,  14F, 0F,  20F, 10F,0F,   -0.08F, 2F,   0.04F, 0F,    0F,    0.12F, 0F,  0F,  ID.EquipType.RADAR_HI,     500F,  4F});	
		put(ID.E_RADAR_SONARMK2,     new float[]{2F, 0F,  4F,  8F,  4F,  10F, 6F, 0F,   -0.04F, 1F,   0.02F, 0F,    0F,    0.06F, 0F,  32F, ID.EquipType.RADAR_HI,     800F,  4F});
		put(ID.E_RADAR_FCSCIC,       new float[]{2F, 0F,  6F,  18F, 6F,  18F, 10F,0F,   -0.12F, 2F,   0.04F, 0F,    0F,    0.1F,  32F, 32F, ID.EquipType.RADAR_HI,     1000F, 6F});
		//turbine                                Typ HP   LA   HA   LAA  HAA  DEF SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Rare Type/Mean                    Dodge
		put(ID.E_TURBINE,            new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   0.15F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.TURBINE_LO,   250F,  6F});
		put(ID.E_TURBINE_IMP,        new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   0.2F,   0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.TURBINE_LO,   1000F, 8F});
		put(ID.E_TURBINE_ENH,        new float[]{2F, 0F,  0F,  0F,  0F,  0F,  0F, 0F,   0.25F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.TURBINE_HI,   500F,  10F});
		//armor
		put(ID.E_ARMOR,              new float[]{2F, 60F, 0F,  0F,  0F,  0F,  8F, 0F,   -0.1F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.ARMOR_LO,     500F,  0F});
		put(ID.E_ARMOR_ENH,          new float[]{2F, 300F,0F,  0F,  0F,  0F,  16F,0F,   -0.2F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.ARMOR_HI,     500F,  0F});
		//catapult
		put(ID.E_CATAPULT_F,         new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 0.5F, -0.1F,  2F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_LO,  250F,  0F});
		put(ID.E_CATAPULT_H,         new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 0.8F, -0.18F, 4F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_LO,  500F,  0F});
		put(ID.E_CATAPULT_C,         new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 1.2F, -0.26F, 6F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_HI,  800F,  0F});
		put(ID.E_CATAPULT_E,         new float[]{3F, 0,   0F,  0F,  0F,  0F,  0F, 1.8F, -0.34F, 8F,   0F,    0F,    0F,    0F,    0F,  0F,  ID.EquipType.CATAPULT_HI,  1000F, 0F});
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
		put(ID.Item.IronIG,     new ItemStack(Items.iron_ingot));
		put(ID.Item.Grudge,     new ItemStack(ModItems.Grudge));
		put(ID.Item.GrudgeB,    new ItemStack(ModBlocks.BlockGrudge));
		put(ID.Item.GrudgeBH,   new ItemStack(ModBlocks.BlockGrudgeHeavy));
		put(ID.Item.AbyssIG,    new ItemStack(ModItems.AbyssMetal, 1, 0));
		put(ID.Item.AbyssB,     new ItemStack(ModBlocks.BlockAbyssium));
		put(ID.Item.PolymIG,    new ItemStack(ModItems.AbyssMetal, 1, 1));
		put(ID.Item.PolymOre,   new ItemStack(ModBlocks.BlockPolymetalOre));
		put(ID.Item.PolymB,     new ItemStack(ModBlocks.BlockPolymetal));
		put(ID.Item.PolymBG,    new ItemStack(ModBlocks.BlockPolymetalGravel));
		put(ID.Item.Gunpowder,  new ItemStack(Items.gunpowder));
		put(ID.Item.Blazepowder,new ItemStack(Items.blaze_powder));
		put(ID.Item.AmmoL,      new ItemStack(ModItems.Ammo, 1, 0));
		put(ID.Item.AmmoLC,     new ItemStack(ModItems.Ammo, 1, 1));
		put(ID.Item.AmmoH,      new ItemStack(ModItems.Ammo, 1, 2));
		put(ID.Item.AmmoHC,     new ItemStack(ModItems.Ammo, 1, 3));
		put(ID.Item.RpBucket,   new ItemStack(ModItems.BucketRepair));
		put(ID.Item.LaBucket,   new ItemStack(Items.lava_bucket));
		put(ID.Item.NStar,      new ItemStack(Items.nether_star));
		put(ID.Item.Ring,       new ItemStack(ModItems.MarriageRing));
		put(ID.Item.Paper,      new ItemStack(Items.paper));
		put(ID.Item.OwnPaper,   new ItemStack(ModItems.OwnerPaper));
		put(ID.Item.Stick,      new ItemStack(Items.stick));
		put(ID.Item.KHammer,    new ItemStack(ModItems.KaitaiHammer));
		put(ID.Item.ModTool,    new ItemStack(ModItems.ModernKit));
		put(ID.Item.SpawnEgg0,  new ItemStack(ModItems.ShipSpawnEgg, 1, 0));
		put(ID.Item.SpawnEgg1,  new ItemStack(ModItems.ShipSpawnEgg, 1, 1));
		put(ID.Item.SpawnEgg2,  new ItemStack(ModItems.ShipSpawnEgg, 1, 2));
		put(ID.Item.InstantMat, new ItemStack(ModItems.InstantConMat));
		put(ID.Item.DiamondB,   new ItemStack(Blocks.diamond_block));
		put(ID.Item.RpGod,      new ItemStack(ModItems.RepairGoddess));
		put(ID.Item.Pointer,    new ItemStack(ModItems.PointerItem));
		put(ID.Item.ModelZF,    new ItemStack(ModItems.ToyAirplane));
		put(ID.Item.Desk,       new ItemStack(ModBlocks.BlockDesk));
		put(ID.Item.DeskBook,    new ItemStack(ModItems.DeskItemBook));
		put(ID.Item.DeskRadar,    new ItemStack(ModItems.DeskItemRadar));
		put(ID.Item.WriteBook,    new ItemStack(Items.writable_book));
		put(ID.Item.Compass,    new ItemStack(Items.compass));
		put(ID.Item.ObsidianB,  new ItemStack(Blocks.obsidian));
		put(ID.Item.WoolB,      new ItemStack(Blocks.wool));
		put(ID.Item.SmallSY,    new ItemStack(ModBlocks.BlockSmallShipyard));
		put(ID.Item.EqCannon0,  new ItemStack(ModItems.EquipCannon, 1, 0));
		put(ID.Item.EqCannon1,  new ItemStack(ModItems.EquipCannon, 1, 2));
		put(ID.Item.EqCannon2,  new ItemStack(ModItems.EquipCannon, 1, 9));
		put(ID.Item.EqMGun,     new ItemStack(ModItems.EquipMachinegun));
		put(ID.Item.EqCatap,    new ItemStack(ModItems.EquipCatapult));
		put(ID.Item.EqRadar,    new ItemStack(ModItems.EquipRadar));
		put(ID.Item.EqTurbine,  new ItemStack(ModItems.EquipTurbine));
		put(ID.Item.EqTorpedo,  new ItemStack(ModItems.EquipTorpedo));
		put(ID.Item.EqAirT,     new ItemStack(ModItems.EquipAirplane, 1, 0));
		put(ID.Item.EqAirF,     new ItemStack(ModItems.EquipAirplane, 1, 4));
		put(ID.Item.EqAirB,     new ItemStack(ModItems.EquipAirplane, 1, 9));
		put(ID.Item.EqAirR,     new ItemStack(ModItems.EquipAirplane, 1, 13));
		put(ID.Item.EqArmor,    new ItemStack(ModItems.EquipArmor));
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
								new int[] {2, 0, 13, -3, ID.Item.Grudge},
								new int[] {2, 0, 43, -3, ID.Item.GrudgeB},
								new int[] {2, 0, 73, -3, ID.Item.GrudgeBH}));
		//page 1: abyssium
		put(1001, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3, 17, ID.Item.Grudge},
								new int[] {2, 0, 23, 17, ID.Item.IronIG},
								new int[] {2, 0, 81, 17, ID.Item.AbyssIG}));
		//page 2: polymetal
		put(1002, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 0, 56, 100, 56},
								new int[] {1, 1, 0, 73, 0, 0, 112, 100, 59},
								new int[] {2, 0, 5, 52, ID.Item.PolymOre},
								new int[] {2, 0, 30, 52, ID.Item.PolymIG},
								new int[] {2, 0, 55, 52, ID.Item.PolymB},
								new int[] {2, 0, 80, 52, ID.Item.PolymBG}));
		//page 3: ammo
		put(1003, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3,  ID.Item.IronIG},
								new int[] {2, 0, 23, -3,  ID.Item.IronIG},
								new int[] {2, 0, 43, -3,  ID.Item.IronIG},
								new int[] {2, 0, 3,  17, ID.Item.IronIG},
								new int[] {2, 0, 23, 17, ID.Item.Grudge},
								new int[] {2, 0, 43, 17, ID.Item.IronIG},
								new int[] {2, 0, 3,  37, ID.Item.IronIG},
								new int[] {2, 0, 23, 37, ID.Item.Gunpowder},
								new int[] {2, 0, 43, 37, ID.Item.IronIG},
								new int[] {2, 0, 81, 17, ID.Item.AmmoL},
								new int[] {2, 1, 3, 110, ID.Item.AmmoL},
								new int[] {2, 1, 28, 110, ID.Item.AmmoLC},
								new int[] {2, 1, 53, 110, ID.Item.AmmoH},
								new int[] {2, 1, 78, 110, ID.Item.AmmoHC}));
		//page 4: repair bucket
		put(1004, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  17, ID.Item.LaBucket},
								new int[] {2, 0, 23, 17, ID.Item.Grudge},
								new int[] {2, 0, 81, 17, ID.Item.RpBucket}));
		//page 5: ring
		put(1005, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3,  ID.Item.AbyssIG},
								new int[] {2, 0, 23, -3,  ID.Item.NStar},
								new int[] {2, 0, 43, -3,  ID.Item.AbyssIG},
								new int[] {2, 0, 3,  17, ID.Item.AbyssIG},
								new int[] {2, 0, 43, 17, ID.Item.AbyssIG},
								new int[] {2, 0, 3,  37, ID.Item.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Item.AbyssIG},
								new int[] {2, 0, 43, 37, ID.Item.AbyssIG},
								new int[] {2, 0, 81, 17, ID.Item.Ring}));
		//page 6: ownership paper
		put(1006, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  17, ID.Item.Paper},
								new int[] {2, 0, 23, 17, ID.Item.Grudge},
								new int[] {2, 0, 81, 17, ID.Item.OwnPaper}));
		//page 7: hammer
		put(1007, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Item.AbyssIG},
								new int[] {2, 0, 23, -3, ID.Item.AbyssIG},
								new int[] {2, 0, 43, -3, ID.Item.AbyssIG},
								new int[] {2, 0, 3,  17, ID.Item.AbyssIG},
								new int[] {2, 0, 23, 17, ID.Item.AbyssIG},
								new int[] {2, 0, 43, 17, ID.Item.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Item.Stick},
								new int[] {2, 0, 81, 17, ID.Item.KHammer}));
		//page 8: modernization toolkit
		put(1008, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Item.KHammer},
								new int[] {2, 0, 23, -3, ID.Item.SpawnEgg2},
								new int[] {2, 0, 43, -3, ID.Item.SpawnEgg0},
								new int[] {2, 0, 3,  17, ID.Item.SpawnEgg2},
								new int[] {2, 0, 23, 17, ID.Item.SpawnEgg2},
								new int[] {2, 0, 43, 17, ID.Item.SpawnEgg1},
								new int[] {2, 0, 3,  37, ID.Item.SpawnEgg2},
								new int[] {2, 0, 23, 37, ID.Item.SpawnEgg1},
								new int[] {2, 0, 43, 37, ID.Item.SpawnEgg2},
								new int[] {2, 0, 81, 17, ID.Item.ModTool}));
		//page 9: instant mats
		put(1009, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  17, ID.Item.KHammer},
								new int[] {2, 0, 23, 17, ID.Item.SpawnEgg0},
								new int[] {2, 0, 81, 17, ID.Item.InstantMat}));
		//page 10: abyssal goddess
		put(1010, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Item.GrudgeB},
								new int[] {2, 0, 23, -3, ID.Item.GrudgeBH},
								new int[] {2, 0, 43, -3, ID.Item.GrudgeB},
								new int[] {2, 0, 3,  17, ID.Item.GrudgeBH},
								new int[] {2, 0, 23, 17, ID.Item.DiamondB},
								new int[] {2, 0, 43, 17, ID.Item.GrudgeBH},
								new int[] {2, 0, 3,  37, ID.Item.GrudgeB},
								new int[] {2, 0, 23, 37, ID.Item.GrudgeBH},
								new int[] {2, 0, 43, 37, ID.Item.GrudgeB},
								new int[] {2, 0, 81, 17, ID.Item.RpGod}));
		//page 11: pointer
		put(1011, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  37, ID.Item.PolymIG},
								new int[] {2, 0, 23, 17, ID.Item.PolymIG},
								new int[] {2, 0, 43, -3, ID.Item.GrudgeB},
								new int[] {2, 0, 81, 17, ID.Item.Pointer}));
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
								new int[] {2, 0, 23, -3, ID.Item.PolymIG},
								new int[] {2, 0, 3,  17, ID.Item.PolymIG},
								new int[] {2, 0, 23, 17, ID.Item.PolymIG},
								new int[] {2, 0, 43, 17, ID.Item.PolymIG},
								new int[] {2, 0, 23, 37, ID.Item.PolymIG},
								new int[] {2, 0, 81, 17, ID.Item.ModelZF}));
		//page 15: desk item book
		put(1015, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Item.Grudge},
								new int[] {2, 0, 23, -3, ID.Item.Grudge},
								new int[] {2, 0, 43, -3, ID.Item.Grudge},
								new int[] {2, 0, 3,  17, ID.Item.Grudge},
								new int[] {2, 0, 23, 17, ID.Item.WriteBook},
								new int[] {2, 0, 43, 17, ID.Item.Grudge},
								new int[] {2, 0, 3,  37, ID.Item.Grudge},
								new int[] {2, 0, 23, 37, ID.Item.Grudge},
								new int[] {2, 0, 43, 37, ID.Item.Grudge},
								new int[] {2, 0, 81, 17, ID.Item.DeskBook}));
		//page 16: desk item radar
		put(1016, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Item.Grudge},
								new int[] {2, 0, 23, -3, ID.Item.Grudge},
								new int[] {2, 0, 43, -3, ID.Item.Grudge},
								new int[] {2, 0, 3,  17, ID.Item.Grudge},
								new int[] {2, 0, 23, 17, ID.Item.Compass},
								new int[] {2, 0, 43, 17, ID.Item.Grudge},
								new int[] {2, 0, 3,  37, ID.Item.Grudge},
								new int[] {2, 0, 23, 37, ID.Item.Grudge},
								new int[] {2, 0, 43, 37, ID.Item.Grudge},
								new int[] {2, 0, 81, 17, ID.Item.DeskRadar}));
		//page 17: desk
		put(1017, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Item.DeskRadar},
								new int[] {2, 0, 23, -3, ID.Item.DeskBook},
								new int[] {2, 0, 43, -3, ID.Item.WoolB},
								new int[] {2, 0, 3,  17, ID.Item.ObsidianB},
								new int[] {2, 0, 23, 17, ID.Item.ObsidianB},
								new int[] {2, 0, 43, 17, ID.Item.ObsidianB},
								new int[] {2, 0, 3,  37, ID.Item.ObsidianB},
								new int[] {2, 0, 43, 37, ID.Item.ObsidianB},
								new int[] {2, 0, 81, 17, ID.Item.Desk}));
		//chap 2: construction
		//page 0: vent
		put(2000, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {1, 1, 0, -6, 0, 100, 134, 100, 46},
								new int[] {2, 0, 3,  -3, ID.Item.Grudge},
								new int[] {2, 0, 23, -3, ID.Item.LaBucket},
								new int[] {2, 0, 43, -3, ID.Item.Grudge},
								new int[] {2, 0, 3,  17, ID.Item.LaBucket},
								new int[] {2, 0, 23, 17, ID.Item.ObsidianB},
								new int[] {2, 0, 43, 17, ID.Item.LaBucket},
								new int[] {2, 0, 3,  37, ID.Item.ObsidianB},
								new int[] {2, 0, 23, 37, ID.Item.ObsidianB},
								new int[] {2, 0, 43, 37, ID.Item.ObsidianB},
								new int[] {2, 0, 81, 17, ID.Item.SmallSY}));
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
								new int[] {1, 0, 0, 60, 0, 100, 180, 100, 68},
								new int[] {1, 1, -7, -18, 0, 200, 0, 38, 38},
								new int[] {1, 1, 31, -18, 0, 200, 38, 38, 38},
								new int[] {1, 1, 69, -18, 0, 200, 76, 38, 38}));
		//page 4: vortex
		put(2004, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//chap 3: maintenance
		//page 0: obtain ship
		put(3000, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 1:
		put(3001, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 2:
		put(3002, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 3:
		put(3003, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 4:
		put(3004, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 5:
		put(3005, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 6:
		put(3006, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 7:
		put(3007, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 8:
		put(3008, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 9:
		put(3009, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 10:
		put(3010, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//page 11:
		put(3011, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		//chap 4: ship list
		//page 0:
		put(4000, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0}));
		
	}});
	
	
	
	
}
