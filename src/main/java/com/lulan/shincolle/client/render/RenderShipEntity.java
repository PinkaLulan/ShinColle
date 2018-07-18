package com.lulan.shincolle.client.render;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import com.lulan.shincolle.client.model.ModelAirfieldHime;
import com.lulan.shincolle.client.model.ModelBBHaruna;
import com.lulan.shincolle.client.model.ModelBBHiei;
import com.lulan.shincolle.client.model.ModelBBKirishima;
import com.lulan.shincolle.client.model.ModelBBKongou;
import com.lulan.shincolle.client.model.ModelBattleshipHime;
import com.lulan.shincolle.client.model.ModelBattleshipNagato;
import com.lulan.shincolle.client.model.ModelBattleshipRe;
import com.lulan.shincolle.client.model.ModelBattleshipRu;
import com.lulan.shincolle.client.model.ModelBattleshipTa;
import com.lulan.shincolle.client.model.ModelBattleshipYamato;
import com.lulan.shincolle.client.model.ModelCAHime;
import com.lulan.shincolle.client.model.ModelCarrierAkagi;
import com.lulan.shincolle.client.model.ModelCarrierHime;
import com.lulan.shincolle.client.model.ModelCarrierKaga;
import com.lulan.shincolle.client.model.ModelCarrierWDemon;
import com.lulan.shincolle.client.model.ModelCarrierWo;
import com.lulan.shincolle.client.model.ModelCruiserAtago;
import com.lulan.shincolle.client.model.ModelCruiserTakao;
import com.lulan.shincolle.client.model.ModelCruiserTatsuta;
import com.lulan.shincolle.client.model.ModelCruiserTenryuu;
import com.lulan.shincolle.client.model.ModelDestroyerAkatsuki;
import com.lulan.shincolle.client.model.ModelDestroyerHa;
import com.lulan.shincolle.client.model.ModelDestroyerHibiki;
import com.lulan.shincolle.client.model.ModelDestroyerHime;
import com.lulan.shincolle.client.model.ModelDestroyerI;
import com.lulan.shincolle.client.model.ModelDestroyerIkazuchi;
import com.lulan.shincolle.client.model.ModelDestroyerInazuma;
import com.lulan.shincolle.client.model.ModelDestroyerNi;
import com.lulan.shincolle.client.model.ModelDestroyerRo;
import com.lulan.shincolle.client.model.ModelDestroyerShimakaze;
import com.lulan.shincolle.client.model.ModelHarbourHime;
import com.lulan.shincolle.client.model.ModelHeavyCruiserNe;
import com.lulan.shincolle.client.model.ModelHeavyCruiserRi;
import com.lulan.shincolle.client.model.ModelIsolatedHime;
import com.lulan.shincolle.client.model.ModelMidwayHime;
import com.lulan.shincolle.client.model.ModelNorthernHime;
import com.lulan.shincolle.client.model.ModelSSNH;
import com.lulan.shincolle.client.model.ModelSubmHime;
import com.lulan.shincolle.client.model.ModelSubmKa;
import com.lulan.shincolle.client.model.ModelSubmRo500;
import com.lulan.shincolle.client.model.ModelSubmSo;
import com.lulan.shincolle.client.model.ModelSubmU511;
import com.lulan.shincolle.client.model.ModelSubmYo;
import com.lulan.shincolle.client.model.ModelTransportWa;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.other.EntityAirplaneTakoyaki;
import com.lulan.shincolle.proxy.ClientProxy;
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
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderShipEntity extends RenderBasic
{

	//textures
	//AP
	public static final ResourceLocation TEX_AP_Wa = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityTransportWa.png");
	public static final ModelBase MD_AP_Wa = new ModelTransportWa();
	//BB
	public static final ResourceLocation TEX_BB_Re = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipRe.png");
	public static final ModelBase MD_BB_Re = new ModelBattleshipRe();
	public static final ResourceLocation TEX_BB_Ru = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipRu.png");
	public static final ModelBase MD_BB_Ru = new ModelBattleshipRu();
	public static final ResourceLocation TEX_BB_Ta = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipTa.png");
	public static final ModelBase MD_BB_Ta = new ModelBattleshipTa();
	//CA
	public static final ResourceLocation TEX_CA_Ri = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHeavyCruiserRi.png");
	public static final ModelBase MD_CA_Ri = new ModelHeavyCruiserRi();
	public static final ResourceLocation TEX_CA_Ne = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHeavyCruiserNe.png");
	public static final ModelBase MD_CA_Ne = new ModelHeavyCruiserNe();
	//CV
	public static final ResourceLocation TEX_CV_Wo = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierWo.png");
	public static final ModelBase MD_CV_Wo = new ModelCarrierWo();
	//DD
	public static final ResourceLocation TEX_DD_I = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerI.png");
	public static final ModelBase MD_DD_I = new ModelDestroyerI();
	public static final ResourceLocation TEX_DD_Ro = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerRo.png");
	public static final ModelBase MD_DD_Ro = new ModelDestroyerRo();
	public static final ResourceLocation TEX_DD_Ha = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerHa.png");
	public static final ModelBase MD_DD_Ha = new ModelDestroyerHa();
	public static final ResourceLocation TEX_DD_Ni = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerNi.png");
	public static final ModelBase MD_DD_Ni = new ModelDestroyerNi();
	//Hime
	public static final ResourceLocation TEX_Hime_Airfield = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirfieldHime.png");
	public static final ModelBase MD_Hime_Airfield = new ModelAirfieldHime();
	public static final ResourceLocation TEX_Hime_Battleship = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipHime.png");
	public static final ModelBase MD_Hime_Battleship = new ModelBattleshipHime();
	public static final ResourceLocation TEX_Hime_Destroyer = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerHime.png");
	public static final ModelBase MD_Hime_Destroyer = new ModelDestroyerHime();
	public static final ResourceLocation TEX_Hime_Carrier = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierHime.png");
	public static final ModelBase MD_Hime_Carrier = new ModelCarrierHime();
	public static final ResourceLocation TEX_Hime_CA = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCAHime.png");
	public static final ModelBase MD_Hime_CA = new ModelCAHime();
	public static final ResourceLocation TEX_Hime_Harbour = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityHarbourHime.png");
	public static final ModelBase MD_Hime_Harbour = new ModelHarbourHime();
	public static final ResourceLocation TEX_Hime_Isolated = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityIsolatedHime.png");
	public static final ModelBase MD_Hime_Isolated = new ModelIsolatedHime();
	public static final ResourceLocation TEX_Hime_Midway = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMidwayHime.png");
	public static final ModelBase MD_Hime_Midway = new ModelMidwayHime();
	public static final ResourceLocation TEX_Hime_Northern = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityNorthernHime.png");
	public static final ModelBase MD_Hime_Northern = new ModelNorthernHime();
	public static final ResourceLocation TEX_Hime_Subm = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmHime.png");
	public static final ModelBase MD_Hime_Subm = new ModelSubmHime();
	public static final ResourceLocation TEX_Hime_SubmNew = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmHimeNew.png");
	public static final ModelBase MD_Hime_SubmNew = new ModelSSNH();
	//SS
	public static final ResourceLocation TEX_SS_Ka = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmKa.png");
	public static final ModelBase MD_SS_Ka = new ModelSubmKa();
	public static final ResourceLocation TEX_SS_Yo = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmYo.png");
	public static final ModelBase MD_SS_Yo = new ModelSubmYo();
	public static final ResourceLocation TEX_SS_So = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmSo.png");
	public static final ModelBase MD_SS_So = new ModelSubmSo();
	//WD
	public static final ResourceLocation TEX_WD_Carrier = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierWDemon.png");
	public static final ModelBase MD_WD_Carrier = new ModelCarrierWDemon();
	//Hostile Sip
	//BB
	public static final ResourceLocation TEX_BB_Nagato = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipNagato.png");
	public static final ModelBase MD_BB_Nagato = new ModelBattleshipNagato();
	public static final ResourceLocation TEX_BB_Yamato = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBattleshipYamato.png");
	public static final ModelBase MD_BB_Yamato = new ModelBattleshipYamato();
	public static final ResourceLocation TEX_BB_Kongou = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBBKongou.png");
	public static final ModelBase MD_BB_Kongou = new ModelBBKongou();
	public static final ResourceLocation TEX_BB_Hiei = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBBHiei.png");
	public static final ModelBase MD_BB_Hiei = new ModelBBHiei();
	public static final ResourceLocation TEX_BB_Haruna = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBBHaruna.png");
	public static final ModelBase MD_BB_Haruna = new ModelBBHaruna();
	public static final ResourceLocation TEX_BB_Kirishima = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityBBKirishima.png");
	public static final ModelBase MD_BB_Kirishima = new ModelBBKirishima();
	//CV
	public static final ResourceLocation TEX_CV_Akagi = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierAkagi.png");
	public static final ModelBase MD_CV_Akagi = new ModelCarrierAkagi();
	public static final ResourceLocation TEX_CV_Kaga = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCarrierKaga.png");
	public static final ModelBase MD_CV_Kaga = new ModelCarrierKaga();
	//CL
	public static final ResourceLocation TEX_CL_Tenryuu = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCruiserTenryuu.png");
	public static final ModelBase MD_CL_Tenryuu = new ModelCruiserTenryuu();
	public static final ResourceLocation TEX_CL_Tatsuta = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCruiserTatsuta.png");
	public static final ModelBase MD_CL_Tatsuta = new ModelCruiserTatsuta();
	//CA
	public static final ResourceLocation TEX_CA_Atago = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCruiserAtago.png");
	public static final ModelBase MD_CA_Atago = new ModelCruiserAtago();
	public static final ResourceLocation TEX_CA_Takao = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityCruiserTakao.png");
	public static final ModelBase MD_CA_Takao = new ModelCruiserTakao();
	//DD
	public static final ResourceLocation TEX_DD_Akatsuki = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerAkatsuki.png");
	public static final ModelBase MD_DD_Akatsuki = new ModelDestroyerAkatsuki();
	public static final ResourceLocation TEX_DD_Hibiki = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerHibiki.png");
	public static final ModelBase MD_DD_Hibiki = new ModelDestroyerHibiki();
	public static final ResourceLocation TEX_DD_Ikazuchi = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerIkazuchi.png");
	public static final ModelBase MD_DD_Ikazuchi = new ModelDestroyerIkazuchi();
	public static final ResourceLocation TEX_DD_Inazuma = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerInazuma.png");
	public static final ModelBase MD_DD_Inazuma = new ModelDestroyerInazuma();
	public static final ResourceLocation TEX_DD_Shimakaze = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerShimakaze.png");
	public static final ModelBase MD_DD_Shimakaze = new ModelDestroyerShimakaze();
	//SS
	public static final ResourceLocation TEX_SS_Ro500 = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmRo500.png");
	public static final ModelBase MD_SS_Ro500 = new ModelSubmRo500();
	public static final ResourceLocation TEX_SS_U511 = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntitySubmU511.png");
	public static final ModelBase MD_SS_U511 = new ModelSubmU511();
	
	//factory
	public static final FactoryDefault FACTORY_SHIP = new FactoryDefault();
	
	
    public RenderShipEntity(RenderManager rm)
    {
        super(rm);
        this.addLayer(new LayerShipHeldItem(this));
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityLiving entity)
    {
		switch (this.textuerID)
		{
		//AP
		case ID.ShipClass.APWA:
			return TEX_AP_Wa;
		//BB
		case ID.ShipClass.BBRU:
			return TEX_BB_Ru;
		case ID.ShipClass.BBTA:
			return TEX_BB_Ta;
		case ID.ShipClass.BBRE:
			return TEX_BB_Re;
		//CL
		case ID.ShipClass.CARI:
			return TEX_CA_Ri;
		case ID.ShipClass.CANE:
			return TEX_CA_Ne;	
		//CV
		case ID.ShipClass.CVWO:
			return TEX_CV_Wo;
		//DD
		case ID.ShipClass.DDI:
			return TEX_DD_I;
		case ID.ShipClass.DDRO:
			return TEX_DD_Ro;
		case ID.ShipClass.DDHA:
			return TEX_DD_Ha;
		case ID.ShipClass.DDNI:
			return TEX_DD_Ni;
		//Hime
		case ID.ShipClass.AirfieldHime:
			return TEX_Hime_Airfield;
		case ID.ShipClass.BBHime:
			return TEX_Hime_Battleship;
		case ID.ShipClass.DDHime:
			return TEX_Hime_Destroyer;
		case ID.ShipClass.CVHime:
			return TEX_Hime_Carrier;
		case ID.ShipClass.CAHime:
			return TEX_Hime_CA;
		case ID.ShipClass.HarbourHime:
			return TEX_Hime_Harbour;
		case ID.ShipClass.IsolatedHime:
			return TEX_Hime_Isolated;
		case ID.ShipClass.MidwayHime:
			return TEX_Hime_Midway;
		case ID.ShipClass.NorthernHime:
			return TEX_Hime_Northern;
		case ID.ShipClass.SSHime:
			return TEX_Hime_Subm;
		case ID.ShipClass.SSNH:
			return TEX_Hime_SubmNew;
		//SS
		case ID.ShipClass.SSKA:
			return TEX_SS_Ka;
		case ID.ShipClass.SSSO:
			return TEX_SS_So;
		case ID.ShipClass.SSYO:
			return TEX_SS_Yo;
		//WD
		case ID.ShipClass.CVWD:
			return TEX_WD_Carrier;
			
		//Hostile Ship
		//BB
		case ID.ShipClass.BBNagato:
			return TEX_BB_Nagato;
		case ID.ShipClass.BBYamato:
			return TEX_BB_Yamato;
		case ID.ShipClass.BBKongou:
			return TEX_BB_Kongou;
		case ID.ShipClass.BBHiei:
			return TEX_BB_Hiei;
		case ID.ShipClass.BBHaruna:
			return TEX_BB_Haruna;
		case ID.ShipClass.BBKirishima:
			return TEX_BB_Kirishima;
		//CV
		case ID.ShipClass.CVAkagi:
			return TEX_CV_Akagi;
		case ID.ShipClass.CVKaga:
			return TEX_CV_Kaga;
		//CL
		case ID.ShipClass.CLTenryuu:
			return TEX_CL_Tenryuu;
		case ID.ShipClass.CLTatsuta:
			return TEX_CL_Tatsuta;
		//CA
		case ID.ShipClass.CAAtago:
			return TEX_CA_Atago;
		case ID.ShipClass.CATakao:
			return TEX_CA_Takao;
		//DD
		case ID.ShipClass.DDAkatsuki:
			return TEX_DD_Akatsuki;
		case ID.ShipClass.DDHibiki:
			return TEX_DD_Hibiki;
		case ID.ShipClass.DDIkazuchi:
			return TEX_DD_Ikazuchi;
		case ID.ShipClass.DDInazuma:
			return TEX_DD_Inazuma;
		case ID.ShipClass.DDShimakaze:
			return TEX_DD_Shimakaze;
		//SS
		case ID.ShipClass.SSRo500:
			return TEX_SS_Ro500;
		case ID.ShipClass.SSU511:
			return TEX_SS_U511;
		default:	//default texture
			return TEX_DD_I;
		}//end switch
    }
    
    @Override
    protected void setModel()
    {
		switch (this.textuerID)
		{
		//AP
		case ID.ShipClass.APWA:
			this.mainModel = MD_AP_Wa;
		break;
		//BB
		case ID.ShipClass.BBRU:
			this.mainModel = MD_BB_Ru;
		break;
		case ID.ShipClass.BBTA:
			this.mainModel = MD_BB_Ta;
		break;
		case ID.ShipClass.BBRE:
			this.mainModel = MD_BB_Re;
		break;
		//CL
		case ID.ShipClass.CARI:
			this.mainModel = MD_CA_Ri;
		break;
		case ID.ShipClass.CANE:
			this.mainModel = MD_CA_Ne;
		break;
		//CV
		case ID.ShipClass.CVWO:
			this.mainModel = MD_CV_Wo;
		break;
		//DD
		case ID.ShipClass.DDI:
			this.mainModel = MD_DD_I;
		break;
		case ID.ShipClass.DDRO:
			this.mainModel = MD_DD_Ro;
		break;
		case ID.ShipClass.DDHA:
			this.mainModel = MD_DD_Ha;
		break;
		case ID.ShipClass.DDNI:
			this.mainModel = MD_DD_Ni;
		break;
		//Hime
		case ID.ShipClass.AirfieldHime:
			this.mainModel = MD_Hime_Airfield;
		break;
		case ID.ShipClass.BBHime:
			this.mainModel = MD_Hime_Battleship;
		break;
		case ID.ShipClass.DDHime:
			this.mainModel = MD_Hime_Destroyer;
		break;
		case ID.ShipClass.CVHime:
			this.mainModel = MD_Hime_Carrier;
		break;
		case ID.ShipClass.CAHime:
			this.mainModel = MD_Hime_CA;
		break;
		case ID.ShipClass.HarbourHime:
			this.mainModel = MD_Hime_Harbour;
		break;
		case ID.ShipClass.IsolatedHime:
			this.mainModel = MD_Hime_Isolated;
		break;
		case ID.ShipClass.MidwayHime:
			this.mainModel = MD_Hime_Midway;
		break;
		case ID.ShipClass.NorthernHime:
			this.mainModel = MD_Hime_Northern;
		break;
		case ID.ShipClass.SSHime:
			this.mainModel = MD_Hime_Subm;
		break;
		case ID.ShipClass.SSNH:
			this.mainModel = MD_Hime_SubmNew;
		break;
		//SS
		case ID.ShipClass.SSKA:
			this.mainModel = MD_SS_Ka;
		break;
		case ID.ShipClass.SSSO:
			this.mainModel = MD_SS_So;
		break;
		case ID.ShipClass.SSYO:
			this.mainModel = MD_SS_Yo;
		break;
		//WD
		case ID.ShipClass.CVWD:
			this.mainModel = MD_WD_Carrier;
		break;
		//Hostile Ship
		//BB
		case ID.ShipClass.BBNagato:
			this.mainModel = MD_BB_Nagato;
		break;
		case ID.ShipClass.BBYamato:
			this.mainModel = MD_BB_Yamato;
		break;
		case ID.ShipClass.BBKongou:
			this.mainModel = MD_BB_Kongou;
		break;
		case ID.ShipClass.BBHiei:
			this.mainModel = MD_BB_Hiei;
		break;
		case ID.ShipClass.BBHaruna:
			this.mainModel = MD_BB_Haruna;
		break;
		case ID.ShipClass.BBKirishima:
			this.mainModel = MD_BB_Kirishima;
		break;
		//CV
		case ID.ShipClass.CVAkagi:
			this.mainModel = MD_CV_Akagi;
		break;
		case ID.ShipClass.CVKaga:
			this.mainModel = MD_CV_Kaga;
		break;
		//CL
		case ID.ShipClass.CLTenryuu:
			this.mainModel = MD_CL_Tenryuu;
		break;
		case ID.ShipClass.CLTatsuta:
			this.mainModel = MD_CL_Tatsuta;
		break;
		//CA
		case ID.ShipClass.CAAtago:
			this.mainModel = MD_CA_Atago;
		break;
		case ID.ShipClass.CATakao:
			this.mainModel = MD_CA_Takao;
		break;
		//DD
		case ID.ShipClass.DDAkatsuki:
			this.mainModel = MD_DD_Akatsuki;
		break;
		case ID.ShipClass.DDHibiki:
			this.mainModel = MD_DD_Hibiki;
		break;
		case ID.ShipClass.DDIkazuchi:
			this.mainModel = MD_DD_Ikazuchi;
		break;
		case ID.ShipClass.DDInazuma:
			this.mainModel = MD_DD_Inazuma;
		break;
		case ID.ShipClass.DDShimakaze:
			this.mainModel = MD_DD_Shimakaze;
		break;
		//SS
		case ID.ShipClass.SSRo500:
			this.mainModel = MD_SS_Ro500;
		break;
		case ID.ShipClass.SSU511:
			this.mainModel = MD_SS_U511;
		break;
		default:	//default model
			this.mainModel = MD_DD_I;
		break;
		}//end switch
    }
    
    @Override
    protected void setMiscModel()
    {
    	switch (this.textuerID)
		{
		case ID.ShipClass.MidwayHime:
			this.miscModelList = new ArrayList<MiscModel>();
			EntityAirplaneTakoyaki tako1 = new EntityAirplaneTakoyaki(ClientProxy.getClientWorld());
			tako1.posX = 0D;
			tako1.posY = 0D;
			tako1.posZ = 0D;
			MiscModel m1 = new MiscModel(tako1, RenderSummonEntity.MD_AirplaneTako, RenderSummonEntity.TEX_AirplaneTako);
			this.miscModelList.add(m1);
			((ModelMidwayHime) MD_Hime_Midway).miscModelList = this.miscModelList;
			m1.scale = new Vec3d(0.65D, 0.65D, 0.65D);
			m1.rotX = -30F;
			m1.entity.posX = 0D;
			m1.entity.posY = 0.34D;
			m1.entity.posZ = -0.18D;
		break;
		}
    }
    
    /** set shadow size */
    @Override
    protected void setShadowSize()
    {
		switch (this.textuerID)
		{
		case ID.ShipClass.NorthernHime:
		case ID.ShipClass.SSNH:
		case ID.ShipClass.SSKA:
		case ID.ShipClass.SSSO:
		case ID.ShipClass.SSYO:
		case ID.ShipClass.DDAkatsuki:
		case ID.ShipClass.DDHibiki:
		case ID.ShipClass.DDIkazuchi:
		case ID.ShipClass.DDInazuma:
		case ID.ShipClass.DDShimakaze:
		case ID.ShipClass.SSRo500:
		case ID.ShipClass.SSU511:
			this.shadowSize = 0.5F;
		break;
		case ID.ShipClass.CLTenryuu:
		case ID.ShipClass.CLTatsuta:
		case ID.ShipClass.DDHime:
			this.shadowSize = 0.6F;
		break;
		case ID.ShipClass.HarbourHime:
		case ID.ShipClass.MidwayHime:
			this.shadowSize = 0.8F;
		break;
		case ID.ShipClass.DDI:
		case ID.ShipClass.DDRO:
		case ID.ShipClass.DDHA:
		case ID.ShipClass.DDNI:
			this.shadowSize = 0.9F;
		break;
		case ID.ShipClass.CVWO:
			this.shadowSize = 1F;
		break;
		default:	//default size
			this.shadowSize = 0.7F;
		break;
		}//end switch
    }
    
    @Override
    protected boolean hasMiscModel()
    {
    	switch (this.textuerID)
		{
		case ID.ShipClass.MidwayHime:
			return true;
		}
    	
    	return false;
    }
    
	//get leash height
	protected float[] getLeashHeight()
	{
		float[] f = Values.ShipLeashHeight.get(this.textuerID);
		
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
	
	@Override
    protected float getDeathMaxRotation(EntityLiving entityLivingBaseIn)
    {
        return 0F;
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