package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockGrudgeHeavy;
import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.crafting.LargeRecipes;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.TileEntityHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 24min / 1382400  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 8min / 460800
 * 	MaxMaterial / MaxFuelCost = 1000*4 / 1382400
 *  MinMaterial / MinFuelCost = 100*4 / 460800 = BaseCost(460800) CostPerMaterial(256)
 *  
 *  Slots:
 *    0: output
 *    1~9: inventory
 */
public class TileMultiGrudgeHeavy extends BasicTileMulti implements ITileLiquidFurnace, ITickable
{	
	
	//furnace
	private int powerConsumed = 0;	//已花費的能量
	private int powerRemained = 0;	//剩餘燃料
	private int powerGoal = 0;		//需要達成的目標能量
	private int buildType = 0;		//type 0:none 1:ship 2:equip 3:ship loop 4: equip loop
	private int invMode = 0;		//物品欄模式 0:收物品 1:放出物品
	private int selectMat = 0;		//物品選擇模式, 用於物品輸出 0:grudge 1:abyss 2:ammo 3:poly
	private boolean isActive;		//是否正在建造中, 此為紀錄isBuilding是否有變化用
	private int[] matsBuild;		//建造材料量
	private int[] matsStock;		//庫存材料量
	public static int POWERINST;	//power per instant material
	public static int BUILDSPEED;	//power cost per tick
	public static int POWERMAX; 	//max power storage
	public static float FUELMAGN; 	//fuel magnification
	public static final int SLOTS_NUM = 10;  //total slots
	public static final int SLOTS_OUT = 0;   //output slot
	public static final int[] ALLSLOTS = new int[] {0,2,3,4,5,6,7,8,9}; //slot 1 for fuel

	//fluid tank
	protected FluidTank tank;
	
	
	/** 注意constructor只會在server端呼叫, client端需要另外init以免噴出NPE */
	public TileMultiGrudgeHeavy()
	{
		super();
		
		//0:output 2~10:inventory
		this.itemHandler = new CapaInventory(SLOTS_NUM, this);
		this.isActive = false;
		this.syncTime = 0;
		
		//tank
		this.tank = new FluidTank(FluidRegistry.LAVA, 0, 2000);
		this.tank.setCanDrain(false);
		this.tank.setTileEntity(this);
		
		POWERMAX = (int) ConfigHandler.tileShipyardLarge[0];
		BUILDSPEED = (int) ConfigHandler.tileShipyardLarge[1];
		FUELMAGN = (float) ConfigHandler.tileShipyardLarge[2];
		POWERINST = BUILDSPEED * 1200;
	}
	
	/** get block meta for render, if tile is item, return meta = -1 */
	@Override
	public int getRenderMetadata()
	{
		if (this.world == null || this.pos == BlockPos.ORIGIN)
		{
			return -1;
		}
		else
		{
			return this.getBlockMetadata();
		}
	}
	
	@Override
	public String getRegName()
	{
		return BlockGrudgeHeavy.TILENAME;
	}
	
	@Override
	public byte getGuiIntID()
	{
		return ID.Gui.LARGESHIPYARD;
	}
	
	@Override
	public byte getPacketID(int type)
	{
		switch (type)
		{
		case 0:
			return S2CGUIPackets.PID.TileLargeSY;
		}
		
		return -1;
	}
	
	//依照輸出入口設定, 決定漏斗等裝置如何輸出入物品到特定slot中
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		//type 1: large shipyard
		//slot 0:output 2~10:inventory
		//side 0:bottom 1:top 2~5:side
		if (this.structType == 1)
		{
			return ALLSLOTS;
		}
		
		return new int[] {};
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);
        
        //load tank
        tank.readFromNBT(nbt);

        powerConsumed = nbt.getInteger("powerConsumed");
        powerRemained = nbt.getInteger("powerRemained");
        powerGoal = nbt.getInteger("powerGoal");
        buildType = nbt.getInteger("buildType");
        invMode = nbt.getInteger("invMode");
        selectMat = nbt.getInteger("selectMat");
        setMatBuild(nbt.getIntArray("matsBuild"));
        setMatStock(nbt.getIntArray("matsStock"));
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		//save tank
		tank.writeToNBT(nbt);
		
		nbt.setInteger("powerConsumed", powerConsumed);
		nbt.setInteger("powerRemained", powerRemained);
		nbt.setInteger("powerGoal", powerGoal);
		nbt.setInteger("buildType", buildType);
		nbt.setInteger("invMode", invMode);
		nbt.setInteger("selectMat", selectMat);
		nbt.setIntArray("matsBuild", getMatBuild());
		nbt.setIntArray("matsStock", getMatStock());
		
		return nbt;
	}
	
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return (T) tank;
        
        return super.getCapability(capability, facing);
    }
	
	//判定物品是否能放入該格子, 用於canExtractItem等方法
	//格子用途:0:grudge 1:abyss 2:ammo 3:poly 4:fuel 5:output
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		//output slot
		if (slot == SLOTS_OUT)
		{
			return false;
		}
		//other slot
		else if (stack != null)
		{
			//if item is IFluidContainerItem (1.7.10) or IFluidHandler (1.10.2)
			if (stack.getItem() instanceof IFluidContainerItem ||
				stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
			{
				if (this.itemHandler.getStackInSlot(slot) != null) return false;
			}
		}
		
		return true;
	}
	
	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing face)
	{
		//for "release mode" or output slot
		if (slot == SLOTS_OUT || invMode == 1)
		{
			return true;
		}
		//for "recycle mode" or other slots
		else
		{
			if (stack == null) return false;
			
			/** for fluid container, empty container will always be extracted */
			//is IFluidContainerItem (1.7.10)
			if (stack.getItem() instanceof IFluidContainerItem)
			{
				IFluidContainerItem container = (IFluidContainerItem) stack.getItem();
				FluidStack fluid = container.getFluid(stack);
				
				if (fluid == null || fluid.amount < 1000) return true;
			}
			//is IFluidHandler (1.10.2)
			else if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
			{
				IFluidHandler fluid = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
				FluidStack fstack = fluid.drain(BlockHelper.SampleFluidLava, false);
				if (fstack == null) return true;
			}
			else
			{
				//check special item
				if (stack.getItem() == ModItems.InstantConMat ||
					stack.getItem() == ModItems.ShipSpawnEgg ||
					stack.getItem() instanceof BasicEquip ||
					TileEntityHelper.getItemFuelValue(stack) > 0) return false;
				
				//check 1.7.10 FluidContainerRegistry
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(stack);
				
				//fluid empty or not fluid container
				if (fluid == null)
				{
					if (invMode != 0) return true;
				}
				//fluid remaining
				else if (fluid.amount < 1000)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	//建造ship方法
	public void buildComplete()
	{
		//輸入材料數量, 取得build output到slot 5
		switch(this.buildType)
		{
		default:
		case ID.Build.SHIP:			//build ship
		case ID.Build.SHIP_LOOP:
			itemHandler.setStackInSlot(SLOTS_OUT, LargeRecipes.getBuildResultShip(getMatBuild()));
			break;
		case ID.Build.EQUIP:		//build equip
		case ID.Build.EQUIP_LOOP:
			itemHandler.setStackInSlot(SLOTS_OUT, LargeRecipes.getBuildResultEquip(getMatBuild()));
			break;
		}
	}
	
	//判定是否建造中
	public boolean isBuilding()
	{
		return hasPowerRemained() && canBuild();
	}
	
	//判定是否有燃料
	public boolean hasPowerRemained()
	{
		return powerRemained > BUILDSPEED;
	}
	
	//判定是否有建造目標
	public boolean canBuild()
	{
		return powerGoal > 0 && itemHandler.getStackInSlot(SLOTS_OUT) == null;
	}
	
	//方塊的流程進行方法
	//資料必須以markDirty標記block更新, 以及讀寫NBT tag來保存
	@Override
	public void update()
	{
		//do not update if no structure
		if (this.getStructType() <= 0) return;
		
		boolean sendUpdate = false;  //標紀要block update, 有要更新metadata時設為true
		
		//server side
		if (!world.isRemote)
		{
			//update goalPower
			if (this.buildType != 0)
			{
				//TODO move calc goal power to GUI INPUT PACKET
				this.powerGoal = LargeRecipes.calcGoalPower(getMatBuild());
			}
			else
			{
				this.powerGoal = 0;
			}
			
			//add item fuel
			if(TileEntityHelper.decrItemFuel(this))
			{
				sendUpdate = true;
			}
			
			//add liquid fuel
			TileEntityHelper.decrLiquidFuel(this);
			
			//inventory mode 0:ADD 1:RELEASE
			//RECYCLE MODE
			if (invMode == 0)
			{
				for (int i = SLOTS_OUT + 1; i < SLOTS_NUM; i++)
				{
					ItemStack item = itemHandler.getStackInSlot(i);
					
					//add material
					if (LargeRecipes.addMaterialStock(this, item))
					{
						item.stackSize--;
						
						if (item.stackSize == 0)
						{
							itemHandler.setStackInSlot(i, null);
						}
						
						sendUpdate = true;
						break;		//新增材料成功, 跳到下個tick
					}
				}
			}
			//RELEASE MODE
			else
			{
				int compressNum = 9;	//output block
				int normalNum = 1;		//output single item
				
				//抽出物品的數量
				if (ConfigHandler.easyMode)
				{	
					compressNum = 90;
					normalNum = 10;
				}
				
				//放出block or container等壓縮型態
				if (getMatStock(selectMat) >= compressNum)
				{
					if (LargeRecipes.outputMaterialToSlot(this, selectMat, true))
					{
						this.addMatStock(selectMat, -compressNum);
						sendUpdate = true;
					}
				}
				//放出單件物品型態
				else if (getMatStock(selectMat) >= normalNum)
				{
					if (LargeRecipes.outputMaterialToSlot(this, selectMat, false))
					{
						this.addMatStock(selectMat, -normalNum);
						sendUpdate = true;
					}
				}
			}

			//判定是否建造中, 每tick進行進度值更新, 若非建造中則重置進度值
			if (this.isBuilding())
			{
				this.syncTime++;
				this.powerRemained -= BUILDSPEED;	//fuel bar --
				this.powerConsumed += BUILDSPEED;	//build bar ++
				
				//消耗高速建造材料
				for (int i = SLOTS_OUT + 1; i < SLOTS_NUM; i++)
				{
					ItemStack item = itemHandler.getStackInSlot(i);
					
					if (item != null && item.getItem() == ModItems.InstantConMat)
					{
						item.stackSize--;
						this.powerConsumed += POWERINST;
						
						if (item.stackSize == 0)
						{
							itemHandler.setStackInSlot(i, null);
						}
						
						sendUpdate = true;
						break;
					}
				}
				
				//power達標, 建造完成
				if (this.powerConsumed >= this.powerGoal)
				{
					this.buildComplete();	//建造出成品放到output slot
					this.powerConsumed = 0;
					this.powerGoal = 0;

					//continue build if mode = loop mode
					switch(buildType)
					{
					default:
					case ID.Build.SHIP:
					case ID.Build.EQUIP:		//reset build type
						this.buildType = ID.Build.NONE;
						//將建造材料清除
						this.setMatBuild(new int[] {0,0,0,0});
						break;
					case ID.Build.SHIP_LOOP:	//remain build type
					case ID.Build.EQUIP_LOOP:	//remain build type
						this.setRepeatBuild();
						break;
					}

					sendUpdate = true;
				}
			}			
			
			//非建造中, 重置build bar
			if (!this.canBuild())
			{
				this.powerConsumed = 0;
			}
			
			//若狀態有改變過, 則發送更新  ex:本來active 而燃料用光導致無法active時
			if (isActive != this.isBuilding())
			{
				isActive = this.isBuilding();
				sendUpdate = true;
			}
			
			//標紀要更新
			if (sendUpdate)
			{
				this.syncTime = 0;
				//update blockstate & send packet
				BlockGrudgeHeavy.updateBlockState(this.isBuilding() ? 2 : 1, this.world, this.pos);
				//標記此方塊要更新, 以保證資料會存到硬碟
				this.markDirty();
			}

			//force update every 12000 ticks if no update
			if (this.syncTime > 12000)
			{
				this.syncTime = 0;
				
				//TODO force update
			}
		}//end server side
		//client side
		else
		{
			//valid tile
			if (this.world.getBlockState(this.pos).getBlock() != ModBlocks.BlockGrudgeHeavy)
			{
				this.invalidate();
				return;
			}
		}
	}
	
	//set materials for repeat build
	public void setRepeatBuild()
	{
		//set materials
		for (int i = 0; i < 4; i++)
		{
			//has enough materials
			if (getMatStock(i) >= getMatBuild(i))
			{
				addMatStock(i, -getMatBuild(i));
			}
			//no materials, reset matsBuild
			else
			{
				setMatBuild(i, 0);
				buildType = ID.Build.NONE;
			}
		}
	}

	//計算fuel存量條
	public int getPowerRemainingScaled(int i)
	{
		return (powerRemained * i) / POWERMAX;
	}
	
	//計算建造時間 (換算成真實時間)
	public String getBuildTimeString()
	{
		//剩餘秒數 = (目標能量 - 目前能量) / (每tick增加能量) / 20
		int timeSec = (int)((powerGoal - powerConsumed) / BUILDSPEED * 0.05F);  //get time (單位: sec)		
		return CalcHelper.getTimeFormated(timeSec);
	}
	
	//getter
	@Override
	public int getPowerConsumed()
	{
		return powerConsumed;
	}
	
	@Override
	public int getPowerRemained()
	{
		return powerRemained;
	}
	
	@Override
	public int getPowerGoal()
	{
		return powerGoal;
	}
	
	@Override
	public int getPowerMax()
	{
		return POWERMAX;
	}
	
	public int getBuildType()
	{
		return buildType;
	}
	
	public int getInvMode()
	{
		return invMode;
	}
	
	public int getSelectMat()
	{
		return selectMat;
	}
	
	public int[] getMatBuild()
	{
		if (matsBuild == null || matsBuild.length < 4) matsBuild = new int[] {0,0,0,0};
		
		return matsBuild;
	}
	
	public int[] getMatStock()
	{
		if (matsStock == null || matsStock.length < 4) matsStock = new int[] {0,0,0,0};
		
		return matsStock;
	}
	
	public int getMatBuild(int id)
	{
		if (matsBuild == null || matsBuild.length < 4) matsBuild = new int[] {0,0,0,0};
		
		return matsBuild[id];
	}
	
	public int getMatStock(int id)
	{
		if (matsStock == null || matsStock.length < 4) matsStock = new int[] {0,0,0,0};
		
		return matsStock[id];
	}
	
	//setter
	@Override
	public void setPowerConsumed(int par1)
	{
		this.powerConsumed = par1;
	}
	
	@Override
	public void setPowerRemained(int par1)
	{
		this.powerRemained = par1;
	}
	
	@Override
	public void setPowerGoal(int par1)
	{
		this.powerGoal = par1;
	}
	
	@Override
	public void setPowerMax(int par1) {}
	
	public void setBuildType(int par1)
	{
		this.buildType = par1;
	}
	
	public void setInvMode(int par1)
	{
		this.invMode = par1;
	}
	
	public void setSelectMat(int par1)
	{
		this.selectMat = par1;
	}
	
	public void setMatBuild(int[] par1)
	{
		if (par1 == null || par1.length < 4)
		{
			matsBuild = new int[] {0,0,0,0};
		}
		else
		{
			this.matsBuild = par1;
		}
	}
	
	public void setMatStock(int[] par1)
	{
		if (par1 == null || par1.length < 4)
		{
			matsStock = new int[] {0,0,0,0};
		}
		else
		{
			this.matsStock = par1;
		}
	}
	
	public void setMatBuild(int id, int par1)
	{
		if (matsBuild == null || matsBuild.length < 4) matsBuild = new int[] {0,0,0,0};
		
		this.matsBuild[id] = par1;
	}
	
	public void setMatStock(int id, int par1)
	{
		if (matsStock == null || matsStock.length < 4) matsStock = new int[] {0,0,0,0};
		
		this.matsStock[id] = par1;
	}
	
	//add a number to build
	public void addMatBuild(int id, int par1)
	{
		if (matsBuild == null || matsBuild.length < 4) matsBuild = new int[] {0,0,0,0};
		
		this.matsBuild[id] += par1;
	}
	
	//add a number to stock
	public void addMatStock(int id, int par1)
	{
		if (matsStock == null || matsStock.length < 4) matsStock = new int[] {0,0,0,0};
		
		this.matsStock[id] += par1;
	}

	@Override
	public int getFluidFuelAmount()
	{
		return this.tank.getFluidAmount();
	}

	//getter for fuel handler
	@Override
	public int consumeFluidFuel(int amount)
	{
		if (this.tank != null)
		{
			FluidStack fluid = this.tank.drainInternal(amount, true);
			
			if (fluid != null) return fluid.amount;
		}
		
		return 0;
	}

	@Override
	public float getFuelMagni()
	{
		return FUELMAGN;
	}
	
	//expend render scope
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        BlockPos pos = getPos();
        AxisAlignedBB bb = new AxisAlignedBB(pos.add(-2, -3, -2), pos.add(2, 2, 2));
        return bb;
    }
	
	
}