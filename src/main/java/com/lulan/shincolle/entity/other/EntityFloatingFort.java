package com.lulan.shincolle.entity.other;

import java.util.List;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAircraftAttack;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
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
	public void initAttrs(IShipAircraftAttack host, Entity target, double launchPos)
	{
        this.host = host;
        this.atkTarget = target;
        
        if (host instanceof BasicEntityShip)
        {
        	BasicEntityShip ship = (BasicEntityShip) host;
        	
        	this.targetSelector = new TargetHelper.Selector(ship);
    		this.targetSorter = new TargetHelper.Sorter(ship);
    		
        	//basic attr
            this.atk = ship.getStateFinal(ID.ATK_H) * 0.75F;
            this.def = ship.getStateFinal(ID.DEF) * 0.75F;
            this.atkSpeed = ship.getStateFinal(ID.SPD);
            this.movSpeed = 0.3F;
            
            //AI flag
            this.numAmmoLight = 0;
            this.numAmmoHeavy = 1;
            this.useAmmoLight = false;
            this.useAmmoHeavy = true;
            this.backHome = false;
            this.canFindTarget = false;
            
            //設定發射位置
            this.posX = ship.posX;
            this.posY = launchPos;
            this.posZ = ship.posZ;
            this.setPosition(this.posX, this.posY, this.posZ);
     
    	    //設定基本屬性
    	    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ship.getStateFinal(ID.HP)*0.1D);
    		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
    		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(ship.getStateFinal(ID.HIT)+32D); //此為找目標, 路徑的範圍
    		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
    		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
    				
    		//設定AI
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
		if (this.worldObj.isRemote)
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
		//get attack value
		float atk2;
		boolean isTargetHurt = false;
		EntityLivingBase hostent = null;
		
		if (this.host instanceof EntityLivingBase)
		{
			hostent = (EntityLivingBase) this.host;
		}
		else
		{
			return;
		}
		
		//calc miss chance, if not miss, calc cri/multi hit
		//計算範圍爆炸傷害: 判定bounding box內是否有可以吃傷害的entity
        List<Entity> hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(4.5D, 4.5D, 4.5D));
        
        //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
        for(Entity ent : hitList)
        {
        	atk2 = this.atk;
        	
        	//check target attackable
      		if (!TargetHelper.checkUnattackTargetList(ent))
      		{
      			//calc equip special dmg: AA, ASM
            	atk2 = CalcHelper.calcDamageBySpecialEffect(this, ent, atk2, 0);
            	
            	//目標可以被碰撞, 且目標不同主人, 則判定可傷害
            	if (ent.canBeCollidedWith() && !TeamHelper.checkSameOwner(hostent, ent))
            	{
        			//calc critical, only for type:ship
            		if (host != null && (this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_CRI)))
            		{
            			atk2 *= 3F;
                		//spawn critical particle
                		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
                    	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(hostent, 11, false), point);
                	}
            		
            		//若攻擊到玩家, 則傷害減為25%, 且最大傷害固定為TNT傷害 (non-owner)
                	if (ent instanceof EntityPlayer)
                	{
                		atk2 *= 0.25F;
                		
                		if (atk2 > 59F)
                		{
                			atk2 = 59F;	//same with TNT
                		}
                	}
                	
                	//check friendly fire
            		if(!TeamHelper.doFriendlyFire(this.host, ent))
            		{
            			atk2 = 0F;
            		}
                	
            		//對entity造成傷害
                	if (host != null)
                	{
                		isTargetHurt = ent.attackEntityFrom(DamageSource.causeMobDamage(hostent).setExplosion(), atk2);
                	}
                	else
                	{
                		isTargetHurt = ent.attackEntityFrom(DamageSource.causeMobDamage(this).setExplosion(), atk2);
                	}
            	}//end can be collided with
      		}//end is attackable
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


}

