package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityRensouhouMob;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityDestroyerShimakazeMob extends BasicEntityShipHostile
{

	public int numRensouhou;
	
	
	public EntityDestroyerShimakazeMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerShimakaze);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
		this.numRensouhou = 10;
		
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(4), false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.4F, 6.75F);
		break;
		case 2:
			this.setSize(1.2F, 4.5F);
		break;
		case 1:
			this.setSize(0.85F, 2.8F);
		break;
		default:
			this.setSize(0.6F, 1.65F);
		break;
		}
	}
	
	@Override
	protected float[] getAttrsMod()
	{                     //HP     ATK    DEF    SPD    MOV    HIT
		return new float[] {0.75F, 0.75F, 0.75F, 1.2F, 0.75F, 0.75F};
	}
	
	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);
	}
	
	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}
	
	//num rensouhou++
	@Override
  	public void onLivingUpdate()
	{
  		super.onLivingUpdate();
          
  		if (!world.isRemote)
  		{
  			//add aura to master every N ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				//add num of rensouhou
  				if (this.numRensouhou < 10) numRensouhou++;
  			}
  		}    
  	}
	
	//招喚連裝砲進行攻擊
  	@Override
  	public boolean attackEntityWithAmmo(Entity target)
  	{
  		//check num rensouhou
  		if (this.numRensouhou <= 0)
  		{
  			return false;
  		}
  		else
  		{
  			this.numRensouhou--;
  		}
  		
  		//play attack sound
		if (this.rand.nextInt(10) > 7)
		{
			this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
        }

        //發射者煙霧特效 (招喚連裝砲不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  		
		//spawn rensouhou
    	EntityRensouhouMob rensoho = new EntityRensouhouMob(this.world);
    	rensoho.initAttrs(this, target);
        this.world.spawnEntity(rensoho);
        
        //show emotes
		applyEmotesReaction(3);
		
        return true;
	}
  	
  	//五連裝酸素魚雷
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{	
		//get attack value
		float atkHeavy = this.atk * 0.9F;
		float kbValue = 0.08F;
		
		//飛彈是否採用直射
		boolean isDirect = false;
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float distX = tarX - (float)this.posX;
		float distY = tarY - (float)this.posY;
		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
        float launchPos = (float)posY + height * 0.5F;
        
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if ((distX*distX+distY*distY+distZ*distZ) < 36F || this.getShipDepth() > 0D)
        {
        	isDirect = true;
        }
	
		//play attack sound
        this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
        this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.8F);
        this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.9F);
        
        //entity sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
        }
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        float missChance = 0.25F;	//max miss chance = 30%
       
        if (this.rand.nextFloat() < missChance)
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }
        
        //發射者煙霧特效 (不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);

        //spawn missile
        EntityAbyssMissile missile1 = new EntityAbyssMissile(this.world, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atkHeavy, kbValue, isDirect, -1F);
        EntityAbyssMissile missile2 = new EntityAbyssMissile(this.world, this, 
        		tarX+3F, tarY+target.height*0.2F, tarZ+6F, launchPos, atkHeavy, kbValue, isDirect, -1F);
        EntityAbyssMissile missile3 = new EntityAbyssMissile(this.world, this, 
        		tarX+3F, tarY+target.height*0.2F, tarZ-6F, launchPos, atkHeavy, kbValue, isDirect, -1F);
        EntityAbyssMissile missile4 = new EntityAbyssMissile(this.world, this, 
        		tarX-3F, tarY+target.height*0.2F, tarZ+6F, launchPos, atkHeavy, kbValue, isDirect, -1F);
        EntityAbyssMissile missile5 = new EntityAbyssMissile(this.world, this, 
        		tarX-3F, tarY+target.height*0.2F, tarZ-6F, launchPos, atkHeavy, kbValue, isDirect, -1F);
        
        this.world.spawnEntity(missile1);
        this.world.spawnEntity(missile2);
        this.world.spawnEntity(missile3);
        this.world.spawnEntity(missile4);
        this.world.spawnEntity(missile5);
        
        //show emotes
		applyEmotesReaction(3);
		
        return true;
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
		case ID.EF_CRI:
			return 0.15F;
		case ID.EF_AA:  //DD vs AA,ASM effect
		case ID.EF_ASM:
			return this.atk * 0.5F;
		default:
			return 0F;
		}
	}
  	

}
