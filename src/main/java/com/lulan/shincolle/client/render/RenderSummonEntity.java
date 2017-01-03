package com.lulan.shincolle.client.render;

import javax.annotation.Nonnull;

import com.lulan.shincolle.client.model.ModelAirplane;
import com.lulan.shincolle.client.model.ModelAirplaneT;
import com.lulan.shincolle.client.model.ModelAirplaneZero;
import com.lulan.shincolle.client.model.ModelFloatingFort;
import com.lulan.shincolle.client.model.ModelRensouhou;
import com.lulan.shincolle.client.model.ModelRensouhouS;
import com.lulan.shincolle.client.model.ModelTakoyaki;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSummonEntity extends RenderBasic
{

	//textures
	private static final ResourceLocation TEX_Airplane = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAircraft.png");
	private static ModelBase MD_Airplane = new ModelAirplane();
	private static final ResourceLocation TEX_AirplaneT = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirplaneT.png");
	private static ModelBase MD_AirplaneT = new ModelAirplaneT();
	private static final ResourceLocation TEX_AirplaneTako = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAircraftTakoyaki.png");
	private static ModelBase MD_AirplaneTako = new ModelTakoyaki();
	private static final ResourceLocation TEX_AirplaneZero = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirplaneZero.png");
	private static ModelBase MD_AirplaneZero = new ModelAirplaneZero();
	private static final ResourceLocation TEX_FloatingFort = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityFloatingFort.png");
	private static ModelBase MD_FloatingFort = new ModelFloatingFort();
	private static final ResourceLocation TEX_Rensouhou = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityRensouhou.png");
	private static ModelBase MD_Rensouhou = new ModelRensouhou();
	private static final ResourceLocation TEX_RensouhouS = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityRensouhouS.png");
	private static ModelBase MD_RensouhouS = new ModelRensouhouS();

	//factory
	public static final FactoryDefault FACTORY_SUMMON = new FactoryDefault();
	
	
    public RenderSummonEntity(RenderManager rm)
    {
        super(rm);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity)
    {
		switch (this.shipClass)
		{
		case ID.ShipMisc.Airplane:
			return TEX_Airplane;
		case ID.ShipMisc.AirplaneT:
			return TEX_AirplaneT;
		case ID.ShipMisc.AirplaneTako:
			return TEX_AirplaneTako;
		case ID.ShipMisc.AirplaneZero:
			return TEX_AirplaneZero;
		case ID.ShipMisc.FloatingFort:
			return TEX_FloatingFort;
		case ID.ShipMisc.Rensouhou:
			return TEX_Rensouhou;
		case ID.ShipMisc.RensouhouS:
			return TEX_RensouhouS;
		default:	//default texture
			return TEX_Rensouhou;
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
		case ID.ShipMisc.Airplane:
			this.mainModel = MD_Airplane;
		break;
		case ID.ShipMisc.AirplaneT:
			this.mainModel = MD_AirplaneT;
		break;
		case ID.ShipMisc.AirplaneTako:
			this.mainModel = MD_AirplaneTako;
		break;
		case ID.ShipMisc.AirplaneZero:
			this.mainModel = MD_AirplaneZero;
		break;
		case ID.ShipMisc.FloatingFort:
			this.mainModel = MD_FloatingFort;
		break;
		case ID.ShipMisc.Rensouhou:
			this.mainModel = MD_Rensouhou;
		break;
		case ID.ShipMisc.RensouhouS:
			this.mainModel = MD_RensouhouS;
		break;
		default:	//default model
			this.mainModel = MD_Rensouhou;
		break;
		}//end switch
    }
    
    /** set shadow size */
    @Override
    protected void setShadowSize()
    {
		switch (this.shipClass)
		{
		case ID.ShipMisc.Airplane:
		case ID.ShipMisc.AirplaneZero:
		case ID.ShipMisc.FloatingFort:
			this.shadowSize = 0.5F;
		break;
		case ID.ShipMisc.AirplaneT:
		case ID.ShipMisc.AirplaneTako:
		case ID.ShipMisc.Rensouhou:
		case ID.ShipMisc.RensouhouS:
			this.shadowSize = 0.7F;
		break;
		default:	//default model
			this.shadowSize = 1F;
		break;
		}//end switch
    }
    
    public static class FactoryDefault implements IRenderFactory<EntityLiving>
    {
        @Override
        public Render<? super EntityLiving> createRenderFor(RenderManager rm)
        {
            return new RenderSummonEntity(rm);
        }
    }
    
    
}

