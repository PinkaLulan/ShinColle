package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;

/** custom model renderer
 * 
 *
 */
public class BasicShipRenderer extends RenderLiving
{
	
	//textures
	//AP
	private static final ResourceLocation AP_Wa = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityTransportWa.png");
	//BB
	private static final ResourceLocation BB_Re = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipRe.png");
	private static final ResourceLocation BB_Ta = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipTa.png");
	//CA
	private static final ResourceLocation CA_Ri = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHeavyCruiserRi.png");
	private static final ResourceLocation CA_Ne = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHeavyCruiserNe.png");
	//CV
	private static final ResourceLocation CV_Wo = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierWo.png");
	//DD
	private static final ResourceLocation DD_I = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerI.png");
	private static final ResourceLocation DD_Ro = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerRo.png");
	private static final ResourceLocation DD_Ha = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerHa.png");
	private static final ResourceLocation DD_Ni = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerNi.png");
	//Hime
	private static final ResourceLocation Hime_Airfield = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirfieldHime.png");
	private static final ResourceLocation Hime_Battleship = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipHime.png");
	private static final ResourceLocation Hime_Harbour = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHarbourHime.png");
	private static final ResourceLocation Hime_Northern = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityNorthernHime.png");
	//SS
	private static final ResourceLocation SS_So = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmSo.png");
	//WD
	private static final ResourceLocation WD_Carrier = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierWDemon.png");
	
	//Hostile Sip
	//BB
	private static final ResourceLocation BB_Nagato = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipNagato.png");
	private static final ResourceLocation BB_Yamato = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipYamato.png");
	//CV
	private static final ResourceLocation CV_Akagi = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierAkagi.png");
	private static final ResourceLocation CV_Kaga = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierKaga.png");
	//DD
	private static final ResourceLocation DD_Ikazuchi = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerIkazuchi.png");
	private static final ResourceLocation DD_Inazuma = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerInazuma.png");
	private static final ResourceLocation DD_Shimakaze = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerShimakaze.png");
	//SS
	private static final ResourceLocation SS_Ro500 = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmRo500.png");
	private static final ResourceLocation SS_U511 = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmU511.png");
	
	protected int shipClass;
	
	
	public BasicShipRenderer(ModelBase model, float scale)
	{
		super(model, scale);
	}
	
	public BasicShipRenderer(ModelBase model, float scale, int shipClass)
	{
		super(model, scale);
		this.shipClass = shipClass;
	}

	//get texture from entity
	@Override
	protected ResourceLocation getEntityTexture(Entity host)
	{
		return getShipTexture(this.shipClass);
	}
	
	//get texture by id
	private ResourceLocation getShipTexture(int id)
	{
		switch(id)
		{
		//AP
		case ID.Ship.TransportWA:
			return AP_Wa;
		//BB
		case ID.Ship.BattleshipTA:
			return BB_Ta;
		case ID.Ship.BattleshipRE:
			return BB_Re;
		//CL
		case ID.Ship.HeavyCruiserRI:
			return CA_Ri;
		case ID.Ship.HeavyCruiserNE:
			return CA_Ne;	
		//CV
		case ID.Ship.CarrierWO:
			return CV_Wo;
		//DD
		case ID.Ship.DestroyerI:
			return DD_I;
		case ID.Ship.DestroyerRO:
			return DD_Ro;
		case ID.Ship.DestroyerHA:
			return DD_Ha;
		case ID.Ship.DestroyerNI:
			return DD_Ni;
		//Hime
		case ID.Ship.AirfieldHime:
			return Hime_Airfield;
		case ID.Ship.BattleshipHime:
			return Hime_Battleship;
		case ID.Ship.HarbourHime:
			return Hime_Harbour;
		case ID.Ship.NorthernHime:
			return Hime_Northern;
		//SS
		case ID.Ship.SubmarineSO:
			return SS_So;
		//WD
		case ID.Ship.CarrierWD:
			return WD_Carrier;
			
		//Hostile Ship
		//BB
		case ID.Ship.BattleshipNagato:
			return BB_Nagato;
		case ID.Ship.BattleshipYamato:
			return BB_Yamato;
		//CV
		case ID.Ship.CarrierAkagi:
			return CV_Akagi;
		case ID.Ship.CarrierKaga:
			return CV_Kaga;	
		//DD
		case ID.Ship.DestroyerIkazuchi:
			return DD_Ikazuchi;
		case ID.Ship.DestroyerInazuma:
			return DD_Inazuma;
		case ID.Ship.DestroyerShimakaze:
			return DD_Shimakaze;
		//SS
		case ID.Ship.SubmarineRo500:
			return SS_Ro500;
		case ID.Ship.SubmarineU511:
			return SS_U511;
		}
		
		return null;
	}
	
	//get leash height
	protected float[] getLeashHeight() {
		return Values.ShipLeashHeight.get((short)shipClass);
	}
	
	//render lead
	@Override
	protected void func_110827_b(EntityLiving host, double d, double d1, double d2, float f, float f1)
	{
        Entity entity = host.getLeashedToEntity();
        float[] leashHeight = getLeashHeight();

//        if(entity != null && leashHeight != null)
        if (entity != null)
        {
        	IShipEmotion host1 = (IShipEmotion) host;
        	
        	if (host1.getIsRiding())
        	{
        		if (host1.getIsSitting())
        		{
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashHeight[2];
        		}
        		else
        		{
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashHeight[1];
        		}
        	}
        	else
        	{
        		if (host1.getIsSitting())
        		{
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashHeight[3];
//        			d1 -= (1.6D - (double)host.height) * 0.5D + 0.8D;
        		}
        		else
        		{
        			d1 -= (1.6D - (double)host.height) * 0.5D + leashHeight[4];
//        			d1 -= (1.6D - (double)host.height) * 0.5D + 0.3D;
        		}
        	}
            
            Tessellator tessellator = Tessellator.instance;
            double d3 = this.interp((double)entity.prevRotationYaw, (double)entity.rotationYaw, (double)(f1 * 0.5F)) * 0.01745329238474369D;
            double d4 = this.interp((double)entity.prevRotationPitch, (double)entity.rotationPitch, (double)(f1 * 0.5F)) * 0.01745329238474369D;
            double d5 = Math.cos(d3);
            double d6 = Math.sin(d3);
            double d7 = Math.sin(d4);

            if (entity instanceof EntityHanging)
            {
                d5 = 0.0D;
                d6 = 0.0D;
                d7 = -1.0D;
            }

            double d8 = Math.cos(d4);
            double d9 = this.interp(entity.prevPosX, entity.posX, (double)f1) - d5 * 0.7D - d6 * 0.5D * d8;
            double d10 = this.interp(entity.prevPosY + (double)entity.getEyeHeight() * 0.7D, entity.posY + (double)entity.getEyeHeight() * 0.7D, (double)f1) - d7 * 0.5D - 0.25D;
            double d11 = this.interp(entity.prevPosZ, entity.posZ, (double)f1) - d6 * 0.7D + d5 * 0.5D * d8;
            double d12 = this.interp((double)host.prevRenderYawOffset, (double)host.renderYawOffset, (double)f1) * 0.01745329238474369D + (Math.PI / 2D);

//            leashHeight[0] = 0.2D;
            d5 = Math.cos(d12) * (double)host.width * leashHeight[0];
            d6 = Math.sin(d12) * (double)host.width * leashHeight[0];
            
            double d13 = this.interp(host.prevPosX, host.posX, (double)f1) + d5;
            double d14 = this.interp(host.prevPosY, host.posY, (double)f1);
            double d15 = this.interp(host.prevPosZ, host.posZ, (double)f1) + d6;
            d += d5;
            d2 += d6;
            double d16 = (double)((float)(d9 - d13));
            double d17 = (double)((float)(d10 - d14));
            double d18 = (double)((float)(d11 - d15));
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            boolean flag = true;
            double d19 = 0.025D;
            tessellator.startDrawing(5);
            int i;
            float f2;

            for (i = 0; i <= 24; ++i)
            {
                if (i % 2 == 0)
                {
                    tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                }
                else
                {
                    tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
                }

                f2 = (float)i / 24.0F;
                tessellator.addVertex(d + d16 * (double)f2 + 0.0D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F), d2 + d18 * (double)f2);
                tessellator.addVertex(d + d16 * (double)f2 + 0.025D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F) + 0.025D, d2 + d18 * (double)f2);
            }

            tessellator.draw();
            tessellator.startDrawing(5);

            for (i = 0; i <= 24; ++i)
            {
                if (i % 2 == 0)
                {
                    tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                }
                else
                {
                    tessellator.setColorRGBA_F(0.35F, 0.28F, 0.21000001F, 1.0F);
                }

                f2 = (float)i / 24.0F;
                tessellator.addVertex(d + d16 * (double)f2 + 0.0D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F) + 0.025D, d2 + d18 * (double)f2);
                tessellator.addVertex(d + d16 * (double)f2 + 0.025D, d1 + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F), d2 + d18 * (double)f2 + 0.025D);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }
	
	//interpolation
	private double interp(double par1, double par2, double par3)
	{
        return par1 + (par2 - par1) * par3;
    }
	

}
