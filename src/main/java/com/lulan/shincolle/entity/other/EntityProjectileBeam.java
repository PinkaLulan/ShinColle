package com.lulan.shincolle.entity.other;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.client.render.ICustomTexture;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipAttrs;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.IShipProjectile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Attrs;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/** Beam entity
 *  fly to target, create beam particle between host and target
 *  damage everything on the way
 *  setDead after X ticks
 *  
 *  未使用移動向量碰撞法, 因此速度必須設定為
 *	(width一半 + onImpact範圍) * 2, 以盡量碰撞到路徑上所有物體
 *
 *	實際最大射程約為 lifeLength * acc (格)
 */
public class EntityProjectileBeam extends Entity implements IShipOwner, IShipAttrs, ICustomTexture, IShipProjectile
{
	
	//host data
	private IShipAttackBase host;	//main host type
    private Entity host2;			//second host type: entity living
    private int playerUID;			//owner UID, for owner check
    
    //beam data
	private int type, lifeLength;
	private float atk, kbValue;
	private float acc, accX, accY, accZ;
	private ArrayList<Entity> damagedTarget;
	

	public EntityProjectileBeam(World world)
	{
		super(world);
		this.setSize(1F, 1F);
		this.ignoreFrustumCheck = true;  //always render
		this.noClip = true;				 //can't block
		this.stepHeight = 0F;
		this.damagedTarget = new ArrayList<Entity>();
	}
	
	//init attrs
	public void initAttrs(IShipAttackBase host, int type, float ax, float ay, float az, float atk, float kb)
	{
		//host
		this.host = host;
		this.host2 = (Entity) host;
		this.playerUID = host.getPlayerUID();
		
		//type
		this.type = type;
		
		switch (type)
		{
		case 1:		//gae bolg
			this.setPosition(host2.posX, host2.posY + host2.height * 0.75D, host2.posZ);
			this.lifeLength = 8;
			this.acc = 3F;
		break;
		default:	//normal beam
			this.setPosition(host2.posX + ax, host2.posY + host2.height * 0.5D, host2.posZ + az);
			this.lifeLength = 31;
			this.acc = 4F;
		break;
		}
		
		this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
		
		//beam data
		this.accX = ax * acc;
		this.accY = ay * acc;
		this.accZ = az * acc;
		this.atk = atk;
		this.kbValue = kb;
	}

	@Override
	public int getPlayerUID()
	{
		return this.playerUID;
	}

	@Override
	public void setPlayerUID(int uid) {}

	@Override
	public Entity getHostEntity()
	{
		return host2;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {}
	
	@Override
	public boolean isEntityInvulnerable(DamageSource attacker)
	{
        return true;
    }
	
	@Override
    public boolean canBeCollidedWith()
	{
        return false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
	public void onUpdate()
    {
    	/**************** BOTH SIDE ******************/
    	//set speed
    	this.motionX = this.accX;
    	this.motionY = this.accY;
    	this.motionZ = this.accZ;
    	
    	//set position
		this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
		this.posX += this.motionX;
		this.posY += this.motionY;
        this.posZ += this.motionZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        
        super.onUpdate();
        
        /*************** SERVER SIDE *****************/
        if (!this.world.isRemote)
        {
        	//check life
        	if (this.ticksExisted > this.lifeLength || this.host == null)
        	{
        		this.setDead();
        		return;
        	}
        	
        	//sync beam type at start
    		if (this.ticksExisted == 1)
    		{
    			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
    			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, this.type, S2CEntitySync.PID.SyncProjectile), point);
    		}
        	
    		//判定bounding box內是否有可以觸發爆炸的entity
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class,
            								this.getEntityBoundingBox().expand(1.5D, 1.5D, 1.5D));
            
            //搜尋list, 找出可碰撞目標執行onImpact
            for (Entity ent : hitList)
            { 
            	if (ent.canBeCollidedWith() && EntityHelper.isNotHost(this, ent) &&
            		!TargetHelper.isEntityInvulnerable(ent))
            	{
        			boolean attacked = false;
        			
        			//check target was not attacked before
        			for (Entity ent2 : this.damagedTarget)
        			{
        				if (ent.equals(ent2))
        				{
        					attacked = true;
        					break;
        				}
        			}
        			
        			if (attacked)
    				{	//attacked, skip to next
        				continue;
    				}
        			else
        			{	//not attacked, add to attacked list
        				this.damagedTarget.add(ent);
        			}
            		
            		this.onImpact(ent);
            	}
            }
        }
        /*************** CLIENT SIDE *****************/
        else
        {
    		//spawn beam head particle
        	ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 32 - this.ticksExisted, 0D, (byte)4);
        }
    }
    
    @Override
	public boolean attackEntityFrom(DamageSource attacker, float atk)
    {
    	return false;
    }
    
	//撞擊判定時呼叫此方法
    protected void onImpact(Entity target)
    {
    	if (this.host == null) return;
    	
    	//play sound
    	this.playSound(ModSounds.SHIP_EXPLODE, ConfigHandler.volumeFire * 1.5F, 0.7F / (this.rand.nextFloat() * 0.4F + 0.8F));
    	
    	//set attack value
    	float beamAtk = this.atk;

	    TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
    
		//calc equip special dmg: AA, ASM
    	beamAtk = CombatHelper.modDamageByAdditionAttrs(this.host, target, beamAtk, 1);
    	
		//若owner相同, 則傷害設為0 (但是依然觸發擊飛特效)
		if (TeamHelper.checkSameOwner(host2, target))
		{
			beamAtk = 0F;
    	}
		else
		{
			//roll miss, cri, dhit, thit
			beamAtk = CombatHelper.applyCombatRateToDamage(this.host, target, false, 1F, beamAtk);
	  		
	  		//damage limit on player target
			beamAtk = CombatHelper.applyDamageReduceOnPlayer(target, beamAtk);
	  		
	  		//check friendly fire
			if (!TeamHelper.doFriendlyFire(this.host, target)) beamAtk = 0F;
		}
		
		//if attack success
	    if (target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, host2).setExplosion(), beamAtk))
	    {
	    	if (!TeamHelper.checkSameOwner(this.getHostEntity(), target)) BuffHelper.applyBuffOnTarget(target, this.host.getAttackEffectMap());
	        //send packet to client for display partical effect
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point);
	    }
    }

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.Invisible;
	}

	@Override
	public Attrs getAttrs()
	{
		return this.host.getAttrs();
	}

	@Override
	public void setAttrs(Attrs data) {}
	
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
	
	
}