package fr.nathanael2611.kryopackets.api;

import fr.nathanael2611.kryopackets.client.kryo.KryoClient;
import fr.nathanael2611.kryopackets.network.Context;
import fr.nathanael2611.kryopackets.server.KryoServer;

public interface MessageHandler<T>
{

    public void receiveClient(KryoClient server, Context context, T object);

    public void receiveServer(KryoServer server, Context context, T object);
}
