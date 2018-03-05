package com.lulan.shincolle.init;

import java.util.HashMap;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class ModSounds
{

	/**
	 * custom sound map: <key, value>
	 * key = ship ID * 100 + sound type, key xxx10 ~ xxx33 are time keeping sounds
	 * value = SoundEvent
	 * 
	 * sound type = 0:idle, 1:hit, 2:hurt, 3:dead, 4:marry, 5:knockback, 6:item, 7:feed, 8:timekeep
	 */
	private static int soundEventId = 0;
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
	public static void register(RegistryEvent.Register<SoundEvent> event)
	{
		//init map
		CUSTOM_SOUND = new HashMap<Integer, SoundEvent>();
		
		//register sounds
		SHIP_IDLE = initSounds(event, "ship-idle");
		SHIP_HURT = initSounds(event, "ship-hurt");
		SHIP_DEATH = initSounds(event, "ship-death");
		SHIP_FIRELIGHT = initSounds(event, "ship-firelight");
		SHIP_EXPLODE = initSounds(event, "ship-explode");
		SHIP_FIREHEAVY = initSounds(event, "ship-fireheavy");
		SHIP_HIT = initSounds(event, "ship-hit");
		SHIP_AIRCRAFT = initSounds(event, "ship-aircraft");
		SHIP_MACHINEGUN = initSounds(event, "ship-machinegun");
		SHIP_LASER = initSounds(event, "ship-laser");
		SHIP_MARRY = initSounds(event, "ship-marry");
		SHIP_TIME0 = initSounds(event, "ship-time0");
		SHIP_TIME1 = initSounds(event, "ship-time1");
		SHIP_TIME2 = initSounds(event, "ship-time2");
		SHIP_TIME3 = initSounds(event, "ship-time3");
		SHIP_TIME4 = initSounds(event, "ship-time4");
		SHIP_TIME5 = initSounds(event, "ship-time5");
		SHIP_TIME6 = initSounds(event, "ship-time6");
		SHIP_TIME7 = initSounds(event, "ship-time7");
		SHIP_TIME8 = initSounds(event, "ship-time8");
		SHIP_TIME9 = initSounds(event, "ship-time9");
		SHIP_TIME10 = initSounds(event, "ship-time10");
		SHIP_TIME11 = initSounds(event, "ship-time11");
		SHIP_TIME12 = initSounds(event, "ship-time12");
		SHIP_TIME13 = initSounds(event, "ship-time13");
		SHIP_TIME14 = initSounds(event, "ship-time14");
		SHIP_TIME15 = initSounds(event, "ship-time15");
		SHIP_TIME16 = initSounds(event, "ship-time16");
		SHIP_TIME17 = initSounds(event, "ship-time17");
		SHIP_TIME18 = initSounds(event, "ship-time18");
		SHIP_TIME19 = initSounds(event, "ship-time19");
		SHIP_TIME20 = initSounds(event, "ship-time20");
		SHIP_TIME21 = initSounds(event, "ship-time21");
		SHIP_TIME22 = initSounds(event, "ship-time22");
		SHIP_TIME23 = initSounds(event, "ship-time23");
		SHIP_KAITAI = initSounds(event, "ship-kaitai");
		SHIP_AP_P1 = initSounds(event, "ship-ap_phase1");
		SHIP_AP_P2 = initSounds(event, "ship-ap_phase2");
		SHIP_AP_ATTACK = initSounds(event, "ship-ap_attack");
		SHIP_WAKA_ATTACK = initSounds(event, "ship-waka_attack");
		SHIP_WAKA_HURT = initSounds(event, "ship-waka_hurt");
		SHIP_WAKA_IDLE = initSounds(event, "ship-waka_idle");
		SHIP_WAKA_DEATH = initSounds(event, "ship-waka_death");
		SHIP_GARURU = initSounds(event, "ship-garuru");
		SHIP_YAMATO_READY = initSounds(event, "ship-yamato_ready");
		SHIP_YAMATO_SHOT = initSounds(event, "ship-yamato_shot");
		SHIP_KNOCKBACK = initSounds(event, "ship-knockback");
		SHIP_ITEM = initSounds(event, "ship-item");
		SHIP_LEVEL = initSounds(event, "ship-levelup");
		SHIP_FEED = initSounds(event, "ship-feed");
		SHIP_BELL = initSounds(event, "ship-bell");
		SHIP_JET = initSounds(event, "ship-jet");
		SHIP_HITMETAL = initSounds(event, "ship-hitmetal");
//		SHIP_TEST1 = initSounds(event, "ship-test1");
//		SHIP_TEST2 = initSounds(event, "ship-test2");
//		SHIP_TEST3 = initSounds(event, "ship-test3");
//		SHIP_TEST4 = initSounds(event, "ship-test4");
//		SHIP_TEST5 = initSounds(event, "ship-test5");
//		SHIP_TEST6 = initSounds(event, "ship-test6");
//		SHIP_TEST7 = initSounds(event, "ship-test7");
//		SHIP_TEST8 = initSounds(event, "ship-test8");
//		SHIP_TEST9 = initSounds(event, "ship-test9");
//		SHIP_TEST10 = initSounds(event, "ship-test10");
		
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
						SoundEvent sound = initSounds(event, getCustomSoundString(k, i));
						//put into custom sound map
						CUSTOM_SOUND.put(k * 100 + i, sound);
					}
				}
				
				//register time keeping sound
				if (v[8] > 0F)
				{
					for (int j = 0; j < 24; j++)
					{
						//register sound event
						SoundEvent sound = initSounds(event, getCustomSoundStringForTimekeeping(k, j));
						//put into custom sound map
						CUSTOM_SOUND.put(k * 100 + 10 + j, sound);
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
	private static SoundEvent initSounds(RegistryEvent.Register<SoundEvent> event, String soundName)
	{
		ResourceLocation name = new ResourceLocation(Reference.MOD_ID, soundName);
		SoundEvent sound = new SoundEvent(name);
		event.getRegistry().register(sound);
		
		return sound;
	}
	
	
}