package fr.nathanael2611.kryopackets.proxy;

import fr.nathanael2611.kryopackets.client.kryo.ClientEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{

    @Override
    public void onPreInitialization(FMLPreInitializationEvent event)
    {
        super.onPreInitialization(event);

        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

    }

    @Override
    public void onInitialization(FMLInitializationEvent event)
    {
        super.onInitialization(event);
    }

    @Override
    public void onPostInitialization(FMLPostInitializationEvent event)
    {
        super.onPostInitialization(event);
    }

}
