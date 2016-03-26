package com.lulan.shincolle.client.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.model.ModelBasicEntityItem;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBasicEntityItem extends Render {
    
	//貼圖檔路徑
	private static final ResourceLocation entityTexture = new ResourceLocation(Reference.TEXTURES_ENTITY+"ModelBasicEntityItem.png");
	private ModelBasicEntityItem model;

    public RenderBasicEntityItem(float scale) {   
    	this.model = new ModelBasicEntityItem(scale);
	}
    
    @Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return entityTexture;
	}

    public void doRender(BasicEntityItem entity, double offsetX, double offsetY, double offsetZ, float p_76986_8_, float p_76986_9_) {

    	//bind texture
        this.bindEntityTexture(entity);  		//call getEntityTexture
        
        //render start
        GL11.glPushMatrix();
//        GL11.glDisable(GL11.GL_CULL_FACE);	//保證model全部都畫出來, 不是只畫看得到的面
        
        //model position set to center
        GL11.glTranslatef((float)offsetX, (float)offsetY+0.3F, (float)offsetZ);   		

        //parm: entity, f依移動速度, f1依移動速度, f2遞增, f3左右角度, f4上下角度, f5(scale)
        this.model.render(entity, entity.ticksExisted, 0F, 0F, 0F, 0F, 0.0625F);

        GL11.glPopMatrix();       
    }

    //傳入entity的都轉成abyssmissile
    @Override
	public void doRender(Entity entity, double offsetX, double offsetY, double offsetZ, float p_76986_8_, float p_76986_9_) {
    	this.doRender((BasicEntityItem)entity, offsetX, offsetY, offsetZ, p_76986_8_, p_76986_9_);
    }
}
