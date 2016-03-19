package com.lulan.shincolle.utility;

import org.apache.logging.log4j.Level;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.FMLLog;

public class LogHelper {
	
	//
	public static void log(Level logLevel, Object object) {
		FMLLog.log(Reference.MOD_NAME,logLevel,String.valueOf(object));
	}
	
	//off: 不紀錄log
	public static void off(Object object) { log(Level.OFF,object); }
	
	//fatal: 紀錄程式停止運作的致命問題
	public static void fatal(Object object) { log(Level.FATAL,object); }
	
	//error: 紀錄程式可能停止運作的問題
	public static void error(Object object) { log(Level.ERROR,object); }
	
	//warn: 紀錄可能導致error的問題
	public static void warn(Object object) { log(Level.WARN,object); }
	
	//log: 紀錄非debug模式也能顯示的訊息
	public static void log(Object object) { 
		log(Level.INFO,object); 
	}
	
	//info: 紀錄簡易的debug訊息
	public static void info(Object object) { 
		if(ConfigHandler.debugMode) log(Level.INFO,object); 
	}
	
	//debug: 紀錄debug訊息
	public static void debug(Object object) { 
		if(ConfigHandler.debugMode) log(Level.DEBUG,object);
	}
	
	//trace: 同debug, 常用於追蹤程式動向
	public static void trace(Object object) { log(Level.TRACE,object); }
	
	//all: 紀錄全部訊息
	public static void all(Object object) { log(Level.ALL,object); }
	
	
}
