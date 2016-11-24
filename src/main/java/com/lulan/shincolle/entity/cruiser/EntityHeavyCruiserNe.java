package com.lulan.shincolle.entity.cruiser;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityHeavyCruiserNe extends BasicEntityShipSmall {
	
	private boolean isPushing = false;
	private int tickPush = 0;
	private EntityLivingBase targetPush = null;
	
	
	public EntityHeavyCruiserNe(World world) {
		super(world);
		this.setSize(0.6F, 1.3F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.HeavyCruiserNE);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CA]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CA]);
		this.ModelPos = new float[] {0F, 10F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(14);
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}

	@Override
	public void setAIList() {
		super.setAIList();
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	//NE級額外增加屬性
	@Override
	public void calcShipAttributes() {
		EffectEquip[ID.EF_CRI] = EffectEquip[ID.EF_CRI] + 0.15F;
		
		super.calcShipAttributes();	
	}

    @Override
    public void onLivingUpdate() {
    	//check server side
    	if(!this.worldObj.isRemote) {
    		//check every 128 ticks
    		if(this.ticksExisted % 128 == 0) {
	    		//apply potion effect in the night
	        	if(!this.worldObj.isDaytime() && this.getStateFlag(ID.F.UseRingEffect)) {	
        			this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 300, 1));
        			this.addPotionEffect(new PotionEffect(Potion.jump.id, 300, 2));
        		}
	        	
	        	//push other people every 256 ticks
	        	if(this.ticksExisted % 256 == 0) {
	        		if(this.getRNG().nextInt(5) == 0 && !this.isSitting() && !this.isRiding() &&
	        		   !this.getStateFlag(ID.F.NoFuel) && !this.getIsLeashed()) {
	        			//find target
	        			this.findTargetPush();
	        		}
	        	}
        	}
    		
    		//若要找騎乘目標
        	if(this.isPushing) {
        		this.tickPush++;
        		
        		//找太久, 放棄騎乘目標
        		if(this.tickPush > 200 || this.targetPush == null) {
        			this.cancelPush();
        		}
        		else {
        			float distPush = this.getDistanceToEntity(this.targetPush);
        			
        			//每32 tick找一次路徑
            		if(this.ticksExisted % 32 == 0) {
            			if(distPush > 2F) {
            				this.getShipNavigate().tryMoveToEntityLiving(this.targetPush, 1D);
            			}
            		}
            		
            		if(distPush <= 2.5F) {
            			this.targetPush.addVelocity(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * 0.5F, 
         	                   0.5D, MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * 0.5F);
            			
            			//for other player, send ship state for display
            	  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
            	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.targetPush, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
					    
					    //play entity attack sound
					    this.playSound(getSoundString(ID.Sound.Hit), ConfigHandler.volumeShip, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
					    
					    this.cancelPush();
            		}
        		}
        	}//end push target
    	}
    	super.onLivingUpdate();
    }
    
    private void cancelPush() {
    	this.isPushing = false;
    	this.tickPush = 0;
    	this.targetPush = null;
    }
    
    //find target to push
    private void findTargetPush() {
    	EntityLivingBase getEnt = null;
        AxisAlignedBB impactBox = this.boundingBox.expand(12D, 6D, 12D); 
        List hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
        List<EntityLivingBase> canPushList = new ArrayList();
        
        //搜尋list, 找出第一個可以騎乘的目標
        if(hitList != null && !hitList.isEmpty()) {
            for(int i = 0; i < hitList.size(); ++i) {
            	getEnt = (EntityLivingBase)hitList.get(i);
            	
            	//只騎乘同主人的棲艦或者主人
        		if(getEnt != this) {
        			canPushList.add(getEnt);
        		}
            }
        }
        
        //從可騎乘目標中挑出一個目標騎乘
        if(canPushList.size() > 0) {
        	this.targetPush = canPushList.get(rand.nextInt(canPushList.size()));
        	this.tickPush = 0;
			this.isPushing = true;
        }
    }
    
    @Override
	public int getKaitaiType() {
		return 0;
	}
	
    @Override
	public double getMountedYOffset() {
    	if(this.isSitting()) {
  			return (double)this.height * -0.15F;
  		}
  		else {
  			return (double)this.height * 0.15F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {}


}


