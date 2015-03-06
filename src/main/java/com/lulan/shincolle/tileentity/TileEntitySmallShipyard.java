package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.crafting.SmallRecipes;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.FormatHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;

/** Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 8min / 460800  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 1min / 57600
 * 	MaxMaterial / MaxFuelCost = 64*4 / 460800
 *  MinMaterial / MinFuelCost = 16*4 / 57600 = BaseCost(57600) CostPerMaterial(2100)
 */
public class TileEntitySmallShipyard extends BasicTileEntity {
		
	private int consumedPower = 0;	//已花費的能量
	private int remainedPower = 0;	//剩餘燃料
	private int goalPower = 0;		//需要達成的目標能量
	private int buildType = 0;		//type 0:none 1:ship 2:equip
	private boolean isActive;		//是否正在建造中, 此為紀錄isBuilding是否有變化用
	private static final int BUILDSPEED = 48;  	//power cost per tick	
	private static final int MAXPOWER = 460800; 	//max power storage
	private static final int[] ALLSLOTS = new int[] {0, 1, 2, 3, 4, 5};  //dont care side

	
	public TileEntitySmallShipyard() {
		//0:grudge 1:abyss 2:ammo 3:poly 4:fuel 5:output
		this.slots = new ItemStack[6];
		this.isActive = false;
		this.syncTime = 0;
	}

	//依照輸出入口設定, 決定漏斗等裝置如何輸出入物品到特定slot中
	@Override
	public int[] getAccessibleSlotsFromSide(int i) {
		return ALLSLOTS;
		//return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_side);
	}

	//GUI顯示的名稱, 有custom name則用, 不然就用預設名稱
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container."+Reference.MOD_ID+":SmallShipyard";
	}

	//是否可以右鍵點開方塊
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		//由於會有多個tile entity副本, 要先確認座標相同的副本才能使用
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		else {	//確認player要在該tile entity 64格內, 以免超出讀取範圍 or 產生其他不明bug
			return player.getDistanceSq((double)xCoord+0.5D, (double)yCoord+0.5D, (double)zCoord+0.5D) <= 64;
		}
	}

	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);	//從nbt讀取方塊的xyz座標

        NBTTagList list = compound.getTagList("Items", 10);	//抓nbt tag: Items (此為類型10:TagCompound)
        
        for(int i=0; i<list.tagCount(); i++) {			//將tag列出的所有物品抓出來
            NBTTagCompound item = list.getCompoundTagAt(i);
            byte sid = item.getByte("Slot");
            
            if (sid>=0 && sid<slots.length) {	//讀取nbt紀錄的物品, 生成到各slot中 
            	slots[sid] = ItemStack.loadItemStackFromNBT(item);
            }
        }

        consumedPower = compound.getInteger("consumedPower");
        remainedPower = compound.getInteger("remainedPower");
        goalPower = compound.getInteger("goalPower");
        buildType = compound.getInteger("buildType");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();
		compound.setTag("Items", list);
		for(int i=0; i<slots.length; i++) {		//將slots[]資料寫進nbt
			if (slots[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);	//在tag: Slot下儲存資料i
				slots[i].writeToNBT(item);		//在tag: Slot下儲存slots[i]資料
				list.appendTag(item);			//增加下一個欄位
			}
		}
			
		compound.setInteger("consumedPower", consumedPower);
		compound.setInteger("remainedPower", remainedPower);
		compound.setInteger("goalPower", goalPower);
		compound.setInteger("buildType", buildType);
	}
	
	//判定物品是否能放入該格子, 用於canExtractItem等方法
	//格子用途:0:grudge 1:abyss 2:ammo 3:poly 4:fuel 5:output
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if(itemstack != null) {
			Item item = itemstack.getItem();
			int meta = itemstack.getItemDamage();
		
			switch(slot) {
			case 0:		//grudge slot
				return item == ModItems.Grudge;
			case 1:		//abyssium slot
				return item == ModItems.AbyssMetal && meta == 0;
			case 2:		//ammo slot
				return item == ModItems.Ammo && meta == 0;
			case 3:		//polymetal slot
				return item == ModItems.AbyssMetal && meta == 1;
			case 4:		//fuel slot
				return TileEntityFurnace.isItemFuel(itemstack);
			default:
				return false;
			}
		}
		else {
			return false;
		}
	}

	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		//只有output slot跟fuel slot的空bucket可以輸出
		return (slot == 5) || (itemstack.getItem() == Items.bucket);
	}
	
	//建造ship方法
	public void buildComplete() {
		byte[] matAmount = new byte[4];
		//取得四樣材料數量
		matAmount = SmallRecipes.getMaterialAmount(slots);

		//將輸入材料全部吃掉
		slots[0] = null;
		slots[1] = null;
		slots[2] = null;
		slots[3] = null;
		
		//輸入材料數量, 取得build output到slot 5
		if(this.buildType == 1) {	//build ship
			slots[5] = SmallRecipes.getBuildResultShip(matAmount);
		}
		else {						//build equip or no select
			slots[5] = SmallRecipes.getBuildResultEquip(matAmount);
		}
		
	}
	
	//判定是否建造中
	public boolean isBuilding() {
		return hasRemainedPower() && canBuild();
	}
	
	//判定是否有燃料
	public boolean hasRemainedPower() {
		return remainedPower > BUILDSPEED;
	}
	
	//判定是否有建造目標
	public boolean canBuild() {
		return goalPower > 0 && slots[5] == null;
	}
	
	//取得建造花費
	public void getGoalPower() {
		byte[] itemAmount = new byte[4];	
		//計算材料量
		itemAmount = SmallRecipes.getMaterialAmount(slots);		
		//依照材料量計算goalPower, 若材料沒達minAmount則goalPower會得到0
		goalPower = SmallRecipes.calcGoalPower(itemAmount); 
	}
	
	//方塊的流程進行方法
	//資料必須以markDirty標記block更新, 以及讀寫NBT tag來保存
	@Override
	public void updateEntity() {
		boolean sendUpdate = false;	//標紀要block update, 有要更新metadata時設為true
		
		//update goalPower, check goalPower if material in slots[3] (polymetal slot)
		if(this.buildType != 0) {
			this.getGoalPower();
		}
		else {
			this.goalPower = 0;
		}
		
		//server side
		if(!worldObj.isRemote) {
			//fuel補充
			if(TileEntityFurnace.isItemFuel(this.slots[4]) && this.remainedPower < (this.MAXPOWER - TileEntityFurnace.getItemBurnTime(this.slots[4]))) {
				this.remainedPower += TileEntityFurnace.getItemBurnTime(this.slots[4]);
				
				if(this.slots[4] != null) {
					sendUpdate = true;			//標紀要update block
					this.slots[4].stackSize--;	//fuel -1
					
					if(this.slots[4].stackSize == 0) {
						this.slots[4] = this.slots[4].getItem().getContainerItem(this.slots[4]);
					}
				}
			}
			
			//判定是否建造中, 每tick進行進度值更新, 若非建造中則重置進度值
			if(this.isBuilding()) {
				this.remainedPower -= BUILDSPEED;	//fuel bar --
				this.consumedPower += BUILDSPEED;	//build bar ++
				
				//power達標, 建造完成
				if (this.consumedPower >= this.goalPower) {
					this.buildComplete();	//建造出成品放到output slot
					this.consumedPower = 0;
					this.goalPower = 0;		
					this.buildType = 0;
					sendUpdate = true;
				}
			}
			
			if(!this.canBuild()) {	//非建造中, 重置build bar
				this.consumedPower = 0;
			}
			
			//若狀態有改變過, 則發送更新  ex:本來active 而燃料用光導致無法active時
			if(isActive != this.isBuilding()) {
				isActive = this.isBuilding();
				sendUpdate = true;
			}
		}
		
		//標紀要更新
		if(sendUpdate) {
			//更新方塊metadata
			BlockSmallShipyard.updateBlockState(this.isBuilding(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			//標記此方塊要更新, 以保證資料會存到硬碟
			this.markDirty();
		}
	}

	//計算fuel存量條
	public int getPowerRemainingScaled(int i) {
		return (remainedPower * i) / MAXPOWER;
	}
	
	//計算建造時間 (換算成真實時間)
	public String getBuildTimeString() {
		//剩餘秒數 = (目標能量 - 目前能量) / (每tick增加能量) / 20
		int timeSec = (goalPower - consumedPower) / BUILDSPEED / 20;	//get time (單位: sec)		
		return FormatHelper.getTimeFormated(timeSec);
	}
	
	//getter
	public int getPowerConsumed() {
		return this.consumedPower;
	}
	public int getPowerRemained() {
		return this.remainedPower;
	}
	public int getPowerGoal() {
		return this.goalPower;
	}
	public int getBuildType() {
		return this.buildType;
	}
	
	//setter
	public void setPowerConsumed(int par1) {
		this.consumedPower = par1;
	}
	public void setPowerRemained(int par1) {
		this.remainedPower = par1;
	}
	public void setPowerGoal(int par1) {
		this.goalPower = par1;
	}
	public void setBuildType(int par1) {
		this.buildType = par1;
	}

	
}
