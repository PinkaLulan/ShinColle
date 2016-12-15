package com.lulan.shincolle.block;

import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.item.TargetWrench;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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
	private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0D, 0D, 0D, 0D, 0D, 0D);
	
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
    public MapColor getMapColor(IBlockState state)
    {
        return MapColor.IRON;
    }
	
	//用於pathing AI檢查是否卡到方塊
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {}
	
	@Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return false;
    }
	
	//用於檢查火把植物之類是否可以貼在方塊上
	@Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
    	return false;
    }
	
	//用於檢查鄰接的方塊是否要畫出以及視線上是否擋住後面方塊 (CULLING)
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
        return false;
    }
	
	//用於pathing AI檢查是否卡到方塊
	@Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return true;
    }
	
	//用於檢查是否跟草一樣可以被其他方塊蓋掉
	@Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
	
	//用於檢查生物是否可以生成
	@Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }
	
	//用於檢查生物是否可以碰撞此方塊
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	//使玩家可以點到方塊, 用於ray trace等方法
	@Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
    {
        return true;
    }
    
	@Override
    public boolean isCollidable()
    {
        return true;
    }
	
	//樹木生成時是否會被樹葉擠掉
	@Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
    	return false;
    }
	
	//透光度 0 = 全透明, 255 = 完全不透光
	@Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos)
    {
    	return 0;
    }
    
	//if side is liquid, return true
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
		Material mat = world.getBlockState(pos.offset(face)).getMaterial();
		
		if (mat == Material.WATER || mat == Material.LAVA)
		{
			return true;
		}
		
        return false;
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

