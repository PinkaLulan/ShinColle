package com.lulan.shincolle.tileentity;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.lulan.shincolle.block.BlockCrane;
import com.lulan.shincolle.block.ItemBlockWaypoint;
import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaInventoryExtend;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EnchantHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.InventoryHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TileEntityHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

/**
 * crane function
 * 
 * mode redstone: 0:no signal, 1:emit one pulse on ending, 2:NYI 
 * mode liquid: 0:none, 1:load liquid to ship, 2:unload liquid from ship
 * mode EU: 0:none, 1:load, 2:unload
 */
public class TileEntityCrane extends BasicTileInventory implements ITileWaypoint, ITickable
{
    
    private int tick, partDelay, modeItem, modeRedstone, tickRedstone, craneTime, modeLiquid,
                modeEnergy, rateLiquid, rateEU;
    private boolean isActive, isPaired, checkMetadata, checkOredict, checkNbt, enabLoad, enabUnload;
    private BlockPos lastPos, nextPos, chestPos;
    
    /** craneMode:
     *  0:     no wait:     no trans action -> stop craning immediately
     *  1:     until full:  L: ship full        U: chest full
     *  2:     until empty: L: chest empty      U: ship empty
     *  3:     excess:      L: ship excess      U: chest excess
     *  4:     remain:      L: chest remain     U: ship remain
     *  5~9:   wait:        16/32/48/64/80 ticks
     *  10~14: wait:        5/10/15/20/25 sec
     *  15~19: wait:        1/2/3/4/5 min
     *  20~24: wait:        10/20/30/40/50 min
     */
    private int craneMode;
    
    //target
    public EntityPlayer owner;
    private BasicEntityShip ship;
    private TileEntity chest;
    
    //fluid tank
    protected FluidTank tank;
    
    
    public TileEntityCrane()
    {
        super();
        
        //0~8: loading items, 9~17: unloading items
        this.itemHandler = new CapaInventory(18, this, ID.InvName.ItemSlot);
        this.ship = null;
        this.chest = null;
        this.isActive = false;
        this.isPaired = false;
        this.enabLoad = true;
        this.enabUnload = true;
        this.checkMetadata = false;
        this.checkOredict = false;
        this.checkNbt = false;
        this.craneMode = 0;
        this.tick = 0;
        this.partDelay = 0;
        this.modeItem = 0;
        this.modeRedstone = 0;
        this.modeLiquid = 0;
        this.modeEnergy = 0;
        this.rateLiquid = 0;
        this.rateEU = 0;
        this.tickRedstone = 0;
        this.lastPos = BlockPos.ORIGIN;
        this.nextPos = BlockPos.ORIGIN;
        this.chestPos = BlockPos.ORIGIN;
        
        //tank
        this.tank = new FluidTank(ConfigHandler.tileCrane[0]);
        this.tank.setTileEntity(this);
        
        //EU storage TODO NYI
    }
    
    @Override
    public String getRegName()
    {
        return BlockCrane.TILENAME;
    }
    
    @Override
    public byte getGuiIntID()
    {
        return ID.Gui.CRANE;
    }
    
    @Override
    public byte getPacketID(int type)
    {
        switch (type)
        {
        case 0:
            return S2CGUIPackets.PID.TileCrane;
        }
        
        return -1;
    }
    
    //讀取nbt資料
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        //load values
        this.isActive = nbt.getBoolean("active");
        this.isPaired = nbt.getBoolean("paired");
        this.enabLoad = nbt.getBoolean("load");
        this.enabUnload = nbt.getBoolean("unload");
        this.checkMetadata = nbt.getBoolean("meta");
        this.checkOredict = nbt.getBoolean("dict");
        this.checkNbt = nbt.getBoolean("nbt");
        this.craneMode = nbt.getInteger("mode");
        this.modeItem = nbt.getInteger("imode");
        this.modeRedstone = nbt.getInteger("rmode");
        this.modeLiquid = nbt.getInteger("lmode");
        this.modeEnergy = nbt.getInteger("emode");
        
        //load tank
        this.tank.readFromNBT(nbt);
        
        //load pos
        int[] pos =  nbt.getIntArray("chestPos");
        if (pos == null || pos.length != 3) this.chestPos = BlockPos.ORIGIN;
        else this.chestPos = new BlockPos(pos[0], pos[1], pos[2]);
        
        pos =  nbt.getIntArray("lastPos");
        if (pos == null || pos.length != 3) this.lastPos = BlockPos.ORIGIN;
        else this.lastPos = new BlockPos(pos[0], pos[1], pos[2]);
        
        pos =  nbt.getIntArray("nextPos");
        if (pos == null || pos.length != 3) this.nextPos = BlockPos.ORIGIN;
        else this.nextPos = new BlockPos(pos[0], pos[1], pos[2]);
    }
    
    //將資料寫進nbt
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        //save values
        nbt.setBoolean("active", this.isActive);
        nbt.setBoolean("paired", this.isPaired);
        nbt.setBoolean("load", this.enabLoad);
        nbt.setBoolean("unload", this.enabUnload);
        nbt.setBoolean("meta", this.checkMetadata);
        nbt.setBoolean("dict", this.checkOredict);
        nbt.setBoolean("nbt", this.checkNbt);
        nbt.setInteger("mode", this.craneMode);
        nbt.setInteger("imode", this.modeItem);
        nbt.setInteger("rmode", this.modeRedstone);
        nbt.setInteger("lmode", this.modeLiquid);
        nbt.setInteger("emode", this.modeEnergy);
        
        //save tank
        this.tank.writeToNBT(nbt);

        //save pos
        if (this.lastPos != null) nbt.setIntArray("lastPos", new int[] {this.lastPos.getX(), this.lastPos.getY(), this.lastPos.getZ()});
        else nbt.setIntArray("lastPos", new int[] {0, 0, 0});
        
        if (this.nextPos != null) nbt.setIntArray("nextPos", new int[] {this.nextPos.getX(), this.nextPos.getY(), this.nextPos.getZ()});
        else nbt.setIntArray("nextPos", new int[] {0, 0, 0});
        
        if (this.chestPos != null) nbt.setIntArray("chestPos", new int[] {this.chestPos.getX(), this.chestPos.getY(), this.chestPos.getZ()});
        else nbt.setIntArray("chestPos", new int[] {0, 0, 0});
        
        return nbt;
    }
    
    /** disable ItemHandler, enable FluidHandler */
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        //disable itemhandler for external use
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return false;
        //enable fluidhandler for external use
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
    
    /** disable ItemHandler, enable FluidHandler */
    @Override
    @Nullable
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        //disable itemhandler for external use
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return null;
        //enable fluidhandler for external use
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != EnumFacing.DOWN) return (T) tank;
        return super.getCapability(capability, facing);
    }
    
    /** check paired chest */
    public void checkPairedChest()
    {
        if (this.isPaired)
        {
            boolean shouldReset = (this.chest != null);
            
            //check chest valid
            TileEntity tile = TileEntityHelper.getTileInventory(this.world, this.chestPos);
            this.chest = tile;
            
            //chest lost, reset
            if (shouldReset && this.chest == null)
            {
                this.clearPairedChest();
                this.sendSyncPacket();
            }
        }
    }
    
    public void clearPairedChest()
    {
        this.chest = null;
        this.isPaired = false;
        this.chestPos = BlockPos.ORIGIN;
    }
    
    @Override
    public void setNextWaypoint(BlockPos pos)
    {
        if (pos != null)
        {
            this.nextPos = pos;
            
            //sync to client
            if (!this.world.isRemote) this.sendSyncPacket();
        }
    }

    @Override
    public BlockPos getNextWaypoint()
    {
        return this.nextPos;
    }
    
    @Override
    public void setLastWaypoint(BlockPos pos)
    {
        if (pos != null)
        {
            this.lastPos = pos;
            
            //sync to client
            if (!this.world.isRemote) this.sendSyncPacket();
        }
    }

    @Override
    public BlockPos getLastWaypoint()
    {
        return this.lastPos;
    }
    
    @Override
    public void update()
    {
        //server side
        if (!this.world.isRemote)
        {
            boolean update = false;
            this.tick++;
            
            //redstone signal
            if (this.tickRedstone > 0)
            {
                this.tickRedstone--;
                if (this.tickRedstone <= 0)
                {
                    this.world.notifyNeighborsOfStateChange(this.pos, ModBlocks.BlockCrane, false);
                }
            }
            
            //get owner for sync packet for client GUI display
            if ((this.tick & 31) == 0 && this.owner == null && this.playerUID > 0)
            {
                this.owner = EntityHelper.getEntityPlayerByUID(this.playerUID);
            }
            
            //can work
            if (this.isActive && this.isPaired)
            {
                //check every 16 ticks
                if (this.tick > 64 && (this.tick & 15) == 0)
                {
                    //check chest and ship
                    checkPairedChest();
                    checkCraningShip();
                    
                    //set redstone tick
                    if (this.modeRedstone == 1 && this.ship != null)
                    {
                        this.tickRedstone = 18;
                        this.world.notifyNeighborsOfStateChange(this.pos, ModBlocks.BlockCrane, false);
                    }
                    
                    //crane <-> chest liquid transfer
                    this.applyPreLiquidTransfer(this.modeLiquid);
                    
                    if (this.chest != null && this.ship != null && this.ship.getStateHandler().getStateInt(ID.Keys.CraneState) == 2)
                    {
                        //work: 0:load item, 1:unload item, 2:liquid, 3:EU
                        boolean[] workList = new boolean[4];
                        
                        try
                        {
                            //check item loading
                            if (this.enabLoad)
                            {
                                workList[0] = applyItemTransfer(true);
                            }
                            
                            //check item unloading
                            if (this.enabUnload)
                            {
                                workList[1] = applyItemTransfer(false);
                            }
                            
                            //check liquid transport
                            if (this.modeLiquid != 0 && this.rateLiquid > 0)
                            {
                                workList[2] = applyLiquidTransfer(this.modeLiquid);
                            }
                            else
                            {
                                workList[2] = false;
                            }
                            
                            //check EU transport
                            if (CommonProxy.activeIC2 && this.modeEnergy != 0 && this.rateEU > 0)
                            {
                                workList[3] = applyEnergyTransfer(this.modeEnergy);
                            }
                            else
                            {
                                workList[3] = false;
                            }
                            
                            //add exp to transport ship, every work +X exp to ship
                            if (this.ship != null && this.ship.getShipType() == ID.ShipType.TRANSPORT)
                            {
                                for (boolean b : workList)
                                {
                                    this.ship.addShipExp(ConfigHandler.expGain[6]);
                                }
                            }
                            
                            //apply particle on ship
                              TargetPoint tp = new TargetPoint(this.ship.dimension, this.ship.posX, this.ship.posY, this.ship.posZ, 48D);
                              CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.ship, 22, 0D, 0.1D, 0D), tp);
                            
                            //check craning ending
                            if (checkCraneEnding(workList))
                            {
                                //emit redstone signal
                                if (this.modeRedstone == 2)
                                {
                                    this.tickRedstone = 2;
                                    this.world.notifyNeighborsOfStateChange(this.pos, ModBlocks.BlockCrane, false);
                                }
                                
                                //set crane state
                                this.ship.setStateMinor(ID.M.CraneState, 0);
                                this.ship.setStateTimer(ID.T.CraneTime, 0);
                                
                                //set next waypoint
                                    if (EntityHelper.applyNextWaypoint(this, this.ship, false, 0))
                                    {
                                        //set follow dist
                                        this.ship.setStateMinor(ID.M.FollowMin, 2);
                                    }
                                    
                                    //player sound
                                    this.ship.playSound(ModSounds.SHIP_BELL, ConfigHandler.volumeShip * 1.5F, this.ship.getRNG().nextFloat() * 0.3F + 1F);
                                    
                                    //clear ship
                                    this.ship = null;
                            }
                        }
                        catch (Exception e)
                        {
                            LogHelper.info("EXCEPTION: ship loading/unloading fail: "+e);
                            e.printStackTrace();
                            return;
                        }
                    }
                }//end 16 ticks
            }//is active
            
            //update checking
            if ((this.tick & 127) == 0)
            {
                //valid position
                if (this.chestPos.getY() > 0 || this.lastPos.getY() > 0 || this.nextPos.getY() > 0)
                {
                    update = true;
                }
            }
            
            //can update
            if (update)
            {
                sendSyncPacket();
            }
        }//end server side
        //client side
        else
        {
            //valid tile
            if (this.world.getBlockState(this.pos).getBlock() != ModBlocks.BlockCrane)
            {
                this.invalidate();
                return;
            }
            
            this.tick++;
            if (this.partDelay > 0) this.partDelay--;
            
            //craning particle
            if (this.isActive && this.ship != null && this.partDelay <= 0)
            {
                this.partDelay = 128;
                
                double len = this.pos.getY() - this.ship.posY - 1D;
                if (len < 1D) len = 1D;
                
                ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()-1D, pos.getZ()+0.5D,
                                                                            len, 0D, 0.25D, (byte) 40);
            }
                
            //check every 16 ticks
            if ((this.tick & 15) == 0)
            {
                //player hold waypoint or target wrench
                EntityPlayer player = ClientProxy.getClientPlayer();
                ItemStack item = player.inventory.getCurrentItem();
                
                //if holding pointer, wrench, waypoint
                if (item != null && (item.getItem() instanceof ItemBlockWaypoint || item.getItem() == ModItems.TargetWrench ||
                    (item.getItem() instanceof PointerItem && item.getItemDamage() < 3)))
                {
                    //next point mark
                    if (this.nextPos.getY() > 0)
                    {
                        double dx = this.nextPos.getX() - this.pos.getX();
                        double dy = this.nextPos.getY() - this.pos.getY();
                        double dz = this.nextPos.getZ() - this.pos.getZ();
                        dx *= 0.01D;
                        dy *= 0.01D;
                        dz *= 0.01D;
                                
                        ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D,
                                                                                        dx, dy, dz, (byte) 38);
                    }
                    
                    //paired chest mark
                    if (this.chestPos.getY() > 0)
                    {
                        double dx = this.chestPos.getX() - this.pos.getX();
                        double dy = this.chestPos.getY() - this.pos.getY();
                        double dz = this.chestPos.getZ() - this.pos.getZ();
                        dx *= 0.01D;
                        dy *= 0.01D;
                        dz *= 0.01D;

                        ParticleHelper.spawnAttackParticleAt(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D,
                                                                                        dx, dy, dz, (byte) 39);
                    }
                    
                    //every 32 ticks
                    if ((this.tick & 31) == 0)
                    {
                        //draw point text
                        if (this.lastPos.getY() > 0 || this.nextPos.getY() > 0 || this.chestPos.getY() > 0)
                        {
                            String name = "";
                            String postext1 = "";
                            String postext2 = "";
                            String postext3 = "";
                            int len0 = 0;
                            int len1 = 0;
                            int len2 = 0;
                            int len3 = 0;
                            
                            if (this.owner != null)
                            {
                                name = TextFormatting.GREEN + this.owner.getName();
                                len0 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(name);
                            }
                            
                            postext1 = "F: " + TextFormatting.LIGHT_PURPLE + this.lastPos.getX() + ", " + this.lastPos.getY() + ", " + this.lastPos.getZ();
                            len1 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext1);
                            len1 = len1 > len0 ? len1 : len0;
                            postext2 = "T: " + TextFormatting.AQUA + this.nextPos.getX() + ", " + this.nextPos.getY() + ", " + this.nextPos.getZ();
                            len2 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext2);
                            if (len1 < len2) len1 = len2;
                            postext3 = "C: " + TextFormatting.YELLOW + this.chestPos.getX() + ", " + this.chestPos.getY() + ", " + this.chestPos.getZ();
                            len3 = ClientProxy.getMineraft().getRenderManager().getFontRenderer().getStringWidth(postext3);
                            if (len1 < len3) len1 = len3;
                            postext1 = name + "\n" + TextFormatting.WHITE + postext1 + "\n" + TextFormatting.WHITE + postext2 + "\n" + TextFormatting.WHITE + postext3;
                            
                            ParticleHelper.spawnAttackParticleAt(postext1, this.pos.getX()+0.5D, this.pos.getY()+1.9D, this.pos.getZ()+0.5D, (byte) 0, 4, len1+1);
                        }
                    }//end every 32 ticks
                }//end holding item
            }//end every 16 ticks
        }//end client side
    }
    
    /**
     * workList: TRUE = has work to do
     * return: TRUE = stop craning
     *         FALSE = continue craning
     */
    private boolean checkCraneEnding(boolean[] workList)
    {
        switch (this.craneMode)
        {
        case 0:  //no wait
            for (boolean b : workList)
            {
                if (b) return false;
            }
            
            return true;
        case 1:  //until full
            return isInventoryFull();
        case 2:  //until empty
            return isInventoryEmpty();
        case 3:  //excess
            if (workList[2] || workList[3] || !isInventoryExcess()) return false;
            return true;
        case 4:  //remain
            if (workList[2] || workList[3] || !isInventoryRemain()) return false;
            return true;
        default: //wait
            if (this.ship.getStateTimer(ID.T.CraneTime) >= getWaitTime(this.craneMode))
                return true;
            return false;
        }
    }
    
    /** check wait mode: until full<br>
     *    loading: ship full<br>
     *    unloading: chest full<br>
     *    fluid/EU loading: all container in ship is full or no container<br>
     *    fluid/EU unloading: all container in chest is full or no container<br>
     *  
     *  @return true = is full
     */
    private boolean isInventoryFull()
    {
        boolean[] fullList = new boolean[6];
        
        //loading item: check ship full
        if (this.enabLoad && this.ship != null) fullList[0] = InventoryHelper.checkInventoryFull(this.ship.getCapaShipInventory());
        else fullList[0] = true;
        
        //unloading item: check chest full
        if (this.enabUnload && this.chest != null) fullList[1] = InventoryHelper.checkInventoryFullFromObj(this.chest);
        else fullList[1] = true;
        
        //loading fluid: check ship full
        if (this.modeLiquid == 1 && this.tank != null)
        {
            FluidStack fs = this.tank.getFluid() == null ? null : this.tank.getFluid().copy();
            if (this.ship != null) fullList[2] = InventoryHelper.checkInventoryFluidContainer(this.ship.getCapaShipInventory(), fs, true);
            else fullList[2] = true;
        }
        else
        {
            fullList[2] = true;
        }
        
        //unloading fluid: check chest full
        if (this.modeLiquid == 2 && this.tank != null)
        {
            FluidStack fs = this.tank.getFluid() == null ? null : this.tank.getFluid().copy();
            if (this.chest != null) fullList[3] = InventoryHelper.checkInventoryFluidContainerFromObj(this.chest, fs, true);
            else fullList[3] = true;
        }
        else
        {
            fullList[3] = true;
        }
        
        //loading EU: check ship full TODO
        fullList[4] = true;
        
        //unloading EU: check chest full TODO
        fullList[5] = true;
        
        //check all target is full
        for (boolean isFull : fullList)
        {
            if (!isFull) return false;
        }
        
        return true;
    }
    
    /** check wait mode: until empty
     *  loading: chest empty
     *  unloading: ship empty
     *  fluid/EU loading: all container in chest is empty or no container
     *  fluid/EU unloading: all container in ship is empty or no container
     */
    private boolean isInventoryEmpty()
    {
        boolean[] emptyList = new boolean[6];
        
        //loading item: check chest empty
        if (this.enabLoad && this.chest != null) emptyList[0] = InventoryHelper.checkInventoryEmptyFromObj(this.chest, this.getItemstackTemp(true), this.getItemMode(true), checkMetadata, checkNbt, checkOredict);
        else emptyList[0] = true;
        
        //unloading item: check ship empty
        if (this.enabUnload && this.ship != null) emptyList[1] = InventoryHelper.checkInventoryEmpty(this.ship.getCapaShipInventory(), this.getItemstackTemp(false), this.getItemMode(false), checkMetadata, checkNbt, checkOredict);
        else emptyList[1] = true;
        
        //loading fluid: check chest empty
        if (this.modeLiquid == 1 && this.tank != null)
        {
            FluidStack fs = this.tank.getFluid() == null ? null : this.tank.getFluid().copy();
            if (this.chest != null) emptyList[2] = InventoryHelper.checkInventoryFluidContainer(this.chest, fs, false);
            else emptyList[2] = true;
        }
        else
        {
            emptyList[2] = true;
        }
        
        //unloading fluid: check ship empty
        if (this.modeLiquid == 2 && this.tank != null)
        {
            FluidStack fs = this.tank.getFluid() == null ? null : this.tank.getFluid().copy();
            if (this.ship != null) emptyList[3] = InventoryHelper.checkInventoryFluidContainer(this.ship.getCapaShipInventory(), fs, false);
            else emptyList[3] = true;
        }
        else
        {
            emptyList[3] = true;
        }
        
        //loading EU: check chest empty TODO
        emptyList[4] = true;
        
        //unloading EU: check ship empty TODO
        emptyList[5] = true;
        
        //check all target is empty
        for (boolean isEmpty : emptyList)
        {
            if (!isEmpty) return false;
        }
        
        return true;
    }
    
    /** check wait mode: excess stacks
     *  loading: ship excess
     *  unloading: chest excess
     *  fluid/EU loading: same with no waiting
     *  fluid/EU unloading: same with no waiting
     */
    private boolean isInventoryExcess()
    {
        boolean[] excessList = new boolean[2];
        
        //loading item: check ship excess
        if (this.enabLoad && this.ship != null) excessList[0] = InventoryHelper.checkInventoryAmount(this.ship.getCapaShipInventory(), this.getItemstackTemp(true), this.getItemMode(true), checkMetadata, checkNbt, checkOredict, true);
        else excessList[0] = true;
        
        //unloading item: check chest excess
        if (this.enabUnload && this.chest != null) excessList[1] = InventoryHelper.checkInventoryAmount(this.chest, this.getItemstackTemp(false), this.getItemMode(false), checkMetadata, checkNbt, checkOredict, true);
        else excessList[1] = true;

        //check all work is done
        for (boolean isExcess : excessList)
        {
            if (!isExcess) return false;
        }
        
        return true;
    }
    
    /** check wait mode: remain stacks
     *  loading: chest remain
     *  unloading: ship remain
     *  fluid/EU loading: same with no waiting
     *  fluid/EU unloading: same with no waiting
     */
    private boolean isInventoryRemain()
    {
        boolean[] remainList = new boolean[2];
        
        //loading item: check ship excess
        if (this.enabLoad && this.chest != null) remainList[0] = InventoryHelper.checkInventoryAmount(this.chest, this.getItemstackTemp(true), this.getItemMode(true), checkMetadata, checkNbt, checkOredict, false);
        else remainList[0] = true;
        
        //unloading item: check chest excess
        if (this.enabUnload && this.ship != null) remainList[1] = InventoryHelper.checkInventoryAmount(this.ship.getCapaShipInventory(), this.getItemstackTemp(false), this.getItemMode(false), checkMetadata, checkNbt, checkOredict, false);
        else remainList[1] = true;
        
        //check all work is done
        for (boolean isRemain : remainList)
        {
            if (!isRemain) return false;
        }
        
        return true;
    }
    
    /**
     * transfer liquid: crane tank <-> inventory
     * 
     * mode: 1: chest -> ship
     *       2: ship -> chest
     */
    private void applyPreLiquidTransfer(int mode)
    {
        if (this.chest != null)
        {
            IFluidHandler fh;
            FluidStack fs;
            boolean checkNextChest = true;
            
            //get fluid from chest inventory
            if (mode == 1)
            {
                //check all fluid container in chest
                for (int i = 0; i < this.chest.getSizeInventory(); i++)
                {
                    fh = FluidUtil.getFluidHandler(this.chest.getStackInSlot(i));
                    
                    if (fh != null)
                    {
                        fs = FluidUtil.tryFluidTransfer(this.tank, fh, 64000, true);
                        
                        //only do 1 stack every 16 ticks
                        if (fs != null)
                        {
                            checkNextChest = false;
                            break;
                        }
                    }
                }
                
                //if chest is TileEntityChest, check nearby chest
                if (checkNextChest && this.chest instanceof TileEntityChest)
                {
                    TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) this.chest);
                    
                    if (chest2 != null)
                    {
                        //check all fluid container in chest
                        for (int i = 0; i < chest2.getSizeInventory(); i++)
                        {
                            fh = FluidUtil.getFluidHandler(chest2.getStackInSlot(i));
                            
                            if (fh != null)
                            {
                                fs = FluidUtil.tryFluidTransfer(this.tank, fh, 64000, true);
                                
                                //only do 1 stack every 16 ticks
                                if (fs != null) break;
                            }
                        }
                    }
                }
            }//end mode 1
            //transfer fluid to chest inventory
            else if (mode == 2 && this.tank.getFluid() != null)
            {
                //check all fluid container in chest
                for (int i = 0; i < this.chest.getSizeInventory(); i++)
                {
                    fh = FluidUtil.getFluidHandler(this.chest.getStackInSlot(i));
                    
                    if (fh != null)
                    {
                        fs = FluidUtil.tryFluidTransfer(fh, this.tank, 16000, true);
                        
                        //only do 1 stack every 16 ticks
                        if (fs != null)
                        {
                            checkNextChest = false;
                            break;
                        }
                    }
                }
                
                //if chest is TileEntityChest, check nearby chest
                if (checkNextChest && this.chest instanceof TileEntityChest)
                {
                    TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) this.chest);
                    
                    if (chest2 != null)
                    {
                        //check all fluid container in chest
                        for (int i = 0; i < chest2.getSizeInventory(); i++)
                        {
                            fh = FluidUtil.getFluidHandler(chest2.getStackInSlot(i));
                            
                            if (fh != null)
                            {
                                fs = FluidUtil.tryFluidTransfer(fh, this.tank, 16000, true);
                                
                                //only do 1 stack every 16 ticks
                                if (fs != null) break;
                            }
                        }
                    }
                }
            }//end mode 2
        }//end get chest
    }
    
    /**
     * liquid transport method, return true if some liquid is moved
     */
    private boolean applyLiquidTransfer(int mode)
    {
        //get fluid by simulatly drain
        FluidStack f1;
        FluidStack f2;
        boolean moved = false;
        
        //crane tank to ship inventory
        if (mode == 1)
        {
            //f1 = remaining fluid after fill, f2 = fluid before fill
            //get fluid and null check
            f1 = this.tank.getFluid();
            
            if (f1 == null) return false;
            else if (f1.amount <= 0)
            {
                this.tank.setFluid(null);
                return false;
            }
            
            //get fluid amount, max = this.rateLiquid
            f1 = f1.copy();
            f1.amount = Math.min(this.rateLiquid, f1.amount);
            f2 = f1.copy();

            //fill all containers in ship inventory
            f1 = tryFillContainer(this.ship, f1);
            
            //liquid moved: X liquid -> null or amount changed
            if (f1 == null || f1.amount != f2.amount)
            {
                //set moved
                moved = true;
                
                //calc trans amount
                if (f1 != null) f2.amount = f2.amount - f1.amount;
                
                //drain liquid from tank
                this.tank.drainInternal(f2, true);
            }
        }
        //ship's inventory to crane tank
        else if (mode == 2)
        {
            //f1 = fluid drain from container, f2 = tank fluid before drain
            //get fluid and null check
            f1 = this.tank.getFluid();
            f1 = (f1 == null) ? null : f1.copy();
            
            //tank is full
            if (f1 != null && f1.amount >= this.tank.getCapacity())
            {
                return false;
            }
            else
            {
                //calc max drain amount
                int maxDrain = 0;
                
                if (f1 != null) maxDrain = Math.min(this.tank.getCapacity() - f1.amount, this.rateLiquid);
                else maxDrain = Math.min(this.tank.getCapacity(), this.rateLiquid);
                
                //check container in ship inventory, input and return f1 may be NULL
                f1 = tryDrainContainer(this.ship, f1, maxDrain);
                
                //no liquid moved
                if (f1 == null) return false;
                //liquid moved: null -> X liquid or amount changed
                else if (f1.amount > 0)
                {
                    //set moved
                    moved = true;
                    
                    //fill liquid to tank
                    this.tank.fillInternal(f1, true);
                }
            }
        }
        
        return moved;
    }
    
    //fill container, return remaining fluid
    private static FluidStack tryFillContainer(BasicEntityShip ship, FluidStack fstack)
    {
        CapaInventoryExtend inv = ship.getCapaShipInventory();
        ItemStack stack;
        int amount;
        
        //fill all container in inventory
        for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < inv.getSizeInventoryPaged(); i++)
        {
            stack = inv.getStackInSlotWithPageCheck(i);
            
            //only for container with stackSize = 1
            if (stack != null && stack.stackSize == 1)
            {
                amount = 0;
                
                //check item has fluid capa
                if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
                {
                    IFluidHandler fluid = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
                    amount = fluid.fill(fstack, true);
                }//end get capa
                else if (stack.getItem() instanceof IFluidContainerItem)
                {
                    amount = ((IFluidContainerItem) stack.getItem()).fill(stack, fstack, true);
                }
                
                //if fill success
                if (amount > 0)
                {
                    fstack.amount -= amount;
                    if (fstack.amount <= 0) break;
                }
            }//end get item
        }//end for all slots
        
        return fstack;
    }
    
    //drain container, return remaining fluid
    private static FluidStack tryDrainContainer(BasicEntityShip ship, FluidStack targetFluid, int maxDrain)
    {
        CapaInventoryExtend inv = ship.getCapaShipInventory();
        ItemStack stack;
        FluidStack drainTemp;
        FluidStack drainTotal = null;
        
        if (targetFluid != null) targetFluid.amount = maxDrain;
        
        //fill all container in inventory
        for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < inv.getSizeInventoryPaged(); i++)
        {
            stack = inv.getStackInSlotWithPageCheck(i);
            drainTemp = null;
            
            if (stack != null && stack.stackSize == 1)
            {
                //check item has fluid capa
                if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP))
                {
                    IFluidHandler fluid = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);

                    //drain targetFluid from container
                    if (targetFluid != null) drainTemp = fluid.drain(targetFluid, true);
                    //targetFluid is null -> set the first liquid found as targetFluid
                    else drainTemp = fluid.drain(maxDrain, true);
                }//end has fluid capa
                else if (stack.getItem() instanceof IFluidContainerItem)
                {
                    //drain targetFluid from container
                    if (targetFluid != null && targetFluid.isFluidEqual(((IFluidContainerItem) stack.getItem()).getFluid(stack)))
                        drainTemp = ((IFluidContainerItem) stack.getItem()).drain(stack, targetFluid.amount, true);
                    //targetFluid is null -> set the first liquid found as targetFluid
                    else if (targetFluid == null)
                        drainTemp = ((IFluidContainerItem) stack.getItem()).drain(stack, maxDrain, true);
                }
                
                //add temp to total drain
                if (drainTemp != null)
                {
                    //targetFluid amount--
                    if (targetFluid != null)
                    {
                        targetFluid.amount -= drainTemp.amount;
                    }
                    else
                    {
                        targetFluid = drainTemp.copy();
                        targetFluid.amount = maxDrain - drainTemp.amount;
                    }
                    
                    //drainTotal amount++
                    if (drainTotal == null) drainTotal = drainTemp.copy();
                    else drainTotal.amount += drainTemp.amount;
                }
                
                if (targetFluid != null && targetFluid.amount <= 0) break;
            }//end get item
        }//end for all slots
        
        return drainTotal;
    }
    
    /**
     * EU transport method, return true if some energy is moved
     */
    private boolean applyEnergyTransfer(int mode)
    {
        //TODO NYI
        return false;
    }
    
    /**
     * LOADING: transfer item from CHEST to SHIP
     * 
     * @return <tt>true</tt> if item moved, <tt>false</tt> if no item can move
     */
    private boolean applyItemTransferLoading()
    {
        invFrom = this.chest;
        invTo = this.ship.getCapaShipInventory();
        
        return applyItemTransfer(true, );
    }
    
    /**
     * item loading / unloading
     * 
     * @param isLoading is loading item from chest to ship
     * @return <tt>true</tt> if item moved, <tt>false</tt> if no item can move
     */
    private boolean applyItemTransfer(boolean isLoading, IItemHandler invFrom, IItemHandler invTo)
    {
        ItemStack tempitem = null;
        ItemStack moveitem = null;
        boolean allEmpty = true;  //all temp slots are null = move all items
        boolean moved = false;
        int i, j;
        int slotid = -1;
        
        /** get target slot */
        //check temp slots
        for (i = 0; i < 9; i++)
        {
            //set inv
            if (isLoading)
            {
                
            }
            else
            {
                invTo = this.chest;
                invFrom = this.ship.getCapaShipInventory();
            }
            
            //get load item type
            tempitem = getItemstackTemp(i, isLoading);
            
            //temp != null
            if (tempitem != null)
            {
                allEmpty = false;
                
                //check target item exist in invFrom
                slotid = matchTempItem(invFrom, tempitem);
                
                //check target item in adj chest if no item in main chest
                if (slotid < 0 && invFrom instanceof TileEntityChest)
                {
                    TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) invFrom);
                    
                    if (chest2 != null)
                    {
                        invFrom = chest2;
                        slotid = matchTempItem(invFrom, tempitem);
                    }
                }
                
                //check number of stacks
                boolean canMove = true;
                int targetStacks = 0;
                
                //if excess mode
                if (this.craneMode == 3)
                {
                    //if loading, check ship has enough stacks
                    if (isLoading)
                    {
                        targetStacks = InventoryHelper.calcItemStackAmount(this.ship.getCapaShipInventory(), tempitem, this.checkMetadata, this.checkNbt, this.checkOredict);
                        if (targetStacks >= tempitem.stackSize) canMove = false;
                    }
                    //if unloading, check chest has enough stacks
                    else
                    {
                        targetStacks = InventoryHelper.calcItemStackAmount(this.chest, tempitem, this.checkMetadata, this.checkNbt, this.checkOredict);
                        if (targetStacks >= tempitem.stackSize) canMove = false;
                    }
                }
                //if remain mode
                else if (this.craneMode == 4)
                {
                    //if loading, check chest has enough stacks
                    if (isLoading)
                    {
                        targetStacks = InventoryHelper.calcItemStackAmount(this.chest, tempitem, this.checkMetadata, this.checkNbt, this.checkOredict);
                        if (targetStacks <= tempitem.stackSize) canMove = false;
                    }
                    //if unloading, check ship has enough stacks
                    else
                    {
                        targetStacks = InventoryHelper.calcItemStackAmount(this.ship.getCapaShipInventory(), tempitem, this.checkMetadata, this.checkNbt, this.checkOredict);
                        if (targetStacks <= tempitem.stackSize) canMove = false;
                    }
                }
                
                //try move target item
                if (canMove && slotid >= 0)
                {
                    //move item
                    if (invFrom instanceof CapaInventoryExtend) moveitem = ((CapaInventoryExtend) invFrom).getStackInSlotWithPageCheck(slotid);
                    else moveitem = invFrom.getStackInSlot(slotid);
                    moved = InventoryHelper.moveItemstackToInv(invTo, moveitem, null);
                    
                    //check item size
                    if (moved && moveitem.stackSize <= 0)
                    {
                        if (invFrom instanceof CapaInventoryExtend) ((CapaInventoryExtend) invFrom).setInventorySlotWithPageCheck(slotid, null);
                        else invFrom.setInventorySlotContents(slotid, null);
                    }
                    
                    //end moving item (1 itemstack per method call)
                    if (moved) break;
                }
            }//end temp != null
            else
            {
                //all slots are null
                if (i == 8 && allEmpty)
                {
                    slotid = matchAnyItemExceptNotModeItem(invFrom, isLoading);
                    
                    //check target item in adj chest if no item in main chest
                    if (slotid < 0 && invFrom instanceof TileEntityChest)
                    {
                        TileEntityChest chest2 = TileEntityHelper.getAdjChest((TileEntityChest) invFrom);
                        
                        if (chest2 != null)
                        {
                            invFrom = chest2;
                            slotid = matchAnyItemExceptNotModeItem(invFrom, isLoading);
                        }
                    }
                    
                    /** move target item */
                    if (slotid >= 0)
                    {
                        //move item
                        if (invFrom instanceof CapaInventoryExtend) moveitem = ((CapaInventoryExtend) invFrom).getStackInSlotWithPageCheck(slotid);
                        else moveitem = invFrom.getStackInSlot(slotid);
                        moved = InventoryHelper.moveItemstackToInv(invTo, moveitem, null);
                        
                        //check item size
                        if (moved && moveitem.stackSize <= 0)
                        {
                            if (invFrom instanceof CapaInventoryExtend) ((CapaInventoryExtend) invFrom).setInventorySlotWithPageCheck(slotid, null);
                            else invFrom.setInventorySlotContents(slotid, null);
                        }
                    }
                }
            }//end temp is null
        }//end all temp slots
        
        return moved;
    }
    
    //get itemstack temp from loading or unloading slots
    @Nonnull
    private NonNullList<ItemStack> getItemstackTemp(boolean isLoadingTemp)
    {
        NonNullList<ItemStack> temp = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
        int start = isLoadingTemp ? 0 : 9;
        
        for (int i = 0; i < 9; i++)
        {
            temp.set(i, this.itemHandler.getStackInSlot(i + start));
        }
        
        return temp;
    }
    
    /** check ship under crane waiting for craning */
    private void checkCraningShip()
    {
        AxisAlignedBB box = new AxisAlignedBB(pos.getX() - 6D, pos.getY() - 12D, pos.getZ() - 6D,
                                              pos.getX() + 6D, pos.getY(), pos.getZ() + 6D);
        List<BasicEntityShip> slist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, box);

        if (slist != null && !slist.isEmpty())
        {
            //get craning ship
            for (BasicEntityShip s : slist)
            {
                if (s.getStateHandler().getStateInt(ID.Keys.CraneState) == 2 &&
                    s.getGuardedPos(0) == pos.getX() &&
                    s.getGuardedPos(1) == pos.getY() &&
                    s.getGuardedPos(2) == pos.getZ())
                {
                    setShipData(s);
                    return;
                }
            }
            
            //no craning ship, get waiting ship
            for (BasicEntityShip s : slist)
            {
                if(s.getStateMinor(ID.M.CraneState) == 1 &&
                    s.getGuardedPos(0) == pos.getX() &&
                    s.getGuardedPos(1) == pos.getY() &&
                    s.getGuardedPos(2) == pos.getZ())
                {
                    setShipData(s);
                     return;
                 }
            }
        }
        
        //sync if get ship -> no ship
        boolean needSync = false;
        if (this.ship != null) needSync = true;
        
        this.ship = null;
        
        if (needSync) this.sendSyncPacket();
    }
    
    //set ship data: drum level
    protected void setShipData(BasicEntityShip ship)
    {
        this.ship = ship;
        this.ship.setStateMinor(ID.M.CraneState, 2);            //set crane state = craning
        this.ship.getShipNavigate().tryMoveToXYZ(pos.getX()+0.5D, pos.getY()-2D, pos.getZ()+0.5D, 0.5D);
        
        int[] drumNum = calcDrumLevel(ship, 0);
        
        //check liquid drum level
        this.rateLiquid = drumNum[1] * ConfigHandler.drumLiquid[1] + drumNum[0] * ConfigHandler.drumLiquid[0];
        this.rateLiquid = this.rateLiquid * 16 * ((int)((float)ship.getLevel() * 0.1F) + 1);
        
        //check EU storage level
        if (CommonProxy.activeIC2)
        {
            drumNum = calcDrumLevel(ship, 1);
            this.rateEU = drumNum[1] * ConfigHandler.drumEU[1] + drumNum[0] * ConfigHandler.drumEU[0];
            this.rateEU = this.rateEU * 16 * ((int)((float)ship.getLevel() * 0.1F) + 1);
        }
        
        //sync to client
        this.sendSyncPacket();
    }
    
    //type: 0:fluid, 1:EU, return int[2]: 0:#equips, 1:#enchantments
    protected int[] calcDrumLevel(BasicEntityShip ship, int type)
    {
        int[] num = new int[] {0, 0};
        CapaInventoryExtend inv = ship.getCapaShipInventory();
        
        //transport ship will get built-in pump station
        if (ship.getShipType() == ID.ShipType.TRANSPORT && ship.getStateFlag(ID.F.IsMarried))
            num[0] = 1;
        
        //check equip slots
        for (int i = 0; i < 6; i++)
        {
            ItemStack stack = inv.getStackInSlotWithPageCheck(i);
            
            if (stack != null && stack.getItem() == ModItems.EquipDrum)
            {
                //check liquid drum
                if ((type == 0 && stack.getItemDamage() == 1) ||
                    (type == 1 && stack.getItemDamage() == 2))
                {
                    num[0]++;
                    num[1] += EnchantHelper.calcEnchantNumber(stack);
                }
            }//end get drum
        }//end for all equip slot
        
        return num;
    }
    
    @Override
      public ItemStack getStackInSlot(int i)
    {
          return this.itemHandler.getStackInSlot(i);
      }
    
    @Override
      public ItemStack decrStackSize(int i, int j)
    {
        return null;
    }
    
    @Override
      public void setInventorySlotContents(int i, ItemStack stack)
    {
          this.itemHandler.setStackInSlot(i, stack);    
      }
    
    //每格可放的最大數量上限
      @Override
      public int getInventoryStackLimit()
      {
          return 0;
      }
      
    /** 5~9:   wait 16/32/48/64/80 ticks = 16/32/48/64/80 ticks
     *  10~14: wait 5/10/15/20/25 sec    = 100/200/300/400/500 ticks
     *  15~19: wait 1/2/3/4/5 min        = 1200/2400/3600/4800/6000 ticks
     *  20~24: wait 10/20/30/40/50 min   = 12000/24000/36000/48000/60000 ticks
     */
      public static int getWaitTime(int mode)
      {
          switch (mode)
          {
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
              return (mode - 4) * 16;
          case 10:
          case 11:
          case 12:
          case 13:
          case 14:
              return (mode - 9) * 100;
          case 15:
          case 16:
          case 17:
          case 18:
          case 19:
              return (mode - 14) * 1200;
          case 20:
          case 21:
          case 22:
          case 23:
          case 24:
              return (mode - 19) * 12000;
          default:
              return 0;
          }
      }

    @Override
    public void setWpStayTime(int time) {}

    @Override
    public int getWpStayTime()
    {
        return 0;
    }
    
    /**
     * set slot mode
     * 
     * @param slotID 0 ~ 17 (18 slots)
     * @param notMode <tt>true</tt> = set slot to NOT mode
     */
    public void setItemMode(int slotID, boolean notMode)
    {
        int slot = 1 << slotID;
        
        //set bit to 1
        if (notMode)
        {
            this.modeItem = this.modeItem | slot;
        }
        //set bit to 0
        else
        {
            this.modeItem = this.modeItem & (~slot);
        }
    }
    
    /**
     * check slot is NOT mode
     * 
     * itemMode: right most bits are slots mode: 1 = NOT MODE, 0 = NORMAL MODE
     *     bits: 17 16 15 ... 3 2 1 0 = (18 slots)
     * 
     * @param slotID 0 ~ 17 (18 slots)
     * @return <tt>true</tt> if slot is NOT mode
     */
    public boolean getItemMode(int slotID)
    {
        return ((this.modeItem >> slotID) & 1) == 1 ? true : false;
    }
    
    /**
     * get slot item mode array
     * 
     * @param isLoading set <tt>true</tt> to get LOADING temp
     */
    @Nonnull
    public boolean[] getItemMode(boolean isLoading)
    {
        int start = isLoading ? 0 : 9;
        boolean[] temp = new boolean[9];
        
        for (int i = 0; i < 9; i++)
        {
            temp[i] = this.getItemMode(i + start);
        }
        
        return temp;
    }
    
    //getter, setter
    public int getRedMode()
    {
        return this.modeRedstone;
    }
    
    public int getRedTick()
    {
        return this.tickRedstone;
    }
    
    public void setShip(BasicEntityShip ship)
    {
        this.ship = ship;
    }
    
    public BasicEntityShip getShip()
    {
        return this.ship;
    }
    
    /** FIELD相關方法
     *  使其他mod或class也能存取該tile的內部值
     *  ex: gui container可用get/setField來更新數值
     *  
     *  field id:
     *  0:ship eid, 1:craneTime, 2:isActive, 3:checkMeta, 4:checkDict
     *  5:craneMode, 6:enabLoad, 7:enabUnload, 8:checkNbt, 9:itemMode
     *  10:redMode
     */
    @Override
    public int getField(int id)
    {
        switch (id)
        {
        case 0:
            return this.ship == null ? 0 : this.ship.getEntityId();
        case 1:
            return this.ship == null ? 0 : this.ship.getStateTimer(ID.T.CraneTime);
        case 2:
            return this.isActive ? 1 : 0;
        case 3:
            return this.checkMetadata ? 1 : 0;
        case 4:
            return this.checkOredict ? 1 : 0;
        case 5:
            return this.craneMode;
        case 6:
            return this.enabLoad ? 1 : 0;
        case 7:
            return this.enabUnload ? 1 : 0;
        case 8:
            return this.checkNbt ? 1 : 0;
        case 9:
            return this.modeItem;
        case 10:
            return this.modeRedstone;
        case 11:
            return this.playerUID;
        case 12:
            return this.modeLiquid;
        case 13:
            return this.modeEnergy;
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
        case 1:
        break;
        case 2:
            this.isActive = value == 0 ? false : true;
        break;
        case 3:
            this.checkMetadata = value == 0 ? false : true;
        break;
        case 4:
            this.checkOredict = value == 0 ? false : true;
        break;
        case 5:
            this.craneMode = value;
        break;
        case 6:
            this.enabLoad = value == 0 ? false : true;
        break;
        case 7:
            this.enabUnload = value == 0 ? false : true;
        break;
        case 8:
            this.checkNbt = value == 0 ? false : true;
        break;
        case 9:
            this.modeItem = value;
        break;
        case 10:
            this.modeRedstone = value;
        break;
        case 11:
            this.playerUID = value;
        break;
        case 12:
            this.modeLiquid = value;
        break;
        case 13:
            this.modeEnergy = value;
        break;
        }
    }

    @Override
    public int getFieldCount()
    {
        return 14;
    }

    @Override
    public void setPairedChest(BlockPos pos)
    {
        if (pos != null)
        {
            TileEntity tile = this.world.getTileEntity(pos);
            
            if (tile instanceof IInventory)
            {
                this.chestPos = pos;
                this.isPaired = true;
                this.chest = (IInventory) tile;
            }
            
            //sync to client
            if (!this.world.isRemote) this.sendSyncPacket();
        }
    }

    @Override
    public BlockPos getPairedChest()
    {
        return this.chestPos;
    }

    
}