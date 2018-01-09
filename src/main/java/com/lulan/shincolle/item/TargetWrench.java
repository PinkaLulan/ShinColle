package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.tileentity.ITileWaypoint;
import com.lulan.shincolle.tileentity.TileEntityCrane;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/** target selector for OP only
 * 
 *  left click: add/remove attackable target
 *  right click: show target list
 */
public class TargetWrench extends BasicItem
{
	
	private static final String NAME = "TargetWrench";
	private BlockPos[] tilePoint;
	private int pointID;
	
	
	public TargetWrench()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		this.setFull3D();
        
        GameRegistry.register(this);
		
		this.tilePoint = new BlockPos[] {BlockPos.ORIGIN, BlockPos.ORIGIN};
		this.pointID = 0;
	}
	
	/**
	 * right click on block
	 * sneaking: pair Chest, Crane and Waypoint
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player != null)
		{
			//client side
			if (world.isRemote)
			{
				//sneaking
				if (player.isSneaking())
				{
					TileEntity tile = world.getTileEntity(pos);
					
					if (tile instanceof TileEntityCrane ||
						tile instanceof IInventory ||
						tile instanceof ITileWaypoint)
					{
						this.tilePoint[this.pointID] = pos;
						this.switchPoint();
						this.setPair(player);
						
						return EnumActionResult.FAIL;	//return fail to prevent item swing
					}
					else
					{
						//fail msg
						player.sendMessage(new TextComponentTranslation("chat.shincolle:wrench.wrongtile"));
						
						//clear data
						resetPos();
					}
				}
			}//end client side
		}//end get player
		
		return EnumActionResult.PASS;
    }
	
	/**
	 * left click on entity to get morph
	 */
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if (CommonProxy.activeMetamorph)
		{
			MetamorphHelper.getShipMorph(player, entity);
			return true;
		}
		
        return false;
    }
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{
    	list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:wrench3"));
	}
	
	// 0 <-> 1
	private void switchPoint()
	{
		this.pointID = this.pointID == 0 ? 1 : 0;
	}
	
	private void resetPos()
	{
		this.tilePoint = new BlockPos[] {BlockPos.ORIGIN, BlockPos.ORIGIN};
		this.pointID = 0;
	}

	//crane pairing, CLIENT SIDE ONLY
	private boolean setPair(EntityPlayer player)
	{
		//valid point position
		if (tilePoint[0].getY() <= 0 || tilePoint[1].getY() <= 0) return false;
		
		//get player UID
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		int uid = 0;
		if (capa != null) uid = capa.getPlayerUID();
		if (uid <= 0) return false;
		
		//get tile
		TileEntity[] tiles = new TileEntity[2];
		tiles[0] = player.world.getTileEntity(tilePoint[0]);
		tiles[1] = player.world.getTileEntity(tilePoint[1]);
		
		//calc distance
		int dx = this.tilePoint[0].getX() - this.tilePoint[1].getX();
		int dy = this.tilePoint[0].getY() - this.tilePoint[1].getY();
		int dz = this.tilePoint[0].getZ() - this.tilePoint[1].getZ();
		int dist = dx * dx + dy * dy + dz * dz;
		
		//is same point
		if (dx == 0 && dy == 0 && dz == 0)
		{
			player.sendMessage(new TextComponentTranslation("chat.shincolle:wrench.samepoint"));
			
			//clear data
			resetPos();
			
			return false;
		}

		//chest and waypoint pairing
		if (tiles[0] instanceof IInventory && !(tiles[0] instanceof ITileWaypoint) && tiles[1] instanceof ITileWaypoint ||
			tiles[1] instanceof IInventory && !(tiles[1] instanceof ITileWaypoint) && tiles[0] instanceof ITileWaypoint)
		{
			//check dist < ~6 blocks
			if (dist < 40)
			{
				BlockPos wpPos = null;
				BlockPos chestPos = null;
				
				//set chest pair
				if (tiles[0] instanceof ITileWaypoint)
				{
					wpPos = tilePoint[0];
					chestPos = tilePoint[1];
				}
				else
				{
					wpPos = tilePoint[1];
					chestPos = tilePoint[0];
				}
				
				//send pairing request packet
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_ChestSet,
					uid, wpPos.getX(), wpPos.getY(), wpPos.getZ(),
					chestPos.getX(), chestPos.getY(), chestPos.getZ()));
				
				//clear data
				resetPos();
				
				return true;
			}
			//send too far away msg
			else
			{
				TextComponentTranslation str = new TextComponentTranslation("chat.shincolle:wrench.toofar");
				str.getStyle().setColor(TextFormatting.YELLOW);
				player.sendMessage(str);
				
				//clear data
				resetPos();
				
				return false;
			}
		}
		//waypoint pairing
		else if (tiles[0] instanceof ITileWaypoint && tiles[1] instanceof ITileWaypoint)
		{
			//dist < 48 blocks
			if (dist < 2304)
			{
				//get waypoint order
				ITileWaypoint wpFrom = (ITileWaypoint) tiles[this.pointID];
				BlockPos posF = tilePoint[this.pointID];
				this.switchPoint();
				ITileWaypoint wpTo = (ITileWaypoint) tiles[this.pointID];
				BlockPos posT = tilePoint[this.pointID];
				
				//send pairing request packet
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_WpSet,
					uid, posF.getX(), posF.getY(), posF.getZ(), posT.getX(), posT.getY(), posT.getZ()));
				
				//clear data
				resetPos();
				
				return true;
			}
			//send too far away msg
			else
			{
				TextComponentTranslation str = new TextComponentTranslation("chat.shincolle:wrench.wptoofar");
				str.getStyle().setColor(TextFormatting.YELLOW);
				player.sendMessage(str);
				
				//clear data
				resetPos();
				
				return false;
			}
		}
		else
		{
			TextComponentTranslation str = new TextComponentTranslation("chat.shincolle:wrench.wrongtile");
			str.getStyle().setColor(TextFormatting.YELLOW);
			player.sendMessage(str);
			
			//clear data
			resetPos();
			
			return false;
		}
	}
	

}