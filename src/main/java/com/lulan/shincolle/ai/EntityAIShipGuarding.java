package com.lulan.shincolle.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipGuardian;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
/**SHIP GUARDING AI
 * CanFollow = false時可以執行
 * 持續固守某一點followMax格之內, 距離該點followMax格以上就會嘗試回去該點直到靠近followMin格內
 * 距離超過max格超過指定時間後, 強制傳送回該點
 * 若host的gy<=0, 則視為必須跟隨owner, 會設定CanFollow = true
 * 
 * 2015/9/30:
 * move & attack mode:
 *   1. set StateMinor[GuardType] = 1
 *   2. get target within attack range every X ticks
 *   3. attack target if cooldown = 0
 */
public class EntityAIShipGuarding extends EntityAIBase {

    private IShipGuardian host;
    private EntityLiving host2;
    private Entity guarded;
    private ShipPathNavigate ShipNavigator;
    private int findCooldown;
    private int checkTeleport;	//> 200 = use teleport
    private double maxDistSq;
    private double minDistSq;
    private double distSq;
    private double distSqrt;
    private double distX, distY, distZ;	//跟目標的直線距離(的平方)
    private int gx, gy, gz;				//guard position (block position)

    
    public EntityAIShipGuarding(IShipGuardian entity) {
        this.host = entity;
        this.host2 = (EntityLiving) entity;
        this.ShipNavigator = entity.getShipNavigate();
        this.distSq = 1D;
        this.distSqrt = 1D;
        this.setMutexBits(7);  
    }
    
    //判定是否開始執行AI
    public boolean shouldExecute() {
    	//非坐下, 非騎乘, 非被綁, 非可跟隨, 且有fuel才執行
    	if(host != null && !host.getIsSitting() && !host.getStateFlag(ID.F.NoFuel) && !host.getStateFlag(ID.F.CanFollow)) {
    		//check guarded entity
    		this.guarded = host.getGuarded();
    		
    		//get guard target
    		if(this.guarded != null) {
    			if(!this.guarded.isEntityAlive()) {
    				host.setGuarded(null);
    				return false;
    			}
    			else {
    				this.gx = (int) this.guarded.posX;
        			this.gy = (int) this.guarded.posY;
        			this.gz = (int) this.guarded.posZ;
    			}
    		}
    		else {
    			this.gx = host.getStateMinor(ID.M.GuardX);
        		this.gy = host.getStateMinor(ID.M.GuardY);
        		this.gz = host.getStateMinor(ID.M.GuardZ);
    		}
    		
    		//若gy=0, 表示這entity剛初始化, 還不能執行AI
    		if(gy <= 0) {
    			host.setStateFlag(ID.F.CanFollow, true);
    			return false;
    		}
    		else {
    			float fMin = host.getStateMinor(ID.M.FollowMin);
            	float fMax = host.getStateMinor(ID.M.FollowMax);
            	this.minDistSq = fMin * fMin;
                this.maxDistSq = fMax * fMax;
                
                //計算直線距離
            	this.distX = this.gx - this.host2.posX;
        		this.distY = this.gy - this.host2.posY;
        		this.distZ = this.gz - this.host2.posZ;
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
    	if(host != null) {
    		//非坐下, 非騎乘, 非被綁, 非可跟隨, 且有fuel才執行
    		if(!host.getIsSitting() && !host.getStateFlag(ID.F.NoFuel) && !host.getStateFlag(ID.F.CanFollow)) {
    			//還沒走進min follow range, 繼續走
	        	if(this.distSq > this.minDistSq) {		
	        		return true;	//need update guard position
	        	}
	        	
	        	//其他情況
	        	return !ShipNavigator.noPath() || shouldExecute();
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
    	this.guarded = null;
    	this.findCooldown = 20;
        this.checkTeleport = 0;
        this.ShipNavigator.clearPathEntity();
    }

    public void updateTask() {
    	if(host != null) {
//    		LogHelper.info("DEBUG : exec guarding");
        	this.findCooldown--;
        	
        	//update position every 30 ticks
        	if(host2.ticksExisted % 30 == 0) {
        		//check guarded entity
        		this.guarded = host.getGuarded();
        		
        		//get guard target
        		if(this.guarded != null) {
        			if(!this.guarded.isEntityAlive()) {
        				host.setGuarded(null);
        				this.resetTask();
        				return;
        			}
        			else {
        				this.gx = (int) this.guarded.posX;
            			this.gy = (int) this.guarded.posY;
            			this.gz = (int) this.guarded.posZ;
        			}
        		}
        		else {
        			this.gx = host.getStateMinor(ID.M.GuardX);
            		this.gy = host.getStateMinor(ID.M.GuardY);
            		this.gz = host.getStateMinor(ID.M.GuardZ);
        		}
        		
        		//若gy<=0, 表示這entity停止定點防守, 改跟隨owner
        		if(gy <= 0) {
        			host.setStateFlag(ID.F.CanFollow, true);
        			this.resetTask();
        			return;
        		}
        		else {
        			float fMin = host.getStateMinor(ID.M.FollowMin);
                	float fMax = host.getStateMinor(ID.M.FollowMax);
                	this.minDistSq = fMin * fMin;
                    this.maxDistSq = fMax * fMax;
                    
                    //計算直線距離
                	this.distX = this.gx - this.host2.posX;
            		this.distY = this.gy - this.host2.posY;
            		this.distZ = this.gz - this.host2.posZ;
                	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;
        		}
        	}//end update
        	
        	//end move
        	if(this.distSq <= this.minDistSq) {
        		this.ShipNavigator.clearPathEntity();
        	}
        	
        	//設定頭部轉向
            this.host2.getLookHelper().setLookPosition(gx, gy, gz, 30F, (float)this.host2.getVerticalFaceSpeed());

        	//距離超過傳送距離, 直接傳送到目標上
        	if(this.distSq > this.maxDistSq && host2.dimension == host.getStateMinor(ID.M.GuardDim)) {
        		this.checkTeleport++;
        		
        		if(this.checkTeleport > 200) {
        			LogHelper.info("DEBUG : guarding AI: point away > 200 ticks, teleport entity");
        			this.checkTeleport = 0;
        			
        			//teleport
        			if(this.distSq > 1024) {	//32 blocks away, drop seat2
        				this.clearMountSeat2();
        			}
        			
    				this.host2.setLocationAndAngles(this.gx+0.5D, this.gy+0.5D, this.gz+0.5D, this.host2.rotationYaw, this.host2.rotationPitch);
    				this.ShipNavigator.clearPathEntity();
    				this.sendSyncPacket();
                    return;
        		}
        	}
        	
        	//每cd到找一次路徑
        	if(this.findCooldown <= 0) {
    			this.findCooldown = 30;
    			
    			//check path result
            	if(host2.dimension == host.getStateMinor(ID.M.GuardDim) && !this.ShipNavigator.tryMoveToXYZ(gx, gy, gz, 1D)) {
            		LogHelper.info("DEBUG : guarding AI: fail to move, cannot reach or too far away "+gx+" "+gy+" "+gz);
            		//若超過max dist持續120ticks, 則teleport
            		if(this.distSq > this.maxDistSq && host2.dimension == host.getStateMinor(ID.M.GuardDim)) {
            			this.checkTeleport++;	//若距離超過max dist且移動又失敗, 會使checkTP每30 tick+1
                		
                		if(this.checkTeleport > 120) {
                			this.checkTeleport = 0;
                			
                			if(this.distSq > 1024) {	//32 blocks away, drop seat2
                				this.clearMountSeat2();
                			}
                			
                			//teleport
                			this.host2.setLocationAndAngles(this.gx+0.5D, this.gy+0.5D, this.gz+0.5D, this.host2.rotationYaw, this.host2.rotationPitch);
            				this.ShipNavigator.clearPathEntity();
            				this.sendSyncPacket();
                            return;
                		}	
                    }
                }//end !try move
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
  			host2.mountEntity(null);
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
