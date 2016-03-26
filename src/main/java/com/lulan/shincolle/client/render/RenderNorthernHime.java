package com.lulan.shincolle.client.render;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import com.lulan.shincolle.client.model.ModelNorthernHime;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNorthernHime extends RenderLiving {
	
	//貼圖檔路徑
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityNorthernHime.png");
	private ModelNorthernHime model = null;
	private ItemStack holdItem = new ItemStack(ModItems.ToyAirplane);
	private Random rand = new Random();
	
	public RenderNorthernHime(ModelNorthernHime par1, float par2) {
		super(par1, par2);
		this.model = par1;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}

	@Override
	protected void renderEquippedItems(EntityLivingBase host, float swing) {
		//恢復正常顏色
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		IShipEmotion host1 = (IShipEmotion) host;
        ItemStack itemstack = host.getHeldItem();	//手持物品(用於morph時)
        Item item;
        float f1;
        
        //部份動作不畫出物品
        if(((IShipEmotion)host).getIsSitting()) {
        	return;
        }
        
        //若沒有手持物品, 則預設為玩具飛機
        if(itemstack == null) {
        	itemstack = holdItem;
        }

        //將物品畫在手上
        if(itemstack != null && itemstack.getItem() != null) {
            item = itemstack.getItem();
            GL11.glPushMatrix();
            
            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, IItemRenderer.ItemRenderType.EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(IItemRenderer.ItemRenderType.EQUIPPED, itemstack, IItemRenderer.ItemRendererHelper.BLOCK_3D));

            //依照模型手臂位置及物品類型, 設定旋轉點, 使旋轉點符合手臂轉動原點
            //若該物品為方塊類
            if(item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
            	f1 = 0.2F;
            	GL11.glTranslatef(0F, 0.9F, 0F);
                GL11.glScalef(f1, f1, f1);
            }
            //若該物品要用3D畫出 ex: 武器, 工具類
            else if(item.isFull3D()) {
                f1 = 0.375F;
                GL11.glScalef(f1, f1, f1);
                GL11.glTranslatef(0F, 2.7F, 0F);
            }
            //其他一般物品
            else {
                f1 = 0.25F;	//SIZE
                GL11.glScalef(f1, f1, f1);
                GL11.glTranslatef(0F, 3.7F, 0F);
            }
	
            if(host.ridingEntity instanceof BasicEntityShip) {
	    		GL11.glTranslatef(0F, 0F, 1.0F);
	    	}
            
	    	if(host.ridingEntity instanceof EntityPlayer) {
	    		GL11.glTranslatef(0F, 7.6F, 1.0F);
	    	}
            
            //呼叫手部post render, 使物品能依照手部模型角度改變位置
            //所有呼叫過post render的部位造成的移動, 都會套用到物品移動上
            GL11.glPushMatrix();
            this.model.BodyMain.postRender(0.0625F);
            this.model.ArmRight01.postRender(0.0625F);
            this.model.ArmRight02.postRender(0.0625F);
            
            //依照模型手臂位置及物品類型, 平移物品位置, 使轉動軸長度符合手臂長度
            //若該物品為方塊類
            if(item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType()))) {
            	GL11.glTranslatef(0.1F, 1.6F, -0.7F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            //若該物品要用3D畫出 ex: 武器, 工具類
            else if(item.isFull3D()) {
                if(item.shouldRotateAroundWhenRendering()) {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }
            	//轉動武器角度
            	GL11.glTranslatef(0F, 0.6F, 0F);
            	GL11.glRotatef(-60.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(35.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            }
            //其他一般物品
            else {
            	GL11.glTranslatef(-0.4F, 1.5F, -0.4F);
            	GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            	GL11.glRotatef(15.0F, 0.0F, 1.0F, 1.0F);
            	GL11.glRotatef(50.0F, 0.0F, 0.0F, 1.0F);
            }
            
            //畫出該物品
            float f2;
            float f5;
            int i;
            //若該物品有特殊render pass, ex:冰, 透明方塊等
            if(itemstack.getItem().requiresMultipleRenderPasses()) {
                for(i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getItemDamage()); ++i) {
                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                    f5 = (j >> 16 & 255) / 255.0F;
                    f2 = (j >> 8 & 255) / 255.0F;
                    float f3 = (j & 255) / 255.0F;
                    GL11.glColor4f(f5, f2, f3, 1.0F);
                    this.renderManager.itemRenderer.renderItem(host, itemstack, i);
                }
            }
            //其他一般物品
            else {
                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                float f4 = (i >> 16 & 255) / 255.0F;
                f5 = (i >> 8 & 255) / 255.0F;
                f2 = (i & 255) / 255.0F;
                GL11.glColor4f(f4, f5, f2, 1.0F);
                this.renderManager.itemRenderer.renderItem(host, itemstack, 0);
            }

            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
	}
}


