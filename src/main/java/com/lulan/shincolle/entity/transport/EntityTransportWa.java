package com.lulan.shincolle.entity.transport;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

public class EntityTransportWa extends BasicEntityShipSmall {

	public EntityTransportWa(World world) {
		super(world);
		this.setSize(0.7F, 1.53F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.TRANSPORT);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.TransportWA);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.UNDEFINED);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.AP]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.AP]);
		this.ModelPos = new float[] {-3F, 10F, 0F, 45F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		
		this.initTypeModify();
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.45F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//pick up item AI
		this.tasks.addTask(5, new EntityAIShipPickItem(this, 8F));	//0111
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
  		//client side
  		if(worldObj.isRemote) {
  			if(this.ticksExisted % 128 == 0) {
  				//show hungry emotes
  				if(this.rand.nextInt(4) == 0 && !this.getStateFlag(ID.F.NoFuel)) {
  					this.applyParticleEmotion(2);
  				}
  			}
  		}
  	}
  	
	//increase inventory size
  	@Override
  	public void calcShipAttributes() {
  		StateMinor[ID.M.InvSize] = 2;
  		
  		super.calcShipAttributes();	
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
  			return (double)this.height * 0.37F;
  		}
  		else {
  			return (double)this.height * 0.5F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		if(isSneaking) {
			switch(getStateEmotion(ID.S.State2)) {
			case ID.State.NORMAL_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
				break;
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
				break;
			}
		}
		else {
			switch(getStateEmotion(ID.S.State)) {
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
	}


}



