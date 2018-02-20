package com.lulan.shincolle.ai.path;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;

/**SHIP MOVE HELPER
 * 配合ship navigator使用, 額外增加y軸移動量, 適用於水中或飛行entity
 * 
 * 1.9.4:
 * 去除update變數, 改用action代替:
 * action可代表多種移動狀態, 其中WAIT狀態表示不需要移動更新
 * 
 */
public class ShipMoveHelper
{
	
    /** The EntityLiving that is being moved */
    private EntityLiving entity;
    private IShipNavigator entityN;
    private double posX;
    private double posY;
    private double posZ;
    /** The speed at which the entity should move */
    private double speed;
    private float rotateLimit;  //每tick最多可以轉身的角度, 角度小則轉彎半徑大
    private int stuckTick;
    protected Action action = Action.WAIT;
    

    public ShipMoveHelper(EntityLiving entity, float rotlimit)
    {
        this.entity = entity;
        this.entityN = (IShipNavigator) entity;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
        this.rotateLimit = rotlimit;
        this.stuckTick = 0;
        
    }

    public boolean isUpdating()
    {
    	return this.action == Action.MOVE_TO;
    }

    public double getSpeed()
    {
        return this.speed;
    }

    /**
     * 指定移動到某點, 狀態設為MOVE_TO
     */
    public void setMoveTo(double x, double y, double z, double speed)
    {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speed;
        this.action = Action.MOVE_TO;
        
    }

    /**CHANGE: 增加y軸移動計算, 不靠自然掉落或者跳躍來移動y軸
     * 適用於ship跟airplane
     */
    public void onUpdateMoveHelper()
    {
    	//reset movement
        this.entity.setMoveForward(0F);
        
        //MOVE_TO狀態
        if (this.action == Action.MOVE_TO)
        {
        	this.action = Action.WAIT;
        	
            //計算目標點跟目前點差距
            double x1 = this.posX - this.entity.posX;
            double y1 = this.posY - this.entity.posY;
            double z1 = this.posZ - this.entity.posZ;
            double moveSq = x1 * x1 + y1 * y1 + z1 * z1;
            
            //若距離夠大, 則計算身體面向方向, 以及y軸移動動作
            if (moveSq > 0.001D)
            {
                float f = (float)(MathHelper.atan2(z1, x1) * Values.N.DIV_180_PI) - 90F;
                float moveSpeed = (float) this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
                
                //check formation speed
                if (this.entity instanceof BasicEntityShip)
                {
                	//if in formation
                	if (((BasicEntityShip) this.entity).getStateMinor(ID.M.FormatType) > 0)
                	{
                		moveSpeed = FormationHelper.getFormationMOV((BasicEntityShip) this.entity);
                	}
                }
                else if (this.entity instanceof BasicEntityMount)
                {
                	BasicEntityShip host = (BasicEntityShip) ((BasicEntityMount) this.entity).getHostEntity();
                	
                	if (host != null && host.getStateMinor(ID.M.FormatType) > 0)
                	{
                		moveSpeed = FormationHelper.getFormationMOV(host);
                	}
                }
                
                moveSpeed *= this.speed;

                //設定每tick最多可以轉動的角度
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, this.rotateLimit);
                
                //y軸移動: 由於官方setAIMoveSpeed只提供水平移動, 因此y軸移動必須自行設定
                //爬升時速度較慢, 落下時速度快
                //fly entity
                if (entityN.canFly())
	            {
                	if(y1 > 0.5D)
                	{
                		this.entity.motionY += moveSpeed * 0.12D;
                		moveSpeed *= 0.8F;
                	}
                	else if (y1 < -0.5D)
                	{
                		this.entity.motionY -= moveSpeed * 0.16D;
                		moveSpeed *= 0.92F;
                	}
                }
                //non-fly entity in water
                else if (EntityHelper.checkEntityIsInLiquid(this.entity))
                {
                	if (y1 > 1D)  //UP
            		{
                    	this.entity.motionY += moveSpeed * 0.2D;
                    	moveSpeed *= 0.5F;
                    }
                	else if (y1 > 0.35D)  //UP
            		{
                    	this.entity.motionY += moveSpeed * 0.1D;
                    	moveSpeed *= 0.5F;
                    }
                    else if (y1 < -1D)  //DOWN
                    {
                    	this.entity.motionY -= moveSpeed * 0.25D;
                    	moveSpeed *= 0.82F;
                    }
                }
                else if (y1 > this.entity.stepHeight && x1 * x1 + z1 * z1 < 1D)
                {	//用於陸上跳躍
                    this.entity.getJumpHelper().setJumping();
                }

                this.entity.setAIMoveSpeed(moveSpeed);
            }
            //若移動目標的距離太小(ex: xyz差距在0.1格內), 則停止entity移動
            else
            {
            	this.entity.setMoveForward(0F);
                return;
            }
            
        }//end MOVE_TO
        //WAIT狀態
        else
        {
            this.entity.setMoveForward(0F);
        }//end WAIT
        
    }

    /**
     * Limits the given angle to a upper and lower limit.
     */
    private float limitAngle(float yaw, float degree, float limit)
    {
        float f = MathHelper.wrapDegrees(degree - yaw);

        if (f > limit)
        {
            f = limit;
        }

        if (f < -limit)
        {
            f = -limit;
        }

        float f1 = yaw + f;

        if (f1 < 0.0F)
        {
            f1 += 360.0F;
        }
        else if (f1 > 360.0F)
        {
            f1 -= 360.0F;
        }

        return f1;
    }
    
    /** move state */
    public static enum Action
    {
        WAIT,
        MOVE_TO;
    }
    
    
}