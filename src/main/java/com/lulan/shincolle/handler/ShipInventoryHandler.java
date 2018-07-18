package com.lulan.shincolle.handler;

import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaInventoryExtend;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.InteractHelper;
import com.lulan.shincolle.utility.InventoryHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * handle ship's inventories
 * 
 * item inventory:
 *   slot number = level + ship type + ship icon + equip
 *   
 * equip inventory:
 *   slot number = level + ship icon
 *   page number = by level: page1:lv1, page2:lv75, page3:lv120
 *   
 * handheld inventory:
 *   slot number = 2 (mainhand, offhand)
 */
public class ShipInventoryHandler
{
    
    /** item slot, get from null side */
    protected CapaInventoryExtend invItem;
    /** equip slot, get from horizontal side */
    protected CapaInventoryExtend invEquip;
    /** handheld slot, get from vertical side */
    protected CapaInventory invHand;
    /** inventory host */
    protected BasicEntityShip host;
    
    protected int maxSlotItem;    //max slot number of item inventory
    protected int maxSlotEquip;   //max slot number of equip inventory
    protected int maxSlotHand;    //max slot number of hand inventory
    
    
    public ShipInventoryHandler(BasicEntityShip host)
    {
        this.host = host;
                
        this.initFirst();
    }
    
    /** init data on new object */
    protected void initFirst()
    {
        ShipStateHandler st = this.host.getStateHandler();
        
        st.setNumberState(ID.Keys.SlotsLevel, 0);
        
        this.maxSlotItem = 1;    //set 1 at first, change number after nbt loaded
        this.maxSlotEquip = 1;
        this.maxSlotHand = 2;    //fixed 2 slots
        
        //init inventory
        this.invItem = new CapaInventoryExtend<BasicEntityShip>(this.maxSlotItem, 3, this.host, ID.InvName.ItemSlot);
        this.invEquip = new CapaInventoryExtend<BasicEntityShip>(this.maxSlotEquip, 6, this.host, ID.InvName.EquipSlot);
        this.invHand = new CapaInventory<BasicEntityShip>(this.maxSlotHand, this.host, ID.InvName.HandSlot);
    }
    
    /** init data after nbt tags loaded */
    protected void initPost(NBTTagCompound nbt)
    {
        //load equip inventory
        if (nbt.hasKey(ID.InvName.EquipSlot))
        {
            this.invEquip.deserializeNBT((NBTTagCompound) nbt.getTag(ID.InvName.EquipSlot));
            this.maxSlotEquip = this.invEquip.getSlots();
        }
        
        //load item inventory
        if (nbt.hasKey(ID.InvName.ItemSlot))
        {
            this.invItem.deserializeNBT((NBTTagCompound) nbt.getTag(ID.InvName.ItemSlot));
            this.maxSlotItem = this.invItem.getSlots();
        }
        
        //load hand inventory
        if (nbt.hasKey(ID.InvName.HandSlot))
        {
            this.invHand.deserializeNBT((NBTTagCompound) nbt.getTag(ID.InvName.HandSlot));
        }
        
        //calc equip attrs to get new SlotLevel, SERVER SIDE ONLY
        if (!this.host.world.isRemote)
        {
            //recalc attrs
            this.host.calcShipAttributes(this.host, 2, true);
            
            /*
             * resize if size changed
             * item slot size = level / N + icon type + ship type + equip
             * equip slot size = level / N + icon type
             * equip page size = by level
             * hand size = fixed 2
             */
            //check item slots
            ShipStateHandler st = this.host.getStateHandler();
            int sizetemp = st.getShipLevel() / ConfigHandler.itemSlotsLevel +    //level
                           ConfigHandler.getSlotsItemByIcon(st.getShipIcon()) +  //by icon
                           ConfigHandler.itemSlotsType[st.getShipType()] +  //by type
                           this.host.getStateHandler().getShipSlotsLevel();      //equip
            
            if (sizetemp != this.invItem.getSlots()) this.invItem.resize(sizetemp);
            
            //check equip slots
            sizetemp = st.getShipLevel() / ConfigHandler.equipSlotsLevel +   //level
                       ConfigHandler.getSlotsEquipByIcon(st.getShipIcon());  //by icon
            
            if (sizetemp != this.invEquip.getSlots()) this.invEquip.resize(sizetemp);
        }
    }
    
    /** get inventory */
    public CapaInventoryExtend getItemInventory()
    {
        return this.invItem;
    }
    
    public CapaInventoryExtend getEquipInventory()
    {
        return this.invEquip;
    }
    
    public CapaInventory getHandInventory()
    {
        return this.invHand;
    }
    
    /**
     * on itemstack changed in slots
     * if equip slots => update attrs
     */
    public void onContentChanged(int slot, CapaInventory inv)
    {
        if (!this.host.world.isRemote && inv.getInvName().equals(ID.InvName.EquipSlot))
        {
            this.host.calcShipAttributes(this.host, 2, true);
        }
    }
    
    /** update on general value changed, ex: max slot number */
    public void onContentChanged(CapaInventory inv)
    {
        //send sync packet
        if (!this.host.world.isRemote)
        {
          //TODO send ship sync packet
        }
    }
    
    private static ItemStack AmmoLight = new ItemStack(ModItems.Ammo, 1, 0);
    private static ItemStack AmmoHeavy = new ItemStack(ModItems.Ammo, 1, 2);
    private static ItemStack AmmoLightContainer = new ItemStack(ModItems.Ammo, 1, 1);
    private static ItemStack AmmoHeavyContainer = new ItemStack(ModItems.Ammo, 1, 3);
    
    /**
     * consume ammo item x1
     * 
     * @param type 0:light, 1:heavy
     * @return -1:no item can be consumed, 0:light ammo, 1:heavy ammo, 2:light container, 3:heavy container
     */
    public int consumeAmmoItem(Enums.Ammo type)
    {
        if (type == Enums.Ammo.HEAVY)
        {
            if (InventoryHelper.consumeItem(this.invItem, AmmoHeavy, 1, true, false, false)) return 1;
            if (InventoryHelper.consumeItem(this.invItem, AmmoHeavyContainer, 1, true, false, false)) return 3;
        }
        else
        {
            if (InventoryHelper.consumeItem(this.invItem, AmmoLight, 1, true, false, false)) return 0;
            if (InventoryHelper.consumeItem(this.invItem, AmmoLightContainer, 1, true, false, false)) return 2;
        }
        
        //item not found
        return -1;    
    }
    
    private static ItemStack GrudgeItem = new ItemStack(ModItems.Grudge, 1, 0);
    private static ItemStack GrudgeBlock = new ItemStack(ModBlocks.BlockGrudge);
    
    /**
     * consume grudge item x1
     * 
     * @return -1:no item can be consumed, 0:grudge, 1:grudge block
     */
    public int consumeGrudgeItem()
    {
        if (InventoryHelper.consumeItem(this.invItem, GrudgeItem, 1, true, false, false)) return 0;
        if (InventoryHelper.consumeItem(this.invItem, GrudgeBlock, 1, false, false, false)) return 1;

        //item not found
        return -1;    
    }
    
    private static ItemStack RepairBucket = new ItemStack(ModItems.BucketRepair);
    
    /**
     * consume repair bucket x1
     * @return <tt>true</tt> if item consumed
     */
    public boolean consumeRepairBucket()
    {
        return InventoryHelper.consumeItem(this.invItem, RepairBucket, 1, false, false, false);
    }
    
    private static ItemStack RepairGoddess = new ItemStack(ModItems.RepairGoddess);
    
    /**
     * consume repair goddess x1
     * @return <tt>true</tt> if item consumed
     */
    public boolean consumeRepairGoddess()
    {
        return InventoryHelper.consumeItem(this.invItem, RepairGoddess, 1, false, false, false);
    }
    
    private static ItemStack CombatRation = new ItemStack(ModItems.CombatRation);
    
    /**
     * autouse combat ration
     * 
     * CombatRation meta:
     *   0:Rice Balls, 1:Rice Balls +grudge,
     *   2:Rice Balls XL, 3:Rice Balls XL +grudge,
     *   4:Ice Cream, 5:Ice Cream +grudge
     */
    public void autouseCombatRation()
    {
        //search item in ship inventory
        ItemStack stack = InventoryHelper.getAndRemoveItem(this.invItem, CombatRation, 1, false, false, false, null);
        
        if (!stack.isEmpty()) InteractHelper.interactFeed(this.host, null, stack);
    }
    
    
}