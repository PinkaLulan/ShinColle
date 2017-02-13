package com.lulan.shincolle.item;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class KaitaiHammer extends BasicItem
{
	
	private static final String NAME = "KaitaiHammer";

	
	public KaitaiHammer()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		this.setMaxDamage(20);
		this.setFull3D();
		this.setNoRepair();
		this.setHasSubtypes(false);
        
        GameRegistry.register(this);
	}
	
	//此鎚子可用於合成其他道具, 且不為消耗品, 故ContainerItem為耐久度-1的自己本身
	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}
	
	//此鎚子在合成桌中使用後, 耐久度 -1
	@Override
	public ItemStack getContainerItem(ItemStack stack)
	{
		//耐久度--
		int meta = stack.getItemDamage() + 1;
		stack.stackSize = 0;
		
		//物品達到耐久度上限, 回傳空物品
		if (meta >= this.getMaxDamage(stack)) return null;
		
		return new ItemStack(ModItems.KaitaiHammer, 1, meta);
	}

	//避免meta值影響到物品名稱
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return String.format("item.%s", Reference.MOD_ID + ":KaitaiHammer");
	}
	
	//左鍵用於自己的棲艦, 可使該棲艦一擊死亡 (轉回物品型態, 等級-1)
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		//entity is ship
		if (!player.world.isRemote && entity instanceof BasicEntityShip)
		{
			//player is owner
			if (TeamHelper.checkSameOwner(player, entity) || EntityHelper.checkOP(player))
			{
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), ((BasicEntityShip) entity).getMaxHealth() * 1.1F);
				
				//item meta+1
				int meta = stack.getItemDamage();
				
				if (!player.capabilities.isCreativeMode)
				{
					meta++;
				}
				
				if (meta >= stack.getMaxDamage())
				{
					//destroy the hammer
					if (player.inventory.getCurrentItem() != null && 
						player.inventory.getCurrentItem().getItem() == ModItems.KaitaiHammer)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
					}
				}
				else
				{
					stack.setItemDamage(meta);
				}
			}
			
			return true;
		}
		
        return false;
    }
	
	
}
