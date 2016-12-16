package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

/**
 * AbyssMissile - PinkaLulan
 * Created using Tabula 4.1.1
 */
public class ModelAbyssMissile extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Tail1;
    public ModelRenderer Tail2;

    public ModelAbyssMissile() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 14.0F, -1.5F);
        this.Body.addBox(-2.0F, -2.0F, -5.5F, 4, 4, 11, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(-1.5F, -1.5F, -6.5F);
        this.Head.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);  
        this.Tail = new ModelRenderer(this, 0, 4);
        this.Tail.setRotationPoint(-1.0F, -1.0F, 5.5F);
        this.Tail.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.Tail1 = new ModelRenderer(this, 0, 20);
        this.Tail1.setRotationPoint(-0.5F, -2.5F, 5.5F);
        this.Tail1.addBox(0.0F, 0.0F, 0.0F, 1, 5, 4, 0.0F);
        this.Tail2 = new ModelRenderer(this, 0, 15);
        this.Tail2.setRotationPoint(-2.5F, -0.5F, 5.5F);
        this.Tail2.addBox(0.0F, 0.0F, 0.0F, 5, 1, 4, 0.0F);      
        this.Body.addChild(this.Head);
        this.Body.addChild(this.Tail);
        this.Body.addChild(this.Tail1);
        this.Body.addChild(this.Tail2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.Body.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {  
    	GlStateManager.translate(0F, -0.65F, 0F);
    	this.Body.rotateAngleY = f3;	//左右角度
    	this.Body.rotateAngleX = f4; 	//上下角度
    }

}
