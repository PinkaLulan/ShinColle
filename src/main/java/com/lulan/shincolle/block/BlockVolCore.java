package com.lulan.shincolle.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityVolCore;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVolCore extends BasicBlockContainer {

	
	public BlockVolCore() {
		super();  //不指定型態 預設即為rock
		this.setBlockName("BlockVolCore");
		this.setHardness(10F);
	    this.setHarvestLevel("pickaxe", 3);
	    this.setLightLevel(1F);
	    this.setResistance(600F);
	    
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityVolCore();
	}
	
	//打掉方塊後, 掉落其內容物
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntityVolCore tileentity = (TileEntityVolCore) world.getTileEntity(x, y, z);
	
		//抓到tile entity後, 掃描全部slot內容物, 然後做成entity掉落出來
		if(tileentity != null) {
			for(int i = 0; i < tileentity.getSizeInventory(); i++) {  //check all slots
				ItemStack itemstack = tileentity.getStackInSlot(i);
	
				if(itemstack != null) {
					float f = world.rand.nextFloat() * 0.8F + 0.1F;  //設定要隨機噴出的range
					float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
	
					while(itemstack.stackSize > 0) {
						int j = world.rand.nextInt(21) + 10;
						
						//如果物品超過一個隨機數量, 會分更多疊噴出
						if(j > itemstack.stackSize) {  
							j = itemstack.stackSize;
						}
	
						itemstack.stackSize -= j;
						//將item做成entity, 生成到世界上
						EntityItem item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
						//如果有NBT tag, 也要複製到物品上
						if(itemstack.hasTagCompound()) {
							item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
	
						world.spawnEntityInWorld(item);	//生成entity
					}
				}
			}
			
			world.func_147453_f(x, y, z, block);	//alert block changed
		}

		//呼叫原先的breakBlock, 會把tile entity移除掉
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	/**右鍵點到方塊時呼叫此方法
	 * 參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z
	 */	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
    		return true;
    	}
		else if(!player.isSneaking()) {  //server端需要處理玩家動作, 如shift等
			TileEntityVolCore tile = (TileEntityVolCore) world.getTileEntity(x, y, z);
    		
    		if(tile != null) {	//開啟方塊GUI 參數:玩家,mod instance,gui ID,world,座標xyz
    			FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.VOLCORE, world, x, y, z);
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {		
	}
		
		
}
