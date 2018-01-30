package com.lulan.shincolle.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.proxy.ClientProxy;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFrame extends BasicBlockFacing
{
	
	public static final String NAME = "BlockFrame";
	protected static final AxisAlignedBB AABB_Frame = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

	
	public BlockFrame()
	{
		super(Material.GLASS);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(0.1F);
	    this.setResistance(40F);
	    this.setLightOpacity(0);
	    this.setTickRandomly(false);
	    this.setSoundType(SoundType.METAL);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), this.getRegistryName());
        
        //default state
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World w, BlockPos pos, Random rand) {}

	@Override
	public boolean getTickRandomly()
	{
        return false;
    }
	
	/** 設定AABB */
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_Frame);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	//用於檢查生物是否可以碰撞此方塊以及視線是否會被阻擋
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
	}
	
	/** ladder movement */
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		//if client player
		if (world.isRemote)
		{
			if (entity.equals(ClientProxy.getClientPlayer()) || entity.equals(ClientProxy.getClientPlayer().getRidingEntity()))
			{
				GameSettings keySet = ClientProxy.getGameSetting();
				
				if (keySet.keyBindForward.isKeyDown() ||
					keySet.keyBindBack.isKeyDown() ||
					keySet.keyBindLeft.isKeyDown() ||
					keySet.keyBindRight.isKeyDown())
				{
					ClientProxy.getClientPlayer().addVelocity(0D, 0.4D, 0D);
					if (ClientProxy.getClientPlayer().getRidingEntity() != null) ClientProxy.getClientPlayer().getRidingEntity().addVelocity(0D, 0.4D, 0D);
				}
			}
		}
		
		//最低下降速度
		if (entity.motionY < -0.1D)
		{
			entity.motionY = -0.1D;
		}
		
		//最高上升速度
		else if (entity.motionY > 0.4D)
		{
			entity.motionY = 0.4D;
		}
		
		//蹲下時速度
		if (entity.isSneaking())
		{
			entity.motionY = 0.08D;
		}
		
		//重設墜落距離
		entity.fallDistance = 0F;
	}
	
	/** random facing on placed */
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		//只考慮水平四個方向
//		world.setBlockState(pos, state.withProperty(FACING, EnumFacing.getHorizontal(rand.nextInt(4))), 2);
		//依照人物面向調整
		world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
	}
	
	//cancel water block side rendering
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
		IBlockState sideState = world.getBlockState(pos.offset(face));
		
		if (sideState != null && sideState.getMaterial() != null && sideState.getMaterial().isLiquid())
		{
			return true;
		}
		
        return state.isOpaqueCube();
    }
	
	
}