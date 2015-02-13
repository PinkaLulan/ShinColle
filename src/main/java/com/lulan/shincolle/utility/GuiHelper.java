package com.lulan.shincolle.utility;

public class GuiHelper {

	//hard coded button position (x1,y1,x2,y2)
	private static final int[][][][] BUTTON = {
		{//gui0: ship inventory
		 //0: page0         1: page1         2: page2
		 {{145,18,154,52}, {145,53,154,88}, {145,89,154,125}},	//page 0
		 //0: light ammo    1: heavy ammo
		 {{84,61,140,82},  {84,83,140,104}}						//page 1
		},
		{//gui1: small shipyard
		 //0:ship button    1:equip button
		 {{122,16,141,36}, {142,16,162,36}}						//page 0
		},
		{//gui2: large shipyard
		 //0:ship button    1:equip button   2:inventory mode
		 {{157,24,175,42}, {177,24,195,42}, {23,93,48,112},			//page 0
		 //3:grudge         4:abyssium       5:ammo
		  {27,14,45,32},   {27,33,45,51},   {27,52,45,70},
		 //6:polymetal
		  {27,71,45,89}},
		 //0:grud +1k    1:grud +100   2:grud +10    3:grud +1
		 {{50,8,62,18}, {62,8,74,18}, {74,8,86,18},	{86,8,98,18},	//page 1 = grudge page
		 //4:grud -1k    5:grud -100   6:grud -10    7:grud -1    
		  {50,28,62,38},{62,28,74,38},{74,28,86,38},{86,28,98,38}},
		 //0:abyss +1k   1:abyss +100  2:abyss +10   3:abyss +1
		 {{50,27,62,37},{62,27,74,37},{74,27,86,37},{86,27,98,37},	//page 2 = abyss page
		 //4:abyss -1k   5:abyss -100  6:abyss -10   7:abyss -1    
		  {50,47,62,57},{62,47,74,57},{74,47,86,57},{86,47,98,57}},
		 //0:ammo +1k    1:ammo +100   2:ammo +10    3:ammo +1 
		 {{50,46,62,56},{62,46,74,56},{74,46,86,56},{86,46,98,56},	//page 3 = ammo page
		 //4:ammo -1k    5:ammo -100   6:ammo -10    7:ammo -1    
		  {50,66,62,76},{62,66,74,76},{74,66,86,76},{86,66,98,76}},
		 //0:poly +1k    1:poly +100   2:poly +10    3:poly +1 
		 {{50,65,62,75},{62,65,74,75},{74,65,86,75},{86,65,98,75},	//page 4 = poly page
		 //4:poly -1k    5:poly -100   6:poly -10    7:poly -1    
		  {50,85,62,95},{62,85,74,95},{74,85,86,95},{86,85,98,95}}
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
