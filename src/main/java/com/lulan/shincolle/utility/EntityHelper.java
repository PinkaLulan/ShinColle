package com.lulan.shincolle.utility;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.IFluidBlock;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathEntity;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.ai.path.ShipPathPoint;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.EntityRensouhou;
import com.lulan.shincolle.entity.EntityRensouhouS;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		if(block.getMaterial() == Material.air || block == null || checkBlockIsLiquid(block)) {
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
	
	/**check entity is in (stand on, y+0D) liquid (not air or solid block) */
	public static boolean checkEntityIsInLiquid(Entity entity) {
		Block block = entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), (int)(entity.boundingBox.minY), MathHelper.floor_double(entity.posZ));
		return checkBlockIsLiquid(block);
	}
	
	/**check entity is free to move in (stand in, y+0.5D) the block */
	public static boolean checkEntityIsFree(Entity entity) {
		Block block = entity.worldObj.getBlock(MathHelper.floor_double(entity.posX), (int)(entity.boundingBox.minY + 0.5D), MathHelper.floor_double(entity.posZ));
		return checkBlockSafe(block);
	}
	
	/**check is same owner for ship (host's owner == target's owner) */
	public static boolean checkSameOwner(Entity host, Entity target) {
		Entity getOwnerA = null;
		Entity getOwnerB = null;
		
		if(host != null && target != null) {
			//for hostile mob
			if(host instanceof BasicEntityShipHostile && target instanceof BasicEntityShipHostile) {
				return true;
			}
			
			//for other entity
			getOwnerA = getOwnerFromEntity(host);
			getOwnerB = getOwnerFromEntity(target);
			
			//檢查uuid是否相同
			if(getOwnerA != null && getOwnerB != null) {
				return getOwnerA.getUniqueID().equals(getOwnerB.getUniqueID());
			}
		}
		
		return false;
	}
	
	//replace isInWater, check water block with NO extend AABB
	public static void checkDepth(IShipFloating entity) {
		Entity entityCD = (Entity) entity;
		Block BlockCheck = checkBlockWithOffset(entityCD, 0);
		double depth = 0;
		
		if(EntityHelper.checkBlockIsLiquid(BlockCheck)) {
			depth = 1;

			for(int i = 1; entityCD.posY + i < 255D; i++) {
				BlockCheck = checkBlockWithOffset(entityCD, i);
				
				if(EntityHelper.checkBlockIsLiquid(BlockCheck)) {
					depth++;
				}
				else {	//最上面碰到空氣類方塊才可以上浮, 否則不上浮
					if(BlockCheck.getMaterial() == Material.air) {
						entity.setStateFlag(ID.F.CanFloatUp, true);
					}
					else {
						entity.setStateFlag(ID.F.CanFloatUp, false);
					}
					break;
				}
			}		
			depth = depth - (double)(entityCD.posY - (int)entityCD.posY);
		}
		else {
			depth = 0;	
			entity.setStateFlag(ID.F.CanFloatUp, false);
		}
		
		entity.setShipDepth(depth);
	}
	
	/**get block from entity position with offset*/
	public static Block checkBlockWithOffset(Entity entity, int par1) {
		int blockX = MathHelper.floor_double(entity.posX);
	    int blockY = MathHelper.floor_double(entity.boundingBox.minY);
	    int blockZ = MathHelper.floor_double(entity.posZ);

	    return entity.worldObj.getBlock(blockX, blockY + par1, blockZ);    
	}
	
	/**get owner from entity */
	public static Entity getOwnerFromEntity(Entity host) {
		//get owner from target
		//若為玩家, 則直接回傳玩家
		if(host instanceof EntityPlayer) {
			return host;
		}
		//若為寵物, 則回傳寵物owner
		else if(host instanceof EntityTameable) {
			return ((EntityTameable)host).getOwner();
		}
		//若為其他用IShipAttack的, 直接取player owner
		else if(host instanceof IShipAttackBase) {
			return ((IShipAttackBase) host).getPlayerOwner();
		}
		
		
//		//若為飛機,座騎,召喚物, 則取得owner的owner
//		else if(host instanceof BasicEntityAirplane || host instanceof BasicEntityMount ||
//				host instanceof EntityRensouhou || host instanceof EntityRensouhouS) {
//			//先取得airplane的owner(為一種ship), 再取得該ship的owner(為一種EntityPlayer)
//			BasicEntityShip owner = (BasicEntityShip) ((IShipAttackBase)host).getOwner();
//			
//			if(owner != null) {
//				return owner.getOwner();
//			}
//		}
//		//若為其他用IshipAttack的, 直接取owner
//		else if(host instanceof IShipAttackBase) {
//			return ((IShipAttackBase) host).getOwner();
//		}
		
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
			   host instanceof BasicEntityShip || host instanceof BasicEntityMount ||
			   host instanceof EntityRensouhou || host instanceof EntityRensouhouS) {
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
	
	/**sync player extend props data by integer */
	public static void setPlayerExtProps(int[] value) {
		EntityPlayer player = ClientProxy.getClientPlayer();
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			extProps.setRingActiveI(value[0]);
			extProps.setMarriageNum(value[1]);
			
			//set team list
			BasicEntityShip teamship = null;
			for(int i = 0; i < 6; i++) {
				if(value[i+2] <= 0) {
					extProps.setTeamList(i, null, true);
				}
				else {
					teamship = (BasicEntityShip) EntityHelper.getEntityByID(value[i+2], 0, true);
					LogHelper.info("DEBUG : player extend props: set team "+i+" "+teamship);
					extProps.setTeamList(i, teamship, true);
				}
			}
			
			//disable fly if non-active
			if(!extProps.isRingActive() && !player.capabilities.isCreativeMode && extProps.isRingFlying()) {
				LogHelper.info("DEBUG : cancel fly by right click");
				player.capabilities.isFlying = false;
				extProps.setRingFlying(false);
			}
		}
	}
	
	/**sync player extend props data by boolean */
	public static void setPlayerExtProps(boolean[] value) {
		EntityPlayer player = ClientProxy.getClientPlayer();
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
			//set team selected
			for(int i = 0; i < 6; i++) {
				extProps.setTeamSelected(i, value[i]);
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

	/** update ship path navigator */
	public static void updateShipNavigator(IShipAttackBase entity) {
		EntityLiving entity2 = (EntityLiving) entity;
		ShipPathNavigate pathNavi = entity.getShipNavigate();
		ShipMoveHelper moveHelper = entity.getShipMoveHelper();
		
		//若有水空path, 則更新ship navigator
        if(pathNavi != null && moveHelper != null && !pathNavi.noPath()) {
        	//若同時有官方ai的路徑, 則清除官方ai路徑
        	if(!entity2.getNavigator().noPath()) {
        		entity2.getNavigator().clearPathEntity();
        	}

        	//若坐下或綁住, 則清除路徑
        	if(entity.getIsSitting() || entity.getIsLeashed()) {
        		entity.getShipNavigate().clearPathEntity();
        	}
        	else {
//            	LogHelper.info("DEBUG : AI tick: path navi update");
//            	LogHelper.info("DEBUG : AI tick: path length A "+this.getShipNavigate().getPath().getCurrentPathIndex()+" / "+this.getShipNavigate().getPath().getCurrentPathLength());
//            	LogHelper.info("DEBUG : AI tick: path length A "+this.getShipNavigate().getPath().getCurrentPathIndex());
    			
        		//用particle顯示path point
    			if(ConfigHandler.debugMode && entity2.ticksExisted % 20 == 0) {
    				ShipPathEntity pathtemp = pathNavi.getPath();
    				ShipPathPoint pointtemp;
//    				LogHelper.info("DEBUG : AI tick: path length A "+pathtemp.getCurrentPathIndex()+" / "+pathtemp.getCurrentPathLength()+" xyz: "+pathtemp.getPathPointFromIndex(0).xCoord+" "+pathtemp.getPathPointFromIndex(0).yCoord+" "+pathtemp.getPathPointFromIndex(0).zCoord+" ");
    				
    				for(int i = 0; i < pathtemp.getCurrentPathLength(); i++) {
    					pointtemp = pathtemp.getPathPointFromIndex(i);
    					//發射者煙霧特效
    			        TargetPoint point = new TargetPoint(entity2.dimension, entity2.posX, entity2.posY, entity2.posZ, 48D);
    					//路徑點畫紅色, 目標點畫綠色
    					if(i == pathtemp.getCurrentPathIndex()) {
    						CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(entity2, 16, pointtemp.xCoord + entity2.width * 0.5D, pointtemp.yCoord + 0.5D, pointtemp.zCoord + entity2.width * 0.5D, 0F, 0F, 0F, false), point);
    					}
    					else {
    						CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(entity2, 18, pointtemp.xCoord + entity2.width * 0.5D, pointtemp.yCoord + 0.5D, pointtemp.zCoord + entity2.width * 0.5D, 0F, 0F, 0F, false), point);
    					}
    				}
    			}
        	}

        	entity2.worldObj.theProfiler.startSection("ship navi");
        	pathNavi.onUpdateNavigation();
	        entity2.worldObj.theProfiler.endSection();
	        entity2.worldObj.theProfiler.startSection("ship move");
	        moveHelper.onUpdateMoveHelper();
	        entity2.worldObj.theProfiler.endSection();
		}

        if(!entity2.getNavigator().noPath()) {
//        	LogHelper.info("DEBUG : AI tick: path length B "+this.getNavigator().getPath().getCurrentPathIndex()+" / "+this.getNavigator().getPath().getCurrentPathLength());
			//用particle顯示path point
        	if(ConfigHandler.debugMode && entity2.ticksExisted % 20 == 0) {
				PathEntity pathtemp2 = entity2.getNavigator().getPath();
				PathPoint pointtemp2;
//				LogHelper.info("DEBUG : AI tick: path length B "+pathtemp2.getCurrentPathLength()+" "+pathtemp2.getPathPointFromIndex(0).xCoord+" "+pathtemp2.getPathPointFromIndex(0).yCoord+" "+pathtemp2.getPathPointFromIndex(0).zCoord+" ");
//				LogHelper.info("DEBUG : AI tick: path length B "+pathtemp2.getCurrentPathIndex());
				for(int i = 0; i < pathtemp2.getCurrentPathLength(); i++) {
					pointtemp2 = pathtemp2.getPathPointFromIndex(i);
					//發射者煙霧特效
			        TargetPoint point = new TargetPoint(entity2.dimension, entity2.posX, entity2.posY, entity2.posZ, 48D);
					//路徑點畫紅色, 目標點畫綠色
					if(i == pathtemp2.getCurrentPathIndex()) {
						CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(entity2, 16, pointtemp2.xCoord + entity2.width * 0.5D, pointtemp2.yCoord + 0.5D, pointtemp2.zCoord + entity2.width * 0.5D, 0F, 0F, 0F, false), point);
					}
					else {
						CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(entity2, 17, pointtemp2.xCoord + entity2.width * 0.5D, pointtemp2.yCoord + 0.5D, pointtemp2.zCoord + entity2.width * 0.5D, 0F, 0F, 0F, false), point);
					}
				}
			}
        }
	}
	
	/**ray trace for block, include liquid block
	 * 
	 */
	@SideOnly(Side.CLIENT)
    public static MovingObjectPosition getPlayerMouseOverBlock(double dist, float duringTicks) {
        EntityPlayer player = ClientProxy.getClientPlayer();
		
		Vec3 vec3 = player.getPosition(duringTicks);
        Vec3 vec31 = player.getLook(duringTicks);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * dist, vec31.yCoord * dist, vec31.zCoord * dist);
        
        //func_147447_a(所有視線追蹤皆用此方法) 參數: entity位置, entity視線最遠位置, 是否抓液體方塊, ??, 距離內沒抓到任何東西回傳MISS(而不是回傳null)
        return player.worldObj.func_147447_a(vec3, vec32, true, false, false);
    }
	
	/**ray trace for entity: 
	 * 1. get the farest block
	 * 2. create collision box (rectangle, diagonal vertex = player and farest block)
	 * 3. get entity in this box
	 * 4. check entity on player~block sight line
	 * 5. return nearest entity
	 */
	@SideOnly(Side.CLIENT)
	public static MovingObjectPosition getPlayerMouseOverEntity(double dist, float duringTicks) {
		EntityLivingBase viewer = ClientProxy.getMineraft().renderViewEntity;
		MovingObjectPosition lookBlock = null;
		
        if(viewer != null && viewer.worldObj != null) {
            lookBlock = viewer.rayTrace(dist, duringTicks);
            Vec3 vec3 = viewer.getPosition(duringTicks);

            //若有抓到方塊, 則d1改為抓到方塊的距離
            double d1 = dist;
            if(lookBlock != null) {
                d1 = lookBlock.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = viewer.getLook(duringTicks);
            double vec3x = vec31.xCoord * dist;
            double vec3y = vec31.yCoord * dist;
            double vec3z = vec31.zCoord * dist;
            Vec3 vec32 = vec3.addVector(vec3x, vec3y, vec3z);
            Vec3 vec33 = null;
            MovingObjectPosition lookEntity = null;
            Entity pointedEntity = null;
            
            //從玩家到目標方塊之間, 做出擴展1格的方形collision box, 抓出其中碰到的entity
            double f1 = 1D;
            List list = viewer.worldObj.getEntitiesWithinAABBExcludingEntity(viewer, viewer.boundingBox.addCoord(vec3x, vec3y, vec3z).expand(f1, f1, f1));
            double d2 = d1;

            //檢查抓到的entity, 是否在玩家~目標方塊的視線上
            for(int i = 0; i < list.size(); ++i) {
                Entity entity = (Entity)list.get(i);

                if(entity.canBeCollidedWith()) {
                	//檢查碰到的entity是否在視線上
                    double f2 = entity.getCollisionBorderSize();
                    AxisAlignedBB targetBox = entity.boundingBox.expand(f2, f2, f2);
                    MovingObjectPosition getObj = targetBox.calculateIntercept(vec3, vec32);

                    //若viewer完全塞在目標的box裡面
                    if(targetBox.isVecInside(vec3)) {
                        if(d2 >= 0D) {
                        	pointedEntity = entity;
                        	//抓到位置玩家位置或者目標box位置
                            vec33 = (getObj == null ? vec3 : getObj.hitVec);
                            //抓到距離改為0
                            d2 = 0D;
                        }
                    }
                    //其他有抓到目標的情況
                    else if(getObj != null) {
                        double d3 = vec3.distanceTo(getObj.hitVec);	//抓到距離

                        //若抓到距離在dist之內, 則判定為抓到目標
                        if(d3 < d2 || d2 == 0D) {
                        	//若抓到的是玩家自己的座騎, 且屬於不能互動的座騎
                            if(entity == viewer.ridingEntity && !entity.canRiderInteract()) {
                                //若dist設為0D, 才會抓到自己的座騎, 否則都無視座騎
                            	if(d2 == 0D) {
                                    pointedEntity = entity;
                                    vec33 = getObj.hitVec;
                                }
                            }
                            //其他非座騎entity
                            else {
                                pointedEntity = entity;
                                vec33 = getObj.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }
            }

            //若有抓到entity, 且抓到距離在視線碰到的最遠方塊之內, 才算有抓到entity
            if(pointedEntity != null && (d2 < d1 || lookBlock == null)) {
            	lookBlock = new MovingObjectPosition(pointedEntity, vec33);
            }
        }
        
        return lookBlock;
    }
	
	/** set ship guard, and check guard position is not same */
	public static void applyShipGuard(BasicEntityShip ship, int x, int y, int z) {
		if(ship != null) {
			int gx = ship.getStateMinor(ID.N.GuardX);
			int gy = ship.getStateMinor(ID.N.GuardY);
			int gz = ship.getStateMinor(ID.N.GuardZ);
			
			//same guard position, cancel guard mode
			if(gx == x && gy == y && gz == z) {
				ship.setStateFlag(ID.F.CanFollow, true);	//set follow
				ship.setStateMinor(ID.N.GuardX, -1);		//reset guard position
				ship.setStateMinor(ID.N.GuardY, -1);
				ship.setStateMinor(ID.N.GuardZ, -1);
			}
			//apply guard mode
			else {
				ship.setSitting(false);						//stop sitting
				ship.setStateFlag(ID.F.CanFollow, false);	//stop follow
				ship.setStateMinor(ID.N.GuardX, x);
				ship.setStateMinor(ID.N.GuardY, y);
				ship.setStateMinor(ID.N.GuardZ, z);
				ship.getShipNavigate().tryMoveToXYZ(x, y, z, 1D);
			}
		}
	}
	
	/** set ship attack target with team list */
	public static void applyTeamAttack(EntityPlayer player, int meta, Entity target) {
		if(target instanceof EntityLivingBase) {
			EntityLivingBase tar = (EntityLivingBase) target;
			ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			BasicEntityShip[] ships = props.getTeamListWithSelectState(meta);
			BasicEntityMount mounts = null;
			
			if(props != null) {
				switch(meta) {
				default:	//single mode
					if(ships[0] != null) {
						//設定ship攻擊目標
						ships[0].setSitting(false);
						ships[0].setAttackTarget(tar);
						
						//若該ship有騎乘座騎, 將座騎目標也設定
						if(ships[0].ridingEntity instanceof BasicEntityMount) {
							((BasicEntityMount)ships[0].ridingEntity).setAttackTarget(tar);
						}
					}
					break;
				case 1:		//group mode
				case 2:		//formation mode
					for(int i = 0; i < ships.length; i++) {
						if(ships[i] != null) {
							//設定ship攻擊目標
							ships[i].setSitting(false);
							ships[i].setAttackTarget(tar);
							
							//若該ship有騎乘座騎, 將座騎目標也設定
							if(ships[i].ridingEntity instanceof BasicEntityMount) {
								((BasicEntityMount)ships[i].ridingEntity).setAttackTarget(tar);
							}
						}
					}
					break;
				}//end switch
			}
		}
	}

	/** set ship move with team list */
	public static void applyTeamMove(EntityPlayer player, int meta, int side, int x, int y, int z) {
		ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		BasicEntityShip[] ships = props.getTeamListWithSelectState(meta);
		
		if(props != null) {
			switch(meta) {
			default:	//single mode
				if(ships[0] != null) {
					//設定ship移動地點
					applyShipGuard(ships[0], x, y, z);
					//若該ship有騎乘座騎, 將座騎目標也設定
					if(ships[0].ridingEntity instanceof BasicEntityMount) {
						((BasicEntityMount)ships[0].ridingEntity).getShipNavigate().tryMoveToXYZ(x, y, z, 1D);
					}
				}
				break;
			case 1:		//group mode
			case 2:		//formation mode
				for(int i = 0;i < ships.length; i++) {
					if(ships[i] != null) {
						//設定ship移動地點
						applyShipGuard(ships[i], x, y, z);
						
						//若該ship有騎乘座騎, 將座騎目標也設定
						if(ships[i].ridingEntity instanceof BasicEntityMount) {
							((BasicEntityMount)ships[i].ridingEntity).getShipNavigate().tryMoveToXYZ(x, y, z, 1D);
						}
					}
				}
				break;
			}//end switch
		}		
	}
	
	/** set ship sitting with team list, only called at server side
	 *  1. 若目標不在隊伍, 則單獨設定目標坐下
	 *  2. 若目標在隊伍, 則依照 pointer類型設定目標坐下
	 */
	public static void applyTeamSit(EntityPlayer player, int meta, int entityid) {
		ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		BasicEntityShip[] ships = props.getTeamListWithSelectState(meta);
		
		if(props != null) {
			//不在隊伍名單裡面
			if(props.checkInTeamList(entityid) < 0) {
				Entity target = getEntityByID(entityid, player.worldObj.provider.dimensionId, player.worldObj.isRemote);
				
				if(target instanceof IShipEmotion) {
					((IShipEmotion) target).setEntitySit();
				}
			}
			//有在隊伍中, 則依照pointer類型抓目標
			else {
				switch(meta) {
				default:	//single mode
					if(ships[0] != null) {
						//設定ship sit
						ships[0].setEntitySit();
					}
					break;
				case 1:		//group mode
				case 2:		//formation mode
					for(int i = 0; i < ships.length; i++) {
						if(ships[i] != null) {
							//設定ship sit
							ships[i].setEntitySit();
						}
					}
					break;
				}//end switch
			}//end not in team
		}
	}
	
	/** set ship select (focus) with team list, only called at server side
	 */
	public static void applyTeamSelect(EntityPlayer player, int meta, int entityid, boolean select) {
		ExtendPlayerProps props = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(props != null) {
			int i = props.checkInTeamList(entityid);
			
			//check entity is in team
			if(i >= 0) {
				switch(meta) {
				default:	//single mode (僅一隻可以focus)
					/**single mode不能取消focus, 一定有一隻會是focus狀態*/
					props.clearTeamSelected();
					props.setTeamSelected(i, true);
					break;
				case 1:		//group mode (不限focus數量)
					props.setTeamSelected(i, !props.getTeamSelected(i));
					break;
				case 2:		//formation mode (僅一隻可以focus, 會設為flagship)
					/**formation mode不能取消focus, 一定有一隻會是focus (flagship)狀態*/
					props.clearTeamSelected();
					props.setTeamSelected(i, true);
					break;
				}
			}
			
			//sync team list to client
			CommonProxy.channelG.sendTo(new S2CGUIPackets(props), (EntityPlayerMP) player);
		}
		
		
	}
	
	
}
