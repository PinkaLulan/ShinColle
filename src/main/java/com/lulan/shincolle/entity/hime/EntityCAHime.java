package com.lulan.shincolle.entity.hime;

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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:tail state 1, 1:tail state 2
 *   2:hat state 1, 3:hat state 2, 4:hat state 3
 *   
 *   0 & 1 false = no tail (tail state 0)
 *   2 & 3 & 4 false = no hat (hat state 0)
 */
public class EntityCAHime extends BasicEntityShipSmall
{
	
	private boolean isPushing = false;
	private int tickPush = 0;
	private EntityLivingBase targetPush = null;
	
	
	public EntityCAHime(World world)
	{
		super(world);
		this.setSize(0.7F, 1.2F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.HeavyCruiserHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setStateMinor(ID.M.NumState, 5);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CA]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CA]);
		this.ModelPos = new float[] {0F, 10F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.HaveRingEffect] = true;
		
		//misc
		this.setFoodSaturationMax(17);
		
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

		//use range attack
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	//晚上時額外增加屬性
	@Override
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		if (!this.world.isDaytime())
		{
			this.getAttrs().setAttrsRaw(ID.Attrs.CRI, this.getAttrs().getAttrsRaw(ID.Attrs.CRI) + 0.1F);
			this.getAttrs().setAttrsRaw(ID.Attrs.THIT, this.getAttrs().getAttrsRaw(ID.Attrs.THIT) + 0.1F);
			this.getAttrs().setAttrsRaw(ID.Attrs.THIT, this.getAttrs().getAttrsRaw(ID.Attrs.THIT) + 0.1F);
		}
	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return 0F;
  			}
  			else
  			{
  				return this.height * 0.62F;
  			}
  		}
  		else
  		{
  			return this.height * 0.76F;
  		}
	}
	
    @Override
    public void onLivingUpdate()
    {
    	//check server side
    	if(!this.world.isRemote)
    	{
    		//check every 128 ticks
    		if ((this.ticksExisted & 127) == 0)
    		{
	        	//apply potion effect in the night
	        	if (!this.world.isDaytime() && this.getStateFlag(ID.F.UseRingEffect))
	        	{	
        			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 150, getStateMinor(ID.M.ShipLevel) / 50, false, false));
        			this.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 150, getStateMinor(ID.M.ShipLevel) / 40, false, false));
        		}
	        	
	        	//push other people every 256 ticks
	        	if ((this.ticksExisted & 255) == 0)
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
            			
            			//sync target motion
            	  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
            	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.targetPush, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
					    
					    //play entity attack sound
            	  		this.swingArm(EnumHand.MAIN_HAND);
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

	
}