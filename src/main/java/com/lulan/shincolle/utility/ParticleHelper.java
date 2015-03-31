package com.lulan.shincolle.utility;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.particle.EntityFXLaser;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.client.particle.EntityFXTexts;
import com.lulan.shincolle.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**粒子特效處理class
 * 包含呼叫特效, 旋轉特效位置(NxNxN旋轉), 
 */
public class ParticleHelper {
	
	private static World world = ClientProxy.getClientWorld();
	private static Random rand = new Random();
	
	/**ROTATE PARTICLE POSITION (NxNxN)
	 * in:原始座標, 邊長, 以及要轉的面向 	out:轉完的新位置
	 * 現階段沒有做上下翻轉, 所以y值不會變動
	 * f = face = 0,4:north  1,5:east  2,6:south  3,7:west
	 */
	public static double[] rotateForBlock(double x, double y, double z, int f, int len) {
		double[] newParm = new double[3];
		newParm[1] = y;
		
		//依照面向, 旋轉原始位置
		switch(f) {
		case 1:		//turn east
		case 5:
			newParm[0] = (double)len - z;
			newParm[2] = x;
			break;
		case 2:		//turn south
		case 6:
			newParm[0] = (double)len - x;
			newParm[2] = (double)len - z;
			break;
		case 3:		//turn west
		case 7:
			newParm[0] = z;
			newParm[2] = (double)len - x;
			break;
		default:	//default north, no change
			newParm[0] = x;
			newParm[2] = z;
			break;			
		}
			
		return newParm;
	}
	
	/**ROTATE PARTICLE FOR ENTITY
	 * entity專用的特效位置旋轉方法, 因為entity才有yaw跟pitch參數, 可做到較細緻轉換
	 * 由於模型是以Z軸正向轉X軸負向為正角度, 且Z軸正向為0度, 因此座標參數為(z,y,x)
	 */
	public static float[] rotateForEntity(float z, float y, float x, float yaw, float pitch, float scale) {
		float cosYaw = MathHelper.cos(yaw);
		float sinYaw = MathHelper.sin(yaw);
		float cosPitch = MathHelper.cos(pitch);
		float sinPitch = MathHelper.sin(pitch);
		float[] newPos = new float[] {z, y, x};
//		LogHelper.info("DEBUG : cos Yaw = "+cosYaw);
//		LogHelper.info("DEBUG : sin Yaw = "+sinYaw);
		
		//計算水平旋轉: X+Z
		newPos[0] = z * cosYaw + x * sinYaw;
		newPos[2] = x * cosYaw - z * sinYaw;
		//計算垂直旋轉
//		newPos[1] = y * cosPitch * cosPitch - newPos[0] * sinPitch + newPos[2] * cosPitch * sinPitch;
		
		newPos[0] *= scale;
//		newPos[1] *= scale;
		newPos[2] *= scale;
		
		return newPos;
	}
	
	/**ROTATE PARTICLE FOR ENTITY (Z AXIS)
	 * 針對entity的Z軸做旋轉
	 */
	public static float[] rotateForEntityZaxis(float x, float y, float zdeg, float scale) {
		float cosZdeg = MathHelper.cos(zdeg);
		float sinZdeg = MathHelper.sin(zdeg);
		float[] newPos = new float[] {0F, 0F};
//		LogHelper.info("DEBUG : cos Zdeg = "+cosZdeg);
//		LogHelper.info("DEBUG : sin Zdeg = "+sinZdeg);
		
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
	 * @parm world, posX, posY, posZ, particleID
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAt(double posX, double posY, double posZ, double lookX, double lookY, double lookZ, byte type) {
		//get target position
		double ran1 = 0D;
		double ran2 = 0D;
		double ran3 = 0D;
		
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
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat() - 0.5F;
				ran3 = rand.nextFloat() - 0.5F;
				world.spawnParticle("smoke", posX+ran1, posY+ran2, posZ+ran3, 0D, 0D, 0D);
			}	
			break;
		case 5:		//flame+smoke: for moderate damage
			for(int i=0; i<3; i++) {
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat() - 0.5F;
				ran3 = rand.nextFloat() - 0.5F;
				world.spawnParticle("smoke", posX+ran1, posY+ran2, posZ+ran3, 0D, 0D, 0D);
				world.spawnParticle("flame", posX+ran3, posY+ran2, posZ+ran1, 0D, 0D, 0D);
			}
			break;
		case 6: 	//largesmoke
			for(int i=0; i<20; i++) {
				ran1 = rand.nextFloat() - 0.5F;
				world.spawnParticle("largesmoke", posX+lookX-0.5D+0.05D*i, posY+0.8D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.2D, 0.05D, lookZ*0.2D);
			}	
			break;
		case 7: 	//flame+large smoke: for heavy damage
			for(int i=0; i<4; i++) {
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat() - 0.5F;
				ran3 = rand.nextFloat() - 0.5F;
				world.spawnParticle("largesmoke", posX+ran1, posY+ran2, posZ+ran3, 0D, 0D, 0D);
				world.spawnParticle("flame", posX+ran3, posY+ran2, posZ+ran1, 0D, 0D, 0D);
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
            		posX, posY, posZ, lookX, lookY, lookZ, 1F, 1F, 1F, 1F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray);
			break;
		case 16:	//cyan spray
			EntityFXSpray particleSpray2 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 0.5F, 1F, 1F, 1F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray2);
			break;
		case 17:	//green spray
			EntityFXSpray particleSpray3 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 0.2F, 1.0F, 0.6F, 0.7F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray3);
			break;
		case 18:	//red spray
			EntityFXSpray particleSpray4 = new EntityFXSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 1.0F, 0.0F, 0.0F, 0.8F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray4);
			break;
		default:
			break;		
		}
	}

	
}
