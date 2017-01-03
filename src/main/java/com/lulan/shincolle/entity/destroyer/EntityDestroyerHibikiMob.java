package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityDestroyerHibikiMob extends BasicEntityShipHostile implements IShipRiderType
{

	private int ridingState;
	private float smokeX, smokeY;
	
	
	public EntityDestroyerHibikiMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerHibiki);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
        this.ridingState = 0;
        this.smokeX = 0F;
        this.smokeY = 0F;
        
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(2), false);
        this.setStateEmotion(ID.S.State2, rand.nextInt(4), false);
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
	protected float[] getAttrsMod()
	{                     //HP    ATK   DEF   SPD   MOV   HIT
		return new float[] {0.5F, 0.5F, 0.5F, 1F,   1F,   0.7F};
	}
	
	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
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
  				if (this.getStateEmotion(ID.S.State) > ID.State.NORMAL)
  				{
  					//計算煙霧位置, 生成裝備冒煙特效
  	  				float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*0.8D, 0D, 0D, (byte)43);
  	  			}
  			}//end every 4 ticks
  		}
  	}
  	
	@Override
	public int getRiderType()
	{
		return 0;
	}

	@Override
	public void setRiderType(int type) {}
	
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
  	

}