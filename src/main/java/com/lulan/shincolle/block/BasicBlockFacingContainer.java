package com.lulan.shincolle.block;

import com.lulan.shincolle.utility.BlockHelper;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** block with tile and facing
 * 
 */
abstract public class BasicBlockFacingContainer extends BasicBlockFacing implements ITileEntityProvider
{
	
	
	public BasicBlockFacingContainer()
	{
		this(Material.ROCK);
	}
	
	public BasicBlockFacingContainer(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public BasicBlockFacingContainer(Material material, MapColor color)
    {
        super(material, color);
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
	
	//new tile entity instance in child class 
	@Override
	abstract public TileEntity createNewTileEntity(World world, int i);
	
	//can drop items in inventory
	public boolean canDropInventory(IBlockState state)
	{
		return true;
	}
	
	//can send block change when on block break
	public boolean canAlertBlockChange()
	{
		return false;
	}
	
	//打掉方塊後, 掉落其內容物
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
        TileEntity tile = world.getTileEntity(pos);

        //drop item
        if (canDropInventory(state) && tile instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(world, pos, (IInventory) tile);
        }
        
        //alert block change
        if (canAlertBlockChange())
        {
        	world.updateComparatorOutputLevel(pos, this);  //alert block changed
        }

        super.breakBlock(world, pos, state);
	}
	
	/**右鍵點到方塊時呼叫此方法
	 * 參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z
	 */	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return BlockHelper.handleBlockClick(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }


}