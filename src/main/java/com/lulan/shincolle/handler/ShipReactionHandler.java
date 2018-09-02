package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;

public class ShipReactionHandler extends ReactionHandler
{
    
    protected BasicEntityShip host;
    protected ShipStateHandler state;
    
    
    public ShipReactionHandler(BasicEntityShip host)
    {
        super(host);
        
        this.host = host;
        this.state = this.host.getStateHandler();
    }
    
    @Override
    public void reactEatItem()
    {
        if (this.state.getEmoteDelay() <= 0)
        {
            this.state.setEmoteDelay(40);
            super.reactEatItem();
        }
    }
    
    @Override
    public void reactItemNotFound()
    {
        if (this.state.getEmoteDelay() <= 0)
        {
            this.state.setEmoteDelay(20);
            super.reactItemNotFound();
        }
    }
    
    @Override
    public void reactAttackStart()
    {
        if (this.state.getEmoteDelay() <= 0 && this.rand.nextInt(4) == 0)
        {
            this.state.setEmoteDelay(60);
            
            //show emotes by morale level
            switch (EntityHelper.getMoraleLevel(this.host.getStateHandler().getMorale()))
            {
            case Excited:
                this.state.setShipEmotion(ID.Emotion.XD);
                  
                switch (this.rand.nextInt(4))
                {
                case 1:
                    applyParticleEmotion(17);  //sinister
                break;
                case 2:
                    applyParticleEmotion(14);  //+_+
                break;
                case 3:
                    applyParticleEmotion(16);  //ha
                break;
                default:
                    applyParticleEmotion(7);   //note
                break;
                }
            break;
            case Happy:
                this.state.setShipEmotion(ID.Emotion.XD);
                  
                switch (this.rand.nextInt(4))
                {
                case 1:
                    applyParticleEmotion(33);  //:p
                break;
                case 2:
                    applyParticleEmotion(19);  //lick
                break;
                default:
                    applyParticleEmotion(7);   //note
                break;
                }
            break;
            case Normal:   //normal
            case Tired:    //tired
            default:  //exhausted
                switch (this.rand.nextInt(6))
                {
                case 1:
                    applyParticleEmotion(30);  //pif
                break;
                case 2:
                    applyParticleEmotion(7);   //note
                break;
                case 3:
                    applyParticleEmotion(4);   //!
                break;
                default:
                    applyParticleEmotion(6);   //angry
                break;
                }
                break;
            }//end morale level switch
        }//end if delay = 0
    }
    
    
}