package com.lulan.shincolle.client.render;

import javax.annotation.Nonnull;

import com.lulan.shincolle.client.model.ModelAbyssMissile;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderMiscEntity extends Render<Entity>
{

	//texture & model
	private static final ResourceLocation TEX_AM = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAbyssMissile.png");
	private static final ModelBase MD_AM = new ModelAbyssMissile();
	
	//factory
	public static final FactoryDefault FACTORY_DEFAULT = new FactoryDefault();
	
	//parm
	protected int mobID = 0;
	protected boolean initModel = true;
	protected ModelBase mainModel;
	
	
    public RenderMiscEntity(RenderManager rm)
    {
        super(rm);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull Entity entity)
    {
		switch (this.mobID)
		{
		case ID.ShipMisc.AbyssalMissile:
			return TEX_AM;
		default:	//default texture
			return TEX_AM;
		}//end switch
    }
    
    protected void setModel()
    {
		switch (this.mobID)
		{
		case ID.ShipMisc.Invisible:
			this.mainModel = null;
		break;
		case ID.ShipMisc.AbyssalMissile:
			this.mainModel = MD_AM;
		break;
		default:	//default texture
			this.mainModel = MD_AM;
		break;
		}//end switch
    }
    
    /** set shadow size */
    protected void setShadowSize()
    {
		switch (this.mobID)
		{
		default:	//default texture
			this.shadowSize = 0F;
		break;
		}
	}
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float parTick)
    {
    	//model init
    	if (this.initModel)
    	{
    		this.mobID = ((IShipCustomTexture) entity).getTextureID();
    		this.initModel = false;
    		setModel();
    	}
    	
    	//for invisible model
    	if (this.mainModel == null) return;
    	
    	//set shadow size
    	setShadowSize();
    	
    	//bind texture
    	this.bindEntityTexture(entity);
    	
    	//start render
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + 0.3F, (float)z);
        
        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        GlStateManager.enableRescaleNormal();
        this.mainModel.render(entity, parTick, 0F, 0F, entity.rotationYaw, entity.rotationPitch, 0.0625F);
        GlStateManager.disableRescaleNormal();
        
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, yaw, parTick);
    }

    //default factory
    public static class FactoryDefault implements IRenderFactory<Entity>
    {
        @Override
        public Render<? super Entity> createRenderFor(RenderManager rm)
        {
            return new RenderMiscEntity(rm);
        }
    }
    
    
}