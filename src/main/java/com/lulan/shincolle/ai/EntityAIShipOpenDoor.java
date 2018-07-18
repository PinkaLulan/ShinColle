package com.lulan.shincolle.ai;

import java.util.ArrayList;

import com.lulan.shincolle.ai.path.ShipPath;
import com.lulan.shincolle.ai.path.ShipPathPoint;
import com.lulan.shincolle.handler.IMoveShip;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/** ship open door AI
 * 
 *  get door: 只尋找posY +1 格位置的門 (僅適用於身高 < 2的生物)
 *  
 *  get gate: 依據身體大小計算全部會碰到的gate
 *
 */
public class EntityAIShipOpenDoor extends EntityAIBase
{
	
	private Entity host;
	private IMoveShip host2;
	private ArrayList<BlockPos> doors;
	private BlockPos pathPoint;
	private boolean hasPassed;  //true時表示已經通過門，可準備結束AI
	private float vecX;
	private float vecZ;
	private boolean closeDoor;		//通過門後是否要隨手關門
	private int delay;				//通過門的delay, 時間到後關門
	private float dist;
	
    
    public EntityAIShipOpenDoor(IMoveShip host, boolean closeDoor)
    {
    	this.setMutexBits(0);
        this.host = (Entity) host;
        this.host2 = host;
        this.closeDoor = closeDoor;
        this.dist = (this.host.width + 1F) * (this.host.width + 1F);
    	this.doors = new ArrayList<BlockPos>();
    	this.pathPoint = BlockPos.ORIGIN;
    }
    
    @Override
    public boolean shouldExecute()
    {
        ShipPath path = this.host2.getShipNavigate().getPath();
        
        if (!this.host.collidedHorizontally)
        {
        	return false;
        }
        //有尚未完成的移動路徑
        else if (path != null && !path.isFinished())
        {
        	BlockPos pos = null;
        	
        	//找路徑中接下來兩點, 偵測有無碰到門
            for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
            {
                ShipPathPoint pp = path.getPathPointFromIndex(i);
                pos = new BlockPos(pp.xCoord, pp.yCoord, pp.zCoord);
                
                //若路徑點已經在特定距離內, 則開始找門
                if (this.host.getDistanceSqToCenter(pos) <= this.dist)
                {
                	//get doors or gates
                    this.checkDoors(pos, this.host, this.doors);
                    this.pathPoint = pos;
                }
            }

            //若路徑點沒有門, 則偵測host是否本身卡在門方塊中
            pos = new BlockPos(this.host);
            this.checkDoors(pos, this.host, this.doors);
            this.pathPoint = pos;
            
            //若都沒有門, 則結束
            if (this.doors.isEmpty()) return false;
            //找到門, 繼續執行
            return true;
        }//end has path
        
        return false;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
    	//若需要關門, 則等待delay時間到
        return this.closeDoor && this.delay > 0 && !this.hasPassed;
    }

    @Override
    public void startExecuting()
    {
        this.delay = 30;
        this.hasPassed = false;
        
        //更改meta值為開門
        this.setDoorOpen(true);
        
        //計算向量以便判定是否通過
        if (this.pathPoint != BlockPos.ORIGIN)
        {
        	this.vecX = (float)this.pathPoint.getX() + 0.5F - (float)this.host.posX;
            this.vecZ = (float)this.pathPoint.getZ() + 0.5F - (float)this.host.posZ;
        }
    }

    @Override
    public void resetTask()
    {
    	//隨手關門
        if (this.closeDoor && this.doors.size() > 0) this.setDoorOpen(false);
        
        //清空list
    	this.doors = new ArrayList<BlockPos>();
    }

    @Override
    public void updateTask()
    {
        --this.delay;
        
        //check passed
        if (this.delay <= 0)
        {
        	//計算host位置vector
        	float vx = (float)this.pathPoint.getX() + 0.5F - (float)this.host.posX;
        	float vz = (float)this.pathPoint.getZ() + 0.5F - (float)this.host.posZ;
            float v = this.vecX * vx + this.vecZ * vz;

            //若host已經通過門, 則vec相乘後會小於0, 表示可以關門
            if (v < 0F)
            {
                this.hasPassed = true;
            }
        }
    }

    //add door or gate block to list
    private static void checkDoors(BlockPos pos, Entity host, ArrayList<BlockPos> list)
    {
    	int range = MathHelper.floor(host.width + 1F);
    	int minX = pos.getX() - range;
    	int minY = pos.getY();
    	int minZ = pos.getZ() - range;
    	int maxX = pos.getX() + range;
    	int maxY = pos.getY() + MathHelper.floor(host.height + 1F);
    	int maxZ = pos.getZ() + range;
    	BlockPos pos2;
    	IBlockState state;
    	Block block;
    	
    	//get gate within entity hitbox at x,y,z
    	for (int ix = minX; ix <= maxX; ix++)
    	{
    		for (int iz = minZ; iz <= maxZ; iz++)
    		{
    			for (int iy = minY; iy <= maxY; iy++)
    			{
    				pos2 = new BlockPos(ix, iy, iz);
    				state = host.world.getBlockState(pos2);
    				block = state.getBlock();
    				
    				if (block instanceof BlockFenceGate ||
    					(iy != minY && block instanceof BlockDoor &&
    					 state.getMaterial() == Material.WOOD))
    				{
    					list.add(pos2);
    				}
            	}
        	}
    	}
    }
    
    //true = open, false = close door
    private void setDoorOpen(boolean open)
    {
        IBlockState state = null;
        
        for (BlockPos pos : this.doors)
        {
        	state = this.host.world.getBlockState(pos);
        	
        	if (state.getBlock() instanceof BlockDoor)
        	{
        		((BlockDoor) state.getBlock()).toggleDoor(this.host.world, pos, open);
        	}
        	else if (state.getBlock() instanceof BlockFenceGate)
        	{
        		toggleGate(pos, this.host, state, open);
        	}
        }
    }
    
    //activate all gate in list to open or close
    private static void toggleGate(BlockPos pos, Entity host, IBlockState state, boolean openGate)
    {
    	//null check
		if (state.getBlock() instanceof BlockFenceGate)
		{
			//open gate
			if (((Boolean)state.getValue(BlockFenceGate.OPEN)).booleanValue())
			{
				if (!openGate)  //want to close gate
				{
		            state = state.withProperty(BlockFenceGate.OPEN, Boolean.valueOf(false));
		            host.world.setBlockState(pos, state, 10);
		            host.world.playEvent(null, 1014, pos, 0);
				}
			}//end open gate
			//close gate
			else
			{
				if (openGate)  //want to open gate
				{
		            EnumFacing enumfacing = EnumFacing.fromAngle((double)host.rotationYaw);

		            if (state.getValue(BlockFenceGate.FACING) == enumfacing.getOpposite())
		            {
		                state = state.withProperty(BlockFenceGate.FACING, enumfacing);
		            }

		            state = state.withProperty(BlockFenceGate.OPEN, Boolean.valueOf(true));
		            host.world.setBlockState(pos, state, 10);
		            host.world.playEvent(null, 1008, pos, 0);
				}
			}//end close gate
		}//end get gate
    }
    
    
}