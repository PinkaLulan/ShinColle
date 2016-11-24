package com.lulan.shincolle.utility;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileInventory;
import com.lulan.shincolle.tileentity.ITileFurnace;
import com.lulan.shincolle.tileentity.ITileLiquidFurnace;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

public class TileEntityHelper
{
	
	
	public TileEntityHelper() {}
	
	/** consume fuel item for ITileFurnace, return true = add fuel success */
	public static boolean decrItemFuel(BasicTileInventory tile)
	{
		ITileFurnace tile2 = (ITileFurnace) tile;
		ItemStack stack = null;
		boolean sendUpdate = false;
		int fuelx = 0;
		
		//check power storage first
		if (tile2.getPowerRemained() >= tile2.getPowerMax()) return false;
		
		//get item in slot
		for (int i = 0; i < tile.getSizeInventory(); i++)
		{
			stack = tile.getStackInSlot(i);
			
			if (stack != null)
			{
				//get normal fuel value
				fuelx = TileEntityFurnace.getItemBurnTime(stack);
				
				//若一般的getFuelValue無效, 則改查詢liquid fuel
				if (fuelx <= 0)
				{
					FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);

					//只接受岩漿為液體燃料 : fluid.tile.lava
					if (checkLiquidIsLava1000(fluid))
					{
						//not support for large container
						if (fluid.amount > 1000)
						{
							LogHelper.info("DEBUG : fluid registry: lava amount > 1000 mb");
						}
						else
						{
							fuelx = 20000;
						}
					}
				}
				
				//calc fuel magnification
				if (tile instanceof TileEntitySmallShipyard)
				{
					fuelx *= ((TileEntitySmallShipyard)tile).FUELMAGN;
				}
//				else if (tile instanceof TileEntityGrudgeHeavy) TODO
//				{
//					
//				}
				
				//check power storage and add disposable fuel (ex: coal, lava bucket, lava cell)
				if (fuelx > 0 && fuelx + tile2.getPowerRemained() < tile2.getPowerMax())
				{
					stack.stackSize--;	//fuel size -1
					tile2.setPowerRemained(tile2.getPowerRemained() + fuelx);	//fuel++
					
					//若該物品用完, 用getContainerItem處理是否要清空還是留下桶子 ex: lava bucket -> empty bucket
					if (stack.stackSize <= 0)
					{
						stack = stack.getItem().getContainerItem(stack);
					}
					
					//update slot
					tile.setInventorySlotContents(i, stack);
					sendUpdate = true;
					break;	//only consume 1 fuel every tick
				}//end disposable fuel
				
				//add disposable fuel FAILED, try fluid container (ex: drum, universal cell)
				if (stack.getItem() instanceof IFluidContainerItem)
				{
					IFluidContainerItem container = (IFluidContainerItem) stack.getItem();
					FluidStack fluid = container.getFluid(stack);
					
					//is lava and stack size = 1
					if (stack.stackSize > 1)
					{
						LogHelper.info("DEBUG : lava fluid container stackSize > 1, no drain");
					}
					else if (checkLiquidIsLava1000(fluid))
					{
						//lava fuel = 20k
						fuelx = 20000;
						
						//check fuel magnification
						if (tile instanceof TileEntitySmallShipyard)
						{
							fuelx *= ((TileEntitySmallShipyard)tile).FUELMAGN;
						}
//						else if (tile instanceof TileEntityGrudgeHeavy) TODO
//						{
//							
//						}
						
						if(fuelx > 0 && fuelx + tile2.getPowerRemained() < tile2.getPowerMax()) {
							//drain lava, capacity is checked in checkLiquidIsLava(), no check again
							container.drain(stack, 1000, true);
							//fuel++
							tile2.setPowerRemained(tile2.getPowerRemained() + fuelx);
							//update slot
							tile.setInventorySlotContents(i, stack);
							sendUpdate = true;
							break;	//only consume 1 fuel every tick
						}
					}//has enough lava
				}//end fluid container fuel
			}//end stack != null
		}//end all slots for loop
		
		return sendUpdate;
	}
	
	/** consume fuel liquid for ITileFurnace, return true = add fuel success */
	public static boolean decrLiquidFuel(ITileLiquidFurnace tile) {
		//lava to fuel: 1k lava = 20k fuel value
		if(tile.getPowerMax() - tile.getPowerRemained() >= 800) {
			if(tile.getFluidFuelAmount() >= 40) {
				//drain liquid
				FluidStack getf = tile.drainFluidFuel(40);
				//add liquid to fuel value
				tile.setPowerRemained(tile.getPowerRemained() + getf.amount * 20);
				
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
					LogHelper.info("DEBUG : fluid registry: lava amount > 1000 mb");
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
				LogHelper.info("DEBUG : lava fluid container stackSize > 1, no drain");
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
	
	/** check liquid is a bucket of lava */
	public static boolean checkLiquidIsLava1000(FluidStack fluid)
	{
		return checkLiquidIsLavaWithAmount(fluid, 1000);
	}
	
	/** check liquid is lava */
	public static boolean checkLiquidIsLava(FluidStack fluid)
	{
		return checkLiquidIsLavaWithAmount(fluid, 0);
	}
	
	/** check liquid is lava and enough amount */
	public static boolean checkLiquidIsLavaWithAmount(FluidStack fluid, int amount)
	{
		if(fluid != null && checkLiquidIsLava(fluid.getFluid()) && fluid.amount >= amount)
		{
			return true;
		}
		
		return false;
	}
	
	/** check liquid is lava */
	public static boolean checkLiquidIsLava(Fluid fluid)
	{
		if(fluid != null && fluid.getUnlocalizedName().equals("fluid.tile.lava"))
		{
			return true;
		}
		
		return false;
	}
	
	/**增減large shipyard的matBuild[] */
	private static void setLargeShipyardBuildMats(TileMultiGrudgeHeavy tile, int button, int matType, int value)
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
	
	/**process tile GUI click */
	public static void setTileEntityByGUI(TileEntity tile, int button, int value, int value2)
	{
		if (tile instanceof TileEntitySmallShipyard)
		{
			TileEntitySmallShipyard smalltile = (TileEntitySmallShipyard) tile;
			smalltile.setBuildType(value);
			
			//set build record
			if (value == ID.Build.EQUIP_LOOP || value == ID.Build.SHIP_LOOP)
			{
				int[] getMat = new int[] {0,0,0,0};
				
				for (int i = 0; i < 4; i++)
				{
					if (smalltile.getStackInSlot(i) != null)
					{
						getMat[i] = smalltile.getStackInSlot(i).stackSize;
					}
				}
				
				smalltile.setBuildRecord(getMat);
			}
			
			return;
		}
		else if (tile instanceof TileMultiGrudgeHeavy)
		{
			switch (button)
			{
			case ID.B.Shipyard_Type:		//build type
				((TileMultiGrudgeHeavy) tile).setBuildType(value);
				break;
			case ID.B.Shipyard_InvMode:		//select inventory mode
				((TileMultiGrudgeHeavy) tile).setInvMode(value);
				break;
			case ID.B.Shipyard_SelectMat:	//select material
				((TileMultiGrudgeHeavy) tile).setSelectMat(value);
				break;
			case ID.B.Shipyard_INCDEC:		//material inc,dec
				setLargeShipyardBuildMats((TileMultiGrudgeHeavy) tile, button, value, value2);
				break;
			}
		}
//		else if(tile instanceof TileEntityCrane) {
//			switch(button) {
//			case ID.B.Crane_Load:
//				((TileEntityCrane)tile).enabLoad = value == 0 ? false : true;
//				break;
//			case ID.B.Crane_Unload:
//				((TileEntityCrane)tile).enabUnload = value == 0 ? false : true;
//				break;
//			case ID.B.Crane_Power:
//				((TileEntityCrane)tile).isActive = value == 0 ? false : true;
//				
//				//power off, clear ship
//				if(!((TileEntityCrane)tile).isActive) {
//					((TileEntityCrane)tile).ship = null;
//				}
//				break;
//			case ID.B.Crane_Meta:
//				((TileEntityCrane)tile).checkMetadata = value == 0 ? false : true;
//				break;
//			case ID.B.Crane_Dict:
//				((TileEntityCrane)tile).checkOredict = value == 0 ? false : true;
//				break;
//			case ID.B.Crane_Mode:
//				((TileEntityCrane)tile).craneMode = value;
//				break;
//			case ID.B.Crane_Nbt:
//				((TileEntityCrane)tile).checkNbt = value == 0 ? false : true;
//				break;
//			case ID.B.Crane_Red:
//				if (value > 2) value = 0;
//				((TileEntityCrane)tile).redMode = value;
//				break;
//			}
//		}
		else
		{
			LogHelper.info("DEBUG : set tile entity by GUI fail: tile: "+tile);
		}	
	}
	

}
