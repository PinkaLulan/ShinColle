package com.lulan.shincolle.ai;

import java.util.ArrayList;
import java.util.List;

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
 *  get door: 只尋找posY +1 格位置的門 (僅適用於身高 < 2的生物)
 *  
 *  get gate: 依據身體大小計算全部會碰到的gate
 *
 */
public class EntityAIShipOpenDoor extends EntityAIBase {
	
	private Entity host;
	private IShipNavigator host2;
	private int doorX;
	private int doorY;
	private int doorZ;
	private BlockDoor door;
	private List<int[]> gates;
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
                    this.doorY = pp.yCoord;  //抓高一格的門, 才能確定身高2格以內可以通過
                    this.doorZ = pp.zCoord;

                    //若路徑點已經在1.5格內
                    if (this.host.getDistanceSq((double)this.doorX, this.host.posY, (double)this.doorZ) <= 2.25D)
                    {
                    	//get door
                        this.door = this.getDoor(this.doorX, this.doorY+1, this.doorZ);

                        if (this.door != null)
                        {
                        	this.doorY += 1;
                            return true;
                        }
                        //get gate
                        else
                        {
                        	//get gates
                        	if (this.getGate(this.doorX + 0.5F, this.doorY, this.doorZ + 0.5F))
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
                	//get gates
                	return this.getGate((float) this.host.posX, (float) this.host.posY, (float) this.host.posZ);
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
        
        //更改door meta值為開門
        if (this.door != null)
        {
        	this.vecX = (float)((double)((float)this.doorX + 0.5F) - this.host.posX);
            this.vecZ = (float)((double)((float)this.doorZ + 0.5F) - this.host.posZ);
        	this.door.func_150014_a(this.host.worldObj, this.doorX, this.doorY, this.doorZ, true);
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
                this.door.func_150014_a(this.host.worldObj, this.doorX, this.doorY, this.doorZ, false);
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
            float vx = (float)((this.doorX + 0.5D) - this.host.posX);
            float vz = (float)((this.doorZ + 0.5D) - this.host.posZ);
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
    
    private BlockDoor getDoor(int x, int y, int z)
    {
        Block block = this.host.worldObj.getBlock(x, y, z);
        return block != Blocks.wooden_door ? null : (BlockDoor) block;
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
    				int gx = MathHelper.floor_float(ix);
    				int gy = (int)iy;
    				int gz = MathHelper.floor_float(iz);
    				Block block = this.host.worldObj.getBlock(gx, gy, gz);
    				
    				if (block instanceof BlockFenceGate)
    				{
    					this.gates.add(new int[] {gx, gy, gz});
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
    	for (int[] gpos : gates)
    	{
    		Block b = this.host.worldObj.getBlock(gpos[0], gpos[1], gpos[2]);
    		
    		if (b instanceof BlockFenceGate)
    		{
    			int meta = this.host.worldObj.getBlockMetadata(gpos[0], gpos[1], gpos[2]);
    			
    			//open gate
    			if (BlockFenceGate.isFenceGateOpen(meta))
    			{
    				if (!openGate)  //want to close gate
    				{
    					host.worldObj.setBlockMetadataWithNotify(gpos[0], gpos[1], gpos[2], meta & -5, 2);
    		            host.worldObj.playAuxSFXAtEntity(null, 1003, gpos[0], gpos[1], gpos[2], 0);  //play sound
    				}
    			}//end open gate
    			//close gate
    			else
    			{
    				if (openGate)  //want to open gate
    				{
    					int j1 = (MathHelper.floor_double((double)(host.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
    	                int k1 = BlockFenceGate.getDirection(meta);

    	                if (k1 == (j1 + 2) % 4)
    	                {
    	                	meta = j1;
    	                }

    	                host.worldObj.setBlockMetadataWithNotify(gpos[0], gpos[1], gpos[2], meta | 4, 2);
    	                host.worldObj.playAuxSFXAtEntity(null, 1003, gpos[0], gpos[1], gpos[2], 0);  //play sound
    				}
    			}//end close gate
    		}//end get gate
    	}//end for all gates
    	
    }

    
}
