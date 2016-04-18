package com.lulan.shincolle.utility;

public class GuiHelper {

	//hard coded button position (x1,y1,x2,y2)
	private static final int[][][][] BUTTON = {
		{//gui 0: ship inventory
		 //0: page0          1: page1          2: page2
		 {{133,18,142,52},  {133,53,142,88},  {133,89,142,125},		//0: all
		 //3: AI op0         4: AI op1         5: AI op2
		  {173,131,237,143},{173,144,237,155},{173,156,237,167},
		 //6: AI op3         7: AI op4		   8: AI op5
		  {173,168,237,179},{173,180,237,191},{173,192,237,203},
		 //9: AI page0       10: AI page1      11: AI page2
		  {238,130,245,156},{238,156,245,182},{238,182,245,208},
		 //12: AI page3      13: AI page4      14: AI page5
		  {246,130,253,156},{246,156,253,182},{246,182,253,208},
		 //15: inv page0     16: inv page1     17: inv page2
		  {61,18,70,52},    {61,53,70,88},    {61,89,70,125}},
		 //0: attack
		  {{73,18,132,40}},											//1: attribute
		 //0: AI page2 bar0  1: AI page2 bar1  2: AI page3 bar2
		  {{187,145,238,154},{187,169,238,178},{187,193,238,202}}	//2: AI control
		},
		{//gui 1: small shipyard
		 //0:ship button    1:equip button
		 {{122,16,141,36}, {142,16,162,36}}							//0: all
		},
		{//gui 2: large shipyard
		 //0:ship button    1:equip button   2:inventory mode
		 {{157,24,175,42}, {177,24,195,42}, {23,93,48,112},			//0: all
		 //3:grudge         4:abyssium       5:ammo
		  {27,14,45,32},   {27,33,45,51},   {27,52,45,70},
		 //6:polymetal      7:grudge num     8:abyss num
		  {27,71,45,89},   {51,19,97,27},   {51,38,97,46},
		 //9:ammo num       10:poly num
		  {51,57,97,65},   {51,76,97,84}},
		 //0:grud +1k    1:grud +100   2:grud +10    3:grud +1
		 {{50,8,62,18}, {62,8,74,18}, {74,8,86,18},	{86,8,98,18},	//1: grudge
		 //4:grud -1k    5:grud -100   6:grud -10    7:grud -1    
		  {50,28,62,38},{62,28,74,38},{74,28,86,38},{86,28,98,38}},
		 //0:abyss +1k   1:abyss +100  2:abyss +10   3:abyss +1
		 {{50,27,62,37},{62,27,74,37},{74,27,86,37},{86,27,98,37},	//2: abyss
		 //4:abyss -1k   5:abyss -100  6:abyss -10   7:abyss -1    
		  {50,47,62,57},{62,47,74,57},{74,47,86,57},{86,47,98,57}},
		 //0:ammo +1k    1:ammo +100   2:ammo +10    3:ammo +1 
		 {{50,46,62,56},{62,46,74,56},{74,46,86,56},{86,46,98,56},	//3: ammo
		 //4:ammo -1k    5:ammo -100   6:ammo -10    7:ammo -1    
		  {50,66,62,76},{62,66,74,76},{74,66,86,76},{86,66,98,76}},
		 //0:poly +1k    1:poly +100   2:poly +10    3:poly +1 
		 {{50,65,62,75},{62,65,74,75},{74,65,86,75},{86,65,98,75},	//4: poly
		 //4:poly -1k    5:poly -100   6:poly -10    7:poly -1    
		  {50,85,62,95},{62,85,74,95},{74,85,86,95},{86,85,98,95}}
		},
		{//gui 3: admiral desk
		 //0:radar btn  1:book btn	  2:team btn   	3:target btn	//0: all
		 {{3,2,19,18}, {22,2,38,18}, {41,2,57,18}, {60,2,76,18}},
		 //0:radar scale	 1:ship slot 0     2:ship slot 1		//1: radar
		 {{7,158,55,170},   {140,23,252,54},  {140,55,252,86},
		 //3:ship slot 2     4:ship slot 3     5:ship slot 4
		  {140,87,252,118}, {140,119,252,150},{140,151,252,187},
		 //6:openGUI 
		  {7,172,55,184}},
		 //0:left page       1:right page	   2:chap 0				//2: book
		 {{0,25,122,193},   {123,25,240,193}, {243,34,256,45},
		 //3:chap 1          4:chap 2          5:chap 3
		  {243,46,256,59},  {243,60,256,71},  {243,72,256,82},
		 //6:chap 4          7:chap 5          8:chap 6
		  {243,83,256,96},  {243,97,256,109}, {243,110,256,121}},
		 //0:left top   	 1:team slot 0     2:team slot 1		//3: team
		 {{7,158,55,170},   {140,23,252,54},  {140,55,252,86},
		 //3:team slot 2     4:team slot 3     5:team slot 4
		  {140,87,252,118}, {140,119,252,150},{140,151,252,187},
		 //6:left bottom     7:right top       8:right bottom
		  {7,172,55,184},   {86,158,134,170}, {86,172,135,184},
		 //9:left slot 0     10:left slot 1    11:left slot 2 
		  {7,61,134,91},    {7,92,134,122},   {7,123,134,153}},
		 //0:target remove	 1:slot 0          2:slot 1				//4: target
		 {{7,158,55,170},   {140,23,252,37},  {140,38,252,49},
		 //3:slot 2	         4:slot 3          5:slot 4  
		  {140,50,252,61},  {140,62,252,73},  {140,74,252,85},
		 //6:slot 5          7:slot 6          8:slot 7 
		  {140,86,252,97},  {140,98,252,109}, {140,110,252,121},
		 //9:slot 8          10:slot 9         11:slot 10 
		  {140,122,252,133},{140,134,252,145},{140,146,252,157},
		 //12:slot 11        13:slot 12        
		  {140,158,252,169},{140,170,252,183}},
		 //0:model       	 1:cake	       	   2:cake sneak			//5: book chap 4/5
		 {{18,45,110,158},  {22,158,38,173},  {41,158,57,173},
		 //3:sit             4:run             5:attack
		  {59,160,70,171},  {71,160,82,170},  {83,160,94,171},
		 //6: -
		  {95,160,106,171}}
		},
		{//gui 4: formation
		 //0:no format       1:LineAhead       2:DoubleLine
		 {{17,148,34,165},  {35,148,52,165},  {53,148,70,165},
		 //3:diamond         4:echelon         5:LineAbreast
		  {71,148,88,165},  {89,148,106,165}, {107,148,124,165},
		 //6:team 0          7:team 1          8:team 2
		  {17,166,28,179},  {29,166,40,179},  {41,166,52,179},
		 //9:team 3          10:team 4         11:team 5
		  {53,166,64,179},  {65,166,76,179},  {77,166,88,179},
		 //12:team 6         13:team 7         14:team 8
		  {89,166,100,179}, {101,166,112,179},{113,166,124,179},
		 //15:list 0         16:list 1         17:list 2
		  {142,5,250,32},   {142,33,250,59},  {142,60,250,86},
		 //18:list 3         19:list 4         20:list 5
		  {142,87,250,113}, {142,114,250,140},{142,141,250,167},
		 //21:btn DOWN       22:btn UP         23:gui
		  {159,170,189,180},{203,170,233,180},{46,180,94,192}}
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
		WHITE, YELLOW, ORANGE, RED1, GRAY1, BLACK, RED2, GRAY2, PINK
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
		case 8:
			return 15515845;	//pink
		default:
			return 16724787;	//red
		}
	}
	

}
