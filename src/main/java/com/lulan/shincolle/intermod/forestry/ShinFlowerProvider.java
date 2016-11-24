package com.lulan.shincolle.intermod.forestry;

import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.reference.Reference;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.IFlower;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;


/** flower provider for shinbees
 * 
 */
public class ShinFlowerProvider implements IFlowerProvider
{
	
	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable)
	{
		return false;
	}

	@Override
	public String getFlowerType()
	{
		return Reference.MOD_ID + ".flower";
	}

	@Override
	public String getDescription()
	{
		return StatCollector.translateToLocal("inter.shincolle:flower");
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual individual, int x, int y, int z, ItemStack[] products)
	{
		return products;
	}

	@Override
	public Set<IFlower> getFlowers()
	{
		return FlowerManager.flowerRegistry.getAcceptableFlowers(getFlowerType());
	}

	@Override
	public boolean growFlower(World world, IIndividual individual, int x, int y, int z)
	{
		return false;
	}

}
