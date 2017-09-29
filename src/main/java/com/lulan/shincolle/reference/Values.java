package com.lulan.shincolle.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/** HARD CODED VALUES
 *
 */
public class Values
{
	
	/** numbers */
	public static final class N
	{
		public static final float DIV_180_PI = 57.295779513F;  // 180 / PI
		public static final float DIV_PI_180 = 0.0174532925F;  // PI / 180
		
		public static final int BaseGrudge = 300;
		public static final int BaseLightAmmo = 30;
		public static final int BaseHeavyAmmo = 15;
	}
	
	/**SHIP ATTRIBUTES MAP
	 * index by {@link ID.AttrsBase}
	 */
	public static final Map<Integer, float[]> ShipAttrMap = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
	{{
		//destroyer                                            HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT     
		put((int)ID.ShipClass.DestroyerI,         new float[] {20F,  3F,  0.05F,  1.0F, 0.5F,  6F,  0.3F,  0.25F, 0.11F, 0.5F,  1F,    0.4F});
		put((int)ID.ShipClass.DestroyerRO,        new float[] {22F,  4F,  0.06F,  1.0F, 0.5F,  6F,  0.32F, 0.28F, 0.12F, 0.5F,  1F,    0.4F});
		put((int)ID.ShipClass.DestroyerHA,        new float[] {24F,  3F,  0.07F,  1.0F, 0.5F,  6F,  0.34F, 0.25F, 0.13F, 0.5F,  1F,    0.4F});
		put((int)ID.ShipClass.DestroyerNI,        new float[] {28F,  4F,  0.09F,  1.0F, 0.5F,  6F,  0.36F, 0.28F, 0.15F, 0.5F,  1F,    0.4F});
		//cruiser
//		put((int)ID.ShipClass.LightCruiserHO,     new float[] {33F,  6F,  0.11F,  1.0F, 0.45F, 8F,  0.38F, 0.3F,  0.17F, 0.53F, 0.9F,  0.5F});
//		put((int)ID.ShipClass.LightCruiserHE,     new float[] {36F,  9F,  0.13F,  1.0F, 0.45F, 8F,  0.4F,  0.35F, 0.18F, 0.53F, 0.9F,  0.5F});
//		put((int)ID.ShipClass.LightCruiserTO,     new float[] {39F,  8F,  0.15F,  1.0F, 0.45F, 8F,  0.42F, 0.32F, 0.19F, 0.53F, 0.9F,  0.5F});
//		put((int)ID.ShipClass.LightCruiserTSU,    new float[] {48F,  12F, 0.16F,  1.0F, 0.45F, 8F,  0.44F, 0.38F, 0.20F, 0.53F, 0.9F,  0.5F});
//		put((int)ID.ShipClass.TorpedoCruiserCHI,  new float[] {48F,  16F, 0.18F,  1.0F, 0.42F, 9F,  0.46F, 0.44F, 0.21F, 0.56F, 0.84F, 0.5F});
		put((int)ID.ShipClass.HeavyCruiserRI,     new float[] {58F,  14F, 0.18F,  1.0F, 0.42F, 9F,  0.48F, 0.4F,  0.21F, 0.56F, 0.84F, 0.5F});
		put((int)ID.ShipClass.HeavyCruiserNE,     new float[] {62F,  15F, 0.19F,  1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
		//carrier                                              HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put((int)ID.ShipClass.LightCarrierNU,     new float[] {65F,  20F, 0.20F,  0.7F, 0.32F, 14F, 0.52F, 0.45F, 0.22F, 0.5F,  0.64F, 0.6F});
		put((int)ID.ShipClass.CarrierWO,          new float[] {85F,  25F, 0.21F,  1.0F, 0.36F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		//battleship
		put((int)ID.ShipClass.BattleshipRU,       new float[] {95F,  30F, 0.30F,  1.0F, 0.32F, 12F, 0.85F, 0.65F, 0.27F, 0.63F, 0.66F, 0.5F});
		put((int)ID.ShipClass.BattleshipTA,       new float[] {84F,  19F, 0.23F,  1.2F, 0.42F, 10F, 0.65F, 0.55F, 0.24F, 0.7F,  0.84F, 0.5F});
		put((int)ID.ShipClass.BattleshipRE,       new float[] {120F, 27F, 0.25F,  1.1F, 0.36F, 12F, 0.8F,  0.65F, 0.25F, 0.63F, 0.72F, 0.5F});
		//transport
		put((int)ID.ShipClass.TransportWA,        new float[] {90F,  3F,  0.10F,  1.0F, 0.3F,  8F,  0.7F,  0.25F, 0.16F, 0.35F, 0.6F,  0.3F});
		//submarine
		put((int)ID.ShipClass.SubmarineKA,        new float[] {40F,  28F, 0.09F,  0.8F, 0.3F,  5F,  0.35F, 0.67F, 0.14F, 0.7F,  0.6F,  0.3F});
		put((int)ID.ShipClass.SubmarineYO,        new float[] {36F,  30F, 0.10F,  0.8F, 0.3F,  5F,  0.33F, 0.7F,  0.16F, 0.7F,  0.6F,  0.3F});
		put((int)ID.ShipClass.SubmarineSO,        new float[] {34F,  38F, 0.12F,  0.8F, 0.28F, 5.5F,0.3F,  0.8F,  0.18F, 0.7F,  0.6F,  0.3F});
		//demon                                                HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
		put((int)ID.ShipClass.IsolatedHime,       new float[] {225F, 13F, 0.34F,  0.9F, 0.22F, 24F, 1.3F,  0.4F,  0.29F, 0.6F,  0.44F, 0.8F});
//		put((int)ID.ShipClass.LightCruiserDemon,  new float[] {130F, 30F, 0.25F,  1.0F, 0.45F, 13F, 0.8F,  0.65F, 0.25F, 0.6F,  0.9F,  0.55F});
		//hime
//		put((int)ID.ShipClass.AirdefenseHime,     new float[] {120F, 30F, 0.35F,  1.0F, 0.5F,  12F, 0.8F,  0.7F,  0.3F,  0.6F,  1F,    0.5F});
		put((int)ID.ShipClass.AirfieldHime,       new float[] {240F, 16F, 0.32F,  1.0F, 0.3F,  26F, 1.2F,  0.45F, 0.28F, 0.6F,  0.6F,  0.8F});
//		put((int)ID.ShipClass.AnchorageHime,      new float[] {150F, 19F, 0.32F,  0.9F, 0.3F,  23F, 0.95F, 0.5F,  0.28F, 0.6F,  0.6F,  0.8F});
//		put((int)ID.ShipClass.ArmoredCarrierHime, new float[] {200F, 34F, 0.35F,  1.0F, 0.42F, 20F, 0.9F,  0.73F, 0.3F,  0.63F, 0.84F, 0.7F});
		put((int)ID.ShipClass.BattleshipHime,     new float[] {220F, 42F, 0.40F,  1.0F, 0.4F,  16F, 1.0F,  0.8F,  0.32F, 0.73F, 0.8F,  0.6F});
		put((int)ID.ShipClass.CarrierHime,        new float[] {180F, 40F, 0.28F,  1.0F, 0.45F, 22F, 0.85F, 0.75F, 0.26F, 0.62F, 0.85F, 0.7F});
		put((int)ID.ShipClass.DestroyerHime,      new float[] {90F,  22F, 0.20F,  1.0F, 0.5F,  12F, 0.55F, 0.5F,  0.22F, 0.6F,  1F,    0.5F});
		put((int)ID.ShipClass.HarbourHime,        new float[] {260F, 14F, 0.36F,  0.8F, 0.2F,  24F, 1.35F, 0.4F,  0.3F,  0.6F,  0.4F,  0.8F});
//		put((int)ID.ShipClass.MidwayHime,         new float[] {350F, 22F, 0.45F,  0.8F, 0.25F, 30F, 1.5F,  0.5F,  0.34F, 0.6F,  0.4F,  0.8F});
		put((int)ID.ShipClass.NorthernHime,       new float[] {210F, 13F, 0.30F,  0.8F, 0.32F, 22F, 1.15F, 0.35F, 0.27F, 0.6F,  0.64F, 0.8F});
//		put((int)ID.ShipClass.SeaplaneHime,       new float[] {170F, 24F, 0.25F,  1.0F, 0.45F, 18F, 1F,    0.6F,  0.25F, 0.63F, 0.9F,  0.65F});
//		put((int)ID.ShipClass.SouthernHime,       new float[] {170F, 35F, 0.34F,  1.0F, 0.3F,  20F, 1F,    0.73F, 0.29F, 0.63F, 0.6F,  0.7F});
//		put((int)ID.ShipClass.SubmarineHime,      new float[] {75F,  45F, 0.18F,  0.9F, 0.3F,  10F, 0.65F, 0.85F, 0.2F,  0.6F,  0.6F,  0.4F});
//		put((int)ID.ShipClass.LightCruiserHime,   new float[] {160F, 32F, 0.26F,  1.0F, 0.45F, 14F, 0.82F, 0.7F,  0.25F, 0.6F,  0.9F,  0.55F});
		put((int)ID.ShipClass.HeavyCruiserHime,   new float[] {180F, 35F, 0.32F,  1.0F, 0.45F, 14F, 0.85F, 0.77F, 0.29F, 0.65F, 0.9F,  0.6F});
//		put((int)ID.ShipClass.SupplyDepotHime,    new float[] {320F, 16F, 0.38F,  0.8F, 0.18F, 26F, 1.4F,  0.42F, 0.32F, 0.6F,  0.4F,  0.8F});
		//	
		//water demon                                          HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
//		put((int)ID.ShipClass.AnchorageWD,        new float[] {230F, 28F, 0.35F,  1.0F, 0.35F, 26F, 1.35F, 0.6F,  0.3F,  0.7F,  0.7F,  1F});
//		put((int)ID.ShipClass.BattleshipWD,       new float[] {280F, 50F, 0.45F,  1.0F, 0.42F, 21F, 1.1F,  1F,    0.34F, 0.85F, 0.84F, 0.7F});
		put((int)ID.ShipClass.CarrierWD,          new float[] {190F, 45F, 0.40F,  1.0F, 0.42F, 25F, 1F,    0.95F, 0.32F, 0.75F, 0.84F, 0.8F});
//		put((int)ID.ShipClass.HarbourWD,          new float[] {300F, 35F, 0.45F,  1.0F, 0.35F, 29F, 1.5F,  0.63F, 0.34F, 0.7F,  0.7F,  1F});
//		put((int)ID.ShipClass.DestroyerWD,        new float[] {150F, 23F, 0.25F,  1.0F, 0.55F, 15F, 0.9F,  0.8F,  0.3F,  0.7F,  1F,    0.6F});
		//hostile ship                                         
		//destroyer                                            HP    ATK  DEF     SPD   MOV    HIT  HP     ATK    DEF    SPD    MOV    HIT
		put((int)ID.ShipClass.DestroyerAkatsuki,  new float[] {32F,  9F,  0.09F,  1.0F, 0.5F,  11F, 0.32f, 0.38F, 0.12F, 0.5F,  1F,    0.5F});
		put((int)ID.ShipClass.DestroyerHibiki,    new float[] {40F,  7F,  0.11F,  1.0F, 0.5F,  10F, 0.38f, 0.36F, 0.14F, 0.5F,  1F,    0.48F});
		put((int)ID.ShipClass.DestroyerIkazuchi,  new float[] {30F,  5F,  0.09F,  1.0F, 0.5F,  9F,  0.3f,  0.32F, 0.12F, 0.5F,  1F,    0.46F});
		put((int)ID.ShipClass.DestroyerInazuma,   new float[] {30F,  5F,  0.09F,  1.0F, 0.5F,  9F,  0.3f,  0.32F, 0.12F, 0.5F,  1F,    0.46F});
		put((int)ID.ShipClass.DestroyerShimakaze, new float[] {38F,  11F, 0.12F,  1.0F, 0.6F,  9F,  0.35F, 0.4F,  0.16F, 0.55F, 1.2F,  0.46F});
		//cruiser
		put((int)ID.ShipClass.LightCruiserTenryuu,new float[] {42F,  13F, 0.16F,  1.0F, 0.42F, 8F,  0.4F,  0.4F,  0.2F,  0.6F,  0.9F,  0.4F});
		put((int)ID.ShipClass.LightCruiserTatsuta,new float[] {42F,  13F, 0.16F,  1.0F, 0.42F, 8F,  0.4F,  0.4F,  0.2F,  0.6F,  0.9F,  0.4F});
		put((int)ID.ShipClass.HeavyCruiserAtago,  new float[] {62F,  15F, 0.18F,  1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
		put((int)ID.ShipClass.HeavyCruiserTakao,  new float[] {62F,  15F, 0.18F,  1.0F, 0.42F, 9F,  0.5F,  0.42F, 0.22F, 0.56F, 0.84F, 0.5F});
		//battleship
		put((int)ID.ShipClass.BattleshipNagato,   new float[] {135F, 40F, 0.26F,  1.0F, 0.32F, 14F, 0.85F, 0.8F,  0.25F, 0.63F, 0.64F, 0.6F});
		put((int)ID.ShipClass.BattleshipYamato,   new float[] {150F, 55F, 0.36F,  1.0F, 0.3F,  20F, 1F,    1F,    0.3F,  0.7F,  0.6F,  0.7F});
		put((int)ID.ShipClass.BattleshipKongou,   new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
		put((int)ID.ShipClass.BattleshipHiei,     new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
		put((int)ID.ShipClass.BattleshipHaruna,   new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
		put((int)ID.ShipClass.BattleshipKirishima,new float[] {90F,  28F, 0.36F,  1.0F, 0.42F, 12F, 0.7F,  0.6F,  0.24F, 0.6F,  0.84F, 0.55F});
		//submarine
		put((int)ID.ShipClass.SubmarineU511,      new float[] {28F,  30F, 0.07F,  0.8F, 0.3F,  7F,  0.3F,  0.7F,  0.13F, 0.7F,  0.6F,  0.4F});
		put((int)ID.ShipClass.SubmarineRo500,     new float[] {32F,  32F, 0.10F,  0.8F, 0.3F,  8F,  0.33F, 0.75F, 0.16F, 0.7F,  0.6F,  0.4F});
		//carrier
		put((int)ID.ShipClass.CarrierKaga,        new float[] {70F,  22F, 0.21F,  1.0F, 0.34F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		put((int)ID.ShipClass.CarrierAkagi,       new float[] {75F,  22F, 0.20F,  1.0F, 0.32F, 16F, 0.65f, 0.6F,  0.23F, 0.6F,  0.72F, 0.6F});
		
	}});
	
	/**HOSTILE SHIP ATTRIBUTES MAP
	 * index by ID.AttrsBase
	 */
	public static final Map<Integer, float[]> HostileShipAttrMap = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
	{{
		//destroyer                                            HP     ATK    DEF    SPD    MOV    HIT
		put((int)ID.ShipClass.DestroyerAkatsuki,  new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
		put((int)ID.ShipClass.DestroyerHibiki,    new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
		put((int)ID.ShipClass.DestroyerIkazuchi,  new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
		put((int)ID.ShipClass.DestroyerInazuma,   new float[] {0.35F, 0.35F, 0.35F, 1F,    1.1F,  0.7F});
		put((int)ID.ShipClass.DestroyerShimakaze, new float[] {0.4F,  0.5F,  0.4F,  1.2F,  1.2F,  0.75F});
		//cruiser
		put((int)ID.ShipClass.LightCruiserTenryuu,new float[] {0.55F, 0.6F,  0.5F,  1F,    0.9F,  0.8F});
		put((int)ID.ShipClass.LightCruiserTatsuta,new float[] {0.55F, 0.6F,  0.5F,  1F,    0.9F,  0.8F});
		put((int)ID.ShipClass.HeavyCruiserAtago,  new float[] {0.8F,  0.8F,  0.8F,  0.8F,  0.8F,  0.9F});
		put((int)ID.ShipClass.HeavyCruiserTakao,  new float[] {0.8F,  0.8F,  0.8F,  0.8F,  0.8F,  0.9F});
		//battleship
		put((int)ID.ShipClass.BattleshipNagato,   new float[] {1.1F,  1.1F,  1.1F,  1F,    0.8F,  1.05F});
		put((int)ID.ShipClass.BattleshipYamato,   new float[] {1.2F,  1.2F,  1.2F,  1.2F,  0.8F,  1.1F});
		put((int)ID.ShipClass.BattleshipKongou,   new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
		put((int)ID.ShipClass.BattleshipHiei,     new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
		put((int)ID.ShipClass.BattleshipHaruna,   new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
		put((int)ID.ShipClass.BattleshipKirishima,new float[] {1F,    1.05F, 1F,    1F,    1F,    1F});
		//submarine                                            HP     ATK    DEF    SPD    MOV    HIT
		put((int)ID.ShipClass.SubmarineU511,      new float[] {0.25F, 0.8F,  0.25F, 0.75F, 0.4F,  0.4F});
		put((int)ID.ShipClass.SubmarineRo500,     new float[] {0.25F, 0.8F,  0.25F, 0.75F, 0.4F,  0.4F});
		//carrier
		put((int)ID.ShipClass.CarrierKaga,        new float[] {0.8F,  0.8F,  0.8F,  0.75F, 0.8F,  1.2F});
		put((int)ID.ShipClass.CarrierAkagi,       new float[] {0.8F,  0.8F,  0.8F,  0.75F, 0.8F,  1.2F});
	}});
	
	/** SHIP LEASH HEIGHT
	 * 
	 *  map <ship id(short), data(float[])>
	 *  data: 0:Width, 1:Ride, 2:RideSit, 3:Sit, 4:Stand
	 */
	public static final Map<Integer, float[]> ShipLeashHeight = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
	{{
		//destroyer
		put((int)ID.ShipClass.DestroyerI,       new float[] {0.8F, 0.8F, 0.8F, 0.8F, 0.8F});
		put((int)ID.ShipClass.DestroyerRO,		new float[] {1.2F, 0.8F, 0.8F, 0.8F, 0.7F});
		put((int)ID.ShipClass.DestroyerHA,		new float[] {1.5F, 0.8F, 0.8F, 0.8F, 0.8F});
		put((int)ID.ShipClass.DestroyerNI,		new float[] {0.5F, 0.9F, 0.9F, 0.9F, 0.9F});
		//cruiser
//		put((int)ID.Ship.LightCruiserHO,		new float[] {});
//		put((int)ID.Ship.LightCruiserHE,		new float[] {});
//		put((int)ID.Ship.LightCruiserTO,		new float[] {});
//		put((int)ID.Ship.LightCruiserTSU,	new float[] {});
//		put((int)ID.Ship.TorpedoCruiserCHI,	new float[] {});
		put((int)ID.ShipClass.HeavyCruiserRI,	new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
		put((int)ID.ShipClass.HeavyCruiserNE,	new float[] {0.5F, 1.1F, 1.1F, 1.1F, 0.85F});
		//carrier
//		put((int)ID.Ship.LightCarrierNU,		new float[] {});
		put((int)ID.ShipClass.CarrierWO,		new float[] {0.1F, 0.2F, 0.2F, 0.2F, 0.15F});
		//battleship
		put((int)ID.ShipClass.BattleshipRU,		new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.15F});
		put((int)ID.ShipClass.BattleshipTA,		new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.15F});
		put((int)ID.ShipClass.BattleshipRE,		new float[] {0.1F, 0.45F, 0.45F, 0.65F, 0.45F});
		//transport
		put((int)ID.ShipClass.TransportWA,		new float[] {0.2F, 0.8F, 0.8F, 0.8F, 0.3F});
		//submarine
		put((int)ID.ShipClass.SubmarineKA,		new float[] {0.2F, 0.7F, 0.8F, 0.8F, 0.2F});
		put((int)ID.ShipClass.SubmarineYO,		new float[] {0.2F, 0.7F, 0.8F, 0.8F, 0.2F});
		put((int)ID.ShipClass.SubmarineSO,		new float[] {0.2F, 0.7F, 0.8F, 0.8F, 0.2F});
		//hime
		put((int)ID.ShipClass.CarrierHime,		new float[] {0.1F, 0.95F, 0.85F, 0.9F, 0.15F});
		put((int)ID.ShipClass.AirfieldHime,		new float[] {0.1F, 1.2F, 1.5F, 0.7F, 0.2F});
//		put((int)ID.Ship.ArmoredCarrierHime,	new float[] {});
//		put((int)ID.Ship.AnchorageHime,		new float[] {});
//		put((int)ID.Ship.LightCruiserDemon,	new float[] {});
//		put((int)ID.Ship.AirdefenseHime,		new float[] {});
		put((int)ID.ShipClass.BattleshipHime,	new float[] {0.1F, 0.3F, 0.8F, 0.7F, 0.1F});
		put((int)ID.ShipClass.DestroyerHime,	new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.35F});
		put((int)ID.ShipClass.HarbourHime,		new float[] {0F, 1.5F, 2.1F, 1.1F, 0.15F});
		put((int)ID.ShipClass.IsolatedHime,	    new float[] {0.1F, 0.85F, 0.85F, 0.85F, 0.35F});
//		put((int)ID.Ship.MidwayHime,			new float[] {});
		put((int)ID.ShipClass.NorthernHime,		new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.5F});
//		put((int)ID.Ship.SouthernHime,		new float[] {});
//		put((int)ID.Ship.SeaplaneHime,		new float[] {});
		put((int)ID.ShipClass.CarrierWD,		new float[] {0.1F, 0.95F, 0.85F, 0.9F, 0.15F});
//		put((int)ID.Ship.BattleshipWD,		new float[] {});
//		put((int)ID.Ship.AnchorageWD,		new float[] {});
//		put((int)ID.Ship.HarbourWD,			new float[] {});
//		put((int)ID.Ship.DestroyerWD,		new float[] {});
//		put((int)ID.Ship.LightCruiserHime,	new float[] {});
		put((int)ID.ShipClass.HeavyCruiserHime,	new float[] {0.5F, 1.1F, 1.1F, 1.1F, 0.85F});
//		put((int)ID.Ship.SubmarineHime,		new float[] {});
//		put((int)ID.Ship.SupplyDepotHime,	new float[] {});
		
		//hostile ship
		//DD
		put((int)ID.ShipClass.DestroyerAkatsuki,    new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put((int)ID.ShipClass.DestroyerHibiki,	    new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put((int)ID.ShipClass.DestroyerIkazuchi,    new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put((int)ID.ShipClass.DestroyerInazuma,	    new float[] {0.1F, 0.8F, 0.8F, 0.8F, 0.3F});
		put((int)ID.ShipClass.DestroyerShimakaze,   new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.4F});
		//CL
		put((int)ID.ShipClass.LightCruiserTenryuu,  new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
		put((int)ID.ShipClass.LightCruiserTatsuta,  new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.25F});
		put((int)ID.ShipClass.HeavyCruiserAtago,    new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.2F});
		put((int)ID.ShipClass.HeavyCruiserTakao,    new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.2F});
		//BB
		put((int)ID.ShipClass.BattleshipNagato,	    new float[] {0.1F, 0.95F, 0.95F, 0.95F, 0.1F});
		put((int)ID.ShipClass.BattleshipYamato,	    new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.15F});
		put((int)ID.ShipClass.BattleshipKongou,     new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		put((int)ID.ShipClass.BattleshipHiei,       new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		put((int)ID.ShipClass.BattleshipHaruna,     new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		put((int)ID.ShipClass.BattleshipKirishima,  new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		//SS
		put((int)ID.ShipClass.SubmarineU511,	    new float[] {0.1F, 1F, 1F, 1F, 0.35F});
		put((int)ID.ShipClass.SubmarineRo500,	    new float[] {0.1F, 0.9F, 0.9F, 0.9F, 0.35F});
		//CV
		put((int)ID.ShipClass.CarrierKaga,		    new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
		put((int)ID.ShipClass.CarrierAkagi,		    new float[] {0.1F, 0.85F, 0.85F, 0.9F, 0.2F});
	}});
	
	
	/** SHIP NAME ICON MAP
	 * 
	 *  map <ship id(short), int[]: 0:icon file id, 1:icon X, 2:icon Y>
	 *  
	 *  font: MS Mincho
	 *  size: 12
	 */
	public static final Map<Integer, int[]> ShipNameIconMap = Collections.unmodifiableMap(new HashMap<Integer, int[]>()
	{{
		//destroyer
		put((int)ID.ShipClass.DestroyerI,        new int[] {0, 128, 0});
		put((int)ID.ShipClass.DestroyerRO,		 new int[] {0, 139, 0});
		put((int)ID.ShipClass.DestroyerHA,		 new int[] {0, 150, 0});
		put((int)ID.ShipClass.DestroyerNI,		 new int[] {0, 161, 0});
		//cruiser
		put((int)ID.ShipClass.LightCruiserHO,	 new int[] {0, 172, 0});
		put((int)ID.ShipClass.LightCruiserHE,	 new int[] {0, 183, 0});
		put((int)ID.ShipClass.LightCruiserTO,	 new int[] {0, 194, 0});
		put((int)ID.ShipClass.LightCruiserTSU,	 new int[] {0, 205, 0});
		put((int)ID.ShipClass.TorpedoCruiserCHI, new int[] {0, 216, 0});
		put((int)ID.ShipClass.HeavyCruiserRI,	 new int[] {0, 227, 0});
		put((int)ID.ShipClass.HeavyCruiserNE,	 new int[] {0, 238, 0});
		//carrier
		put((int)ID.ShipClass.LightCarrierNU,	 new int[] {0, 128, 60});
		put((int)ID.ShipClass.CarrierWO,		 new int[] {0, 139, 60});
		//battleship
		put((int)ID.ShipClass.BattleshipRU,		 new int[] {0, 150, 60});
		put((int)ID.ShipClass.BattleshipTA,		 new int[] {0, 161, 60});
		put((int)ID.ShipClass.BattleshipRE,		 new int[] {0, 172, 60});
		//transport
		put((int)ID.ShipClass.TransportWA,		 new int[] {0, 183, 60});
		//submarine
		put((int)ID.ShipClass.SubmarineKA,		 new int[] {0, 194, 60});
		put((int)ID.ShipClass.SubmarineYO,		 new int[] {0, 205, 60});
		put((int)ID.ShipClass.SubmarineSO,		 new int[] {0, 216, 60});
		//hime
		put((int)ID.ShipClass.CarrierHime,		 new int[] {0, 227, 60});
		put((int)ID.ShipClass.AirfieldHime,		 new int[] {0, 238, 60});
		put((int)ID.ShipClass.ArmoredCarrierHime,new int[] {0, 128, 120});
		put((int)ID.ShipClass.AnchorageHime,	 new int[] {0, 139, 120});
		put((int)ID.ShipClass.LightCruiserDemon, new int[] {0, 150, 120});
		put((int)ID.ShipClass.AirdefenseHime,	 new int[] {0, 161, 120});
		put((int)ID.ShipClass.BattleshipHime,	 new int[] {0, 172, 120});
		put((int)ID.ShipClass.DestroyerHime,	 new int[] {0, 183, 120});
		put((int)ID.ShipClass.HarbourHime,		 new int[] {0, 194, 120});
		put((int)ID.ShipClass.IsolatedHime,	 	 new int[] {0, 205, 120});
		put((int)ID.ShipClass.MidwayHime,		 new int[] {0, 216, 120});
		put((int)ID.ShipClass.NorthernHime,		 new int[] {0, 227, 120});
		put((int)ID.ShipClass.SouthernHime,		 new int[] {0, 238, 120});
		
		put((int)ID.ShipClass.SeaplaneHime,		 new int[] {0, 128, 180});
		put((int)ID.ShipClass.CarrierWD,		 new int[] {0, 139, 180});
		put((int)ID.ShipClass.BattleshipWD,		 new int[] {0, 150, 180});
		put((int)ID.ShipClass.AnchorageWD,		 new int[] {0, 161, 180});
		put((int)ID.ShipClass.HarbourWD,		 new int[] {0, 172, 180});
		put((int)ID.ShipClass.DestroyerWD,		 new int[] {0, 183, 180});
		put((int)ID.ShipClass.LightCruiserHime,	 new int[] {0, 194, 180});
		put((int)ID.ShipClass.HeavyCruiserHime,	 new int[] {0, 205, 180});
		put((int)ID.ShipClass.SubmarineHime,	 new int[] {0, 216, 180});
		put((int)ID.ShipClass.SupplyDepotHime,	 new int[] {0, 227, 180});
//		put(ID.Ship.,		new int[] {0, 238, 180});  //empty for now
		
		//hostile ship
		put((int)ID.ShipClass.DestroyerShimakaze,new int[] {0, 0,  120});
		put((int)ID.ShipClass.BattleshipNagato,	 new int[] {0, 11, 120});
		put((int)ID.ShipClass.SubmarineU511,	 new int[] {0, 22, 120});
		put((int)ID.ShipClass.SubmarineRo500,	 new int[] {0, 33, 120});
		put((int)ID.ShipClass.BattleshipYamato,	 new int[] {0, 44, 120});
		put((int)ID.ShipClass.CarrierKaga,		 new int[] {0, 55, 120});
		put((int)ID.ShipClass.CarrierAkagi,		 new int[] {0, 66, 120});
		put((int)ID.ShipClass.DestroyerAkatsuki, new int[] {0, 77, 120});
		put((int)ID.ShipClass.DestroyerHibiki,	 new int[] {0, 88, 120});
		put((int)ID.ShipClass.DestroyerIkazuchi, new int[] {0, 99, 120});
		put((int)ID.ShipClass.DestroyerInazuma,	 new int[] {0, 110,120});
		
		put((int)ID.ShipClass.Raiden,			  new int[] {0, 0,  180});
		put((int)ID.ShipClass.LightCruiserTenryuu,new int[] {0, 11, 180});
		put((int)ID.ShipClass.LightCruiserTatsuta,new int[] {0, 22, 180});
		put((int)ID.ShipClass.HeavyCruiserAtago,  new int[] {0, 33, 180});
		put((int)ID.ShipClass.HeavyCruiserTakao,  new int[] {0, 44, 180});
		put((int)ID.ShipClass.BattleshipKongou,   new int[] {0, 55, 180});
		put((int)ID.ShipClass.BattleshipHiei,     new int[] {0, 66, 180});
		put((int)ID.ShipClass.BattleshipHaruna,   new int[] {0, 77, 180});
		put((int)ID.ShipClass.BattleshipKirishima,new int[] {0, 88, 180});
	}});
	
	/**SHIP LIST for guidebook
	 * 
	 * index by page number
	 */
	public static final List<Integer> ShipBookList = Collections.unmodifiableList(new ArrayList<Integer>()
	{{
		add((int)ID.ShipClass.DestroyerI);
		add((int)ID.ShipClass.DestroyerRO);
		add((int)ID.ShipClass.DestroyerHA);
		add((int)ID.ShipClass.DestroyerNI);
		add((int)ID.ShipClass.HeavyCruiserRI);
		add((int)ID.ShipClass.HeavyCruiserNE);
		add((int)ID.ShipClass.CarrierWO);
		add((int)ID.ShipClass.BattleshipTA);
		add((int)ID.ShipClass.BattleshipRE);
		add((int)ID.ShipClass.AirfieldHime);
		add((int)ID.ShipClass.BattleshipHime);
		add((int)ID.ShipClass.HarbourHime);
		add((int)ID.ShipClass.NorthernHime);
		add((int)ID.ShipClass.CarrierWD);
		add((int)ID.ShipClass.TransportWA);
		add((int)ID.ShipClass.SubmarineKA);
		add((int)ID.ShipClass.SubmarineYO);
		add((int)ID.ShipClass.SubmarineSO);
		add((int)ID.ShipClass.CarrierHime);
		add((int)ID.ShipClass.BattleshipRU);
		add((int)ID.ShipClass.DestroyerHime);
		add((int)ID.ShipClass.HeavyCruiserHime);
		add((int)ID.ShipClass.IsolatedHime);
	}});
	
	/**ENEMY LIST for guidebook
	 * 
	 * index by page number
	 */
	public static final List<Integer> EnemyBookList = Collections.unmodifiableList(new ArrayList<Integer>()
	{{
		add((int)ID.ShipClass.DestroyerShimakaze);
		add((int)ID.ShipClass.BattleshipNagato);
		add((int)ID.ShipClass.SubmarineU511);
		add((int)ID.ShipClass.SubmarineRo500);
		add((int)ID.ShipClass.BattleshipYamato);
		add((int)ID.ShipClass.CarrierKaga);
		add((int)ID.ShipClass.CarrierAkagi);
		add((int)ID.ShipClass.DestroyerAkatsuki);
		add((int)ID.ShipClass.DestroyerHibiki);
		add((int)ID.ShipClass.DestroyerIkazuchi);
		add((int)ID.ShipClass.DestroyerInazuma);
		add((int)ID.ShipClass.LightCruiserTenryuu);
		add((int)ID.ShipClass.LightCruiserTatsuta);
		add((int)ID.ShipClass.HeavyCruiserAtago);
		add((int)ID.ShipClass.HeavyCruiserTakao);
		add((int)ID.ShipClass.BattleshipKongou);
		add((int)ID.ShipClass.BattleshipHiei);
		add((int)ID.ShipClass.BattleshipHaruna);
		add((int)ID.ShipClass.BattleshipKirishima);
	}});
	
	
	/** SHIP TYPE ICON MAP
	 * 
	 *  map <ship id(short), int[]: 0:icon X, 1:icon Y>
	 *  
	 *  font: MS Mincho
	 *  size: 12
	 */
	public static final Map<Byte, int[]> ShipTypeIconMap = Collections.unmodifiableMap(new HashMap<Byte, int[]>()
	{{
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
	
	
	/**
	 * equip main attributes, index by {@link ID.Attrs}
	 * 
	 * EquipID = EquipType + EquipSubID * 100
	 * 
	 * note: 新增裝備要記得在LargeRecipe新增回收價格
	 */
	public static final Map<Integer, float[]> EquipAttrsMain = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
	{{
		//single cannon                                                                             HP   AL   AH   AAL  AAH  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_5 * 100,     new float[]{0F,  2F,  0F,  0F,  0F,  0F,   0.1F, -0.01F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_6 * 100,     new float[]{0F,  3F,  0F,  0F,  0F,  0F,   0.1F, -0.02F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_CG_5 * 100,         new float[]{30F, 4F,  0F,  0F,  0F,  0.04F,0.1F, -0.04F, 1F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.1F, 0F,   0F});
		//twin cannon                            
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5 * 100,    new float[]{0F,  4F,  0F,  0F,  0F,  0F,   0.2F, -0.06F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_6 * 100,    new float[]{0F,  5F,  0F,  0F,  0F,  0F,   0.4F, -0.08F, 1F,   0F,    0.05F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_8 * 100,    new float[]{0F,  4F,  0F,  0F,  0F,  0F,   0.2F, -0.10F, 3F,   0F,    0.05F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5DP * 100,  new float[]{0F,  5F,  0F,  0F,  0F,  0F,   0.25F,-0.12F, 1F,   0F,    0.06F, 0F,    0F,    20F, 0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_125 * 100,  new float[]{0F,  7F,  0F,  0F,  0F,  0F,   0.2F, -0.15F, 1.5F, 0F,    0.06F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_14 * 100,   new float[]{0F,  9F,  0F,  0F,  0F,  0F,   0.2F, -0.18F, 2F,   0F,    0.07F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_16 * 100,   new float[]{0F,  12F, 0F,  0F,  0F,  0F,   0.2F, -0.24F, 2F,   0F,    0.08F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_20 * 100,   new float[]{0F,  15F, 0F,  0F,  0F,  0F,   0.2F, -0.30F, 2.5F, 0F,    0.10F, 0F,    0F,    0F,  0F,  0F,   0F,   0F,   0.2F, 0F,   0F});
		//triple cannon
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_8 * 100,        new float[]{0F,  9F,  0F,  0F,  0F,  0F,   0.4F, -0.25F, 1.5F, 0F,    0.06F, 0.06F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_16 * 100,       new float[]{0F,  15F, 0F,  0F,  0F,  0F,   0.4F, -0.32F, 2F,   0F,    0.08F, 0.08F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_FG_15 * 100,        new float[]{140F,6F,  0F,  0F,  0F,  0.06F,0.2F, -0.32F, 1.5F, 0F,    0.04F, 0.04F, 0F,    0F,  0F,  0F,   0F,   0F,   0.3F, 0F,   0F});
		//machine gun
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_3 * 100,          		new float[]{0F,  1F,  0F,  0F,  0F,  0F,   0F,   -0.02F, 0F,   0F,    0F,    0F,    0F,    8F,  0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_5 * 100,          		new float[]{0F,  2F,  0F,  0F,  0F,  0F,   0F,   -0.02F, 0F,   0F,    0F,    0F,    0F,    12F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_12 * 100,     		new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.03F, 0F,   0F,    0F,    0F,    0F,    18F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_20 * 100,      	new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.04F, 0F,   0F,    0F,    0F,    0F,    25F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_40 * 100,        	new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.05F, 0F,   0.01F, 0F,    0F,    0F,    35F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_QUAD_40 * 100,        	new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   -0.08F, 0F,   0.02F, 0F,    0F,    0F,    48F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_4_CIC * 100,     	new float[]{0F,  3F,  0F,  0F,  0F,  0F,   0F,   -0.10F, 2F,   0.04F, 0F,    0F,    0.06F, 65F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		//torpedo                                                                                   HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK1 * 100,      new float[]{0F,  0F,  6F,  0F,  0F,  0F,   0F,   -0.08F, 0F,   0.03F, 0F,    0F,    0F,    0F,  8F,  0F,   0F,   0F,   0.1F, 0F,   0F});
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK2 * 100,      new float[]{0F,  0F,  12F, 0F,  0F,  0F,   0F,   -0.12F, 0.5F, 0.05F, 0F,    0F,    0F,    0F,  12F, 0F,   0F,   0F,   0.1F, 0F,   0F});
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_22MK1 * 100,      new float[]{0F,  0F,  18F, 0F,  0F,  0F,   0.1F, -0.16F, 0.5F, 0.07F, 0F,    0F,    0F,    0F,  18F, 0F,   0F,   0F,   0.15F,0F,   0F});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_CUTTLEFISH * 100, new float[]{0F,  0F,  25F, 0F,  0F,  0F,   0.1F, -0.20F, 1F,   0.09F, 0F,    0F,    0F,    0F,  24F, 0F,   0F,   0F,   0.2F, 0F,   0F});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED * 100,  new float[]{0F,  0F,  35F, 0F,  0F,  0F,   0.15F,-0.25F, 1.3F, 0.11F, 0F,    0F,    0F,    0F,  32F, 0F,   0F,   0F,   0.25F,0F,   0F});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED2 * 100, new float[]{0F,  0F,  48F, 0F,  0F,  0F,   0.2F, -0.30F, 1.6F, 0.15F, 0F,    0F,    0.06F, 0F,  45F, 0F,   0F,   0F,   0,3F, 0F,   0F});
		//Torpedo aircraft
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK1 * 100,      	new float[]{0F,  0F,  0F,  2F,  14F, 0F,   0F,   -0.10F, 2F,   0F,    0F,    0F,    0F,    3F,  12F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK2 * 100,      	new float[]{0F,  0F,  0F,  5F,  20F, 0F,   0F,   -0.14F, 2F,   0F,    0F,    0F,    0F,    6F,  18F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK3 * 100,      	new float[]{0F,  0F,  0F,  7F,  28F, 0F,   0F,   -0.18F, 2.5F, 0F,    0F,    0F,    0F,    9F,  24F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGER * 100,  	new float[]{0F,  0F,  0F,  5F,  38F, 0F,   0.05F,-0.25F, 2.5F, 0F,    0F,    0F,    0F,    15F, 30F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGERK * 100, 	new float[]{0F,  0F,  0F,  8F,  50F, 0F,   0.08F,-0.30F, 3F,   0F,    0F,    0F,    0F,    20F, 45F, 0F,   0F,   0F,   0F,   0F,   0F});
		//Fighter aircraft
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK1 * 100,      	new float[]{0F,  0F,  0F,  6F,  0F,  0F,   0F,   -0.10F, 4F,   0F,    0F,    0F,    0F,    20F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK2 * 100,      	new float[]{0F,  0F,  0F,  8F,  0F,  0F,   0F,   -0.14F, 4F,   0F,    0F,    0F,    0F,    35F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK3 * 100,      	new float[]{0F,  0F,  0F,  10F, 0F,  0F,   0.05F,-0.18F, 4.5F, 0F,    0F,    0F,    0F,    50F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FFLYFISH * 100,  	new float[]{0F,  0F,  0F,  12F, 0F,  0F,   0.05F,-0.22F, 4.5F, 0F,    0F,    0F,    0.01F, 65F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCAT * 100,  	new float[]{0F,  0F,  0F,  16F, 0F,  0F,   0.08F,-0.24F, 5F,   0F,    0F,    0F,    0.02F, 80F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATB * 100, 	new float[]{0F,  0F,  0F,  8F,  24F, 0F,   0.08F,-0.30F, 5F,   0.1F,  0F,    0F,    0.04F, 50F, 0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATK * 100, 	new float[]{0F,  0F,  0F,  20F, 0F,  0F,   0.12F,-0.28F, 5F,   0F,    0F,    0F,    0.04F, 100F,0F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FBC * 100, 	    new float[]{0F,  0F,  0F,  24F, 0F,  0F,   0.12F,-0.32F, 5.5F, 0F,    0F,    0F,    0.04F, 120F,0F,  0F,   0F,   0F,   0F,   0F,   0F});
		//Bomber aircraft                                                                           HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
		put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK1 * 100,      	new float[]{0F,  0F,  0F,  2F,  8F,  0F,   0F,   -0.14F, 1.5F, 0.04F, 0F,    0F,    0F,    2F,  2F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK2 * 100,      	new float[]{0F,  0F,  0F,  5F,  10F, 0F,   0F,   -0.18F, 2F,   0.05F, 0F,    0F,    0F,    3F,  4F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BFLYFISH * 100,  	new float[]{0F,  0F,  0F,  7F,  12F, 0F,   0F,   -0.22F, 2F,   0.06F, 0F,    0F,    0F,    4F,  8F,  0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELL * 100,     	new float[]{0F,  0F,  0F,  5F,  14F, 0F,   0F,   -0.25F,  2.5F, 0.08F, 0F,    0F,    0F,    6F,  12F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELLK * 100,    	new float[]{0F,  0F,  0F,  7F,  16F, 0F,   0.05F,-0.30F, 2.5F, 0.12F, 0F,    0F,    0F,    8F,  16F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLAND * 100,    	new float[]{0F,  0F,  0F,  8F,  18F, 0F,   0.06F,-0.35F, 3F,   0.14F, 0F,    0F,    0F,    10F, 20F, 0F,   0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLANDA * 100,    	new float[]{0F,  0F,  0F,  9F,  20F, 0F,   0.08F,-0.38F, 3.5F, 0.16F, 0F,    0F,    0F,    12F, 24F, 0F,   0F,   0F,   0F,   0F,   0F});
		//Recon aircraft
		put((int)ID.EquipType.AIR_R_LO + (int)ID.EquipSubID.AIRCRAFT_R * 100,         	new float[]{0F,  1F,  1F,  1F,  1F,  0.03F,0.02F,-0.10F, 3.5F, 0.03F, 0F,    0F,    0.10F, 8F,  4F,  0.08F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.AIR_R_HI + (int)ID.EquipSubID.AIRCRAFT_RFLYFISH * 100,  	new float[]{0F,  2F,  2F,  2F,  2F,  0.06F,0.04F,-0.16F, 5.5F, 0.05F, 0F,    0F,    0.15F, 12F, 8F,  0.15F,0F,   0F,   0F,   0F,   0F});
		//radar
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK1 * 100,       	new float[]{0F,  2F,  0F,  3F,  0F,  0.03F,0F,   -0.08F, 1F,   0F,    0F,    0F,    0.05F, 12F, 0F,  0.02F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK2 * 100,       	new float[]{0F,  3F,  0F,  4F,  0F,  0.06F,0F,   -0.10F, 2F,   0.02F, 0F,    0F,    0.10F, 18F, 0F,  0.02F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_AIRABYSS * 100,     	new float[]{0F,  4F,  0F,  6F,  0F,  0.10F,0F,   -0.12F, 2F,   0.03F, 0F,    0F,    0.15F, 24F, 0F,  0.04F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK1 * 100,       	new float[]{0F,  2F,  3F,  3F,  4F,  0.03F,0F,   -0.08F, 1F,   0F,    0F,    0F,    0.05F, 0F,  0F,  0.02F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK2 * 100,       	new float[]{0F,  3F,  4F,  4F,  5F,  0.06F,0F,   -0.10F, 2F,   0.02F, 0F,    0F,    0.10F, 0F,  0F,  0.02F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SURABYSS * 100,     	new float[]{0F,  3F,  5F,  6F,  8F,  0.10F,0F,   -0.12F, 2F,   0.03F, 0F,    0F,    0.15F, 0F,  0F,  0.04F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SONAR * 100,        	new float[]{0F,  0F,  6F,  0F,  6F,  0.03F,0F,   -0.06F, 1F,   0.02F, 0F,    0F,    0.05F, 0F,  24F, 0.03F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SONARMK2 * 100,     	new float[]{0F,  0F,  9F,  0F,  9F,  0.06F,0F,   -0.08F, 1F,   0.04F, 0F,    0F,    0.10F, 0F,  32F, 0.04F,0F,   0F,   0F,   0F,   0F});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_FCSCIC * 100,       	new float[]{0F,  4F,  6F,  8F,  10F, 0.12F,0F,   -0.12F, 3F,   0.06F, 0F,    0F,    0.18F, 24F, 24F, 0.08F,0F,   0F,   0F,   0F,   0F});
		//turbine                                                                                   HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
		put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE * 100,            new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.12F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.06F,0F,   0.2F, 0F,   0F,   0F});     
		put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE_IMP * 100,        new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.16F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.08F,0F,   0.3F, 0F,   0F,   0F});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_ENH * 100,        new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.20F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.10F,0F,   0.4F, 0F,   0F,   0F});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GE * 100,         new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.24F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.12F,0F,   0.5F, 0F,   0F,   0F});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GENEW * 100,      new float[]{0F,  0F,  0F,  0F,  0F,  0F,   0F,   0.28F,  0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.14F,0F,   0.6F, 0F,   0F,   0F});
		//armor                                                                                                                                                                                                       
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR * 100,              	new float[]{80F, 0F,  0F,  0F,  0F,  0.12F,0F,   -0.16F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.1F});
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBS * 100,          	new float[]{180F,0F,  0F,  0F,  0F,  0.02F,0F,   -0.12F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.04F,0F,   0F,   0F,   0.1F, 0.1F});
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBM * 100,          	new float[]{260F,0F,  0F,  0F,  0F,  0.03F,0F,   -0.14F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.06F,0F,   0F,   0F,   0.15F,0.12F});
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBA * 100,          	new float[]{100F,0F,  0F,  0F,  0F,  0.16F,0F,   -0.22F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0.1F, 0.2F});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ATBL * 100,          	new float[]{340F,0F,  0F,  0F,  0F,  0.04F,0F,   -0.18F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0.08F,0F,   0F,   0F,   0.3F, 0.15F});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ENH * 100,          	new float[]{100F,0F,  0F,  0F,  0F,  0.20F,0F,   -0.28F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.25F});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_APB * 100,          	new float[]{120F,0F,  0F,  0F,  0F,  0.24F,0F,   -0.35F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.3F});
		//catapult                                                                                                                                                                                                    
		put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_F * 100,        new float[]{0,   0F,  0F,  0F,  0F,  0F,   0.4F, -0.15F, 2F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.08F});
		put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_H * 100,        new float[]{0,   0F,  0F,  0F,  0F,  0F,   0.8F, -0.22F, 4F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.1F});
		put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_C * 100,        new float[]{0,   0F,  0F,  0F,  0F,  0F,   1.2F, -0.30F, 6F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0F,   0F,   0F,   0.15F});
		put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_E * 100,        new float[]{100F,0F,  0F,  0F,  0F,  0.1F, 2.2F, -0.40F, 8F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0F,   0.25F,0F,   0.5F, 0.2F});
		//drum                                                                                                                                                                                                        
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM * 100,        		    new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0.5F, 0F,   0F,   0.15F,0F});
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_F * 100,        	    new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0.5F, 0F,   0F,   0.15F,0F});
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_E * 100,        	    new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.25F, 0F,   0F,    0F,    0F,    0F,    0F,  0F,  0F,   0.5F, 0F,   0F,   0.15F,0F});
		//compass                                                                                                                                                                            
		put((int)ID.EquipType.COMPASS_LO + (int)ID.EquipSubID.COMPASS * 100,            new float[]{0,   0F,  0F,  0F,  0F,  0F,   0F,   -0.01F, 2F,   0F,    0F,    0F,    0.05F, 0F,  0F,  0F,   0.5F, 0F,   0F,   0F,   0F});
		//flare                                                                                                                                                                              
		put((int)ID.EquipType.FLARE_LO + (int)ID.EquipSubID.FLARE * 100,                new float[]{0,   1F,  0F,  2F,  0F,  0F,   0F,   -0.02F, 2F,   0.04F, 0F,    0F,    0.05F, 0F,  5F,  0F,   0F,   0F,   0F,   0F,   0F}); 
		//searchlight                                                                               HP   LA   HA   LAA  HAA  DEF   SPD   MOV     RNG   CRI    DHit   THit   Miss   AA   ASM  Dodge XP    GRUD  AMMO  HPRES KB      
		put((int)ID.EquipType.SEARCHLIGHT_LO + (int)ID.EquipSubID.SEARCHLIGHT * 100,    new float[]{0,   0F,  0F,  0F,  0F,  -0.06F,0F,  -0.02F, 2F,   0.06F, 0F,    0F,    0.1F,  0F,  10F, -0.10F,0F,  0F,   0F,   0F,   0F});
		
	}});
	
	/**
	 * equip misc attributes, index by {@link ID.EquipMisc}
	 * 
	 * EquipID = EquipType + EquipSubID * 100
	 * 
	 * equip type:
	 * 	   0:unused
	 *     1:cannon, torpedo
	 *     2:aircraft-R, engine, armor, radar
	 *     3:aircraft-T/F/B
	 */
	public static final Map<Integer, int[]> EquipAttrsMisc = Collections.unmodifiableMap(new HashMap<Integer, int[]>()
	{{
		//single cannon                                                                           Type Rare Type/Mean                     MatsType, EnchType
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_5 * 100,     new int[]{1,   ID.EquipType.CANNON_SI,      1000, 128,  2,  1});
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_SINGLE_6 * 100,     new int[]{1,   ID.EquipType.CANNON_SI,      3000, 128,  2,  1});
		put((int)ID.EquipType.CANNON_SI + (int)ID.EquipSubID.CANNON_CG_5 * 100,         new int[]{1,   ID.EquipType.CANNON_SI,      4000, 128,  2,  1});
		//twin cannon                            
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5 * 100,    new int[]{1,   ID.EquipType.CANNON_TW_LO,   1000, 320,  2,  1});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_6 * 100,    new int[]{1,   ID.EquipType.CANNON_TW_LO,   2000, 320,  2,  1});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_8 * 100,    new int[]{1,   ID.EquipType.CANNON_TW_LO,   3000, 320,  2,  1});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_5DP * 100,  new int[]{1,   ID.EquipType.CANNON_TW_LO,   3200, 320,  2,  1});
		put((int)ID.EquipType.CANNON_TW_LO + (int)ID.EquipSubID.CANNON_TWIN_125 * 100,  new int[]{1,   ID.EquipType.CANNON_TW_LO,   4000, 320,  2,  1});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_14 * 100,   new int[]{1,   ID.EquipType.CANNON_TW_HI,   1000, 1600, 2,  1});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_16 * 100,   new int[]{1,   ID.EquipType.CANNON_TW_HI,   2400, 1600, 2,  1});
		put((int)ID.EquipType.CANNON_TW_HI + (int)ID.EquipSubID.CANNON_TWIN_20 * 100,   new int[]{1,   ID.EquipType.CANNON_TW_HI,   4000, 1600, 2,  1});
		//triple cannon
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_8 * 100,        new int[]{1,   ID.EquipType.CANNON_TR,      1000, 4400, 2,  1});
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_TRI_16 * 100,       new int[]{1,   ID.EquipType.CANNON_TR,      2400, 4400, 2,  1});
		put((int)ID.EquipType.CANNON_TR + (int)ID.EquipSubID.CANNON_FG_15 * 100,        new int[]{1,   ID.EquipType.CANNON_TR,      4000, 4400, 2,  1});
		//machine gun
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_3 * 100,          		new int[]{2,   ID.EquipType.GUN_LO,         1000, 100,  2,  1});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_HA_5 * 100,          		new int[]{2,   ID.EquipType.GUN_LO,         2000, 100,  2,  1});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_12 * 100,     		new int[]{2,   ID.EquipType.GUN_LO,         3200, 100,  2,  1});
		put((int)ID.EquipType.GUN_LO + (int)ID.EquipSubID.GUN_SINGLE_20 * 100,      	new int[]{2,   ID.EquipType.GUN_LO,         4000, 100,  2,  1});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_40 * 100,        	new int[]{2,   ID.EquipType.GUN_HI,         1000, 800,  2,  1});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_QUAD_40 * 100,        	new int[]{2,   ID.EquipType.GUN_HI,         2400, 800,  2,  1});
		put((int)ID.EquipType.GUN_HI + (int)ID.EquipSubID.GUN_TWIN_4_CIC * 100,     	new int[]{2,   ID.EquipType.GUN_HI,         4000, 800,  2,  1});
		//torpedo                                                                                 Type Rare Type/Mean                     MatsType, EnchType
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK1 * 100,      new int[]{1,   ID.EquipType.TORPEDO_LO,     1000, 160,  2,  1});
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_21MK2 * 100,      new int[]{1,   ID.EquipType.TORPEDO_LO,     2400, 160,  2,  1});
		put((int)ID.EquipType.TORPEDO_LO + (int)ID.EquipSubID.TORPEDO_22MK1 * 100,      new int[]{1,   ID.EquipType.TORPEDO_LO,     4000, 160,  2,  1});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_CUTTLEFISH * 100, new int[]{1,   ID.EquipType.TORPEDO_HI,     1000, 1200, 2,  1});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED * 100,  new int[]{1,   ID.EquipType.TORPEDO_HI,     3300, 1200, 2,  1});
		put((int)ID.EquipType.TORPEDO_HI + (int)ID.EquipSubID.TORPEDO_HIGHSPEED2 * 100, new int[]{1,   ID.EquipType.TORPEDO_HI,     4000, 1200, 2,  1});
		//Torpedo aircraft
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK1 * 100,      	new int[]{3,   ID.EquipType.AIR_T_LO,       1000, 2400, 3,  1});
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK2 * 100,      	new int[]{3,   ID.EquipType.AIR_T_LO,       2400, 2400, 3,  1});
		put((int)ID.EquipType.AIR_T_LO + (int)ID.EquipSubID.AIRCRAFT_TMK3 * 100,      	new int[]{3,   ID.EquipType.AIR_T_LO,       4000, 2400, 3,  1});
		put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGER * 100,  	new int[]{3,   ID.EquipType.AIR_T_HI,       1000, 3800, 3,  1});
		put((int)ID.EquipType.AIR_T_HI + (int)ID.EquipSubID.AIRCRAFT_TAVENGERK * 100, 	new int[]{3,   ID.EquipType.AIR_T_HI,       4000, 3800, 3,  1});
		//Fighter aircraft
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK1 * 100,      	new int[]{3,   ID.EquipType.AIR_F_LO,       1000, 2400, 3,  1});
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK2 * 100,      	new int[]{3,   ID.EquipType.AIR_F_LO,       2400, 2400, 3,  1});
		put((int)ID.EquipType.AIR_F_LO + (int)ID.EquipSubID.AIRCRAFT_FMK3 * 100,      	new int[]{3,   ID.EquipType.AIR_F_LO,       4000, 2400, 3,  1});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FFLYFISH * 100,  	new int[]{3,   ID.EquipType.AIR_F_HI,       1000, 3800, 3,  1});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCAT * 100,  	new int[]{3,   ID.EquipType.AIR_F_HI,       2400, 3800, 3,  1});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATB * 100, 	new int[]{3,   ID.EquipType.AIR_F_HI,       2900, 3800, 3,  1});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FHELLCATK * 100, 	new int[]{3,   ID.EquipType.AIR_F_HI,       3400, 3800, 3,  1});
		put((int)ID.EquipType.AIR_F_HI + (int)ID.EquipSubID.AIRCRAFT_FBC * 100, 	    new int[]{3,   ID.EquipType.AIR_F_HI,       4000, 3800, 3,  1});
		//Bomber aircraft                                                                         Type Rare Type/Mean                     MatsType, EnchType
		put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK1 * 100,      	new int[]{3,   ID.EquipType.AIR_B_LO,       1000, 2400, 3,  1});
		put((int)ID.EquipType.AIR_B_LO + (int)ID.EquipSubID.AIRCRAFT_BMK2 * 100,      	new int[]{3,   ID.EquipType.AIR_B_LO,       4000, 2400, 3,  1});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BFLYFISH * 100,  	new int[]{3,   ID.EquipType.AIR_B_HI,       1000, 3800, 3,  1});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELL * 100,     	new int[]{3,   ID.EquipType.AIR_B_HI,       2400, 3800, 3,  1});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BHELLK * 100,    	new int[]{3,   ID.EquipType.AIR_B_HI,       3200, 3800, 3,  1});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLAND * 100,    	new int[]{3,   ID.EquipType.AIR_B_HI,       3500, 3800, 3,  1});
		put((int)ID.EquipType.AIR_B_HI + (int)ID.EquipSubID.AIRCRAFT_BLANDA * 100,    	new int[]{3,   ID.EquipType.AIR_B_HI,       4000, 3800, 3,  1});
		//Recon aircraft
		put((int)ID.EquipType.AIR_R_LO + (int)ID.EquipSubID.AIRCRAFT_R * 100,         	new int[]{2,   ID.EquipType.AIR_R_LO,       200,  256,  3,  1});
		put((int)ID.EquipType.AIR_R_HI + (int)ID.EquipSubID.AIRCRAFT_RFLYFISH * 100,  	new int[]{2,   ID.EquipType.AIR_R_HI,       200,  1000, 3,  1});
		//radar
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK1 * 100,       	new int[]{2,   ID.EquipType.RADAR_LO,       1000, 200,  0,  3});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_AIRMK2 * 100,       	new int[]{2,   ID.EquipType.RADAR_LO,       1800, 200,  0,  3});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_AIRABYSS * 100,     	new int[]{2,   ID.EquipType.RADAR_HI,       1000, 2000, 0,  3});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK1 * 100,       	new int[]{2,   ID.EquipType.RADAR_LO,       2600, 200,  0,  3});
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SURMK2 * 100,       	new int[]{2,   ID.EquipType.RADAR_LO,       3400, 200,  0,  3});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SURABYSS * 100,     	new int[]{2,   ID.EquipType.RADAR_HI,       2000, 2000, 0,  3});	
		put((int)ID.EquipType.RADAR_LO + (int)ID.EquipSubID.RADAR_SONAR * 100,        	new int[]{2,   ID.EquipType.RADAR_LO,       4000, 200,  0,  3});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_SONARMK2 * 100,     	new int[]{2,   ID.EquipType.RADAR_HI,       3200, 2000, 0,  3});
		put((int)ID.EquipType.RADAR_HI + (int)ID.EquipSubID.RADAR_FCSCIC * 100,       	new int[]{2,   ID.EquipType.RADAR_HI,       4000, 2000, 0,  3});
		//turbine                                                                                 Type Rare Type/Mean                     MatsType, EnchType
		put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE * 100,            new int[]{2,   ID.EquipType.TURBINE_LO,     1000, 1400, 0,  3});
		put((int)ID.EquipType.TURBINE_LO + (int)ID.EquipSubID.TURBINE_IMP * 100,        new int[]{2,   ID.EquipType.TURBINE_LO,     4000, 1400, 0,  3});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_ENH * 100,        new int[]{2,   ID.EquipType.TURBINE_HI,     2000, 3200, 0,  3});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GE * 100,         new int[]{2,   ID.EquipType.TURBINE_HI,     3000, 3200, 0,  3});
		put((int)ID.EquipType.TURBINE_HI + (int)ID.EquipSubID.TURBINE_GENEW * 100,      new int[]{2,   ID.EquipType.TURBINE_HI,     4400, 3200, 0,  3});
		//armor
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR * 100,              	new int[]{2,   ID.EquipType.ARMOR_LO,       1000, 80,   1,  2});
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBS * 100,          	new int[]{2,   ID.EquipType.ARMOR_LO,       2000, 80,   1,  2});
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBM * 100,          	new int[]{2,   ID.EquipType.ARMOR_LO,       3000, 80,   1,  2});
		put((int)ID.EquipType.ARMOR_LO + (int)ID.EquipSubID.ARMOR_ATBA * 100,          	new int[]{2,   ID.EquipType.ARMOR_LO,       4000, 80,   1,  2});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ATBL * 100,          	new int[]{2,   ID.EquipType.ARMOR_HI,       1000, 500,  1,  2});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_ENH * 100,          	new int[]{2,   ID.EquipType.ARMOR_HI,       2000, 500,  1,  2});
		put((int)ID.EquipType.ARMOR_HI + (int)ID.EquipSubID.ARMOR_APB * 100,          	new int[]{2,   ID.EquipType.ARMOR_HI,       4000, 500,  1,  2});
		//catapult
		put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_F * 100,        new int[]{3,   ID.EquipType.CATAPULT_LO,    1000, 2800, 3,  3});
		put((int)ID.EquipType.CATAPULT_LO + (int)ID.EquipSubID.CATAPULT_H * 100,        new int[]{3,   ID.EquipType.CATAPULT_LO,    2000, 2800, 3,  3});
		put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_C * 100,        new int[]{3,   ID.EquipType.CATAPULT_HI,    3200, 5000, 3,  3});
		put((int)ID.EquipType.CATAPULT_HI + (int)ID.EquipSubID.CATAPULT_E * 100,        new int[]{3,   ID.EquipType.CATAPULT_HI,    4400, 5000, 3,  3});
		//drum
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM * 100,        		    new int[]{2,   ID.EquipType.DRUM_LO,  	    2000, 120,  1,  3});
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_F * 100,        		new int[]{2,   ID.EquipType.DRUM_LO,  	    2500, 120,  1,  3});
		put((int)ID.EquipType.DRUM_LO + (int)ID.EquipSubID.DRUM_E * 100,        		new int[]{2,   ID.EquipType.DRUM_LO,  	    2500, 120,  1,  3});
		//compass
		put((int)ID.EquipType.COMPASS_LO + (int)ID.EquipSubID.COMPASS * 100,        	new int[]{2,   ID.EquipType.COMPASS_LO,     2000, 90,   0,  3});
		//flare
		put((int)ID.EquipType.FLARE_LO + (int)ID.EquipSubID.FLARE * 100,        		new int[]{1,   ID.EquipType.FLARE_LO,       2000, 80,   2,  3});
		//searchlight                                                                             Type Rare Type/Mean                     MatsType, EnchType
		put((int)ID.EquipType.SEARCHLIGHT_LO + (int)ID.EquipSubID.SEARCHLIGHT * 100,    new int[]{2,   ID.EquipType.SEARCHLIGHT_LO, 2000, 80,   0,  3});
					
	}});

	
	/** FORMATION BUFFS MAP
	 * 
	 *  index: {@link ID.Attrs}
	 *  put id: formatID * 10 + slotID
	 *  
	 *  ex: formation 2, slot 4 = 24
	 *  
	 *  multiplication:
	 *    ATK, DEF, SPD, CRI, DHIT, THIT, MISS, AA, ASM 
	 *  addition:
	 *    HP, MOV, HIT, DODGE, XP, GRUDGE, AMMO, HPRES, KB
	 *  no effect by default:
	 *    HP, XP, AMMO
	 */
	public static final Map<Integer, float[]> FormationAttrs = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
	{{
		//Line Ahead          HP, ATK_L  ATK_H  ATK_AL ATK_AH DEF    SPD,   MOV    HIT, CRI    DHIT   THIT   MISS   AA     ASM    DODGE   XP   GRU    AMMO HPRES KB
		put(10,  new float[] {0F, 2F,    2F,    1.2F,  1.2F,  0.3F,  1.3F,  0.08F, 4F,  1.75F, 1.75F, 1.75F, 1.25F, 0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
		put(11,  new float[] {0F, 1.75F, 1.75F, 1.2F,  1.2F,  0.4F,  1.3F,  0.08F, 4F,  1.55F, 1.55F, 1.55F, 1.2F,  0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
		put(12,  new float[] {0F, 1.55F, 1.55F, 1.15F, 1.15F, 0.5F,  1.2F,  0.08F, 3F,  1.4F,  1.4F,  1.4F,  1.2F,  0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
		put(13,  new float[] {0F, 1.4F,  1.4F,  1.15F, 1.15F, 0.6F,  1.2F,  0.08F, 3F,  1.3F,  1.3F,  1.3F,  1.15F, 0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
		put(14,  new float[] {0F, 1.3F,  1.3F,  1.1F,  1.1F,  0.7F,  1.1F,  0.08F, 2F,  1.2F,  1.2F,  1.2F,  1.15F, 0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
		put(15,  new float[] {0F, 1.25F, 1.25F, 1.1F,  1.1F,  0.8F,  1.1F,  0.08F, 2F,  1.1F,  1.1F,  1.1F,  1.1F,  0.5F,  0.4F,  0.1F,   0F,  0.2F,  0F,  0F,   0F});
		//Double Line
		put(20,  new float[] {0F, 1.4F,  1.4F,  1.1F,  1.1F,  0.9F,  1.08F, 0F,    2F,  1.15F, 1.15F, 1.15F, 1.55F, 1.2F,  1F,    -0.15F, 0F,  0.1F,  0F,  0.3F, 0.05F});
		put(21,  new float[] {0F, 1.4F,  1.4F,  1.1F,  1.1F,  0.9F,  1.08F, 0F,    2F,  1.15F, 1.15F, 1.15F, 1.55F, 1.2F,  1F,    -0.15F, 0F,  0.1F,  0F,  0.3F, 0.05F});
		put(22,  new float[] {0F, 1.5F,  1.5F,  1.15F, 1.15F, 0.75F, 1.15F, 0F,    3F,  1.3F,  1.3F,  1.3F,  1.75F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
		put(23,  new float[] {0F, 1.5F,  1.5F,  1.15F, 1.15F, 0.75F, 1.15F, 0F,    3F,  1.3F,  1.3F,  1.3F,  1.75F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
		put(24,  new float[] {0F, 1.3F,  1.3F,  1.05F, 1.05F, 1F,    1F,    0F,    1F,  1.1F,  1.1F,  1.1F,  1.35F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
		put(25,  new float[] {0F, 1.3F,  1.3F,  1.05F, 1.05F, 1F,    1F,    0F,    1F,  1.1F,  1.1F,  1.1F,  1.35F, 1.1F,  1F,    -0.05F, 0F,  0.1F,  0F,  0.1F, 0.05F});
		//Diamond
		put(30,  new float[] {0F, 0.6F,  0.3F,  2F,    2F,    1.5F,  1F,    -0.1F, 4F,  1.1F,  1F,    1F,    1F,    2F,    1F,    -0.5F,  0F,  0F,    0F,  0.5F, 0.1F});
		put(31,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
		put(32,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
		put(33,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
		put(34,  new float[] {0F, 1F,    0.65F, 1.2F,  1.2F,  1.25F, 1F,    -0.1F, 1F,  1.1F,  1.1F,  1.1F,  1F,    1.75F, 1.3F,  -0.3F,  0F,  0F,    0F,  0.3F, 0.1F});
		put(35,  new float[] {0F, 0.6F,  0.3F,  2F,    2F,    1.5F,  1F,    -0.1F, 4F,  1.1F,  1F,    1F,    1F,    2F,    1F,    -0.5F,  0F,  0F,    0F,  0.5F, 0.1F});
		//Echelon             HP, ATK_L  ATK_H  ATK_AL ATK_AH DEF    SPD,   MOV    HIT, CRI    DHIT   THIT   MISS   AA     ASM    DODGE   XP   GRU    AMMO HPRES KB
		put(40,  new float[] {0F, 1.2F,  1.2F,  1F,    1F,    0.75F, 1F,    0.18F, 2F,  1.25F, 1.25F, 1.25F, 0.65F, 0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
		put(41,  new float[] {0F, 1.1F,  1.1F,  1F,    1F,    0.85F, 1F,    0.18F, 2F,  1.2F,  1.2F,  1.2F,  0.7F,  0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
		put(42,  new float[] {0F, 1F,    1F,    1F,    1F,    0.95F, 1F,    0.18F, 2F,  1.15F, 1.15F, 1.15F, 0.75F, 0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
		put(43,  new float[] {0F, 1F,    1F,    1F,    1F,    1.05F, 1F,    0.18F, 1F,  1.1F,  1.1F,  1.1F,  0.8F,  0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
		put(44,  new float[] {0F, 0.9F,  0.9F,  1F,    1F,    1.15F, 1F,    0.18F, 1F,  1.05F, 1.05F, 1.05F, 0.85F, 0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
		put(45,  new float[] {0F, 0.8F,  0.8F,  1F,    1F,    1.25F, 1F,    0.18F, 1F,  1F,    1F,    1F,    0.9F,  0.3F,  0.8F,  0.25F,  0F,  0.25F, 0F,  0F,   0F});
		//Line Abreast
		put(50,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
		put(51,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
		put(52,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
		put(53,  new float[] {0F, 0.9F,  0.9F,  0.9F,  0.9F,  1.35F, 0.8F,  0.05F, -2F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.15F, 0F,  0F,    0F,  0.1F, 0F});
		put(54,  new float[] {0F, 1F,    1F,    1F,    1F,    1.2F,  0.9F,  0.05F, -1F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.1F,  0F,  0F,    0F,  0F,   0F});
		put(55,  new float[] {0F, 1F,    1F,    1F,    1F,    1.2F,  0.9F,  0.05F, -1F, 1.15F, 1F,    1F,    1F,    1F,    1.75F, -0.1F,  0F,  0F,    0F,  0F,   0F});
		//no buff
		put(0,   AttrsAdv.getResetFormationValue());
		put(1,   AttrsAdv.getResetFormationValue());
		put(2,   AttrsAdv.getResetFormationValue());
		put(3,   AttrsAdv.getResetFormationValue());
		put(4,   AttrsAdv.getResetFormationValue());
		put(5,   AttrsAdv.getResetFormationValue());
	}});
	
	
	/**
	 * morale buff, index by {@link ID.Attrs}
	 * 
	 * key: -1:normal, 0:Excited, 1:Happy, 2:Tired, 3:Exhausted
	 * 
	 * multiplication:
	 *   ATK, SPD, CRI, DHIT, THIT, MISS, AA, ASM 
	 * addition:
	 *   HP, DEF, MOV, HIT, DODGE, XP, GRUDGE, AMMO, HPRES, KB
	 * ignore:
	 *   HP
	 */
	public static final Map<Integer, float[]> MoraleAttrs = Collections.unmodifiableMap(new HashMap<Integer, float[]>()
	{{
		//                 HP,  ATK_L  ATK_H  ATK_AL ATK_AH DEF   SPD,  MOV    HIT,  CRI   DHIT  THIT  MISS  AA    ASM   DODGE  XP     GRU    AMMO   HPRES   KB
		put(0, new float[]{0F,  1.25F, 1.25F, 1.25F, 1.25F, 0.2F, 1.4F, 0.15F, 4F,   1.2F, 1.2F, 1.2F, 1.5F, 1.5F, 1.5F, 0.25F, 0.5F,  0.5F,  0.5F,  0.5F,   0.25F});
		put(1, new float[]{0F,  1.1F,  1.1F,  1.1F,  1.1F,  0.1F, 1.2F, 0.08F, 2F,   1.1F, 1.1F, 1.1F, 1.25F,1.25F,1.25F,0.12F, 0.25F, 0.25F, 0.25F, 0.25F,  0.15F});
		put(2, new float[]{0F,  0.9F,  0.9F,  0.9F,  0.9F,  -0.1F,0.8F, -0.08F,-2F,  0.9F, 0.9F, 0.9F, 0.75F,0.75F,0.75F,-0.12F,-0.25F,-0.25F,-0.25F,-0.25F, -0.1F});
		put(3, new float[]{0F,  0.75F, 0.75F, 0.75F, 0.75F, -0.2F,0.6F, -0.15F,-4F,  0.8F, 0.8F, 0.8F, 0.5F, 0.5F, 0.5F, -0.25F,-0.5F, -0.5F, -0.5F, -0.5F,  -0.2F});
		put(-1, AttrsAdv.getResetMoraleValue());
	}});
	
	
	/** ITEMSTACK MAP FOR ICON
	 *  
	 */
	public static final Map<Short, ItemStack> ItemIconMap = Collections.unmodifiableMap(new HashMap<Short, ItemStack>()
	{{
		put(ID.Icon.IronIG,     new ItemStack(Items.IRON_INGOT));
		put(ID.Icon.Grudge,     new ItemStack(ModItems.Grudge));
		put(ID.Icon.GrudgeB,    new ItemStack(ModBlocks.BlockGrudge));
		put(ID.Icon.GrudgeBH,   new ItemStack(ModBlocks.BlockGrudgeHeavy));
		put(ID.Icon.AbyssIG,    new ItemStack(ModItems.AbyssMetal, 1, 0));
		put(ID.Icon.AbyssB,     new ItemStack(ModBlocks.BlockAbyssium));
		put(ID.Icon.PolymIG,    new ItemStack(ModItems.AbyssMetal, 1, 1));
		put(ID.Icon.PolymOre,   new ItemStack(ModBlocks.BlockPolymetalOre));
		put(ID.Icon.PolymB,     new ItemStack(ModBlocks.BlockPolymetal));
		put(ID.Icon.PolymBG,    new ItemStack(ModBlocks.BlockPolymetalGravel));
		put(ID.Icon.Gunpowder,  new ItemStack(Items.GUNPOWDER));
		put(ID.Icon.Blazepowder,new ItemStack(Items.BLAZE_POWDER));
		put(ID.Icon.AmmoL,      new ItemStack(ModItems.Ammo, 1, 0));
		put(ID.Icon.AmmoLC,     new ItemStack(ModItems.Ammo, 1, 1));
		put(ID.Icon.AmmoH,      new ItemStack(ModItems.Ammo, 1, 2));
		put(ID.Icon.AmmoHC,     new ItemStack(ModItems.Ammo, 1, 3));
		put(ID.Icon.RpBucket,   new ItemStack(ModItems.BucketRepair));
		put(ID.Icon.LaBucket,   new ItemStack(Items.LAVA_BUCKET));
		put(ID.Icon.NStar,      new ItemStack(Items.NETHER_STAR));
		put(ID.Icon.Ring,       new ItemStack(ModItems.MarriageRing));
		put(ID.Icon.Paper,      new ItemStack(Items.PAPER));
		put(ID.Icon.OwnPaper,   new ItemStack(ModItems.OwnerPaper));
		put(ID.Icon.Stick,      new ItemStack(Items.STICK));
		put(ID.Icon.KHammer,    new ItemStack(ModItems.KaitaiHammer));
		put(ID.Icon.ModTool,    new ItemStack(ModItems.ModernKit));
		put(ID.Icon.SpawnEgg0,  new ItemStack(ModItems.ShipSpawnEgg, 1, 0));
		put(ID.Icon.SpawnEgg1,  new ItemStack(ModItems.ShipSpawnEgg, 1, 1));
		put(ID.Icon.SpawnEgg2,  new ItemStack(ModItems.ShipSpawnEgg, 1, 2));
		put(ID.Icon.InstantMat, new ItemStack(ModItems.InstantConMat));
		put(ID.Icon.DiamondB,   new ItemStack(Blocks.DIAMOND_BLOCK));
		put(ID.Icon.RpGod,      new ItemStack(ModItems.RepairGoddess));
		put(ID.Icon.Pointer,    new ItemStack(ModItems.PointerItem));
		put(ID.Icon.ModelZF,    new ItemStack(ModItems.ToyAirplane));
		put(ID.Icon.Desk,       new ItemStack(ModBlocks.BlockDesk));
		put(ID.Icon.DeskBook,   new ItemStack(ModItems.DeskItemBook));
		put(ID.Icon.DeskRadar,  new ItemStack(ModItems.DeskItemRadar));
		put(ID.Icon.WriteBook,  new ItemStack(Items.WRITABLE_BOOK));
		put(ID.Icon.Compass,    new ItemStack(Items.COMPASS));
		put(ID.Icon.ObsidianB,  new ItemStack(Blocks.OBSIDIAN));
		put(ID.Icon.WoolB,      new ItemStack(Blocks.WOOL));
		put(ID.Icon.SmallSY,    new ItemStack(ModBlocks.BlockSmallShipyard));
		put(ID.Icon.Wrench,     new ItemStack(ModItems.TargetWrench));
		put(ID.Icon.VolCore,    new ItemStack(ModBlocks.BlockVolCore));
		put(ID.Icon.VolBlock,   new ItemStack(ModBlocks.BlockVolBlock));
		put(ID.Icon.Frame,      new ItemStack(ModBlocks.BlockFrame));
		put(ID.Icon.Waypoint,   new ItemStack(ModBlocks.BlockWaypoint));
		put(ID.Icon.Crane,      new ItemStack(ModBlocks.BlockCrane));
		put(ID.Icon.Piston,     new ItemStack(Blocks.PISTON));
		put(ID.Icon.TrainBook,  new ItemStack(ModItems.TrainingBook));
		put(ID.Icon.MagmaB,     new ItemStack(Blocks.MAGMA));
		put(ID.Icon.Tank0,      new ItemStack(ModItems.ShipTank, 1, 0));
		put(ID.Icon.Tank1,      new ItemStack(ModItems.ShipTank, 1, 1));
		put(ID.Icon.Tank2,      new ItemStack(ModItems.ShipTank, 1, 2));
		put(ID.Icon.Tank3,      new ItemStack(ModItems.ShipTank, 1, 3));
		put(ID.Icon.Cauldron,   new ItemStack(Items.CAULDRON));
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
	public static final Map<Integer, List> BookList = Collections.unmodifiableMap(new HashMap<Integer, List>()
	{{
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
								new int[] {2, 0, 23, -3, ID.Icon.NStar},
								new int[] {2, 0, 3,  17, ID.Icon.AbyssIG},
								new int[] {2, 0, 43, 17, ID.Icon.AbyssIG},
								new int[] {2, 0, 23, 37, ID.Icon.AbyssIG},
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
								new int[] {2, 0, 23, -3, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 43, -3, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 3,  17, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 23, 17, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 43, 17, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 3,  37, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 23, 37, ID.Icon.SpawnEgg0},
								new int[] {2, 0, 43, 37, ID.Icon.SpawnEgg0},
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
								new int[] {2, 0, 23, -3, ID.Icon.MagmaB},
								new int[] {2, 0, 43, -3, ID.Icon.ObsidianB},
								new int[] {2, 0, 3,  17, ID.Icon.MagmaB},
								new int[] {2, 0, 23, 17, ID.Icon.GrudgeB},
								new int[] {2, 0, 43, 17, ID.Icon.MagmaB},
								new int[] {2, 0, 3,  37, ID.Icon.ObsidianB},
								new int[] {2, 0, 23, 37, ID.Icon.MagmaB},
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
								new int[] {2, 0, 23, 17, ID.Icon.GrudgeBH},
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
		//page 24: training book
		put(1024, Arrays.asList(new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {2, 0, 23, 17, ID.Icon.TrainBook}));
		//page 25: ship tank
		put(1025, Arrays.asList(new int[] {3, 6, 1},
								new int[] {0, 0, 0, 0},
								new int[] {0, 1, 0, 0},
								new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 0, 3,  -3, ID.Icon.PolymIG},
								new int[] {2, 0, 23, -3, ID.Icon.Cauldron},
								new int[] {2, 0, 43, -3, ID.Icon.PolymIG},
								new int[] {2, 0, 3,  17, ID.Icon.PolymIG},
								new int[] {2, 0, 23, 17, ID.Icon.Cauldron},
								new int[] {2, 0, 43, 17, ID.Icon.PolymIG},
								new int[] {2, 0, 3,  37, ID.Icon.PolymIG},
								new int[] {2, 0, 23, 37, ID.Icon.Cauldron},
								new int[] {2, 0, 43, 37, ID.Icon.PolymIG},
								new int[] {2, 0, 81, 17, ID.Icon.Tank0},
								new int[] {1, 2, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 2, 3,  -3, ID.Icon.ObsidianB},
								new int[] {2, 2, 23, -3, ID.Icon.Tank0},
								new int[] {2, 2, 43, -3, ID.Icon.ObsidianB},
								new int[] {2, 2, 3,  17, ID.Icon.ObsidianB},
								new int[] {2, 2, 23, 17, ID.Icon.Tank0},
								new int[] {2, 2, 43, 17, ID.Icon.ObsidianB},
								new int[] {2, 2, 3,  37, ID.Icon.ObsidianB},
								new int[] {2, 2, 23, 37, ID.Icon.Tank0},
								new int[] {2, 2, 43, 37, ID.Icon.ObsidianB},
								new int[] {2, 2, 81, 17, ID.Icon.Tank1},
								new int[] {1, 4, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 4, 3,  -3, ID.Icon.AbyssB},
								new int[] {2, 4, 23, -3, ID.Icon.Tank1},
								new int[] {2, 4, 43, -3, ID.Icon.AbyssB},
								new int[] {2, 4, 3,  17, ID.Icon.AbyssB},
								new int[] {2, 4, 23, 17, ID.Icon.Tank1},
								new int[] {2, 4, 43, 17, ID.Icon.AbyssB},
								new int[] {2, 4, 3,  37, ID.Icon.AbyssB},
								new int[] {2, 4, 23, 37, ID.Icon.Tank1},
								new int[] {2, 4, 43, 37, ID.Icon.AbyssB},
								new int[] {2, 4, 81, 17, ID.Icon.Tank2},
								new int[] {1, 6, 0, -6, 0, 100, 72, 100, 62},
								new int[] {2, 6, 3,  -3, ID.Icon.GrudgeBH},
								new int[] {2, 6, 23, -3, ID.Icon.Tank2},
								new int[] {2, 6, 43, -3, ID.Icon.GrudgeBH},
								new int[] {2, 6, 3,  17, ID.Icon.GrudgeBH},
								new int[] {2, 6, 23, 17, ID.Icon.Tank2},
								new int[] {2, 6, 43, 17, ID.Icon.GrudgeBH},
								new int[] {2, 6, 3,  37, ID.Icon.GrudgeBH},
								new int[] {2, 6, 23, 37, ID.Icon.Tank2},
								new int[] {2, 6, 43, 37, ID.Icon.GrudgeBH},
								new int[] {2, 6, 81, 17, ID.Icon.Tank3}));
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