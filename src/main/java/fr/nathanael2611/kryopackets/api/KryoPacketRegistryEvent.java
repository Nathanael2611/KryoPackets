package fr.nathanael2611.kryopackets.api;

import fr.nathanael2611.kryopackets.server.KryoObjects;
import net.minecraftforge.fml.common.eventhandler.Event;

public class KryoPacketRegistryEvent extends Event
{

    private KryoObjects objects;

    public KryoPacketRegistryEvent(KryoObjects objects)
    {
        this.objects = objects;
    }

    public KryoObjects getObjects()
    {
        return objects;
    }
}
