package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.utility.TargetHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityAirplaneZeroMob extends EntityAirplaneZero
{

	public EntityAirplaneZeroMob(World world)
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
        if (host instanceof BasicEntityShipHostile)
        {
        	BasicEntityShipHostile ship = (BasicEntityShipHostile) host;
        	
        	this.targetSelector = new TargetHelper.SelectorForHostile(ship);
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
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.HP, ship.getMaxHealth() * 0.06F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_L, ship.getAttackBaseDamage(3, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_H, ship.getAttackBaseDamage(4, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AL, ship.getAttackBaseDamage(3, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AH, ship.getAttackBaseDamage(4, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.DEF, ship.getAttrs().getDefense() * 0.5F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.SPD, ship.getAttrs().getAttackSpeed() * 3F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.MOV, ship.getAttrs().getMoveSpeed() * 0.2F + 0.2F + this.getScaleLevel() * 0.05F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.HIT, 16F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.DODGE, this.shipAttrs.getAttrsBuffed(ID.Attrs.DODGE) + 0.3F);
    		
    		//apply attrs to entity
    	    getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.HP));
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.MOV));
    		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64D);
    		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1D);
    		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
    		
    		//AI flag
            this.numAmmoLight = 9;
            this.numAmmoHeavy = 0;
    				
    		//設定AI
    		this.shipNavigator = new ShipPathNavigate(this);
    		this.shipMoveHelper = new ShipMoveHelper(this, 36F);
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