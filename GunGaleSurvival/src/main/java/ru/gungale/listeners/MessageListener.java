package ru.gungale.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import ru.gungale.network.NetworkManager;

public class MessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equals("gungale:main"))
            return;
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        NetworkManager.onMessageReceived(in);
    }
}
