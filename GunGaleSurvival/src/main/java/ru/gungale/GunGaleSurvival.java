package ru.gungale;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.gungale.executors.GunGaleExecutors;
import ru.gungale.listeners.DamageListener;
import ru.gungale.listeners.MessageListener;
import ru.gungale.utils.DefaultConfigData;

import java.util.logging.Logger;

public class GunGaleSurvival extends JavaPlugin {
    private static GunGaleSurvival instance;
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;


    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        new DefaultConfigData();
        saveConfig();


        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "gungale:main");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "gungale:main", new MessageListener());


        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        
        initializeCommands();
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public void initializeCommands() {
        getCommand("ec").setExecutor(new GunGaleExecutors());
        getCommand("wb").setExecutor(new GunGaleExecutors());
        getCommand("feed").setExecutor(new GunGaleExecutors());
        getCommand("heal").setExecutor(new GunGaleExecutors());
        getCommand("ext").setExecutor(new GunGaleExecutors());
        getCommand("exp").setExecutor(new GunGaleExecutors());
        getCommand("milk").setExecutor(new GunGaleExecutors());
        getCommand("fix").setExecutor(new GunGaleExecutors());
        getCommand("burn").setExecutor(new GunGaleExecutors());
        getCommand("balance").setExecutor(new GunGaleExecutors());
        getCommand("getonline").setExecutor(new GunGaleExecutors());
    }

    public static GunGaleSurvival getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }


}
