package fr.nathanael2611.kryopackets.network.objects;

import com.esotericsoftware.kryonet.Connection;
import fr.nathanael2611.kryopackets.api.MessageHandler;
import fr.nathanael2611.kryopackets.client.kryo.KryoClient;
import fr.nathanael2611.kryopackets.client.kryo.KryoClientManager;
import fr.nathanael2611.kryopackets.network.Context;
import fr.nathanael2611.kryopackets.server.KryoServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HelloYouAreAPlayer
{
    public static class Handler implements MessageHandler<HelloYouAreAPlayer>
    {

        @SideOnly(Side.CLIENT)
        @Override
        public void receiveClient(KryoClient server, Context connection, HelloYouAreAPlayer object)
        {
            if(KryoClientManager.isStarted())
            {
                KryoClientManager.getClient().setHandshakeDone();
            }
        }

        @Override
        public void receiveServer(KryoServer server, Context connection, HelloYouAreAPlayer object)
        {

        }
    }
}
