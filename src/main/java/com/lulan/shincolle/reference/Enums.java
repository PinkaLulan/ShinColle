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
    
}