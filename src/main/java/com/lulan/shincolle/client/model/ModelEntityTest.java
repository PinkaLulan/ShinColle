package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEntityTest extends ModelBase {
	  
	  ModelRenderer head;
	  ModelRenderer headaccessory;
	  ModelRenderer hair;
	  ModelRenderer body;
	  ModelRenderer apron;
	  ModelRenderer chest;
	  ModelRenderer rightarm;
	  ModelRenderer leftarm;
	  ModelRenderer rightleg;
	  ModelRenderer leftleg;
	  ModelRenderer skirtribbon;
	  ModelRenderer skirt1;
	  ModelRenderer skirt2;
	  ModelRenderer skirt3;
	  ModelRenderer skirt4;
	  
	  public ModelEntityTest() {

		    this.textureWidth = 128;
		    this.textureHeight = 64;
		    
		    this.head = new ModelRenderer(this, 0, 0);
		    this.head.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6);
		    this.head.setRotationPoint(0.0F, 1.0F, 0.0F);
		    this.head.mirror = true;
		    setRotation(this.head, 0.0F, 0.0F, 0.0F);
		    
		    this.headaccessory = new ModelRenderer(this, 36, 0);
		    this.headaccessory.addBox(-3.5F, -6.5F, -3.5F, 7, 7, 7);
		    this.headaccessory.setRotationPoint(0.0F, 1.0F, 0.0F);
		    this.headaccessory.mirror = true;
		    setRotation(this.headaccessory, 0.0F, 0.0F, 0.0F);
		    
		    this.hair = new ModelRenderer(this, 44, 14);
		    this.hair.addBox(-3.5F, -2.0F, 0.5F, 7, 9, 3);
		    this.hair.setRotationPoint(0.0F, 1.0F, 0.0F);
		    this.hair.mirror = true;
		    setRotation(this.hair, 0.0F, 0.0F, 0.0F);
		    
		    this.body = new ModelRenderer(this, 0, 12);
		    this.body.addBox(-2.5F, 0.0F, -1.5F, 5, 10, 3);
		    this.body.setRotationPoint(0.0F, 1.0F, 0.0F);
		    this.body.mirror = true;
		    setRotation(this.body, 0.0F, 0.0F, 0.0F);
		    
		    this.apron = new ModelRenderer(this, 16, 58);
		    this.apron.addBox(-5.0F, 0.0F, -1.5F, 10, 3, 3);
		    this.apron.setRotationPoint(0.0F, 1.0F, 0.0F);
		    this.apron.mirror = true;
		    setRotation(this.apron, 0.0F, 0.0F, 0.0F);
		    
		    this.chest = new ModelRenderer(this, 0, 25);
		    this.chest.addBox(-2.5F, -1.0F, -1.0F, 5, 2, 2);
		    this.chest.setRotationPoint(0.0F, 3.0F, -1.5F);
		    this.chest.mirror = true;
		    setRotation(this.chest, -2.356194F, 0.0F, 0.0F);
		    
		    this.rightarm = new ModelRenderer(this, 16, 12);
		    this.rightarm.addBox(-2.0F, -1.0F, -1.0F, 2, 10, 2);
		    this.rightarm.setRotationPoint(-2.5F, 2.5F, 0.0F);
		    this.rightarm.mirror = true;
		    setRotation(this.rightarm, -0.0872665F, 0.0F, 0.0872665F);
		    
		    this.leftarm = new ModelRenderer(this, 16, 12);
		    this.leftarm.addBox(0.0F, -1.0F, -1.0F, 2, 10, 2);
		    this.leftarm.setRotationPoint(2.5F, 2.5F, 0.0F);
		    this.leftarm.mirror = true;
		    setRotation(this.leftarm, -0.0872665F, 0.0F, -0.0872665F);
		    
		    this.rightleg = new ModelRenderer(this, 24, 9);
		    this.rightleg.addBox(-1.0F, 0.0F, -1.0F, 2, 13, 2);
		    this.rightleg.setRotationPoint(-1.5F, 11.0F, 0.0F);
		    this.rightleg.mirror = true;
		    setRotation(this.rightleg, 0.0F, 0.0F, -0.034907F);
		    
		    this.leftleg = new ModelRenderer(this, 24, 9);
		    this.leftleg.addBox(-1.0F, 0.0F, -1.0F, 2, 13, 2);
		    this.leftleg.setRotationPoint(1.5F, 11.0F, 0.0F);
		    this.leftleg.mirror = true;
		    setRotation(this.leftleg, 0.0F, 0.0F, 0.034907F);
		    
		    this.skirtribbon = new ModelRenderer(this, 54, 26);
		    this.skirtribbon.addBox(-2.5F, -1.0F, 3.0F, 5, 4, 0);
		    this.skirtribbon.setRotationPoint(0.0F, 11.0F, 0.0F);
		    this.skirtribbon.mirror = true;
		    setRotation(this.skirtribbon, 0.4363323F, 0.0F, 0.0F);
		    
		    this.skirt1 = new ModelRenderer(this, 16, 24);
		    this.skirt1.addBox(-3.0F, -2.0F, -2.5F, 6, 2, 5);
		    this.skirt1.setRotationPoint(0.0F, 11.0F, 0.0F);
		    this.skirt1.mirror = true;
		    setRotation(this.skirt1, 0.0872665F, 0.0F, 0.0F);
		    
		    this.skirt2 = new ModelRenderer(this, 16, 31);
		    this.skirt2.addBox(-3.5F, 0.0F, -3.0F, 7, 2, 6);
		    this.skirt2.setRotationPoint(0.0F, 11.0F, 0.0F);
		    this.skirt2.mirror = true;
		    setRotation(this.skirt2, 0.1309F, 0.0F, 0.0F);
		    
		    this.skirt3 = new ModelRenderer(this, 16, 39);
		    this.skirt3.addBox(-4.0F, 1.0F, -3.5F, 8, 2, 7);
		    this.skirt3.setRotationPoint(0.0F, 12.0F, 0.0F);
		    this.skirt3.mirror = true;
		    setRotation(this.skirt3, 0.1745329F, 0.0F, 0.0F);
		    
		    this.skirt4 = new ModelRenderer(this, 16, 48);
		    this.skirt4.addBox(-4.5F, 3.0F, -4.0F, 9, 2, 8);
		    this.skirt4.setRotationPoint(0.0F, 12.0F, 0.0F);
		    this.skirt4.mirror = true;
		    setRotation(this.skirt4, 0.218166F, 0.0F, 0.0F);
		  }
		  
		  public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
		  {
		    super.render(entity, par2, par3, par4, par5, par6, par7);
		    setRotationAngles(par2, par3, par4, par5, par6, par7);
		    this.head.render(par7);
		    this.headaccessory.render(par7);
		    this.hair.render(par7);
		    this.body.render(par7);
		    this.apron.render(par7);
		    this.chest.render(par7);
		    this.rightarm.render(par7);
		    this.leftarm.render(par7);
		    this.rightleg.render(par7);
		    this.leftleg.render(par7);
		    this.skirtribbon.render(par7);
		    this.skirt1.render(par7);
		    this.skirt2.render(par7);
		    this.skirt3.render(par7);
		    this.skirt4.render(par7);
		  }
		  
		  
		  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
		  {
		    this.head.rotateAngleY = (par4 / 57.295776F);
		    this.head.rotateAngleX = (par5 / 57.295776F);
		    this.headaccessory.rotateAngleY = this.head.rotateAngleY;
		    this.headaccessory.rotateAngleX = this.head.rotateAngleX;
		    this.hair.rotateAngleY = this.head.rotateAngleY;
		    this.hair.rotateAngleX = this.head.rotateAngleX;
		    
		    this.rightarm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 0.8F * par2 * 0.5F);
		    this.leftarm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.8F * par2 * 0.5F);
		    
		    this.skirt1.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.3F * par2);
		    this.skirt2.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.2F * par2);
		    this.skirt3.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.1F * par2);
		    this.skirt4.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.0F * par2);
		    this.skirt1.rotateAngleX += 0.0872665F;
		    this.skirt2.rotateAngleX += 0.1309F;
		    this.skirt3.rotateAngleX += 0.1745329F;
		    this.skirt4.rotateAngleX += 0.218166F;
		    this.rightleg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 0.5F * par2);
		    this.leftleg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 0.5F * par2);
		  }
		  
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z) {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	
}
