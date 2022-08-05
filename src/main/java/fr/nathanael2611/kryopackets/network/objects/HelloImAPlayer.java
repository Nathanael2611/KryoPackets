package fr.nathanael2611.kryopackets.network.objects;

import com.esotericsoftware.kryonet.Connection;
import fr.nathanael2611.kryopackets.api.MessageHandler;
import fr.nathanael2611.kryopackets.client.kryo.KryoClient;
import fr.nathanael2611.kryopackets.network.Context;
import fr.nathanael2611.kryopackets.server.KryoServer;
import fr.nathanael2611.kryopackets.util.Helpers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

/**
 * This object will be send from client to the server to inform it that a new player is connected to
 * the voice-server.
 */
public class HelloImAPlayer
{

    /**
     * The player-name.
     */
    public String playerName;

    /**
     * Constructor
     * @param playerName the name of the player that will be assigned to the connection.
     */
    public HelloImAPlayer(String playerName)
    {
        this.playerName = playerName;
    }

    /**
     * Constructor
     * Empty for serialization
     */
    public HelloImAPlayer()
    {
    }

    public static class Handler implements MessageHandler<HelloImAPlayer>
    {

        @Override
        public void receiveClient(KryoClient server, Context connection, HelloImAPlayer object)
        {

        }

        @Override
        public void receiveServer(KryoServer server, Context connection, HelloImAPlayer object)
        {
            HelloImAPlayer hello = ((HelloImAPlayer) object);
            Helpers.log("A new player tried to connect to VoiceServer named: " + hello.playerName);
            EntityPlayerMP playerMP = Helpers.getPlayerByUsername(hello.playerName);
            if (playerMP != null)
            {
                server.CONNECTIONS_MAP.put(playerMP.getEntityId(), connection.getConnection());
                Helpers.log("Successfully added " + hello.playerName + " to voice-server connected-players!");
                connection.getConnection().sendTCP(new HelloYouAreAPlayer());
            }
            else
            {
                Helpers.log("No player named: " + hello.playerName);
            }
        }
    }

}
