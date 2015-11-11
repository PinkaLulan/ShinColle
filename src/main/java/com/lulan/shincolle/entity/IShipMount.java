package com.lulan.shincolle.entity;

import net.minecraft.entity.EntityLivingBase;

/**for mount type entity
 *
 */
public interface IShipMount extends IShipFloating {
	
	/**get rider*/
	public EntityLivingBase getRiddenByEntity();
	
//	/**get player eye height mod value, no use for now*/
//	public float getRidingPlayerEyeHeight();

}


