package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipSummonAttack;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Attrs;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRensouhou extends BasicEntitySummon
{
	
	private BasicEntityShip host2;
	
	
    public EntityRensouhou(World world)
    {
		super(world);
		this.setSize(0.3F, 0.7F);
	}
    
    //init attrs
    @Override
    public void initAttrs(IShipAttackBase host, Entity target, int scaleLevel, float... par2)
    {
    	this.host2 = (BasicEntityShip) host;
        this.host = host;
        this.atkTarget = target;
        this.setScaleLevel(scaleLevel);
        
        //設定發射位置
        this.posX = this.host2.posX + rand.nextDouble() * 3D - 1.5D;
        this.posY = this.host2.posY + 0D;
        this.posZ = this.host2.posZ + rand.nextDouble() * 3D - 1.5D;

    	//check the place is safe to summon
    	if (!BlockHelper.checkBlockSafe(this.world, (int)posX, (int)posY, (int)posZ))
    	{
    		this.posX = this.host2.posX;
            this.posY = this.host2.posY;
            this.posZ = this.host2.posZ;
    	}

        this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        
        //calc attrs
        this.shipAttrs = Attrs.copyAttrs(host.getAttrs());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.HP, host.getLevel() + host.getAttrs().getAttrsBuffed(ID.Attrs.HP) * 0.2F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_L, host.getAttrs().getAttackDamage());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_H, host.getAttrs().getAttackDamageHeavy());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AL, host.getAttrs().getAttackDamageAir());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.ATK_AH, host.getAttrs().getAttackDamageAirHeavy());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.DEF, host.getAttrs().getDefense() * 0.5F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.SPD, host.getAttrs().getAttackSpeed() * 0.75F - 0.25F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.MOV, host.getAttrs().getMoveSpeed() * 0.2F + 0.4F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.HIT, host.getAttrs().getAttackRange() + 1F);
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
    @Override
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
	{
		switch (flag)
		{
		default:
			return true;
		case ID.F.OnSightChase:
			if(host != null) return host.getStateFlag(flag);
			return false;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag)
	{
		this.headTilt = flag;
	}
	
	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		return CombatHelper.modDamageByAdditionAttrs(this.host, target, this.shipAttrs.getAttackDamage(), 0);
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
	    	if (!TeamHelper.checkSameOwner(this.getHostEntity(), target)) BuffHelper.applyBuffOnTarget(target, this.getAttackEffectMap());
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(1, target, distVec);
	        
	        if (ConfigHandler.canFlare) this.host2.flareTarget(target);
        }

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		return false;
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
		if (this.host != null) return this.host.getPlayerUID();
		return -1;
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
	protected void returnSummonResource()
	{
		//召喚時消耗4 light ammo, 連裝砲可6次攻擊, 回收時次數要-2
		this.numAmmoLight -= 2;
		if (this.numAmmoLight < 0) this.numAmmoLight = 0;
		
		//連裝砲數量+1
		int numR = ((IShipSummonAttack) this.host).getNumServant();
		if (numR < 6) ((IShipSummonAttack) this.host).setNumServant(numR + 1);
		
		//歸還彈藥
		this.host.setAmmoLight(this.host.getAmmoLight() + this.getAmmoLight() * ((BasicEntityShip)this.host).getAmmoConsumption());
	}

	@Override
	protected void setSizeWithScaleLevel() {}
	
	@Override
	protected void setAttrsWithScaleLevel() {}
	
	@Override
	public float getJumpSpeed()
	{
		return 1.5F;
	}
	
	
}