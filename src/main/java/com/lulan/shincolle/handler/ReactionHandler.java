package com.lulan.shincolle.handler;

import java.util.Random;

import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.BodyHeight;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class ReactionHandler
{
    
    protected EntityLivingBase host;
    protected Random rand;
    
    
    public ReactionHandler(EntityLivingBase host)
    {
        this.host = host;
        this.rand = this.host.getRNG();
    }
    
    /** eat item */
    public void reactEatItem()
    {
        switch (this.rand.nextInt(4))
        {
        case 1:
            applyParticleEmotion(29);  //blink
        break;
        case 2:
            applyParticleEmotion(30);  //pif
        break;
        default:
            applyParticleEmotion(9);   //hungry
        break;
        }
    }
    
    /** has no item */
    public void reactItemNotFound()
    {
        switch (this.rand.nextInt(7))
        {
        case 1:
            applyParticleEmotion(0);   //drop
        break;
        case 2:
            applyParticleEmotion(2);   //panic
        break;
        case 3:
            applyParticleEmotion(5);   //...
        break;
        case 4:
            applyParticleEmotion(20);  //orz
        break;
        default:
            applyParticleEmotion(32);  //hmm
        break;
        }
    }
    
    
    
    
    
    
    
    
    
    
    /** TODO OLD METHODS */
    
    /** knockback AI target */
    public void pushAITarget()
    {
        if (this.aiTarget != null)
        {
            //swing arm
            this.swingArm(EnumHand.MAIN_HAND);
            
            //push target
            this.aiTarget.addVelocity(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * 0.5F, 
                     0.5D, MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * 0.5F);
          
            //sync target motion
            TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
            CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.aiTarget, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
        }
    }
      
    //check caress state for model display, CLIENT SIDE
    public void checkCaressed()
    {
        BodyHeight hit = getBodyIDFromHeight();
          
        //default: only top or head = caressed
        if (hit == BodyHeight.TOP || hit == BodyHeight.HEAD ||
            hit == BodyHeight.NECK || hit == BodyHeight.CHEST)
        {
            setStateEmotion(ID.S.Emotion3, ID.Emotion3.CARESS, false);
            setStateTimer(ID.T.Emotion3Time, 80);
        }
    }
      
    /** normal emotes for head caress */
    public void reactionNormal()
    {
        Random ran = new Random();
        int m = this.getMorale();
        int body = EntityHelper.getHitBodyID(this);
        int baseMorale = (int) ((float)ConfigHandler.baseCaressMorale * 2.5F);
        LogHelper.debug("DEBUG: hit ship: Morale: "+m+" BodyID: "+body+" sensitiveBodyID: "+this.getSensitiveBody());         
          
        //show emotes by morale level
        switch (EntityHelper.getMoraleLevel(m))
        {
        case 0:   //excited
            //check sensitive body
            if (body == getSensitiveBody())
            {
                //apply emotion
                this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
                
                if (this.rand.nextInt(2) == 0)
                {
                    applyParticleEmotion(31);  //shy
                }
                else
                {
                    applyParticleEmotion(10);  //dizzy
                }
                
                if (m < (int)(ID.Morale.L_Excited * 1.5F))
                {
                    this.addMorale(baseMorale * 3 + this.rand.nextInt(baseMorale + 1));
                }
            }
            //other reaction
            else
            {
                //apply emotion
                this.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
                  
                switch (body)
                {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    if (this.getStateFlag(ID.F.IsMarried))
                    {
                        applyParticleEmotion(15);  //kiss
                    }
                    else
                    {
                        applyParticleEmotion(1);  //heart
                    }
                break;
                default:
                    if (this.rand.nextInt(2) == 0)
                    {
                        applyParticleEmotion(1);  //heart
                    }
                    else
                    {
                        applyParticleEmotion(7);  //note
                    }
                break;
                }
            }
        break;
        case 1:   //happy
            //apply emotion
            this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
              
            //check sensitive body
            if (body == getSensitiveBody())
            {
                if (this.getStateFlag(ID.F.IsMarried))
                {
                    if (this.rand.nextInt(2) == 0)
                    {
                        applyParticleEmotion(31);  //shy
                    }
                    else
                    {
                        applyParticleEmotion(10);  //dizzy
                    }
                }
                else
                {
                    applyParticleEmotion(10);  //dizzy
                }
                  
                this.addMorale(baseMorale + this.rand.nextInt(baseMorale + 1));
            }
            //other reaction
            else
            {
                switch (body)
                {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    if (this.getStateFlag(ID.F.IsMarried))
                    {
                        applyParticleEmotion(1);  //heart
                    }
                    else
                    {
                        applyParticleEmotion(16);  //haha
                    }
                break;
                default:
                    if (this.rand.nextInt(2) == 0)
                    {
                        applyParticleEmotion(1);  //heart
                    }
                    else
                    {
                        applyParticleEmotion(7);  //note
                    }
                break;
                }
            }
        break;
        case 2:   //normal
            //check sensitive body
            if (body == getSensitiveBody())
            {
                //apply emotion
                this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
                  
                if (this.getStateFlag(ID.F.IsMarried))
                {
                    applyParticleEmotion(19);  //lick
                }
                else
                {
                    applyParticleEmotion(18);  //sigh
                }
                  
                this.addMorale(baseMorale + this.rand.nextInt(baseMorale + 1));
                  
                //push target
                if (ran.nextInt(6) == 0)
                {
                    this.pushAITarget();
                    this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                }
            }
            //other reaction
            else
            {
                switch (body)
                {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    if (this.getStateFlag(ID.F.IsMarried))
                    {
                        applyParticleEmotion(1);  //heart
                    }
                    else
                    {
                        applyParticleEmotion(27);  //-w-
                    }
                    
                    //push target
                    if (ran.nextInt(8) == 0)
                    {
                        this.pushAITarget();
                        this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                    }
                break;
                default:
                    switch (this.rand.nextInt(7))
                    {
                    case 1:
                        applyParticleEmotion(30);  //pif
                        break;
                    case 3:
                        applyParticleEmotion(7);  //note
                        break;
                    case 4:
                        applyParticleEmotion(26);  //ya
                        break;
                    case 6:
                        applyParticleEmotion(11);  //find
                        break;
                    default:
                        applyParticleEmotion(29);  //blink
                        break;
                    }
                break;
                }
            }
        break;
        case 3:   //tired
            //check sensitive body
            if  (body == getSensitiveBody())
            {
                //apply emotion
                this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
                  
                applyParticleEmotion(32);  //hmm
                this.addMorale(this.rand.nextInt(baseMorale + 1));
                  
                //push target
                if (ran.nextInt(2) == 0)
                {
                    this.pushAITarget();
                    this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                }
                else if (this.aiTarget != null && ran.nextInt(8) == 0)
                {
                    switch (ran.nextInt(3))
                    {
                    case 0:
                        attackEntityWithAmmo(this.aiTarget);
                    break;
                    case 1:
                        attackEntityWithHeavyAmmo(this.aiTarget);
                    break;
                    default:
                        attackEntityAsMob(this.aiTarget);
                    break;
                    }
                }
            }
            //other reaction
            else
            {
                switch (body)
                {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
                    applyParticleEmotion(32);  //hmm
                    //push target
                    if (ran.nextInt(4) == 0)
                    {
                        this.pushAITarget();
                        this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                    }
                break;
                default:
                    switch (this.rand.nextInt(5))
                    {
                    case 1:
                        applyParticleEmotion(30);  //pif
                    break;
                    case 2:
                        applyParticleEmotion(2);  //panic
                    break;
                    case 4:
                        applyParticleEmotion(3);  //?
                    break;
                    default:
                        applyParticleEmotion(0);  //sweat
                    break;
                    }
                break;
                }
            }
        break;
        default:  //exhausted
            //check sensitive body
              if (body == getSensitiveBody())
              {
                  //apply emotion
                  this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
                  
                  applyParticleEmotion(6);  //angry
                  this.addMorale((baseMorale * 10 + this.rand.nextInt(baseMorale * 5 + 1)) * -1);
                  
                  //push target
                  this.pushAITarget();
                  this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                
                if (this.aiTarget != null && ran.nextInt(3) == 0)
                {
                    switch (ran.nextInt(3))
                    {
                    case 0:
                        attackEntityWithAmmo(this.aiTarget);
                        break;
                    case 1:
                        attackEntityWithHeavyAmmo(this.aiTarget);
                        break;
                    default:
                        attackEntityAsMob(this.aiTarget);
                        break;
                    }
                  }
              }
              //other reaction
              else
              {
                  switch (body)
                  {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, true);
                    
                    if (this.rand.nextInt(3) == 0)
                    {
                        applyParticleEmotion(6);  //angry
                    }
                    else
                    {
                        applyParticleEmotion(32);  //hmm
                    }
                    
                    //push target
                      if (ran.nextInt(2) == 0)
                      {
                          this.pushAITarget();
                          this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                      }
                      else if (this.aiTarget != null && ran.nextInt(5) == 0)
                      {
                          switch (ran.nextInt(3))
                          {
                        case 0:
                            attackEntityWithAmmo(this.aiTarget);
                            break;
                        case 1:
                            attackEntityWithHeavyAmmo(this.aiTarget);
                            break;
                        default:
                            attackEntityAsMob(this.aiTarget);
                            break;
                        }
                      }
                    break;
                default:
                    switch (this.rand.nextInt(5))
                    {
                    case 1:
                        applyParticleEmotion(8);  //cry
                        break;
                    case 2:
                        applyParticleEmotion(2);  //panic
                        break;
                    case 3:
                        applyParticleEmotion(20);  //orz
                        break;
                    case 4:
                        applyParticleEmotion(5);  //...
                        break;
                    default:
                        applyParticleEmotion(34);  //lll
                        break;
                    }
                    break;
                }
              }
            break;
        }//end morale level switch
      }
      
      /** stranger (not owner) emotes */
      public void reactionStranger()
      {
          int body = EntityHelper.getHitBodyID(this);
          LogHelper.debug("DEBUG: hit ship: BodyID: "+body+" sensitiveBodyID: "+this.getSensitiveBody());         

        //check sensitive body
          if (body == getSensitiveBody())
          {
              //apply emotion
              this.setStateEmotion(ID.S.Emotion, ID.Emotion.ANGRY, true);
              
              if (this.rand.nextInt(2) == 0)
              {
                applyParticleEmotion(6);  //angry
            }
            else
            {
                applyParticleEmotion(22);  //x
            }
              
              //push target
              if (rand.nextInt(2) == 0)
              {
                  this.pushAITarget();
                  this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
              }
              else if (this.aiTarget != null && rand.nextInt(4) == 0)
              {
                  switch (rand.nextInt(3))
                  {
                case 0:
                    attackEntityWithAmmo(this.aiTarget);
                    break;
                case 1:
                    attackEntityWithHeavyAmmo(this.aiTarget);
                    break;
                default:
                    attackEntityAsMob(this.aiTarget);
                    break;
                }
              }
          }
          //other reaction
          else
          {
              //apply emotion
              this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
              
              switch (body)
              {
            case ID.Body.UBelly:
            case ID.Body.Butt:
            case ID.Body.Chest:
            case ID.Body.Face:
                if (this.rand.nextInt(2) == 0)
                {
                    applyParticleEmotion(6);  //angry
                }
                else
                {
                    applyParticleEmotion(5);  //...
                }
                
                //push target
                  if (rand.nextInt(4) == 0)
                  {
                      this.pushAITarget();
                      this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
                  }
                  else if (this.aiTarget != null && rand.nextInt(8) == 0)
                  {
                      switch (rand.nextInt(3))
                      {
                    case 0:
                        attackEntityWithAmmo(this.aiTarget);
                        break;
                    case 1:
                        attackEntityWithHeavyAmmo(this.aiTarget);
                        break;
                    default:
                        attackEntityAsMob(this.aiTarget);
                        break;
                    }
                  }
                break;
            default:
                switch (this.rand.nextInt(7))
                {
                case 1:
                    applyParticleEmotion(9);  //hungry
                    break;
                case 2:
                    applyParticleEmotion(2);  //panic
                    break;
                case 3:
                    applyParticleEmotion(20);  //orz
                    break;
                case 4:
                    applyParticleEmotion(8);  //cry
                    break;
                case 5:
                    applyParticleEmotion(0);  //sweat
                    break;
                default:
                    applyParticleEmotion(34);  //lll
                    break;
                }
                break;
            }
          }
      }
      
      /** damaged emotes */
      public void reactionAttack()
      {
          //show emotes by morale level
        switch (EntityHelper.getMoraleLevel(this.getMorale()))
        {
        case 0:   //excited
              //apply emotion
              this.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
              
            switch (this.rand.nextInt(8))
            {
            case 1:
                applyParticleEmotion(33);  //:p
                break;
            case 2:
                applyParticleEmotion(17);  //gg
                break;
            case 3:
                applyParticleEmotion(19);  //lick
                break;
            case 4:
                applyParticleEmotion(16);  //ha
                break;
            default:
                applyParticleEmotion(7);  //note
                break;
            }
            break;
        case 1:   //happy
        case 2:   //normal
        case 3:   //tired
        default:  //exhausted
            switch (this.rand.nextInt(8))
            {
            case 1:
                applyParticleEmotion(14);  //+_+
                break;
            case 2:
                applyParticleEmotion(30);  //pif
                break;
            case 3:
                applyParticleEmotion(7);  //note
                break;
            case 4:
                applyParticleEmotion(4);  //!
                break;
            case 5:
                applyParticleEmotion(7);  //note
                break;
            default:
                applyParticleEmotion(6);  //angry
                break;
            }
            break;
        }//end morale level switch
      }
      
      /** damaged emotes */
      public void reactionDamaged()
      {
          int body = EntityHelper.getHitBodyID(this);
          
          //show emotes by morale level
        switch (EntityHelper.getMoraleLevel(this.getMorale()))
        {
        case 0:   //excited
        case 1:   //happy
        case 2:   //normal
            //check sensitive body
              if (body == getSensitiveBody())
              {
                  applyParticleEmotion(6);  //angry
              }
              //other reaction
              else
              {
                switch (body)
                {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    applyParticleEmotion(6);  //angry
                    break;
                default:
                    switch (this.rand.nextInt(7))
                    {
                    case 1:
                        applyParticleEmotion(30);  //pif
                        break;
                    case 2:
                        applyParticleEmotion(5);  //...
                        break;
                    case 3:
                        applyParticleEmotion(2);  //panic
                        break;
                    case 4:
                        applyParticleEmotion(3);  //?
                        break;
                    default:
                        applyParticleEmotion(8);  //cry
                        break;
                    }
                    break;
                }
              }
            break;
        case 3:   //tired
        default:  //exhausted
            //check sensitive body
              if (body == getSensitiveBody())
              {
                  applyParticleEmotion(10);  //dizzy
              }
              //other reaction
              else
              {
                  switch (body)
                  {
                case ID.Body.UBelly:
                case ID.Body.Butt:
                case ID.Body.Chest:
                case ID.Body.Face:
                    applyParticleEmotion(10);  //dizzy
                    break;
                default:
                    switch (this.rand.nextInt(7))
                    {
                    case 1:
                        applyParticleEmotion(30);  //pif
                        break;
                    case 2:
                        applyParticleEmotion(5);  //...
                        break;
                    case 3:
                        applyParticleEmotion(2);  //panic
                        break;
                    case 4:
                        applyParticleEmotion(3);  //?
                        break;
                    case 5:
                        applyParticleEmotion(0);  //sweat
                        break;
                    default:
                        applyParticleEmotion(8);  //cry
                        break;
                    }
                }
              }
            break;
        }//end morale level switch
      }
      
      /** idle emotes */
      public void reactionIdle()
      {
          //show emotes by morale level
        switch (EntityHelper.getMoraleLevel(this.getMorale()))
        {
        case 0:   //excited
        case 1:   //happy
            if (this.getStateFlag(ID.F.IsMarried) && this.rand.nextInt(2) == 0)
            {
                switch (this.rand.nextInt(3))
                {
                case 1:
                    applyParticleEmotion(31);  //shy
                    break;
                default:
                    applyParticleEmotion(15);  //kiss
                    break;
                }
                
                return;
            }
            
            switch (this.rand.nextInt(10))
            {
            case 1:
                applyParticleEmotion(33);  //:p
                break;
            case 2:
                applyParticleEmotion(17);  //gg
                break;
            case 3:
                applyParticleEmotion(19);  //lick
                break;
            case 4:
                applyParticleEmotion(9);  //hungry
                break;
            case 5:
                applyParticleEmotion(1);  //love
                break;
            case 6:
                applyParticleEmotion(15);  //kiss
                break;
            case 7:
                applyParticleEmotion(16);  //haha
                break;
            case 8:
                applyParticleEmotion(14);  //+_+
                break;
            default:
                applyParticleEmotion(7);  //note
                break;
            }
            break;
        case 2:   //normal
            if (this.getStateFlag(ID.F.IsMarried) && this.rand.nextInt(2) == 0)
            {
                switch (this.rand.nextInt(3))
                {
                case 1:
                    applyParticleEmotion(1);  //love
                    break;
                default:
                    applyParticleEmotion(15);  //kiss
                    break;
                }
                
                return;
            }
            
            switch (this.rand.nextInt(8))
            {
            case 1:
                applyParticleEmotion(11);  //find
                break;
            case 2:
                applyParticleEmotion(3);  //?
                break;
            case 3:
                applyParticleEmotion(13);  //nod
                break;
            case 4:
                applyParticleEmotion(9);  //hungry
                break;
            case 5:
                applyParticleEmotion(18);  //sigh
                break;
            case 7:
                applyParticleEmotion(16);  //haha
                break;
            default:
                applyParticleEmotion(29);  //blink
                break;
            }
            break;
        case 3:   //tired
        default:  //exhausted
            switch (this.rand.nextInt(8))
            {
            case 1:
                applyParticleEmotion(0);  //drop
                break;
            case 2:
                applyParticleEmotion(2);  //panic
                break;
            case 3:
                applyParticleEmotion(3);  //?
                break;
            case 4:
                applyParticleEmotion(8);  //cry
                break;
            case 5:
                applyParticleEmotion(10);  //dizzy
                break;
            case 6:
                applyParticleEmotion(20);  //orz
                break;
            default:
                applyParticleEmotion(32);  //hmm
                break;
            }
            break;
        }//end morale level switch
      }
      
      /** command emotes */
      public void reactionCommand()
      {
          //show emotes by morale level
        switch (EntityHelper.getMoraleLevel(this.getMorale()))
        {
        case 0:   //excited
        case 1:   //happy
        case 2:   //normal
            switch (this.rand.nextInt(7))
            {
            case 1:
                applyParticleEmotion(21);  //o
                break;
            case 2:
                applyParticleEmotion(4);  //!
                break;
            case 3:
                applyParticleEmotion(14);  //+_+
                break;
            case 4:
                applyParticleEmotion(11);  //find
                break;
            default:
                applyParticleEmotion(13);  //nod
                break;
            }
            break;
        case 3:   //tired
        default:  //exhausted
            switch (this.rand.nextInt(8))
            {
            case 1:
                applyParticleEmotion(0);  //drop
            case 2:
                applyParticleEmotion(33);  //:p
                break;
            case 3:
                applyParticleEmotion(3);  //?
                break;
            case 5:
                applyParticleEmotion(10);  //dizzy
                break;
            case 6:
                applyParticleEmotion(13);  //nod
                break;
            default:
                applyParticleEmotion(32);  //hmm
                break;
            }
            break;
        }//end morale level switch
      }
      
      /** shock emotes */
      public void reactionShock()
      {
        switch (this.rand.nextInt(8))
        {
        case 1:
            applyParticleEmotion(0);  //drop
            break;
        case 2:
            applyParticleEmotion(8);  //cry
            break;
        case 3:
            applyParticleEmotion(4);  //!
            break;
        default:
            applyParticleEmotion(12);  //omg
            break;
        }
      }
      
      /** emotes method
       * 
       *  type:
       *  0: caress head (owner)
       *  1: caress head (other)
       *  2: damaged
       *  3: attack
       *  4: idle
       *  5: command
       *  6: shock
       */
      public void applyEmotesReaction(int type)
      {
          Random ran = new Random();
          
          switch (type)
          {
          case 1:        //caress head (no fuel / not owner)
              if (ran.nextInt(9) == 0 && this.getEmotesTick() <= 0)
              {
                this.setEmotesTick(60);
                reactionStranger();
            }
              break;
          case 2:        //damaged
              if (this.getEmotesTick() <= 10)
              {
                this.setEmotesTick(40);
                reactionDamaged();
            }
              break;
          case 3:        //attack
              if (ran.nextInt(6) == 0 && this.getEmotesTick() <= 0)
              {
                this.setEmotesTick(60);
                reactionAttack();
            }
              break;
          case 4:        //idle
              if (ran.nextInt(3) == 0 && this.getEmotesTick() <= 0)
              {
                this.setEmotesTick(20);
                reactionIdle();
            }
              break;
          case 5:
              if (ran.nextInt(3) == 0 && this.getEmotesTick() <= 0)
              {
                this.setEmotesTick(25);
                reactionCommand();
            }
              break;
          case 6:
            reactionShock();
              break;
          default: //caress head (owner)
              if (ran.nextInt(7) == 0 && this.getEmotesTick() <= 0)
              {
                this.setEmotesTick(50);
                reactionNormal();
            }
              break;
          }
      }
      
      /** spawn emotion particle */
      public void applyParticleEmotion(int type)
      {
          float h = isSitting() ? this.height * 0.4F : this.height * 0.45F;
          
          //server side emotes
          if (!this.world.isRemote)
          {
              TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
                CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 36, h, 0, type), point);
          }
          //client side emotes
          else
          {
              ParticleHelper.spawnAttackParticleAtEntity(this, h, 0, type, (byte)36);
          }
      }
}