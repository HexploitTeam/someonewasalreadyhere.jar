package com.someonewasalreadyhere.system.phantom;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.network.ModNetwork;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class PhantomSleepHandler {

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!(player.level() instanceof ServerLevel sl)) return;

        // Check if any PhantomPlayer entity is within 32 blocks
        boolean phantomNearby = sl.getEntitiesOfClass(
            net.minecraft.world.entity.LivingEntity.class,
            player.getBoundingBox().inflate(32),
            e -> e.getType() == com.someonewasalreadyhere.core.registry.ModEntities.PHANTOM_PLAYER.get()
        ).stream().findFirst().isPresent();

        if (phantomNearby) {
            event.setResult(net.minecraft.world.entity.player.Player.BedSleepingProblem.OTHER_PROBLEM);
            player.sendSystemMessage(Component.literal("§8[??] Sleep is not available in observed zones."));

            // Deal a small amount of damage to the player
            player.hurt(player.level().damageSources().generic(), 2.0f);

            // Send the error screen packet
            ModNetwork.sendToPlayer(player, new OpenErrorScreenPacket());
        }
    }
}
