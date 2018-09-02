package com.lulan.shincolle.utility;

import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityProjectileStatic;
import com.lulan.shincolle.handler.AttrStateHandler;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.IAttackEntity;
import com.lulan.shincolle.handler.ParticleHandler;
import com.lulan.shincolle.handler.StateHandler;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.Enums.ActType;
import com.lulan.shincolle.reference.Enums.Ammo;
import com.lulan.shincolle.reference.Enums.AtkType;
import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.reference.Enums.MoveType;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.dataclass.AttackData;
import com.lulan.shincolle.reference.dataclass.Attrs;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.reference.dataclass.MissileData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * helper for combat calculation
 *
 */
public class CombatHelper
{
    
    public static Random rand = new Random();
    
    
    public CombatHelper() {}
    
    /** get exp value by attack type */
    public static int getExpByAction(AtkType type)
    {
        switch (type)
        {
        case GENERIC_MELEE:
            return ConfigHandler.expGain[0];
        case GENERIC_LIGHT:
            return ConfigHandler.expGain[1];
        case GENERIC_HEAVY_LAUNCH:
        case YAMATO_CANNON_LAUNCH:
            return ConfigHandler.expGain[2];
        case GENERIC_AIR_LIGHT_LAUNCH:
            return ConfigHandler.expGain[3];
        case GENERIC_AIR_HEAVY_LAUNCH:
            return ConfigHandler.expGain[4];
        default:
            return 0;
        }
    }
    
    /** get DamageSource by AtkType and host type */
    public static DamageSource getDamageSource(AtkType atkType, IAttackEntity host)
    {
        DamageSource ds = null;
        
        //if PlayerSign = true, cause player damage TODO
        
        //if no PlayerSign
        if (host instanceof EntityLivingBase)
        {
            switch (atkType)
            {
            default:
                ds = DamageSource.causeMobDamage((EntityLivingBase) host);
            break;
            }
        }
        
        return ds;
    }
    
    /**
     * apply DEF
     */
    public static float applyDamageReduceByDEF(Random rand, Attrs attrs, float rawAtk)
    {
        return rawAtk * (1F - attrs.getDefense() + (rand.nextFloat() * 0.5F - 0.25F));    
    }
    
    /**
     * damage reduction on player
     */
    public static void applyDamageReduceOnPlayer(AttackData ad)
    {
        //damage limit on player target
        if (ad.target instanceof EntityPlayer)
        {
            ad.damage *= 0.25F;
            if (ad.damage > (float) ConfigHandler.maxDmgOnPlayer)
                ad.damage = (float) ConfigHandler.maxDmgOnPlayer;    //same with TNT
        }
    }
    
    /**
     * calc miss rate
     */
    public static float calcMissRate(IAttackEntity host, float distance)
    {
        float miss = 0F;
        AttrStateHandler sh = host.getStateHandler();
        
        //distance < 3 blocks
        if (sh.getAttrs().getAttackRange() <= 3F)
        {
            miss = 0.25F - 0.001F * (float) sh.getLevel();
        }
        //distance < 6 blocks
        else if (sh.getAttrs().getAttackRange() <= 6F)
        {
            miss = 0.25F + 0.15F * (distance / sh.getAttrs().getAttackRange()) - 0.001F * (float) sh.getLevel();
        }
        else
        {
            miss = 0.25F + 0.25F * (distance / sh.getAttrs().getAttackRange()) - 0.001F * (float) sh.getLevel();
        }
        
        //miss reduction on equips
        miss -= sh.getAttrs().getAttrsBuffed(ID.Attrs.MISS);
        
        //miss min and max limit
        if (miss > 0.5F) miss = 0.5F;    //raw miss chance limit = 50%
        else if (miss < 0F) miss = 0F;
        
        //apply nausea potion effect (after limit)
        if (BuffHelper.getPotionLevel(host.getBuffHandler().getBuffMap(), 9) > 0) miss += 0.4F;
        
        return miss;
    }
    
    /** roll dodge, parms: distSQ = distance^2 */
    public static boolean canDodge(IAttackEntity host, float distSq)
    {
        if (host != null && !((Entity)host).world.isRemote)
        {
            AttrStateHandler sh = host.getStateHandler();
            float dodge = sh.getAttrs().getAttrsBuffed(ID.Attrs.DODGE);
            
            //if host is submarine, increase dodge by invisible level
            if (host instanceof IShipInvisible)
            {
                float dodgeDist = (1F - (float) sh.getLevel() * (1F + ((IShipInvisible) host).getInvisibleLevel()) / 300F) * 64F;
                
                if (distSq > dodgeDist * dodgeDist)
                {
                    dodge += ((IShipInvisible) host).getInvisibleLevel();
                }
            }
            
            Entity ent2 = (Entity) host;
            
            //roll dodge
            if (rand.nextFloat() <= dodge)
            {
                //check host is morph
                if (ent2 instanceof IShipMorph && ((IShipMorph)ent2).getMorphHost() != null) ent2 = ((IShipMorph)ent2).getMorphHost();
                //spawn dodge particle
                host.getParticleHandler().applyParticleAttackText(4);
                return true;
            }
        }
        
        return false;
    }
    
    /** tweak damage by type and light, called in attackEntityFrom
     *  dmg: damage value
     *  typeAtk: attacker type id
     *  typeDef: defender type id
     *  lightCoef: 0=night, 1=day, 0.X=night vision potion
     */
    public static float modDamageByLight(float dmg, int typeAtk, int typeDef, float lightCoef)
    {
        //if type = undefined, return org damage
        if (typeAtk <= 0 || typeDef <= 0) return dmg;
        
        //limit value
        if (lightCoef < 0F) lightCoef = 0F;
        else if (lightCoef > 1F) lightCoef = 1F;
        
        //calc damage modifier
        float modDay = Values.ModDmgNight[typeAtk-1][typeDef-1];
        float modNight = Values.ModDmgDay[typeAtk-1][typeDef-1];
        float mod = modNight + (modDay - modNight) * lightCoef;
        
        return dmg * mod;
    }
    
    /**
     * tweak damage by attack type, ex: YAMATO_CANNON
     * note: this should be called after #modDamageByAttrs
     * 
     * @param type attack type by {@link Enums.AtkType}
     * @param dmg raw attack damage
     * @return new attack damage
     */
    public static float modDamageByAtkType(AtkType type, @Nullable Entity target, float dmg)
    {
        float modEffect = 1F;
        
        //check attack type
        switch(type)
        {
        case AP91_FIST:              //nagato heavy attack
            modEffect = 4F;
        break;
        case YAMATO_CANNON_COLLIDE:  //yamato heavy attack
            modEffect = 1.5F;
        break;
        default:
        break;
        }
        
        return dmg * modEffect;
    }
    
    /**
     * tweak damage by attrs, ex: AA, ASM
     * 
     * @param state host state handler
     * @param dmg raw attack damage
     * @return new attack damage
     */
    public static float modDamageByAttrs(AttrStateHandler state, @Nullable Entity target, float dmg)
    {
        float newDmg = dmg;
        
        //check target type is fly or undersea
        MoveType type = null;
        
        if (target != null) type = EntityHelper.getEntityMoveType(target);
        else type = MoveType.LAND;
        
        switch (type)
        {
        case FLY:
            newDmg += state.getAttrs().getAttrsBuffed(ID.Attrs.AA);
        break;
        case UNDERSEA:
            newDmg += state.getAttrs().getAttrsBuffed(ID.Attrs.ASM);
        break;
        default:
        break;
        }
        
        return newDmg;
    }
    
    /**
     * roll miss/cri/double hit/triple hit on collide, return new damage
     * 
     * miss rate = base rate + distance - level - equip + buff
     * base rate = 0.25
     * distance = 0.25 * (target dist / max hit range)
     * level = 0.001 * ship level
     * equip = total miss reduction on equips
     * buff = nausea +0.5/level 
     * 
     * parms: host, target, can multiple hit, distance, raw damage
     * return: new damage
     */
    public static float modDamageByRateOnCollide(AtkType type, IAttackEntity host, Entity target, Dist4d distVec, float rawAtk)
    {
        //TODO
        
        return rawAtk;
    }
    
    /**
     * roll miss/cri/double hit/triple hit on attack method call, return new damage
     * 
     * miss rate = base rate + distance - level - equip + buff
     * base rate = 0.25
     * distance = 0.25 * (target dist / max hit range)
     * level = 0.001 * ship level
     * equip = total miss reduction on equips
     * buff = nausea +0.5/level 
     * 
     * parms: host, target, can multiple hit, distance, raw damage
     * return: new damage
     */
    public static AttackData modDamageByRateOnAttack(AttackData rawdata)
    {
        AttrStateHandler sh = rawdata.host.getStateHandler();
        
        /**
         * priority: miss > cri > dhit > thit
         */
        boolean canMiss = rawdata.atkType.canMiss();
        boolean canCri = rawdata.atkType.canCri();
        boolean canDhit = rawdata.atkType.canDHit();
        boolean canThit = rawdata.atkType.canTHit();
        float miss = 0F;
        float cri = 0F;
        float dhit = 0F;
        float thit = 0F;
        
        if (canMiss) miss = calcMissRate(rawdata.host, (float) rawdata.targetVec.d);
        if (canCri) cri = sh.getAttrs().getAttrsBuffed(ID.Attrs.CRI);
        if (canDhit) dhit = sh.getAttrs().getAttrsBuffed(ID.Attrs.DHIT);
        if (canThit) thit = sh.getAttrs().getAttrsBuffed(ID.Attrs.THIT);
        
        //add cri rate
        cri += miss;
        if (cri < miss) cri = miss;
        
        //add double hit rate
        dhit += cri;
        if (dhit < cri) dhit = cri;
        
        //add triple hit rate
        thit += dhit;
        if (thit < dhit) thit = dhit;
        
        //roll
        float roll = rawdata.host.getEntityRandom().nextFloat();
        ParticleHandler ph = rawdata.host.getParticleHandler();
        
        //justLaunch = 單純發射物體, ex: 飛彈/飛機/魚雷/光束head, miss時不影響傷害
        if (rawdata.atkType.justLaunch())
        {
            //if miss hit
            if (canMiss && roll <= miss)
            {
                //apply particle
                if (rawdata.host instanceof Entity)
                {
                    ph.applyParticleAttackText(0);
                }
                
                rawdata.isMiss = true;
            }
        }
        //非justLaunch類攻擊
        else
        {
            //if miss hit
            if (canMiss && roll <= miss)
            {
                //apply particle
                if (rawdata.host instanceof Entity) ph.applyParticleAttackText(0);
                
                rawdata.isMiss = true;
                rawdata.damage = 0F;
            }
            //if cri hit
            else if (canCri && roll <= cri)
            {
                //apply particle
                if (rawdata.host instanceof Entity) ph.applyParticleAttackText(1);
                
                rawdata.isCri = true;
                rawdata.damage *= 1.5F;
            }
            //if double hit
            else if (canDhit && roll <= dhit)
            {
                //apply particle
                if (rawdata.host instanceof Entity) ph.applyParticleAttackText(2);
                
                rawdata.isDhit = true;
                rawdata.damage *= 2F;
            }
            //if triple hit
            else if (canThit && roll <= thit)
            {
                //apply particle
                if (rawdata.host instanceof Entity) ph.applyParticleAttackText(3);
                
                rawdata.isThit = true;
                rawdata.damage *= 3F;
            }
            else {}
        }//end 非justLaunch類攻擊
        
        return rawdata;
    }
    
    /**
     * calc attack delay
     * 
     * type:
     *   0: melee
     *   1: light
     *   2: heavy
     *   3: air light
     *   4: air heavy
     */
    public static int getAttackDelay(float aspd, int type)
    {
        if (aspd < 0.01F) aspd = 0.01F;
        
        switch (type)
        {
        case 0:
            return (int)(ConfigHandler.baseAttackSpeed[0] / aspd) + ConfigHandler.fixedAttackDelay[0];
        case 1:
            return (int)(ConfigHandler.baseAttackSpeed[1] / aspd) + ConfigHandler.fixedAttackDelay[1];
        case 2:
            return (int)(ConfigHandler.baseAttackSpeed[2] / aspd) + ConfigHandler.fixedAttackDelay[2];
        case 3:
            return (int)(ConfigHandler.baseAttackSpeed[3] / aspd) + ConfigHandler.fixedAttackDelay[3];
        case 4:
            return (int)(ConfigHandler.baseAttackSpeed[4] / aspd) + ConfigHandler.fixedAttackDelay[4];
        }
        
        return 40;
    }
    
    /**
     * get missile move type
     * type: 0:melee, 1:light, 2:heavy, 3:air-light, 4:air-heavy
     */
    public static int calcMissileMoveType(IAttackEntity host, double tarY, int type)
    {
        int moveType = host.getAttackHandler().getMissileData(type).movetype;
        
        //moveType = -1, check depth
        if (moveType < 0)
        {
            double depth = host.getStateHandler().getStateDouble(AttrNum.ShipDepth);
            
            //in water
            if (depth > 2D)
            {
                moveType = 0;
            }
            //on water
            else if (depth > 0D)
            {
                //target is lower
                if (tarY <= ((Entity) host).posY || tarY - ((Entity) host).posY < depth)
                {
                    moveType = 2;
                }
                //target is higher
                else
                {
                    moveType = 1;
                }
            }
            //on solid block
            else
            {
                moveType = 1;
            }
        }
        
        return moveType;
    }
    
    /** get missile move type for airplane */
    public static int calcMissileMoveTypeForAirplane(IAttackEntity host, Entity target, int type)
    {
        int moveType = host.getAttackHandler().getMissileData(type).movetype;
        
        if (moveType < 0)
        {
            boolean targetliq = EntityHelper.checkEntityIsInLiquid(target);
            boolean hostliq = EntityHelper.checkEntityIsInLiquid((Entity)host);
            boolean hostunderliq = BlockHelper.checkBlockIsLiquid(target.world.getBlockState(new BlockPos(((Entity)host).posX, target.posY, ((Entity)host).posZ)));
            
            //host in water
            if (hostliq)
            {
                moveType = 0;
            }
            //on water
            else if (targetliq && hostunderliq)
            {
                //target is lower
                if (target.posY <= ((Entity) host).posY)
                {
                    moveType = 2;
                }
                //target is higher
                else
                {
                    moveType = 0;
                }
            }
            //on solid block
            else
            {
                moveType = 0;
            }
        }
        
        return moveType;
    }
    
    /**
     * special attack effect
     * 
     * type:
     *   5: black hole, data: 0: posX, 1: posY, 2: posZ
     */
    public static void specialAttackEffect(IShipAttackBase host, int type, float[] data)
    {
        switch (type)
        {
        case 5:   //black hole
        {
            if (!(host instanceof Entity)) return;
            
            Entity host2 = (Entity) host;
            EntityProjectileStatic beam = new EntityProjectileStatic(host2.world);
            double[] holedata = new double[] {data[0], data[1], data[2],
                    20D+host.getLevel()*0.125D,
                    0.12D+host.getLevel()*0.00075D,
                    4D+host.getLevel()*0.035D};
            beam.initAttrs(host, 0, holedata);
            host2.world.spawnEntity(beam);
        }
        break;
        }
    }
    
    /** add exp to ship by attack type, only for SHIP host for now */
    public static void addExp(AtkType type, IAttackEntity host)
    {
        BasicEntityShip ship = EntityHelper.getShipEntity(host);
        
        if (ship != null) ship.getStateHandler().addShipExp(CombatHelper.getExpByAction(type));
    }
    
    /** decrease morale by attack type, only for SHIP host for now */
    public static void decrMorale(AtkType type, IAttackEntity host)
    {
        BasicEntityShip ship = EntityHelper.getShipEntity(host);
        ActType t2 = ActType.ATTACK_MELEE;
        
        if (ship != null)
        {
            switch (type)
            {
            case GENERIC_LIGHT:
                t2 = ActType.ATTACK_LIGHT;
            break;
            case GENERIC_HEAVY_LAUNCH:
            case YAMATO_CANNON_LAUNCH:
                t2 = ActType.ATTACK_HEAVY;
            break;
            case GENERIC_AIR_LIGHT_LAUNCH:
                t2 = ActType.ATTACK_AIR_LIGHT;
            break;
            case GENERIC_AIR_HEAVY_LAUNCH:
                t2 = ActType.ATTACK_AIR_HEAVY;
            break;
            }
            
            ship.getStateHandler().decrMorale(t2);
        }
    }
    
    /**
     * decrease ammo by attack type, only for SHIP host for now
     * 
     * @return <tt>true</tt> if ammo consumed
     */
    public static boolean decrAmmo(AtkType type, IAttackEntity host)
    {
        BasicEntityShip ship = EntityHelper.getShipEntity(host);
        
        //if not ship = no ammo checking
        if (ship == null) return true;
        
        //if ship, decr ammo
        Ammo at = Ammo.LIGHT;
        int amount = ship.getStateHandler().getAmmoConsumption();
        
        switch (type)
        {
        case GENERIC_MELEE:      //melee without ammo
            return true;
        case GENERIC_HEAVY_LAUNCH:      //use heavy ammo
            at = Ammo.HEAVY;
        break;
        case YAMATO_CANNON_LAUNCH:      //use heavy ammo
            at = Ammo.HEAVY;
        break;
        case GENERIC_AIR_LIGHT_LAUNCH:  //airplane cost 6x LightAmmo
            amount *= 6;
        break;
        case GENERIC_AIR_HEAVY_LAUNCH:  //airplane cost 2x HeavyAmmo
            at = Ammo.HEAVY;
            amount *= 2;
        break;
        }
        
        return ship.getStateHandler().decrAmmo(at, amount);
    }
    
    /** decrease grudge by attack type, only for SHIP host for now */
    public static void decrGrudge(AtkType type, IAttackEntity host)
    {
        BasicEntityShip ship = EntityHelper.getShipEntity(host);
        
        //if not ship = no checking
        if (ship == null) return;
        
        //if ship, decr grudge
        int amount = 0;
        
        switch (type)
        {
        case GENERIC_MELEE:      //melee without grudge
            return;
        case GENERIC_LIGHT:
            amount = ConfigHandler.consumeGrudgeAction[0];
        break;
        case GENERIC_HEAVY_LAUNCH:
        case YAMATO_CANNON_LAUNCH:
            amount = ConfigHandler.consumeGrudgeAction[1];
        break;
        case GENERIC_AIR_LIGHT_LAUNCH:
            amount = ConfigHandler.consumeGrudgeAction[2];
        break;
        case GENERIC_AIR_HEAVY_LAUNCH:
            amount = ConfigHandler.consumeGrudgeAction[3];
        break;
        }
        
        ship.getStateHandler().decrGrudge(amount);
    }
    
    /** set combat start time */
    protected static void setLastCombatTime(IAttackEntity host)
    {
        StateHandler state = HandlerHelper.getStateHandler(host);
        
        if (state != null)
        {
            if (host instanceof Entity)
            {
                state.setLastCombatTick(((Entity) host).ticksExisted);
            }
        }
    }
    
    /** get attack base damage */
    protected static float getAttackDamage(AtkType type, IAttackEntity host, @Nullable Entity target)
    {
        if (host.getStateHandler() instanceof AttrStateHandler)
        {
            AttrStateHandler state = (AttrStateHandler) host.getStateHandler();
            float dmg = 0;
            
            switch (type)
            {
            case GENERIC_MELEE:
                return state.getAttrs().getAttackDamage() * 0.125F;
            case GENERIC_LIGHT:
                dmg = CombatHelper.modDamageByAttrs(state, target, state.getAttrs().getAttackDamage());
                dmg = CombatHelper.modDamageByAtkType(type, target, dmg);
                return dmg;
            case GENERIC_HEAVY_LAUNCH:
                return state.getAttrs().getAttackDamageHeavy();
            case GENERIC_AIR_LIGHT_LAUNCH:
                return state.getAttrs().getAttackDamageAir();
            case GENERIC_AIR_HEAVY_LAUNCH:
                return state.getAttrs().getAttackDamageAirHeavy();
            default:
            break;
            }
        }
        
        return 0F;
    }
    
    /** apply flare at position */
    public static void flareTarget(IAttackEntity host, BlockPos target)
    {
        World w = EntityHelper.getWorld(host);

        //server side and flare level > 0
        if (w != null && !w.isRemote &&
            host.getStateHandler().getStateInt(AttrNum.FlareLevel) > 0)
        {
            PacketHelper.sendFlare(host, target);
        }
    }
    
    /**
     * generic attack method for "entity" host
     */
    public static boolean attackTarget(AtkType type, IAttackEntity host, Entity target, BlockPos targetPos)
    {
        /* if no target, check hit ground and hit position */
        if (target == null)
        {
            if (targetPos == null || !type.canHitGround()) return false;
        }
        
        /* check ammo number */
        if (!decrAmmo(type, host)) return false;
        
        /* grudge-- */
        decrGrudge(type, host);
        
        /* morale-- */
        decrMorale(type, host);
        
        /* set combat tick to recording combat start time */
        setLastCombatTime(host);
        
        /* exp++ */
        addExp(type, host);
        
        /* get distance vector */
        Dist4d distVec = null;
        
        if (target == null) distVec = CalcHelper.getDistanceFromA2B(EntityHelper.getHostBlokcPosition(host), targetPos);
        else distVec = CalcHelper.getDistanceFromA2B(host, target);
        
        /* new attack object */
        AttackData ad = new AttackData(type, host, target, targetPos, distVec, host.getAttackHandler().getMissileData(), getAttackDamage(type, host, target));
        
        /* roll miss, cri, dhit, thit */
        CombatHelper.modDamageByRateOnAttack(ad);
        
        /* apply emotion reaction */
        ad.host.getReactHandler().reactAttackStart();
        
        /* apply attack by type */
        boolean isTargetHurt = applyAttack(ad);
        
        //TODO

        return isTargetHurt;
    }
    
    /** called by CombatHelper.attackTarget */
    private static boolean applyAttack(AttackData ad)
    {
        boolean result = false;
        
        switch (ad.atkType)
        {
        case GENERIC_HEAVY_LAUNCH:
        case GENERIC_AIR_LIGHT_LAUNCH:
        case GENERIC_AIR_HEAVY_LAUNCH:
        case YAMATO_CANNON_LAUNCH:
            result = applyAttackSummon(ad);
        break;
        default:
            result = applyAttackDirect(ad);
        break;
        }
        
        return result;
    }
    
    private static boolean applyAttackDirect(AttackData ad)
    {
        /* apply damage limit on player */
        if (ad.target != null) CombatHelper.applyDamageReduceOnPlayer(ad);

        /* apply friendly fire */
        if (!TeamHelper.doFriendlyFire(ad.host, ad.target)) ad.damage = 0F;
        
        /* apply attack */
        DamageSource ds = CombatHelper.getDamageSource(ad.atkType, ad.host);
        boolean isTargetHurt = false;
        
        if (ad.target != null) isTargetHurt = ad.target.attackEntityFrom(ds, ad.damage);
        else isTargetHurt = true;
        
        /* apply sound at attacker */
        ad.host.getSoundHandler().applySoundAtShipAttacker(ad);

        /* apply particle at attacker */
        ad.host.getParticleHandler().applyParticleAtAttacker(ad);
        
        if (isTargetHurt)
        {
            if (ad.target != null)
            {
                /* apply debuff on target */
                if (!TeamHelper.checkSameOwner(ad.host, ad.target)) BuffHelper.applyBuffOnTarget(ad.target, ad.host.getBuffHandler().getAttackEffectMap());
                
                /* apply sound at target */
                SoundHelper.applySoundAtAttackTarget(ad.atkType, ad.target);
                
                /* apply particle at target */
                ad.host.getParticleHandler().applyParticleAtAttackTarget(ad);
            }
            
            if (ConfigHandler.canFlare)
            {
                if (ad.target == null) flareTarget(ad.host, ad.targetPos);
                else flareTarget(ad.host, ad.target.getPosition());
            }
        }
        
        return isTargetHurt;
    }
    
    private static boolean applyAttackSummon(AttackData ad)
    {
        boolean isTargetHurt = false;
        
        /* apply sound at attacker */
        ad.host.getSoundHandler().applySoundAtShipAttacker(ad);

        /* apply particle at attacker */
        ad.host.getParticleHandler().applyParticleAtAttacker(ad);
        
        /* apply particle at target */
        ad.host.getParticleHandler().applyParticleAtAttackTarget(ad);
        
        /* summon attack entity */
        switch (ad.atkType)
        {
        default:  //summon 1 missile
            summonMissile(ad);
        break;
        }
        
        return isTargetHurt;
    }
    
    /**
     * spawn attack missile, used in attack method
     */
    private static void summonMissile(AttackData ad)
    {
        //init launch height
        float launchPosY = 0.5F;
        
        //if entity host
        if (ad.host instanceof Entity) launchPosY = ((Entity) ad.host).height;
        
        //if morph entity host
        if (ad.host instanceof IShipMorph && ((IShipMorph) ad.host).isMorph()) launchPosY += 0.5F;
        
        //add base posY
        launchPosY += (float) EntityHelper.getHostPosition(ad.host).y;
        
        //TODO
        //missile type
        if (this.isMorph) launchPos += 0.5F;
        int moveType = CombatHelper.calcMissileMoveType(this, tarY, attackType);
        if (moveType == 0) launchPos = (float) posY + height * 0.3F;
        
        MissileData md = this.getMissileData(attackType);
        float[] data = new float[] {atk, 0.15F, launchPos, tarX, tarY + targetHeight * 0.1F, tarZ, 140, 0.25F, md.vel0, md.accY1, md.accY2};
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
        this.world.spawnEntity(missile);
    }
    
    
}