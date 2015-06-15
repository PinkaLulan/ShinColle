package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
/**SHIP FOLLOW OWNER AI
 * 距離超過max dist時觸發移動, 直到走進min dist距離時停止
 * 距離超過TP_DIST會直接teleport到owner旁邊
 */
public class EntityAIShipFollowOwner extends EntityAIBase {

    private IShipAttackBase host;
    private EntityLiving host2;
    private EntityLivingBase owner;
    private static final double TP_DIST = 2048D;	//teleport condition ~ 45 blocks
    private ShipPathNavigate ShipNavigator;
    private int findCooldown;
    private int checkTeleport;	//> 40 = use teleport
    private double maxDistSq;
    private double minDistSq;
    private double distSq;
    private double distSqrt;
    private double distX, distY, distZ;	//跟目標的直線距離(的平方)

    
    public EntityAIShipFollowOwner(IShipAttackBase entity) {
        this.host = entity;
        this.host2 = (EntityLiving) entity;
        this.ShipNavigator = entity.getShipNavigate();
        this.distSq = 1D;
        this.distSqrt = 1D;
        this.setMutexBits(7);  
    }
    
    //有owner且目標超過max dist時觸發AI, 觸發後此方法不再執行, 改為持續執行cont exec
    public boolean shouldExecute() {
    	if(!host.getIsSitting() && !host.getIsRiding() && !host.getIsLeashed() && 
    	   !host.getStateFlag(ID.F.NoFuel) && host.getStateFlag(ID.F.CanFollow)) {
    		EntityLivingBase OwnerEntity = this.host.getPlayerOwner();

    		//get owner distance
            if(OwnerEntity != null) {
            	this.owner = OwnerEntity;
                
            	if(this.owner.dimension != this.host2.dimension) {
            		return false;
            	}
            	
            	float fMin = host.getStateMinor(ID.N.FollowMin);
            	float fMax = host.getStateMinor(ID.N.FollowMax);
            	this.minDistSq = fMin * fMin;
                this.maxDistSq = fMax * fMax;

            	//計算直線距離
            	this.distX = this.owner.posX - this.host2.posX;
        		this.distY = this.owner.posY - this.host2.posY;
        		this.distZ = this.owner.posZ - this.host2.posZ;
            	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;

            	if(distSq > this.maxDistSq) {
            		return true;
            	}
            }
    	}
        return false;
    }

    //目標還沒接近min dist或者距離超過TP_DIST時繼續AI
    public boolean continueExecuting() {
    	//非坐下, 騎乘, 被綁住時可以繼續執行AI
    	if(host != null) { 
    		if(!host.getIsSitting() && !host.getIsRiding() && !host.getIsLeashed() && 
    		   !host.getStateFlag(ID.F.NoFuel) && host.getStateFlag(ID.F.CanFollow)) {

	        	//距離超過傳送距離, 則處理傳送部份
	        	if(this.distSq > this.TP_DIST) {
	        		return true;
	        	}

	        	//還沒走進min follow range, 繼續走
	        	if(this.distSq > this.minDistSq ) {		
	        		return true;
	        	}
	        	
	        	//其他情況
	        	return !ShipNavigator.noPath() ||shouldExecute();
    		}
    		else {	//若為坐下, 騎乘, 被綁, 則重置AI
    			this.resetTask();
    			return false;
    		}
    	}
    	
    	return false;
    }

    public void startExecuting() {
        this.findCooldown = 20;
        this.checkTeleport = 0;
    }

    public void resetTask() {
        this.owner = null;
        this.ShipNavigator.clearPathEntity();
    }

    public void updateTask() {
    	if(host != null) {
    		LogHelper.info("DEBUG : exec follow owner");
        	this.findCooldown--;
        	
        	//update follow range every 60 ticks
        	if(host2.ticksExisted % 60 == 0){
        		//update owner distance
            	EntityLivingBase OwnerEntity = this.host.getPlayerOwner();
                if(OwnerEntity != null) {
                	this.owner = OwnerEntity;
                    
                	if(this.owner.dimension != this.host2.dimension) {
                		this.resetTask();
                		return;
                	}
                	
                	float fMin = host.getStateMinor(ID.N.FollowMin);
                	float fMax = host.getStateMinor(ID.N.FollowMax);
                	this.minDistSq = fMin * fMin;
                    this.maxDistSq = fMax * fMax;

                	//計算直線距離
                	this.distX = this.owner.posX - this.host2.posX;
            		this.distY = this.owner.posY - this.host2.posY;
            		this.distZ = this.owner.posZ - this.host2.posZ;
                	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;
                }
        	}//end update
        	
        	//end move
        	if(this.distSq <= this.minDistSq) {
        		this.ShipNavigator.clearPathEntity();
        	}
        	
        	//設定頭部轉向
            this.host2.getLookHelper().setLookPositionWithEntity(this.owner, 30.0F, (float)this.host2.getVerticalFaceSpeed());

        	//距離超過傳送距離, 直接傳送到目標上
        	if(this.distSq > this.TP_DIST) {
        		this.checkTeleport++;
        		
        		if(this.checkTeleport > 60) {
        			this.checkTeleport = 0;
        			//相同dim才傳送
        			LogHelper.info("DEBUG : follow AI: distSQ > "+this.TP_DIST+" , teleport entity. dim: "+host2.dimension+" "+owner.dimension);
        			if(this.host2.dimension == this.owner.dimension) {
        				
        				//teleport
            			if(this.distSq > 1024) {	//32 blocks away, drop seat2
            				this.clearMountSeat2();
            			}
            			
        				this.host2.setLocationAndAngles(this.owner.posX, this.owner.posY + 1D, this.owner.posZ, this.host2.rotationYaw, this.host2.rotationPitch);
        				this.ShipNavigator.clearPathEntity();
        				this.sendSyncPacket();
                        return;
        			}
        		}
        	}
        	
        	//每cd到找一次路徑
        	if(this.findCooldown <= 0) {
    			this.findCooldown = 30;
    			
    			//check path result
            	if(!this.ShipNavigator.tryMoveToEntityLiving(this.owner, 1D)) {
            		LogHelper.info("DEBUG : follow AI: fail to follow, cannot reach or too far away");
            		//若超過max dist持續240ticks, 則teleport
            		if(this.distSq > this.maxDistSq) {
            			this.checkTeleport++;	//若距離超過max dist且移動又失敗, 會使checkTP每30 tick+1
                		
                		if(this.checkTeleport > 8) {
                			this.checkTeleport = 0;
                			//相同dim才傳送
                			LogHelper.info("DEBUG : follow AI: teleport entity: dimension "+host2.dimension+" "+owner.dimension);
                			if(this.host2.dimension == this.owner.dimension) {
                				
                				//teleport
                    			if(this.distSq > 1024) {	//32 blocks away, drop seat2
                    				this.clearMountSeat2();
                    			}
                    			
                				this.host2.setLocationAndAngles(this.owner.posX, this.owner.posY + 1D, this.owner.posZ, this.host2.rotationYaw, this.host2.rotationPitch);
                				this.ShipNavigator.clearPathEntity();
                				this.sendSyncPacket();
                                return;
                			}
                		}	
                    }
                }//end !try move to owner
            }//end path find cooldown
    	}
    }
    
	//clear seat2
  	private void clearMountSeat2() {
  		//若座位2有人, 要先把座位2的乘客踢掉
  		if(host2.ridingEntity != null) {
  			if(host2.ridingEntity instanceof BasicEntityMount) {
	  			BasicEntityMount mount = (BasicEntityMount) host2.ridingEntity;
	  			if(mount.seat2 != null) {
	  				mount.seat2.setRiderNull();
	  			}
  			}
  			else {
  				host2.mountEntity(null);
  			}
  		}
  		
  		//清空騎乘的人
  		if(host2.riddenByEntity != null) {
  			host2.riddenByEntity.mountEntity(null);
  			host2.riddenByEntity = null;
  		}
  	}
  	
  	//sync position
  	private void sendSyncPacket() {
  		//for other player, send ship state for display
  		TargetPoint point = new TargetPoint(host2.dimension, host2.posX, host2.posY, host2.posZ, 48D);
  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(host2, 0, 9), point);
  	}
	
	
}