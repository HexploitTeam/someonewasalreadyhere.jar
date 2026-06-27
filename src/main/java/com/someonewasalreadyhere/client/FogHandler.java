package com.someonewasalreadyhere.client;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.phantom.PhantomClientHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SomeoneWasAlreadyHere.MODID)
public class FogHandler {

    /** When a PhantomPlayer is active, force extremely thick fog. */
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (!PhantomClientHandler.isActive()) return;
        event.setNearPlaneDistance(0.1f);
        event.setFarPlaneDistance(4.0f);
        event.setCanceled(true);
    }
}
