package com.lulan.shincolle.block;

import javax.annotation.Nullable;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockVolCore extends BasicBlockContainer
{
	
	public static final String NAME = "BlockVolCore";
	public static final String TILENAME = "TileEntityVolCore";

	
	public BlockVolCore()
	{
	    super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHardness(6F);
		this.setResistance(600F);
	    this.setHarvestLevel("pickaxe", 0);
	    this.setLightLevel(1F);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), this.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityVolCore.class, TILENAME);	    
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityVolCore();
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack item, EnumFacing side, float hitX, float hitY, float hitZ)
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
		
		return super.onBlockActivated(world, pos, state, player, hand, item, side, hitX, hitY, hitZ);
	}
		
		
}
