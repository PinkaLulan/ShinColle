package com.lulan.shincolle.block;

import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockWaypoint extends BasicItemBlock {

	public ItemBlockWaypoint(Block block) {
		super(block);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
	}
	
	//waypoint can place on water, air, ...etc.
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		//calc ray trace
    	MovingObjectPosition hitobj = EntityHelper.getMouseoverTarget(world, player, 6D, true, false, false);
        
    	if(hitobj != null) {
        	//get block
            if(hitobj.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int x = hitobj.blockX;
                int y = hitobj.blockY;
                int z = hitobj.blockZ;
                
                if(!player.canPlayerEdit(x, y, z, hitobj.sideHit, item)) {
                    return item;
                }

                //tweak block position
                switch(hitobj.sideHit) {
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
                int i1 = this.getMetadata(item.getItemDamage());
                int j1 = this.field_150939_a.onBlockPlaced(world, x, y, z, hitobj.sideHit, 0F, 0F, 0F, i1);

                //place block
                if(placeBlockAt(item, player, world, x, y, z, hitobj.sideHit, 0F, 0F, 0F, j1)) {
                    //play sound
                	world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);

                    //if creative mode, item not consumed
                    if(!player.capabilities.isCreativeMode) {
                        //item -1
                        --item.stackSize;
                    }
                }

                return item;
            }//end get block
        }//end get target
    	
    	return super.onItemRightClick(item, world, player);
	}


}

