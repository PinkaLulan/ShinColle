package com.lulan.shincolle.entity.hime;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.IShipMount;
import com.lulan.shincolle.entity.mounts.EntityMountHbH;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityHarbourHime extends BasicEntityShipLarge {
	
	public EntityHarbourHime(World world) {
		super(world);
		this.setSize(0.8F, 1.7F);
		this.ShipType = ID.ShipType.HIME;
		this.ShipID = ID.S_HarbourHime;
		this.ModelPos = new float[] {-6F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		launchHeight = this.height * 0.7F;
	}
	
	@Override
	public float getEyeHeight() {
		return this.height;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 2;
	}
	
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));		   //0100
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));			   //0011
	}
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		//server side
  		if(!worldObj.isRemote) {
  			//飛行場特殊能力
        	if(this.ticksExisted % 100 == 0) {
        		//1: 增強被動回血
        		if(getStateMinor(ID.N.NumGrudge) > 0 && this.getHealth() < this.getMaxHealth()) {
        			this.setHealth(this.getHealth() + this.getMaxHealth() * 0.03F);
        		}
        		
        		//2: 結婚後, 周圍某一目標回血, 包括玩家, 回血目標依等級提昇
				if(getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.N.NumGrudge) > 0) {
					//判定bounding box內是否有可以回血的目標
					int healCount = (int)(this.getLevel() / 50) + 1;
		            EntityLivingBase hitEntity = null;
		            List hitList = null;
		            hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(12D, 12D, 12D));
		           
		            for(int i = 0; i < hitList.size(); i++) {
		            	//補血名額沒了, break
		            	if(healCount <= 0) break;
		            	
		            	hitEntity = (EntityLivingBase) hitList.get(i);
		            	
		            	//抓可以補血的目標, 不包含自己
		            	if(hitEntity != this && hitEntity.getHealth() / hitEntity.getMaxHealth() < 0.96F) {
	            			if(hitEntity instanceof EntityPlayer) {
	            				hitEntity.heal(1F + this.getLevel() * 0.05F);
		            			healCount--;
		            		}
		            		else if(hitEntity instanceof BasicEntityShip) {
		            			hitEntity.heal(1F + hitEntity.getMaxHealth() * 0.05F + this.getLevel() * 0.1F);
		            			healCount--;
			            	}
		            	}
		            }
				}//end heal ability
        	}
  		}//end server side
  			
  		super.onLivingUpdate();
  	}
	
	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				//切換裝備顯示
				if(player.isSneaking()) {
					switch(getStateEmotion(ID.S.State2)) {
					case ID.State.NORMAL_2:
						setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
						break;
					case ID.State.EQUIP00_2:
						setStateEmotion(ID.S.State2, ID.State.EQUIP01_2, true);
						break;
					case ID.State.EQUIP01_2:
						setStateEmotion(ID.S.State2, ID.State.EQUIP02_2, true);
						break;
					case ID.State.EQUIP02_2:
						setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
						break;
					default:
						setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
						break;
					}
				}
				//切換是否騎乘座騎
				else {
					switch(getStateEmotion(ID.S.State)) {
					case ID.State.NORMAL:
						setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
						break;
					case ID.State.EQUIP00:
						setStateEmotion(ID.S.State, ID.State.NORMAL, true);
						this.setPositionAndUpdate(posX, posY + 2D, posZ);
						break;
					default:
						setStateEmotion(ID.S.State, ID.State.NORMAL, true);
						break;
					}
				}
				return true;
			}
		}
		
		return super.interact(player);
  	}
	
	@Override
	public int getKaitaiType() {
		return 1;
	}
	
	//修改煙霧特效 & 檢查是否riding
  	@Override
  	public boolean attackEntityWithAmmo(Entity target) {
  		//check riding
  		if(this.isRiding()) {
  			//stop attack if riding ship mount
  			if(this.ridingEntity instanceof IShipMount) {
  				return false;
  			}
  		}
  		
  		return super.attackEntityWithAmmo(target);
	}
  	
  	//檢查是否riding
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
  		//check riding
  		if(this.isRiding()) {
  			//stop attack if riding ship mount
  			if(this.ridingEntity instanceof IShipMount) {
  				return false;
  			}
  		}
  		
  		return this.attackEntityWithSpecialAmmo(target);
  	}
  	
  	//special heavy attack, call by mounts
  	/**RAILGUN
  	 * hight speed, low damage, direct shot only
  	 */
  	public boolean attackEntityWithSpecialAmmo(Entity target) {	
  		//get attack value
  		float atk = StateFinal[ID.ATK_H];
  		
  		//set knockback value (testing)
  		float kbValue = 0.15F;

  		//計算目標距離
  		float tarX = (float)target.posX;	//for miss chance calc
  		float tarY = (float)target.posY;
  		float tarZ = (float)target.posZ;
  		float distX = tarX - (float)this.posX;
  		float distY = tarY - (float)this.posY;
  		float distZ = tarZ - (float)this.posZ;
  		float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
  		float launchPos = (float)posY - 1F;
  		
  		//experience++
  		addShipExp(16);
  		
  		//grudge--
  		decrGrudgeNum(1);
  	
  		//play cannon fire sound at attacker
  		this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  		//play entity attack sound
  		if(this.getRNG().nextInt(10) > 7) {
  			this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  		}
          
  		//heavy ammo -1
  		if(!decrAmmoNum(1)) {	//not enough ammo
  			atk = atk * 0.125F;	//reduce damage to 12.5%
  		}
          
  		//calc miss chance, miss: add random offset(0~6) to missile target 
  		float missChance = 0.2F + 0.15F * (distSqrt / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.N.ShipLevel];
  		missChance -= EffectEquip[ID.EF_MISS];	//equip miss reduce
  		if(missChance > 0.35F) missChance = 0.35F;	//max miss chance = 30%
         
  		if(this.rand.nextFloat() < missChance) {
          	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
          	tarY = tarY + this.rand.nextFloat() * 5F;
          	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
          	//spawn miss particle
          	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
          	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
  		}
          
  		//spawn missile
  		EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
          		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, true, 0.15F);
  		this.worldObj.spawnEntityInWorld(missile);
          
  		return true;
  	}
	
  	//BUG: NOT WORKING
  	@Override
	public boolean canBePushed() {
        return this.ridingEntity == null;
    }
  	
  	//true if use mounts
  	@Override
  	public boolean canSummonMounts() {
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity() {
		return new EntityMountHbH(worldObj, this);
	}
  	
  	@Override
  	public float[] getModelPos() {
  		if(this.isRiding()) {
  			ModelPos[1] = -25F;
  		}
  		else {
  			ModelPos[1] = 15F;
  		}
  		
		return ModelPos;
	}


}




