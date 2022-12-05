package ru.gungale.network.packets;

import com.google.common.io.ByteArrayDataOutput;

public record PlayerGetBalancePacket(String owner, String playerNick, double balance) implements IOutPacket {

    @Override
    public void write(ByteArrayDataOutput buf) {
        buf.writeUTF(owner);
        buf.writeUTF(playerNick);
        buf.writeDouble(balance);
    }
}
