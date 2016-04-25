package com.lulan.shincolle.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.lulan.shincolle.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFrame extends BasicBlock {
	
	IIcon[] icons = new IIcon[5];
	Random rand = new Random();
	
	public BlockFrame() {
		super(Material.glass);
		this.setBlockName("BlockFrame");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(1F);
	    this.setResistance(40F);
	    this.setStepSound(soundTypeMetal);
	    this.setLightOpacity(0);
	    
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox((double)x + 0.0625D, (double)y, (double)z + 0.0625D, (double)x + 0.9375D, (double)y + 1D, (double)z + 0.9375D);
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon(String.format("shincolle:BlockFrame2"));
		icons[1] = iconRegister.registerIcon(String.format("shincolle:BlockFrame3"));
		icons[2] = iconRegister.registerIcon(String.format("shincolle:BlockFrame4"));
		icons[3] = iconRegister.registerIcon(String.format("shincolle:BlockFrame5"));
		icons[4] = iconRegister.registerIcon(String.format("shincolle:BlockFrameTop"));
	}
	
	//side: 0:bottom, 1:top, 2:N, 3:S, 4:W, 5:E
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
		switch(side) {
		case 0:
		case 1:
			return icons[4];
		default:
			return icons[rand.nextInt(4)];
		}
    }
	
	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return true;
    }
	
	//ladder moving
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		//if client player
		if(entity instanceof EntityPlayer && world.isRemote) {
			GameSettings keySet = ClientProxy.getGameSetting();
			
			if(keySet.keyBindForward.getIsKeyPressed()) {
				entity.addVelocity(0D, 0.05D, 0D);
			}
		}
		
		if(entity.motionY < -0.1D) {
			entity.motionY = -0.1D;
		}
		else if(entity.motionY > 0.3D) {
			entity.motionY = 0.2D;
		}
		
		if(entity.isSneaking()) {
			entity.motionY = 0.08D;
		}
	}
	
//	//can leash TODO issue: knot will setDead after 100 ticks
//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
//        ItemStack i = player.inventory.getCurrentItem();
//        
//		if(i != null && i.getItem() instanceof ItemLead) {
//			if(world.isRemote) {
//	        	return true;
//	        }
//			else {
//				//set leash
//				EntityLeashKnot knot = EntityLeashKnot.getKnotForBlock(world, x, y, z);
//		        
//				//get all nearby entity leashed by player
//				double d0 = 7.0D;
//		        List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox((double)x - d0, (double)y - d0, (double)z - d0, (double)x + d0, (double)y + d0, (double)z + d0));
//
//		        if(list != null) {
//		        	for(EntityLiving ent : list) {
//		        		if(ent.getLeashed() && ent.getLeashedToEntity() == player) {
//		        			//if no knot, create one
//		                    if(knot == null) {
//		                    	knot = EntityLeashKnot.func_110129_a(world, x, y, z);
//		                    }
//
//		                    //leash the entity to knot
//		                    ent.setLeashedToEntity(knot, true);
//		                }
//		        	}
//		        }
//		        
//		        //tweak knot position
//		        if(knot != null) {
//		        	switch(side) {
//		        	case 0:
//		        		knot.setPosition(knot.posX, knot.posY - 0.5D, knot.posZ);
//		        		break;
//		        	case 1:
//		        		knot.setPosition(knot.posX, knot.posY + 0.5D, knot.posZ);
//						break;
//					case 2:
//						knot.setPosition(knot.posX, knot.posY, knot.posZ - 0.5D);
//						break;
//					case 3:
//						knot.setPosition(knot.posX, knot.posY, knot.posZ + 0.5D);
//						break;
//					case 4:
//						knot.setPosition(knot.posX - 0.5D, knot.posY, knot.posZ);
//						break;
//					case 5:
//						knot.setPosition(knot.posX + 0.5D, knot.posY, knot.posZ);
//						break;
//			        }
//		        }
//			}//end server side
//		}//end holding lead
//		
//		return false;
//    }

	
	
}
