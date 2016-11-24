package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class OwnerPaper extends BasicItem
{
	
	public static final String SignNameA = "SignNameA";	//player name tag
	public static final String SignNameB = "SignNameB";
	public static final String SignIDA = "SignIDA";		//player id tag
	public static final String SignIDB = "SignIDB";
	private static final String NAME = "OwnerPaper";
	
	
	public OwnerPaper()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
        
        GameRegistry.register(this);
	}
	
	//TODO rewrite onItemRightClick
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }
//	//right click to sign the paper
//	@Override
//	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
////		if(!world.isRemote) { TODO: review: is server only in 1.9.4?
//			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
//
//			if(extProps != null) {
//				//first time use
//				if(!itemstack.hasTagCompound()) {
//					itemstack.setTagCompound(new NBTTagCompound());
//					itemstack.getTagCompound().setString(SignNameA, player.getDisplayName());
//					itemstack.getTagCompound().setString(SignNameB, "");
//					itemstack.getTagCompound().setInteger(SignIDA, -1);
//					itemstack.getTagCompound().setInteger(SignIDB, -1);
//					itemstack.getTagCompound().setBoolean("signPos", false);
//					
//					//get player UID
//					itemstack.getTagCompound().setInteger(SignIDA, extProps.getPlayerUID());
//
//				}
//				//use > second time
//				else {
//					//signPos: true -> sign at A, false -> sign at B
//					if(itemstack.getTagCompound().getBoolean("signPos")) {
//						itemstack.getTagCompound().setString(SignNameA, player.getDisplayName());
//						itemstack.getTagCompound().setInteger(SignIDA, extProps.getPlayerUID());
//						itemstack.getTagCompound().setBoolean("signPos", false);
//					}
//					else {
//						itemstack.getTagCompound().setString(SignNameB, player.getDisplayName());
//						itemstack.getTagCompound().setInteger(SignIDB, extProps.getPlayerUID());
//						itemstack.getTagCompound().setBoolean("signPos", true);
//					}
//				}
//			}//end extprops != null
////		}
//		
//		return itemstack;
//	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {
    	if(itemstack.hasTagCompound())
    	{
    		list.add(TextFormatting.AQUA + itemstack.getTagCompound().getString(SignNameA));
    		list.add(TextFormatting.RED + String.valueOf(itemstack.getTagCompound().getInteger(SignIDA)));
    		list.add(TextFormatting.AQUA + itemstack.getTagCompound().getString(SignNameB));
    		list.add(TextFormatting.RED + String.valueOf(itemstack.getTagCompound().getInteger(SignIDB)));
    	}
    	
    }

    
}
