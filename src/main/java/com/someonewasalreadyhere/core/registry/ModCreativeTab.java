package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(new ResourceLocation("minecraft", "creative_mode_tab"), SomeoneWasAlreadyHere.MODID);

    public static final RegistryObject<CreativeModeTab> HORROR_TAB = CREATIVE_TABS.register(
        "horror_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.someonewasalreadyhere"))
            .icon(() -> new ItemStack(ModItems.DISTORTED_COMPASS.get()))
            .displayItems((params, output) -> {
                output.accept(ModItems.DISTORTED_COMPASS.get());
                output.accept(ModItems.SOUND_LANTERN.get());
                output.accept(ModItems.STABILIZER_CHARM.get());
                output.accept(ModItems.ANALOG_RECORDER.get());
                output.accept(ModItems.MEMORY_SHARD.get());
                output.accept(ModItems.BROKEN_WATCH.get());
                output.accept(ModItems.REPAIR_INJECTOR.get());
                output.accept(ModItems.ANOMALY_SCANNER.get());
                output.accept(ModItems.ECHO_LENS.get());
                output.accept(ModItems.SIGNAL_JAMMER.get());
                output.accept(ModItems.RESIDUAL_TAPE.get());
                output.accept(ModItems.ARCHIVE_BADGE.get());
                output.accept(ModItems.DISTORTION_SHARD.get());
                output.accept(ModItems.NOISE_GENERATOR.get());
                output.accept(ModItems.MEMORY_LANTERN.get());
                output.accept(ModItems.REPAIR_SPIKE.get());
                output.accept(ModItems.OBSERVATION_MASK.get());
                output.accept(ModItems.STOLEN_BLUEPRINT.get());
                output.accept(ModItems.STATIC_BATTERY.get());
                output.accept(ModItems.ECHO_FRAGMENT_TOTEM.get());
                output.accept(ModItems.QUIET_BLOOM.get());
                output.accept(ModItems.BROKEN_FREQUENCY_RADIO.get());
                // Blocks
                output.accept(ModBlocks.CORRUPTED_STONE.get());
                output.accept(ModBlocks.ARCHIVE_BRICKS.get());
                output.accept(ModBlocks.QUIET_GRASS_BLOCK.get());
                output.accept(ModBlocks.DISTORTED_ORE.get());
                output.accept(ModBlocks.REINFORCED_ARCHIVE.get());
                output.accept(ModBlocks.STATIC_STONE.get());
            })
            .build()
    );
}
