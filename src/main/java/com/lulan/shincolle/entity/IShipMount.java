package com.lulan.shincolle.entity;

import net.minecraft.entity.EntityLivingBase;

/**for mount type entity
 *
 */
public interface IShipMount {
	
	/**get rider*/
	abstract public EntityLivingBase getRiddenByEntity();

}


