package com.lulan.shincolle.client.model;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


public class ModelSmallShipyard extends ModelBase
{
	//fields
	private ModelRenderer Shape1;
	private ModelRenderer Shape2;
	private ModelRenderer Shape3;
	private ModelRenderer Shape4;
	private ModelRenderer Shape5;
	private ModelRenderer Shape6;
	private ModelRenderer Shape7;
	private ModelRenderer Shape8;
	private ModelRenderer Shape9;
	private ModelRenderer Shape10;
	private ModelRenderer Shape11;
  
  public ModelSmallShipyard()
  {
    textureWidth = 64;
    textureHeight = 32;
    
      Shape1 = new ModelRenderer(this, 0, 0);
      Shape1.addBox(0F, 0F, 0F, 16, 1, 16);
      Shape1.setRotationPoint(-8F, 23F, -8F);
      Shape1.setTextureSize(64, 32);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 19);
      Shape2.addBox(0F, 0F, 0F, 14, 3, 10);
      Shape2.setRotationPoint(-7F, 20F, -3F);
      Shape2.setTextureSize(64, 32);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 0);
      Shape3.addBox(-5F, 0F, 0F, 6, 4, 6);
      Shape3.setRotationPoint(-1F, 17F, -1.5F);
      Shape3.setTextureSize(64, 32);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, 0, 0);
      Shape4.addBox(0F, 0F, 0F, 4, 6, 4);
      Shape4.setRotationPoint(-5F, 11F, -1F);
      Shape4.setTextureSize(64, 32);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 48, 6);
      Shape5.addBox(0F, 0F, 0F, 3, 3, 3);
      Shape5.setRotationPoint(-4.5F, 8F, -0.5F);
      Shape5.setTextureSize(64, 32);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 32, 20);
      Shape6.addBox(0F, 0F, 0F, 10, 3, 6);
      Shape6.setRotationPoint(-3.5F, 18F, 0F);
      Shape6.setTextureSize(64, 32);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
      Shape7 = new ModelRenderer(this, 0, 10);
      Shape7.addBox(0F, 0F, 0F, 4, 3, 4);
      Shape7.setRotationPoint(2F, 15F, 1.5F);
      Shape7.setTextureSize(64, 32);
      Shape7.mirror = true;
      setRotation(Shape7, 0F, 0F, 0F);
      Shape8 = new ModelRenderer(this, 48, 12);
      Shape8.addBox(0F, 0F, 0F, 3, 3, 3);
      Shape8.setRotationPoint(2.5F, 12.5F, 2F);
      Shape8.setTextureSize(64, 32);
      Shape8.mirror = true;
      setRotation(Shape8, 0F, 0F, 0F);
      Shape9 = new ModelRenderer(this, 0, 17);
      Shape9.addBox(0F, 1F, 0F, 11, 2, 4);
      Shape9.setRotationPoint(-5F, 20F, -7F);
      Shape9.setTextureSize(64, 32);
      Shape9.mirror = true;
      setRotation(Shape9, 0F, 0F, 0F);
      Shape10 = new ModelRenderer(this, 0, 10);
      Shape10.addBox(-2F, 0F, 0F, 4, 2, 4);
      Shape10.setRotationPoint(1F, 19F, -6.5F);
      Shape10.setTextureSize(64, 32);
      Shape10.mirror = true;
      setRotation(Shape10, 0F, 0F, 0F);
      Shape11 = new ModelRenderer(this, 48, 0);
      Shape11.addBox(0F, 0F, 0F, 3, 3, 3);
      Shape11.setRotationPoint(-0.5F, 16.01333F, -6F);
      Shape11.setTextureSize(64, 32);
      Shape11.mirror = true;
      setRotation(Shape11, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
    Shape9.render(f5);
    Shape10.render(f5);
    Shape11.render(f5);
  }
  
  public void renderModel(float f5)
  {
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    Shape7.render(f5);
    Shape8.render(f5);
    Shape9.render(f5);
    Shape10.render(f5);
    Shape11.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override
public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }

}
