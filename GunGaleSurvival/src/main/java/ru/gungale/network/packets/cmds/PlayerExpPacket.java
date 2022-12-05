package ru.gungale.network.packets.cmds;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.network.packets.IInPacket;
import ru.gungale.utils.Replacer;

public record PlayerExpPacket(String owner) implements IInPacket {


    public PlayerExpPacket(ByteArrayDataInput input) {
        this(input.readUTF());
    }

    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) {
            addExperience(player, 1000);
            player.sendMessage(Replacer.getStringFromConfig("expMessage"));
        }
    }

    public void addExperience(Player player, int count) {
        int total = player.getTotalExperience() + count;
        player.setTotalExperience(total);
        player.setLevel(0);
        player.setExp(0);
        for (; total > player.getExpToLevel(); ) {
            total -= player.getExpToLevel();
            player.setLevel(player.getLevel() + 1);
        }
        float xp = (float) total / (float) player.getExpToLevel();
        player.setExp(xp);
    }
}
