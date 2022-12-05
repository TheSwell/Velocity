package ru.gungale.network.packets.cmds;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.GunGaleSurvival;
import ru.gungale.network.packets.IInPacket;

import java.util.HashMap;
import java.util.Map;

public record PlayerConnectOtherServerPacket(String owner) implements IInPacket {
    public PlayerConnectOtherServerPacket(ByteArrayDataInput input) {
        this(input.readUTF());
    }


    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) {
            player.setInvisible(PlayerVanishPacket.invisibleMap.getOrDefault(player.getName(), false));
        }
    }
}
