package com.someonewasalreadyhere.core.network;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.phantom.OpenErrorScreenPacket;
import com.someonewasalreadyhere.system.phantom.PhantomPresencePacket;
import com.someonewasalreadyhere.system.silence.SilenceSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register() {
        CHANNEL.registerMessage(id++, SilenceSyncPacket.class,
            SilenceSyncPacket::encode,
            SilenceSyncPacket::decode,
            SilenceSyncPacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        CHANNEL.registerMessage(id++, PhantomPresencePacket.class,
            PhantomPresencePacket::encode,
            PhantomPresencePacket::decode,
            PhantomPresencePacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        CHANNEL.registerMessage(id++, OpenErrorScreenPacket.class,
            OpenErrorScreenPacket::encode,
            OpenErrorScreenPacket::decode,
            OpenErrorScreenPacket::handle,
            Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToAll(Object packet) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }
}
