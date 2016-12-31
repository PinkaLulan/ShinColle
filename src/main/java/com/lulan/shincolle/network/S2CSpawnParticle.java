package com.lulan.shincolle.network;

import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : SPAWN PARTICLE PACKET
 * 用於指定位置生成particle
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CSpawnParticle implements IMessage
{

	private Entity entity, entity2;
	private int entityID, entityID2;
	private byte packetType, particleType;
	private boolean setAtkTime;		//set ship attackTime for model render
	private int[] valueInt1;
	private float[] valueFloat1;
	
	/** packet id for internal use only */
	private static final class PID
	{
		private static final byte Entity_Animate = 0;
		private static final byte Entity_Pos_Look_Animate = 1;
		private static final byte Entity_Pos_Look = 2;
		private static final byte Entity_Target_Animate = 3;
		private static final byte Entity_Par3 = 4;
		private static final byte Ints_Path = 5;
	}
	
	
	public S2CSpawnParticle() {}	//必須要有空參數constructor, forge才能使用此class
	
	/** spawn particle: 
	 *  type 0: spawn particle with entity
	 */
	public S2CSpawnParticle(Entity entity, int type, boolean setAtkTime)
	{
        this.entity = entity;
        this.packetType = PID.Entity_Animate;
        this.setAtkTime = setAtkTime;
        this.particleType = (byte) type;
    }
	
	/** type 1: spawn particle with entity and position
	 */
	public S2CSpawnParticle(Entity entity, int type, double posX, double posY, double posZ, double lookX, double lookY, double lookZ, boolean setAtkTime)
	{
		this.entity = entity;
        this.packetType = PID.Entity_Pos_Look_Animate;
        this.setAtkTime = setAtkTime;
        this.particleType = (byte) type;
        
        this.valueFloat1 = new float[6];
        this.valueFloat1[0] = (float) posX;
        this.valueFloat1[1] = (float) posY;
        this.valueFloat1[2] = (float) posZ;
        this.valueFloat1[3] = (float) lookX;
        this.valueFloat1[4] = (float) lookY;
        this.valueFloat1[5] = (float) lookZ;
    }
	
	/** type 2: spawn particle at position
	 */
	public S2CSpawnParticle(int type, double posX, double posY, double posZ, double lookX, double lookY, double lookZ)
	{
        this.packetType = PID.Entity_Pos_Look;
        this.particleType = (byte) type;
        
        this.valueFloat1 = new float[6];
        this.valueFloat1[0] = (float) posX;
        this.valueFloat1[1] = (float) posY;
        this.valueFloat1[2] = (float) posZ;
        this.valueFloat1[3] = (float) lookX;
        this.valueFloat1[4] = (float) lookY;
        this.valueFloat1[5] = (float) lookZ;
    }
	
	/** type 3: spawn particle with host and target entity
	 */
	public S2CSpawnParticle(Entity host, Entity target, double par1, double par2, double par3, int type, boolean setAtkTime)
	{
		this.packetType = PID.Entity_Target_Animate;
		this.setAtkTime = setAtkTime;
        this.particleType = (byte) type;
        this.entityID = host.getEntityId();
        this.entityID2 = target.getEntityId();
        
        this.valueFloat1 = new float[3];
        this.valueFloat1[0] = (float) par1;
        this.valueFloat1[1] = (float) par2;
        this.valueFloat1[2] = (float) par3;
	}
	
	/** type 4: spawn particle with entity and 3 double parms
	 */
	public S2CSpawnParticle(Entity entity, int type, double par1, double par2, double par3)
	{
		this.entity = entity;
        this.packetType = PID.Entity_Par3;
        this.particleType = (byte) type;
        
        this.valueFloat1 = new float[3];
        this.valueFloat1[0] = (float) par1;
        this.valueFloat1[1] = (float) par2;
        this.valueFloat1[2] = (float) par3;
    }
	
	/** type 5: spawn path indicator particle
	 */
	public S2CSpawnParticle(int type, int[] data)
	{
		this.packetType = PID.Ints_Path;
		this.particleType = (byte) type;
		this.valueInt1 = data;
	}
	
	//接收packet: CLIENT SIDE
	@Override
	public void fromBytes(ByteBuf buf)
	{
		//get type and entityID
		this.packetType = buf.readByte();
	
		switch(this.packetType)
		{
		case PID.Entity_Animate:	//spawn particle with entity
		{
			this.entityID = buf.readInt();
			this.particleType = buf.readByte();
			this.setAtkTime = buf.readBoolean();
		}
		break;
		case PID.Entity_Pos_Look_Animate: //spawn particle with entity and position
		{
			this.entityID = buf.readInt();
			this.particleType = buf.readByte();
			this.setAtkTime = buf.readBoolean();
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 6);
		}
		break;
		case PID.Entity_Pos_Look: //spawn particle with position
		{
			this.particleType = buf.readByte();
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 6);
		}
		break;
		case PID.Entity_Target_Animate: //spawn particle with position
		{
			this.particleType = buf.readByte();
			this.setAtkTime = buf.readBoolean();
			this.entityID = buf.readInt();
			this.entityID2 = buf.readInt();
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 3);
		}
		break;
		case PID.Entity_Par3: //spawn particle with entity and 3 parms
		{
			this.entityID = buf.readInt();
			this.particleType = buf.readByte();
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 3);
		}
		break;
		case PID.Ints_Path:		//spawn path indicator particle
		{
			this.particleType = buf.readByte();
			
			int len = buf.readInt();
			this.valueInt1 = PacketHelper.readIntArray(buf, len);
		}
		break;
		}//end switch
	}

	//發出packet: SERVER SIDE
	@Override
	public void toBytes(ByteBuf buf)
	{
		switch (this.packetType)
		{
		case PID.Entity_Animate:	//spawn particle with entity
		{
			if(this.entity == null) return;
			
			buf.writeByte(0);	//type 0
			buf.writeInt(this.entity.getEntityId());
			buf.writeByte(this.particleType);
			buf.writeBoolean(this.setAtkTime);
		}
		break;
		case PID.Entity_Pos_Look_Animate:	//spawn particle with entity and position
		{
			if(this.entity == null) return;
			
			buf.writeByte(1);	//type 1
			buf.writeInt(this.entity.getEntityId());
			buf.writeByte(this.particleType);
			buf.writeBoolean(this.setAtkTime);
			buf.writeFloat(this.valueFloat1[0]);
			buf.writeFloat(this.valueFloat1[1]);
			buf.writeFloat(this.valueFloat1[2]);
			buf.writeFloat(this.valueFloat1[3]);
			buf.writeFloat(this.valueFloat1[4]);
			buf.writeFloat(this.valueFloat1[5]);
		}
		break;
		case PID.Entity_Pos_Look:	//spawn particle with position
		{
			buf.writeByte(2);	//type 2
			buf.writeByte(this.particleType);
			buf.writeFloat(this.valueFloat1[0]);
			buf.writeFloat(this.valueFloat1[1]);
			buf.writeFloat(this.valueFloat1[2]);
			buf.writeFloat(this.valueFloat1[3]);
			buf.writeFloat(this.valueFloat1[4]);
			buf.writeFloat(this.valueFloat1[5]);
		}
		break;
		case PID.Entity_Target_Animate:	//spawn particle with host and target
		{
			buf.writeByte(3);	//type 3
			buf.writeByte(this.particleType);
			buf.writeBoolean(this.setAtkTime);
			buf.writeInt(this.entityID);
			buf.writeInt(this.entityID2);
			buf.writeFloat(this.valueFloat1[0]);
			buf.writeFloat(this.valueFloat1[1]);
			buf.writeFloat(this.valueFloat1[2]);
		}
		break;
		case PID.Entity_Par3:	//spawn particle with entity and position
		{
			if (this.entity == null) return;
			
			buf.writeByte(4);	//type 1
			buf.writeInt(this.entity.getEntityId());
			buf.writeByte(this.particleType);
			buf.writeFloat(this.valueFloat1[0]);
			buf.writeFloat(this.valueFloat1[1]);
			buf.writeFloat(this.valueFloat1[2]);
		}
		break;
		case PID.Ints_Path:		//spawn path indicator particle
		{
			if (this.valueInt1 == null || this.valueInt1.length <= 0) return;
			
			buf.writeByte(5);	//type 1
			buf.writeByte(this.particleType);
			
			int len = this.valueInt1.length;
			buf.writeInt(len);
			
			for (int i = 0; i < len; i++)
			{
				buf.writeInt(this.valueInt1[i]);
			}
		}
		break;
		}//end switch
	}
	
	//packet handle method
	private static void handle(S2CSpawnParticle msg, MessageContext ctx)
	{
		switch(msg.packetType)
		{
		case PID.Entity_Animate:		//spawn particle with entity
			ParticleHelper.spawnAttackParticle(EntityHelper.getEntityByID(msg.entityID, 0, true),
												msg.particleType, msg.setAtkTime);	
		break;
		case PID.Entity_Pos_Look_Animate: //spawn particle with entity and position
			ParticleHelper.spawnAttackParticleCustomVector(EntityHelper.getEntityByID(msg.entityID, 0, true),
												msg.valueFloat1[0], msg.valueFloat1[1], msg.valueFloat1[2],
												msg.valueFloat1[3], msg.valueFloat1[4], msg.valueFloat1[5],
												msg.particleType, msg.setAtkTime);
		break;
		case PID.Entity_Pos_Look: 		//spawn particle with position
			ParticleHelper.spawnAttackParticleAt(msg.valueFloat1[0], msg.valueFloat1[1], msg.valueFloat1[2],
												 msg.valueFloat1[3], msg.valueFloat1[4], msg.valueFloat1[5],
												 msg.particleType);
		break;
		case PID.Entity_Target_Animate: //spawn particle with position
			ParticleHelper.spawnAttackParticleAtEntity(EntityHelper.getEntityByID(msg.entityID, 0, true),
												EntityHelper.getEntityByID(msg.entityID2, 0, true),
												msg.valueFloat1[0], msg.valueFloat1[1], msg.valueFloat1[2],
												msg.particleType, msg.setAtkTime);
		break;
		case PID.Entity_Par3: 			//spawn particle with entity and 3 parms
			ParticleHelper.spawnAttackParticleAtEntity(EntityHelper.getEntityByID(msg.entityID, 0, true),
							msg.valueFloat1[0], msg.valueFloat1[1], msg.valueFloat1[2], msg.particleType);
		break;
		case PID.Ints_Path:				//spawn path indicator particle
		{
			int len, cid;
			byte parType;
			
			if (msg.particleType == 0)	//is ship path
			{
				len = (msg.valueInt1.length - 1) / 3;
				cid = msg.valueInt1[0];
				
				for (int i = 0; i < len; i++)
				{
					if (i == cid)
					{
						parType = 32;
					}
					else
					{
						parType = 33;
					}
					
					ParticleHelper.spawnAttackParticleAt(msg.valueInt1[i * 3 + 1] + 0.5D,
							msg.valueInt1[i * 3 + 2] + 0.5D, msg.valueInt1[i * 3 + 3] + 0.5D,
							0D, 0D, 0D, parType);
				}
			}
			else	//is vanilla path
			{
				len = (msg.valueInt1.length - 1) / 3;
				cid = msg.valueInt1[0];
				
				for (int i = 0; i < len; i++)
				{
					if (i == cid)
					{
						parType = 16;
					}
					else
					{
						parType = 29;
					}
					
					ParticleHelper.spawnAttackParticleAt(msg.valueInt1[i * 3 + 1] + 0.5D,
							msg.valueInt1[i * 3 + 2] + 0.5D, msg.valueInt1[i * 3 + 3] + 0.5D,
							0D, 0D, 0D, parType);
				}
			}
		}
		break;
		}//end switch
	}

	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CSpawnParticle, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CSpawnParticle message, MessageContext ctx)
		{
			/**
			 * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
			 * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
			 * 以避免多執行緒下各種並行處理問題
			 */
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> S2CSpawnParticle.handle(message, ctx));
			return null;
		}
    }
	
	
}