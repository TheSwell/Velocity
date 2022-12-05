package ru.ggsv;

import com.mojang.brigadier.Command;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import org.slf4j.Logger;
import ru.ggsv.commands.*;
import ru.ggsv.listeners.PluginListener;

import javax.inject.Inject;

@Plugin(id = "ggs", name = "Gun Gale Velocity", version = "0.1.0-SNAPSHOT", authors = "TheSwell")
public class Main {
    private ProxyServer server = null;
    private final Logger logger;

    private static Main instance;

    @Inject
    public Main(ProxyServer server, Logger logger) {
        instance = this;
        this.server = server;
        this.logger = logger;
        server.getChannelRegistrar().register(MinecraftChannelIdentifier.create("gungale", "main"));
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getEventManager().register(this, new PluginListener(this, server));

        CommandManager manager = server.getCommandManager();
        CommandMeta commandBurn = manager.metaBuilder("burn")
                .plugin(this)
                .build();
        CommandMeta commandBalance = manager.metaBuilder("balance")
                .plugin(this)
                .build();
        CommandMeta commandFeed = manager.metaBuilder("feed")
                .plugin(this)
                .build();
        CommandMeta commandExt = manager.metaBuilder("ext")
                .plugin(this)
                .build();
        CommandMeta commandExp = manager.metaBuilder("exp")
                .plugin(this)
                .build();
        CommandMeta commandHeal = manager.metaBuilder("heal")
                .plugin(this)
                .build();
        CommandMeta commandMilk = manager.metaBuilder("milk")
                .plugin(this)
                .build();
        CommandMeta commandFix = manager.metaBuilder("fix")
                .plugin(this)
                .build();
        CommandMeta commandVanish = manager.metaBuilder("vanish")
                .plugin(this)
                .build();

        BrigadierCommand balanceBrigadierCommand = BurnCommand.createBrigadierCommand(server);
        BrigadierCommand burnBrigadierCommand = BalanceCommand.createBrigadierCommand(server);
        BrigadierCommand feedBrigadierCommand = FeedCommand.createBrigadierCommand(server);
        BrigadierCommand extBrigadierCommand = ExtCommand.createBrigadierCommand(server);
        BrigadierCommand expBrigadierCommand = ExpCommand.createBrigadierCommand(server);
        BrigadierCommand healBrigadierCommand = HealCommand.createBrigadierCommand(server);
        BrigadierCommand milkBrigadierCommand = MilkCommand.createBrigadierCommand(server);
        BrigadierCommand fixBrigadierCommand = FixCommand.createBrigadierCommand(server);

        manager.register(commandBurn, balanceBrigadierCommand);
        manager.register(commandBalance, burnBrigadierCommand);
        manager.register(commandFeed, feedBrigadierCommand);
        manager.register(commandExt, extBrigadierCommand);
        manager.register(commandExp, expBrigadierCommand);
        manager.register(commandHeal, healBrigadierCommand);
        manager.register(commandMilk, milkBrigadierCommand);
        manager.register(commandFix, fixBrigadierCommand);
//        server.getEventManager().register(this, new ChatListener());
    }

    public static Main getInstance() {
        return instance;
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }


}
