package com.lulan.shincolle.intermod.forestry;

import com.lulan.shincolle.reference.Reference;

import forestry.api.genetics.ICheckPollinatable;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IIndividual;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/** flower provider for shinbees
 * 
 */
public class ShinFlowerProvider implements IFlowerProvider
{
	
	
	@Override
	public boolean isAcceptedPollinatable(World world, ICheckPollinatable pollinatable)
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
		return I18n.format("inter.shincolle:flower");
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual individual, BlockPos pos, ItemStack[] products)
	{
		return products;
	}
	
	
}