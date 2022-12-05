package ru.gungale.network.packets.cmds;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.network.packets.IInPacket;
import ru.gungale.utils.Replacer;

public record PlayerExtPacket(String owner) implements IInPacket {


    public PlayerExtPacket(ByteArrayDataInput input) {
        this(input.readUTF());
    }

    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) {
            player.setFireTicks(0);
            player.sendMessage(Replacer.getStringFromConfig("extMessage"));
        }
    }
}
