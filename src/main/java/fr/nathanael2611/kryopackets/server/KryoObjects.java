package fr.nathanael2611.kryopackets.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryonet.Connection;
import com.google.common.collect.Maps;
import fr.nathanael2611.kryopackets.api.KryoPacketRegistryEvent;
import fr.nathanael2611.kryopackets.api.MessageHandler;
import fr.nathanael2611.kryopackets.client.kryo.KryoClient;
import fr.nathanael2611.kryopackets.network.Context;
import fr.nathanael2611.kryopackets.network.objects.HelloImAPlayer;
import fr.nathanael2611.kryopackets.network.objects.HelloYouAreAPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;

public class KryoObjects
{

    private final Object theoricServer;
    private int nextId = 0;
    private final HashMap<Class, MessageHandler> associations = Maps.newHashMap();
    private final Kryo kryo;
    private Side side;

    public KryoObjects(Object theoricServer, Kryo kryo, Side side)
    {
        this.theoricServer = theoricServer;
        this.kryo = kryo;
        this.side = side;
    }

    public void registerKryoObjects()
    {
        this.registerObject(HelloImAPlayer.class, new HelloImAPlayer.Handler());
        this.registerObject(HelloYouAreAPlayer.class, new HelloYouAreAPlayer.Handler());
        KryoPacketRegistryEvent event = new KryoPacketRegistryEvent(this);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public void receive(EntityPlayer playerMP, Connection connection, Object object)
    {
        MessageHandler messageHandler = null;
        for (Class aClass : associations.keySet())
        {
            if(aClass.isInstance(object))
            {
                messageHandler = this.associations.get(aClass);
            }
        }
        if(messageHandler == null) return;
        if((this.side == Side.CLIENT && theoricServer instanceof KryoClient))
        {
            messageHandler.receiveClient((KryoClient)theoricServer, new Context(connection, Minecraft.getMinecraft().player), object);
        }
        else if((this.side == Side.SERVER && theoricServer instanceof KryoServer))
        {
            messageHandler.receiveServer((KryoServer) theoricServer, new Context(connection, playerMP), object);
        }
    }

    public void resterObject(Class objectClass, Serializer serializer)
    {
        this.registerObject(objectClass, serializer, null);
    }

    public void registerObject(Class objectClass, MessageHandler messageHandler)
    {
        this.registerObject(objectClass, null, messageHandler);
    }

    public void registerObject(Class objectClass, Serializer serializer, MessageHandler messageHandler)
    {
        if(serializer != null)
        {
            this.kryo.register(objectClass, serializer);
        }
        else
        {
            this.kryo.register(objectClass);
        }
        if(messageHandler != null)
        {
            this.associations.put(objectClass, messageHandler);
        }
    }

    public Kryo getKryo()
    {
        return kryo;
    }
}
