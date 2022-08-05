/**
 * Copyright 2019-2021 Keldaria. Tous droits réservés.
 * Toute reproduction, diffusion, partage, distribution,
 * commercialisation sans autorisation explicite est interdite.
 */
package fr.nathanael2611.kryopackets.api;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.KryoDataInput;
import com.esotericsoftware.kryo.io.KryoDataOutput;
import com.esotericsoftware.kryo.io.Output;
import io.netty.handler.codec.EncoderException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.io.IOException;
import java.util.function.Function;

public class INBTSerializer<T extends INBTSerializable<NBTTagCompound>> extends Serializer<T>
{

    private Function<NBTTagCompound, T> converter;


    public INBTSerializer(Function<NBTTagCompound, T> converter)
    {
        super(false);
        this.converter = converter;
    }

    @Override
    public void write(Kryo kryo, Output output, T nbt)
    {
        if (nbt == null)
        {
            output.writeByte(0);
        } else
        {
            try
            {
                CompressedStreamTools.write(nbt.serializeNBT(), new KryoDataOutput(output));
            } catch (IOException ioexception)
            {
                throw new EncoderException(ioexception);
            }
        }
    }

    @Override
    public T read(Kryo kryo, Input input, Class<T> aClass)
    {
        try
        {
            NBTTagCompound compound = CompressedStreamTools.read(new KryoDataInput(input), new NBTSizeTracker(2097152L));
            return this.converter.apply(compound);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
