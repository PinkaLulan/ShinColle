package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * model base for advance emotion
 */
abstract public class ShipModelBaseAdv extends ModelBase implements IModelEmotionAdv
{

	/** basic model */
	public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Mouth0;
    public ModelRenderer Mouth1;
    public ModelRenderer Mouth2;
    public ModelRenderer Flush0;
    public ModelRenderer Flush1;
    
	
	public ShipModelBaseAdv() {}
	
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    /**
     * set model pose
     */
    @Override
    public void setRotationAngles(float swingTick, float swingAmount, float tick, float yaw, float pitch, float scale, Entity entity)
    { 	
    	super.setRotationAngles(swingTick, swingAmount, tick, yaw, pitch, scale, entity);

		IShipEmotion ent = (IShipEmotion) entity;
		
		//set equip
		this.showEquip(ent);
		
		//apply flush
		if (ent.getStateMinor(ID.M.Morale) > 3900)
		{
			this.setFlush(true);
		}
		else
		{
			this.setFlush(false);
		}
		
		//roll emotion
		EmotionHelper.rollEmotionAdv(this, ent);
		
		if (ent.getStateFlag(ID.F.NoFuel))
		{
			this.applyDeadPose(swingTick, swingAmount, tick, yaw, pitch, ent);
		}
		else
		{
			this.applyNormalPose(swingTick, swingAmount, tick, yaw, pitch, ent);
		}
		
		this.syncRotationGlowPart();
    }
    
    //設定顯示的臉型
   	@Override
   	public void setFace(int emo)
   	{
   		switch (emo)
   		{
   		case 0:
   			this.Face0.isHidden = false;
   			this.Face0.rotateAngleY = 0F;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = true;
   		break;
   		case 1:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = false;
   			this.Face1.rotateAngleY = 0F;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = true;
   		break;
   		case 2:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = false;
   			this.Face2.rotateAngleY = 0F;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = true;
   		break;
   		case 3:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = false;
   			this.Face3.rotateAngleY = 0F;
   			this.Face4.isHidden = true;
   		break;
   		case 4:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = false;
   			this.Face4.rotateAngleY = 0F;
   		break;
   		case 5:
   			this.Face0.isHidden = false;
   			this.Face0.rotateAngleY = 3.14159F;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = true;
   		break;
   		case 6:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = false;
   			this.Face1.rotateAngleY = 3.14159F;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = true;
   		break;
   		case 7:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = false;
   			this.Face2.rotateAngleY = 3.14159F;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = true;
   		break;
   		case 8:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = false;
   			this.Face3.rotateAngleY = 3.14159F;
   			this.Face4.isHidden = true;
   		break;
   		case 9:
   			this.Face0.isHidden = true;
   			this.Face1.isHidden = true;
   			this.Face2.isHidden = true;
   			this.Face3.isHidden = true;
   			this.Face4.isHidden = false;
   			this.Face4.rotateAngleY = 3.14159F;
   		break;
   		default:
   		break;
   		}
   	}
   	
    //設定顯示的嘴型
   	@Override
   	public void setMouth(int emo)
   	{
   		switch (emo)
   		{
   		case 0:
   			this.Mouth0.isHidden = false;
   			this.Mouth0.rotateAngleY = 0F;
   			this.Mouth1.isHidden = true;
   			this.Mouth2.isHidden = true;
   		break;
   		case 1:
   			this.Mouth0.isHidden = true;
   			this.Mouth1.isHidden = false;
   			this.Mouth1.rotateAngleY = 0F;
   			this.Mouth2.isHidden = true;
   		break;
   		case 2:
   			this.Mouth0.isHidden = true;
   			this.Mouth1.isHidden = true;
   			this.Mouth2.isHidden = false;
   			this.Mouth2.rotateAngleY = 0F;
   		break;
   		case 3:
   			this.Mouth0.isHidden = false;
   			this.Mouth0.rotateAngleY = 3.14159F;
   			this.Mouth1.isHidden = true;
   			this.Mouth2.isHidden = true;
   		break;
   		case 4:
   			this.Mouth0.isHidden = true;
   			this.Mouth1.isHidden = false;
   			this.Mouth1.rotateAngleY = 3.14159F;
   			this.Mouth2.isHidden = true;
   		break;
   		case 5:
   			this.Mouth0.isHidden = true;
   			this.Mouth1.isHidden = true;
   			this.Mouth2.isHidden = false;
   			this.Mouth2.rotateAngleY = 3.14159F;
   		break;
   		default:
   		break;
   		}
   	}
   	
    //設定是否顯示臉紅紅
   	@Override
   	public void setFlush(boolean show)
   	{
   		if (show)
   		{
   			this.Flush0.isHidden = false;
   			this.Flush1.isHidden = false;
   		}
   		else
   		{
   			this.Flush0.isHidden = true;
   			this.Flush1.isHidden = true;
   		}
   	}

	@Override
	public void setFaceNormal(IShipEmotion ent)
	{
		this.setFace(0);
		
		if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED && (ent.getTickExisted() & 255) > 160)
		{
			this.setMouth(3);
		}
		else
		{
			this.setMouth(0);
		}
	}

	@Override
	public void setFaceBlink0(IShipEmotion ent)
	{
		this.setFace(0);
	}

	@Override
	public void setFaceBlink1(IShipEmotion ent)
	{
		this.setFace(1);
	}

	@Override
	public void setFaceCry(IShipEmotion ent)
	{
		int t = ent.getTickExisted() & 255;
		
		if (t < 128)
		{
			this.setFace(6);
			
			if (t < 64)
			{
				this.setMouth(5);
			}
			else
			{
				this.setMouth(2);
			}
		}
		else
		{
			this.setFace(7);
			this.setMouth(2);
		}
	}

	@Override
	public void setFaceAttack(IShipEmotion ent)
	{
		int t = ent.getTickExisted() & 511;
		
		if (t < 128)
		{
			this.setFace(1);
			
			if (t < 64)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(2);
			}
		}
		else if (t < 256)
		{
			this.setFace(2);
			
			if (t < 180)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else if (t < 384)
		{
			this.setFace(3);
			
			if (t < 320)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(8);
			
			if (t < 450)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
	}
	
	@Override
	public void setFaceDamaged(IShipEmotion ent)
	{
		int t = ent.getTickExisted() & 511;
		
		if (t < 170)
		{
			this.setFace(6);
			
			if (t < 60)
			{
				this.setMouth(5);
			}
			else
			{
				this.setMouth(2);
			}
		}
		else if (t < 340)
		{
			this.setFace(3);
			
			if (t < 250)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(9);
			
			if (t < 450)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
	}
	
	@Override
	public void setFaceScorn(IShipEmotion ent)
	{
		this.setFace(2);
		this.setMouth(1);
	}

	@Override
	public void setFaceHungry(IShipEmotion ent)
	{
		this.setFace(4);	
		this.setMouth(2);
	}

	@Override
	public void setFaceAngry(IShipEmotion ent)
	{
		int t = ent.getTickExisted() & 255;
		
		if (t < 128)
		{
			this.setFace(1);
			
			if (t < 64)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else
		{
			this.setFace(2);

			if (t < 170)
			{
				this.setMouth(1);
			}
			else
			{
				this.setMouth(2);
			}
		}
	}

	@Override
	public void setFaceBored(IShipEmotion ent)
	{
		int t = ent.getTickExisted() & 511;
		
		if (t < 170)
		{
			this.setFace(5);
			
			if (t < 60)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else if (t < 340)
		{
			this.setFace(8);
			this.setMouth(0);
		}
		else
		{
			this.setFace(9);
			
			if (t < 450)
			{
				this.setMouth(5);
			}
			else
			{
				this.setMouth(2);
			}
		}
	}
	
	@Override
	public void setFaceShy(IShipEmotion ent)
	{
		this.setFlush(true);
		
		int t = ent.getTickExisted() & 255;
		
		if (t < 140)
		{
			this.setFace(0);
			
			if (t < 80)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(2);
			}
		}
		else
		{
			this.setFace(8);
			this.setMouth(0);
		}
	}
	
	@Override
	public void setFaceHappy(IShipEmotion ent)
	{
		this.setFace(8);
		this.setMouth(0);
		this.setFlush(true);
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void setField(int id, float value)
	{
	}

	@Override
	public float getField(int id)
	{
		return 0;
	}
    
    
}
