package com.someonewasalreadyhere.system.event;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class EventTickHandler {

    private static final Random RNG = new Random();
    private static int tickCounter = 0;
    private static final int RANDOM_EVENT_INTERVAL = 400; // every 20 seconds

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;

        // Tick scheduled events every server tick
        EventScheduler.tick(sl);

        // Randomly trigger events when awareness is sufficiently high
        tickCounter++;
        if (tickCounter < RANDOM_EVENT_INTERVAL) return;
        tickCounter = 0;

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness <= 250) return;

        // Chance increases with awareness: at 250 → ~5%, at 1000 → ~60%
        double chance = (awareness - 250) / 1250.0;
        if (RNG.nextDouble() < chance) {
            EventScheduler.randomEvent(sl, awareness);
        }
    }
}
