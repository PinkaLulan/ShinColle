package com.lulan.shincolle.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.IShipGuardian;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;
/**SHIP GUARDING AI
 * CanFollow = false時可以執行
 * 持續固守某一點followMax格之內, 距離該點followMax格以上就會嘗試回去該點直到靠近followMin格內
 * 距離超過max格超過指定時間後, 強制傳送回該點
 * 若host的gy<=0, 則視為必須跟隨owner, 會設定CanFollow = true
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
    			this.gx = host.getStateMinor(ID.N.GuardX);
        		this.gy = host.getStateMinor(ID.N.GuardY);
        		this.gz = host.getStateMinor(ID.N.GuardZ);
    		}
    		
    		//若gy=0, 表示這entity剛初始化, 還不能執行AI
    		if(gy <= 0) {
    			host.setStateFlag(ID.F.CanFollow, true);
    			return false;
    		}
    		else {
    			float fMin = host.getStateMinor(ID.N.FollowMin);
            	float fMax = host.getStateMinor(ID.N.FollowMax);
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
        			this.gx = host.getStateMinor(ID.N.GuardX);
            		this.gy = host.getStateMinor(ID.N.GuardY);
            		this.gz = host.getStateMinor(ID.N.GuardZ);
        		}
        		
        		//若gy<=0, 表示這entity停止定點防守, 改跟隨owner
        		if(gy <= 0) {
        			host.setStateFlag(ID.F.CanFollow, true);
        			this.resetTask();
        			return;
        		}
        		else {
        			float fMin = host.getStateMinor(ID.N.FollowMin);
                	float fMax = host.getStateMinor(ID.N.FollowMax);
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
        	if(this.distSq > this.maxDistSq && host2.dimension == host.getStateMinor(ID.N.GuardDim)) {
        		this.checkTeleport++;
        		
        		if(this.checkTeleport > 200) {
        			LogHelper.info("DEBUG : guarding AI: point away > 200 ticks, teleport entity");
        			this.checkTeleport = 0;
        			//teleport
    				this.host2.setLocationAndAngles(this.gx, this.gy + 0.5D, this.gz, this.host2.rotationYaw, this.host2.rotationPitch);
    				this.ShipNavigator.clearPathEntity();
                    return;
        		}
        	}
        	
        	//每cd到找一次路徑
        	if(this.findCooldown <= 0) {
    			this.findCooldown = 30;
    			
    			//check path result
            	if(!this.ShipNavigator.tryMoveToXYZ(gx, gy, gz, 1D)) {
            		LogHelper.info("DEBUG : guarding AI: fail to move, cannot reach or too far away");
            		//若超過max dist持續240ticks, 則teleport
            		if(this.distSq > this.maxDistSq && host2.dimension == host.getStateMinor(ID.N.GuardDim)) {
            			this.checkTeleport++;	//若距離超過max dist且移動又失敗, 會使checkTP每30 tick+1
                		
                		if(this.checkTeleport > 8) {
                			this.checkTeleport = 0;
                			//teleport
            				this.host2.setLocationAndAngles(this.gx, this.gy + 0.5D, this.gz, this.host2.rotationYaw, this.host2.rotationPitch);
            				this.ShipNavigator.clearPathEntity();
                            return;
                		}	
                    }
                }//end !try move
            }//end path find cooldown
    	}
    }
	
	
}
