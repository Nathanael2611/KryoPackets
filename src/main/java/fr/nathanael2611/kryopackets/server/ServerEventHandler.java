package fr.nathanael2611.kryopackets.server;

import fr.nathanael2611.kryopackets.config.ServerConfig;
import fr.nathanael2611.kryopackets.network.vanilla.PacketConnectPlayer;
import fr.nathanael2611.kryopackets.network.vanilla.VanillaPacketHandler;
import fr.nathanael2611.kryopackets.util.Helpers;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ServerEventHandler
{

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (KryoServerManager.isStarted())
        {
            Helpers.log("Requesting " + event.player.getName() + " to connect to voice-server... Sending packet.");
            VanillaPacketHandler.getInstance().getNetwork().sendTo(new PacketConnectPlayer(ServerConfig.generalConfig.forcedHostname, KryoServerManager.getServer().getPort(), event.player.getName()), Helpers.getPlayerMP(event.player));
        }

    }


}
