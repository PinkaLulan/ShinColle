package com.lulan.shincolle.client.render.item;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/** tile entity itemblock renderer
 *
 *  ref: http://www.minecraftforge.net/forum/index.php?topic=28643.0
 *       [1.8] Custom model block rendering in inventory?
 *       
 *  
 */
public class RenderTileEntityItem extends TileEntityItemStackRenderer
{
	
	private static TileEntity SmallShipyard = new TileEntitySmallShipyard();
	private static TileEntity Desk = new TileEntityDesk();
	
	
    @Override
    public void renderByItem(ItemStack itemStack)
    {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        
        //mod特製模型的tile entity
        if (block == ModBlocks.BlockSmallShipyard)
        {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(SmallShipyard, 0D, 0D, 0D, 0F);
        }
        else if (block == ModBlocks.BlockDesk)
        {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(Desk, 0D, 0D, 0D, 0F);
        }
        //其他mc的tile entity
        else
        {
            super.renderByItem(itemStack);
        }
    }
    
    
}
