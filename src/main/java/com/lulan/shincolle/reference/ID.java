package com.lulan.shincolle.reference;

//for array ID
public class ID {

	/**GUI Button ID*/
	public static final class B {
		public static final byte ShipInv_Melee = 0;
		public static final byte ShipInv_AmmoLight = 1;
		public static final byte ShipInv_AmmoHeavy = 2;
		public static final byte ShipInv_AirLight = 3;
		public static final byte ShipInv_AirHeavy = 4;
		public static final byte ShipInv_FollowMin = 5;
		public static final byte ShipInv_FollowMax = 6;
		public static final byte ShipInv_FleeHP = 7;
		public static final byte ShipInv_TarAI = 8;
		public static final byte ShipInv_AuraEffect = 9;
		public static final byte ShipInv_OnSightAI = 10;
		public static final byte ShipInv_PVPAI = 11;
		public static final byte ShipInv_AAAI = 12;
		public static final byte ShipInv_ASMAI = 13;
		public static final byte ShipInv_TIMEKEEPAI = 14;
		public static final byte ShipInv_InvPage = 15;
		
		public static final byte Shipyard_Type = 0;
		public static final byte Shipyard_InvMode = 1;
		public static final byte Shipyard_SelectMat = 2;
		public static final byte Shipyard_INCDEC = 3;
		
		public static final byte Desk_Sync = 0;
	}
	
	/** ship body part */
	public static final class Body {
		public static final byte UBelly = 20;
		public static final byte Chest = 21;
		public static final byte Butt = 22;
		public static final byte Neck = 23;
		public static final byte Face = 24;
		public static final byte Back = 25;
		public static final byte Belly = 26;
		public static final byte Top = 27;
		public static final byte Head = 28;
		public static final byte Leg = 29;
		public static final byte Arm = 30;
		
		/** by height */
		public static final class Height {
			public static final byte Top = 0;
			public static final byte Head = 1;
			public static final byte Neck = 2;
			public static final byte Chest = 3;
			public static final byte Belly = 4;
			public static final byte UBelly = 5;
			public static final byte Leg = 6;
		}
		
		/** by angle */
		public static final class Side {
			public static final byte Back = 10;
			public static final byte Right = 11;
			public static final byte Front = 12;
			public static final byte Left = 13;
		}
	}
	
	/** Shipyard build type*/
	public static final class Build {
		public static final byte NONE = 0;
		public static final byte SHIP = 1;
		public static final byte EQUIP = 2;
		public static final byte SHIP_LOOP = 3;
		public static final byte EQUIP_LOOP = 4;
	}
	
	/** Equip Attrs Map */
	public static final class E {
		public static final byte LEVEL = 0;
		public static final byte HP = 1;
		public static final byte ATK_L = 2;
		public static final byte ATK_H = 3;
		public static final byte ATK_AL = 4;
		public static final byte ATK_AH = 5;
		public static final byte DEF = 6;
		public static final byte SPD = 7;
		public static final byte MOV = 8;
		public static final byte HIT = 9;
		public static final byte CRI = 10;
		public static final byte DHIT = 11;
		public static final byte THIT = 12;
		public static final byte MISS = 13;
		public static final byte AA = 14;
		public static final byte ASM = 15;
		public static final byte RARE_TYPE = 16;
		public static final byte RARE_MEAN = 17;
		public static final byte DODGE = 18;
	}
	
	/** Emotion */
	public static final class Emotion {
		public static final byte NORMAL = 0;			//no emotion
		public static final byte BLINK = 1;				//blink eye
		public static final byte T_T = 2;				//sad
		public static final byte O_O = 3;				//...
		public static final byte BORED = 4;				//sit phase 2
		public static final byte HUNGRY = 5;			//no grudge
	}
	
	/** Equip type */
	public static final class EquipType {
		public static final byte CANNON_SI = 0;			//single cannon
		public static final byte CANNON_TW_LO = 1;		//low level twin cannon
		public static final byte CANNON_TW_HI = 2;		//high level twin cannon
		public static final byte CANNON_TR = 3;			//triple cannon
		public static final byte TORPEDO_LO = 4;		//low level torpedo
		public static final byte TORPEDO_HI = 5;		//high level torpedo
		public static final byte AIR_T_LO = 6;			//low level aircraft T
		public static final byte AIR_T_HI = 7;			//high level aircraft T
		public static final byte AIR_F_LO = 8;			//low level aircraft F
		public static final byte AIR_F_HI = 9;			//high level aircraft F
		public static final byte AIR_B_LO = 10;			//low level aircraft B
		public static final byte AIR_B_HI = 11;			//high level aircraft B
		public static final byte AIR_R_LO = 12;			//low level aircraft R
		public static final byte AIR_R_HI = 13;			//high level aircraft R
		public static final byte RADAR_LO = 14;			//low level radar
		public static final byte RADAR_HI = 15;			//high level radar
		public static final byte TURBINE_LO = 16;		//low level turbine
		public static final byte TURBINE_HI = 17;		//high level turbine
		public static final byte ARMOR_LO = 18;			//low level armor
		public static final byte ARMOR_HI = 19;			//high level armor
		public static final byte GUN_LO = 20;			//low level machine gun
		public static final byte GUN_HI = 21;			//high level machine gun
		public static final byte CATAPULT_LO = 22;		//low level catapult
		public static final byte CATAPULT_HI = 23;		//high level catapult
		public static final byte DRUM_LO = 24;			//low level drum
		public static final byte COMPASS_LO = 25;		//low level compass
		public static final byte FLARE_LO = 26;			//low level flare
		public static final byte SEARCHLIGHT_LO = 27;	//low level searchlight
	}
	
	/** Equip Sub ID = item meta value
	 * 
	 *  EquipID = EquipType + EquipSubID * 100
	 * 
	 */
	public static final class EquipSubID {
		//cannon
		public static final byte CANNON_SINGLE_5 = 0;
		public static final byte CANNON_SINGLE_6 = 1;
		public static final byte CANNON_TWIN_5 = 2;
		public static final byte CANNON_TWIN_6 = 3;
		public static final byte CANNON_TWIN_5DP = 4;
		public static final byte CANNON_TWIN_125 = 5;
		public static final byte CANNON_TWIN_14 = 6;
		public static final byte CANNON_TWIN_16 = 7;
		public static final byte CANNON_TWIN_20 = 8;
		public static final byte CANNON_TRI_8 = 9;
		public static final byte CANNON_TRI_16 = 10;
		public static final byte CANNON_FG_15 = 11;
		//torpedo
		public static final byte TORPEDO_21MK1 = 0;
		public static final byte TORPEDO_21MK2 = 1;
		public static final byte TORPEDO_22MK1 = 2;
		public static final byte TORPEDO_CUTTLEFISH = 3;
		public static final byte TORPEDO_HIGHSPEED = 4;
		//aircraft
		public static final byte AIRCRAFT_TMK1 = 0;
		public static final byte AIRCRAFT_TMK2 = 1;
		public static final byte AIRCRAFT_TMK3 = 2;
		public static final byte AIRCRAFT_TAVENGER = 3;
		public static final byte AIRCRAFT_FMK1 = 4;
		public static final byte AIRCRAFT_FMK2 = 5;
		public static final byte AIRCRAFT_FMK3 = 6;
		public static final byte AIRCRAFT_FFLYFISH = 7;
		public static final byte AIRCRAFT_FHELLCAT = 8;
		public static final byte AIRCRAFT_BMK1 = 9;
		public static final byte AIRCRAFT_BMK2 = 10;
		public static final byte AIRCRAFT_BFLYFISH = 11;
		public static final byte AIRCRAFT_BHELL = 12;
		public static final byte AIRCRAFT_R = 13;
		public static final byte AIRCRAFT_RFLYFISH = 14;
		public static final byte AIRCRAFT_TAVENGERK = 15;
		public static final byte AIRCRAFT_FHELLCATK = 16;
		public static final byte AIRCRAFT_BHELLK = 17;
		//radar
		public static final byte RADAR_AIRMK1 = 0;
		public static final byte RADAR_AIRMK2 = 1;
		public static final byte RADAR_SURMK1 = 2;
		public static final byte RADAR_SURMK2 = 3;
		public static final byte RADAR_SONAR = 4;
		public static final byte RADAR_AIRABYSS = 5;
		public static final byte RADAR_SURABYSS = 6;
		public static final byte RADAR_SONARMK2 = 7;
		public static final byte RADAR_FCSCIC = 8;
		//turbine
		public static final byte TURBINE = 0;
		public static final byte TURBINE_IMP = 1;
		public static final byte TURBINE_ENH = 2;
		//armor
		public static final byte ARMOR = 0;
		public static final byte ARMOR_ENH = 1;
		//machine gun
		public static final byte GUN_HA_3 = 0;
		public static final byte GUN_HA_5 = 1;
		public static final byte GUN_SINGLE_12 = 2;
		public static final byte GUN_SINGLE_20 = 3;
		public static final byte GUN_TWIN_40 = 4;
		public static final byte GUN_QUAD_40 = 5;
		public static final byte GUN_TWIN_4_CIC = 6;
		//catapult
		public static final byte CATAPULT_F = 0;
		public static final byte CATAPULT_H = 1;
		public static final byte CATAPULT_C = 2;
		public static final byte CATAPULT_E = 3;
		//drum
		public static final byte DRUM_N = 0;
		//compass
		public static final byte COMPASS = 0;
		//flare
		public static final byte FLARE = 0;
		//searchlight
		public static final byte SEARCHLIGHT = 0;
	}
	
	/** Entity Flag */
	public static final class F {
		public static final byte CanFloatUp = 0;
		public static final byte IsMarried = 1;
		public static final byte NoFuel = 2;
		public static final byte UseMelee = 3;
		public static final byte UseAmmoLight = 4;
		public static final byte UseAmmoHeavy = 5;
		public static final byte UseAirLight = 6;
		public static final byte UseAirHeavy = 7;
		public static final byte HeadTilt = 8;			//client only, no sync
		public static final byte UseRingEffect = 9;
		public static final byte CanDrop = 10;			//server only, no sync
		public static final byte CanFollow = 11;
		public static final byte OnSightChase = 12;
		public static final byte AtkType_Light = 13;
		public static final byte AtkType_Heavy = 14;
		public static final byte AtkType_AirLight = 15;
		public static final byte AtkType_AirHeavy = 16;
		public static final byte HaveRingEffect = 17;
		public static final byte PVPFirst = 18;
		public static final byte AntiAir = 19;
		public static final byte AntiSS = 20;
		public static final byte PassiveAI = 21;
		public static final byte TimeKeeper = 22;
	}
	
	/** Update Flag */
	public static final class FU {
		public static final byte FormationBuff = 0;
	}
	
	/** Formation Effect */
	public static final class Formation {
		public static final byte ATK_L = 0;
		public static final byte ATK_H = 1;
		public static final byte ATK_AL = 2;
		public static final byte ATK_AH = 3;
		public static final byte DEF = 4;
		public static final byte MOV = 5;
		public static final byte MISS = 6;
		public static final byte DODGE = 7;
		public static final byte CRI = 8;
		public static final byte DHIT = 9;
		public static final byte THIT = 10;
		public static final byte AA = 11;
		public static final byte ASM = 12;
	}
	
	/** Formation Fixed Effect */
	public static final class FormationFixed {
		public static final byte MOV = 0;
	}
	
	/**GUI ID*/
	public static final class G {
		public static final byte SHIPINVENTORY = 0;
		public static final byte SMALLSHIPYARD = 1;
		public static final byte LARGESHIPYARD = 2;
		public static final byte ADMIRALDESK = 3;
		public static final byte ADMIRALDESK_ITEM = 4;
		public static final byte FORMATION = 5;
		public static final byte DEBUGGER = 6;  //NYI
		public static final byte VOLCORE = 7;
	}
	
	/** ICON ID for BOOK */
	public static final class Icon {
		public static final byte IronIG = 0;
		public static final byte Grudge = 1;
		public static final byte GrudgeB = 2;
		public static final byte GrudgeBH = 3;
		public static final byte AbyssIG = 4;
		public static final byte AbyssB = 5;
		public static final byte PolymIG = 6;
		public static final byte PolymB = 7;
		public static final byte PolymBG = 8;
		public static final byte PolymOre = 9;
		public static final byte Gunpowder = 10;
		public static final byte Blazepowder = 11;
		public static final byte AmmoL = 12;
		public static final byte AmmoLC = 13;
		public static final byte AmmoH = 14;
		public static final byte AmmoHC = 15;
		public static final byte RpBucket = 16;
		public static final byte LaBucket = 17;
		public static final byte NStar = 18;
		public static final byte Ring = 19;
		public static final byte Paper = 20;
		public static final byte OwnPaper = 21;
		public static final byte Stick = 22;
		public static final byte KHammer = 23;
		public static final byte ModTool = 24;
		public static final byte SpawnEgg0 = 25;
		public static final byte SpawnEgg1 = 26;
		public static final byte SpawnEgg2 = 27;
		public static final byte InstantMat = 28;
		public static final byte DiamondB = 29;
		public static final byte RpGod = 30;
		public static final byte Pointer = 31;
		public static final byte ModelZF = 32;
		public static final byte Desk = 33;
		public static final byte ObsidianB = 34;
		public static final byte WoolB = 35;
		public static final byte SmallSY = 36;
		public static final byte EqCannon0 = 37;
		public static final byte EqCannon1 = 38;
		public static final byte EqCannon2 = 39;
		public static final byte EqMGun = 40;
		public static final byte EqCatap = 41;
		public static final byte EqRadar = 42;
		public static final byte EqTurbine = 43;
		public static final byte EqTorpedo = 44;
		public static final byte EqAirT = 45;
		public static final byte EqAirF = 46;
		public static final byte EqAirB = 47;
		public static final byte EqAirR = 48;
		public static final byte EqArmor = 49;
		public static final byte DeskBook = 50;
		public static final byte DeskRadar = 51;
		public static final byte WriteBook = 52;
		public static final byte Compass = 53;
		public static final byte Wrench = 54;
		public static final byte VolCore = 55;
		public static final byte VolBlock = 56;
		public static final byte EqDrum = 57;
	}
	
	/**ship state2*/
	public static final class HPState {
		public static final byte NORMAL = 0;	//正常
		public static final byte MINOR = 1;		//小破
		public static final byte MODERATE = 2;	//中破
		public static final byte HEAVY = 3;		//大破
	}
	
	/** Minor State */
	public static final class M {
		public static final byte ShipLevel = 0;
		public static final byte Kills = 1;
		public static final byte ExpCurrent = 2;	//exp curr/next
		public static final byte ExpNext = 3;
		public static final byte NumAmmoLight = 4;
		public static final byte NumAmmoHeavy = 5;
		public static final byte NumGrudge = 6;
		public static final byte NumAirLight = 7;
		public static final byte NumAirHeavy = 8;
		public static final byte ImmuneTime = 9;	//entity immune time
		public static final byte FollowMin = 10;	//follow range min/max
		public static final byte FollowMax = 11;	
		public static final byte FleeHP = 12;		//flee hp%
		public static final byte TargetAI = 13;		//NO USE
		public static final byte GuardX = 14;		//guard xyz pos
		public static final byte GuardY = 15;
		public static final byte GuardZ = 16;
		public static final byte GuardDim = 17;		//guard entity world id
		public static final byte GuardID = 18;		//guard entity id
		public static final byte ShipType = 19;		//ship type
		public static final byte ShipClass = 20;	//ship class
		public static final byte PlayerUID = 21;	//player UID
		public static final byte ShipUID = 22;		//ship UID
		public static final byte PlayerEID = 23;	//player entity id
		public static final byte GuardType = 24;	//guard type: 0:move 1:move & attack
		public static final byte DamageType = 25;	//damage type
		public static final byte FormatType = 26;	//formation type
		public static final byte FormatPos = 27;	//formation position
		public static final byte GrudgeCon = 28;	//grudge consumption when idle
		public static final byte AmmoCon = 29;		//ammo base consumption
		public static final byte Morale = 30;		//morale value
		public static final byte Food = 31;			//food saturation
		public static final byte FoodMax = 32;		//max food saturation
		public static final byte HitHeight = 33;	//hit height by pointer item, NO SYNC
		public static final byte HitAngle = 34;		//hit angle by pointer item, NO SYNC
		public static final byte SensBody = 35;		//sensitive body part id
		public static final byte InvSize = 36;		//inventory page size
		public static final byte LevelChunkLoader = 37;	//level of chunk loader
		public static final byte LevelFlare = 38;		//level of flare
		public static final byte LevelSearchlight = 39;	//level of searchlight
	}
	
	/** morale level */
	public static final class Morale {
		public static final byte Excited = 0;
		public static final byte Happy = 1;
		public static final byte Normal = 2;
		public static final byte Tired = 3;
		public static final byte Exhausted = 4;
	}
	
	/** packet type ID */
	public static final class Packets {
		public static final byte TEST = 0;
		public static final byte S2C_EntitySync = 1;
		public static final byte S2C_Particle = 2;
		public static final byte C2S_GUIInput = 3;
		public static final byte S2C_GUISync = 4;
		public static final byte S2C_PlayerSync = 5;  //no use for now
		public static final byte C2S_CmdInput = 6;
		public static final byte S2C_CmdSync = 7;
	}
	
	/** ring effect, no used for now */
	public static final class R {
		public static final byte Haste = 0;
		public static final byte Speed = 1;
		public static final byte Jump = 2;
		public static final byte Damage = 3;
	}
	
	/** Entity State */
	public static final class S {
		public static final byte State = 0;				//equip state
		public static final byte Emotion = 1;			//emotion
		public static final byte Emotion2 = 2;			//emotion 2
		public static final byte HPState = 3;			//hp state
		public static final byte State2 = 4;			//equip state 2
		public static final byte Phase = 5;				//entity phase
	}
	
	/** ship class id */
	public static final class Ship {
		public static final short DestroyerI = 0;
		public static final short DestroyerRO = 1;
		public static final short DestroyerHA = 2;
		public static final short DestroyerNI = 3;
		
		public static final short LightCruiserHO = 4;
		public static final short LightCruiserHE = 5;
		public static final short LightCruiserTO = 6;
		public static final short LightCruiserTSU = 7;
		
		public static final short TorpedoCruiserCHI = 8;
		public static final short HeavyCruiserRI = 9;
		public static final short HeavyCruiserNE = 10;
		
		public static final short LightCarrierNU = 11;
		public static final short CarrierWO = 12;
		
		public static final short BattleshipRU = 13;
		public static final short BattleshipTA = 14;
		public static final short BattleshipRE = 15;
		
		public static final short TransportWA = 16;
		public static final short SubmarineKA = 17;
		public static final short SubmarineYO = 18;
		public static final short SubmarineSO = 19;
		
		public static final short CarrierHime = 20;
		public static final short AirfieldHime = 21;
		public static final short ArmoredCarrierHime = 22;
		public static final short AnchorageHime = 23;
		public static final short HarbourWD = 24;
		public static final short AnchorageWD = 25;
		public static final short BattleshipHime = 26;
		public static final short DestroyerHime = 27;
		public static final short HarbourHime = 28;
		public static final short IsolatedDemon = 29;
		public static final short MidwayHime = 30;
		public static final short NorthernHime = 31;
		public static final short SouthernHime = 32;
		public static final short CarrierWD = 33;
		public static final short LightCruiserDemon = 34;
		public static final short BattleshipWD = 35;
		
		public static final short DestroyerShimakaze = 36;
		public static final short BattleshipNagato = 37;
		public static final short SubmarineU511 = 38;
		public static final short SubmarineRo500 = 39;
		
		public static final short SeaplaneHime = 40;
		public static final short AirdefenseHime = 41;
		public static final short PTImp = 42;
		public static final short LightCruiserHime = 43;
		public static final short SubmarineHime = 44;
		public static final short DestroyerWD = 45;
		
		public static final short BattleshipYamato = 46;
		public static final short CarrierKaga = 47;
		public static final short CarrierAkagi = 48;
		
		public static final short HeavyCruiserHime = 49;
		public static final short SupplyDepotHime = 50;
	}
	
	/** ship attrs id, for Values.ShipAttrMap */
	public static final class ShipAttr {
		public static final byte BaseHP = 0;
		public static final byte BaseATK = 1;
		public static final byte BaseDEF = 2;
		public static final byte BaseSPD = 3;
		public static final byte BaseMOV = 4;
		public static final byte BaseHIT = 5;
		public static final byte ModHP = 6;
		public static final byte ModATK = 7;
		public static final byte ModDEF = 8;
		public static final byte ModSPD = 9;
		public static final byte ModMOV = 10;
		public static final byte ModHIT = 11;
	}
	
	/** ship type for damage calc */
	public static final class ShipDmgType {
		public static final byte UNDEFINED = 0;			//未定義
		public static final byte CARRIER = 1;			//航母
		public static final byte AVIATION = 2;			//航戰
		public static final byte BATTLESHIP	= 3;		//戰艦
		public static final byte CRUISER = 4;			//巡洋
		public static final byte DESTROYER = 5;			//驅逐
		public static final byte SUBMARINE = 6;			//潛艇
		public static final byte AIRPLANE = 7;			//飛機
	}
	
	/** ship type for GUI display */
	public static final class ShipType {				//for GUI display
		public static final byte DESTROYER = 0;			//DD
		public static final byte LIGHT_CRUISER = 1;		//CL
		public static final byte HEAVY_CRUISER = 2;		//CA CAV
		public static final byte TORPEDO_CRUISER = 3;	//CLT
		public static final byte LIGHT_CARRIER = 4;		//CVL
		public static final byte STANDARD_CARRIER = 5;	//CV
		public static final byte BATTLESHIP	= 6;		//BB BBV
		public static final byte TRANSPORT = 7;			//AR AO
		public static final byte SUBMARINE = 8;			//SS
		public static final byte DEMON = 9;				//demon + water demon
		public static final byte HIME = 10;				//princess
	}
	
	/** ship type for consumption ID */
	public static final class ShipConsume {
		/** ship type */
		public static final byte DD = 0;
		public static final byte CL = 1;
		public static final byte CA = 2;
		public static final byte CAV = 3;
		public static final byte CLT = 4;
		public static final byte CVL = 5;
		public static final byte CV	= 6;
		public static final byte BB = 7;
		public static final byte BBV = 8;
		public static final byte SS = 9;
		public static final byte AP = 10;
		/** ship action */
		public static final byte LAtk = 0;
		public static final byte HAtk = 1;
		public static final byte LAir = 2;
		public static final byte HAir = 3;
		public static final byte Move = 4;
	}
	
	/** ship state for equip model display */
	public static final class State {
		/** for ID.S.State */
		public static final byte NORMAL = 0;
		public static final byte EQUIP00 = 1;
		public static final byte EQUIP01 = 2;
		public static final byte EQUIP02 = 3;
		public static final byte EQUIP03 = 4;
		public static final byte EQUIP04 = 5;
		public static final byte EQUIP05 = 6;
		public static final byte EQUIP06 = 7;
		public static final byte EQUIP07 = 8;
		public static final byte EQUIP08 = 9;
		public static final byte EQUIP09 = 10;
		/** for ID.S.State2 */
		public static final byte NORMAL_2 = 0;
		public static final byte EQUIP00_2 = 1;
		public static final byte EQUIP01_2 = 2;
		public static final byte EQUIP02_2 = 3;
		public static final byte EQUIP03_2 = 4;
		public static final byte EQUIP04_2 = 5;
		public static final byte EQUIP05_2 = 6;
		public static final byte EQUIP06_2 = 7;
		public static final byte EQUIP07_2 = 8;
		public static final byte EQUIP08_2 = 9;
		public static final byte EQUIP09_2 = 10;
	}

	/** StateEquip, StateFinal, BonusPoint, TypeModify */
	public static final byte HP = 0;
	public static final byte ATK = 1;
	public static final byte DEF = 2;
	public static final byte SPD = 3;
	public static final byte MOV = 4;
	public static final byte HIT = 5;
	public static final byte ATK_H = 6;
	public static final byte ATK_AL = 7;
	public static final byte ATK_AH = 8;
	
	/** Effect Equip */
	public static final byte EF_CRI = 0;
	public static final byte EF_DHIT = 1;
	public static final byte EF_THIT = 2;
	public static final byte EF_MISS = 3;
	public static final byte EF_AA = 4;
	public static final byte EF_ASM = 5;
	public static final byte EF_DODGE = 6;
	
		
}
