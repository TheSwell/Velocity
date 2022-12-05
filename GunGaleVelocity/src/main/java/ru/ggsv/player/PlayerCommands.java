package ru.ggsv.player;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import ru.ggsv.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerCommands {
    private static final Map<CommandSource, List<String>> commandMap = new HashMap<>();


    public static void runRemoveCommandTask(CommandSource player, String cmd, int delay) {
        if (isUsed(player, cmd)) {
            Main.getInstance().getServer().getScheduler().buildTask(Main.getInstance(), () -> {
                List<String> list = commandMap.get(player);
                list.remove(cmd);
                commandMap.put(player, list);
            }).delay(delay, TimeUnit.MINUTES).schedule();
        }
    }

    public static List<String> getUsedCommandList(CommandSource player) {
        return commandMap.get(player);
    }

    public static void addUsedCommandInList(CommandSource player, String command, int delay) {
        List<String> list = commandMap.getOrDefault(player, new ArrayList<>());
        list.add(command);
        commandMap.put(player, list);
        runRemoveCommandTask(player, command, delay);
    }


    public static boolean isUsed(CommandSource player, String command) {
        List<String> list = commandMap.getOrDefault(player, new ArrayList<>());
        return list.contains(command);
    }


}
