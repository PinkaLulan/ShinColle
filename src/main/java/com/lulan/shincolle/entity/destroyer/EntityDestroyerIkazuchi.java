package com.lulan.shincolle.entity.destroyer;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;


public class EntityDestroyerIkazuchi extends BasicEntityShipSmall
{

	public boolean isGattai = false;
	
	
	public EntityDestroyerIkazuchi(World world)
	{
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerIkazuchi);
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
		
		//gattai
		this.isGattai = false;
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.4F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	@Override
	public void setAIList() {
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
  				if (this.ridingEntity instanceof EntityDestroyerInazuma)
  				{
  					this.isGattai = true;
  				}
  				else
  				{
  					this.isGattai = false;
  				}
  				
  				//add morale when gattai
  				if (this.isGattai)
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
  	  					getStateMinor(ID.M.NumGrudge) > 0 && getStateMinor(ID.M.CraneState) == 0 &&
  	  					player != null && getDistanceSqToEntity(player) < 256D)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 300, getStateMinor(ID.M.ShipLevel) / 50));
  	  				}
  	  				
  	  				//try gattai
  	  				tryGattai();
  	  			}
  			}
  		}
  		//client side
  		else
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && !isSitting() && !getStateFlag(ID.F.NoFuel))
  				{
  					double smokeY = posY + 1.4D;
  					
  					if (this.ridingEntity != null)
					{
  						smokeY -= 0.2F;
					}
  					
  					//計算煙霧位置
  	  				float[] partPos = ParticleHelper.rotateXZByAxis(-0.42F, 0F, (this.renderYawOffset % 360) / 57.2957F, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}	
  			}
  		}
  		
		//sync rotate when gattai
  		if (this.ridingEntity instanceof EntityDestroyerInazuma)
		{
  			if (this.ridingEntity.ridingEntity instanceof EntityDestroyerHibiki)
  			{
  				if (this.ridingEntity.ridingEntity.ridingEntity instanceof EntityDestroyerAkatsuki)
  	  			{
  					((EntityLivingBase) this.ridingEntity.ridingEntity).renderYawOffset = ((EntityLivingBase) this.ridingEntity.ridingEntity.ridingEntity).renderYawOffset;
  	  				((EntityLivingBase) this.ridingEntity.ridingEntity).prevRenderYawOffset = ((EntityLivingBase) this.ridingEntity.ridingEntity.ridingEntity).prevRenderYawOffset;
  	  				this.ridingEntity.ridingEntity.rotationYaw = this.ridingEntity.ridingEntity.ridingEntity.rotationYaw;
  	  				this.ridingEntity.ridingEntity.prevRotationYaw = this.ridingEntity.ridingEntity.ridingEntity.prevRotationYaw;
  	  			}
  				
  				((EntityLivingBase) this.ridingEntity).renderYawOffset = ((EntityLivingBase) this.ridingEntity.ridingEntity).renderYawOffset;
  				((EntityLivingBase) this.ridingEntity).prevRenderYawOffset = ((EntityLivingBase) this.ridingEntity.ridingEntity).prevRenderYawOffset;
  				this.ridingEntity.rotationYaw = this.ridingEntity.ridingEntity.rotationYaw;
  				this.ridingEntity.prevRotationYaw = this.ridingEntity.ridingEntity.prevRotationYaw;
  			}
  			
  			this.renderYawOffset = ((EntityLivingBase) this.ridingEntity).renderYawOffset;
			this.prevRenderYawOffset = ((EntityLivingBase) this.ridingEntity).prevRenderYawOffset;
			this.rotationYaw = this.ridingEntity.rotationYaw;
			this.prevRotationYaw = this.ridingEntity.prevRotationYaw;
		}
  	}
  	
  	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
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
  		if(this.isSitting()) {
  			return (double)this.height * 0.15F;
  		}
  		else {
  			return (double)this.height * 0.47F;
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
	
  	@Override
  	protected void updateFuelState(boolean nofuel)
	{
  		if (this.isGattai && nofuel)
  		{
  			this.isGattai = false;

  			if (this.ridingEntity instanceof EntityDestroyerInazuma)
  			{
  				((EntityDestroyerInazuma) this.ridingEntity).isGattai2 = false;
  				this.mountEntity(null);
  			}
  		}
  		
  		super.updateFuelState(nofuel);
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk)
	{
		boolean dd = super.attackEntityFrom(attacker, atk);
		
		if (dd)
		{
			//cancel gattai
			if (this.ridingEntity instanceof EntityDestroyerInazuma)
			{
				this.isGattai = false;
				this.mountEntity(null);
			}
		}
		
		return dd;
	}
	
	//檢查是否可以合體
	public void tryGattai()
	{
		//stop gattai if no fuel
		if (getStateFlag(ID.F.NoFuel))
		{
			//stop gattai
			if (ridingEntity instanceof EntityDestroyerInazuma)
			{
				//stop gattai
				this.isGattai = false;
				mountEntity(null);
			}
			
			return;
		}
		
		//already gattai, return
  		if (this.isGattai || isSitting() || this.isRiding()) return;
		
		//not sitting, hp > 50%, not craning
		if (!getStateFlag(ID.F.NoFuel) && !isSitting() && getHealth() > getMaxHealth() * 0.5F &&
			getStateMinor(ID.M.CraneState) == 0)
		{
			//get nearby ship
            List<EntityDestroyerInazuma> slist = null;
            slist = this.worldObj.getEntitiesWithinAABB(EntityDestroyerInazuma.class, this.boundingBox.expand(4D, 4D, 4D));

            if (slist != null && !slist.isEmpty())
            {
            	for (EntityDestroyerInazuma s : slist)
            	{
            		if (s != null && EntityHelper.checkSameOwner(this, s) && s.isEntityAlive() &&
                  		s.riddenByEntity == null && s.getStateMinor(ID.M.CraneState) == 0)
            		{
        				applyGattai(s);
        				return;
            		}
            	}
            }//end get ship
		}//end can gattai
	}
	
	//合體
  	private void applyGattai(EntityDestroyerInazuma ship)
  	{
  		this.isGattai = true;
  		ship.isGattai2 = true;
  		this.mountEntity(ship);
  	}
  	

}
