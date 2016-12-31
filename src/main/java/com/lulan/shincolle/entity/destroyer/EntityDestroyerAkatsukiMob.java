package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityDestroyerAkatsukiMob extends BasicEntityShipHostile implements IShipRiderType
{

	private int ridingState;
	private float smokeX, smokeY;
	
	
	public EntityDestroyerAkatsukiMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerAkatsuki);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
        this.ridingState = 0;
        this.smokeX = 0F;
        this.smokeY = 0F;
        
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(4), false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.4F, 6F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		case 2:
			this.setSize(1.2F, 4F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		case 1:
			this.setSize(0.85F, 2.5F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		default:
			this.setSize(0.6F, 1.5F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		}
	}
	
	@Override
	protected float[] getAttrsMod()
	{                     //HP    ATK   DEF   SPD   MOV   HIT
		return new float[] {0.5F, 0.5F, 0.5F, 1F,   1F,   0.7F};
	}
	
	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS);
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
  	
  	@Override
	public float getEffectEquip(int id)
  	{
		switch (id)
		{
		case ID.EF_AA:  //DD vs AA,ASM effect
		case ID.EF_ASM:
			return this.atk * 0.5F;
		default:
			return 0F;
		}
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
  				if (this.getStateEmotion(ID.S.State) > ID.State.EQUIP01)
  				{
  					//計算煙霧位置, 生成裝備冒煙特效
  	  				float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*0.3D, 0D, 0D, (byte)43);
  	  			}
  	  			
  				if (this.ticksExisted % 16 == 0)
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
