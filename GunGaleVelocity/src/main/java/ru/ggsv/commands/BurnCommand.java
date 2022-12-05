package ru.ggsv.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.io.*;
import java.util.Optional;

public class BurnCommand {

    public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> helloNode = LiteralArgumentBuilder
                .<CommandSource>literal("burn")

                .then(RequiredArgumentBuilder.<CommandSource, String>argument("username", StringArgumentType.word())
                                // Here you can define the hints to be provided in case the ArgumentType does not provide them.
                                // In this example, the names of all connected players are provided
                                .suggests((ctx, builder) -> {
                                            // Here we provide the names of the players along with a tooltip,
                                            // which can be used as an explanation of a specific argument or as a simple decoration
                                            proxy.getAllPlayers().forEach(player -> builder.suggest(
                                                    player.getUsername()
                                            ));
                                            return builder.buildFuture();
                                        }
                                )
                                .executes(context -> {

                                    String argumentProvided = StringArgumentType.getString(context, "username");
                                    Player player1 = proxy.getPlayer(argumentProvided).orElse(null);
                                    if (player1 != null) {
                                        context.getSource().sendMessage(Component.text("Вы подожгли игрока " + argumentProvided));
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        DataOutputStream outputStream = new DataOutputStream(stream);
                                        try {
                                            String channel = "Burn";
                                            String owner = "Console";
                                            if (context.getSource() instanceof Player) {
                                                owner = ((Player) context.getSource()).getUsername();
                                            }
                                            String playerNick = argumentProvided;
                                            outputStream.writeUTF(channel);
                                            outputStream.writeUTF(owner);
                                            outputStream.writeUTF(playerNick);
                                            proxy.getPlayer(playerNick).get().getCurrentServer().get().getServer().sendPluginMessage(() -> "gungale:main", stream.toByteArray());
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    } else context.getSource().sendMessage(Component.text("Игрок не найден"));

                                    return Command.SINGLE_SUCCESS;
                                })
                )
                .build();

        // BrigadierCommand implements Command
        return new BrigadierCommand(helloNode);
    }
}
