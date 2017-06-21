package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.utility.TargetHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityAirplaneTMob extends EntityAirplaneT
{

	public EntityAirplaneTMob(World world)
	{
		super(world);
	}
	
	@Override
	public void initAttrs(IShipAttackBase host, Entity target, int scaleLevel, float...par2)
	{
        this.host = host;
        this.atkTarget = target;
        this.setScaleLevel(scaleLevel);
        
        //host is mob ship
        if(host instanceof BasicEntityShipHostile)
        {
        	BasicEntityShipHostile ship = (BasicEntityShipHostile) host;
        	
        	this.targetSelector = new TargetHelper.SelectorForHostile(ship);
    		this.targetSorter = new TargetHelper.Sorter(ship);
    		
            //basic attr
    		this.atk = ship.getAttackDamage() * 3F;
            this.atkSpeed = ship.getAttackSpeed() * 2.5F;
            this.atkRange = 6F;
            this.defValue = ship.getDefValue() * 0.5F;
            this.movSpeed = ship.getMoveSpeed() * 0.1F + 0.23F + this.getScaleLevel() * 0.05F;
            
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
            
            //設定基本屬性
            double mhp = ship.getMaxHealth() * 0.09F;
            
    	    getEntityAttribute(MAX_HP).setBaseValue(mhp);
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
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
        else
        {
        	return;
        }
	}
	
	@Override
	public int getPlayerUID()
	{
		return -100;	//-100 for hostile mob
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(2F, 2F);
		break;
		case 2:
			this.setSize(1.5F, 1.5F);
		break;
		case 1:
			this.setSize(1F, 1F);
		break;
		default:
			this.setSize(0.5F, 0.5F);
		break;
		}
	}

	@Override
	protected void setAttrsWithScaleLevel()
	{
	}
	
	
}
