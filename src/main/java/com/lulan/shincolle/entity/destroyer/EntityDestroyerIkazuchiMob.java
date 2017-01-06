package com.lulan.shincolle.entity.destroyer;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityDestroyerIkazuchiMob extends BasicEntityShipHostile implements IShipRiderType
{

	public boolean isRaiden;
	private int ridingState;
	private float smokeX, smokeY;
	
	
	public EntityDestroyerIkazuchiMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerIkazuchi);
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
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);
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
		switch(id) {
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
  			if (this.getStateEmotion(ID.S.State) > ID.State.EQUIP01 && this.ticksExisted % 4 == 0)
  			{
  				//計算煙霧位置, 生成裝備冒煙特效
  				float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*0.8D, 0D, 0D, (byte)43);
  			}
  			
  			if (this.ticksExisted % 16 == 0)
  			{
				this.checkIsRaiden();
  				this.checkRidingState();
  			}//end 16 ticks
  		}
  		//server side
  		else
  		{
  			if (this.ticksExisted % 128 == 0)
  			{
  				this.checkIsRaiden();
				this.tryRaidenGattai();
  			}
  		}
  	}
  	
	//檢查是否可以合體
	public void tryRaidenGattai()
	{
		//stop gattai if sit/no fuel/already 6D gattai
  		if (this.isRaiden) return;
		
		//get nearby ship
        List<EntityDestroyerInazumaMob> slist = this.world.getEntitiesWithinAABB(EntityDestroyerInazumaMob.class, this.getEntityBoundingBox().expand(16D, 8D, 16D));

        if (slist != null && !slist.isEmpty())
        {
        	for (EntityDestroyerInazumaMob s : slist)
        	{
        		if (s != null && s.isEntityAlive() && !s.isRaiden && s.getScaleLevel() == this.scaleLevel)
        		{
        			this.startRiding(s, true);
          			this.isRaiden = true;
          			s.isRaiden = true;
          			return;
        		}
        	}
        }//end get ship
	}
	
  	/**
  	 * state: 0:無騎乘, 1:六驅合體 or 雷電合體
  	 */
  	public void checkRidingState()
  	{
  		if (this.isRaiden)
  		{
  			this.ridingState = 1;
  		}
  		else
  		{
  			this.ridingState = 0;
  		}
  	}
	
  	public void checkIsRaiden()
  	{
		if (this.getRidingEntity() instanceof EntityDestroyerInazumaMob)
		{
			this.isRaiden = true;
		}
		else
		{
			this.isRaiden = false;
		}
  	}
	
  	@Override
	public double getMountedYOffset()
  	{
  		return this.height * 0.47F;
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