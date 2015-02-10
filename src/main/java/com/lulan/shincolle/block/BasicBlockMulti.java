package com.lulan.shincolle.block;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.reference.GUIs;
import com.lulan.shincolle.tileentity.BasicTileMulti;
import com.lulan.shincolle.tileentity.TileMultiLargeShipyard;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.MulitBlockHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

abstract public class BasicBlockMulti extends BasicBlockContainer {

	
	//指定方塊類型block
	public BasicBlockMulti(Material material) {
		super(material);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	//無指定類型時 預設為rock型
	public BasicBlockMulti() {
		this(Material.rock);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	abstract public TileEntity createNewTileEntity(World world, int i);

	//方塊圖示登錄
	//取出方塊名稱(不含mod名稱)作為參數丟給icon register來登錄icon
	//注意icon只在client端才需要執行
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
		
	}
	
	/**右鍵點到方塊時呼叫此方法
	 * 參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z
	 */	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {	//client端只需要收到true
    		return true;
    	}
		else if(!player.isSneaking()) {	//server端偵測是否可以成形	
			BasicTileMulti entity = (BasicTileMulti)world.getTileEntity(x, y, z);

			if(entity != null) {
				if(entity.hasMaster()) {	//該方塊已經成形, 則打開GUI
					LogHelper.info("DEBUG : multi block GUI open");
					switch(entity.getStructType()) {
					case GUIs.LARGESHIPYARD:
						FMLNetworkHandler.openGui(player, ShinColle.instance, GUIs.LARGESHIPYARD, world, 
								entity.getMasterX(), entity.getMasterY(), entity.getMasterZ());
						break;
					}
					return true;			//已開啟GUI, 回傳true避免手上的東西用出去
				}
				else {						//該方塊尚未成形, 檢查是否可以成形
					if(entity instanceof TileMultiLargeShipyard) {
						int type = MulitBlockHelper.checkMultiBlockForm(world, x, y, z);
						if(type > 0) {
							MulitBlockHelper.setupStructure(world, x, y, z, type);
							LogHelper.info("DEBUG : check multi block form: type "+type);
							return true;
						}				
					}		
				}
			}
		}

    	return false;	//沒事情做, 回傳false則會變成使用手上的物品
    }
	
	//若block周圍有方塊改變, 則偵測一次結構是否完整
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
//		BasicTileMulti tile = (BasicTileMulti) world.getTileEntity(x, y, z);
//	    
//	    if(tile != null && tile.hasMaster()) {	//若已經成形, 則檢查形狀是否仍然完整
//            if(MulitBlockHelper.checkMultiBlockForm(world, tile.getMasterX(), tile.getMasterY(), tile.getMasterZ()) <= 0) {
//            	MulitBlockHelper.resetStructure(world, tile.getMasterX(), tile.getMasterY(), tile.getMasterZ());
//            }
//        }
//	    super.onNeighborBlockChange(world, x, y, z, block);
	}
	
	//打掉方塊後, 掉落其內容物
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BasicTileMulti tile = (BasicTileMulti) world.getTileEntity(x, y, z);

		//原本形成結構, 則解除之
		if(!world.isRemote && tile != null && tile.hasMaster()) {
			MulitBlockHelper.resetStructure(world, tile.getMasterX(), tile.getMasterY(), tile.getMasterZ());
		}
		
//			//抓到tile entity後, 掃描全部slot內容物, 然後做成entity掉落出來
//			if(tile != null) {
//				for(int i = 0; i < tile.getSizeInventory(); i++) {  //check all slots
//					ItemStack itemstack = tile.getStackInSlot(i);
//	
//					if(itemstack != null) {
//						float f = world.rand.nextFloat() * 0.8F + 0.1F;  //設定要隨機噴出的range
//						float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
//						float f2 = world.rand.nextFloat() * 0.8F + 0.1F;
//	
//						while(itemstack.stackSize > 0) {
//							int j = world.rand.nextInt(21) + 10;
//							//如果物品超過一個隨機數量, 會分更多疊噴出
//							if(j > itemstack.stackSize) {  
//								j = itemstack.stackSize;
//							}
//	
//							itemstack.stackSize -= j;
//							//將item做成entity, 生成到世界上
//							EntityItem item = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
//							//如果有NBT tag, 也要複製到物品上
//							if(itemstack.hasTagCompound()) {
//								item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
//							}
//	
//						world.spawnEntityInWorld(item);	//生成entity
//						}
//					}
//				}	
//				world.func_147453_f(x, y, z, block);	//alert block changed
//			}

		//呼叫原先的breakBlock, 會把tile entity移除掉
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	//將tile entity資料寫到block metadata中
	public static void updateBlockState(World world, int x, int y, int z, int type) {
		BasicTileMulti tile = (BasicTileMulti)world.getTileEntity(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, type, 2);		
	}

}
