package com.someonewasalreadyhere;

import com.someonewasalreadyhere.core.network.ModNetwork;
import com.someonewasalreadyhere.core.registry.ModCreativeTab;
import com.someonewasalreadyhere.core.registry.ModEntities;
import com.someonewasalreadyhere.core.registry.ModItems;
import com.someonewasalreadyhere.core.registry.ModSounds;
import com.someonewasalreadyhere.system.awareness.AwarenessEventHandler;
import com.someonewasalreadyhere.system.echo.EchoCapability;
import com.someonewasalreadyhere.system.loot.ModLootConditions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SomeoneWasAlreadyHere.MODID)
public class SomeoneWasAlreadyHere {

    public static final String MODID = "someonewasalreadyhere";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public SomeoneWasAlreadyHere() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Core registries
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);

        // Creative tab
        ModCreativeTab.CREATIVE_TABS.register(modEventBus);

        // Custom loot conditions
        ModLootConditions.LOOT_CONDITIONS.register(modEventBus);

        // Network + capabilities
        ModNetwork.register();
        EchoCapability.register(modEventBus);

        // Forge event bus listeners
        MinecraftForge.EVENT_BUS.register(AwarenessEventHandler.class);
        MinecraftForge.EVENT_BUS.register(new SomeoneWasAlreadyHere());
    }
}
