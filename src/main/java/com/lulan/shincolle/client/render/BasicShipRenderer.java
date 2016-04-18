package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;

/** custom model renderer
 * 
 *
 */
abstract public class BasicShipRenderer extends RenderLiving {

	protected static double leashWidthMod = 0.1D;
	protected static double leashOffsetRide = 0D;
	protected static double leashOffsetRideSit = 0D;
	protected static double leashOffsetSit = 0D;
	protected static double leashOffsetStand = 0D;
	
	
	public BasicShipRenderer(ModelBase model, float scale) {
		super(model, scale);
	}

	@Override
	abstract protected ResourceLocation getEntityTexture(Entity host);
	
	//render lead
	@Override
	protected void func_110827_b(EntityLiving host, double d, double d1, double d2, float f, float f1) {
        Entity entity = host.getLeashedToEntity();

        if(entity != null) {
        	IShipEmotion host1 = (IShipEmotion) host;
        	
        	if(host1.getIsRiding()) {
        		if(host1.getIsSitting()) {
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashOffsetRideSit;
        		}
        		else {
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashOffsetRide;
        		}
        	}
        	else {
        		if(host1.getIsSitting()) {
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashOffsetSit;
//        			d1 -= (1.6D - (double)host.height) * 0.5D + 0.8D;
        		}
        		else {
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashOffsetStand;
//        			d1 -= (1.6D - (double)host.height) * 0.5D + 0.3D;
        		}
        	}
            
            Tessellator tessellator = Tessellator.instance;
            double d3 = this.interp((double)entity.prevRotationYaw, (double)entity.rotationYaw, (double)(f1 * 0.5F)) * 0.01745329238474369D;
            double d4 = this.interp((double)entity.prevRotationPitch, (double)entity.rotationPitch, (double)(f1 * 0.5F)) * 0.01745329238474369D;
            double d5 = Math.cos(d3);
            double d6 = Math.sin(d3);
            double d7 = Math.sin(d4);

            if(entity instanceof EntityHanging) {
                d5 = 0.0D;
                d6 = 0.0D;
                d7 = -1.0D;
            }

            double d8 = Math.cos(d4);
            double d9 = this.interp(entity.prevPosX, entity.posX, (double)f1) - d5 * 0.7D - d6 * 0.5D * d8;
            double d10 = this.interp(entity.prevPosY + (double)entity.getEyeHeight() * 0.7D, entity.posY + (double)entity.getEyeHeight() * 0.7D, (double)f1) - d7 * 0.5D - 0.25D;
            double d11 = this.interp(entity.prevPosZ, entity.posZ, (double)f1) - d6 * 0.7D + d5 * 0.5D * d8;
            double d12 = this.interp((double)host.prevRenderYawOffset, (double)host.renderYawOffset, (double)f1) * 0.01745329238474369D + (Math.PI / 2D);

//            leashWidthMod = 0.2D;
            d5 = Math.cos(d12) * (double)host.width * leashWidthMod;
            d6 = Math.sin(d12) * (double)host.width * leashWidthMod;
            
            double d13 = this.interp(host.prevPosX, host.posX, (double)f1) + d5;
            double d14 = this.interp(host.prevPosY, host.posY, (double)f1);
            double d15 = this.interp(host.prevPosZ, host.posZ, (double)f1) + d6;
            d += d5;
            d2 += d6;
            double d16 = (double)((float)(d9 - d13));
            double d17 = (double)((float)(d10 - d14));
            double d18 = (double)((float)(d11 - d15));
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            boolean flag = true;
            double d19 = 0.025D;
            tessellator.startDrawing(5);
            int i;
            float f2;

            for(i = 0; i <= 24; ++i) {
                if(i % 2 == 0) {
                    tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                }
                else {
                    tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
                }

                f2 = (float)i / 24.0F;
                tessellator.addVertex(d + d16 * (double)f2 + 0.0D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F), d2 + d18 * (double)f2);
                tessellator.addVertex(d + d16 * (double)f2 + 0.025D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F) + 0.025D, d2 + d18 * (double)f2);
            }

            tessellator.draw();
            tessellator.startDrawing(5);

            for(i = 0; i <= 24; ++i) {
                if(i % 2 == 0) {
                    tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                }
                else {
                    tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
                }

                f2 = (float)i / 24.0F;
                tessellator.addVertex(d + d16 * (double)f2 + 0.0D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F) + 0.025D, d2 + d18 * (double)f2);
                tessellator.addVertex(d + d16 * (double)f2 + 0.025D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F), d2 + d18 * (double)f2 + 0.025D);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }
	
	//interpolation
	private double interp(double par1, double par2, double par3) {
        return par1 + (par2 - par1) * par3;
    }
	

}
