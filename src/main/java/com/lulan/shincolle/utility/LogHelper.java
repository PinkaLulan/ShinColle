package com.lulan.shincolle.utility;

import net.minecraftforge.fml.common.FMLLog;

import org.apache.logging.log4j.Level;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;


public class LogHelper
{
	
	private static void createLog(Level logLevel, Object object)
	{
		FMLLog.log(Reference.MOD_NAME,logLevel,String.valueOf(object));
	}
	
	//off: 不紀錄log
	public static void off(Object object) { createLog(Level.OFF,object); }
	
	//fatal: 紀錄程式停止運作的致命問題
	public static void fatal(Object object) { createLog(Level.FATAL,object); }
	
	//error: 紀錄程式可能停止運作的問題
	public static void error(Object object) { createLog(Level.ERROR,object); }
	
	//warn: 紀錄可能導致error的問題
	public static void warn(Object object) { createLog(Level.WARN,object); }
	
	//info: 紀錄一般訊息, 不需要debug模式
	public static void info(Object object) { createLog(Level.INFO,object); }
	
	//log: 紀錄debug訊息, 需要debug模式
	public static void debug(Object object)
	{
		if (ConfigHandler.debugMode) createLog(Level.INFO,object);
	}
	
	//debug: 紀錄debug訊息, 需要修改預設log等級為Level.DEBUG
	public static void debugHighLevel(Object object)
	{ 
		if (ConfigHandler.debugMode) createLog(Level.DEBUG,object);
	}
	
	//trace: 同debug, 常用於追蹤程式動向
	public static void trace(Object object) { createLog(Level.TRACE,object); }
	
	//all: 紀錄全部訊息
	public static void all(Object object) { createLog(Level.ALL,object); }
	
	
}
