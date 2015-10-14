package com.lulan.shincolle.utility;


/**FORMAT HELPER
 * format and math method
 */
public class CalcHelper {
	
	public CalcHelper() {
	}
	
	/** 將second轉成格式 00:00:00 */
	public static String getTimeFormated(int sec) {
		int timeSec = 0;
		int timeMin = 0;
		int timeHr = 0;
		
		timeSec = sec % 60;
		timeMin = (sec % 3600) / 60;
		timeHr = sec / 3600;
		
		return get2Digit(timeHr) + ":" + get2Digit(timeMin) + ":" + get2Digit(timeSec);
	}

	/** 將數字轉為2字母字串, 注意num > 0 */
	public static String get2Digit(int num) {
	    if(num == 0) {
	        return "00";
	    }
	    
	    if(num < 10) {
	        return "0" + num;
	    }

	    return String.valueOf(num);
	}
	
	/** return 0 if par1 = true */
	public static int boolean2int(boolean par1) {
		return par1 ? 0 : 1;
	}
	
	/** 3 number find min, if equal, return a->b->c 
	 *  return ordinal number (1,2,3)
	 */
	public static int min(double a, double b, double c) {
		if(a <= b) {		//a <= b
			if(a <= c) {
				return 1;
			}
			else {
				return 3;
			}
		}
		else {				//a >  b
			if(b <= c) {
				return 2;
			}
			else {
				return 3;
			}
		}
	}
    

}
