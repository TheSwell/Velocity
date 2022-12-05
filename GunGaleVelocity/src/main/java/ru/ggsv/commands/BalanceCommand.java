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
import ru.ggsv.Main;

import java.io.*;

public class BalanceCommand {
    public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> helloNode = LiteralArgumentBuilder
                .<CommandSource>literal("balance")

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
                                // Here the logic of the command "/test <some argument>" is executed
                                .executes(context -> {
                                    // Here you get the argument that the CommandSource has entered.
                                    // You must enter exactly the name as you have named the argument
                                    // and you must provide the class of the argument you expect, in this case... a String
                                    //    String argumentProvided = context.getArgument("argument", String.class);

                                    if (context.getSource() instanceof Player) {

                                        String argumentProvided = StringArgumentType.getString(context, "username");
                                        // This method will check if the given string corresponds to a
                                        // player's name and if it does, it will send a message to that player
                                        proxy.getPlayer(argumentProvided).ifPresent(player -> {

                                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                                    DataOutputStream outputStream = new DataOutputStream(stream);
                                                    try {
                                                        String channel = "BalanceRequest";
                                                        String owner = ((Player) context.getSource()).getUsername();
                                                        String playerNick = argumentProvided;
                                                        outputStream.writeUTF(channel);
                                                        outputStream.writeUTF(owner);
                                                        outputStream.writeUTF(playerNick);
                                                        proxy.getPlayer(playerNick).get().getCurrentServer().get().getServer().sendPluginMessage(() -> "gungale:main", stream.toByteArray());
                                                    } catch (IOException ex) {
                                                        ex.printStackTrace();
                                                    }
                                                }
                                        );
                                    }
                                    // Returning Command.SINGLE_SUCCESS means that the execution was successful
                                    // Returning BrigadierCommand.FORWARD will send the command to the server
                                    return Command.SINGLE_SUCCESS;
                                })
                )
                .build();

        // BrigadierCommand implements Command
        return new BrigadierCommand(helloNode);
    }
}
