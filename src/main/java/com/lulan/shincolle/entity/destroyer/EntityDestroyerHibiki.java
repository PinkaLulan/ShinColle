package com.lulan.shincolle.entity.destroyer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;


public class EntityDestroyerHibiki extends BasicEntityShipSmall
{

	
	public EntityDestroyerHibiki(World world)
	{
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerHibiki);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 13F, 0F, 50F};
		this.ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight()
	{
		return 1.4F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType()
	{
		return 1;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//server side
  		if (!worldObj.isRemote)
  		{
			if (this.ticksExisted % 128 == 0)
  			{
  				//add aura to master every 128 ticks
  				EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  				
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0 && player != null &&
  					getDistanceSqToEntity(player) < 256D)
  				{
  					//potion effect: id, time, level
  					player.addPotionEffect(new PotionEffect(Potion.jump.id, 300, getStateMinor(ID.M.ShipLevel) / 45 + 1));
  				}
  			}
  		}
  		//client side
  		else
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (getStateEmotion(ID.S.State) > ID.State.NORMAL && !isSitting() && !getStateFlag(ID.F.NoFuel))
  				{
  					double smokeY = posY + 1.4D;
  					float addz = 0F;
  					
  					if (this.ridingEntity != null) addz = -0.2F;
  					
  					//計算煙霧位置
  	  				float[] partPos = ParticleHelper.rotateXZByAxis(-0.42F + addz, 0F, (this.renderYawOffset % 360) / 57.2957F, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}	
  			}
  		}
  	}
  	
  	@Override
  	public boolean interact(EntityPlayer player)
  	{	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if (itemstack != null)
		{
			if (itemstack.getItem() == Items.cake)
			{
				this.setShipOutfit(player.isSneaking());
				return true;
			}
		}
		
		return super.interact(player);
  	}
  	
  	//增加閃避30%
  	@Override
  	public void calcShipAttributes() {
  		EffectEquip[ID.EF_DODGE] += 30F;
  		
  		super.calcShipAttributes();	
  	}
  	
  	@Override
	public int getKaitaiType()
  	{
		return 2;
	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
  			return (double)this.height * 0.15F;
  		}
  		else
  		{
  			return (double)this.height * 0.47F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.State.NORMAL_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
				break;
			case ID.State.EQUIP00_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP01_2, true);
				break;
			case ID.State.EQUIP01_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP02_2, true);
				break;
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
				break;
			}
		}
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.State.NORMAL:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
				break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
				break;
			}
		}
	}
	

}




