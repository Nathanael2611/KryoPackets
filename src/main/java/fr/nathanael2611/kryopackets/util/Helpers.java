package fr.nathanael2611.kryopackets.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Helpers
{


    public static String readFileToString(File file)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "ERROR";
    }


    public static void log(String log)
    {
        System.out.println("[KryoPackets] " + log);
    }



    public static EntityPlayerMP getPlayerMP(EntityPlayer player)
    {
        if (player instanceof EntityPlayerMP) return (EntityPlayerMP) player;
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(player.getName());
    }

    public static EntityPlayerMP getPlayerByUsername(String name)
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name);
    }

    public static EntityPlayerMP getPlayerByEntityId(int entityId)
    {
        for (EntityPlayerMP player : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers())
        {
            if (player.getEntityId() == entityId)
            {
                return player;
            }
        }
        return null;
    }



}
