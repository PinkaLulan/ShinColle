package com.lulan.shincolle.handler;


import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.AtkType;
import com.lulan.shincolle.reference.Enums.AttrBoo;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.reference.dataclass.MissileData;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * handler for attack methods
 */
public class AttackHandler
{
    
    /** host object */
    protected IAttackEntity host;
    /** host's missile data */
    protected MissileData missileData;
    /**
     * host's attack type, 5 slots, default:
     *   0:GENERIC_MELEE
     *   1:GENERIC_LIGHT
     *   2:GENERIC_HEAVY_LAUNCH
     *   3:GENERIC_AIR_LIGHT_LAUNCH
     *   4:GENERIC_AIR_HEAVY_LAUNCH
     */
    protected AtkType[] attackType;
    
    
    public AttackHandler(IAttackEntity host)
    {
        this.host = host;
        
        this.initFirst();
    }
    
    /** init data on construct */
    protected void initFirst()
    {
        AttrStateHandler sh = this.host.getStateHandler();
        
        //set default attack type
        this.attackType = new AtkType[5];
        this.attackType[0] = AtkType.GENERIC_MELEE;
        if (sh.getBooleanState(AttrBoo.CanAtkLight)) this.attackType[1] = AtkType.GENERIC_LIGHT;
        if (sh.getBooleanState(AttrBoo.CanAtkHeavy)) this.attackType[2] = AtkType.GENERIC_HEAVY_LAUNCH;
        if (sh.getBooleanState(AttrBoo.CanAtkAirLight)) this.attackType[3] = AtkType.GENERIC_AIR_LIGHT_LAUNCH;
        if (sh.getBooleanState(AttrBoo.CanAtkAirHeavy)) this.attackType[4] = AtkType.GENERIC_AIR_HEAVY_LAUNCH;
    }
    
    public void setAttackType(int slotID, AtkType type)
    {
        if (slotID >= 0 && slotID < this.attackType.length) this.attackType[slotID] = type;
    }
    
    public AtkType getAttackType(int slotID)
    {
        if (slotID >= 0 && slotID < this.attackType.length) return this.attackType[slotID];
        return null;
    }
    
    public MissileData getMissileData()
    {
        if (this.missileData == null) this.missileData = new MissileData();
        return this.missileData;
    }
    
    public void setMissileData(MissileData md)
    {
        if (md == null) this.missileData = new MissileData();
        else this.missileData = md;
    }
    
    
    
    
    
    
    
    
    /********************* TODO refactoring *********************/
    
    
    

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