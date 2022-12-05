package ru.ggsv.listeners;

import com.velocitypowered.api.command.VelocityBrigadierMessage;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import ru.ggsv.Main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PluginListener {
    private final Main v;
    private final ProxyServer server;


    public PluginListener(Main v, ProxyServer server) {
        this.v = v;
        this.server = server;
    }


    @Subscribe
    public void onPlugMessage(PluginMessageEvent e) {
        String tag = e.getIdentifier().getId();
        if (!tag.equals("gungale:main")) return;
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(e.getData()));

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(stream);
        String channel;
        try {
            channel = inputStream.readUTF();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
//            System.out.println(channel);
        switch (channel) {
            case "GetBalance" -> {
                try {
                    String owner = inputStream.readUTF();
                    String playerNick = inputStream.readUTF();
                    double value = inputStream.readDouble();
                    outputStream.writeUTF(channel);
                    outputStream.writeUTF(owner);
                    outputStream.writeUTF(playerNick);
                    outputStream.writeDouble(value);
                    server.getPlayer(owner).get().getCurrentServer().get().getServer().sendPluginMessage(() -> "gungale:main", stream.toByteArray());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }


}
