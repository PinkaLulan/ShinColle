package com.lulan.shincolle.reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//for array ID
public class ID
{
    
    
    /** ship attributes index */
    public static final class Attrs
    {
        /**
         * HP: float, 0~N
         * ATK: float, 1~N
         * DEF: int, -N~1 (1 = 100%)
         * SPD: float, 0.1~4
         * MOV: float, 0~0.6 (blocks per tick)
         * HIT: int, 1~64 (blocks)
         * CRI, DHIT, THIT, MISS, DODGE, KB: float, 0.0~1.0 (1 = 100%)
         * XP, GRUDGE, AMMO, HPRES: float, 0~N (1 = 100%, default = 100%)
         */
        public static final byte HP = 0;
        public static final byte ATK_L = 1;
        public static final byte ATK_H = 2;
        public static final byte ATK_AL = 3;
        public static final byte ATK_AH = 4;
        public static final byte DEF = 5;
        public static final byte SPD = 6;
        public static final byte MOV = 7;
        public static final byte HIT = 8;
        public static final byte CRI = 9;
        public static final byte DHIT = 10;
        public static final byte THIT = 11;
        public static final byte MISS = 12;
        public static final byte AA = 13;
        public static final byte ASM = 14;
        public static final byte DODGE = 15;
        public static final byte XP = 16;
        public static final byte GRUDGE = 17;
        public static final byte AMMO = 18;
        public static final byte HPRES = 19;
        public static final byte KB = 20;
    }
    
    /** index for bonus point, type modify, scale config */
    public static final class AttrsBase
    {
        public static final byte HP = 0;
        public static final byte ATK = 1;
        public static final byte DEF = 2;
        public static final byte SPD = 3;
        public static final byte MOV = 4;
        public static final byte HIT = 5;
        public static final byte modHP = 6;
        public static final byte modATK = 7;
        public static final byte modDEF = 8;
        public static final byte modSPD = 9;
        public static final byte modMOV = 10;
        public static final byte modHIT = 11;
    }
    
    /** GUI Button ID */
    public static final class B
    {
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
        public static final byte ShipInv_PickitemAI = 16;
        public static final byte ShipInv_WpStay = 17;
        public static final byte ShipInv_ShowHeld = 18;
        public static final byte ShipInv_AutoCR = 19;
        public static final byte ShipInv_AutoPump = 20;
        public static final byte ShipInv_ModelState01 = 21;
        public static final byte ShipInv_ModelState02 = 22;
        public static final byte ShipInv_ModelState03 = 23;
        public static final byte ShipInv_ModelState04 = 24;
        public static final byte ShipInv_ModelState05 = 25;
        public static final byte ShipInv_ModelState06 = 26;
        public static final byte ShipInv_ModelState07 = 27;
        public static final byte ShipInv_ModelState08 = 28;
        public static final byte ShipInv_ModelState09 = 29;
        public static final byte ShipInv_ModelState10 = 30;
        public static final byte ShipInv_ModelState11 = 31;
        public static final byte ShipInv_ModelState12 = 32;
        public static final byte ShipInv_ModelState13 = 33;
        public static final byte ShipInv_ModelState14 = 34;
        public static final byte ShipInv_ModelState15 = 35;
        public static final byte ShipInv_ModelState16 = 36;
        public static final byte ShipInv_Task = 37;
        public static final byte ShipInv_TaskSide = 38;
        public static final byte ShipInv_NoFuel = 39;
        public static final byte ShipInv_Sit = 40;
        public static final byte ShipInv_EmoFlag1 = 41;
        public static final byte ShipInv_EmoFlag2 = 42;
        
        public static final byte MorphInv_AddAmmoL = 0;
        public static final byte MorphInv_AddAmmoH = 1;
        public static final byte MorphInv_AddGrudge = 2;
        
        public static final byte Shipyard_Type = 0;
        public static final byte Shipyard_InvMode = 1;
        public static final byte Shipyard_SelectMat = 2;
        public static final byte Shipyard_INCDEC = 3;
        
        public static final byte Desk_Sync = 0;
        
        public static final byte Crane_Power = 0;
        public static final byte Crane_Mode = 1;
        public static final byte Crane_Meta = 2;
        public static final byte Crane_Dict = 3;
        public static final byte Crane_Load = 4;
        public static final byte Crane_Unload = 5;
        public static final byte Crane_Nbt = 6;
        public static final byte Crane_Red = 7;
        public static final byte Crane_Liquid = 8;
        public static final byte Crane_Energy = 9;
        
        public static final byte VolCore_Power = 0;
    }
    
    /** ship body part */
    public static final class Body
    {
        public static final byte UBelly = 0;
        public static final byte Chest = 1;
        public static final byte Butt = 2;
        public static final byte Neck = 3;
        public static final byte Face = 4;
        public static final byte Back = 5;
        public static final byte Belly = 6;
        public static final byte Top = 7;
        public static final byte Head = 8;
        public static final byte Leg = 9;
        public static final byte Arm = 10;
    }
    
    /** Shipyard build type*/
    public static final class Build
    {
        public static final byte NONE = 0;
        public static final byte SHIP = 1;
        public static final byte EQUIP = 2;
        public static final byte SHIP_LOOP = 3;
        public static final byte EQUIP_LOOP = 4;
    }
    
    /** value of Emotion / Emotion4 */
    public static final class Emotion
    {
        public static final byte NORMAL = 0;        //no emotion
        public static final byte BLINK = 1;         //blink eye
        public static final byte T_T = 2;           //sad
        public static final byte O_O = 3;           //stare
        public static final byte BORED = 4;         //bored
        public static final byte HUNGRY = 5;        //no grudge
        public static final byte ANGRY = 6;         //angry
        public static final byte SHY = 7;           //shy
        public static final byte XD = 8;            //happy
    }
    
    /** value of Emotion3 */
    public static final class Emotion3
    {
        public static final byte NORMAL = 0;        //no emotion
        public static final byte CARESS = 1;        //caressed
    }
    
    /** equip raw data array */
    public static final class EquipMisc
    {
        public static final byte EQUIP_TYPE = 0;    //0:none, 1:cannon, 2:misc, 3:aircraft
        public static final byte RARE_TYPE = 1;
        public static final byte RARE_MEAN = 2;
        public static final byte DEVELOP_NUM = 3;
        public static final byte DEVELOP_MAT = 4;
        public static final byte ENCH_TYPE = 5;     //0:none, 1:weapon, 2:armor, 3:misc
    }

    /** Equip type */
    public static final class EquipType
    {
        public static final byte CANNON_SI = 0;          //single cannon
        public static final byte CANNON_TW_LO = 1;       //low level twin cannon
        public static final byte CANNON_TW_HI = 2;       //high level twin cannon
        public static final byte CANNON_TR = 3;          //triple cannon
        public static final byte TORPEDO_LO = 4;         //low level torpedo
        public static final byte TORPEDO_HI = 5;         //high level torpedo
        public static final byte AIR_T_LO = 6;           //low level aircraft T
        public static final byte AIR_T_HI = 7;           //high level aircraft T
        public static final byte AIR_F_LO = 8;           //low level aircraft F
        public static final byte AIR_F_HI = 9;           //high level aircraft F
        public static final byte AIR_B_LO = 10;          //low level aircraft B
        public static final byte AIR_B_HI = 11;          //high level aircraft B
        public static final byte AIR_R_LO = 12;          //low level aircraft R
        public static final byte AIR_R_HI = 13;          //high level aircraft R
        public static final byte RADAR_LO = 14;          //low level radar
        public static final byte RADAR_HI = 15;          //high level radar
        public static final byte TURBINE_LO = 16;        //low level turbine
        public static final byte TURBINE_HI = 17;        //high level turbine
        public static final byte ARMOR_LO = 18;          //low level armor
        public static final byte ARMOR_HI = 19;          //high level armor
        public static final byte GUN_LO = 20;            //low level machine gun
        public static final byte GUN_HI = 21;            //high level machine gun
        public static final byte CATAPULT_LO = 22;       //low level catapult
        public static final byte CATAPULT_HI = 23;       //high level catapult
        public static final byte DRUM_LO = 24;           //low level drum
        public static final byte COMPASS_LO = 25;        //low level compass
        public static final byte FLARE_LO = 26;          //low level flare
        public static final byte SEARCHLIGHT_LO = 27;    //low level searchlight
        public static final byte AMMO_LO = 28;           //low level ammo
        public static final byte AMMO_HI = 29;           //high level ammo
    }
    
    /** Equip Sub ID = item meta value
     * 
     *  EquipID = EquipType + EquipSubID * 100
     * 
     */
    public static final class EquipSubID
    {
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
        public static final byte CANNON_CG_5 = 12;
        public static final byte CANNON_TWIN_8 = 13;
        public static final byte CANNON_QUAD_15 = 14;
        public static final byte CANNON_TRI_12 = 15;
        //torpedo
        public static final byte TORPEDO_21MK1 = 0;
        public static final byte TORPEDO_21MK2 = 1;
        public static final byte TORPEDO_22MK1 = 2;
        public static final byte TORPEDO_CUTTLEFISH = 3;
        public static final byte TORPEDO_HIGHSPEED = 4;
        public static final byte TORPEDO_HIGHSPEED2 = 5;
        public static final byte TORPEDO_AMB = 6;
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
        public static final byte AIRCRAFT_FHELLCATB = 18;
        public static final byte AIRCRAFT_BLAND = 19;
        public static final byte AIRCRAFT_BLANDA = 20;
        public static final byte AIRCRAFT_FBC = 21;
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
        public static final byte TURBINE_GE = 3;
        public static final byte TURBINE_GENEW = 4;
        //armor
        public static final byte ARMOR = 0;
        public static final byte ARMOR_ENH = 1;
        public static final byte ARMOR_ATBS = 2;
        public static final byte ARMOR_ATBM = 3;
        public static final byte ARMOR_ATBL = 4;
        public static final byte ARMOR_ATBA = 5;
        public static final byte ARMOR_APB = 6;
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
        public static final byte DRUM = 0;      //item drum
        public static final byte DRUM_F = 1;    //fluid drum
        public static final byte DRUM_E = 2;    //EU drum
        //compass
        public static final byte COMPASS = 0;
        //flare
        public static final byte FLARE = 0;
        //searchlight
        public static final byte SEARCHLIGHT = 0;
        //ammo
        public static final byte AMMO_T91 = 0;
        public static final byte AMMO_T1 = 1;
        public static final byte AMMO_AA = 2;
        public static final byte AMMO_T3 = 3;
        public static final byte AMMO_DU = 4;
        public static final byte AMMO_G = 5;
        public static final byte AMMO_AG = 6;
        public static final byte AMMO_E = 7;
        public static final byte AMMO_C = 8;
    }
    
    /** GUI ID */
    public static final class Gui
    {
        public static final int SHIPINVENTORY = 0;
        public static final int SMALLSHIPYARD = 1;
        public static final int LARGESHIPYARD = 2;
        public static final int ADMIRALDESK = 3;
        public static final int ADMIRALDESK_ITEM = 4;
        public static final int FORMATION = 5;
        public static final int CRANE = 6;
        public static final int VOLCORE = 7;
        public static final int RECIPE = 8;
        public static final int MORPHINVENTORY = 9;
    }
    
    /** icon id for book GUI */
    public static final class Icon
    {
        public static final short IronIG = 0;
        public static final short Grudge = 1;
        public static final short GrudgeB = 2;
        public static final short GrudgeBH = 3;
        public static final short AbyssIG = 4;
        public static final short AbyssB = 5;
        public static final short PolymIG = 6;
        public static final short PolymB = 7;
        public static final short PolymBG = 8;
        public static final short PolymOre = 9;
        public static final short Gunpowder = 10;
        public static final short Blazepowder = 11;
        public static final short AmmoL = 12;
        public static final short AmmoLC = 13;
        public static final short AmmoH = 14;
        public static final short AmmoHC = 15;
        public static final short RpBucket = 16;
        public static final short LaBucket = 17;
        public static final short NStar = 18;
        public static final short Ring = 19;
        public static final short Paper = 20;
        public static final short OwnPaper = 21;
        public static final short Stick = 22;
        public static final short KHammer = 23;
        public static final short ModTool = 24;
        public static final short SpawnEgg0 = 25;
        public static final short SpawnEgg1 = 26;
        public static final short SpawnEgg2 = 27;
        public static final short InstantMat = 28;
        public static final short DiamondB = 29;
        public static final short RpGod = 30;
        public static final short Pointer = 31;
        public static final short ModelZF = 32;
        public static final short Desk = 33;
        public static final short ObsidianB = 34;
        public static final short WoolB = 35;
        public static final short SmallSY = 36;
        public static final short EqCannon0 = 37;
        public static final short EqCannon1 = 38;
        public static final short EqCannon2 = 39;
        public static final short EqMGun = 40;
        public static final short EqCatap = 41;
        public static final short EqRadar = 42;
        public static final short EqTurbine = 43;
        public static final short EqTorpedo = 44;
        public static final short EqAirT = 45;
        public static final short EqAirF = 46;
        public static final short EqAirB = 47;
        public static final short EqAirR = 48;
        public static final short EqArmor = 49;
        public static final short DeskBook = 50;
        public static final short DeskRadar = 51;
        public static final short WriteBook = 52;
        public static final short Compass = 53;
        public static final short Wrench = 54;
        public static final short VolCore = 55;
        public static final short VolBlock = 56;
        public static final short EqDrum = 57;
        public static final short EqCompass = 58;
        public static final short EqFlare = 59;
        public static final short EqSearchlight = 60;
        public static final short Frame = 61;
        public static final short Waypoint = 62;
        public static final short Crane = 63;
        public static final short Piston = 64;
        public static final short TrainBook = 65;
        public static final short MagmaB = 66;
        public static final short Tank0 = 67;
        public static final short Tank1 = 68;
        public static final short Tank2 = 69;
        public static final short Tank3 = 70;
        public static final short Cauldron = 71;
        public static final short Lapis = 72;
        public static final short RecipePaper = 73;
        public static final short OPTool = 74;
        public static final short AmmoEnchant = 75;
        public static final short Potions = 76;
        public static final short GrudgeXP = 77;
        public static final short GrudgeXPB = 78;
        public static final short XPBot = 79;
    }
    
    /** inventory name for NBT tags */
    public static final class InvName
    {
        public static final String ItemSlot = "CpInv";
        public static final String HandSlot = "HandInv";
        public static final String EquipSlot = "EquipInv";
    }
    
    /** value of hp state */
    public static final class HPState
    {
        public static final byte NORMAL = 0;    //正常
        public static final byte MINOR = 1;     //小破
        public static final byte MODERATE = 2;  //中破
        public static final byte HEAVY = 3;     //大破
    }
    
    /** state keys for StateHandler */
    public static final class Keys
    {
        //AI setting
        public static final short CanFloatUp = 100;          
        public static final short CanDropItem = 101;        //SERVER: no sync
        public static final short CanFollow = 102;
        public static final short CanOnSightChase = 103;
        public static final short CanAtkLight = 104;
        public static final short CanAtkHeavy = 105;
        public static final short CanAtkAirLight = 106;
        public static final short CanAtkAirHeavy = 107;
        public static final short UseMelee = 108;
        public static final short UseAtkLight = 109;
        public static final short UseAtkHeavy = 110;
        public static final short UseAtkAirLight = 111;
        public static final short UseAtkAirHeavy = 112;
        public static final short Phase = 113;              //skill phase
        public static final short CanRingEffect = 114;
        public static final short UseRingEffect = 115;
        public static final short CanPickItem = 116;        //can pick item
        public static final short UsePickItem = 117;        //active picking item
        public static final short UseAutoPump = 118;        //active auto pump
        public static final short FollowMin = 119;          //follow range min/max
        public static final short FollowMax = 120;    
        public static final short FleeHP = 121;             //flee hp%
        public static final short CombatRationLevel = 122;  //morale level of auto using combat ration
        public static final short GuardType = 123;          //guard type: 0:move 1:move & attack
        public static final short GuardX = 124;             //guard xyz pos
        public static final short GuardY = 125;
        public static final short GuardZ = 126;
        public static final short GuardDim = 127;           //guard entity world id
        public static final short GuardID = 128;            //guard entity id
        public static final short DamageType = 129;         //damage type
        public static final short FormatType = 130;         //formation type
        public static final short FormatPos = 131;          //formation position
        public static final short ShipDepth = 132;          //height below water block (double)
        public static final short Task = 133;               //doing task id
        public static final short TaskSide = 134;           //side setting for task
        public static final short TaskTime = 135;           //SERVER: task start time
        public static final short CraneState = 136;         //crane state: 0:none 1:wait 2:craning
        public static final short CraneTime = 137;          //BOTH:   craning time
        public static final short CraneDelay = 138;         //SERVER: crane state changing delay
        public static final short WpStay = 139;             //waypoint stay setting
        public static final short WpStayTime = 140;         //SERVER: waypoint stay timer
        public static final short IsMarried = 141;
        public static final short IsNoFuel = 142;
        public static final short IsHeadTilt = 143;          //CLIENT: no sync
        public static final short IsPVPFirst = 144;
        public static final short IsAntiAir = 145;
        public static final short IsAntiSS = 146;
        public static final short IsPassive = 147;
        public static final short IsTimeKeeper = 148;
        public static final short EquipType = 149;
        
        //model and emotion
        public static final short ShowHeldItem = 300;
        public static final short ModelStateNum = 301;      //total model state number
        public static final short ModelState = 302;         //model state (1 bit for 1 state; if has mounts, first bit = mounts bit)
        public static final short Emotion = 303;            //emotion, for face emotion
        public static final short Emotion2 = 304;           //emotion 2, for head tilt
        public static final short Emotion3 = 305;           //emotion 3, for caress reaction
        public static final short Emotion4 = 306;           //emotion 4, for the other pose emotion
        public static final short HPState = 307;            //hp state
        public static final short TimeEmotion3 = 308;       //SERVER: emotion 3 tick
        public static final short TimeSound = 309;          //SERVER: sound event cooldown
        public static final short TimeFace = 310;           //CLIENT: face emotion time
        public static final short TimeHeadTilt = 311;       //CLIENT: head tilt time
        public static final short EmoteDelay = 312;         //SERVER: emote reaction delay
        
        //number data
        public static final short ShipType = 400;           //ship type
        public static final short ShipClass = 401;          //ship class
        public static final short ShipLevel = 402;          //ship level
        public static final short ShipUID = 403;            //ship UID
        public static final short PlayerUID = 404;          //player UID
        public static final short PlayerEID = 405;          //player entity id
        public static final short Kills = 406;              //ship kill number
        public static final short ExpCurrent = 407;         //exp current level
        public static final short ExpNext = 408;            //exp next level
        public static final short GrudgeNumber = 409;       //grudge number
        public static final short AmmoLightNumber = 410;    //ammo light number
        public static final short AmmoHeavyNumber = 411;    //ammo heavy number
        public static final short AirLightNumber = 412;     //airplane light number
        public static final short AirHeavyNumber = 413;     //airplane heavy number
        public static final short GrudgeConsume = 414;      //grudge base consumption when idle
        public static final short AmmoConsume = 415;        //ammo base consumption
        public static final short Morale = 416;             //morale value
        public static final short Food = 417;               //current food saturation
        public static final short FoodMax = 418;            //max food saturation
        public static final short HitHeight = 419;          //hit height by pointer item
        public static final short HitAngle = 420;           //hit angle by pointer item
        public static final short SensBody = 421;           //sensitive body part id
        public static final short SlotsLevel = 422;         //drum state, ref: EquipDrum.class
        public static final short ChunkLoaderLevel = 423;   //level of chunk loader
        public static final short FlareLevel = 424;         //level of flare
        public static final short SearchlightLevel = 425;   //level of searchlight
        public static final short XP = 426;                 //current xp orb value
        public static final short IconType = 427;           //ship icon in GUI
        
        //timer
        public static final short ImmuneTime = 500;         //SERVER: immune time
        public static final short RevengeTime = 501;        //SERVER: revenge target time
        public static final short LastCombatTime = 502;     //SERVER: last combat time
        public static final short AttackTime = 503;         //CLIENT: attack time for model display
        public static final short AttackTime2 = 504;        //CLIENT: attack time 2 for model display
        public static final short AttackTime3 = 505;        //SERVER: attack time 3 for skill AI
        public static final short MountSkillCD1 = 506;      //player skill1~5 cooldown
        public static final short MountSkillCD2 = 507;
        public static final short MountSkillCD3 = 508;
        public static final short MountSkillCD4 = 509;
        public static final short MountSkillCD5 = 510;
        
        //attrs update flag
        public static final short UpdateAttrsBuffed = 601;
        public static final short UpdateAttrsBonus = 602;
        public static final short UpdateAttrsEquip = 603;
        public static final short UpdateAttrsMorale = 604;
        public static final short UpdateAttrsPotion = 605;
        public static final short UpdateAttrsFormation = 606;
        public static final short UpdateAttrsRaw = 607;
        public static final short UpdateFormatBuff = 608;
        
        //string data
        public static final short OwnerName = 700;
    }
    
    /** morale level */
    public static final class Morale
    {
        /* morale id */
        public static final byte Excited = 0;
        public static final byte Happy = 1;
        public static final byte Normal = 2;
        public static final byte Tired = 3;
        public static final byte Exhausted = 4;
        /* morale level value */
        public static final int L_Excited = 5100;
        public static final int L_Happy = 3900;
        public static final int L_Normal = 2100;
        public static final int L_Tired = 900;
    }
    
    /** packet type ID */
    public static final class Packets
    {
        public static final byte TEST = 0;
        public static final byte S2C_EntitySync = 1;
        public static final byte S2C_Particle = 2;
        public static final byte C2S_GUIInput = 3;
        public static final byte S2C_GUISync = 4;
        public static final byte S2C_PlayerSync = 5;  //no use for now
        public static final byte C2S_CmdInput = 6;
        public static final byte S2C_CmdReact = 7;
    }
    
    /** ring effect, no used for now */
    public static final class R
    {
        public static final byte Haste = 0;
        public static final byte Speed = 1;
        public static final byte Jump = 2;
        public static final byte Damage = 3;
    }
    
    /** ship class id */
    public static final class ShipClass
    {
        public static final short DDI = 0;
        public static final short DDRO = 1;
        public static final short DDHA = 2;
        public static final short DDNI = 3;
        
        public static final short CLHO = 4;
        public static final short CLHE = 5;
        public static final short CLTO = 6;
        public static final short CLTSU = 7;
        
        public static final short CLTCHI = 8;
        public static final short CARI = 9;
        public static final short CANE = 10;
        
        public static final short CVLNU = 11;
        public static final short CVWO = 12;
        
        public static final short BBRU = 13;
        public static final short BBTA = 14;
        public static final short BBRE = 15;
        
        public static final short APWA = 16;
        public static final short SSKA = 17;
        public static final short SSYO = 18;
        public static final short SSSO = 19;
        
        public static final short CVHime = 20;
        public static final short AirfieldHime = 21;
        public static final short ArmoredCVHime = 22;
        public static final short AnchorageHime = 23;
        public static final short HarbourWD = 24;
        public static final short AnchorageWD = 25;
        public static final short BBHime = 26;
        public static final short DDHime = 27;
        public static final short HarbourHime = 28;
        public static final short IsolatedHime = 29;
        public static final short MidwayHime = 30;
        public static final short NorthernHime = 31;
        public static final short SouthernHime = 32;
        public static final short CVWD = 33;
        public static final short CLDemon = 34;
        public static final short BBWD = 35;
        
        public static final short DDShimakaze = 36;
        public static final short BBNagato = 37;
        public static final short SSU511 = 38;
        public static final short SSRo500 = 39;
        
        public static final short STHime = 40;
        public static final short AirdefenseHime = 41;
        public static final short APMamiya = 42;
        public static final short CLHime = 43;
        public static final short SSHime = 44;
        public static final short DDWD = 45;
        
        public static final short BBYamato = 46;
        public static final short CVKaga = 47;
        public static final short CVAkagi = 48;
        
        public static final short CAHime = 49;
        public static final short SupplyDepotHime = 50;
        
        public static final short DDAkatsuki = 51;
        public static final short DDHibiki = 52;
        public static final short DDIkazuchi = 53;
        public static final short DDInazuma = 54;
        public static final short Raiden = 55;
        
        public static final short CLTenryuu = 56;
        public static final short CLTatsuta = 57;
        public static final short CAAtago = 58;
        public static final short CATakao = 59;
        
        public static final short BBKongou = 60;
        public static final short BBHiei = 61;
        public static final short BBHaruna = 62;
        public static final short BBKirishima = 63;
        
        public static final short DDNA = 64;
        public static final short DDAH = 65;
        public static final short STWH = 66;
        public static final short NorthernWH = 67;
        public static final short JellyfishHime = 68;
        public static final short EscortHime = 69;
        public static final short EuropeanHime = 70;
        public static final short CentralHime = 71;
        public static final short SSNH = 72;
        public static final short FrenchHime = 73;
        public static final short NightStraitHime = 74;
        public static final short EntombedAAHime = 75;
        public static final short NorthlandHime = 76;
        public static final short SupplyDepotSH = 77;
        public static final short SSSH = 78;
        public static final short BBSH = 79;
        public static final short CASH = 80;
        public static final short CVSH = 81;
        public static final short HarbourSH = 82;
        public static final short LycorisHime = 83;
        public static final short TwinHime = 84;
    }
    
    /**
     * SHIP REGISTER NAME MAP
     * index by {@link ID.ShipClass}
     */
    public static final Map<Short, String> NameMap = Collections.unmodifiableMap(new HashMap<Short, String>()
    {{
        put(ID.ShipClass.DDI, "EntityDestroyerI");
        put(ID.ShipClass.DDRO, "EntityDestroyerRo");
        put(ID.ShipClass.DDHA, "EntityDestroyerHa");
        put(ID.ShipClass.DDNI, "EntityDestroyerNi");
        put(ID.ShipClass.DDNA, "EntityDestroyerNa");
        
        put(ID.ShipClass.CLHO, "EntityLightCruiserHo");
        put(ID.ShipClass.CLHE, "EntityLightCruiserHe");
        put(ID.ShipClass.CLTO, "EntityLightCruiserTo");
        put(ID.ShipClass.CLTSU, "EntityLightCruiserTsu");
        put(ID.ShipClass.CLTCHI, "EntityLightCruiserChi");
        put(ID.ShipClass.CARI, "EntityHeavyCruiserRi");
        put(ID.ShipClass.CANE, "EntityHeavyCruiserNe");
        
        put(ID.ShipClass.CVLNU, "EntityCarrierNu");
        put(ID.ShipClass.CVWO, "EntityCarrierWo");
        
        put(ID.ShipClass.BBRU, "EntityBattleshipRu");
        put(ID.ShipClass.BBTA, "EntityBattleshipTa");
        put(ID.ShipClass.BBRE, "EntityBattleshipRe");
        
        put(ID.ShipClass.APWA, "EntityTransportWa");
        put(ID.ShipClass.SSKA, "EntitySubmKa");
        put(ID.ShipClass.SSYO, "EntitySubmYo");
        put(ID.ShipClass.SSSO, "EntitySubmSo");
        
        put(ID.ShipClass.CLDemon, "EntityLightCruiserDemon");
        
        put(ID.ShipClass.AirdefenseHime, "EntityAirdefenseHime");
        put(ID.ShipClass.DDHime, "EntityDestroyerHime");
        put(ID.ShipClass.EntombedAAHime, "EntityEntombedAAHime");
        put(ID.ShipClass.DDAH, "EntityDestroyerAH");
        put(ID.ShipClass.DDWD, "EntityDestroyerWD");
        
        put(ID.ShipClass.CLHime, "EntityCLHime");
        put(ID.ShipClass.CAHime, "EntityCAHime");
        put(ID.ShipClass.CASH, "EntityCASH");
        
        put(ID.ShipClass.ArmoredCVHime, "EntityArmoredCarrierHime");
        put(ID.ShipClass.CVHime, "EntityCarrierHime");
        put(ID.ShipClass.JellyfishHime, "EntityJellyfishHime");
        put(ID.ShipClass.STHime, "EntitySeaplaneHime");
        put(ID.ShipClass.EscortHime, "EntityEscortHime");
        put(ID.ShipClass.CVSH, "EntityCarrierSH");
        put(ID.ShipClass.CVWD, "EntityCarrierWD");
        put(ID.ShipClass.STWH, "EntitySeaplaneWH");
        
        put(ID.ShipClass.AnchorageHime, "EntityAnchorageHime");
        put(ID.ShipClass.AirfieldHime, "EntityAirfieldHime");
        put(ID.ShipClass.HarbourHime, "EntityHarbourHime");
        put(ID.ShipClass.IsolatedHime, "EntityIsolatedHime");
        put(ID.ShipClass.MidwayHime, "EntityMidwayHime");
        put(ID.ShipClass.NorthernHime, "EntityNorthernHime");
        put(ID.ShipClass.SouthernHime, "EntitySouthernHime");
        put(ID.ShipClass.SupplyDepotHime, "EntitySDHime");
        put(ID.ShipClass.CentralHime, "EntityCentralHime");
        put(ID.ShipClass.NorthlandHime, "EntityNorthlandHime");
        put(ID.ShipClass.LycorisHime, "EntityLycorisHime");
        put(ID.ShipClass.SupplyDepotSH, "EntitySDSH");
        put(ID.ShipClass.HarbourSH, "EntityHarbourSH");
        put(ID.ShipClass.AnchorageWD, "EntityAnchorageWD");
        put(ID.ShipClass.HarbourWD, "EntityHarbourWD");
        put(ID.ShipClass.NorthernWH, "EntityNorthernWH");
        
        put(ID.ShipClass.BBHime, "EntityBattleshipHime");
        put(ID.ShipClass.EuropeanHime, "EntityEuropeanHime");
        put(ID.ShipClass.FrenchHime, "EntityFrenchHime");
        put(ID.ShipClass.NightStraitHime, "EntityNightStraitHime");
        put(ID.ShipClass.TwinHime, "EntityTwinHime");
        put(ID.ShipClass.BBSH, "EntityBattleshipSH");
        put(ID.ShipClass.BBWD, "EntityBattleshipWD");
        
        put(ID.ShipClass.SSHime, "EntitySubmHime");
        put(ID.ShipClass.SSNH, "EntitySubmNewHime");
        put(ID.ShipClass.SSSH, "EntitySubmSH");
        
        put(ID.ShipClass.DDShimakaze, "EntityDestroyerShimakaze");
        put((short)(ID.ShipClass.DDShimakaze+2000), "EntityDestroyerShimakazeMob");
        put(ID.ShipClass.DDAkatsuki, "EntityDestroyerAkatsuki");
        put((short)(ID.ShipClass.DDAkatsuki+2000), "EntityDestroyerAkatsukiMob");
        put(ID.ShipClass.DDHibiki, "EntityDestroyerHibiki");
        put((short)(ID.ShipClass.DDHibiki+2000), "EntityDestroyerHibikiMob");
        put(ID.ShipClass.DDIkazuchi, "EntityDestroyerIkazuchi");
        put((short)(ID.ShipClass.DDIkazuchi+2000), "EntityDestroyerIkazuchiMob");
        put(ID.ShipClass.DDInazuma, "EntityDestroyerInazuma");
        put((short)(ID.ShipClass.DDInazuma+2000), "EntityDestroyerInazumaMob");
        put(ID.ShipClass.Raiden, "Entity");
        
        put(ID.ShipClass.CLTenryuu, "EntityCruiserTenryuu");
        put((short)(ID.ShipClass.CLTenryuu+2000), "EntityCruiserTenryuuMob");
        put(ID.ShipClass.CLTatsuta, "EntityCruiserTatsuta");
        put((short)(ID.ShipClass.CLTatsuta+2000), "EntityCruiserTatsutaMob");
        put(ID.ShipClass.CAAtago, "EntityCruiserAtago");
        put((short)(ID.ShipClass.CAAtago+2000), "EntityCruiserAtagoMob");
        put(ID.ShipClass.CATakao, "EntityCruiserTakao");
        put((short)(ID.ShipClass.CATakao+2000), "EntityCruiserTakaoMob");
        
        put(ID.ShipClass.CVKaga, "EntityCarrierKaga");
        put((short)(ID.ShipClass.CVKaga+2000), "EntityCarrierKagaMob");
        put(ID.ShipClass.CVAkagi, "EntityCarrierAkagi");
        put((short)(ID.ShipClass.CVAkagi+2000), "EntityCarrierAkagiMob");
        
        put(ID.ShipClass.BBNagato, "EntityBattleshipNGT");
        put((short)(ID.ShipClass.BBNagato+2000), "EntityBattleshipNGTMob");
        put(ID.ShipClass.BBYamato, "EntityBattleshipYMT");
        put((short)(ID.ShipClass.BBYamato+2000), "EntityBattleshipYMTMob");
        put(ID.ShipClass.BBKongou, "EntityBattleshipKongou");
        put((short)(ID.ShipClass.BBKongou+2000), "EntityBattleshipKongouMob");
        put(ID.ShipClass.BBHiei, "EntityBattleshipHiei");
        put((short)(ID.ShipClass.BBHiei+2000), "EntityBattleshipHieiMob");
        put(ID.ShipClass.BBHaruna, "EntityBattleshipHaruna");
        put((short)(ID.ShipClass.BBHaruna+2000), "EntityBattleshipHarunaMob");
        put(ID.ShipClass.BBKirishima, "EntityBattleshipKirishima");
        put((short)(ID.ShipClass.BBKirishima+2000), "EntityBattleshipKirishimaMob");
        
        put(ID.ShipClass.SSU511, "EntitySubmU511");
        put((short)(ID.ShipClass.SSU511+2000), "EntitySubmU511Mob");
        put(ID.ShipClass.SSRo500, "EntitySubmRo500");
        put((short)(ID.ShipClass.SSRo500+2000), "EntitySubmRo500Mob");
        
        put(ID.ShipClass.APMamiya, "EntityAPMamiya");
        put((short)(ID.ShipClass.APMamiya+2000), "EntityAPMamiyaMob");
    }});
    
    /** ship summons entity name */
    public static final class NameMinions
    {
        //mounts
        public static final String MountAfH = "EntityMountAfH";
        public static final String MountBaH = "EntityMountBaH";
        public static final String MountCaH = "EntityMountCaH";
        public static final String MountCaWD = "EntityMountCaWD";
        public static final String MountHbH = "EntityMountHbH";
        public static final String MountIsH = "EntityMountIsH";
        public static final String MountMiH = "EntityMountMiH";
        public static final String MountSuH = "EntityMountSuH";
        //projectile
        public static final String AbyssMissile = "EntityAbyssMissile";
        public static final String ProjectileBeam = "EntityProjectileBeam";
        public static final String ProjectileStatic = "EntityProjectileStatic";
        public static final String ShipFishingHook = "EntityShipFishingHook";
        //summons
        public static final String Rensouhou = "EntityRensouhou";
        public static final String RensouhouMob = "EntityRensouhouMob";
        public static final String RensouhouS = "EntityRensouhouS";
        public static final String Airplane = "EntityAirplane";
        public static final String AirplaneTakoyaki = "EntityAirplaneTakoyaki";
        public static final String AirplaneT = "EntityAirplaneT";
        public static final String AirplaneZero = "EntityAirplaneZero";
        public static final String AirplaneTMob = "EntityAirplaneTMob";
        public static final String AirplaneZeroMob = "EntityAirplaneZeroMob";
        public static final String FloatingFort = "EntityFloatingFort";
        //item entity
        public static final String BasicEntityItem = "BasicEntityItem";
    }
    
    /** ship misc entity id for renderer */
    public static final class ShipMisc
    {
        public static final short Invisible = -1;
        public static final short AbyssalMissile = 0;
        public static final short Airplane = 1;
        public static final short AirplaneT = 2;
        public static final short AirplaneTako = 3;
        public static final short AirplaneZero = 4;
        public static final short FloatingFort = 5;
        public static final short Rensouhou = 6;
        public static final short RensouhouS = 7;
        public static final short AirfieldMount = 8;
        public static final short BattleshipMount = 9;
        public static final short CarrierWDMount = 10;
        public static final short HarbourMount = 11;
        public static final short CarrierMount = 12;
        public static final short IsloatedMount = 13;
        public static final short MidwayMount = 14;
        public static final short SubmMount = 15;
    }
    
    /** ship type for damage calc */
    public static final class ShipDmgType
    {
        public static final byte UNDEFINED = 0;           //未定義
        public static final byte CARRIER = 1;             //航母
        public static final byte AVIATION = 2;            //航戰
        public static final byte BATTLESHIP    = 3;       //戰艦
        public static final byte CRUISER = 4;             //巡洋
        public static final byte DESTROYER = 5;           //驅逐
        public static final byte SUBMARINE = 6;           //潛艇
        public static final byte AIRPLANE = 7;            //飛機+魚雷
    }
    
    /** ship type for GUI display */
    public static final class IconType
    {
        public static final byte DESTROYER = 0;           //DD
        public static final byte LIGHT_CRUISER = 1;       //CL
        public static final byte HEAVY_CRUISER = 2;       //CA CAV
        public static final byte TORPEDO_CRUISER = 3;     //CLT
        public static final byte LIGHT_CARRIER = 4;       //CVL
        public static final byte STANDARD_CARRIER = 5;    //CV
        public static final byte BATTLESHIP    = 6;       //BB BBV
        public static final byte TRANSPORT = 7;           //AR AO
        public static final byte SUBMARINE = 8;           //SS
        public static final byte DEMON = 9;               //demon + water demon (De)
        public static final byte HIME = 10;               //princess (Pr)
    }
    
    /** ship type for calculation */
    public static final class ShipType
    {
        public static final byte DD = 0;                  //destroyer
        public static final byte CL = 1;                  //light cruiser
        public static final byte CA = 2;                  //heavy cruiser
        public static final byte CAV = 3;                 //carrier cruiser
        public static final byte CLT = 4;                 //torpedo cruiser
        public static final byte CVL = 5;                 //light carrier
        public static final byte CV = 6;                  //carrier
        public static final byte BB = 7;                  //battleship
        public static final byte BBV = 8;                 //aviation battleship
        public static final byte SS = 9;                  //submarine
        public static final byte AP = 10;                 //transport
    }
    
    
}