package com.lulan.shincolle.utility;

public class GuiHelper {

	//hard coded button position
	private static final int[][][][] BUTTON = {
		{//ship inventory
		 //0: page0         1: page1         2: page2
		 {{145,18,154,52}, {145,53,154,88}, {145,89,154,125}},	//page 0 = all
		 //0: light ammo    1: heavy ammo
		 {{84,61,140,82},  {84,83,140,104}}						//page 1
		},
		{//small shipyard
			
		},
		{//large shipyard
			
		}
	};
	
	public GuiHelper() {}
	
//	//test
//	public static void printButtons() {
//		LogHelper.info("DEBUG : print button:"+BUTTON.length+" "+BUTTON[0].length);
//	}
	
	//get button in gui and page
	public static int getButton(int gui, int page, int x, int y) {  
		//match button
		for(int i = 0; i < BUTTON[gui][page].length; i++) {
			if(x >= BUTTON[gui][page][i][0] && y >= BUTTON[gui][page][i][1] &&
			   x <= BUTTON[gui][page][i][2] && y <= BUTTON[gui][page][i][3]) {
				LogHelper.info("DEBUG : GUI get button: gui "+gui+" page "+page+" x "+x+" y "+y+" button "+i);
				return i;
			}
		}
		
		LogHelper.info("DEBUG : GUI get no button:gui "+gui+" page "+page+" x "+x+" y "+y);
		return -1;
	}
	

}
