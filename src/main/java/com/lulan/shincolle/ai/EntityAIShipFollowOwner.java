package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipMount;
import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
/**SHIP FOLLOW OWNER AI
 * 距離超過max dist時觸發移動, 直到走進min dist距離時停止
 * 距離超過TP_DIST會直接teleport到owner旁邊
 */
public class EntityAIShipFollowOwner extends EntityAIBase {

    private BasicEntityShip host;
    private EntityLivingBase owner;
    World TheWorld;
    private static final double TP_DIST = 2048D;	//teleport condition ~ 45 blocks
//    private PathNavigate PetPathfinder;
    private ShipPathNavigate ShipNavigator;
    private int findCooldown;
    private double maxDistSq;
    private double minDistSq;
    private double distSq;
    private double distSqrt;
    
    //直線前進用餐數
    private double distX, distY, distZ, motX, motY, motZ;	//跟目標的直線距離(的平方)
    private float rotYaw;

    
    public EntityAIShipFollowOwner(BasicEntityShip entity) {
        this.host = entity;
        this.TheWorld = entity.worldObj;
//        this.PetPathfinder = entity.getNavigator();
        this.ShipNavigator = entity.getShipNavigate();
        this.distSq = 1D;
        this.distSqrt = 1D;
        this.setMutexBits(7);  
    }
    
    //有owner且目標超過max dist時觸發AI, 觸發後此方法不再執行, 改為持續執行cont exec
    public boolean shouldExecute() {
    	if(!host.isSitting() && !host.isRiding() && !host.getLeashed() && !host.getStateFlag(ID.F.NoFuel)) {
    		EntityLivingBase OwnerEntity = this.host.getOwner();

            //get owner distance
            if(OwnerEntity != null) {
            	this.owner = OwnerEntity;
                
            	if(this.owner.dimension != this.host.dimension) {
            		return false;
            	}
            	
            	float fMin = host.getStateMinor(ID.N.FollowMin);
            	float fMax = host.getStateMinor(ID.N.FollowMax);
            	this.minDistSq = fMin * fMin;
                this.maxDistSq = fMax * fMax;

            	//計算直線距離
            	this.distX = this.owner.posX - this.host.posX;
        		this.distY = this.owner.posY - this.host.posY;
        		this.distZ = this.owner.posZ - this.host.posZ;
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
    	//計算直線距離
    	this.distX = this.owner.posX - this.host.posX;
		this.distY = this.owner.posY - this.host.posY;
		this.distZ = this.owner.posZ - this.host.posZ;
    	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;
    	
    	//距離超過傳送距離, 則處理傳送部份
    	if(this.distSq > this.TP_DIST) {
    		return true;
    	}

    	//有path還沒走完, 則處理path移動部份
    	if(this.distSq > this.minDistSq && !host.isSitting() && !host.isRiding()) {		
    		if(!this.ShipNavigator.noPath()) {		//用於水空移動, 若有path
    			return true;
    		}
    	}
    	
    	//其他情況
    	return shouldExecute();
    }

    public void startExecuting() {
    	this.rotYaw = 0F;
        this.findCooldown = 20;
    }

    public void resetTask() {
        this.owner = null;
        this.ShipNavigator.clearPathEntity();
    }

    public void updateTask() {
//    	LogHelper.info("DEBUG : exec follow owner");
    	this.findCooldown--;
    	this.motY = 0D;
    	
    	//設定頭部轉向
        this.host.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float)this.host.getVerticalFaceSpeed());

        if(!this.host.isSitting() && !this.host.getLeashed()) {
        	//距離超過傳送距離, 直接傳送到目標上
        	if(this.distSq > this.TP_DIST) {
        		this.host.posX = this.owner.posX;
        		this.host.posY = this.owner.posY + 1D;
        		this.host.posZ = this.owner.posZ;
        		this.host.setPosition(this.host.posX, this.host.posY, this.host.posZ);
        	}
        	
        	//每cd到找一次路徑
        	if(this.findCooldown <= 0) {
    			this.findCooldown = 30;

    			//check path result
            	if(!this.ShipNavigator.tryMoveToEntityLiving(this.owner, 1D)) {
            		LogHelper.info("DEBUG : follow AI: fail to follow, teleport entity");
            		if(this.distSq > this.TP_DIST) {
            			//相同dim才傳送
            			LogHelper.info("DEBUG : follow AI: entity dimension "+host.dimension+" "+owner.dimension);
            			if(this.host.dimension == this.owner.dimension) {
            				this.host.setLocationAndAngles(this.owner.posX, this.owner.posY + 0.5D, this.owner.posZ, this.host.rotationYaw, this.host.rotationPitch);
                        	this.ShipNavigator.clearPathEntity();
                            return;
            			}
                    }
                }//end !try move to owner
            }//end path find cooldown
        }
    }
	
	
}