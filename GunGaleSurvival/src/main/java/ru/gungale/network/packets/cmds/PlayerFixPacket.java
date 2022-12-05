package ru.gungale.network.packets.cmds;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import ru.gungale.network.packets.IInPacket;
import ru.gungale.utils.Replacer;

public record PlayerFixPacket(String owner) implements IInPacket {


    public PlayerFixPacket(ByteArrayDataInput input) {
        this(input.readUTF());
    }

    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) {
            player.getInventory().getItemInMainHand().setDurability((short) 0);
            player.sendMessage(Replacer.getStringFromConfig("fixMessage"));
        }
    }
}
