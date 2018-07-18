package com.lulan.shincolle.tileentity;

import java.util.List;
import java.util.Random;

import com.lulan.shincolle.block.BlockVolCore;
import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/** Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 8min / 460800  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 1min / 57600
 * 	MaxMaterial / MaxFuelCost = 64*4 / 460800
 *  MinMaterial / MinFuelCost = 16*4 / 57600 = BaseCost(57600) CostPerMaterial(2100)
 */
public class TileEntityVolCore extends BasicTileInventory implements ITickable
{

	private Random rand = new Random();
	private boolean canWork;			//是否啟動中
	private boolean btnActive;			//是否按下啟動按鈕
	private int remainedPower = 0;		//剩餘燃料
	public static int CONSUMEDSPEED;	//power cost per tick
	public static int POWERMAX;			//max power storage
	public static int FUELMAGN;			//fuel value per grudge item
	private static final int[] ALLSLOTS = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};  //dont care side

	
	public TileEntityVolCore()
	{
		//0~8: fuel slots
		this.itemHandler = new CapaInventory(9, this);
		this.canWork = false;
		this.btnActive = false;
		this.syncTime = 0;

		POWERMAX = (int) ConfigHandler.tileVolCore[0];
		CONSUMEDSPEED = (int) ConfigHandler.tileVolCore[1];
		FUELMAGN = (int) ConfigHandler.tileVolCore[2];
	}
	
	@Override
	public String getRegName()
	{
		return BlockVolCore.TILENAME;
	}
	
	@Override
	public byte getGuiIntID()
	{
		return ID.Gui.VOLCORE;
	}
	
	@Override
	public byte getPacketID(int type)
	{
		switch (type)
		{
		case 0:
			return S2CGUIPackets.PID.TileVolCore;
		}
		
		return -1;
	}
	
	//依照輸出入口設定, 決定漏斗等裝置如何輸出入物品到特定slot中
	//注意: 此設定必須跟getCapability相同以免出現bug
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return ALLSLOTS;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);	//從nbt讀取方塊的xyz座標
        
        remainedPower = nbt.getInteger("power");
        btnActive = nbt.getBoolean("active");
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
			
		nbt.setInteger("power", remainedPower);
		nbt.setBoolean("active", btnActive);
		
		return nbt;
	}
	
	//判定物品是否能放入該格子, 用於canExtractItem等方法
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (stack != null)
		{
			Item item = stack.getItem();

			if(item == ModItems.Grudge || item == Item.getItemFromBlock(ModBlocks.BlockGrudge))
			{
				return true;
			}
		}
		
		return false;
	}

	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack item, EnumFacing face)
	{
		return true;
	}
	
	/** consume fuel item , return true = add fuel success */
	public boolean decrItemFuel()
	{
		ItemStack stack = null;
		Item item = null;
		boolean sendUpdate = false;
		int fuelx = 0;
		
		//get item in slot
		for (int i = 0; i < this.itemHandler.getSlots(); i++)
		{
			stack = getStackInSlot(i);
			
			if (stack != null)
			{
				item = stack.getItem();
				
				//get fuel value
				if (item == ModItems.Grudge)
				{
					fuelx = this.FUELMAGN;
				}
				else if (item == Item.getItemFromBlock(ModBlocks.BlockGrudge))
				{
					fuelx = this.FUELMAGN * 9;
				}
				
				//add disposable fuel (ex: coal, lava bucket, lava cell)
				if (fuelx > 0 && fuelx + this.remainedPower < this.POWERMAX)
				{
					stack.stackSize--;	//fuel item --
					this.remainedPower += fuelx;
					
					//若該物品用完, 用getContainerItem處理是否要清空還是留下桶子 ex: lava bucket -> empty bucket
					if (stack.stackSize <= 0)
					{
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
	
	private boolean isWorking()
	{
		return this.canWork && this.btnActive;
	}

	//方塊的流程進行方法: 方塊資料必須以markDirty標記block更新(更新meta值時), 以及讀寫NBT tag來保存
	@Override
	public void update()
	{
		//tick time
		this.syncTime++;
		
		//server side
		if (!world.isRemote)
		{
			boolean checkActive = this.isWorking();
			
			//check every 16 ticks
			if (this.syncTime % 16 == 0)
			{
				//fuel足夠, 啟動方塊功能
				if (this.remainedPower >= this.CONSUMEDSPEED)
				{
					this.canWork = true;
				}
				else
				{
					this.canWork = false;
				}
				
				//fuel--
				if (this.isWorking()) this.remainedPower -= this.CONSUMEDSPEED;
				
				//check every 32 ticks
				if (this.syncTime % 32 == 0)
				{
					//add item fuel
					decrItemFuel();
					
					if (this.isWorking())
					{
						volcoreFunction();
					}
					
					//check every 256 ticks
					if (this.syncTime % 256 == 0)
					{
						if (this.isWorking())
						{
							//TODO show emotes
							double dx = pos.getX() + 0.5D;
							double dy = pos.getY() + 2.5D;
							double dz = pos.getZ() + 0.5D;
							int emotes;
							
	                		switch (this.world.rand.nextInt(5))
	                		{
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
	                		
	                		AxisAlignedBB box = new AxisAlignedBB(dx - 6D, dy - 6D, dz - 6D, dx + 6D, dy + 6D, dz + 6D);
	                        List<BasicEntityShip> slist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, box);
	                		
	                		EntityHelper.applyEmotesAOE(slist, emotes);
						}
					}//end every 256 ticks
				}//end every 32 ticks
			}//end every 16 ticks
			
			//need sync
			if (checkActive != this.isWorking())
			{
				this.sendSyncPacket();
			}
		}//end server side
		//client side
		else
		{
			//valid tile
			if (this.world.getBlockState(this.pos).getBlock() != ModBlocks.BlockVolCore)
			{
				this.invalidate();
				return;
			}
			
			//spawn bubble particle
			if ((this.syncTime & 15) == 0 && this.remainedPower > this.CONSUMEDSPEED && this.btnActive)
			{
				//particleSetting: 0:all, 1:decr, 2:min
				int maxpar = (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 25;
				
				for (int i = 0; i < maxpar; i++)
				{
					//random position
					double dx = pos.getX() + 0.5D + rand.nextFloat() * 13F - 6.5F;
					double dy = pos.getY() + 1.5D + rand.nextFloat() * 13F - 4.5F;
					double dz = pos.getZ() + 0.5D + rand.nextFloat() * 13F - 6.5F;
					
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
	private void volcoreFunction()
	{
		//check nearby water block, start to function (every 32 ticks)
		if (BlockHelper.checkBlockNearbyIsLiquid(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 1))
		{
			//get ship entity
			double dx = pos.getX() + 0.5D;
			double dy = pos.getY() + 0.5D;
			double dz = pos.getZ() + 0.5D;
			
            AxisAlignedBB box = new AxisAlignedBB(dx - 6D, dy - 6D, dz - 6D, dx + 6D, dy + 6D, dz + 6D);
            List<BasicEntityShip> slist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, box);
            
            if (slist != null)
            {
                for (BasicEntityShip s : slist)
                {
                	//check ship is out of combat and in liquid
                	if (EntityHelper.checkShipOutOfCombat(s) && EntityHelper.checkEntityIsInLiquid(s))
                	{
                		//restore HP
                		if (s.getHealth() < s.getMaxHealth())
                		{
                			s.heal(s.getMaxHealth() * 0.01F + 4F);
                		}
                		
                		//restore Morale
                		if (s.getMorale() < (int) (ID.Morale.L_Excited * 1.8F))
                		{
                			s.addMorale(80);
                		}
                	}//end ship OOC
                }//end loop all ship
            }//end get list
		}
		//no water, ignite nearby entity
		else
		{
			//get ship entity
			double dx = pos.getX() + 0.5D;
			double dy = pos.getY() + 0.5D;
			double dz = pos.getZ() + 0.5D;
			
            AxisAlignedBB box = new AxisAlignedBB(dx - 6D, dy - 6D, dz - 6D, dx + 6D, dy + 6D, dz + 6D);
            List<EntityLivingBase> slist = this.world.getEntitiesWithinAABB(EntityLivingBase.class, box);
            
            if (slist != null)
            {
                for (EntityLivingBase ent : slist)
                {
                	if (ent instanceof BasicEntityShip || ent instanceof BasicEntityMount ||
                		ent instanceof BasicEntityAirplane || ent instanceof BasicEntityShipHostile)
                	{
                		//ship immune fire, return
                		return;
                	}
                	else
                	{
                		//ignite target
                		ent.setFire(2);
                		
                		//hurt target
                		ent.attackEntityFrom(DamageSource.inFire, 4F);
                		
                		//show hot emotes
                		int emotes;
                		switch (this.world.rand.nextInt(5))
                		{
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
	public int getPowerRemainingScaled(int i)
	{
		return (remainedPower * i) / POWERMAX;
	}

	public int getPowerRemained()
	{
		return this.remainedPower;
	}

	public void setPowerRemained(int par1)
	{
		this.remainedPower = par1;
	}

	public int getPowerMax()
	{
		return POWERMAX;
	}
	
	/** FIELD相關方法
	 *  使其他mod或class也能存取該tile的內部值
	 *  ex: gui container可用get/setField來更新數值
	 *  
	 *  field id:
	 *  0:btnActive
	 */
	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.btnActive ? 1 : 0;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.btnActive = value == 0 ? false : true;
		break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 1;
	}

	
}

