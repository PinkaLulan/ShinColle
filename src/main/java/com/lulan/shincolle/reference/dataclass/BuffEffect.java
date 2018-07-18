package com.lulan.shincolle.reference.dataclass;

public class BuffEffect
{
	
	public int level;
	public int type;
	
	public BuffEffect()
	{
		level = 0;
		type = 0;
	}
	
	public BuffEffect(int type, int level)
	{
		this.level = level;
		this.type = type;
	}
	
	
}