package ru.gungale.utils;

import org.bukkit.ChatColor;
import ru.gungale.GunGaleSurvival;

public class Replacer {


    public static String getStringFromConfig(String s) {
        String key = GunGaleSurvival.getInstance().getConfig().getString(s);
        return ChatColor.translateAlternateColorCodes('&', key);
    }

    public static String getStringFromConfig(String s, String player) {
        String key = GunGaleSurvival.getInstance().getConfig().getString(s);
        return ChatColor.translateAlternateColorCodes('&', key.replaceAll("%player%", player));
    }
}
