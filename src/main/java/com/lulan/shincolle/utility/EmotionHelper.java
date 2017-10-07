package com.lulan.shincolle.utility;

import com.lulan.shincolle.client.model.IModelEmotion;
import com.lulan.shincolle.client.model.IModelEmotionAdv;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

import net.minecraft.util.math.MathHelper;

/**
 * EMOTION HELPER
 */
public class EmotionHelper
{
	
	
	public EmotionHelper() {}
	
	/** roll emotion */
    public static void rollEmotion(IModelEmotion model, IShipEmotion ent)
    { 
    	switch (ent.getStateEmotion(ID.S.Emotion))
    	{
    	case 6:		//for admiral desk function
    	case 7:		//for admiral desk function
    	case 8:		//for admiral desk function
    	case 9:		//for admiral desk function
    		model.setFace(ent.getStateEmotion(ID.S.Emotion) - 5);
		break;
    	case ID.Emotion.BLINK:	//blink
    		applyEmotionBlink(model, ent);
    	break;
    	case ID.Emotion.T_T:	//cry
    		model.setFace(2);
    	break;
    	case ID.Emotion.O_O:    //stare
    		applyEmotion(model, ent, ID.Emotion.O_O, 45);
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
    			applyEmotionBlink(model, ent);
    		}
    		
    		//roll emotion (about 3 times) every 6 sec
    		//1 tick in entity = ~3 tick in model class (20 vs 60 fps)
    		if ((ent.getTickExisted() & 127) == 0)
    		{
        		int emotionRand = ent.getRand().nextInt(10);
        		
        		if (emotionRand > 7)
        		{
        			applyEmotionBlink(model, ent);
        		} 		
        	}
    	break;
    	}	
    }
    
    /** roll emotion */
    public static void rollEmotionAdv(IModelEmotionAdv model, IShipEmotion ent)
    {
//    	LogHelper.debug("AAAAAAAA "+ent.getStateEmotion(ID.S.Emotion)+" "+ent.getFaceTick());
    	switch (ent.getStateEmotion(ID.S.Emotion))
    	{
    	case 9:					//for admiral desk function
    		model.setFace(ent.getStateEmotion(ID.S.Emotion));
		break;
    	case ID.Emotion.BLINK:	//blink
    		applyEmotionBlinkAdv(model, ent);
    	break;
    	case ID.Emotion.T_T:	//cry
    		applyEmotionAdv(model, ent, ID.Emotion.T_T, 80);
    	break;
    	case ID.Emotion.O_O:    //stare
    		applyEmotionAdv(model, ent, ID.Emotion.O_O, 45);
		break;
    	case ID.Emotion.HUNGRY: //hungry
    		model.setFaceHungry(ent);
    		ent.setFaceTick(-1);
		break;
    	case ID.Emotion.ANGRY:	//angry
    		applyEmotionAdv(model, ent, ID.Emotion.ANGRY, 40);
		break;
    	case ID.Emotion.SHY: 	//shy
    		applyEmotionAdv(model, ent, ID.Emotion.SHY, 80);
		break;
    	case ID.Emotion.XD:		//happy
    		applyEmotionAdv(model, ent, ID.Emotion.XD, 60);
		break;
    	case ID.Emotion.BORED:
    		model.setFaceBored(ent);  //NO BREAK! continue for blink rolling
    	default:				//roll blink
    		//reset face to 0 or blink if emotion time > 0
    		if (ent.getFaceTick() <= 0)
    		{
    			if (ent.getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED) model.setFaceNormal(ent);
    		}
    		else
    		{
    			applyEmotionBlinkAdv(model, ent);
    		}
    		//roll emotion (about 3 times) every 6 sec
    		//1 tick in entity = ~3 tick in model class (20 vs 60 fps)
    		if ((ent.getTickExisted() & 127) == 0)
    		{
        		int emotionRand = ent.getRand().nextInt(10);
        		
        		if (emotionRand > 7)
        		{
        			applyEmotionBlinkAdv(model, ent);
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
    	if (cd > 70 + ent.getRand().nextInt(5))
    	{
    		//update head tilt time
    		ent.setHeadTiltTick(ent.getTickExisted());
    		partTick = f2 - (int)f2;
    		
    		//roll head tilt state
			if (ent.getRand().nextInt(10) > 4)
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
    public static void applyEmotionBlink(IModelEmotion model, IShipEmotion ent)
    {
    	//要在沒表情狀態才做表情
  		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL)
  		{
  			ent.setFaceTick(ent.getTickExisted());						//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			model.setFace(1);
  		}
  		
  		int EmoTime = ent.getTickExisted() - ent.getFaceTick();
  		
  		//reset face
    	if (EmoTime > 25)
    	{
    		model.setFace(0);
    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BLINK)
    		{
    			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
    		}
			ent.setFaceTick(-1);
    	}
    	//20~25 -.-
    	else if (EmoTime > 20)
    	{
    		model.setFace(1);
    	}
    	//10~20 O_O
    	else if (EmoTime > 10)
    	{
    		model.setFace(0);
    	}
    	//0~10 -_-
    	else if (EmoTime > -1)
    	{
    		model.setFace(1);
    	}		
  	}
    
    /** 眨眼動作, this emotion is CLIENT ONLY, no sync packet required */
    public static void applyEmotionBlinkAdv(IModelEmotionAdv model, IShipEmotion ent)
    {
    	//要在沒表情狀態才做表情
  		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL)
  		{
  			ent.setFaceTick(ent.getTickExisted());						//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			model.setFaceBlink1(ent);
  		}
  		
  		int emoTime = ent.getTickExisted() - ent.getFaceTick();
  		
  		//reset face
    	if (emoTime > 25)
    	{
    		model.setFaceBlink0(ent);
    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BLINK)
    		{
    			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
    		}
			ent.setFaceTick(-1);
    	}
    	//20~25 -.-
    	else if (emoTime > 20)
    	{
    		model.setFaceBlink1(ent);
    	}
    	//10~20 O_O
    	else if (emoTime > 10)
    	{
    		model.setFaceBlink0(ent);
    	}
    	//0~10 -_-
    	else if (emoTime > -1)
    	{
    		model.setFaceBlink1(ent);
    	}		
  	}
  	
    /**
     * apply emotion with time limit
     *   parms: type: ID.Emotion
     */
    public static void applyEmotion(IModelEmotion model, IShipEmotion ent, int type, int maxTime)
    {
    	//表情開始時間
    	if (ent.getFaceTick() <= 0)
    	{
			ent.setFaceTick(ent.getTickExisted());
		}
    	
    	int emoTime = ent.getTickExisted() - ent.getFaceTick();
    	
    	//reset face
    	if (emoTime > maxTime)
    	{
    		model.setFace(0);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setFaceTick(-1);
    	}
    	else
    	{
    		switch (type)
    		{
    		case ID.Emotion.O_O:
    			model.setFace(3);
			break;
    		}
    	}
	}
    
    /**
     * apply emotion with time limit
     *   parms: type: ID.Emotion
     */
    public static void applyEmotionAdv(IModelEmotionAdv model, IShipEmotion ent, int type, int maxTime)
    {
    	//表情開始時間
    	if (ent.getFaceTick() <= 0)
    	{
			ent.setFaceTick(ent.getTickExisted());
		}
    	
    	int emoTime = ent.getTickExisted() - ent.getFaceTick();
    	
    	//reset face
    	if (emoTime > maxTime)
    	{
    		model.setFaceNormal(ent);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setFaceTick(-1);
    	}
    	else
    	{
    		switch (type)
    		{
    		case ID.Emotion.O_O:
    			if ((ent.getTickExisted() & 2047) > 1024)
    			{
    				model.setFaceDamaged(ent);
    			}
    			else
    			{
    				model.setFaceScorn(ent);
    			}
			break;
    		case ID.Emotion.T_T:
    			model.setFaceCry(ent);
			break;
    		case ID.Emotion.ANGRY:
    			model.setFaceAngry(ent);
			break;
    		case ID.Emotion.SHY:
    			model.setFaceShy(ent);
			break;
    		case ID.Emotion.XD:
    			model.setFaceHappy(ent);
			break;
    		}
    	}
	}
    
	/**
	 * check model display flags
	 * id = 0, 1, 2, ..., 15
	 * state = entity.getStateEmotion(ID.S.State)
	 */
	public static boolean checkModelState(int id, int state)
	{
		return (state & Values.N.Pow2[id]) == Values.N.Pow2[id];
	}
    
    
}