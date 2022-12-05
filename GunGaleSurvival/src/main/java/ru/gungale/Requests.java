package ru.gungale;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Requests {

    public static void sendBalanceRequest(String owner, String playerNick) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Balance");
        out.writeUTF(owner);
        out.writeUTF(playerNick);
        Player player = Bukkit.getPlayerExact(owner);
        if (player == null) return;
        player.sendPluginMessage(GunGaleSurvival.getInstance(), "gungale:main", out.toByteArray());
    }


}
