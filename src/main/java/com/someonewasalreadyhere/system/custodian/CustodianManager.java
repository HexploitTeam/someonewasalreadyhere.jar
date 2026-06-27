package com.someonewasalreadyhere.system.custodian;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class CustodianManager {

    private static final int TICK_INTERVAL = 600;
    private static final int SCAN_RADIUS = 32;
    private static final Random RNG = new Random();

    private static int tickCounter = 0;
    private static final List<String> repairLog = new ArrayList<>();

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;

        tickCounter++;
        if (tickCounter < TICK_INTERVAL) return;
        tickCounter = 0;

        AwarenessWorldData data = AwarenessWorldData.get(sl);
        int awareness = data.getAwareness();

        // Chance of custodian action scales with awareness
        double actionChance = awareness / 1000.0;

        for (ServerPlayer player : sl.getServer().getPlayerList().getPlayers()) {
            if (RNG.nextDouble() > actionChance) continue;

            int repairs = 0;
            BlockPos origin = player.blockPosition();

            for (int attempt = 0; attempt < 20; attempt++) {
                int dx = RNG.nextInt(SCAN_RADIUS * 2) - SCAN_RADIUS;
                int dz = RNG.nextInt(SCAN_RADIUS * 2) - SCAN_RADIUS;
                int dy = RNG.nextInt(9) - 4;

                BlockPos pos = origin.offset(dx, dy, dz);
                BlockState state = sl.getBlockState(pos);

                // Replace lava with obsidian at high awareness (Custodian "sealing")
                if (state.is(Blocks.LAVA) && data.isHigh()) {
                    sl.setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
                    log("Custodian sealed lava source at " + pos);
                    repairs++;
                }

                // Convert gravel to stone (stabilisation)
                else if (state.is(Blocks.GRAVEL) && RNG.nextDouble() < 0.4) {
                    sl.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
                    log("Custodian compacted gravel at " + pos);
                    repairs++;
                }

                // Replace dirt paths with cobblestone (infrastructure repair)
                else if (state.is(Blocks.DIRT_PATH) && RNG.nextDouble() < 0.3) {
                    sl.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
                    log("Custodian paved path at " + pos);
                    repairs++;
                }

                // At critical awareness, add fences to "contain" areas
                else if (data.isCritical() && state.is(Blocks.AIR) && RNG.nextDouble() < 0.05) {
                    BlockState below = sl.getBlockState(pos.below());
                    if (below.isSolidRender(sl, pos.below())) {
                        sl.setBlockAndUpdate(pos, Blocks.OAK_FENCE.defaultBlockState());
                        log("Custodian erected barrier at " + pos);
                        repairs++;
                    }
                }

                if (repairs >= 5) break;
            }

            if (repairs > 0) {
                player.sendSystemMessage(Component.literal(
                    "§8[Maintenance] " + repairs + " environmental correction(s) applied nearby."
                ));
                // Play custodian work sound
                sl.playSound(null, player.blockPosition(),
                    com.someonewasalreadyhere.core.registry.ModSounds.CUSTODIAN_WORK.get(),
                    net.minecraft.sounds.SoundSource.AMBIENT, 0.6f, 0.8f + RNG.nextFloat() * 0.4f);
            }
        }
    }

    private static void log(String msg) {
        if (repairLog.size() >= 500) repairLog.remove(0);
        repairLog.add(msg);
        SomeoneWasAlreadyHere.LOGGER.debug("[Custodian] " + msg);
    }

    public static List<String> getRepairLog() {
        return List.copyOf(repairLog);
    }
}
