package com.lulan.shincolle.entity.carrier;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:head, 1:weapon, 2:neck, 3:cloak, 4:eye effect
 */
public class EntityCarrierWo extends BasicEntityShipCV
{
	
	public EntityCarrierWo(World world)
	{
		super(world);
		this.setSize(0.7F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.STANDARD_CARRIER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.CVWO);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setStateMinor(ID.M.NumState, 5);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.modelPosInGUI = new float[] {0F, 20F, 0F, 30F};
		this.launchHeight = this.height * 0.9F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(18);
		
		this.initPre();
	}

	@Override
	public int getEquipType()
	{
		return 3;
	}

	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
	}
    
    //增加艦載機數量計算
  	@Override
  	public void calcShipAttributesAddRaw()
  	{
  		super.calcShipAttributesAddRaw();
  		
  		this.maxAircraftLight += this.getLevel() * 0.25F;
  		this.maxAircraftHeavy += this.getLevel() * 0.15F;
  	}
      
    @Override
    public void onLivingUpdate()
    {
    	//check client side
    	if (this.world.isRemote)
    	{
    		//every 4 ticks
    		if (this.ticksExisted % 4 ==  0)
    		{
    			//若顯示裝備時, 則生成眼睛煙霧特效 (client only)
    			if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) && !getStateFlag(ID.F.NoFuel) &&
    				(!isSitting() || getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED))
    			{
    				//set origin position
    				float[] eyePosL;
    				float[] eyePosR;
    				float radYaw = this.rotationYawHead * Values.N.DIV_PI_180;
    				float radPitch = this.rotationPitch * Values.N.DIV_PI_180;
    				
    				//坐下位置計算
    				if (this.isSitting())
    				{
    					eyePosL = new float[] {-0.3F, 1F, -0.4F};
        				eyePosR = new float[] {-0.7F, 0.8F, 0.6F};
    				}
    				else
    				{
    					eyePosL = new float[] {0.55F, 1F, 0.2F};
        				eyePosR = new float[] {-0.55F, 1F, 0.2F};
    				}
    				
    				//側歪頭位置計算, 歪頭只會修改Y高度跟X位置
    				if (getStateEmotion(ID.S.Emotion2) == 1 && !this.isSitting())
    				{
    					float[] tiltLeft = CalcHelper.rotateXZByAxis(eyePosL[0], eyePosL[1], -0.24F, 1F);
    					float[] tiltRight = CalcHelper.rotateXZByAxis(eyePosR[0], eyePosR[1], -0.24F, 1F);
    					eyePosL[0] = tiltLeft[0];
    					eyePosL[1] = tiltLeft[1];
    					eyePosR[0] = tiltRight[0];
    					eyePosR[1] = tiltRight[1];
    				}

    				//依照新位置, 繼續旋轉Y軸
    				eyePosL = CalcHelper.rotateXYZByYawPitch(eyePosL[0], eyePosL[1], eyePosL[2], radYaw, radPitch, 1F);
    				eyePosR = CalcHelper.rotateXYZByYawPitch(eyePosR[0], eyePosR[1], eyePosR[2], radYaw, radPitch, 1F);		
    				
    				//旋轉完三軸, 生成特效
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosL[0], this.posY+1.5D+eyePosL[1], this.posZ+eyePosL[2], 
                    		0D, 0.05D, 1D, (byte)16);
    				
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosR[0], this.posY+1.5D+eyePosR[1], this.posZ+eyePosR[2], 
                    		0D, 0.05D, 1D, (byte)16);
    			}
    			
    			//every 16 ticks
    			if ((this.ticksExisted & 15) == 0)
    			{
    				if (EmotionHelper.checkModelState(4, this.getStateEmotion(ID.S.State)) &&
    					!this.isSitting() && !this.isRiding())
      	  			{
    					ParticleHelper.spawnAttackParticleAtEntity(this, 1.28D, 0.12D, 0.17D, (byte)17);
    				}
    			}//end every 16 ticks
    		}//end every 4 ticks
    	}//end client
    	//server side
    	else
  		{
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				//married effect: apply str buff to nearby ships
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					List<BasicEntityShip> shiplist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(16D, 16D, 16D));
  	  	  			
  					if (shiplist != null && shiplist.size() > 0)
  					{
  						for (BasicEntityShip s : shiplist)
  						{
  							if (TeamHelper.checkSameOwner(this, s))
  							{
  								//potion effect: id, time, level
  								s.addPotionEffect(new PotionEffect(MobEffects.STRENGTH , 30+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 80, false, false));
  							}
  						}
  					}
  				}//end married buff
  			}//end every 128 ticks
  		}//end server side
    	
    	super.onLivingUpdate();
    }
    
    @Override
	public double getMountedYOffset()
    {
    	if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
    	{
    		if (this.isSitting())
    		{
      			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
      			{
					return (double)this.height * 0.2F;
	  			}
	  			else
	  			{
	  				return (double)this.height * 0.43F;
	  			}
      		}
      		else
      		{
      			return (double)this.height * 1.21F;
      		}
    	}
    	else
    	{
    		if (this.isSitting())
    		{
      			return (double)this.height * 0.68D;
      		}
      		else
      		{
      			return (double)this.height * 0.68D;
      		}
    	}
	}
	

}