package ru.gungale.network.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.utils.Replacer;

public record PlayerBurnPacket(String owner, String playerNick) implements IInPacket {

    public PlayerBurnPacket(ByteArrayDataInput input) {
        this(input.readUTF(), input.readUTF());
    }


    @Override
    public void handle() {

        Player selectedPlayer = Bukkit.getPlayerExact(playerNick);
        if (selectedPlayer != null) {
            selectedPlayer.sendMessage(Replacer.getStringFromConfig("burnedMessage", owner));
            selectedPlayer.setFireTicks(20 * 10);
        }

    }
}
