package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MiscMobRenderer extends RenderLiving {
	
	//textures
	private static final ResourceLocation Airplane = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAircraft.png");
	private static final ResourceLocation AirplaneT = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirplaneT.png");
	private static final ResourceLocation AirplaneTako = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAircraftTakoyaki.png");
	private static final ResourceLocation AirplaneZ = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirplaneZero.png");
	private static final ResourceLocation AirplaneF = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityFloatingFort.png");
	private static final ResourceLocation Rensouhou = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityRensouhou.png");
	private static final ResourceLocation RensouhouS = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityRensouhouS.png");

	protected int mobID;
	
	
	public MiscMobRenderer(ModelBase par1, float par2, int mobID) {
		super(par1, par2);
		this.mobID = mobID;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		switch(mobID)
		{
		case 0:
			return Airplane;
		case 1:
			return AirplaneTako;
		case 2:
			return AirplaneZ;
		case 3:
			return AirplaneT;
		case 4:
			return AirplaneF;
		case 5:
			return Rensouhou;
		case 6:
			return RensouhouS;
		}
		
		return null;
	}

	
}



