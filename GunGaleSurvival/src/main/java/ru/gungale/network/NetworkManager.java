package ru.gungale.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import ru.gungale.GunGaleSurvival;
import ru.gungale.network.packets.*;
import ru.gungale.network.packets.cmds.*;

import java.util.HashMap;
import java.util.function.Function;

public class NetworkManager {
    public static final HashMap<String, Function<ByteArrayDataInput, IInPacket>> inPackets = new HashMap<>();
    public static final HashMap<Class<? extends IOutPacket>, String> outPackets = new HashMap<>();

    static {
        regInPacket("Burn", PlayerBurnPacket::new);
        regInPacket("BalanceRequest", PlayerBalanceRequestPacket::new);
        regInPacket("GetBalance", PlayerBalanceReceivePacket::new);

        regInPacket("FeedCmd", PlayerFeedPacket::new);
        regInPacket("HealCmd", PlayerHealPacket::new);
        regInPacket("ExtCmd", PlayerExtPacket::new);
        regInPacket("ExpCmd", PlayerExpPacket::new);
        regInPacket("MilkCmd", PlayerMilkPacket::new);
        regInPacket("FixCmd", PlayerFixPacket::new);
        regInPacket("VanishEnter", PlayerConnectOtherServerPacket::new);

        regOutPacket("GetBalance", PlayerGetBalancePacket.class);
    }

    static void regInPacket(String id, Function<ByteArrayDataInput, IInPacket> factory) {
        inPackets.put(id, factory);
    }


    static void regOutPacket(String id, Class<? extends IOutPacket> factory) {
        outPackets.put(factory, id);
    }

    public static void sendPacket(Player player, IOutPacket outPacket) {
        ByteArrayDataOutput newOut = ByteStreams.newDataOutput();
        newOut.writeUTF(outPackets.get(outPacket.getClass()));
        outPacket.write(newOut);
        player.sendPluginMessage(GunGaleSurvival.getInstance(), "gungale:main", newOut.toByteArray());
    }

    public static void onMessageReceived(ByteArrayDataInput input) {
        inPackets.get(input.readUTF()).apply(input).handle();
    }

}
