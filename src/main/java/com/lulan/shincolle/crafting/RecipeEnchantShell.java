package com.lulan.shincolle.crafting;

import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.EquipAmmo;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * recipe for enchant shell
 */
public class RecipeEnchantShell extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
	
    private static final NonNullList<ItemStack> EMPTY_ITEMS = NonNullList.withSize(9, ItemStack.EMPTY);
    
    
    public RecipeEnchantShell() {}
    
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        if (inv.getWidth() == 3 && inv.getHeight() == 3)
        {
        	//check item in first slot
        	ItemStack stack0 = inv.getStackInRowAndColumn(0, 0);
        	
        	if (stack0.isEmpty()) return false;
        	else if (stack0.getItem() != Items.POTIONITEM) return false;
        	
        	//check other slots
            for (int i = 0; i < inv.getWidth(); ++i)
            {
                for (int j = 0; j < inv.getHeight(); ++j)
                {
                	if (i == 0 && j == 0) continue;
                	
                    ItemStack stackX = inv.getStackInRowAndColumn(i, j);

                    if (stackX.isEmpty())
                    {
                        return false;
                    }

                    Item item = stackX.getItem();
                    
                    //中間必為EquipAmmo
                    if (i == 1 && j == 1)
                    {
                        if (item != ModItems.EquipAmmo)
                        {
                            return false;
                        }
                        else if (stackX.getMetadata() != 7)
                        {
                        	return false;
                        }
                    }
                    //其他位置必為藥水
                    else if (item == Items.POTIONITEM)
                    {
                        if (!ItemStack.areItemStackTagsEqual(stack0, stackX))
                        {
                        	return false;
                        }
                    }
                    else
                    {
                    	return false;
                    }
                }//end for j
            }//end for i
            
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * apply potion effect to enchant shell, only first effect will be added
     */
    @Nullable
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        ItemStack ammo = inv.getStackInRowAndColumn(1, 1);
        ItemStack potion = inv.getStackInRowAndColumn(0, 0);
        
        //null check
        if (!ammo.isEmpty() && ammo.getItem() == ModItems.EquipAmmo &&
        	!potion.isEmpty() && potion.getItem() == Items.POTIONITEM)
        {
            ItemStack ammoNew = ammo.copy();
            
            //get potion effect from potion
            List<PotionEffect> elist = PotionUtils.getEffectsFromStack(potion);
            
            if (elist != null && elist.size() > 0)
            {
            	PotionEffect effect = elist.get(0);
            	int pid = Potion.getIdFromPotion(effect.getPotion());
            	if (pid < 1) return ammoNew;
            	int plv = effect.getAmplifier();
            	int ptime = 100;
            	int pchance = 20;
            	
            	//get potion effect from old ammo
            	NBTTagCompound nbtOld = ammo.getTagCompound();
            	
            	if (nbtOld != null)
            	{
            		NBTTagList listOld = nbtOld.getTagList(EquipAmmo.PLIST, Constants.NBT.TAG_COMPOUND);
                	NBTTagCompound nbt0 = listOld.getCompoundTagAt(0);
                	
                	if (nbt0 != null)
                	{
                		int pidOld = nbt0.getInteger(EquipAmmo.PID);
                		int plvOld = nbt0.getInteger(EquipAmmo.PLEVEL);
                		int ptimeOld = nbt0.getInteger(EquipAmmo.PTIME);
                		int pchanceOld = nbt0.getInteger(EquipAmmo.PCHANCE);
                		
                		if (pid == pidOld && plv == plvOld)
                		{
                			ptime = ptimeOld + 20;
                			pchance = pchanceOld + 10;
                			
                			if (pchance > 100) pchance = 100;
                		}
                	}
            	}
            	
            	//set potion effect to item
            	NBTTagCompound nbtNew = new NBTTagCompound();
            	NBTTagList listNew = new NBTTagList();
            	NBTTagCompound nbt0 = new NBTTagCompound();
            	nbt0.setInteger(EquipAmmo.PID, pid);
            	nbt0.setInteger(EquipAmmo.PLEVEL, plv);
            	nbt0.setInteger(EquipAmmo.PTIME, ptime);
            	nbt0.setInteger(EquipAmmo.PCHANCE, pchance);
            	listNew.appendTag(nbt0);
            	nbtNew.setTag(EquipAmmo.PLIST, listNew);
            	ammoNew.setTagCompound(nbtNew);
            }
            
            return ammoNew;
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canFit(int width, int height)
    {
        return width <= 3 && height <= 3;
    }

    @Nullable
    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }

    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        return EMPTY_ITEMS;
    }
}