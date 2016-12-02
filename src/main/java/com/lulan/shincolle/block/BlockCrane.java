package com.lulan.shincolle.block;

import com.lulan.shincolle.tileentity.TileEntityCrane;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockCrane extends BasicBlockContainer
{

	public static final String NAME = "BlockCrane";
	public static final String TILENAME = "TileEntityCrane";
	
	
	public BlockCrane()
	{
	    super(Material.IRON);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHardness(1F);
		this.setResistance(10F);
	    this.setHarvestLevel("pickaxe", 0);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), this.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityCrane.class, TILENAME);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityCrane();
	}
	
	//can drop items in inventory
	@Override
	public boolean canDropInventory(IBlockState state)
	{
		return false;
	}
	
	//can send block change when on block break
	@Override
	public boolean canAlertBlockChange()
	{
		return true;
	}

	//false = 紅石類方塊
	@Override
	public boolean isNormalCube(IBlockState state)
    {
        return false;
    }
	
	//true = 可跟紅石線連接
	@Override
	public boolean canProvidePower(IBlockState state)
    {
        return true;
    }
	
	//get redstone power value for active
	@Override
	public int getStrongPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return this.getWeakPower(state, world, pos, side);
    }
	
	//get redstone power value for inactive
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
		TileEntity tile = world.getTileEntity(pos);
        
        if (tile instanceof TileEntityCrane)
        {
        	TileEntityCrane crane = (TileEntityCrane) tile;
        	if (crane.getRedMode() > 0 && crane.getRedTick() > 0) return 15;
        }
        
        return 0;
    }
	
	
}
