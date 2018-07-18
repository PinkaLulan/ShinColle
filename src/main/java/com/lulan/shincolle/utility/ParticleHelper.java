package com.lulan.shincolle.utility;

import java.util.Random;

import com.lulan.shincolle.client.particle.Particle91Type;
import com.lulan.shincolle.client.particle.ParticleChi;
import com.lulan.shincolle.client.particle.ParticleCraning;
import com.lulan.shincolle.client.particle.ParticleCube;
import com.lulan.shincolle.client.particle.ParticleDebugPlane;
import com.lulan.shincolle.client.particle.ParticleEmotion;
import com.lulan.shincolle.client.particle.ParticleGradient;
import com.lulan.shincolle.client.particle.ParticleLaser;
import com.lulan.shincolle.client.particle.ParticleLaserNoTexture;
import com.lulan.shincolle.client.particle.ParticleLightning;
import com.lulan.shincolle.client.particle.ParticleLine;
import com.lulan.shincolle.client.particle.ParticleSmoke;
import com.lulan.shincolle.client.particle.ParticleSparkle;
import com.lulan.shincolle.client.particle.ParticleSphereLight;
import com.lulan.shincolle.client.particle.ParticleSpray;
import com.lulan.shincolle.client.particle.ParticleStickyLightning;
import com.lulan.shincolle.client.particle.ParticleSweep;
import com.lulan.shincolle.client.particle.ParticleTeam;
import com.lulan.shincolle.client.particle.ParticleTexts;
import com.lulan.shincolle.client.particle.ParticleTextsCustom;
import com.lulan.shincolle.handler.IStateEntity;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.dataclass.ParticleData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * particle helper
 */
public class ParticleHelper
{
    
    
    public ParticleHelper() {}
    
    /** spawn particle and set attack time for attack pose */
    @SideOnly(Side.CLIENT)
    public static void spawnParticleWithAttackPose(Entity host, ParticleData data) throws Exception
    {
        if (host instanceof IStateEntity)
        {
            ((IStateEntity) host).getStateHandler().setAttackTick(50);
            spawnParticle(data);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static void spawnParticle(Particle p)
    {
        if (p != null) Minecraft.getMinecraft().effectRenderer.addEffect(p);
    }
    
    @SideOnly(Side.CLIENT)
    public static void spawnParticle(ParticleData data) throws Exception
    {
        World world = ClientProxy.getClientWorld();
        Random rand = world.rand;
        Entity host = null;       //entity temp
        Entity target = null;       //entity temp
        double d1, d2, d3, d4;     //double temp
        int maxpar = (int)((3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 1.8F);
        
        /* get entity */
        switch (data.getType())
        {
        case SPRAY_MISSILE:
        case SMOKE_DOUBLE_L_CUSTOM:
        case SMOKE_DOUBLE_L_2JOINTS:
        case TYPE91APFIST_CHI:
        case ARROW_ENTITY:
        case EMOTION_ENTITY:
        case LIGHTNING_ENTITY:
        case LIGHTNING_STICKY:
        case LIGHTNING_SPHERE:
        case CUBE_VIBRATE:
        case LINE_GUARD_BLOCK:
        case BEAM_IN:
        case BEAM_IN_SIMPLE:
        case BEAM_OUT:
        case SPARKLE:
        case EYE_FIRE:
        case GRADIENT_DOUBLE_OUT:
        case SWEEP_VERTICAL:
        case SWEEP_HORIZONTAL:
        case DEBUG_PLANE:
        case TEXT_ENTITY:
        {
            //check morph
            if (data.getBooleanData(0))
            {
                host = MetamorphHelper.getMorphEntityByPlayerEID(data.getIntData(0), 0, true);
            }
            //get host entity
            else
            {
                host = EntityHelper.getEntityPlayerByID(data.getIntData(0), 0, true);
            }
            
            //null check
            if (host == null) return;
            
            //change random
            if (host instanceof EntityLivingBase) rand = ((EntityLivingBase) host).getRNG();
        }
        break;
        case LASER_DOUBLE:
        case LASER_YAMATO:
        case LINE_GUARD_ENTITY:
        case LINE_GUARD_SUPPLY:
        case LINE_GUARD_POS:
        case LINE_CUSTOM:
        {
            //check morph
            if (data.getBooleanData(0))
            {
                host = MetamorphHelper.getMorphEntityByPlayerEID(data.getIntData(0), 0, true);
            }
            //get host entity
            else
            {
                host = EntityHelper.getEntityPlayerByID(data.getIntData(0), 0, true);
            }
            
            //get target entity
            target = EntityHelper.getEntityPlayerByID(data.getIntData(1), 0, true);
           
            //null check
            if (host == null || target == null) return;
           
            //change random
            if (host instanceof EntityLivingBase) rand = ((EntityLivingBase) host).getRNG();
            else if (target instanceof EntityLivingBase) rand = ((EntityLivingBase) target).getRNG();
        }
        break;
        default:
        break;
        }
        
        /* spaw particle */
        switch (data.getType())
        {
        case EXPLOSION_LARGE:
            world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, data.getFloatData(0), data.getFloatData(1) + 2D, data.getFloatData(2), 0D, 0D, 0D, new int[0]);
        break;
        case EXPLOSION_HUGE_LAVA:
            world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, data.getFloatData(0), data.getFloatData(1) + 1D, data.getFloatData(2), 0D, 0D, 0D, new int[0]);
            
            for (int i = 0; i < maxpar * 8; ++i)
            {
                d1 = rand.nextFloat() * 6D - 3D;
                d2 = rand.nextFloat() * 6D - 3D;
                world.spawnParticle(EnumParticleTypes.LAVA, data.getFloatData(0) + d1, data.getFloatData(1) + 1D, data.getFloatData(2) + d2, 0D, 0D, 0D, new int[0]);
            }
        break;
        case EXPLOSION_LARGE_LAVA:
            world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, data.getFloatData(0), data.getFloatData(1) + 1.5D, data.getFloatData(2), 0D, 0D, 0D, new int[0]);
            
            for (int i = 0; i < maxpar * 5; i++)
            {
                d1 = rand.nextFloat() * 3D - 1.5D;
                d2 = rand.nextFloat() * 3D - 1.5D;
                world.spawnParticle(EnumParticleTypes.LAVA, data.getFloatData(0) + d1, data.getFloatData(1) + 1D, data.getFloatData(2) + d2, 0D, 0D, 0D, new int[0]);
            }            
        break;
        case HEARTS:
            for (int i = 0; i < maxpar * 3; ++i)
            {
                d1 = rand.nextGaussian() * 0.02D;
                d2 = rand.nextGaussian() * 0.02D;
                d3 = rand.nextGaussian() * 0.02D;
                world.spawnParticle(EnumParticleTypes.HEART, data.getFloatData(0) + rand.nextFloat() * 2D - 1D, data.getFloatData(1) + 0.5D + rand.nextFloat() * 2D, data.getFloatData(2) + rand.nextFloat() * 2.0F - 1D, d1, d2, d3, new int[0]);
            }
        break;
        case SMOKE_S:
            for (int i = 0; i < maxpar; i++)
            {
                d1 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                d2 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                d3 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, data.getFloatData(0) + d1, data.getFloatData(1) + d2, data.getFloatData(2) + d3, 0D, data.getFloatData(4), 0D, new int[0]);
            }
        break;
        case SMOKE_M:
            for (int i = 0; i < maxpar; i++)
            {
                d1 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                d2 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                d3 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, data.getFloatData(0) + d1, data.getFloatData(1) + d2, data.getFloatData(2) + d3, 0D, data.getFloatData(4), 0D, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, data.getFloatData(0) + d3, data.getFloatData(1) + d2, data.getFloatData(2) + d1, 0D, data.getFloatData(4), 0D, new int[0]);
            }
        break;
        case SMOKE_L:
            for (int i = 0; i < maxpar * 8; i++)
            {
                d1 = rand.nextFloat() - 0.5F;
                d2 = rand.nextFloat();
                d3 = rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i, data.getFloatData(1) + 0.6D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i, data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i, data.getFloatData(1) + 1.0D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i, data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
            }
        break;
        case SMOKE_XL:
            for (int i = 0; i < maxpar * 1.5; i++)
            {
                d1 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                d2 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                d3 = rand.nextFloat() * data.getFloatData(3) - data.getFloatData(3) / 2D;
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + d1, data.getFloatData(1) + d2, data.getFloatData(2) + d3, 0D, 0D, 0D, new int[0]);
                world.spawnParticle(EnumParticleTypes.FLAME, data.getFloatData(0) + d3, data.getFloatData(1) + d2, data.getFloatData(2) + d1, 0D, 0.05D, 0D, new int[0]);
            }
        break;
        case FLAME:
            world.spawnParticle(EnumParticleTypes.FLAME, data.getFloatData(0), data.getFloatData(1) - 0.1, data.getFloatData(2), 0D, 0D, 0D, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), 0D, 0D, 0D, new int[0]);
            world.spawnParticle(EnumParticleTypes.FLAME, data.getFloatData(0), data.getFloatData(1) + 0.1, data.getFloatData(2), 0D, 0D, 0D, new int[0]);
        break;
        case TEXT_MISS:
            spawnParticle(new ParticleTexts(world, data.getFloatData(0), data.getFloatData(1) + data.getFloatData(3), data.getFloatData(2), 1F, 0));
        break;
        case TEXT_CRI:
            spawnParticle(new ParticleTexts(world, data.getFloatData(0), data.getFloatData(1) + data.getFloatData(3), data.getFloatData(2), 1F, 1));        
        break;
        case TEXT_DOUBLEHIT:
            spawnParticle(new ParticleTexts(world, data.getFloatData(0), data.getFloatData(1) + data.getFloatData(3), data.getFloatData(2), 1F, 2));        
        break;
        case TEXT_TRIPLEHIT:
            spawnParticle(new ParticleTexts(world, data.getFloatData(0), data.getFloatData(1) + data.getFloatData(3), data.getFloatData(2), 1F, 3));        
        break;
        case TEXT_DODGE:
            spawnParticle(new ParticleTexts(world, data.getFloatData(0), data.getFloatData(1) + data.getFloatData(4), data.getFloatData(2), 1F, 4));        
        break;
        case LASER_RE:
            spawnParticle(new ParticleLaser(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 1F, 0));
        break;
        case LASER_DOUBLE:
        {
            spawnParticle(new ParticleLaserNoTexture(world, host, target, data.getFloatData(0), data.getFloatData(1), 0F, data.getFloatData(2), 0));
            spawnParticle(new ParticleLaserNoTexture(world, host, target, -data.getFloatData(0), data.getFloatData(1), 0F, data.getFloatData(2), 0));
        }
        break;
        case LASER_YAMATO:        //yamato cannon beam
        {
            spawnParticle(new ParticleCube(world, host, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), 1));
            spawnParticle(new ParticleLaserNoTexture(world, host, target, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(4), 1));
        }
        break;
        case SPRAY_WHITE:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 1));
        break;
        case SPRAY_CYAN:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 2));
        break;
        case SPRAY_GREEN:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 3));
        break;
        case SPRAY_RED:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 4));
        break;
        case SPRAY_CYAN_LIGHT:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 7));
        break;
        case SPRAY_YELLOW:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 8));
        break;
        case SPRAY_ORANGE:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 9));
        break;
        case SPRAY_CYAN_TRANSPARENT:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 10));
        break;
        case SPRAY_CYAN_LIGHT_TRANSPARENT:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 15));
        break;
        case SPRAY_RED_TRANSPARENT:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 11));
        break;
        case SPRAY_WHITE_TRANSPARENT:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 12));
        break;
        case SPRAY_NEXT_WAYPOINT:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 13));
        break;
        case SPRAY_PAIRED_CHEST:
            spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 14));
        break;
        case SPRAY_TRIPLE:
            for (int i = 0; i < maxpar; i++)
            {
                spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 16));
            }
        break;
        case SPRAY_MISSILE:
        {
            spawnParticle(new ParticleSpray(host, data.getIntData(0), data.getFloatData()));
        }
        break;
        case SMOKE_DOUBLE_S:
        {
            //計算煙霧位置
            float degYaw = CalcHelper.getLookDegree(data.getFloatData(3), 0D, data.getFloatData(4), false)[0];
            float[] newPos1 = CalcHelper.rotateXZByAxis(0F, (float)data.getFloatData(5), degYaw, 1F);
            float[] newPos2 = CalcHelper.rotateXZByAxis(0F, (float)-data.getFloatData(5), degYaw, 1F);
            
            for (int i = 0; i < maxpar * 5; i++)
            {
                d1 = rand.nextFloat() - 0.5F;
                d2 = rand.nextFloat();
                d3 = rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i + newPos1[1], data.getFloatData(1) + 0.6D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i + newPos1[0], data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i + newPos2[1], data.getFloatData(1) + 0.6D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i + newPos2[0], data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i + newPos1[1], data.getFloatData(1) + 0.9D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i + newPos1[0], data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i + newPos2[1], data.getFloatData(1) + 0.9D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i + newPos2[0], data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
            }
        }
        break;
        case SMOKE_DOUBLE_L:
        {
            //計算煙霧位置
            float degYaw = CalcHelper.getLookDegree(data.getFloatData(3), 0D, data.getFloatData(4), false)[0];
            float[] newPos1 = CalcHelper.rotateXZByAxis(0F, data.getFloatData(5), degYaw, 1F);
            float[] newPos2 = CalcHelper.rotateXZByAxis(0F, -data.getFloatData(5), degYaw, 1F);
            
            for (int i = 0; i < maxpar * 6; i++)
            {
                d1 = rand.nextFloat() - 0.5F;
                d2 = rand.nextFloat();
                d3 = rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.6D + 0.1D * i + newPos1[1] + d2, data.getFloatData(1) + d1, data.getFloatData(2) + data.getFloatData(4) - 0.6D + 0.1D * i + newPos1[0] + d2, data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.6D + 0.1D * i + newPos2[1] + d3, data.getFloatData(1) + d1, data.getFloatData(2) + data.getFloatData(4) - 0.6D + 0.1D * i + newPos2[0] + d3, data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.6D + 0.1D * i + newPos1[1] + d3, data.getFloatData(1) + 0.3D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.6D + 0.1D * i + newPos1[0] + d3, data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.6D + 0.1D * i + newPos2[1] + d2, data.getFloatData(1) + 0.3D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.6D + 0.1D * i + newPos2[0] + d2, data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.6D + 0.1D * i + newPos1[1] + d2, data.getFloatData(1) + 0.6D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.6D + 0.1D * i + newPos1[0] + d2, data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0) + data.getFloatData(3) - 0.6D + 0.1D * i + newPos2[1] + d3, data.getFloatData(1) + 0.6D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.6D + 0.1D * i + newPos2[0] + d3, data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
            }
        }
        break;
        case SMOKE_DOUBLE_L_CUSTOM:
        {
            //計算煙霧位置
            float degYaw = EntityHelper.getRadRenderYawOffset(host);
            float[] newPos1 = CalcHelper.rotateXZByAxis(data.getFloatData(1), data.getFloatData(0), degYaw, 1F);
            float[] newPos2 = CalcHelper.rotateXZByAxis(data.getFloatData(1), -data.getFloatData(0), degYaw, 1F);
            float[] newPos3 = CalcHelper.rotateXZByAxis(data.getFloatData(3), 0F, degYaw, 1F);
            
            for (int i = 0; i < maxpar * 8; i++)
            {
                d1 = (rand.nextFloat() - 0.5F) * 2F;
                d2 = (rand.nextFloat() - 0.5F) * 2F;
                d3 = (rand.nextFloat() - 0.5F) * 2F;
                d4 = rand.nextFloat() * 2F;
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX + newPos1[1] + d1, host.posY + data.getFloatData(2) + d2, host.posZ + newPos1[0] + d3, newPos3[1] * d4, 0.05D * d4, newPos3[0] * d4, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX + newPos2[1] + d1, host.posY + data.getFloatData(2) + d3, host.posZ + newPos2[0] + d2, newPos3[1] * d4, 0.05D * d4, newPos3[0] * d4, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX + newPos1[1] + d2, host.posY + data.getFloatData(2) + d1, host.posZ + newPos1[0] + d3, newPos3[1] * d4, 0.05D * d4, newPos3[0] * d4, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX + newPos2[1] + d2, host.posY + data.getFloatData(2) + d3, host.posZ + newPos2[0] + d1, newPos3[1] * d4, 0.05D * d4, newPos3[0] * d4, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX + newPos1[1] + d3, host.posY + data.getFloatData(2) + d1, host.posZ + newPos1[0] + d2, newPos3[1] * d4, 0.05D * d4, newPos3[0] * d4, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX + newPos2[1] + d3, host.posY + data.getFloatData(2) + d2, host.posZ + newPos2[0] + d1, newPos3[1] * d4, 0.05D * d4, newPos3[0] * d4, new int[0]);
            }
        }
        break;
        case SMOKE_DOUBLE_L_2JOINTS:
        {
            if (host == null) break;
            //煙霧出現位置: 依照身體旋轉
            float degYaw = EntityHelper.getRadRenderYawOffset(host);
            float[] newPos1 = CalcHelper.rotateXZByAxis(data.getFloatData(1), data.getFloatData(0), degYaw, 1F);
            float[] newPos2 = CalcHelper.rotateXZByAxis(data.getFloatData(1), -data.getFloatData(0), degYaw, 1F);
            //煙霧噴射方向: 依照頭部旋轉
            float[] newPos3 = CalcHelper.rotateXZByAxis(data.getFloatData(3), 0F, EntityHelper.getRadRenderYawHead(host), 1F);
            
            //實際煙霧位置: 身體旋轉xz位移(達到砲台底座位置)+頭部旋轉xz位移(達到砲管旋轉位置)
            for (int i = 0; i < maxpar * 8; i++)
            {
                d1 = rand.nextFloat() - 0.5F;
                d2 = rand.nextFloat();
                d3 = rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX - 0.5D + 0.05D * i + newPos1[1] + newPos3[1], host.posY + data.getFloatData(2) + 0.6D + d1, host.posZ - 0.5D + 0.05D * i + newPos1[0] + newPos3[0], newPos3[1] * 0.5D * d2, 0.05D * d2, newPos3[0] * 0.5D * d2, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX - 0.5D + 0.05D * i + newPos2[1] + newPos3[1], host.posY + data.getFloatData(2) + 0.6D + d1, host.posZ - 0.5D + 0.05D * i + newPos2[0] + newPos3[0], newPos3[1] * 0.5D * d3, 0.05D * d3, newPos3[0] * 0.5D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX - 0.5D + 0.05D * i + newPos1[1] + newPos3[1], host.posY + data.getFloatData(2) + 0.9D + d1, host.posZ - 0.5D + 0.05D * i + newPos1[0] + newPos3[0], newPos3[1] * 0.5D * d3, 0.05D * d3, newPos3[0] * 0.5D * d3, new int[0]);
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, host.posX - 0.5D + 0.05D * i + newPos2[1] + newPos3[1], host.posY + data.getFloatData(2) + 0.9D + d1, host.posZ - 0.5D + 0.05D * i + newPos2[0] + newPos3[0], newPos3[1] * 0.5D * d2, 0.05D * d2, newPos3[0] * 0.5D * d2, new int[0]);
            }
        }
        break;
        case SMOKE_CHIMNEY_S:
            for (int i = 0; i < maxpar; i++)
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, data.getFloatData(0), data.getFloatData(1) + i * 0.1D, data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), new int[0]);
            }
        break;
        case SMOKE_CHIMNEY_L:
            for (int i = 0; i < maxpar; i++)
            {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, data.getFloatData(0), data.getFloatData(1) + i * 0.3D, data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), new int[0]);
            }
        break;
        case SMOKE_CHIMNEY_CUSTOM:
        {
            for (int i = 0; i < maxpar; i++)
            {
                spawnParticle(new ParticleSmoke(world, data.getFloatData(0), data.getFloatData(1) + i * 0.1D, data.getFloatData(2), 0D, data.getFloatData(3), 0D, data.getFloatData(4)));
            }
        }
        break;
        case TYPE91APFIST_ATTACK:
        {
            //draw speed blur
            spawnParticle(new ParticleLaser(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), 4F, 1));
            spawnParticle(new ParticleLaser(world, data.getFloatData(0), data.getFloatData(1)+0.4D, data.getFloatData(2), data.getFloatData(3), data.getFloatData(4) + 0.4D, data.getFloatData(5), 4F, 1));
            spawnParticle(new ParticleLaser(world, data.getFloatData(0), data.getFloatData(1)+0.8D, data.getFloatData(2), data.getFloatData(3), data.getFloatData(4) + 0.8D, data.getFloatData(5), 4F, 1));
            
            int maxspray = maxpar * 7;
            float[] newPos1;
            
            //draw hit particle
            for (int i = 0; i < maxspray; ++i)
            {
                newPos1 = CalcHelper.rotateXZByAxis(1, 0, 6.28F / maxspray * i, 1);
                spawnParticle(new ParticleSpray(world, data.getFloatData(3), data.getFloatData(4) + 0.3D, data.getFloatData(5), newPos1[0] * 0.35D, 0D, newPos1[1] * 0.35D, 0));
            }
            
            //draw hit text
            spawnParticle(new Particle91Type(world, data.getFloatData(3), data.getFloatData(4) + data.getFloatData(6) + 1D, data.getFloatData(5), 0.6F));
        }
        break;
        case TYPE91APFIST_SHOCKWAVE_IN:
        {
            int maxspray = maxpar * 7;
            float[] newPos1;
            
            for (int i = 0; i < maxspray; ++i)
            {
                newPos1 = CalcHelper.rotateXZByAxis((float)data.getFloatData(3), 0, 6.28F / maxspray * i, 1);
                spawnParticle(new ParticleSpray(world, data.getFloatData(0) + newPos1[0], data.getFloatData(1) + data.getFloatData(4), data.getFloatData(2) + newPos1[1], -newPos1[0] * 0.06D, 0D, -newPos1[1] * 0.06D, 5));
            }
        }
        break;
        case TYPE91APFIST_SHOCKWAVE_OUT:
        {
            int maxspray = maxpar * 7;
            float[] newPos1;
            
            for (int i = 0; i < maxspray; ++i)
            {
                newPos1 = CalcHelper.rotateXZByAxis((float)data.getFloatData(3), 0, 6.28F / maxspray * i, 1);
                spawnParticle(new ParticleSpray(world, data.getFloatData(0), data.getFloatData(1) + data.getFloatData(4), data.getFloatData(2), newPos1[0], 0D, newPos1[1], 6));
            }
        }
        break;
        case TYPE91APFIST_CHI:
        {
            spawnParticle(new ParticleChi(world, host, data.getFloatData(0), data.getIntData(1)));
        }
        break;
        case ARROW_BLOCK:
            spawnParticle(new ParticleTeam(world, data.getFloatData(3), data.getIntData(0), data.getFloatData(0), data.getFloatData(1), data.getFloatData(2)));
        break;
        case ARROW_ENTITY:
        {
            spawnParticle(new ParticleTeam(world, host, data.getFloatData(0), data.getIntData(1)));
        }
        break;
        case DRIPWATER:
            d1 = rand.nextFloat() * 0.7D - 0.35D;
            d2 = rand.nextFloat() * 0.7D - 0.35D;
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, data.getFloatData(0) + d1, data.getFloatData(1), data.getFloatData(2) + d2, data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), new int[0]);
        break;
        case DRIPLAVA:
            d1 = rand.nextFloat() * 0.7D - 0.35D;
            d2 = rand.nextFloat() * 0.7D - 0.35D;
            world.spawnParticle(EnumParticleTypes.DRIP_LAVA, data.getFloatData(0) + d1, data.getFloatData(1), data.getFloatData(2) + d2, data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), new int[0]);
        break;
        case SNOWHIT:
            for (int i = 0; i < maxpar * 5; i++)
            {
                d1 = rand.nextFloat() * 2F - 1F;
                d2 = rand.nextFloat() * 2F - 1F;
                d3 = rand.nextFloat() * 2F - 1F;
                world.spawnParticle(EnumParticleTypes.SNOWBALL, data.getFloatData(0) + d1, data.getFloatData(1) + 0.8D + d2, data.getFloatData(2) + d3, data.getFloatData(3) * 0.2D, 0.5D, data.getFloatData(4) * 0.2D, new int[0]);
            }
        break;
        case SNOWSPRAY:
            for (int i = 0; i < maxpar * 8; i++)
            {
                d1 = rand.nextFloat() - 0.5F;
                d2 = rand.nextFloat();
                d3 = rand.nextFloat();
                world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i, data.getFloatData(1) + 0.7D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i, data.getFloatData(3) * 0.3D * d2, 0.05D * d2, data.getFloatData(4) * 0.3D * d2, new int[0]);
                world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, data.getFloatData(0) + data.getFloatData(3) - 0.5D + 0.05D * i, data.getFloatData(1) + 0.9D + d1, data.getFloatData(2) + data.getFloatData(4) - 0.5D + 0.05D * i, data.getFloatData(3) * 0.3D * d3, 0.05D * d3, data.getFloatData(4) * 0.3D * d3, new int[0]);
            }
        break;
        case EMOTION_BLOCK:
            spawnParticle(new ParticleEmotion(world, null, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getIntData(0), data.getIntData(1)));
        break;
        case EMOTION_ENTITY:
        {
            spawnParticle(new ParticleEmotion(world, host, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getIntData(1), data.getIntData(2)));
        }
        case CRANING:
            spawnParticle(new ParticleCraning(world, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getIntData(0)));
        break;
        case HIGHSPEED_THICKYELLOW:
        {
            //draw speed blur: H, Wfront, Wback, R, G, B, A, px, py, pz, mx, my, mz
            spawnParticle(new ParticleLine(world, 0, new float[] {2.5F, 8F, 22F, 1F, 1F, 0.4F, 0.8F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
            spawnParticle(new ParticleLine(world, 0, new float[] {1F, 8F, 20F, 1F, 1F, 0.7F, 0.9F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
            spawnParticle(new ParticleLine(world, 0, new float[] {0.8F, 7F, 18F, 1F, 1F, 1F, 1F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
        }
        break;
        case HIGHSPEED_THICKPINK:
        {
            //draw speed blur: H, Wfor, Wback, R, G, B, A, px, py, pz, mx, my, mz
            spawnParticle(new ParticleLine(world, 0, new float[] {0.6F, 7F, 7F, 1F, 0.6F, 1F, 0.3F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
            spawnParticle(new ParticleLine(world, 0, new float[] {0.3F, 4F, 4F, 1F, 0.8F, 1F, 0.8F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
            spawnParticle(new ParticleLine(world, 0, new float[] {0.2F, 3F, 3F, 1F, 1F, 1F, 1F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
        }
        break;
        case HIGHSPEED_AURABLUR:
        {
            //draw speed blur: H, Wfront, Wback, R, G, B, A, px, py, pz, mx, my, mz
            spawnParticle(new ParticleLine(world, 0, new float[] {2.4F, 8F, 22F, 1F, 0F, 0F, 0.4F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
            spawnParticle(new ParticleLine(world, 0, new float[] {0.24F, 8F, 20F, 1F, 0F, 1F, 0.85F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
            spawnParticle(new ParticleLine(world, 0, new float[] {0.2F, 7F, 18F, 1F, 1F, 1F, 1F,
                (float)data.getFloatData(0), (float)data.getFloatData(1), (float)data.getFloatData(2), (float)data.getFloatData(3), (float)data.getFloatData(4), (float)data.getFloatData(5)}));
        }
        break;
        case BUBBLE:
        {
            for (int i = 0; i < maxpar * 5; i++)
            {
                d1 = (rand.nextFloat() - 0.5F) * data.getFloatData(4);
                d2 = (rand.nextFloat() - 0.5F) * data.getFloatData(3);
                d3 = (rand.nextFloat() - 0.5F) * data.getFloatData(5);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, data.getFloatData(0) + d2, data.getFloatData(1) + d1, data.getFloatData(2) + d3, 0D, 0D, 0D, new int[0]);
                world.spawnParticle(EnumParticleTypes.WATER_WAKE, data.getFloatData(0) + d2, data.getFloatData(1) + d1, data.getFloatData(2) + d3, 0D, 0D, 0D, new int[0]);
            }
        }
        break;
        case LIGHTNING_ENTITY:
        {
            spawnParticle(new ParticleLightning(world, host, data.getFloatData(0), data.getIntData(1)));
        }
        break;
        case LIGHTNING_STICKY:
        {
            for (int i = 0; i < data.getIntData(3); i++)
            {
                spawnParticle(new ParticleStickyLightning(world, host, data.getFloatData(0), data.getIntData(1), data.getIntData(2)));
            }
        }
        break;
        case LIGHTNING_SPHERE:
        {
            //in
            for (int i = 0; i < maxpar * 1.7; i++)
            {
                spawnParticle(new ParticleStickyLightning(world, host, data.getFloatData(0), data.getIntData(1), 2));
            }
            //out
            for (int i = 0; i < maxpar * 1.7; i++)
            {
                spawnParticle(new ParticleStickyLightning(world, host, data.getFloatData(0), data.getIntData(1), 3));
            }
        }
        break;
        case CUBE_VIBRATE:
        {
            //in
            spawnParticle(new ParticleCube(world, host, data.getFloatData(0), 0D, 0D, 1.5F, 0));
            
            //out
            for (int i = 0; i < 6; i++)
            {
                spawnParticle(new ParticleStickyLightning(world, host, data.getFloatData(0), 40, 3));
            }
        }
        break;
        case LINE_GUARD_BLOCK:
        {
            spawnParticle(new ParticleLaserNoTexture(world, host, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), 0.1F, 3));
        }
        break;
        case LINE_GUARD_ENTITY:
        {
            spawnParticle(new ParticleLaserNoTexture(world, host, target, 0D, 0D, 0D, 0.1F, 2));
        }
        break;
        case LINE_GUARD_SUPPLY:
        {
            spawnParticle(new ParticleLaserNoTexture(world, host, target, 0D, 0D, 0D, 0.1F, 4));
        }
        break;
        case LINE_GUARD_POS:
        {
            spawnParticle(new ParticleLaserNoTexture(world, host, target, 0D, 0D, 0D, 0.1F, 5));
        }
        break;
        case LINE_CUSTOM:        //紫色可調粗細光束
        {
            //                                                            height,               scale out,            scale in,         NO_USE, type
            spawnParticle(new ParticleLaserNoTexture(world, host, target, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), 0F, 6));
        }
        break;
        case BEAM_IN:
        {
            //                                    host, type, scale,             radius,               beam speed,           beam thick,           R                     G                     B                     A                     height
            spawnParticle(new ParticleSphereLight(host, 0, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), data.getFloatData(6), data.getFloatData(7), data.getFloatData(8)));
        }
        break;
        case BEAM_IN_SIMPLE:
        {
            //                                    host, type, life,              scale
            spawnParticle(new ParticleSphereLight(host, 5, data.getFloatData(0), data.getFloatData(1)));
        }
        break;
        case BEAM_OUT:
        {
            //TODO ent.setAttackTick(100)
            //                                 host, type, scale,             fade,                 speed,                space,                R                     G                     B                     A                     height
            spawnParticle(new ParticleGradient(host, 1, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), data.getFloatData(6), data.getFloatData(7), data.getFloatData(8)));
        }
        break;
        case SPARKLE:
        {
            //                                host, type,               scale,                rad,                  NO_USE, R                     G                     B                     A                     height
            spawnParticle(new ParticleSparkle(host, data.getIntData(1), data.getFloatData(0), data.getFloatData(1), 0F, 0F, data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), data.getFloatData(6)));
        }
        break;
        case EYE_FIRE:
        {
            //                                host, type, height, eye x, eye z, 4~7:RGBA, height
            spawnParticle(new ParticleSparkle(host, 1, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), data.getFloatData(6)));
        }
        break;
        case GRADIENT_DOUBLE_OUT:
            //                                 host, type, scale,                    fade, speed, space, R                 G                     B                     A,    height fade, slope
            spawnParticle(new ParticleGradient(host, 2, data.getFloatData(0) * 1.5F, 0.7F, 0.4F, 0F, data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), 0.8F, 40F, 1.6F));
            spawnParticle(new ParticleGradient(host, 2, data.getFloatData(0) * 1.3F, 0.7F, 0.3F, 0F, 1F, 1F, 1F, 1F, 40F, 1.5F));
        break;
        case SWEEP_VERTICAL:
            //                              host, type, scale1, scale2, scale3, fade, life, RGBA
            spawnParticle(new ParticleSweep(host, 0, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), data.getFloatData(6), data.getFloatData(7), data.getFloatData(8)));
        break;
        case SWEEP_HORIZONTAL:
            //                              host, type, scale1, scale2, scale3, fade, life, RGBA
            spawnParticle(new ParticleSweep(host, 0, data.getFloatData(0), data.getFloatData(1), data.getFloatData(2), data.getFloatData(3), data.getFloatData(4), data.getFloatData(5), data.getFloatData(6), data.getFloatData(7), data.getFloatData(8)));
        break;
        case DEBUG_PLANE:
            //raytrace target indicator          host, type = 0,           hit height,           hit side,             NO_USE
            //raytrace body cube indicator       host, type = 1,           cube top,             cube bottom,          bodyID
            //raytrace body cube indicator       host, type = 2,           cube top,             cube bottom,          bodyID
            spawnParticle(new ParticleDebugPlane(host, data.getIntData(1), data.getFloatData(0), data.getFloatData(1), data.getFloatData(2)));
        break;
        case TEXT_BLOCK:
            spawnParticle(new ParticleTextsCustom(null, world, data));
        break;
        case TEXT_ENTITY:
            spawnParticle(new ParticleTextsCustom(host, world, data));
        break;
        default:
        break;
        }
    }
    
    
}