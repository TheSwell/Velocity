package ru.gungale.network.packets.cmds;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.network.packets.IInPacket;
import ru.gungale.utils.Replacer;

public record PlayerHealPacket(String owner) implements IInPacket {


    public PlayerHealPacket(ByteArrayDataInput input) {
        this(input.readUTF());
    }

    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) {
            player.setHealth(player.getMaxHealth());
            player.sendMessage(Replacer.getStringFromConfig("healMessage"));
        }
    }
}
