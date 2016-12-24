package com.lulan.shincolle.entity.destroyer;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityDestroyerInazumaMob extends BasicEntityShipHostile implements IShipRiderType
{

	public boolean isRaiden;
	private int ridingState;
	
	
	public EntityDestroyerInazumaMob(World world)
	{
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerInazuma);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		
        //basic attr
        this.atk = (float) ConfigHandler.scaleMobSmall[ID.ATK] * 0.5F;
        this.atkSpeed = (float) ConfigHandler.scaleMobSmall[ID.SPD] * 1F;
        this.atkRange = (float) ConfigHandler.scaleMobSmall[ID.HIT] * 0.7F;
        this.defValue = (float) ConfigHandler.scaleMobSmall[ID.DEF] * 0.5F;
        this.movSpeed = (float) ConfigHandler.scaleMobSmall[ID.MOV] * 1F;

        //AI flag
        this.isRaiden = false;
        this.ridingState = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
        this.setStateEmotion(ID.S.State, rand.nextInt(2), false);
        
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.scaleMobSmall[ID.HP] * 0.5F);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
		this.setAITargetList();
	}
	
	@Override
	protected boolean canDespawn()
	{
        return this.ticksExisted > 600;
    }
	
	@Override
	public float getEyeHeight()
	{
		return 1.4F;
	}
	
	//chance drop
	@Override
	public ItemStack getDropEgg()
	{
		return this.rand.nextInt(5) == 0 ? this.dropItem : null;
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
  			if(this.getStateEmotion(ID.S.State) > ID.State.NORMAL && this.ticksExisted % 4 == 0) {
				double smokeY = posY + 1.4D;
				
				//計算煙霧位置
  				float[] partPos = CalcHelper.rotateXZByAxis(-0.42F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  				//生成裝備冒煙特效
  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
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
  				
  				if (this.isRaiden)
  				{
  			        //basic attr
  			        this.atk = (float) ConfigHandler.scaleMobSmall[ID.ATK] * 0.7F;
  			        this.atkSpeed = (float) ConfigHandler.scaleMobSmall[ID.SPD] * 1.2F;
  			        this.defValue = (float) ConfigHandler.scaleMobSmall[ID.DEF] * 0.7F;
  				}
  				else
  				{
  			        //basic attr
  			        this.atk = (float) ConfigHandler.scaleMobSmall[ID.ATK] * 0.5F;
  			        this.atkSpeed = (float) ConfigHandler.scaleMobSmall[ID.SPD] * 1F;
  			        this.defValue = (float) ConfigHandler.scaleMobSmall[ID.DEF] * 0.5F;
  				}
  			}
  		}
  	}
  	
  	@Override
    public void updatePassenger(Entity passenger)
    {
        if (this.isPassenger(passenger))
        {
            passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
        }
    }
  	
  	@Override
	public double getMountedYOffset()
  	{
  		return this.height * 0.47F;
	}
  	
  	/**
  	 * state: 0:無騎乘, 2:雷電合體狀態
  	 */
  	public void checkRidingState()
  	{
  		if (this.isRaiden)
  		{
  			this.ridingState = 2;
  		}
  		else
  		{
  			this.ridingState = 0;
  		}
  	}
  	
  	public void checkIsRaiden()
  	{
  		this.isRaiden = false;
  		
  		List<Entity> riders = this.getPassengers();
  		
  		for (Entity rider : riders)
  		{
  			if (rider instanceof EntityDestroyerIkazuchiMob)
  			{
  				this.isRaiden = true;
  				break;
  			}
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