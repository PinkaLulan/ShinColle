package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : SPAWN PARTICLE PACKET
 * 用於指定位置生成particle
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CSpawnParticle implements IMessage {

	private Entity entity;
	private int entityID, type;
	private byte particleType;
	private boolean isShip;
	private float posX, posY, posZ, lookX, lookY, lookZ;
	
	
	public S2CSpawnParticle() {}	//必須要有空參數constructor, forge才能使用此class
	
	//spawn particle: 
	//type 0: spawn particle with entity, if isShip -> set ship attackTime
	public S2CSpawnParticle(Entity entity, int type, boolean isShip) {
        this.entity = entity;
        this.type = 0;
        this.isShip = isShip;
        this.particleType = (byte)type;
    }
	
	//type 1: spawn particle with entity and position, if isShip -> set ship attackTime
	public S2CSpawnParticle(Entity entity, int type, double posX, double posY, double posZ, double lookX, double lookY, double lookZ, boolean isShip) {
		this.entity = entity;
        this.type = 1;
        this.isShip = isShip;
        this.particleType = (byte)type;
        this.posX = (float)posX;
        this.posY = (float)posY;
        this.posZ = (float)posZ;
        this.lookX = (float)lookX;
        this.lookY = (float)lookY;
        this.lookZ = (float)lookZ;
    }
	
	//type 2: spawn particle with position
	public S2CSpawnParticle(int type, int worldID, double posX, double posY, double posZ, double lookX, double lookY, double lookZ) {
        this.type = 2;
        this.particleType = (byte)type;
        this.posX = (float)posX;
        this.posY = (float)posY;
        this.posZ = (float)posZ;
        this.lookX = (float)lookX;
        this.lookY = (float)lookY;
        this.lookZ = (float)lookZ;
    }
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case 0:	//spawn particle with entity
			{
				this.entityID = buf.readInt();
				
				entity = EntityHelper.getEntityByID(entityID, 0, true);
				
				this.particleType = buf.readByte();
				this.isShip = buf.readBoolean();
				ParticleHelper.spawnAttackParticle(entity, particleType, isShip);	
			}
			break;
		case 1: //spawn particle with entity and position
			{
				this.entityID = buf.readInt();
				
				entity = EntityHelper.getEntityByID(entityID, 0, true);
				
				this.particleType = buf.readByte();
				this.isShip = buf.readBoolean();
				this.posX = buf.readFloat();
				this.posY = buf.readFloat();
				this.posZ = buf.readFloat();
				this.lookX = buf.readFloat();
				this.lookY = buf.readFloat();
				this.lookZ = buf.readFloat();
				ParticleHelper.spawnAttackParticleCustomVector(entity, (double)posX, (double)posY, (double)posZ, (double)lookX, (double)lookY, (double)lookZ, particleType, isShip);
			}
			break;
		case 2: //spawn particle with position
			{
				this.particleType = buf.readByte();
				this.posX = buf.readFloat();
				this.posY = buf.readFloat();
				this.posZ = buf.readFloat();
				this.lookX = buf.readFloat();
				this.lookY = buf.readFloat();
				this.lookZ = buf.readFloat();
				
				ParticleHelper.spawnAttackParticleAt(posX, posY, posZ, lookX, lookY, lookZ, particleType);	
			}
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0:	//spawn particle with entity
			{
				buf.writeByte(0);	//type 0
				buf.writeInt(this.entity.getEntityId());
				buf.writeByte(this.particleType);
				buf.writeBoolean(this.isShip);
			}
			break;
		case 1:	//spawn particle with entity and position
			{
				buf.writeByte(1);	//type 1
				buf.writeInt(this.entity.getEntityId());
				buf.writeByte(this.particleType);
				buf.writeBoolean(this.isShip);
				buf.writeFloat(this.posX);
				buf.writeFloat(this.posY);
				buf.writeFloat(this.posZ);
				buf.writeFloat(this.lookX);
				buf.writeFloat(this.lookY);
				buf.writeFloat(this.lookZ);
			}
			break;
		case 2:	//spawn particle with position
			{
				buf.writeByte(2);	//type 2
				buf.writeByte(this.particleType);
				buf.writeFloat(this.posX);
				buf.writeFloat(this.posY);
				buf.writeFloat(this.posZ);
				buf.writeFloat(this.lookX);
				buf.writeFloat(this.lookY);
				buf.writeFloat(this.lookZ);
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CSpawnParticle, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CSpawnParticle message, MessageContext ctx) {
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv Spawn Particle packet : type "+recvType+" particle "+recvParticleType);
			return null;
		}
    }
	

}

