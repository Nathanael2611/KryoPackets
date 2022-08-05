package fr.nathanael2611.kryopackets.server;

import fr.nathanael2611.kryopackets.util.Helpers;

/**
 * Static VoiceServer manager
 * Used for simply access to the VoiceServer
 */
public class KryoServerManager
{

    /* VoiceServer instance */
    private static KryoServer INSTANCE;

    /**
     * Used to start the VoiceServer
     */
    public synchronized static void start()
    {
        Helpers.log("Starting KryoServer...");
        INSTANCE = new KryoServer();
    }

    /**
     * Used for stop the VoiceServer
     */
    public synchronized static void stop()
    {
        Helpers.log("Stopping KryoServer...");
        if (INSTANCE != null)
        {
            INSTANCE.close();
            INSTANCE = null;
        }
    }

    /**
     * Used for check if VoiceServer is actually running
     * @return true if server is started
     */
    public static boolean isStarted()
    {
        return INSTANCE != null;
    }

    /**
     * VoiceServer getter
     * @return the VoiceServer instance
     */
    public static KryoServer getServer()
    {
        return INSTANCE;
    }

}