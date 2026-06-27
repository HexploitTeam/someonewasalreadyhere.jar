package com.someonewasalreadyhere.system.phantom;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.registry.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class TunnelListenerSoundListener {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel sl)) return;

        // Notify all TunnelListener entities near the break position
        sl.getEntitiesOfClass(LivingEntity.class,
            new net.minecraft.world.phys.AABB(event.getPos()).inflate(64),
            e -> e.getType() == ModEntities.TUNNEL_LISTENER.get()
        ).forEach(e -> {
            if (e instanceof com.someonewasalreadyhere.core.registry.ModEntities.TunnelListenerEntity listener) {
                listener.onBlockBrokenNearby(event.getPos());
            }
        });
    }
}
