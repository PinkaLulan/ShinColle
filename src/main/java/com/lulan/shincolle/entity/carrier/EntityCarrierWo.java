package com.lulan.shincolle.entity.carrier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityCarrierWo extends BasicEntityShipCV {
	
	public EntityCarrierWo(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.STANDARD_CARRIER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierWO);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {0F, 15F, 0F, 30F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		this.launchHeight = this.height * 1.1D;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.7375F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 3;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));		   //0011
	}
    
    //增加艦載機數量計算
  	@Override
  	public void calcShipAttributes() {
  		super.calcShipAttributes();
  		
  		this.maxAircraftLight += this.getLevel() * 0.25F;
  		this.maxAircraftHeavy += this.getLevel() * 0.15F;
  	}
      
    @Override
    public void onLivingUpdate() {
    	//check client side
    	if(this.worldObj.isRemote) {
    		if(this.ticksExisted % 5 ==  0) {
    			//若顯示裝備時, 則生成眼睛煙霧特效 (client only)
    			if(getStateEmotion(ID.S.State) >= ID.State.EQUIP00 &&
    			   getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED) {
    				//set origin position
    				float[] eyePosL;
    				float[] eyePosR;
    				float radYaw = this.rotationYawHead * Values.N.RAD_MUL;
    				float radPitch = this.rotationPitch * Values.N.RAD_MUL;
    				
    				//坐下位置計算
    				if(this.isSitting()) {
    					eyePosL = new float[] {-0.3F, 1.2F, -0.4F};
        				eyePosR = new float[] {-0.7F, 1.0F, 0.6F};
    				}
    				else {
    					eyePosL = new float[] {0.55F, 1.2F, 0.2F};
        				eyePosR = new float[] {-0.55F, 1.2F, 0.2F};
    				}
    				
    				//側歪頭位置計算, 歪頭只會修改Y高度跟X位置
    				if(getStateEmotion(ID.S.Emotion2) == 1 && !this.isSitting()) {
    					float[] tiltLeft = ParticleHelper.rotateXZByAxis(eyePosL[0], eyePosL[1], -0.24F, 1F);
    					float[] tiltRight = ParticleHelper.rotateXZByAxis(eyePosR[0], eyePosR[1], -0.24F, 1F);
    					eyePosL[0] = tiltLeft[0];
    					eyePosL[1] = tiltLeft[1];
    					eyePosR[0] = tiltRight[0];
    					eyePosR[1] = tiltRight[1];
    				}

    				//依照新位置, 繼續旋轉Y軸
    				eyePosL = ParticleHelper.rotateXYZByYawPitch(eyePosL[0], eyePosL[1], eyePosL[2], radYaw, radPitch, 1F);
    				eyePosR = ParticleHelper.rotateXYZByYawPitch(eyePosR[0], eyePosR[1], eyePosR[2], radYaw, radPitch, 1F);		
    				
    				//旋轉完三軸, 生成特效
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosL[0], this.posY+1.5D+eyePosL[1], this.posZ+eyePosL[2], 
                    		0D, 0.05D, 0D, (byte)16);
    				
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosR[0], this.posY+1.5D+eyePosR[1], this.posZ+eyePosR[2], 
                    		0D, 0.05D, 0D, (byte)16);
    			}			
    		}	
    	}//end client
    	
    	super.onLivingUpdate();
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
		
		super.interact(player);
		return false;
  	}
    
    @Override
	public int getKaitaiType() {
		return 1;
	}
    
    @Override
	public double getMountedYOffset() {
    	if(this.getStateEmotion(ID.S.State) > ID.State.NORMAL) {
    		if(this.isSitting()) {
      			if(getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
					return (double)this.height * 0.9D;
	  			}
	  			else {
	  				return (double)this.height * 1.3D;
	  			}
      		}
      		else {
      			return (double)this.height * 1.35D;
      		}
    	}
    	else {
    		if(this.isSitting()) {
      			return (double)this.height * 0.68D;
      		}
      		else {
      			return (double)this.height * 0.68D;
      		}
    	}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		switch(getStateEmotion(ID.S.State)) {
		case ID.State.NORMAL:
			setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
		case ID.State.EQUIP00:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		default:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		}
	}
	

}


