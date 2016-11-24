package com.lulan.shincolle.ai.path;

/**SHIP PATH HEAP
 * 以heap結構儲存path, 依照終點距離做排序
 * point在path heap中的順序跟point在實際路徑上的順序(index)無關, 單純是距離大小排序
 */
public class ShipPathHeap
{
    /** Contains the points in this path, 起始大小為128點 */
    private ShipPathPoint[] pathPoints = new ShipPathPoint[128];
    /** The number of points in this path */
    private int count;


    public ShipPathPoint[] getPathPoints()
    {
    	return pathPoints;
    }
    
    public int getCount()
    {
    	return count;
    }
    
    /**
     * Adds a point to the path
     */
    public ShipPathPoint addPoint(ShipPathPoint point)
    {
        if (point.index >= 0)
        {
            throw new IllegalStateException("OW KNOWS!");
        }
        else
        {
        	//若path heap已滿, 則再擴張一倍大小
            if (this.count == this.pathPoints.length)
            {
            	ShipPathPoint[] apathpoint = new ShipPathPoint[this.count << 1];
                System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
                this.pathPoints = apathpoint;
            }

            //將新point增加到最尾端的leaf位置
            this.pathPoints[this.count] = point;
            point.index = this.count;
            //從leaf重新排序回root
            this.sortToRoot(this.count++);
            
            return point;
        }
    }

    /**
     * Clears the path
     */
    public void clearPath()
    {
        this.count = 0;
    }

    /**
     * Returns and removes the first point in the path
     * 取出heap第一個點, 即離目標最近的點
     * 
     * heap取出點方法: 1.移除root, 2.最後一個leaf搬移到root, 3.從root開始重新排序
     */
    public ShipPathPoint dequeue()
    {
    	//將最後一個leaf搬移到root
    	ShipPathPoint pathpoint = this.pathPoints[0];
        this.pathPoints[0] = this.pathPoints[--this.count];
        this.pathPoints[this.count] = null;
        
        //從root開始重新排序
        if (this.count > 0)
        {
            this.sortToLeaf(0);
        }

        pathpoint.index = -1;	//取出的點id設為-1
        
        return pathpoint;
    }

    /**
     * Changes the provided point's distance to target
     * 改變某個node的距離值, 要再重新排序一次, 使離目標距離最近的點排序到root
     */
    public void changeDistance(ShipPathPoint point, float dist)
    {
        float f1 = point.distanceToTarget;
        point.distanceToTarget = dist;

        //若新距離比舊距離小, 則往回排序到root
        if (dist < f1)
        {
            this.sortToRoot(point.index);
        }
        //若新距離比舊距離大, 則往後排序到leaf
        else
        {
            this.sortToLeaf(point.index);
        }
    }

    /**
     * 由目前點往回排序到root, 使root為離目標距離最短的點
     */
    private void sortToRoot(int id)
    {
        ShipPathPoint fromNode = this.pathPoints[id];
        int j;

        for (float f = fromNode.distanceToTarget; id > 0; id = j)
        {
            j = id - 1 >> 1;
            
            ShipPathPoint parentNode = this.pathPoints[j];

            if (f >= parentNode.distanceToTarget)
            {
                break;
            }

            this.pathPoints[id] = parentNode;
            parentNode.index = id;
        }

        this.pathPoints[id] = fromNode;
        fromNode.index = id;
    }

    /**
     * 由目前點往後排序到最末端leaf, 使leaf為離目標距離最遠的點
     */
    private void sortToLeaf(int id)
    {
    	ShipPathPoint fromNode = this.pathPoints[id];
        float fromDist = fromNode.distanceToTarget;

        while (true)
        {
            int left = 1 + (id << 1);	//left child
            int right = left + 1;		//right child
            
            //若child已超過heap大小, 則中止
            if (left >= this.count)
            {
                break;
            }

            //取得left node
            ShipPathPoint leftNode = this.pathPoints[left];
            ShipPathPoint rightNode;
            float leftDist = leftNode.distanceToTarget;
            float rightDist;

            //取得right node
            if (right >= this.count)
            {
            	rightNode = null;
            	rightDist = Float.POSITIVE_INFINITY;
            }
            else
            {
            	rightNode = this.pathPoints[right];
            	rightDist = rightNode.distanceToTarget;
            }

            //比較from, left, right三者的大小, 並將最小的調整到parent位置
            //left比right小
            if (leftDist < rightDist)
            {
            	//left比from大, 表示from為最小, sort完成
                if (leftDist >= fromDist)
                {
                    break;
                }

                //left比from小, 表示left為最小, left設為parent node
                this.pathPoints[id] = leftNode;
                leftNode.index = id;
                id = left;
            }
            //left比right大
            else
            {
            	//right比from大, 表示from為最小, sort完成
                if (rightDist >= fromDist)
                {
                    break;
                }

                //right比from小, 表示right為最小, right設為parent node
                this.pathPoints[id] = rightNode;
                rightNode.index = id;
                id = right;
            }
        }

        this.pathPoints[id] = fromNode;
        fromNode.index = id;
    }

    /**
     * Returns true if this path contains no points
     */
    public boolean isPathEmpty()
    {
        return this.count == 0;
    }
    
    
}
