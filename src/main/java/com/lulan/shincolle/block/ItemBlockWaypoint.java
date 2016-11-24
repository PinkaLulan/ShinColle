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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		//get ray trace block
    	RayTraceResult hitobj = EntityHelper.getMouseoverTarget(world, player, 6D, true, false, false);
        
    	if (hitobj != null)
    	{
        	//get block
            if (hitobj.typeOfHit == RayTraceResult.Type.BLOCK)
            {
            	BlockPos pos = hitobj.getBlockPos();
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                
                //若player沒有放置方塊權限, 則return pass
                if (!player.canPlayerEdit(pos, hitobj.sideHit, stack))
                {
                    return new ActionResult(EnumActionResult.PASS, stack);
                }

                //tweak block position
                switch (hitobj.sideHit.getIndex())
                {
				case 0:
					y--;
					break;
				case 1:
					y++;
					break;
				case 2:
					z--;
					break;
				case 3:
					z++;
					break;
				case 4:
					x--;
					break;
				case 5:
					x++;
					break;
				}
                
                //check metadata
                pos = new BlockPos(x, y, z);
                int i = this.getMetadata(stack.getMetadata());
                IBlockState state = this.block.onBlockPlaced(world, pos, hitobj.sideHit, 0F, 0F, 0F, i, player);

                //place block
                if (placeBlockAt(stack, player, world, pos, hitobj.sideHit, 0F, 0F, 0F, state))
                {
                    SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
                    world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                    //if creative mode, item not consumed
                    if (!player.capabilities.isCreativeMode)
                    {
                        --stack.stackSize;
                    }
                }

                return new ActionResult(EnumActionResult.SUCCESS, stack);
            }//end get block
        }//end get target
    	
    	return super.onItemRightClick(stack, world, player, hand);
	}
	
	
}