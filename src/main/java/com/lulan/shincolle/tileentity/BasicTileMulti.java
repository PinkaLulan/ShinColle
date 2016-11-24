package com.lulan.shincolle.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.lulan.shincolle.block.BasicBlockMulti;

/**BASIC MULTI BLOCK TILE ENTITY
 * tut by Lomeli
 * web: https://lomeli12.net/tutorials/tutorial-how-to-make-a-simple-multiblock-structure/
 * 
 * multi-block structure (MBS) type:
 * 0: none
 * 1: large shipyard
 * 
 */
abstract public class BasicTileMulti extends BasicTileInventory
{

	protected BasicTileMulti masterTile = null;
	protected boolean hasMaster, isMaster;			//master flag
	protected String customName;
	protected int structType;						//MBS info
	private BlockPos masterPos = BlockPos.ORIGIN;	//master pos 
	
	
	public BasicTileMulti()
	{
		super();
    }
	
	@Override
	public int getGuiIntID()
	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).getGuiIntID();
		}
		
		return -1;
	}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data)
    {
        super.writeToNBT(data);
        
        data.setInteger("masterX", masterPos.getX());
        data.setInteger("masterY", masterPos.getY());
        data.setInteger("masterZ", masterPos.getZ());
        data.setInteger("structType", structType);
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);
        
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);
        
        masterPos = new BlockPos(data.getInteger("masterX"), data.getInteger("masterY"), data.getInteger("masterZ"));
        structType = data.getInteger("structType");
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");
        
    }

    //getter
    public int getStructType()
    {
    	return this.structType;
    }
    
    public BasicTileMulti getMaster()
    {
    	if (this.masterTile != null)
    	{
    		return this.masterTile;
    	}
    	else
    	{
    		//check master again
    		if (hasMaster)
    		{
    			TileEntity tile = this.worldObj.getTileEntity(this.masterPos);
    			
    			if (tile instanceof BasicTileMulti)
    			{
    				this.setMaster((BasicTileMulti) tile);
            		return this.masterTile;
    			}
            }
    	}
    	
    	return null;
    }
    
    public boolean hasMaster()
    {
        return this.hasMaster;
    }
    
    public boolean isMaster()
    {
        return this.isMaster;
    }
    
    public BlockPos getMasterPos()
    {
        return this.masterPos;
    }

    //setter
    /** set multi-block structure type, NOT blockstate!! */
    public void setStructType(int type, World world)
    {
        //set type
    	this.structType = type;
    	
    	//set blockstate
    	//type: 0:NO mbs, 1:mbs INACTIVE, 2:mbs ACTIVE
    	if (type == 0)
    	{
    		BasicBlockMulti.updateBlockState(0, world, this.getPos());  //NO MBS
    	}
    	else
    	{
    		BasicBlockMulti.updateBlockState(1, world, this.getPos());  //INACTIVE
    	}
    }
    
    /** set mater tile, separate from setHasMaster */
    public void setMaster(BasicTileMulti master)
    {
    	if (master != null && !master.isInvalid())
    	{
    		this.masterTile = master;
    		this.setMasterCoords(master.getPos());
    	}
    	else
    	{
    		this.masterTile = null;
    	}
    }
    
    /** set master flag, separate from setMaster due to tile loading order problem */
    public void setHasMaster(boolean par1)
    {
    	this.hasMaster = par1;
    }
    
    public void setIsMaster(boolean par1)
    {
    	this.isMaster = par1;
    }
    
    public void setMasterCoords(BlockPos pos)
    {
    	this.masterPos = pos;
    }
    
    
}
