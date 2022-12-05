package ru.gungale.network.packets;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.GunGaleSurvival;
import ru.gungale.network.NetworkManager;

public record PlayerBalanceReceivePacket(String owner, String playerNick, double balance) implements IInPacket {


    public PlayerBalanceReceivePacket(ByteArrayDataInput input) {
        this(input.readUTF(), input.readUTF(), input.readDouble());
    }

    @Override
    public void handle() {
        Player player = Bukkit.getPlayerExact(owner);
        if (player != null) player.sendMessage("Баланс игрока " + playerNick + " = " + balance);
    }
}
