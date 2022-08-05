package fr.nathanael2611.kryopackets.client.kryo;

import com.esotericsoftware.kryonet.Client;
import fr.nathanael2611.kryopackets.KryoPackets;
import fr.nathanael2611.kryopackets.network.objects.HelloImAPlayer;
import fr.nathanael2611.kryopackets.network.vanilla.PacketAlternate;
import fr.nathanael2611.kryopackets.network.vanilla.VanillaPacketHandler;
import fr.nathanael2611.kryopackets.server.KryoObjects;
import fr.nathanael2611.kryopackets.util.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The VoiceClient
 */
public class KryoClient
{
    /* The server port */
    private int port;
    /* The server host */
    private String host;
    /* The Client */
    private Client client;
    /* Is handshake done */
    private boolean handshakeDone = false;

    protected KryoObjects kryoObjects;

    private final ScheduledExecutorService RECONNECT_SERVICE = Executors.newSingleThreadScheduledExecutor();

    /**
     * Constructor
     *
     * @param playerName the player name
     * @param port       the server port
     */
    KryoClient(String playerName, String host, int port)
    {
        this.port = port;
        this.host = host;
        this.client = new Client(10000000, 10000000);
        client.start();
        this.kryoObjects = new KryoObjects(this, this.client.getKryo(), Side.CLIENT);
        this.kryoObjects.registerKryoObjects();
        client.addListener(new KryoNetClientListener(this));
        RECONNECT_SERVICE.scheduleAtFixedRate(() ->
        {
            if (!this.client.isConnected() && host != null)
            {
                try
                {
                    Helpers.log(String.format("Try to connect to the UDP server! [%s:%s]", host, this.port));
                    client.connect(5000, host, port, port);
                    this.authenticate(playerName);
                } catch (IOException e)
                {
                    e.printStackTrace();
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§4[" + KryoPackets.MOD_NAME + "] §c" + I18n.format("kryopackets.error.cantconnect")));
                    Helpers.log("Failed to connect to KryoServer.");
                }
            } else if (host == null)
            {
                Helpers.log("Host is null!");
            } else if (!isHandshakeDone())
            {
                {
                    this.authenticate(playerName);
                }
            }
        }, 5, 15, TimeUnit.SECONDS);


    }


    public KryoObjects getKryoObjects()
    {
        return kryoObjects;
    }

    public void setHandshakeDone()
    {
        this.handshakeDone = true;
        Helpers.log("Successfully authenticate with " + Minecraft.getMinecraft().player.getName());
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§2[" + KryoPackets.MOD_NAME + "] §a" + I18n.format("kryopackets.messages.connected")));
    }

    public boolean isHandshakeDone()
    {
        return handshakeDone;
    }

    /**
     * Send a packet to server
     *
     * @param object packet
     */
    public void send(Object object)
    {
        if(this.client.isConnected())
        {
            this.client.sendUDP(object);
        }
        else
        {
            if(!(object instanceof HelloImAPlayer))
            {
                VanillaPacketHandler.getInstance().getNetwork().sendToServer(new PacketAlternate(Side.CLIENT, object));
            }
        }
    }

    /**
     * Authenticate with a given name
     *
     * @param name player name
     */
    private void authenticate(String name)
    {
        Helpers.log("Try authenticate with username " + name);
        send(new HelloImAPlayer(name));
    }

    /**
     * Close the VoiceClient
     */
    public void close()
    {
        this.client.close();
        this.RECONNECT_SERVICE.shutdown();
    }

    /**
     * Check if the connection is connected to server
     *
     * @return true if client is connected
     */
    public boolean isConnected()
    {
        return this.client != null && this.client.isConnected();
    }

}
