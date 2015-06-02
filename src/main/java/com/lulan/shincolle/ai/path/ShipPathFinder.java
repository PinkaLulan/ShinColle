package com.lulan.shincolle.ai.path;

import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;

/**SHIP PATH FINDER
 * for ship path navigator
 * 建立空中 or 水中path, 無視重力跟浮力
 */
public class ShipPathFinder {
    /** Used to find obstacles */
    private IBlockAccess worldMap;
    /** The path being generated */
    private ShipPath path = new ShipPath();
    /** The points in the path */
    private IntHashMap pointMap = new IntHashMap();
    /** Selection of path points to add to the path */
    private ShipPathPoint[] pathOptions = new ShipPathPoint[32];
    /** is air path, for non-airplane entity */
    private boolean isPathingInAir;
    /** is airplane */
    private boolean canEntityFly;


    public ShipPathFinder(IBlockAccess block, boolean canFly) {
        this.worldMap = block;
        this.isPathingInAir = false;
        this.canEntityFly = canFly;
    }

    /**
     * Creates a path from one entity to another within a minimum distance
     */
    public ShipPathEntity createEntityPathTo(Entity fromEnt, Entity toEnt, float range) {
        return this.createEntityPathTo(fromEnt, toEnt.posX, toEnt.boundingBox.minY, toEnt.posZ, range);
    }

    /**
     * Creates a path from an entity to a specified location within a minimum distance
     */
    public ShipPathEntity createEntityPathTo(Entity entity, int x, int y, int z, float range) {
        return this.createEntityPathTo(entity, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), range);
    }

    /**
     * Internal implementation of creating a path from an entity to a point
     */
    private ShipPathEntity createEntityPathTo(Entity entity, double x, double y, double z, float range) {
        this.path.clearPath();
        this.pointMap.clearMap();
        int i;

    	i = MathHelper.floor_double(entity.boundingBox.minY + 0.5D);

        //將起點終點加入point map
        ShipPathPoint startpp = this.openPoint(MathHelper.floor_double(entity.boundingBox.minX), i, MathHelper.floor_double(entity.boundingBox.minZ));
        ShipPathPoint endpp = this.openPoint(MathHelper.floor_double(x - (double)(entity.width / 2.0F)), MathHelper.floor_double(y), MathHelper.floor_double(z - (double)(entity.width / 2.0F)));
        //目標的長寬高+1建立為一個path point, 用於判定路徑寬高是否會卡住該entity
        ShipPathPoint entitySize = new ShipPathPoint(MathHelper.floor_float(entity.width + 1.0F), MathHelper.floor_float(entity.height + 1.0F), MathHelper.floor_float(entity.width + 1.0F));
        //計算出起點終點之間所有點
        ShipPathEntity pathentity = this.addToPath(entity, startpp, endpp, entitySize, range);
        
        return pathentity;
    }

    /**
     * Adds a path from start to end and returns the whole path (args: unused, start, end, unused, maxDistance)
     */
    private ShipPathEntity addToPath(Entity entity, ShipPathPoint startpp, ShipPathPoint endpp, ShipPathPoint entitySize, float range) {
        startpp.totalPathDistance = 0.0F;
        startpp.distanceToNext = startpp.distanceToSquared(endpp);
        startpp.distanceToTarget = startpp.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(startpp);
        ShipPathPoint ppTemp = startpp;
        int findCount = 0;

        /**path建立方法
         * 從起點開始, 
         */
        while(!this.path.isPathEmpty()) {
        	//從path中取出一點
        	ShipPathPoint ppDequeue = this.path.dequeue();
        	findCount++;
        	//若取出點=終點, 則完成path
            if(ppDequeue.equals(endpp)) {						
            	//將所有point做成path entity
//            	LogHelper.info("DEBUG : path navi: find count (pathing done) "+findCount);
                return this.createEntityPath(startpp, endpp);
            }
            
            //若while跑太多次, 強制中止
            if(findCount > 600) {
            	break;
            }
            
            //若取出點的終點距離較temp點小 (更接近終點)
            if(ppDequeue.distanceToSquared(endpp) < ppTemp.distanceToSquared(endpp)) {
                ppTemp = ppDequeue;	//將temp點設為取出點
            }
            
            //將取出點標記為起點, 判定此點往目標的可走方向有哪些
            ppDequeue.isFirst = true;
            int i = this.findPathOptions(entity, ppDequeue, entitySize, endpp, range); //取得可走方向
            //將所有可走方向都嘗試走看看
            for(int j = 0; j < i; ++j) {
            	ShipPathPoint ppTemp2 = this.pathOptions[j];
                float f1 = ppDequeue.totalPathDistance + ppDequeue.distanceToSquared(ppTemp2);
                //若該點還沒加入過path, 或該點已加入到path中但該點之前算出來的path長度較長, 則需要再更新一次path長度
                if(!ppTemp2.isAssigned() || f1 < ppTemp2.totalPathDistance) {
                	//將該點正式加入到path中, 設定其前後點跟path長度值
                    ppTemp2.previous = ppDequeue;		//前一點設為取出點
                    ppTemp2.totalPathDistance = f1;		//存下其path長度值
                    ppTemp2.distanceToNext = ppTemp2.distanceToSquared(endpp);	//下一點設為終點
                    //若該點本來就在path中, 則更新其path長度值
                    if(ppTemp2.isAssigned()) {
                        this.path.changeDistance(ppTemp2, ppTemp2.totalPathDistance + ppTemp2.distanceToNext);
                    }
                    else {	//不在path中, 正式加入path
                        ppTemp2.distanceToTarget = ppTemp2.totalPathDistance + ppTemp2.distanceToNext;
                        this.path.addPoint(ppTemp2);
                    }
                }
            }
        }
        
        if(ppTemp == startpp) {	//若完全找不到點可以加入, 則回傳null, 表示找不到path
//        if(this.path.getCount() <= 0) {	//若完全找不到點可以加入, 則回傳null, 表示找不到path
//        	LogHelper.info("DEBUG : path navi: no path");
            return null;
        }
        else {					//回傳path entity
        	//將所有point做成path entity
        	LogHelper.info("DEBUG : path navi: find count (pathing fail, cannot reach, find count = "+findCount+" times) ");
            return this.createEntityPath(startpp, ppTemp);
        }
    }

    /**判定此點到終點的path有幾個方向可走
     * populates pathOptions with available points and returns the number of options found (args: unused1, currentPoint,
     * unused2, targetPoint, maxDistance)
     */
    private int findPathOptions(Entity entity, ShipPathPoint currentpp, ShipPathPoint entitySize, ShipPathPoint targetpp, float range) {
        int i = 0;
        byte pathCase = 0;	

        //若頭頂沒卡到東西, 則pathCase = 1, 表示可往上1格找路徑
        int j = this.getVerticalOffset(entity, currentpp.xCoord, currentpp.yCoord + 1, currentpp.zCoord, entitySize);
        if(j == 1 || j == 3) {
            pathCase = 1;
        }
        //探查左右前後四個點的狀況: 下->左右前後->上
        ShipPathPoint pathpoint2 = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord - 1, currentpp.zCoord, entitySize, pathCase);
        ShipPathPoint pathpoint3 = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord, currentpp.zCoord + 1, entitySize, pathCase);
        ShipPathPoint pathpoint4 = this.getSafePoint(entity, currentpp.xCoord - 1, currentpp.yCoord, currentpp.zCoord, entitySize, pathCase);
        ShipPathPoint pathpoint5 = this.getSafePoint(entity, currentpp.xCoord + 1, currentpp.yCoord, currentpp.zCoord, entitySize, pathCase);
        ShipPathPoint pathpoint6 = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord, currentpp.zCoord - 1, entitySize, pathCase);
        ShipPathPoint pathpoint7 = null;
        //上面為液體方塊才允許往上找路徑, 若上面為空氣, 則只有可以飛的才往上找路徑
        if(j == 3 || (j == 1 && this.canEntityFly)) {
        	pathpoint7 = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord + 1, currentpp.zCoord, entitySize, pathCase);
        }       
        
        //若該點為安全點, 且不是起始點, 且在尋路最大範圍內
        if(pathpoint2 != null && !pathpoint2.isFirst && pathpoint2.distanceTo(targetpp) < range) {
//        	LogHelper.info("DEBUG : path navi: down path find: "+pathpoint2.yCoord);
        	this.pathOptions[i++] = pathpoint2;	//加入到可選選項中
        }
        if(pathpoint3 != null && !pathpoint3.isFirst && pathpoint3.distanceTo(targetpp) < range) {
//        	LogHelper.info("DEBUG : path navi: horz path A find: "+pathpoint3.yCoord);
        	this.pathOptions[i++] = pathpoint3;	//加入到可選選項中
        }
        if(pathpoint4 != null && !pathpoint4.isFirst && pathpoint4.distanceTo(targetpp) < range) {
            this.pathOptions[i++] = pathpoint4;
        }
        if(pathpoint5 != null && !pathpoint5.isFirst && pathpoint5.distanceTo(targetpp) < range) {
            this.pathOptions[i++] = pathpoint5;
        }
        if(pathpoint6 != null && !pathpoint6.isFirst && pathpoint6.distanceTo(targetpp) < range) {
            this.pathOptions[i++] = pathpoint6;
        }
        if(pathpoint7 != null && !pathpoint7.isFirst && pathpoint7.distanceTo(targetpp) < range) {
            this.pathOptions[i++] = pathpoint7;
        }

        return i;
    }

    /**找指定點(x,y,z)是否可安全移動過去, 包括該點下面是否可安全站立 (change: 修改水跟岩漿也算可站立方塊)
     * Returns a point that the entity can safely move to
     * pathOption:  0:blocked  1:clear
     */
    private ShipPathPoint getSafePoint(Entity entity, int x, int y, int z, ShipPathPoint entitySize, int pathOption) {
    	ShipPathPoint pathpoint1 = null;
        int pathCase = this.getVerticalOffset(entity, x, y, z, entitySize);	//取得該點路況
        
        if(pathCase == 2 || pathCase == 3) {//若該點路況為: open trapdoor or 液體, 則接受為safe point
            return this.openPoint(x, y, z);	//加入該點到path
        }
        else {				//其他路況
        	//若路況為clear, 則把該點加入到path
            if(pathCase == 1) {
                pathpoint1 = this.openPoint(x, y, z);
            }
            //若路況非clear而是實體方塊, 且允許往上找路徑, 則往上找
            if(pathpoint1 == null && pathOption > 0 && pathCase != -3 && pathCase != -4 && 
               this.getVerticalOffset(entity, x, y + pathOption, z, entitySize) == 1) {
                pathpoint1 = this.openPoint(x, y + pathOption, z);	//把往上一點加入到path
                y += pathOption;
            }
            //若有找到可加入path的點, 則往下找該點底下方塊是否可安全站立(只往下找X格, X依照entity自己的getMaxSafePointTries決定)
            if(pathpoint1 != null) {
            	//若可以飛, 則直接回傳pathpoint
            	if(this.canEntityFly) {
            		return pathpoint1;
            	}
            	
            	//若不能飛, 則往下找安全落地點
                int j1 = 0;
                int downCase = 0;
                
                while(y > 0) {
                    downCase = this.getVerticalOffset(entity, x, y - 1, z, entitySize);
                    //若底下全都為液體(本體在空中, 因此會掉到液體方塊中), 則path點高度改為在液體中
                    if(downCase == 3) {
                    	pathpoint1 = this.openPoint(x, y - 1, z);
                        break;
                    }
                    
                    //若無法往下, 表示已經落地, 則以此點作為path點
                    if(downCase != 1) {
                        break;
                    }
                    
                    //若嘗試超過特定次數, 則判定沒有安全落地, 傳回null
                    if(j1++ >= 32) {
                        return null;
                    }
                    
                    //往下找落地點, 將落地點加入到path
                    --y;
                    if(y > 0) {
                        pathpoint1 = this.openPoint(x, y, z);
                    }
                }
            }
            
//            if(pathCase == -3) {
//            	LogHelper.info("DEBUg : find fence "+x+" "+y+" "+z+" "+pathpoint1);
//            }
            
            return pathpoint1;
        }
    }

    /**
     * Returns a mapped point or creates and adds one
     */
    private final ShipPathPoint openPoint(int x, int y, int z) {
        int l = ShipPathPoint.makeHash(x, y, z);	//座標值算hash
        ShipPathPoint pathpoint = (ShipPathPoint)this.pointMap.lookup(l);	//用hash值找路徑中是否有該點

        //path中找不到該點, 則建立之
        if(pathpoint == null) {
            pathpoint = new ShipPathPoint(x, y, z);
            this.pointMap.addKey(l, pathpoint);
        }

        return pathpoint;
    }

    /**NEW:
     * 3:  liquid (water, lava, forge liquid)
     * 2:  open trapdoor
     * 1:  clear(air)
     * 0:  solid block
     * -3: fence or tracks
     * -4: closed trap door
     * 
     * ORIGIN:
     * Checks if an entity collides with blocks at a position. Returns 1 if clear, 0 for colliding with any solid block,
     * -1 for water(if avoiding water) but otherwise clear, -2 for lava, -3 for fence, -4 for closed trapdoor, 2 if
     * otherwise clear except for open trapdoor or water(if not avoiding)
     */
    public int getVerticalOffset(Entity entity, int x, int y, int z, ShipPathPoint entitySize) {
//    	return func_82565_a(entity, x, y, z, point, this.isPathingInWater, this.isMovementBlockAllowed, this.isWoddenDoorAllowed);
        return func_82565_a(entity, x, y, z, entitySize, this.isPathingInAir);
    }

    public static int func_82565_a(Entity entity, int x, int y, int z, ShipPathPoint entitySize, boolean inAir) {
        boolean pathToDoor = false;		//特定條件可通過的flag: 陷阱門
        boolean pathInLiquid = true;	//是否是站在液體中的path (即高度y全為液體方塊)
        
        //判定該point加上entity的長寬高後, 是否會碰撞到其他方塊
        for(int l = x; l < x + entitySize.xCoord; ++l) {
            for(int i1 = y; i1 < y + entitySize.yCoord; ++i1) {
                for(int j1 = z; j1 < z + entitySize.zCoord; ++j1) {
                	Block block = entity.worldObj.getBlock(l, i1, j1);

                    //若碰撞到非空氣, 水, 岩漿方塊
                    if(block.getMaterial() != Material.air) {
                    	//檢查高度y是否全為液體
                        if(pathInLiquid && i1 == y && !EntityHelper.checkBlockIsLiquid(block)) {
                        	pathInLiquid = false;
                        }
                        
//                    	//碰到的是陷阱門
//                        if(block == Blocks.trapdoor) {
//                        	pathToDoor = true;	//此為特定情況才能通過
//                        }

                        int k1 = block.getRenderType();
                        
                        //若碰到不能穿過的方塊: 關閉的門, 陷阱門, 柵欄門
                        if(!block.getBlocksMovement(entity.worldObj, l, i1, j1)) {
                        	//若在柵欄, 牆壁, 柵欄門方塊中, 回傳-3
                            if(k1 == 11 || block == Blocks.fence_gate || k1 == 32) {
                                return -3;
                            }
                            //若是關閉的陷阱門, 回傳-4
                            if(block == Blocks.trapdoor) {
                                return -4;
                            }
                            //若非流體方塊, 則都判定為0
                            if(!EntityHelper.checkBlockIsLiquid(block)) {
                                return 0;
                            }
                        }
                    }
                    else {	//若為air方塊
                    	//檢查高度y是否為液體
                        if(pathInLiquid && i1 == y) {
                        	pathInLiquid = false;
                        }
                    }
                }
            }
        }

        return pathInLiquid ? 3 : pathToDoor ? 2 : 1;
    }

    /**
     * Returns a new PathEntity for a given start and end point
     */
    private ShipPathEntity createEntityPath(ShipPathPoint startpp, ShipPathPoint endpp) {
        int i = 1;
        ShipPathPoint pathpoint2;

        //找出path總點數, 存為i
        for(pathpoint2 = endpp; pathpoint2.previous != null; pathpoint2 = pathpoint2.previous) {
            ++i;
        }

        ShipPathPoint[] pathtemp = new ShipPathPoint[i];
        pathpoint2 = endpp;
        --i;

        //將path所有點存到pathtemp
        for(pathtemp[i] = endpp; pathpoint2.previous != null; pathtemp[i] = pathpoint2) {
            pathpoint2 = pathpoint2.previous;
            --i;
        }

        return new ShipPathEntity(pathtemp);
    }
}
