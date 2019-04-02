package com.lulan.shincolle.block;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrane extends BasicBlockContainer
{

	public static final String NAME = "BlockCrane";
	public static final String TILENAME = "TileEntityCrane";
	
	
	public BlockCrane()
	{
	    super(Material.IRON);
		this.setTranslationKey(NAME);
		this.setHardness(1F);
		this.setResistance(10F);
	    this.setHarvestLevel("pickaxe", 0);
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
	
	//set owner on placed
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		
		TileEntity tile = world.getTileEntity(pos);
		
		if (!world.isRemote && tile instanceof IShipOwner)
		{
			//set tile ownership
			if (placer instanceof EntityPlayer)
			{
				CapaTeitoku capa = CapaTeitoku.getTeitokuCapability((EntityPlayer) placer);
				if (capa != null) ((IShipOwner) tile).setPlayerUID(capa.getPlayerUID());
			}
		}
	}
	
	@Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
		//check owner
		if (EntityHelper.checkOP(player) || BlockHelper.checkTileOwner(player, world.getTileEntity(pos)))
		{
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		}
		
		return false;
    }
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//sync player UID while right click
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(pos);
			
			if (tile instanceof IShipOwner)
			{
				PacketHelper.sendS2CEntitySync(0, tile, tile.getWorld(), tile.getPos(), null);
			}
		}
		
		return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
	}
	
	
}