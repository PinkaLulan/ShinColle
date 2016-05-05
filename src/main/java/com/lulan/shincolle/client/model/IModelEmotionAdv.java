package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;

public interface IModelEmotionAdv extends IModelEmotion {

	public void setFaceNormal();
	public void setFaceBlink0();
	public void setFaceBlink1();
	public void setFaceCry(IShipEmotion ent);
	public void setFaceAttack(IShipEmotion ent);
	public void setFaceDamaged(IShipEmotion ent);
	public void setFaceHungry(IShipEmotion ent);
	public void setFaceAngry(IShipEmotion ent);
	public void setFaceScorn(IShipEmotion ent);
	public void setFaceBored(IShipEmotion ent);
	
}
