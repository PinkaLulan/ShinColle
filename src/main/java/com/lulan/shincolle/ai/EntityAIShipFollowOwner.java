package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
/**SHIP FOLLOW OWNER AI
 * 會持續跟隨owner, 不會因為找不到路徑就停止跟隨
 * 距離超過max dist時觸發移動, 直到走進min dist距離時停止
 * 距離超過40格會直接teleport到owner旁邊
 * 
 * @parm entity, move speed, min dist, max dist
 */
public class EntityAIShipFollowOwner extends EntityAIBase {
    private BasicEntityShip ThePet;
    private EntityLivingBase TheOwner;
    World TheWorld;
    private static final double TP_DIST = 2048D;	//teleport condition ~ 45 blocks
    private PathNavigate PetPathfinder;
    private int findCooldown;
    private double maxDistSq;
    private double minDistSq;
    private double distSq;
    private double distSqrt;
    
    //直線前進用餐數
    private double distX, distY, distZ, motX, motY, motZ;	//跟目標的直線距離(的平方)
    private float rotYaw;

    public EntityAIShipFollowOwner(BasicEntityShip entity, float MinDist, float MaxDist) {
        this.ThePet = entity;
        this.TheWorld = entity.worldObj;
        this.PetPathfinder = entity.getNavigator();
        this.minDistSq = MinDist * MinDist;
        this.maxDistSq = MaxDist * MaxDist;
        this.distSq = 1D;
        this.distSqrt = 1D;
        this.setMutexBits(7);
    }

    //有owner且目標超過max dist時觸發AI, 觸發後此方法不再執行, 改為持續執行cont exec
    public boolean shouldExecute() {
        EntityLivingBase OwnerEntity = this.ThePet.getOwner();

        //get owner distance
        if(OwnerEntity != null) {
        	this.TheOwner = OwnerEntity;
        	
        	//計算直線距離
        	this.distX = this.TheOwner.posX - this.ThePet.posX;
    		this.distY = this.TheOwner.posY - this.ThePet.posY - 1;
    		this.distZ = this.TheOwner.posZ - this.ThePet.posZ;
        	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;

        	if(!this.ThePet.isSitting() && !this.ThePet.getLeashed() && distSq > this.maxDistSq) {
        		return true;
        	}
        }
        
        return false;
    }

    //目標還沒接近min dist或者距離超過TP_DIST時繼續AI
    public boolean continueExecuting() {
//    	LogHelper.info("DEBUG : exec follow owner");
    	//計算直線距離
    	this.distX = this.TheOwner.posX - this.ThePet.posX;
		this.distY = this.TheOwner.posY - this.ThePet.posY - 1;
		this.distZ = this.TheOwner.posZ - this.ThePet.posZ;
    	this.distSq = this.distX*this.distX + this.distY*this.distY + this.distZ*this.distZ;
    	
    	//距離超過傳送距離
    	if(this.distSq > this.TP_DIST) {
    		return true;
    	}

    	if(this.distSq > this.minDistSq && !this.ThePet.isSitting()) {
    		if(this.ThePet.getShipDepth() > 0D) {	//用於液體中移動, 液體中找path經常false
    			return true;
    		}
    		
    		if(!this.PetPathfinder.noPath()) {		//用於陸上移動, path find可正常運作
    			return true;
    		}
    	}
    	return false;
    }

    public void startExecuting() {
    	this.rotYaw = 0F;
        this.findCooldown = 0;
        this.PetPathfinder.setAvoidsWater(false);
        this.PetPathfinder.setEnterDoors(true);
        this.PetPathfinder.setCanSwim(true);
    }

    public void resetTask() {
        this.TheOwner = null;
        this.PetPathfinder.clearPathEntity();
    }

    public void updateTask() {
    	this.findCooldown--;
    	this.motY = 0D;
    	
    	//設定頭部轉向
        this.ThePet.getLookHelper().setLookPositionWithEntity(this.TheOwner, 10.0F, (float)this.ThePet.getVerticalFaceSpeed());

        if(!this.ThePet.isSitting() && !this.ThePet.getLeashed()) {
        	//距離超過傳送距離, 直接傳送到目標上
        	if(this.distSq > this.TP_DIST) {
        		this.ThePet.posX = this.TheOwner.posX;
        		this.ThePet.posY = this.TheOwner.posY + 1D;
        		this.ThePet.posZ = this.TheOwner.posZ;
        		this.ThePet.setPosition(this.ThePet.posX, this.ThePet.posY, this.ThePet.posZ);
        	}
        	
    		//在液體中, 採直線移動
        	if(this.ThePet.getShipDepth() > 0D) {
        		//額外加上y軸速度, getPathToXYZ對空氣跟液體方塊無效, 因此y軸速度要另外加
        		if(this.distY > 1.5D && this.ThePet.getShipDepth() > 1.5D) {  //避免水面彈跳
        			this.motY = 0.2F;
        		}
        		else if(this.distY < -1D) {
        			this.motY = -0.2F;
        		}
        		else {
	        		this.motY = 0F;
	        	}
        		
        		//若直線可視, 則直接直線移動
        		if(this.ThePet.getEntitySenses().canSee(this.TheOwner)) {
        			double speed = this.ThePet.getStateFinal(ID.MOV);
        			this.distSqrt = MathHelper.sqrt_double(this.distSq);
        			this.motX = (this.distX / this.distSqrt) * speed * 1D;
        			this.motZ = (this.distZ / this.distSqrt) * speed * 1D;
//        			LogHelper.info("DEBUG  : follow owner: "+motX+" "+motZ);
        			this.ThePet.motionY = this.motY;
        			this.ThePet.motionX = this.motX;
        			this.ThePet.motionZ = this.motZ;
//        			this.ThePet.getMoveHelper().setMoveTo(this.ThePet.posX+this.motX, this.ThePet.posY+this.motY, this.ThePet.posZ+this.motZ, 1D);
        			
        			//身體角度設定
        			float[] degree = EntityHelper.getLookDegree(motX, motY, motZ);
        			this.ThePet.rotationYaw = degree[0];
        			this.ThePet.rotationPitch = degree[1];
        			
        			//若水平撞到東西, 則嘗試跳跳
	        		if(this.ThePet.isCollidedHorizontally) {
	        			this.ThePet.motionY += 0.2D;
	        		}
        			return;
        		}
           	}
        	
        	//每cd到找一次路徑
        	if(this.findCooldown <= 0) {
    			this.findCooldown = 10;

            	if(!this.PetPathfinder.tryMoveToEntityLiving(this.TheOwner, 1D)) {
            		LogHelper.info("DEBUG : AI try move fail, teleport entity");
            		if(this.distSq > this.maxDistSq) {
                    	//重複找18次目標附近的隨機點, 直接傳送到目的地
                    	int offsetX, offsetZ, i, j, k;
                    	Block targetBlock;
                    	for(int t=0; t<18; t++) {
                    		offsetX = this.TheWorld.rand.nextInt(7);
                        	offsetZ = this.TheWorld.rand.nextInt(7);
                            i = (int)(this.TheOwner.posX) - 3 + offsetX;
                            j = (int)(this.TheOwner.posZ) - 3 + offsetZ;
                            k = (int)(this.TheOwner.boundingBox.minY) - 1;
                            targetBlock = this.TheWorld.getBlock(i, k, j);
                            //bug: getBlock always get air from water block
                            if(targetBlock != null && 
                               (targetBlock.isSideSolid(TheWorld, i, k, j, ForgeDirection.UP) ||
                            	targetBlock == Blocks.water ||
                            	targetBlock == Blocks.lava)) {
                            	this.ThePet.setLocationAndAngles((double)(i+0.5D), (double)(k+1D), (double)(j+0.5D), this.ThePet.rotationYaw, this.ThePet.rotationPitch);
                            	this.PetPathfinder.clearPathEntity();
                                return;
                            }
                    	}
                    }
                }//end !try move to owner
            }//end path find cooldown
        }
    }
	
	
}