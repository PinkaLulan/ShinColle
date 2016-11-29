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
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
	private int[] tileChest;  //tile position
	private int[][] tilePoint;
	private int pointID;
	
	
	public TargetWrench()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		this.setFull3D();
        
        GameRegistry.register(this);
		
		this.tileChest = new int[] {-1, -1, -1};
		this.tilePoint = new int[][] {{-1, -1, -1}, {-1, -1, -1}};
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
//		if (!world.isRemote) TODO complete crane block
//		{
//			if (player != null && player.isSneaking())
//			{
//				TileEntity tile = world.getTileEntity(pos);
//				
//				if (tile instanceof TileEntityCrane)
//				{
//					this.tilePoint[this.pointID] = new int[] {x, y, z};
//					this.pointID = changePoint(this.pointID);
//					
//					if(!pairCrane(world)) {
//						return setWaypoint(world);
//					}
//					
//					return true;
//				}
//				else if(tile instanceof IInventory) {
//					this.tileChest = new int[] {x, y, z};
//					
//					return pairCrane(world);
//				}
//				else if(tile instanceof ITileWaypoint) {
//					this.tilePoint[this.pointID] = new int[] {x, y, z};
//					this.pointID = changePoint(this.pointID);
//					
//					return setWaypoint(world);
//				}
//				else {
//					//fail msg
//	            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
//	            			new ChatComponentText(EnumChatFormatting.YELLOW+
//	            			StatCollector.translateToLocal("chat.shincolle:wrench.wrongtile")));
//				}
//			}
//		}
////		//client side
////		else {
////			if(!player.isSneaking()) {
////				//show waypoint msg
////				TileEntity te = world.getTileEntity(x, y, z);
////				
////				if(te instanceof ITileWaypoint) {
////					int[] last = ((ITileWaypoint)te).getLastWaypoint();
////					int[] next = ((ITileWaypoint)te).getNextWaypoint();
////					
////					player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA+StatCollector.translateToLocal("chat.shincolle:wrench.wplast")+" "+
////							EnumChatFormatting.YELLOW+last[0]+" "+last[1]+" "+last[2]+"  "+
////							EnumChatFormatting.AQUA+StatCollector.translateToLocal("chat.shincolle:wrench.wpnext")+" "+
////							EnumChatFormatting.GOLD+next[0]+" "+next[1]+" "+next[2]));
////				}
////			}
////		}
		
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
	private int changePoint(int par1)
	{
		return par1 == 0 ? 1 : 0;
	}
	
	private void resetPos()
	{
		this.tileChest = new int[] {-1, -1, -1};
		this.tilePoint = new int[][] {{-1, -1, -1}, {-1, -1, -1}};
		this.pointID = 0;
	}
	
	//waypoint setting
	private boolean setWaypoint(World world)
	{
		try
		{
			//if y position > 0
			if (this.tilePoint != null && this.tilePoint[0][1] > 0 && this.tilePoint[1][1] > 0)
			{
				//calc distance
				int dx = this.tilePoint[0][0] - this.tilePoint[1][0];
				int dy = this.tilePoint[0][1] - this.tilePoint[1][1];
				int dz = this.tilePoint[0][2] - this.tilePoint[1][2];
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
					TileEntity tile1 = world.getTileEntity(new BlockPos(tilePoint[pointID][0], tilePoint[pointID][1], tilePoint[pointID][2]));
					this.pointID = changePoint(this.pointID);
					TileEntity tile2 = world.getTileEntity(new BlockPos(tilePoint[pointID][0], tilePoint[pointID][1], tilePoint[pointID][2]));
					
					if (tile1 instanceof ITileWaypoint && tile2 instanceof ITileWaypoint)
					{
						ITileWaypoint wpFrom = (ITileWaypoint) tile1;
						ITileWaypoint wpTo = (ITileWaypoint) tile2;
						
						//get tile position
						int[] posT = new int[] {tilePoint[pointID][0], tilePoint[pointID][1], tilePoint[pointID][2]};
						this.pointID = changePoint(this.pointID);
						int[] posF = new int[] {tilePoint[pointID][0], tilePoint[pointID][1], tilePoint[pointID][2]};
						int [] nextWpTo = wpTo.getNextWaypoint();
						
						//set waypoint
						wpFrom.setNextWaypoint(posT);
						
						if (nextWpTo[0] != posF[0] || nextWpTo[1] != posF[1] || nextWpTo[2] != posF[2])
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
								.appendText(" " + TextFormatting.GREEN + posF[0] + " " + posF[1] + " " + posF[2] + 
											TextFormatting.AQUA + " --> " + TextFormatting.GOLD +
											posT[0] + " " + posT[1] + " " + posT[2]));
						
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
			LogHelper.info("EXCEPTION : set waypoint fail: "+e);
			return false;
		}
		
		return false;
	}
	
	//crane pairing
	private boolean pairCrane(World world)
	{
//		try TODO complete crane block
//		{
//			//no chest
//			if (tileChest[1] <= 0) return false;
//			
//			TileEntity tile1 = world.getTileEntity(new BlockPos(tileChest[0], tileChest[1], tileChest[2]));
//			this.pointID = changePoint(this.pointID);
//			TileEntity tile2 = world.getTileEntity(new BlockPos(tilePoint[pointID][0], tilePoint[pointID][1], tilePoint[pointID][2]));
//			
//			//check is chest and crane
//			if (tile1 instanceof IInventory && tile2 instanceof TileEntityCrane)
//			{
//				//calc distance
//				int dx = tileChest[0] - tile2.xCoord;
//				int dy = tileChest[1] - tile2.yCoord;
//				int dz = tileChest[2] - tile2.zCoord;
//				dx = dx * dx;
//				dy = dy * dy;
//				dz = dz * dz;
//				int dist = dx + dy + dz;
//				
//				//same tile, reset
//				if (dx == 0 && dy == 0 && dz == 0)
//				{
//	            	resetPos();
//	            	return false;
//				}
//				
//				//check dist < ~6 blocks
//				if (dist <= 40)
//				{
//					((TileEntityCrane)tile2).setPairedChest(tileChest[0], tileChest[1], tileChest[2]);
//					
//					//success msg
//	            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
//	            			new ChatComponentText(EnumChatFormatting.AQUA+
//	            			StatCollector.translateToLocal("chat.shincolle:wrench.paired")+" "+
//	            			EnumChatFormatting.GREEN+tileChest[0]+" "+tileChest[1]+" "+tileChest[2]+
//	            			EnumChatFormatting.AQUA+" & "+
//	            			EnumChatFormatting.GOLD+tile2.xCoord+" "+tile2.yCoord+" "+tile2.zCoord));
//	            	
//	            	//sync
//	            	((TileEntityCrane)tile2).sendSyncPacket();
//	            	
//	            	//reset
//	            	resetPos();
//					
//	            	return true;
//				}
//				else
//				{
//					//too far away msg
//	            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
//	            			new ChatComponentText(EnumChatFormatting.YELLOW+
//	            			StatCollector.translateToLocal("chat.shincolle:wrench.toofar")));
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			//...
//			return false;
//		}
		
		return false;
	}
	

}
