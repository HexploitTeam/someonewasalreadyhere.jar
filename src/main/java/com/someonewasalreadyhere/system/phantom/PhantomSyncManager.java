package com.someonewasalreadyhere.system.phantom;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.network.ModNetwork;
import com.someonewasalreadyhere.core.registry.ModEntities;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class PhantomSyncManager {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity().getType() != ModEntities.PHANTOM_PLAYER.get()) return;

        // Notify all players that a phantom is now present
        event.getLevel().players().stream()
            .filter(p -> p instanceof ServerPlayer)
            .map(p -> (ServerPlayer) p)
            .forEach(p -> ModNetwork.sendToPlayer(p, new PhantomPresencePacket(true)));

        SomeoneWasAlreadyHere.LOGGER.debug("[Phantom] PhantomPlayer joined — notified {} players",
            event.getLevel().players().size());
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity().getType() != ModEntities.PHANTOM_PLAYER.get()) return;

        // Check if any other phantoms remain
        boolean anyRemaining = event.getLevel().getEntitiesOfClass(
            net.minecraft.world.entity.LivingEntity.class,
            event.getEntity().getBoundingBox().inflate(256),
            e -> e.getType() == ModEntities.PHANTOM_PLAYER.get() && e != event.getEntity()
        ).size() > 0;

        if (!anyRemaining) {
            event.getLevel().players().stream()
                .filter(p -> p instanceof ServerPlayer)
                .map(p -> (ServerPlayer) p)
                .forEach(p -> ModNetwork.sendToPlayer(p, new PhantomPresencePacket(false)));
        }
    }
}
