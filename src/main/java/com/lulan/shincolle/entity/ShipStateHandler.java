package com.lulan.shincolle.entity;

import com.lulan.shincolle.reference.ID;

/**
 * ship states handler, get data by {@link ID.Keys}
 */
public class ShipStateHandler extends StateHandler
{
    
    /** host entity */
    private IStateHandler host;
    
    
    public ShipStateHandler(IStateHandler host)
    {
        super();
        this.host = host;
        this.initData();
    }
    
    /** init all data in handler */
    public void initData()
    {
        this.setFlagState(ID.Keys.CanFloatUp, false);
        this.setFlagState(ID.Keys.CanDropItem, true);
        this.setFlagState(ID.Keys.CanFollow, false);
        this.setFlagState(ID.Keys.CanOnSightChase, true);
        this.setFlagState(ID.Keys.CanAtkLight, true);
        this.setFlagState(ID.Keys.CanAtkHeavy, true);
        this.setFlagState(ID.Keys.CanAtkAirLight, true);
        this.setFlagState(ID.Keys.CanAtkAirHeavy, true);
        this.setFlagState(ID.Keys.UseMelee, false);
        this.setFlagState(ID.Keys.UseAtkLight, true);
        this.setFlagState(ID.Keys.UseAtkHeavy, true);
        this.setFlagState(ID.Keys.UseAtkAirLight, true);
        this.setFlagState(ID.Keys.UseAtkAirHeavy, true);
        this.setNumberState(ID.Keys.Phase, 0);
        this.setFlagState(ID.Keys.CanRingEffect, true);
        this.setFlagState(ID.Keys.UseRingEffect, true);
        this.setFlagState(ID.Keys.CanPickItem, false);
        this.setFlagState(ID.Keys.UsePickItem, true);
        this.setFlagState(ID.Keys.UseAutoPump, false);
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
        this.setFlagState(ID.Keys.IsMarried, false);
        this.setFlagState(ID.Keys.IsNoFuel, false);
        this.setFlagState(ID.Keys.IsHeadTilt, false);
        this.setFlagState(ID.Keys.IsPVPFirst, true);
        this.setFlagState(ID.Keys.IsAntiAir, false);
        this.setFlagState(ID.Keys.IsAntiSS, false);
        this.setFlagState(ID.Keys.IsPassive, false);
        this.setFlagState(ID.Keys.IsTimeKeeper, true);
        this.setFlagState(ID.Keys.ShowHeldItem, true);
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
        this.setNumberState(ID.Keys.DelayEmote, 0);
        this.setNumberState(ID.Keys.ShipType, 0);
        this.setNumberState(ID.Keys.ShipClass, 0);
        this.setNumberState(ID.Keys.ShipLevel, 1);
        this.setNumberState(ID.Keys.ShipUID, -1);
        this.setNumberState(ID.Keys.PlayerUID, -1);
        this.setNumberState(ID.Keys.PlayerEID, -1);
        this.setNumberState(ID.Keys.Kills, 0);
        this.setNumberState(ID.Keys.ExpCurrent, 0);
        this.setNumberState(ID.Keys.ExpNext, 40);
        this.setNumberState(ID.Keys.Grudge, 0);
        this.setNumberState(ID.Keys.AmmoLight, 0);
        this.setNumberState(ID.Keys.AmmoHeavy, 0);
        this.setNumberState(ID.Keys.AirLight, 0);
        this.setNumberState(ID.Keys.AirHeavy, 0);
        this.setNumberState(ID.Keys.GrudgeConsume, 0);
        this.setNumberState(ID.Keys.AmmoConsume, 0);
        this.setNumberState(ID.Keys.Morale, 60);
        this.setNumberState(ID.Keys.Food, 0);
        this.setNumberState(ID.Keys.FoodMax, 10);
        this.setNumberState(ID.Keys.HitHeight, 0);
        this.setNumberState(ID.Keys.HitAngle, 0);
        this.setNumberState(ID.Keys.SensBody, -1);
        this.setNumberState(ID.Keys.SlotsLevel, 0);
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
        this.setFlagState(ID.Keys.UpdateAttrsBuffed, false);
        this.setFlagState(ID.Keys.UpdateAttrsBonus, false);
        this.setFlagState(ID.Keys.UpdateAttrsEquip, false);
        this.setFlagState(ID.Keys.UpdateAttrsMorale, false);
        this.setFlagState(ID.Keys.UpdateAttrsPotion, false);
        this.setFlagState(ID.Keys.UpdateAttrsFormation, false);
        this.setFlagState(ID.Keys.UpdateAttrsRaw, false);
        this.setFlagState(ID.Keys.UpdateFormatBuff, false);
    }
    
    
}