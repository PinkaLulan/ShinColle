package com.lulan.shincolle.ai.path;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;

/**SHIP PATH NAVIGATE
 * ship or airplane限定path ai, 該entity必須實作IShipNavigator
 * 無視重力或者浮力作用找出空中 or 水中路徑, 若entity在陸上移動則不需要更新此navigator
 * update move的部份不採用自然墜落跟jump, 而是直接加上一個motionY
 * 注意此path navigator使用時, 必須把ship floating關閉以免阻礙y軸移動
 */
public class ShipPathNavigate
{
	private static int UpdatePathDelay = 20;
    private EntityLiving host;
    /** The entity using ship path navigate */
    private IShipNavigator hostShip;
    private World world;
    /** The PathEntity being followed. */
    @Nullable
    private ShipPath currentPath;
    private double speed;
    /** Time, in number of ticks, following the current path */
    private int pathTicks;
    /** The time when the last position check was done (to detect successful movement) */
    private int ticksAtLastPos;
    /** Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck') */
    private Vec3d lastPosCheck = Vec3d.ZERO;
    /** pos for stuck checking */
    private Vec3d lastPosStuck = Vec3d.ZERO;
    /** time vars for stuck checking */
    private long timeoutTimer = 0L;
    private long lastTimeoutCheck = 0L;
    private double timeoutLimit;
    /** 距離路徑點多近才會判定為到達該點, 預設0.5格 */
    private float maxDistanceToWaypoint = 0.5F;
    private int hostCeilWeight, hostCeilHight;
    private long lastTimeUpdated;
    private BlockPos targetPos;
    

    public ShipPathNavigate(EntityLiving entity, World world)
    {
        this.host = entity;
        this.hostShip = (IShipNavigator) entity;
        this.world = world;
        this.maxDistanceToWaypoint = this.host.width > 0.75F ? this.host.width * 0.5F : 0.75F - this.host.width * 0.5F;
        this.hostCeilWeight = MathHelper.ceiling_float_int(this.host.width);
        this.hostCeilHight = MathHelper.ceiling_float_int(this.host.height);
        
    }

    /**
     * Sets the speed
     */
    public void setSpeed(double par1)
    {
        this.speed = par1;
    }

    /**
     * Gets the maximum distance that the path finding will search in.
     */
    public float getPathSearchRange()
    {
    	if (host instanceof BasicEntityAirplane)
    	{
    		return 64F;
    	}
    	else if (host instanceof IShipAttackBase)
    	{
    		float f = ((IShipAttackBase) host).getAttackRange() + 24F;
    		
    		return f < 40F ? 40F : f;
    	}
    	else
    	{
    		return 32F;
    	}
    }
    
    /**
     * Try to find and set a path to XYZ. Returns true if successful.
     */
    public boolean tryMoveToXYZ(double x, double y, double z, double speed)
    {
        ShipPath path = this.getPathToXYZ(MathHelper.floor_double(x), ((int)y), MathHelper.floor_double(z));
        return this.setPath(path, speed);
    }

    /**
     * Returns the path to the given coordinates
     */
    public ShipPath getPathToXYZ(double x, double y, double z)
    {
        return !this.canNavigate() ? null : this.getShipPathToXYZ(this.host, MathHelper.floor_double(x), (int)y, MathHelper.floor_double(z), this.getPathSearchRange(), this.hostShip.canFly());
    }
    
    public ShipPath getShipPathToXYZ(Entity entity, int x, int y, int z, float range, boolean canFly)
    {
    	//若path已經存在而且目標也相同, 則不重複找path
    	BlockPos pos = new BlockPos(x, y, z);
        if (this.currentPath != null && !this.currentPath.isFinished() && pos.equals(this.targetPos))
        {
            return this.currentPath;
        }
        
        this.targetPos = pos;
        this.world.theProfiler.startSection("pathfind");
        BlockPos hostPos = new BlockPos(entity);
        int i = (int)(range + 8.0F);
        ChunkCache chunkcache = new ChunkCache(this.world, hostPos.add(-i, -i, -i), hostPos.add(i, i, i), 0);
        ShipPath pathentity = (new ShipPathFinder(chunkcache, canFly)).findPath(entity, x, y, z, range);
        this.world.theProfiler.endSection();
        return pathentity;
    }

    /**
     * Returns the path to the given EntityLiving
     */
    public ShipPath getPathToEntityLiving(Entity entity)
    {
        return !this.canNavigate() ? null : this.getPathEntityToEntity(this.host, entity, this.getPathSearchRange(), this.hostShip.canFly());
    }
    
    public ShipPath getPathEntityToEntity(Entity entity, Entity targetEntity, float range, boolean canFly)
    {
    	//若path已經存在而且目標也相同, 則不重複找path
    	BlockPos pos = new BlockPos(targetEntity);
        if (this.currentPath != null && !this.currentPath.isFinished() && pos.equals(this.targetPos))
        {
            return this.currentPath;
        }
    	
        this.targetPos = pos;
        this.world.theProfiler.startSection("pathfind");
        BlockPos hostPos = (new BlockPos(entity)).up();
        int i = (int)(range + 16.0F);
        ChunkCache chunkcache = new ChunkCache(this.world, hostPos.add(-i, -i, -i), hostPos.add(i, i, i), 0);
        ShipPath pathentity = (new ShipPathFinder(chunkcache, canFly)).findPath(entity, targetEntity, range);
        this.world.theProfiler.endSection();
        return pathentity;
    }
    
    /**
     * Try to find and set a path to EntityLiving. Returns true if successful.
     */
    public boolean tryMoveToEntityLiving(Entity entity, double speed)
    {
        ShipPath pathentity = this.getPathToEntityLiving(entity);
        return pathentity != null ? this.setPath(pathentity, speed) : false;
    }

    /**
     * sets the active path data if path is 100% unique compared to old path, checks to adjust path for sun avoiding
     * ents and stores end coords
     */
    public boolean setPath(ShipPath pathEntity, double speed)
    {
        //若路徑為null, 表示找不到路徑
    	if (pathEntity == null)
    	{
            this.currentPath = null;
            return false;
        }
        else
        {
        	//比較新舊路徑是否相同, 不同時將舊路徑蓋掉
            if (!pathEntity.isSamePath(this.currentPath))
            {
                this.currentPath = pathEntity;
            }
            
            //若路徑長度為0, 表示沒path
            if (this.currentPath.getCurrentPathLength() == 0)
            {
                return false;
            }
            //成功設定path
            else
            {
                this.speed = speed;
                Vec3d vec3 = this.getEntityPosition();
                this.ticksAtLastPos = this.pathTicks;
                this.lastPosCheck = vec3;
                return true;
            }
        }
    }

    /**
     * gets the actively used PathEntity
     */
    public ShipPath getPath()
    {
        return this.currentPath;
    }

    /** navigation tick */
    public void onUpdateNavigation()
    {
    	//wait for entity init
    	if (host.ticksExisted > 40)
    	{
    		++this.pathTicks;
    		
            //若有path
            if (!this.noPath())
            {
            	//若可以執行移動, 則跑pathFollow方法更新下一個目標點
                if (this.canNavigate())
                {
                    this.pathFollow();
                }
                
                if (!this.noPath())
                {
                    Vec3d vec3 = this.currentPath.getPosition(this.host);

                    if (vec3 != null)
                    {
                    	//old method 1.7.10, move to target block TODO
//                    	this.hostShip.getShipMoveHelper().setMoveTo(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed);
                        
                    	//new method 1.9.4, move to target block with entity width and block's height (like slab or stair)
                        BlockPos blockPos = (new BlockPos(vec3)).down();
                        AxisAlignedBB blockAABB = this.world.getBlockState(blockPos).getBoundingBox(this.world, blockPos);
                        vec3 = vec3.subtract(0.0D, 1.0D - blockAABB.maxY, 0.0D);
                        this.hostShip.getShipMoveHelper().setMoveTo(vec3.xCoord, vec3.yCoord, vec3.zCoord, this.speed);
                    }
                }
            }
    	}
            
    }

    /** 判定entity是否卡住(超過100 tick仍在原地) or entity是否可以抄捷徑以省略一些路徑點 
     *  以y高度判定是否有些點可以省略 (不判定水平距離)
     */
    private void pathFollow()
    {
        Vec3d hostPos = this.getEntityPosition();
        int i = this.currentPath.getCurrentPathLength();

        //找出y高度不同於host所在高度的點
        for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j)
        {
            if ((double)this.currentPath.getPathPointFromIndex(j).yCoord != Math.floor(hostPos.yCoord))
            {
                i = j;
                break;
            }
        }

        Vec3d nowPos = this.currentPath.getCurrentPos();

        //若host成功到達目標路徑點, 則目標繼續設為下一個路徑點
        if (MathHelper.abs((float)(this.host.posX - (nowPos.xCoord + 0.5D))) < this.maxDistanceToWaypoint && MathHelper.abs((float)(this.host.posZ - (nowPos.zCoord + 0.5D))) < this.maxDistanceToWaypoint)
        {
            this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);
        }

        //若有y高度不同的點, 從該點往回到host目前點, 找其中是否有能直接前進的點
        for (int j1 = i - 1; j1 >= this.currentPath.getCurrentPathIndex(); --j1)
        {
            if (this.isDirectPathBetweenPoints(hostPos, this.currentPath.getVectorFromIndex(this.host, j1), this.hostCeilWeight, this.hostCeilHight, this.hostCeilWeight))
            {
                this.currentPath.setCurrentPathIndex(j1);
                break;
            }
        }

        this.checkForStuck(hostPos);
        
//        //TODO review old code
//        Vec3d entityPos = this.getEntityPosition();
//        int pptemp = this.currentPath.getCurrentPathLength();
//
//        //在entity目前點到終點之間, 找出是否有y高度不同的點
////        for (int j = this.currentPath.getCurrentPathIndex(); j < this.currentPath.getCurrentPathLength(); ++j)
////        {
////            if ((double)this.currentPath.getPathPointFromIndex(j).yCoord != Math.floor(entityPos.yCoord))
////            {
////            	pptemp = j;
////                break;
////            }
////        }
////        float widthSq = this.theEntity.width * this.theEntity.width * 0.25F;
//        float widthSq = this.host.width * this.host.width;
//        int k;
//
//        //掃描目前的點到y高度不同or最後的點, 若距離不到entity大小, 則判定entity已經到達該點
//        for (k = this.currentPath.getCurrentPathIndex(); k < pptemp; ++k)
//        {
//            if (entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.host, k)) < widthSq)
//            {
////            	LogHelper.info("DEBUG : path navi: get path+1 "+k+" "+entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity, k)));
//            	this.currentPath.setCurrentPathIndex(++k);	//已到達目標點, 設定目標點為下一點
//            }
//        }
//
//        k = MathHelper.ceiling_float_int(this.host.width);
//        int heighInt = (int)this.host.height;
//        int widthInt = k;
//
//        //從y高度不合的點往回掃描, 找是否有點能從目前點直線走過去, 有的話將該點設為目標點使entity往該點直線前進
//        for (int j1 = pptemp - 1; j1 >= this.currentPath.getCurrentPathIndex(); --j1)
//        {
//            if (this.isDirectPathBetweenPoints(entityPos, this.currentPath.getVectorFromIndex(this.host, j1), k, heighInt, widthInt))
//            {
//                this.currentPath.setCurrentPathIndex(j1);
//                break;
//            }
//        }
//        
//        checkForStuck(hostPos);
    }
    
    /**
     * clear path if entity is stuck
     */
    protected void checkForStuck(Vec3d pos)
    {
    	//每過100 tick檢查一次, 若移動不到1.5格, 則清空path
    	if (this.pathTicks - this.ticksAtLastPos > 100)
        {
            if (pos.squareDistanceTo(this.lastPosCheck) < 2.25D)
            {
                this.clearPathEntity();
            }

            this.ticksAtLastPos = this.pathTicks;
            this.lastPosCheck = pos;
        }

    	//若path沒被清除, 且還沒走到終點
        if (this.currentPath != null && !this.currentPath.isFinished())
        {
            Vec3d vec3d = this.currentPath.getCurrentPos();

            //若目標點跟上一次檢查點lastPosStuck不同, 則更新之, 並以host速度設定下一次檢查時間
            if (!vec3d.equals(this.lastPosStuck))
            {
                this.lastPosStuck = vec3d;
                double d0 = pos.distanceTo(this.lastPosStuck);
                this.timeoutLimit = this.host.getAIMoveSpeed() > 0F ? d0 / (double)this.host.getAIMoveSpeed() * 1000D : 0D;
            }
            //若lastPosStuck相同, 表示目標點沒換過, 持續計時
            else
            {
                this.timeoutTimer += System.currentTimeMillis() - this.lastTimeoutCheck;
            }

            //若host已經走了超過預定時間三倍, 則清空path
            if (this.timeoutLimit > 0D && (double)this.timeoutTimer > this.timeoutLimit * 3D)
            {
                this.lastPosStuck = Vec3d.ZERO;
                this.timeoutTimer = 0L;
                this.timeoutLimit = 0D;
                this.clearPathEntity();
            }

            this.lastTimeoutCheck = System.currentTimeMillis();
        }
        
        
        //TODO review old code
//        //每N ticks檢查一次移動距離
//        if (this.pathTicks - this.ticksAtLastPos > 32)
//        {
//        	//若距離上一次成功移動的點不到1.5格, 則表示某種原因造成幾乎沒移動, 清除該path
//            if (pos.squareDistanceTo(this.lastPosCheck) < 3D)
//            {
//            	//嘗試跳躍 + 左右隨機移動來脫離柵欄方塊
//            	if (!currentPath.isFinished())
//            	{
//            		float dx = (float) (currentPath.getVectorFromIndex(this.host, currentPath.getCurrentPathIndex()).xCoord - host.posX);
//                	float dz = (float) (currentPath.getVectorFromIndex(this.host, currentPath.getCurrentPathIndex()).zCoord - host.posZ);
//                	double targetX = host.posX;
//                	double targetZ = host.posZ;
//                	
//                	//get random position
//                	if (dx > 0.2F || dx < -0.2F)  //若目標點離x方向一定距離, 則在z方向隨機選+-1
//                	{
//                		targetZ = host.getRNG().nextInt(2) == 0 ? targetZ - 2D : targetZ + 2D;
//                		targetX = dx < 0F ? targetX + 2D : targetX - 2D;
//                	}
//                	else if (dz > 0.2F || dz < -0.2F)  //若目標點離z方向一定距離, 則在x方向隨機選+-1
//                	{
//                		targetX = host.getRNG().nextInt(2) == 0 ? targetX - 2D : targetX + 2D;
//                		targetZ = dz < 0F ? targetZ + 2D : targetZ - 2D;
//                	}
//                	
//                	//set move
//                	this.hostShip.getShipMoveHelper().setMoveTo(targetX, host.posY, targetZ, this.speed);
//                	
//                	//try random jump
//                	if (host.getRNG().nextInt(3) == 0)
//                	{
//                		host.getJumpHelper().setJumping();
//                		float speed = (float) host.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 0.5F;
//                		if(dx > 0.5F) host.motionX += speed;
//                		if(dx < 0.5F) host.motionX -= speed;
//                		if(dz > 0.5F) host.motionZ += speed;
//                		if(dz < 0.5F) host.motionZ -= speed;
//                	}
//            	}
//            	
//            	//超過50 ticks都還是卡住, 清空path
//            	if (this.pathTicks - this.ticksAtLastPos > 50)
//                {
//                    this.ticksAtLastPos = this.pathTicks;
//                    this.lastPosCheck.xCoord = pos.xCoord;
//                    this.lastPosCheck.yCoord = pos.yCoord;
//                    this.lastPosCheck.zCoord = pos.zCoord;
//            		this.clearPathEntity();
//            		return;
//                }
//            }
//            else
//            {
//            	//更新成功移動的紀錄
//                this.ticksAtLastPos = this.pathTicks;
//                this.lastPosCheck.xCoord = pos.xCoord;
//                this.lastPosCheck.yCoord = pos.yCoord;
//                this.lastPosCheck.zCoord = pos.zCoord;
//            }
//        }
    }

    /**
     * If null path or reached the end
     */
    public boolean noPath()
    {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    /**
     * sets active PathEntity to null
     */
    public void clearPathEntity()
    {
        this.currentPath = null;
    }

    /** 
     * 將entity位置資訊以vec3表示
     */
    private Vec3d getEntityPosition()
    {
        return new Vec3d(this.host.posX, this.host.posY + 0.5D, this.host.posZ);
    }

    /**
     * 若能飛, 則以目前y為起點
     * 若不能飛, 則往下找到第一個非空氣的方塊為起點 (水面 or 實體方塊)
     */
    private double getPathableYPos()
    {
    	if (this.hostShip.canFly())
    	{
//    	LogHelper.info("DEBUG : path navi: path y "+(int)(this.theEntity.boundingBox.minY + 0.5D));
    		//可以飛, 直接以目前y為起點
//            return (int)(this.theEntity.boundingBox.minY + 0.5D);
    		return this.host.posY;
        }
        else
        {
        	 int i = (int) this.host.posY;
             IBlockState block = this.world.getBlockState(new BlockPos(MathHelper.floor_double(this.host.posX), i, MathHelper.floor_double(this.host.posZ)));
             int j = 0;
             
             //往下找出第一個非air的方塊, 若是液體方塊則回傳稍微高一點的y
             do
             {
                 if (block != null && block.getMaterial() != Material.AIR)
                 {
                	 if (BlockHelper.checkBlockIsLiquid(block))
                	 {
                		 return i + 0.4D;
                	 }
                	 else
                	 {
                		 return i;
                	 }  
                 }

                 ++i;
                 block = this.world.getBlockState(new BlockPos(MathHelper.floor_double(this.host.posX), i, MathHelper.floor_double(this.host.posZ)));
                 ++j;
             }
             while (j <= 24);	//最多往下找24格就停止
             
             //找超過16格都沒底, 則直接回傳目前y
             return (int) this.host.posY;
        }
    }

    /**非騎乘中, 且該entity可以飛 or 站在地面 or 在可穿透方塊中
     */
    private boolean canNavigate()
    {
        return !host.isRiding() && (this.hostShip.canFly() || this.host.onGround || EntityHelper.checkEntityIsFree(host));
    }

    /**
     * Returns true if the entity is in water or lava or other liquid
     */
    private boolean isInLiquid()
    {
        return EntityHelper.checkEntityIsInLiquid(host);
    }

    /**entity抄捷徑的方法, 若直線可視無阻礙, 則會直線往該點移動
     * 
     * 1.9.4
     * 直接視線判定目標是否有無被阻擋
     * 
     ********** 以下為1.7.10* ***********
     * NEW: 加入y軸判定, 使其可以抄y軸捷徑
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    private boolean isDirectPathBetweenPoints(Vec3d pos1, Vec3d pos2, int xSize, int ySize, int zSize)
    {
    	RayTraceResult raytraceresult = this.world.rayTraceBlocks(pos1, new Vec3d(pos2.xCoord, pos2.yCoord + this.host.height * 0.5D, pos2.zCoord), false, true, false);
        return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
    	
    	//TODO review old code
//        int x1 = MathHelper.floor_double(pos1.xCoord);
//        int z1 = MathHelper.floor_double(pos1.zCoord);
//        int y1 = (int)pos1.yCoord;
//        double xOffset = pos2.xCoord - pos1.xCoord;
//        double zOffset = pos2.zCoord - pos1.zCoord;
//        double yOffset = pos2.yCoord - pos1.yCoord;
//        double xzOffsetSq = xOffset * xOffset + zOffset * zOffset + yOffset * yOffset;
////        double xzOffsetSq = xOffset * xOffset + zOffset * zOffset;
//
//        if(xzOffsetSq < 1.0E-12D) {	//若距離極小, 則判定不須跳點 (移動最小單位是0.01 因此xzSq最小為E-8)
//            return false;
//        }
//        else {						//搜尋可跳的點
//            double xzOffset = 1.0D / Math.sqrt(xzOffsetSq);
//            xOffset *= xzOffset;	//normalize offset
//            zOffset *= xzOffset;
//            yOffset *= xzOffset;
//            xSize += 2;				//size擴張2, 以檢查周圍方塊
//            zSize += 2;
//            ySize += 2;
//            
//            if(!this.isSafeToStandAt(x1, y1, z1, xSize, ySize, zSize, pos1, xOffset, zOffset)) {
//                return false;		//若該點不能安全站立, 則false
//            }
//            else {					//該點可安全站立
//            	//縮回原size
//                xSize -= 2;
//                zSize -= 2;
//                ySize -= 2; 
//                //offset取絕對值
//                double xOffAbs = 1.0D / Math.abs(xOffset);
//                double zOffAbs = 1.0D / Math.abs(zOffset);
//                double yOffAbs = 1.0D / Math.abs(yOffset);  
//                //int座標-double座標, 取得位移theta值
//                double x1Theta = x1*1 - pos1.xCoord;
//                double z1Theta = z1*1 - pos1.zCoord;
//                double y1Theta = y1*1 - pos1.yCoord;     
//                //若offset為正向, 則theta+1變回正值
//                if(xOffset >= 0.0D) {
//                    ++x1Theta;
//                }
//                if(zOffset >= 0.0D) {
//                    ++z1Theta;
//                }
//                if(yOffset >= 0.0D) {
//                    ++y1Theta;
//                }       
//                //normalize theta值 (normalize且全部變為正值, 比較大小用)
//                x1Theta /= xOffset;
//                z1Theta /= zOffset;
//                y1Theta /= yOffset;
//                //xz方向, 用於抓方塊位置
//                int xDir = xOffset < 0.0D ? -1 : 1;
//                int zDir = zOffset < 0.0D ? -1 : 1;
//                int yDir = yOffset < 0.0D ? -1 : 1;
//                //取得pos2整數座標
//                int x2 = MathHelper.floor_double(pos2.xCoord);
//                int z2 = MathHelper.floor_double(pos2.zCoord);
//                int y2 = MathHelper.floor_double(pos2.yCoord);
//                //計算pos1,2整數距離
//                int xIntOffset = x2 - x1;
//                int zIntOffset = z2 - z1;
//                int yIntOffset = y2 - y1;
//                
//                //以theta值做遞增, 檢查該線上經過會碰到的所有方塊, 是否都能安全站立, 若都通過測試, 則回傳true
//                do {
//                	//若三方向都沒有距離差, 則結束
//                    if(xIntOffset * xDir <= 0 && zIntOffset * zDir <= 0 && yIntOffset * yDir <= 0) {
////                    if(xIntOffset * xDir <= 0 && zIntOffset * zDir <= 0) {
//                        return true;
//                    }
//                    
//                    //找出theta最小的值 (最沒有被推進過的方向), 該方向+1格
//                    switch(CalcHelper.min(x1Theta, y1Theta, z1Theta)) {
//                    case 1:
//                    	x1Theta += xOffAbs;
//                        x1 += xDir;
//                        xIntOffset = x2 - x1;
//                    	break;
//                    case 2:
//                    	y1Theta += yOffAbs;
//                        y1 += yDir;
//                        yIntOffset = y2 - y1;
//                    	break;
//                    case 3:
//                    	z1Theta += zOffAbs;
//                        z1 += zDir;
//                        zIntOffset = z2 - z1;
//                    	break;
//                    default:
//                    	break;
//                    }
//                }
//                while(this.isSafeToStandAt(x1, y1, z1, xSize, ySize, zSize, pos1, xOffset, zOffset));
//                //無法通過檢查, 回傳false
//                return false;
//            }
//        }
    }

    /**
     * Returns true when an entity could stand at a position, including solid blocks under the entire entity. Args:
     * xOffset, yOffset, zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean isSafeToStandAt(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, Vec3d orgPos, double vecX, double vecZ)
    {
    	//會飛的entity不須檢查落腳點
    	if (this.hostShip.canFly()) return true;
    	
        int xSize2 = xOffset - xSize / 2;
        int zSize2 = zOffset - zSize / 2;
        
        //若該位置有方塊卡住, 則false
        if (!this.isPositionClear(xSize2, yOffset, zSize2, xSize, ySize, zSize, orgPos, vecX, vecZ))
        {
            return false;
        }
        //檢查所有落腳方塊是否安全可站立
        else
        {
            for (int x1 = xSize2; x1 < xSize2 + xSize; ++x1)
            {
                for (int z1 = zSize2; z1 < zSize2 + zSize; ++z1)
                {
                    double x2 = x1 + 0.5D - orgPos.xCoord;
                    double z2 = z1 + 0.5D - orgPos.zCoord;

                    //檢查底下方塊種類
                    if (x2 * vecX + z2 * vecZ >= 0D)
                    {
                        IBlockState block = this.world.getBlockState(new BlockPos(x1, yOffset - 1, z1));
                        //若不能飛, 底下又是air, 則false
                        if(block == null || block.getMaterial() == Material.AIR)
                        {
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
    private boolean isPositionClear(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, Vec3d orgPos, double vecX, double vecZ)
    {
    	//考慮host大小後, 檢查host身體佔據的範圍是否有方塊是實體不能通過
        for (BlockPos blockpos : BlockPos.getAllInBox(new BlockPos(xOffset, yOffset, zOffset), new BlockPos(xOffset + xSize - 1, yOffset + ySize - 1, zOffset + zSize - 1)))
        {
            double d0 = (double)blockpos.getX() + 0.5D - orgPos.xCoord;
            double d1 = (double)blockpos.getZ() + 0.5D - orgPos.zCoord;

            if (d0 * vecX + d1 * vecZ >= 0D)
            {
                Block block = this.world.getBlockState(blockpos).getBlock();

                if (!block.isPassable(this.world, blockpos))
                {
                    return false;
                }
            }
        }

        return true;
    }
}
