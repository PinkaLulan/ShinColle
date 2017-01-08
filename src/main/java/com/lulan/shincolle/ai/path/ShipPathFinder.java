package com.lulan.shincolle.ai.path;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.reference.Enums.EnumPathType;
import com.lulan.shincolle.utility.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

/**SHIP PATH FINDER
 * for ship path navigator
 * 建立空中 or 水中path, 無視重力跟浮力
 */
public class ShipPathFinder
{
    /** Used to find obstacles */
    private IBlockAccess world;
    /** The path being generated */
    private ShipPathHeap path = new ShipPathHeap();
    /** The points in the path */
    private IntHashMap pointMap = new IntHashMap();
    /** is air path, for non-airplane entity */
    private boolean isPathingInAir;
    /** is airplane */
    private boolean canEntityFly;


    public ShipPathFinder(IBlockAccess world, boolean canFly)
    {
        this.world = world;
        this.isPathingInAir = false;
        this.canEntityFly = canFly;
    }

    /**
     * Creates a path from one entity to another within a minimum distance
     */
    @Nullable
    public ShipPath findPath(Entity fromEnt, Entity toEnt, float range)
    {
        return this.findPath(fromEnt, toEnt.posX, toEnt.getEntityBoundingBox().minY, toEnt.posZ, range);
    }

    /**
     * Creates a path from an entity to a specified location within a minimum distance
     */
    @Nullable
    public ShipPath findPath(Entity entity, int x, int y, int z, float range)
    {
        return this.findPath(entity, x + 0.5F, y + 0.5F, z + 0.5F, range);
    }

    /**
     * Internal implementation of creating a path from an entity to a point
     */
    @Nullable
    private ShipPath findPath(Entity entity, double x, double y, double z, float range)
    {
        this.path.clearPath();
        this.pointMap.clearMap();
        int i;

    	i = MathHelper.floor(entity.getEntityBoundingBox().minY + 0.5D);

        //將起點終點加入point map
    	//設定起點終點: 將entity位置(double)轉為整數位置(int)
        ShipPathPoint startpp = this.openPoint(MathHelper.floor(entity.getEntityBoundingBox().minX), i, MathHelper.floor(entity.getEntityBoundingBox().minZ));
        ShipPathPoint endpp = this.openPoint(MathHelper.floor(x - entity.width * 0.5F), MathHelper.floor(y), MathHelper.floor(z - entity.width * 0.5F));
        
        //目標的長寬高+1建立為一個path point, 用於判定路徑寬高是否會卡住該entity
        ShipPathPoint entitySize = new ShipPathPoint(MathHelper.floor(entity.width + 1F), MathHelper.floor(entity.height + 1F), MathHelper.floor(entity.width + 1F));
        
        //計算出起點終點之間所有點
        ShipPath pathentity = this.findPath(entity, startpp, endpp, entitySize, range);
        
        return pathentity;
    }

    /**
     * Adds a path from start to end and returns the whole path (args: entity, start, end, unused, maxDistance)
     *
     * 1.9.4:
     * 找點的計算方式改用曼哈頓距離來判定, 較適合於方塊世界中的路徑尋找
     * 使路徑選項可能性變多, 而不是堅持走最短的直線距離
     * 
     * ex: 在曼哈頓距離中, 右2+上2 = 上1右1上1右1, 兩種皆可使用
     *     如果用歐式距離, 右2+上2 > 上1右1上1右1, 只會選後者為路徑唯一選項
     * 
     */
    @Nullable
    private ShipPath findPath(Entity entity, ShipPathPoint startpp, ShipPathPoint endpp, ShipPathPoint entitySize, float range)
    {
        startpp.totalPathDistance = 0F;
        startpp.distanceToNext = startpp.distanceManhattan(endpp);
        startpp.distanceToTarget = startpp.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(startpp);
        ShipPathPoint ppTemp = startpp;
        int findCount = 0;

        /**path建立方法
         * 從起點開始, 找出可移動方向, 將判定距離目標最短的點加入path
         */
        while (!this.path.isPathEmpty())
        {
        	//從path中取出一點
        	ShipPathPoint ppDequeue = this.path.dequeue();
        	findCount++;
        	
        	//若取出點=終點, 則完成path
            if (ppDequeue.equals(endpp))
            {
            	//將所有point做成path entity
//            	LogHelper.info("DEBUG : path navi: find count (pathing done) "+findCount);
                return this.createEntityPath(startpp, endpp);
            }
            
            //若while跑太多次, 強制中止
            if (findCount > 450)
            {
            	break;
            }
            
            //若取出點的終點距離較temp點小 (更接近終點), 將取出點定為temp點
            if (ppDequeue.distanceToSquared(endpp) < ppTemp.distanceToSquared(endpp))
            {
                ppTemp = ppDequeue;
            }
            
            //將取出點標記為已拜訪過, 接著尋找該點周圍哪些點可走
            ppDequeue.visited = true;
            ShipPathPoint[] findOption = this.findPathOptions(entity, ppDequeue, entitySize, endpp, range); //取得可走方向
            
            //對所有可走方向, 計算曼哈頓距離, 取最短且沒走過的點加入path
            for (ShipPathPoint pp : findOption)
            {
            	//取出可走點, 計算曼哈頓距離
                float dist = ppDequeue.distanceManhattan(pp);
//                float dist = ppDequeue.distanceToSquared(pp);
                
                //設定可走點的距離成本
                pp.distanceFromOrigin = ppDequeue.distanceFromOrigin + dist;
                pp.cost = dist + pp.costMalus;
                float dist2 = ppDequeue.totalPathDistance + pp.cost;
                
                //若可走點距離尚未超出路徑上限距離, 且沒走過, 則可加入到path中
                if (pp.distanceFromOrigin < range && (!pp.isAssigned() || dist2 < pp.totalPathDistance))
                {
                	pp.previous = ppDequeue;
                	pp.totalPathDistance = dist2;
                	pp.distanceToNext = pp.distanceManhattan(endpp) + pp.costMalus;
//                	pp.distanceToNext = pp.distanceToSquared(endpp) + pp.costMalus;

                	//若這個點已經存在於path中, 則計算新距離並重新排序該點在path中的位置
                    if (pp.isAssigned())
                    {
                        this.path.changeDistance(pp, pp.totalPathDistance + pp.distanceToNext);
                    }
                    //若該點不在path中, 則加入到path
                    else
                    {
                    	pp.distanceToTarget = pp.totalPathDistance + pp.distanceToNext;
                        this.path.addPoint(pp);
                    }
                }
            }
        }//end while
        
        //若完全找不到點可以加入, 則回傳null, 表示找不到path
        if(ppTemp == startpp)
        {
            return null;
        }
        else
        {
        	//將所有point做成path entity
//        	LogHelper.info("DEBUG : find path: fail: find count = "+findCount+" times");
            return this.createEntityPath(startpp, ppTemp);
        }
    }

    /**判定此點到終點的path有幾個方向可走
     * populates pathOptions with available points and returns the number of options found (args: unused1, currentPoint,
     * unused2, targetPoint, maxDistance)
     * 
     * pathCase: 1: can go UP
     */
    private ShipPathPoint[] findPathOptions(Entity entity, ShipPathPoint currentpp, ShipPathPoint entitySize, ShipPathPoint targetpp, float range)
    {
        //若頭頂沒卡到東西, 則pathCase = 1, 表示可往上1格找路徑
        EnumPathType type = getPathType(entity, currentpp.xCoord, currentpp.yCoord + 1, currentpp.zCoord, entitySize);
        int pathYOffset = 0;
        
        if (type == EnumPathType.FLUID || type == EnumPathType.OPEN)
        {
        	pathYOffset = MathHelper.floor(Math.max(1F, entity.stepHeight));
        }
        
        //檢查東西南北上下 + 四個水平對角方向
        //array: Down Up Northwest Northeast Southwest Southeast North South East West
        ShipPathPoint[] pp = new ShipPathPoint[10];
        pp[0] = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord - 1, currentpp.zCoord, entitySize, pathYOffset);
        pp[1] = null;
        pp[2] = null;
        pp[3] = null;
        pp[4] = null;
        pp[5] = null;
        pp[6] = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord, currentpp.zCoord - 1, entitySize, pathYOffset);
        pp[7] = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord, currentpp.zCoord + 1, entitySize, pathYOffset);
        pp[8] = this.getSafePoint(entity, currentpp.xCoord + 1, currentpp.yCoord, currentpp.zCoord, entitySize, pathYOffset);
        pp[9] = this.getSafePoint(entity, currentpp.xCoord - 1, currentpp.yCoord, currentpp.zCoord, entitySize, pathYOffset);

        boolean fn = pp[6] == null || pp[6].costMalus != 0F;	//NYI: cost malus calc in getSafePoint
        boolean fs = pp[7] == null || pp[7].costMalus != 0F;
        boolean fe = pp[8] == null || pp[8].costMalus != 0F;
        boolean fw = pp[9] == null || pp[9].costMalus != 0F;
        
        //上面為液體方塊才允許往上找路徑, 若上面為空氣, 則只有可以飛的才往上找路徑
        if (pathYOffset > 0)
        {
        	pp[1] = this.getSafePoint(entity, currentpp.xCoord, currentpp.yCoord + 1, currentpp.zCoord, entitySize, pathYOffset);
        }
        
        if (fn && fw)  //northwest
        {
        	pp[2] = this.getSafePoint(entity, currentpp.xCoord - 1, currentpp.yCoord, currentpp.zCoord - 1, entitySize, pathYOffset);
        }
        
        if (fn && fe)  //northeast
        {
        	pp[3] = this.getSafePoint(entity, currentpp.xCoord + 1, currentpp.yCoord, currentpp.zCoord - 1, entitySize, pathYOffset);
        }
        
        if (fs && fw)  //southwest
        {
        	pp[4] = this.getSafePoint(entity, currentpp.xCoord - 1, currentpp.yCoord, currentpp.zCoord + 1, entitySize, pathYOffset);
        }
        
        if (fs && fe)  //southeast
        {
        	pp[5] = this.getSafePoint(entity, currentpp.xCoord + 1, currentpp.yCoord, currentpp.zCoord + 1, entitySize, pathYOffset);
        }
        
        //若該點為安全點 & 沒有拜訪過 & 在尋路最大範圍內, 則加入到可拜訪選項中
        List<ShipPathPoint> temp = new ArrayList<ShipPathPoint>();
        int len = 0;
        
        for (ShipPathPoint spp : pp)
        {
            if(spp != null && !spp.visited && spp.distanceTo(targetpp) < range)
            {
            	temp.add(spp);
            	len++;
            }
        }
        
        //list to array
        ShipPathPoint[] ret = temp.toArray(new ShipPathPoint[len]);
        
        return ret;
    }

    /**
     * 找指定點(x,y,z)是否可安全移動過去, 包括該點下面是否可安全站立 (change: 修改水跟岩漿也算可站立方塊)
     * Returns a point that the entity can safely move to.
     * 
     * pathYOffset: 原始值為entity的stepHeight, 用於往上尋找路徑, 0表示不可往上尋找路徑
     */
    private ShipPathPoint getSafePoint(Entity entity, int x, int y, int z, ShipPathPoint entitySize, int pathYOffset)
    {
    	ShipPathPoint pp = null;
    	EnumPathType pathCase = getPathType(entity, x, y, z, entitySize);	//取得該點路況
        
        //若該點路況為: 可開關的門 or 液體, 則接受為safe point
        if (pathCase == EnumPathType.FLUID || pathCase == EnumPathType.OPENABLE)
        {
            return openPoint(x, y, z);
        }
        //其他路況
        else
        {
        	//若路況為OPEN, 則先存到path map, 再繼續落腳點判斷
            if (pathCase == EnumPathType.OPEN)
            {
            	pp = openPoint(x, y, z);
            }
            
            //若路況是實體方塊, 且允許往上找路徑, 則往上找
            if (pp == null)
            {
            	//若pathYOffset > 1, 表示stepHeight足以跨過FENCE類方塊, 若只有1則不能找FENCE類方塊
            	if (pathYOffset > 1 || (pathYOffset > 0 && pathCase != EnumPathType.FENCE))
            	{
            		pp = getSafePoint(entity, x, y + 1, z, entitySize, pathYOffset - 1);
            		pathCase = getPathType(entity, x, y + 1, z, entitySize);
            	}
            }
            
            //若有找到可加入path的點, 則往下找該點底下方塊是否可安全站立(只往下找X格, X依照entity自己的getMaxSafePointTries決定)
            if (pp != null)
            {
            	//若可以飛, 則直接回傳pathpoint
            	if (this.canEntityFly)
            	{
            		return pp;
            	}
            	
            	//若不能飛, 則往下找安全落地點
                int j1 = 0;
                
                while (y > 0)
                {
                	EnumPathType downCase = getPathType(entity, x, y - 1, z, entitySize);
                	
                    //若底下全都為液體(本體在空中, 因此會掉到液體方塊中), 則path點高度改為在液體中
                    if (downCase == EnumPathType.FLUID)
                    {
                    	pp = this.openPoint(x, y - 1, z);
                        break;
                    }
                    
                    //若無法往下, 表示已經落地, 則以此點作為path點
                    if (downCase != EnumPathType.OPEN)
                    {
                        break;
                    }
                    
                    //若嘗試超過特定次數, 則判定沒有安全落地, 傳回null (此即entity可以自己跳懸崖的最大高度)
                    if (j1++ > 64)
                    {
                        return null;
                    }
                    
                    //往下找落地點, 將落地點加入到path
                    --y;
                    
                    if (y > 0)
                    {
                    	pp = this.openPoint(x, y, z);
                    }
                }//end fall while
            }
            
//            //TODO debug
//        	if (pathCase == EnumPathType.BLOCKED)
//        	{
//        		IBlockState block = entity.worldObj.getBlockState(new BlockPos(x, y, z));
//        		LogHelper.debug("AAAAAA "+x+" "+y+" "+z+" "+block.getBlock().getLocalizedName()+" "+pp);
//        	}
            
            return pp;
        }
    }

    /**
     * Returns a mapped point or creates and adds one
     */
    private final ShipPathPoint openPoint(int x, int y, int z)
    {
        int l = ShipPathPoint.makeHash(x, y, z);	//座標值算hash
        ShipPathPoint pathpoint = (ShipPathPoint) this.pointMap.lookup(l);	//用hash值找路徑中是否有該點

        //path中找不到該點, 則建立之
        if (pathpoint == null)
        {
            pathpoint = new ShipPathPoint(x, y, z);
            this.pointMap.addKey(l, pathpoint);
        }

        return pathpoint;
    }

    /**
     * 1.9.4:
     * changed return type to EnumPathType
     * EnumPathType = OPEN, FLUID, BLOCKED, OPENABLE...
     * 
     * 1.7.10:
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
     * 
     * entitySize:
     * xz為entity.width + 1F取floor
     * y為entity.height + 1F取floor
     * 
     * ex: entity size (0.8F, 1.7F) => (1, 2, 1)
     */
    public EnumPathType getPathType(Entity entity, int x, int y, int z, ShipPathPoint entitySize)
    {
        return getPathType(entity, x, y, z, entitySize, this.isPathingInAir);
    }

    public static EnumPathType getPathType(Entity entity, int x, int y, int z, ShipPathPoint entitySize, boolean inAir)
    {
        //用enum set來儲存該路徑點上所有存在的路況, 用於複雜的路況判斷
        EnumSet<EnumPathType> pathset = EnumSet.noneOf(EnumPathType.class);
        boolean pathInLiquid = false;	//是否是站在液體中的path
        int doorCount = 0;	//若門超過2個 (門是2格高) 所以doorCount > 4則視為BLOCKED
        
		//檢查原xyz位置是否為液體方塊
        if (BlockHelper.checkBlockIsLiquid(entity.world.getBlockState(new BlockPos(x, y, z))))
        {
        	pathInLiquid = true;
        }
        
        //判定該point加上entity的長寬高後, 是否會碰撞到其他方塊
        for (int x1 = x; x1 < x + entitySize.xCoord; ++x1)
        {
            for (int y1 = y + entitySize.yCoord - 1; y1 >= y; --y1)	//NOTE: 為從上而下抓方塊, 以便正確判定FENCE類
            {
                for (int z1 = z; z1 < z + entitySize.zCoord; ++z1)
                {
                	//get block
                	BlockPos pos = new BlockPos(x1, y1, z1);
                	IBlockState state = entity.world.getBlockState(pos);
                	Block block = state.getBlock();
                	Material mat = null;
                	EnumPathType type = EnumPathType.OPEN;
                	
                	if (state != null) mat = state.getMaterial();
                	
                	//若碰到非空氣方塊
                	if (mat != null && mat != Material.AIR)
                	{
                		//不能從頂端越過的方塊
                		if (block instanceof BlockFence ||
                			block instanceof BlockWall)
                		{
                			//若fence/wall跟entity相同高度, 則回傳為FENCE以便判定是否要跨過
                			if (y1 == y) return EnumPathType.FENCE;
                			//若fence/wall超過entity一格以上, 則判定被阻擋
                			return EnumPathType.BLOCKED;
                		}
                		
                		//其他可穿越的方塊 (特別列出block.isPassable = false但卻可以通過的例外)
                		if (block instanceof BlockRailBase)
                		{
                			type = EnumPathType.OPEN;
                		}
                		else if (block instanceof BlockDoor)
                		{
                			doorCount++;
                			//除了鐵門以外皆可自行開關
                			if (mat == Material.IRON || doorCount > 4) return EnumPathType.BLOCKED;
                			type = EnumPathType.OPENABLE;
                		}
                		//可自行開關的方塊
                		else if (block instanceof BlockFenceGate)
                		{
                			type = EnumPathType.OPENABLE;
                		}
                		else if (BlockHelper.checkBlockIsLiquid(state))
                		{
                			type = EnumPathType.FLUID;
                		}
                		//其他不能穿過的方塊
                		else
                		{
                            if (block instanceof BlockLilyPad ||
                            	!block.isPassable(entity.world, pos))
                            {
                                return EnumPathType.BLOCKED;
                            }
                		}
                	}
                	
                	//add path type
                	pathset.add(type);
                }//end for z
            }//end for y
        }//end for x
        
        //overall type checking
        return pathInLiquid ? EnumPathType.FLUID : EnumPathType.OPEN;
    }

    /**
     * Returns a new PathEntity for a given start and end point
     */
    private ShipPath createEntityPath(ShipPathPoint startpp, ShipPathPoint endpp)
    {
        int i = 1;
        ShipPathPoint pathpoint2;

        //找出path總點數, 存為i
        for (pathpoint2 = endpp; pathpoint2.previous != null; pathpoint2 = pathpoint2.previous)
        {
            ++i;
        }

        ShipPathPoint[] pathtemp = new ShipPathPoint[i];
        pathpoint2 = endpp;
        --i;

        //將path所有點存到pathtemp
        for (pathtemp[i] = endpp; pathpoint2.previous != null; pathtemp[i] = pathpoint2)
        {
            pathpoint2 = pathpoint2.previous;
            --i;
        }

        return new ShipPath(pathtemp);
    }
    
    
}
