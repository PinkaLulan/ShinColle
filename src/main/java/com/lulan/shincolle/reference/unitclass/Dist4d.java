package com.lulan.shincolle.reference.unitclass;

import net.minecraft.util.math.MathHelper;

/**
 * distance vector
 *
 * (vector x, vector y, vector z, distance d)
 */
public class Dist4d
{
	
    public static final Dist4d ZERO = new Dist4d(0D, 0D, 0D, 0D);
    public static final Dist4d ONE = new Dist4d(1D, 1D, 1D, 1.732051D);
    public double x;
    public double y;
    public double z;
    public double d;
    
    
    public Dist4d(double x, double y, double z, double d)
    {
        if (x == -0.0D) x = 0.0D;

        if (y == -0.0D) y = 0.0D;

        if (z == -0.0D) z = 0.0D;
        
        if (d == -0.0D) d = 0.0D;

        this.x = x;
        this.y = y;
        this.z = z;
        this.d = d;
    }
    
    /**
     * normalize by distance
     */
    public void normalize()
    {
		d = MathHelper.sqrt(x * x + y * y + z * z);
		
		//避免sqrt過小時, 算出的xyz過大
		if (d > 1.0E-4D)
        {
            x = x / d;
            y = y / d;
            z = z / d;
        }
		else
		{
			x = 0D;
			y = 0D;
			z = 0D;
			d = 0D;
		}
    }
    
    /**
     * scale without normalize
     */
    public void scale(double s)
    {
    	x *= s;
    	y *= s;
    	z *= s;
    	d *= s;
    }
    
    
}