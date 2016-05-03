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

public class EntityDestroyerNi extends BasicEntityShipSmall {

	public EntityDestroyerNi(World world) {
		super(world);
		this.setSize(0.9F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerNI);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 0F, 0F, 25F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		
		//higher step
		stepHeight = 2F;
		
		this.initTypeModify();
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.5F;
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
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
  		if(!worldObj.isRemote) {
  			//add aura to master every 100 ticks
  			if(this.ticksExisted % 128 == 0) {
  				EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  				if(getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSqToEntity(player) < 256D) {
  					//potion effect: id, time, level
  	  	  			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 300, getStateMinor(ID.M.ShipLevel) / 50));
  				}
  			}
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
		
		super.interact(player);
		return false;
  	}
  	
  	@Override
	public int getKaitaiType() {
		return 0;
	}
  	
  	@Override
	public double getMountedYOffset() {
  		if(this.isSitting()) {
  			return (double)this.height * 0.2F;
  		}
  		else {
  			return (double)this.height * 0.8F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		switch(getStateEmotion(ID.S.State)) {
		case ID.State.NORMAL:
			setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
		case ID.State.EQUIP00:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		default:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		}
	}
  	

}



