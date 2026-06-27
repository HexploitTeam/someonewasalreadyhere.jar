package com.someonewasalreadyhere.system.phantom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PhantomPresencePacket {

    public final boolean active;

    public PhantomPresencePacket(boolean active) {
        this.active = active;
    }

    public static void encode(PhantomPresencePacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.active);
    }

    public static PhantomPresencePacket decode(FriendlyByteBuf buf) {
        return new PhantomPresencePacket(buf.readBoolean());
    }

    public static void handle(PhantomPresencePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> PhantomClientHandler.setActive(msg.active));
        ctx.get().setPacketHandled(true);
    }
}
