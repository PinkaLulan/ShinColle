package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.crafting.SmallRecipes;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.FormatHelper;
import com.lulan.shincolle.utility.TileEntityHelper;

/** Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 8min / 460800  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 1min / 57600
 * 	MaxMaterial / MaxFuelCost = 64*4 / 460800
 *  MinMaterial / MinFuelCost = 16*4 / 57600 = BaseCost(57600) CostPerMaterial(2100)
 */
public class TileEntitySmallShipyard extends BasicTileEntity implements ITileFurnace {
		
	private int consumedPower = 0;	//已花費的能量
	private int remainedPower = 0;	//剩餘燃料
	private int goalPower = 0;		//需要達成的目標能量
	private int buildType = 0;		//type 0:none 1:ship 2:equip 3:ship loop 4: equip loop
	private int[] buildRecord;
	private boolean isActive;		//是否正在建造中, 此為紀錄isBuilding是否有變化用
	private static int buildSpeed = 48;  			//power cost per tick
	private static final int MAXPOWER = 460800; 	//max power storage
	private static final int[] ALLSLOTS = new int[] {0, 1, 2, 3, 4, 5};  //dont care side

	
	public TileEntitySmallShipyard() {
		//0:grudge 1:abyss 2:ammo 3:poly 4:fuel 5:output
		this.slots = new ItemStack[6];
		this.isActive = false;
		this.syncTime = 0;
		
		if(ConfigHandler.easyMode) {
			buildSpeed = 480;
		}
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
        buildRecord = compound.getIntArray("buildRecord");
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
		compound.setIntArray("buildRecord", buildRecord);
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
				return TileEntityHelper.getItemFuelValue(itemstack) > 0 || item == ModItems.InstantConMat;
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
		//若為無限loop建造, 則檢查record的紀錄
		if(this.buildType == ID.Build.EQUIP_LOOP || this.buildType == ID.Build.SHIP_LOOP) {
			for(int i = 0; i < 4; i++) {
				//檢查材料是否足夠
				if(slots[i] == null || slots[i].stackSize < this.buildRecord[i]) {
					return;
				}
				//吃掉材料
				else {
					slots[i].stackSize -= this.buildRecord[i];
					if(slots[i].stackSize <= 0) slots[i] = null;
				}
			}

			//輸入材料數量, 取得build output到slot 5
			switch(this.buildType) {
			default:
			case ID.Build.SHIP_LOOP:
				slots[5] = SmallRecipes.getBuildResultShip(buildRecord);
				break;
			case ID.Build.EQUIP_LOOP:
				slots[5] = SmallRecipes.getBuildResultEquip(buildRecord);
				break;
			}
		}
		else {
			int[] matAmount = new int[4];
			//取得四樣材料數量
			matAmount = SmallRecipes.getMaterialAmount(slots);

			//將輸入材料全部吃掉
			slots[0] = null;
			slots[1] = null;
			slots[2] = null;
			slots[3] = null;
			
			//輸入材料數量, 取得build output到slot 5
			switch(this.buildType) {
			default:
			case ID.Build.SHIP:			//build ship
			case ID.Build.SHIP_LOOP:
				slots[5] = SmallRecipes.getBuildResultShip(matAmount);
				break;
			case ID.Build.EQUIP:		//build equip
			case ID.Build.EQUIP_LOOP:
				slots[5] = SmallRecipes.getBuildResultEquip(matAmount);
				break;
			}
		}
	}
	
	//判定是否建造中
	public boolean isBuilding() {
		return hasRemainedPower() && canBuild();
	}
	
	//判定是否有燃料
	public boolean hasRemainedPower() {
		return remainedPower > buildSpeed;
	}
	
	//判定是否能建造
	public boolean canBuild() {
		//若為無限loop建造, 則檢查record的紀錄
		if(this.buildType == ID.Build.EQUIP_LOOP || this.buildType == ID.Build.SHIP_LOOP) {		
			//檢查紀錄是否可以建造
			if(SmallRecipes.canRecipeBuild(buildRecord)) {
				//檢查材料是否足夠
				for(int i = 0; i < 4; i++) {
					if(slots[i] == null || slots[i].stackSize < this.buildRecord[i]) return false;
				}
				
				return goalPower > 0 && slots[5] == null;
			}
		}
		else {
			return goalPower > 0 && slots[5] == null;
		}
		
		return false;
	}
	
	//取得建造花費
	public void getGoalPower() {
		//若為無限loop建造, 則計算record的紀錄
		if(this.buildType == ID.Build.EQUIP_LOOP || this.buildType == ID.Build.SHIP_LOOP) {
			//檢查紀錄是否可以建造
			if(SmallRecipes.canRecipeBuild(buildRecord)) {
				goalPower = SmallRecipes.calcGoalPower(buildRecord);
			}
			else {
				goalPower = 0;
			}
		}
		else {
			int[] itemAmount = new int[4];
			//計算材料量
			itemAmount = SmallRecipes.getMaterialAmount(slots);
			//依照材料量計算goalPower, 若材料沒達minAmount則goalPower會得到0
			goalPower = SmallRecipes.calcGoalPower(itemAmount);
		}
	}
	
	//方塊的流程進行方法
	//資料必須以markDirty標記block更新, 以及讀寫NBT tag來保存
	@Override
	public void updateEntity() {
		boolean sendUpdate = false;	//標紀要block update, 有要更新metadata時設為true

		//null check
		if(this.buildRecord == null || this.buildRecord.length < 1) {
			this.buildRecord = new int[4];
			this.buildRecord[0] = 0;
			this.buildRecord[1] = 0;
			this.buildRecord[2] = 0;
			this.buildRecord[3] = 0;
		}
		
		//server side
		if(!worldObj.isRemote) {
			//update goalPower
			if(this.buildType != ID.Build.NONE) {
				this.getGoalPower();
			}
			else {
				this.goalPower = 0;
			}
			
			//fuel補充
			if(TileEntityHelper.checkItemFuel(this)) {
				sendUpdate = true;
			}
			
			//判定是否建造中, 每tick進行進度值更新, 若非建造中則重置進度值
			if(this.isBuilding()) {
				//在燃料格使用快速建造材料
				if(slots[4] != null && slots[4].getItem() == ModItems.InstantConMat) {
					slots[4].stackSize--;
					this.consumedPower += 115200;
					
					if(this.slots[4].stackSize == 0) {
						this.slots[4] = null;
					}
					
					sendUpdate = true;
				}
				
				this.remainedPower -= buildSpeed;	//fuel bar --
				this.consumedPower += buildSpeed;	//build bar ++
				
				//power達標, 建造完成
				if (this.consumedPower >= this.goalPower) {
					this.buildComplete();	//建造出成品放到output slot
					this.consumedPower = 0;
					this.goalPower = 0;
					
					//continue build if mode = loop mode
					switch(buildType) {
					default:
					case ID.Build.SHIP:
					case ID.Build.EQUIP:		//reset build type
						this.buildType = ID.Build.NONE;
						break;
					case ID.Build.SHIP_LOOP:	//remain build type
					case ID.Build.EQUIP_LOOP:
						break;
					}
					
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
		int timeSec = (goalPower - consumedPower) / buildSpeed / 20;	//get time (單位: sec)		
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
	public void setBuildRecord(int[] par1) {
		for(int i = 0; i < 4; i++) this.buildRecord[i] = par1[i];
	}

	@Override
	public int getFuelSlotMin() {
		return 4;
	}

	@Override
	public int getFuelSlotMax() {
		return 4;
	}

	@Override
	public int getPowerMax() {
		return this.MAXPOWER;
	}

	@Override
	public void setPowerMax(int par1) {}

	
}
