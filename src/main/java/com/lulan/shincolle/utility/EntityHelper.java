package com.lulan.shincolle.utility;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.EntityAirplane;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityHelper {

	private static Random rand = new Random();
	
	public EntityHelper() {}
	
	//check is same owner for ship
	public static boolean checkSameOwner(EntityLivingBase owner, EntityLivingBase target) {
		EntityLivingBase getOwner = null;
		
		if(owner != null && target != null) {
			if(target instanceof EntityPlayer) {
				getOwner = target;
			}
			else if(target instanceof BasicEntityShip) {
				getOwner = ((BasicEntityShip)target).getOwner();
			}
			else if(target instanceof BasicEntityAirplane) {
				//先取得airplane的owner(為一種Ship), 再取得該ship的owner(為一種EntityPlayer)
				getOwner = ((BasicEntityAirplane)target).getOwner();
				if(getOwner != null) {
					getOwner = ((BasicEntityShip)getOwner).getOwner();
				}
				else {
					return false;
				}
			}
			
			if(getOwner != null && getOwner.getUniqueID().equals(owner.getUniqueID())) {
				return true;
			}
		}
		return false;
	}
	
	//get entity by ID
	public static Entity getEntityByID(int entityID, World world) {
		for(Object obj: world.getLoadedEntityList()) {
			if(entityID != -1 && ((Entity)obj).getEntityId() == entityID) {
//				LogHelper.info("DEBUG : found entity by ID, is client? "+entityID+" "+world.isRemote);
				return ((Entity)obj);
			}
		}
		return null;
	}
	
	//get player on the server by UUID
	public static EntityPlayerMP getOnlinePlayer(UUID id) {
		//get online id list (server side only)
		List onlineList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		Iterator iter = onlineList.iterator();
		while(iter.hasNext()) {
			EntityPlayerMP player = (EntityPlayerMP)iter.next();
		    if(player.getUniqueID().equals(id)) {
//		    	LogHelper.info("DEBUG : found player by UUID "+player.getDisplayName());
		    	return player;
		    }
		}
		return null;
	}
	
	//process GUI click
	public static void setEntityByGUI(BasicEntityShip entity, int button, int value) {
		if(entity != null) {
			switch(button) {
			case ID.B_ShipInv_Melee:
				entity.setEntityFlagI(ID.F_UseMelee, value);
				break;
			case ID.B_ShipInv_AmmoLight:
				entity.setEntityFlagI(ID.F_UseAmmoLight, value);
				break;
			case ID.B_ShipInv_AmmoHeavy:
				entity.setEntityFlagI(ID.F_UseAmmoHeavy, value);
				break;
			case ID.B_ShipInv_AirLight:
				entity.setEntityFlagI(ID.F_UseAirLight, value);
				break;
			case ID.B_ShipInv_AirHeavy:
				entity.setEntityFlagI(ID.F_UseAirHeavy, value);
				break;
			}
		}
		else {
			LogHelper.info("DEBUG : set entity by GUI fail, entity null");
		}
	}
	
	//process Shipyard GUI click
	public static void setTileEntityByGUI(TileEntity tile, int button, int value, int value2) {
		if(tile != null) {
			if(tile instanceof TileEntitySmallShipyard) {
//				LogHelper.info("DEBUG : set tile entity value "+button+" "+value);
				((TileEntitySmallShipyard)tile).buildType = value;
				return;
			}
			if(tile instanceof TileMultiGrudgeHeavy) {
//				LogHelper.info("DEBUG : set tile entity value "+button+" "+value+" "+value2);
				
				switch(button) {
				case ID.B_Shipyard_Type:		//build type
					((TileMultiGrudgeHeavy)tile).setBuildType(value);
					break;
				case ID.B_Shipyard_InvMode:		//select inventory mode
					((TileMultiGrudgeHeavy)tile).setInvMode(value);
					break;
				case ID.B_Shipyard_SelectMat:	//select material
					((TileMultiGrudgeHeavy)tile).setSelectMat(value);
					break;
				case ID.B_Shipyard_INCDEC:			//material inc,dec
					setLargeShipyardBuildMats((TileMultiGrudgeHeavy)tile, button, value, value2);
					break;
				}	
			}			
		}
		else {
			LogHelper.info("DEBUG : set tile entity by GUI fail, entity null");
		}	
	}

	//設定large shipyard的matBuild[], 必須判定matStock夠不夠轉成matBuild
	private static void setLargeShipyardBuildMats(TileMultiGrudgeHeavy tile, int button, int value, int value2) {
		int num = 0;
		int type = 0;
		
		//value2轉換為數量
		switch(value2) {
		case 0:
			num = 1000;
			break;
		case 1:
			num = 100;
			break;
		case 2:
			num = 10;
			break;
		case 3:
			num = 1;
			break;
		case 4:
			num = -1000;
			break;
		case 5:
			num = -100;
			break;
		case 6:
			num = -10;
			break;
		case 7:
			num = -1;
			break;		
		}
		
		//判定數量是否足夠轉換
		if(num > 0) {	//matStock -> matBuild
			//stock數量要夠轉, 且build數量必須在100~1000之間
			if(tile.getMatStock(value) - num >= 0 &&
			   tile.getMatBuild(value) + num < 1001) {
				//將stock轉移到build
				tile.addMatStock(value, -num);
				tile.addMatBuild(value, num);
			}
		}
		else {			//matBuild -> matStock
			//build數量要足夠轉回stock, 這邊不限制stock上限 (可破萬), 這邊num為"NEGATIVE"
			if(tile.getMatBuild(value) + num >= 0) {
				//將build轉移到stock
				tile.addMatStock(value, -num);
				tile.addMatBuild(value, num);
			}
		}	
	}
	
	//find random position with block check
	//mode 0: find y = Y+1 ~ Y+3 and XZ at side of target
	//mode 1: find y = Y-2 ~ Y+2 (NYI)
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
	
	//計算指定速度下頭部朝向
	public static float[] getLookDegree(double motX, double motY, double motZ) {
		//計算模型要轉的角度 (RAD, not DEG)
        double f1 = MathHelper.sqrt_double(motX*motX + motZ*motZ);
        float[] degree = new float[2];
        degree[1] = (float)(Math.atan2(motY, f1)) * 57.2958F;
        degree[0] = (float)(Math.atan2(motX, motZ)) * 57.2958F;
        degree[0] = -degree[0];
//        LogHelper.info("DEBUG : pitch "+degree[1]);
        
        return degree;
	}
	
}
