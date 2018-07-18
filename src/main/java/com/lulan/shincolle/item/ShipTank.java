package com.lulan.shincolle.item;

import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.capability.CapaFluidContainer;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * ship liquid tank
 * 
 * right click: place liquid block in the air
 * 
 * meta:
 *   0: polymetal tank = 32    buckets
 *   1: obsidian tank  = 128   buckets
 *   2: abyssium tank  = 512   buckets
 *   3: grudge tank    = 2048  buckets
 *
 */
public class ShipTank extends BasicItem
{

	private static final String NAME = "ShipTank";
	
	
	public ShipTank()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getTypes()
	{
		return 4;
	}
	
	//get tank capacity
	public static int getCapacity(int meta)
	{
		switch (meta)
		{
		case 1:
			return ConfigHandler.itemShipTank[1];
		case 2:
			return ConfigHandler.itemShipTank[2];
		case 3:
			return ConfigHandler.itemShipTank[3];
		default:
			return ConfigHandler.itemShipTank[0];
		}
	}
	
	//right click: place liquid block or fill tank
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if (player == null) return new ActionResult(EnumActionResult.PASS, null);
		ItemStack stack = player.getHeldItem(hand);
		
		//server side
        RayTraceResult raytraceresult = this.rayTrace(world, player, true);
        IFluidHandler fh = FluidUtil.getFluidHandler(stack);
        BlockPos pos;
        
		//client side
		if (world.isRemote)
		{
			if (raytraceresult == null)
			{
	        	pos = getBlockInFrontOfPlayer(player);
	        	
	            //check player permission
	            if (!world.isBlockModifiable(player, pos))
	            {
	                return new ActionResult(EnumActionResult.FAIL, stack);
	            }
	            else
	            {
	            	CommonProxy.channelI.sendToServer(new C2SInputPackets(
	            			C2SInputPackets.PID.Request_PlaceFluid, pos.getX(), pos.getY(), pos.getZ()));
	            			
	            	return new ActionResult(EnumActionResult.SUCCESS, stack);
	            }
			}
			
			return new ActionResult(EnumActionResult.PASS, stack);
		}
        
		//server side
        //no target
        if (raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.FAIL, stack);
        }
        //hit block
        else if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            pos = raytraceresult.getBlockPos();
            
            //check player permission
            if (!world.isBlockModifiable(player, pos) ||
            	!player.canPlayerEdit(pos.offset(raytraceresult.sideHit), raytraceresult.sideHit, stack))
            {
                return new ActionResult(EnumActionResult.FAIL, stack);
            }
            else
            {
            	IBlockState state = world.getBlockState(pos);
            	TileEntity tile = world.getTileEntity(pos);
            	FluidStack fs = null;
            	
            	//hit vanilla liquid, fill liquid
            	if (state.getBlock() instanceof BlockLiquid)
            	{
            		//get water
            		if (state.getMaterial() == Material.WATER && ((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0)
            		{
            			fs = new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
            			
            			if (fh.fill(fs, false) > 0)
            			{
            				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                            player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1F, 1F);
                            fh.fill(fs, true);
            			}
            		}
            		//get lava
            		else if (state.getMaterial() == Material.LAVA && ((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0)
            		{
            			fs = new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
            			
            			if (fh.fill(fs, false) > 0)
            			{
            				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                            player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1F, 1F);
                            fh.fill(fs, true);
            			}
            		}
            	}
            	//hit forge liquid, fill liquid to tank
            	else if (state.getBlock() instanceof IFluidBlock)
            	{
            		IFluidBlock fb = (IFluidBlock) state.getBlock();
            		
            		if (fb.canDrain(world, pos))
            		{
            			fs = fb.drain(world, pos, false);
            			
            			//check can fill
            			if (fs != null && fh.fill(fs, false) > 0)
            			{
            				//clear block
                			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                			//play sound
                			player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1F, 1F);
                			//fill tank
                			fh.fill(fs, true);
                			
                			return new ActionResult(EnumActionResult.SUCCESS, stack);
            			}
            		}
            	}
            	//hit liquid container, drain or place fluid
            	else if (tile != null && tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, raytraceresult.sideHit))
            	{
            		IFluidHandler tilefh = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, EnumFacing.UP);
					
            		if (tilefh != null)
            		{
            			//drain liquid from tile
            			if (player.isSneaking())
            			{
            				fs = FluidUtil.tryFluidTransfer(fh, tilefh, 1000, true);
            				if (fs != null) return new ActionResult(EnumActionResult.SUCCESS, stack);
            			}
            			//fill liquid to tile
            			else
            			{
            				fs = FluidUtil.tryFluidTransfer(tilefh, fh, 1000, true);
            				if (fs != null) return new ActionResult(EnumActionResult.SUCCESS, stack);
            			}
            		}
            	}
            	//other block, place liquid from tank
            	else
            	{
            		//check hit block is replaceable
                    boolean flag1 = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
                    BlockPos pos2 = (flag1 && raytraceresult.sideHit == EnumFacing.UP) ? pos : pos.offset(raytraceresult.sideHit);

                    if (!player.canPlayerEdit(pos2, raytraceresult.sideHit, stack))
                    {
                        return new ActionResult(EnumActionResult.FAIL, stack);
                    }
                    else if (tryPlaceContainedLiquid(player, world, pos2, fh))
                    {
                        return new ActionResult(EnumActionResult.SUCCESS, stack);
                    }
                    else
                    {
                        return new ActionResult(EnumActionResult.FAIL, stack);
                    }
            	}
            }//end can edit
        }//end hit block
        
        return new ActionResult(EnumActionResult.PASS, stack);
    }
	
	//get 1 block in front of player
	private static BlockPos getBlockInFrontOfPlayer(EntityPlayer player)
	{
		float yaw = player.rotationYaw % 360F;
		if (yaw > 180F) yaw -= 360F;
		else if (yaw < -180F) yaw += 360F;
		
		if (yaw <= -112.5F && yaw > -157.5F)
			return new BlockPos(player.posX + 1, player.posY + 1, player.posZ - 1);
		else if (yaw <= -67.5F && yaw > -112.5F)
			return new BlockPos(player.posX + 1, player.posY + 1, player.posZ);
		else if (yaw <= -22.5F && yaw > -67.5F)
			return new BlockPos(player.posX + 1, player.posY + 1, player.posZ + 1);
		else if (yaw <= 22.5F && yaw > -22.5F)
			return new BlockPos(player.posX, player.posY + 1, player.posZ + 1);
		else if (yaw <= 67.5F && yaw > 22.5F)
			return new BlockPos(player.posX - 1, player.posY + 1, player.posZ + 1);
		else if (yaw <= 112.5F && yaw > 67.5F)
			return new BlockPos(player.posX - 1, player.posY + 1, player.posZ);
		else if (yaw <= 157.5F && yaw > 112.5F)
			return new BlockPos(player.posX - 1, player.posY + 1, player.posZ - 1);
		else
			return new BlockPos(player.posX, player.posY + 1, player.posZ - 1);
	}
    
	//place liquid block to world
    public static boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World world, BlockPos pos, IFluidHandler fh)
    {
    	//null check
    	if (fh == null) return false;
    	FluidStack fs = fh.drain(1000, false);
    	
    	//not enough liquid or liquid without block type
        if (fs == null || fs.amount < 1000 || fs.getFluid().getBlock() == null)
        {
            return false;
        }
        else
        {
            IBlockState state = world.getBlockState(pos);
            Material material = state.getMaterial();
            boolean flag = !material.isSolid();
            boolean flag1 = state.getBlock().isReplaceable(world, pos);

            if (!world.isAirBlock(pos) && !flag && !flag1)
            {
                return false;
            }
            else
            {
            	//if liquid is water, check world vaporize setting
                if (world.provider.doesWaterVaporize() && fs.getFluid() == FluidRegistry.WATER)
                {
                	//play vaporize effect
                    world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                    for (int k = 0; k < 8; ++k)
                    {
                    	world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)pos.getX() + Math.random(), (double)pos.getY() + Math.random(), (double)pos.getZ() + Math.random(), 0D, 0D, 0D, new int[0]);
                    }
                }
                else
                {
                	//destroy unstable block like grass and torch
                    if (!world.isRemote && (flag || flag1) && !material.isLiquid()) world.destroyBlock(pos, true);
                    //play sound
                    world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1F, 1F);
                    //place liquid block
                    world.setBlockState(pos, fs.getFluid().getBlock().getDefaultState(), 11);
                }
                
                //liquid--
                fh.drain(1000, true);
                
                //send block update
                world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);
                
                return true;
            }//end can place block
        }//end get liquid
    }
	
	//add fluid capability
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
    	return new CapaFluidContainer(stack);
    }
    
	//display equip information
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
    	list.add(TextFormatting.GRAY + I18n.format("gui.shincolle:shiptank"));
    	
    	if (stack != null)
    	{
    		FluidStack fs = FluidUtil.getFluidContained(stack);
    		String name = "";
    		int amount = 0;
    		int max = getCapacity(stack.getItemDamage());
    		
    		if (fs != null)
    		{
    			name = fs.getLocalizedName();
    			amount = fs.amount;
    		}
    		
    		list.add(TextFormatting.AQUA + name + TextFormatting.WHITE +
    				" " + amount + " / " + max + " mB");
    	}
    }
	
	
}