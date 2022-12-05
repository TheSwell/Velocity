package ru.gungale.network.packets;

import com.google.common.io.ByteArrayDataOutput;

public interface IOutPacket {

    public void write(ByteArrayDataOutput buf);
}
