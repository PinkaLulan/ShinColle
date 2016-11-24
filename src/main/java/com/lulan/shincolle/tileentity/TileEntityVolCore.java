package com.lulan.shincolle.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

/** Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 8min / 460800  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 1min / 57600
 * 	MaxMaterial / MaxFuelCost = 64*4 / 460800
 *  MinMaterial / MinFuelCost = 16*4 / 57600 = BaseCost(57600) CostPerMaterial(2100)
 */
public class TileEntityVolCore extends BasicTileInventory {

	private Random rand = new Random();
	public boolean isActive;		//是否啟動中
	private int remainedPower = 0;	//剩餘燃料
	private static int consumeSpeed = 16;  			//power cost per SECOND
	private static final int MAXPOWER = 9600; 	//max power storage
	private static final int[] ALLSLOTS = new int[] {0, 1, 2, 3, 4, 5};  //dont care side

	
	public TileEntityVolCore() {
		//inventory 9 slots
		this.slots = new ItemStack[9];
		this.isActive = false;
		this.syncTime = 0;
		
		if(ConfigHandler.easyMode) {
			consumeSpeed = 2;
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
		return this.hasCustomInventoryName() ? this.customName : "container."+Reference.MOD_ID+":VolCore";
	}

	//是否可以右鍵點開方塊
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		//由於會有多個tile entity副本, 要先確認座標相同的副本才能使用
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		else {	//確認player要在該tile entity 8格內, 以免超出讀取範圍 or 產生其他不明bug
			return player.getDistanceSq(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D) <= 64;
		}
	}

	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);	//從nbt讀取方塊的xyz座標

        NBTTagList list = compound.getTagList("Items", 10);	//抓nbt tag: Items (此為類型10:TagCompound)
        
        for(int i = 0; i < list.tagCount(); i++) {			//將tag列出的所有物品抓出來
            NBTTagCompound item = list.getCompoundTagAt(i);
            byte sid = item.getByte("Slot");
            
            if(sid >= 0 && sid < slots.length) {	//讀取nbt紀錄的物品, 生成到各slot中 
            	slots[sid] = ItemStack.loadItemStackFromNBT(item);
            }
        }

        remainedPower = compound.getInteger("power");
        isActive = compound.getBoolean("active");
    }
	
	//將資料寫進nbt
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagList list = new NBTTagList();
		compound.setTag("Items", list);
		
		for(int i = 0; i < slots.length; i++) {		//將slots[]資料寫進nbt
			if(slots[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);	//在tag: Slot下儲存資料i
				slots[i].writeToNBT(item);		//在tag: Slot下儲存slots[i]資料
				list.appendTag(item);			//增加下一個欄位
			}
		}
			
		compound.setInteger("power", remainedPower);
		compound.setBoolean("active", isActive);
	}
	
	//判定物品是否能放入該格子, 用於canExtractItem等方法
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if(itemstack != null) {
			Item item = itemstack.getItem();

			if(item == ModItems.Grudge || item == Item.getItemFromBlock(ModBlocks.BlockGrudge)) {
				return true;
			}
		}
		
		return false;
	}

	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return true;
	}
	
	/** consume fuel item , return true = add fuel success */
	public boolean decrItemFuel() {
		ItemStack stack = null;
		Item item = null;
		boolean sendUpdate = false;
		int fuelx = 0;
		
		//get item in slot
		for(int i = 0; i < this.slots.length; i++) {
			stack = getStackInSlot(i);
			
			if(stack != null) {
				item = stack.getItem();
				
				//get fuel value
				if(item == ModItems.Grudge) {
					fuelx = ConfigHandler.volcoreGrudgeValue;
				}
				else if(item == Item.getItemFromBlock(ModBlocks.BlockGrudge)) {
					fuelx = ConfigHandler.volcoreGrudgeValue * 9;
				}
				
				//add disposable fuel (ex: coal, lava bucket, lava cell)
				if(fuelx > 0 && fuelx + this.remainedPower < this.MAXPOWER) {
					stack.stackSize--;	//fuel item --
					this.remainedPower += fuelx;
					
					//若該物品用完, 用getContainerItem處理是否要清空還是留下桶子 ex: lava bucket -> empty bucket
					if(stack.stackSize <= 0) {
						stack = stack.getItem().getContainerItem(stack);
					}
					
					//update slot
					setInventorySlotContents(i, stack);
					sendUpdate = true;
					break;	//only consume 1 fuel every tick
				}//end disposable fuel
			}//end stack != null
		}//end all slots for loop
		
		return sendUpdate;
	}

	//方塊的流程進行方法: 方塊資料必須以markDirty標記block更新(更新meta值時), 以及讀寫NBT tag來保存
	@Override
	public void updateEntity() {
		//tick time
		this.syncTime++;
		
		//server side
		if(!worldObj.isRemote) {
			boolean checkActive = this.isActive;
			
			//check every 16 ticks
			if(this.syncTime % 16 == 0) {
				//fuel足夠, 啟動方塊功能
				if(this.remainedPower >= this.consumeSpeed) {
					//fuel--
					this.remainedPower -= this.consumeSpeed;
					this.isActive = true;
				}
				else {
					this.isActive = false;
				}
				
				//check every 32 ticks
				if(this.syncTime % 32 == 0) {
					//add item fuel
					decrItemFuel();
					
					if(this.isActive) {
						volcoreFunction();
					}
					
					//check every 256 ticks
					if(this.syncTime % 256 == 0) {
						if(this.isActive) {
							//TODO show emotes
							double dx = xCoord + 0.5D;
							double dy = yCoord + 2.5D;
							double dz = zCoord + 0.5D;
							int emotes;
							
	                		switch(this.worldObj.rand.nextInt(5)) {
	                		case 0:
	                			emotes = 2;  //panic
	                			break;
	                		case 1:
	                			emotes = 30;  //pif
	                			break;
	                		case 2:
	                			emotes = 10;  //spin
	                			break;
	            			default:
	            				emotes = 27;  //-w-
	            				break;
	                		}
	                		
	                		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(dx - 4D, dy - 2D, dz - 4D, dx + 4D, dy + 6D, dz + 4D);
	                        List<BasicEntityShip> slist = this.worldObj.getEntitiesWithinAABB(BasicEntityShip.class, box);
	                		
	                		EntityHelper.applyEmotesAOE(slist, emotes);
						}
					}//end every 256 ticks
				}//end every 32 ticks
			}//end every 16 ticks
			
			//need sync
			if(checkActive != this.isActive) {
				this.sendSyncPacket();
			}
		}//end server side
		//client side
		else {
			//spawn bubble particle
			if(this.isActive && this.syncTime % 32 == 0) {
				for(int i = 0; i < 20; i++) {
					//random position
					double dx = xCoord + 0.5D + rand.nextFloat() * 8F - 4F;
					double dy = yCoord + 2.5D + rand.nextFloat() * 8F - 4F;
					double dz = zCoord + 0.5D + rand.nextFloat() * 8F - 4F;
					
					ParticleHelper.spawnAttackParticleAt(dx, dy, dz, 0D, 0.05D, 0D, (byte)37);
				}
			}
		}
	}
	
	/** function:
	 *  restore morale/HP or ignite nearby entity, SERVER SIDE ONLY
	 *  
	 *  no owner checking on ship, all ships in range will get buff
	 */
	private void volcoreFunction() {
		//check nearby water block, start to function (every 32 ticks)
		if(BlockHelper.checkBlockNearbyHasLiquid(this.worldObj, this.xCoord, this.yCoord, this.zCoord, 1)) {
			//get ship entity
			double dx = xCoord + 0.5D;
			double dy = yCoord + 0.5D;
			double dz = zCoord + 0.5D;
			
            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(dx - 4D, dy - 2D, dz - 4D, dx + 4D, dy + 6D, dz + 4D);
            List<BasicEntityShip> slist = this.worldObj.getEntitiesWithinAABB(BasicEntityShip.class, box);
            
            if(slist != null) {
                for(BasicEntityShip s : slist) {
                	//check ship is out of combat and in liquid
                	if(EntityHelper.checkShipOutOfCombat(s) && EntityHelper.checkEntityIsInLiquid(s)) {
                		//restore HP
                		if(s.getHealth() < s.getMaxHealth()) {
                			s.heal(s.getMaxHealth() * 0.01F + 4F);
                		}
                		
                		//restore Morale
                		int m = s.getStateMinor(ID.M.Morale);
                		if(m < 8100) {
                			s.setStateMinor(ID.M.Morale, m + 65);
                		}
                	}//end ship OOC
                }//end loop all ship
            }//end get list
		}
		//no water, ignite nearby entity
		else {
			//get ship entity
			double dx = xCoord + 0.5D;
			double dy = yCoord + 0.5D;
			double dz = zCoord + 0.5D;
			
            AxisAlignedBB box = AxisAlignedBB.getBoundingBox(dx - 4D, dy - 2D, dz - 4D, dx + 4D, dy + 6D, dz + 4D);
            List<EntityLivingBase> slist = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box);
            
            if(slist != null) {
                for(EntityLivingBase ent : slist) {
                	if(ent instanceof BasicEntityShip || ent instanceof BasicEntityMount ||
                	   ent instanceof BasicEntityAirplane || ent instanceof BasicEntityShipHostile) {
                		//not for ship
                	}
                	else {
                		//ignite target
                		ent.setFire(2);
                		
                		//hurt target
                		ent.attackEntityFrom(DamageSource.inFire, 4F);
                		
                		//show hot emotes
                		int emotes;
                		switch(this.worldObj.rand.nextInt(5)) {
                		case 0:
                			emotes = 12;  //omg
                			break;
                		case 1:
                			emotes = 28;  //-o-
                			break;
                		case 2:
                			emotes = 0;  //drop
                			break;
            			default:
            				emotes = 2;  //panic
            				break;
                		}
                		
                		//send emotes packet
                		TargetPoint point = new TargetPoint(ent.dimension, ent.posX, ent.posY, ent.posZ, 48D);
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(ent, 36, ent.height * 0.75F, 0, emotes), point);
                	}
                }
            }//end hit list
		}//end no water
	}

	//計算fuel存量條
	public int getPowerRemainingScaled(int i) {
		return (remainedPower * i) / MAXPOWER;
	}

	public int getPowerRemained() {
		return this.remainedPower;
	}

	public void setPowerRemained(int par1) {
		this.remainedPower = par1;
	}

	@Override
	public int getFuelSlotMin() {
		return 0;
	}

	@Override
	public int getFuelSlotMax() {
		return 8;
	}

	public int getPowerMax() {
		return MAXPOWER;
	}

	
}

