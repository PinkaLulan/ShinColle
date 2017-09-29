package com.lulan.shincolle.reference.unitclass;

/**
 * distance vector
 *
 * (vector x, vector y, vector z, distance d)
 */
public class Dist4d
{
	
    public static final Dist4d ZERO = new Dist4d(0D, 0D, 0D, 0D);
    public static final Dist4d ONE = new Dist4d(1D, 1D, 1D, 1.732051D);
    public final double x;
    public final double y;
    public final double z;
    public final double distance;
    
    
    public Dist4d(double x, double y, double z, double distance)
    {
        if (x == -0.0D) x = 0.0D;

        if (y == -0.0D) y = 0.0D;

        if (z == -0.0D) z = 0.0D;
        
        if (distance == -0.0D) distance = 0.0D;

        this.x = x;
        this.y = y;
        this.z = z;
        this.distance = distance;
    }
    
    
}