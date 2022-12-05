package ru.ggsv.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import ru.ggsv.player.PlayerCommands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ExtCommand {

    public static BrigadierCommand createBrigadierCommand(final ProxyServer proxy) {
        LiteralCommandNode<CommandSource> helloNode = LiteralArgumentBuilder
                .<CommandSource>literal("ext")
                .executes(context -> {
                    if (!PlayerCommands.isUsed(context.getSource(), "ext")) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        DataOutputStream outputStream = new DataOutputStream(stream);

                        try {
                            String channel = "ExtCmd";
                            String owner = "Console";
                            if (context.getSource() instanceof Player) {
                                owner = ((Player) context.getSource()).getUsername();
                                PlayerCommands.addUsedCommandInList(context.getSource(), "ext", 5);
                            }
                            outputStream.writeUTF(channel);
                            outputStream.writeUTF(owner);
                            proxy.getPlayer(owner).get().getCurrentServer().get().getServer().sendPluginMessage(() -> "gungale:main", stream.toByteArray());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        context.getSource().sendMessage(Component.text("Ждите"));
                    }
                    return Command.SINGLE_SUCCESS;
                })
                .build();

        // BrigadierCommand implements Command
        return new BrigadierCommand(helloNode);
    }
}
