package ru.gungale.utils;

import org.bukkit.configuration.file.FileConfiguration;
import ru.gungale.GunGaleSurvival;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfigData {
    private static final Map<String, Object> defaultConfigMessages = new HashMap<>();
    private static final FileConfiguration config = GunGaleSurvival.getInstance().getConfig();

    static {
        defaultConfigMessages.put("feedMessage", "Вы пополнили голод");
        defaultConfigMessages.put("healMessage", "Вы восстановили здоровье.");
        defaultConfigMessages.put("extMessage", "Вы потушили себя.");
        defaultConfigMessages.put("expMessage", "Вы выдали себе 1000 опыта.");
        defaultConfigMessages.put("milkMessage", "Вы сняли с себя все эффекты.");
        defaultConfigMessages.put("fixMessage", "Вы починили предмет");

        config.addDefaults(defaultConfigMessages);
        config.options().copyDefaults(true);
        GunGaleSurvival.getInstance().saveConfig();
    }

}
