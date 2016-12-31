package com.lulan.shincolle.client.particle;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

/**
 * custom scale smoke
 */
public class ParticleSmoke extends ParticleSmokeNormal
{

    public ParticleSmoke(World world, double x, double y, double z, double speedX, double speedY, double speedZ, float scale)
    {
		super(world, x, y, z, speedX, speedY, speedZ, scale);
	}
	
	
}
