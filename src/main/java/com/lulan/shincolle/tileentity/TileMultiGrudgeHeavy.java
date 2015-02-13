package com.lulan.shincolle.tileentity;

import java.util.List;

import com.lulan.shincolle.block.BlockGrudgeHeavy;
import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.crafting.LargeRecipes;
import com.lulan.shincolle.entity.renderentity.BasicRenderEntity;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.FormatHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;

/** Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 24min / 1382400  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 8min / 460800
 * 	MaxMaterial / MaxFuelCost = 1000*4 / 1382400
 *  MinMaterial / MinFuelCost = 100*4 / 460800 = BaseCost(460800) CostPerMaterial(256)
 */
public class TileMultiGrudgeHeavy extends BasicTileMulti {	
	
	private int powerConsumed = 0;	//已花費的能量
	private int powerRemained = 0;	//剩餘燃料
	private int powerGoal = 0;		//需要達成的目標能量
	private int buildType = 0;		//type 0:none 1:ship 2:equip
	private int invMode = 0;		//物品欄模式 0:收物品 1:放出物品
	private int selectMat = 0;		//物品選擇模式, 用於物品輸出 0:grudge 1:abyss 2:ammo 3:poly
	private boolean isActive;		//是否正在建造中, 此為紀錄isBuilding是否有變化用
	private int[] matsBuild;		//建造材料量
	private int[] matsStock;		//庫存材料量
	public static final int BUILDSPEED = 48;  	//power cost per tick
	public static final int POWERMAX = 1382400; 	//max power storage
	public static final int SLOTS_NUM = 10;
	public static final int SLOTS_OUT = 0;
	private static final int[] SLOTS_ALL = new int[] {0,1,2,3,4,5,6,7,8,9};

	
	public TileMultiGrudgeHeavy() {
		//0:output 2~10:inventory
		this.slots = new ItemStack[SLOTS_NUM];
		this.isActive = false;
		this.matsBuild = new int[] {0,0,0,0};
		this.matsStock = new int[] {0,0,0,0};
	}
	
	//依照輸出入口設定, 決定漏斗等裝置如何輸出入物品到特定slot中
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		//slot 0:output 2~10:inventory
		//side 0:bottom 1:top 2~5:side
		return SLOTS_ALL;
	}
	
	//GUI顯示的名稱, 有custom name則用, 不然就用預設名稱
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container."+Reference.MOD_ID+":LargeShipyard";
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

        powerConsumed = compound.getInteger("powerConsumed");
        powerRemained = compound.getInteger("powerRemained");
        powerGoal = compound.getInteger("powerGoal");
        buildType = compound.getInteger("buildType");
        invMode = compound.getInteger("invMode");
        selectMat = compound.getInteger("selectMat");
        matsBuild = compound.getIntArray("matsBuild");
        matsStock = compound.getIntArray("matsStock");
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
			
		compound.setInteger("powerConsumed", powerConsumed);
		compound.setInteger("powerRemained", powerRemained);
		compound.setInteger("powerGoal", powerGoal);
		compound.setInteger("buildType", buildType);
		compound.setInteger("invMode", invMode);
		compound.setInteger("selectMat", selectMat);
		compound.setIntArray("matsBuild", matsBuild);
		compound.setIntArray("matsStock", matsStock);
	}
	
	//判定物品是否能放入該格子, 用於canExtractItem等方法
	//格子用途:0:grudge 1:abyss 2:ammo 3:poly 4:fuel 5:output
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if(slot == 0) {	//output slot
			return false;
		}	
		return true;
	}
	
	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return true;
	}
	
	//建造ship方法
	public void buildComplete() {
		//輸入材料數量, 取得build output到slot 5
		if(this.buildType == 1) {	//build ship
			slots[0] = LargeRecipes.getBuildResultShip(matsBuild);
		}
		else {						//build equip or no select
			slots[0] = LargeRecipes.getBuildResultEquip(matsBuild);
		}
				
		//將建造材料扣掉
		matsBuild[0] = 0;
		matsBuild[1] = 0;
		matsBuild[2] = 0;
		matsBuild[3] = 0;	
	}
	
	//判定是否建造中
	public boolean isBuilding() {
		return hasPowerRemained() && canBuild();
	}
	
	//判定是否有燃料
	public boolean hasPowerRemained() {
		return powerRemained > BUILDSPEED;
	}
	
	//判定是否有建造目標
	public boolean canBuild() {
		return powerGoal > 0 && slots[0] == null;
	}
	
	//方塊的流程進行方法
	//資料必須以markDirty標記block更新, 以及讀寫NBT tag來保存
	@Override
	public void updateEntity() {
		boolean sendUpdate = false;	//標紀要block update, 有要更新metadata時設為true

		//update goalPower, check goalPower if material in slots[3] (polymetal slot)
		if(this.buildType != 0) {
			this.powerGoal = LargeRecipes.calcGoalPower(matsBuild);
		}
		else {
			this.powerGoal = 0;
		}
		
		//server side
		if(!worldObj.isRemote) {
			//fuel補充
			//1.找出物品欄中最靠左邊的燃料 2.比較是否可以增加該燃料 3.增加燃料
			int burnTime;
			for(int i = SLOTS_OUT + 1; i < SLOTS_NUM; i++) {
				burnTime = TileEntityFurnace.getItemBurnTime(this.slots[i]);
				if(burnTime > 0 && burnTime + this.powerRemained < this.POWERMAX) {
					this.slots[i].stackSize--;	//fuel -1
					this.powerRemained += burnTime;
					
					//若該物品用完, 用getContainerItem處理是否要清空還是留下桶子 ex: lava bucket -> empty bucket
					if(this.slots[i].stackSize == 0) {
						this.slots[i] = this.slots[i].getItem().getContainerItem(this.slots[i]);
					}
					
					sendUpdate = true;	//標紀要update block
					break;	//加過一個燃料就停止loop, 每tick最多吃掉一顆燃料
				}
			}
			
			//inventory mode 0:收入物品 1:放出物品
			int itemType;
			if(invMode == 0) {	//收入物品
				for(int i = SLOTS_OUT + 1; i < SLOTS_NUM; i++) {
					itemType = LargeRecipes.getMaterialType(slots[i]);
					if(itemType > 0) {	//is material
						if(LargeRecipes.addMaterialStock(this, i, itemType)) {
							slots[i].stackSize--;
							
							if(slots[i].stackSize == 0) {
								slots[i] = null;
							}
							
							sendUpdate = true;
							break;		//新增材料成功, 跳到下個tick
						}
						//新增材料失敗, 搜尋下一個slot
					}
				}
			}
			else {				//放出物品
				if(getMatStock(selectMat) > 8) {		//放出block or container等壓縮型態
					if(LargeRecipes.outputMaterialToSlot(this, selectMat, 1)) {
						this.addMatStock(selectMat, -9);
						sendUpdate = true;
					}
				}
				else if(getMatStock(selectMat) > 0) {	//放出單件物品型態
					if(LargeRecipes.outputMaterialToSlot(this, selectMat, 0)) {
						this.addMatStock(selectMat, -1);
						sendUpdate = true;
					}
				}
			}

			//判定是否建造中, 每tick進行進度值更新, 若非建造中則重置進度值
			if(this.isBuilding()) {
				this.powerRemained -= BUILDSPEED;	//fuel bar --
				this.powerConsumed += BUILDSPEED;	//build bar ++
				
				//power達標, 建造完成
				if (this.powerConsumed >= this.powerGoal) {
					this.buildComplete();	//建造出成品放到output slot
					this.powerConsumed = 0;
					this.powerGoal = 0;
					this.buildType = 0;	
					sendUpdate = true;
				}
			}			
			
			if(!this.canBuild()) {	//非建造中, 重置build bar
				this.powerConsumed = 0;
			}
			
			//若狀態有改變過, 則發送更新  ex:本來active 而燃料用光導致無法active時
			if(this.isActive != this.isBuilding()) {
				this.isActive = this.isBuilding();
				
				//set render entity state
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord-1.5D, yCoord-2D, zCoord-1.5D, xCoord+1.5D, yCoord+1D, zCoord+1.5D);
				List renderEntityList = this.worldObj.getEntitiesWithinAABB(EntityRenderVortex.class, aabb);
				
	            for(int i = 0; i < renderEntityList.size(); i++) { 
	            	LogHelper.info("DEBUG : set render entity state "+this.isBuilding()+" "+renderEntityList.get(i)+xCoord+" "+yCoord+" "+zCoord);
	            	((EntityRenderVortex)renderEntityList.get(i)).setIsActive(this.isBuilding());
	            }
	            
				sendUpdate = true;
			}
		}
		else {	//client端, 修改render entity狀態
			//若狀態有改變過, 則發送更新  ex:本來active 而燃料用光導致無法active時
			if(this.isActive != this.isBuilding()) {
				this.isActive = this.isBuilding();
				
				//set render entity state
				AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord-1.5D, yCoord-2D, zCoord-1.5D, xCoord+1.5D, yCoord+1D, zCoord+1.5D);
				List renderEntityList = this.worldObj.getEntitiesWithinAABB(EntityRenderVortex.class, aabb);
				
	            for(int i = 0; i < renderEntityList.size(); i++) { 
	            	LogHelper.info("DEBUG : set render entity state "+this.isBuilding()+" "+renderEntityList.get(i)+xCoord+" "+yCoord+" "+zCoord);
	            	((EntityRenderVortex)renderEntityList.get(i)).setIsActive(this.isBuilding());
	            }
			}
		}
		
		//標紀要更新
		if(sendUpdate) {
//			int meta;
//			if(this.isBuilding()) { 
//				meta = 2;
//			}
//			else {
//				meta = 1;
//			}
//			
//			//更新方塊metadata
//			BlockGrudgeHeavy.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord, meta);
			//標記此方塊要更新, 以保證資料會存到硬碟
			this.markDirty();
		}
	}

	//計算fuel存量條
	public int getPowerRemainingScaled(int i) {
		return (powerRemained * i) / POWERMAX;
	}
	
	//計算建造時間 (換算成真實時間)
	public String getBuildTimeString() {
		//剩餘秒數 = (目標能量 - 目前能量) / (每tick增加能量) / 20
		int timeSec = (powerGoal - powerConsumed) / BUILDSPEED / 20;	//get time (單位: sec)		
		return FormatHelper.getTimeFormated(timeSec);
	}
	
	//getter
	public int getPowerConsumed() {
		return powerConsumed;
	}
	public int getPowerRemained() {
		return powerRemained;
	}
	public int getPowerGoal() {
		return powerGoal;
	}
	public int getBuildType() {
		return buildType;
	}
	public int getInvMode() {
		return invMode;
	}
	public int getSelectMat() {
		return selectMat;
	}
	public int getMatBuild(int id) {
		return matsBuild[id];
	}
	public int getMatStock(int id) {
		return matsStock[id];
	}
	
	//setter
	public void setPowerConsumed(int par1) {
		this.powerConsumed = par1;
	}
	public void setPowerRemained(int par1) {
		this.powerRemained = par1;
	}
	public void setPowerGoal(int par1) {
		this.powerGoal = par1;
	}
	public void setBuildType(int par1) {
		this.buildType = par1;
	}
	public void setInvMode(int par1) {
		this.invMode = par1;
	}
	public void setSelectMat(int par1) {
		this.selectMat = par1;
	}
	public void setMatBuild(int id, int par1) {
		this.matsBuild[id] = par1;
	}
	public void setMatStock(int id, int par1) {
		this.matsStock[id] = par1;
	}
	public void addMatBuild(int id, int par1)  {	//add a number to build
		this.matsBuild[id] += par1;
	}
	public void addMatStock(int id, int par1)  {	//add a number to stock
		this.matsStock[id] += par1;
	}

}
