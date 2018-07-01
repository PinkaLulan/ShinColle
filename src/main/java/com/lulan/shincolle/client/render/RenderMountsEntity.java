package com.lulan.shincolle.client.render;

import javax.annotation.Nonnull;

import com.lulan.shincolle.client.model.ModelMountAfH;
import com.lulan.shincolle.client.model.ModelMountBaH;
import com.lulan.shincolle.client.model.ModelMountCaH;
import com.lulan.shincolle.client.model.ModelMountCaWD;
import com.lulan.shincolle.client.model.ModelMountHbH;
import com.lulan.shincolle.client.model.ModelMountIsH;
import com.lulan.shincolle.client.model.ModelMountMiH;
import com.lulan.shincolle.client.model.ModelMountSuH;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMountsEntity extends RenderShipEntity
{

	//textures
	public static final ResourceLocation TEX_AirfieldMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountAfH.png");
	public static final ModelBase MD_AirfieldMount = new ModelMountAfH();
	public static final ResourceLocation TEX_BattleshipMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountBaH.png");
	public static final ModelBase MD_BattleshipMount = new ModelMountBaH();
	public static final ResourceLocation TEX_CarrierMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountCaH.png");
	public static final ModelBase MD_CarrierMount = new ModelMountCaH();
	public static final ResourceLocation TEX_CarrierWDMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountCaWD.png");
	public static final ModelBase MD_CarrierWDMount = new ModelMountCaWD();
	public static final ResourceLocation TEX_HarbourMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountHbH.png");
	public static final ModelBase MD_HarbourMount = new ModelMountHbH();
	public static final ResourceLocation TEX_IsloatedMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountIsH.png");
	public static final ModelBase MD_IsloatedMount = new ModelMountIsH();
	public static final ResourceLocation TEX_MidwayMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountMiH.png");
	public static final ModelBase MD_MidwayMount = new ModelMountMiH();
	public static final ResourceLocation TEX_SubmMount = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountSuH.png");
	public static final ModelBase MD_SubmMount = new ModelMountSuH();
	
	//factory
	public static final FactoryDefault FACTORY_MOUNT = new FactoryDefault();
		
	
    public RenderMountsEntity(RenderManager rm)
    {
        super(rm);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity)
    {
		switch (this.textuerID)
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
		case ID.ShipMisc.IsloatedMount:
			return TEX_IsloatedMount;
		case ID.ShipMisc.MidwayMount:
			return TEX_MidwayMount;
		case ID.ShipMisc.SubmMount:
			return TEX_SubmMount;
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
		switch (this.textuerID)
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
		case ID.ShipMisc.IsloatedMount:
			this.mainModel = MD_IsloatedMount;
		break;
		case ID.ShipMisc.MidwayMount:
			this.mainModel = MD_MidwayMount;
		break;
		case ID.ShipMisc.SubmMount:
			this.mainModel = MD_SubmMount;
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
		switch(this.textuerID)
		{
		case ID.ShipMisc.AirfieldMount:
			return new float[] {0.37F, 0.5F, 0.5F, 0.5F, 0.5F};
		case ID.ShipMisc.BattleshipMount:
			return new float[] {0.4F, 0.5F, 0.5F, 0.5F, 0.5F};
		case ID.ShipMisc.CarrierMount:
			return new float[] {0.45F, 1.05F, 1.05F, 1.05F, 1.05F};
		case ID.ShipMisc.CarrierWDMount:
			return new float[] {0.45F, 1.05F, 1.05F, 1.05F, 1.05F};
		case ID.ShipMisc.HarbourMount:
			return new float[] {0F, 0.5F, 0.5F, 0.5F, 0.6F};
		case ID.ShipMisc.IsloatedMount:
			return new float[] {0.45F, 1.05F, 1.05F, 1.05F, 1.05F};
		case ID.ShipMisc.MidwayMount:
			return new float[] {0.45F, 1.05F, 1.05F, 1.05F, 1.05F};
		case ID.ShipMisc.SubmMount:
			return new float[] {0.45F, 1.05F, 1.05F, 1.05F, 1.05F};
		default:
			return new float[] {0.8F, 0.8F, 0.8F, 0.8F, 0.8F};
		}
	}
	
    public static class FactoryDefault implements IRenderFactory<EntityLiving>
    {
        @Override
        public Render<? super EntityLiving> createRenderFor(RenderManager rm)
        {
            return new RenderMountsEntity(rm);
        }
    }
    
    
}