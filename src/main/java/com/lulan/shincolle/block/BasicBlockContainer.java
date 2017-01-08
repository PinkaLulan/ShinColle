package com.lulan.shincolle.block;

import javax.annotation.Nullable;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.tileentity.BasicTileEntity;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** block with tile and NO facing
 *  
 */
abstract public class BasicBlockContainer extends BasicBlock implements ITileEntityProvider
{
	
	
	public BasicBlockContainer()
	{
		this(Material.ROCK);
	}
	
	public BasicBlockContainer(Material material)
	{
		this(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}

	public BasicBlockContainer(Material material, MapColor color)
    {
        super(material, color);
        this.isBlockContainer = true;
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
		return true;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack item, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//client端: 只需要收到true
        if (world.isRemote)
        {
            return true;
        }
        
        //server端: 若玩家不是sneaking, 則開啟gui
        if (!player.isSneaking())
        {
        	TileEntity tile = world.getTileEntity(pos);
        	
        	//open gui
        	if (tile instanceof BasicTileEntity && ((BasicTileEntity) tile).getGuiIntID() >= 0)
        	{
        		player.openGui(ShinColle.instance, ((BasicTileEntity) tile).getGuiIntID(), world, pos.getX(), pos.getY(), pos.getZ());
                return true;
        	}
        }

		return false;
    }
	
	
}
