package fr.nathanael2611.kryopackets.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import fr.nathanael2611.kryopackets.config.ServerConfig;
import fr.nathanael2611.kryopackets.network.objects.HelloImAPlayer;
import fr.nathanael2611.kryopackets.network.objects.HelloYouAreAPlayer;
import fr.nathanael2611.kryopackets.network.vanilla.PacketAlternate;
import fr.nathanael2611.kryopackets.network.vanilla.VanillaPacketHandler;
import fr.nathanael2611.kryopackets.util.Helpers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class KryoServer
{

    public final HashBiMap<Integer, Connection> CONNECTIONS_MAP = HashBiMap.create();

    protected KryoObjects objects;
    private int port;
    private Server server;

    KryoServer()
    {
        this.port = ServerConfig.generalConfig.port;
        this.server = new Server(10000000, 10000000);
        this.objects = new KryoObjects(this, this.server.getKryo(), Side.SERVER);
        this.objects.registerKryoObjects();
        server.start();
        try
        {
            server.bind(this.port, this.port);
            server.addListener(new KryoNetServerListener(this));
            Helpers.log("Successfully started KryoServer.");
        } catch (IOException e)
        {
            e.printStackTrace();
            Helpers.log("Failed to start KryoServer.");
        }
    }

    public void sendToAllExcept(EntityPlayerMP except, Object obj)
    {
        for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
        {
            if(player != except)
            {
                send(player, obj);
            }
        }
    }

    public void send(EntityPlayerMP dest, Object obj)
    {
        Connection connection = getPlayerConnection(dest);
        if (connection != null)
        {
            connection.sendUDP(obj);
        }
        else
        {
            if(!(obj instanceof HelloYouAreAPlayer))
            {
                VanillaPacketHandler.getInstance().getNetwork().sendTo(new PacketAlternate(Side.SERVER, obj), dest);
            }
        }
    }

    public boolean isPlayerConnected(EntityPlayer player)
    {
        return getPlayerConnection(player) != null;
    }

    public Connection getPlayerConnection(EntityPlayer player)
    {
        return player == null ? null : this.CONNECTIONS_MAP.get(player.getEntityId());
    }

    public boolean hasAssignedPlayer(Connection connection)
    {
        return getPlayer(connection) != null;
    }

    public EntityPlayerMP getPlayer(Connection connection)
    {
        return Helpers.getPlayerByEntityId(this.CONNECTIONS_MAP.inverse().getOrDefault(connection, -1));
    }

    public void close()
    {
        this.server.close();
        Helpers.log("Successfully closed VoiceServer.");
    }

    public int getPort()
    {
        return port;
    }

    public List<EntityPlayerMP> getConnectedPlayers()
    {
        List<EntityPlayerMP> list = Lists.newArrayList();
        for (Map.Entry<Connection, Integer> entry : this.CONNECTIONS_MAP.inverse().entrySet())
        {
            EntityPlayerMP player = Helpers.getPlayerByEntityId(entry.getValue());
            Connection conn = entry.getKey();
            if (player != null && conn != null && conn.isConnected())
            {
                list.add(player);
            }
        }
        return list;
    }

    public KryoObjects getKryoObjects()
    {
        return this.objects;
    }
}

