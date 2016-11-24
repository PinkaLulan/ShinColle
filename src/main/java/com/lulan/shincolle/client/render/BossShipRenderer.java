package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;

public class BossShipRenderer extends BasicShipRenderer
{
	
	public BossShipRenderer(ModelBase par1, float par2, int shipClass) {
		super(par1, par2, shipClass);
	}
	
	//get leash height
	protected float[] getLeashHeight() {
		return new float[] {0F, 0F, 0F, 0F, 0F};
	}
	
	//顯示boss血條
	@Override
    protected void preRenderCallback(EntityLivingBase entity, float f1) {
		BossStatus.setBossStatus((IBossDisplayData) entity, true);
	}

}
