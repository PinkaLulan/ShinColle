package com.lulan.shincolle.utility;

import java.util.Random;

import com.lulan.shincolle.client.model.IModelEmotion;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

public class EmotionHelper {
	
	private static Random rand = new Random();
	
	
	public EmotionHelper() {}
	
	/** 隨機抽取顯示的表情 */
    public static void rollEmotion(IModelEmotion model, IShipEmotion ent) { 
    	switch(ent.getStateEmotion(ID.S.Emotion)) {
    	case ID.Emotion.BLINK:	//blink
    		EmotionBlink(model, ent);
    		break;
    	case ID.Emotion.T_T:	//cry
    		if(ent.getStartEmotion() <= 0) { model.setFace(2); }
    		break;
    	case ID.Emotion.O_O:
    		EmotionStaring(model, ent);
			break;
    	case ID.Emotion.HUNGRY:
    		if(ent.getStartEmotion() <= 0) { model.setFace(4); }
			break;
    	case ID.Emotion.BORED:
    	default:						//normal face
    		//reset face to 0 or blink if emotion time > 0
    		if(ent.getStartEmotion() <= 0) {
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
  			ent.setStartEmotion(ent.getTickExisted());		//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			model.setFace(1);
  		}
  		
  		int EmoTime = ent.getTickExisted() - ent.getStartEmotion();
    	 		
    	if(EmoTime > 46) {	//reset face
    		model.setFace(0);
    		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BLINK) {
    			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
    		}
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 35) {
    		model.setFace(1);
    	}
    	else if(EmoTime > 25) {
    		model.setFace(0);
    	}
    	else if(EmoTime > -1) {
    		model.setFace(1);
    	}		
  	}
  	
  	/** 瞪人表情 */
    public static void EmotionStaring(IModelEmotion model, IShipEmotion ent) {	
    	if(ent.getStartEmotion() == -1) {
			ent.setStartEmotion(ent.getTickExisted());	//表情開始時間
		}
    	
    	int EmoTime = ent.getTickExisted() - ent.getStartEmotion();
    	
    	if(EmoTime > 41) {	//reset face
    		model.setFace(0);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 1) {
    		model.setFace(3);
    	}
	}
  	   
    
}
