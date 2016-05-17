package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.lulan.shincolle.item.TargetWrench;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.utility.CalcHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWaypoint extends BasicBlockContainer {

	
	public BlockWaypoint() {
		this(Material.glass);
		this.setBlockName("BlockWaypoint");
	}
	
	public BlockWaypoint(Material mat) {
		super(mat);
		this.setStepSound(soundTypeGlass);
		this.setHardness(0F);
		this.setLightOpacity(0);
	}
	
	@Override
	public int getRenderType() {
        return -1;
    }
	
	//使entity會穿過方塊
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		return null;
    }
	
	@Override
	public boolean isOpaqueCube() {
        return false;
    }
	
	//使玩家可以點到方塊
	@Override
	public boolean canCollideCheck(int par1, boolean par2) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityWaypoint();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitx, float hity, float hitz)
    {
		//server side
		if (!world.isRemote && player != null && !player.isSneaking())
		{
			ItemStack item = player.inventory.getCurrentItem();
			
			//change stay time if holding target wrench
			if (item != null && item.getItem() instanceof TargetWrench)
			{
				TileEntity tile = world.getTileEntity(x, y, z);
				
				if (tile instanceof TileEntityWaypoint)
				{
					((TileEntityWaypoint) tile).nextWpStayTime();
					
					player.addChatMessage(new ChatComponentText("Change waypoint stay time to: "+
							CalcHelper.tick2SecOrMin(((TileEntityWaypoint) tile).getWpStayTime())));
					
				}
				
				return true;
			}
		}
				
        return false;
    }
		
		
}

