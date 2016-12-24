package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**EMOTION PARTICLE
 * 
 */
@SideOnly(Side.CLIENT)
public class ParticleEmotion extends Particle
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleEmotion.png");
	public static final int EMO_NUMBER = 30;
	
	private Entity host = null;
	private int particleType, playTimes, fadeTick, fadeState, stayTick, stayTickCount, frameSize;
	private float playSpeed, playSpeedCount, particleIconX, particleIconY, addHeight, entType;
	private float[] spawnRange;
	private double px, py, pz, addx, addy, addz;
	
	
	/**
	 *  par1: entityType by command /emotes
	 */
    public ParticleEmotion(World world, Entity host, double posX, double posY, double posZ, float height, int entType, int type)
    {
        super(world, posX, posY, posZ);
        this.host = host;
        this.setSize(0F, 0F);
        this.setPosition(posX, posY, posZ);
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleType = type;
        this.particleScale = this.rand.nextFloat() * 0.05F + 0.275F;
        this.particleAlpha = 0F;
        this.playSpeed = 1F;
        this.playSpeedCount = 0F;
        this.stayTick = 10;
        this.stayTickCount = 0;
        this.fadeTick = 0;
        this.fadeState = 0;  //0:fade in, 1:normal, 2:fade out, 3:set dead
        this.frameSize = 1;
        this.addHeight = height;
        this.entType = entType;  //0:any entity, 1:entity, 2:block
        this.particleAge = -1;  //prevent showing the emo's initial moving from posY = 0
        this.canCollide = false;
        
        //set icon position
        switch(this.particleType)
        {
        case 1:   //小愛心
        	this.particleIconX = 0.0625F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 4;
        	//no stay
        	this.stayTick = 0;
			break;
        case 2:   //噴汗
        	this.particleIconX = 0.0625F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 3;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 5;
        	//no stay
        	this.stayTick = 0;
        	break;
        case 3:   //問號R
        	this.particleIconX = 0.125F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
			break;
        case 4:   //驚嘆號
        	this.particleIconX = 0.125F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//long stay
        	this.stayTick = 20;
        	break;
        case 5:   //點點點
        	this.particleIconX = 0.1875F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 20;
        	//slow play
        	this.playSpeed = 0.5F;
			break;
        case 6:   //冒青筋
        	this.particleIconX = 0.1875F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	break;
        case 7:   //音符
        	this.particleIconX = 0.25F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 15;
        	this.playTimes = 1;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//short stay
        	this.stayTick = 3;
        	//slow play
        	this.playSpeed = 0.7F;
        	break;
        case 8:   //cry
        	this.particleIconX = 0.3125F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 3;
        	//short fade in
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 0;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 9:   //流口水
        	this.particleIconX = 0.3125F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 2;
        	//short fade in
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 1;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 10:  //混亂
        	this.particleIconX = 0.375F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 4;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//short stay
        	this.stayTick = 1;
        	break;
        case 11:  //尋找
        	this.particleIconX = 0.375F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 2;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//short stay
        	this.stayTick = 0;
        	//slow play
        	this.playSpeed = 0.75F;
        	break;
        case 12:  //驚嚇
        	this.particleIconX = 0.4375F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 14;
        	this.playTimes = 1;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//long stay
        	this.stayTick = 20;
        	//slow play
        	this.playSpeed = 0.75F;
        	//large frame
        	this.frameSize = 2;
        	break;
        case 13:  //點頭
        	this.particleIconX = 0.5F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 2;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 0;
        	//slow play
        	this.playSpeed = 0.75F;
        	break;
        case 14:  //+_+
        	this.particleIconX = 0.5F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 2;
        	//short fade in
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 0;
        	break;
        case 15:  //kiss
        	this.particleIconX = 0.5625F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//long stay
        	this.stayTick = 15;
        	//slow play
        	this.playSpeed = 0.7F;
        	break;
        case 16:  //lol
        	this.particleIconX = 0.5625F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 3;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 0;
        	break;
        case 17:  //奸笑
        	this.particleIconX = 0.625F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 15;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 18:  //殘念
        	this.particleIconX = 0.6875F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//no stay
        	this.stayTick = 0;
        	//slow play
        	this.playSpeed = 0.4F;
        	break;
        case 19:  //舔舔
        	this.particleIconX = 0.6875F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 3;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 0;
        	//slow play
        	this.playSpeed = 0.75F;
        	break;
        case 20:  //orz
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//long stay
        	this.stayTick = 20;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 21:  //O
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 22:  //X
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.5625F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 23:  //!?
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.625F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 24:  //rock
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.6875F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 25:  //paper
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.75F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 26:  //scissors
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.8125F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 27:  //-w-
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.875F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 28:  //-口-
        	this.particleIconX = 0.75F;
        	this.particleIconY = 0.9375F;
        	this.particleMaxAge = 0;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 40;
        	break;
        case 29:  //blink
        	this.particleIconX = 0.8125F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//slow play
        	this.playSpeed = 0.35F;
        	//long stay
        	this.stayTick = 20;
        	break;
        case 30:  //哼
        	this.particleIconX = 0.8125F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//slow play
        	this.playSpeed = 0.75F;
        	//short stay
        	this.stayTick = 3;
        	break;
        case 31:  //臉紅紅
        	this.particleIconX = 0.875F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 3;
        	this.particleScale += 0.2F;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//slow play
        	this.playSpeed = 0.75F;
        	//long stay
        	this.stayTick = 30;
        	break;
        case 32:  //尷尬
        	this.particleIconX = 0.875F;
        	this.particleIconY = 0.25F;
        	this.particleMaxAge = 5;
        	this.playTimes = 4;
        	//slow play
        	this.playSpeed = 0.75F;
        	//no stay
        	this.stayTick = 0;
        	break;
        case 33:  //:P
        	this.particleIconX = 0.875F;
        	this.particleIconY = 0.625F;
        	this.particleMaxAge = 4;
        	this.playTimes = 1;
        	//slow play
        	this.playSpeed = 0.25F;
        	//long stay
        	this.stayTick = 30;
        	break;
        case 34:  //|||
        	this.particleIconX = 0.875F;
        	this.particleIconY = 0.9375F;
        	this.particleMaxAge = 0;
        	this.particleScale += 0.3F;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 50;
        	break;
        default:  //汗
        	this.particleIconX = 0F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 15;
        	this.playTimes = 1;
			break;
        }
        
        //init position
        this.px = posX;
        this.py = posY;
        this.pz = posZ;
        this.addx = 0D;
        this.addy = 0D;
        this.addz = 0D;
        
        calcParticlePosition();
    }
    
    //設定此particle是否會被透明物件 ex: ice, water等擋住
    @Override
    public boolean isTransparent()
    {
    	return false;
    }

    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ)
    {
    	if (particleAge < 0) return;
    	
    	Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
    	
    	GlStateManager.pushAttrib();  //save prev flags
    	GlStateManager.pushMatrix();  //save prev pos
		GlStateManager.depthMask(true);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableLighting();
		
		int age = particleAge > particleMaxAge ? particleMaxAge : particleAge;

		float f6 = particleIconX;
		float f7 = f6 + 0.0625F;
		float f8 = particleIconY + age * 0.0625F;
		float f9 = f8 + 0.0625F * this.frameSize;
		
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ptick - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ptick - interpPosZ);

        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        //正面
        render.pos(f11 - rotX * particleScale - rotXY * particleScale, f12 - rotZ * particleScale * frameSize, f13 - rotYZ * particleScale - rotXZ * particleScale).tex(f7, f9).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        render.pos(f11 - rotX * particleScale + rotXY * particleScale, f12 + rotZ * particleScale * frameSize, f13 - rotYZ * particleScale + rotXZ * particleScale).tex(f7, f8).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        render.pos(f11 + rotX * particleScale + rotXY * particleScale, f12 + rotZ * particleScale * frameSize, f13 + rotYZ * particleScale + rotXZ * particleScale).tex(f6, f8).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        render.pos(f11 + rotX * particleScale - rotXY * particleScale, f12 - rotZ * particleScale * frameSize, f13 + rotYZ * particleScale - rotXZ * particleScale).tex(f6, f9).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        //反面
        render.pos(f11 + rotX * particleScale - rotXY * particleScale, f12 - rotZ * particleScale * frameSize, f13 + rotYZ * particleScale - rotXZ * particleScale).tex(f6, f9).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        render.pos(f11 + rotX * particleScale + rotXY * particleScale, f12 + rotZ * particleScale * frameSize, f13 + rotYZ * particleScale + rotXZ * particleScale).tex(f6, f8).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        render.pos(f11 - rotX * particleScale + rotXY * particleScale, f12 + rotZ * particleScale * frameSize, f13 - rotYZ * particleScale + rotXZ * particleScale).tex(f7, f8).color(1F, 1F, 1F, this.particleAlpha).endVertex();
        render.pos(f11 - rotX * particleScale - rotXY * particleScale, f12 - rotZ * particleScale * frameSize, f13 - rotYZ * particleScale - rotXZ * particleScale).tex(f7, f9).color(1F, 1F, 1F, this.particleAlpha).endVertex();

        //draw
        Tessellator.getInstance().draw();

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    
    //layer: 0:particle 1:terrain 2:items 3:custom
    @Override
    public int getFXLayer()
    {
        return 3;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
    	//update pos
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if(host != null)
        {
        	updateHostPosition();
        }
        
        //fade state
        switch (this.fadeState)
        {
        case 0:   //fade in
        	this.fadeTick++;
        	this.particleAlpha = this.fadeTick * 0.2F;
        	
        	if (this.fadeTick > 5) this.fadeState = 1;
        	break;
        case 1:   //age++
        	this.playSpeedCount += this.playSpeed;
        	this.particleAge = this.frameSize * (int)this.playSpeedCount;
        	this.particleAlpha = 1F;
        	break;
        case 2:   //fade out
        	this.fadeTick--;
        	this.particleAlpha = this.fadeTick * 0.2F;
        	
        	if (this.fadeTick < 1)
        	{
        		this.setExpired();
        		return;
        	}
        	break;
    	default:
    		this.setExpired();
    		return;
        }
        
        //stay at last frame
        if (this.particleAge >= particleMaxAge)
        {
    		this.particleAge = this.particleMaxAge;
    		
    		//count stay ticks
    		if (this.stayTickCount > this.stayTick)
    		{
    			this.particleAge = this.particleMaxAge + 1;  //next loop flag
    			this.stayTickCount = 0;
    		}
    		else
    		{
    			this.stayTickCount += 1;
    		}
    	}

        //loop play
        if (this.particleAge > this.particleMaxAge)
        {
        	//loop times--
        	if (--this.playTimes <= 0)
        	{
        		this.fadeState = 2;  //change to fade out
        	}
        	else
        	{
        		this.particleAge = 0;
        		this.playSpeedCount = 0F;
        	}
        }
    }
    
    private void updateHostPosition()
    {
    	//get host position
    	if (this.host != null)
    	{
            this.posX = this.host.posX + addx;
            this.posY = this.host.posY + addy;
            this.posZ = this.host.posZ + addz;
        }
    }
    
    private void calcParticlePosition()
    {
    	//get host position
    	if (this.host != null)
    	{
        	this.px = this.host.posX;
            this.py = this.host.posY;
            this.pz = this.host.posZ;
        }
    	
    	//get player view angle
        float angle = ClientProxy.getClientPlayer().renderYawOffset % 360 * Values.N.DIV_PI_180;
        float[] newPos;
        
        //tweak emote position by entity type
        if (entType == 1)  //entity type
        {
        	//replace emotes into player's view cone
        	float frontDist = 0.7F;
        	float leftDist = -0.2F;
        	
        	switch (this.particleType)
        	{
        	case 12:  //omg
        		leftDist = 0F;
        		addy += 0.6D;
        		break;
        	case 15:  //kiss
        		frontDist = 1.5F;
            	leftDist = -0.7F;
        		break;
        	case 19:  //lick
        		frontDist = 1.4F;
            	leftDist = -1.1F;
        		break;
        	case 34:  //lll
        		frontDist = -0.2F;
        		leftDist = 0F;
        		addy -= 0.2D;
        		break;
        	}
        	
        	newPos = CalcHelper.rotateXZByAxis(frontDist, leftDist, angle, 1F);
        	addx += newPos[1];
        	addy -= 0.2D;
        	addz += newPos[0];
        }
        else  //block type
        {
        	newPos = CalcHelper.rotateXZByAxis(0F, -0.2F, angle, 1F);
        	addx += newPos[1];
        	addy += 0.5D;
        	addz += newPos[0];
        }
        	
        
        //enlarge if boss entity
        float addx2 = 0F;
        float addy2 = 0F;
        float addz2 = 0F;
        
        if (this.addHeight > 2F)
        {
        	this.particleScale += 1F;
        	addx2 = 1.2F;
        	addy2 = 1.5F;
        	addz2 = 0.5F;
        }
        
        //set particle position
        switch (this.particleType)
        {
        case 2:  //right side
			newPos = CalcHelper.rotateXZByAxis(-0.2F - addz2, this.rand.nextFloat() * 0.3F - 1F - addx2, angle, 1F);
			addx = addx + newPos[1];
	    	addy = addy + this.rand.nextDouble() * this.addHeight * 0.2D + this.addHeight * 1.8D + addy2;
	    	addz = addz + newPos[0];
        	break;
        case 15: //front
			newPos = CalcHelper.rotateXZByAxis(this.rand.nextFloat() * 0.1F - 0.7F - addx2, this.rand.nextFloat() * 0.1F + 0.2F + addz2, angle, 1F);
			addx = addx + newPos[1];
			addy = addy + this.rand.nextDouble() * this.addHeight * 0.2D + this.addHeight * 1.6D + addy2;
			addz = addz + newPos[0];
        	break;
        case 34: //top
			newPos = CalcHelper.rotateXZByAxis(0.15F, 0F, angle, 1F);
			addx = addx + newPos[1];
			addy = addy + this.rand.nextDouble() * this.addHeight * 0.15D + this.addHeight * 1.9D + addy2;
			addz = addz + newPos[0];
        	break;
    	default: //left side
			newPos = CalcHelper.rotateXZByAxis(-0.4F - addz2, this.rand.nextFloat() * 0.3F + 0.7F + addx2, angle, 1F);
			addx = addx + newPos[1];
			addy = addy + this.rand.nextDouble() * this.addHeight * 0.5D + this.addHeight * 1.5D + addy2;
			addz = addz + newPos[0];
    		break;
        }
        
        //set position
        this.setPosition(px + addx, py + addy, pz + addz);
        
    }
    
    
}

