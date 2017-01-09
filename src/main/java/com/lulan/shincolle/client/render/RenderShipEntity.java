package com.lulan.shincolle.client.render;

import javax.annotation.Nonnull;

import com.lulan.shincolle.client.model.ModelAirfieldHime;
import com.lulan.shincolle.client.model.ModelBattleshipHime;
import com.lulan.shincolle.client.model.ModelBattleshipNagato;
import com.lulan.shincolle.client.model.ModelBattleshipRe;
import com.lulan.shincolle.client.model.ModelBattleshipTa;
import com.lulan.shincolle.client.model.ModelBattleshipYamato;
import com.lulan.shincolle.client.model.ModelCarrierAkagi;
import com.lulan.shincolle.client.model.ModelCarrierHime;
import com.lulan.shincolle.client.model.ModelCarrierKaga;
import com.lulan.shincolle.client.model.ModelCarrierWDemon;
import com.lulan.shincolle.client.model.ModelCarrierWo;
import com.lulan.shincolle.client.model.ModelCruiserTenryuu;
import com.lulan.shincolle.client.model.ModelDestroyerAkatsuki;
import com.lulan.shincolle.client.model.ModelDestroyerHa;
import com.lulan.shincolle.client.model.ModelDestroyerHibiki;
import com.lulan.shincolle.client.model.ModelDestroyerI;
import com.lulan.shincolle.client.model.ModelDestroyerIkazuchi;
import com.lulan.shincolle.client.model.ModelDestroyerInazuma;
import com.lulan.shincolle.client.model.ModelDestroyerNi;
import com.lulan.shincolle.client.model.ModelDestroyerRo;
import com.lulan.shincolle.client.model.ModelDestroyerShimakaze;
import com.lulan.shincolle.client.model.ModelHarbourHime;
import com.lulan.shincolle.client.model.ModelHeavyCruiserNe;
import com.lulan.shincolle.client.model.ModelHeavyCruiserRi;
import com.lulan.shincolle.client.model.ModelNorthernHime;
import com.lulan.shincolle.client.model.ModelSubmKa;
import com.lulan.shincolle.client.model.ModelSubmRo500;
import com.lulan.shincolle.client.model.ModelSubmSo;
import com.lulan.shincolle.client.model.ModelSubmU511;
import com.lulan.shincolle.client.model.ModelSubmYo;
import com.lulan.shincolle.client.model.ModelTransportWa;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderShipEntity extends RenderBasic
{

	//textures
	//AP
	private static final ResourceLocation TEX_AP_Wa = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityTransportWa.png");
	private static final ModelBase MD_AP_Wa = new ModelTransportWa();
	//BB
	private static final ResourceLocation TEX_BB_Re = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipRe.png");
	private static final ModelBase MD_BB_Re = new ModelBattleshipRe();
	private static final ResourceLocation TEX_BB_Ta = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipTa.png");
	private static final ModelBase MD_BB_Ta = new ModelBattleshipTa();
	//CA
	private static final ResourceLocation TEX_CA_Ri = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHeavyCruiserRi.png");
	private static final ModelBase MD_CA_Ri = new ModelHeavyCruiserRi();
	private static final ResourceLocation TEX_CA_Ne = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHeavyCruiserNe.png");
	private static final ModelBase MD_CA_Ne = new ModelHeavyCruiserNe();
	//CV
	private static final ResourceLocation TEX_CV_Wo = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierWo.png");
	private static final ModelBase MD_CV_Wo = new ModelCarrierWo();
	//DD
	private static final ResourceLocation TEX_DD_I = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerI.png");
	private static final ModelBase MD_DD_I = new ModelDestroyerI();
	private static final ResourceLocation TEX_DD_Ro = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerRo.png");
	private static final ModelBase MD_DD_Ro = new ModelDestroyerRo();
	private static final ResourceLocation TEX_DD_Ha = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerHa.png");
	private static final ModelBase MD_DD_Ha = new ModelDestroyerHa();
	private static final ResourceLocation TEX_DD_Ni = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerNi.png");
	private static final ModelBase MD_DD_Ni = new ModelDestroyerNi();
	//Hime
	private static final ResourceLocation TEX_Hime_Airfield = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirfieldHime.png");
	private static final ModelBase MD_Hime_Airfield = new ModelAirfieldHime();
	private static final ResourceLocation TEX_Hime_Battleship = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipHime.png");
	private static final ModelBase MD_Hime_Battleship = new ModelBattleshipHime();
	private static final ResourceLocation TEX_Hime_Carrier = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierHime.png");
	private static final ModelBase MD_Hime_Carrier = new ModelCarrierHime();
	private static final ResourceLocation TEX_Hime_Harbour = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHarbourHime.png");
	private static final ModelBase MD_Hime_Harbour = new ModelHarbourHime();
	private static final ResourceLocation TEX_Hime_Northern = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityNorthernHime.png");
	private static final ModelBase MD_Hime_Northern = new ModelNorthernHime();
	//SS
	private static final ResourceLocation TEX_SS_Ka = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmKa.png");
	private static final ModelBase MD_SS_Ka = new ModelSubmKa();
	private static final ResourceLocation TEX_SS_Yo = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmYo.png");
	private static final ModelBase MD_SS_Yo = new ModelSubmYo();
	private static final ResourceLocation TEX_SS_So = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmSo.png");
	private static final ModelBase MD_SS_So = new ModelSubmSo();
	//WD
	private static final ResourceLocation TEX_WD_Carrier = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierWDemon.png");
	private static final ModelBase MD_WD_Carrier = new ModelCarrierWDemon();
	//Hostile Sip
	//BB
	private static final ResourceLocation TEX_BB_Nagato = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipNagato.png");
	private static final ModelBase MD_BB_Nagato = new ModelBattleshipNagato();
	private static final ResourceLocation TEX_BB_Yamato = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipYamato.png");
	private static final ModelBase MD_BB_Yamato = new ModelBattleshipYamato();
	//CV
	private static final ResourceLocation TEX_CV_Akagi = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierAkagi.png");
	private static final ModelBase MD_CV_Akagi = new ModelCarrierAkagi();
	private static final ResourceLocation TEX_CV_Kaga = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierKaga.png");
	private static final ModelBase MD_CV_Kaga = new ModelCarrierKaga();
	//CL
	private static final ResourceLocation TEX_CL_Tenryuu = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCruiserTenryuu.png");
	private static final ModelBase MD_CL_Tenryuu = new ModelCruiserTenryuu();
	//DD
	private static final ResourceLocation TEX_DD_Akatsuki = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerAkatsuki.png");
	private static final ModelBase MD_DD_Akatsuki = new ModelDestroyerAkatsuki();
	private static final ResourceLocation TEX_DD_Hibiki = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerHibiki.png");
	private static final ModelBase MD_DD_Hibiki = new ModelDestroyerHibiki();
	private static final ResourceLocation TEX_DD_Ikazuchi = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerIkazuchi.png");
	private static final ModelBase MD_DD_Ikazuchi = new ModelDestroyerIkazuchi();
	private static final ResourceLocation TEX_DD_Inazuma = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerInazuma.png");
	private static final ModelBase MD_DD_Inazuma = new ModelDestroyerInazuma();
	private static final ResourceLocation TEX_DD_Shimakaze = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerShimakaze.png");
	private static final ModelBase MD_DD_Shimakaze = new ModelDestroyerShimakaze();
	//SS
	private static final ResourceLocation TEX_SS_Ro500 = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmRo500.png");
	private static final ModelBase MD_SS_Ro500 = new ModelSubmRo500();
	private static final ResourceLocation TEX_SS_U511 = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmU511.png");
	private static final ModelBase MD_SS_U511 = new ModelSubmU511();
	
	//factory
	public static final FactoryDefault FACTORY_SHIP = new FactoryDefault();
	
	
    public RenderShipEntity(RenderManager rm)
    {
        super(rm);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity)
    {
		switch (this.shipClass)
		{
		//AP
		case ID.Ship.TransportWA:
			return TEX_AP_Wa;
		//BB
		case ID.Ship.BattleshipTA:
			return TEX_BB_Ta;
		case ID.Ship.BattleshipRE:
			return TEX_BB_Re;
		//CL
		case ID.Ship.HeavyCruiserRI:
			return TEX_CA_Ri;
		case ID.Ship.HeavyCruiserNE:
			return TEX_CA_Ne;	
		//CV
		case ID.Ship.CarrierWO:
			return TEX_CV_Wo;
		//DD
		case ID.Ship.DestroyerI:
			return TEX_DD_I;
		case ID.Ship.DestroyerRO:
			return TEX_DD_Ro;
		case ID.Ship.DestroyerHA:
			return TEX_DD_Ha;
		case ID.Ship.DestroyerNI:
			return TEX_DD_Ni;
		//Hime
		case ID.Ship.AirfieldHime:
			return TEX_Hime_Airfield;
		case ID.Ship.BattleshipHime:
			return TEX_Hime_Battleship;
		case ID.Ship.CarrierHime:
			return TEX_Hime_Carrier;
		case ID.Ship.HarbourHime:
			return TEX_Hime_Harbour;
		case ID.Ship.NorthernHime:
			return TEX_Hime_Northern;
		//SS
		case ID.Ship.SubmarineKA:
			return TEX_SS_Ka;
		case ID.Ship.SubmarineSO:
			return TEX_SS_So;
		case ID.Ship.SubmarineYO:
			return TEX_SS_Yo;
		//WD
		case ID.Ship.CarrierWD:
			return TEX_WD_Carrier;
			
		//Hostile Ship
		//BB
		case ID.Ship.BattleshipNagato:
			return TEX_BB_Nagato;
		case ID.Ship.BattleshipYamato:
			return TEX_BB_Yamato;
		//CV
		case ID.Ship.CarrierAkagi:
			return TEX_CV_Akagi;
		case ID.Ship.CarrierKaga:
			return TEX_CV_Kaga;
		case ID.Ship.LightCruiserTenryuu:
			return TEX_CL_Tenryuu;
//		case ID.Ship.LightCruiserTatsuta:
//			return TEX_CL_Tatsuta;
		//DD
		case ID.Ship.DestroyerAkatsuki:
			return TEX_DD_Akatsuki;
		case ID.Ship.DestroyerHibiki:
			return TEX_DD_Hibiki;
		case ID.Ship.DestroyerIkazuchi:
			return TEX_DD_Ikazuchi;
		case ID.Ship.DestroyerInazuma:
			return TEX_DD_Inazuma;
		case ID.Ship.DestroyerShimakaze:
			return TEX_DD_Shimakaze;
		//SS
		case ID.Ship.SubmarineRo500:
			return TEX_SS_Ro500;
		case ID.Ship.SubmarineU511:
			return TEX_SS_U511;
		default:	//default texture
			return TEX_DD_I;
		}//end switch
    }
    
    @Override
    protected void setModel()
    {
		switch (this.shipClass)
		{
		//AP
		case ID.Ship.TransportWA:
			this.mainModel = MD_AP_Wa;
		break;
		//BB
		case ID.Ship.BattleshipTA:
			this.mainModel = MD_BB_Ta;
		break;
		case ID.Ship.BattleshipRE:
			this.mainModel = MD_BB_Re;
		break;
		//CL
		case ID.Ship.HeavyCruiserRI:
			this.mainModel = MD_CA_Ri;
		break;
		case ID.Ship.HeavyCruiserNE:
			this.mainModel = MD_CA_Ne;
		break;
		//CV
		case ID.Ship.CarrierWO:
			this.mainModel = MD_CV_Wo;
		break;
		//DD
		case ID.Ship.DestroyerI:
			this.mainModel = MD_DD_I;
		break;
		case ID.Ship.DestroyerRO:
			this.mainModel = MD_DD_Ro;
		break;
		case ID.Ship.DestroyerHA:
			this.mainModel = MD_DD_Ha;
		break;
		case ID.Ship.DestroyerNI:
			this.mainModel = MD_DD_Ni;
		break;
		//Hime
		case ID.Ship.AirfieldHime:
			this.mainModel = MD_Hime_Airfield;
		break;
		case ID.Ship.BattleshipHime:
			this.mainModel = MD_Hime_Battleship;
		break;
		case ID.Ship.CarrierHime:
			this.mainModel = MD_Hime_Carrier;
		break;
		case ID.Ship.HarbourHime:
			this.mainModel = MD_Hime_Harbour;
		break;
		case ID.Ship.NorthernHime:
			this.mainModel = MD_Hime_Northern;
		break;
		//SS
		case ID.Ship.SubmarineKA:
			this.mainModel = MD_SS_Ka;
		break;
		case ID.Ship.SubmarineSO:
			this.mainModel = MD_SS_So;
		break;
		case ID.Ship.SubmarineYO:
			this.mainModel = MD_SS_Yo;
		break;
		//WD
		case ID.Ship.CarrierWD:
			this.mainModel = MD_WD_Carrier;
		break;
		//Hostile Ship
		//BB
		case ID.Ship.BattleshipNagato:
			this.mainModel = MD_BB_Nagato;
		break;
		case ID.Ship.BattleshipYamato:
			this.mainModel = MD_BB_Yamato;
		break;
		//CV
		case ID.Ship.CarrierAkagi:
			this.mainModel = MD_CV_Akagi;
		break;
		case ID.Ship.CarrierKaga:
			this.mainModel = MD_CV_Kaga;
		break;
		case ID.Ship.LightCruiserTenryuu:
			this.mainModel = MD_CL_Tenryuu;
		break;
//		case ID.Ship.LightCruiserTatsuta:
//			this.mainModel = MD_CL_Tatsuta;
//		break;
		//DD
		case ID.Ship.DestroyerAkatsuki:
			this.mainModel = MD_DD_Akatsuki;
		break;
		case ID.Ship.DestroyerHibiki:
			this.mainModel = MD_DD_Hibiki;
		break;
		case ID.Ship.DestroyerIkazuchi:
			this.mainModel = MD_DD_Ikazuchi;
		break;
		case ID.Ship.DestroyerInazuma:
			this.mainModel = MD_DD_Inazuma;
		break;
		case ID.Ship.DestroyerShimakaze:
			this.mainModel = MD_DD_Shimakaze;
		break;
		//SS
		case ID.Ship.SubmarineRo500:
			this.mainModel = MD_SS_Ro500;
		break;
		case ID.Ship.SubmarineU511:
			this.mainModel = MD_SS_U511;
		break;
		default:	//default model
			this.mainModel = MD_DD_I;
		break;
		}//end switch
    }
    
    /** set shadow size */
    @Override
    protected void setShadowSize()
    {
		switch (this.shipClass)
		{
		case ID.Ship.NorthernHime:
		case ID.Ship.SubmarineKA:
		case ID.Ship.SubmarineSO:
		case ID.Ship.SubmarineYO:
		case ID.Ship.DestroyerAkatsuki:
		case ID.Ship.DestroyerHibiki:
		case ID.Ship.DestroyerIkazuchi:
		case ID.Ship.DestroyerInazuma:
		case ID.Ship.DestroyerShimakaze:
		case ID.Ship.SubmarineRo500:
		case ID.Ship.SubmarineU511:
		case ID.Ship.LightCruiserTenryuu:
		case ID.Ship.LightCruiserTatsuta:
			this.shadowSize = 0.5F;
		break;
		case ID.Ship.HarbourHime:
			this.shadowSize = 0.8F;
		break;
		case ID.Ship.DestroyerI:
		case ID.Ship.DestroyerRO:
		case ID.Ship.DestroyerHA:
		case ID.Ship.DestroyerNI:
			this.shadowSize = 0.9F;
		break;
		case ID.Ship.CarrierWO:
			this.shadowSize = 1F;
		break;
		default:	//default size
			this.shadowSize = 0.7F;
		break;
		}//end switch
    }
    
	//get leash height
	protected float[] getLeashHeight()
	{
		float[] f = Values.ShipLeashHeight.get((short) this.shipClass);
		
		if (f == null)
		{
			return new float[] {0.8F, 0.8F, 0.8F, 0.8F, 0.8F};
		}
		
		return f;
	}
	
	//render leash
	@Override
    protected void renderLeash(EntityLiving host, double x, double y, double z, float yaw, float parTick)
    {
        Entity entity = host.getLeashedToEntity();
        float[] leashHeight = getLeashHeight();
        
        if (entity != null)
        {
        	IShipEmotion host1 = (IShipEmotion) host;
        	
        	//get leash point height
        	if (host1.getIsRiding())
        	{
        		if (host1.getIsSitting())
        		{
        			y -= (1.6D - (double)host.height) * 0.5D + leashHeight[2];
        		}
        		else
        		{
        			y -= (1.6D - (double)host.height) * 0.5D + leashHeight[1];
        		}
        	}
        	else
        	{
        		if (host1.getIsSitting())
        		{
        			y -= (1.6D - (double)host.height) * 0.5D + leashHeight[3];
//        			d1 -= (1.6D - (double)host.height) * 0.5D + 0.8D;
        		}
        		else
        		{
        			y -= (1.6D - (double)host.height) * 0.5D + leashHeight[4];
//        			d1 -= (1.6D - (double)host.height) * 0.5D + 0.3D;
        		}
        	}
            
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexbuffer = tessellator.getBuffer();
            double d3 = this.interp((double)entity.prevRotationYaw, (double)entity.rotationYaw, (double)(parTick * 0.5F)) * 0.01745329238474369D;
            double d4 = this.interp((double)entity.prevRotationPitch, (double)entity.rotationPitch, (double)(parTick * 0.5F)) * 0.01745329238474369D;
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
            double d9 = this.interp(entity.prevPosX, entity.posX, (double)parTick) - d5 * 0.7D - d6 * 0.5D * d8;
            double d10 = this.interp(entity.prevPosY + (double)entity.getEyeHeight() * 0.7D, entity.posY + (double)entity.getEyeHeight() * 0.7D, (double)parTick) - d7 * 0.5D - 0.25D;
            double d11 = this.interp(entity.prevPosZ, entity.posZ, (double)parTick) - d6 * 0.7D + d5 * 0.5D * d8;
            double d12 = this.interp((double)host.prevRenderYawOffset, (double)host.renderYawOffset, (double)parTick) * 0.01745329238474369D + (Math.PI / 2D);

            //get leash point width
//            leashHeight[0] = 0.2D;
            d5 = Math.cos(d12) * (double)host.width * leashHeight[0];
            d6 = Math.sin(d12) * (double)host.width * leashHeight[0];
            
            double d13 = this.interp(host.prevPosX, host.posX, (double)parTick) + d5;
            double d14 = this.interp(host.prevPosY, host.posY, (double)parTick);
            double d15 = this.interp(host.prevPosZ, host.posZ, (double)parTick) + d6;
            x += d5;
            z += d6;
            double d16 = (double)((float)(d9 - d13));
            double d17 = (double)((float)(d10 - d14));
            double d18 = (double)((float)(d11 - d15));
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            
            //draw leash front
            vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);
            for (int j = 0; j <= 24; ++j)
            {
                float f = 0.5F;
                float f1 = 0.4F;
                float f2 = 0.3F;

                if (j % 2 == 0)
                {
                    f *= 0.7F;
                    f1 *= 0.7F;
                    f2 *= 0.7F;
                }

                float f3 = (float)j / 24.0F;
                vertexbuffer.pos(x + d16 * (double)f3 + 0.0D, y + d17 * (double)(f3 * f3 + f3) * 0.5D + (double)((24.0F - (float)j) / 18.0F + 0.125F), z + d18 * (double)f3).color(f, f1, f2, 1.0F).endVertex();
                vertexbuffer.pos(x + d16 * (double)f3 + 0.025D, y + d17 * (double)(f3 * f3 + f3) * 0.5D + (double)((24.0F - (float)j) / 18.0F + 0.125F) + 0.025D, z + d18 * (double)f3).color(f, f1, f2, 1.0F).endVertex();
            }
            tessellator.draw();
            
            //draw leash back
            vertexbuffer.begin(5, DefaultVertexFormats.POSITION_COLOR);
            for (int k = 0; k <= 24; ++k)
            {
                float f4 = 0.5F;
                float f5 = 0.4F;
                float f6 = 0.3F;

                if (k % 2 == 0)
                {
                    f4 *= 0.7F;
                    f5 *= 0.7F;
                    f6 *= 0.7F;
                }

                float f7 = (float)k / 24.0F;
                vertexbuffer.pos(x + d16 * (double)f7 + 0.0D, y + d17 * (double)(f7 * f7 + f7) * 0.5D + (double)((24.0F - (float)k) / 18.0F + 0.125F) + 0.025D, z + d18 * (double)f7).color(f4, f5, f6, 1.0F).endVertex();
                vertexbuffer.pos(x + d16 * (double)f7 + 0.025D, y + d17 * (double)(f7 * f7 + f7) * 0.5D + (double)((24.0F - (float)k) / 18.0F + 0.125F), z + d18 * (double)f7 + 0.025D).color(f4, f5, f6, 1.0F).endVertex();
            }
            tessellator.draw();
            
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.enableCull();
        }
    }
	
    public static class FactoryDefault implements IRenderFactory<EntityLiving>
    {
        @Override
        public Render<? super EntityLiving> createRenderFor(RenderManager rm)
        {
            return new RenderShipEntity(rm);
        }
    }
	
    
}