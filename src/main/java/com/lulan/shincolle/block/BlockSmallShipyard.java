package com.lulan.shincolle.block;

import java.util.Random;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.handler.GuiHandler;
import com.lulan.shincolle.reference.GUIs;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSmallShipyard extends BasicBlockContainer {

	public Random rand = new Random();
	public static boolean keepInventory = false;
	private static final double[] smoke1 = new double[] {0.72, 1.1, 0.55};	//主煙囪 粒子位置
	private static final double[] smoke2 = new double[] {0.22, 0.8, 0.7};	//中煙囪 粒子位置
	private static final double[] smoke3 = new double[] {0.47, 0.6, 0.25};	//小煙囪 粒子位置
	
	
	public BlockSmallShipyard() {
		super(); //不指定型態 預設即為rock
		this.setBlockName("BlockSmallShipyard");
		this.setHardness(10F);
	    this.setHarvestLevel("pickaxe", 3);
	    this.setLightLevel(4);
	}
	
	//非標準方形方塊  要傳-1表示用自己的render
	@Override
	public int getRenderType() {
		return -1;	//-1 = non standard render
	}
	
	//非標準方形方塊  設為false
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	//非標準方形方塊  設為false
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntitySmallShipyard();
	}
	
	//打掉方塊後, 掉落其內容物
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		if(!keepInventory) {
			TileEntitySmallShipyard tileentity = (TileEntitySmallShipyard)world.getTileEntity(x, y, z);
		
			//抓到tile entity後, 掃描全部slot內容物, 然後做成entity掉落出來
			if(tileentity != null) {
				for(int i = 0; i < tileentity.getSizeInventory(); i++) {  //check all slots
					ItemStack itemstack = tileentity.getStackInSlot(i);
		
					if(itemstack != null) {
						float f = this.rand.nextFloat() * 0.8F + 0.1F;  //設定要隨機噴出的range
						float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
		
						while(itemstack.stackSize > 0) {
							int j = this.rand.nextInt(21) + 10;
							//如果物品超過一個隨機數量, 會分更多疊噴出
							if(j > itemstack.stackSize) {  
								j = itemstack.stackSize;
							}
		
							itemstack.stackSize -= j;
							//將item做成entity, 生成到世界上
							EntityItem item = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
							//如果有NBT tag, 也要複製到物品上
							if(itemstack.hasTagCompound()) {
								item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
							}
		
						world.spawnEntityInWorld(item);	//生成entity
						}
					}
				}	
				world.func_147453_f(x, y, z, block);	//???
			}
		}
		//呼叫原先的breakBlock, 會把tile entity移除掉
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	/**右鍵點到方塊時呼叫此方法
	 * 參數: world,方塊x,y,z,玩家,玩家面向,玩家點到的x,y,z
	 */	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {	//client端只需要收到true
    		return true;
    	}
		else if (!player.isSneaking()) {  //server端需要處理玩家動作, 如shift等
    		TileEntitySmallShipyard entity = (TileEntitySmallShipyard) world.getTileEntity(x, y, z);
    		
    		if (entity != null) {	//開啟方塊GUI 參數:玩家,mod instance,gui ID,world,座標xyz
    			FMLNetworkHandler.openGui(player, ShinColle.instance, GUIs.SMALLSHIPYARD, world, x, y, z);
    		}
    		return true;
    	}
    	else {
    		return false;
    	}
    }
	
	//spawn particle: largesmoke, posX, posY, posZ, motionX, motionY, motionZ
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		
		//取得旋轉後的新位置
		double[] smokeR1 = new double[3];
		double[] smokeR2 = new double[3];
		double[] smokeR3 = new double[3];
		
		smokeR1 = ParticleHelper.getNewPosition(smoke1[0], smoke1[1], smoke1[2], meta, 1);
		smokeR2 = ParticleHelper.getNewPosition(smoke2[0], smoke2[1], smoke2[2], meta, 1);
		smokeR3 = ParticleHelper.getNewPosition(smoke3[0], smoke3[1], smoke3[2], meta, 1);
		
		//if active -> spawn smoke
		if(meta>3) {	//meta=4~7 = active
			//world.setBlockMetadataWithNotify(x, y, z, 0, 2);	//send meta update packet
			switch(rand.nextInt(3)) {	//使三根煙囪分開冒煙
			case 0:
				//主煙囪特效
				world.spawnParticle("smoke", (double)x+smokeR1[0], (double)y+smokeR1[1], (double)z+smokeR1[2], 0.0D, 0D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR1[0], (double)y+smokeR1[1]+0.1D, (double)z+smokeR1[2], 0.0D, 0.005D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR1[0], (double)y+smokeR1[1]+0.2D, (double)z+smokeR1[2], 0.0D, 0.01D, 0.0D);
				//小煙囪特效
				world.spawnParticle("smoke", (double)x+smokeR3[0], (double)y+smokeR3[1], (double)z+smokeR3[2], 0.0D, 0D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR3[0], (double)y+smokeR3[1]+0.1D, (double)z+smokeR3[2], 0.0D, 0.01D, 0.0D);
				break;
			case 1:
				//主煙囪特效
				world.spawnParticle("smoke", (double)x+smokeR1[0], (double)y+smokeR1[1], (double)z+smokeR1[2], 0.0D, 0D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR1[0], (double)y+smokeR1[1]+0.1D, (double)z+smokeR1[2], 0.0D, 0.005D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR1[0], (double)y+smokeR1[1]+0.2D, (double)z+smokeR1[2], 0.0D, 0.01D, 0.0D);				//中煙囪特效
				//中煙囪特效
				world.spawnParticle("smoke", (double)x+smokeR2[0], (double)y+smokeR2[1], (double)z+smokeR2[2], 0.0D, 0D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR2[0], (double)y+smokeR2[1]+0.1D, (double)z+smokeR2[2], 0.0D, 0.01D, 0.0D);
				break;
			case 2:
				//中煙囪特效
				world.spawnParticle("smoke", (double)x+smokeR2[0], (double)y+smokeR2[1], (double)z+smokeR2[2], 0.0D, 0D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR2[0], (double)y+smokeR2[1]+0.1D, (double)z+smokeR2[2], 0.0D, 0.01D, 0.0D);
				//小煙囪特效
				world.spawnParticle("smoke", (double)x+smokeR3[0], (double)y+smokeR3[1], (double)z+smokeR3[2], 0.0D, 0D, 0.0D);
				world.spawnParticle("smoke", (double)x+smokeR3[0], (double)y+smokeR3[1]+0.1D, (double)z+smokeR3[2], 0.0D, 0.01D, 0.0D);
				break;
			default:
				break;
			}//end switch		
		}//end if
	}

	//將tile entity資料寫到block metadata中
	public static void updateBlockState(boolean isBuilding, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);

		if (isBuilding) {	//啟動中, 將meta設為4~7
			if(meta < 4) {	//檢查一下meta < 4 才需要更新meta
				world.setBlockMetadataWithNotify(x, y, z, meta+4, 2);
			}
		}
		else {				//啟動中, 將meta設為0~3
			if(meta > 3) {	//檢查一下meta > 3 才需要更新meta
				world.setBlockMetadataWithNotify(x, y, z, meta-4, 2);
			}
		}
		
		//unknow function
		if (entity != null) {
			entity.validate();
			world.setTileEntity(x, y, z, entity);
		}
		
	}

	

}
