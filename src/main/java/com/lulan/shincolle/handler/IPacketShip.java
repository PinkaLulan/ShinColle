package com.lulan.shincolle.handler;

public interface IPacketShip extends IPacketEntity
{
    
    /** get handler */
    @Override
    ShipPacketHandler getPacketHandler();
    
}