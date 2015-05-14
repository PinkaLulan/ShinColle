package com.lulan.shincolle.utility;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.IFluidBlock;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.EntityRensouhou;
import com.lulan.shincolle.entity.EntityRensouhouS;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.IShipAttack;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

public class EntityHelper {

	private static Random rand = new Random();
	
	public EntityHelper() {}
	
	/**check block is safe (not solid block) */
	public static boolean checkBlockSafe(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return checkBlockSafe(block);
	}
	
	/**check block is safe (not solid block) */
	public static boolean checkBlockSafe(Block block) {
		if(block == Blocks.air || block == null || checkBlockIsLiquid(block)) {
    		return true;
    	}	
		return false;
	}
	
	/**check block is liquid (not air or solid block) */
	public static boolean checkBlockIsLiquid(Block block) {
        if(block instanceof BlockLiquid || block instanceof IFluidBlock || block instanceof BlockFluidBase) {
            return true;
        }		
		return false;
	}
	
	/**check entity is in liquid (not air or solid block) */
	public static boolean checkEntityIsInLiquid(Entity entity) {
		Block block = entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), (int)(entity.boundingBox.minY + 0.5D), MathHelper.floor_double(entity.posZ));
		return checkBlockIsLiquid(block);
	}
	
	/**check entity is free to move in the block */
	public static boolean checkEntityIsFree(Entity entity) {
		Block block = entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), (int)(entity.boundingBox.minY + 0.5D), MathHelper.floor_double(entity.posZ));
		return checkBlockSafe(block);
	}
	
	/**check is same owner for ship (host's owner == target's owner) */
	public static boolean checkSameOwner(EntityLivingBase host, EntityLivingBase target) {
		EntityLivingBase getOwnerA = null;
		EntityLivingBase getOwnerB = null;
		
		if(host != null && target != null) {
			getOwnerA = getOwnerFromEntity(host);
			getOwnerB = getOwnerFromEntity(target);
			
			//檢查uuid是否相同
			if(getOwnerA != null && getOwnerB != null) {
				return getOwnerA.getUniqueID().equals(getOwnerB.getUniqueID());
			}
		}
		
		return false;
	}
	
	/**get owner from entity */
	public static EntityLivingBase getOwnerFromEntity(EntityLivingBase host) {
		//get owner from target
		if(host instanceof EntityPlayer) {
			return host;
		}
		else if(host instanceof EntityTameable) {
			return ((EntityTameable)host).getOwner();
		}
		else if(host instanceof BasicEntityAirplane) {
			//先取得airplane的owner(為一種Ship), 再取得該ship的owner(為一種EntityPlayer)
			EntityLivingBase owner = ((BasicEntityAirplane)host).getOwner();
			
			if(owner != null) {
				return ((BasicEntityShip)owner).getOwner();
			}
			else {
				return null;
			}
		}
		else if(host instanceof EntityRensouhou) {
			//先取得連裝砲的owner(為一種Ship), 再取得該ship的owner(為一種EntityPlayer)
			EntityLivingBase owner = ((EntityRensouhou)host).getOwner();
			
			if(owner != null) {
				return ((BasicEntityShip)owner).getOwner();
			}
			else {
				return null;
			}
		}
		else if(host instanceof EntityRensouhouS) {
			//先取得連裝砲的owner(為一種Ship), 再取得該ship的owner(為一種EntityPlayer)
			EntityLivingBase owner = ((EntityRensouhouS)host).getOwner();
			
			if(owner != null) {
				return ((BasicEntityShip)owner).getOwner();
			}
			else {
				return null;
			}
		}
		else if(host instanceof IShipAttack) {
			return ((IShipAttack) host).getOwner();
		}
		
		return null;
	}
	
	/**check entity ID is not same */
    public static boolean checkNotSameEntityID(Entity enta, Entity entb) {
		if(enta.getEntityId() == entb.getEntityId()) {
			return false;
		}
    	
		return true;
	}

	/**check host's owner is EntityPlayer */
	public static boolean checkOwnerIsPlayer(EntityLivingBase host) {
		EntityLivingBase getOwner = null;
		
		if(host != null) {
			if(host instanceof EntityPlayer || host instanceof BasicEntityAirplane || 
					host instanceof BasicEntityShip || host instanceof EntityRensouhou ||
					host instanceof EntityRensouhouS) {
				return true;
			}
			else if(host instanceof EntityTameable) {
				getOwner = ((EntityTameable)host).getOwner();
				return (getOwner != null) && (getOwner instanceof EntityPlayer);
			}
		}
		
		return false;
	}
	
	/** */
	public static boolean checkOP(EntityPlayer player) {
		MinecraftServer server = ServerProxy.getServer();
		return server.getConfigurationManager().func_152596_g(player.getGameProfile());
	}
	
	/**get entity by ID */
	public static Entity getEntityByID(int entityID, int worldID, boolean isClient) {
		World world;
		
		if(isClient) {
			world = ClientProxy.getClientWorld();
		}
		else {
			world = DimensionManager.getWorld(worldID);
		}
		
		if(world != null) {
			for(Object obj: world.loadedEntityList) {
				if(entityID != -1 && ((Entity)obj).getEntityId() == entityID) {
					return ((Entity)obj);
				}
			}
		}
			
		LogHelper.info("DEBUG : cannot fund entity "+entityID+" in world "+worldID+" client? "+world.isRemote);
		return null;
	}
	
	/**get player is online by entity */
	public static EntityPlayerMP getOnlinePlayer(EntityLivingBase entity) {
		if(entity != null) {
			//get online id list (server side only)
			List onlineList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			Iterator iter = onlineList.iterator();
			
			while(iter.hasNext()) {
				EntityPlayerMP player = (EntityPlayerMP)iter.next();
			    if(player.getUniqueID().equals(entity.getUniqueID())) {
			    	return player;
			    }
			}
		}
		return null;	//player offline
	}
	
	/**process player sync data */
	public static void setPlayerExtProps(int[] value) {
		EntityPlayer player = ClientProxy.getClientPlayer();
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			extProps.setRingActiveI(value[0]);
			extProps.setMarriageNum(value[1]);
			
			//disable fly
			if(!extProps.isRingActive() && !player.capabilities.isCreativeMode && extProps.isRingFlying()) {
				LogHelper.info("DEBUG : cancel fly by right click");
				player.capabilities.isFlying = false;
				extProps.setRingFlying(false);
			}
		}
	}
	
	/**process player sync data */
	public static void setPlayerByGUI(int value, int value2) {
		EntityPlayer player = ClientProxy.getClientPlayer();
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			switch(value) {
			case 0:
				extProps.setRingActiveI(value2);
				break;
			case 1:
				extProps.setMarriageNum(value2);
				break;
			}
		}
	}
	
	/**process GUI click */
	public static void setEntityByGUI(BasicEntityShip entity, int button, int value) {
		if(entity != null) {
			switch(button) {
			case ID.B.ShipInv_Melee:
				entity.setEntityFlagI(ID.F.UseMelee, value);
				break;
			case ID.B.ShipInv_AmmoLight:
				entity.setEntityFlagI(ID.F.UseAmmoLight, value);
				break;
			case ID.B.ShipInv_AmmoHeavy:
				entity.setEntityFlagI(ID.F.UseAmmoHeavy, value);
				break;
			case ID.B.ShipInv_AirLight:
				entity.setEntityFlagI(ID.F.UseAirLight, value);
				break;
			case ID.B.ShipInv_AirHeavy:
				entity.setEntityFlagI(ID.F.UseAirHeavy, value);
				break;
			case ID.B.ShipInv_FollowMin:
				entity.setStateMinor(ID.N.FollowMin, value);
				break;
			case ID.B.ShipInv_FollowMax:
				entity.setStateMinor(ID.N.FollowMax, value);
				break;
			case ID.B.ShipInv_FleeHP:
				entity.setStateMinor(ID.N.FleeHP, value);
				break;
			case ID.B.ShipInv_TarAI:
				entity.setStateMinor(ID.N.TargetAI, value);
				break;
			case ID.B.ShipInv_AuraEffect:
				entity.setEntityFlagI(ID.F.UseRingEffect, value);
				break;
			}
		}
		else {
			LogHelper.info("DEBUG : set entity by GUI fail, entity null");
		}
	}
	
	/**process Shipyard GUI click */
	public static void setTileEntityByGUI(TileEntity tile, int button, int value, int value2) {
		if(tile != null) {
			if(tile instanceof TileEntitySmallShipyard) {
//				LogHelper.info("DEBUG : set tile entity value "+button+" "+value);
				((TileEntitySmallShipyard)tile).setBuildType(value);
				return;
			}
			if(tile instanceof TileMultiGrudgeHeavy) {
//				LogHelper.info("DEBUG : set tile entity value "+button+" "+value+" "+value2);
				
				switch(button) {
				case ID.B.Shipyard_Type:		//build type
					((TileMultiGrudgeHeavy)tile).setBuildType(value);
					break;
				case ID.B.Shipyard_InvMode:		//select inventory mode
					((TileMultiGrudgeHeavy)tile).setInvMode(value);
					break;
				case ID.B.Shipyard_SelectMat:	//select material
					((TileMultiGrudgeHeavy)tile).setSelectMat(value);
					break;
				case ID.B.Shipyard_INCDEC:			//material inc,dec
					setLargeShipyardBuildMats((TileMultiGrudgeHeavy)tile, button, value, value2);
					break;
				}	
			}			
		}
		else {
			LogHelper.info("DEBUG : set tile entity by GUI fail, tile is null");
		}	
	}

	/**增減large shipyard的matBuild[] */
	private static void setLargeShipyardBuildMats(TileMultiGrudgeHeavy tile, int button, int matType, int value) {
		int num = 0;
		int num2 = 0;
		boolean stockToBuild = true;	//false = build -> stock , true = stock -> build
		
		//value2轉換為數量
		switch(value) {
		case 0:
		case 4:
			num = 1000;
			break;
		case 1:
		case 5:
			num = 100;
			break;
		case 2:
		case 6:
			num = 10;
			break;
		case 3:
		case 7:
			num = 1;
			break;	
		}
		
		if(value > 3) stockToBuild = false;
		
		//判定num是否要修改, 再增減MatStock跟MatBuild
		if(stockToBuild) {	//matStock -> matBuild
			//材料不夠指定數量, 則num改為剩餘全部材料數量
			if(num > tile.getMatStock(matType)) num = tile.getMatStock(matType);
			//材料超過製造上限(1000), 則num降為上限數量
			if(num + tile.getMatBuild(matType) > 1000) num = 1000 - tile.getMatBuild(matType);
			
			tile.addMatStock(matType, -num);
			tile.addMatBuild(matType, num);
		}
		else {			//matBuild -> matStock
			//材料不夠指定數量, 則num改為剩餘全部材料數量
			if(num > tile.getMatBuild(matType)) num = tile.getMatBuild(matType);
			
			tile.addMatBuild(matType, -num);
			tile.addMatStock(matType, num);
		}	
	}
	
	/**find random position with block check
	//mode 0: find y = Y+1 ~ Y+3 and XZ at side of target
	//mode 1: find y = Y-2 ~ Y+2 (NYI)
	 */
	public static double[] findRandomPosition(Entity host, Entity target, double minDist, double randDist, int mode) {
		Block findBlock = null;
		double[] newPos = new double[] {0D, 0D, 0D};
		
		//try 25 times
		for(int i = 0; i < 25; i++) {
			switch(mode) {
			case 0:	//y = y+1~y+3
				newPos[1] = rand.nextDouble() * 2D + target.posY + 1D;
				
				//find side position
				newPos[0] = rand.nextDouble() * randDist + minDist;	//ran = min + randN
				newPos[2] = rand.nextDouble() * randDist + minDist;	
				
//				//輪擺位移法
//				if(target.posX - host.posX > 0) {
//					newPos[0] = target.posX + newPos[0];
//				}
//				else {
//					newPos[0] = target.posX - newPos[0];
//				}
//				
//				if(target.posZ - host.posZ > 0) {
//					newPos[2] = target.posZ + newPos[2];
//				}
//				else {
//					newPos[2] = target.posZ - newPos[2];
//				}
				//隨機選象限法
				switch(rand.nextInt(4)) {
				case 0:
					newPos[0] = target.posX + newPos[0];
					newPos[2] = target.posZ - newPos[2];
					break;
				case 1:
					newPos[0] = target.posX - newPos[0];
					newPos[2] = target.posZ + newPos[2];
					break;
				case 2:
					newPos[0] = target.posX - newPos[0];
					newPos[2] = target.posZ - newPos[2];
					break;
				case 3:
					newPos[0] = target.posX + newPos[0];
					newPos[2] = target.posZ + newPos[2];
					break;
				}//end inner switch
				break;
			case 1: //y = y-2~y+2, minDist unused
				//NYI
				break;
			}//end mode switch
			//check block
			findBlock = host.worldObj.getBlock((int)newPos[0], (int)newPos[1], (int)newPos[2]);
			if(findBlock != null && (findBlock == Blocks.air || findBlock == Blocks.water)) {
				return newPos;
			}	
		}
		
		//find block fail, return target position
		newPos[0] = target.posX;
		newPos[1] = target.posY + 1D;
		newPos[2] = target.posZ;
		
		return newPos;
	}	
	
	/**由XYZ三個向量值計算 [XZ夾角(Yaw), XY夾角(Pitch)], return單位為度數
	 */
	public static float[] getLookDegree(double motX, double motY, double motZ) {
		//計算模型要轉的角度 (RAD, not DEG)
        double f1 = MathHelper.sqrt_double(motX*motX + motZ*motZ);
        float[] degree = new float[2];
        
        degree[1] = (float)(Math.atan2(motY, f1)) * 57.2958F;
        degree[0] = (float)(Math.atan2(motX, motZ)) * 57.2958F;
        degree[0] = -degree[0];
        
        return degree;
	}

	
}
