package com.lulan.shincolle.block;

import com.lulan.shincolle.creativetab.CreativeTabSC;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

abstract public class BasicBlockContainer extends BlockContainer {

	//指定方塊類型block
	public BasicBlockContainer(Material material) {
		super(material);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	//無指定類型時 預設為rock型
	public BasicBlockContainer() {
		this(Material.rock);
		this.setCreativeTab(CreativeTabSC.SC_TAB);	//加入到creative tab中
	}
	
	//name設定用方法: 將原本mc給的block名稱 去掉.之前的字串 以便另外串上mod名稱形成的字串
	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
	
	//將name冠上mod名稱 用於之後給各語系檔案放上正確名稱
	//格式為tile.MOD名稱:方塊名稱.name
	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.MOD_ID+":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	//不給icon, 全都用custom render block
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
	}
	
	//new tile entity instance in child class 
	abstract public TileEntity createNewTileEntity(World world, int i);
	
	/**方塊放下時設定其朝向
	 * parm: world,x座標,y座標,z座標,玩家,物品
	 * 設定meta方法: setBlockMetadataWithNotify parm:x,y,z,metadata,flag(1:設定此方塊要update  2:除了1還發送更新封包給client)
	 * metadata代表的方向要自行決定  一般依照block六方向貼圖的順序: 
	 * 0:bottom 1:top 2:north 3:south 4:west 5:east
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {		
		//由玩家的旋轉角度決定方塊的朝向
		int facecase = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (facecase == 0) {	//player face south , block -> 2:north
		    world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}     
		if (facecase == 1) {	//player face west  , block -> 5:east
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}      
		if (facecase == 2) {	//player face north , block -> 3:south
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);      	
		}       
		if (facecase == 3) {	//player face east  , block -> 4:west
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
   }


}
