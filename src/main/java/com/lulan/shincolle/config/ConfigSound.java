package com.lulan.shincolle.config;

import java.io.File;
import java.util.ArrayList;

public class ConfigSound extends BasicShipConfig
{

	
	public ConfigSound() {}
	
	public ConfigSound(File file) throws Exception
	{
		super(file);
	}

	@Override
	protected void parse(ArrayList<String> lines)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ArrayList<String> getDefaultContent()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
