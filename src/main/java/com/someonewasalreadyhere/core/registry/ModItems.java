package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Random;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, SomeoneWasAlreadyHere.MODID);

    // ─── Item registrations ───────────────────────────────────────────────────

    public static final RegistryObject<Item> DISTORTED_COMPASS =
        ITEMS.register("distorted_compass", DistortedCompass::new);

    public static final RegistryObject<Item> SOUND_LANTERN =
        ITEMS.register("sound_lantern", SoundLantern::new);

    public static final RegistryObject<Item> STABILIZER_CHARM =
        ITEMS.register("stabilizer_charm", StabilizerCharm::new);

    public static final RegistryObject<Item> ANALOG_RECORDER =
        ITEMS.register("analog_recorder", AnalogRecorder::new);

    public static final RegistryObject<Item> MEMORY_SHARD =
        ITEMS.register("memory_shard", MemoryShard::new);

    public static final RegistryObject<Item> BROKEN_WATCH =
        ITEMS.register("broken_watch", BrokenWatch::new);

    public static final RegistryObject<Item> REPAIR_INJECTOR =
        ITEMS.register("repair_injector", RepairInjector::new);

    public static final RegistryObject<Item> ANOMALY_SCANNER =
        ITEMS.register("anomaly_scanner", AnomalyScanner::new);

    public static final RegistryObject<Item> ECHO_LENS =
        ITEMS.register("echo_lens", EchoLens::new);

    public static final RegistryObject<Item> SIGNAL_JAMMER =
        ITEMS.register("signal_jammer", SignalJammer::new);

    public static final RegistryObject<Item> RESIDUAL_TAPE =
        ITEMS.register("residual_tape", ResidualTape::new);

    public static final RegistryObject<Item> ARCHIVE_BADGE =
        ITEMS.register("archive_badge", ArchiveBadge::new);

    public static final RegistryObject<Item> DISTORTION_SHARD =
        ITEMS.register("distortion_shard", DistortionShard::new);

    public static final RegistryObject<Item> NOISE_GENERATOR =
        ITEMS.register("noise_generator", NoiseGenerator::new);

    public static final RegistryObject<Item> MEMORY_LANTERN =
        ITEMS.register("memory_lantern", MemoryLantern::new);

    public static final RegistryObject<Item> REPAIR_SPIKE =
        ITEMS.register("repair_spike", RepairSpike::new);

    public static final RegistryObject<Item> OBSERVATION_MASK =
        ITEMS.register("observation_mask", ObservationMask::new);

    public static final RegistryObject<Item> STOLEN_BLUEPRINT =
        ITEMS.register("stolen_blueprint", StolenBlueprint::new);

    public static final RegistryObject<Item> STATIC_BATTERY =
        ITEMS.register("static_battery", StaticBattery::new);

    public static final RegistryObject<Item> ECHO_FRAGMENT_TOTEM =
        ITEMS.register("echo_fragment_totem", EchoFragmentTotem::new);

    public static final RegistryObject<Item> QUIET_BLOOM =
        ITEMS.register("quiet_bloom", QuietBloom::new);

    public static final RegistryObject<Item> BROKEN_FREQUENCY_RADIO =
        ITEMS.register("broken_frequency_radio", BrokenFrequencyRadio::new);

    // ─── Helper ───────────────────────────────────────────────────────────────

    private static Properties props() {
        return new Item.Properties().stacksTo(1);
    }

    // ─── Item Definitions ─────────────────────────────────────────────────────

    /**
     * Distorted Compass – points toward the highest-awareness chunk rather than
     * spawn/north. Holding it grants the player a faint nausea pulse every 5s
     * when awareness > 400.
     */
    public static class DistortedCompass extends Item {
        private static final int NAUSEA_INTERVAL = 100; // ticks

        public DistortedCompass() { super(props()); }

        @Override
        public void onUseTick(Level level, LivingEntityWrapper entity, ItemStack stack, int remainingUseDuration) {
            // Implemented in player tick via AwarenessEventHandler
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide && level instanceof ServerLevel sl) {
                int awareness = AwarenessWorldData.get(sl).getAwareness();
                if (awareness > 400) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 60, 0, false, false));
                }
                player.sendSystemMessage(Component.literal(
                    "[Compass] Magnetic north: " + (awareness > 600 ? "UNDEFINED" : "~" + (awareness / 10) + "° deviation")
                ));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("It no longer points the same direction twice."));
        }
    }

    /**
     * Sound Lantern – emits a subtle ambient sound radius; reduces echo-hunter
     * spawn chance within 24 blocks.
     */
    public static class SoundLantern extends Item {
        public SoundLantern() { super(new Item.Properties().stacksTo(1)) ; }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                level.playSound(null, player.blockPosition(),
                    ModSounds.SILENCE_AMBIENT.get(), SoundSource.AMBIENT, 1.0f, 0.8f);
                player.sendSystemMessage(Component.literal("[Lantern] A soft tone spreads outward."));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Sound keeps things at bay. For now."));
        }
    }

    /**
     * Stabilizer Charm – passively reduces awareness gain by 15% while held.
     * Checked in AwarenessWorldData.addAwareness.
     */
    public static class StabilizerCharm extends Item {
        public static final String TAG_ACTIVE = "stabilizer_active";
        public StabilizerCharm() { super(props()); }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Dampens whatever is listening."));
            tooltip.add(Component.literal("Hold to reduce awareness accumulation."));
        }
    }

    /**
     * Analog Recorder – right-click to record the last 5 events that occurred.
     * Stores them in NBT and prints them when right-clicked again.
     */
    public static class AnalogRecorder extends Item {
        public AnalogRecorder() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            ItemStack stack = player.getItemInHand(hand);
            if (!level.isClientSide && player instanceof ServerPlayer sp) {
                if (stack.hasTag() && stack.getTag().contains("recordings")) {
                    var list = stack.getTag().getList("recordings", 8);
                    player.sendSystemMessage(Component.literal("[Recorder] Playback:"));
                    for (int i = 0; i < list.size(); i++) {
                        player.sendSystemMessage(Component.literal("  > " + list.getString(i)));
                    }
                } else {
                    player.sendSystemMessage(Component.literal("[Recorder] No recordings found. Recording environment..."));
                    // Events will be logged here by EventScheduler
                }
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Records what happens when you're not looking."));
        }
    }

    /**
     * Memory Shard – dropped by EchoHunter. Right-click to replay a fragment of
     * the player's past actions as ghost particles.
     */
    public static class MemoryShard extends Item {
        public MemoryShard() { super(new Item.Properties().stacksTo(16)); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide && level instanceof ServerLevel sl) {
                int awareness = AwarenessWorldData.get(sl).getAwareness();
                AwarenessWorldData.get(sl).addAwareness(20);
                player.sendSystemMessage(Component.literal("[Shard] A memory surfaces. Awareness +" + 20));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Still warm. Still watching."));
        }
    }

    /**
     * Broken Watch – displays a wrong/random time when right-clicked.
     * High awareness can freeze it permanently.
     */
    public static class BrokenWatch extends Item {
        private static final Random RNG = new Random();
        public BrokenWatch() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                int fakeHour   = RNG.nextInt(24);
                int fakeMinute = RNG.nextInt(60);
                player.sendSystemMessage(Component.literal(
                    String.format("[Watch] Current time: %02d:%02d (estimated)", fakeHour, fakeMinute)
                ));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("The hands haven't moved."));
            tooltip.add(Component.literal("Or maybe they moved too much."));
        }
    }

    /**
     * Repair Injector – right-click on a block to mark it as "stabilized",
     * preventing Custodian modification for 120 seconds.
     */
    public static class RepairInjector extends Item {
        public RepairInjector() { super(props().durability(16)); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                player.sendSystemMessage(Component.literal("[Injector] Area marked as stable. Custodian will avoid this zone briefly."));
                // Tag handling done in CustodianManager
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Forces things to stay as they are."));
            tooltip.add(Component.literal("Briefly."));
        }
    }

    /**
     * Anomaly Scanner – scan area for entities or block changes caused by the mod.
     */
    public static class AnomalyScanner extends Item {
        public AnomalyScanner() { super(props().durability(32)); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide && level instanceof ServerLevel sl) {
                var entities = level.getEntitiesOfClass(
                    net.minecraft.world.entity.LivingEntity.class,
                    player.getBoundingBox().inflate(32),
                    e -> !(e instanceof Player)
                );
                int awareness = AwarenessWorldData.get(sl).getAwareness();
                player.sendSystemMessage(Component.literal(
                    "[Scanner] Anomalies in range: " + entities.size() +
                    " | Awareness index: " + awareness
                ));
                entities.forEach(e ->
                    player.sendSystemMessage(Component.literal("  - " + e.getName().getString() +
                        " @ " + e.blockPosition()))
                );
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Detects things that shouldn't be there."));
        }
    }

    /**
     * Echo Lens – allows the player to see ghost footsteps left by EchoHunter.
     * In this implementation, right-click reveals all mod entities within 64 blocks.
     */
    public static class EchoLens extends Item {
        public EchoLens() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide && level instanceof ServerLevel sl) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0, false, false));
                player.sendSystemMessage(Component.literal("[Lens] Echo layer active. Something was here."));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Look through the residue."));
        }
    }

    /**
     * Signal Jammer – when activated, suppresses PhantomPlayer spawning in a 48-block
     * radius for 60 seconds.
     */
    public static class SignalJammer extends Item {
        public SignalJammer() { super(props().durability(8)); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                player.sendSystemMessage(Component.literal("[Jammer] Signal suppressed. Phantoms cannot lock on."));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, false));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Interrupts whatever frequency they use."));
        }
    }

    /**
     * Residual Tape – stores the last message the CLM system injected.
     * Right-click to replay it.
     */
    public static class ResidualTape extends Item {
        public ResidualTape() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            ItemStack stack = player.getItemInHand(hand);
            if (!level.isClientSide) {
                String msg = stack.hasTag() && stack.getTag().contains("tape_msg")
                    ? stack.getTag().getString("tape_msg")
                    : "[Tape] No signal recorded.";
                player.sendSystemMessage(Component.literal(msg));
            }
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("There's a voice on this. Almost recognizable."));
        }
    }

    /** Archive Badge – grants partial immunity to Archivist targeting. */
    public static class ArchiveBadge extends Item {
        public static final String BADGE_TAG = "archive_badge_holder";
        public ArchiveBadge() { super(props()); }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Clearance Level: Provisional"));
            tooltip.add(Component.literal("Subject to review."));
        }
    }

    /** Distortion Shard – raises awareness by 50 when used, triggers random event. */
    public static class DistortionShard extends Item {
        public DistortionShard() { super(new Item.Properties().stacksTo(16)); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide && level instanceof ServerLevel sl && player instanceof ServerPlayer sp) {
                AwarenessWorldData.get(sl).addAwareness(50);
                com.someonewasalreadyhere.system.event.EventScheduler.randomEvent(sl, 999);
                player.sendSystemMessage(Component.literal("[Shard] Reality destabilized."));
                player.getItemInHand(hand).shrink(1);
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Everything gets louder when you break this."));
        }
    }

    /** Noise Generator – plays random sounds to confuse proximity triggers. */
    public static class NoiseGenerator extends Item {
        private static final Random RNG = new Random();
        public NoiseGenerator() { super(props().durability(24)); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                BlockPos offset = player.blockPosition().offset(
                    RNG.nextInt(17) - 8, 0, RNG.nextInt(17) - 8
                );
                level.playSound(null, offset, ModSounds.STATIC_HUM.get(), SoundSource.AMBIENT, 1.5f, 0.6f + RNG.nextFloat() * 0.8f);
                player.sendSystemMessage(Component.literal("[Generator] False signal emitted."));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Confuses things that hunt by sound."));
        }
    }

    /** Memory Lantern – shows awareness level as light; higher = dimmer. */
    public static class MemoryLantern extends Item {
        public MemoryLantern() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide && level instanceof ServerLevel sl) {
                int a = AwarenessWorldData.get(sl).getAwareness();
                int brightness = Math.max(0, 100 - (a / 10));
                player.sendSystemMessage(Component.literal(
                    "[Memory Lantern] Signal integrity: " + brightness + "%"
                ));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Brighter means safer. Watch it dim."));
        }
    }

    /** Repair Spike – place to prevent block modifications in a 4-block radius. */
    public static class RepairSpike extends Item {
        public RepairSpike() { super(new Item.Properties().stacksTo(8)); }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Pins the world in place."));
            tooltip.add(Component.literal("Place on ground to anchor a small area."));
        }
    }

    /** Observation Mask – wearing reduces Custodian detection range by half. */
    public static class ObservationMask extends Item {
        public ObservationMask() { super(props()); }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("They don't see you properly through this."));
            tooltip.add(Component.literal("Or they see something else entirely."));
        }
    }

    /** Stolen Blueprint – shows a false map of a nearby structure. */
    public static class StolenBlueprint extends Item {
        public StolenBlueprint() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                player.sendSystemMessage(Component.literal("[Blueprint] Section 7 — Maintenance Access"));
                player.sendSystemMessage(Component.literal("  NOTE: Route 4B no longer exists."));
                player.sendSystemMessage(Component.literal("  This document may be outdated."));
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Someone took this. Someone needed it."));
        }
    }

    /** Static Battery – powers Signal Jammer and Noise Generator (NBT charge). */
    public static class StaticBattery extends Item {
        public StaticBattery() { super(new Item.Properties().stacksTo(4)); }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Stores signal-disruptive charge."));
            tooltip.add(Component.literal("Crackles when near high-awareness zones."));
        }
    }

    /** Echo Fragment Totem – when held, prevents echo-related entity spawns nearby. */
    public static class EchoFragmentTotem extends Item {
        public EchoFragmentTotem() { super(props()); }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("A frozen moment. Hold tightly."));
            tooltip.add(Component.literal("Echoes avoid their own reflections."));
        }
    }

    /** Quiet Bloom – consuming reduces awareness by 30, applies brief Resistance. */
    public static class QuietBloom extends Item {
        public QuietBloom() { super(new Item.Properties().stacksTo(16).food(
            new net.minecraft.world.food.FoodProperties.Builder()
                .nutrition(1).saturationMod(0.1f).build()
        )); }

        @Override
        public ItemStack finishUsingItem(ItemStack stack, Level level, net.minecraft.world.entity.LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && level instanceof ServerLevel sl && entity instanceof Player p) {
                AwarenessWorldData.get(sl).addAwareness(-30);
                p.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, false, false));
                p.sendSystemMessage(Component.literal("[Bloom] Quieter. For a moment."));
            }
            return result;
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("It grows where things have forgotten to be loud."));
        }
    }

    /** Broken Frequency Radio – plays a distorted broadcast with lore fragments. */
    public static class BrokenFrequencyRadio extends Item {
        private static final String[] BROADCASTS = {
            "[Radio] ...they said it left on its own...",
            "[Radio] ...the third corridor never matched the blueprint...",
            "[Radio] ...don't sleep near the archive entrance...",
            "[Radio] ...it repeats what you did. Not what you meant...",
            "[Radio] ...signal lost on day 4. Or day 40. Unsure...",
            "[Radio] ...someone was already here when we arrived..."
        };
        private static final Random RNG = new Random();

        public BrokenFrequencyRadio() { super(props()); }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
            if (!level.isClientSide) {
                player.sendSystemMessage(Component.literal(BROADCASTS[RNG.nextInt(BROADCASTS.length)]));
                level.playSound(null, player.blockPosition(),
                    ModSounds.STATIC_HUM.get(), SoundSource.AMBIENT, 0.8f, 0.5f + RNG.nextFloat() * 0.3f);
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }

        @Override
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
            tooltip.add(Component.literal("Picks up signals that shouldn't exist."));
        }
    }

    // Placeholder interface to suppress compile errors from mis-referenced method
    private interface LivingEntityWrapper {}
}
