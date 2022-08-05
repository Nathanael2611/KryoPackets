package fr.nathanael2611.kryopackets.network;

import com.esotericsoftware.kryonet.Connection;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Context
{

    private Connection connection;
    private EntityPlayer playerMP;

    public Context(Connection connection, EntityPlayer playerMP)
    {
        this.connection = connection;
        this.playerMP = playerMP;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public EntityPlayerMP getPlayerMP()
    {
        return (EntityPlayerMP) playerMP;
    }

    public EntityPlayerSP getPlayerSP()
    {
        return (EntityPlayerSP) playerMP;
    }

}
