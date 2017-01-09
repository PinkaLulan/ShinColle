package com.lulan.shincolle.entity.cruiser;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityHeavyCruiserNe extends BasicEntityShipSmall
{
	
	private boolean isPushing = false;
	private int tickPush = 0;
	private EntityLivingBase targetPush = null;
	
	
	public EntityHeavyCruiserNe(World world)
	{
		super(world);
		this.setSize(0.6F, 1.3F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.HeavyCruiserNE);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CA]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CA]);
		this.ModelPos = new float[] {0F, 10F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.HaveRingEffect] = true;
		
		//misc
		this.setFoodSaturationMax(14);
		
		this.postInit();
	}

	@Override
	public int getEquipType()
	{
		return 1;
	}

	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}

	//晚上時額外增加屬性
	@Override
	public void calcShipAttributes()
	{
		if (!this.world.isDaytime())
		{
			EffectEquip[ID.EF_CRI] = EffectEquip[ID.EF_CRI] + 0.3F;
		}
		
		super.calcShipAttributes();	
	}

    @Override
    public void onLivingUpdate()
    {
    	//check server side
    	if(!this.world.isRemote)
    	{
    		//check every 128 ticks
    		if (this.ticksExisted % 128 == 0)
    		{
	    		//apply potion effect in the night
	        	if (!this.world.isDaytime() && this.getStateFlag(ID.F.UseRingEffect))
	        	{	
        			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 1));
        			this.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 300, 2));
        		}
	        	
	        	//push other people every 256 ticks
	        	if (this.ticksExisted % 256 == 0)
	        	{
	        		if (this.getRNG().nextInt(5) == 0 && !this.isSitting() && !this.isRiding() &&
	        			!this.getStateFlag(ID.F.NoFuel) && !this.getIsLeashed())
	        		{
	        			//find target
	        			this.findTargetPush();
	        		}
	        	}
        	}
    		
    		//若要找騎乘目標
        	if (this.isPushing)
        	{
        		this.tickPush++;
        		
        		//找太久, 放棄騎乘目標
        		if (this.tickPush > 200 || this.targetPush == null)
        		{
        			this.cancelPush();
        		}
        		else
        		{
        			float distPush = this.getDistanceToEntity(this.targetPush);
        			
            		if (distPush <= 2.5F)
            		{
            			this.targetPush.addVelocity(-MathHelper.sin(rotationYaw * Values.N.DIV_PI_180) * 0.5F, 
         	                   0.5D, MathHelper.cos(rotationYaw * Values.N.DIV_PI_180) * 0.5F);
            			
            			//for other player, send ship state for display
            	  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
            	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.targetPush, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
					    
					    //play entity attack sound
            	  		this.playSound(getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
					    
					    this.cancelPush();
            		}
            		else
            		{
            			//每32 tick找一次路徑
                		if (this.ticksExisted % 32 == 0)
                		{
                			this.getShipNavigate().tryMoveToEntityLiving(this.targetPush, 1D);
                		}
            		}
        		}
        	}//end push target
    	}
    	
    	super.onLivingUpdate();
    }
    
    private void cancelPush()
    {
    	this.isPushing = false;
    	this.tickPush = 0;
    	this.targetPush = null;
    }
    
    //find target to push
    private void findTargetPush()
    {
        AxisAlignedBB impactBox = this.getEntityBoundingBox().expand(12D, 6D, 12D); 
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
        
        //valid entity
        list.removeIf(ent -> this.equals(ent) || !ent.canBePushed() || !ent.canBeCollidedWith());
        
        //從可騎乘目標中挑出一個目標騎乘
        if (list.size() > 0)
        {
        	this.targetPush = list.get(rand.nextInt(list.size()));
        	this.tickPush = 0;
			this.isPushing = true;
        }
    }

    @Override
	public double getMountedYOffset()
    {
  		if (this.isSitting())
  		{
			return 0F;
  		}
  		else
  		{
  			return this.height * 0.24F;
  		}
	}
    
    @Override
    public double getYOffset()
    {
        return 0.3D;
    }

	@Override
	public void setShipOutfit(boolean isSneaking) {}


}