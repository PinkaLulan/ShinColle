package com.lulan.shincolle.utility;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.client.particle.EntityFX91Type;
import com.lulan.shincolle.client.particle.EntityFXChi;
import com.lulan.shincolle.client.particle.EntityFXLaser;
import com.lulan.shincolle.client.particle.EntityFXLaserNoTexture;
import com.lulan.shincolle.client.particle.EntityFXLightning;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.client.particle.EntityFXStickyLightning;
import com.lulan.shincolle.client.particle.EntityFXTeam;
import com.lulan.shincolle.client.particle.EntityFXTexts;
import com.lulan.shincolle.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**粒子特效處理class
 * 包含呼叫特效, 旋轉特效位置(NxNxN旋轉), 
 */
public class ParticleHelper {
	
	private static Random rand = new Random();
	
	
	/**ROTATE PARTICLE POSITION (NxNxN)
	 * in:原始座標, 邊長, 以及要轉的面向 	out:轉完的新位置
	 * 現階段沒有做上下翻轉, 所以y值不會變動
	 * f = face = 0,4:north  1,5:east  2,6:south  3,7:west
	 */
	public static double[] rotateParticleByFace(double x, double y, double z, int f, int len) {
		double[] newParm = new double[3];
		newParm[1] = y;
		
		//依照面向, 旋轉原始位置
		switch(f) {
		case 1:		//turn east
		case 5:
			newParm[0] = len - z;
			newParm[2] = x;
			break;
		case 2:		//turn south
		case 6:
			newParm[0] = len - x;
			newParm[2] = len - z;
			break;
		case 3:		//turn west
		case 7:
			newParm[0] = z;
			newParm[2] = len - x;
			break;
		default:	//default north, no change
			newParm[0] = x;
			newParm[2] = z;
			break;			
		}
			
		return newParm;
	}
	
	/**ROTATE PARTICLE FOR ENTITY
	 * entity專用的特效位置旋轉方法, 使用yaw跟pitch為參數
	 * 模型是以Z軸正向轉X軸負向為正角度, 且Z軸正向為0度
	 */
	public static float[] rotateXYZByYawPitch(float x, float y, float z, float yaw, float pitch, float scale) {
		float cosYaw = MathHelper.cos(yaw);
		float sinYaw = MathHelper.sin(yaw);
		float cosPitch = MathHelper.cos(-pitch);
		float sinPitch = MathHelper.sin(-pitch);
		float[] newPos = new float[] {x, y, z};
		
		//計算pitch旋轉: z,y
		newPos[2] = z * cosPitch - y * sinPitch;
		newPos[1] = y * cosPitch + z * sinPitch;
		
		//計算yaw旋轉: x,z
		newPos[0] = x * cosYaw - newPos[2] * sinYaw;
		newPos[2] = newPos[2] * cosYaw + x * sinYaw;
		
		//計算scale
		newPos[0] *= scale;
		newPos[1] *= scale;
		newPos[2] *= scale;
		
		return newPos;
	}
	
	/**ROTATE BY AXIS
	 * 針對entity的某一軸做旋轉, 角度單位為RAD
	 * 注意entity前後為Z軸, 左右為X軸
	 */
	public static float[] rotateXZByAxis(float z, float x, float rad, float scale) {
		float cosD = MathHelper.cos(rad);
		float sinD = MathHelper.sin(rad);
		float[] newPos = new float[] {0F, 0F};
		
		//計算水平旋轉: X+Z
		newPos[0] = z * cosD + x * sinD;
		newPos[1] = x* cosD - z * sinD;
		
		newPos[0] *= scale;
		newPos[1] *= scale;
		
		return newPos;
	}
	
	/**ROTATE PARTICLE FOR ENTITY
	 * 針對entity的某一軸做旋轉, 以目標的位置為參數
	 */
	public static float[] rotateXZByLook(float x, float y, float lookX, float lookY, float scale) {		
		float[] degree = CalcHelper.getLookDegree(lookX, 0, lookY, false);
		float cosZdeg = MathHelper.cos(degree[0]);
		float sinZdeg = MathHelper.sin(degree[0]);
		float[] newPos = new float[] {0F, 0F};
		
		//計算水平旋轉: X+Z
		newPos[0] = x * cosZdeg + y * sinZdeg;
		newPos[1] = y* cosZdeg - x * sinZdeg;
		
		newPos[0] *= scale;
		newPos[1] *= scale;
		
		return newPos;
	}
	
	/**SPAWN ATTACK PARTICLE WITH CUSTOM POSITION
	 * @parm posX, posY, posZ, lookX, lookY, lookZ, type
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleCustomVector(Entity target, double posX, double posY, double posZ, double lookX, double lookY, double lookZ, byte type, boolean isShip) {
		if(target != null) {
			if(isShip) ((EntityLivingBase) target).attackTime = 50;
			
			//spawn particle
			spawnAttackParticleAt(posX, posY, posZ, lookX, lookY, lookZ, type);
		}
	}
	
	/**SPAWN ATTACK PARTICLE
	 * spawn particle and set attack time for model rendering
	 * @parm entity, type
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticle(Entity target, byte type, boolean setAtkTime) {
		if(setAtkTime && target != null) {
			((EntityLivingBase) target).attackTime = 50;
		}
		
		//0 = no particle
		if(type == 0) return;
		
		//target look
		double lookX = 0;
		double lookY = 0;
		double lookZ = 0;
		
		//get target position
		if(target != null) {
			if(type > 9) {
				lookY = target.height;
			}
			else if(target.getLookVec() != null) {
				lookX = target.getLookVec().xCoord;
				lookY = target.getLookVec().yCoord;
				lookZ = target.getLookVec().zCoord;
			}
			//spawn particle
			spawnAttackParticleAt(target.posX, target.posY, target.posZ, lookX, lookY, lookZ, type);
		}		
	}
	
	/**Spawn particle at xyz position
	 * @parm posX, posY, posZ, lookX, lookY, lookZ, particleID
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAt(double posX, double posY, double posZ, double lookX, double lookY, double lookZ, byte type) {
		//注意此world不能設為static變數, 否則client端重登後world依然是舊world而非重登後的新world
//		World world = Minecraft.getMinecraft().theWorld;
		World world = ClientProxy.getClientWorld();
		
		//get target position
		double ran1 = 0D;
		double ran2 = 0D;
		double ran3 = 0D;
		float[] newPos1;
		float[] newPos2;
		float degYaw = 0F;
//		LogHelper.info("DEBUG : spawn partile "+type);
		//spawn particle
		switch(type) {
		case 1:		//largeexplode
			world.spawnParticle("largeexplode", posX, posY+2, posZ, 0.0D, 0.0D, 0.0D);
			break;
		case 2:		//hugeexplosion
			world.spawnParticle("hugeexplosion", posX, posY+1, posZ, 0.0D, 0.0D, 0.0D);
			for(int i = 0; i < 20; ++i) {
				ran1 = rand.nextFloat() * 6F - 3F;
				ran2 = rand.nextFloat() * 6F - 3F;
				world.spawnParticle("lava", posX+ran1, posY+1, posZ+ran2, 0D, 0D, 0D);
			}
			break;
		case 3:		//hearts effect
			for(int i = 0; i < 7; ++i) {
	            double d0 = rand.nextGaussian() * 0.02D;
	            double d1 = rand.nextGaussian() * 0.02D;
	            double d2 = rand.nextGaussian() * 0.02D;
	            world.spawnParticle("heart",
	            		posX + rand.nextFloat() * 2D - 1D, 
	            		posY + 0.5D + rand.nextFloat() * 2D, 
	            		posZ + rand.nextFloat() * 2.0F - 1D, d0, d1, d2);
	        }
			break;
		case 4: 	//smoke: for minor damage
			for(int i=0; i<3; i++) {
				ran1 = rand.nextFloat() * lookX - lookX / 2D;
				ran2 = rand.nextFloat() * lookX - lookX / 2D;
				ran3 = rand.nextFloat() * lookX - lookX / 2D;
				world.spawnParticle("smoke", posX+ran1, posY+ran2, posZ+ran3, 0D, lookY, 0D);
			}
			break;
		case 5:		//flame+smoke: for moderate damage
			for(int i=0; i<3; i++) {
				ran1 = rand.nextFloat() * lookX - lookX / 2D;
				ran2 = rand.nextFloat() * lookX - lookX / 2D;
				ran3 = rand.nextFloat() * lookX - lookX / 2D;
				world.spawnParticle("smoke", posX+ran1, posY+ran2, posZ+ran3, 0D, lookY, 0D);
				world.spawnParticle("flame", posX+ran3, posY+ran2, posZ+ran1, 0D, lookY, 0D);
			}
			break;
		case 6: 	//largesmoke
			for(int i=0; i<20; i++) {
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i, posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2);
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i, posY+1.0D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3);
			}
			break;
		case 7: 	//flame+large smoke: for heavy damage
			for(int i=0; i<4; i++) {
				ran1 = rand.nextFloat() * lookX - lookX / 2D;
				ran2 = rand.nextFloat() * lookX - lookX / 2D;
				ran3 = rand.nextFloat() * lookX - lookX / 2D;
				world.spawnParticle("largesmoke", posX+ran1, posY+ran2, posZ+ran3, 0D, 0D, 0D);
				world.spawnParticle("flame", posX+ran3, posY+ran2, posZ+ran1, 0D, 0.05D, 0D);
			}
			break;
		case 8:	 	//flame
			world.spawnParticle("flame", posX, posY-0.1, posZ, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", posX, posY+0.1, posZ, 0.0D, 0.0D, 0.0D);
			break;
		case 9: 	//lava + largeexplode
			world.spawnParticle("largeexplode", posX, posY+1.5, posZ, 0.0D, 0.0D, 0.0D);
			for(int i=0; i<12; i++) {
				ran1 = rand.nextFloat() * 3F - 1.5F;
				ran2 = rand.nextFloat() * 3F - 1.5F;
				world.spawnParticle("lava", posX+ran1, posY+1, posZ+ran2, 0D, 0D, 0D);
			}			
			break;
		case 10:	//miss
			EntityFXTexts particleMiss = new EntityFXTexts(world, 
  		          posX, posY + lookY, posZ, 1F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleMiss);
			break;
		case 11:	//cri
			EntityFXTexts particleCri = new EntityFXTexts(world, 
  		          posX, posY + lookY, posZ, 1F, 1);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleCri);
			break;
		case 12:	//double hit
			EntityFXTexts particleDHit = new EntityFXTexts(world, 
	  		          posX, posY + lookY, posZ, 1F, 2);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleDHit);
			break;
		case 13:	//triple hit
			EntityFXTexts particleTHit = new EntityFXTexts(world, 
	  		          posX, posY + lookY, posZ, 1F, 3);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleTHit);
			break;
		case 14:	//laser
			EntityFXLaser particleLaser = new EntityFXLaser(world, 
			          posX, posY, posZ, lookX, lookY, lookZ, 1F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser);
			break;
		case 15:	//white spray
			EntityFXSpray particleSpray = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 1);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray);
			break;
		case 16:	//cyan spray
			EntityFXSpray particleSpray2 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 2);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray2);
			break;
		case 17:	//green spray
			EntityFXSpray particleSpray3 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray3);
			break;
		case 18:	//red spray
			EntityFXSpray particleSpray4 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 4);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray4);
			break;
		case 19: 	//double largesmoke for 14~20 inch cannon
			//計算煙霧位置
			degYaw = CalcHelper.getLookDegree(lookX, 0D, lookZ, false)[0];
			newPos1 = ParticleHelper.rotateXZByAxis(0F, (float)lookY, degYaw, 1F);
			newPos2 = ParticleHelper.rotateXZByAxis(0F, (float)-lookY, degYaw, 1F);
			
			for(int i=0; i<12; i++) {
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i+newPos1[1], posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos1[0], lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2);
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i+newPos2[1], posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos2[0], lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3);
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i+newPos1[1], posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos1[0], lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3);
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i+newPos2[1], posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos2[0], lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2);
			}
			break;
		case 20: 	//smoke: for nagato equip
			for(int i=0; i<3; i++) {
				world.spawnParticle("smoke", posX, posY+i*0.1D, posZ, lookX, lookY, lookZ);
			}
			break;
		case 21:	//Type 91 AP Fist: phase 4 hit particle
			//draw speed blur
			EntityFXLaser particleLaser2 = new EntityFXLaser(world, 
			          posX, posY, posZ, lookX, lookY, lookZ, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser2);
			EntityFXLaser particleLaser3 = new EntityFXLaser(world, 
			          posX, posY+0.4D, posZ, lookX, lookY+0.4D, lookZ, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser3);
			EntityFXLaser particleLaser4 = new EntityFXLaser(world, 
			          posX, posY+0.8D, posZ, lookX, lookY+0.8D, lookZ, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser4);
			
			//draw hit particle
			for(int i = 0; i < 20; ++i) {
				newPos1 = rotateXZByAxis(1, 0, 6.28F / 20F * i, 1);
				//motionY傳入4, 表示為特殊設定
				EntityFXSpray particleSpray5 = new EntityFXSpray(world, 
						lookX, lookY+0.3D, lookZ, newPos1[0]*0.4D, 4D, newPos1[1]*0.4D, 0);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray5);
			}
			
			//draw hit text
			EntityFX91Type particleSpray6 = new EntityFX91Type(world, 
					lookX, lookY+2.5D, lookZ, 0.6F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray6);
			break;
		case 22:	//Type 91 AP Fist: phase 1,3 particle
			for(int i = 0; i < 20; ++i) {
				newPos1 = rotateXZByAxis((float)lookX, 0, 6.28F / 20F * i, 1);
				//motionY傳入4, 表示為特殊設定
				EntityFXSpray particleSpray7 = new EntityFXSpray(world, 
						posX+newPos1[0]*1.8D, posY+1.2D+lookY, posZ+newPos1[1]*1.8D, -newPos1[0]*0.06D, 0D, -newPos1[1]*0.06D, 5);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray7);
			}
			break;
		case 23:	//Type 91 AP Fist: phase 2 particle
			for(int i = 0; i < 20; ++i) {
				newPos1 = rotateXZByAxis((float)lookX, 0, 6.28F / 20F * i, 1);
				//motionY傳入4, 表示為特殊設定
				EntityFXSpray particleSpray8 = new EntityFXSpray(world, 
						posX, posY+0.3D+lookY, posZ, newPos1[0]*0.15D, 0D, newPos1[1]*0.15D, 6);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray8);
			}
			break;
		case 24: 	//smoke: for nagato BOSS equip
			for(int i=0; i<3; i++) {
				world.spawnParticle("largesmoke", posX, posY+i*0.3D, posZ, lookX, lookY, lookZ);
			}
			break;
		case 25:	//arrow particle: for move or attack target mark
			EntityFXTeam particleTeam = new EntityFXTeam(world, (float)lookX, (int)lookY, posX, posY, posZ);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleTeam);
			break;
		case 26:	//white spray
			EntityFXSpray particleSpray7 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 7);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray7);
			break;
		case 27:	//yellow spray
			EntityFXSpray particleSpray8 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 8);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray8);
			break;
		case 28:	//drip water
			ran1 = rand.nextFloat() * 0.7D - 0.35D;
			ran2 = rand.nextFloat() * 0.7D - 0.35D;
			world.spawnParticle("dripWater", posX+ran1, posY, posZ+ran2, lookX, lookY, lookZ);
			break;
		case 29:	//orange spray
			EntityFXSpray particleSpray9 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 9);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray9);
			break;
		case 30:	//snow hit
			for(int i=0; i<12; i++) {
				ran1 = rand.nextFloat() * 2F - 1F;
				ran2 = rand.nextFloat() * 2F - 1F;
				ran3 = rand.nextFloat() * 2F - 1F;
				world.spawnParticle("snowballpoof", posX+ran1, posY+0.8D+ran2, posZ+ran3, lookX*0.2D, 0.5D, lookZ*0.2D);
			}
			break;
		case 31: 	//throw snow smoke
			for(int i=0; i<20; i++) {
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle("snowshovel", posX+lookX-0.5D+0.05D*i, posY+0.7D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2);
				world.spawnParticle("snowshovel", posX+lookX-0.5D+0.05D*i, posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3);
			}
			break;
		case 32:	//transparent cyan spray
			EntityFXSpray particleSpray10 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 10);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray10);
			break;
		case 33:	//transparent red spray
			EntityFXSpray particleSpray11 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 11);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray11);
			break;
		case 34:	//dodge
			EntityFXTexts particleTDodge = new EntityFXTexts(world, 
	  		          posX, posY + lookY, posZ, 1F, 4);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleTDodge);
			break;
		default:
			break;		
		}
	}
	
	/**Spawn particle at entity position
	 * @parm host, par1, par2, par3, particleID
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAtEntity(Entity ent, double par1, double par2, double par3, byte type) {
		World world = Minecraft.getMinecraft().theWorld;
		
		//get target position
		double ran1 = 0D;
		double ran2 = 0D;
		double ran3 = 0D;
		float[] newPos1;
		float[] newPos2;
		float degYaw = 0F;
		
		//spawn particle
		switch(type) {
		case 1:		//氣彈特效 par1:scale par2:type
			EntityFXChi fxChi1 = new EntityFXChi(world, ent, (float)par1, (int)par2);
        	Minecraft.getMinecraft().effectRenderer.addEffect(fxChi1);
			break;
		case 2:		//隊伍圈選特效 par1:scale par2:type
			EntityFXTeam fxTeam = new EntityFXTeam(world, ent, (float)par1, (int)par2);
			Minecraft.getMinecraft().effectRenderer.addEffect(fxTeam);
			break;
		case 3:
			EntityFXLightning fxLightning = new EntityFXLightning(world, ent, (float)par1, (int)par2);
			Minecraft.getMinecraft().effectRenderer.addEffect(fxLightning);
			break;
		case 4:	//sticky lightning
			EntityFXStickyLightning light1 = new EntityFXStickyLightning(world, ent, (float)par1, (int)par2);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light1);
			break;
		default:
			break;
		}
	}
	
	/**Spawn particle at entity position
	 * @parm host, par1, par2, par3, particleID
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAtEntity(Entity host, Entity target, double par1, double par2, double par3, byte type, boolean setAtkTime) {
		World world = Minecraft.getMinecraft().theWorld;
		
		//null check
		if(host == null || target == null) {
			return;
		}
		//set attack time, EntityLivingBase only
		else {
			if(setAtkTime) {
				((EntityLivingBase) host).attackTime = 50;
			}
		}
		
		//get target position
		double ran1 = 0D;
		double ran2 = 0D;
		double ran3 = 0D;
		float[] newPos1;
		float[] newPos2;
		float degYaw = 0F;
		
		//spawn particle
		switch(type) {
		case 0:		//雙光束砲
			//host check
			EntityLivingBase host2 = null;
			if(host instanceof EntityLivingBase) {
				host2 = (EntityLivingBase) host;
			}
			else {
				return;
			}
			
			EntityFXLaserNoTexture laser1 = new EntityFXLaserNoTexture(world, host2, target, 0.78F, par1, 0F, 0.05F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser1);
			
			EntityFXLaserNoTexture laser2 = new EntityFXLaserNoTexture(world, host2, target, -0.78F, par1, 0F, 0.05F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser2);

			break;
		default:
			break;
		}
	}

	
}
