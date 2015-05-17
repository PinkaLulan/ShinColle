package com.lulan.shincolle.entity;

import net.minecraft.entity.EntityLivingBase;

/**for mount type entity
 *
 */
public interface IShipMount extends IShipFloating, IShipEmotion, IShipAttack {
	
	/**get rider*/
	abstract public EntityLivingBase getRiddenByEntity();

}


