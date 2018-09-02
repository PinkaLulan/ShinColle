package com.lulan.shincolle.handler;

import java.util.EnumMap;
import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.reference.Enums.AtkType;
import com.lulan.shincolle.reference.Enums.SoundType;
import com.lulan.shincolle.reference.dataclass.AttackData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * handler for sound, must init AFTER StateHandler
 */
public class SoundHandler
{
    
    /** host object */
    protected ISoundEntity host;
    protected Random rand;
    protected float volume, pitch;
    protected SoundCategory category;
    /** key for sound map, for ship = classID + 2 */
    protected int soundKey;
    
    
    public SoundHandler(ISoundEntity host, int soundKey, SoundCategory category, float volume, float pitch)
    {
        this.host = host;
        
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
        this.soundKey = soundKey;
        
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
    
    /** @see #playSound(SoundEvent sound) */
    public void playSound(SoundType type)
    {
        this.playSound(this.getSound(type));
    }
    
    /** @see #playSound(SoundEvent sound, float volume, float pitch) */
    public void playSound(SoundEvent sound)
    {
        this.playSound(sound, this.getVolume(), this.getPitch());
    }
    
    /** @see #playSound(ISoundEntity host, SoundEvent sound, SoundCategory category, float volume, float pitch) */
    public void playSound(SoundType type, float volume, float pitch)
    {
        this.playSound(this.host, this.getSound(type), this.category, volume, pitch);
    }
    
    /** @see #playSound(ISoundEntity host, SoundEvent sound, SoundCategory category, float volume, float pitch) */
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
    
    /** get sound by type, for IStateEntityAdv ONLY */
    @Nullable
    public SoundEvent getSound(SoundType type)
    {
        return getSound(type, this.soundKey, this.rand);
    }
    
    /**
     * get sound by type
     * 
     * @param shipID = AttrClass + 2
     */
    @Nullable
    public static SoundEvent getSound(SoundType type, int shipID, Random rand)
    {
        /* use custom sound */
        EnumMap<SoundType, Float> rate = ConfigHandler.configSound.SOUNDRATE.get(shipID);
        int soundKey = shipID * 100 + type.ordinal();
        
        //has custom sound
        if (rate != null)
        {
            SoundEvent sound = ModSounds.CUSTOM_SOUND.get(soundKey);
            Float r = rate.get(type);
            
            if (sound != null && r != null && r > 0F && rand.nextFloat() < r)
            {
                return sound;
            }
        }
        
        /* use default sound */
        switch (type)
        {
        case IDLE:
            return ModSounds.SHIP_IDLE;
        case HIT:
            return ModSounds.SHIP_HIT;
        case HURT:
            return ModSounds.SHIP_HURT;
        case DEAD:
            return ModSounds.SHIP_DEATH;
        case MARRY:
            return ModSounds.SHIP_MARRY;
        case KNOCKBACK:
            return ModSounds.SHIP_KNOCKBACK;
        case PICKITEM:
            return ModSounds.SHIP_ITEM;
        case FEED:
            return ModSounds.SHIP_FEED;
        case TIMEKEEP00:
            return ModSounds.SHIP_TIME0;
        case TIMEKEEP01:
            return ModSounds.SHIP_TIME1;
        case TIMEKEEP02:
            return ModSounds.SHIP_TIME2;
        case TIMEKEEP03:
            return ModSounds.SHIP_TIME3;
        case TIMEKEEP04:
            return ModSounds.SHIP_TIME4;
        case TIMEKEEP05:
            return ModSounds.SHIP_TIME5;
        case TIMEKEEP06:
            return ModSounds.SHIP_TIME6;
        case TIMEKEEP07:
            return ModSounds.SHIP_TIME7;
        case TIMEKEEP08:
            return ModSounds.SHIP_TIME8;
        case TIMEKEEP09:
            return ModSounds.SHIP_TIME9;
        case TIMEKEEP10:
            return ModSounds.SHIP_TIME10;
        case TIMEKEEP11:
            return ModSounds.SHIP_TIME11;
        case TIMEKEEP12:
            return ModSounds.SHIP_TIME12;
        case TIMEKEEP13:
            return ModSounds.SHIP_TIME13;
        case TIMEKEEP14:
            return ModSounds.SHIP_TIME14;
        case TIMEKEEP15:
            return ModSounds.SHIP_TIME15;
        case TIMEKEEP16:
            return ModSounds.SHIP_TIME16;
        case TIMEKEEP17:
            return ModSounds.SHIP_TIME17;
        case TIMEKEEP18:
            return ModSounds.SHIP_TIME18;
        case TIMEKEEP19:
            return ModSounds.SHIP_TIME19;
        case TIMEKEEP20:
            return ModSounds.SHIP_TIME20;
        case TIMEKEEP21:
            return ModSounds.SHIP_TIME21;
        case TIMEKEEP22:
            return ModSounds.SHIP_TIME22;
        case TIMEKEEP23:
            return ModSounds.SHIP_TIME23;
        }
        
        return null;
    }
    
    /** play ship attack sound at ship attacker */
    public void applySoundAtShipAttacker(AttackData ad)
    {
        //play sound
        switch (ad.atkType)
        {
        case GENERIC_MELEE:
            //entity sound
            if (this.rand.nextInt(3) == 0) this.playSound(SoundType.HIT);
        break;
        case GENERIC_LIGHT:
            //weapon sound
            this.playSound(ModSounds.SHIP_FIRELIGHT, ConfigHandler.volumeFire, host.getSoundHandler().getPitch() * 0.85F);
            //entity sound
            if (this.rand.nextInt(8) == 0) this.playSound(SoundType.HIT);
        break;
        case GENERIC_HEAVY_LAUNCH:
            //weapon sound
            this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, host.getSoundHandler().getPitch() * 0.85F);
            //entity sound
            if (this.rand.nextInt(8) == 0) this.playSound(SoundType.HIT);
        break;
        case GENERIC_AIR_LIGHT_LAUNCH:
        case GENERIC_AIR_HEAVY_LAUNCH:
            //weapon sound
            this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, host.getSoundHandler().getPitch() * 0.85F);
            //entity sound
            if (this.rand.nextInt(8) == 0) this.playSound(SoundType.HIT);
        break;
        case YAMATO_CANNON_LAUNCH:
        break;
        case AP91_FIST:
        break;
        default:
        break;
        }//end switch
    }
    
    /** play timekeeping sound */
    public void playTimeSound(World w)
    {
        long time = w.provider.getWorldTime();
        
        if ((int) (time % 1000L) == 0)
        {
            switch ((int) (time / 1000L) % 24)
            {
            case 0:
                this.playSound(SoundType.TIMEKEEP00, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 1:
                this.playSound(SoundType.TIMEKEEP01, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 2:
                this.playSound(SoundType.TIMEKEEP02, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 3:
                this.playSound(SoundType.TIMEKEEP03, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 4:
                this.playSound(SoundType.TIMEKEEP04, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 5:
                this.playSound(SoundType.TIMEKEEP05, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 6:
                this.playSound(SoundType.TIMEKEEP06, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 7:
                this.playSound(SoundType.TIMEKEEP07, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 8:
                this.playSound(SoundType.TIMEKEEP08, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 9:
                this.playSound(SoundType.TIMEKEEP09, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 10:
                this.playSound(SoundType.TIMEKEEP00, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 11:
                this.playSound(SoundType.TIMEKEEP11, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 12:
                this.playSound(SoundType.TIMEKEEP12, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 13:
                this.playSound(SoundType.TIMEKEEP13, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 14:
                this.playSound(SoundType.TIMEKEEP14, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 15:
                this.playSound(SoundType.TIMEKEEP15, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 16:
                this.playSound(SoundType.TIMEKEEP16, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 17:
                this.playSound(SoundType.TIMEKEEP17, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 18:
                this.playSound(SoundType.TIMEKEEP18, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 19:
                this.playSound(SoundType.TIMEKEEP19, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 20:
                this.playSound(SoundType.TIMEKEEP20, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 21:
                this.playSound(SoundType.TIMEKEEP21, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 22:
                this.playSound(SoundType.TIMEKEEP22, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            case 23:
                this.playSound(SoundType.TIMEKEEP23, this.getVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
            break;
            }
        }
    }
    
    
}