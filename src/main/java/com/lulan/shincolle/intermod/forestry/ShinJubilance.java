package com.lulan.shincolle.intermod.forestry;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IJubilanceProvider;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

/** 蜜蜂生產特殊產物條件
 *  條件: 遮蔽 + 潮濕
 */
public class ShinJubilance implements IJubilanceProvider
{

	@Override
	public boolean isJubilant(IAlleleBeeSpecies species, IBeeGenome genome, IBeeHousing housing) {
		if (housing.getHumidity() == EnumHumidity.DAMP &&
			!housing.canBlockSeeTheSky())
		{
			return true;
		}
		
		return false;
	}

}
