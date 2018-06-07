package com.lulan.shincolle.entity;

import com.lulan.shincolle.reference.ID;

/**
 * ship states handler
 */
public class ShipStateHandler extends StatesHandler
{
    
    /** host entity */
    private IShipStateHandler host;
    
    
    public ShipStateHandler(IShipStateHandler host)
    {
        this.host = host;
        this.initData();
    }
    
    /** init all data in handler */
    public void initData()
    {
        this.setStateBoolean(ID.Keys.CanFloatUp, false);
        this.setStateBoolean(ID.Keys.CanDropItem, true);
        this.setStateBoolean(ID.Keys.CanFollow, false);
        this.setStateBoolean(ID.Keys.CanOnSightChase, true);
        this.setStateBoolean(ID.Keys.CanAtkLight, true);
        this.setStateBoolean(ID.Keys.CanAtkHeavy, true);
        this.setStateBoolean(ID.Keys.CanAtkAirLight, true);
        this.setStateBoolean(ID.Keys.CanAtkAirHeavy, true);
        this.setStateBoolean(ID.Keys.UseMelee, false);
        this.setStateBoolean(ID.Keys.UseAtkLight, true);
        this.setStateBoolean(ID.Keys.UseAtkHeavy, true);
        this.setStateBoolean(ID.Keys.UseAtkAirLight, true);
        this.setStateBoolean(ID.Keys.UseAtkAirHeavy, true);
        this.setState(ID.Keys.Phase, 0);
        this.setStateBoolean(ID.Keys.CanRingEffect, true);
        this.setStateBoolean(ID.Keys.UseRingEffect, true);
        this.setStateBoolean(ID.Keys.CanPickItem, false);
        this.setStateBoolean(ID.Keys.UsePickItem, true);
        this.setStateBoolean(ID.Keys.UseAutoPump, false);
        this.setState(ID.Keys.FollowMin, 3);
        this.setState(ID.Keys.FollowMax, 12);
        this.setState(ID.Keys.FleeHP, 35);
        this.setState(ID.Keys.CombatRationLevel, 3);
        this.setState(ID.Keys.GuardType, 0);
        this.setState(ID.Keys.GuardX, -1);
        this.setState(ID.Keys.GuardY, -1);
        this.setState(ID.Keys.GuardZ, -1);
        this.setState(ID.Keys.GuardDim, 0);
        this.setState(ID.Keys.GuardID, -1);
        this.setState(ID.Keys.DamageType, 0);
        this.setState(ID.Keys.FormatType, 0);
        this.setState(ID.Keys.FormatPos, 0);
        this.setState(ID.Keys.Task, -1);
        this.setState(ID.Keys.TaskSide, -1);
        this.setState(ID.Keys.TaskTime, 0);
        this.setState(ID.Keys.CraneState, 0);
        this.setState(ID.Keys.CraneTime, 0);
        this.setState(ID.Keys.CraneDelay, 0);
        this.setState(ID.Keys.WpStay, 0);
        this.setState(ID.Keys.WpStayTime, 0);
        this.setStateBoolean(ID.Keys.IsMarried, false);
        this.setStateBoolean(ID.Keys.IsNoFuel, false);
        this.setStateBoolean(ID.Keys.IsHeadTilt, false);
        this.setStateBoolean(ID.Keys.IsPVPFirst, true);
        this.setStateBoolean(ID.Keys.IsAntiAir, false);
        this.setStateBoolean(ID.Keys.IsAntiSS, false);
        this.setStateBoolean(ID.Keys.IsPassive, false);
        this.setStateBoolean(ID.Keys.IsTimeKeeper, true);
        this.setStateBoolean(ID.Keys.ShowHeldItem, true);
        this.setState(ID.Keys.ModelStateNum, 1);
        this.setState(ID.Keys.ModelState, 0);
        this.setState(ID.Keys.Emotion, 0);
        this.setState(ID.Keys.Emotion2, 0);
        this.setState(ID.Keys.Emotion3, 0);
        this.setState(ID.Keys.Emotion4, 0);
        this.setState(ID.Keys.HPState, 0);
        this.setState(ID.Keys.TimeEmotion3, 0);
        this.setState(ID.Keys.TimeSound, 0);
        this.setState(ID.Keys.TimeFace, 0);
        this.setState(ID.Keys.TimeHeadTilt, 0);
        this.setState(ID.Keys.DelayEmote, 0);
        this.setState(ID.Keys.ShipType, 0);
        this.setState(ID.Keys.ShipClass, 0);
        this.setState(ID.Keys.ShipLevel, 1);
        this.setState(ID.Keys.ShipUID, -1);
        this.setState(ID.Keys.PlayerUID, -1);
        this.setState(ID.Keys.PlayerEID, -1);
        this.setState(ID.Keys.Kills, 0);
        this.setState(ID.Keys.ExpCurrent, 0);
        this.setState(ID.Keys.ExpNext, 40);
        this.setState(ID.Keys.Grudge, 0);
        this.setState(ID.Keys.AmmoLight, 0);
        this.setState(ID.Keys.AmmoHeavy, 0);
        this.setState(ID.Keys.AirLight, 0);
        this.setState(ID.Keys.AirHeavy, 0);
        this.setState(ID.Keys.GrudgeConsume, 0);
        this.setState(ID.Keys.AmmoConsume, 0);
        this.setState(ID.Keys.Morale, 60);
        this.setState(ID.Keys.Food, 0);
        this.setState(ID.Keys.FoodMax, 10);
        this.setState(ID.Keys.HitHeight, 0);
        this.setState(ID.Keys.HitAngle, 0);
        this.setState(ID.Keys.SensBody, -1);
        this.setState(ID.Keys.SlotsLevel, 0);
        this.setState(ID.Keys.ChunkLoaderLevel, 0);
        this.setState(ID.Keys.FlareLevel, 0);
        this.setState(ID.Keys.SearchlightLevel, 0);
        this.setState(ID.Keys.XP, 0);
        this.setState(ID.Keys.ImmuneTime, 0);
        this.setState(ID.Keys.RevengeTime, 0);
        this.setState(ID.Keys.LastCombatTime, 0);
        this.setState(ID.Keys.AttackTime, 0);
        this.setState(ID.Keys.AttackTime2, 0);
        this.setState(ID.Keys.AttackTime3, 0);
        this.setState(ID.Keys.MountSkillCD1, 0);
        this.setState(ID.Keys.MountSkillCD2, 0);
        this.setState(ID.Keys.MountSkillCD3, 0);
        this.setState(ID.Keys.MountSkillCD4, 0);
        this.setState(ID.Keys.MountSkillCD5, 0);
        this.setStateBoolean(ID.Keys.UpdateAttrsBuffed, false);
        this.setStateBoolean(ID.Keys.UpdateAttrsBonus, false);
        this.setStateBoolean(ID.Keys.UpdateAttrsEquip, false);
        this.setStateBoolean(ID.Keys.UpdateAttrsMorale, false);
        this.setStateBoolean(ID.Keys.UpdateAttrsPotion, false);
        this.setStateBoolean(ID.Keys.UpdateAttrsFormation, false);
        this.setStateBoolean(ID.Keys.UpdateAttrsRaw, false);
        this.setStateBoolean(ID.Keys.UpdateFormatBuff, false);
    }
    
    
}