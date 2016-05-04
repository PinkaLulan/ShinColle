package com.lulan.shincolle.entity.submarine;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntitySubmRo500 extends BasicEntityShipSmall implements IShipInvisible {
	
	private static float ilevel = 35F;
	

	public EntitySubmRo500(World world) {
		super(world);
		this.setSize(0.6F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.SUBMARINE);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.SubmarineRo500);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.SUBMARINE);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.SS]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.SS]);
		this.ModelPos = new float[] {0F, 10F, 0F, 45F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.28F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	//平常音效新增: garuru
	@Override
	protected String getLivingSound() {
		if(rand.nextInt(8) == 0) {
			return Reference.MOD_ID+":ship-garuru";
		}
		else {
			return super.getLivingSound();
		}
    }
	
	@Override
	public void setAIList() {
		super.setAIList();
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
  		if(!worldObj.isRemote) {
  			if(this.ticksExisted % 128 == 0) {
  				if(getStateFlag(ID.F.UseRingEffect)) {
  					//apply ability to player
  					EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				if(getStateFlag(ID.F.IsMarried) && getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSqToEntity(player) < 256D) {
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 60 + getLevel() * 10));
  	  				}
  				}
  				
  				if(this.ticksExisted % 256 == 0) {
  	  				if(getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 0) {
  	  					//apply ability to ship
  	  					this.addPotionEffect(new PotionEffect(Potion.invisibility.id, 46 + getLevel()));
  	  				}
  	  			}//end 256 ticks
  			}//end 128 ticks
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
		return 2;
	}
  	
  	//潛艇的輕攻擊一樣使用飛彈
  	@Override
  	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
  	public boolean attackEntityWithAmmo(Entity target) {	
  		//get attack value
  		float atk = StateFinal[ID.ATK];
  		
  		//set knockback value (testing)
  		float kbValue = 0.15F;
  		//飛彈是否採用直射
  		boolean isDirect = false;
  		//計算目標距離
  		float tarX = (float)target.posX;	//for miss chance calc
  		float tarY = (float)target.posY;
  		float tarZ = (float)target.posZ;
  		float distX = tarX - (float)this.posX;
  		float distY = tarY - (float)this.posY;
  		float distZ = tarZ - (float)this.posZ;
  		float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
  		float launchPos = (float)posY + height * 0.7F;
          
  		//超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
  		if((distX*distX+distY*distY+distZ*distZ) < 36F) {
  			isDirect = true;
  		}
  		if(getShipDepth() > 0D) {
  			isDirect = true;
  			launchPos = (float)posY;
  		}
  		
  		//發射者煙霧特效 (發射飛機不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  		
  		//experience++
  		addShipExp(ConfigHandler.expGain[1]);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
  		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);
  	
  		//play cannon fire sound at attacker
  		this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.volumeFire, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  		//play entity attack sound
  		if(this.getRNG().nextInt(10) > 7) {
  			this.playSound(getSoundString(ID.Sound.Hit), ConfigHandler.volumeShip, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  		}
          
  		//heavy ammo--
  		if(!decrAmmoNum(0, this.getAmmoConsumption())) {
  			return false;
  		}
          
  		//calc miss chance, miss: add random offset(0~6) to missile target 
  		float missChance = 0.2F + 0.15F * (distSqrt / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.M.ShipLevel];
  		missChance -= EffectEquip[ID.EF_MISS];	//equip miss reduce
  		if(missChance > 0.35F) missChance = 0.35F;	//max miss chance = 30%
         
  		if(this.rand.nextFloat() < missChance) {
			tarX = tarX - 3F + this.rand.nextFloat() * 6F;
			tarY = tarY + this.rand.nextFloat() * 3F;
			tarZ = tarZ - 3F + this.rand.nextFloat() * 6F;
			//spawn miss particle
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
  		}

  		//spawn missile
  		EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
          		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect, 0.08F);
  		this.worldObj.spawnEntityInWorld(missile);
          
        //show emotes
      	applyEmotesReaction(3);
      	
      	if(ConfigHandler.canFlare) {
			flareTarget(target);
		}
      	return true;
  	}
  	
  	@Override
	public double getMountedYOffset() {
  		if(this.isSitting()) {
			if(getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
				return 0F;
  			}
  			else {
  				return 0F;
  			}
  		}
  		else {
  			return (double)this.height * 0.5F;
  		}
	}

	@Override
	public float getInvisibleLevel() {
		return this.ilevel;
	}
	
	@Override
	public void setInvisibleLevel(float level) {
		this.ilevel = level;
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
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
		case ID.State.EQUIP02:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		default:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		}
	}
  	

}




