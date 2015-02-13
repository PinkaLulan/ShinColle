package com.lulan.shincolle.utility;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.CreatePacketS2C;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class EntityHelper {

	public EntityHelper() {}
	
	//get entity by ID
	public static Entity getEntityByID(int entityID, World world) {
		for(Object obj: world.getLoadedEntityList()) {
			if(entityID != -1 && ((Entity)obj).getEntityId() == entityID) {
				LogHelper.info("DEBUG : found entity by ID/client? "+entityID+" "+world.isRemote);
				return ((Entity)obj);
			}
		}
		return null;
	}
	
	//process GUI click
	public static void setEntityByGUI(BasicEntityShip entity, int button, int value) {
		if(entity != null) {
			switch(button) {
			case AttrID.B_ShipInv_AmmoLight:
				LogHelper.info("DEBUG : set entity value "+button+" "+value);
				entity.setEntityFlagI(AttrID.F_UseAmmoLight, value);
				break;
			case AttrID.B_ShipInv_AmmoHeavy:
				LogHelper.info("DEBUG : set entity value "+button+" "+value);
				entity.setEntityFlagI(AttrID.F_UseAmmoHeavy, value);
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
				LogHelper.info("DEBUG : set tile entity value "+button+" "+value);
				((TileEntitySmallShipyard)tile).buildType = value;
				return;
			}
			if(tile instanceof TileMultiGrudgeHeavy) {
				LogHelper.info("DEBUG : set tile entity value "+button+" "+value+" "+value2);
				
				switch(button) {
				case AttrID.B_Shipyard_Type:		//build type
					((TileMultiGrudgeHeavy)tile).setBuildType(value);
					break;
				case AttrID.B_Shipyard_InvMode:		//select inventory mode
					((TileMultiGrudgeHeavy)tile).setInvMode(value);
					break;
				case AttrID.B_Shipyard_SelectMat:	//select material
					((TileMultiGrudgeHeavy)tile).setSelectMat(value);
					break;
				case AttrID.B_Shipyard_INCDEC:			//material inc,dec
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
	
}
