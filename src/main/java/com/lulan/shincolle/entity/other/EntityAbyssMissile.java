package com.lulan.shincolle.entity.other;

import java.util.HashMap;
import java.util.List;

import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipAttrs;
import com.lulan.shincolle.entity.IShipFlyable;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.IShipProjectile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ENTITY ABYSS MISSILE
 * XZ為等速運動, Y軸為等加速度移動的entity, 有數種移動方式
 */
public class EntityAbyssMissile extends Entity implements IShipOwner, IShipAttrs, IShipFlyable, IShipCustomTexture, IShipProjectile
{
	
    protected IShipAttackBase host;	//main host type
    protected EntityLiving host2;	//second host type: entity living
    protected int playerUID;		//owner UID, for owner check
    protected Attrs attrs;
    protected int type;
    public int moveType;
    protected float[] data;
    public int life;
    public HashMap<Integer, int[]> EffectMap;
    public boolean startMove;
    public int startMoveDelay;
    
	//move speed
    public double vel0;			//XZ初速(拋物線) or XYZ初速(直線)
    public double accY1;		//Y加速度1(拋物線:上升中)
    public double accY2;		//Y加速度2(拋物線:下降中)
    public double t0;			//上升時間
    public double t1;			//下降時間
    public double velX;			//三軸速度
    public double velY;
    public double velZ;
    
    //misc attrs
    public int invisibleTicks;
    
    
    //基本constructor, size必須在此設定
    public EntityAbyssMissile(World world)
    {
    	super(world);
    	this.setSize(1F, 1F);
    	this.data = new float[11];
    }

    /**
     * type:<br>
     *   0: white smoke<br>
     *   1: NO_USE<br>
     *   2: railgun<br>
     *   3: cluster missile main<br>
     *   4: cluster missile sub<br>
     *   5: black hole<br>
     * <br>
     * move type:<br>
     *   0: direct without gravity<br>
     *   1: parabola<br>
     *   2: sim-torpedo<br>
     *   3: direct with gravity<br>
     *   4: custom xyz
     * <br>
     * data:<br>
     *   basic missile:<br>
     *     0:atk, 1:knockback, 2:launchPosY, 3:tarX, 4:tarY, 5:tarZ, 6:life, 7:addHeight, 8:vel0, 9:accY1, 10:accY2 <br>
     *   addition pos:<br>
     *     11:posX, 12:posY, 13:posZ<br>
     *   invisible time:<br>
     *     14:invisible ticks<br>
     */
    public EntityAbyssMissile(World world, IShipAttackBase host, int type, int moveType, float[] data)
    {
    	this(world);
        
        //設定host跟owner
        this.host = host;
        this.host2 = (EntityLiving) host;
        this.EffectMap = host.getAttackEffectMap();  //發射時就決定debuff內容
        this.setPlayerUID(host.getPlayerUID());
        this.type = type;
        this.moveType = moveType;
        this.data = data;
        this.life = (int) data[6];
        this.startMove = false;
        this.startMoveDelay = 0;
        
        //set basic attributes
        this.attrs = new Attrs();
        this.attrs.copyRaw2Buffed();
        this.attrs.setAttrsBuffed(ID.Attrs.ATK_L, data[0]);
        this.attrs.setAttrsBuffed(ID.Attrs.ATK_H, data[0]);
        this.attrs.setAttrsBuffed(ID.Attrs.ATK_AL, data[0]);
        this.attrs.setAttrsBuffed(ID.Attrs.ATK_AH, data[0]);
        this.attrs.setAttrsBuffed(ID.Attrs.DODGE, 0.5F);
        this.attrs.setAttrsBuffed(ID.Attrs.KB, data[1]);
        
        //set misc attrs
        if (this.data.length > 14)
        {
        	this.invisibleTicks = (int) this.data[14];
        }
        
        //set position
        if (this.data.length > 13)
        {
        	data[2] = data[12];
        	this.posX = data[11];
            this.posY = data[12];
            this.posZ = data[13];
        }
        else
        {
        	this.posX = this.host2.posX;
            this.posY = data[2];
            this.posZ = this.host2.posZ;
        }
        
        this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
    	this.setPosition(this.posX, this.posY, this.posZ);
    	
		//預設初速
    	this.vel0 = data[8];
    	this.accY1 = data[9];
    	this.accY2 = data[10];
    	this.t0 = 0D;
    	this.t1 = 0D;
    	
        //get target vector
    	Dist4d dist = CalcHelper.getDistanceFromA2B(this.getPositionVector(), new Vec3d(data[3], data[4], data[5]));

    	//若目標太近, 直接直射
    	if (dist.d < 4D)
    	{
    		this.moveType = 0;
    	}
    	
		//missile move type
        switch (this.moveType)
        {
        case 0:  //direct without gravity
        	this.velX = dist.x * this.vel0;
        	this.velY = dist.y * this.vel0;
        	this.velZ = dist.z * this.vel0;
        	this.accY1 = 0D;
        	this.accY2 = 0D;
    	break;
        case 1:   //parabola: 固定往上addHeight高度, 以此計算velY(Y初速), accY1(前半段加速度), accY2(後半段加速度)
        {
        	//若無設定addHeight, 則改為直射
        	if (data[7] <= 0F)
        	{
        		this.moveType = 0;
        		this.velX = dist.x * this.vel0;
            	this.velY = dist.y * this.vel0;
            	this.velZ = dist.z * this.vel0;
            	this.accY1 = 0D;
            	this.accY2 = 0D;
            	break;
        	}
        	
        	double dx = data[3] - this.posX;
        	double dz = data[5] - this.posZ;
        	double dxz = MathHelper.sqrt(dx * dx + dz * dz);
        	
        	//若水平距離太近, 改為直射
        	if (dxz <= 4D)
        	{
        		this.velX = dist.x * this.vel0;
            	this.velY = dist.y * this.vel0;
            	this.velZ = dist.z * this.vel0;
            	this.accY1 = 0D;
            	this.accY2 = 0D;
            	break;
        	}
        	else
        	{
            	//以vel0為xz軸初速, 計算花費時間t跟xz軸初速
        		dx /= dxz;
        		dz /= dxz;
        		double t = dxz / this.vel0;
        		
        		//額外高度, 定為目標距離的一定比例
        		double addHeight = dist.d * data[7];
        		double dy = Math.abs(this.posY - data[4]);
        		double hy = 0D;
        		this.velX = dx * this.vel0;
            	this.velZ = dz * this.vel0;
        		
        		//若目標比攻擊者位置高
        		if (this.posY - data[4] < 1D)
        		{
        			hy = MathHelper.sqrt(addHeight / (addHeight + dy));
        			this.t0 = MathHelper.floor(t / (1 + hy));
        			this.t1 = MathHelper.floor(t * hy / (1 + hy));
            		this.velY = 2D * (addHeight + dy) / t0;
            		this.accY1 = -this.velY / t0;
                	this.accY2 = -2D * addHeight / (t1 * t1);
        		}
        		//若目標比攻擊者位置低
        		else
        		{
        			hy = MathHelper.sqrt(addHeight / (addHeight + dy));
        			this.t0 = MathHelper.floor(t * hy / (1 + hy));
            		this.t1 = MathHelper.floor(t / (1 + hy));
            		this.accY1 = -2D * addHeight / (t0 * t0);
            		this.velY = -this.accY1 * t0;
                	this.accY2 = -2D * (addHeight + dy) / (t1 * t1);
        		}
        		
        		//若高低差太多導致過高的加速, 改為直射
        		double limit = 0.15D;
        		if (Math.abs(this.accY1) > limit || Math.abs(this.accY2) > limit)
        		{
        			this.moveType = 0;
        			this.velX = dist.x * this.vel0;
                	this.velY = dist.y * this.vel0;
                	this.velZ = dist.z * this.vel0;
                	this.accY1 = 0D;
                	this.accY2 = 0D;
                	break;
        		}
        	}
        }
    	break;
        case 2:   //torpedo
        	this.velX = dist.x * 0.6D;
        	this.velY = 0.1D;
        	this.velZ = dist.z * 0.6D;
        	this.accY1 = -0.035D;
    	break;
        case 3:   //direct with gravity
        	this.velX = dist.x * this.vel0;
        	this.velY = dist.y * this.vel0;
        	this.velZ = dist.z * this.vel0;
        	this.accY1 = -0.035D;
        	this.accY2 = -0.035D;
    	break;
        case 4:   //custom xyz
        	this.velX = data[3];
        	this.velY = data[4];
        	this.velZ = data[5];
    	break;
        default:
    	break;
        }
    }

    @Override
	protected void entityInit() {}

    /**
     * Checks if the entity is in range to render by using the past in distance and 
     * comparing it to its average bounding box edge length * 64 * renderDistanceWeight 
     * Args: distance
     * 
     * 由於entity可能不為正方體, 故取平均邊長大小來計算距離, 此方法預設為256倍邊長大小
     */
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distanceSq)
    {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 256D;
        return distanceSq < d1 * d1;
    }

    //update entity
    //注意: 移動要在server+client都做畫面才能顯示平順, particle則只能在client做
    @Override
	public void onUpdate()
    {
        super.onUpdate();
        
        /********* BOTH SIDE *******/
		//update position
    	this.motionX = this.velX;
    	this.motionY = this.velY;
    	this.motionZ = this.velZ;
		this.posX += this.motionX;
		this.posY += this.motionY;
        this.posZ += this.motionZ;
        this.setPosition(this.posX, this.posY, this.posZ);
		
        //calc new velocity
        this.handleMissileMovement();
        
        //timer--
		if (this.invisibleTicks > 0) this.invisibleTicks--;
        
        /**********server side***********/
    	if (!this.world.isRemote)
    	{
    		//沒有host資料, 消除此飛彈
    		if (this.host == null)
    		{
    			this.setDead();	//直接抹消, 不觸發爆炸
    			return;
    		}
    		
    		//發射超過8 sec, 設定為死亡(消失), 注意server restart後此值會歸零
    		if (this.ticksExisted > this.life)
    		{
    			this.onImpact(null);
    			return;
    		}
    		//sync missile at start
    		else if (this.ticksExisted == 1)
    		{
    			float[] data = new float[] {0F, (float)this.type, (float)this.moveType, (float)this.velX, (float)this.velY, (float)this.velZ, (float)this.vel0, (float)this.accY1, (float)this.accY2, this.invisibleTicks};
    			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
    	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_CustomData, data), point);
    		}
    		
    		//apply missile type action
    		switch (this.type)
    		{
    		case 3:
    			//spawn cluster sub missile
        		if (this.ticksExisted > 6 && this.ticksExisted < 41)
        		{
        			if ((this.ticksExisted & 7) == 0)
        			{
        				//atk = 50%, motionY = -0.06
        				float[] subdata = new float[] {this.data[0] * 0.5F, this.data[1], (float)this.posY - 0.75F, (float)this.motionX, (float)this.motionY, (float)this.motionZ, 140, 0F, 0.5F, -0.06F, -0.06F, (float)this.posX, (float)this.posY - 0.65F - MathHelper.abs((float)this.motionY), (float)this.posZ, 4};
        				EntityAbyssMissile subm = new EntityAbyssMissile(this.world, this.host, 4, 4, subdata);
        		        this.world.spawnEntity(subm);
        			}
        		}
			break;
    		}
    		
    		//超過一定時間才能爆炸
    		if (this.ticksExisted > 5)
    		{
    			//碰撞判定1: 本體位置碰撞: missile本身位置是固體方塊
        		IBlockState state = this.world.getBlockState(new BlockPos(this));
        		if (state.getMaterial().isSolid())
        		{
        			this.onImpact(null);
        		}
        		
        		//碰撞判定2: 移動量投射法: 以1 tick移動的範圍內判定會碰撞到的物體
        		Vec3d posStart = new Vec3d(this.posX, this.posY, this.posZ);
                Vec3d posEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                RayTraceResult raytrace = this.world.rayTraceBlocks(posStart, posEnd);          
                
                posStart = new Vec3d(this.posX, this.posY, this.posZ);
                posEnd = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                
                if (raytrace != null)
                {
                	posEnd = new Vec3d(raytrace.hitVec.xCoord, raytrace.hitVec.yCoord, raytrace.hitVec.zCoord);
                    
                    if (raytrace.typeOfHit == RayTraceResult.Type.ENTITY)
                    {
                    	if (raytrace.entityHit.canBeCollidedWith() && EntityHelper.isNotHost(this, raytrace.entityHit) && !TeamHelper.checkSameOwner(host2, raytrace.entityHit))
                    	{
                    		this.onImpact(raytrace.entityHit);
                    		return;
                    	}
                    }
                    else if (raytrace.typeOfHit == RayTraceResult.Type.BLOCK)
                    {
                    	this.onImpact(null);
                    	return;
                    }
                }
                
                //碰撞判定3: 擴展AABB碰撞: missile擴展1格大小內是否有entity可觸發爆炸
                List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().expand(1D, 1.5D, 1D));
                
                //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
                for (Entity ent : hitList)
                { 
                	//不會對自己主人觸發爆炸
                	if (ent.canBeCollidedWith() && EntityHelper.isNotHost(this, ent) && !TeamHelper.checkSameOwner(host2, ent))
                	{
                		this.onImpact(ent);
                		return;
                	}
                }
    		}
    	}//end server side
    	/**********client side***********/
    	else
    	{
    		//計算模型要轉的角度 (RAD, not DEG)
    		if (this.moveType == 2 && !this.startMove)
    		{
        		this.rotationPitch = 0F;
        		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ));
    		}
    		else
    		{
        		float f1 = MathHelper.sqrt(this.motionX*this.motionX + this.motionZ*this.motionZ);
        		this.rotationPitch = (float)(Math.atan2(this.motionY, f1));
        		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ));
    		}
    		
    		//依照x,z軸正負向修正角度(轉180)
    		if (this.motionX > 0)
    		{
    			this.rotationYaw -= Math.PI;
    		}
    		else
    		{
    			this.rotationYaw += Math.PI;
    		}
    		
    		//type 0: normal missile particle
    		if (this.type != 2)
    		{
        		//for high/low speed
        		if (this.vel0 > 0.55D || this.vel0 < 0.45D)
        		{
        			byte type = this.vel0 > 0.55D ? (byte)1 : (byte)2;
        			
        			if (this.invisibleTicks <= 0 &&
        				((this.moveType == 2 && this.startMove) ||
            			(this.moveType != 2 && this.ticksExisted > 2)))
            		{
            			for (int j = 0; j < 4; ++j)
                		{
                        	ParticleHelper.spawnAttackParticleAtEntity(this, type, new double[] {this.vel0, j});
                		}
            		}
        		}
        		//for normal speed
        		else
        		{
        			//spawn particle by speed type
            		byte smokeType = 15;
            		
        			switch(this.type)
            		{
            		case 3:  //cluster bomb main
            		case 4:  //cluster bomb sub
            			smokeType = 18;
            		break;
            		}
            		
        			if (this.invisibleTicks <= 0 &&
        				((this.moveType == 2 && this.startMove) ||
            			(this.moveType != 2 && this.ticksExisted > 2)))
            		{
            			for (int j = 0; j < 4; ++j)
                		{
                        	ParticleHelper.spawnAttackParticleAt(this.posX+this.motionX*2D-this.motionX*1.5D*j, this.posY+this.motionY*2D+0.5D-this.motionY*1.5D*j, this.posZ+this.motionZ*2D-this.motionZ*1.5D*j, 
                            		-this.motionX*0.1D, -this.motionY*0.1D, -this.motionZ*0.1D, smokeType);
                		}
            		}
        		}
    		}
    		//type 2: railgun particle
    		else
    		{
    			if (this.invisibleTicks <= 0)
    			{
    				//spawn beam head particle
                	ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 10, 4D, (byte)9);
    			}
    		}
    	}//end client side
    }
    
    //calc motionXYZ
    protected void handleMissileMovement()
    {
    	//for cluster sub missile
    	if (this.type == 4)
    	{
    		this.velX *= 0.95D;
    		this.velY += this.accY1;
    		this.velZ *= 0.95D;
    	}
    	//for other missile
    	else
    	{
    		switch (this.moveType)
    		{
    		case 1:   //parabola
    		{
    			if (this.ticksExisted <= this.t0)
    			{
    				this.velY += this.accY1;
    			}
    			else
    			{
    				this.velY += this.accY2;
    			}
    		}
			break;
    		case 2:   //sim-torpedo
    		{
    			//server side: check torpedo is in liquid block then start to move
    			if (!this.world.isRemote)
    			{
    				if (this.startMove)
    				{
    					//if start move, check start move delay
        				if (this.startMoveDelay >= 0)
        				{
        					this.startMoveDelay--;
        					
        					//sync velXYZ to client
        					if (this.startMoveDelay < 0)
        					{
        						Dist4d dist = CalcHelper.getDistanceFromA2B(this.getPositionVector(), new Vec3d(data[3], data[4], data[5]));
            			    	
            					this.velX = dist.x * this.vel0 * 0.25D;
            		        	this.velY = dist.y * this.vel0 * 0.25D;
            		        	this.velZ = dist.z * this.vel0 * 0.25D;
            		        	
            		        	if (this.velY > 0.003D) this.velY = 0.003D;
            					
            					float[] data = new float[] {2F, (float)this.type, (float)this.moveType, (float)this.velX, (float)this.velY, (float)this.velZ, 1F, (float)this.vel0, (float)this.accY1, (float)this.accY2};
            	    			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
            	    	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_CustomData, data), point);
        					}
        				}
        				else
        				{
        					if ((this.velX*this.velX+this.velY*this.velY+this.velZ*this.velZ) < 2D)
        					{
        						this.velX *= this.accY2;
                				this.velY *= this.accY2;
                				this.velZ *= this.accY2;
        					}
        				}
    				}
    				else
    				{
    					this.velX *= 0.85D;
        				this.velY += this.accY1;
        				this.velZ *= 0.85D;
        				
    					//if in liquid, set start move delay = X ticks
        				BlockPos pos = new BlockPos(this);
        				IBlockState state = this.world.getBlockState(pos);
        				if (BlockHelper.checkBlockIsLiquid(state))
        				{
        					this.startMove = true;
        					this.startMoveDelay = 3;
        				}
    				}
    			}//end server side
    			//client side
    			else
    			{
    				if (this.startMove)
    				{
    					if ((this.velX*this.velX+this.velY*this.velY+this.velZ*this.velZ) < 2D)
    					{
    						this.velX *= this.accY2;
            				this.velY *= this.accY2;
            				this.velZ *= this.accY2;
    					}
    				}
    				else
    				{
    					this.velX *= 0.85D;
        				this.velY += this.accY1;
        				this.velZ *= 0.85D;
    				}
    			}//end clientside
    		}
			break;
    		}//end switch move type
    	}//end other missile
    }

	//撞擊判定時呼叫此方法
    protected void onImpact(Entity target)
    {
    	//play sound
    	this.playSound(ModSounds.SHIP_EXPLODE, ConfigHandler.volumeFire * 1.5F, 0.7F / (this.rand.nextFloat() * 0.4F + 0.8F));
    	
    	//null check
    	if (this.host == null)
    	{
    		this.setDead();
    		return;
    	}
    	
    	//server side
    	if (!this.world.isRemote)
    	{
    		float missileAtk = this.attrs.getAttackDamage();
    		
    		//special missile action
    		CombatHelper.specialAttackEffect(this.host, this.type, new float[] {(float)this.posX, (float)this.posY, (float)this.posZ});
    		
            //計算範圍爆炸傷害: 判定bounding box內是否有可以吃傷害的entity
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class,
            						this.getEntityBoundingBox().expand(3.5D, 3.5D, 3.5D));
            
            //對list中所有可攻擊entity做出傷害判定
            for (Entity ent : hitList)
            {
            	missileAtk = this.attrs.getAttackDamage();
            	
            	//目標不能是自己 or 主人, 且可以被碰撞
            	if (ent.canBeCollidedWith() && EntityHelper.isNotHost(this, ent) &&
            		!TargetHelper.isEntityInvulnerable(ent))
            	{
            		//calc equip special dmg: AA, ASM
                	missileAtk = CombatHelper.modDamageByAdditionAttrs(this, ent, missileAtk, 0);
                	
            		//若owner相同, 則傷害設為0
            		if (TeamHelper.checkSameOwner(host2, ent))
            		{
                		missileAtk = 0F;
                		continue;
                	}
            		else
            		{
            		    //roll miss, cri, dhit, thit
            			missileAtk = CombatHelper.applyCombatRateToDamage(this.host, ent, false, 1F, missileAtk);
            	  		
            	  		//damage limit on player target
            			missileAtk = CombatHelper.applyDamageReduceOnPlayer(ent, missileAtk);
            	  		
            	  		//check friendly fire
            			if (!TeamHelper.doFriendlyFire(this.host, ent)) missileAtk = 0F;
            		}
            		
            		//attack
            		if (ent.attackEntityFrom(DamageSource.causeMobDamage(host2).setExplosion(), missileAtk))
            		{
            			if (!TeamHelper.checkSameOwner(this.getHostEntity(), ent)) BuffHelper.applyBuffOnTarget(ent, this.EffectMap);
            		}
            	}//end can be collided with
            }//end hit target list for loop
            
            //send packet to client for display partical effect
            TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 2, false), point);
        
    		//set dead
        	this.setDead();
    	}//end if server side
    }
    
	//儲存entity的nbt
    @Override
	public void writeEntityToNBT(NBTTagCompound nbt) {}

    //讀取entity的nbt
    @Override
	public void readEntityFromNBT(NBTTagCompound nbt) {}

    //設定true可使其他生物判定是否可碰撞此entity
    @Override
	public boolean canBeCollidedWith()
    {
        return true;
    }

    //取得此entity的bounding box大小
    @Override
	public float getCollisionBorderSize()
    {
        return 1F;
    }

    //entity被攻擊到時呼叫此方法
    @Override
	public boolean attackEntityFrom(DamageSource source, float atk)
    {
		if (this.world.isRemote) return false;
		
		//null check
		if (this.host == null)
		{
			this.setDead();
			return false;
		}
		
		//damage disabled
		if (source == DamageSource.inWall || source == DamageSource.starve ||
			source == DamageSource.cactus || source == DamageSource.fall  ||
			source == DamageSource.lava || source == DamageSource.inFire ||
			source == DamageSource.hotFloor || source == DamageSource.anvil ||
			source == DamageSource.fallingBlock || source == DamageSource.onFire)
		{
			return false;
		}
		//damage ignore def value
		else if (source == DamageSource.magic || source == DamageSource.dragonBreath ||
				 source == DamageSource.wither)
		{
        	this.onImpact(null);
			return true;
		}
		//out of world
		else if (source == DamageSource.outOfWorld)
		{
        	this.onImpact(null);
        	return true;
		}
		
    	//進行dodge計算
		if (CombatHelper.canDodge(this, 0F))
		{
			return false;
		}
    	
		//無敵時回傳false
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        
        //攻擊到飛彈會導致立刻爆炸
        if (this.isEntityAlive() && atk > 8F)
        {
        	this.onImpact(null);
        	return true;
        }
        
        return super.attackEntityFrom(source, atk);
    }

    //render用, entity亮度
    @Override
	public float getBrightness(float parTicks)
    {
        return 1F;
    }

    //render用, lightmap位置
    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float parTicks)
    {
        return 15728880;
    }

	@Override
	public int getPlayerUID()
	{
		return this.playerUID;
	}

	@Override
	public void setPlayerUID(int uid)
	{
		this.playerUID = uid;
	}

	@Override
	public Entity getHostEntity()
	{
		return this.host2;
	}

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.AbyssalMissile;
	}

	@Override
	public Attrs getAttrs()
	{
		return this.attrs;
	}

	@Override
	public void setAttrs(Attrs data)
	{
		this.attrs = data;
	}

	@Override
	public int getProjectileType()
	{
		return this.type;
	}

	@Override
	public void setProjectileType(int type)
	{
		this.type = type;
	}
	
	@Override
	public boolean isInvisible()
    {
		if (this.data != null && this.data.length > 14)
		{
			return this.data[14] > 0;
		}
		
        return this.getFlag(5);
    }
	
	
}