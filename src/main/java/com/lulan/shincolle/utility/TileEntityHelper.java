package com.lulan.shincolle.utility;

import com.lulan.shincolle.tileentity.BasicTileInventory;
import com.lulan.shincolle.tileentity.ITileFurnace;
import com.lulan.shincolle.tileentity.ITileLiquidFurnace;
import com.lulan.shincolle.tileentity.ITileWaypoint;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityHelper
{
	
	
	public TileEntityHelper() {}
	
	/** consume fuel item for ITileFurnace, return true = add fuel success */
	public static boolean decrItemFuel(BasicTileInventory tile)
	{
		ITileFurnace tile2 = (ITileFurnace) tile;
		ItemStack stack = null;
		boolean sendUpdate = false;
		
		//check power storage first
		if (tile2.getPowerRemained() >= tile2.getPowerMax()) return false;
		
		//get item in slot
		for (int i = 0; i < tile.getSizeInventory(); i++)
		{
			stack = tile.getStackInSlot(i);
			
			if (stack != null)
			{
				//get normal fuel value
				int fuelx = TileEntityFurnace.getItemBurnTime(stack);
				
				//calc config setting
				if (tile instanceof ITileFurnace)
				{
					fuelx *= ((ITileFurnace) tile).getFuelMagni();
				}
				
				//check power storage and add disposable fuel (ex: coal, lava bucket, lava cell)
				if (fuelx > 0 && fuelx + tile2.getPowerRemained() < tile2.getPowerMax())
				{
					ItemStack container = stack.getItem().getContainerItem(stack);
					
					//if item has container, ignore item with stackSize > 1
					if (container != null && stack.stackSize > 1)
					{
						continue;
					}
					
					stack.stackSize--;	//fuel size -1
					tile2.setPowerRemained(tile2.getPowerRemained() + fuelx);	//fuel++
					
					//若該物品用完, 用getContainerItem處理是否要清空還是留下桶子 ex: lava bucket -> empty bucket
					if (stack.stackSize <= 0)
					{
						stack = container;
					}
					
					//update slot
					tile.setInventorySlotContents(i, stack);
					sendUpdate = true;
					break;	//only consume 1 fuel per tick
				}//end disposable fuel
				
				//add disposable fuel FAILED, try fluid container (ex: drum, universal cell)
				if (stack.getItem() instanceof IFluidContainerItem)
				{
					IFluidContainerItem container = (IFluidContainerItem) stack.getItem();
					FluidStack fluid = container.getFluid(stack);
					
					//is lava and stack size = 1
					if (stack.stackSize > 1)
					{
						fuelx = 0;
					}
					else if (checkLiquidIsLava1000(fluid))
					{
						fuelx = 20000;
						
						//calc config setting
						if (tile instanceof ITileFurnace)
						{
							fuelx *= ((ITileFurnace) tile).getFuelMagni();
						}
						
						if (fuelx > 0 && fuelx + tile2.getPowerRemained() < tile2.getPowerMax())
						{
							//drain lava, capacity is checked in checkLiquidIsLava(), no check again
							container.drain(stack, 1000, true);
							//fuel++
							tile2.setPowerRemained(tile2.getPowerRemained() + fuelx);
							//update slot
							tile.setInventorySlotContents(i, stack);
							sendUpdate = true;
							break;	//only consume 1 fuel per tick
						}
					}//has enough lava
				}//end fluid container fuel
				
				//check capability (1.10.2 new)
				if (fuelx <= 0)
				{
					if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
					{
						//check fluid type
						IFluidHandler fluid = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
						FluidStack fstack = fluid.drain(BlockHelper.SampleFluidLava, false);
						
						fuelx = 20000;
						
						//calc config setting
						if (tile instanceof ITileFurnace)
						{
							fuelx *= ((ITileFurnace) tile).getFuelMagni();
						}
						
						//is lava and > 1000 mb
						if (fstack != null && fstack.containsFluid(BlockHelper.SampleFluidLava) &&
							fuelx + tile2.getPowerRemained() < tile2.getPowerMax())
						{
							//drain fluid
							fluid.drain(BlockHelper.SampleFluidLava, true);
							//fuel++
							tile2.setPowerRemained(tile2.getPowerRemained() + fuelx);
							sendUpdate = true;
							break;	//only consume 1 fuel per tick
						}
					}//end has capability
				}//end check capability
			}//end stack != null
		}//end for all slot
		
		return sendUpdate;
	}
	
	/** consume fuel liquid for ITileFurnace, return true = add fuel success */
	public static boolean decrLiquidFuel(ITileLiquidFurnace tile)
	{
		//lava to fuel: 1k lava = 20k fuel value
		if (tile.getPowerMax() - tile.getPowerRemained() >= 800)
		{
			if (tile.getFluidFuelAmount() >= 40)
			{
				//drain liquid
				int amount = tile.consumeFluidFuel(40) * 20;
				amount = (int) ((float)amount * tile.getFuelMagni());
				
				tile.setPowerRemained(tile.getPowerRemained() + amount);
				
				return true;
			}
		}
		
		return false;
	}
	
	/** get item burn time, include fluid container, used for fuel item check only!! */
	public static int getItemFuelValue(ItemStack fuel)
	{
		int fuelx = 0;
		
		//check fuel item
		fuelx = TileEntityFurnace.getItemBurnTime(fuel);
		
		//若一般的getFuelValue無效, 則改查詢liquid fuel
		if (fuelx <= 0)
		{
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(fuel);

			//只接受岩漿為液體燃料 : fluid.tile.lava
			if (checkLiquidIsLava1000(fluid))
			{
				//not support for large container
				if (fluid.amount > 1000)
				{
					return 0;
				}
				else
				{
					fuelx = 20000;
				}
			}
		}
		
		//add fluid container fuel (ex: drum, universal cell)
		if (fuel.getItem() instanceof IFluidContainerItem)
		{
			IFluidContainerItem container = (IFluidContainerItem) fuel.getItem();
			FluidStack fluid = container.getFluid(fuel);
			
			if (fuel.stackSize > 1)
			{
				return 0;
			}
			
			//check is lava
			if (checkLiquidIsLava1000(fluid))
			{
				//lava fuel = 20k
				fuelx = 20000;
			}//has enough lava
		}//end fluid container fuel
		
		return fuelx;
	}
	
	/** check liquid is a bucket of lava for 1.7.10 */
	public static boolean checkLiquidIsLava1000(FluidStack fluid)
	{
		return checkLiquidIsLavaWithAmount(fluid, 1000);
	}
	
	/** check liquid is lava for 1.7.10 */
	public static boolean checkLiquidIsLava(FluidStack fluid)
	{
		return checkLiquidIsLavaWithAmount(fluid, 0);
	}
	
	/** check liquid is lava and enough amount for 1.7.10 */
	public static boolean checkLiquidIsLavaWithAmount(FluidStack fluid, int amount)
	{
		if (fluid != null && checkLiquidIsLava(fluid.getFluid()) && fluid.amount == amount)
		{
			return true;
		}
		
		return false;
	}
	
	/** check liquid is lava for 1.7.10 */
	public static boolean checkLiquidIsLava(Fluid fluid)
	{
		if(fluid != null && fluid.getUnlocalizedName().equals("fluid.tile.lava"))
		{
			return true;
		}
		
		return false;
	}
	
	/**增減large shipyard的matBuild[] */
	public static void setLargeShipyardBuildMats(TileMultiGrudgeHeavy tile, int button, int matType, int value)
	{
		//null check
		if (tile == null) return;
		
		int num = 0;
		int num2 = 0;
		boolean stockToBuild = true;	//false = build -> stock , true = stock -> build
		
		//value轉換為數量
		switch (value)
		{
		case 0:
		case 4:
			num = 1000;
			break;
		case 1:
		case 5:
			num = 100;
			break;
		case 2:
		case 6:
			num = 10;
			break;
		case 3:
		case 7:
			num = 1;
			break;	
		}
		
		if (value > 3) stockToBuild = false;
		
		//判定num是否要修改, 再增減MatStock跟MatBuild
		//matStock -> matBuild
		if (stockToBuild)
		{
			//材料不夠指定數量, 則num改為剩餘全部材料數量
			if (num > tile.getMatStock(matType)) num = tile.getMatStock(matType);
			//材料超過製造上限(1000), 則num降為上限數量
			if (num + tile.getMatBuild(matType) > 1000) num = 1000 - tile.getMatBuild(matType);
			
			tile.addMatStock(matType, -num);
			tile.addMatBuild(matType, num);
		}
		//matBuild -> matStock
		else
		{
			//材料不夠指定數量, 則num改為剩餘全部材料數量
			if (num > tile.getMatBuild(matType)) num = tile.getMatBuild(matType);
			
			tile.addMatBuild(matType, -num);
			tile.addMatStock(matType, num);
		}
		
	}
	
	/** pairing waypoints, called by C2S packet
	 *  parms: pid: packet sender uid, posF: from pos, posT: to pos
	 */
	public static void pairingWaypointAndChest(EntityPlayer player, int pid, World w, BlockPos posWp, BlockPos posChest)
	{
		if (pid <= 0) return;
		
		TileEntity pos1 = w.getTileEntity(posWp);
		TileEntity pos2 = w.getTileEntity(posChest);
		
		if (pos1 instanceof ITileWaypoint && pos2 instanceof IInventory)
		{
			ITileWaypoint wp = (ITileWaypoint) pos1;
			IInventory chest = (IInventory) pos2;
			
			//check owner
			if (wp.getPlayerUID() != pid)
			{
				//not the owner, return
				player.sendMessage(new TextComponentTranslation("chat.shincolle:wrongowner")
						.appendText(" "+wp.getPlayerUID()));
				return;
			}
			
			//set chest
			wp.setPairedChest(posChest);
			
			//send msg
			TextComponentTranslation text = new TextComponentTranslation("chat.shincolle:wrench.setwp");
			text.getStyle().setColor(TextFormatting.AQUA);
			player.sendMessage
			(
				text.appendText(" " + TextFormatting.GREEN +
				posWp.getX() + " " + posWp.getY() + " " + posWp.getZ() +
	        	TextFormatting.AQUA + " & " + TextFormatting.GOLD +
	        	posChest.getX() + " " + posChest.getY() + " " + posChest.getZ())
			);
		}
	}
	
	/** pairing waypoints, called by C2S packet
	 *  parms: pid: packet sender uid, posF: from pos, posT: to pos
	 */
	public static void pairingWaypoints(EntityPlayer player, int pid, World w, BlockPos posF, BlockPos posT)
	{
		if (pid <= 0) return;
		
		TileEntity tileF = w.getTileEntity(posF);
		TileEntity tileT = w.getTileEntity(posT);

		if (tileF instanceof ITileWaypoint && tileT instanceof ITileWaypoint)
		{
			ITileWaypoint wpFrom = (ITileWaypoint) tileF;
			ITileWaypoint wpTo = (ITileWaypoint) tileT;
			BlockPos nextWpTo = wpTo.getNextWaypoint();
			
			//check owner
			if (wpFrom.getPlayerUID() != pid)
			{
				//not the owner, return
				player.sendMessage(new TextComponentTranslation("chat.shincolle:wrongowner")
						.appendText(" "+wpFrom.getPlayerUID()));
				return;
			}
			
			//set waypoint
			wpFrom.setNextWaypoint(posT);
			
			//若wpTo的next不是連到wpFrom (即沒有形成cycle) 則正常將wpTo的last設為wpFrom (若形成cycle則不修改wpTo的last wp)
			if (nextWpTo.getX() != posF.getX() || nextWpTo.getY() != posF.getY() || nextWpTo.getZ() != posF.getZ())
			{
				wpTo.setLastWaypoint(posF);
			}
			
			TextComponentTranslation text = new TextComponentTranslation("chat.shincolle:wrench.setwp");
			text.getStyle().setColor(TextFormatting.YELLOW);
			player.sendMessage
			(
				text.appendText(" " + TextFormatting.GREEN + posF.getX() + " " + posF.getY() + " " + posF.getZ() + 
				TextFormatting.AQUA + " --> " + TextFormatting.GOLD +
				posT.getX() + " " + posT.getY() + " " + posT.getZ())
			);
		}
	}
	
	/** get adj chest for TileEntityChest */
	public static TileEntityChest getAdjChest(TileEntityChest chest)
	{
		TileEntityChest chest2 = null;
		
		if (chest != null && !chest.isInvalid())
		{
			//check adj chest valid
			chest.checkForAdjacentChests();
			
			//get adj chest
			chest2 = chest.adjacentChestXNeg;
			if (chest2 == null)
			{
				chest2 = chest.adjacentChestXPos;
				if (chest2 == null)
				{
					chest2 = chest.adjacentChestZNeg;
					if (chest2 == null) chest2 = chest.adjacentChestZPos;
				}
			}
		}
		
		if (chest2 != null && chest2.isInvalid()) return null;
		
		return chest2;
	}
	
	
}