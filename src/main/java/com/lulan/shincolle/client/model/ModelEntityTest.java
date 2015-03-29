package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEntityTest extends ModelBase {

	  
	  public ModelEntityTest() {

		    this.textureWidth = 128;
		    this.textureHeight = 64;

		  }
		  
		  public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
		  {

		  }
		  
		  
		  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
		  {

		  }
		  
	  
	  private void setRotation(ModelRenderer model, float x, float y, float z) {
	    model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	  }
	
}
