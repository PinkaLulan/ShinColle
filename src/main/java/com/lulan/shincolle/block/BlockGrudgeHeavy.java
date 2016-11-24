package com.lulan.shincolle.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.client.render.block.RenderLargeShipyard;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

public class BlockGrudgeHeavy extends BasicBlockMulti
{
	
	public static final String NAME = "BlockGrudgeHeavy";
	public static final String TILENAME = "TileMultiLargeShipyard";

	
	public BlockGrudgeHeavy()
	{
		super(Material.SAND);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setResistance(600F);
	    this.setSoundType(SoundType.SAND);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockGrudgeHeavy(this), this.getRegistryName());
        GameRegistry.registerTileEntity(TileMultiGrudgeHeavy.class, TILENAME);

	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileMultiGrudgeHeavy();
	}
	
	@SideOnly(Side.CLIENT)
    public void initModel()
	{
		super.initModel();
		
		//prevent property mapping to blockstate
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(MBS).build());
		
        //register tile entity render
        ClientRegistry.bindTileEntitySpecialRenderer(TileMultiGrudgeHeavy.class, new RenderLargeShipyard());
    
	}
	
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
		if (state.getValue(MBS) > 0)
		{
			return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
		}
		else
		{
			return EnumBlockRenderType.MODEL;
		}
    }
		
	//禁止該方塊產生掉落物, 所有掉落物都改在breakBlock生成
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new ArrayList<ItemStack>();	//直接回傳空的array (不能傳null會噴出NPE)
    }
	
	//方塊放置時, 將物品的mats數量取出存到tile的nbt中
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		super.onBlockPlacedBy(world, pos, state, placer, stack);
		
		//set init resource value
		TileEntity tile = world.getTileEntity(pos);
		
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			//將mats資料存到matStock中
			if (stack.hasTagCompound())
			{
				TileMultiGrudgeHeavy tile2 = (TileMultiGrudgeHeavy) tile;
				NBTTagCompound nbt = stack.getTagCompound();
				int[] mats = nbt.getIntArray("mats");
				int fuel = nbt.getInteger("fuel");
		        
				tile2.setMatStock(0, mats[0]);
				tile2.setMatStock(1, mats[1]);
				tile2.setMatStock(2, mats[2]);
				tile2.setMatStock(3, mats[3]);
				tile2.setPowerRemained(fuel);
			}
		}
	}
	
	//打掉方塊後, 掉落其內容物
	//heavy grudge打掉時, 會把matBuild跟matStock存在item的nbt中
	//注意tile會在這邊消滅掉, 所以getDrops呼叫時已經抓不到tile, 任何tile資料要留下的都要在此方法做完 (1.7.10)
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity getTile = world.getTileEntity(pos);
		
		if (getTile instanceof TileMultiGrudgeHeavy)
		{
			TileMultiGrudgeHeavy tile = (TileMultiGrudgeHeavy) getTile;
			
			//掃描matBuild跟matStock是否有存值, 有的話轉存到block item上並生成到world中
			float ranf1 = world.rand.nextFloat() * 0.5F + 0.2F;
			float ranf2 = world.rand.nextFloat() * 0.5F + 0.2F;
			float ranf3 = world.rand.nextFloat() * 0.5F + 0.2F;
			BasicEntityItem item = new BasicEntityItem(world, pos.getX() + ranf1, pos.getY() + ranf2, pos.getZ() + ranf3, new ItemStack(ModBlocks.BlockGrudgeHeavy, 1 ,0));
			NBTTagCompound nbt = new NBTTagCompound();
			
			int[] mats = new int[4];
			mats[0] = tile.getMatBuild(0) + tile.getMatStock(0);
			mats[1] = tile.getMatBuild(1) + tile.getMatStock(1);
			mats[2] = tile.getMatBuild(2) + tile.getMatStock(2);
			mats[3] = tile.getMatBuild(3) + tile.getMatStock(3);		
			
			//save nbt
			nbt.setIntArray("mats", mats);
			nbt.setInteger("fuel", tile.getPowerRemained());
			item.getEntityItem().setTagCompound(nbt);	//將nbt存到entity item中
			
			//spawn entity item
			world.spawnEntityInWorld(item);				//生成item entity
		}
		
		super.breakBlock(world, pos, state);
	}
	
	//隨機發出傳送門音效
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		//play portal sound
		if (rand.nextInt(50) == 0)
        {
            world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
    }

	//cancel water block side rendering
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
		IBlockState sideState = world.getBlockState(pos.offset(face));
		
		if (sideState != null && state.getValue(MBS) > 0 &&
			(sideState.getMaterial() == Material.WATER || sideState.getMaterial() == Material.LAVA))
		{
			return true;
		}
		
        return state.isOpaqueCube();
    }
	
	
}
