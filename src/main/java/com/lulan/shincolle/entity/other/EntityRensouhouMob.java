package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRensouhouMob extends BasicEntitySummon
{
	
	private BasicEntityShipHostile host2;
	
	
    public EntityRensouhouMob(World world)
    {
		super(world);
		this.setSize(0.3F, 0.7F);
	}
    
    public void initAttrs(IShipAttackBase host, Entity target, int scaleLevel, float... par2)
    {
    	this.host2 = (BasicEntityShipHostile) host;
        this.host = this.host2;
        this.atkTarget = target;
        this.setScaleLevel(scaleLevel);
        
        //設定發射位置
        this.posX = this.host2.posX + rand.nextDouble() * 6D - 3D;
        this.posY = this.host2.posY + 0.5D;
        this.posZ = this.host2.posZ + rand.nextDouble() * 6D - 3D;
        
        //check the place is safe to summon
    	if (!BlockHelper.checkBlockSafe(this.world, (int)posX, (int)posY, (int)posZ))
    	{
    		this.posX = host2.posX;
            this.posY = host2.posY;
            this.posZ = host2.posZ;
    	}
        
    	this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        
        //calc attrs
        this.shipAttrs = Attrs.copyAttrs(host.getAttrs());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.HP, this.host2.getMaxHealth() * 0.15F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_L, host.getAttrs().getAttackDamage());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_H, host.getAttrs().getAttackDamageHeavy());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AL, host.getAttrs().getAttackDamageAir());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AH, host.getAttrs().getAttackDamageAirHeavy());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.DEF, host.getAttrs().getDefense() * 0.5F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.SPD, host.getAttrs().getAttackSpeed() * 0.75F - 0.25F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.MOV, host.getAttrs().getMoveSpeed() * 0.2F + 0.4F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.HIT, host.getAttrs().getAttackRange() + 1F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.CRI, host.getAttrs().getAttrsBuffed(ID.Attrs.CRI) + 0.15F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.DODGE, this.shipAttrs.getAttrsBuffed(ID.Attrs.DODGE) + 0.2F);
		
		//apply attrs to entity
	    getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.HP));
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.MOV));
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
 
		//設定AI
		this.shipNavigator = new ShipPathNavigate(this);
		this.shipMoveHelper = new ShipMoveHelper(this, 60F);
		this.setAIList();
	}

	//setup AI
	protected void setAIList()
	{
		this.clearAITasks();
		this.clearAITargetTasks();
		
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
		this.setEntityTarget(atkTarget);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//client side
		if (this.world.isRemote)
		{
			//有移動時, 產生水花特效
			//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
			double motX = this.posX - this.prevPosX;
			double motZ = this.posZ - this.prevPosZ;
			double parH = this.posY - (int)this.posY;
			
			if (motX != 0 || motZ != 0)
			{
				ParticleHelper.spawnAttackParticleAt(this.posX + motX*1.5D, this.posY, this.posZ + motZ*1.5D, 
						-motX*0.5D, 0D, -motZ*0.5D, (byte)15);
			}
		}
	}

	@Override
	public boolean getStateFlag(int flag)
	{	//hostile mob: for attack and headTile check
		switch (flag)
		{
		default:
			return true;
		case ID.F.HeadTilt:
			return this.headTilt;
		case ID.F.OnSightChase:
			return false;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag)
	{
		this.headTilt = flag;
	}

    @Override
	//light attack
	public boolean attackEntityWithAmmo(Entity target)
    {
    	//host check
    	if (this.host2 == null)
    	{
    		this.setDead();
    		return false;
    	}
    	
		//light ammo -1
  		if (numAmmoLight > 0)
  		{
  			numAmmoLight--;
  			if (numAmmoLight <= 0) this.setDead();
  		}
        
  		//get attack value
  		float atk = getAttackBaseDamage(1, target);
  		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
	    applyParticleAtAttacker(1, target, distVec);

	    //roll miss, cri, dhit, thit
	    atk = CombatHelper.applyCombatRateToDamage(this, target, true, (float)distVec.d, atk);
  		
  		//damage limit on player target
	    atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
  		
  		//check friendly fire
		if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
		
  		//確認攻擊是否成功
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);

	    //if attack success
	    if (isTargetHurt)
	    {
	    	if (!TeamHelper.checkSameOwner(this, target)) BuffHelper.applyBuffOnTarget(target, this.getAttackEffectMap());
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(1, target, distVec);
	        
	        if (ConfigHandler.canFlare)
	        {
	        	this.host2.flareTarget(target);
			}
        }

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
        return false;
	}
	
	@Override
	public float getJumpSpeed()
	{
		return 1.5F;
	}
	
	@Override
	public int getLevel()
	{
		return 150;
	}
	
	@Override
	public boolean useAmmoLight()
	{
		return true;
	}

	@Override
	public boolean useAmmoHeavy()
	{
		return false;
	}
	
	@Override
	public int getPlayerUID()
	{
		return -100;	//-100 for hostile mob
	}

	@Override
	public void setPlayerUID(int uid) {}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.DESTROYER;
	}

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.Rensouhou;
	}

	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.5F, 2.8F);
		break;
		case 2:
			this.setSize(1.1F, 2.1F);
		break;
		case 1:
			this.setSize(0.7F, 1.4F);
		break;
		default:
			this.setSize(0.3F, 0.7F);
		break;
		}
	}

	@Override
	protected void setAttrsWithScaleLevel() {}

	@Override
	protected void returnSummonResource() {}

	@Override
	public float getAttackBaseDamage(int type, Entity target)
	{
		return CombatHelper.modDamageByAdditionAttrs(this.host, target, this.shipAttrs.getAttackDamage(), 0);
	}
	
	
}