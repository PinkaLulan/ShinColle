package com.lulan.shincolle.handler;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModSounds;

import net.minecraft.util.SoundEvent;

public class SoundHandler
{
    
    
    
    
    
    
    
    /**
     * type: 0:idle, 1:hit, 2:hurt, 3:dead, 4:marry, 5:knockback, 6:item, 7:feed, 10~33:timekeep
     */
    @Nullable
    public static SoundEvent getCustomSound(int type, BasicEntityShip ship)
    {
        //get custom sound rate
        int key = ship.getShipClass() + 2;
        float[] rate = ConfigHandler.configSound.SOUNDRATE.get(key);
        int typeKey = key * 100 + type;
        int typeTemp = type;
        
        //if timekeeping sound
        if (type >= 10 && type <= 33)
        {
            type = 8;
        }
        
        //has custom sound
        if (rate != null && rate[type] > 0.01F)
        {
            SoundEvent sound = ModSounds.CUSTOM_SOUND.get(typeKey);
            
            if (sound != null && ship.rand.nextFloat() < rate[type])
            {
                return sound;
            }
        }
        
        //no custom sound, return default sound
        switch (typeTemp)
        {
        case 0:
            return ModSounds.SHIP_IDLE;
        case 1:
            return ModSounds.SHIP_HIT;
        case 2:
            return ModSounds.SHIP_HURT;
        case 3:
            return ModSounds.SHIP_DEATH;
        case 4:
            return ModSounds.SHIP_MARRY;
        case 5:
            return ModSounds.SHIP_KNOCKBACK;
        case 6:
            return ModSounds.SHIP_ITEM;
        case 7:
            return ModSounds.SHIP_FEED;
        case 10:
            return ModSounds.SHIP_TIME0;
        case 11:
            return ModSounds.SHIP_TIME1;
        case 12:
            return ModSounds.SHIP_TIME2;
        case 13:
            return ModSounds.SHIP_TIME3;
        case 14:
            return ModSounds.SHIP_TIME4;
        case 15:
            return ModSounds.SHIP_TIME5;
        case 16:
            return ModSounds.SHIP_TIME6;
        case 17:
            return ModSounds.SHIP_TIME7;
        case 18:
            return ModSounds.SHIP_TIME8;
        case 19:
            return ModSounds.SHIP_TIME9;
        case 20:
            return ModSounds.SHIP_TIME10;
        case 21:
            return ModSounds.SHIP_TIME11;
        case 22:
            return ModSounds.SHIP_TIME12;
        case 23:
            return ModSounds.SHIP_TIME13;
        case 24:
            return ModSounds.SHIP_TIME14;
        case 25:
            return ModSounds.SHIP_TIME15;
        case 26:
            return ModSounds.SHIP_TIME16;
        case 27:
            return ModSounds.SHIP_TIME17;
        case 28:
            return ModSounds.SHIP_TIME18;
        case 29:
            return ModSounds.SHIP_TIME19;
        case 30:
            return ModSounds.SHIP_TIME20;
        case 31:
            return ModSounds.SHIP_TIME21;
        case 32:
            return ModSounds.SHIP_TIME22;
        case 33:
            return ModSounds.SHIP_TIME23;
        }
        
        return null;
    }
    
    //timekeeping method
    protected void playTimeSound(int hour)
    {
        SoundEvent sound = this.getCustomSound(hour + 10, this);

        //play sound
        if (sound != null)
        {
            this.playSound(sound, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
        }
    }
    
}