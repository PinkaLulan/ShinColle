package com.lulan.shincolle.handler;

import com.lulan.shincolle.reference.Enums.ParType;
import com.lulan.shincolle.reference.dataclass.AttackData;
import com.lulan.shincolle.reference.dataclass.ParticleData;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;

/**
 * process particle for host
 */
public class ParticleHandler
{
    
    /** host object */
    protected IParticleEntity host;
    
    
    public ParticleHandler(IParticleEntity host)
    {
        this.host = host;
        
        this.initFirst();
    }
    
    /** init data on construct */
    protected void initFirst()
    {
    }
    
    /** apply attack particle at attacker, SERVER SIDE */
    public void applyParticleAtAttacker(AttackData ad)
    {
        ParticleData pdata = null;
        
        switch (ad.atkType)
        {
        case GENERIC_LIGHT:
            PacketHelper.sendParticle(this.host,
                ParticleHelper.getParticleData(ParType.SMOKE_L, true, host, ad.target, ad.targetVec, null, null, null, null));
        break;
        case YAMATO_CANNON_LAUNCH:
            //TODO
        break;
        case AP91_FIST:
            //TODO
        break;
        case GENERIC_MELEE:  //set attack time only
        case GENERIC_HEAVY_LAUNCH:
        case GENERIC_AIR_LIGHT_LAUNCH:
        case GENERIC_AIR_HEAVY_LAUNCH:
        default:
            if (this.host instanceof IStateEntity)
                PacketHelper.sendAttackTime((IStateEntity) this.host);
        break;
        }
    }
    
    /** attack particle at attack target, SERVER SIDE */
    public void applyParticleAtAttackTarget(AttackData ad)
    {
        ParticleData pdata = null;
        
        switch (ad.atkType)
        {
        case GENERIC_MELEE:
            if (ad.target != null) PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.EXPLOSION_LARGE, false, ad.host, ad.target, ad.targetVec, null, null, null, null));
        break;
        case GENERIC_LIGHT:
            if (ad.target != null) PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.EXPLOSION_LARGE_LAVA, false, ad.host, ad.target, ad.targetVec, null, null, null, null));
        break;
        case YAMATO_CANNON_LAUNCH:
            //TODO particle: mark target
        break;
        case AP91_FIST:
        case GENERIC_HEAVY_LAUNCH:
        case GENERIC_AIR_LIGHT_LAUNCH:
        case GENERIC_AIR_HEAVY_LAUNCH:
            //TODO particle: mark target position
        default:
        break;
        }
    }
    
    /**
     * spawn attack text particle on entity, SERVER SIDE
     * type: 0:miss, 1:critical, 2:double hit, 3:triple hit, 4:dodge
     */
    public void applyParticleAttackText(int type)
    {
        Entity ent = EntityHelper.getEntity(this.host);
        
        switch (type)
        {
        case 0:  //miss
            PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.TEXT_MISS, true, this.host, null, null, null, null, new float[] {ent.height * 1.1F}, null));
        break;
        case 1:  //critical
            PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.TEXT_CRI, true, this.host, null, null, null, null, new float[] {ent.height * 1.1F}, null));
            break;
        case 2:  //double hit
            PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.TEXT_DOUBLEHIT, true, this.host, null, null, null, null, new float[] {ent.height * 1.1F}, null));
            break;
        case 3:  //triple hit
            PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.TEXT_TRIPLEHIT, true, this.host, null, null, null, null, new float[] {ent.height * 1.1F}, null));
            break;
        case 4:  //dodge
            PacketHelper.sendParticle(this.host, ParticleHelper.getParticleData(ParType.TEXT_DODGE, true, this.host, null, null, null, null, new float[] {ent.height * 1.1F}, null));
            break;
        default:
        break;
        }
    }
    
    
}