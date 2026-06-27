package com.someonewasalreadyhere.system.phantom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenErrorScreenPacket {

    public static void encode(OpenErrorScreenPacket msg, FriendlyByteBuf buf) {}

    public static OpenErrorScreenPacket decode(FriendlyByteBuf buf) {
        return new OpenErrorScreenPacket();
    }

    public static void handle(OpenErrorScreenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(PhantomClientHandler::openErrorScreen);
        ctx.get().setPacketHandled(true);
    }
}
