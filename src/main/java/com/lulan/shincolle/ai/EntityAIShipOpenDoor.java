package com.lulan.shincolle.ai;

import java.util.ArrayList;

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

import com.lulan.shincolle.ai.path.ShipPath;
import com.lulan.shincolle.ai.path.ShipPathPoint;
import com.lulan.shincolle.entity.IShipNavigator;

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
	private IShipNavigator host2;
	private BlockPos doorPos = BlockPos.ORIGIN;
	private BlockDoor door;
	private ArrayList<BlockPos> gates;
	private boolean hasPassed;  //true時表示已經通過門，可準備結束AI
	private float vecX;
	private float vecZ;
	private boolean closeDoor;		//通過門後是否要隨手關門
	private int delay;				//通過門的delay, 時間到後關門

    
    public EntityAIShipOpenDoor(IShipNavigator host, boolean closeDoor)
    {
        this.host = (Entity) host;
        this.host2 = host;
        this.closeDoor = closeDoor;
    }
    
    @Override
    public boolean shouldExecute()
    {
    	//水平方向撞到東西才啟動AI
        if (!this.host.isCollidedHorizontally)
        {
            return false;
        }
        else
        {
            ShipPath path = this.host2.getShipNavigate().getPath();
            
            //有尚未完成的移動路徑
            if (path != null && !path.isFinished())
            {
            	//找路徑中接下來兩點, 偵測有無碰到門
                for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
                {
                    ShipPathPoint pp = path.getPathPointFromIndex(i);
                    this.doorPos = new BlockPos(pp.xCoord, pp.yCoord, pp.zCoord);

                    //若路徑點已經在1.5格內
                    if (this.host.getDistanceSq((double)this.doorPos.getX(), this.host.posY, (double)this.doorPos.getZ()) <= 2.25D)
                    {
                    	//get door
                    	BlockPos pos2 = new BlockPos(pp.xCoord, pp.yCoord + 1, pp.zCoord);
                        this.door = this.getDoor(pos2);

                        if (this.door != null)
                        {
                        	this.doorPos = pos2;
                            return true;
                        }
                        //get gate
                        else
                        {
                        	//get gates
                        	if (this.getGate(pp.xCoord + 0.5F, pp.yCoord, pp.zCoord + 0.5F))
                        	{
                        		return true;
                        	}
                        }
                    }
                }

                //若路徑點沒有門, 則偵測host是否本身卡在門方塊中
                this.doorPos = new BlockPos(MathHelper.floor_double(this.host.posX),
                							(int) this.host.posY + 1,
                							MathHelper.floor_double(this.host.posZ));
                this.door = this.getDoor(this.doorPos);
                
                //get door
                if (this.door != null)
                {
                	return true;
                }
                else
                {
                	//get gates
                	return this.getGate((float)this.host.posX, (float)this.host.posY, (float)this.host.posZ);
                }
            }//end has path
            //no path
            else
            {
                return false;
            }
        }
    }

    @Override
    public boolean continueExecuting()
    {
    	//若需要關門, 則等待delay時間到
        return this.closeDoor && this.delay > 0 && !this.hasPassed;
    }

    @Override
    public void startExecuting()
    {
        this.delay = 40;
        this.hasPassed = false;
        
        //更改door meta值為開門
        if (this.door != null)
        {
        	this.vecX = (float) ((double)this.doorPos.getX() + 0.5D - this.host.posX);
            this.vecZ = (float) ((double)this.doorPos.getZ() + 0.5D - this.host.posZ);
        	this.door.toggleDoor(this.host.worldObj, this.doorPos, true);
        }
        else if (this.gates != null && !this.gates.isEmpty())
        {
        	activateGate(true);
        }
    }

    @Override
    public void resetTask()
    {
    	//隨手關門
        if (this.closeDoor)
        {
        	if (this.door != null)
        	{
        		//更改door meta值為關門
                this.door.toggleDoor(this.host.worldObj, this.doorPos, false);
        	}
        	else if (this.gates != null && !this.gates.isEmpty())
        	{
        		activateGate(false);
        	}
        }
    }

    @Override
    public void updateTask()
    {
        --this.delay;
        
        //for door
        if (this.door != null)
        {
        	//計算host位置vector
            float vx = (float) ((double)this.doorPos.getX() + 0.5D - this.host.posX);
            float vz = (float) ((double)this.doorPos.getZ() + 0.5D - this.host.posZ);
            float v = this.vecX * vx + this.vecZ * vz;

            //若host已經通過門, 則vec相乘後會小於0, 表示可以關門
            if (v < 0F)
            {
                this.hasPassed = true;
            }
        }
        //for gate
        else
        {
        	//不管位置, 時間到就設定為通過
        	if (this.delay <= 0) this.hasPassed = true;
        }
        
    }
    
    private BlockDoor getDoor(BlockPos pos)
    {
        IBlockState state = this.host.worldObj.getBlockState(pos);
        Block block = state.getBlock();
        return block instanceof BlockDoor && state.getMaterial() == Material.WOOD ? (BlockDoor)block : null;
    }
    
    //return true = get gate in list
    private boolean getGate(float x, float y, float z)
    {
    	boolean getGate = false;
    	float range = host.width < 1F ? 1F : host.width;
    	this.gates = new ArrayList();
    	
    	//get gate within entity hitbox at x,y,z
    	for (float ix = x - range; ix <= x + range; ix += 1F)
    	{
    		for (float iz = z - range; iz <= z + range; iz += 1F)
    		{
    			for (float iy = y; iy <= y + host.height; iy += 1F)
    			{
    				BlockPos pos = new BlockPos(MathHelper.floor_float(ix), (int)iy, MathHelper.floor_float(iz));
    				IBlockState block = this.host.worldObj.getBlockState(pos);
    				
    				if (block.getBlock() instanceof BlockFenceGate)
    				{
    					this.gates.add(pos);
    					getGate = true;
    				}
            	}
        	}
    	}
    	
        return getGate;
    }
    
    //activate all gate in list to open or close
    private void activateGate(boolean openGate)
    {
    	//loop all gates
    	for (BlockPos pos : gates)
    	{
    		IBlockState state = this.host.worldObj.getBlockState(pos);
    		
    		if (state.getBlock() instanceof BlockFenceGate)
    		{
    			//open gate
    			if (((Boolean)state.getValue(BlockFenceGate.OPEN)).booleanValue())
    			{
    				if (!openGate)  //want to close gate
    				{
    		            state = state.withProperty(BlockFenceGate.OPEN, Boolean.valueOf(false));
    		            host.worldObj.setBlockState(pos, state, 10);
    		            host.worldObj.playEvent(null, 1014, pos, 0);
    				}
    			}//end open gate
    			//close gate
    			else
    			{
    				if (openGate)  //want to open gate
    				{
    		            EnumFacing enumfacing = EnumFacing.fromAngle((double)this.host.rotationYaw);

    		            if (state.getValue(BlockFenceGate.FACING) == enumfacing.getOpposite())
    		            {
    		                state = state.withProperty(BlockFenceGate.FACING, enumfacing);
    		            }

    		            state = state.withProperty(BlockFenceGate.OPEN, Boolean.valueOf(true));
    		            host.worldObj.setBlockState(pos, state, 10);
    		            host.worldObj.playEvent(null, 1008, pos, 0);
    				}
    			}//end close gate
    		}//end get gate
    	}//end for all gates
    	
    }
    
    
}
