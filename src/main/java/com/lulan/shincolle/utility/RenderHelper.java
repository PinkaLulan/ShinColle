package com.lulan.shincolle.utility;

import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * render helper
 */
@SideOnly(Side.CLIENT)
public class RenderHelper
{
	
	public static Random rand = new Random();
	
	
	public RenderHelper() {}
	
    /**
     * Draws a textured rectangle at the current z-value.
     */
    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, double zLevel)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public static void drawTexturedModalRectWithUV(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV, double zLevel)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
    
	//custom main hand renderer
	@SideOnly(Side.CLIENT)
    public static void renderItemInFirstPerson(AbstractClientPlayer player, float ptick, float pitch, EnumHand hand, float swing, @Nullable ItemStack stack, float equip)
    {
        EnumHandSide enumhandside = player.getPrimaryHand();
        
        if (!player.isInvisible())
        {
            boolean flag = enumhandside != EnumHandSide.LEFT;
            float f = flag ? 1.0F : -1.0F;
            
            //get player skin
            ClientProxy.getMineraft().getTextureManager().bindTexture(player.getLocationSkin());
            
            //draw hand
            GlStateManager.pushMatrix();
            GlStateManager.translate(f * 0.64000005F, -0.6F, -0.71999997F);
            GlStateManager.rotate(f * 45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(f * -1.0F, 3.6F, 3.5F);
            GlStateManager.rotate(f * 120.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f * -135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(f * 5.6F, 0.0F, 0.0F);
            
            if (ClientProxy.getGameSetting().keyBindUseItem.isKeyDown())
            {
            	switch (stack.getMetadata())
            	{
            	case 3:
                	GlStateManager.translate(1.3F, 4F, 0.0F);
                	GlStateManager.scale(3F, 3F, 3F);
                	GlStateManager.rotate(MathHelper.cos((player.ticksExisted + ptick) * 0.125F) * -20F - 60F, 0F, 0F, 1F);
        		break;
	            case 4:
	            	GlStateManager.rotate(70F, 0F, 1F, 0F);
	            	GlStateManager.rotate(-20F, 0F, 0F, 1F);
	            	GlStateManager.translate(-2F, 16F, 10F);
					GlStateManager.scale(12F, 12F, 12F);
					GlStateManager.rotate(MathHelper.cos((player.ticksExisted + ptick) * 0.1F) * -15F + 20F, 1F, 0F, 0F);
        		break;
        		default:
        			GlStateManager.translate(13.5F, 12.5F, 2.5F);
    				GlStateManager.scale(9F, 9F, 9F);
    				GlStateManager.rotate(MathHelper.cos((player.ticksExisted + ptick) * 0.2F) * -15F - 20F, 1F, 1F, 0F);
    			break;
            	}
            }
            
            RenderPlayer renderplayer = (RenderPlayer)ClientProxy.getMineraft().getRenderManager().getEntityRenderObject(player);
            
            GlStateManager.disableCull();

            if (flag)
            {
                renderplayer.renderRightArm(player);
            }
            else
            {
                renderplayer.renderLeftArm(player);
            }

            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }
    }
    
    /**
     * draw mount skill icon
     */
    public static void drawMountSkillIcon(RenderGameOverlayEvent event)
    {
		//get mc
		Minecraft mc = ClientProxy.getMineraft();
		if (mc == null || mc.skipRenderWorld) return;
		FontRenderer fr = mc.fontRendererObj;
		
		//get player
		EntityPlayer player = ClientProxy.getClientPlayer();
		if (player == null) return;
		
		//get ship
		BasicEntityShip ship = null;
		boolean[] drawBtn = new boolean[4];
		int[] drawCD = new int[4];
		int[] drawCDMax = new int[4];
		
		if (player.getRidingEntity() instanceof BasicEntityMount &&
			((BasicEntityMount)player.getRidingEntity()).getHostEntity() instanceof BasicEntityShip)
		{
			ship = (BasicEntityShip) ((BasicEntityMount) player.getRidingEntity()).getHostEntity();
			drawBtn[0] = ship.getStateFlag(ID.F.AtkType_Light);
			drawBtn[1] = ship.getStateFlag(ID.F.AtkType_Heavy);
			drawBtn[2] = ship.getStateFlag(ID.F.AtkType_AirLight);
			drawBtn[3] = ship.getStateFlag(ID.F.AtkType_AirHeavy);
			drawCD[0] = ship.getStateTimer(ID.T.MountSkillCD1);
			drawCD[1] = ship.getStateTimer(ID.T.MountSkillCD2);
			drawCD[2] = ship.getStateTimer(ID.T.MountSkillCD3);
			drawCD[3] = ship.getStateTimer(ID.T.MountSkillCD4);
			drawCDMax[0] = CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 1);
			drawCDMax[1] = CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 2);
			drawCDMax[2] = CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 3);
			drawCDMax[3] = CombatHelper.getAttackDelay(ship.getAttrs().getAttackSpeed(), 4);
		}
		else
		{
			return;
		}
		
		try
		{
			//get window size
			ScaledResolution sr = new ScaledResolution(mc);
	        int i = sr.getScaledWidth();
	        int j = sr.getScaledHeight();
	        int px = (int) (i * 0.5F);
	        int py = (int) (j * 0.7F);
	        
	        //start drawing
	        GlStateManager.pushMatrix();
	        GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

	        //draw mount skill buttons
	        for (int k = 0; k < 4; k++)
	        {
	        	if (drawBtn[k])
	        	{
	        		//set color and texture before every drawTexturedModalRect()
	        		GlStateManager.color(1F, 1F, 1F, 1F);
	        		mc.getTextureManager().bindTexture(ClientProxy.TextureGuiHUD);
	    	        
	    	        int px2 = px-40+k*21;
	        		int len = (int)((float)drawCD[k] / (float)drawCDMax[k] * 18F);
	        		//draw button icon
	        		RenderHelper.drawTexturedModalRect(px2, py, 0+k*18, 0, 18, 18, 0);
	        		//draw button cooldown icon
	        		RenderHelper.drawTexturedModalRect(px2, py+18-len, 0, 36-len, 18, len, 0);
	        		//draw button cooldown text
	        		if (len > 0) fr.drawStringWithShadow(String.format("%.1f", (float)drawCD[k] * 0.05F), px2+5, py+18, Enums.EnumColors.YELLOW.getValue());
	        	}
	        }
	        
	        //end drawing
	        mc.getTextureManager().bindTexture(Gui.ICONS);	//reset textures
	        GlStateManager.color(1F, 1F, 1F, 1F);
	        GlStateManager.disableBlend();
            GlStateManager.popMatrix();
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: render game overlay fail: "+e);
			e.printStackTrace();
		}
    }
    
    
}