package com.lulan.shincolle.utility;

import java.util.Random;

import com.lulan.shincolle.client.model.IModelEmotion;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

/** EMOTION HELPER
 *  
 *  face icon id:
 *  0: normal
 *  1: blink
 *  2: cry
 *  3: stare
 *  4: hungry
 *  
 */
public class EmotionHelper {
	
	private static Random rand = new Random();
	
	
	public EmotionHelper() {}
	
	/** roll emotion */
    public static void rollEmotion(IModelEmotion model, IShipEmotion ent) { 
    	switch(ent.getStateEmotion(ID.S.Emotion)) {
    	case ID.Emotion.BLINK:	//blink
    		EmotionBlink(model, ent);
    		break;
    	case ID.Emotion.T_T:	//cry
//    		if(ent.getStartEmotion() <= 0) { model.setFace(2); }
    		model.setFace(2);
    		break;
    	case ID.Emotion.O_O:    //stare
    		EmotionStaring(model, ent);
			break;
    	case ID.Emotion.HUNGRY: //hungry
//    		if(ent.getStartEmotion() <= 0) { model.setFace(4); }
    		model.setFace(4);
			break;
    	case ID.Emotion.BORED:
    	default:						//normal face
    		//reset face to 0 or blink if emotion time > 0
    		if(ent.getFaceTick() <= 0) {
    			model.setFace(0);
    		}
    		else {
    			EmotionBlink(model, ent);
    		}
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if(ent.getTickExisted() % 120 == 0) {
        		int emotionRand = rand.nextInt(10);
        		
        		if(emotionRand > 7) {
        			EmotionBlink(model, ent);
        		} 		
        	}
    		break;
    	}	
    }
    
    /** 眨眼動作, this emotion is CLIENT ONLY, no sync packet required */
    public static void EmotionBlink(IModelEmotion model, IShipEmotion ent) {
  		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL) {	//要在沒表情狀態才做表情		
  			ent.setFaceTick(ent.getTickExisted());		//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			model.setFace(1);
  		}
  		
  		int EmoTime = ent.getTickExisted() - ent.getFaceTick();
    	 		
    	if(EmoTime > 25) {	//reset face
    		model.setFace(0);
    		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BLINK) {
    			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
    		}
			ent.setFaceTick(-1);
    	}
    	else if(EmoTime > 20) {  //20~25 -.-
    		model.setFace(1);
    	}
    	else if(EmoTime > 10) {  //10~20 O_O
    		model.setFace(0);
    	}
    	else if(EmoTime > -1) {  //0~10 -_-
    		model.setFace(1);
    	}		
  	}
  	
  	/** 瞪人表情 */
    public static void EmotionStaring(IModelEmotion model, IShipEmotion ent) {	
    	if(ent.getFaceTick() == -1) {
			ent.setFaceTick(ent.getTickExisted());	//表情開始時間
		}
    	
    	int EmoTime = ent.getTickExisted() - ent.getFaceTick();
    	
    	if(EmoTime > 41) {	//reset face
    		model.setFace(0);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setFaceTick(-1);
    	}
    	else if(EmoTime > 1) {
    		model.setFace(3);
    	}
	}
  	   
    
}
