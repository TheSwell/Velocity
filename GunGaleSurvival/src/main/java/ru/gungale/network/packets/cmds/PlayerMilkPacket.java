package ru.gungale.network.packets.cmds;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import ru.gungale.network.packets.IInPacket;
import ru.gungale.utils.Replacer;

public record PlayerMilkPacket(String owner) implements IInPacket {


    public PlayerMilkPacket(ByteArrayDataInput input) {
        this(input.readUTF());
    }

    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) {
            for (PotionEffect effect :
                    player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
            player.sendMessage(Replacer.getStringFromConfig("milkMessage"));
        }
    }
}
