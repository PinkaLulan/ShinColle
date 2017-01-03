package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityAirplaneTakoyaki extends BasicEntityAirplane
{
	
	public EntityAirplaneTakoyaki(World world)
	{
		super(world);
		this.setSize(0.6F, 0.6F);
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
            this.atk = ship.getAttackBaseDamage(4, target);
            this.atkSpeed = ship.getStateFinal(ID.SPD);
            this.atkRange = 6F;
            this.defValue = ship.getStateFinal(ID.DEF) * 0.5F;
            this.movSpeed = ship.getStateFinal(ID.MOV) * 0.1F + 0.23F;
            
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
            
            double mhp = ship.getLevel() + ship.getStateFinal(ID.HP)*0.15D;
            
    	    getEntityAttribute(MAX_HP).setBaseValue(mhp);
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
    		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64D);
    		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
            
            //AI flag
            this.numAmmoLight = 0;
            this.numAmmoHeavy = 3;
            this.backHome = false;
            this.canFindTarget = true;
    				
    		//設定AI
    		this.shipNavigator = new ShipPathNavigate(this);
    		this.shipMoveHelper = new ShipMoveHelper(this, 30F);
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
		
		if (this.world.isRemote)
		{
			if (this.ticksExisted % 2 == 0)
			{
				applyFlyParticle();
			}
		}
		else
		{
			if (!this.hasAmmoHeavy())
			{
				this.backHome = true;
				this.canFindTarget = false;
				this.setEntityTarget(null);
			}
		}
	}
	
	@Override
	public boolean useAmmoLight()
	{
		return false;
	}

	@Override
	public boolean useAmmoHeavy()
	{
		return true;
	}
	
	protected void applyFlyParticle()
	{
		ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+0.1D, this.posZ, 
	      		-this.motionX*0.5D, 0.07D, -this.motionZ*0.5D, (byte)18);
	}
	
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.AirplaneTako;
	}

	
}
