package com.lulan.shincolle.handler;

import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * packet for entity
 * 
 * send type:
 *   IMMEDIATELY: send packet on method call
 *   TICKING: send packet on entity's ticking method (every 1/20 sec)
 */
public class PacketHandler
{
    
    /** host object */
    protected IPacketEntity host;
    
    
    public PacketHandler(IPacketEntity host)
    {
        this.host = host;
        
        this.initFirst();
    }
    
    /** init data on construct */
    protected void initFirst()
    {
    }
    
    

    /******************* TODO refactoring *****************************/
    
   
    
    /** sync data for GUI display */
    public void sendSyncPacketGUI()
    {
        if (!this.world.isRemote)
        {
            if (this.getPlayerUID() > 0)
            {
                EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
                
                //owner在附近才需要sync
                if (player != null && player.dimension == this.dimension &&
                    this.getDistanceToEntity(player) < 64F)
                {
                    CommonProxy.channelG.sendTo(new S2CGUIPackets(this), player);
                }
            }
        }
    }
    
    
}