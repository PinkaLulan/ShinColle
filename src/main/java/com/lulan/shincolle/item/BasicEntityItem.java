package com.lulan.shincolle.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Optional;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.utility.EntityHelper;

/** custom entity item with:
 *  1. owner checking
 *  2. custom model
 *  3. fire proof
 *  4. no clip
 *  5. can't be pushed
 * 
 */
public class BasicEntityItem extends Entity
{
	
	/** item of this entity */
    private static final DataParameter<Optional<ItemStack>> ITEM = EntityDataManager.<Optional<ItemStack>>createKey(EntityItem.class, DataSerializers.OPTIONAL_ITEM_STACK);
    /** The age of this EntityItem (used to animate it up and down as well as expire it) */
    private int delayBeforeCanPickup;
    private String owner;  //TODO checking owner
	
	
	public BasicEntityItem(World world)
	{
		super(world);
		this.setSize(0.8F, 0.8F);
	}
	
	public BasicEntityItem(World world, double x, double y, double z, ItemStack item)
	{
		this(world);
		this.setPosition(x, y, z);
		this.motionX = 0D;
		this.motionY = 0D;
		this.motionZ = 0D;
		this.isImmuneToFire = true;
		this.onGround = true;
		this.noClip = false;
		this.delayBeforeCanPickup = 10;
		this.setEntityItemStack(item);
		
		//stop vanilla init
		this.firstUpdate = false;
	}
	
	//新增item data到data watcher中
	@Override
    protected void entityInit()
    {
        this.getDataManager().register(ITEM, Optional.<ItemStack>absent());
    }
	
	//此entity不會走動
	@Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
	
	//can not damage this item
	@Override
	public boolean attackEntityFrom(DamageSource attacker, float dmg)
	{
		return false;
	}
	
	@Override
    public boolean canBeAttackedWithItem()
    {
        return false;
    }
	
	@Override
	public Entity changeDimension(int dimensionIn)
	{
		return null;
	}
	
    public String getOwner()
    {
        return this.owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }
	
	//cancel motionY
	@Override
    public void onUpdate()
    {
		this.setPosition(posX, posY, posZ);

		//play portal sound
		if (this.world.isRemote && rand.nextInt(50) == 0)
        {
            this.world.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
		
		if(this.getEntityItem() == null)
		{
			this.setDead();
		}
		else
		{
			//stop motion
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
          
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
			this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
            this.move(this.motionX, this.motionY, this.motionZ);
            
			//check item
			ItemStack item = this.getDataManager().get(ITEM).orNull();
  
			if (item != null && item.stackSize <= 0)
			{
				this.setDead();
			}
		}

		//pick delay --
        if (this.delayBeforeCanPickup > 0)
        {
            --this.delayBeforeCanPickup;
        }
        
    }
	
	//immune to fire and lava
	@Override
	protected void dealFireDamage(int fire) {}
	
	@Override
	public void setFire(int time) {}
	
	@Override
	protected void setOnFireFromLava() {}

	@Override
	public boolean handleWaterMovement()
	{
		return false;
	}
	
	@Override
	public void move(double x, double y, double z)
	{
        this.world.theProfiler.startSection("move");
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        double d3 = x;
        double d4 = y;
        double d5 = z;
        double d6;

        //move x when collision onGround
        for (d6 = 0.05D; x != 0.0D && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x)
        {
            if (x < d6 && x >= -d6)
            {
                x = 0.0D;
            }
            else if (x > 0.0D)
            {
                x -= d6;
            }
            else
            {
                x += d6;
            }
        }

        //move z when collision onGround
        for (; z != 0.0D && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty(); d5 = z)
        {
            if (z < d6 && z >= -d6)
            {
                z = 0.0D;
            }
            else if (z > 0.0D)
            {
                z -= d6;
            }
            else
            {
                z += d6;
            }
        }

        //move xz when collision onGround
        for (; x != 0.0D && z != 0.0D && this.world.getCollisionBoxes(this, this.getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty(); d5 = z)
        {
            if (x < d6 && x >= -d6)
            {
                x = 0.0D;
            }
            else if (x > 0.0D)
            {
                x -= d6;
            }
            else
            {
                x += d6;
            }

            d3 = x;

            if (z < d6 && z >= -d6)
            {
                z = 0.0D;
            }
            else if (z > 0.0D)
            {
                z -= d6;
            }
            else
            {
                z += d6;
            }
        }

        //move x,y,z when collision without checking onGround
        List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(x, y, z));
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
        int i = 0;

        for (int j = list1.size(); i < j; ++i)
        {
            y = ((AxisAlignedBB)list1.get(i)).calculateYOffset(this.getEntityBoundingBox(), y);
        }

        this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
        int j4 = 0;

        for (int k = list1.size(); j4 < k; ++j4)
        {
            x = ((AxisAlignedBB)list1.get(j4)).calculateXOffset(this.getEntityBoundingBox(), x);
        }

        this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));
        j4 = 0;

        for (int k4 = list1.size(); j4 < k4; ++j4)
        {
            z = ((AxisAlignedBB)list1.get(j4)).calculateZOffset(this.getEntityBoundingBox(), z);
        }

        this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));

        //move entity Y by stepHeight if x,z moved
        if (this.stepHeight > 0.0F && (d3 != x || d5 != z))
        {
            double d11 = x;
            double d7 = y;
            double d8 = z;
            AxisAlignedBB axisalignedbb1 = this.getEntityBoundingBox();
            this.setEntityBoundingBox(axisalignedbb);
            y = (double)this.stepHeight;
            List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().addCoord(d3, y, d5));
            AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
            AxisAlignedBB axisalignedbb3 = axisalignedbb2.addCoord(d3, 0.0D, d5);
            double d9 = y;
            int l = 0;

            for (int i1 = list.size(); l < i1; ++l)
            {
                d9 = ((AxisAlignedBB)list.get(l)).calculateYOffset(axisalignedbb3, d9);
            }

            axisalignedbb2 = axisalignedbb2.offset(0.0D, d9, 0.0D);
            double d15 = d3;
            int j1 = 0;

            for (int k1 = list.size(); j1 < k1; ++j1)
            {
                d15 = ((AxisAlignedBB)list.get(j1)).calculateXOffset(axisalignedbb2, d15);
            }

            axisalignedbb2 = axisalignedbb2.offset(d15, 0.0D, 0.0D);
            double d16 = d5;
            int l1 = 0;

            for (int i2 = list.size(); l1 < i2; ++l1)
            {
                d16 = ((AxisAlignedBB)list.get(l1)).calculateZOffset(axisalignedbb2, d16);
            }

            axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d16);
            AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
            double d17 = y;
            int j2 = 0;

            for (int k2 = list.size(); j2 < k2; ++j2)
            {
                d17 = ((AxisAlignedBB)list.get(j2)).calculateYOffset(axisalignedbb4, d17);
            }

            axisalignedbb4 = axisalignedbb4.offset(0.0D, d17, 0.0D);
            double d18 = d3;
            int l2 = 0;

            for (int i3 = list.size(); l2 < i3; ++l2)
            {
                d18 = ((AxisAlignedBB)list.get(l2)).calculateXOffset(axisalignedbb4, d18);
            }

            axisalignedbb4 = axisalignedbb4.offset(d18, 0.0D, 0.0D);
            double d19 = d5;
            int j3 = 0;

            for (int k3 = list.size(); j3 < k3; ++j3)
            {
                d19 = ((AxisAlignedBB)list.get(j3)).calculateZOffset(axisalignedbb4, d19);
            }

            axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d19);
            double d20 = d15 * d15 + d16 * d16;
            double d10 = d18 * d18 + d19 * d19;

            if (d20 > d10)
            {
                x = d15;
                z = d16;
                y = -d9;
                this.setEntityBoundingBox(axisalignedbb2);
            }
            else
            {
                x = d18;
                z = d19;
                y = -d17;
                this.setEntityBoundingBox(axisalignedbb4);
            }

            int l3 = 0;

            for (int i4 = list.size(); l3 < i4; ++l3)
            {
                y = ((AxisAlignedBB)list.get(l3)).calculateYOffset(this.getEntityBoundingBox(), y);
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));

            if (d11 * d11 + d8 * d8 >= x * x + z * z)
            {
                x = d11;
                y = d7;
                z = d8;
                this.setEntityBoundingBox(axisalignedbb1);
            }
        }

        this.world.theProfiler.endSection();
        
        //rest motion
        this.world.theProfiler.startSection("rest");
        this.resetPositionToBB();
        this.isCollidedHorizontally = d3 != x || d5 != z;
        this.isCollidedVertically = d4 != y;
        this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;

        if (d3 != x)
        {
            this.motionX = 0D;
        }
        
        if (d4 != y)
        {
        	this.motionY = 0D;
        }

        if (d5 != z)
        {
            this.motionZ = 0D;
        }

        this.world.theProfiler.endSection();

	}
	
	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
	
	@Override
	public void fall(float distance, float damageMultiplier) {}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
	{
        return false;
    }
	
	@Override
	public boolean shouldRenderInPass(int pass)
	{
        return true;
    }
	
	@Override
	public boolean isInvisible()
	{
		return false;
	}
	
	@Override
	public void setInvisible(boolean par1) {}
	
    /**
     * Returns the ItemStack corresponding to the Entity (Note: if no item exists, will log an error but still return an
     * ItemStack containing Block.stone)
     */
    public ItemStack getEntityItem()
    {
        ItemStack itemstack = (ItemStack)((Optional)this.getDataManager().get(ITEM)).orNull();

        if (itemstack == null)
        {
            return new ItemStack(Blocks.STONE);
        }
        else
        {
            return itemstack;
        }
    }

    /**
     * Sets the ItemStack for this entity
     */
    public void setEntityItemStack(@Nullable ItemStack stack)
    {
        this.getDataManager().set(ITEM, Optional.fromNullable(stack));
        this.getDataManager().setDirty(ITEM);
    }
	
	/**
     * Called by a player entity when they collide with an entity
     */
	@Override
    public void onCollideWithPlayer(EntityPlayer player)
	{
        if(!this.world.isRemote && !this.isDead)
        {
        	//check delay
        	if (this.delayBeforeCanPickup > 0) return;
        	
        	//get item
        	EntityTracker entitytracker = ((WorldServer)this.world).getEntityTracker();
            ItemStack itemstack = this.getEntityItem();
            int i = itemstack.stackSize;
            
            //if can pick
            if (this.delayBeforeCanPickup <= 0)
            {
                //is OP
                if (EntityHelper.checkOP(player))
                {
                	player.inventory.addItemStackToInventory(itemstack);
                }
                //not OP
                else
                {
                	//ship spawn egg = owner pick only
                    if (itemstack.getItem() == ModItems.ShipSpawnEgg)
                    {
                    	NBTTagCompound nbt = itemstack.getTagCompound();
                    	
                    	//ship egg with tag
                    	if (nbt != null)
                    	{
                    		String pid1 = nbt.getString("ownername");
                    		String pid2 = player.getName();
                    		
                    		//check player UID
                    		//if no owner name
                    		if (pid1 == null || pid1.length() <= 1)
                    		{
                    			//ship's player UID isn't inited (for ship before 1.7.10.rv22)
                    			//check player UUID
                    			String uuid1 = nbt.getString("owner");
                    			String uuid2 = player.getUniqueID().toString();
                    			
                    			if (uuid2.equals(uuid1))
                    			{
                    				player.inventory.addItemStackToInventory(itemstack);
                    			}
                    		}
                    		else
                    		{
                    			if (pid1.equals(pid2))
                    			{
                    				player.inventory.addItemStackToInventory(itemstack);
                    			}
                    		}
                    		
                    		
                    		//TODO old method
//                    		int pid1 = nbt.getInteger("PlayerID");
//                    		int pid2 = EntityHelper.getPlayerUID(player);
//                    		
//                    		//check player UID
//                    		if (pid1 <= 0)
//                    		{	//ship's player UID isn't inited (old ship)
//                    			//check player UUID
//                    			String uuid1 = nbt.getString("owner");
//                    			String uuid2 = player.getUniqueID().toString();
//                    			
//                    			if (uuid2.equals(uuid1))
//                    			{
//                    				player.inventory.addItemStackToInventory(itemstack);
//                    			}
//                    		}
//                    		else
//                    		{	//get legal player UID
//                    			if (pid1 == pid2)
//                    			{
//                    				player.inventory.addItemStackToInventory(itemstack);
//                    			}
//                    		}
                    	}
                    	//ship egg w/o tag
                    	else
                    	{
                    		player.inventory.addItemStackToInventory(itemstack);
                    	}
                    }
                    //not ship spawn egg
                    else
                    {
                    	player.inventory.addItemStackToInventory(itemstack);
                    }
                }
            	
            	//play pick sound
                if (!this.isSilent())
                {
                    this.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
            }//end delay = 0
            
            if (itemstack.stackSize <= 0)
            {
                this.setDead();
            }
        }//end server side
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
        NBTTagCompound itemtag = nbt.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(itemtag));

        ItemStack item = this.getDataManager().get(ITEM).orNull();

        if(item == null || item.stackSize <= 0)
        {
            this.setDead();
        }
        
        if (nbt.hasKey("PickupDelay"))
        {
            this.delayBeforeCanPickup = nbt.getShort("PickupDelay");
        }

        if (nbt.hasKey("Owner"))
        {
            this.owner = nbt.getString("Owner");
        }

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
        if (this.getEntityItem() != null)
        {
            nbt.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
        
        if (this.getOwner() != null)
        {
        	nbt.setString("Owner", this.owner);
        }
        
        nbt.setShort("PickupDelay", (short)this.delayBeforeCanPickup);

	}
	
	
}
