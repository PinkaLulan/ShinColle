package missile2;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelAbyssMissile - Either Mojang or a mod author
 * Created using Tabula 4.1.1
 */
public class ModelAbyssMissile extends ModelBase {
    public ModelRenderer Body;
    public ModelRenderer BodyChild;
    public ModelRenderer BodyChild_1;
    public ModelRenderer BodyChild_2;
    public ModelRenderer BodyChild_3;

    public ModelAbyssMissile() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.BodyChild_3 = new ModelRenderer(this, 0, 15);
        this.BodyChild_3.setRotationPoint(-2.5F, -0.5F, 5.5F);
        this.BodyChild_3.addBox(0.0F, 0.0F, 0.0F, 5, 1, 4, 0.0F);
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.setRotationPoint(0.0F, 14.0F, -1.5F);
        this.Body.addBox(-2.0F, -2.0F, -5.5F, 4, 4, 11, 0.0F);
        this.BodyChild_2 = new ModelRenderer(this, 0, 20);
        this.BodyChild_2.setRotationPoint(-0.5F, -2.5F, 5.5F);
        this.BodyChild_2.addBox(0.0F, 0.0F, 0.0F, 1, 5, 4, 0.0F);
        this.BodyChild = new ModelRenderer(this, 0, 0);
        this.BodyChild.setRotationPoint(-1.5F, -1.5F, -6.5F);
        this.BodyChild.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.BodyChild_1 = new ModelRenderer(this, 0, 4);
        this.BodyChild_1.setRotationPoint(-1.0F, -1.0F, 5.5F);
        this.BodyChild_1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.Body.addChild(this.BodyChild_3);
        this.Body.addChild(this.BodyChild_2);
        this.Body.addChild(this.BodyChild);
        this.Body.addChild(this.BodyChild_1);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body.render(f5);
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
