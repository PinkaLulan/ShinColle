package com.lulan.shincolle.handler;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;

import net.minecraft.init.MobEffects;

public class ShipMoveHandler
{
    
    protected ShipPathNavigate shipNavigator;
    protected ShipMoveHelper shipMoveHelper;
    
    
    /** ship navigator */
    public ShipPathNavigate getShipNavigate()
    {
        return shipNavigator;
    }
    
    /** ship move helper */
    public ShipMoveHelper getShipMoveHelper()
    {
        return shipMoveHelper;
    }
    
    /** can entity fly flag */
    public boolean canFly()
    {
        return isPotionActive(MobEffects.LEVITATION);
    }
    
    /** entity is jumping */
    public boolean isJumping();
    
    /** move speed */
    public float getMoveSpeed();
    
    /** jump strength */
    public float getJumpSpeed();
    
}