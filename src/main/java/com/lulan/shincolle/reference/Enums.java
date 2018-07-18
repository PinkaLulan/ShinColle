package com.lulan.shincolle.reference;

public class Enums
{
    
    /**
     * body ID
     */
    public static enum BodyHeight
    {
        TOP,
        HEAD,
        NECK,
        CHEST,
        BELLY,
        UBELLY,
        LEG
    }
    
    /**
     * body side
     */
    public static enum BodySide
    {
        LEFT,
        FRONT,
        RIGHT,
        BACK
    }
    
    /**
     * color enum for gui
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
        PURPLE(8388863),
        GRAY_MIDDLE(4210752),
        GRAY_DARK_HP(16119285),
        YELLOW_DARK_HP(13421568),
        ORANGE_DARK_HP(16747520),
        RED_DARK_HP(13107200),
        GREEN(65344),
        GREEN_DARK(36352),
        BLUE(255),
        BLUE_LIGHT(7829503),
        BLUE_LIGHT2(40703),
        BLUE_DARK(128);
        
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
    
    /**
     * path type for path finding
     */
    public static enum EnumPathType
    {
        BLOCKED,
        OPEN,
        FLUID,
        OPENABLE,    //gate, wood door, trap door...
        FENCE
        
    }//end path type
    
    /**
     * equip special effect for BasicEquip.getSpecialEffect
     */
    public static enum EnumEquipEffectSP
    {
        NONE,
        DRUM,
        DRUM_LIQUID,
        DRUM_EU,
        COMPASS,
        FLARE,
        SEARCHLIGHT
    }//end sp effect
    
    /**
     * action type for morale, ammo, grudge consume
     */
    public static enum ActType
    {
        //general
        IDLE,
        MOVE,
        PLAY,
        //task
        TASK_COOKING,
        TASK_CRAFTING,
        TASK_FISHING,
        TASK_MINING,
        //ai
        AI_PICKITEM,
        //battle
        ATTACK_MELEE,
        ATTACK_LIGHT,
        ATTACK_HEAVY,
        ATTACK_AIR_LIGHT,
        ATTACK_AIR_HEAVY,
        DAMAGED
    }
    
    /**
     * ammo type
     */
    public static enum Ammo
    {
        LIGHT,
        HEAVY
    }
    
    /**
     * attack type
     */
    public static enum AtkType
    {
        GENERIC_MELEE,
        GENERIC_LIGHT,
        GENERIC_HEAVY,
        GENERIC_AIR_LIGHT,
        GENERIC_AIR_HEAVY,
        YAMATO_CANNON,
        AP91_FIST
    }
    
    /**
     * moving type
     */
    public static enum MoveType
    {
        LAND,
        FLY,
        SEA,
        UNDERSEA
    }
    
    /**
     * sound type
     */
    public static enum SoundType
    {
        IDLE,
        HIT,
        HURT,
        DEAD,
        MARRY,
        KNOCKBACK,
        PICKITEM,
        FEED,
        TIMEKEEP00,
        TIMEKEEP01,
        TIMEKEEP02,
        TIMEKEEP03,
        TIMEKEEP04,
        TIMEKEEP05,
        TIMEKEEP06,
        TIMEKEEP07,
        TIMEKEEP08,
        TIMEKEEP09,
        TIMEKEEP10,
        TIMEKEEP11,
        TIMEKEEP12,
        TIMEKEEP13,
        TIMEKEEP14,
        TIMEKEEP15,
        TIMEKEEP16,
        TIMEKEEP17,
        TIMEKEEP18,
        TIMEKEEP19,
        TIMEKEEP20,
        TIMEKEEP21,
        TIMEKEEP22,
        TIMEKEEP23
    }
    
    /**
     * key for boolean attrs
     */
    public static enum AttrBoo
    {
        CanFloatUp,          
        CanDropItem,        //SERVER: no sync
        CanFollow,
        CanOnSightChase,
        CanAtkLight,
        CanAtkHeavy,
        CanAtkAirLight,
        CanAtkAirHeavy,
        UseMelee,
        UseAtkLight,
        UseAtkHeavy,
        UseAtkAirLight,
        UseAtkAirHeavy,
        CanRingEffect,
        UseRingEffect,
        CanPickItem,        //can pick item
        UsePickItem,        //active picking item
        UseAutoPump,        //active auto pump
        IsMarried,
        IsNoFuel,
        IsHeadTilt,         //CLIENT: no sync
        IsPVPFirst,
        IsAntiAir,
        IsAntiSS,
        IsPassive,
        IsTimeKeeper,
        ShowHeldItem,
        
        //attrs update flag
        UpdateAttrsBuffed,
        UpdateAttrsBonus,
        UpdateAttrsEquip,
        UpdateAttrsMorale,
        UpdateAttrsPotion,
        UpdateAttrsFormation,
        UpdateAttrsRaw,
        UpdateFormatBuff
    }
    
    /**
     * key for number attrs
     */
    public static enum AttrNum
    {
        Phase,              //skill phase
        FollowMin,          //follow range min/max
        FollowMax,    
        FleeHP,             //flee hp%
        CombatRationLevel,  //morale level of auto using combat ration
        GuardType,          //guard type: 0:move 1:move & attack
        GuardX,             //guard xyz pos
        GuardY,
        GuardZ,
        GuardDim,           //guard entity world id
        GuardID,            //guard entity id
        DamageType,         //damage type
        FormatType,         //formation type
        FormatPos,          //formation position
        ShipDepth,          //height below water block (double)
        Task,               //doing task id
        TaskSide,           //side setting for task
        TaskTime,           //SERVER: task start time
        CraneState,         //crane state: 0:none 1:wait 2:craning
        CraneTime,          //BOTH:   craning time
        CraneDelay,         //SERVER: crane state changing delay
        WpStay,             //waypoint stay setting
        WpStayTime,         //SERVER: waypoint stay timer
        EquipType,
        
        //model and emotion
        ModelStateNum,      //total model state number
        ModelState,         //model state (1 bit for 1 state; if has mounts, first bit = mounts bit)
        Emotion,            //emotion, for face emotion
        Emotion2,           //emotion 2, for head tilt
        Emotion3,           //emotion 3, for caress reaction
        Emotion4,           //emotion 4, for the other pose emotion
        HPState,            //hp state
        TimeEmotion3,       //SERVER: emotion 3 tick
        TimeSound,          //SERVER: sound event cooldown
        TimeFace,           //CLIENT: face emotion time
        TimeHeadTilt,       //CLIENT: head tilt time
        EmoteDelay,         //SERVER: emote reaction delay
        Scale,              //BOTH: model size level
        
        //number data
        ShipType,           //ship type
        ShipClass,          //ship class
        ShipLevel,          //ship level
        ShipUID,            //ship UID
        PlayerUID,          //player UID
        PlayerEID,          //player entity id
        Kills,              //ship kill number
        ExpCurrent,         //exp current level
        ExpNext,            //exp next level
        GrudgeNumber,       //grudge number
        AmmoLightNumber,    //ammo light number
        AmmoHeavyNumber,    //ammo heavy number
        AirLightNumber,     //airplane light number
        AirHeavyNumber,     //airplane heavy number
        GrudgeConsume,      //grudge base consumption when idle
        AmmoConsume,        //ammo base consumption
        Morale,             //morale value
        Food,               //current food saturation
        FoodMax,            //max food saturation
        HitHeight,          //hit height by pointer item
        HitAngle,           //hit angle by pointer item
        SensBody,           //sensitive body part id
        SlotsLevel,         //drum state, ref: EquipDrum.class
        ChunkLoaderLevel,   //level of chunk loader
        FlareLevel,         //level of flare
        SearchlightLevel,   //level of searchlight
        XP,                 //current xp orb value
        IconType,           //ship icon in GUI
        
        //timer
        ImmuneTime,         //SERVER: immune time
        RevengeTime,        //SERVER: revenge target time
        LastCombatTime,     //SERVER: last combat time
        AttackTime,         //CLIENT: attack time for model display
        AttackTime2,        //CLIENT: attack time 2 for model display
        AttackTime3,        //SERVER: attack time 3 for skill AI
        MountSkillCD1,      //player skill1~5 cooldown
        MountSkillCD2,
        MountSkillCD3,
        MountSkillCD4,
        MountSkillCD5
    }
    
    /**
     * key for string attrs
     */
    public static enum AttrStr
    {
        OwnerName
    }
    
    /**
     * particle type enum
     * 
     * particle params:
     *   b0~bN: boolean data in ParticleData
     *   f0~fN: float data
     *   i0~iN: int data
     *   s0~sN: string data
     * 
     */
    public static enum ParType
    {
        /** f0:posX, f1:posY, f2:posZ, f3:scale, i0:type */
        ARROW_BLOCK,
        /** i0:host entity ID, i1:type, f0:scale */
        ARROW_ENTITY,
        /** i0:host entity ID, f0:scale (ent.height * 1.2F), f1:radius (ent.height * 0.75F), f2:beam speed (0.8F), f3:beam thick (0.03F), f4~7:RGBA, f8:height (ent.height * 0.4F) */
        BEAM_IN,
        /** i0:host entity ID, f0:life, f1:scale */
        BEAM_IN_SIMPLE,
        /** i0:host entity ID, f0:scale (ent.height * 1.2F), f1:fade speed (0.85F), f2:beam speed (0.08F), f3:space (8F), f4~7:RGBA, f8:height (0.7F) */
        BEAM_OUT,
        /** f0:posX, f1:posY, f2:posZ */
        BUBBLE,
        /** f0:posX, f1:posY, f2:posZ, f3:max length, f4:scale, i0:type */
        CRANING,
        /** i0:host entity ID, f0:scale */
        CUBE_VIBRATE,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        DRIPWATER,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        DRIPLAVA,
        /** i0:host entity ID, i1:type, f0~2:params */
        DEBUG_PLANE,
        /** i0:host entity ID, f0:height, f1:eyeX, f2:eyeZ, f3~6:RGBA */
        EYE_FIRE,
        /** f0:posX, f1:posY, f2:posZ */
        EXPLOSION_LARGE,
        /** f0:posX, f1:posY, f2:posZ */
        EXPLOSION_HUGE_LAVA,
        /** f0:posX, f1:posY, f2:posZ */
        EXPLOSION_LARGE_LAVA,
        /** f0:posX, f1:posY, f2:posZ, f3:height, i0:host type, i1:emotion type */
        EMOTION_BLOCK,
        /** i0:host entity ID, i1:host type, i2:emotion type, f0:posX, f1:posY, f2:posZ, f3:height */
        EMOTION_ENTITY,
        /** f0:posX, f1:posY, f2:posZ */
        FLAME,
        /** i0:host entity ID, f0:scale (ent.height), f1~4:RGBA */
        GRADIENT_DOUBLE_OUT,
        /** f0:posX, f1:posY, f2:posZ */
        HEARTS,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        HIGHSPEED_THICKYELLOW,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        HIGHSPEED_THICKPINK,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        HIGHSPEED_AURABLUR,
        /** f0:posX, f1:posY, f2:posZ, f3:tarX, f4:tarY, f5:tarZ */
        LASER_RE,
        /** i0:host entity ID, i1:target entity ID, f0:width between 2 laser (0.9F), f1:offsetY, f2:scale (0.05F) */
        LASER_DOUBLE,
        /** i0:host entity ID, i1:target entity ID, f0:vecX, f1:vecY, f2:vecZ, f3:scaleHead (2.5F, boss:5F), f4:scaleBeam (2F, boss:4F) */
        LASER_YAMATO,
        /** i0:host entity ID, i1:type, f0:scale */
        LIGHTNING_ENTITY,
        /** i0:host entity ID, i1:life, i2:type, i3:amount, f0:scale */
        LIGHTNING_STICKY,
        /** i0:host entity ID, i1:life, f0:scale */
        LIGHTNING_SPHERE,
        /** i0:host entity ID, f0:tarX, f1:tarY, f2:tarZ */
        LINE_GUARD_BLOCK,
        /** i0:host entity ID, i1:target entity ID */
        LINE_GUARD_ENTITY,
        /** i0:host entity ID, i1:target entity ID */
        LINE_GUARD_SUPPLY,
        /** i0:host entity ID, i1:target entity ID */
        LINE_GUARD_POS,
        /** i0:host entity ID, i1:target entity ID, f0:offsetY, f1:scaleOUT, f2:scaleIN */
        LINE_CUSTOM,
        /** f0:posX, f1:posY, f2:posZ, f3:width range, f4:motionY */
        SMOKE_S,
        /** f0:posX, f1:posY, f2:posZ, f3:width range, f4:motionY */
        SMOKE_M,
        /** for cannon smoke, f0:posX, f1:posY, f2:posZ, f3:vecX, f4:vecZ */
        SMOKE_L,
        /** f0:posX, f1:posY, f2:posZ, f3:width range, f4:motionY */
        SMOKE_XL,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetX, f4:offsetZ, f5:width between 2 smoke */
        SMOKE_DOUBLE_S,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetX, f4:offsetZ, f5:width between 2 smoke */
        SMOKE_DOUBLE_L,
        /** i0:host entity ID, f0:width between 2 smoke, f1:offsetZ, f2:offsetY, f3:smoke speed (default: 0.25F) */
        SMOKE_DOUBLE_L_CUSTOM,
        /** i0:host entity ID, f0:width between 2 smoke, f1:offsetZ, f2:offsetY, f3:joint2 offsetZ (default: 1.5F) */
        SMOKE_DOUBLE_L_2JOINTS,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SMOKE_CHIMNEY_S,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SMOKE_CHIMNEY_L,
        /** f0:posX, f1:posY, f2:posZ, f3:motionY, f4:scale */
        SMOKE_CHIMNEY_CUSTOM,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_WHITE,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_WHITE_TRANSPARENT,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_CYAN,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_CYAN_LIGHT,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_CYAN_LIGHT_TRANSPARENT,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_CYAN_TRANSPARENT,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_GREEN,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_RED,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_RED_TRANSPARENT,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_YELLOW,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_ORANGE,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_NEXT_WAYPOINT,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_PAIRED_CHEST,
        /** f0:posX, f1:posY, f2:posZ, f3:motionX, f4:motionY, f5:motionZ */
        SPRAY_TRIPLE,
        /** i0:host entity ID, i1:type, f0~N:custom data */
        SPRAY_MISSILE,
        /** f0:posX, f1:posY, f2:posZ, f3:vecX, f4:vecZ */
        SNOWHIT,
        /** f0:posX, f1:posY, f2:posZ, f3:vecX, f4:vecZ */
        SNOWSPRAY,
        /**
         * type: 0:fluid fill, 2:healing, 3:craning, 8:goddess
         * i0:host entity ID, i1:type, f0:scale (type0:0.025F, type2:0.075F, type3:0.05F, type8:0.1F) f1:radius (type0,2:ent.width * 1.5F, type3:ent.width, type8:ent.width * 2F), f2~5:RGBA, f6:height (type0,8:ent.height * 0.4F)
         */
        SPARKLE,
        /** i0:host entity ID, f0:scale1 (ent.height), f1:scale2 (ent.height * 5.6F), f2:scale3 (ent.height * 2F), f3:fade (0.95F), f4:life (4F), f5~8:RGBA */
        SWEEP_VERTICAL,
        /** i0:host entity ID, f0:scale1 (ent.height * 0.1F), f1:scale2 (ent.height * 5.6F), f2:scale3 (ent.height * 6F), f3:fade (0.95F), f4:life (4F), f5~8:RGBA */
        SWEEP_HORIZONTAL,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetY */
        TEXT_MISS,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetY */
        TEXT_CRI,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetY */
        TEXT_DOUBLEHIT,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetY */
        TEXT_TRIPLEHIT,
        /** f0:posX, f1:posY, f2:posZ, f3:offsetY */
        TEXT_DODGE,
        /** i0:NO_USE, i1:type, i2:textH, i3:textW, f0:posX, f1:posY, f2:posZ, f3:scale */
        TEXT_BLOCK,
        /** i0:host entity ID, i1:type, i2:textH, i3:textW, f0:posX, f1:posY, f2:posZ, f3:scale */
        TEXT_ENTITY,
        /** f0:posX, f1:posY, f2:posZ, f3:tarX, f4:tarY, f5:tarZ, f6:text offsetY */
        TYPE91APFIST_ATTACK,
        /** f0:posX, f1:posY, f2:posZ, f3:aura width, f4:offsetY */
        TYPE91APFIST_SHOCKWAVE_IN,
        /** f0:posX, f1:posY, f2:posZ, f3:aura width, f4:offsetY */
        TYPE91APFIST_SHOCKWAVE_OUT,
        /** i0:host entity ID, i1:type, f0:scale */
        TYPE91APFIST_CHI
    }
    
    
}