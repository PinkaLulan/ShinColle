package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** target selector for OP only
 * 
 *  left click: add/remove attackable target
 *  right click: show target list
 */
public class TargetWrench extends BasicItem {
	
	private int[] tileChest, tileCrane;  //tile position
			
	
	public TargetWrench() {
		super();
		this.setUnlocalizedName("TargetWrench");
		this.maxStackSize = 1;
		this.setFull3D();
		
		this.tileChest = new int[] {-1, -1, -1};
		this.tileCrane = new int[] {-1, -1, -1};
		
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
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
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack item) {
		int meta = item.getItemDamage();
		
		EntityPlayer player = null;
		if(entityLiving instanceof EntityPlayer) {
			player = (EntityPlayer) entityLiving;
			
			//玩家左鍵使用此武器時 (client side only)
			if(entityLiving.worldObj.isRemote) {
				MovingObjectPosition hitObj = EntityHelper.getPlayerMouseOverEntity(64D, 1F);
				
				//hit entity
				if(hitObj != null && hitObj.entityHit != null) {
					//target != ship
					if(!(hitObj.entityHit instanceof BasicEntityShip || hitObj.entityHit instanceof BasicEntityShipHostile)) {
						String tarName = hitObj.entityHit.getClass().getSimpleName();
						LogHelper.info("DEBUG : target wrench get class: "+tarName);
						//send packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetOPTarClass, tarName));
						return false;
					}//end not ship
				}//end hit != null
			}//end client side
		}//end player not null
		
        return false;	//both side
    }
	
	/** right click: show unattackable target list
	 */
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		int meta = item.getItemDamage();
		
		//null check
		if(player == null) return item;
		
		//server side
		if(!world.isRemote) {
			if(!player.isSneaking()) {
				List<String> tarlist = ServerProxy.getUnattackableTargetClassList();
				
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED+"Show unattackable entity list:"));
				
				for(String s : tarlist) {
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA+s));
				}
			}
		}
		
		return item;
    }
	
	/** right click on block
	 *  sneaking: pair Chest and Crane
	 */
	@Override
	public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		//server side
		if(!world.isRemote) {
			if(player != null && player.isSneaking()) {
				TileEntity tile = world.getTileEntity(x, y, z);
				
				if(tile instanceof TileEntityCrane) {
					this.tileCrane = new int[] {x, y, z};
					return pairCrane(world);
				}
				else if(tile instanceof IInventory) {
					this.tileChest = new int[] {x, y, z};
					return pairCrane(world);
				}
				else {
					//fail msg
	            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
	            			new ChatComponentText(EnumChatFormatting.YELLOW+
	            			StatCollector.translateToLocal("chat.shincolle:wrench.wrongtile")));
				}
			}
		}
		
		return false;
    }
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  		
    	list.add(EnumChatFormatting.RED + I18n.format("gui.shincolle:wrench1"));
    	list.add(EnumChatFormatting.AQUA + I18n.format("gui.shincolle:wrench2"));
    	list.add(EnumChatFormatting.YELLOW + I18n.format("gui.shincolle:wrench3"));
	}
	
	private boolean pairCrane(World world) {
		if(this.tileChest != null && this.tileCrane != null) {
			TileEntity tile1 = world.getTileEntity(tileChest[0], tileChest[1], tileChest[2]);
			TileEntity tile2 = world.getTileEntity(tileCrane[0], tileCrane[1], tileCrane[2]);
			
			//check is chest and crane
			if(tile1 instanceof IInventory && tile2 instanceof TileEntityCrane) {
				//check distance < 8D
				int dx = tileChest[0] - tileCrane[0];
				int dy = tileChest[1] - tileCrane[1];
				int dz = tileChest[2] - tileCrane[2];
				dx = dx * dx;
				dy = dy * dy;
				dz = dz * dz;
				int dist = dx + dy + dz;
				
				if(dist <= 64) {
					((TileEntityCrane)tile2).setPairedChest(tileChest[0], tileChest[1], tileChest[2]);
					
					//success msg
	            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
	            			new ChatComponentText(EnumChatFormatting.AQUA+
	            			StatCollector.translateToLocal("chat.shincolle:wrench.paired")+" "+
	            			EnumChatFormatting.GREEN+tileChest[0]+" "+tileChest[1]+" "+tileChest[2]+
	            			EnumChatFormatting.AQUA+" & "+
	            			EnumChatFormatting.GOLD+tileCrane[0]+" "+tileCrane[1]+" "+tileCrane[2]));
	            	
	            	//reset
	            	this.tileChest = new int[] {-1, -1, -1};
					this.tileCrane = new int[] {-1, -1, -1};
					
	            	return true;
				}
				else {
					//too far away msg
	            	ServerProxy.getServer().getConfigurationManager().sendChatMsg(
	            			new ChatComponentText(EnumChatFormatting.YELLOW+
	            			StatCollector.translateToLocal("chat.shincolle:wrench.toofar")));
				}
			}
		}
		
		return false;
	}
	

}
