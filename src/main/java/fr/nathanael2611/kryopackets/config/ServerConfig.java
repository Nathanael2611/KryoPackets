package fr.nathanael2611.kryopackets.config;

import fr.nathanael2611.kryopackets.KryoPackets;
import net.minecraftforge.common.config.Config;

@Config(modid = KryoPackets.MOD_ID, name = KryoPackets.MOD_NAME + "/ServerConfig")
public class ServerConfig
{

    @Config.Comment({"This is the general config of ModularVoiceChat"})
    public static General generalConfig = new General();

    public static class General
    {
        @Config.Comment("The vocal-server port")
        public int port = KryoPackets.DEFAULT_PORT;

        @Config.Comment({"This field is optionnal, but may correct some issue with connecting to voice-server!", "By providing an given hostname you are assured that all players use the same."})
        public String forcedHostname = "";
    }

}
