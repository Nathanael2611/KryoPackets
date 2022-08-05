package fr.nathanael2611.kryopackets.client.kryo;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import net.minecraft.client.Minecraft;

public class KryoNetClientListener extends Listener
{

    private KryoClient voiceClient;

    public KryoNetClientListener(KryoClient kryoNetVoiceClient)
    {
        this.voiceClient = kryoNetVoiceClient;
    }

    @Override
    public void received(Connection connection, Object object)
    {
        this.voiceClient.kryoObjects.receive(Minecraft.getMinecraft().player, connection, object);
        super.received(connection, object);
    }
}
