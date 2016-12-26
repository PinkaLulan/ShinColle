package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityDestroyerAkatsukiMob extends BasicEntityShipHostile implements IShipRiderType
{

	private int ridingState;
	
	
	public EntityDestroyerAkatsukiMob(World world)
	{
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerAkatsuki);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		
        //basic attr
        this.atk = (float) ConfigHandler.scaleMobSmall[ID.ATK] * 0.5F;
        this.atkSpeed = (float) ConfigHandler.scaleMobSmall[ID.SPD] * 1F;
        this.atkRange = (float) ConfigHandler.scaleMobSmall[ID.HIT] * 0.7F;
        this.defValue = (float) ConfigHandler.scaleMobSmall[ID.DEF] * 0.5F;
        this.movSpeed = (float) ConfigHandler.scaleMobSmall[ID.MOV] * 1F;

        //AI flag
        this.ridingState = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
        this.setStateEmotion(ID.S.State, rand.nextInt(4), false);
 
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
		if (ConfigHandler.despawnMinion > -1)
		{
			return this.ticksExisted > ConfigHandler.despawnMinion;
		}
        
		return false;
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
  					double smokeY = posY + 1.4D;
  					
  					//計算煙霧位置
  	  				float[] partPos = CalcHelper.rotateXZByAxis(-0.42F, 0F, (this.renderYawOffset % 360) / 57.2957F, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
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
