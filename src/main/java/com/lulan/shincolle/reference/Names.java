package com.lulan.shincolle.reference;

public final class Names {

	//按鍵設定用參考字串  此處會參考語系檔並抓出最後顯示的字串
	public static final class Keys {
		public static final String CATEGORY = "keys.shincolle.category";
		public static final String REPAIR = "keys.shincolle.repair";
	}
	
	public static final class Packets {
		public static final byte TEST = 0;
		public static final byte ENTITY_SYNC = 1;
		public static final byte SPAWN_PARTICLE = 2;
		public static final byte GUI_CLICK = 3;
		public static final byte GUI_SYNC = 4;
		public static final byte PLAYER_SYNC = 5;
		public static final byte KEY_INPUT = 6;
	}

}
