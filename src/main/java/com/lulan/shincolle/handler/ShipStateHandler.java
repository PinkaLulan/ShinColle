package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.Enums.AttrBoo;
import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.reference.Enums.AttrStr;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.dataclass.AttrsAdv;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.init.SoundEvents;

/**
 * ship states handler, get data by {@link ID.Keys}
 */
public class ShipStateHandler extends AttrStateHandler
{
    
    /** host entity */
    protected BasicEntityShip host;
    /** ship attributes: hp, def, atk, ... */
    protected AttrsAdv attrs;
    
    
    public ShipStateHandler(BasicEntityShip host)
    {
        super(host);
        
        this.host = host;
    }
    
    @Override
    protected void initFirst()
    {
        super.initFirst();
        
        //init numbers and flags
        this.setBooleanState(AttrBoo.CanFloatUp, false);
        this.setBooleanState(AttrBoo.CanDropItem, true);
        this.setBooleanState(AttrBoo.CanFollow, false);
        this.setBooleanState(AttrBoo.CanOnSightChase, true);
        this.setBooleanState(AttrBoo.CanAtkLight, true);
        this.setBooleanState(AttrBoo.CanAtkHeavy, true);
        this.setBooleanState(AttrBoo.CanAtkAirLight, true);
        this.setBooleanState(AttrBoo.CanAtkAirHeavy, true);
        this.setBooleanState(AttrBoo.UseMelee, false);
        this.setBooleanState(AttrBoo.UseAtkLight, true);
        this.setBooleanState(AttrBoo.UseAtkHeavy, true);
        this.setBooleanState(AttrBoo.UseAtkAirLight, true);
        this.setBooleanState(AttrBoo.UseAtkAirHeavy, true);
        this.setNumberState(AttrNum.Phase, 0);
        this.setBooleanState(AttrBoo.CanRingEffect, true);
        this.setBooleanState(AttrBoo.UseRingEffect, true);
        this.setBooleanState(AttrBoo.CanPickItem, false);
        this.setBooleanState(AttrBoo.UsePickItem, true);
        this.setBooleanState(AttrBoo.UseAutoPump, false);
        this.setNumberState(AttrNum.FollowMin, 3);
        this.setNumberState(AttrNum.FollowMax, 12);
        this.setNumberState(AttrNum.FleeHP, 35);
        this.setNumberState(AttrNum.CombatRationLevel, 3);
        this.setNumberState(AttrNum.GuardType, 0);
        this.setNumberState(AttrNum.GuardX, -1);
        this.setNumberState(AttrNum.GuardY, -1);
        this.setNumberState(AttrNum.GuardZ, -1);
        this.setNumberState(AttrNum.GuardDim, 0);
        this.setNumberState(AttrNum.GuardID, -1);
        this.setNumberState(AttrNum.EquipType, 2);
        this.setNumberState(AttrNum.DamageType, 0);
        this.setNumberState(AttrNum.FormatType, 0);
        this.setNumberState(AttrNum.FormatPos, 0);
        this.setNumberState(AttrNum.ShipDepth, 0D);     //double
        this.setNumberState(AttrNum.Task, -1);
        this.setNumberState(AttrNum.TaskSide, -1);
        this.setNumberState(AttrNum.TaskTime, 0);
        this.setNumberState(AttrNum.CraneState, 0);
        this.setNumberState(AttrNum.CraneTime, 0);
        this.setNumberState(AttrNum.CraneDelay, 0);
        this.setNumberState(AttrNum.WpStay, 0);
        this.setNumberState(AttrNum.WpStayTime, 0);
        this.setBooleanState(AttrBoo.IsMarried, false);
        this.setBooleanState(AttrBoo.IsNoFuel, false);
        this.setBooleanState(AttrBoo.IsHeadTilt, false);
        this.setBooleanState(AttrBoo.IsPVPFirst, true);
        this.setBooleanState(AttrBoo.IsAntiAir, false);
        this.setBooleanState(AttrBoo.IsAntiSS, false);
        this.setBooleanState(AttrBoo.IsPassive, false);
        this.setBooleanState(AttrBoo.IsTimeKeeper, true);
        this.setBooleanState(AttrBoo.ShowHeldItem, true);
        this.setNumberState(AttrNum.ModelStateNum, 1);
        this.setNumberState(AttrNum.ModelState, 0);
        this.setNumberState(AttrNum.Emotion, 0);
        this.setNumberState(AttrNum.Emotion2, 0);
        this.setNumberState(AttrNum.Emotion3, 0);
        this.setNumberState(AttrNum.Emotion4, 0);
        this.setNumberState(AttrNum.HPState, 0);
        this.setNumberState(AttrNum.TimeEmotion3, 0);
        this.setNumberState(AttrNum.TimeSound, 0);
        this.setNumberState(AttrNum.TimeFace, 0);
        this.setNumberState(AttrNum.TimeHeadTilt, 0);
        this.setNumberState(AttrNum.EmoteDelay, 0);
        this.setNumberState(AttrNum.Scale, 0);
        this.setNumberState(AttrNum.ShipType, 0);
        this.setNumberState(AttrNum.IconType, 0);
        this.setNumberState(AttrNum.ShipLevel, 1);
        this.setNumberState(AttrNum.ShipUID, -1);
        this.setNumberState(AttrNum.PlayerUID, -1);
        this.setNumberState(AttrNum.PlayerEID, -1);
        this.setNumberState(AttrNum.Kills, 0);
        this.setNumberState(AttrNum.ExpCurrent, 0);
        this.setNumberState(AttrNum.ExpNext, this.calcExpNext(1));
        this.setNumberState(AttrNum.GrudgeNumber, 0);
        this.setNumberState(AttrNum.AmmoLightNumber, 0);
        this.setNumberState(AttrNum.AmmoHeavyNumber, 0);
        this.setNumberState(AttrNum.AirLightNumber, 0);
        this.setNumberState(AttrNum.AirHeavyNumber, 0);
        this.setNumberState(AttrNum.GrudgeConsume, 0);
        this.setNumberState(AttrNum.AmmoConsume, 0);
        this.setNumberState(AttrNum.Morale, 60);
        this.setNumberState(AttrNum.Food, 0);
        this.setNumberState(AttrNum.FoodMax, 10);
        this.setNumberState(AttrNum.HitHeight, 0);
        this.setNumberState(AttrNum.HitAngle, 0);
        this.setNumberState(AttrNum.SensBody, -1);
        this.setNumberState(AttrNum.ChunkLoaderLevel, 0);
        this.setNumberState(AttrNum.FlareLevel, 0);
        this.setNumberState(AttrNum.SearchlightLevel, 0);
        this.setNumberState(AttrNum.XP, 0);
        this.setNumberState(AttrNum.ImmuneTime, 0);
        this.setNumberState(AttrNum.RevengeTime, 0);
        this.setNumberState(AttrNum.MountSkillCD1, 0);
        this.setNumberState(AttrNum.MountSkillCD2, 0);
        this.setNumberState(AttrNum.MountSkillCD3, 0);
        this.setNumberState(AttrNum.MountSkillCD4, 0);
        this.setNumberState(AttrNum.MountSkillCD5, 0);
        this.setBooleanState(AttrBoo.UpdateAttrsBuffed, false);
        this.setBooleanState(AttrBoo.UpdateAttrsBonus, false);
        this.setBooleanState(AttrBoo.UpdateAttrsEquip, false);
        this.setBooleanState(AttrBoo.UpdateAttrsMorale, false);
        this.setBooleanState(AttrBoo.UpdateAttrsPotion, false);
        this.setBooleanState(AttrBoo.UpdateAttrsFormation, false);
        this.setBooleanState(AttrBoo.UpdateAttrsRaw, false);
        this.setBooleanState(AttrBoo.UpdateFormatBuff, false);
        this.setStringState(AttrStr.OwnerName, "");
        
        //choice random sensitive body part
        this.randomSensitiveBody();
    }
    
    /** init data at the end of {@link BasicEntityShip#initPre()} */
    @Override
    public void initPre()
    {
        //init ship attrs
        this.attrs = new AttrsAdv(this.getAttrClass());
    }
    
    /** init data at the end of {@link BasicEntityShip#initPost()} */
    @Override
    public void initPost()
    {
    }
    
    /*
     *       getter / setter
     */
    
    @Override
    public AttrsAdv getAttrs()
    {
        return this.attrs;
    }
    
    public int getShipType()
    {
        return this.getStateInt(AttrNum.ShipType);
    }
    
    public void setShipType(byte value)
    {
        this.setNumberState(AttrNum.ShipType, value);  //ID.ShipType
    }
    
    public int getShipIcon()
    {
        return this.getStateInt(AttrNum.IconType);
    }
    
    public void setShipIcon(byte value)
    {
        this.setNumberState(AttrNum.IconType, value);  //ID.IconType
    }
    
    public int getShipLevel()
    {
        return this.getStateInt(AttrNum.ShipLevel);
    }
    
    public void setShipLevel(int value)
    {
        this.setNumberState(AttrNum.ShipLevel, value);
    }
    
    public int getShipScale()
    {
        return this.getStateInt(AttrNum.Scale);
    }
    
    public void setShipScale(int value)
    {
        this.setNumberState(AttrNum.Scale, value);
    }
    
    public int getShipUID()
    {
        return this.getStateInt(AttrNum.ShipUID);
    }
    
    public void setShipUID(int value)
    {
        this.setNumberState(AttrNum.ShipUID, value);
    }
    
    public int getOwnerUID()
    {
        return this.getStateInt(AttrNum.PlayerUID);
    }
    
    public void setOwnerUID(int value)
    {
        this.setNumberState(AttrNum.PlayerUID, value);
    }
    
    public int getOwnerEID()
    {
        return this.getStateInt(AttrNum.PlayerEID);
    }
    
    public void setOwnerEID(int value)
    {
        this.setNumberState(AttrNum.PlayerEID, value);
    }
    
    public int getShipDamageType()
    {
        return this.getStateInt(AttrNum.ShipType);
    }
    
    public void setShipDamageType(byte value)
    {
        this.setNumberState(AttrNum.ShipType, value);  //ID.ShipDmgType
    }
    
    public int getShipSlotsLevel()
    {
        return this.getStateInt(AttrNum.SlotsLevel);
    }
    
    public void setShipSlotsLevel(int value)
    {
        if (value < 0) value = 0;
        this.setNumberState(AttrNum.SlotsLevel, value);
    }
    
    public int getShipStateNumber()
    {
        return this.getStateInt(AttrNum.ShipType);
    }
    
    public void setShipStateNumber(byte value)
    {
        this.setNumberState(AttrNum.ShipType, value);  //max state number <= 32
    }
    
    /** change ship outfit (model state) */
    public void setShipOutfit(int id)
    {
        if (id > this.getStateByte(AttrNum.ModelStateNum) || id < 0) id = 0;
        this.setNumberState(AttrNum.ModelState, this.getStateInt(AttrNum.ModelState) ^ Values.N.Pow2[id]);
    }
    
    public byte getSensitiveBody()
    {
        return this.getStateByte(AttrNum.SensBody);
    }
    
    public void setSensitiveBody(byte value)
    {
        this.setNumberState(AttrNum.SensBody, value);
    }
    
    /** set random sensitive body id, ref: ID.Body */
    public void randomSensitiveBody()
    {
        int ran = this.host.getRNG().nextInt(100);
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
            bodyid = (byte) (3 + this.host.getRNG().nextInt(8));  //roll 3~10
        }
        
        //reroll if HEAD/BACK
        if (bodyid == ID.Body.Head || bodyid == ID.Body.Back) bodyid = ID.Body.UBelly;
        if (bodyid == ID.Body.Arm || bodyid == ID.Body.Butt) bodyid = ID.Body.Chest;
        
        this.setSensitiveBody(bodyid);
    }
    
    /** 1:cannon only, 2:both, 3:aircraft only */
    public int getShipEquipType()
    {
        return this.getStateInt(AttrNum.EquipType);
    }
    
    /** @see #getEquipType() */
    public void setShipEquipType(byte value)
    {
        this.setNumberState(AttrNum.EquipType, value);  //max equip type < 3
    }
    
    public int getGrudgeConsumeIdle()
    {
        return this.getStateInt(AttrNum.GrudgeConsume);
    }
    
    public void setGrudgeConsumeIdle(int value)
    {
        this.setNumberState(AttrNum.GrudgeConsume, value);
    }
    
    public int getAmmoConsumption()
    {
        return this.getStateInt(AttrNum.AmmoConsume);
    }
    
    public void setAmmoConsumption(int value)
    {
        this.setNumberState(AttrNum.AmmoConsume, value);
    }
    
    public int getFoodSaturation()
    {
        return this.getStateInt(AttrNum.Food);
    }
    
    public void setFoodSaturation(int value)
    {
        this.setNumberState(AttrNum.Food, value);
    }
    
    public int getFoodSaturationMax()
    {
        return this.getStateInt(AttrNum.FoodMax);
    }
    
    public void setFoodSaturationMax(int value)
    {
        this.setNumberState(AttrNum.FoodMax, value);
    }
    
    public byte getDamageType()
    {
        return this.getStateByte(AttrNum.DamageType);
    }
    
    public void setDamageType(byte value)
    {
        this.setNumberState(AttrNum.DamageType, value);
    }
    
    public boolean canAttackLight()
    {
        return this.getBooleanState(AttrBoo.CanAtkLight);
    }
    
    public void setCanAttackLight(boolean value)
    {
        this.setBooleanState(AttrBoo.CanAtkLight, value);
    }
    
    public boolean canAttackHeavy()
    {
        return this.getBooleanState(AttrBoo.CanAtkHeavy);
    }
    
    public void setCanAttackHeavy(boolean value)
    {
        this.setBooleanState(AttrBoo.CanAtkHeavy, value);
    }
    
    public boolean canAttackAirLight()
    {
        return this.getBooleanState(AttrBoo.CanAtkAirLight);
    }
    
    public void setCanAttackAirLight(boolean value)
    {
        this.setBooleanState(AttrBoo.CanAtkAirLight, value);
    }
    
    public boolean canAttackAirHeavy()
    {
        return this.getBooleanState(AttrBoo.CanAtkAirHeavy);
    }
    
    public void setCanAttackAirHeavy(boolean value)
    {
        this.setBooleanState(AttrBoo.CanAtkAirHeavy, value);
    }
    
    public boolean useAttackMelee()
    {
        return this.getBooleanState(AttrBoo.UseMelee);
    }
    
    public void setUseAttackMelee(boolean value)
    {
        this.setBooleanState(AttrBoo.UseMelee, value);
        
        //reset AI
        this.host.getAIHandler().resetShipAI();
    }
    
    public boolean useAttackLight()
    {
        return this.getBooleanState(AttrBoo.UseAtkLight);
    }
    
    public void setUseAttackLight(boolean value)
    {
        this.setBooleanState(AttrBoo.UseAtkLight, value);
    }
    
    public boolean useAttackHeavy()
    {
        return this.getBooleanState(AttrBoo.UseAtkHeavy);
    }
    
    public void setUseAttackHeavy(boolean value)
    {
        this.setBooleanState(AttrBoo.UseAtkHeavy, value);
    }
    
    public boolean useAttackAirLight()
    {
        return this.getBooleanState(AttrBoo.UseAtkAirLight);
    }
    
    public void setUseAttackAirLight(boolean value)
    {
        this.setBooleanState(AttrBoo.UseAtkAirLight, value);
    }
    
    public boolean useAttackAirHeavy()
    {
        return this.getBooleanState(AttrBoo.UseAtkAirHeavy);
    }
    
    public void setUseAttackAirHeavy(boolean value)
    {
        this.setBooleanState(AttrBoo.UseAtkAirHeavy, value);
    }
    
    public int getMorale()
    {
        return this.getStateInt(AttrNum.Morale);
    }
    
    public void setMorale(int value)
    {
        this.setNumberState(AttrNum.Morale, value);
    }
    
    public int getGrudge()
    {
        return this.getStateInt(AttrNum.GrudgeNumber);
    }
    
    public void setGrudge(int value)
    {
        this.setNumberState(AttrNum.GrudgeNumber, value);
    }
    
    public int getAmmoLight()
    {
        return this.getStateInt(AttrNum.AmmoLightNumber);
    }
    
    public int getAmmoHeavy()
    {
        return this.getStateInt(AttrNum.AmmoHeavyNumber);
    }
    
    public int getAirplaneLight()
    {
        return this.getStateInt(AttrNum.AirLightNumber);
    }
    
    public int getAirplaneHeavy()
    {
        return this.getStateInt(AttrNum.AirHeavyNumber);
    }
    
    public int getFaceTick()
    {
        return this.getStateInt(AttrNum.TimeFace);
    }
    
    public void setFaceTick(int value)
    {
        this.setNumberState(AttrNum.TimeFace, value);
    }
    
    public int getHeadTiltTick()
    {
        return this.getStateInt(AttrNum.TimeHeadTilt);
    }
    
    public void setHeadTiltTick(int value)
    {
        this.setNumberState(AttrNum.TimeHeadTilt, value);
    }
    
    public int getRevengeTime()
    {
        return this.getStateInt(AttrNum.RevengeTime);
    }
    
    public void setRevengeTime(int value)
    {
        this.setNumberState(AttrNum.RevengeTime, value);
    }
    
    /** show emote cooldown */
    public int getEmoteDelay()
    {
        return this.getStateInt(AttrNum.EmoteDelay);
    }
    
    /** @see #getEmoteDelay() */
    public void setEmoteDelay(int value)
    {
        this.setNumberState(AttrNum.EmoteDelay, value);
    }
    
    public int getCraneDelay()
    {
        return this.getStateInt(AttrNum.CraneDelay);
    }
    
    public void setCraneDelay(int value)
    {
        this.setNumberState(AttrNum.CraneDelay, value);
    }
    
    public int getCraneTime()
    {
        return this.getStateInt(AttrNum.CraneTime);
    }
    
    public void setCraneTime(int value)
    {
        this.setNumberState(AttrNum.CraneTime, value);
    }
    
    /** pointer item click height on entity, sole = 0, headtop = 100 */
    public int getHitHeight()
    {
        return this.getStateInt(AttrNum.HitHeight);
    }
    
    /** @see #getHitHeight() */
    public void setHitHeight(int value)
    {
        if (value > 120) value = 120;
        else if (value < -20) value = -20;
        
        this.setNumberState(AttrNum.HitHeight, (byte) value);
    }
    
    public Enums.BodyHeight getBodyIDFromHeight()
    {
        return EntityHelper.getBodyIDFromHeight(this.getHitHeight(), this.host);
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
        return this.getStateInt(AttrNum.HitHeight);
    }
    
    /** @see #getHitAngle() */
    public void setHitAngle(int value)
    {
        value %= 360;
        
        this.setNumberState(AttrNum.HitHeight, (short) value);
    }
    
    public int getTickExisted()
    {
        return this.host.ticksExisted;
    }
    
    public int getKills()
    {
        return this.getStateInt(AttrNum.Kills);
    }
    
    public void addKills(int value)
    {
        this.addIntegerState(AttrNum.Kills, value);
    }
    
    public void addMorale(int value)
    {
        int n = this.getStateInt(AttrNum.Morale) + value;
        
        if (n < 0) n = 0;
        else if (n > 16000) n = 16000;
        
        this.setNumberState(AttrNum.Morale, n);
    }
    
    public void addAmmoLight(int value)
    {
        if (value > 0 && ConfigHandler.easyMode) value *= 10;
        
        int n = this.getStateInt(AttrNum.AmmoLightNumber) + value;
        
        if (n < 0) n = 0;
        
        this.setNumberState(AttrNum.AmmoLightNumber, n);
    }
    
    public void addAmmoHeavy(int value)
    {
        if (value > 0 && ConfigHandler.easyMode) value *= 10;
        
        int n = this.getStateInt(AttrNum.AmmoHeavyNumber) + value;
        
        if (n < 0) n = 0;
        
        this.setNumberState(AttrNum.AmmoHeavyNumber, n);
    }
    
    public void addGrudge(int value)
    {
        if (value > 0 && ConfigHandler.easyMode) value *= 10;
        
        int n = this.getStateInt(AttrNum.GrudgeNumber) + value;
        
        if (n < 0) n = 0;
        
        this.setNumberState(AttrNum.GrudgeNumber, n);
    }
    
    /** 0:ready, 1:waiting, 2:craning */
    public byte getCraneState()
    {
        return this.getStateByte(AttrNum.CraneState);
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
        
        this.setNumberState(AttrNum.CraneState, value);
    }
    
    public boolean isRiding()
    {
        return this.host.isRiding();
    }
    
    public boolean isLeashed()
    {
        return this.host.getLeashed();
    }

    public boolean isSprinting()
    {
        return this.host.isSprinting() || this.host.limbSwingAmount > 0.9F;
    }

    public boolean isSitting()
    {
        return this.host.isSitting();
    }

    public boolean isSneaking()
    {
        return this.host.isSneaking();
    }
    
    public float getMoveSpeed()
    {
        return this.attrs.getMoveSpeed(false);
    }
    
    public float getJumpSpeed()
    {
        return 1F;
    }
    
    /** get depth: 0:self depth, 1:mounts depth */
    public double getShipDepth(boolean getMountDepth)
    {
        if (getMountDepth && this.host.getRidingEntity() instanceof IStateMinion)
        {
            return ((IStateMinion) this.host.getRidingEntity()).getStateHandler().getShipDepth(false);
        }
        
        return this.getStateDouble(AttrNum.ShipDepth);
    }
    
    public void setShipDepth(double value)
    {
        this.setNumberState(AttrNum.ShipDepth, value);
    }
    
    public boolean hasNoFuel()
    {
        //treat death as no fuel
        if (!this.host.isEntityAlive() || this.host.deathTime > 0) return true;
        
        return this.getBooleanState(AttrBoo.IsNoFuel);
    }
    
    public void setNoFuel(boolean value)
    {
        this.setBooleanState(AttrBoo.IsNoFuel, value);
    }
    
    /** check ammo > 4 * base consume number */
    public boolean hasAmmoLight()
    {
        return this.getAmmoLight() >= 4 * ConfigHandler.consumeAmmoShip[this.getShipType()];
    }
    
    /** check ammo > 4 * base consume number */
    public boolean hasAmmoHeavy()
    {
        return this.getAmmoHeavy() >= 4 * ConfigHandler.consumeAmmoShip[this.getShipType()];
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
        int capLevel = this.getBooleanState(AttrBoo.IsMarried) ? ConfigHandler.maxLevel : ConfigHandler.midLimitLevel;
        int curLevel = this.getStateInt(AttrNum.ShipLevel);
        
        //if not cap level
        if (curLevel < capLevel)
        {
            //check morph
            if (this.host.isMorph()) exp = (int) ((float)exp * ConfigHandler.expGainPlayerSkill);
        
            //apply equip effect
            exp = (int) ((float)exp * this.attrs.getAttrsBuffed(ID.Attrs.XP));
            
            int curExp = this.getStateInt(AttrNum.ExpCurrent) + exp;
            int nextExp = this.getStateInt(AttrNum.ExpCurrent);
            
            //level++
            while (curExp >= nextExp || curLevel >= capLevel)
            {
                //level up sound
                this.host.world.playSound(null, this.host.posX, this.host.posY, this.host.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.host.getSoundCategory(), 0.7F, 1F);
                
                if (this.host.getRNG().nextInt(4) == 0)
                {
                    this.host.playSound(ModSounds.SHIP_LEVEL, ConfigHandler.volumeShip, 1F);
                }
                
                curLevel++;
                curExp -= nextExp;
                nextExp = this.calcExpNext(curLevel);
            }
            
            this.setNumberState(AttrNum.ExpCurrent, curExp);
            this.setNumberState(AttrNum.ExpNext, nextExp);
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
            BasicEntityShip.calcShipAttributes(this.host, 31, true);
            this.host.setHealth(this.host.getMaxHealth());
        }
    }
    
    public void decrMorale(Enums.ActType type)
    {
        if (this.host.isMorph()) return;
        
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
    
    /**
     * consume ammo. if not enough ammo, then consume ammo item. 
     * 
     * @param type by Enums.Ammo
     * @return <tt>true</tt> if ammo number was decreased
     */
    public boolean decrAmmo(Enums.Ammo type, int amount)
    {
        //check morph
        if (this.host.isMorph())
        {
            return MetamorphHelper.decrAmmoNum(this.host, type, amount);
        }
        
        boolean isHeavy = type == Enums.Ammo.HEAVY;
        int ammoNum = isHeavy ? this.getAmmoHeavy() : this.getAmmoLight();
        boolean useItem = isHeavy ? !hasAmmoHeavy() : !this.hasAmmoLight();
        float modAmmo = this.attrs.getAttrsBuffed(ID.Attrs.AMMO);
        
        //check buff: hunger
        if (amount > 0)
        {
            int level = BuffHelper.getPotionLevel(this.host, 17);
            amount = (int) ((float)amount * (1F + level * 2F));
        }
        
        //use item first if not enough ammo
        if (ammoNum <= amount || useItem)
        {
            int addAmmo = 0;
            int geti = this.host.getItemHandler().consumeAmmoItem(type);
            
            //show eat emotes
            if (geti >= 0) this.host.getReactHandler().reactEatItem();
            
            switch (geti)
            {
            case 0:
                addAmmo = (int) (ConfigHandler.baseLightAmmo * modAmmo);
            break;
            case 1:
                addAmmo = (int) (ConfigHandler.baseHeavyAmmo * modAmmo);
            break;
            case 2:
                addAmmo = (int) (ConfigHandler.baseLightAmmo * 9 * modAmmo);
            break;
            case 3:
                addAmmo = (int) (ConfigHandler.baseHeavyAmmo * 9 * modAmmo);
            break;
            }
            
            if (isHeavy) this.addAmmoHeavy(addAmmo);
            else this.addAmmoLight(addAmmo);
        }
        
        //check ammo again
        if (isHeavy ? !hasAmmoHeavy() : !this.hasAmmoLight())
        {
            //if still not enough ammo, show emotes
            this.host.getReactHandler().reactItemNotFound();
            return false;
        }
        
        //decr ammo number
        if (isHeavy) this.addAmmoHeavy(-amount);
        else this.addAmmoLight(-amount);
        
        return true;
    }
    
    /** consume grudge. if not enough grudge, then consume grudge item. */
    public void decrGrudge(int value)
    {
        //check morph
        if (this.host.isMorph())
        {
            MetamorphHelper.decrGrudgeNum(this.host, value);
            return;
        }
        
        //get grudge magnification
        float modGrudge = this.attrs.getAttrsBuffed(ID.Attrs.GRUDGE);
        
        //if grudge--, check buff: hunger
        if (value > 0)
        {
            int level = BuffHelper.getPotionLevel(this.host, 17);
            value = (int) ((float)value * (1F + level * 2F));
        }
        //if grudge++, check buff: grudge mod
        else if (value < 0)
        {
            value = (int) ((float)value * modGrudge);
        }
        
        //check fuel flag, only decrGrudge when entity alive
        if (!this.hasNoFuel())
        {
            this.addGrudge(-value);
        }
        
        //eat "ONE" grudge item per method call
        if (this.getGrudge() <= 0)
        {
            int addnum = 0;
            int geti = this.host.getItemHandler().consumeGrudgeItem();
            
            //show eat emotes
            if (geti >= 0) this.host.getReactHandler().reactEatItem();
            
            switch (geti)
            {
            case 0:  //grudge item
                addnum = (int) (ConfigHandler.baseGrudge * modGrudge);
            break;
            case 1:  //grudge block
                addnum = (int) (ConfigHandler.baseGrudge * 9 * modGrudge);
            break;
            }
            
            this.addGrudge(addnum);
        }
        
        //check fuel again and set fuel flag
        if (this.getGrudge() <= 0)
        {
            this.setNoFuel(true);
        }
        else
        {
            this.setNoFuel(false);
        }
        
        //check fuel flag again and set AI
        if (this.hasNoFuel())
        {
            //no fuel, clear AI
            if (this.host.getAIHandler().hasAI())
            {
                this.host.updateFuelState(true);
            }
        }
        else
        {
            //has fuel, set AI
            if (!this.host.getAIHandler().hasAI())
            {
                this.host.updateFuelState(false);
            }
        }
    }
    
    
}