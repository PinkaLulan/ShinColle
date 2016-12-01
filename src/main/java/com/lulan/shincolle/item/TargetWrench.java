package com.lulan.shincolle.item;

import java.util.HashMap;
import java.util.List;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.tileentity.BasicTileLockable;
import com.lulan.shincolle.tileentity.ITileWaypoint;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** target selector for OP only
 * 
 *  left click: add/remove attackable target
 *  right click: show target list
 */
public class TargetWrench extends BasicItem
{
	
	private static final String NAME = "TargetWrench";
	private BlockPos tileChest;  //tile position
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
		
		this.tileChest = BlockPos.ORIGIN;
		this.tilePoint = new BlockPos[] {BlockPos.ORIGIN, BlockPos.ORIGIN};
		this.pointID = 0;
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
	{
        return true;
    }
	
	/** left click: add / remove attackable target
	 * 
	 *  excluding BasicEntityShip and BasicEntityShipHostile
	 *  
	 *  process:
	 *  1. get mouseover entity (client)
	 *  2. send player eid and entity to server (c 2 s)
	 *  3. check player is OP (server)
	 *  4. add/remove entity to list (server)
	 */
	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack item)
	{
		int meta = item.getItemDamage();
		
		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			
			//玩家左鍵使用此武器時 (client side only)
			if (player.worldObj.isRemote)
			{
				RayTraceResult hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
				
				//hit entity
				if (hitObj != null && hitObj.entityHit != null)
				{
					//target != ship
					if (!(hitObj.entityHit instanceof BasicEntityShip ||
						  hitObj.entityHit instanceof BasicEntityShipHostile))
					{
						String tarName = hitObj.entityHit.getClass().getSimpleName();
						LogHelper.info("DEBUG : target wrench get class: "+tarName);
						
						//send packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetUnatkClass, tarName));
						return false;
					}//end not ship
				}//end hit != null
			}//end client side
			else
			{
				if (player.isSneaking())
				{
					HashMap<Integer, String> tarlist = ServerProxy.getUnattackableTargetClass();
					
					player.addChatMessage(new TextComponentString(TextFormatting.RED+"Show unattackable entity list:"));
					
					tarlist.forEach((k, v) ->
					{
						player.addChatMessage(new TextComponentString(TextFormatting.AQUA+v));
					});
					
					return true;
				}
			}
		}//end player not null
		
        return false;	//both side
    }
	
	/** right click on block
	 *  sneaking: pair Chest and Crane
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{	//server side
		if (!world.isRemote)
		{
			if (player != null && player.isSneaking())
			{
				TileEntity tile = world.getTileEntity(pos);
				
				if (tile instanceof TileEntityCrane)
				{
					this.tilePoint[this.pointID] = pos;
					this.switchPoint();
					
					if (!pairCrane(world))
					{
						return setWaypoint(world) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
					}
					
					return EnumActionResult.SUCCESS;
				}
				else if (tile instanceof IInventory)
				{
					this.tileChest = pos;
					
					return pairCrane(world) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
				}
				else if (tile instanceof ITileWaypoint)
				{
					this.tilePoint[this.pointID] = pos;
					this.switchPoint();
					
					return setWaypoint(world) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
				}
				else
				{
					//fail msg
					TextComponentTranslation text = new TextComponentTranslation("chat.shincolle:wrench.wrongtile");
					text.getStyle().setColor(TextFormatting.YELLOW);
					ServerProxy.getServer().addChatMessage(text);
				}
			}
		}
		
		return EnumActionResult.PASS;
    }
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{  		
    	list.add(TextFormatting.RED + I18n.format("gui.shincolle:wrench1"));
    	list.add(TextFormatting.AQUA + I18n.format("gui.shincolle:wrench2"));
    	list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:wrench3"));
	}
	
	// 0 <-> 1
	private void switchPoint()
	{
		this.pointID = this.pointID == 0 ? 1 : 0;
	}
	
	private void resetPos()
	{
		this.tileChest = BlockPos.ORIGIN;
		this.tilePoint = new BlockPos[] {BlockPos.ORIGIN, BlockPos.ORIGIN};
		this.pointID = 0;
	}
	
	//waypoint setting
	private boolean setWaypoint(World world)
	{
		try
		{
			//if y position > 0
			if (this.tilePoint != null && this.tilePoint[0].getY() > 0 && this.tilePoint[1].getY() > 0)
			{
				//calc distance
				int dx = this.tilePoint[0].getX() - this.tilePoint[1].getX();
				int dy = this.tilePoint[0].getY() - this.tilePoint[1].getY();
				int dz = this.tilePoint[0].getZ() - this.tilePoint[1].getZ();
				dx = dx * dx;
				dy = dy * dy;
				dz = dz * dz;
				
				//is same point
				if (dx == 0 && dy == 0 && dz == 0)
				{
					//clear data
					resetPos();
					
					return false;
				}
				
				//dist < 32 blocks
				if (dx + dy + dz < 2304)
				{
					//get waypoint tile
					TileEntity tile1 = world.getTileEntity(tilePoint[pointID]);
					this.switchPoint();
					TileEntity tile2 = world.getTileEntity(tilePoint[pointID]);
					
					if (tile1 instanceof ITileWaypoint && tile2 instanceof ITileWaypoint)
					{
						ITileWaypoint wpFrom = (ITileWaypoint) tile1;
						ITileWaypoint wpTo = (ITileWaypoint) tile2;
						
						//get tile position
						BlockPos posT = tilePoint[pointID];
						this.switchPoint();
						BlockPos posF = tilePoint[pointID];
						BlockPos nextWpTo = wpTo.getNextWaypoint();
						
						//set waypoint
						wpFrom.setNextWaypoint(posT);
						
						if (nextWpTo.getX() != posF.getX() || nextWpTo.getY() != posF.getY() || nextWpTo.getZ() != posF.getZ())
						{
							wpTo.setLastWaypoint(posF);
						}
						
						//sync
						((BasicTileLockable) wpFrom).sendSyncPacket();
						((BasicTileLockable) wpTo).sendSyncPacket();
						
						//clear data
						resetPos();
						
						ServerProxy.getServer().addChatMessage(
								new TextComponentTranslation("chat.shincolle:wrench.setwp")
								.appendText(" " + TextFormatting.GREEN + posF.getX() + " " + posF.getY() + " " + posF.getZ() + 
											TextFormatting.AQUA + " --> " + TextFormatting.GOLD +
											posT.getX() + " " + posT.getY() + " " + posT.getZ()));
						
						return true;
					}
				}
				//send too far away msg
				else
				{
					TextComponentTranslation str = new TextComponentTranslation("chat.shincolle:wrench.wptoofar");
					str.getStyle().setColor(TextFormatting.YELLOW);
					ServerProxy.getServer().addChatMessage(str);
				}
				
				//clear data
				resetPos();
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION : TargetWrench: set waypoint fail: "+e);
			return false;
		}
		
		return false;
	}
	
	//crane pairing
	private boolean pairCrane(World world)
	{
		try
		{
			//no chest
			if (tileChest.getY() <= 0) return false;
			
			TileEntity tile1 = world.getTileEntity(tileChest);
			this.switchPoint();
			TileEntity tile2 = world.getTileEntity(tilePoint[pointID]);
			
			//check is chest and crane
			if (tile1 instanceof IInventory && tile2 instanceof TileEntityCrane)
			{
				//calc distance
				int dx = tileChest.getX() - tile2.getPos().getX();
				int dy = tileChest.getY() - tile2.getPos().getY();
				int dz = tileChest.getZ() - tile2.getPos().getZ();
				dx = dx * dx;
				dy = dy * dy;
				dz = dz * dz;
				int dist = dx + dy + dz;
				
				//same tile, reset
				if (dx == 0 && dy == 0 && dz == 0)
				{
	            	resetPos();
	            	return false;
				}
				
				//check dist < ~6 blocks
				if (dist <= 40)
				{
					((TileEntityCrane) tile2).setPairedChest(tileChest);
					
					//success msg
					TextComponentTranslation text = new TextComponentTranslation("chat.shincolle:wrench.setwp");
					text.getStyle().setColor(TextFormatting.AQUA);
					ServerProxy.getServer().addChatMessage(
							text.appendText(" " + TextFormatting.GREEN +
							tileChest.getX() + " " + tileChest.getY() + " " + tileChest.getZ() +
			            	TextFormatting.AQUA + " & " + TextFormatting.GOLD +
			            	tile2.getPos().getX() + " " + tile2.getPos().getY() + " " + tile2.getPos().getZ()));
					
	            	//sync
	            	((TileEntityCrane) tile2).sendSyncPacket();
	            	
	            	//reset
	            	resetPos();
					
	            	return true;
				}
				else
				{
					//too far away msg
					TextComponentTranslation text = new TextComponentTranslation("chat.shincolle:wrench.toofar");
	            	text.getStyle().setColor(TextFormatting.YELLOW);
	            	ServerProxy.getServer().addChatMessage(text);
				}
			}
		}
		catch (Exception e)
		{
			//...
			return false;
		}
		
		return false;
	}
	

}
