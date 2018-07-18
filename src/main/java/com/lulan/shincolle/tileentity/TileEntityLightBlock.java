package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockLightAir;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

/** light source block
 *  
 *  type: 0:air, 1:water source, 2:water flowing
 *
 */
public class TileEntityLightBlock extends BasicTileEntity implements ITickable
{

	public int type;
	public int life;
	public int tick;
	
	
	public TileEntityLightBlock()
	{
		super();
		this.type = 0;
		this.life = 120;
		this.tick = 0;
	}
	
	@Override
	public String getRegName()
	{
		return BlockLightAir.TILENAME;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);	//從nbt讀取方塊的xyz座標
        
        this.type = nbt.getInteger("type");
        this.life = nbt.getInteger("life");
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("type", this.type);
		nbt.setInteger("life", this.life);
		
		return nbt;
	}
	
	@Override
	public void update()
	{
		this.tick++;
		
		if (this.tick > this.life)
		{
			//replace block
			switch (type)
			{
			case 1:   //water source
				this.world.setBlockState(this.pos, Blocks.WATER.getDefaultState(), 1);
			break;
			case 2:   //water flowing
				this.world.setBlockState(this.pos, Blocks.FLOWING_WATER.getDefaultState(), 1);
			break;
			default:  //default air
				this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState(), 1);
			break;
			}
			
			//set dead to tile entity
			this.invalidate();
		}
	}
	
	
}
