package com.someonewasalreadyhere.client;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.client.renderer.*;
import com.someonewasalreadyhere.core.registry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // Humanoid-shaped entities
        event.registerEntityRenderer(ModEntities.OBSERVER.get(),          HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.ECHO_HUNTER.get(),       HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.CARETAKER.get(),         HumanoidEntityRenderer.CustodianRenderer::new);
        event.registerEntityRenderer(ModEntities.PHANTOM_PLAYER.get(),    HumanoidEntityRenderer.PhantomRenderer::new);
        event.registerEntityRenderer(ModEntities.ARCHIVIST.get(),         HumanoidEntityRenderer.ArchivistRenderer::new);
        event.registerEntityRenderer(ModEntities.HOLLOW_MIMIC.get(),      HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.WINDOW_WATCHER.get(),    HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.SURVEYOR.get(),          HumanoidEntityRenderer.CustodianRenderer::new);
        event.registerEntityRenderer(ModEntities.STATIC_SHEPHERD.get(),   HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.SLEEPER.get(),           HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.TUNNEL_LISTENER.get(),   HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.INCOMPLETE.get(),        HumanoidEntityRenderer.IncompleteRenderer::new);
        event.registerEntityRenderer(ModEntities.FORGOTTEN_MINER.get(),   HumanoidEntityRenderer.CustodianRenderer::new);
        event.registerEntityRenderer(ModEntities.MIRROR_WALKER.get(),     HumanoidEntityRenderer.MirrorRenderer::new);
        event.registerEntityRenderer(ModEntities.QUIET_HOST.get(),        HumanoidEntityRenderer.HorrorRenderer::new);
        event.registerEntityRenderer(ModEntities.OBSERVER_PRIME.get(),    HumanoidEntityRenderer.BossRenderer::new);
        event.registerEntityRenderer(ModEntities.RESIDUAL_ECHO.get(),     HumanoidEntityRenderer.PhantomRenderer::new);
        event.registerEntityRenderer(ModEntities.CARTOGRAPHER.get(),      HumanoidEntityRenderer.CustodianRenderer::new);
        event.registerEntityRenderer(ModEntities.CURATOR.get(),           HumanoidEntityRenderer.BossRenderer::new);

        // Quadruped entity
        event.registerEntityRenderer(ModEntities.ARCHIVE_HOUND.get(),     QuadrupedEntityRenderer::new);

        // Flying entity
        event.registerEntityRenderer(ModEntities.MAINTENANCE_DRONE.get(), DroneEntityRenderer::new);
    }
}
