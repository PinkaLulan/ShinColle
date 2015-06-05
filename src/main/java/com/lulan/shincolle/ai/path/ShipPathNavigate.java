package com.lulan.shincolle.ai.path;

import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormatHelper;
import com.lulan.shincolle.utility.LogHelper;

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
            //若pathFollow沒把path清除, 表示還可以繼續移動
        	//取得下一個目標點
            Vec3 vec3 = this.currentPath.getPosition(this.theEntity);
//                LogHelper.info("DEBUG : path navi: path vec "+this.currentPath.getCurrentPathIndex()+" / "+this.currentPath.getCurrentPathLength()+" "+vec3.xCoord+" "+vec3.yCoord+" "+vec3.zCoord);
//                LogHelper.info("DEBUG : path navi: path pp  "+currentPath.getPathPointFromIndex(currentPath.getCurrentPathIndex()).xCoord+" "+currentPath.getPathPointFromIndex(currentPath.getCurrentPathIndex()).yCoord+" "+currentPath.getPathPointFromIndex(currentPath.getCurrentPathIndex()).zCoord+" ");
//                LogHelper.info("DEBUG : path navi: path pos "+this.theEntity.posX+" "+this.theEntity.posY+" "+this.theEntity.posZ+" ");
            //若還有下一個點要移動, 則設定移動量使move helper可以實際移動entity
            if(vec3 != null) {
                this.theEntity2.getShipMoveHelper().setMoveTo(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed);
            }
            
            //若可以執行移動, 則跑pathFollow方法更新下一個目標點
            if(this.canNavigate()) {
//            	LogHelper.info("DEBUG : path navi: path follow");
                this.pathFollow();
            }
        }
    }

    /** 判定entity是否卡住(超過100 tick仍在原地) or entity是否可以抄捷徑以省略一些路徑點 
     *  以y高度判定是否有些點可以省略 (不判定水平距離)
     */
    private void pathFollow() {
        Vec3 entityPos = this.getEntityPosition();
        int pptemp = this.currentPath.getCurrentPathLength();

//        //掃描還沒走的路徑點, 找出是否有y高度差距接近1格的點
//        for(int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j) {
////            if(this.currentPath.getPathPointFromIndex(j).yCoord != (int)entityPos.yCoord) {
//        	float diff = (float) (this.currentPath.getPathPointFromIndex(j).yCoord - entityPos.yCoord);
//        	if(MathHelper.abs(diff) > 0.8F) {
//            	pptemp = j;
////            	LogHelper.info("DEBUG : path navi: y diff "+j+" "+diff+" : "+this.currentPath.getPathPointFromIndex(j).yCoord+" "+entityPos.yCoord);
////            	LogHelper.info("DEBUG : path navi: y diff "+j+" "+" : "+this.currentPath.getPathPointFromIndex(j).yCoord+" "+entityPos.yCoord);
//            	break;
//            }
//        }

//        float widthSq = this.theEntity.width * this.theEntity.width * 0.25F;
        float widthSq = this.theEntity.width * this.theEntity.width;
        int k;

        //掃描目前的點到y高度不同or最後的點, 若距離不到entity大小, 則判定entity已經到達該點
        for(k = this.currentPath.getCurrentPathIndex(); k < pptemp; ++k) {
            if(entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, k)) < (double)widthSq) {
//            	LogHelper.info("DEBUG : path navi: get path+1 "+k+" "+entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, k)));
            	this.currentPath.setCurrentPathIndex(k + 1);	//已到達目標點, 設定目標點為下一點
            }
        }

        k = MathHelper.ceiling_float_int(this.theEntity.width);
        int heighInt = (int)this.theEntity.height;
        int widthInt = k;

        //從y高度不合的點往回掃描, 找是否有點能從目前點直線走過去, 有的話將該點設為目標點使entity往該點直線前進
        for(int j1 = pptemp - 1; j1 >= this.currentPath.getCurrentPathIndex(); --j1) {
            if(this.isDirectPathBetweenPoints(entityPos, this.currentPath.getVectorFromIndex(this.theEntity, j1), k, heighInt, widthInt)) {
                this.currentPath.setCurrentPathIndex(j1);
                break;
            }
        }

        //每N ticks檢查一次移動距離
        if(this.totalTicks - this.ticksAtLastPos > 25) {
        	//若距離上一次成功移動的點不到1.5格, 則表示某種原因造成幾乎沒移動, 清除該path
            if(entityPos.squareDistanceTo(this.lastPosCheck) < 2.25D) {
            	//可能本身在柵欄方塊中(起點就在柵欄方塊), 判定要移動到柵欄隔壁空地方塊, 但是要穿過柵欄, AI無法判斷人在柵欄哪一側
            	//暫定解法: 隨機往路徑方向的左右移動一格, 嘗試脫離柵欄
//            	Block stand = theEntity.worldObj.getBlock(MathHelper.floor_double(theEntity.posX), (int)theEntity.posY, MathHelper.floor_double(theEntity.posZ));
            	float dx = (float) (theEntity.posX - currentPath.getVectorFromIndex(this.theEntity, currentPath.getCurrentPathIndex()).xCoord);
            	float dz = (float) (theEntity.posZ - currentPath.getVectorFromIndex(this.theEntity, currentPath.getCurrentPathIndex()).zCoord);
            	LogHelper.info("DEBUG : path navi: get stand block: "+dx+" "+dz);
            	double targetX = theEntity.posX;
            	double targetZ = theEntity.posZ;
            	
            	//get random position
            	if(dx > 0.2 || dx < -0.2) {	//若目標點離x方向一定距離, 則在z方向隨機選+-1
            		targetZ = theEntity.getRNG().nextInt(2) == 0 ? targetZ - 1.5D : targetZ + 1.5D;
            	}
            	
            	if(dz > 0.2 || dz < -0.2) {
            		targetX = theEntity.getRNG().nextInt(2) == 0 ? targetX - 1.5D : targetX + 1.5D;
            	}
            	
            	//set move
            	this.theEntity2.getShipMoveHelper().setMoveTo(targetX, theEntity.posY, targetZ, this.speed);
            
            	//超過5秒無法移動, clear path
                if(this.totalTicks - this.ticksAtLastPos > 100) {
                	this.clearPathEntity();
                }
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
//        return Vec3.createVectorHelper(this.theEntity.posX, this.getPathableYPos(), this.theEntity.posZ);
        return Vec3.createVectorHelper(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ);
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
    private double getPathableYPos() {
    	if(this.canFly) {
//    	LogHelper.info("DEBUG : path navi: path y "+(int)(this.theEntity.boundingBox.minY + 0.5D));
    		//可以飛, 直接以目前y為起點
//            return (int)(this.theEntity.boundingBox.minY + 0.5D);
    		return this.theEntity.posY;
        }
        else {
        	 int i = (int)this.theEntity.boundingBox.minY;
             Block block = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), i, MathHelper.floor_double(this.theEntity.posZ));
             int j = 0;
             //往下找出第一個非air的方塊
             do {
                 if(block != Blocks.air && block != null) {
                	 if(EntityHelper.checkBlockIsLiquid(block)) {
                		 return i + 0.4D;
                	 }
                	 else {
                		 return i;
                	 }  
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

    /**非騎乘中, 且該entity可以飛 or 站在地面 or 在可穿透方塊中
     */
    private boolean canNavigate() {
        return !theEntity.isRiding() && (this.canFly || this.theEntity.onGround || EntityHelper.checkEntityIsFree(theEntity));
    }

    /**
     * Returns true if the entity is in water or lava or other liquid
     */
    private boolean isInLiquid() {
        return EntityHelper.checkEntityIsInLiquid(theEntity);
    }

    /**NO USE
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

    /**entity抄捷徑的方法, 若直線可視無阻礙, 則會直線往該點移動
     * NEW: 加入y軸判定, 使其可以抄y軸捷徑
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    private boolean isDirectPathBetweenPoints(Vec3 pos1, Vec3 pos2, int xSize, int ySize, int zSize) {
        int x1 = MathHelper.floor_double(pos1.xCoord);
        int z1 = MathHelper.floor_double(pos1.zCoord);
        int y1 = (int)pos1.yCoord;
        double xOffset = pos2.xCoord - pos1.xCoord;
        double zOffset = pos2.zCoord - pos1.zCoord;
        double yOffset = pos2.yCoord - pos1.yCoord;
        double xzOffsetSq = xOffset * xOffset + zOffset * zOffset + yOffset * yOffset;
//        double xzOffsetSq = xOffset * xOffset + zOffset * zOffset;

        if(xzOffsetSq < 1.0E-12D) {	//若距離極小, 則判定不須跳點 (移動最小單位是0.01 因此xzSq最小為E-8)
            return false;
        }
        else {						//搜尋可跳的點
            double xzOffset = 1.0D / Math.sqrt(xzOffsetSq);
            xOffset *= xzOffset;	//normalize offset
            zOffset *= xzOffset;
            yOffset *= xzOffset;
            xSize += 2;				//size擴張2, 以檢查周圍方塊
            zSize += 2;
            ySize += 2;
            
            if(!this.isSafeToStandAt(x1, y1, z1, xSize, ySize, zSize, pos1, xOffset, zOffset)) {
                return false;		//若該點不能安全站立, 則false
            }
            else {					//該點可安全站立
            	//縮回原size
                xSize -= 2;
                zSize -= 2;
                ySize -= 2; 
                //offset取絕對值
                double xOffAbs = 1.0D / Math.abs(xOffset);
                double zOffAbs = 1.0D / Math.abs(zOffset);
                double yOffAbs = 1.0D / Math.abs(yOffset);  
                //int座標-double座標, 取得位移theta值
                double x1Theta = (double)(x1*1) - pos1.xCoord;
                double z1Theta = (double)(z1*1) - pos1.zCoord;
                double y1Theta = (double)(y1*1) - pos1.yCoord;     
                //若offset為正向, 則theta+1變回正值
                if(xOffset >= 0.0D) {
                    ++x1Theta;
                }
                if(zOffset >= 0.0D) {
                    ++z1Theta;
                }
                if(yOffset >= 0.0D) {
                    ++y1Theta;
                }       
                //normalize theta值 (normalize且全部變為正值, 比較大小用)
                x1Theta /= xOffset;
                z1Theta /= zOffset;
                y1Theta /= yOffset;
                //xz方向, 用於抓方塊位置
                int xDir = xOffset < 0.0D ? -1 : 1;
                int zDir = zOffset < 0.0D ? -1 : 1;
                int yDir = yOffset < 0.0D ? -1 : 1;
                //取得pos2整數座標
                int x2 = MathHelper.floor_double(pos2.xCoord);
                int z2 = MathHelper.floor_double(pos2.zCoord);
                int y2 = MathHelper.floor_double(pos2.yCoord);
                //計算pos1,2整數距離
                int xIntOffset = x2 - x1;
                int zIntOffset = z2 - z1;
                int yIntOffset = y2 - y1;
                
                //以theta值做遞增, 檢查該線上經過會碰到的所有方塊, 是否都能安全站立, 若都通過測試, 則回傳true
                do {
                	//若三方向都沒有距離差, 則結束
                    if(xIntOffset * xDir <= 0 && zIntOffset * zDir <= 0 && yIntOffset * yDir <= 0) {
//                    if(xIntOffset * xDir <= 0 && zIntOffset * zDir <= 0) {
                        return true;
                    }
                    
                    //找出theta最小的值 (最沒有被推進過的方向), 該方向+1格
                    switch(FormatHelper.min(x1Theta, y1Theta, z1Theta)) {
                    case 1:
                    	x1Theta += xOffAbs;
                        x1 += xDir;
                        xIntOffset = x2 - x1;
                    	break;
                    case 2:
                    	y1Theta += yOffAbs;
                        y1 += yDir;
                        yIntOffset = y2 - y1;
                    	break;
                    case 3:
                    	z1Theta += zOffAbs;
                        z1 += zDir;
                        zIntOffset = z2 - z1;
                    	break;
                    default:
                    	break;
                    }
                }
                while(this.isSafeToStandAt(x1, y1, z1, xSize, ySize, zSize, pos1, xOffset, zOffset));
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
        
        //會飛的entity不須檢查落腳點
    	if(this.canFly) return true;
        
        //若該位置有方塊卡住, 則false
        if(!this.isPositionClear(xSize2, yOffset, zSize2, xSize, ySize, zSize, orgPos, vecX, vecZ)) {
            return false;
        }
        else {
        	//不會飛的entity必須檢查全部落腳方塊
            for(int x1 = xSize2; x1 < xSize2 + xSize; ++x1) {
                for(int z1 = zSize2; z1 < zSize2 + zSize; ++z1) {
                    double x2 = (double)x1 + 0.5D - orgPos.xCoord;
                    double z2 = (double)z1 + 0.5D - orgPos.zCoord;

                    //檢查底下方塊是否可安全站立
                    if(x2 * vecX + z2 * vecZ >= 0.0D) {
                        Block block = this.worldObj.getBlock(x1, yOffset - 1, z1);
                        Material material = block.getMaterial();
                        //若不能飛, 底下又是air, 則false
                        if(block == null || material == Material.air) {
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
                        
                        //若為安全類方塊, 則視為clear
                        if(EntityHelper.checkBlockSafe(block)) return true;
                        
                        if(block == Blocks.fence) {
                        	return false;
                        }
                        
                        //若該方塊為其他不可通過方塊, 則視為有障礙物
                        if(!block.getBlocksMovement(this.worldObj, x1, y1, z1)) {
                        	return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
