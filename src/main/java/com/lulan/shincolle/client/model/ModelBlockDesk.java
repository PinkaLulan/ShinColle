package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * BlockDesk - PinkaLulan
 * Created using Tabula 4.1.1  2015/11/29
 */
public class ModelBlockDesk extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;
    public ModelRenderer shape6;

    public ModelBlockDesk() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape3 = new ModelRenderer(this, 0, 0);
        this.shape3.setRotationPoint(-8.0F, 9.0F, -8.0F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 1, 15, 15, 0.0F);
        this.shape6 = new ModelRenderer(this, 0, 30);
        this.shape6.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.shape6.addBox(0.0F, 0.0F, 0.0F, 16, 1, 16, 0.0F);
        this.shape1 = new ModelRenderer(this, 34, 0);
        this.shape1.setRotationPoint(-7.0F, 9.0F, -8.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 14, 6, 1, 0.0F);
        this.shape2 = new ModelRenderer(this, 0, 0);
        this.shape2.setRotationPoint(-8.0F, 9.0F, 7.0F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 16, 15, 1, 0.0F);
        this.shape4 = new ModelRenderer(this, 0, 0);
        this.shape4.setRotationPoint(7.0F, 9.0F, -8.0F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 1, 15, 15, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape3.render(f5);
        this.shape6.render(f5);
        this.shape1.render(f5);
        this.shape2.render(f5);
        this.shape4.render(f5);
    }
    
    public void render(float f5) {
    	this.shape3.render(f5);
        this.shape6.render(f5);
        this.shape1.render(f5);
        this.shape2.render(f5);
        this.shape4.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
