package com.lulan.shincolle.proxy;

import com.lulan.shincolle.client.render.RenderMiscEntity;
import com.lulan.shincolle.client.render.RenderMountsEntity;
import com.lulan.shincolle.client.render.RenderShipEntity;
import com.lulan.shincolle.client.render.RenderSummonEntity;
import com.lulan.shincolle.client.render.item.RenderBasicEntityItem;
import com.lulan.shincolle.client.render.item.RenderTileEntityItem;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGT;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGTMob;
import com.lulan.shincolle.entity.battleship.EntityBattleshipRe;
import com.lulan.shincolle.entity.battleship.EntityBattleshipRu;
import com.lulan.shincolle.entity.battleship.EntityBattleshipTa;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMT;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMTMob;
import com.lulan.shincolle.entity.carrier.EntityCarrierAkagi;
import com.lulan.shincolle.entity.carrier.EntityCarrierAkagiMob;
import com.lulan.shincolle.entity.carrier.EntityCarrierKaga;
import com.lulan.shincolle.entity.carrier.EntityCarrierKagaMob;
import com.lulan.shincolle.entity.carrier.EntityCarrierWo;
import com.lulan.shincolle.entity.cruiser.EntityCruiserAtago;
import com.lulan.shincolle.entity.cruiser.EntityCruiserAtagoMob;
import com.lulan.shincolle.entity.cruiser.EntityCruiserTatsuta;
import com.lulan.shincolle.entity.cruiser.EntityCruiserTatsutaMob;
import com.lulan.shincolle.entity.cruiser.EntityCruiserTenryuu;
import com.lulan.shincolle.entity.cruiser.EntityCruiserTenryuuMob;
import com.lulan.shincolle.entity.cruiser.EntityHeavyCruiserNe;
import com.lulan.shincolle.entity.cruiser.EntityHeavyCruiserRi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerAkatsuki;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerAkatsukiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHa;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHibiki;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHibikiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerI;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazuma;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazumaMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerNi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerRo;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakaze;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakazeMob;
import com.lulan.shincolle.entity.hime.EntityAirfieldHime;
import com.lulan.shincolle.entity.hime.EntityBattleshipHime;
import com.lulan.shincolle.entity.hime.EntityCarrierHime;
import com.lulan.shincolle.entity.hime.EntityCarrierWD;
import com.lulan.shincolle.entity.hime.EntityDestroyerHime;
import com.lulan.shincolle.entity.hime.EntityHarbourHime;
import com.lulan.shincolle.entity.hime.EntityNorthernHime;
import com.lulan.shincolle.entity.mounts.EntityMountAfH;
import com.lulan.shincolle.entity.mounts.EntityMountBaH;
import com.lulan.shincolle.entity.mounts.EntityMountCaH;
import com.lulan.shincolle.entity.mounts.EntityMountCaWD;
import com.lulan.shincolle.entity.mounts.EntityMountHbH;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneTMob;
import com.lulan.shincolle.entity.other.EntityAirplaneTakoyaki;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroMob;
import com.lulan.shincolle.entity.other.EntityFloatingFort;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.entity.other.EntityRensouhouMob;
import com.lulan.shincolle.entity.other.EntityRensouhouS;
import com.lulan.shincolle.entity.submarine.EntitySubmKa;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmSo;
import com.lulan.shincolle.entity.submarine.EntitySubmU511;
import com.lulan.shincolle.entity.submarine.EntitySubmU511Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmYo;
import com.lulan.shincolle.entity.transport.EntityTransportWa;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

	
	public ClientProxy() {}
	
	//client world會隨玩家所在位置持續改變, 但是dim id永遠都是0不變, 無法反推dim id?
	public static World getClientWorld()
	{
		return Minecraft.getMinecraft().world;
	}
	
	//client player
	public static EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().player;
	}
	
	//client minecraft
	public static GameSettings getGameSetting()
	{
		return Minecraft.getMinecraft().gameSettings;
	}
	
	//client minecraft
	public static Minecraft getMineraft()
	{
		return Minecraft.getMinecraft();
	}

	//登錄偵測按鍵
	@Override
	public void registerKeyBindings() {}
	
	//init entity, item, block models
	@Override
	public void registerRender() throws Exception
	{
		//init item models
		ModItems.initModels();
		
		//init block models
		ModBlocks.initModels();
		
		//取代mc原本的tesr item renderer, 改為自訂的renderer
		TileEntityItemStackRenderer.instance = new RenderTileEntityItem();
		
		//custom item entity render
		RenderingRegistry.registerEntityRenderingHandler(BasicEntityItem.class, RenderBasicEntityItem.FACTORY);

		//entity render (model class, shadow size)
		RenderingRegistry.registerEntityRenderingHandler(EntityAirfieldHime.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipHime.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGT.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGTMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMT.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMTMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRu.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipTa.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRe.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierAkagi.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierAkagiMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKaga.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKagaMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierHime.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWD.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWo.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserAtago.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserAtagoMob.class, RenderShipEntity.FACTORY_SHIP);
//		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserTakao.class, RenderShipEntity.FACTORY_SHIP);
//		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserTakaoMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserTenryuu.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserTenryuuMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserTatsuta.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityCruiserTatsutaMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerRo.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHa.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerNi.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHime.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerAkatsuki.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerAkatsukiMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHibiki.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHibikiMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerIkazuchi.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerIkazuchiMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerInazuma.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerInazumaMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakaze.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakazeMob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityHarbourHime.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserRi.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserNe.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityNorthernHime.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhou.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouMob.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouS.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmKa.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmYo.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmSo.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500Mob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511Mob.class, RenderShipEntity.FACTORY_SHIP);
		RenderingRegistry.registerEntityRenderingHandler(EntityTransportWa.class, RenderShipEntity.FACTORY_SHIP);
		
		//mount render
		RenderingRegistry.registerEntityRenderingHandler(EntityMountAfH.class, RenderMountsEntity.FACTORY_MOUNT);
		RenderingRegistry.registerEntityRenderingHandler(EntityMountBaH.class, RenderMountsEntity.FACTORY_MOUNT);
		RenderingRegistry.registerEntityRenderingHandler(EntityMountCaWD.class, RenderMountsEntity.FACTORY_MOUNT);
		RenderingRegistry.registerEntityRenderingHandler(EntityMountHbH.class, RenderMountsEntity.FACTORY_MOUNT);
		RenderingRegistry.registerEntityRenderingHandler(EntityMountCaH.class, RenderMountsEntity.FACTORY_MOUNT);
		
		//misc render
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssMissile.class, RenderMiscEntity.FACTORY_MISC);
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBeam.class, RenderMiscEntity.FACTORY_MISC);
		
		//summons render
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplane.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTakoyaki.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneT.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZero.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTMob.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZeroMob.class, RenderSummonEntity.FACTORY_SUMMON);
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingFort.class, RenderSummonEntity.FACTORY_SUMMON);
	
	}
	
	
}