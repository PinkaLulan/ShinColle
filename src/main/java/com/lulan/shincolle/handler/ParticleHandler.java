package com.lulan.shincolle.handler;

import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.AtkType;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.reference.dataclass.ParticleData;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

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
    
    /** apply attack particle at attacker */
    public void applyParticleAtAttacker(AtkType type, Entity target, Dist4d distance)
    {
        ParticleData pdata = null;
        
        switch (type)
        {
        case GENERIC_MELEE:  //set attack time only
            pdata = new ParticleData(ParType);
        break;
        case GENERIC_LIGHT:
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
        default:
        break;
            
        case 1:  //light cannon
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, distance.x, distance.y, distance.z, true), point);
        break;
        case 2:  //heavy cannon
        case 3:  //light aircraft
        case 4:  //heavy aircraft
        default: //melee
        break;
        }
    }
    
    
    
    
    
    
    /**************** TODO refactoring *****************/
    
    
    /** spawn attack text particle on entity, SERVER SIDE
     *  type: 0:miss, 1:critical, 2:double hit, 3:triple hit, 4:dodge
     */
    public static void spawnAttackTextParticle(Entity host, int type)
    {
        //null check
        if (host == null || host.world == null || host.world.isRemote) return;
        
        TargetPoint point = new TargetPoint(host.dimension, host.posX, host.posY, host.posZ, 64D);
        
        switch (type)
        {
        case 0:  //miss
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host, 10, false), point);
      break;
        case 1:  //critical
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host, 11, false), point);
      break;
        case 2:  //double hit
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host, 12, false), point);
      break;
        case 3:  //triple hit
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host, 13, false), point);
      break;
        case 4:  //dodge
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host, 34, false), point);
      break;
      default:
      break;
        }
    }
    
    
}