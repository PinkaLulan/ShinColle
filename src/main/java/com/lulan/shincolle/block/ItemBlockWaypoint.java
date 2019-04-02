package com.lulan.shincolle.block;

import com.lulan.shincolle.utility.EntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBlockWaypoint extends BasicItemBlock
{

	public ItemBlockWaypoint(Block block)
	{
		super(block);
	}
	
	//waypoint can place on water, air, ...etc.
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItemMainhand();

		//server side
		if (!world.isRemote)
		{
			//get ray trace block
	    	RayTraceResult hitobj = EntityHelper.getMouseoverTarget(world, player, 6D, true, false, false);
	        
	    	if (hitobj != null)
	    	{
	        	//get block
	            if (hitobj.typeOfHit == RayTraceResult.Type.BLOCK)
	            {
	            	BlockPos pos = hitobj.getBlockPos();
	                
	                //若player沒有放置方塊權限, 則return pass
	                if (!player.canPlayerEdit(pos, hitobj.sideHit, stack))
	                {
	                    return new ActionResult(EnumActionResult.PASS, stack);
	                }
	                
	                if (!block.isReplaceable(world, pos))
	                {
	                    pos = pos.offset(hitobj.sideHit);
	                }
	                
	                if (stack.getCount() != 0)
	                {
	                    //check metadata
	                    int i = this.getMetadata(stack.getMetadata());
	                    IBlockState state = this.block.getStateForPlacement(world, pos, hitobj.sideHit, 0F, 0F, 0F, i, player, hand);

	                    //place block
	                    if (placeBlockAt(stack, player, world, pos, hitobj.sideHit, 0F, 0F, 0F, state))
	                    {
	                        SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
	                        world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

	                        //if creative mode, item not consumed
	                        if (!player.capabilities.isCreativeMode)
	                        {
	                            stack.shrink(1);
	                        }
	                    }

	                    return new ActionResult(EnumActionResult.SUCCESS, stack);
	                }
	            }//end get block
	            else if (hitobj.typeOfHit == RayTraceResult.Type.ENTITY)
	            {
	            	return new ActionResult(EnumActionResult.FAIL, stack);
	            }
	        }//end get target
		}
    	
    	return super.onItemRightClick(world, player, hand);
	}
	
	
}