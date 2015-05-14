package com.lulan.shincolle.ai.path;

import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

/**SHIP PATH POINT
 * for ship path navigator
 */
public class ShipPathPoint {
    /** The x coordinate of this point */
    public final int xCoord;
    /** The y coordinate of this point */
    public final int yCoord;
    /** The z coordinate of this point */
    public final int zCoord;
    /** A hash of the coordinates used to identify this point */
    private final int hash;
    /** The index of this point in its assigned path */
    public int index = -1;
    /** The distance along the path to this point */
    public float totalPathDistance;
    /** The linear distance to the next point */
    public float distanceToNext;
    /** The distance to the target */
    public float distanceToTarget;
    /** The point preceding this in its assigned path */
    public ShipPathPoint previous;
    /** Indicates this is the origin */
    public boolean isFirst;

    
    public ShipPathPoint(int x, int y, int z) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        this.hash = makeHash(x, y, z);
    }

    public static int makeHash(int x, int y, int z) {
        return y & 255 | (x & 32767) << 8 | (z & 32767) << 24 | (x < 0 ? Integer.MIN_VALUE : 0) | (z < 0 ? 32768 : 0);
    }

    /**
     * Returns the linear distance to another path point
     */
    public float distanceTo(ShipPathPoint point) {
        float f = (float)(point.xCoord - this.xCoord);
        float f1 = (float)(point.yCoord - this.yCoord);
        float f2 = (float)(point.zCoord - this.zCoord);
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    /**
     * Returns the squared distance to another path point
     */
    public float distanceToSquared(ShipPathPoint point) {
        float f = (float)(point.xCoord - this.xCoord);
        float f1 = (float)(point.yCoord - this.yCoord);
        float f2 = (float)(point.zCoord - this.zCoord);
        return f * f + f1 * f1 + f2 * f2;
    }

    public boolean equals(Object obj) {
        if(!(obj instanceof ShipPathPoint)) {
            return false;
        }
        else {
        	ShipPathPoint pathpoint = (ShipPathPoint)obj;
            return this.hash == pathpoint.hash && this.xCoord == pathpoint.xCoord && this.yCoord == pathpoint.yCoord && this.zCoord == pathpoint.zCoord;
        }
    }

    public int hashCode() {
        return this.hash;
    }

    /**
     * Returns true if this point has already been assigned to a path
     */
    public boolean isAssigned() {
        return this.index >= 0;
    }

    public String toString() {
        return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
    }
    
    
}
