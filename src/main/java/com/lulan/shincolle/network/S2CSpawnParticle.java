package com.lulan.shincolle.network;

import com.lulan.shincolle.reference.dataclass.ParticleData;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * SERVER TO CLIENT : SPAWN PARTICLE PACKET
 * 用於指定位置生成particle
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CSpawnParticle implements IMessage
{

	private ParticleData pdata;
	
	
	public S2CSpawnParticle() {}	//必須要有空參數constructor, forge才能使用此class
	
	public S2CSpawnParticle(ParticleData data)
	{
	    this.pdata = data;
	}
	
    /** send packet */
    @Override
    public void toBytes(ByteBuf buf)
    {
        try
        {
            PacketHelper.sendParticleData(buf, this.pdata);
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION: S2C particle packet: send data fail: "+e);
            e.printStackTrace();
        }
    }
    
    /** get packet */
    @Override
    public void fromBytes(ByteBuf buf)
    {   
        try
        {
            this.pdata = PacketHelper.readParticleData(buf);
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION: S2C particle packet: get data fail: "+e);
            e.printStackTrace();
        }
    }
	
    /** packet handle method */
    private static void handle(S2CSpawnParticle msg, MessageContext ctx)
    {
        try
        {
            ParticleHelper.spawnParticle(msg.pdata);
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION: client spawn particle fail: "+e);
            e.printStackTrace();
        }
    }
	
    /** packet handler (inner class) */
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