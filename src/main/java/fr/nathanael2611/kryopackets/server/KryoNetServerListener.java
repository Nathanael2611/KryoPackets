package fr.nathanael2611.kryopackets.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.common.collect.BiMap;
import net.minecraft.entity.player.EntityPlayerMP;

public class KryoNetServerListener extends Listener
{

    private KryoServer voiceServer;

    KryoNetServerListener(KryoServer server)
    {
        this.voiceServer = server;
    }

    @Override
    public void disconnected(Connection connection)
    {
        super.disconnected(connection);
        BiMap<Connection, Integer> map = this.voiceServer.CONNECTIONS_MAP.inverse();
        if (map.containsKey(connection))
        {
            this.voiceServer.CONNECTIONS_MAP.remove(map.get(connection));
        }
    }

    @Override
    public void received(Connection connection, Object object)
    {
        EntityPlayerMP player = this.voiceServer.getPlayer(connection);
        this.voiceServer.objects.receive(player, connection, object);

        super.received(connection, object);
    }
}