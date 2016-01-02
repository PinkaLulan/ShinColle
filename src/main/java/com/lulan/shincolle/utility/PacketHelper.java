package com.lulan.shincolle.utility;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketHelper {

	public PacketHelper() {}
	
	/** send integer list data
	 *  stream: 0:size 1~N:data
	 */
	public static void sendListInt(ByteBuf buf, List data) {
		if(data != null) {
			Iterator iter = data.iterator();
			
			//send size
			buf.writeInt(data.size());
			
			//send data
			while(iter.hasNext()) {
				buf.writeInt((Integer) iter.next());
			}
		}
		else {
			buf.writeInt(-1);
		}
	}
	
	/** send string list data
	 *  steam: 0:size 1~N:string
	 */
	public static void sendListString(ByteBuf buf, List data) {
		if(data != null) {
			Iterator iter = data.iterator();
			
			//send size
			buf.writeInt(data.size());
			
			//send data
			while(iter.hasNext()) {
				ByteBufUtils.writeUTF8String(buf, (String) iter.next());
			}
		}
		else {
			buf.writeInt(-1);
		}
	}
	
	/** get int list data */
	public static List<Integer> getListInt(ByteBuf buf) {
		List<Integer> getlist = new ArrayList();
		
		//get size
		int size = buf.readInt();
		
		//get data
		if(size > 0) {
			for(int i = 0; i < size; ++i) {
				getlist.add(buf.readInt());
			}
		}
		
		return getlist;
	}
	
	/** get string list data */
	public static List<String> getListString(ByteBuf buf) {
		List<String> getlist = new ArrayList();
		
		//get size
		int size = buf.readInt();
		
		//get data
		if(size > 0) {
			for(int i = 0; i < size; ++i) {
				getlist.add(ByteBufUtils.readUTF8String(buf));
			}
		}
		
		return getlist;
	}
	
	/** send string data */
	public static void sendString(ByteBuf buf, String str) {
		if(str != null) {
			buf.writeInt(1);  //has string
			ByteBufUtils.writeUTF8String(buf, str);
		}
		else {
			buf.writeInt(-1);  //no string
		}
	}
	
	/** get string data */
	public static String getString(ByteBuf buf) {
		String str = null;
		int flag = buf.readInt();
		
		if(flag > 0) {
			str = ByteBufUtils.readUTF8String(buf);
		}
		
		return str;
	}
	
	
}
