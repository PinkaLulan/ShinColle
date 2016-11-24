package com.lulan.shincolle.entity.destroyer;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;


public class EntityDestroyerAkatsuki extends BasicEntityShipSmall
{

	public boolean isGattai2 = false;  //ridden by Hibiki
	
	
	public EntityDestroyerAkatsuki(World world)
	{
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerAkatsuki);
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
		
		this.isGattai2 = false;
		
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
  			if (this.ticksExisted % 32 == 0)
  			{
  				//update gattai
  				if (this.riddenByEntity instanceof EntityDestroyerHibiki)
  				{
  					this.isGattai2 = true;
  				}
  				else
  				{
  					this.isGattai2 = false;
  				}
  				
  				//add morale when gattai
  				if (this.isGattai2)
  				{
  					int m = this.getStateMinor(ID.M.Morale);
  					
  					if (m < 7000)
  					{
  		  	  			this.setStateMinor(ID.M.Morale, m + 100);
  		  	  		}
  				}
  				
  				if (this.ticksExisted % 128 == 0)
  	  			{
  	  				//add aura to master every 128 ticks
  	  				EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				
  	  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  	  					getStateMinor(ID.M.NumGrudge) > 0 && player != null &&
  	  					getDistanceSqToEntity(player) < 256D)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 300, getStateMinor(ID.M.ShipLevel) / 30));
  	  				}
  	  				
  	  				//try gattai
  	  				tryGattai();
  	  			}
  			}//end every 32 ticks
  		}
  		//client side
  		else
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && !isSitting() && !getStateFlag(ID.F.NoFuel) && this.riddenByEntity == null)
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
  		
  		//sync rotate when gattai
  		if (this.riddenByEntity instanceof EntityDestroyerHibiki)
		{
			((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
			((EntityLivingBase) this.riddenByEntity).prevRenderYawOffset = this.prevRenderYawOffset;
			this.riddenByEntity.rotationYaw = this.rotationYaw;
			this.riddenByEntity.prevRotationYaw = this.prevRotationYaw;
			
			if (this.riddenByEntity.riddenByEntity instanceof EntityDestroyerInazuma)
			{
				((EntityLivingBase) this.riddenByEntity.riddenByEntity).renderYawOffset = this.renderYawOffset;
				((EntityLivingBase) this.riddenByEntity.riddenByEntity).prevRenderYawOffset = this.prevRenderYawOffset;
				this.riddenByEntity.riddenByEntity.rotationYaw = this.rotationYaw;
				this.riddenByEntity.riddenByEntity.prevRotationYaw = this.prevRotationYaw;
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
			if (this.riddenByEntity instanceof EntityDestroyerHibiki)
	    	{
				return -0.1F;
	    	}
			else
			{
				return (double)this.height * 0.15F;
			}
  		}
  		else
  		{
  			if (this.riddenByEntity instanceof EntityDestroyerHibiki)
	    	{
				return (double)this.height * 0.5F;
	    	}
			else
			{
				return (double)this.height * 0.47F;
			}
  		}
	}
  	
  	@Override
  	public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
        	if (this.riddenByEntity instanceof EntityDestroyerHibiki)
        	{
	  			float[] partPos = ParticleHelper.rotateXZByAxis(-0.2F, 0F, (this.renderYawOffset % 360) * Values.N.RAD_MUL, 1F);
        		this.riddenByEntity.setPosition(this.posX + partPos[1], this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + partPos[0]);
        	}
        	else
        	{
        		this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
        	}
        }
    }
  	
  	@Override
  	public boolean canBePushed()
    {
  		if (this.riddenByEntity instanceof BasicEntityShip ||
  			this.ridingEntity instanceof BasicEntityShip)
    	{
  			return false;
    	}
  		else
  		{
  			return !this.isDead;
  		}
    }

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		switch (getStateEmotion(ID.S.State))
		{
		case ID.State.NORMAL:
			setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
		case ID.State.EQUIP00:
			setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
			break;
		case ID.State.EQUIP01:
			setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
			break;
		default:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		}
	}
	
  	/** update flare effect */
  	protected void updateSearchlight()
  	{
  		if (getStateFlag(ID.F.IsMarried) &&
  			getStateFlag(ID.F.UseRingEffect) &&
			getStateMinor(ID.M.NumGrudge) > 0)
  		{
  			int px = MathHelper.floor_double(this.posX);
			int py = (int) this.posY + 1;
			int pz = MathHelper.floor_double(this.posZ);
			float light = this.worldObj.getBlockLightValue(px, py, pz);

  			if (light < 12F)
  			{
				BlockHelper.placeLightBlock(this.worldObj, px, py, pz);
  			}
  			//search light block, renew lifespan
  			else
  			{
  				BlockHelper.updateNearbyLightBlock(this.worldObj, px, py, pz);
  			}
  		}
  	}
  	
  	//檢查是否可以合體
  	public void tryGattai()
  	{
  		//already gattai, return
  		if (getStateFlag(ID.F.NoFuel) || isSitting() || this.isGattai2) return;
  		
  		//not sitting, hp > 50%, not craning, formation = line ahead
  		if (!getStateFlag(ID.F.NoFuel) && !isSitting() && getHealth() > getMaxHealth() * 0.5F &&
  			getStateMinor(ID.M.CraneState) == 0 && getStateMinor(ID.M.FormatType) == 1)
  		{
  			//get nearby ship
  			List<EntityDestroyerHibiki> slist = null;
  			slist = this.worldObj.getEntitiesWithinAABB(EntityDestroyerHibiki.class, this.boundingBox.expand(4D, 2D, 4D));

  			if (slist != null && !slist.isEmpty())
  			{
              	for (EntityDestroyerHibiki s : slist)
              	{
              		if (s != null && EntityHelper.checkSameOwner(this, s) && s.isEntityAlive() &&
              			!s.isRiding() && s.getStateMinor(ID.M.CraneState) == 0 &&
              			s.getStateMinor(ID.M.FormatType) == 1)
              		{
              			applyGattai(s);
              			return;
              		}
              	}
              }//end get ship
  		}//end can gattai
  	}
  	
  	//合體
  	private void applyGattai(EntityDestroyerHibiki ship)
  	{
  		this.isGattai2 = true;
  		ship.isGattai = true;
  		ship.mountEntity(this);
  	}
	

}



