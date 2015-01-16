package com.lulan.shincolle.client.handler;

import com.lulan.shincolle.client.settings.KeyBindings;
import com.lulan.shincolle.reference.Key;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputEventHandler {
	
/*	//偵測按鍵類型用方法
	private static Key getPressedKeyBinding() {
		
		if(KeyBindings.repair.isPressed()) {
			return Key.REPAIR;
		}
		else if() {
			
		}
		return Key.UNKNOWN;	//其他按鍵類型 回傳UNKNOWN
	}*/
	
	//接收按鍵event 根據按鍵做出回應
	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
	
		//debug用: 按下按鍵 在console顯示按下該按鍵
	/*	LogHelper.info(KeyBindings.repair.isPressed());
		LogHelper.info(KeyBindings.repair.getIsKeyPressed());*/
		
	}

}
