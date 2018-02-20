package com.lulan.shincolle.init;

import javax.annotation.Nullable;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.entity.battleship.EntityBBHaruna;
import com.lulan.shincolle.entity.battleship.EntityBBHarunaMob;
import com.lulan.shincolle.entity.battleship.EntityBBHiei;
import com.lulan.shincolle.entity.battleship.EntityBBHieiMob;
import com.lulan.shincolle.entity.battleship.EntityBBKirishima;
import com.lulan.shincolle.entity.battleship.EntityBBKirishimaMob;
import com.lulan.shincolle.entity.battleship.EntityBBKongou;
import com.lulan.shincolle.entity.battleship.EntityBBKongouMob;
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
import com.lulan.shincolle.entity.cruiser.EntityCAAtago;
import com.lulan.shincolle.entity.cruiser.EntityCAAtagoMob;
import com.lulan.shincolle.entity.cruiser.EntityCANe;
import com.lulan.shincolle.entity.cruiser.EntityCARi;
import com.lulan.shincolle.entity.cruiser.EntityCATakao;
import com.lulan.shincolle.entity.cruiser.EntityCATakaoMob;
import com.lulan.shincolle.entity.cruiser.EntityCLTatsuta;
import com.lulan.shincolle.entity.cruiser.EntityCLTatsutaMob;
import com.lulan.shincolle.entity.cruiser.EntityCLTenryuu;
import com.lulan.shincolle.entity.cruiser.EntityCLTenryuuMob;
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
import com.lulan.shincolle.entity.hime.EntityCAHime;
import com.lulan.shincolle.entity.hime.EntityCarrierHime;
import com.lulan.shincolle.entity.hime.EntityCarrierWD;
import com.lulan.shincolle.entity.hime.EntityDestroyerHime;
import com.lulan.shincolle.entity.hime.EntityHarbourHime;
import com.lulan.shincolle.entity.hime.EntityIsolatedHime;
import com.lulan.shincolle.entity.hime.EntityMidwayHime;
import com.lulan.shincolle.entity.hime.EntityNorthernHime;
import com.lulan.shincolle.entity.hime.EntitySSNH;
import com.lulan.shincolle.entity.hime.EntitySubmHime;
import com.lulan.shincolle.entity.mounts.EntityMountAfH;
import com.lulan.shincolle.entity.mounts.EntityMountBaH;
import com.lulan.shincolle.entity.mounts.EntityMountCaH;
import com.lulan.shincolle.entity.mounts.EntityMountCaWD;
import com.lulan.shincolle.entity.mounts.EntityMountHbH;
import com.lulan.shincolle.entity.mounts.EntityMountIsH;
import com.lulan.shincolle.entity.mounts.EntityMountMiH;
import com.lulan.shincolle.entity.mounts.EntityMountSuH;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneTMob;
import com.lulan.shincolle.entity.other.EntityAirplaneTakoyaki;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroMob;
import com.lulan.shincolle.entity.other.EntityFloatingFort;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.entity.other.EntityProjectileStatic;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.entity.other.EntityRensouhouMob;
import com.lulan.shincolle.entity.other.EntityRensouhouS;
import com.lulan.shincolle.entity.other.EntityShipFishingHook;
import com.lulan.shincolle.entity.submarine.EntitySubmKa;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmSo;
import com.lulan.shincolle.entity.submarine.EntitySubmU511;
import com.lulan.shincolle.entity.submarine.EntitySubmU511Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmYo;
import com.lulan.shincolle.entity.transport.EntityTransportWa;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.ID.NameMinions;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
// register natural spawns for entities (1.7.10)
// EntityRegistry.addSpawn(MyEntity.class, spawnProbability, minSpawn, maxSpawn, enumCreatureType, [spawnBiome]);
// See the constructor in BiomeGenBase.java to see the rarity of vanilla mobs; Sheep are probability 10 while Endermen are probability 1
// minSpawn and maxSpawn are about how groups of the entity spawn
// enumCreatureType represents the "rules" Minecraft uses to determine spawning, based on creature type. By default, you have three choices:
//    EnumCreatureType.creature uses rules for animals: spawn everywhere it is light out.
//    EnumCreatureType.monster uses rules for monsters: spawn everywhere it is dark out.
//    EnumCreatureType.waterCreature uses rules for water creatures: spawn only in water.
// [spawnBiome] is an optional parameter of type BiomeGenBase that limits the creature spawn to a single biome type. Without this parameter, it will spawn everywhere. 
// For the biome type you can use an list, but unfortunately the built-in biomeList contains
// null entries and will crash, so you need to clean up that list.
// Diesieben07 suggested the following code to remove the nulls and create list of all biomes:
// BiomeGenBase[] allBiomes = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.getBiomeGenArray()), Predicates.notNull()), BiomeGenBase.class);
// example
// EntityRegistry.addSpawn(EntityLion.class, 6, 1, 5, EnumCreatureType.creature, BiomeGenBase.savanna); //change the values to vary the spawn rarity, biome, etc.              
// EntityRegistry.addSpawn(EntityElephant.class, 10, 1, 5, EnumCreatureType.creature, BiomeGenBase.savanna); //change the values to vary the spawn rarity, biome, etc.              
*/
public class ModEntity
{
	
	private static int modEntityID = 1;  //start id
	
	
	public static void init()
	{
		/** entity */
		//register ship entity
		regShipEntity(EntityAirfieldHime.class, ID.ShipClass.AirfieldHime, modEntityID++);
		regShipEntity(EntityBattleshipHime.class, ID.ShipClass.BBHime, modEntityID++);
		regShipEntity(EntityBBKongou.class, ID.ShipClass.BBKongou, modEntityID++);
		regShipEntity(EntityBBKongouMob.class, ID.ShipClass.BBKongou+2000, modEntityID++);
		regShipEntity(EntityBBHiei.class, ID.ShipClass.BBHiei, modEntityID++);
		regShipEntity(EntityBBHieiMob.class, ID.ShipClass.BBHiei+2000, modEntityID++);
		regShipEntity(EntityBBHaruna.class, ID.ShipClass.BBHaruna, modEntityID++);
		regShipEntity(EntityBBHarunaMob.class, ID.ShipClass.BBHaruna+2000, modEntityID++);
		regShipEntity(EntityBBKirishima.class, ID.ShipClass.BBKirishima, modEntityID++);
		regShipEntity(EntityBBKirishimaMob.class, ID.ShipClass.BBKirishima+2000, modEntityID++);
		regShipEntity(EntityBattleshipNGT.class, ID.ShipClass.BBNagato, modEntityID++);
		regShipEntity(EntityBattleshipNGTMob.class, ID.ShipClass.BBNagato+2000, modEntityID++);
		regShipEntity(EntityBattleshipYMT.class, ID.ShipClass.BBYamato, modEntityID++);
		regShipEntity(EntityBattleshipYMTMob.class, ID.ShipClass.BBYamato+2000, modEntityID++);
		regShipEntity(EntityBattleshipRe.class, ID.ShipClass.BBRE, modEntityID++);
		regShipEntity(EntityBattleshipRu.class, ID.ShipClass.BBRU, modEntityID++);
		regShipEntity(EntityBattleshipTa.class, ID.ShipClass.BBTA, modEntityID++);
		regShipEntity(EntityCAHime.class, ID.ShipClass.CAHime, modEntityID++);
		regShipEntity(EntityCarrierAkagi.class, ID.ShipClass.CVAkagi, modEntityID++);
		regShipEntity(EntityCarrierAkagiMob.class, ID.ShipClass.CVAkagi+2000, modEntityID++);
		regShipEntity(EntityCarrierKaga.class, ID.ShipClass.CVKaga, modEntityID++);
		regShipEntity(EntityCarrierKagaMob.class, ID.ShipClass.CVKaga+2000, modEntityID++);
		regShipEntity(EntityCarrierHime.class, ID.ShipClass.CVHime, modEntityID++);
		regShipEntity(EntityCarrierWD.class, ID.ShipClass.CVWD, modEntityID++);
		regShipEntity(EntityCarrierWo.class, ID.ShipClass.CVWO, modEntityID++);
		regShipEntity(EntityCAAtago.class, ID.ShipClass.CAAtago, modEntityID++);
		regShipEntity(EntityCAAtagoMob.class, ID.ShipClass.CAAtago+2000, modEntityID++);
		regShipEntity(EntityCATakao.class, ID.ShipClass.CATakao, modEntityID++);
		regShipEntity(EntityCATakaoMob.class, ID.ShipClass.CATakao+2000, modEntityID++);
		regShipEntity(EntityCLTenryuu.class, ID.ShipClass.CLTenryuu, modEntityID++);
		regShipEntity(EntityCLTenryuuMob.class, ID.ShipClass.CLTenryuu+2000, modEntityID++);
		regShipEntity(EntityCLTatsuta.class, ID.ShipClass.CLTatsuta, modEntityID++);
		regShipEntity(EntityCLTatsutaMob.class, ID.ShipClass.CLTatsuta+2000, modEntityID++);
		regShipEntity(EntityDestroyerI.class, ID.ShipClass.DDI, modEntityID++);
		regShipEntity(EntityDestroyerRo.class, ID.ShipClass.DDRO, modEntityID++);
		regShipEntity(EntityDestroyerHa.class, ID.ShipClass.DDHA, modEntityID++);
		regShipEntity(EntityDestroyerNi.class, ID.ShipClass.DDNI, modEntityID++);
		regShipEntity(EntityDestroyerHime.class, ID.ShipClass.DDHime, modEntityID++);
		regShipEntity(EntityDestroyerAkatsuki.class, ID.ShipClass.DDAkatsuki, modEntityID++);
		regShipEntity(EntityDestroyerAkatsukiMob.class, ID.ShipClass.DDAkatsuki+2000, modEntityID++);
		regShipEntity(EntityDestroyerHibiki.class, ID.ShipClass.DDHibiki, modEntityID++);
		regShipEntity(EntityDestroyerHibikiMob.class, ID.ShipClass.DDHibiki+2000, modEntityID++);
		regShipEntity(EntityDestroyerIkazuchi.class, ID.ShipClass.DDIkazuchi, modEntityID++);
		regShipEntity(EntityDestroyerIkazuchiMob.class, ID.ShipClass.DDIkazuchi+2000, modEntityID++);
		regShipEntity(EntityDestroyerInazuma.class, ID.ShipClass.DDInazuma, modEntityID++);
		regShipEntity(EntityDestroyerInazumaMob.class, ID.ShipClass.DDInazuma+2000, modEntityID++);
		regShipEntity(EntityDestroyerShimakaze.class, ID.ShipClass.DDShimakaze, modEntityID++);
		regShipEntity(EntityDestroyerShimakazeMob.class, ID.ShipClass.DDShimakaze+2000, modEntityID++);
		regShipEntity(EntityHarbourHime.class, ID.ShipClass.HarbourHime, modEntityID++);
		regShipEntity(EntityIsolatedHime.class, ID.ShipClass.IsolatedHime, modEntityID++);
		regShipEntity(EntityMidwayHime.class, ID.ShipClass.MidwayHime, modEntityID++);
		regShipEntity(EntityCARi.class, ID.ShipClass.CARI, modEntityID++);
		regShipEntity(EntityCANe.class, ID.ShipClass.CANE, modEntityID++);
		regShipEntity(EntityNorthernHime.class, ID.ShipClass.NorthernHime, modEntityID++);
		regShipEntity(EntitySubmHime.class, ID.ShipClass.SSHime, modEntityID++);
		regShipEntity(EntitySSNH.class, ID.ShipClass.SSNH, modEntityID++);
		regShipEntity(EntitySubmKa.class, ID.ShipClass.SSKA, modEntityID++);
		regShipEntity(EntitySubmYo.class, ID.ShipClass.SSYO, modEntityID++);
		regShipEntity(EntitySubmSo.class, ID.ShipClass.SSSO, modEntityID++);
		regShipEntity(EntitySubmRo500.class, ID.ShipClass.SSRo500, modEntityID++);
		regShipEntity(EntitySubmRo500Mob.class, ID.ShipClass.SSRo500+2000, modEntityID++);
		regShipEntity(EntitySubmU511.class, ID.ShipClass.SSU511, modEntityID++);
		regShipEntity(EntitySubmU511Mob.class, ID.ShipClass.SSU511, modEntityID++);
		regShipEntity(EntityTransportWa.class, ID.ShipClass.APWA, modEntityID++);
		
		//register mount entity
		regMiscEntity(EntityMountAfH.class, NameMinions.MountAfH, modEntityID++);
		regMiscEntity(EntityMountBaH.class, NameMinions.MountBaH, modEntityID++);
		regMiscEntity(EntityMountCaH.class, NameMinions.MountCaH, modEntityID++);
		regMiscEntity(EntityMountCaWD.class, NameMinions.MountCaWD, modEntityID++);
		regMiscEntity(EntityMountHbH.class, NameMinions.MountHbH, modEntityID++);
		regMiscEntity(EntityMountIsH.class, NameMinions.MountIsH, modEntityID++);
		regMiscEntity(EntityMountMiH.class, NameMinions.MountMiH, modEntityID++);
		regMiscEntity(EntityMountSuH.class, NameMinions.MountSuH, modEntityID++);
		
		//register projectile entity
		regMiscEntity(EntityAbyssMissile.class, NameMinions.AbyssMissile, modEntityID++);
		regMiscEntity(EntityProjectileBeam.class, NameMinions.ProjectileBeam, modEntityID++);
		regMiscEntity(EntityProjectileStatic.class, NameMinions.ProjectileStatic, modEntityID++);
		regMiscEntity(EntityShipFishingHook.class, NameMinions.ShipFishingHook, modEntityID++);
		
		//register summons entity
		regMiscEntity(EntityAirplane.class, NameMinions.Airplane, modEntityID++);
		regMiscEntity(EntityAirplaneTakoyaki.class, NameMinions.AirplaneTakoyaki, modEntityID++);
		regMiscEntity(EntityAirplaneT.class, NameMinions.AirplaneT, modEntityID++);
		regMiscEntity(EntityAirplaneTMob.class, NameMinions.AirplaneTMob, modEntityID++);
		regMiscEntity(EntityAirplaneZero.class, NameMinions.AirplaneZero, modEntityID++);
		regMiscEntity(EntityAirplaneZeroMob.class, NameMinions.AirplaneZeroMob, modEntityID++);
		regMiscEntity(EntityFloatingFort.class, NameMinions.FloatingFort, modEntityID++);
		regMiscEntity(EntityRensouhou.class, NameMinions.Rensouhou, modEntityID++);
		regMiscEntity(EntityRensouhouMob.class, NameMinions.RensouhouMob, modEntityID++);
		regMiscEntity(EntityRensouhouS.class, NameMinions.RensouhouS, modEntityID++);
		
		//register item entity
		regStaticEntity(BasicEntityItem.class, NameMinions.BasicEntityItem, modEntityID++);
		
	}
	
	/** get ship register name by id */
	@Nullable
	public static String getRegNameByID(int classID)
	{
		return ID.NameMap.get((short)classID);
	}
	
//	/** mob自然生成方法, 必須放在postInit才呼叫, 以取得全部mod註冊的全部biome
//	 *  prob: witch = 5, enderman = 10, zombie = 100
//	 */
//	public static void initNaturalSpawn()
//	{
//		//spawn in ALL ocean biome
//		BiomeGenBase[] allBiomes = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.getBiomeGenArray()), Predicates.notNull()), BiomeGenBase.class);
//		EnumCreatureType spawnType = new EnumCreatureType(BasicEntityShipHostile.class, 5, Material.water, true, false);
//		LogHelper.info("AAAAAAAAAAAAA "+allBiomes.length);
//		
//		
//		for (int i = 0; i < allBiomes.length; ++i)
//		{
//			if(BiomeDictionary.isBiomeOfType(allBiomes[i], BiomeDictionary.Type.WATER) ||
//			   BiomeDictionary.isBiomeOfType(allBiomes[i], BiomeDictionary.Type.BEACH))
//			{
//				EntityRegistry.addSpawn(EntitySubmU511Mob.class, 10, 1, 1, spawnType, allBiomes[i]);
//			}
//		}
//	}
	
	/**
	 * register ship entity
	 */
	public static void regShipEntity(Class entityClass, int classID, int entityID)
	{
		String name = getRegNameByID(classID);
		LogHelper.debug("DEBUG: register entity: "+entityID+" "+entityClass+" "+name);
		
		if (name != null)
		{
			//登錄參數: 生物class, 生物名稱, 生物id, mod副本, 追蹤更新距離, 更新時間間隔, 是否發送同步封包(高速entity必須true才會顯示平順)
			EntityRegistry.registerModEntity(new ResourceLocation(name), entityClass, name, entityID, ShinColle.instance, 64, 1, true);
		}
		else
		{
			LogHelper.info("INFO: register entity fail, entity null: id: "+classID);
		}
	}
	
	/**
	 * register misc entity
	 */
	public static void regMiscEntity(Class entityClass, String entityName, int entityID)
	{
		LogHelper.debug("DEBUG: register entity: "+entityID+" "+entityClass+" "+entityName);
		
		if (entityName != null)
		{
			EntityRegistry.registerModEntity(new ResourceLocation(entityName), entityClass, entityName, entityID, ShinColle.instance, 64, 1, true);
		}
		else
		{
			LogHelper.info("INFO: register entity fail, entity null: id: "+entityID);
		}
	}
	
	/**
	 * register static entity, less velocity update
	 */
	public static void regStaticEntity(Class entityClass, String entityName, int entityID)
	{
		LogHelper.debug("DEBUG: register entity: "+entityID+" "+entityClass+" "+entityName);
		
		if (entityName != null)
		{
			EntityRegistry.registerModEntity(new ResourceLocation(entityName), entityClass, entityName, entityID, ShinColle.instance, 64, 4, false);
		}
		else
		{
			LogHelper.info("INFO: register entity fail, entity null: id: "+entityID);
		}
	}
	
	
}