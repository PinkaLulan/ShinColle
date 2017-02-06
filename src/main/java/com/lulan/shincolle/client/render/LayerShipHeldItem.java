package com.lulan.shincolle.client.render;

import com.lulan.shincolle.client.model.ShipModelBaseAdv;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.ConfigHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;

public class LayerShipHeldItem implements LayerRenderer<EntityLivingBase>
{
	
    protected final RenderLivingBase<?> render;
    protected ShipModelBaseAdv mainModel;
    protected ModelRenderer[] hand;
    protected float[] itemOffset;
    protected float[] itemRotate;
    protected float modelScale;
    
    
    public LayerShipHeldItem(RenderLivingBase<?> render)
    {
        this.render = render;
    }
    
    @Override
    public void doRenderLayer(EntityLivingBase entity, float swingTick, float swingAmount, float partialTicks, float tick, float yaw, float pitch, float scale)
    {
    	//check AI option
    	if (entity instanceof BasicEntityShip)
    	{
    		if (!((BasicEntityShip) entity).canShowHeldItem()) return;
    	}
    	
        ItemStack stackMain = entity.getHeldItemMainhand();
        ItemStack stackOff = entity.getHeldItemOffhand();
        
        //TODO debug
//        stackMain = new ItemStack(Items.DIAMOND_SWORD);
//        stackOff = new ItemStack(Items.DIAMOND_SWORD);
//        stackMain = new ItemStack(Item.getItemFromBlock(Blocks.GRASS));
//        stackOff = new ItemStack(Item.getItemFromBlock(Blocks.GRASS));
        
        if (stackMain != null)
        {
            this.renderHeldItem(entity, stackMain, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
        }
        
        if (stackOff != null)
        {
        	this.renderHeldItem(entity, stackOff, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
        }
    }

    private void renderHeldItem(EntityLivingBase entity, ItemStack stack, ItemCameraTransforms.TransformType type, EnumHandSide handSide)
    {
        GlStateManager.pushMatrix();

        if (entity.isSneaking())
        {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
        
        //only for advanced model
        if (this.render.getMainModel() instanceof ShipModelBaseAdv)
        {
        	boolean isBlock = stack.getItem() instanceof ItemBlock;
        	this.mainModel = (ShipModelBaseAdv) this.render.getMainModel();
        	this.hand = this.mainModel.getArmForSide(handSide);
        	this.itemOffset = this.mainModel.getHeldItemOffset((IShipEmotion) entity, handSide, isBlock?1:0);
        	this.itemRotate = this.mainModel.getHeldItemRotate((IShipEmotion) entity, handSide, isBlock?1:0);
        	this.modelScale = this.mainModel.getScale();
        	
        	if (hand != null)
    		{
        		boolean flag = handSide == EnumHandSide.LEFT;
        		
            	GlStateManager.translate((this.itemOffset[0]+ConfigHandler.scaleHeldItem[1])*(flag?-1F:1F),
				            			 this.itemOffset[1]+ConfigHandler.scaleHeldItem[2],
				            			 this.itemOffset[2]+ConfigHandler.scaleHeldItem[3]);
            	//TODO debug
//        		GlStateManager.translate(EventHandler.field1*(flag?-1F:1F), EventHandler.field2, EventHandler.field3);
//        		this.itemRotate[0] = 0F;
//        		this.itemRotate[1] = 0F;
//        		this.itemRotate[2] = 0F;
        		
        		//從bodyMain到手部所有連接的模型都post render一次以便套用位移跟旋轉
        		for (int i = 0; i < hand.length; i++)
        		{
        			hand[i].postRender(0.0625F * this.modelScale);
        		}
        		
        		GlStateManager.scale(this.modelScale, this.modelScale, this.modelScale);
        		GlStateManager.rotate(-90F+this.itemRotate[0], 1F, 0F, 0F);
        		GlStateManager.rotate(180F+this.itemRotate[1], 0F, 1F, 0F);
        		GlStateManager.rotate(this.itemRotate[2], 0F, 0F, 1F);
            	GlStateManager.translate((float)(flag ? -1 : 1) / 16F, 0.125F, -0.625F);
            	
            	//is weapon
            	if (stack.getItem().isFull3D())
            	{
            		GlStateManager.scale(ConfigHandler.scaleHeldItem[0]*0.5D, ConfigHandler.scaleHeldItem[0], ConfigHandler.scaleHeldItem[0]);
            	}
            	//is block
            	else if (isBlock)
            	{
            		GlStateManager.scale(ConfigHandler.scaleHeldItem[0]*0.75D, ConfigHandler.scaleHeldItem[0]*0.75D, ConfigHandler.scaleHeldItem[0]*0.75D);
            	}
            	//other item
            	else
            	{
            		GlStateManager.scale(ConfigHandler.scaleHeldItem[0], ConfigHandler.scaleHeldItem[0], ConfigHandler.scaleHeldItem[0]);
            	}
            	
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, stack, type, flag);
    		}
        }
        
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
    

}