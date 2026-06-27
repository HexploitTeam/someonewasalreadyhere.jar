package com.someonewasalreadyhere.system.echo;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class EchoCapability {

    public static final Capability<PlayerEchoHistory> ECHO_HISTORY =
        CapabilityManager.get(new CapabilityToken<>() {});

    private static final ResourceLocation CAP_ID =
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "echo_history");

    public static void register(IEventBus modBus) {
        modBus.addListener(EchoCapability::onRegisterCaps);
    }

    private static void onRegisterCaps(RegisterCapabilitiesEvent event) {
        event.register(PlayerEchoHistory.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) return;

        PlayerEchoHistory history = new PlayerEchoHistory();
        LazyOptional<PlayerEchoHistory> optional = LazyOptional.of(() -> history);

        event.addCapability(CAP_ID, new ICapabilitySerializable<CompoundTag>() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ECHO_HISTORY ? optional.cast() : LazyOptional.empty();
            }

            @Override
            public CompoundTag serializeNBT() { return history.serializeNBT(); }

            @Override
            public void deserializeNBT(CompoundTag tag) { history.deserializeNBT(tag); }
        });

        event.addListener(optional::invalidate);
    }

    /** Copy capability data on respawn / dimension change. */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(ECHO_HISTORY).ifPresent(oldHistory -> {
            event.getEntity().getCapability(ECHO_HISTORY).ifPresent(newHistory -> {
                newHistory.deserializeNBT(oldHistory.serializeNBT());
            });
        });
        event.getOriginal().invalidateCaps();
    }

    /** Helper: get echo history for a player. */
    public static java.util.Optional<PlayerEchoHistory> get(Player player) {
        return player.getCapability(ECHO_HISTORY).resolve();
    }
}
