package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.entity.IShipMount;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

/**MOUNT FOLLOW OWNER AI
 */
public class EntityAIMountFollowOwner extends EntityAIBase {

	private IShipMount mountM;
	private EntityLiving mount;
    private BasicEntityShip host;
    private EntityLivingBase owner;
    private static final double TP_DIST = 2048D;	//teleport condition ~ 45 blocks
//    private PathNavigate PetPathfinder;
    private ShipPathNavigate ShipPathfinder;
    private int findCooldown;
    private double maxDistSq;
    private double minDistSq;
    private double distSq;
    private double distSqrt;
    
    //直線前進用餐數
    private double distX, distY, distZ, motX, motY, motZ;	//跟目標的直線距離(的平方)
    private float rotYaw;

    
    public EntityAIMountFollowOwner(IShipMount entity) {
    	this.mountM = entity;
    	this.mount = (EntityLiving) entity;
        this.host = (BasicEntityShip) entity.getRiddenByEntity();
//        this.PetPathfinder = mount.getNavigator();
        this.ShipPathfinder = mountM.getShipNavigate();
        this.distSq = 1D;
        this.distSqrt = 1D;
        this.setMutexBits(7);
    }
    
    //有owner且目標超過max dist時觸發AI, 觸發後此方法不再執行, 改為持續執行cont exec
    public boolean shouldExecute() {
    	//set host, dunno why host always reset to null
    	if(mountM.getRiddenByEntity() == null) {
    		return false;
    	}
    	else {
    		host = (BasicEntityShip) mountM.getRiddenByEntity();
    	}
    	
    	if(!host.isSitting() && !host.getLeashed() && !mount.getLeashed() && !host.getStateFlag(ID.F.NoFuel)) {
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
            	this.distX = this.owner.posX - this.mount.posX;
        		this.distY = this.owner.posY - this.mount.posY;
        		this.distZ = this.owner.posZ - this.mount.posZ;
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
    	//set host, dunno why host always reset to null
    	if(mountM.getRiddenByEntity() == null) {
    		return false;
    	}
    	else {
    		host = (BasicEntityShip) mountM.getRiddenByEntity();
    	}
    	
    	//計算直線距離
    	this.distX = this.owner.posX - this.mount.posX;
		this.distY = this.owner.posY - this.mount.posY;
		this.distZ = this.owner.posZ - this.mount.posZ;
    	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;
    	
    	//距離超過傳送距離
    	if(this.distSq > this.TP_DIST) {
    		return true;
    	}

    	if(this.distSq > this.minDistSq && !this.host.isSitting()) {
    		if(this.mount.isInWater()) {	//用於液體中移動, 液體中找path經常false
    			return true;
    		}
    		
    		if(!this.ShipPathfinder.noPath()) {		//用於陸上移動, path find可正常運作
    			return true;
    		}
    	}
    	return shouldExecute();
    }

    public void startExecuting() {
    	this.rotYaw = 0F;
        this.findCooldown = 20;
    }

    public void resetTask() {
        this.owner = null;
        this.ShipPathfinder.clearPathEntity();
    }

    public void updateTask() {
    	//set host, dunno why host always reset to null
    	if(mountM.getRiddenByEntity() == null) {
    		return;
    	}
    	else {
    		host = (BasicEntityShip) mountM.getRiddenByEntity();
    	}
//    	LogHelper.info("DEBUG : exec follow owner");
    	this.findCooldown--;
    	this.motY = 0D;
    	
    	//設定頭部轉向
        this.host.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float)this.host.getVerticalFaceSpeed());

        if(!this.host.isSitting() && !this.host.getLeashed()) {
        	//距離超過傳送距離, 直接傳送到目標上
        	if(this.distSq > this.TP_DIST) {
        		this.mount.posX = this.owner.posX;
        		this.mount.posY = this.owner.posY + 1D;
        		this.mount.posZ = this.owner.posZ;
        		this.mount.setPosition(this.mount.posX, this.mount.posY, this.mount.posZ);
        	}

        	//每cd到找一次路徑
        	if(this.findCooldown <= 0) {
    			this.findCooldown = 20;

            	if(!this.ShipPathfinder.tryMoveToEntityLiving(this.owner, 1D)) {
            		LogHelper.info("DEBUG : mount follow AI: mount fail to follow, teleport entity");
            		if(this.distSq > this.maxDistSq) {
            			//相同dim才傳送
            			LogHelper.info("DEBUG : follow AI: entity dimension "+mount.dimension+" "+owner.dimension);
            			if(this.mount.dimension == this.owner.dimension) {
            				this.mount.setLocationAndAngles(this.owner.posX, this.owner.posY + 0.5D, this.owner.posZ, this.mount.rotationYaw, this.mount.rotationPitch);
                        	this.ShipPathfinder.clearPathEntity();
                            return;
            			}
                    }
                }//end !try move to owner
            }//end path find cooldown
        }
    }
	
	
}
