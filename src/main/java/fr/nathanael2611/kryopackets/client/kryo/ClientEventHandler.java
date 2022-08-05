package fr.nathanael2611.kryopackets.client.kryo;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientEventHandler
{

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.phase == TickEvent.Phase.START)
        {
            if (mc.player == null && mc.world == null)
            {
                if(KryoClientManager.isStarted())
                {
                    KryoClientManager.stop();
                }
            }
        }
    }

}
