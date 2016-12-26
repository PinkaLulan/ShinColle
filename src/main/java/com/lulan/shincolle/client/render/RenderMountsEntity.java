package com.lulan.shincolle.client.render;

import javax.annotation.Nonnull;

import com.lulan.shincolle.client.model.ModelMountAfH;
import com.lulan.shincolle.client.model.ModelMountBaH;
import com.lulan.shincolle.client.model.ModelMountCaH;
import com.lulan.shincolle.client.model.ModelMountCaWD;
import com.lulan.shincolle.client.model.ModelMountHbH;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderMountsEntity extends RenderShipEntity
{

	//textures
	private static final ResourceLocation TEX_AirfieldMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountAfH.png");
	private static final ModelBase MD_AirfieldMount = new ModelMountAfH();
	private static final ResourceLocation TEX_BattleshipMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountBaH.png");
	private static final ModelBase MD_BattleshipMount = new ModelMountBaH();
	private static final ResourceLocation TEX_CarrierMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountCaH.png");
	private static final ModelBase MD_CarrierMount = new ModelMountCaH();
	private static final ResourceLocation TEX_CarrierWDMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountCaWD.png");
	private static final ModelBase MD_CarrierWDMount = new ModelMountCaWD();
	private static final ResourceLocation TEX_HarbourMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountHbH.png");
	private static final ModelBase MD_HarbourMount = new ModelMountHbH();
	
	//factory
	public static final FactoryDefault FACTORY_DEFAULT = new FactoryDefault();
	
	
    public RenderMountsEntity(RenderManager rm)
    {
        super(rm);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity)
    {
		switch (this.shipClass)
		{
		case ID.ShipMisc.AirfieldMount:
			return TEX_AirfieldMount;
		case ID.ShipMisc.BattleshipMount:
			return TEX_BattleshipMount;
		case ID.ShipMisc.CarrierMount:
			return TEX_CarrierMount;
		case ID.ShipMisc.CarrierWDMount:
			return TEX_CarrierWDMount;
		case ID.ShipMisc.HarbourMount:
			return TEX_HarbourMount;
		default:	//default texture
			return TEX_AirfieldMount;
		}//end switch
    }
    
    /**
     * set mainModel, shadowSize, scale
     */
    @Override
    protected void setModel()
    {
		switch (this.shipClass)
		{
		case ID.ShipMisc.AirfieldMount:
			this.mainModel = MD_AirfieldMount;
		break;
		case ID.ShipMisc.BattleshipMount:
			this.mainModel = MD_BattleshipMount;
		break;
		case ID.ShipMisc.CarrierMount:
			this.mainModel = MD_CarrierMount;
		break;
		case ID.ShipMisc.CarrierWDMount:
			this.mainModel = MD_CarrierWDMount;
		break;
		case ID.ShipMisc.HarbourMount:
			this.mainModel = MD_HarbourMount;
		break;
		default:	//default model
			this.mainModel = MD_AirfieldMount;
		break;
		}//end switch
    }
    
    @Override
    protected void setShadowSize()
    {
    	this.shadowSize = 1.5F;
    }
    
	//get leash height
	@Override
	protected float[] getLeashHeight()
	{
		switch(this.shipClass)
		{
		case ID.ShipMisc.AirfieldMount:
			return new float[] {0.1F, 0.5F, 0.5F, 0.5F, 0.5F};
		case ID.ShipMisc.BattleshipMount:
			return new float[] {0.4F, 0.5F, 0.5F, 0.5F, 0.5F};
		case ID.ShipMisc.CarrierMount:
			return new float[] {0.3F, 0.4F, 0.4F, 0.4F, 0.4F};
		case ID.ShipMisc.CarrierWDMount:
			return new float[] {0.3F, 0.4F, 0.4F, 0.4F, 0.4F};
		case ID.ShipMisc.HarbourMount:
			return new float[] {0F, 0.5F, 0.5F, 0.5F, 0.5F};
		default:
			return new float[] {0.8F, 0.8F, 0.8F, 0.8F, 0.8F};
		}
	}
		

}
