package com.lulan.shincolle.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.ai.path.ShipPathEntity;
import com.lulan.shincolle.ai.path.ShipPathPoint;
import com.lulan.shincolle.entity.IShipNavigator;
import com.lulan.shincolle.utility.LogHelper;

/** ship open door AI
 * 
 *
 */
public class EntityAIShipOpenDoor extends EntityAIBase {
	
	private Entity host;
	private IShipNavigator host2;
	private int doorX;
	private int doorY;
	private int doorZ;
	private BlockDoor door;
	private BlockFenceGate gate;
	private boolean hasPassed;  //true時表示已經通過門，可準備結束AI
	private float vecX;
	private float vecZ;
	private boolean closeDoor;		//通過門後是否要隨手關門
	private int delay;				//通過門的delay, 時間到後關門

    
    public EntityAIShipOpenDoor(IShipNavigator host, boolean closeDoor) {
        this.host = (Entity) host;
        this.host2 = host;
        this.closeDoor = closeDoor;
        
    }
    
    @Override
    public boolean shouldExecute() {
    	//水平方向撞到東西才啟動AI
        if(!this.host.isCollidedHorizontally)
        {
            return false;
        }
        else
        {
            ShipPathEntity path = this.host2.getShipNavigate().getPath();
            
            //有尚未完成的移動路徑
            if (path != null && !path.isFinished())
            {
            	//找路徑中接下來兩點, 偵測有無碰到門
                for (int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
                {
                    ShipPathPoint pp = path.getPathPointFromIndex(i);
                    this.doorX = pp.xCoord;
                    this.doorY = pp.yCoord + 1;  //抓高一格的門, 才能確定身高2格以內可以通過
                    this.doorZ = pp.zCoord;

                    //若路徑點已經在1.5格內
                    if (this.host.getDistanceSq((double)this.doorX, this.host.posY, (double)this.doorZ) <= 2.25D)
                    {
                    	//get door
                        this.door = this.getDoor(this.doorX, this.doorY, this.doorZ);

                        if (this.door != null)
                        {
                            return true;
                        }
                        //get gate
                        else
                        {
                        	this.gate = this.getGate(this.doorX, this.doorY - 1, this.doorZ);
                        	
                        	if (this.gate != null)
                        	{
                        		return true;
                        	}
                        }
                    }
                }

                //若路徑點沒有門, 則偵測host是否本身卡在門方塊中
                this.doorX = MathHelper.floor_double(this.host.posX);
                this.doorY = MathHelper.floor_double(this.host.posY + 1D);
                this.doorZ = MathHelper.floor_double(this.host.posZ);
                this.door = this.getDoor(this.doorX, this.doorY, this.doorZ);
                
                //get door
                if (this.door != null)
                {
                	return true;
                }
                else
                {
                	//get gate
                	this.gate = this.getGate(this.doorX, this.doorY - 1, this.doorZ);
                	
                	return this.gate != null;
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
    public boolean continueExecuting() {
    	//若需要關門, 則等待delay時間到
        return this.closeDoor && this.delay > 0 && !this.hasPassed;
    }

    @Override
    public void startExecuting()
    {
        this.delay = 20;
        this.hasPassed = false;
        this.vecX = (float)((double)((float)this.doorX + 0.5F) - this.host.posX);
        this.vecZ = (float)((double)((float)this.doorZ + 0.5F) - this.host.posZ);
        
        //更改door meta值為開門
        if (this.door != null)
        {
        	this.door.func_150014_a(this.host.worldObj, this.doorX, this.doorY, this.doorZ, true);
        }
        else if (this.gate != null)
        {
        	this.doorY -= 1;
        	int meta = this.host.worldObj.getBlockMetadata(this.doorX, this.doorY, this.doorZ);
        	
        	if(!this.gate.isFenceGateOpen(meta)) {
        		activateGate();
        	}
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
                this.door.func_150014_a(this.host.worldObj, this.doorX, this.doorY, this.doorZ, false);
        	}
        	else if (this.gate != null)
        	{
        		activateGate();
        	}
        }
    }

    @Override
    public void updateTask()
    {
        --this.delay;
        
        //計算host位置vector
        float vx = (float)((this.doorX + 0.5D) - this.host.posX);
        float vz = (float)((this.doorZ + 0.5D) - this.host.posZ);
        float v = this.vecX * vx + this.vecZ * vz;

        //若host已經通過門, 則vec相乘後會小於0, 表示可以關門
        if (v < 0F)
        {
            this.hasPassed = true;
        }
    }
    
    private BlockDoor getDoor(int x, int y, int z)
    {
        Block block = this.host.worldObj.getBlock(x, y, z);
        return block != Blocks.wooden_door ? null : (BlockDoor) block;
    }
    
    private BlockFenceGate getGate(int x, int y, int z)
    {
        Block block = this.host.worldObj.getBlock(x, y, z);
        return block != Blocks.fence_gate ? null : (BlockFenceGate) block;
    }
    
    private void activateGate() {
    	int meta = this.host.worldObj.getBlockMetadata(this.doorX, this.doorY, this.doorZ);

    	//is open -> close
        if (this.gate.isFenceGateOpen(meta))
        {
        	this.host.worldObj.setBlockMetadataWithNotify(this.doorX, this.doorY, this.doorZ, meta & -5, 2);
        }
        //is close -> open
        else
        {
            int j1 = (MathHelper.floor_double((double)(this.host.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
            int k1 = this.gate.getDirection(meta);

            if (k1 == (j1 + 2) % 4)
            {
            	meta = j1;
            }

            this.host.worldObj.setBlockMetadataWithNotify(this.doorX, this.doorY, this.doorZ, meta | 4, 2);
        }

        //play sound
        this.host.worldObj.playAuxSFXAtEntity(null, 1003, this.doorX, this.doorY, this.doorZ, 0);
    }
    
    
}
