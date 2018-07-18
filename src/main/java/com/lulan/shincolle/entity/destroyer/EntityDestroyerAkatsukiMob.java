package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * model state:
 *   0:cannon, 1:head, 2:weapon, 3:armor
 */
public class EntityDestroyerAkatsukiMob extends BasicEntityShipHostile implements IShipRiderType
{

	private int ridingState;
	private float smokeX, smokeY;
	
	
	public EntityDestroyerAkatsukiMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDAkatsuki);
        this.ridingState = 0;
        this.smokeX = 0F;
        this.smokeY = 0F;
        
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(16), false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.7F, 6F);
			this.smokeX = -1.65F;
			this.smokeY = 5.3F;
		break;
		case 2:
			this.setSize(1.3F, 4.5F);
			this.smokeX = -1.2F;
			this.smokeY = 4.1F;
		break;
		case 1:
			this.setSize(0.9F, 3F);
			this.smokeX = -0.8F;
			this.smokeY = 2.7F;
		break;
		default:
			this.setSize(0.5F, 1.5F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_10);
	}
	
	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}
	
  	@Override
	public int getDamageType()
  	{
		return ID.ShipDmgType.DESTROYER;
	}
  	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (world.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
  				{
  					//計算煙霧位置, 生成裝備冒煙特效
  	  				float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*0.8D, 0D, 0D, (byte)43);
  	  			}
  	  			
  				if (this.ticksExisted % 16 == 0 && !this.isMorph)
  	  			{
  					//update searchlight
  	  	    		if(ConfigHandler.canSearchlight)
  	  	    		{
  	  	        		updateSearchlight();
  	  	    		}
  	  			}//end every 16 ticks
  			}//end every 4 ticks
  		}
  	}
  	
  	/** update flare effect */
  	protected void updateSearchlight()
  	{
		BlockPos pos = new BlockPos(this);
		float light = this.world.getLightFor(EnumSkyBlock.BLOCK, pos);

		//place new light block
		if (light < 12F)
		{
			BlockHelper.placeLightBlock(this.world, pos);
		}
		//search light block, renew lifespan
		else
		{
			BlockHelper.updateNearbyLightBlock(this.world, pos);
		}
  	}

  	@Override
  	public int getRidingState()
  	{
  		return this.ridingState;
  	}
  	
	@Override
	public void setRidingState(int state)
	{
		this.ridingState = state;
	}

	@Override
	public int getRiderType()
	{
		return 0;
	}

	@Override
	public void setRiderType(int type) {}
	
	
}