package fr.nathanael2611.kryopackets.network.vanilla;

import fr.nathanael2611.kryopackets.KryoPackets;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * The Vanilla packet handler, that use the Minecraft Network system
 */
public class VanillaPacketHandler
{

    /**
     * Define the mod-network, we will use it to send packets ! :3
     */
    private SimpleNetworkWrapper network;

    /* Packet handler instance */
    private static VanillaPacketHandler instance;

    /* Used for automatic registry */
    private int nextID = 0;

    /**
     * Simply the instance getter
     *
     * @return the PacketHandler instance
     */
    public static VanillaPacketHandler getInstance()
    {
        if (instance == null) instance = new VanillaPacketHandler();
        return instance;
    }

    /**
     * Network getter
     *
     * @return SimpleNetworkWrapper instance
     */
    public SimpleNetworkWrapper getNetwork()
    {
        return network;
    }

    /**
     * This method will register all our packets.
     */
    public void registerPackets()
    {
        this.network = NetworkRegistry.INSTANCE.newSimpleChannel(KryoPackets.MOD_ID.toUpperCase());

        registerPacket(PacketConnectPlayer.Message.class, PacketConnectPlayer.class, Side.CLIENT);
        registerPacket(PacketAlternate.Message.class, PacketAlternate.class, Side.CLIENT);
        registerPacket(PacketAlternate.Message.class, PacketAlternate.class, Side.SERVER);

    }

    /**
     * Register a single packet
     */
    private <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        network.registerMessage(messageHandler, requestMessageType, nextID, side);
        nextID++;
    }

}
