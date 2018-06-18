package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.init.SoundEvents;

/**
 * ship states handler, get data by {@link ID.Keys}
 */
public class ShipStateHandler extends StateHandler
{
    
    /** convenient ship entity to use */
    private BasicEntityShip ship;
    /** ship attributes: hp, def, atk, ... */
    protected AttrsAdv shipAttrs;
    
    
    public ShipStateHandler(BasicEntityShip host)
    {
        super(host);
        
        this.ship = host;
    }
    
    /** init data on new object */
    @Override
    protected void initFirst()
    {
        super.initFirst();
        
        //init numbers and flags
        this.setBooleanState(ID.Keys.CanFloatUp, false);
        this.setBooleanState(ID.Keys.CanDropItem, true);
        this.setBooleanState(ID.Keys.CanFollow, false);
        this.setBooleanState(ID.Keys.CanOnSightChase, true);
        this.setBooleanState(ID.Keys.CanAtkLight, true);
        this.setBooleanState(ID.Keys.CanAtkHeavy, true);
        this.setBooleanState(ID.Keys.CanAtkAirLight, true);
        this.setBooleanState(ID.Keys.CanAtkAirHeavy, true);
        this.setBooleanState(ID.Keys.UseMelee, false);
        this.setBooleanState(ID.Keys.UseAtkLight, true);
        this.setBooleanState(ID.Keys.UseAtkHeavy, true);
        this.setBooleanState(ID.Keys.UseAtkAirLight, true);
        this.setBooleanState(ID.Keys.UseAtkAirHeavy, true);
        this.setNumberState(ID.Keys.Phase, 0);
        this.setBooleanState(ID.Keys.CanRingEffect, true);
        this.setBooleanState(ID.Keys.UseRingEffect, true);
        this.setBooleanState(ID.Keys.CanPickItem, false);
        this.setBooleanState(ID.Keys.UsePickItem, true);
        this.setBooleanState(ID.Keys.UseAutoPump, false);
        this.setNumberState(ID.Keys.FollowMin, 3);
        this.setNumberState(ID.Keys.FollowMax, 12);
        this.setNumberState(ID.Keys.FleeHP, 35);
        this.setNumberState(ID.Keys.CombatRationLevel, 3);
        this.setNumberState(ID.Keys.GuardType, 0);
        this.setNumberState(ID.Keys.GuardX, -1);
        this.setNumberState(ID.Keys.GuardY, -1);
        this.setNumberState(ID.Keys.GuardZ, -1);
        this.setNumberState(ID.Keys.GuardDim, 0);
        this.setNumberState(ID.Keys.GuardID, -1);
        this.setNumberState(ID.Keys.EquipType, 2);
        this.setNumberState(ID.Keys.DamageType, 0);
        this.setNumberState(ID.Keys.FormatType, 0);
        this.setNumberState(ID.Keys.FormatPos, 0);
        this.setNumberState(ID.Keys.ShipDepth, 0D);     //double
        this.setNumberState(ID.Keys.Task, -1);
        this.setNumberState(ID.Keys.TaskSide, -1);
        this.setNumberState(ID.Keys.TaskTime, 0);
        this.setNumberState(ID.Keys.CraneState, 0);
        this.setNumberState(ID.Keys.CraneTime, 0);
        this.setNumberState(ID.Keys.CraneDelay, 0);
        this.setNumberState(ID.Keys.WpStay, 0);
        this.setNumberState(ID.Keys.WpStayTime, 0);
        this.setBooleanState(ID.Keys.IsMarried, false);
        this.setBooleanState(ID.Keys.IsNoFuel, false);
        this.setBooleanState(ID.Keys.IsHeadTilt, false);
        this.setBooleanState(ID.Keys.IsPVPFirst, true);
        this.setBooleanState(ID.Keys.IsAntiAir, false);
        this.setBooleanState(ID.Keys.IsAntiSS, false);
        this.setBooleanState(ID.Keys.IsPassive, false);
        this.setBooleanState(ID.Keys.IsTimeKeeper, true);
        this.setBooleanState(ID.Keys.ShowHeldItem, true);
        this.setNumberState(ID.Keys.ModelStateNum, 1);
        this.setNumberState(ID.Keys.ModelState, 0);
        this.setNumberState(ID.Keys.Emotion, 0);
        this.setNumberState(ID.Keys.Emotion2, 0);
        this.setNumberState(ID.Keys.Emotion3, 0);
        this.setNumberState(ID.Keys.Emotion4, 0);
        this.setNumberState(ID.Keys.HPState, 0);
        this.setNumberState(ID.Keys.TimeEmotion3, 0);
        this.setNumberState(ID.Keys.TimeSound, 0);
        this.setNumberState(ID.Keys.TimeFace, 0);
        this.setNumberState(ID.Keys.TimeHeadTilt, 0);
        this.setNumberState(ID.Keys.EmoteDelay, 0);
        this.setNumberState(ID.Keys.ShipType, 0);
        this.setNumberState(ID.Keys.IconType, 0);
        this.setNumberState(ID.Keys.ShipClass, 0);
        this.setNumberState(ID.Keys.ShipLevel, 1);
        this.setNumberState(ID.Keys.ShipUID, -1);
        this.setNumberState(ID.Keys.PlayerUID, -1);
        this.setNumberState(ID.Keys.PlayerEID, -1);
        this.setNumberState(ID.Keys.Kills, 0);
        this.setNumberState(ID.Keys.ExpCurrent, 0);
        this.setNumberState(ID.Keys.ExpNext, this.calcExpNext(1));
        this.setNumberState(ID.Keys.GrudgeNumber, 0);
        this.setNumberState(ID.Keys.AmmoLightNumber, 0);
        this.setNumberState(ID.Keys.AmmoHeavyNumber, 0);
        this.setNumberState(ID.Keys.AirLightNumber, 0);
        this.setNumberState(ID.Keys.AirHeavyNumber, 0);
        this.setNumberState(ID.Keys.GrudgeConsume, 0);
        this.setNumberState(ID.Keys.AmmoConsume, 0);
        this.setNumberState(ID.Keys.Morale, 60);
        this.setNumberState(ID.Keys.Food, 0);
        this.setNumberState(ID.Keys.FoodMax, 10);
        this.setNumberState(ID.Keys.HitHeight, 0);
        this.setNumberState(ID.Keys.HitAngle, 0);
        this.setNumberState(ID.Keys.SensBody, -1);
        this.setNumberState(ID.Keys.ChunkLoaderLevel, 0);
        this.setNumberState(ID.Keys.FlareLevel, 0);
        this.setNumberState(ID.Keys.SearchlightLevel, 0);
        this.setNumberState(ID.Keys.XP, 0);
        this.setNumberState(ID.Keys.ImmuneTime, 0);
        this.setNumberState(ID.Keys.RevengeTime, 0);
        this.setNumberState(ID.Keys.LastCombatTime, 0);
        this.setNumberState(ID.Keys.AttackTime, 0);
        this.setNumberState(ID.Keys.AttackTime2, 0);
        this.setNumberState(ID.Keys.AttackTime3, 0);
        this.setNumberState(ID.Keys.MountSkillCD1, 0);
        this.setNumberState(ID.Keys.MountSkillCD2, 0);
        this.setNumberState(ID.Keys.MountSkillCD3, 0);
        this.setNumberState(ID.Keys.MountSkillCD4, 0);
        this.setNumberState(ID.Keys.MountSkillCD5, 0);
        this.setBooleanState(ID.Keys.UpdateAttrsBuffed, false);
        this.setBooleanState(ID.Keys.UpdateAttrsBonus, false);
        this.setBooleanState(ID.Keys.UpdateAttrsEquip, false);
        this.setBooleanState(ID.Keys.UpdateAttrsMorale, false);
        this.setBooleanState(ID.Keys.UpdateAttrsPotion, false);
        this.setBooleanState(ID.Keys.UpdateAttrsFormation, false);
        this.setBooleanState(ID.Keys.UpdateAttrsRaw, false);
        this.setBooleanState(ID.Keys.UpdateFormatBuff, false);
        this.setStringState(ID.Keys.OwnerName, "");
        
    }
    
    /** init data at the end of {@link BasicEntityShip#initPre()} */
    @Override
    public void initPre()
    {
        //init ship attrs
        this.shipAttrs = new AttrsAdv(this.getShipClass());
    }
    
    /** init data at the end of {@link BasicEntityShip#initPost()} */
    @Override
    public void initPost()
    {
    }
    
    /*
     *       getter / setter
     */
    
    public AttrsAdv getAttrs()
    {
        return this.shipAttrs;
    }
    
    public int getShipClass()
    {
        return this.getStateInt(ID.Keys.ShipClass);
    }
    
    public void setShipClass(short value)
    {
        this.setNumberState(ID.Keys.ShipClass, value);  //ID.ShipClass
    }
    
    public int getShipType()
    {
        return this.getStateInt(ID.Keys.ShipType);
    }
    
    public void setShipType(byte value)
    {
        this.setNumberState(ID.Keys.ShipType, value);  //ID.ShipType
    }
    
    public int getShipIcon()
    {
        return this.getStateInt(ID.Keys.IconType);
    }
    
    public void setShipIcon(byte value)
    {
        this.setNumberState(ID.Keys.IconType, value);  //ID.IconType
    }
    
    public int getShipLevel()
    {
        return this.getStateInt(ID.Keys.ShipLevel);
    }
    
    public void setShipLevel(int value)
    {
        this.setNumberState(ID.Keys.ShipLevel, value);
    }
    
    public int getShipUID()
    {
        return this.getStateInt(ID.Keys.ShipUID);
    }
    
    public void setShipUID(int value)
    {
        this.setNumberState(ID.Keys.ShipUID, value);
    }
    
    public int getOwnerUID()
    {
        return this.getStateInt(ID.Keys.PlayerUID);
    }
    
    public void setOwnerUID(int value)
    {
        this.setNumberState(ID.Keys.PlayerUID, value);
    }
    
    public int getOwnerEID()
    {
        return this.getStateInt(ID.Keys.PlayerEID);
    }
    
    public void setOwnerEID(int value)
    {
        this.setNumberState(ID.Keys.PlayerEID, value);
    }
    
    public int getShipDamageType()
    {
        return this.getStateInt(ID.Keys.ShipType);
    }
    
    public void setShipDamageType(byte value)
    {
        this.setNumberState(ID.Keys.ShipType, value);  //ID.ShipDmgType
    }
    
    public int getShipStateNumber()
    {
        return this.getStateInt(ID.Keys.ShipType);
    }
    
    public void setShipStateNumber(byte value)
    {
        this.setNumberState(ID.Keys.ShipType, value);  //max state number <= 32
    }
    
    /** change ship outfit (model state) */
    public void setShipOutfit(int id)
    {
        if (id > this.getStateByte(ID.Keys.ModelStateNum) || id < 0) id = 0;
        this.setNumberState(ID.Keys.ModelState, this.getStateInt(ID.Keys.ModelState) ^ Values.N.Pow2[id]);
    }
    
    public byte getSensitiveBody()
    {
        return this.getStateByte(ID.Keys.SensBody);
    }
    
    public void setSensitiveBody(byte value)
    {
        this.setNumberState(ID.Keys.SensBody, value);
    }
    
    /** set random sensitive body id, ref: ID.Body */
    public void randomSensitiveBody()
    {
        int ran = this.ship.getRNG().nextInt(100);
        byte bodyid = 20;
        
        //first roll
        if (ran > 80)
        {  //20%
            bodyid = ID.Body.UBelly;
        }
        else if (ran > 65)
        {  //15%
            bodyid = ID.Body.Chest;
        }
        else
        {  //55%
            bodyid = (byte) (3 + this.ship.getRNG().nextInt(8));  //roll 3~10
        }
        
        //reroll if HEAD/BACK
        if (bodyid == ID.Body.Head || bodyid == ID.Body.Back) bodyid = ID.Body.UBelly;
        if (bodyid == ID.Body.Arm || bodyid == ID.Body.Butt) bodyid = ID.Body.Chest;
        
        this.setSensitiveBody(bodyid);
    }
    
    /** 1:cannon only, 2:both, 3:aircraft only */
    public int getShipEquipType()
    {
        return this.getStateInt(ID.Keys.EquipType);
    }
    
    /** @see #getEquipType() */
    public void setShipEquipType(byte value)
    {
        this.setNumberState(ID.Keys.EquipType, value);  //max equip type < 3
    }
    
    public int getGrudgeConsumption()
    {
        return this.getStateInt(ID.Keys.GrudgeConsume);
    }
    
    public void setGrudgeConsumption(int value)
    {
        this.setNumberState(ID.Keys.GrudgeConsume, value);
    }
    
    public int getAmmoConsumption()
    {
        return this.getStateInt(ID.Keys.AmmoConsume);
    }
    
    public void setAmmoConsumption(int value)
    {
        this.setNumberState(ID.Keys.AmmoConsume, value);
    }
    
    public int getFoodSaturation()
    {
        return this.getStateInt(ID.Keys.Food);
    }
    
    public void setFoodSaturation(int value)
    {
        this.setNumberState(ID.Keys.Food, value);
    }
    
    public int getFoodSaturationMax()
    {
        return this.getStateInt(ID.Keys.FoodMax);
    }
    
    public void setFoodSaturationMax(int value)
    {
        this.setNumberState(ID.Keys.FoodMax, value);
    }
    
    public byte getDamageType()
    {
        return this.getStateByte(ID.Keys.DamageType);
    }
    
    public void setDamageType(byte value)
    {
        this.setNumberState(ID.Keys.DamageType, value);
    }
    
    public boolean canAttackLight()
    {
        return this.getBooleanState(ID.Keys.CanAtkLight);
    }
    
    public void setCanAttackLight(boolean value)
    {
        this.setBooleanState(ID.Keys.CanAtkLight, value);
    }
    
    public boolean canAttackHeavy()
    {
        return this.getBooleanState(ID.Keys.CanAtkHeavy);
    }
    
    public void setCanAttackHeavy(boolean value)
    {
        this.setBooleanState(ID.Keys.CanAtkHeavy, value);
    }
    
    public boolean canAttackAirLight()
    {
        return this.getBooleanState(ID.Keys.CanAtkAirLight);
    }
    
    public void setCanAttackAirLight(boolean value)
    {
        this.setBooleanState(ID.Keys.CanAtkAirLight, value);
    }
    
    public boolean canAttackAirHeavy()
    {
        return this.getBooleanState(ID.Keys.CanAtkAirHeavy);
    }
    
    public void setCanAttackAirHeavy(boolean value)
    {
        this.setBooleanState(ID.Keys.CanAtkAirHeavy, value);
    }
    
    public boolean useAttackMelee()
    {
        return this.getBooleanState(ID.Keys.UseMelee);
    }
    
    public void setUseAttackMelee(boolean value)
    {
        this.setBooleanState(ID.Keys.UseMelee, value);
        
        //reset AI
        this.ship.getAIHandler().resetShipAI();
    }
    
    public boolean useAttackLight()
    {
        return this.getBooleanState(ID.Keys.UseAtkLight);
    }
    
    public void setUseAttackLight(boolean value)
    {
        this.setBooleanState(ID.Keys.UseAtkLight, value);
    }
    
    public boolean useAttackHeavy()
    {
        return this.getBooleanState(ID.Keys.UseAtkHeavy);
    }
    
    public void setUseAttackHeavy(boolean value)
    {
        this.setBooleanState(ID.Keys.UseAtkHeavy, value);
    }
    
    public boolean useAttackAirLight()
    {
        return this.getBooleanState(ID.Keys.UseAtkAirLight);
    }
    
    public void setUseAttackAirLight(boolean value)
    {
        this.setBooleanState(ID.Keys.UseAtkAirLight, value);
    }
    
    public boolean useAttackAirHeavy()
    {
        return this.getBooleanState(ID.Keys.UseAtkAirHeavy);
    }
    
    public void setUseAttackAirHeavy(boolean value)
    {
        this.setBooleanState(ID.Keys.UseAtkAirHeavy, value);
    }
    
    public int getMorale()
    {
        return this.getStateInt(ID.Keys.Morale);
    }
    
    public void setMorale(int value)
    {
        this.setNumberState(ID.Keys.Morale, value);
    }
    
    public int getGrudge()
    {
        return this.getStateInt(ID.Keys.GrudgeNumber);
    }
    
    public void setGrudge(int value)
    {
        this.setNumberState(ID.Keys.GrudgeNumber, value);
    }
    
    public int getAmmoLight()
    {
        return this.getStateInt(ID.Keys.AmmoLightNumber);
    }
    
    public int getAmmoHeavy()
    {
        return this.getStateInt(ID.Keys.AmmoHeavyNumber);
    }
    
    public int getAirplaneLight()
    {
        return this.getStateInt(ID.Keys.AirLightNumber);
    }
    
    public int getAirplaneHeavy()
    {
        return this.getStateInt(ID.Keys.AirHeavyNumber);
    }
    
    public int getFaceTick()
    {
        return this.getStateInt(ID.Keys.TimeFace);
    }
    
    public void setFaceTick(int value)
    {
        this.setNumberState(ID.Keys.TimeFace, value);
    }
    
    public int getHeadTiltTick()
    {
        return this.getStateInt(ID.Keys.TimeHeadTilt);
    }
    
    public void setHeadTiltTick(int value)
    {
        this.setNumberState(ID.Keys.TimeHeadTilt, value);
    }
    
    public int getAttackTick()
    {
        return this.getStateInt(ID.Keys.AttackTime);
    }
    
    public void setAttackTick(int value)
    {
        this.setNumberState(ID.Keys.AttackTime, value);
    }
    
    public int getAttackTick2()
    {
        return this.getStateInt(ID.Keys.AttackTime2);
    }
    
    public void setAttackTick2(int value)
    {
        this.setNumberState(ID.Keys.AttackTime2, value);
    }
    
    public int getAttackTick3()
    {
        return this.getStateInt(ID.Keys.AttackTime3);
    }
    
    public void setAttackTick3(int value)
    {
        this.setNumberState(ID.Keys.AttackTime3, value);
    }
    
    public int getRevengeTime()
    {
        return this.getStateInt(ID.Keys.RevengeTime);
    }
    
    public void setRevengeTime(int value)
    {
        this.setNumberState(ID.Keys.RevengeTime, value);
    }
    
    /** last combat time */
    public int getLastCombatTick()
    {
        return this.getStateInt(ID.Keys.LastCombatTime);
    }
    
    /** @see #getLastCombatTick() */
    public void setLastCombatTick(int value)
    {
        this.setNumberState(ID.Keys.LastCombatTime, value);
    }
    
    /** show emote cooldown */
    public int getEmoteDelay()
    {
        return this.getStateInt(ID.Keys.EmoteDelay);
    }
    
    /** @see #getEmoteDelay() */
    public void setEmoteDelay(int value)
    {
        this.setNumberState(ID.Keys.EmoteDelay, value);
    }
    
    public int getCraneDelay()
    {
        return this.getStateInt(ID.Keys.CraneDelay);
    }
    
    public void setCraneDelay(int value)
    {
        this.setNumberState(ID.Keys.CraneDelay, value);
    }
    
    public int getCraneTime()
    {
        return this.getStateInt(ID.Keys.CraneTime);
    }
    
    public void setCraneTime(int value)
    {
        this.setNumberState(ID.Keys.CraneTime, value);
    }
    
    /** pointer item click height on entity, sole = 0, headtop = 100 */
    public int getHitHeight()
    {
        return this.getStateInt(ID.Keys.HitHeight);
    }
    
    /** @see #getHitHeight() */
    public void setHitHeight(int value)
    {
        if (value > 120) value = 120;
        else if (value < -20) value = -20;
        
        this.setNumberState(ID.Keys.HitHeight, (byte) value);
    }
    
    public Enums.BodyHeight getBodyIDFromHeight()
    {
        return EntityHelper.getBodyIDFromHeight(this.getHitHeight(), this.ship);
    }
    
    public Enums.BodySide getHitAngleID()
    {
        return EntityHelper.getHitAngleID(this.getHitAngle());
    }
    
    /**
     * pointer item click angle on entity, value = 0 ~ -360
     *   front: -180
     *   back: 0/-360
     *   right:-90
     *   left:-270
     */
    public int getHitAngle()
    {
        return this.getStateInt(ID.Keys.HitHeight);
    }
    
    /** @see #getHitAngle() */
    public void setHitAngle(int value)
    {
        value %= 360;
        
        this.setNumberState(ID.Keys.HitHeight, (short) value);
    }
    
    public int getTickExisted()
    {
        return this.ship.ticksExisted;
    }
    
    public int getKills()
    {
        return this.getStateInt(ID.Keys.Kills);
    }
    
    public void addKills(int value)
    {
        this.addNumberState(ID.Keys.Kills, value);
    }
    
    public void addMorale(int value)
    {
        int n = this.getStateInt(ID.Keys.Morale) + value;
        
        if (n < 0) n = 0;
        else if (n > 16000) n = 16000;
        
        this.setNumberState(ID.Keys.Morale, n);
    }
    
    public void addAmmoLight(int value)
    {
        int n = this.getStateInt(ID.Keys.AmmoLightNumber) + value;
        
        if (n < 0) n = 0;
        
        this.setNumberState(ID.Keys.AmmoLightNumber, n);
    }
    
    public void addAmmoHeavy(int value)
    {
        int n = this.getStateInt(ID.Keys.AmmoHeavyNumber) + value;
        
        if (n < 0) n = 0;
        
        this.setNumberState(ID.Keys.AmmoHeavyNumber, n);
    }
    
    /** 0:ready, 1:waiting, 2:craning */
    public byte getCraneState()
    {
        return this.getStateByte(ID.Keys.CraneState);
    }
    
    /** @see #getCraneState() */
    public void setCraneState(byte value)
    {
        //if READY -> WAITING, check delay
        if (value == 1)
        {
            if (this.getCraneDelay() > 0) return;
            else
            {
                this.setCraneTime(20);
            }
        }
        
        this.setNumberState(ID.Keys.CraneState, value);
    }
    
    public boolean isRiding()
    {
        return this.ship.isRiding();
    }
    
    public boolean isLeashed()
    {
        return this.ship.getLeashed();
    }

    public boolean isSprinting()
    {
        return this.ship.isSprinting() || this.ship.limbSwingAmount > 0.9F;
    }

    public boolean isSitting()
    {
        return this.ship.isSitting();
    }

    public boolean isSneaking()
    {
        return this.ship.isSneaking();
    }
    
    public float getMoveSpeed()
    {
        return this.shipAttrs.getMoveSpeed(false);
    }
    
    public float getJumpSpeed()
    {
        return 1F;
    }
    
    /** get depth: 0:self depth, 1:mounts depth */
    public double getShipDepth(boolean getMountDepth)
    {
        if (getMountDepth && this.ship.getRidingEntity() instanceof IStateMinion)
        {
            return ((IStateMinion) this.ship.getRidingEntity()).getStateHandler().getShipDepth(false);
        }
        
        return this.getStateDouble(ID.Keys.ShipDepth);
    }
    
    public void setShipDepth(double value)
    {
        this.setNumberState(ID.Keys.ShipDepth, value);
    }
    
    public boolean hasNoFuel()
    {
        //treat death as no fuel
        if (!this.ship.isEntityAlive() || this.ship.deathTime > 0) return true;
        
        return this.getBooleanState(ID.Keys.IsNoFuel);
    }
    
    public static int calcExpNext(int level)
    {
        int exp = (level + 1) * ConfigHandler.expMod;
        if (exp < 1) exp = 1;
        
        return exp;
    }
    
    /** add exp, called SERVER SIDE ONLY */
    public void addShipExp(int exp)
    {
        int capLevel = this.getBooleanState(ID.Keys.IsMarried) ? ConfigHandler.maxLevel : ConfigHandler.midLimitLevel;
        int curLevel = this.getStateInt(ID.Keys.ShipLevel);
        
        //if not cap level
        if (curLevel < capLevel)
        {
            //check morph
            if (this.ship.isMorph()) exp = (int) ((float)exp * ConfigHandler.expGainPlayerSkill);
        
            //apply equip effect
            exp = (int) ((float)exp * this.shipAttrs.getAttrsBuffed(ID.Attrs.XP));
            
            int curExp = this.getStateInt(ID.Keys.ExpCurrent) + exp;
            int nextExp = this.getStateInt(ID.Keys.ExpCurrent);
            
            //level++
            while (curExp >= nextExp || curLevel >= capLevel)
            {
                //level up sound
                this.ship.world.playSound(null, this.ship.posX, this.ship.posY, this.ship.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.ship.getSoundCategory(), 0.7F, 1F);
                
                if (this.ship.getRNG().nextInt(4) == 0)
                {
                    this.ship.playSound(ModSounds.SHIP_LEVEL, ConfigHandler.volumeShip, 1F);
                }
                
                curLevel++;
                curExp -= nextExp;
                nextExp = this.calcExpNext(curLevel);
            }
            
            this.setNumberState(ID.Keys.ExpCurrent, curExp);
            this.setNumberState(ID.Keys.ExpNext, nextExp);
            this.addShipLevel(curLevel, true);
        }//end below cap level
    }
    
    //called when entity level up
    public void addShipLevel(int targetLevel, boolean update)
    {
        //set level
        this.setShipLevel(targetLevel);
        
        //update attributes
        if (update)
        {
            BasicEntityShip.calcShipAttributes(this.ship, 31, true);
            this.ship.setHealth(this.ship.getMaxHealth());
        }
    }
    
    public void decrMorale(Enums.CostType type)
    {
        if (this.ship.isMorph()) return;
        
        switch (type)
        {
        case ATTACK_MELEE:
            this.addMorale(-2);
        break;
        case ATTACK_LIGHT:
            this.addMorale(-4);
        break;
        case ATTACK_HEAVY:
            this.addMorale(-6);
        break;
        case ATTACK_AIR_LIGHT:
            this.addMorale(-6);
        break;
        case ATTACK_AIR_HEAVY:
            this.addMorale(-8);
        break;
        case DAMAGED:  //damaged
            this.addMorale(-5);
        break;
        default:
        break;
        }
    }
    
    
}