package com.lulan.shincolle.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.tileentity.BasicTileMulti;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.MulitBlockHelper;

/** basic block of multiblock structure
 * 
 *  hide or texture changed when structure completed
 */
abstract public class BasicBlockMulti extends BasicBlockContainer
{

	/** multi block structure state: 0:NO multi-structure, 1:mbs INACTIVE, 2:mbs ACTIVE */
	public static final PropertyInteger MBS = PropertyInteger.create("mbs", 0, 2);

	
	public BasicBlockMulti()
	{
		super();
	}
	
	public BasicBlockMulti(Material material)
	{
		super(material);
	}
	
	public BasicBlockMulti(Material material, MapColor color)
	{
		super(material, color);
	}
	
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

	/** hide block if meta > 0 (hide mode) */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		if (state.getValue(MBS) > 0)
		{
			return EnumBlockRenderType.INVISIBLE;
		}
		else
		{
			return EnumBlockRenderType.MODEL;
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		if (state.getValue(MBS) > 0)
		{
			return false;
		}
		
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
		if (state.getValue(MBS) > 0)
		{
			return false;
		}
		
        return true;
    }
    
	//update multi-block structure state
	public static void updateBlockState(int mbState, World world, BlockPos pos)
	{
		//check block exists
		IBlockState state = world.getBlockState(pos);
		
		if (state != null && state.getBlock() instanceof BasicBlockMulti)
		{
			//set state
			world.setBlockState(pos, world.getBlockState(pos).withProperty(MBS, mbState), 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(MBS, 0), 2);
	}
	
	/** meta轉state, 注意meta值只有0~15 */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        // MBS: 0:normal model, 1~15:change mbs model
        return getDefaultState().withProperty(MBS, meta > 2 ? 0 : meta);
    }

    /** state轉meta, 注意meta值只有0~15 */
    @Override
    public int getMetaFromState(IBlockState state)
    {
    	// MBS: 0:normal model, 1~15:change mbs model
    	int meta = state.getValue(MBS);
    	
        return meta > 2 ? 0 : meta;
    }
    
    /** 建立blockstate */
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { MBS });
    }
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tile = world.getTileEntity(pos);

		//原本形成結構, 則解除之
		if(!world.isRemote && tile instanceof BasicTileMulti)
		{
			BasicTileMulti tile2 = (BasicTileMulti) tile;
			
			if (tile2.hasMaster())
			{
				MulitBlockHelper.resetStructure(world, tile2.getMasterPos().getX(), tile2.getMasterPos().getY(), tile2.getMasterPos().getZ());
			}
		}

		//呼叫原先的breakBlock, 會把tile entity移除掉
		super.breakBlock(world, pos, state);
	}
	
	//右鍵點到方塊時呼叫此方法
	//參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack item, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		//client端只需要收到true
		if(world.isRemote)
		{
    		return true;
    	}
		//server端偵測是否可以成形
		else if (!player.isSneaking())
		{
			TileEntity te = world.getTileEntity(pos);
			
			if (te instanceof BasicTileMulti)
			{
				BasicTileMulti tile = (BasicTileMulti) te;
				
				//MBS已經成形, 則打開GUI
				if (tile.hasMaster())
				{
					if (tile.getGuiIntID() >= 0)
					{
						LogHelper.infoDebugMode("DEBUG : open multi block GUI");
						
						switch (tile.getStructType())
						{
						case 1:  //large shipyard
							player.openGui(ShinColle.instance, tile.getMaster().getGuiIntID(), world, tile.getMasterPos().getX(), tile.getMasterPos().getY(), tile.getMasterPos().getZ());
							break;
						}
						
						return true;  //已開啟GUI, 回傳true避免手上的東西用出去
					}
				}
				//MBS尚未成形, 檢查MBS是否能成形
				else
				{	//該方塊尚未成形, 檢查是否可以成形
					//MBS 1: heavy grudge -> large shipyard
					if (tile instanceof TileMultiGrudgeHeavy)
					{
						int type = MulitBlockHelper.checkMultiBlockForm(world, pos.getX(), pos.getY(), pos.getZ());
						
						if (type > 0)
						{
							MulitBlockHelper.setupStructure(world, pos.getX(), pos.getY(), pos.getZ(), type);
							LogHelper.info("DEBUG : check multi block form: type "+type);	
							
							return true;
						}				
					}//end tile = heavy grudge
					
				}//end no master
			}//end tile = BasicTileMulti
		}//end server side & player != sneaking

    	return false;	//回傳false則會變成使用手上的物品
    }
	
	
}
