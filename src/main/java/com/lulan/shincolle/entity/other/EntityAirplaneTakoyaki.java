package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Attrs;
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
            
            //calc attrs
            this.shipAttrs = Attrs.copyAttrs(ship.getAttrs());
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.HP, ship.getLevel() + ship.getAttrs().getAttrsBuffed(ID.Attrs.HP) * 0.15F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_L, ship.getAttackBaseDamage(3, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_H, ship.getAttackBaseDamage(4, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AL, ship.getAttackBaseDamage(3, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AH, ship.getAttackBaseDamage(4, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.DEF, ship.getAttrs().getDefense() * 0.5F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.SPD, ship.getAttrs().getAttackSpeed() * 2.5F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.MOV, ship.getAttrs().getMoveSpeed() * 0.1F + 0.23F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.HIT, 16F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.DODGE, this.shipAttrs.getAttrsBuffed(ID.Attrs.DODGE) + 0.2F);
    		
    		//apply attrs to entity
    	    getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.HP));
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.MOV));
    		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64D);
    		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
    		
            //AI flag
            this.numAmmoLight = 0;
            this.numAmmoHeavy = 3;
    				
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