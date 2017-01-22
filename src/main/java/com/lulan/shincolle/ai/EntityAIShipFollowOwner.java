package com.lulan.shincolle.ai;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
/**SHIP FOLLOW OWNER AI
 * 距離超過max dist時觸發移動, 直到走進min dist距離時停止
 * 距離超過TP_DIST會直接teleport到owner旁邊
 */
public class EntityAIShipFollowOwner extends EntityAIBase
{

    private IShipAttackBase host;
    private EntityLiving host2;
    private EntityLivingBase owner;
    private EntityPlayer player;
    private static final double TP_DIST = 1600D;	//40 block for tp dist
    private static final int TP_TIME = 400;			//20 sec for can't move time
    private int checkTP_T, checkTP_D;				//teleport cooldown count
    private int findCooldown;						//path navi cooldown
    private ShipPathNavigate ShipNavigator;
    private double maxDistSq;
    private double minDistSq;
    private double distSq;
    private double distX, distY, distZ;
    private double[] pos, ownerPosOld;		//position record

    
    public EntityAIShipFollowOwner(IShipAttackBase entity)
    {
		this.host = entity;
        this.host2 = (EntityLiving) entity;
        this.ShipNavigator = entity.getShipNavigate();
        this.distSq = 1D;
        this.setMutexBits(7);
        
        //init vars
        this.pos = new double[] {host2.posX, host2.posY, host2.posZ};
        this.ownerPosOld = new double[] {host2.posX, host2.posY, host2.posZ};
        
        if (entity instanceof BasicEntityShip || entity instanceof BasicEntityMount)
        {
        	this.player = EntityHelper.getEntityPlayerByUID(entity.getPlayerUID());
        }
    }
    
    //有owner且目標超過max dist時觸發AI, 觸發後此方法不再執行, 改為持續執行cont exec
    @Override
	public boolean shouldExecute()
    {
    	if (!host.getIsSitting() && !host.getIsRiding() && !host.getIsLeashed() && 
    		host.getStateFlag(ID.F.CanFollow) && host.getStateMinor(ID.M.CraneState) < 1)
    	{
    		EntityLivingBase OwnerEntity = EntityHelper.getEntityPlayerByUID(this.host.getPlayerUID());

    		//get owner distance
            if (OwnerEntity != null)
            {
            	this.owner = OwnerEntity;
                
            	if (this.owner.dimension != this.host2.dimension)
            	{
            		return false;
            	}
            	
            	updateDistance();

            	if (distSq > this.maxDistSq)
            	{
            		return true;
            	}
            }
    	}
        return false;
    }

    //目標還沒接近min dist或者距離超過TP_DIST時繼續AI
    @Override
	public boolean continueExecuting()
    {
    	if (host != null && owner != null)
    	{
    		//非坐下, 騎乘, 綁住, 可跟隨, 非裝載中 = 可執行AI
    		if (!host.getIsSitting() && !host.getIsRiding() && !host.getIsLeashed() && 
    			host.getStateFlag(ID.F.CanFollow) && host.getStateMinor(ID.M.CraneState) < 1)
    		{
	        	//還沒走進min follow range, 繼續走
	        	if (this.distSq > this.minDistSq)
	        	{		
	        		return true;
	        	}
	        	
	        	//其他情況
	        	return !ShipNavigator.noPath() || shouldExecute();
    		}
    		else
    		{	//若為坐下, 騎乘, 被綁, 則重置AI
    			this.resetTask();
    			return false;
    		}
    	}
    	
    	return false;
    }

    @Override
	public void startExecuting()
    {
        this.findCooldown = 10;
        this.checkTP_T = 0;
        this.checkTP_D = 0;
    }

    @Override
	public void resetTask()
    {
        this.owner = null;
        this.ShipNavigator.clearPathEntity();
    }

    @Override
	public void updateTask()
    {
    	if (host != null)
    	{
//    		LogHelper.info("DEBUG : exec follow owner");
        	this.findCooldown--;
        	this.checkTP_T++;
        	
        	//update follow range every 32 ticks
        	if (host2.ticksExisted % 32 == 0)
        	{
        		//update owner distance
            	EntityLivingBase OwnerEntity = EntityHelper.getEntityPlayerByUID(this.host.getPlayerUID());

                if (OwnerEntity != null)
                {
                	this.owner = OwnerEntity;
                    
                	//owner dim changed
                	if (this.owner.dimension != this.host2.dimension)
                	{
                		this.resetTask();
                		return;
                	}
                	
                	updateDistance();
                }
                //owner not found
                else
                {
                	this.resetTask();
            		return;
                }
        	}//end update
        	
        	//end move
        	if (this.distSq <= this.minDistSq)
        	{
        		this.ShipNavigator.clearPathEntity();
        	}
        	
        	//每cd到找一次路徑
        	if (this.findCooldown <= 0)
        	{
    			this.findCooldown = 32;
    			this.ShipNavigator.tryMoveToXYZ(pos[0], pos[1], pos[2], 1D);
        	}
        	
        	//設定頭部轉向
            this.host2.getLookHelper().setLookPositionWithEntity(this.owner, 20F, 40F);
            
        	//check teleport conditions: same DIM and (dist > TP_DIST or time > TP_TIME)
        	if (this.host2.dimension == this.owner.dimension)
        	{
        		//check dist
        		if (this.distSq > TP_DIST)
        		{
        			this.checkTP_D++;
        			
        			if (this.checkTP_D > TP_TIME)
        			{
        				this.checkTP_D = 0;
        				
        				LogHelper.debug("DEBUG: follow AI: distSQ > "+TP_DIST+" , teleport to target. dim: "+host2.dimension+" "+owner.dimension);
        				applyTeleport(this.host, this.distSq, new Vec3d(this.owner.posX, this.owner.posY + 0.75D, this.owner.posZ));
                        return;
        			}
        		}
        		
        		//check moving time
        		if (this.checkTP_T > TP_TIME)
        		{
        			this.checkTP_T = 0;
        			
        			LogHelper.debug("DEBUG: follow AI: teleport entity: dimension check: "+host2.dimension+" "+owner.dimension);
        			applyTeleport(this.host, this.distSq, new Vec3d(this.owner.posX, this.owner.posY + 0.75D, this.owner.posZ));
                    return;
        		}
        	}//end same dim
    	}//end host != null
    }
  	
  	//update distance
  	private void updateDistance()
  	{
  		//if ship with formation
		if (host.getStateMinor(ID.M.FormatType) > 0)
		{
			this.minDistSq = 4;
            this.maxDistSq = 7;
			
            //if owner moving distSQ > 7, get new position
			double dx = ownerPosOld[0] - owner.posX;
			double dy = ownerPosOld[1] - owner.posY;
			double dz = ownerPosOld[2] - owner.posZ;
			double dsq = dx * dx + dy * dy + dz * dz;
			
			if (dsq > 7)
			{
				//get new position
				pos = FormationHelper.getFormationGuardingPos(host, owner, ownerPosOld[0], ownerPosOld[2]);
				
				//backup old position
				ownerPosOld[0] = owner.posX;
				ownerPosOld[1] = owner.posY;
				ownerPosOld[2] = owner.posZ;
					
				//draw moving particle
				if (player != null && (ConfigHandler.alwaysShowTeamParticle || EntityHelper.getPointerInUse(player) != null) &&
					player.dimension == host2.dimension)
				{
					CommonProxy.channelP.sendTo(new S2CSpawnParticle(25, pos[0], pos[1], pos[2], 0.3, 4, 0), (EntityPlayerMP) player);
				}
			}
			
			if (this.host2.ticksExisted % 16 == 0)
			{
				//draw moving particle
				if (player != null && (ConfigHandler.alwaysShowTeamParticle || EntityHelper.getPointerInUse(player) != null) &&
					player.dimension == host2.dimension)
				{
					CommonProxy.channelP.sendTo(new S2CSpawnParticle(25, pos[0], pos[1], pos[2], 0.3, 6, 0), (EntityPlayerMP) player);
				}
			}
			
			if (host.getStateFlag(ID.F.PickItem)) this.maxDistSq = 64D;
		}
		//no formation
		else
		{
			float fMin = host.getStateMinor(ID.M.FollowMin) + host2.width * 0.75F;
	    	float fMax = host.getStateMinor(ID.M.FollowMax) + host2.width * 0.75F;
	    	
	    	if (host.getStateFlag(ID.F.PickItem)) fMax += 5D;
	    	
	    	this.minDistSq = fMin * fMin;
	        this.maxDistSq = fMax * fMax;
	        
	        pos[0] = owner.posX;
			pos[1] = owner.posY;
			pos[2] = owner.posZ;
		}
		
    	//計算直線距離
    	this.distX = pos[0] - this.host2.posX;
		this.distY = pos[1] - this.host2.posY;
		this.distZ = pos[2] - this.host2.posZ;
    	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;
    
  	}
  	
    /**
     *  teleport host to Vec3d
     *  if host = mount, teleport both mount and rider
     *  if host = ship, teleport ship only (dismount)
     */
  	public static void applyTeleport(IShipNavigator host, double dist, Vec3d tpPos)
    {
    	if (host instanceof BasicEntityMount)
    	{
    		BasicEntityShip hostHost = (BasicEntityShip) ((BasicEntityMount) host).getHostEntity();
    		
    		//too far away, dismount
    		if (dist > 1024D)
    		{
    			EntityHelper.clearMountSeat((BasicEntityMount) host);
    			EntityHelper.clearMountSeat(hostHost);
    		}
    		//teleport mounts
    		else
    		{
    			((Entity)host).setLocationAndAngles(tpPos.xCoord, tpPos.yCoord, tpPos.zCoord, hostHost.rotationYaw, hostHost.rotationPitch);
        		sendSyncPacket(((Entity)host));
    		}
    		
    		//teleport rider
    		host.getShipNavigate().clearPathEntity();
    		hostHost.setLocationAndAngles(tpPos.xCoord, tpPos.yCoord, tpPos.zCoord, hostHost.rotationYaw, hostHost.rotationPitch);
    		sendSyncPacket(hostHost);
    	}
    	else if (host instanceof EntityLiving)
    	{
    		EntityLiving host2 = (EntityLiving) host;
    		
    		//too far away, dismount
    		if (dist > 1024D)
    		{
    			EntityHelper.clearMountSeat(host2);
    		}
    		//teleport mounts
    		else
    		{
    			((Entity)host).setLocationAndAngles(tpPos.xCoord, tpPos.yCoord, tpPos.zCoord, host2.rotationYaw, host2.rotationPitch);
        		sendSyncPacket(((Entity)host));
    		}
        	
    		//teleport rider
    		host.getShipNavigate().clearPathEntity();
    		host2.setLocationAndAngles(tpPos.xCoord, tpPos.yCoord, tpPos.zCoord, host2.rotationYaw, host2.rotationPitch);
    		sendSyncPacket(host2);
    	}
    }
	
	//sync position
	public static void sendSyncPacket(Entity ent)
	{
		//for other player, send ship state for display
		TargetPoint point = new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, 64D);
		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(ent, 0, S2CEntitySync.PID.SyncEntity_PosRot), point);
	}
	
	
}
