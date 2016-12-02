package com.lulan.shincolle.block;

import javax.annotation.Nullable;

import com.lulan.shincolle.item.TargetWrench;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockWaypoint extends BasicBlockContainer
{

	public static final String NAME = "BlockWaypoint";
	public static final String TILENAME = "TileEntityWaypoint";
	
	
	public BlockWaypoint()
	{
	    super(Material.GLASS);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHardness(0F);
		this.setLightOpacity(0);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockWaypoint(this), this.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityWaypoint.class, TILENAME);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityWaypoint();
	}
	
	//can drop items in inventory
	@Override
	public boolean canDropInventory(IBlockState state)
	{
		return false;
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
        return false;
    }
	
	//使玩家可以點到方塊, 用於ray trace等方法
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
    {
        return true;
    }
    
	//使entity會穿過方塊
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return NULL_AABB;
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack item, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//server side
		if (!world.isRemote && player != null && !player.isSneaking())
		{
			//change stay time if holding target wrench
			if (item != null && item.getItem() instanceof TargetWrench)
			{
				TileEntity tile = world.getTileEntity(pos);
				
				if (tile instanceof TileEntityWaypoint)
				{
					((TileEntityWaypoint) tile).nextWpStayTime();
					
					player.addChatMessage(new TextComponentString("Change waypoint stay time to: "+
							CalcHelper.tick2SecOrMin(((TileEntityWaypoint) tile).getWpStayTime())));
				}
				
				return true;
			}
		}
				
        return false;
    }
		
		
}

