package com.lulan.shincolle.ai.path;

import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.utility.EntityHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;

/**SHIP MOVE HELPER
 * 配合ship navigator使用, 額外增加y軸移動量, 適用於水中或飛行entity
 */
public class ShipMoveHelper {
    /** The EntityLiving that is being moved */
    private EntityLiving entity;
    private IShipNavigator entityN;
    private double posX;
    private double posY;
    private double posZ;
    /** The speed at which the entity should move */
    private double speed;
    private boolean update;
    private float rotateLimit;  //每tick最多可以轉身的角度, 角度小則轉彎半徑大


    public ShipMoveHelper(EntityLiving entity, float rotlimit) {
        this.entity = entity;
        this.entityN = (IShipNavigator) entity;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
        this.rotateLimit = rotlimit;
    }

    public boolean isUpdating() {
        return this.update;
    }

    public double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setMoveTo(double x, double y, double z, double speed) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speed;
        this.update = true;
    }

    /**CHANGE: 增加y軸移動計算, 不靠自然掉落或者跳躍來移動y軸
     * 適用於ship跟airplane
     */
    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0F);

        if(this.update) {
            this.update = false;
            
            //計算目標點跟目前點差距
            int i = MathHelper.floor_double(this.entity.boundingBox.minY + 0.5D);
            double x1 = this.posX - this.entity.posX;
            double z1 = this.posZ - this.entity.posZ;
            double y1 = this.posY - this.entity.posY;
            double moveSq = x1 * x1 + y1 * y1 + z1 * z1;
            
            //若移動值夠大, 則計算身體面向方向, 以及y軸移動動作
            if(moveSq >= 0.1D) {
                float f = (float)(Math.atan2(z1, x1) * 180.0D / Math.PI) - 90.0F;
                float moveSpeed = (float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
//                LogHelper.info("DEBUG : moveHelper: update f "+(x1 * x1 + z1 * z1));
                
                //設定每tick最多可以轉動的角度
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, this.rotateLimit);
//                this.entity.setAIMoveSpeed(moveSpeed);

                //y軸移動: 由於官方setAIMoveSpeed只提供水平移動, 因此y軸移動必須自行設定
                //爬升時速度較慢, 落下時速度快
                //fly entity
                if(entityN.canFly()) {
//                    if(x1 * x1 + z1 * z1 < 3.0D) {
                        if(y1 > 0D) {
                        	this.entity.motionY += moveSpeed * 0.12D;
                        	moveSpeed *= 0.8F;
                        }
                        else if(y1 < -0.2D){
                        	this.entity.motionY -= moveSpeed * 0.16D;
                        	moveSpeed *= 0.92F;
                        }
//                    }
                }
                //non-fly entity
                else if(EntityHelper.checkEntityIsInLiquid(entity)) {
//                	if(x1 * x1 + z1 * z1 < 3.0D) {
                		if(y1 > 0D) {
                        	this.entity.motionY += moveSpeed * 0.1D;
                        	moveSpeed *= 0.5F;
//                        	LogHelper.info("DEBUG : moveHelper: get up in water ");
                        }
                        else if(y1 < -0.2D){
                        	this.entity.motionY -= moveSpeed * 0.12D;
                        	moveSpeed *= 0.82F;
//                        	LogHelper.info("DEBUG : moveHelper: get down in water ");
                        }
//                	}
                }
                else if(y1 > 0.0D && x1 * x1 + z1 * z1 < 3.0D) {	//用於陸上跳躍
//                	LogHelper.info("DEBUG : moveHelper: get up on land ");
                    this.entity.getJumpHelper().setJumping();
                }
//                LogHelper.info("DEBUG : moveHelper: speed "+moveSpeed);
                this.entity.setAIMoveSpeed(moveSpeed);
            }
        }
    }

    /**
     * Limits the given angle to a upper and lower limit.
     */
    private float limitAngle(float yaw, float degree, float limit) {
        float f3 = MathHelper.wrapAngleTo180_float(degree - yaw);

        if(f3 > limit) {
            f3 = limit;
        }

        if(f3 < -limit) {
            f3 = -limit;
        }

        return yaw + f3;
    }
}
