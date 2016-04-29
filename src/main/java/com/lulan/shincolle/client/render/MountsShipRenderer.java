package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

public class MountsShipRenderer extends BasicShipRenderer
{

	//textures
	private static final ResourceLocation AfH = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountAfH.png");
	private static final ResourceLocation BaH = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountBaH.png");
	private static final ResourceLocation CaWD = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountCaWD.png");
	private static final ResourceLocation HbH = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountHbH.png");
	
	
	public MountsShipRenderer(ModelBase model, float scale, int shipClass)
	{
		super(model, scale, shipClass);
		
	}
	
	//get texture from entity
	@Override
	protected ResourceLocation getEntityTexture(Entity host)
	{
		switch(this.shipClass)
		{
		case 0:
			return AfH;
		case 1:
			return BaH;
		case 2:
			return CaWD;
		case 3:
			return HbH;
		}
		
		return null;
	}
	
	//get leash height
	@Override
	protected float[] getLeashHeight()
	{
		switch(this.shipClass)
		{
		case 0:
			return new float[] {0.1F, 0.5F, 0.5F, 0.5F, 0.5F};
		case 1:
			return new float[] {0.4F, 0.5F, 0.5F, 0.5F, 0.5F};
		case 2:
			return new float[] {0.3F, 0.4F, 0.4F, 0.4F, 0.4F};
		case 3:
			return new float[] {0F, 0.5F, 0.5F, 0.5F, 0.5F};
		}
		
		return new float[] {0.1F, 0F, 0F, 0F, 0F};
	}
		

}
