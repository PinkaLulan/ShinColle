package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAircraftAttack;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityAirplane extends BasicEntityAirplane
{
	
	public EntityAirplane(World world)
	{
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	@Override
	public void initAttrs(IShipAttackBase host, Entity target, int scaleLevel, float...par2)
	{
        this.host = host;
        this.atkTarget = target;
        this.setScaleLevel(scaleLevel);
        
        if (host instanceof BasicEntityShip)
        {
        	BasicEntityShip ship = (BasicEntityShip) host;
        	
        	this.targetSelector = new TargetHelper.Selector(ship);
    		this.targetSorter = new TargetHelper.Sorter(ship);
    		
            //basic attr
            this.atk = ship.getAttackBaseDamage(3, target);
            this.atkSpeed = ship.getStateFinal(ID.SPD);
            this.atkRange = 6F;
            this.defValue = ship.getStateFinal(ID.DEF) * 0.5F;
            this.movSpeed = ship.getStateFinal(ID.MOV) * 0.2F + 0.3F;
            
            //設定發射位置
            float launchPos = (float) ship.posY;
        	if (par2 != null) launchPos = par2[0];
        	
            this.posX = ship.posX;
            this.posY = launchPos;
            this.posZ = ship.posZ;
            this.prevPosX = this.posX;
        	this.prevPosY = this.posY;
        	this.prevPosZ = this.posZ;
            this.setPosition(this.posX, this.posY, this.posZ);
            
            double mhp = ship.getLevel() + ship.getStateFinal(ID.HP)*0.1D;
            
    	    getEntityAttribute(MAX_HP).setBaseValue(mhp);
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
    		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64D);
    		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
            
            //AI flag
            this.numAmmoLight = 9;
            this.numAmmoHeavy = 0;
            this.backHome = false;
            this.canFindTarget = true;
    				
    		//設定AI
    		this.shipNavigator = new ShipPathNavigate(this);
    		this.shipMoveHelper = new ShipMoveHelper(this, 36F);
    		this.setAIList();
        }
        //not ship
        else
        {
        	return;
        }
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//client side particle
		if (this.world.isRemote)
		{	
			applyFlyParticle();
		}
		//server side
		else
		{
			if (!this.hasAmmoLight())
			{
				this.backHome = true;
				this.canFindTarget = false;
				this.setEntityTarget(null);
			}
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float atk)
	{
		//33% dodge heavy damage
		if (atk > this.getMaxHealth() * 0.5F && this.getRNG().nextInt(3) == 0)
		{
			//spawn miss particle
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 34, false), point);
			return false;
		}
        
        return super.attackEntityFrom(source, atk);
    }
	
	protected void applyFlyParticle()
	{
		ParticleHelper.spawnAttackParticleAt(this.posX-this.motionX*1.5D, this.posY+0.5D-this.motionY*1.5D, this.posZ-this.motionZ*1.5D, 
          		-this.motionX*0.5D, -this.motionY*0.5D, -this.motionZ*0.5D, (byte)17);
	}
	
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.Airplane;
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
	
	
}
