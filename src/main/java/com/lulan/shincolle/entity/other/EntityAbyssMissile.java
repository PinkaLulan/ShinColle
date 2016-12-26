package com.lulan.shincolle.entity.other;

import java.util.List;

import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipAttributes;
import com.lulan.shincolle.entity.IShipFlyable;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
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

/**ENTITY ABYSS MISSILE
 * @parm world, host entity, tarX, tarY, tarZ, damage, knockback value
 * 
 * Parabola Orbit(for distance 7~65)
 * 形狀設定:
 * 在經過距離中點之前, 加上額外motionY向上以及accY向下
 * 到中點時, Vy = 0
 * 
 * speical type:
 * type 1:  high speed torpedo
 *   customAcc: 0.05~0.09
 * type 2:  railgun
 *   customAcc: >0.09
 * type 3:  cluster main
 *   customAcc: 0.02
 * type 4:  cluster sub
 *   customAcc: 0.02
 *   
 * 
 */
public class EntityAbyssMissile extends Entity implements IShipOwner, IShipAttributes, IShipFlyable, IShipCustomTexture
{
	
    private IShipAttackBase host;	//main host type
    private EntityLiving host2;		//second host type: entity living
    private int playerUID;			//owner UID, for owner check
    
    //missile motion
    private boolean isDirect;		//false:parabola  true:direct
  
    //for parabola y position
    private float accParaY;			//額外y軸加速度
    private int midFlyTime;			//一半的飛行時間
   
    //for direct only
    private static final float ACC = 0.01F;
    private float acce;	//預設加速度
    private float accX;				//三軸加速度
    private float accY;
    private float accZ;
    
    //missile attributes
    public int type;				//missile type
    private float atk;				//missile damage
    private float kbValue;			//knockback value
    private float missileHP;		//if hp = 0 -> onImpact
    
    
    //基本constructor, size必須在此設定
    public EntityAbyssMissile(World world)
    {
    	super(world);
    	this.setSize(1F, 1F);
    }
    
    /** for cluster sub missile */
    public EntityAbyssMissile(World world, IShipAttackBase host, float mX, float mY, float mZ, float pX, float pY, float pZ, float atk, float kbValue)
    {
        this(world);
        
        //設定host跟owner
        this.host = host;
        this.host2 = (EntityLiving) host;
        this.setPlayerUID(host.getPlayerUID());
        
        //set basic attributes
        this.atk = atk;
        this.kbValue  = kbValue;
        this.posX = pX;
        this.posY = pY;
        this.posZ = pZ;
        this.isDirect = false;
        this.type = 4;
        this.acce = ACC;
        this.accParaY = this.acce * 0.5F;
        
        if (mY > 0) mY = 0F;
        
        //acc and motion
        this.accX = mX * 0.1F;
	    this.accY = -this.acce;
	    this.accZ = mZ * 0.1F;
	    this.motionX = mX;
	    this.motionY = mY;
	    this.motionZ = mZ;
    }
    
    public EntityAbyssMissile(World world, IShipAttackBase host, float tarX, float tarY, float tarZ, float launchPos, float atk, float kbValue, boolean isDirect, float customAcc)
    {
    	this(world);
        
        //設定host跟owner
        this.host = host;
        this.host2 = (EntityLiving) host;
        this.setPlayerUID(host.getPlayerUID());
        
        //set basic attributes
        this.atk = atk;
        this.kbValue  = kbValue;
        this.posX = this.host2.posX;
        this.posZ = this.host2.posZ;
        this.posY = launchPos;
             
        //計算距離, 取得方向vector, 並且初始化速度, 使飛彈方向朝向目標
        float distX = (float) (tarX - this.posX);
        float distY = (float) (tarY - this.posY);
        float distZ = (float) (tarZ - this.posZ);
        
        if (MathHelper.abs(distX) < 0.001F) distX = 0F;
        if (MathHelper.abs(distY) < 0.001F) distY = 0F;
        if (MathHelper.abs(distZ) < 0.001F) distZ = 0F;
        
        //設定直射或者拋物線
        this.isDirect = isDirect;
        this.type = 0;
        
        //設定飛彈速度
        if (customAcc > 0F)
        {
        	this.acce = customAcc;
        	
        	if (customAcc > 0.09F)
        	{
        		this.type = 2;
        	}
        	else if (customAcc > 0.05F)
        	{	//ro500, u511
        		this.type = 1;
        	}
        }
        else
        {
        	this.acce = ACC;
        }
        
        //check special type
        if (customAcc == -2F)
        {
        	this.type = 3;	//cluster main
        	LogHelper.info("DEBUG : const type 3 missile ");
        }
        
        //直射彈道, no gravity
    	float dist = MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
  	    this.accX = distX / dist * this.acce;
	    this.accY = distY / dist * this.acce;
	    this.accZ = distZ / dist * this.acce;
	    this.motionX = this.accX;
	    this.motionY = this.accY;
	    this.motionZ = this.accZ;
 
	    //拋物線軌道計算, y軸初速加上 (一半飛行時間 * 額外y軸加速度)
	    if (!this.isDirect)
	    {
	    	this.midFlyTime = (int) (0.5F * MathHelper.sqrt(2F * dist / this.acce));
	    	this.accParaY = this.acce;
	    	this.motionY = this.motionY + (double)this.midFlyTime * this.accParaY;
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
    	/**********both side***********/
    	//將位置更新 (包含server, client間同步位置, 才能使bounding box運作正常)
        this.setPosition(this.posX, this.posY, this.posZ);

        //計算發射體的高度
    	if (!this.isDirect)
    	{  //直射軌道計算  	
			this.motionY = this.motionY + this.accY - this.accParaY;                   
    	}
    	else
    	{
    		this.motionY += this.accY;
    	}
    	
    	//cluster sub missile acc
    	if (this.type == 4)
    	{
    		this.motionX *= 0.8F;
    		this.motionZ *= 0.8F;
    	}
    	
    	//計算next tick的速度
        this.motionX += this.accX;
        this.motionZ += this.accZ;
        
    	//設定發射體的下一個位置
		this.posX += this.motionX;
		this.posY += this.motionY;
        this.posZ += this.motionZ;
        
    	//計算模型要轉的角度 (RAD, not DEG)
        float f1 = MathHelper.sqrt(this.motionX*this.motionX + this.motionZ*this.motionZ);
        this.rotationPitch = (float)(Math.atan2(this.motionY, f1));
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ));    
        
        //依照x,z軸正負向修正角度(轉180)
        if (this.motionX > 0)
        {
        	this.rotationYaw -= Math.PI;
        }
        else
        {
        	this.rotationYaw += Math.PI;
        }
        
        //更新位置等等基本資訊, 同時更新prePosXYZ
        super.onUpdate();
        
        /**********server side***********/
    	if (!this.world.isRemote)
    	{
    		//沒有host資料, 消除此飛彈
    		if (this.host == null)
    		{
    			this.setDead();	//直接抹消, 不觸發爆炸
    			return;
    		}
    		
    		//發射超過10 sec, 設定為死亡(消失), 注意server restart後此值會歸零
    		if (this.ticksExisted > 200)
    		{
    			this.setDead();	//直接抹消, 不觸發爆炸
    			return;
    		}
    		//sync missile type at start
    		else if (this.ticksExisted == 1)
    		{
    			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
    			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, this.type, S2CEntitySync.PID.SyncProjectile), point);
    		}
    		
    		//spawn cluster sub missile
    		if (this.type == 3 && this.ticksExisted > 15)
    		{
    			if (this.ticksExisted % 8 == 0)
    			{
    				EntityAbyssMissile subm = new EntityAbyssMissile(this.world, this.host, 
    						(float)this.motionX, (float)this.motionY, (float)this.motionZ, 
    						(float)this.posX, (float)this.posY - 0.75F, (float)this.posZ,
    		        		atk, kbValue);
    		        this.world.spawnEntity(subm);
    			}
    		}
    		
    		//爆炸判定1: 本體位置碰撞: missile本身位置是固體方塊
    		IBlockState state = this.world.getBlockState(new BlockPos(this));
    		if(state.getMaterial().isSolid())
    		{
    			this.onImpact(null);
    		}
    		
    		//爆炸判定2: 移動量投射法: missile每tick的飛行移動量中可碰到的東西, 用於移動量大於擴展AABB的missile
    		Vec3d vec3 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult raytrace = this.world.rayTraceBlocks(vec3, vec31);          
            
            vec3 = new Vec3d(this.posX, this.posY, this.posZ);
            vec31 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            
            if (raytrace != null)
            {
                vec31 = new Vec3d(raytrace.hitVec.xCoord, raytrace.hitVec.yCoord, raytrace.hitVec.zCoord);
                this.onImpact(null);
            }
            
            //爆炸判定3: 擴展AABB碰撞: missile擴展1格大小內是否有entity可觸發爆炸
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().expand(1D, 1D, 1D));
            
            //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
            for (Entity ent : hitList)
            { 
            	/**不會對自己主人觸發爆炸
        		 * isEntityEqual() is NOT working
        		 * use entity id to check entity  */
            	if (ent.canBeCollidedWith() && isNotHost(ent) && !TeamHelper.checkSameOwner(host2, ent))
            	{
            		this.onImpact(ent);
            		return;
            	}
            }
            
    	}//end server side
    	/**********client side***********/
    	else
    	{
    		if (this.type != 2)
    		{
    			//spawn particle by speed type
        		byte smokeType = 15;
        		
        		switch(this.type)
        		{
        		case 1:
        			smokeType = 41;
        			break;
        		case 3:
        		case 4:
        			smokeType = 18;
        			break;
        		default:
        			break;
        		}
        		
        		for (int j = 0; j < 3; ++j)
        		{
                	ParticleHelper.spawnAttackParticleAt(this.posX-this.motionX*1.5D*j, this.posY+0.5D-this.motionY*1.5D*j, this.posZ-this.motionZ*1.5D*j, 
                    		-this.motionX*0.1D, -this.motionY*0.1D, -this.motionZ*0.1D, smokeType);
        		}
    		}
    		else
    		{
    			//spawn beam head particle
            	ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 10, 4D, (byte)9);
    		}
    	}//end client side
    }

    //check entity is not host or launcher
    private boolean isNotHost(Entity entity)
    {
    	//not self
    	if (entity.equals(this)) return false;
    	//not launcher
		if (host2 != null && host2.getEntityId() == entity.getEntityId())
		{
			return false;
		}

		return true;
	}

	//撞擊判定時呼叫此方法
    protected void onImpact(Entity target)
    {
    	//play sound
    	this.playSound(ModSounds.SHIP_EXPLODE, ConfigHandler.volumeFire * 1.5F, 0.7F / (this.rand.nextFloat() * 0.4F + 0.8F));
    	
    	//server side
    	if (!this.world.isRemote)
    	{
    		//set dead
        	this.setDead();
        	
    		float missileAtk = atk;

            //計算範圍爆炸傷害: 判定bounding box內是否有可以吃傷害的entity
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class,
            						this.getEntityBoundingBox().expand(3.5D, 3.5D, 3.5D));
            
            //對list中所有可攻擊entity做出傷害判定
            for (Entity ent : hitList)
            {
            	missileAtk = this.atk;
            	
            	//check target attackable
          		if (!TargetHelper.checkUnattackTargetList(ent))
          		{
          			//calc equip special dmg: AA, ASM
                	missileAtk = CalcHelper.calcDamageBySpecialEffect(this, ent, missileAtk, 0);
                	
                	//目標不能是自己 or 主人, 且可以被碰撞
                	if (ent.canBeCollidedWith() && isNotHost(ent))
                	{
                		//若owner相同, 則傷害設為0 (但是依然觸發擊飛特效)
                		if (TeamHelper.checkSameOwner(host2, ent))
                		{
                    		missileAtk = 0F;
                    	}
                		else
                		{
                			//calc critical, only for type:ship
                    		if (this.host != null && (this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_CRI)))
                    		{
                        		missileAtk *= 3F;
                        		//spawn critical particle
                        		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
                            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(host2, 11, false), point);
                        	}
                    		
                    		//若攻擊到玩家, 最大傷害固定為TNT傷害 (non-owner)
                        	if (ent instanceof EntityPlayer)
                        	{
                        		missileAtk *= 0.25F;
                        		
                        		if (missileAtk > 59F)
                        		{
                        			missileAtk = 59F;	//same with TNT
                        		}
                        	}
                        	
                        	//check friendly fire
                    		if (!TeamHelper.doFriendlyFire(this.host, ent))
                    		{
                    			missileAtk = 0F;
                    		}
                		}
                		
                		//attack
                		ent.attackEntityFrom(DamageSource.causeMobDamage(host2).setExplosion(), missileAtk);
                	}//end can be collided with
          		}//end is attackable
            }//end hit target list for loop
  
            //send packet to client for display partical effect
            TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 2, false), point);
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
	public boolean attackEntityFrom(DamageSource attacker, float atk)
    {
    	//進行dodge計算
		if (EntityHelper.canDodge(this, 0F))
		{
			return false;
		}
    	
		//無敵時回傳false
        if (this.isEntityInvulnerable(attacker))
        {
            return false;
        }
        
        //攻擊到飛彈會導致立刻爆炸
        if (this.isEntityAlive() && atk > 10F)
        {
        	this.setDead();
        	this.onImpact(null);
        	return true;
        }
        
        return false;
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
    
    public void setMissileType(int par1)
    {
    	this.type = par1;
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
	public float getEffectEquip(int id)
	{
		//dodge = 50%
		if (id == ID.EF_DODGE) return 50F;
		
		if (host != null) return host.getEffectEquip(id);
		return 0F;
	}

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.AbyssalMissile;
	}

	
}
