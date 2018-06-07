package com.lulan.shincolle.capability;

import javax.annotation.Nullable;

import com.lulan.shincolle.item.ShipTank;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

/**
 * fluid capability for itemstack
 * 
 * FIX:
 * 1.10.2 itemstack fluid capability bug:
 * setItem() is called before set meta value, so capacity (determined by meta) is unknown
 * while instancing ItemStack.
 * 
 * so this class will determine the capacity on the first time of filling or draining
 */
public class CapaFluidContainer implements IFluidHandler, ICapabilityProvider
{

    public static final String FLUID_NBT_KEY = "Fluid";

    protected ItemStack stack;
    protected int capacity;
    protected boolean needInit = true;

    
    public CapaFluidContainer(ItemStack stack)
    {
        this.stack = stack;
        this.capacity = 1000;	//default 1000 mb, changed while filling and draining
    }
    
    //init capacity by stack meta value
    protected void initCapacity()
    {
    	//set capacity by stack meta value
    	if (this.needInit && this.stack != null)
    	{
    		if (this.stack.getItem() instanceof ShipTank)
    		{
    			this.capacity = ShipTank.getCapacity(stack.getItemDamage());
    		}
    		
    		this.needInit = false;
    	}
    }

    @Nullable
    public FluidStack getFluid()
    {
        NBTTagCompound tagCompound = stack.getTagCompound();
        
        if (tagCompound == null || !tagCompound.hasKey(FLUID_NBT_KEY))
        {
            return null;
        }
        
        return FluidStack.loadFluidStackFromNBT(tagCompound.getCompoundTag(FLUID_NBT_KEY));
    }

    protected void setFluid(FluidStack fluid)
    {
        if (!stack.hasTagCompound())
        {
        	stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound fluidTag = new NBTTagCompound();
        fluid.writeToNBT(fluidTag);
        stack.getTagCompound().setTag(FLUID_NBT_KEY, fluidTag);
    }

    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        return new FluidTankProperties[] { new FluidTankProperties(getFluid(), capacity) };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
    	this.initCapacity();
    	
        if (stack.getCount() != 1 || resource == null || resource.amount <= 0 || !canFillFluidType(resource))
        {
            return 0;
        }

        FluidStack contained = getFluid();
        
        if (contained == null)
        {
            int fillAmount = Math.min(capacity, resource.amount);

            if (doFill)
            {
                FluidStack filled = resource.copy();
                filled.amount = fillAmount;
                setFluid(filled);
            }

            return fillAmount;
        }
        else
        {
            if (contained.isFluidEqual(resource))
            {
                int fillAmount = Math.min(capacity - contained.amount, resource.amount);

                if (doFill && fillAmount > 0) {
                    contained.amount += fillAmount;
                    setFluid(contained);
                }

                return fillAmount;
            }

            return 0;
        }
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
    	this.initCapacity();
    	
        if (stack.getCount() != 1 || resource == null || resource.amount <= 0 || !resource.isFluidEqual(getFluid()))
        {
            return null;
        }
        
        return drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
    	this.initCapacity();
    	
        if (stack.getCount() != 1 || maxDrain <= 0)
        {
            return null;
        }

        FluidStack contained = getFluid();
        
        if (contained == null || contained.amount <= 0 || !canDrainFluidType(contained))
        {
            return null;
        }

        final int drainAmount = Math.min(contained.amount, maxDrain);
        FluidStack drained = contained.copy();
        drained.amount = drainAmount;

        if (doDrain)
        {
            contained.amount -= drainAmount;
            
            if (contained.amount == 0)
            {
                setContainerToEmpty();
            }
            else
            {
                setFluid(contained);
            }
        }

        return drained;
    }

    public boolean canFillFluidType(FluidStack fluid)
    {
        return true;
    }

    public boolean canDrainFluidType(FluidStack fluid)
    {
        return true;
    }

    /**
     * Override this method for special handling.
     * Can be used to swap out the container's item for a different one with "container.setItem".
     * Can be used to destroy the container with "container.stackSize--"
     */
    protected void setContainerToEmpty()
    {
    	stack.getTagCompound().removeTag(FLUID_NBT_KEY);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? (T) this : null;
    }
    
    
}