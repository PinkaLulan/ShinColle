package com.lulan.shincolle.utility;

import java.util.Random;

import com.lulan.shincolle.client.particle.Particle91Type;
import com.lulan.shincolle.client.particle.ParticleChi;
import com.lulan.shincolle.client.particle.ParticleCraning;
import com.lulan.shincolle.client.particle.ParticleCube;
import com.lulan.shincolle.client.particle.ParticleDebugPlane;
import com.lulan.shincolle.client.particle.ParticleEmotion;
import com.lulan.shincolle.client.particle.ParticleGradient;
import com.lulan.shincolle.client.particle.ParticleLaser;
import com.lulan.shincolle.client.particle.ParticleLaserNoTexture;
import com.lulan.shincolle.client.particle.ParticleLightning;
import com.lulan.shincolle.client.particle.ParticleLine;
import com.lulan.shincolle.client.particle.ParticleSmoke;
import com.lulan.shincolle.client.particle.ParticleSparkle;
import com.lulan.shincolle.client.particle.ParticleSphereLight;
import com.lulan.shincolle.client.particle.ParticleSpray;
import com.lulan.shincolle.client.particle.ParticleStickyLightning;
import com.lulan.shincolle.client.particle.ParticleSweep;
import com.lulan.shincolle.client.particle.ParticleTeam;
import com.lulan.shincolle.client.particle.ParticleTexts;
import com.lulan.shincolle.client.particle.ParticleTextsCustom;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Values;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**粒子特效處理class
 * 包含呼叫特效, 旋轉特效位置(NxNxN旋轉), 
 */
public class ParticleHelper
{
	
	private static Random rand = new Random();
	
	
	/**SPAWN ATTACK PARTICLE WITH CUSTOM POSITION
	 * @parm posX, posY, posZ, lookX, lookY, lookZ, type
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleCustomVector(Entity target, double posX, double posY, double posZ, double lookX, double lookY, double lookZ, byte type, boolean isShip)
	{
		if (target != null)
		{
			if (isShip && target instanceof IShipEmotion)
			{
				((IShipEmotion) target).setAttackTick(50);
			}
			
			//spawn particle
			spawnAttackParticleAt(posX, posY, posZ, lookX, lookY, lookZ, type);
		}
	}
	
	/**SPAWN ATTACK PARTICLE
	 * spawn particle and set attack time for model rendering
	 * @parm entity, type
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticle(Entity target, byte type, boolean setAtkTime)
	{
		if (setAtkTime && target instanceof IShipEmotion)
		{
			((IShipEmotion) target).setAttackTick(50);
		}
		
		//0 = no particle
		if (type == 0) return;
		
		//target look
		double lookX = 0;
		double lookY = 0;
		double lookZ = 0;
		
		//get target position
		if (target != null)
		{
			if (type > 9)
			{
				lookY = target.height * 1.3D;
			}
			else if (target.getLookVec() != null)
			{
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
	public static void spawnAttackParticleAt(double posX, double posY, double posZ, double lookX, double lookY, double lookZ, byte type)
	{
		World world = ClientProxy.getClientWorld();
		
		//get target position
		double ran1 = 0D;
		double ran2 = 0D;
		double ran3 = 0D;
		float[] newPos1;
		float[] newPos2;
		float degYaw = 0F;
		
		//spawn particle
		//parameters除了ITEM_CRACK BLOCK_CRACK BLOCK_DUST以外都是傳入new int[0]即可
		//spawnParticle(EnumParticleTypes particleType, boolean ignoreRange,
		//              double xCoord, double yCoord, double zCoord,
		//              double xSpeed, double ySpeed, double zSpeed,
		//              int... parameters)
		switch (type)
		{
		case 1:		//largeexplode
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX, posY+2, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		break;
		case 2:		//hugeexplosion
			world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY+1, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			for (int i = 0; i < 24; ++i)
			{
				ran1 = rand.nextFloat() * 6F - 3F;
				ran2 = rand.nextFloat() * 6F - 3F;
				world.spawnParticle(EnumParticleTypes.LAVA, posX+ran1, posY+1, posZ+ran2, 0D, 0D, 0D, new int[0]);
			}
		break;
		case 3:		//hearts effect
			for (int i = 0; i < 7; ++i)
			{
	            double d0 = rand.nextGaussian() * 0.02D;
	            double d1 = rand.nextGaussian() * 0.02D;
	            double d2 = rand.nextGaussian() * 0.02D;
	            world.spawnParticle(EnumParticleTypes.HEART, posX + rand.nextFloat() * 2D - 1D, posY + 0.5D + rand.nextFloat() * 2D, posZ + rand.nextFloat() * 2.0F - 1D, d0, d1, d2, new int[0]);
	        }
		break;
		case 4: 	//smoke: for minor damage
			for (int i = 0; i < 3; i++)
			{
				ran1 = rand.nextFloat() * lookX - lookX / 2D;
				ran2 = rand.nextFloat() * lookX - lookX / 2D;
				ran3 = rand.nextFloat() * lookX - lookX / 2D;
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX+ran1, posY+ran2, posZ+ran3, 0D, lookY, 0D, new int[0]);
			}
		break;
		case 5:		//flame+smoke: for moderate damage
			for (int i = 0; i < 3; i++)
			{
				ran1 = rand.nextFloat() * lookX - lookX / 2D;
				ran2 = rand.nextFloat() * lookX - lookX / 2D;
				ran3 = rand.nextFloat() * lookX - lookX / 2D;
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX+ran1, posY+ran2, posZ+ran3, 0D, lookY, 0D, new int[0]);
				world.spawnParticle(EnumParticleTypes.FLAME, posX+ran3, posY+ran2, posZ+ran1, 0D, lookY, 0D, new int[0]);
			}
		break;
		case 6: 	//largesmoke
			for (int i = 0; i < 24; i++)
			{
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i, posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i, posY+1.0D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
			}
		break;
		case 7: 	//flame+large smoke: for heavy damage
			for (int i = 0; i < 4; i++)
			{
				ran1 = rand.nextFloat() * lookX - lookX / 2D;
				ran2 = rand.nextFloat() * lookX - lookX / 2D;
				ran3 = rand.nextFloat() * lookX - lookX / 2D;
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+ran1, posY+ran2, posZ+ran3, 0D, 0D, 0D, new int[0]);
				world.spawnParticle(EnumParticleTypes.FLAME, posX+ran3, posY+ran2, posZ+ran1, 0D, 0.05D, 0D, new int[0]);
			}
		break;
		case 8:	 	//flame
			world.spawnParticle(EnumParticleTypes.FLAME, posX, posY-0.1, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			world.spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			world.spawnParticle(EnumParticleTypes.FLAME, posX, posY+0.1, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		break;
		case 9: 	//lava + largeexplode
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX, posY+1.5, posZ, 0.0D, 0.0D, 0.0D, new int[0]);
			for (int i = 0; i < 15; i++)
			{
				ran1 = rand.nextFloat() * 3F - 1.5F;
				ran2 = rand.nextFloat() * 3F - 1.5F;
				world.spawnParticle(EnumParticleTypes.LAVA, posX+ran1, posY+1, posZ+ran2, 0D, 0D, 0D, new int[0]);
			}			
		break;
		case 10:	//miss
			ParticleTexts particleMiss = new ParticleTexts(world, 
  		          posX, posY + lookY, posZ, 1F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleMiss);
		break;
		case 11:	//cri
			ParticleTexts particleCri = new ParticleTexts(world, 
  		          posX, posY + lookY, posZ, 1F, 1);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleCri);
		break;
		case 12:	//double hit
			ParticleTexts particleDHit = new ParticleTexts(world, 
	  		          posX, posY + lookY, posZ, 1F, 2);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleDHit);
		break;
		case 13:	//triple hit
			ParticleTexts particleTHit = new ParticleTexts(world, 
	  		          posX, posY + lookY, posZ, 1F, 3);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleTHit);
		break;
		case 14:	//laser
			ParticleLaser particleLaser = new ParticleLaser(world, 
			          posX, posY, posZ, lookX, lookY, lookZ, 1F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser);
		break;
		case 15:	//white spray
			ParticleSpray particleSpray = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 1);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray);
		break;
		case 16:	//cyan spray
			ParticleSpray particleSpray2 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 2);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray2);
		break;
		case 17:	//green spray
			ParticleSpray particleSpray3 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray3);
		break;
		case 18:	//red spray
			ParticleSpray particleSpray4 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 4);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray4);
		break;
		case 19: 	//double largesmoke for 14~20 inch cannon
			//計算煙霧位置
			degYaw = CalcHelper.getLookDegree(lookX, 0D, lookZ, false)[0];
			newPos1 = CalcHelper.rotateXZByAxis(0F, (float)lookY, degYaw, 1F);
			newPos2 = CalcHelper.rotateXZByAxis(0F, (float)-lookY, degYaw, 1F);
			
			for (int i = 0; i < 15; i++)
			{
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos1[1], posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos1[0], lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos2[1], posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos2[0], lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos1[1], posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos1[0], lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos2[1], posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos2[0], lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
			}
		break;
		case 20: 	//smoke: for nagato equip
			for (int i = 0; i < 3; i++)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY+i*0.1D, posZ, lookX, lookY, lookZ, new int[0]);
			}
		break;
		case 21:	//Type 91 AP Fist: phase 4 hit particle
			//draw speed blur
			ParticleLaser particleLaser2 = new ParticleLaser(world, 
			          posX, posY, posZ, lookX, lookY, lookZ, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser2);
			ParticleLaser particleLaser3 = new ParticleLaser(world, 
			          posX, posY+0.4D, posZ, lookX, lookY+0.4D, lookZ, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser3);
			ParticleLaser particleLaser4 = new ParticleLaser(world, 
			          posX, posY+0.8D, posZ, lookX, lookY+0.8D, lookZ, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleLaser4);
			
			//draw hit particle
			for (int i = 0; i < 20; ++i)
			{
				newPos1 = CalcHelper.rotateXZByAxis(1, 0, 6.28F / 20F * i, 1);
				//motionY傳入4, 表示為特殊設定
				ParticleSpray particleSpray5 = new ParticleSpray(world, 
						lookX, lookY+0.3D, lookZ, newPos1[0]*0.35D, 0D, newPos1[1]*0.35D, 0);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray5);
			}
			
			//draw hit text
			Particle91Type particle91Type = new Particle91Type(world, 
					lookX, lookY+3D, lookZ, 0.6F);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particle91Type);
		break;
		case 22:	//Type 91 AP Fist: phase 1,3 particle
			for (int i = 0; i < 20; ++i)
			{
				newPos1 = CalcHelper.rotateXZByAxis((float)lookX, 0, 6.28F / 20F * i, 1);
				//motionY傳入4, 表示為特殊設定
				ParticleSpray particleSpray7 = new ParticleSpray(world, 
						posX+newPos1[0], posY+lookY, posZ+newPos1[1], -newPos1[0]*0.06D, 0D, -newPos1[1]*0.06D, 5);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray7);
			}
		break;
		case 23:	//Type 91 AP Fist: phase 2 particle
			for (int i = 0; i < 20; ++i)
			{
				newPos1 = CalcHelper.rotateXZByAxis((float)lookX, 0, 6.28F / 20F * i, 1);
				//motionY傳入4, 表示為特殊設定
				ParticleSpray particleSpray8 = new ParticleSpray(world, 
						posX, posY+lookY, posZ, newPos1[0], 0D, newPos1[1], 6);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray8);
			}
		break;
		case 24: 	//smoke: for nagato BOSS equip
			for (int i = 0; i < 3; i++)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX, posY+i*0.3D, posZ, lookX, lookY, lookZ, new int[0]);
			}
		break;
		case 25:	//arrow particle: for move or attack target mark
			ParticleTeam particleTeam = new ParticleTeam(world, (float)lookX, (int)lookY, posX, posY, posZ);
			Minecraft.getMinecraft().effectRenderer.addEffect(particleTeam);
		break;
		case 26:	//white spray
			ParticleSpray particleSpray7 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 7);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray7);
		break;
		case 27:	//yellow spray
			ParticleSpray particleSpray8 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 8);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray8);
		break;
		case 28:	//drip water
			ran1 = rand.nextFloat() * 0.7D - 0.35D;
			ran2 = rand.nextFloat() * 0.7D - 0.35D;
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, posX+ran1, posY, posZ+ran2, lookX, lookY, lookZ, new int[0]);
		break;
		case 29:	//orange spray
			ParticleSpray particleSpray9 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 9);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray9);
		break;
		case 30:	//snow hit
			for (int i = 0; i < 15; i++)
			{
				ran1 = rand.nextFloat() * 2F - 1F;
				ran2 = rand.nextFloat() * 2F - 1F;
				ran3 = rand.nextFloat() * 2F - 1F;
				world.spawnParticle(EnumParticleTypes.SNOWBALL, posX+ran1, posY+0.8D+ran2, posZ+ran3, lookX*0.2D, 0.5D, lookZ*0.2D, new int[0]);
			}
		break;
		case 31: 	//throw snow smoke
			for (int i = 0; i < 22; i++)
			{
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, posX+lookX-0.5D+0.05D*i, posY+0.7D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, posX+lookX-0.5D+0.05D*i, posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
			}
		break;
		case 32:	//transparent cyan spray
			ParticleSpray particleSpray10 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 10);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray10);
		break;
		case 33:	//transparent red spray
			ParticleSpray particleSpray11 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 11);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray11);
		break;
		case 34:	//dodge
			ParticleTexts particleTDodge = new ParticleTexts(world, 
	  		          posX, posY + lookY, posZ, 1F, 4);	    
			Minecraft.getMinecraft().effectRenderer.addEffect(particleTDodge);
		break;
		case 35: 	//triple largesmoke for boss ship
			//計算煙霧位置
			degYaw = CalcHelper.getLookDegree(lookX, 0D, lookZ, false)[0];
			newPos1 = CalcHelper.rotateXZByAxis(0F, (float)lookY, degYaw, 1F);
			newPos2 = CalcHelper.rotateXZByAxis(0F, (float)-lookY, degYaw, 1F);
			
			for (int i = 0; i < 15; i++)
			{
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.6D+0.1D*i+newPos1[1]+ran2, posY+ran1, posZ+lookZ-0.6D+0.1D*i+newPos1[0]+ran2, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.6D+0.1D*i+newPos2[1]+ran3, posY+ran1, posZ+lookZ-0.6D+0.1D*i+newPos2[0]+ran3, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.6D+0.1D*i+newPos1[1]+ran3, posY+0.3D+ran1, posZ+lookZ-0.6D+0.1D*i+newPos1[0]+ran3, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.6D+0.1D*i+newPos2[1]+ran2, posY+0.3D+ran1, posZ+lookZ-0.6D+0.1D*i+newPos2[0]+ran2, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.6D+0.1D*i+newPos1[1]+ran2, posY+0.6D+ran1, posZ+lookZ-0.6D+0.1D*i+newPos1[0]+ran2, lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.6D+0.1D*i+newPos2[1]+ran3, posY+0.6D+ran1, posZ+lookZ-0.6D+0.1D*i+newPos2[0]+ran3, lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
			}
		break;
		case 36:	//emotion
			ParticleEmotion partEmo = new ParticleEmotion(world, null,
					posX, posY, posZ, (float)lookX, (int)lookY, (int)lookZ);
			Minecraft.getMinecraft().effectRenderer.addEffect(partEmo);
		break;
		case 37:	//white spray
			ParticleSpray particleSpray12 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 12);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray12);
		break;
		case 38:	//next waypoint spray
			ParticleSpray particleSpray13 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 13);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray13);
		break;
		case 39:	//paired chest spray
			ParticleSpray particleSpray14 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 14);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray14);
		break;
		case 40:	//craning
			ParticleCraning particleCrane = new ParticleCraning(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 0);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleCrane);
		break;
		case 41:	//cyan spray 2
			ParticleSpray particleSpray15 = new ParticleSpray(world, 
            		posX, posY, posZ, lookX, lookY, lookZ, 15);
        	Minecraft.getMinecraft().effectRenderer.addEffect(particleSpray15);
		break;
		case 42: 	//double largesmoke for mounts with 14~20 inch cannon: lookX: entity.renderYawOffset, lookY: cannon spacing
			//計算煙霧位置
			newPos1 = CalcHelper.rotateXZByAxis(0F, (float)lookY, (float)(lookX * Values.N.DIV_PI_180), 1F);
			newPos2 = CalcHelper.rotateXZByAxis(0F, (float)-lookY, (float)(lookX * Values.N.DIV_PI_180), 1F);
			
			for (int i = 0; i < 15; i++)
			{
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos1[1], posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos1[0], lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos2[1], posY+0.6D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos2[0], lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos1[1], posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos1[0], lookX*0.3D*ran3, 0.05D*ran3, lookZ*0.3D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX+lookX-0.5D+0.05D*i+newPos2[1], posY+0.9D+ran1, posZ+lookZ-0.5D+0.05D*i+newPos2[0], lookX*0.3D*ran2, 0.05D*ran2, lookZ*0.3D*ran2, new int[0]);
			}
		break;
		case 43:	//custom size smoke: parms: lookY: motionY, lookX: scale
		{
			for (int i = 0; i < 3; i++)
			{
				ParticleSmoke smoke1 = new ParticleSmoke(world, posX, posY+i*0.1D, posZ, 0D, lookY, 0D, (float)lookX);
				Minecraft.getMinecraft().effectRenderer.addEffect(smoke1);
			}
		}
		break;
		case 44:	//high speed movement blur THICK YELLOW
		{
			//draw speed blur: H, Wfor, Wback, R, G, B, A, px, py, pz, mx, my, mz
			ParticleLine line1 = new ParticleLine(world, 0, new float[] {2.5F, 8F, 22F, 1F, 1F, 0.4F, 0.8F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			ParticleLine line2 = new ParticleLine(world, 0, new float[] {1F, 8F, 20F, 1F, 1F, 0.7F, 0.9F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			ParticleLine line3 = new ParticleLine(world, 0, new float[] {0.8F, 7F, 18F, 1F, 1F, 1F, 1F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			Minecraft.getMinecraft().effectRenderer.addEffect(line1);
			Minecraft.getMinecraft().effectRenderer.addEffect(line2);
			Minecraft.getMinecraft().effectRenderer.addEffect(line3);
		}
		break;
		case 45:	//high speed movement blur THIN + THICK Aura
		{
			//draw speed blur: H, Wfor, Wback, R, G, B, A, px, py, pz, mx, my, mz
			ParticleLine line1 = new ParticleLine(world, 0, new float[] {2.4F, 8F, 22F, 1F, 0F, 0F, 0.4F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			ParticleLine line2 = new ParticleLine(world, 0, new float[] {0.24F, 8F, 20F, 1F, 0F, 1F, 0.85F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			ParticleLine line3 = new ParticleLine(world, 0, new float[] {0.2F, 7F, 18F, 1F, 1F, 1F, 1F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			Minecraft.getMinecraft().effectRenderer.addEffect(line1);
			Minecraft.getMinecraft().effectRenderer.addEffect(line2);
			Minecraft.getMinecraft().effectRenderer.addEffect(line3);
		}
		break;
		case 46:	//high speed movement blur THICK PINK
		{
			//draw speed blur: H, Wfor, Wback, R, G, B, A, px, py, pz, mx, my, mz
			ParticleLine line1 = new ParticleLine(world, 0, new float[] {0.6F, 7F, 7F, 1F, 0.6F, 1F, 0.3F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			ParticleLine line2 = new ParticleLine(world, 0, new float[] {0.3F, 4F, 4F, 1F, 0.8F, 1F, 0.8F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			ParticleLine line3 = new ParticleLine(world, 0, new float[] {0.2F, 3F, 3F, 1F, 1F, 1F, 1F,
					(float)posX, (float)posY, (float)posZ, (float)lookX, (float)lookY, (float)lookZ});
			Minecraft.getMinecraft().effectRenderer.addEffect(line1);
			Minecraft.getMinecraft().effectRenderer.addEffect(line2);
			Minecraft.getMinecraft().effectRenderer.addEffect(line3);
		}
		break;
		case 47:	//lots white spray
		{
			int maxpar = (int)((3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 1.8F);
			for (int i = 0; i < maxpar; i++)
			{
				ParticleSpray spray = new ParticleSpray(world, posX, posY, posZ, lookX, lookY, lookZ, 16);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(spray);
			}
		}
		break;
		default:
		break;
		}
	}
	
	/**Spawn particle at entity position
	 * @parm host, par1, par2, par3, particleID
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAtEntity(Entity ent, double par1, double par2, double par3, byte type)
	{
		World world = Minecraft.getMinecraft().world;
		EntityLivingBase host = null;
		
		//get target position
		double ran1 = 0D;
		double ran2 = 0D;
		double ran3 = 0D;
		double ran4 = 0D;
		float[] newPos1;
		float[] newPos2;
		float[] newPos3;
		float degYaw = 0F;
		
		//spawn particle
		switch (type)
		{
		case 1:		//氣彈特效 par1:scale par2:type
		{
			ParticleChi fxChi1 = new ParticleChi(world, ent, (float)par1, (int)par2);
        	Minecraft.getMinecraft().effectRenderer.addEffect(fxChi1);
		}
		break;
		case 2:		//隊伍圈選特效 par1:scale par2:type
		{
			ParticleTeam fxTeam = new ParticleTeam(world, ent, (float)par1, (int)par2);
			Minecraft.getMinecraft().effectRenderer.addEffect(fxTeam);
		}
		break;
		case 3:
		{
			ParticleLightning fxLightning = new ParticleLightning(world, ent, (float)par1, (int)par2);
			Minecraft.getMinecraft().effectRenderer.addEffect(fxLightning);
		}
		break;
		case 4:		//sticky lightning
		{
			ParticleStickyLightning light1 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, (int)par3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light1);
        	ParticleStickyLightning light2 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, (int)par3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light2);
        	ParticleStickyLightning light3 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, (int)par3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light3);
        	ParticleStickyLightning light4 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, (int)par3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light4);
		}
		break;
		case 5: 	//custom largesmoke: par1:wide, par2:length, par3:height, EntityLivingBase ONLY
		{
			//計算煙霧位置
			degYaw = (((EntityLivingBase)ent).renderYawOffset % 360) * Values.N.DIV_PI_180;
			newPos1 = CalcHelper.rotateXZByAxis((float)par2, (float)par1, degYaw, 1F);
			newPos2 = CalcHelper.rotateXZByAxis((float)par2, (float)-par1, degYaw, 1F);
			newPos3 = CalcHelper.rotateXZByAxis(0.25F, 0F, degYaw, 1F);
			
			for (int i = 0; i < 24; i++)
			{
				ran1 = (rand.nextFloat() - 0.5F) * 2F;
				ran2 = (rand.nextFloat() - 0.5F) * 2F;
				ran3 = (rand.nextFloat() - 0.5F) * 2F;
				ran4 = rand.nextFloat() * 2F;
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX+newPos1[1]+ran1, ent.posY+par3+ran2, ent.posZ+newPos1[0]+ran3, newPos3[1]*ran4, 0.05D*ran4, newPos3[0]*ran4, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX+newPos2[1]+ran1, ent.posY+par3+ran3, ent.posZ+newPos2[0]+ran2, newPos3[1]*ran4, 0.05D*ran4, newPos3[0]*ran4, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX+newPos1[1]+ran2, ent.posY+par3+ran1, ent.posZ+newPos1[0]+ran3, newPos3[1]*ran4, 0.05D*ran4, newPos3[0]*ran4, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX+newPos2[1]+ran2, ent.posY+par3+ran3, ent.posZ+newPos2[0]+ran1, newPos3[1]*ran4, 0.05D*ran4, newPos3[0]*ran4, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX+newPos1[1]+ran3, ent.posY+par3+ran1, ent.posZ+newPos1[0]+ran2, newPos3[1]*ran4, 0.05D*ran4, newPos3[0]*ran4, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX+newPos2[1]+ran3, ent.posY+par3+ran2, ent.posZ+newPos2[0]+ran1, newPos3[1]*ran4, 0.05D*ran4, newPos3[0]*ran4, new int[0]);
			}
		}
		break;
		case 6:		//lightning sphere + lightning radiation
		{
			//in
			for (int i = 0; i < 4; i++)
			{
				ParticleStickyLightning light11 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, 2);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(light11);
			}
			//out
			for (int i = 0; i < 4; i++)
			{
				ParticleStickyLightning light21 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, 3);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(light21);
			}
		}
		break;
		case 7:		//vibrate cube
		{
			//host check
			if (ent instanceof EntityLivingBase)
			{
				host = (EntityLivingBase) ent;
			}
			else
			{
				return;
			}
			
			//in
			ParticleCube cube1 = new ParticleCube(world, host, par1, par2, par3, 1.5F, 0);
        	Minecraft.getMinecraft().effectRenderer.addEffect(cube1);
        	
        	//out
			for (int i = 0; i < 6; i++)
			{
				ParticleStickyLightning light21 = new ParticleStickyLightning(world, ent, (float)par1, 40, 3);
	        	Minecraft.getMinecraft().effectRenderer.addEffect(light21);
			}
		}
		break;
		case 8:		//守衛標示線: block類
		{
			//host check
			if (ent instanceof EntityLivingBase)
			{
				host = (EntityLivingBase) ent;
			}
			else
			{
				return;
			}
			
			ParticleLaserNoTexture laser1 = new ParticleLaserNoTexture(world, host, par1, par2, par3, 0.1F, 3);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser1);
		}
		break;
		case 9:		//small sticky lightning
		{
			ParticleStickyLightning light5 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, (int)par3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light5);
        	ParticleStickyLightning light6 = new ParticleStickyLightning(world, ent, (float)par1, (int)par2, (int)par3);
        	Minecraft.getMinecraft().effectRenderer.addEffect(light6);
		}
		break;
		case 10: 	//double largesmoke for mounts: par1: cannon width, par2: cannon height, par3: cannon x pos
		{
			//煙霧出現位置: 依照身體旋轉
			newPos1 = CalcHelper.rotateXZByAxis((float)par3, (float)par1, (float)(((EntityLivingBase)ent).renderYawOffset * Values.N.DIV_PI_180), 1F);
			newPos2 = CalcHelper.rotateXZByAxis((float)par3, (float)-par1, (float)(((EntityLivingBase)ent).renderYawOffset * Values.N.DIV_PI_180), 1F);
			//煙霧噴射方向: 依照頭部旋轉
			newPos3 = CalcHelper.rotateXZByAxis(1.5F, 0F, (float)(((EntityLivingBase)ent).rotationYawHead * Values.N.DIV_PI_180), 1F);
			
			//實際煙霧位置: 身體旋轉xz位移(達到砲台底座位置)+頭部旋轉xz位移(達到砲管旋轉位置)
			for (int i = 0; i < 24; i++)
			{
				ran1 = rand.nextFloat() - 0.5F;
				ran2 = rand.nextFloat();
				ran3 = rand.nextFloat();
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX-0.5D+0.05D*i+newPos1[1]+newPos3[1], ent.posY+par2+0.6D+ran1, ent.posZ-0.5D+0.05D*i+newPos1[0]+newPos3[0], newPos3[1]*0.5D*ran2, 0.05D*ran2, newPos3[0]*0.5D*ran2, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX-0.5D+0.05D*i+newPos2[1]+newPos3[1], ent.posY+par2+0.6D+ran1, ent.posZ-0.5D+0.05D*i+newPos2[0]+newPos3[0], newPos3[1]*0.5D*ran3, 0.05D*ran3, newPos3[0]*0.5D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX-0.5D+0.05D*i+newPos1[1]+newPos3[1], ent.posY+par2+0.9D+ran1, ent.posZ-0.5D+0.05D*i+newPos1[0]+newPos3[0], newPos3[1]*0.5D*ran3, 0.05D*ran3, newPos3[0]*0.5D*ran3, new int[0]);
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ent.posX-0.5D+0.05D*i+newPos2[1]+newPos3[1], ent.posY+par2+0.9D+ran1, ent.posZ-0.5D+0.05D*i+newPos2[0]+newPos3[0], newPos3[1]*0.5D*ran2, 0.05D*ran2, newPos3[0]*0.5D*ran2, new int[0]);
			}
		}
		break;
		case 11:	//color beam INWARD
		{
			//													 ent, type, scale, radius, beam speed, beam thick, 4~7:RGBA, height
			ParticleSphereLight light1 = new ParticleSphereLight(ent, 0, ent.height * 1.2F, ent.height * 0.75F, 0.8F, 0.03F, (float)par1, (float)par2, (float)par3, 1F, ent.height * 0.4F);
			Minecraft.getMinecraft().effectRenderer.addEffect(light1);
		}
		break;
		case 12:	//color gradient OUTWARD
		{
			if (ent instanceof IShipEmotion) ((IShipEmotion) ent).setAttackTick(100);
			//											  ent, type, scale, fade, speed, space, 4~7:RGBA, height
			ParticleGradient grad1 = new ParticleGradient(ent, 1, ent.height * 1.2F, 0.85F, 0.08F, 8F, (float)par1, (float)par2, (float)par3, 0.7F);
			Minecraft.getMinecraft().effectRenderer.addEffect(grad1);
		}
		break;
		case 13:	//abyssal goddess particle
		{
			//											 ent, type, scale, rad, NO_USE, NO_USE, 4~7:RGBA, height
			ParticleSparkle spark1 = new ParticleSparkle(ent, 8, 0.1F, ent.width * 2F, 0F, 0F, (float)par1, (float)par2, (float)par3, 1F, ent.height * 0.4F);
			Minecraft.getMinecraft().effectRenderer.addEffect(spark1);
		}
		break;
		case 14:	//double color gradient OUTWARD
		{
			//											  ent, type, scale, fade, speed, space, 4~7:RGBA, height
			ParticleGradient grad1 = new ParticleGradient(ent, 2, ent.height * 1.5F, 0.7F, 0.4F, 0F, (float)par1, (float)par2, (float)par3, 0.8F, 40F, 1.6F);
			ParticleGradient grad2 = new ParticleGradient(ent, 2, ent.height * 1.3F, 0.7F, 0.3F, 0F, 1F, 1F, 1F, 1F, 40F, 1.5F);
			Minecraft.getMinecraft().effectRenderer.addEffect(grad1);
			Minecraft.getMinecraft().effectRenderer.addEffect(grad2);
		}
		break;
		case 15:	//color sword sweep vertical
		{
			if (ent instanceof IShipEmotion) ((IShipEmotion) ent).setAttackTick(50);
			//									   ent, type, scale1, scale2, scale3, fade, age, RGBA
			ParticleSweep swp1 = new ParticleSweep(ent, 0, ent.height, ent.height * 5.6F, ent.height * 2F, 0.95F, 4F, (float)par1, (float)par2, (float)par3, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(swp1);
		}
		break;
		case 16:	//color sword sweep horizontal
		{
			if (ent instanceof IShipEmotion) ((IShipEmotion) ent).setAttackTick(50);
			//									   ent, type, scale1, scale2, scale3, fade, age, RGBA
			ParticleSweep swp1 = new ParticleSweep(ent, 0, ent.height * 0.1F, ent.height * 5.6F, ent.height * 6F, 0.95F, 4F, (float)par1, (float)par2, (float)par3, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(swp1);
		}
		break;
		case 17:	//eye sparkle
		{
			//											 ent, type, height, eye x, eye z, 4~7:RGBA, height
			ParticleSparkle spark1 = new ParticleSparkle(ent, 1, (float)par1, (float)par2, (float)par3, 0F, 1F, 1F, 1F);
			Minecraft.getMinecraft().effectRenderer.addEffect(spark1);
		}
		break;
		case 18:	//raytrace target indicator, parms: target entity, type, hit height, hit side, NO_USE
		{
			ParticleDebugPlane plane = new ParticleDebugPlane(ent, 0, (float)par1, (float)par2, (float)par3);
			Minecraft.getMinecraft().effectRenderer.addEffect(plane);
		}
		break;
		case 19:	//raytrace body cube indicator, parms: target entity, type, cube top, cube bottom, bodyID
		{
			if (ent instanceof BasicEntityShip)
			{
				ParticleDebugPlane plane = new ParticleDebugPlane(ent, 1, (float)par1, (float)par2, (float)par3);
				Minecraft.getMinecraft().effectRenderer.addEffect(plane);
			}
		}
		break;
		case 20:	//raytrace body cube indicator, parms: target entity, type, cube top, cube bottom, bodyID
		{
			if (ent instanceof BasicEntityShip)
			{
				ParticleDebugPlane plane = new ParticleDebugPlane(ent, 2, (float)par1, (float)par2, (float)par3);
				Minecraft.getMinecraft().effectRenderer.addEffect(plane);
			}
		}
		break;
		case 21:	//fill fluid particle
		{
			//											 ent, type, scale, rad, NO_USE, NO_USE, 4~7:RGBA, height
			ParticleSparkle spark1 = new ParticleSparkle(ent, 0, 0.025F, ent.width * 1.5F, 0F, 0F, (float)par1, (float)par2, (float)par3, 1F, ent.height * 0.4F);
			Minecraft.getMinecraft().effectRenderer.addEffect(spark1);
		}
		break;
		case 22:	//craning particle
		{
			//											 ent, type, scale, rad, NO_USE, NO_USE, 4~7:RGBA, height
			ParticleSparkle spark1 = new ParticleSparkle(ent, 3, 0.05F, ent.width * 1F, 0F, 0F, (float)par1, (float)par2, (float)par3, 1F, ent.height * 0.4F);
			Minecraft.getMinecraft().effectRenderer.addEffect(spark1);
		}
		break;
		case 23:	//healing particle
		{
			//											 ent, type, scale, rad, NO_USE, NO_USE, 4~7:RGBA, height
			ParticleSparkle spark1 = new ParticleSparkle(ent, 2, 0.075F, ent.width * 1.5F, 0F, 0F, (float)par1, (float)par2, (float)par3, 1F, ent.height * 0.4F);
			Minecraft.getMinecraft().effectRenderer.addEffect(spark1);
		}
		break;
		case 36:	//emotion
		{
			ParticleEmotion partEmo = new ParticleEmotion(world, ent,
					ent.posX, ent.posY, ent.posZ, (float)par1, (int)par2, (int)par3);
			Minecraft.getMinecraft().effectRenderer.addEffect(partEmo);
		}
		break;
		default:
		break;
		}
	}
	
	/**Spawn particle at entity position
	 * @parm host, par1, par2, par3, particleID
	 */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAtEntity(Entity host, Entity target, double par1, double par2, double par3, byte type, boolean setAtkTime)
	{
		World world = Minecraft.getMinecraft().world;
		EntityLivingBase host2 = null;
		
		//null check
		if (host == null || target == null)
		{
			return;
		}
		//set attack time, EntityLivingBase only
		else
		{
			if (setAtkTime && host instanceof IShipEmotion)
			{
				((IShipEmotion) host).setAttackTick(50);
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
		switch (type)
		{
		case 0:		//雙光束砲
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			
			ParticleLaserNoTexture laser1 = new ParticleLaserNoTexture(world, host2, target, 0.9F, par1, 0F, 0.05F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser1);
			
			ParticleLaserNoTexture laser2 = new ParticleLaserNoTexture(world, host2, target, -0.9F, par1, 0F, 0.05F, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser2);
		}
		break;
		case 1:		//yamato cannon beam
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			
			//beam head
			ParticleCube cube1 = new ParticleCube(world, host2, par1, par2, par3, 2.5F, 1);
        	Minecraft.getMinecraft().effectRenderer.addEffect(cube1);
        	
        	//beam body
			ParticleLaserNoTexture laser3 = new ParticleLaserNoTexture(world, host2, target, par1, par2, par3, 2F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser3);
		}
		break;
		case 2:		//yamato cannon beam for boss
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			
			//beam head
			ParticleCube cube2 = new ParticleCube(world, host2, par1, par2, par3, 5F, 1);
        	Minecraft.getMinecraft().effectRenderer.addEffect(cube2);
        	
        	//beam body
			ParticleLaserNoTexture laser4 = new ParticleLaserNoTexture(world, host2, target, par1, par2, par3, 4F, 1);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser4);
		}
		break;
		case 3:		//守衛標示線: entity類
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			
			ParticleLaserNoTexture laser5 = new ParticleLaserNoTexture(world, host2, target, 0D, 0D, 0D, 0.1F, 2);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser5);
		}
		break;
		case 4:		//補給標示線
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			
			ParticleLaserNoTexture laser6 = new ParticleLaserNoTexture(world, host2, target, 0D, 0D, 0D, 0.1F, 4);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser6);
		}
		break;
		case 5:		//位置標示線
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			
			ParticleLaserNoTexture laser = new ParticleLaserNoTexture(world, host2, target, 0D, 0D, 0D, 0.1F, 5);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser);
		}
		break;
		case 6:		//紫色可調粗細光束
		{
			//host check
			if (host instanceof EntityLivingBase)
			{
				host2 = (EntityLivingBase) host;
			}
			else
			{
				return;
			}
			//																			   height, scale out, scale in, NO_USE, type
			ParticleLaserNoTexture laser = new ParticleLaserNoTexture(world, host2, target, par1, par2, par3, 0F, 6);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser);
		}
		break;
		default:
		break;
		}
	}
	
	/** render text */
	@SideOnly(Side.CLIENT)
	public static void spawnAttackParticleAt(String text, double posX, double posY, double posZ, byte type, int...parms)
	{
		World w = ClientProxy.getClientWorld();
		
		//spawn particle
		switch (type)
		{
		case 0:		//show text on fixed position
		{
			ParticleTextsCustom ptx = new ParticleTextsCustom(null, w, posX, posY, posZ, 1F, 0, text, parms);
			Minecraft.getMinecraft().effectRenderer.addEffect(ptx);
		}
		break;
		case 1:		//show text on entity
		{
			Entity host = EntityHelper.getEntityByID(parms[2], 0, true);
			if (host == null) return;
			
			ParticleTextsCustom ptx = new ParticleTextsCustom(host, w, posX, posY, posZ, 1F, 1, text, parms);
			Minecraft.getMinecraft().effectRenderer.addEffect(ptx);
		}
		break;
		}
	}

	
}