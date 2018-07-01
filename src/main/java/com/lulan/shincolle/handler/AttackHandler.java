package com.lulan.shincolle.handler;


import java.util.EnumMap;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.network.S2CReactPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.ActType;
import com.lulan.shincolle.reference.Enums.Ammo;
import com.lulan.shincolle.reference.Enums.AtkType;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.reference.dataclass.MissileData;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.HandlerHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * handler for attack methods
 */
public class AttackHandler
{
    
    /** host object */
    protected IAttackEntity host;
    /** missile data for each attack type */
    protected EnumMap<AtkType, MissileData> missileData;
    
    
    public AttackHandler(IAttackEntity host)
    {
        this.host = host;
        
        this.initFirst();
    }
    
    /** init data on construct */
    protected void initFirst()
    {
        this.missileData = new EnumMap<AtkType, MissileData>(AtkType.class);
    }
    
    public MissileData getMissileData(AtkType type)
    {
        MissileData md = this.missileData.get(type);
        
        if (type == null)
        {
            md = new MissileData();
            this.setMissileData(type, md);
        }
        
        return md;
    }
    
    public void setMissileData(AtkType type, MissileData md)
    {
        this.missileData.put(type, md);
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
            case GENERIC_HEAVY:
            case YAMATO_CANNON:
                t2 = ActType.ATTACK_HEAVY;
            break;
            case GENERIC_AIR_LIGHT:
                t2 = ActType.ATTACK_AIR_LIGHT;
            break;
            case GENERIC_AIR_HEAVY:
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
        case GENERIC_HEAVY:      //use heavy ammo
            at = Ammo.HEAVY;
        break;
        case YAMATO_CANNON:      //use heavy ammo
            at = Ammo.HEAVY;
        break;
        case GENERIC_AIR_LIGHT:  //airplane cost 6x LightAmmo
            amount *= 6;
        break;
        case GENERIC_AIR_HEAVY:  //airplane cost 2x HeavyAmmo
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
        case GENERIC_HEAVY:
        case YAMATO_CANNON:
            amount = ConfigHandler.consumeGrudgeAction[1];
        break;
        case GENERIC_AIR_LIGHT:
            amount = ConfigHandler.consumeGrudgeAction[2];
        break;
        case GENERIC_AIR_HEAVY:
            amount = ConfigHandler.consumeGrudgeAction[3];
        break;
        }
        
        ship.getStateHandler().decrGrudge(amount);
    }
    
    /** set combat start time */
    public static void setLastCombatTime(IAttackEntity host)
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
    protected static float getAttackDamage(AtkType type, IAttackEntity host, Entity target)
    {
        //TODO
//        float baseDmg = this.host
//        
//        switch (type)
//        {
//        case 1:  //light cannon
//            return CombatHelper.modDamageByAttrs(state, target, dmg)
//        case 2:  //heavy cannon
//            return this.shipAttrs.getAttackDamageHeavy();
//        case 3:  //light aircraft
//            return this.shipAttrs.getAttackDamageAir();
//        case 4:  //heavy aircraft
//            return this.shipAttrs.getAttackDamageAirHeavy();
//      default: //melee
//          return this.shipAttrs.getAttackDamage() * 0.125F;
//        }
    }
    
    /**
     * generic attack method for "entity" host
     */
    public static boolean attackTarget(AtkType type, IAttackEntity host, Entity target)
    {
        /* check ammo number */
        if (!this.decrAmmo(type)) return false;
        
        /* grudge-- */
        this.decrGrudge(type);
        
        /* morale-- */
        this.decrMorale(type);
        
        /* set combat tick to recording combat start time */
        this.setLastCombatTime();
        
        /* exp++ */
        this.addExp(type);
        
        /* get distance vector */
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this.host, target);
        
        /* apply sound and particle at attacker */
        this.applySoundAtAttacker(1, target);
        this.applyParticleAtAttacker(1, target, distVec);
        
        
        
        
        
        
        
        //roll miss, cri, dhit, thit
        atk = CombatHelper.applyCombatRateToDamage(this, target, true, (float)distVec.d, atk);
          
          //damage limit on player target
        atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
          
          //check friendly fire
        if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
        
          //確認攻擊是否成功
        boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);
        
        //if attack success
        if (isTargetHurt)
        {
            //check owner
            if (!TeamHelper.checkSameOwner(this, target)) BuffHelper.applyBuffOnTarget(target, this.AttackEffectMap);
            applySoundAtTarget(1, target);
            applyParticleAtTarget(1, target, distVec);
            applyEmotesReaction(3);
            
            if (ConfigHandler.canFlare) flareTarget(target);
        }
        
        applyAttackPostMotion(1, target, isTargetHurt, atk);

        return isTargetHurt;
    }
    
    
    
    
    
    
    
    /** TODO refactoring */
    
    //melee attack method, no ammo cost, no attack speed, damage = 12.5% atk
    @Override
    public boolean attackEntityAsMob(Entity target)
    {
        //get attack value
        float atk = getAttackBaseDamage(0, target);
        
        //experience++
        addShipExp(ConfigHandler.expGain[0]);
        
        //morale--
        decrMorale(0);
        setCombatTick(this.ticksExisted);
        
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
                
        //entity attack effect
        applySoundAtAttacker(0, target);
        applyParticleAtAttacker(0, target, distVec);
        
        //是否成功傷害到目標
        boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

        //target attack effect
        if (isTargetHurt)
        {
            if (!TeamHelper.checkSameOwner(this, target)) BuffHelper.applyBuffOnTarget(target, this.AttackEffectMap);
            applySoundAtTarget(0, target);
            applyParticleAtTarget(0, target, distVec);
            applyEmotesReaction(3);
            
            if (ConfigHandler.canFlare)
            {
                flareTarget(target);
            }
        }
        
        applyAttackPostMotion(0, target, isTargetHurt, atk);

        return isTargetHurt;
    }
    
  //range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
    @Override
    public boolean attackEntityWithAmmo(Entity target)
    {
        //light ammo -1
        if (!decrAmmoNum(0, this.getAmmoConsumption())) return false;
        
        //experience++
          addShipExp(ConfigHandler.expGain[1]);
          
          //grudge--
          decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
          
          //morale--
          decrMorale(1);
          setCombatTick(this.ticksExisted);
          
          //get attack value
          float atk = getAttackBaseDamage(1, target);
          
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
        applyParticleAtAttacker(1, target, distVec);
        
        //roll miss, cri, dhit, thit
        atk = CombatHelper.applyCombatRateToDamage(this, target, true, (float)distVec.d, atk);
          
          //damage limit on player target
        atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
          
          //check friendly fire
        if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
        
          //確認攻擊是否成功
        boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);
        
        //if attack success
        if (isTargetHurt)
        {
            //check owner
            if (!TeamHelper.checkSameOwner(this, target)) BuffHelper.applyBuffOnTarget(target, this.AttackEffectMap);
            applySoundAtTarget(1, target);
            applyParticleAtTarget(1, target, distVec);
            applyEmotesReaction(3);
            
            if (ConfigHandler.canFlare) flareTarget(target);
        }
        
        applyAttackPostMotion(1, target, isTargetHurt, atk);

        return isTargetHurt;
    }
    
    /**
     * attack a position with missile, NOTE: SHIP MOUNTS REQUIRED
     */
    public boolean attackEntityWithHeavyAmmo(BlockPos target)
    {
        //ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
        //experience++
        addShipExp(ConfigHandler.expGain[2]);
        
        //grudge--
        decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
        
          //morale--
        decrMorale(2);
          setCombatTick(this.ticksExisted);
    
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this.getPosition(), target);
        
        //play sound and particle
        applySoundAtAttacker(2, this);
        applyParticleAtAttacker(2, this, distVec);
        
        float tarX = (float) target.getX();
        float tarY = (float) target.getY();
        float tarZ = (float) target.getZ();
        
        //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
            tarX = tarX - 5F + this.rand.nextFloat() * 10F;
            tarY = tarY + this.rand.nextFloat() * 5F;
            tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
            
            ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //get attack value
          float atk = getAttackBaseDamage(2, null);
        
        //spawn missile
          summonMissile(2, atk, tarX, tarY, tarZ, 1F);
        
        //play target effect
        applySoundAtTarget(2, this);
        applyParticleAtTarget(2, this, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        applyAttackPostMotion(2, this, true, atk);
        
        return true;
    }

    //range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
    @Override
    public boolean attackEntityWithHeavyAmmo(Entity target)
    {
        //ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
        //experience++
        addShipExp(ConfigHandler.expGain[2]);
        
        //grudge--
        decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
        
          //morale--
        decrMorale(2);
          setCombatTick(this.ticksExisted);
    
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play sound and particle
        applySoundAtAttacker(2, target);
        applyParticleAtAttacker(2, target, distVec);
        
        float tarX = (float) target.posX;
        float tarY = (float) target.posY;
        float tarZ = (float) target.posZ;
        
        //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
            tarX = tarX - 5F + this.rand.nextFloat() * 10F;
            tarY = tarY + this.rand.nextFloat() * 5F;
            tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
            
            ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //get attack value
          float atk = getAttackBaseDamage(2, target);
        
        //spawn missile
        summonMissile(2, atk, tarX, tarY, tarZ, target.height);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        applyAttackPostMotion(2, target, true, atk);
        
        return true;
    }
    
    /**
     * spawn attack missile, used in light or heavy attack method
     * 
     * attackType: 0:melee, 1:light, 2:heavy
     */
    public void summonMissile(int attackType, float atk, float tarX, float tarY, float tarZ, float targetHeight)
    {
        //missile type
        float launchPos = (float) posY + height * 0.5F;
        if (this.isMorph) launchPos += 0.5F;
        int moveType = CombatHelper.calcMissileMoveType(this, tarY, attackType);
        if (moveType == 0) launchPos = (float) posY + height * 0.3F;
        
        MissileData md = this.getMissileData(attackType);
        float[] data = new float[] {atk, 0.15F, launchPos, tarX, tarY + targetHeight * 0.1F, tarZ, 140, 0.25F, md.vel0, md.accY1, md.accY2};
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
        this.world.spawnEntity(missile);
    }
    
    /** apply motion after attack
     *  type: 0:melee, 1:light, 2:heavy, 3:light air, 4:heavy air
     */
    public void applyAttackPostMotion(int type, Entity target, boolean isTargetHurt, float atk) {}

    @Override
    public boolean updateSkillAttack(Entity target)
    {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float atk)
    {
        if (this.world.isRemote || this.morphHost != null) return false;
        
        boolean checkDEF = true;

        //change sensitive body
          if (this.rand.nextInt(10) == 0) randomSensitiveBody();
          
        //damage disabled
        if (source == DamageSource.inWall || source == DamageSource.starve ||
            source == DamageSource.cactus || source == DamageSource.fall)
        {
            return false;
        }
        //damage ignore def value
        else if (source == DamageSource.magic || source == DamageSource.dragonBreath)
        {
            //ignore atk < 1% max hp
            if (atk < this.getMaxHealth() * 0.01F) return false;
            
            //set hurt face
            this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
            
            return super.attackEntityFrom(source, atk);
        }
        //out of world
        else if (source == DamageSource.outOfWorld)
        {
            //取消坐下動作
            this.setSitting(false);
            this.dismountRidingEntity();
            this.setPositionAndUpdate(this.posX, 4D, this.posZ);
            this.motionX = 0D;
            this.motionY = 1D;
            this.motionZ = 0D;
            return false;
        }
        
        //check attacker is potion
        float patk = BuffHelper.getPotionDamage(this, source, atk);
        
        if (patk > 0F)
        {
            atk = patk;
            checkDEF = false;
        }
        
        //若攻擊方為owner, 則直接回傳傷害, 不計def跟friendly fire
        if (source.getEntity() instanceof EntityPlayer &&
            TeamHelper.checkSameOwner(source.getEntity(), this))
        {
            this.setSitting(false);
            
            //set hurt face
            this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
            
            return super.attackEntityFrom(source, atk);
        }
        
        //無敵的entity傷害無效
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (source.getEntity() != null)
        {
            Entity attacker = source.getEntity();
            
            //不會對自己造成傷害, 可免疫毒/掉落/窒息等傷害 (此為自己對自己造成傷害)
            if (attacker.equals(this))
            {
                //取消坐下動作
                this.setSitting(false);
                return false;
            }
            
            //若攻擊方為player, 則檢查friendly fire
            if (attacker instanceof EntityPlayer)
            {
                //若禁止friendlyFire, 則不造成傷害
                if (!ConfigHandler.friendlyFire)
                {
                    return false;
                }
            }
            
            //進行dodge計算
            float dist = (float) this.getDistanceSqToEntity(attacker);
            
            if (CombatHelper.canDodge(this, dist))
            {
                return false;
            }
            
            //進行def計算
            float reducedAtk = atk;
            
            if (checkDEF)
            {
                reducedAtk = CombatHelper.applyDamageReduceByDEF(this.rand, this.shipAttrs, reducedAtk);
            }
            
            //ship vs ship, config傷害調整 (僅限友善船)
            if (attacker instanceof IShipOwner && ((IShipOwner)attacker).getPlayerUID() > 0 &&
                (attacker instanceof BasicEntityShip ||
                 attacker instanceof BasicEntitySummon || 
                 attacker instanceof BasicEntityMount))
            {
                reducedAtk = reducedAtk * (float)ConfigHandler.dmgSvS * 0.01F;
            }
            
            //check resist potion
            reducedAtk = BuffHelper.applyBuffOnDamageByResist(this, source, reducedAtk);

            //check night vision potion
            reducedAtk = BuffHelper.applyBuffOnDamageByLight(this, source, reducedAtk);
            
            //tweak min damage
            if (reducedAtk < 1F && reducedAtk > 0F) reducedAtk = 1F;
            else if (reducedAtk <= 0F) reducedAtk = 0F;

            //取消坐下動作
            this.setSitting(false);
            
            //設置revenge target
            this.setEntityRevengeTarget(attacker);
            this.setEntityRevengeTime();
            
            //若傷害力可能致死, 則尋找物品中有無repair goddess來取消掉此攻擊
            if (reducedAtk >= (this.getHealth() - 1F))
            {
                if (this.decrSupplies(8))
                {
                    this.setHealth(this.getMaxHealth());
                    this.StateTimer[ID.T.ImmuneTime] = 120;
                    
                    //add repair goddess particle
                    TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
                    CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, 0D, 0.03D, 0D), point);
                    
                    return false;
                }
            }
            
              //morale--
            decrMorale(5);
              setCombatTick(this.ticksExisted);
              
              //set damaged body ID and show emotes
              if (this.rand.nextInt(5) == 0)
              {
                //set hit position
                this.setStateMinor(ID.M.HitHeight, CalcHelper.getEntityHitHeight(this, source.getSourceOfDamage()));
                this.setStateMinor(ID.M.HitAngle, CalcHelper.getEntityHitSide(this, source.getSourceOfDamage()));
                
                //apply emotes
                applyEmotesReaction(2);
              }
              
              //set hurt face
            this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
            
            return super.attackEntityFrom(source, reducedAtk);
        }
        
        return false;
    }
    
    //range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
    @Override
    public boolean attackEntityWithAircraft(Entity target)
    {
        //light ammo--
        if (this.getNumAircraftLight() <= 0 ||
            !decrAmmoNum(0, 6 * this.getAmmoConsumption())) return false;
        
        //50% clear target every attack
        if (this.rand.nextInt(2) == 0) this.setEntityTarget(null);
        
        //num aircraft--, number check in carrier AI
        this.setNumAircraftLight(this.getNumAircraftLight()-1);
        
        //experience++
        addShipExp(ConfigHandler.expGain[3]);
        
        //grudge--
        decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAir]);
        
        //morale--
        decrMorale(3);
        setCombatTick(this.ticksExisted);
        
        //play attacker effect
        applySoundAtAttacker(3, target);
        applyParticleAtAttacker(3, target, Dist4d.ONE);
        
        float summonHeight = (float)(posY + launchHeight);
        
        //check the summon block
        if (!BlockHelper.checkBlockSafe(world, (int)posX, (int)(posY+launchHeight), (int)(posZ)))
        {
            summonHeight = (float)posY + 1F;
        }
        
        if (this.getRidingEntity() instanceof BasicEntityMount)
        {
            summonHeight -= 1.5F;
        }
        
        //spawn airplane
        BasicEntityAirplane plane = getAttackAirplane(true);
        plane.initAttrs(this, target, 0, summonHeight);
        this.world.spawnEntity(plane);
        
        //play target effect
        applySoundAtTarget(3, target);
        applyParticleAtTarget(3, target, Dist4d.ONE);
        applyEmotesReaction(3);
        
        applyAttackPostMotion(3, target, true, 0F);
        
        return true;
    }

    //range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
    @Override
    public boolean attackEntityWithHeavyAircraft(Entity target)
    {
        //heavy ammo--
        if (this.getNumAircraftHeavy() <= 0 ||
            !decrAmmoNum(1, 2 * this.getAmmoConsumption())) return false;
        
        //50% clear target every attack
        if (this.rand.nextInt(2) == 0) this.setEntityTarget(null);
        
        //num aircraft--, number check in carrier AI
        this.setNumAircraftHeavy(this.getNumAircraftHeavy()-1);
        
        //experience++
        addShipExp(ConfigHandler.expGain[4]);
        
        //grudge--
        decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAir]);
        
        //morale--
        decrMorale(4);
        setCombatTick(this.ticksExisted);
        
        //play attacker effect
        applySoundAtAttacker(4, target);
        applyParticleAtAttacker(4, target, Dist4d.ONE);
        
        float summonHeight = (float)(posY + launchHeight);
        
        //check the summon block
        if (!BlockHelper.checkBlockSafe(world, (int)posX, (int)(posY+launchHeight), (int)(posZ)))
        {
            summonHeight = (float)posY + 0.5F;
        }
        
        if (this.getRidingEntity() instanceof BasicEntityMount)
        {
            summonHeight -= 1.5F;
        }
        
        //spawn airplane
        BasicEntityAirplane plane = getAttackAirplane(false);
        plane.initAttrs(this, target, 0, summonHeight);
        this.world.spawnEntity(plane);
        
        //play target effect
        applySoundAtTarget(4, target);
        applyParticleAtTarget(4, target, Dist4d.ONE);
        applyEmotesReaction(3);
        
        applyAttackPostMotion(4, target, true, 0F);
        
        return true;
    }
    
    /** attack particle at attacker
     * 
     *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
     */
    public void applyParticleAtAttacker(int type, Entity target, Dist4d distance)
    {
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
      
        switch (type)
        {
        case 1:  //light cannon
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, distance.x, distance.y, distance.z, true), point);
        break;
        case 2:  //heavy cannon
        case 3:  //light aircraft
        case 4:  //heavy aircraft
      default: //melee
          CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
      break;
        }
    }
    
    /** attack particle at target
     * 
     *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
     */
    public void applyParticleAtTarget(int type, Entity target, Dist4d distance)
    {
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
        switch (type)
        {
        case 1:  //light cannon
          CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point);
        break;
        case 2:  //heavy cannon
        break;
        case 3:  //light aircraft
        break;
        case 4:  //heavy aircraft
        break;
      default: //melee
          CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
      break;
        }
    }
    
    
    
    /** attack particle at target
     * 
     *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
     */
    public void applySoundAtTarget(int type, Entity target)
    {
        switch (type)
        {
        case 1:  //light cannon
        break;
        case 2:  //heavy cannon
        break;
        case 3:  //light aircraft
        break;
        case 4:  //heavy aircraft
        break;
      default: //melee
      break;
        }
    }
    
    
    
    /** set flare on target */
    public void flareTarget(Entity target)
    {
        //server side, send flare packet
        if (!this.world.isRemote)
        {
              if (this.getStateMinor(ID.M.LevelFlare) > 0 && target != null)
              {
                  this.flareTarget(target.getPosition());
              }
        }
    }
    
    public void flareTarget(BlockPos target)
    {
        //server side, send flare packet
        if (!this.world.isRemote)
        {
            if (this.getStateMinor(ID.M.LevelFlare) > 0 && target != null)
              {
              TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
              CommonProxy.channelI.sendToAllAround(new S2CReactPackets(S2CReactPackets.PID.FlareEffect, target.getX(), target.getY(), target.getZ()), point);
              }
        }
    }
    
    @Override
    public MissileData getMissileData(int type)
    {
        return this.MissileData[type];
    }

    @Override
    public void setMissileData(int type, MissileData data)
    {
        this.MissileData[type] = data;
    }
    
    //init missile data
    public void resetMissileData()
    {
        this.MissileData = new MissileData[5];
        for (int i = 0; i < 5; i++) this.MissileData[i] = new MissileData();
    }
    
    
}