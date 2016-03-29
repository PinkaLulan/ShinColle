package com.lulan.shincolle.ai.path;

/**SHIP PATH
 * 存放所有path point, 並且依照終點距離做排序
 * point在path array中的順序跟point在實際路徑上的順序(index)無關, 單純是距離大小排序
 */
public class ShipPath {
    /** Contains the points in this path */
    private ShipPathPoint[] pathPoints = new ShipPathPoint[1024];
    /** The number of points in this path */
    private int count;


    public ShipPathPoint[] getPathPoints() {
    	return pathPoints;
    }
    
    public int getCount() {
    	return count;
    }
    
    /**
     * Adds a point to the path
     */
    public ShipPathPoint addPoint(ShipPathPoint point) {
        if(point.index >= 0) {
            throw new IllegalStateException("OW KNOWS!");
        }
        else {
            if(this.count == this.pathPoints.length) {
            	ShipPathPoint[] apathpoint = new ShipPathPoint[this.count << 1];
                System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
                this.pathPoints = apathpoint;
            }

            this.pathPoints[this.count] = point;
            point.index = this.count;
            this.sortBack(this.count++);
            return point;
        }
    }

    /**
     * Clears the path
     */
    public void clearPath() {
        this.count = 0;
    }

    /**
     * Returns and removes the first point in the path
     */
    public ShipPathPoint dequeue() {
    	ShipPathPoint pathpoint = this.pathPoints[0];		//暫存下第一個點
        this.pathPoints[0] = this.pathPoints[--this.count];	//將最後一個點存到地一個點位置
        this.pathPoints[this.count] = null;					//最後一個點+1的位置設為null

        if(this.count > 0) {	//將第一個點往右做一次排序
            this.sortForward(0);
        }

        pathpoint.index = -1;	//取出的點id設為-1
        return pathpoint;
    }

    /**
     * Changes the provided point's distance to target
     */
    public void changeDistance(ShipPathPoint point, float dist)
    {
        float f1 = point.distanceToTarget;
        point.distanceToTarget = dist;

        if(dist < f1) {
            this.sortBack(point.index);
        }
        else {
            this.sortForward(point.index);
        }
    }

    /**
     * Sorts a point to the left
     */
    private void sortBack(int id) {
        ShipPathPoint pathpoint = this.pathPoints[id];
        int j;

        //目前的點跟前面全部的點比較終點距離, 直到碰到終點距離更大者停止 (終點距離大 = 離目標遠的點)
        for(float f = pathpoint.distanceToTarget; id > 0; id = j) {
            j = id - 1 >> 1;
            ShipPathPoint pathpoint1 = this.pathPoints[j];

            if(f >= pathpoint1.distanceToTarget) {
                break;
            }

            this.pathPoints[id] = pathpoint1;
            pathpoint1.index = id;
        }

        this.pathPoints[id] = pathpoint;
        pathpoint.index = id;
    }

    /**
     * Sorts a point to the right
     */
    private void sortForward(int id) {
    	ShipPathPoint pathpoint = this.pathPoints[id];
        float f = pathpoint.distanceToTarget;

        while(true) {
            int j = 1 + (id << 1);
            int k = j + 1;

            if(j >= this.count) {
                break;
            }

            ShipPathPoint pathpoint1 = this.pathPoints[j];
            float f1 = pathpoint1.distanceToTarget;
            ShipPathPoint pathpoint2;
            float f2;

            if(k >= this.count) {
                pathpoint2 = null;
                f2 = Float.POSITIVE_INFINITY;
            }
            else {
                pathpoint2 = this.pathPoints[k];
                f2 = pathpoint2.distanceToTarget;
            }

            if(f1 < f2) {
                if(f1 >= f) {
                    break;
                }

                this.pathPoints[id] = pathpoint1;
                pathpoint1.index = id;
                id = j;
            }
            else {
                if(f2 >= f) {
                    break;
                }

                this.pathPoints[id] = pathpoint2;
                pathpoint2.index = id;
                id = k;
            }
        }

        this.pathPoints[id] = pathpoint;
        pathpoint.index = id;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean isPathEmpty() {
        return this.count == 0;
    }
}
