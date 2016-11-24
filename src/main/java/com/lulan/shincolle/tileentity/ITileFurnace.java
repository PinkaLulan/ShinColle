package com.lulan.shincolle.tileentity;

public interface ITileFurnace
{

	/** 本次處理已經消耗掉的燃料值 */
	public int getPowerConsumed();
	public void setPowerConsumed(int par1);
	
	/** 本次處理要達到的目標燃料值 */
	public int getPowerGoal();
	public void setPowerGoal(int par1);
	
	/** 剩下的燃料值 */
	public int getPowerRemained();
	public void setPowerRemained(int par1);
	
	/** 燃料值上限 */
	public int getPowerMax();
	public void setPowerMax(int par1);
	
	
}
