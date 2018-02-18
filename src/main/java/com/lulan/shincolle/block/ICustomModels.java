package com.lulan.shincolle.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/** interface for textures/models init */
public interface ICustomModels
{
	
	/** init textures/models, CLIENT SIDE ONLY */
	@SideOnly(Side.CLIENT)
	public void initModel();

	
}
