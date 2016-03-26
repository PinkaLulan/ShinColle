package com.lulan.shincolle.block;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileMulti;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.MulitBlockHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

abstract public class BasicBlockMulti extends BasicBlockContainer {

	protected IIcon iconInvsible;
	
	
	//指定方塊類型block
	public BasicBlockMulti(Material material) {
		super(material);
	}
	
	//無指定類型時 預設為rock型
	public BasicBlockMulti() {
		super();
	}

	//multi block都會依照meta切換是否顯示為透明方塊, 因此設false使透明狀態時正常render
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	//使用兩種icon: 一般icon跟透明狀態icon
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
		this.iconInvsible = iconRegister.registerIcon(Reference.MOD_ID+":BlockInvisible");
	}
	
	//meta非0時表示已經形成multi block結構, 因此將方塊改為透明貼圖
	@Override
	public IIcon getIcon(int side, int meta) {
	    if(meta > 0) {
	    	return this.iconInvsible;
	    }
	    else {
	    	return this.blockIcon;
	    }    
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
	}

	//右鍵點到方塊時呼叫此方法
	//參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {	//client端只需要收到true
    		return true;
    	}
		else if(!player.isSneaking()) {	//server端偵測是否可以成形	
			BasicTileMulti entity = (BasicTileMulti)world.getTileEntity(x, y, z);

			if(entity != null) {
				if(entity.hasMaster()) {	//該方塊已經成形, 則打開GUI
					LogHelper.info("DEBUG : open multi block GUI");
					switch(entity.getStructType()) {
					case 1:  //large shipyard off
					case 2:  //large shipyard on
						FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.LARGESHIPYARD, world, 
								entity.getMasterX(), entity.getMasterY(), entity.getMasterZ());
						break;
					}
					return true;			//已開啟GUI, 回傳true避免手上的東西用出去
				}
				else {						//該方塊尚未成形, 檢查是否可以成形
					if(entity instanceof TileMultiGrudgeHeavy) {
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
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BasicTileMulti tile = (BasicTileMulti)world.getTileEntity(x, y, z);

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
	
	//將tile entity資料寫到block metadata中, 輸入world可以判定是client還server端
	//type: 0:normal 1:large shipyard off 2:large shipyard on 3:large workshop off 4:large workshop on
	public static void updateBlockState(World world, int x, int y, int z, int type) {
		BasicTileMulti tile = (BasicTileMulti)world.getTileEntity(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, type, 2);
	}
	
	//打破master方塊時, 掉落物保留tile entity的資料
//	@Override
//	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
//        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
//
//        int count = quantityDropped(metadata, fortune, world.rand);
//        for(int i = 0; i < count; i++)
//        {
//            Item item = getItemDropped(metadata, world.rand, fortune);
//            if (item != null)
//            {
//                ret.add(new ItemStack(item, 1, damageDropped(metadata)));
//            }
//        }
//        return ret;
//    }
	

}
