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

public class RenderBasicEntity extends Render<Entity>
{

	//texture
	private static final ResourceLocation TEX_AM = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAbyssMissile.png");
	
	//factory
	public static final FactoryDefault FACTORY_DEFAULT = new FactoryDefault();
	public static final FactoryBoss FACTORY_BOSS = new FactoryBoss();
	
	//parm
	private float entityScale;	//模型大小
	
	//model
	private static ModelBase modelAbyssMissile = new ModelAbyssMissile();
	
	
    public RenderBasicEntity(RenderManager rm)
    {
        this(rm, 1F);
    }

    public RenderBasicEntity(RenderManager rm, float scale)
    {
        super(rm);
        this.entityScale = scale;
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull Entity entity)
    {
    	if (entity instanceof IShipCustomTexture)
    	{
    		switch (((IShipCustomTexture) entity).getTextureID())
    		{
    		case ID.ShipMisc.AbyssalMissile:
    			return TEX_AM;
    		}//end switch
    	}
    	
    	//default texture
    	return TEX_AM;
    }
    
    @Nonnull
    private ModelBase getModel(@Nonnull Entity entity)
    {
    	if (entity instanceof IShipCustomTexture)
    	{
    		switch (((IShipCustomTexture) entity).getTextureID())
    		{
    		case ID.ShipMisc.AbyssalMissile:
    			return modelAbyssMissile;
    		}//end switch
    	}
    	
    	//default texture
    	return modelAbyssMissile;
    }
    
    @Override
    public void doRender(Entity entity, double x, double y, double z, float yaw, float parTick)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + 0.3F, (float)z);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(this.entityScale, this.entityScale, this.entityScale);
        this.getModel(entity).render(entity, parTick, 0F, 0F, entity.rotationYaw, entity.rotationPitch, 0.0625F);
        GlStateManager.disableRescaleNormal();
        
        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, yaw, parTick);
    }

    //default factory: scale = 1F
    public static class FactoryDefault implements IRenderFactory<Entity>
    {
        @Override
        public Render<? super Entity> createRenderFor(RenderManager rm)
        {
            return new RenderBasicEntity(rm);
        }
    }
    
    //boss factory: scale = 2.5F
    public static class FactoryBoss implements IRenderFactory<Entity>
    {
        @Override
        public Render<? super Entity> createRenderFor(RenderManager rm)
        {
            return new RenderBasicEntity(rm, 2.5F);
        }
    }
    
    
}
