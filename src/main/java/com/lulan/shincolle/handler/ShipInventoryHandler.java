package com.lulan.shincolle.handler;

import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.capability.CapaInventoryExtend;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.InteractHelper;

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
             * equip slot size = 
             */
            ShipStateHandler st = this.host.getStateHandler();
            int sizetemp = st.getShipLevel() / ConfigHandler.slotsLevel +
                           ConfigHandler.getSlotsByIcon(st.getShipIcon()) +
                           ConfigHandler.getSlotsByType(st.getShipType());
        }
            
        
        //TODO
        
        
        
    }
    
    /** calc slot number modify by ship type */
    public static int getShipTypeToSlots(byte type)
    {
        
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
          //TODO
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //TODO old method refactoring
    
    /** decr ammo, type: 0:light, 1:heavy */
    public boolean decrAmmoNum(int type, int amount)
    {
        //check morph
        if (this.isMorph)
        {
            return MetamorphHelper.decrAmmoNum(this, type, amount);
        }
        
        int ammoType = ID.M.NumAmmoLight;
        boolean useItem = !hasAmmoLight();
        boolean showEmo = false;
        float modAmmo = this.shipAttrs.getAttrsBuffed(ID.Attrs.AMMO);
        
        switch (type)
        {
        case 1:   //use heavy ammo
            ammoType = ID.M.NumAmmoHeavy;
            useItem = !hasAmmoHeavy();
            break;
        }

        //check ammo first time
        if (StateMinor[ammoType] <= amount || useItem)
        {
            int addAmmo = 0;
            
            //use light ammo item
            if (ammoType == ID.M.NumAmmoLight)
            {
                if (decrSupplies(0))
                {  //use ammo item
                    addAmmo = (int) (Values.N.BaseLightAmmo * modAmmo);
                    showEmo = true;
                }
                else if (decrSupplies(2))
                {  //use ammo container item
                    addAmmo = (int) (Values.N.BaseLightAmmo * 9 * modAmmo);
                    showEmo = true;
                }
            }
            //use heavy ammo item
            else
            {
                if (decrSupplies(1))
                {  //use ammo item
                    addAmmo = (int) (Values.N.BaseHeavyAmmo * modAmmo);
                    showEmo = true;
                }
                else if (decrSupplies(3))
                {  //use ammo container item
                    addAmmo = (int) (Values.N.BaseHeavyAmmo * 9 * modAmmo);
                    showEmo = true;
                }
            }
            
            //check easy mode
            if (ConfigHandler.easyMode)
            {
                addAmmo *= 10;
            }
            
            StateMinor[ammoType] += addAmmo;
        }
        
        //show emotes
        if (showEmo)
        {
            if (this.getEmotesTick() <= 0)
            {
                this.setEmotesTick(40);
                
                switch (this.rand.nextInt(4))
                {
                case 1:
                    applyParticleEmotion(29);  //blink
                break;
                case 2:
                    applyParticleEmotion(30);  //pif
                break;
                default:
                    applyParticleEmotion(9);  //hungry
                break;
                }
            }
        }
        
        //check ammo second time
        if (StateMinor[ammoType] < amount)
        {
            //show emotes
            if (this.getEmotesTick() <= 0)
            {
                this.setEmotesTick(20);
                
                switch (this.rand.nextInt(7))
                {
                case 1:
                    applyParticleEmotion(0);  //drop
                break;
                case 2:
                    applyParticleEmotion(2);  //panic
                break;
                case 3:
                    applyParticleEmotion(5);  //...
                break;
                case 4:
                    applyParticleEmotion(20);  //orz
                break;
                default:
                    applyParticleEmotion(32);  //hmm
                break;
                }
            }
            
            return false;
        }
        else
        {
            StateMinor[ammoType] -= amount;
            return true;
        }
    }
    
    //consume grudge with buff and item calculation
    public void decrGrudgeNum(int value)
    {
        //check morph
        if (this.isMorph)
        {
            MetamorphHelper.decrGrudgeNum(this, value);
            return;
        }
                
        //get grudge magnification
        float modGrudge = this.shipAttrs.getAttrsBuffed(ID.Attrs.GRUDGE);
        
        //if grudge--, check buff: hunger
        if (value > 0)
        {
            int level = BuffHelper.getPotionLevel(this, 17);
            value = (int) ((float)value * (1F + level * 2F));
        }
        //if grudge++, check buff: grudge mod
        else if (value < 0)
        {
            value = (int) ((float)value * modGrudge);
        }
        
        //check fuel flag
        if (!getStateFlag(ID.F.NoFuel))
        {
            this.addGrudge(-value);
        }
        
        //eat "ONE" grudge item
        if (this.getGrudge() <= 0)
        {
            //try to find grudge item
            if (decrSupplies(4))
            {
                if (ConfigHandler.easyMode)
                {
                    this.addGrudge((int)(Values.N.BaseGrudge * 10F * modGrudge));
                }
                else
                {
                    this.addGrudge((int)(Values.N.BaseGrudge * modGrudge));
                }
            }
            //try to find grudge block
            else if (decrSupplies(5))
            {
                if (ConfigHandler.easyMode)
                {
                    this.addGrudge((int)(Values.N.BaseGrudge * 90F * modGrudge));
                }
                else
                {
                    this.addGrudge((int)(Values.N.BaseGrudge * 9F * modGrudge));
                }
            }
            //避免吃掉含有儲存資訊的方塊, 因此不使用heavy grudge block作為補充道具
        }
        
        //check fuel again and set fuel flag
        if (StateMinor[ID.M.NumGrudge] <= 0)
        {
            setStateFlag(ID.F.NoFuel, true);
        }
        else
        {
            setStateFlag(ID.F.NoFuel, false);
        }
        
        //check fuel flag and set AI
        if (getStateFlag(ID.F.NoFuel))  //no fuel, clear AI
        {
            //原本有AI, 則清除之
            if (this.targetTasks.taskEntries.size() > 0)
            {
                updateFuelState(true);
            }    
        }
        else                            //has fuel, set AI
        {
            if (this.targetTasks.taskEntries.size() < 1)
            {
                updateFuelState(false);
            }
        }
    }
    
    /**
     * decrese ammo/grudge/repair item number
     * return:
     *   true = get item and item number -1
     *   false = not enough item
     */
    public boolean decrSupplies(int type)
    {
        int itemNum = 1;
        boolean noMeta = false;
        ItemStack itemType = null;
        
        //find ammo
        switch (type)
        {
        case 0:    //use 1 light ammo
            itemType = new ItemStack(ModItems.Ammo,1,0);
            break;
        case 1: //use 1 heavy ammo
            itemType = new ItemStack(ModItems.Ammo,1,2);
            break;
        case 2:    //use 1 light ammo container
            itemType = new ItemStack(ModItems.Ammo,1,1);
            break;
        case 3: //use 1 heavy ammo container
            itemType = new ItemStack(ModItems.Ammo,1,3);
            break;
        case 4: //use 1 grudge
            itemType = new ItemStack(ModItems.Grudge,1);
            break;
        case 5: //use 1 grudge block
            itemType = new ItemStack(ModBlocks.BlockGrudge,1);
            break;
        case 6: //use 1 grudge block
            itemType = new ItemStack(ModBlocks.BlockGrudgeHeavy,1);
            break;
        case 7:    //use 1 repair bucket
            itemType = new ItemStack(ModItems.BucketRepair,1);
            break;
        case 8:    //use 1 repair goddess
            itemType = new ItemStack(ModItems.RepairGoddess,1);
            break;
        }
        
        //search item in ship inventory
        int i = findItemInSlot(itemType, noMeta);
        
        if (i == -1)
        {    //item not found
            return false;
        }
        
        //decr item stacksize
        ItemStack getItem = this.itemHandler.getStackInSlot(i);

        if (getItem.stackSize >= itemNum)
        {
            getItem.stackSize -= itemNum;
        }
        else
        {    //not enough item, return false
            return false;
        }
                
        if (getItem.stackSize == 0)
        {
            getItem = null;
        }
        
        //save back itemstack
        //no need to sync because no GUI opened
        this.itemHandler.setStackInSlot(i, getItem);
        
        return true;    
    }
    
  //use combat ration
    protected void useCombatRation()
    {
        //search item in ship inventory
        int i = findItemInSlot(new ItemStack(ModItems.CombatRation), true);
        
        if (i >= 0)
        {
            //decr item stacksize
            ItemStack getItem = this.itemHandler.getStackInSlot(i);
            
            InteractHelper.interactFeed(this, null, getItem);
            
            getItem.stackSize--;
            
            if (getItem.stackSize <= 0)
            {
                getItem = null;
            }
            
            //save back itemstack
            //no need to sync because no GUI opened
            this.itemHandler.setStackInSlot(i, getItem);
        }
    }
    
    
    
    
}