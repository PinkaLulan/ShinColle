package com.lulan.shincolle.ai.path;

import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

/**SHIP PATH NAVIGATE
 * ship or airplane限定path ai, 該entity必須實作IShipNavigator
 * 無視重力或者浮力作用找出空中 or 水中路徑, 若entity在陸上移動則不需要更新此navigator
 * update move的部份不採用自然墜落跟jump, 而是直接加上一個motionY
 * 注意此path navigator使用時, 必須把ship floating關閉以免阻礙y軸移動
 */
public class ShipPathNavigate {
    private EntityLiving theEntity;
    /** The entity using ship path navigate */
    private IShipNavigator theEntity2;
    private World worldObj;
    /** The PathEntity being followed. */
    private ShipPathEntity currentPath;
    private double speed;
    /** The number of blocks (extra) +/- in each axis that get pulled out as cache for the pathfinder's search space */
    private IAttributeInstance pathSearchRange;
    /** Time, in number of ticks, following the current path */
    private int totalTicks;
    /** The time when the last position check was done (to detect successful movement) */
    private int ticksAtLastPos;
    /** Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck') */
    private Vec3 lastPosCheck = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
    /** entity is airplane flag*/
    private boolean canFly;
    

    public ShipPathNavigate(EntityLiving entity, World world) {
        this.theEntity = entity;
        this.theEntity2 = (IShipNavigator) entity;
        this.worldObj = world;
        this.pathSearchRange = entity.getEntityAttribute(SharedMonsterAttributes.followRange);
        this.canFly = false;
    }

    /**
     * Sets the speed
     */
    public void setSpeed(double par1) {
        this.speed = par1;
    }
    
    public void setCanFly(boolean par1) {
    	this.canFly = par1;
    }
    
    public boolean getCanFly() {
    	return this.canFly;
    }

    /**
     * Gets the maximum distance that the path finding will search in.
     */
    public float getPathSearchRange() {
        return (float)this.pathSearchRange.getAttributeValue();
    }
    
    /**
     * Try to find and set a path to XYZ. Returns true if successful.
     */
    public boolean tryMoveToXYZ(double x, double y, double z, double speed) {
        ShipPathEntity pathentity = this.getPathToXYZ((double)MathHelper.floor_double(x), (double)((int)y), (double)MathHelper.floor_double(z));
        return this.setPath(pathentity, speed);
    }

    /**
     * Returns the path to the given coordinates
     */
    public ShipPathEntity getPathToXYZ(double x, double y, double z) {
        return !this.canNavigate() ? null : this.getShipPathToXYZ(this.theEntity, MathHelper.floor_double(x), (int)y, MathHelper.floor_double(z), this.getPathSearchRange(), this.canFly);
    }
    
    public ShipPathEntity getShipPathToXYZ(Entity entity, int x, int y, int z, float range, boolean canFly) {
        this.worldObj.theProfiler.startSection("pathfind");
        //xyz1為原始位置  xyz2為左範圍 xyz3為右範圍
        //即將entity位置, 往六方向擴張range+8格, 在此範圍內計算路徑
        int x1 = MathHelper.floor_double(entity.posX);
        int y1 = MathHelper.floor_double(entity.posY);
        int z1 = MathHelper.floor_double(entity.posZ);
        int range1 = (int)(range + 8.0F);
        int x2 = x1 - range1;
        int y2 = y1 - range1;
        int z2 = z1 - range1;
        int x3 = x1 + range1;
        int y3 = y1 + range1;
        int z3 = z1 + range1;
        ChunkCache chunkcache = new ChunkCache(this.worldObj, x2, y2, z2, x3, y3, z3, 0);
        ShipPathEntity pathentity = (new ShipPathFinder(chunkcache, canFly)).createEntityPathTo(entity, x, y, z, range);
        this.worldObj.theProfiler.endSection();
        return pathentity;
    }

    /**
     * Returns the path to the given EntityLiving
     */
    public ShipPathEntity getPathToEntityLiving(Entity entity) {
//        return !this.canNavigate() ? null : this.worldObj.getPathEntityToEntity(this.theEntity, entity, this.getPathSearchRange(), this.canPassOpenWoodenDoors, this.canPassClosedWoodenDoors, this.avoidsWater, this.canSwim);
        return !this.canNavigate() ? null : this.getPathEntityToEntity(this.theEntity, entity, this.getPathSearchRange(), this.canFly);
    }
    
    public ShipPathEntity getPathEntityToEntity(Entity entity, Entity targetEntity, float range, boolean canFly) {
        this.worldObj.theProfiler.startSection("pathfind");
        //xyz1為原始位置  xyz2為左範圍 xyz3為右範圍
        //即將entity位置, 往六方向擴張range+8格, 在此範圍內計算路徑
        int x1 = MathHelper.floor_double(entity.posX);
        int y1 = MathHelper.floor_double(entity.posY + 1.0D);
        int z1 = MathHelper.floor_double(entity.posZ);
        int range1 = (int)(range + 16.0F);
        int x2 = x1 - range1;
        int y2 = y1 - range1;
        int z2 = z1 - range1;
        int x3 = x1 + range1;
        int y3 = y1 + range1;
        int z3 = z1 + range1;
        ChunkCache chunkcache = new ChunkCache(this.worldObj, x2, y2, z2, x3, y3, z3, 0);
        ShipPathEntity pathentity = (new ShipPathFinder(chunkcache, canFly)).createEntityPathTo(entity, targetEntity, range);
        this.worldObj.theProfiler.endSection();
        return pathentity;
    }

    /**
     * Try to find and set a path to EntityLiving. Returns true if successful.
     */
    public boolean tryMoveToEntityLiving(Entity entity, double speed) {
        ShipPathEntity pathentity = this.getPathToEntityLiving(entity);
        return pathentity != null ? this.setPath(pathentity, speed) : false;
    }

    /**
     * sets the active path data if path is 100% unique compared to old path, checks to adjust path for sun avoiding
     * ents and stores end coords
     */
    public boolean setPath(ShipPathEntity pathEntity, double speed) {
        //若路徑為null, 表示找不到路徑
    	if(pathEntity == null) {
            this.currentPath = null;
            return false;
        }
        else {
        	//比較新舊路徑是否相同, 不同時將舊路徑蓋掉
            if(!pathEntity.isSamePath(this.currentPath)) {
                this.currentPath = pathEntity;
            }
            //若路徑長度為0, 表示沒path
            if(this.currentPath.getCurrentPathLength() == 0) {
                return false;
            }
            else {	//成功設定path
                this.speed = speed;
                Vec3 vec3 = this.getEntityPosition();
                this.ticksAtLastPos = this.totalTicks;
                this.lastPosCheck.xCoord = vec3.xCoord;
                this.lastPosCheck.yCoord = vec3.yCoord;
                this.lastPosCheck.zCoord = vec3.zCoord;
                return true;
            }
        }
    }

    /**
     * gets the actively used PathEntity
     */
    public ShipPathEntity getPath() {
        return this.currentPath;
    }

    /** navigation tick */
    public void onUpdateNavigation() {
        ++this.totalTicks;
        //若有path
        if(!this.noPath()) {
        	//若可以執行移動, 則跑pathFollow方法
            if(this.canNavigate()) {
                this.pathFollow();
            }

            //若pathFollow沒把path清除, 表示還可以繼續移動
            if(!this.noPath()) {
            	//取得下一個目標點
                Vec3 vec3 = this.currentPath.getPosition(this.theEntity);
                //若還有下一個點要移動, 則設定移動量使move helper可以實際移動entity
                if(vec3 != null) {
                    this.theEntity2.getShipMoveHelper().setMoveTo(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed);
                }
            }
        }
    }

    /** 判定entity是否卡住(超過100 tick仍在原地) or entity是否可以省略一些路徑點 
     *  以y高度判定是否有些點可以省略 (不判定水平距離)
     */
    private void pathFollow() {
        Vec3 entityPos = this.getEntityPosition();
        int pptemp = this.currentPath.getCurrentPathLength();

        //掃描還沒走的路徑點, 找出是否有y高度不同的點
        for(int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j) {
            if(this.currentPath.getPathPointFromIndex(j).yCoord != (int)entityPos.yCoord) {
            	pptemp = j;
                break;
            }
        }

        float widthSq = this.theEntity.width * this.theEntity.width;
        int k;

        //掃描目前的點到y高度不同的點, 若距離不到entity大小, 則直接判定entity已經到達該點, 將目標點標記為該點
        //用於縮短path長度?
        for(k = this.currentPath.getCurrentPathIndex(); k < pptemp; ++k) {
            if(entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, k)) < (double)widthSq) {
                this.currentPath.setCurrentPathIndex(k + 1);
            }
        }

        k = MathHelper.ceiling_float_int(this.theEntity.width);
        int l = (int)this.theEntity.height + 1;
        int i1 = k;

        //從y高度不合的點往回掃描, 找是否有點能從目前點直線走過去, 有的話將該點設為目標點
        for(int j1 = pptemp - 1; j1 >= this.currentPath.getCurrentPathIndex(); --j1) {
            if(this.isDirectPathBetweenPoints(entityPos, this.currentPath.getVectorFromIndex(this.theEntity, j1), k, l, i1)) {
                this.currentPath.setCurrentPathIndex(j1);
                break;
            }
        }

        //若距離上一次成功移動的時間超過100 ticks
        if(this.totalTicks - this.ticksAtLastPos > 100) {
        	//若距離上一次成功移動的點不到1.5格, 則表示某種原因造成幾乎沒移動, 清除該path
            if(entityPos.squareDistanceTo(this.lastPosCheck) < 2.25D) {
                this.clearPathEntity();
            }
            //更新成功移動的紀錄
            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck.xCoord = entityPos.xCoord;
            this.lastPosCheck.yCoord = entityPos.yCoord;
            this.lastPosCheck.zCoord = entityPos.zCoord;
        }
    }

    /**
     * If null path or reached the end
     */
    public boolean noPath() {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    /**
     * sets active PathEntity to null
     */
    public void clearPathEntity() {
        this.currentPath = null;
    }

    /** 
     * 將entity位置資訊以vec3表示
     */
    private Vec3 getEntityPosition() {
        return Vec3.createVectorHelper(this.theEntity.posX, (double)this.getPathableYPos(), this.theEntity.posZ);
    }

    /**CHANGE:
     * 若能飛, 則以目前y為起點
     * 若不能飛, 則往下找到第一個非空氣的方塊為起點 (水面 or 實體方塊)
     * 
     * ORIGIN:
     * 若能游泳, 則找出脫離水面的高度y作為path起點
     * 若不能游泳, 則以目前y作為path起點
     * Gets the safe pathing Y position for the entity depending on if it can path swim or not
     */
    private int getPathableYPos() {
    	if(this.canFly) {
    		//可以飛, 直接以目前y為起點
            return (int)(this.theEntity.boundingBox.minY + 0.5D);
        }
        else {
        	 int i = (int)this.theEntity.boundingBox.minY;
             Block block = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ));
             int j = 0;
             //往下找出第一個非air的方塊
             do {
                 if(block != Blocks.air && block != null) {
                     return i;
                 }

                 ++i;
                 block = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ));
                 ++j;
             }
             while (j <= 16);	//最多往下找16格就停止
             //找超過16格都沒底, 則直接回傳目前y
             return (int)this.theEntity.boundingBox.minY;
        }
    }

    /**
     * If on ground or swimming and can swim
     */
    private boolean canNavigate() {
        return !theEntity.isRiding() && EntityHelper.checkEntityIsFree(theEntity);
    }

    /**
     * Returns true if the entity is in water or lava, false otherwise
     */
    private boolean isInLiquid() {
        return EntityHelper.checkEntityIsInLiquid(theEntity);
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    private void removeSunnyPath() {
        if(!this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.theEntity.posX), (int)(this.theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(this.theEntity.posZ))) {
            for(int i = 0; i < this.currentPath.getCurrentPathLength(); ++i) {
                ShipPathPoint pathpoint = this.currentPath.getPathPointFromIndex(i);

                if(this.worldObj.canBlockSeeTheSky(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord)) {
                    this.currentPath.setCurrentPathLength(i - 1);
                    return;
                }
            }
        }
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    private boolean isDirectPathBetweenPoints(Vec3 pos1, Vec3 pos2, int xSize, int ySize, int zSize) {
        int x1 = MathHelper.floor_double(pos1.xCoord);
        int z1 = MathHelper.floor_double(pos1.zCoord);
        double xOffset = pos2.xCoord - pos1.xCoord;
        double zOffset = pos2.zCoord - pos1.zCoord;
        double xzOffsetSq = xOffset * xOffset + zOffset * zOffset;

        if (xzOffsetSq < 1.0E-8D) {	//若距離極小, 則判定不須移動
            return false;
        }
        else {
            double xzOffset = 1.0D / Math.sqrt(xzOffsetSq);
            xOffset *= xzOffset;	//normalize offset
            zOffset *= xzOffset;
            xSize += 2;				//size擴張2, 以檢查周圍方塊
            zSize += 2;
            
            if(!this.isSafeToStandAt(x1, (int)pos1.yCoord, z1, xSize, ySize, zSize, pos1, xOffset, zOffset)) {
                return false;		//若該點不能安全站立, 則false
            }
            else {					//該點可安全站立
                xSize -= 2;			//縮回原size
                zSize -= 2;
                double xOffAbs = 1.0D / Math.abs(xOffset);		//offset取絕對值
                double zOffAbs = 1.0D / Math.abs(zOffset);
                double x1Theta = (double)(x1*1) - pos1.xCoord;	//int座標-double座標, 取得小數offset
                double z1Theta = (double)(z1*1) - pos1.zCoord;	//以決定x,z方向
                //offset為正向, 則theta改為正值
                if(xOffset >= 0.0D) {
                    ++x1Theta;
                }
                if(zOffset >= 0.0D) {
                    ++z1Theta;
                }
                //以x,z位移做切割, 以便檢查該線上會經過的所有方塊
                x1Theta /= xOffset;
                z1Theta /= zOffset;
                int xDir = xOffset < 0.0D ? -1 : 1;				//xz方向
                int zDir = zOffset < 0.0D ? -1 : 1;
                int x2 = MathHelper.floor_double(pos2.xCoord);	//pos2座標值
                int z2 = MathHelper.floor_double(pos2.zCoord);
                int xIntOffset = x2 - x1;						//pos1,2整數距離
                int zIntOffset = z2 - z1;
                //檢查直線路徑上, 是否都能安全站立, 若都通過測試, 則回傳true
                do {
                    if(xIntOffset * xDir <= 0 && zIntOffset * zDir <= 0) {
                        return true;
                    }

                    if(x1Theta < z1Theta) {
                        x1Theta += xOffAbs;
                        x1 += xDir;
                        xIntOffset = x2 - x1;
                    }
                    else {
                        z1Theta += zOffAbs;
                        z1 += zDir;
                        zIntOffset = z2 - z1;
                    }
                }
                while(this.isSafeToStandAt(x1, (int)pos1.yCoord, z1, xSize, ySize, zSize, pos1, xOffset, zOffset));
                //無法通過檢查, 回傳false
                return false;
            }
        }
    }

    /**
     * Returns true when an entity could stand at a position, including solid blocks under the entire entity. Args:
     * xOffset, yOffset, zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean isSafeToStandAt(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, Vec3 orgPos, double vecX, double vecZ) {
        int xSize2 = xOffset - xSize / 2;
        int zSize2 = zOffset - zSize / 2;
        
        //若該位置有方塊卡住, 則false
        if(!this.isPositionClear(xSize2, yOffset, zSize2, xSize, ySize, zSize, orgPos, vecX, vecZ)) {
            return false;
        }
        else {
            for(int x1 = xSize2; x1 < xSize2 + xSize; ++x1) {
                for(int z1 = zSize2; z1 < zSize2 + zSize; ++z1) {
                    double x2 = (double)x1 + 0.5D - orgPos.xCoord;
                    double z2 = (double)z1 + 0.5D - orgPos.zCoord;

                    //該方塊周圍為clear, 檢查底下方塊是否可安全站立
                    if(x2 * vecX + z2 * vecZ >= 0.0D) {
                        Block block = this.worldObj.getBlock(x1, yOffset - 1, z1);
                        Material material = block.getMaterial();
                        //若不能飛, 底下又是air, 則false
                        if(material == Material.air && !this.canFly) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * Returns true if an entity does not collide with any solid blocks at the position. Args: xOffset, yOffset,
     * zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean isPositionClear(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, Vec3 orgPos, double vecX, double vecZ) {
        for(int x1 = xOffset; x1 < xOffset + xSize; ++x1) {
            for(int y1 = yOffset; y1 < yOffset + ySize; ++y1) {
                for(int z1 = zOffset; z1 < zOffset + zSize; ++z1) {
                    double x2 = (double)x1 + 0.5D - orgPos.xCoord;
                    double z2 = (double)z1 + 0.5D - orgPos.zCoord;

                    if(x2 * vecX + z2 * vecZ >= 0.0D) {
                        Block block = this.worldObj.getBlock(x1, y1, z1);
                        //若該方塊為實體方塊, 或者不為安全類方塊, 則視為有障礙物
                        if(!block.getBlocksMovement(this.worldObj, x1, y1, z1) || !EntityHelper.checkBlockSafe(block)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
