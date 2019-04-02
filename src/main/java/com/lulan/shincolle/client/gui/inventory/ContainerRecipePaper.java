package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * container for recipe paper
 */
public class ContainerRecipePaper extends Container
{
	
	//for shift item
	private static final int SLOT_INPUT = 0;
	private static final int SLOT_OUTPUT = 9;
	private static final int SLOT_PLAYERINV = 10;
	private static final int SLOT_HOTBAR = 37;
	private static final int SLOT_ALL = 46;
		
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World world;
    private ItemStack hostStack;	//recipe paper stack
	
	
	public ContainerRecipePaper(World world, IInventory invPlayer, ItemStack hostStack)
	{
		this.world = world;
		this.hostStack = hostStack;
		
		//recipe inventory
		for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
		
		//recipe result slot
		this.addSlotToContainer(new SlotRecipePaper(this.craftResult, 0, 124, 35));

		//player inventory
		for (int k = 0; k < 3; ++k)
        {
            for (int i1 = 0; i1 < 9; ++i1)
            {
                this.addSlotToContainer(new Slot(invPlayer, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
		
		//player hot bar
        for (int l = 0; l < 9; ++l)
        {
            this.addSlotToContainer(new Slot(invPlayer, l, 8 + l * 18, 142));
        }
        
        //init craft matrix slots
        if (hostStack.hasTagCompound())
        {
        	NBTTagCompound nbt = hostStack.getTagCompound();
        	NBTTagList tagList = nbt.getTagList("Recipe", Constants.NBT.TAG_COMPOUND);
        	
            for (int i = 0; i < 9; i++)
            {
                NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
                int slot = itemTags.getInteger("Slot");

                if (slot >= 0 && slot < 9)
                {
                    this.craftMatrix.setInventorySlotContents(slot, new ItemStack(itemTags));
                }
            }
        }
        
        //init result slot
        this.onCraftMatrixChanged(null);
	}
	
    /**
     * Callback for when the crafting matrix is changed.
     */
	@Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
    	ItemStack result = CraftingManager.findMatchingResult(this.craftMatrix, this.world);
    	this.craftResult.setInventorySlotContents(0, result);
    }
    
	//save recipe to RecipePaper on gui closed
    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!this.world.isRemote)
        {
        	ItemStack stack;
            NBTTagCompound nbt;
            NBTTagList nbtlist = new NBTTagList();
            
            //get nbt tag from stack
            if (this.hostStack.hasTagCompound()) nbt = this.hostStack.getTagCompound();
            else nbt = new NBTTagCompound();
            
            //save craft matrix slot (3x3)
            for (int i = 0; i < 9; i++)
            {
            	stack = this.craftMatrix.getStackInSlot(i);
            	
                if (!stack.isEmpty())
                {
                    NBTTagCompound itemTag = new NBTTagCompound();
                    itemTag.setInteger("Slot", i);
                    stack.writeToNBT(itemTag);
                    nbtlist.appendTag(itemTag);
                }
            }
            
            //save craft result slot
            stack = this.craftResult.getStackInSlot(0);
            
            if (!stack.isEmpty())
            {
            	NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setInteger("Slot", 9);
                stack.writeToNBT(itemTag);
                nbtlist.appendTag(itemTag);
            }
            
            //save data to host stack
            nbt.setTag("Recipe", nbtlist);
            this.hostStack.setTagCompound(nbt);
        }
    }
    
    //玩家是否可以開啟gui
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	/** 禁用shift功能 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid)
	{
		return ItemStack.EMPTY;
    }
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
    }

	//client端container接收新值
	@Override
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
	{
    }
	
	/** 複製游標上的itemstack到craftMatrix中, 不影響玩家物品欄跟手上的物品, BOTH SIDE */
	@Override
	public ItemStack slotClick(int id, int key, ClickType type, EntityPlayer player)
	{
        ItemStack itemstack = player.inventory.getItemStack();
        
    	//click on craft matrix slot
        if (id >= 0 && id < 9)
        {
        	Slot slot = (Slot) this.inventorySlots.get(id);
        	
        	//click slot with item
        	if (!itemstack.isEmpty())
        	{
        		//clear slot by right click
        		if (key == 1)
        		{
            		slot.putStack(ItemStack.EMPTY);
        		}
        		//set slot by left click
        		else
        		{
        			ItemStack itemstack2 = itemstack.copy();
            		itemstack2.setCount(1);
            		slot.putStack(itemstack2);
        		}
        	}
        	//any key without item
        	else
        	{
        		slot.putStack(ItemStack.EMPTY);
        	}
        	
        	this.detectAndSendChanges();
        	return ItemStack.EMPTY;
        }
        //click on result slot
        else if (id == 9) return ItemStack.EMPTY;
        
        //click on other slot
        return super.slotClick(id, key, type, player);
    }
	
	
}