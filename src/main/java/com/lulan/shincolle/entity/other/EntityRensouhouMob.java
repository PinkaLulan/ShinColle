package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipCannonAttack;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityRensouhouMob extends EntityMob implements IShipCannonAttack, IShipCustomTexture
{
	
	protected IShipAttackBase host;  		//host target
	protected EntityLivingBase host2;
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
	protected Entity atkTarget;
	protected Entity rvgTarget;					//revenge target
	protected int revengeTime;					//revenge target time
	
    //attributes
	protected float atk;				//damage
	protected float atkSpeed;			//attack speed
	protected float atkRange;			//attack range
	protected float defValue;			//def value
	protected float movSpeed;			//def value
    protected float kbValue;			//knockback value
    
    //AI flag
    protected int numAmmoLight;
    protected int numAmmoHeavy;
    
    //model display
    /**EntityState: 0:HP State 1:Emotion 2:Emotion2*/
	protected byte stateEmotion, stateEmotion2;								//表情type
	protected int startEmotion, startEmotion2, attackTime, attackTime2;		//表情timer
	protected boolean headTilt;

	
    public EntityRensouhouMob(World world)
    {
		super(world);
		this.setSize(0.9F, 1.7F);
		this.isImmuneToFire = true;
		this.shipNavigator = new ShipPathNavigate(this, world);
        this.shipMoveHelper = new ShipMoveHelper(this, 40F);
        
        //AI flag
        this.numAmmoLight = 6;
        this.numAmmoHeavy = 0;
        this.stateEmotion = 0;
        this.stateEmotion2 = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
	}
    
    public void initAttrs(IShipAttackBase host, Entity target)
    {
        this.host = host;
        this.host2 = (EntityLivingBase) host;
        this.atkTarget = target;
        
        //basic attr
        this.atk = 30F;
        this.atkSpeed = 0.8F;
        this.atkRange = this.host.getAttackRange();
        this.defValue = this.host.getDefValue() * 0.5F;
        this.movSpeed = 0.6F;
           
        //設定發射位置
        this.posX = this.host2.posX + rand.nextDouble() * 6D - 3D;
        this.posY = this.host2.posY + 0.5D;
        this.posZ = this.host2.posZ + rand.nextDouble() * 6D - 3D;
        
        //check the place is safe to summon
    	if (!BlockHelper.checkBlockSafe(this.world, (int)posX, (int)posY, (int)posZ))
    	{
    		this.posX = host2.posX;
            this.posY = host2.posY;
            this.posZ = host2.posZ;
    	}
        
        this.setPosition(this.posX, this.posY, this.posZ);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.host2.getMaxHealth() * 0.125D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
    
    @Override
	public float getEyeHeight()
    {
		return this.height;
	}
	
	//setup AI
	protected void setAIList()
	{
		this.clearAITasks();
		this.clearAITargetTasks();
		
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
		this.setEntityTarget(atkTarget);
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk)
	{
		//disable 
		if (attacker.getDamageType() == "inWall")
		{
			return false;
		}
		
		//calc dodge
		if (EntityHelper.canDodge(this, 0F))
		{
			return false;
		}
				
		//set hurt face
    	if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O)
    	{
    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	}
        
        //無敵的entity傷害無效
  		if (this.isEntityInvulnerable(attacker))
  		{	
        	return false;
        }
  		
  		if (attacker.getSourceOfDamage() != null)
  		{
  			Entity entity = attacker.getSourceOfDamage();
  			
  			//不會對自己造成傷害
  			if (entity.equals(this))
  			{  
  				return false;
  			}
  			
  			//若掉到世界外, 則直接使該entity消失
  	        if (attacker.getDamageType().equals("outOfWorld"))
  	        {
  	        	this.setDead();
  	        	return false;
  	        }
  	        
  	        //def calc
  			float reduceAtk = atk;
  			
  			reduceAtk = atk * (1F - this.getDefValue() * 0.01F);
  			
  			//ship vs ship, damage type傷害調整
  			if (entity instanceof IShipAttackBase)
  			{
  				//get attack time for damage modifier setting (day, night or ...etc)
  				int modSet = this.world.provider.isDaytime() ? 0 : 1;
  				reduceAtk = CalcHelper.calcDamageByType(reduceAtk, ((IShipAttackBase) entity).getDamageType(), this.getDamageType(), modSet);
  			}
  			
  	        if (reduceAtk < 1) reduceAtk = 1;
  	        
  	        return super.attackEntityFrom(attacker, reduceAtk);
  		}
    	
    	return false;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//both side
		if (this.attackTime > 0) this.attackTime--;
		
		//client side
		if (this.world.isRemote)
		{
			//有移動時, 產生水花特效
			//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
			double motX = this.posX - this.prevPosX;
			double motZ = this.posZ - this.prevPosZ;
			double parH = this.posY - (int)this.posY;
			
			if (motX != 0 || motZ != 0)
			{
				ParticleHelper.spawnAttackParticleAt(this.posX + motX*1.5D, this.posY, this.posZ + motZ*1.5D, 
						-motX*0.5D, 0D, -motZ*0.5D, (byte)15);
			}
		}
		//server side
		else
		{
			boolean setdead = false;
			
			//owner消失(通常是server restart)
			if (this.host == null || !((Entity)host).isEntityAlive())
			{
				setdead = true;
			}
			else
			{
				//超過60秒自動消失
				if(this.ticksExisted > 1200)
				{
					setdead = true;
				}

				//target is dead
				if (this.getEntityTarget() == null || this.getEntityTarget().isDead)
				{
					//change target
					if (this.host != null && this.host.getEntityTarget() != null &&
						this.host.getEntityTarget().isEntityAlive())
					{
						this.atkTarget = this.host.getEntityTarget();
					}
					else
					{
						setdead = true;
					}	
				}
				
				//防止溺死
				if (this.isInWater() && this.ticksExisted % 100 == 0)
				{
					this.setAir(300);
				}
			}
			
			//is done
			if (setdead)
			{
				this.setDead();
			}
		}
	}

	@Override
	public byte getStateEmotion(int id)
	{
		return id == 1 ? stateEmotion : stateEmotion2;
	}
	
	@Override
	public void setStateEmotion(int id, int value, boolean sync)
	{
		switch (id)
		{
		case 1:
			stateEmotion = (byte) value;
			break;
		case 2:
			stateEmotion2 = (byte) value;
			break;
		default:
			break;
		}
		
		if (sync && !world.isRemote)
		{
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_Emo), point);
		}
	}

	@Override
	public boolean getStateFlag(int flag)
	{	//hostile mob: for attack and headTile check
		switch (flag)
		{
		default:
			return true;
		case ID.F.HeadTilt:
			return this.headTilt;
		case ID.F.OnSightChase:
			return false;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag)
	{
		this.headTilt = flag;
	}

	@Override
	public int getFaceTick()
	{
		return this.startEmotion;
	}

	@Override
	public int getHeadTiltTick()
	{
		return this.startEmotion2;
	}

	@Override
	public void setFaceTick(int par1)
	{
		this.startEmotion = par1;
	}

	@Override
	public void setHeadTiltTick(int par1)
	{
		this.startEmotion2 = par1;
	}

	@Override
	public int getTickExisted()
	{
		return this.ticksExisted;
	}

    //clear AI
  	protected void clearAITasks()
  	{
  	   tasks.taskEntries.clear();
  	}
  	
  	//clear target AI
  	protected void clearAITargetTasks()
  	{
  	   targetTasks.taskEntries.clear();
  	}
    
    @Override
	//light attack
	public boolean attackEntityWithAmmo(Entity target)
    {
    	//null check
    	if (this.host == null)
    	{
    		this.setDead();
    		return false;
    	}
    	
		float atkLight = CalcHelper.calcDamageBySpecialEffect(this, target, this.atk, 0);

		//play cannon fire sound at attacker
		this.playSound(ModSounds.SHIP_FIRELIGHT, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
        
        //此方法比getLook還正確 (client sync問題)
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
		
		//發射者煙霧特效
        TargetPoint point0 = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY+1.5D, this.posZ, distX, distY, distZ, true), point0);
		
		//calc miss chance, if not miss, calc cri/multi hit
		TargetPoint point = new TargetPoint(this.dimension, this.host2.posX, this.host2.posY, this.host2.posZ, 64D);
        float missChance = 0.25F;
  		
        //calc miss chance
        if (this.rand.nextFloat() < missChance)
        {
        	atkLight = 0;	//still attack, but no damage
        	//spawn miss particle
        	
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 10, false), point);
        }
        else
        {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if (this.rand.nextFloat() < 0.1F)
        	{
        		atkLight *= 1.5F;
        		//spawn critical particle
            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 11, false), point);
        	}
        	else
        	{
        		//calc double hit
            	if (this.rand.nextFloat() < 0.1F)
            	{
            		atkLight *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 12, false), point);
            	}
            	else
            	{
            		//calc double hit
                	if (this.rand.nextFloat() < 0.1F)
                	{
                		atkLight *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 13, false), point);
                	}
            	}
        	}
        }
        
        //vs player = 25% dmg
  		if (target instanceof EntityPlayer)
  		{
  			atkLight *= 0.25F;
  			
  			//check friendly fire
    		if (!ConfigHandler.friendlyFire)
    		{
    			atkLight = 0F;
    		}
    		else if (atkLight > 59F)
    		{
    			atkLight = 59F;	//same with TNT
    		}
  		}

	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atkLight);

	    //if attack success
	    if (isTargetHurt)
	    {
	        //display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }
	    
	    //消耗彈藥計算
  		if (numAmmoLight > 0)
  		{
  			numAmmoLight--;
  			
  			if (numAmmoLight <= 0)
  			{
  				this.setDead();
  			}
  		}

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		//get attack value
		float atkHeavy = this.atk;
		float kbValue = 0.08F;

		//play cannon fire sound at attacker
		this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);

		//飛彈是否採用直射
  		boolean isDirect = false;
  		//計算目標距離
  		float tarX = (float)target.posX;	//for miss chance calc
  		float tarY = (float)target.posY;
  		float tarZ = (float)target.posZ;
  		float distX = tarX - (float)this.posX;
  		float distY = tarY - (float)this.posY;
  		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
        float launchPos = (float)posY + height * 0.7F;
          
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if ((distX*distX+distY*distY+distZ*distZ) < 36F)
        {
        	isDirect = true;
        }
        
        if (this.isInWater())
        {
          	isDirect = true;
          	launchPos = (float)posY;
        }
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        float missChance = 0.25F;	//max miss chance = 30%
		
        //calc miss chance
        if (this.rand.nextFloat() < missChance)
        {
        	atkHeavy = 0;	//still attack, but no damage
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, this.host2.posX, this.host2.posY, this.host2.posZ, 64D);
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 10, false), point);
        }

        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atkHeavy, kbValue, isDirect, -1F);
        this.world.spawnEntity(missile);
        
        //消耗彈藥計算
  		if (numAmmoHeavy > 0)
  		{
  			numAmmoHeavy--;
  			
  			if (numAmmoHeavy <= 0)
  			{
  				this.setDead();
  			}
  		}
  		
        return true;
	}
    
    //水中跟岩漿中不會下沉
    @Override
    public void moveEntityWithHeading(float strafe, float forward)
    {
    	EntityHelper.moveEntityWithHeading(this, strafe, forward);
    }

	@Override
	public float getAttackSpeed()
	{
		return this.atkSpeed;
	}

	@Override
	public float getAttackRange()
	{
		return this.atkRange;
	}
	
	@Override
	public float getMoveSpeed()
	{
		return this.movSpeed;
	}
	
	@Override
	public float getJumpSpeed()
	{
		return 1.5F;
	}
	
	@Override
	public int getAmmoLight()
	{
		return this.numAmmoLight;
	}

	@Override
	public int getAmmoHeavy()
	{
		return this.numAmmoHeavy;
	}

	@Override
	public boolean hasAmmoLight()
	{
		return this.numAmmoLight > 0;
	}

	@Override
	public boolean hasAmmoHeavy()
	{
		return false;
	}

	@Override
	public void setAmmoLight(int num)
	{
		this.numAmmoLight = num;
	}

	@Override
	public void setAmmoHeavy(int num)
	{
		this.numAmmoHeavy = num;
	}

	@Override
	public float getAttackDamage()
	{
		return this.atk;
	}

	@Override
	public Entity getEntityTarget()
	{
		return this.atkTarget;
	}
  	
  	@Override
	public void setEntityTarget(Entity target)
  	{
		this.atkTarget = target;
	}
	
	@Override
	public boolean getIsRiding()
	{
		return false;
	}

	@Override
	public boolean getIsSprinting()
	{
		return false;
	}

	@Override
	public boolean getIsSitting()
	{
		return false;
	}

	@Override
	public boolean getIsSneaking()
	{
		return false;
	}
	
	@Override
	public ShipPathNavigate getShipNavigate()
	{
		return this.shipNavigator;
	}

	@Override
	public ShipMoveHelper getShipMoveHelper()
	{
		return this.shipMoveHelper;
	}
	
	@Override
    public void onLivingUpdate()
    {
    	if ((!world.isRemote))
    	{
        	//update movement, NOTE: 1.9.4: must done before vanilla MoveHelper updating in super.onLivingUpdate()
        	EntityHelper.updateShipNavigator(this);
            super.onLivingUpdate();
    	}
    	else
    	{
    		super.onLivingUpdate();
    	}
    }
	
	@Override
	public boolean canFly()
	{
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}
	
	@Override
	public boolean getIsLeashed()
	{
		return false;
	}
	
	@Override
	public int getLevel()
	{
		return 150;
	}
	
	@Override
	public boolean useAmmoLight()
	{
		return true;
	}

	@Override
	public boolean useAmmoHeavy()
	{
		return false;
	}
	
	@Override
	public int getStateMinor(int id)
	{
		return 0;
	}

	@Override
	public void setStateMinor(int state, int par1) {}
	
	@Override
	public float getEffectEquip(int id)
	{
		switch (id)
		{
		case ID.EF_CRI:
			return 0.15F;
		case ID.EF_ASM:  //destroyer AA,ASM++
		case ID.EF_AA:
			return this.atk * 0.5F;
		case ID.EF_DODGE:
			return 20F;
		default:
			return 0F;
		}
	}

	@Override
	public float getDefValue()
	{
		return defValue;
	}
	
	@Override
	public void setEntitySit(boolean sit) {}

	@Override
	public float getModelRotate(int par1)
	{
		return 0F;
	}

	@Override
	public void setModelRotate(int par1, float par2) {}

	@Override
	public boolean getAttackType(int par1)
	{
		return true;
	}
	
	@Override
	public int getPlayerUID()
	{
		return -100;	//-100 for hostile mob
	}

	@Override
	public void setPlayerUID(int uid) {}
	
	@Override
	public Entity getHostEntity()
	{
		return this.host2;
	}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.DESTROYER;
	}
	
	@Override
	public Entity getEntityRevengeTarget()
	{
		return this.rvgTarget;
	}

	@Override
	public int getEntityRevengeTime()
	{
		return this.revengeTime;
	}

	@Override
	public void setEntityRevengeTarget(Entity target)
	{
		this.rvgTarget = target;
	}
  	
  	@Override
	public void setEntityRevengeTime()
  	{
		this.revengeTime = this.ticksExisted;
	}
  	
	@Override
	public int getAttackTick()
	{
		return this.attackTime;
	}
  	
  	@Override
	public int getAttackTick2()
  	{
		return this.attackTime2;
	}
  	
	@Override
	public void setAttackTick(int par1)
	{
		this.attackTime = par1;
	}

	@Override
	public void setAttackTick2(int par1)
	{
		this.attackTime2 = par1;
	}

	@Override
	public float getSwingTime(float partialTick)
	{
		return 0;
	}

	@Override
	public boolean isJumping()
	{
		return this.isJumping;
	}
	
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.Rensouhou;
	}
	
	//for model display
	@Override
	public int getRidingState()
	{
		return 0;
	}
	
	@Override
	public void setRidingState(int state) {}

	@Override
	public int getScaleLevel()
	{
		if (this.host != null) return this.host.getScaleLevel();
		return 0;
	}
	
	
}