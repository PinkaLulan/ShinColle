package com.lulan.shincolle.ai.path;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;

public class ShipMoveHelper {
    /** The EntityLiving that is being moved */
    private EntityLiving entity;
    private double posX;
    private double posY;
    private double posZ;
    /** The speed at which the entity should move */
    private double speed;
    private boolean update;


    public ShipMoveHelper(EntityLiving entity) {
        this.entity = entity;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
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
            double y1 = this.posY - (double)i;
            double moveSq = x1 * x1 + y1 * y1 + z1 * z1;

            //若移動值夠大, 則計算身體面向方向, 以及y軸移動動作
            if(moveSq >= 2.500000277905201E-7D) {
                float f = (float)(Math.atan2(z1, x1) * 180.0D / Math.PI) - 90.0F;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, 30.0F);
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));

                if (y1 > 0.0D && x1 * x1 + z1 * z1 < 1.0D) {
                    this.entity.getJumpHelper().setJumping();
                }
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
