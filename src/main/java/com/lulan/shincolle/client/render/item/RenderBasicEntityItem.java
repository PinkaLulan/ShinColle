package com.lulan.shincolle.client.render.item;

import com.lulan.shincolle.client.model.ModelBasicEntityItem;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBasicEntityItem extends Render
{
    
	public static final Factory FACTORY = new Factory();
	private static final ResourceLocation entityTexture = new ResourceLocation(Reference.TEXTURES_ENTITY+"ModelBasicEntityItem.png");
	private ModelBasicEntityItem model = new ModelBasicEntityItem();
	
	
    public RenderBasicEntityItem(RenderManager render)
    {
        super(render);
        this.shadowSize = 0F;
    }
    
    @Override
	protected ResourceLocation getEntityTexture(Entity entity)
    {
		return entityTexture;
	}

    public void doRender(BasicEntityItem entity, double x, double y, double z, float yaw, float ptick)
    {

    	//bind texture
        this.bindEntityTexture(entity);  		//call getEntityTexture
        
        //render start
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y + 0.1F, z);

        //parm: entity, f依移動速度, f1依移動速度, f2遞增, f3左右角度, f4上下角度, f5(scale)
        this.model.render(entity, entity.ticksExisted + ptick, 0F, 0F, 0F, 0F, 0.0625F);

        GlStateManager.popMatrix();
    }

    //傳入entity的都轉成abyssmissile
    @Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float ptick)
    {
    	this.doRender((BasicEntityItem)entity, x, y, z, yaw, ptick);
    }
    
    /** render factory for register */
    public static class Factory implements IRenderFactory<BasicEntityItem>
    {

        @Override
        public Render<? super BasicEntityItem> createRenderFor(RenderManager manager)
        {
            return new RenderBasicEntityItem(manager);
        }

    }
    
    
}
