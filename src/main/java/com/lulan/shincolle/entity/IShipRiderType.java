package com.lulan.shincolle.entity;

/**
 * for 6D rider state
 */
public interface IShipRiderType
{

	/**
	 * 合體狀態: 0:none, 1:只有響, 2:只有電, 4:只有雷
	 * 可合計, ex: 3:有響跟雷, 5:有響跟雷, 6:有雷跟電, 7:有響雷電
	 */
	public int getRiderType();

	public void setRiderType(int type);
	
	
}
