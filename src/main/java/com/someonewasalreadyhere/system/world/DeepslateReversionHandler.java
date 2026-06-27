package com.someonewasalreadyhere.system.world;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class DeepslateReversionHandler {

    private static final int TICK_INTERVAL = 80;
    private static final int SCAN_RADIUS = 20;
    private static final Random RNG = new Random();

    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;

        tickCounter++;
        if (tickCounter < TICK_INTERVAL) return;
        tickCounter = 0;

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        // Only revert deepslate at moderate+ awareness; chance scales with it
        if (awareness < 200) return;

        float chance = (awareness - 200) / 1600.0f;

        for (ServerPlayer player : sl.getServer().getPlayerList().getPlayers()) {
            for (int attempt = 0; attempt < 10; attempt++) {
                if (RNG.nextFloat() > chance) continue;

                BlockPos pos = player.blockPosition().offset(
                    RNG.nextInt(SCAN_RADIUS * 2) - SCAN_RADIUS,
                    RNG.nextInt(SCAN_RADIUS * 2) - SCAN_RADIUS,
                    RNG.nextInt(SCAN_RADIUS * 2) - SCAN_RADIUS
                );

                BlockState state = sl.getBlockState(pos);

                if (state.is(Blocks.DEEPSLATE)) {
                    sl.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
                } else if (state.is(Blocks.COBBLED_DEEPSLATE)) {
                    sl.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
                } else if (state.is(Blocks.DEEPSLATE_BRICKS)) {
                    sl.setBlockAndUpdate(pos, Blocks.STONE_BRICKS.defaultBlockState());
                } else if (state.is(Blocks.DEEPSLATE_TILES)) {
                    sl.setBlockAndUpdate(pos, Blocks.STONE_BRICKS.defaultBlockState());
                } else if (state.is(Blocks.POLISHED_DEEPSLATE)) {
                    sl.setBlockAndUpdate(pos, Blocks.POLISHED_ANDESITE.defaultBlockState());
                }
            }
        }
    }
}
