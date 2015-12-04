package com.lulan.shincolle.utility;

public class GuiHelper {

	//hard coded button position (x1,y1,x2,y2)
	private static final int[][][][] BUTTON = {
		{//gui0: ship inventory
		 //0: page0          1: page1          2: page2
		 {{120,18,134,52},  {120,53,134,88},  {120,89,134,125},		//all
		 //3: AI op0         4: AI op1         5: AI op2
		  {173,131,238,143},{173,144,238,155},{173,156,238,167},
		 //6: AI op3         7: AI op4		   8: AI op5
		  {173,168,238,179},{173,180,238,191},{173,192,238,203},
		 //9: AI page0       10: AI page1      11: AI page2
		  {238,130,246,156},{238,156,246,182},{238,182,246,208}},
		 //0: attack
		  {{65,18,119,40}},											//attribute
		 //0: AI page2 bar0  1: AI page2 bar1  2: AI page3 bar2
		  {{187,145,238,154},{187,169,238,178},{187,193,238,202}}	//AI control
		},
		{//gui1: small shipyard
		 //0:ship button    1:equip button
		 {{122,16,141,36}, {142,16,162,36}}							//all
		},
		{//gui2: large shipyard
		 //0:ship button    1:equip button   2:inventory mode
		 {{157,24,175,42}, {177,24,195,42}, {23,93,48,112},			//all
		 //3:grudge         4:abyssium       5:ammo
		  {27,14,45,32},   {27,33,45,51},   {27,52,45,70},
		 //6:polymetal      7:grudge num     8:abyss num
		  {27,71,45,89},   {51,19,97,27},   {51,38,97,46},
		 //9:ammo num       10:poly num
		  {51,57,97,65},   {51,76,97,84}},
		 //0:grud +1k    1:grud +100   2:grud +10    3:grud +1
		 {{50,8,62,18}, {62,8,74,18}, {74,8,86,18},	{86,8,98,18},	//grudge
		 //4:grud -1k    5:grud -100   6:grud -10    7:grud -1    
		  {50,28,62,38},{62,28,74,38},{74,28,86,38},{86,28,98,38}},
		 //0:abyss +1k   1:abyss +100  2:abyss +10   3:abyss +1
		 {{50,27,62,37},{62,27,74,37},{74,27,86,37},{86,27,98,37},	//abyss
		 //4:abyss -1k   5:abyss -100  6:abyss -10   7:abyss -1    
		  {50,47,62,57},{62,47,74,57},{74,47,86,57},{86,47,98,57}},
		 //0:ammo +1k    1:ammo +100   2:ammo +10    3:ammo +1 
		 {{50,46,62,56},{62,46,74,56},{74,46,86,56},{86,46,98,56},	//ammo
		 //4:ammo -1k    5:ammo -100   6:ammo -10    7:ammo -1    
		  {50,66,62,76},{62,66,74,76},{74,66,86,76},{86,66,98,76}},
		 //0:poly +1k    1:poly +100   2:poly +10    3:poly +1 
		 {{50,65,62,75},{62,65,74,75},{74,65,86,75},{86,65,98,75},	//poly
		 //4:poly -1k    5:poly -100   6:poly -10    7:poly -1    
		  {50,85,62,95},{62,85,74,95},{74,85,86,95},{86,85,98,95}}
		},
		{//gui3: admiral desk
		 //0:radar btn       1:book btn								//all
		 {{3,2,19,18},      {22,2,38,18}},
		 //0:radar scale											//radar
		 {{7,158,55,170}},
		 //0:left page       1:right page	   2:chap 0				//book
		 {{52,180,72,193},  {174,180,194,193},{243,34,256,45},
		 //3:chap 1          4:chap 2          5:chap 3
		  {243,46,256,59},  {243,60,256,71},  {243,72,256,82},
		 //6:chap 4          7:chap 5          8:chap 6
		  {243,83,256,96},  {243,97,256,109}, {243,110,256,121}}
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
//				LogHelper.info("DEBUG : GUI get button: gui "+gui+" page "+page+" x "+x+" y "+y+" button "+i);
				return i;
			}
		}
		
//		LogHelper.info("DEBUG : GUI get no button: gui "+gui+" page "+page+" x "+x+" y "+y);
		return -1;
	}
	
	public enum pickColorName {
		WHITE, YELLOW, ORANGE, RED1, GRAY1, BLACK, RED2, GRAY2
	}
	
	//color code
	public static int pickColor(int b) {
		switch(b) {
		case 0:
			return 16777215;	//white
		case 1:
			return 16776960;	//yellow
		case 2:
			return 16753920;	//orange
		case 3:
			return 16724787;	//light red, RED1
		case 4:
			return 3158064;		//dark gray, GRAY1
		case 5:
			return 0;			//black
		case 6:
			return 11141120;	//dark red, RED2
		case 7:
			return 11184810;	//light gray, GRAY2
		default:
			return 16724787;	//red
		}
	}
	

}
