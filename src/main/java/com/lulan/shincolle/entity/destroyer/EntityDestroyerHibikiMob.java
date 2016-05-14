package com.lulan.shincolle.entity.destroyer;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityDestroyerHibikiMob extends BasicEntityShipHostile {

	
	public EntityDestroyerHibikiMob(World world)
	{
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityDestroyerHibiki.name"));
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerHibiki);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		
        //basic attr
        this.atk = (float) ConfigHandler.scaleMobSmall[ID.ATK] * 0.5F;
        this.atkSpeed = (float) ConfigHandler.scaleMobSmall[ID.SPD] * 1F;
        this.atkRange = (float) ConfigHandler.scaleMobSmall[ID.HIT] * 0.7F;
        this.defValue = (float) ConfigHandler.scaleMobSmall[ID.DEF] * 0.5F;
        this.movSpeed = (float) ConfigHandler.scaleMobSmall[ID.MOV] * 1F;

        //AI flag
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
        this.setStateEmotion(ID.S.State, rand.nextInt(2), false);
        this.setStateEmotion(ID.S.State2, rand.nextInt(3), false);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(ConfigHandler.scaleMobSmall[ID.HP] * 0.5F);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(atkRange + 32); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1D);
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
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
  		if (worldObj.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (this.getStateEmotion(ID.S.State) > ID.State.NORMAL)
  				{
  					double smokeY = posY + 1.4D;
  					
  					//計算煙霧位置
  	  				float[] partPos = ParticleHelper.rotateXZByAxis(-0.42F, 0F, (this.renderYawOffset % 360) / 57.2957F, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  	  			}
  			}//end every 4 ticks
  		}
  	}
  	

}



