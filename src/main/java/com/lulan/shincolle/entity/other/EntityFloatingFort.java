package com.lulan.shincolle.entity.other;

import java.util.List;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityFloatingFort extends BasicEntityAirplane
{
	
	public EntityFloatingFort(World world)
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
    		
            //AI flag
            this.numAmmoLight = 0;
            this.numAmmoHeavy = 1;
            
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
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.HP, ship.getMaxHealth() * 0.1F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_L, ship.getAttackBaseDamage(1, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_H, ship.getAttackBaseDamage(2, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AL, ship.getAttackBaseDamage(1, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AH, ship.getAttackBaseDamage(2, target));
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.DEF, ship.getAttrs().getDefense() * 0.5F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.SPD, ship.getAttrs().getAttackSpeed());
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.MOV, ship.getAttrs().getMoveSpeed() * 0.1F + 0.3F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.HIT, 6F);
    		this.shipAttrs.setAttrsBuffed(ID.Attrs.DODGE, this.shipAttrs.getAttrsBuffed(ID.Attrs.DODGE) + 0.3F);
    		
    		//apply attrs to entity
    	    getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.HP));
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.MOV));
    		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64D);
    		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
    		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
     
    		//設定AI
    		this.shipNavigator = new ShipPathNavigate(this);
    		this.shipMoveHelper = new ShipMoveHelper(this, 45F);
    		this.setAIList();
        }
        else
        {
        	return;
        }
	}
	
	//setup AI
	@Override
	protected void setAIList()
	{
		this.clearAITasks();
		this.clearAITargetTasks();
		
		this.setEntityTarget(atkTarget);
	}
	
	@Override
	public void onUpdate()
	{
		//client side
		if (this.world.isRemote)
		{
			if (this.ticksExisted % 2 == 0)
			{
				ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+0.2D, this.posZ, 
			      		-this.motionX*0.5D, 0.07D, -this.motionZ*0.5D, (byte)29);
			}
		}
		//server side
		else
		{
			//目標消失或死亡, 直接移除此entity
			if (this.backHome || this.atkTarget == null || !this.atkTarget.isEntityAlive() || this.ticksExisted >= 500)
			{
				this.onImpact();
				return;
			}

			//持續向目標移動
			updateAttackAI();
		}
		
		super.onUpdate();
	}
	
	//attack AI: move and call onImpact
	private void updateAttackAI()
	{
		if (this.atkTarget != null)
		{
            //目標距離計算
            float distX = (float) (atkTarget.posX - this.posX);
            float distY = (float) (atkTarget.posY + 1F - this.posY);
            float distZ = (float) (atkTarget.posZ - this.posZ);	
            float distSq = distX*distX + distY*distY + distZ*distZ;

            //每30 tick找一次路徑, 直到距離目標X格內
        	if (this.ticksExisted % 16 == 0)
        	{
	        	if (distSq > 4F)
	        	{	//距離約2格
		        	this.getShipNavigate().tryMoveToEntityLiving(atkTarget, 1D);
	        	}
        	}
        	
        	if (distSq <= 6F)
        	{
        		this.getShipNavigate().clearPathEntity();
        		this.onImpact();
        	}
    	}//end attack target != null
	}

	//觸發爆炸攻擊
	private void onImpact()
	{
		BasicEntityShip host2 = (BasicEntityShip) this.host;
		if (host2 == null) return;
		
		//apply 
		int type = this.getMissileData(2).type;
		CombatHelper.specialAttackEffect(this.host, type, new float[] {(float)this.posX, (float)this.posY, (float)this.posZ});
		
		//calc miss chance, if not miss, calc cri/multi hit
		//計算範圍爆炸傷害: 判定bounding box內是否有可以吃傷害的entity
        List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().expand(4.5D, 4.5D, 4.5D));
        float atk;
        
        //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
        for(Entity ent : hitList)
        {
        	atk = this.shipAttrs.getAttackDamageHeavy();
        	
        	//目標可以被碰撞, 且目標不同主人, 則判定可傷害
        	if (ent.canBeCollidedWith() && EntityHelper.isNotHost(this, ent) &&
        		!TargetHelper.isEntityInvulnerable(ent))
        	{
        		//calc equip special dmg: AA, ASM
      			atk = CombatHelper.modDamageByAdditionAttrs(this.host, ent, atk, 0);
      			
        		//打到同主人目標, 傷害設為0
        		if (TeamHelper.checkSameOwner(host2, ent))
        		{
        			atk = 0F;
        		}
        		
        		 //roll miss, cri, dhit, thit
        		atk = CombatHelper.applyCombatRateToDamage(this.host, ent, false, 1F, atk);
    	  		
    	  		//damage limit on player target
        		atk = CombatHelper.applyDamageReduceOnPlayer(ent, atk);
    	  		
    	  		//check friendly fire
    			if (!TeamHelper.doFriendlyFire(this.host, ent)) atk = 0F;
        		
        		//對entity造成傷害
            	if (ent.attackEntityFrom(DamageSource.causeMobDamage(host2).setExplosion(), atk))
            	{
            		if (!TeamHelper.checkSameOwner(this.getHostEntity(), ent)) BuffHelper.applyBuffOnTarget(ent, this.getAttackEffectMap());
            	}
        	}//end can be collided with
        }//end hit target list for loop

        //send packet to client for display partical effect
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 2, false), point);
        
        this.setDead();
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
	
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.FloatingFort;
	}
	
	
}