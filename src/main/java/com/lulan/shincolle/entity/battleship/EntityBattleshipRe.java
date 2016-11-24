package com.lulan.shincolle.entity.battleship;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBattleshipRe extends BasicEntityShipCV {
	
	private boolean isPushing = false;
	private int tickPush = 0;
	private EntityLivingBase targetPush = null;
	
	
	public EntityBattleshipRe(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipRE);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.ModelPos = new float[] {-6F, 10F, 0F, 40F};
		this.ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.launchHeight = this.height * 0.8F;
		
		this.postInit();
	}
	
	@Override
	public float getEyeHeight() {
		return 1.7375F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 2;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}

	//增加艦載機數量計算
	@Override
	public void calcShipAttributes() {
		EffectEquip[ID.EF_DHIT] = EffectEquip[ID.EF_DHIT] + 0.1F;
		EffectEquip[ID.EF_THIT] = EffectEquip[ID.EF_THIT] + 0.1F;
		
		this.maxAircraftLight += this.getLevel() * 0.1F;
		this.maxAircraftHeavy += this.getLevel() * 0.05F;
		
		super.calcShipAttributes();	
	}
	
	@Override
    public void onLivingUpdate() {
    	//check server side
    	if(!this.worldObj.isRemote) {
        	//push other people every 256 ticks
        	if(this.ticksExisted % 256 == 0) {
        		if(this.getRNG().nextInt(5) == 0 && !this.isSitting() && !this.isRiding() &&
        		   !this.getStateFlag(ID.F.NoFuel) && !this.getIsLeashed()) {
        			//find target
        			this.findTargetPush();
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
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				this.setShipOutfit(player.isSneaking());
				return true;
			}
		}
		
		return super.interact(player);
  	}
    
    @Override
	public void setShipOutfit(boolean isSneaking) {
		switch(getStateEmotion(ID.S.State)) {
		case ID.State.NORMAL:
			setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
		default:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		}
	}
    
    //change light cannon particle
    @Override
    public void applyParticleAtAttacker(int type, Entity target, float[] vec) {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch(type) {
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 14, posX, posY + 1.5D, posZ, target.posX, target.posY+target.height/2F, target.posZ, true), point);
  			break;
  		case 2:  //heavy cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 3:  //light aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 4:  //heavy aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			break;
  		}
  	}

    //change light cannon sound
    @Override
    public void applySoundAtAttacker(int type, Entity target) {
  		switch(type) {
  		case 1:  //light cannon
  			//fire sound
  	        playSound(Reference.MOD_ID+":ship-laser", 0.2F, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        
  			//entity sound
  			if(this.rand.nextInt(10) > 7) {
  	        	this.playSound(getSoundString(ID.Sound.Hit), ConfigHandler.volumeShip, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 2:  //heavy cannon
  			//fire sound
  	        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.volumeFire, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));

  	        //entity sound
  	        if(this.getRNG().nextInt(10) > 7) {
  	        	this.playSound(getSoundString(ID.Sound.Hit), ConfigHandler.volumeShip, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 3:  //light aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
  		case 4:  //heavy aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
		default: //melee
			if(this.getRNG().nextInt(10) > 6) {
	        	this.playSound(getSoundString(ID.Sound.Hit), ConfigHandler.volumeShip, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        }
			break;
  		}
  	}
	
	@Override
	public int getKaitaiType() {
		return 1;
	}
	
	@Override
	public double getMountedYOffset() {
		if(this.isSitting()) {
  			return (double)this.height * 0.0F;
  		}
  		else {
  			return (double)this.height * 0.4F;
  		}
	}
	
	
}
