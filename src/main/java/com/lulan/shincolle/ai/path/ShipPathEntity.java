package com.lulan.shincolle.ai.path;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Vec3;

/**SHIP PATH ENTITY
 * for ship path navigator
 */
public class ShipPathEntity {
    /** The actual points in the path */
    private final ShipPathPoint[] points;
    /** PathEntity Array Index the Entity is currently targeting */
    private int currentPathIndex;
    /** The total length of the path */
    private int pathLength;

    public ShipPathEntity(ShipPathPoint[] pathpoint) {
        this.points = pathpoint;
        this.pathLength = pathpoint.length;
    }

    /**
     * Directs this path to the next point in its array
     */
    public void incrementPathIndex() {
        ++this.currentPathIndex;
    }

    /**
     * Returns true if this path has reached the end
     */
    public boolean isFinished() {
        return this.currentPathIndex >= this.pathLength;
    }

    /**
     * returns the last PathPoint of the Array
     */
    public ShipPathPoint getFinalPathPoint() {
        return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
    }

    /**
     * return the PathPoint located at the specified PathIndex, usually the current one
     */
    public ShipPathPoint getPathPointFromIndex(int i) {
        return this.points[i];
    }

    public int getCurrentPathLength() {
        return this.pathLength;
    }

    public void setCurrentPathLength(int i) {
        this.pathLength = i;
    }

    public int getCurrentPathIndex() {
    	return this.currentPathIndex;
    }

    public void setCurrentPathIndex(int i) {
        this.currentPathIndex = i;
    }

    /**
     * Gets the vector of the PathPoint associated with the given index.
     */
    public Vec3 getVectorFromIndex(Entity entity, int i) {
    	if(i >= points.length) i = points.length - 1;
    	
        double d0 = (double)this.points[i].xCoord + (double)((int)(entity.width + 1.0F)) * 0.5D;
        double d1 = (double)this.points[i].yCoord;
        double d2 = (double)this.points[i].zCoord + (double)((int)(entity.width + 1.0F)) * 0.5D;
        return Vec3.createVectorHelper(d0, d1, d2);
    }

    /**
     * returns the current PathEntity target node as Vec3D
     */
    public Vec3 getPosition(Entity entity) {
        return this.getVectorFromIndex(entity, this.currentPathIndex);
    }

    /**
     * Returns true if the EntityPath are the same. Non instance related equals.
     */
    public boolean isSamePath(ShipPathEntity path) {
        if(path == null) {	//沒path, 無法判斷, 返回false
            return false;
        }
        else if(path.points.length != this.points.length) {	//長度不同 = 路徑不同
            return false;
        }
        else {
            for(int i = 0; i < this.points.length; ++i) {	//比較全部點是否相同
                if (this.points[i].xCoord != path.points[i].xCoord || this.points[i].yCoord != path.points[i].yCoord || this.points[i].zCoord != path.points[i].zCoord) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns true if the final PathPoint in the PathEntity is equal to Vec3D coords.
     */
    public boolean isDestinationSame(Vec3 vec) {
    	ShipPathPoint pathpoint = this.getFinalPathPoint();
        return pathpoint == null ? false : pathpoint.xCoord == (int)vec.xCoord && pathpoint.zCoord == (int)vec.zCoord;
    }
}
