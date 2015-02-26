package com.lulan.shincolle.reference;

//for array ID
public class ID {

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
	
	//Array ID: Entity State
	public static final byte State = 0;
	public static final byte Emotion = 1;
	public static final byte Emotion2 = 2;
	
	//Array ID: Minor State
	public static final byte ShipLevel = 0;
	public static final byte Kills = 1;
	public static final byte ExpCurrent = 2;
	public static final byte ExpNext = 3;
	public static final byte NumAmmoLight = 4;
	public static final byte NumAmmoHeavy = 5;
	public static final byte NumGrudge = 6;
	public static final byte NumAirLight = 7;
	public static final byte NumAirHeavy = 8;
	
	//Array ID: EntityFlag
	public static final byte F_CanFloatUp = 0;
	public static final byte F_IsMarried = 1;
	public static final byte F_NoFuel = 2;
	public static final byte F_UseMelee = 3;
	public static final byte F_UseAmmoLight = 4;
	public static final byte F_UseAmmoHeavy = 5;
	public static final byte F_UseAirLight = 6;
	public static final byte F_UseAirHeavy = 7;
	
	//GUI Button ID
	public static final byte B_ShipInv_Melee = 0;
	public static final byte B_ShipInv_AmmoLight = 1;
	public static final byte B_ShipInv_AmmoHeavy = 2;
	public static final byte B_ShipInv_AirLight = 3;
	public static final byte B_ShipInv_AirHeavy = 4;
	public static final byte B_Shipyard_Type = 0;
	public static final byte B_Shipyard_InvMode = 1;
	public static final byte B_Shipyard_SelectMat = 2;
	public static final byte B_Shipyard_INCDEC = 3;
	
	//Array ID: Equip Map
	public static final byte LEVEL_E = 0;
	public static final byte HP_E = 1;
	public static final byte ATK_E = 2;
	public static final byte ATK_H_E = 3;
	public static final byte ATK_AL_E = 4;
	public static final byte ATK_AH_E = 5;
	public static final byte DEF_E = 6;
	public static final byte SPD_E = 7;
	public static final byte MOV_E = 8;
	public static final byte HIT_E = 9;
	
	//GUI ID
	public static final byte SHIPINVENTORY = 0;
	public static final byte SMALLSHIPYARD = 1;
	public static final byte LARGESHIPYARD = 2;
	
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
	public static final byte E_RADAR_AIRABYSS = 52;
	public static final byte E_RADAR_SURMK1 = 53;
	public static final byte E_RADAR_SURMK2 = 54;
	public static final byte E_RADAR_SURABYSS = 55;
	public static final byte E_RADAR_SONAR = 56;
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
	
	public static final byte S_AircraftCarrierHime = 20;
	public static final byte S_AirfieldHime = 21;
	public static final byte S_ArmoredCarrierHime = 22;
	public static final byte S_AnchorageHime = 23;
	public static final byte S_EscortFortress = 24;
	public static final byte S_FloatingFortress = 25;
	public static final byte S_BattleshipHime = 26;
	public static final byte S_DestroyerHime = 27;
	public static final byte S_HarbourHime = 28;
	public static final byte S_IsolatedOni = 29;
	public static final byte S_MidwayHime = 30;
	public static final byte S_NorthernHime = 31;
	public static final byte S_SouthernHime = 32;
	public static final byte S_ACWaterOni = 33;
	
		
}
