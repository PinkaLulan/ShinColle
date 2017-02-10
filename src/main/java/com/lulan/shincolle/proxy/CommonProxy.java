package com.lulan.shincolle.proxy;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.capability.CapaTeitokuStorage;
import com.lulan.shincolle.capability.ICapaTeitoku;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CReactPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public abstract class CommonProxy implements IProxy
{
	
	public static final String chNameEnt = "scEnt";
	public static final String chNameGui = "scGui";
	public static final String chNamePar = "scPar";
	public static final String chNameIn = "scIn";
	
	/**packet system
	 * channelE: entity sync
	 * channelG: gui sync and client input
	 * channelP: particle
	 */
	public static SimpleNetworkWrapper channelE;
	public static SimpleNetworkWrapper channelG;
	public static SimpleNetworkWrapper channelP;
	public static SimpleNetworkWrapper channelI;

	public static boolean isMultiplayer = false;	//the world is MP or SP
	public static boolean activeBaubles = false;	//mod Baubles active flag
	public static boolean activeForestry = false;	//mod Forestry active flag
	public static boolean activeIC2 = false;		//mod IC2 active flag
	
	
	public CommonProxy() {}
	
	@Override
	public void registerChannel()
	{
		//SimpleNetworkWrapper packets
		channelE = NetworkRegistry.INSTANCE.newSimpleChannel(chNameEnt);
		channelG = NetworkRegistry.INSTANCE.newSimpleChannel(chNameGui);
		channelP = NetworkRegistry.INSTANCE.newSimpleChannel(chNamePar);
		channelI = NetworkRegistry.INSTANCE.newSimpleChannel(chNameIn);
		
		//register packets
		//entity sync packet
		channelE.registerMessage(S2CEntitySync.Handler.class, S2CEntitySync.class, ID.Packets.S2C_EntitySync, Side.CLIENT);
		//particle packet
		channelP.registerMessage(S2CSpawnParticle.Handler.class, S2CSpawnParticle.class, ID.Packets.S2C_Particle, Side.CLIENT);
		//GUI packet
		channelG.registerMessage(S2CGUIPackets.Handler.class, S2CGUIPackets.class, ID.Packets.S2C_GUISync, Side.CLIENT);
		channelG.registerMessage(C2SGUIPackets.Handler.class, C2SGUIPackets.class, ID.Packets.C2S_GUIInput, Side.SERVER);
		//Input or server reaction packet
		channelI.registerMessage(S2CReactPackets.Handler.class, S2CReactPackets.class, ID.Packets.S2C_CmdReact, Side.CLIENT);
		channelI.registerMessage(C2SInputPackets.Handler.class, C2SInputPackets.class, ID.Packets.C2S_CmdInput, Side.SERVER);
	}
	
	@Override
	public void registerCapability()
	{
		CapabilityManager.INSTANCE.register(ICapaTeitoku.class, new CapaTeitokuStorage(), CapaTeitoku.class);
		
	}
	
	//check mod is loaded
	public static void checkModLoaded()
	{
		//Baubles
		if (Loader.isModLoaded(Reference.MOD_ID_Baubles))
		{
			LogHelper.info("INFO : Enable mod support: "+Reference.MOD_ID_Baubles);
			activeBaubles = true;
		}
		
		//forestry
//		if (Loader.isModLoaded(Reference.MOD_ID_Forestry) && ConfigHandler.enableForestry)
//		{
//			LogHelper.info("INFO : Enable mod support: "+Reference.MOD_ID_Forestry);
//			activeForestry = true;
//			ShinBees.init();
//			
//			//TODO debug
////			AlleleManager.alleleRegistry.getRegisteredAlleles().forEach((k, v) ->
////			{
////				LogHelper.info("AAAAAAAA "+k);
////			});
////			Iterator iter = EntityList.classToStringMapping.entrySet().iterator();
////			Iterator iter = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees").getGenomeTemplates().entrySet().iterator();
////			while (iter.hasNext())
////			{
////				Map.Entry entry = (Map.Entry)iter.next();
////			    Object key = entry.getKey();
////			    Object val = entry.getValue();
////			    LogHelper.info("AAAAAA:  "+key+" , "+val);
////			    
////			    IAllele[] data = (IAllele[]) val;
////			    
////			    for (IAllele d : data)
////			    {
////			    	LogHelper.info("            BBBB:  "+d);
////			    }
////			}
//		}
		
		//IC2
		if (Loader.isModLoaded(Reference.MOD_ID_IC2) && ConfigHandler.enableIC2)
		{
			LogHelper.info("INFO : Enable mod support: "+Reference.MOD_ID_IC2);
			activeIC2 = true;
		}
	}
	

}
