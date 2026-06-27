package com.someonewasalreadyhere.system.echo;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.registry.ModEntities;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class EchoEventManager {

    private static final Random RNG = new Random();
    private static int tickCounter = 0;
    private static final int CHECK_INTERVAL = 200; // every 10 seconds

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;

        tickCounter++;
        if (tickCounter < CHECK_INTERVAL) return;
        tickCounter = 0;

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness < 150) return;

        // Spawn chance: 0–40% depending on awareness
        double spawnChance = (awareness - 150) / 2125.0;
        if (RNG.nextDouble() > spawnChance) return;

        for (ServerPlayer player : sl.getServer().getPlayerList().getPlayers()) {
            EchoCapability.get(player).ifPresent(history -> {
                history.getRandomRecord().ifPresent(record -> {
                    BlockPos spawnPos = record.pos();
                    // Verify the position is safe enough
                    if (!sl.isLoaded(spawnPos)) return;

                    var echoHunterType = ModEntities.ECHO_HUNTER.get();
                    var echoHunter = echoHunterType.create(sl);
                    if (echoHunter == null) return;

                    echoHunter.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, RNG.nextFloat() * 360, 0);
                    sl.addFreshEntityWithPassengers(echoHunter);

                    SomeoneWasAlreadyHere.LOGGER.debug("[Echo] EchoHunter spawned at {} from history action '{}'",
                        spawnPos, record.action());
                });
            });
        }
    }

    /** Call this from player event handlers to record actions. */
    public static void recordAction(ServerPlayer player, BlockPos pos, String action) {
        EchoCapability.get(player).ifPresent(history ->
            history.addRecord(pos, action, player.level().getGameTime())
        );
    }
}
