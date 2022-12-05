package ru.gungale.network.packets;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.gungale.GunGaleSurvival;
import ru.gungale.network.NetworkManager;

public record PlayerBalanceRequestPacket(String owner, String playerNick) implements IInPacket {


    public PlayerBalanceRequestPacket(ByteArrayDataInput input) {
        this(input.readUTF(), input.readUTF());
    }

    @Override
    public void handle() {
        String playerNick = playerNick();
        Player selectedPlayer = Bukkit.getPlayerExact(playerNick);
        double balance = GunGaleSurvival.getEconomy().getBalance(selectedPlayer);
        NetworkManager.sendPacket(selectedPlayer, new PlayerGetBalancePacket(owner, playerNick, balance));
    }
}
