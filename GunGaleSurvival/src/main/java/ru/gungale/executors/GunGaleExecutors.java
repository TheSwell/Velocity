package ru.gungale.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GunGaleExecutors implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        switch (label) {
            case "ec":
                player.openInventory(player.getEnderChest());
                break;
            case "wb":
                player.openInventory(player.openWorkbench(null, true));
                break;
        }

        return false;
    }


}
