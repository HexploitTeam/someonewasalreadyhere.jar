package com.someonewasalreadyhere.system.silence;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SilenceSyncPacket {

    public final float level;

    public SilenceSyncPacket(float level) {
        this.level = level;
    }

    public static void encode(SilenceSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.level);
    }

    public static SilenceSyncPacket decode(FriendlyByteBuf buf) {
        return new SilenceSyncPacket(buf.readFloat());
    }

    public static void handle(SilenceSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> SilenceClientHandler.applySilence(msg.level));
        ctx.get().setPacketHandled(true);
    }
}
