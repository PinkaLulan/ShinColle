package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;

/**
 * add more emotion
 */
public interface IModelEmotionAdv extends IModelEmotion
{
	
	
	/** set mouth: 0:普通, 1:歪嘴, 2:扁嘴, 3:無口, 4:張開, 5:大嘴 */
	public void setMouth(int par1);
	
	/** set flush */
	public void setFlush(boolean par1);
	
	/** set face by emotion */
	public void setFaceNormal(IShipEmotion ent);	//平常
	public void setFaceBlink0(IShipEmotion ent);	//眨眼, 開
	public void setFaceBlink1(IShipEmotion ent);	//眨眼, 閉
	public void setFaceCry(IShipEmotion ent);		//重傷
	public void setFaceAttack(IShipEmotion ent);	//攻擊
	public void setFaceDamaged(IShipEmotion ent);	//受攻擊
	public void setFaceHungry(IShipEmotion ent);	//無燃料
	public void setFaceAngry(IShipEmotion ent);		//生氣
	public void setFaceScorn(IShipEmotion ent);		//瞪人
	public void setFaceBored(IShipEmotion ent);		//無聊
	public void setFaceShy(IShipEmotion ent);		//害羞
	public void setFaceHappy(IShipEmotion ent);		//高興
	
	
}