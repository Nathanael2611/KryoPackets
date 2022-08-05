package fr.nathanael2611.kryopackets.network.vanilla;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import fr.nathanael2611.kryopackets.client.kryo.KryoClientManager;
import fr.nathanael2611.kryopackets.server.KryoObjects;
import fr.nathanael2611.kryopackets.server.KryoServerManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketAlternate implements IMessage
{

    private Object kryoObject;
    private Class objClass;
    private Side originSide;

    public PacketAlternate()
    {
    }

    public PacketAlternate(Side side, Object kryoObject)
    {
        this.originSide = side;
        this.kryoObject = kryoObject;
        this.objClass = kryoObject.getClass();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.originSide = buf.readBoolean() ? Side.CLIENT : Side.SERVER;
        try
        {
            this.objClass = Class.forName(ByteBufUtils.readUTF8String(buf));
            KryoObjects objects = fromSide(getReverseSide());
            this.kryoObject = objects.getKryo().readObject(new Input(new ByteBufInputStream(buf)), objClass);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private Side getReverseSide()
    {
        return this.originSide == Side.CLIENT ? Side.SERVER : Side.CLIENT;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(Side.CLIENT == this.originSide);
        ByteBufUtils.writeUTF8String(buf, this.objClass.getName());
        KryoObjects objects = fromSide(this.originSide);
        objects.getKryo().writeObject(new Output(new ByteBufOutputStream(buf)), kryoObject);
    }

    public static KryoObjects fromSide(Side side)
    {
        if (side.isClient())
        {
            return KryoClientManager.getClient().getKryoObjects();
        } else
        {
            return KryoServerManager.getServer().getKryoObjects();
        }
    }

    public static class Message implements IMessageHandler<PacketAlternate, IMessage>
    {
        @Override
        public IMessage onMessage(PacketAlternate message, MessageContext ctx)
        {
            fromSide(message.getReverseSide()).receive(message.originSide == Side.CLIENT ? Minecraft.getMinecraft().player : ctx.getServerHandler().player, null, message.kryoObject);
            return null;
        }
    }

}
