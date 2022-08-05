package fr.nathanael2611.kryopackets;

import fr.nathanael2611.kryopackets.network.vanilla.VanillaPacketHandler;
import fr.nathanael2611.kryopackets.proxy.CommonProxy;
import fr.nathanael2611.kryopackets.server.KryoServerManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = "kryopackets")
public class KryoPackets
{

    public static final String MOD_ID = "kryopackets";
    public static final String MOD_NAME = "KryoPackets";
    public static final int DEFAULT_PORT = 8773;

    @Mod.Instance(MOD_ID)
    public static KryoPackets INSTANCE;

    @SidedProxy(serverSide = "fr.nathanael2611.kryopackets.proxy.ServerProxy", clientSide = "fr.nathanael2611.kryopackets.proxy.ClientProxy")
    private static CommonProxy proxy;


    @Mod.EventHandler
    public void onPreInitialization(FMLPreInitializationEvent event)
    {

        VanillaPacketHandler.getInstance().registerPackets();

        proxy.onPreInitialization(event);
    }

    @Mod.EventHandler
    public void onInitialization(FMLInitializationEvent event)
    {
        proxy.onInitialization(event);
    }

    @Mod.EventHandler
    public void onPostInitialization(FMLPostInitializationEvent event)
    {
        proxy.onPostInitialization(event);
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event)
    {
        if(event.getServer().isDedicatedServer())
        {
            if(!KryoServerManager.isStarted())
            {
                KryoServerManager.start();
            }
        }
    }

    @Mod.EventHandler
    public void onServerStop(FMLServerStoppingEvent event)
    {
        if(KryoServerManager.isStarted())
        {
            KryoServerManager.stop();
        }
    }

}
