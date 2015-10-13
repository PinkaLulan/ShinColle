package com.lulan.shincolle.reference;

//for array ID
public class ID {

	//GUI Button ID
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
		
		public static final byte Shipyard_Type = 0;
		public static final byte Shipyard_InvMode = 1;
		public static final byte Shipyard_SelectMat = 2;
		public static final byte Shipyard_INCDEC = 3;
	}
	
	//shipyard build type
	public static final class Build {
		public static final byte NONE = 0;
		public static final byte SHIP = 1;
		public static final byte EQUIP = 2;
		public static final byte SHIP_LOOP = 3;
		public static final byte EQUIP_LOOP = 4;
	}
	
	//Array ID: Equip Map
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
	}
	
	//Type ID: Emotion
	public static final class Emotion {
		public static final byte NORMAL = 0;			//no emotion
		public static final byte BLINK = 1;				//blink eye
		public static final byte T_T = 2;				//sad
		public static final byte O_O = 3;				//...
		public static final byte BORED = 4;				//sit phase 2
		public static final byte HUNGRY = 5;			//no grudge
	}
	
	//Type ID: Equip type
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
	}
	
	//Array ID: EntityFlag
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
		public static final byte CanFollow = 11;		//server only, no sync
		public static final byte OnSightChase = 12;
		public static final byte AtkType_Light = 13;
		public static final byte AtkType_Heavy = 14;
		public static final byte AtkType_AirLight = 15;
		public static final byte AtkType_AirHeavy = 16;
		public static final byte HaveRingEffect = 17;
	}
	
	//GUI ID
	public static final class G {
		public static final byte SHIPINVENTORY = 0;
		public static final byte SMALLSHIPYARD = 1;
		public static final byte LARGESHIPYARD = 2;
	}
	
	//Type ID: ship state2
	public static final class HPState {
		public static final byte NORMAL = 0;	//無受損
		public static final byte MINOR = 1;		//小破
		public static final byte MODERATE = 2;	//中破
		public static final byte HEAVY = 3;		//大破
	}
	
	//Array ID: Minor State
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
		public static final byte TargetAI = 13;		//active AI or passive AI 
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
	}
	
	//Array ID: ring effect
	public static final class R {
		public static final byte Haste = 0;
		public static final byte Speed = 1;
		public static final byte Jump = 2;
		public static final byte Damage = 3;
	}
	
	//Array ID: Entity State
	public static final class S {
		public static final byte State = 0;				//equip state
		public static final byte Emotion = 1;			//emotion
		public static final byte Emotion2 = 2;			//emotion 2
		public static final byte HPState = 3;			//hp state
		public static final byte State2 = 4;			//equip state 2
		public static final byte Phase = 5;				//entity phase
	}
	
	//Type ID: ship type
	public static final class ShipType {				//for GUI display
		public static final byte DESTROYER = 0;			//驅逐艦
		public static final byte LIGHT_CRUISER = 1;		//輕巡洋艦
		public static final byte HEAVY_CRUISER = 2;		//重巡洋艦
		public static final byte TORPEDO_CRUISER = 3;	//重雷裝巡洋艦
		public static final byte LIGHT_CARRIER = 4;		//輕航空母艦
		public static final byte STANDARD_CARRIER = 5;	//正規航空母艦
		public static final byte BATTLESHIP	= 6;		//戰艦
		public static final byte TRANSPORT = 7;			//運輸艦
		public static final byte SUBMARINE = 8;			//潛水艇
		public static final byte DEMON = 9;				//鬼級
		public static final byte HIME = 10;				//姬級
		public static final byte FORTRESS = 11;			//浮游要塞
	}
	
	/**Type ID: ship state*/
	public static final class State {
		/**for ID.S.State*/
		public static final byte NORMAL = 0;		//無狀態
		public static final byte EQUIP00 = 20;		//艤裝狀態0
		public static final byte EQUIP01 = 30;		//艤裝狀態1
		public static final byte EQUIP02 = 40;		//艤裝狀態2
		public static final byte EQUIP03 = 50;		//艤裝狀態3
		public static final byte EQUIP04 = 60;		//艤裝狀態4
		public static final byte EQUIP05 = 70;		//艤裝狀態5
		/**for ID.S.State2*/
		public static final byte NORMAL_2 = 0;		//無狀態
		public static final byte EQUIP00_2 = 1;		//艤裝狀態0
		public static final byte EQUIP01_2 = 2;		//艤裝狀態1
		public static final byte EQUIP02_2 = 3;		//艤裝狀態2
		public static final byte EQUIP03_2 = 4;		//艤裝狀態3
		public static final byte EQUIP04_2 = 5;		//艤裝狀態4
		public static final byte EQUIP05_2 = 6;		//艤裝狀態5
	}

	//Array ID: StateEquip, StateFinal, BonusPoint, TypeModify
	public static final byte HP = 0;
	public static final byte ATK = 1;
	public static final byte DEF = 2;
	public static final byte SPD = 3;
	public static final byte MOV = 4;
	public static final byte HIT = 5;
	public static final byte ATK_H = 6;
	public static final byte ATK_AL = 7;
	public static final byte ATK_AH = 8;
	public static final byte CRI = 9;
	public static final byte DHIT = 10;
	public static final byte THIT = 11;
	public static final byte MISS = 12;
	public static final byte SpawnPerSquid = 6;
	
	//Array ID: Effect Equip
	public static final byte EF_CRI = 0;
	public static final byte EF_DHIT = 1;
	public static final byte EF_THIT = 2;
	public static final byte EF_MISS = 3;
	
	//Equip ID
	public static final byte E_CANNON_SINGLE_5 = 0;
	public static final byte E_CANNON_SINGLE_6 = 1;
	public static final byte E_CANNON_TWIN_5 = 2;
	public static final byte E_CANNON_TWIN_6 = 3;
	public static final byte E_CANNON_TWIN_5DP = 4;
	public static final byte E_CANNON_TWIN_125 = 5;
	public static final byte E_CANNON_TWIN_14 = 6;
	public static final byte E_CANNON_TWIN_16 = 7;
	public static final byte E_CANNON_TWIN_20 = 8;
	public static final byte E_CANNON_TRI_8 = 9;
	public static final byte E_CANNON_TRI_16 = 10;
	public static final byte E_CANNON_FG_15 = 11;
	public static final byte E_TORPEDO_21MK1 = 20;
	public static final byte E_TORPEDO_21MK2 = 21;
	public static final byte E_TORPEDO_22MK1 = 22;
	public static final byte E_TORPEDO_CUTTLEFISH = 23;
	public static final byte E_TORPEDO_HIGHSPEED = 24;
	public static final byte E_AIRCRAFT_TMK1 = 30;
	public static final byte E_AIRCRAFT_TMK2 = 31;
	public static final byte E_AIRCRAFT_TMK3 = 32;
	public static final byte E_AIRCRAFT_TAVENGER = 33;
	public static final byte E_AIRCRAFT_FMK1 = 34;
	public static final byte E_AIRCRAFT_FMK2 = 35;
	public static final byte E_AIRCRAFT_FMK3 = 36;
	public static final byte E_AIRCRAFT_FFLYFISH = 37;
	public static final byte E_AIRCRAFT_FHELLCAT = 38;
	public static final byte E_AIRCRAFT_BMK1 = 39;
	public static final byte E_AIRCRAFT_BMK2 = 40;
	public static final byte E_AIRCRAFT_BFLYFISH = 41;
	public static final byte E_AIRCRAFT_BHELL = 42;
	public static final byte E_AIRCRAFT_R = 43;
	public static final byte E_AIRCRAFT_RFLYFISH = 44;
	public static final byte E_RADAR_AIRMK1 = 50;
	public static final byte E_RADAR_AIRMK2 = 51;
	public static final byte E_RADAR_SURMK1 = 52;
	public static final byte E_RADAR_SURMK2 = 53;
	public static final byte E_RADAR_SONAR = 54;
	public static final byte E_RADAR_AIRABYSS = 55;
	public static final byte E_RADAR_SURABYSS = 56;
	public static final byte E_RADAR_SONARMK2 = 57;
	public static final byte E_TURBINE = 60;
	public static final byte E_TURBINE_IMP = 61;
	public static final byte E_TURBINE_ENH = 62;
	public static final byte E_ARMOR = 70;
	public static final byte E_ARMOR_ENH = 71;

	//Ship ID
	public static final byte S_DestroyerI = 0;
	public static final byte S_DestroyerRO = 1;
	public static final byte S_DestroyerHA = 2;
	public static final byte S_DestroyerNI = 3;
	
	public static final byte S_LightCruiserHO = 4;
	public static final byte S_LightCruiserHE = 5;
	public static final byte S_LightCruiserTO = 6;
	public static final byte S_LightCruiserTSU = 7;
	
	public static final byte S_TorpedoCruiserCHI = 8;
	public static final byte S_HeavyCruiserRI = 9;
	public static final byte S_HeavyCruiserNE = 10;
	
	public static final byte S_LightCarrierNU = 11;
	public static final byte S_CarrierWO = 12;
	
	public static final byte S_BattleshipRU = 13;
	public static final byte S_BattleshipTA = 14;
	public static final byte S_BattleshipRE = 15;
	
	public static final byte S_TransportWA = 16;
	public static final byte S_SubmarineKA = 17;
	public static final byte S_SubmarineYO = 18;
	public static final byte S_SubmarineSO = 19;
	
	public static final byte S_CarrierHime = 20;
	public static final byte S_AirfieldHime = 21;
	public static final byte S_ArmoredCarrierHime = 22;
	public static final byte S_AnchorageHime = 23;
	public static final byte S_EscortFortress = 24;
	public static final byte S_FloatingFortress = 25;
	public static final byte S_BattleshipHime = 26;
	public static final byte S_DestroyerHime = 27;
	public static final byte S_HarbourHime = 28;
	public static final byte S_IsolatedDemon = 29;
	public static final byte S_MidwayHime = 30;
	public static final byte S_NorthernHime = 31;
	public static final byte S_SouthernHime = 32;
	public static final byte S_CarrierWD = 33;
	public static final byte S_LightCruiserDemon = 34;
	public static final byte S_BattleshipWD = 35;
	
	public static final byte S_DestroyerShimakaze = 36;
	public static final byte S_BattleshipNagato = 37;
	public static final byte S_SubmarineU511 = 38;
	public static final byte S_SubmarineRo500 = 39;
	
		
}
