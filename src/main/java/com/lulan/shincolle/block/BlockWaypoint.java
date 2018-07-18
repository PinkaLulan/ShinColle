package com.lulan.shincolle.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
		this.setResistance(0F);
		this.setHardness(0F);
		this.setLightOpacity(0);
		this.setHarvestLevel(null, -1);
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
	public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return MapColor.IRON;
    }
	
	//用於pathing AI檢查是否卡到方塊
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
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
	
	//用於檢查生物是否可以碰撞此方塊以及視線是否會被阻擋
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
		
		if (mat != null && mat.isLiquid())
		{
			return true;
		}
		
        return false;
    }

	//right click on block
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
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
		
		//server side
		if (!world.isRemote && player != null && !player.isSneaking())
		{
			ItemStack item = player.getHeldItem(player.getActiveHand());
			
			//change stay time if holding target wrench
			if (item != null && item.getItem() == ModItems.TargetWrench)
			{
				TileEntity tile = world.getTileEntity(pos);
				
				if (tile instanceof TileEntityWaypoint && BlockHelper.checkTileOwner(player, tile))
				{
					((TileEntityWaypoint) tile).nextWpStayTime();
					
					player.sendMessage(new TextComponentString("Change waypoint stay time to: "+
							CalcHelper.tick2SecOrMin(((TileEntityWaypoint) tile).getWpStayTime())));
					
					return true;
				}
				
				player.sendMessage(new TextComponentTranslation("chat.shincolle:wrongowner")
						.appendText(" "+((TileEntityWaypoint) tile).getPlayerUID()));
			}
		}
		
        return false;
    }
	
	/**
	 * disable normal drop, drop itemstack into player's inventory on block removed
	 */
	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }
	
	@Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
		return;
    }
	
	@Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
    {
    	if (world != null && entity instanceof EntityPlayer)
    	{
    		if (EntityHelper.checkOP((EntityPlayer) entity)) return true;
    		
    		return BlockHelper.checkTileOwner(entity, world.getTileEntity(pos));
    	}
    	
    	return false;
    }
	
	@Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
		//check owner
		if (EntityHelper.checkOP(player) || BlockHelper.checkTileOwner(player, world.getTileEntity(pos)))
		{
			//server side
			if (!world.isRemote)
			{
				ItemStack stack = new ItemStack(Item.getItemFromBlock(this));
				
				//inventory is full, drop item onto ground
				if (!player.inventory.addItemStackToInventory(stack))
				{
		            EntityItem entityitem = new EntityItem(world, player.posX, player.posY + 0.5D, player.posZ, stack);
		            world.spawnEntity(entityitem);
				}
			}
			
			//both side
	        return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
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
	
	//immune to explode
	@Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {}
		
		
}