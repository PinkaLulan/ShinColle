package com.lulan.shincolle.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** 增加了面向設定的block
 *
 *  ref: http://modwiki.temporal-reality.com/mw/index.php/Render_Block_Different_faces-1.9
 */
abstract public class BasicBlockFacing extends BasicBlock
{
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	
	public BasicBlockFacing()
	{
		super();
	}

	public BasicBlockFacing(Material material)
	{
		super(material);
	}
	
	public BasicBlockFacing(Material material, MapColor color)
    {
        super(material, color);
    }

	/** 放置block時設定預設面向 */
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		//只考慮水平四個方向
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
//		//全方向
//		world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
    }

	/** 依照placer面向, 設定方塊為面向反向, ex: 人面向北, 方塊面向南 */ //TODO move to entity/block helper?
    public static EnumFacing getFacingFromEntity(BlockPos clickedBlock, EntityLivingBase entity)
    {
        return EnumFacing.getFacingFromVector(
             (float) (entity.posX - clickedBlock.getX()),
             (float) (entity.posY - clickedBlock.getY()),
             (float) (entity.posZ - clickedBlock.getZ()));
    }
    
    /** meta轉state, 注意meta值只有0~15 */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        // all direction: 3 bits for 6 direction
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    /** state轉meta, 注意meta值只有0~15 */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        //all direction
        return state.getValue(FACING).getIndex();
    }

    /** 建立blockstate */
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }
    
	
}
