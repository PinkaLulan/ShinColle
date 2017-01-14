package com.lulan.shincolle.init;

import java.util.HashMap;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModSounds
{

	/**
	 * custom sound map: <key, value>
	 * key = ship ID * 100 + sound type, key xxx10 ~ xxx33 are time keeping sounds
	 * value = SoundEvent
	 * 
	 * sound type = 0:idle, 1:hit, 2:hurt, 3:dead, 4:marry, 5:knockback, 6:item, 7:feed, 8:timekeep
	 */
	public static HashMap<Integer, SoundEvent> CUSTOM_SOUND;
	public static SoundEvent SHIP_IDLE;
	public static SoundEvent SHIP_HURT;
	public static SoundEvent SHIP_DEATH;
	public static SoundEvent SHIP_FIRELIGHT;
	public static SoundEvent SHIP_EXPLODE;
	public static SoundEvent SHIP_FIREHEAVY;
	public static SoundEvent SHIP_HIT;
	public static SoundEvent SHIP_AIRCRAFT;
	public static SoundEvent SHIP_MACHINEGUN;
	public static SoundEvent SHIP_LASER;
	public static SoundEvent SHIP_MARRY;
	public static SoundEvent SHIP_TIME0;
	public static SoundEvent SHIP_TIME1;
	public static SoundEvent SHIP_TIME2;
	public static SoundEvent SHIP_TIME3;
	public static SoundEvent SHIP_TIME4;
	public static SoundEvent SHIP_TIME5;
	public static SoundEvent SHIP_TIME6;
	public static SoundEvent SHIP_TIME7;
	public static SoundEvent SHIP_TIME8;
	public static SoundEvent SHIP_TIME9;
	public static SoundEvent SHIP_TIME10;
	public static SoundEvent SHIP_TIME11;
	public static SoundEvent SHIP_TIME12;
	public static SoundEvent SHIP_TIME13;
	public static SoundEvent SHIP_TIME14;
	public static SoundEvent SHIP_TIME15;
	public static SoundEvent SHIP_TIME16;
	public static SoundEvent SHIP_TIME17;
	public static SoundEvent SHIP_TIME18;
	public static SoundEvent SHIP_TIME19;
	public static SoundEvent SHIP_TIME20;
	public static SoundEvent SHIP_TIME21;
	public static SoundEvent SHIP_TIME22;
	public static SoundEvent SHIP_TIME23;
	public static SoundEvent SHIP_KAITAI;
	public static SoundEvent SHIP_AP_P1;
	public static SoundEvent SHIP_AP_P2;
	public static SoundEvent SHIP_AP_ATTACK;
	public static SoundEvent SHIP_WAKA_ATTACK;
	public static SoundEvent SHIP_WAKA_HURT;
	public static SoundEvent SHIP_WAKA_IDLE;
	public static SoundEvent SHIP_WAKA_DEATH;
	public static SoundEvent SHIP_GARURU;
	public static SoundEvent SHIP_YAMATO_READY;
	public static SoundEvent SHIP_YAMATO_SHOT;
	public static SoundEvent SHIP_KNOCKBACK;
	public static SoundEvent SHIP_ITEM;
	public static SoundEvent SHIP_LEVEL;
	public static SoundEvent SHIP_FEED;
	public static SoundEvent SHIP_BELL;
	public static SoundEvent SHIP_JET;
	public static SoundEvent SHIP_HITMETAL;
//	public static SoundEvent SHIP_TEST1;
//	public static SoundEvent SHIP_TEST2;
//	public static SoundEvent SHIP_TEST3;
//	public static SoundEvent SHIP_TEST4;
//	public static SoundEvent SHIP_TEST5;
//	public static SoundEvent SHIP_TEST6;
//	public static SoundEvent SHIP_TEST7;
//	public static SoundEvent SHIP_TEST8;
//	public static SoundEvent SHIP_TEST9;
//	public static SoundEvent SHIP_TEST10;
	
	
	public ModSounds() {}
	
	//init sound
	public static void init()
	{
		//init map
		CUSTOM_SOUND = new HashMap<Integer, SoundEvent>();
		
		//register sounds
		SHIP_IDLE = initSounds("ship-idle");
		SHIP_HURT = initSounds("ship-hurt");
		SHIP_DEATH = initSounds("ship-death");
		SHIP_FIRELIGHT = initSounds("ship-firelight");
		SHIP_EXPLODE = initSounds("ship-explode");
		SHIP_FIREHEAVY = initSounds("ship-fireheavy");
		SHIP_HIT = initSounds("ship-hit");
		SHIP_AIRCRAFT = initSounds("ship-aircraft");
		SHIP_MACHINEGUN = initSounds("ship-machinegun");
		SHIP_LASER = initSounds("ship-laser");
		SHIP_MARRY = initSounds("ship-marry");
		SHIP_TIME0 = initSounds("ship-time0");
		SHIP_TIME1 = initSounds("ship-time1");
		SHIP_TIME2 = initSounds("ship-time2");
		SHIP_TIME3 = initSounds("ship-time3");
		SHIP_TIME4 = initSounds("ship-time4");
		SHIP_TIME5 = initSounds("ship-time5");
		SHIP_TIME6 = initSounds("ship-time6");
		SHIP_TIME7 = initSounds("ship-time7");
		SHIP_TIME8 = initSounds("ship-time8");
		SHIP_TIME9 = initSounds("ship-time9");
		SHIP_TIME10 = initSounds("ship-time10");
		SHIP_TIME11 = initSounds("ship-time11");
		SHIP_TIME12 = initSounds("ship-time12");
		SHIP_TIME13 = initSounds("ship-time13");
		SHIP_TIME14 = initSounds("ship-time14");
		SHIP_TIME15 = initSounds("ship-time15");
		SHIP_TIME16 = initSounds("ship-time16");
		SHIP_TIME17 = initSounds("ship-time17");
		SHIP_TIME18 = initSounds("ship-time18");
		SHIP_TIME19 = initSounds("ship-time19");
		SHIP_TIME20 = initSounds("ship-time20");
		SHIP_TIME21 = initSounds("ship-time21");
		SHIP_TIME22 = initSounds("ship-time22");
		SHIP_TIME23 = initSounds("ship-time23");
		SHIP_KAITAI = initSounds("ship-kaitai");
		SHIP_AP_P1 = initSounds("ship-ap_phase1");
		SHIP_AP_P2 = initSounds("ship-ap_phase2");
		SHIP_AP_ATTACK = initSounds("ship-ap_attack");
		SHIP_WAKA_ATTACK = initSounds("ship-waka_attack");
		SHIP_WAKA_HURT = initSounds("ship-waka_hurt");
		SHIP_WAKA_IDLE = initSounds("ship-waka_idle");
		SHIP_WAKA_DEATH = initSounds("ship-waka_death");
		SHIP_GARURU = initSounds("ship-garuru");
		SHIP_YAMATO_READY = initSounds("ship-yamato_ready");
		SHIP_YAMATO_SHOT = initSounds("ship-yamato_shot");
		SHIP_KNOCKBACK = initSounds("ship-knockback");
		SHIP_ITEM = initSounds("ship-item");
		SHIP_LEVEL = initSounds("ship-levelup");
		SHIP_FEED = initSounds("ship-feed");
		SHIP_BELL = initSounds("ship-bell");
		SHIP_JET = initSounds("ship-jet");
		SHIP_HITMETAL = initSounds("ship-hitmetal");
//		SHIP_TEST1 = initSounds("ship-test1");
//		SHIP_TEST2 = initSounds("ship-test2");
//		SHIP_TEST3 = initSounds("ship-test3");
//		SHIP_TEST4 = initSounds("ship-test4");
//		SHIP_TEST5 = initSounds("ship-test5");
//		SHIP_TEST6 = initSounds("ship-test6");
//		SHIP_TEST7 = initSounds("ship-test7");
//		SHIP_TEST8 = initSounds("ship-test8");
//		SHIP_TEST9 = initSounds("ship-test9");
//		SHIP_TEST10 = initSounds("ship-test10");
		
		//custom sound
		ConfigHandler.configSound.SOUNDRATE.forEach((k, v) ->
		{
			//null check
			if (k > 1 && v != null && v.length == 9)
			{
				//custom sound registering, except time keeping
				for (int i = 0; i < 8; i++)
				{
					//if sound rate > 0%
					if (v[i] > 0F)
					{
						//register sound event
						SoundEvent event = initSounds(getCustomSoundString(k, i));
						//put into custom sound map
						CUSTOM_SOUND.put(k * 100 + i, event);
					}
				}
				
				//register time keeping sound
				if (v[8] > 0F)
				{
					for (int j = 0; j < 24; j++)
					{
						//register sound event
						SoundEvent event = initSounds(getCustomSoundStringForTimekeeping(k, j));
						//put into custom sound map
						CUSTOM_SOUND.put(k * 100 + 10 + j, event);
					}
				}
			}//end rate null check
		});//end for each sound rate
	}
	
	//in: shipID, time   out: "ship-time(type)-(shipID)"
	private static String getCustomSoundStringForTimekeeping(int shipID, int type)
	{
		return "ship-time" + type + "-" + shipID;
	}
	
	//in: shipID, sound type   out: "ship-(type)-(shipID)"
	private static String getCustomSoundString(int shipID, int type)
	{
		//init string
		String str = "ship-";
		
		//add type
		switch (type)
		{
		case 0:
			str += "idle";
		break;
		case 1:
			str += "hit";
		break;
		case 2:
			str += "hurt";
		break;
		case 3:
			str += "death";
		break;
		case 4:
			str += "marry";
		break;
		case 5:
			str += "knockback";
		break;
		case 6:
			str += "item";
		break;
		case 7:
			str += "feed";
		break;
		}
		
		//add ship id
		str = str + "-" + shipID;
		
		return str;
	}
	
	//register sound
	private static SoundEvent initSounds(String soundName)
	{
		ResourceLocation name = new ResourceLocation(Reference.MOD_ID, soundName);
		SoundEvent event = new SoundEvent(name);
		GameRegistry.register(event, name);
		
		return event;
	}
	
	
}
