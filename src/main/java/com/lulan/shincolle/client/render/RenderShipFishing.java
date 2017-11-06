package com.lulan.shincolle.client.render;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.other.EntityShipFishingHook;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * render fishing hook, copy from RenderFish
 */
@SideOnly(Side.CLIENT)
public class RenderShipFishing extends Render<EntityShipFishingHook>
{
	
	public static final ResourceLocation FISH_PARTICLES = new ResourceLocation("textures/particle/particles.png");
    
	//factory
  	public static final FactoryDefault FACTORY_FISHINGHOOK = new FactoryDefault();
  	
    
    public RenderShipFishing(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityShipFishingHook entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	float y2 = MathHelper.cos((entity.ticksExisted + partialTicks) * 0.15F) * 0.05F - 0.25F;
    	float y3 = (float)y + y2;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, y3 + 0.25F, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        this.bindEntityTexture(entity);
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        int i = 1;
        int j = 2;
        float f = 0.0625F;
        float f1 = 0.125F;
        float f2 = 0.125F;
        float f3 = 0.1875F;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.5F;
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        vertexbuffer.pos(-0.5D, -0.5D, 0.0D).tex(0.0625D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, -0.5D, 0.0D).tex(0.125D, 0.1875D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(0.5D, 0.5D, 0.0D).tex(0.125D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        vertexbuffer.pos(-0.5D, 0.5D, 0.0D).tex(0.0625D, 0.125D).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        if (entity.host != null && !this.renderOutlines)
        {
            int k = entity.host.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
            float f7 = entity.host.getSwingProgress(partialTicks);
            float f8 = MathHelper.sin(MathHelper.sqrt(f7) * (float)Math.PI);
            float f9 = (entity.host.prevRenderYawOffset + (entity.host.renderYawOffset - entity.host.prevRenderYawOffset) * partialTicks) * 0.017453292F;
            double d0 = (double)MathHelper.sin(f9);
            double d1 = (double)MathHelper.cos(f9);
            double d2 = (double)k * 0.25D;
            double d3 = entity.host.width;
            double d4 = entity.host.prevPosX + (entity.host.posX - entity.host.prevPosX) * (double)partialTicks - d1 * d2 - d0 * d3;
            double d5 = entity.host.prevPosY + (double)entity.host.getEyeHeight() + (entity.host.posY - entity.host.prevPosY) * (double)partialTicks - 0.45D;
            double d6 = entity.host.prevPosZ + (entity.host.posZ - entity.host.prevPosZ) * (double)partialTicks - d0 * d2 + d1 * d3;
            double d7 = (entity.host.isSneaking() ? -0.1875D : 0.0D) + entity.host.height * 0.2D;
            
            if (entity.host instanceof BasicEntityShip)
            {
            	d7 += ((BasicEntityShip)entity.host).isSitting() ? entity.host.height * -0.3D : 0D;
            }
            
            double d13 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks;
            double d8 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks + 0.55D + y2;
            double d9 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks;
            double d10 = (double)((float)(d4 - d13));
            double d11 = (double)((float)(d5 - d8)) + d7;
            double d12 = (double)((float)(d6 - d9));
            
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            
            vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            int l = 16;

            for (int i1 = 0; i1 <= 16; ++i1)
            {
                float f10 = (float)i1 / 16.0F;
                vertexbuffer.pos(x + d10 * (double)f10, y3 + d11 * (double)(f10 * f10 + f10) * 0.5D + 0.25D, z + d12 * (double)f10).color(200, 200, 200, 255).endVertex();
            }

            tessellator.draw();
            
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityShipFishingHook entity)
    {
        return FISH_PARTICLES;
    }
    
	//default factory
    public static class FactoryDefault implements IRenderFactory<EntityShipFishingHook>
    {
        @Override
        public Render<? super EntityShipFishingHook> createRenderFor(RenderManager rm)
        {
            return new RenderShipFishing(rm);
        }
    }
    
    
}