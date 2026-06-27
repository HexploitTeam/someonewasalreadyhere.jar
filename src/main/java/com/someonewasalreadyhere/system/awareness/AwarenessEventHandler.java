package com.someonewasalreadyhere.system.awareness;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class AwarenessEventHandler {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;
        // Only tick in the overworld to keep a single shared awareness state
        if (!sl.dimension().equals(Level.OVERWORLD)) return;

        AwarenessWorldData.get(sl).tick(sl);
    }
}
