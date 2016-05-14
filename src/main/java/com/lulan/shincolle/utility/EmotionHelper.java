package com.lulan.shincolle.utility;

import java.util.Random;

import net.minecraft.util.MathHelper;

import com.lulan.shincolle.client.model.IModelEmotion;
import com.lulan.shincolle.client.model.IModelEmotionAdv;
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
    public static void rollEmotion(IModelEmotion model, IShipEmotion ent)
    { 
    	switch (ent.getStateEmotion(ID.S.Emotion))
    	{
    	//for admiral desk function
    	case 6:
    	case 7:
    	case 8:
    	case 9:
    	case 10:
    		model.setFace(ent.getStateEmotion(ID.S.Emotion) - 6);
			break;
    	case ID.Emotion.BLINK:	//blink
    		EmotionBlink(model, ent);
    		break;
    	case ID.Emotion.T_T:	//cry
    		model.setFace(2);
    		break;
    	case ID.Emotion.O_O:    //stare
    		EmotionStaring(model, ent);
			break;
    	case ID.Emotion.HUNGRY: //hungry
    		model.setFace(4);
			break;
    	case ID.Emotion.BORED:
    	default:				//roll blink
    		//reset face to 0 or blink if emotion time > 0
    		if (ent.getFaceTick() <= 0)
    		{
    			model.setFace(0);
    		}
    		else
    		{
    			EmotionBlink(model, ent);
    		}
    		
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if (ent.getTickExisted() % 120 == 0)
    		{
        		int emotionRand = rand.nextInt(10);
        		
        		if (emotionRand > 7)
        		{
        			EmotionBlink(model, ent);
        		} 		
        	}
    		break;
    	}	
    }
    
    /** roll emotion */
    public static void rollEmotionAdv(IModelEmotionAdv model, IShipEmotion ent)
    { 
    	switch (ent.getStateEmotion(ID.S.Emotion))
    	{
    	//for admiral desk function
    	case 6:
    	case 7:
    	case 8:
    	case 9:
    	case 10:
    		model.setFace(ent.getStateEmotion(ID.S.Emotion) - 1);
			break;
    	case ID.Emotion.BLINK:	//blink
    		EmotionBlinkAdv(model, ent);
    		break;
    	case ID.Emotion.T_T:	//cry
    		model.setFaceCry(ent);
    		break;
    	case ID.Emotion.O_O:    //stare
    		if (ent.getTickExisted() % 2048 > 1024)
    		{
    			model.setFaceDamaged(ent);
    		}
    		else
    		{
    			EmotionStaringAdv(model, ent);
    		}
			break;
    	case ID.Emotion.HUNGRY: //hungry
    		model.setFaceHungry(ent);
			break;
    	case ID.Emotion.BORED:
    		model.setFaceBored(ent);  //do not break, continue for blink
    	default:				//roll blink
    		//reset face to 0 or blink if emotion time > 0
    		if (ent.getFaceTick() <= 0)
    		{
    			if (ent.getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED) model.setFaceNormal();
    		}
    		else
    		{
    			EmotionBlinkAdv(model, ent);
    		}
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if (ent.getTickExisted() % 128 == 0)
    		{
        		int emotionRand = rand.nextInt(10);
        		
        		if (emotionRand > 7)
        		{
        			EmotionBlinkAdv(model, ent);
        		} 		
        	}
    		break;
    	}	
    }
    
    /** 歪頭動作 */
    public static float getHeadTiltAngle(IShipEmotion ent, float f2)
    {
    	int cd = ent.getTickExisted() - ent.getHeadTiltTick();
    	float maxAngle = -0.27F;
    	float partTick = f2 - (int)f2 + cd;
    	
    	//check head tilt CD
    	if (cd > 70 + rand.nextInt(5))
    	{
    		//update head tilt time
    		ent.setHeadTiltTick(ent.getTickExisted());
    		partTick = f2 - (int)f2;
    		
    		//roll head tilt state
			if (rand.nextInt(10) > 4)
			{
				ent.setStateFlag(ID.F.HeadTilt, true);
	    	}
	    	else
	    	{
	    		ent.setStateFlag(ID.F.HeadTilt, false);
	    	}
		}
    	
    	//歪頭flag
	    if (ent.getStateFlag(ID.F.HeadTilt))
	    {
	    	//之前已經完成歪頭, 則保持歪頭
	    	if (ent.getStateEmotion(ID.S.Emotion2) > 0)
	    	{
	    		return maxAngle;
	    	}
	    	//尚未歪頭, 計算角度
	    	else
	    	{
	    		float f = MathHelper.sin(partTick * 0.1F * 1.5708F) * maxAngle;
//	    		float f = partTick * 0.1F * maxAngle;

	    		//已達最大角度
	    		if (f - 0.03F < maxAngle || partTick > 10F)
	    		{
	    			//標記為歪頭狀態
	    			ent.setStateEmotion(ID.S.Emotion2, 1, false);
	    			f = maxAngle;
	    		}
	    		
	    		return f;
	    	}
	    }
	    else
	    {
	    	//尚未歪頭, 保持原狀
	    	if (ent.getStateEmotion(ID.S.Emotion2) <= 0)
	    	{
	    		return 0F;
	    	}
	    	//已經歪頭, 計算角度
	    	else
	    	{
	    		float f = (1F - MathHelper.sin(partTick * 0.2F * 1.5708F)) * maxAngle;
//	    		float f = (1F - partTick * 0.2F) * maxAngle;

	    		//已達0度
		    	if (f + 0.03F > 0F || partTick > 8F)
		    	{
		    		//標記為無歪頭狀態
	    			ent.setStateEmotion(ID.S.Emotion2, 0, false);
	    			f = 0F;
		    	}
		    	
		    	return f;
	    	}
	    }
    }
    
    /** 眨眼動作, this emotion is CLIENT ONLY, no sync packet required */
    public static void EmotionBlink(IModelEmotion model, IShipEmotion ent)
    {
  		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL)
  		{	//要在沒表情狀態才做表情
  			ent.setFaceTick(ent.getTickExisted());						//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			model.setFace(1);
  		}
  		
  		int EmoTime = ent.getTickExisted() - ent.getFaceTick();
    	 		
    	if (EmoTime > 25)
    	{	//reset face
    		model.setFace(0);
    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BLINK)
    		{
    			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
    		}
			ent.setFaceTick(-1);
    	}
    	else if (EmoTime > 20)
    	{  //20~25 -.-
    		model.setFace(1);
    	}
    	else if (EmoTime > 10)
    	{  //10~20 O_O
    		model.setFace(0);
    	}
    	else if (EmoTime > -1)
    	{  //0~10 -_-
    		model.setFace(1);
    	}		
  	}
    
    /** 眨眼動作, this emotion is CLIENT ONLY, no sync packet required */
    public static void EmotionBlinkAdv(IModelEmotionAdv model, IShipEmotion ent)
    {
  		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL)
  		{	//要在沒表情狀態才做表情		
  			ent.setFaceTick(ent.getTickExisted());						//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			model.setFaceBlink1();
  		}
  		
  		int EmoTime = ent.getTickExisted() - ent.getFaceTick();
    	 		
    	if (EmoTime > 25)
    	{	//reset face
    		model.setFaceBlink0();
    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BLINK)
    		{
    			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
    		}
			ent.setFaceTick(-1);
    	}
    	else if (EmoTime > 20)
    	{  //20~25 -.-
    		model.setFaceBlink1();
    	}
    	else if (EmoTime > 10)
    	{  //10~20 O_O
    		model.setFaceBlink0();
    	}
    	else if (EmoTime > -1)
    	{  //0~10 -_-
    		model.setFaceBlink1();
    	}		
  	}
  	
  	/** 瞪人表情 */
    public static void EmotionStaring(IModelEmotion model, IShipEmotion ent)
    {	
    	if (ent.getFaceTick() == -1)
    	{
			ent.setFaceTick(ent.getTickExisted());	//表情開始時間
		}
    	
    	int EmoTime = ent.getTickExisted() - ent.getFaceTick();
    	
    	if (EmoTime > 41)
    	{	//reset face
    		model.setFace(0);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setFaceTick(-1);
    	}
    	else if (EmoTime > 1)
    	{
    		model.setFace(3);
    	}
	}
    
    /** 瞪人表情 */
    public static void EmotionStaringAdv(IModelEmotionAdv model, IShipEmotion ent)
    {	
    	if (ent.getFaceTick() == -1)
    	{
			ent.setFaceTick(ent.getTickExisted());	//表情開始時間
		}
    	
    	int EmoTime = ent.getTickExisted() - ent.getFaceTick();
    	
    	if (EmoTime > 41)
    	{	//reset face
    		model.setFaceNormal();
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setFaceTick(-1);
    	}
    	else if (EmoTime > 1)
    	{
    		model.setFaceScorn(ent);
    	}
	}
  	   
    
}
