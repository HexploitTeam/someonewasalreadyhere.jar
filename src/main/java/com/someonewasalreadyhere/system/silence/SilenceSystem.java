package com.someonewasalreadyhere.system.silence;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.network.ModNetwork;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class SilenceSystem {

    /** Maps chunk positions to their silence level [0.0, 1.0]. */
    public static final Map<ChunkPos, Float> silenceLevels = new HashMap<>();

    private static int tickCounter = 0;
    private static final int TICK_INTERVAL = 40; // every 2 seconds

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;

        tickCounter++;
        if (tickCounter < TICK_INTERVAL) return;
        tickCounter = 0;

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        float awarenessRatio = awareness / 1000.0f;

        // Decay all existing silence levels slightly
        silenceLevels.replaceAll((pos, val) -> Math.max(0f, val - 0.01f));
        // Remove fully decayed entries
        silenceLevels.entrySet().removeIf(e -> e.getValue() <= 0f);

        for (ServerPlayer player : sl.getServer().getPlayerList().getPlayers()) {
            ChunkPos playerChunk = new ChunkPos(player.blockPosition());

            // Increase silence in nearby chunks based on awareness
            float increase = awarenessRatio * 0.05f;
            for (int dx = -2; dx <= 2; dx++) {
                for (int dz = -2; dz <= 2; dz++) {
                    ChunkPos nearby = new ChunkPos(playerChunk.x + dx, playerChunk.z + dz);
                    float cur = silenceLevels.getOrDefault(nearby, 0f);
                    silenceLevels.put(nearby, Math.min(1.0f, cur + increase));
                }
            }

            // Apply effects to player based on local silence
            float localSilence = silenceLevels.getOrDefault(playerChunk, 0f);
            applyPlayerEffects(player, localSilence);

            // Sync to client
            ModNetwork.sendToPlayer(player, new SilenceSyncPacket(localSilence));
        }
    }

    private static void applyPlayerEffects(ServerPlayer player, float silence) {
        if (silence > 0.3f) {
            // Slowness scaling with silence
            int amplifier = (int) (silence * 2);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, TICK_INTERVAL + 10, amplifier, false, false));
        }
        if (silence > 0.6f) {
            // Mining fatigue at very high silence
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, TICK_INTERVAL + 10, 0, false, false));
        }
        if (silence > 0.85f) {
            // Darkness at near-max silence
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, TICK_INTERVAL + 10, 0, false, false));
        }
    }

    public static float getSilenceAt(ChunkPos pos) {
        return silenceLevels.getOrDefault(pos, 0f);
    }
}
