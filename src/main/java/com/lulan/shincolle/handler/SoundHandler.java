package com.lulan.shincolle.handler;

import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.reference.Enums.AtkType;
import com.lulan.shincolle.reference.Enums.SoundType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

/**
 * handler for sound
 */
public class SoundHandler
{
    
    /** host object */
    protected ISoundEntity host;
    protected Random rand;
    protected float volume, pitch;
    protected SoundCategory category;
    
    
    public SoundHandler(ISoundEntity host, SoundCategory category, float volume, float pitch)
    {
        this.host = host;
        
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
        
        if (this.host instanceof EntityLivingBase)
        {
            this.rand = ((EntityLivingBase) this.host).getRNG();
        }
        else
        {
            this.rand = new Random();
        }
    }
    
    public float getVolume()
    {
        return this.volume;
    }
    
    public float getPitch()
    {
        return this.pitch;
    }
    
    /** @see #playSound(ISoundEntity, SoundEvent, SoundCategory, float, float) */
    public void playSound(SoundEvent sound)
    {
        this.playSound(sound, this.getVolume(), this.getPitch());
    }
    
    /** @see #playSound(SoundEvent, volume, pitch) */
    public void playSound(SoundEvent sound, float volume, float pitch)
    {
        this.playSound(this.host, sound, this.category, volume, pitch);
    }
    
    /** play sound with host type checking at SERVER SIDE */
    public static void playSound(ISoundEntity host, SoundEvent sound, SoundCategory category, float volume, float pitch)
    {
        if (host instanceof Entity)
        {
            ((Entity) host).playSound(sound, volume, pitch);
        }
        else if (host instanceof TileEntity)
        {
            if (((TileEntity) host).hasWorld())
            {
                BlockPos pos = ((TileEntity) host).getPos();
                ((TileEntity) host).getWorld().playSound(null,
                        pos.getX(), pos.getY(), pos.getZ(),
                        sound, category, volume, pitch);
            }
        }
    }
    
    /** get sound by type */
    @Nullable
    public SoundEvent getSound(SoundType type)
    {
        if (this.host instanceof BasicEntityShip ||
            this.host instanceof BasicEntityShipHostile)
        {
            return getSound(type, (IStateEntityAdv) this.host);
        }
    }
    
    /** get sound by type */
    @Nullable
    public static SoundEvent getSound(SoundType type, IStateEntityAdv ship)
    {
        //get custom sound rate
        int key = ship.getStateHandler().getAttrClass() + 2;
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
    
    /** play ship attack sound at ship attacker */
    public void applySoundAtShipAttacker(AtkType type, Entity target)
    {
        //play sound
        switch (type)
        {
        case GENERIC_MELEE:
            if (this.rand.nextInt(3) == 0)
            {
                this.playSound(this.getCustomSound(1, this), this.getVolume(), this.getPitch());
            }
        break;
        case GENERIC_LIGHT:
            this.playSound(ModSounds.SHIP_FIRELIGHT, ConfigHandler.volumeFire, host.getSoundHandler().getPitch() * 0.85F);
            
            //entity sound
            if (this.rand.nextInt(8) == 0)
            {
                this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
            }
        break;
        case GENERIC_HEAVY:
        break;
        case GENERIC_AIR_LIGHT:
        break;
        case GENERIC_AIR_HEAVY:
        break;
        case YAMATO_CANNON:
        break;
        case AP91_FIST:
        break;
        
        
        
        case 2:  //heavy cannon
            this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
            
            //entity sound
            if (this.getRNG().nextInt(8) == 0)
            {
                this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
            }
        break;
        case 3:  //light aircraft
        case 4:  //heavy aircraft
            this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, this.getSoundPitch() * 0.85F);
          
            //entity sound
            if (this.getRNG().nextInt(8) == 0)
            {
                this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
            }
        break;
      default: //melee
          if (this.getRNG().nextInt(3) == 0)
          {
              this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
          }
      break;
        }//end switch
    }
    
    
    
    
    
    
    
    
    
    /**************** TODO refactoring ******************/
    
    
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