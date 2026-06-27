package com.someonewasalreadyhere.system.event;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.core.registry.ModSounds;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import com.someonewasalreadyhere.system.echo.EchoEventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class ModEvents {

    private static final Random RNG = new Random();

    // ─── Base event ───────────────────────────────────────────────────────────

    public abstract static class BaseEvent {
        protected final ServerPlayer player;
        protected final ServerLevel level;

        protected BaseEvent(ServerPlayer player, ServerLevel level) {
            this.player = player;
            this.level  = level;
        }

        public final void start() {
            try { onStart(); }
            catch (Exception e) {
                SomeoneWasAlreadyHere.LOGGER.error("[Event] {} failed: {}", getClass().getSimpleName(), e.getMessage());
            }
        }

        protected abstract void onStart();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SCHEDULED EVENTS (26 total)
    // ─────────────────────────────────────────────────────────────────────────

    /** Sets the day time to 0 (dawn) so it looks like morning arrived too soon. */
    public static class FalseMorning extends BaseEvent {
        public FalseMorning(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setDayTime(0);
            player.sendSystemMessage(Component.literal("§8[??] Morning came early."));
        }
    }

    /** Logs fake maintenance text and plays custodian sound. */
    public static class MaintenanceWindow extends BaseEvent {
        public MaintenanceWindow(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            player.sendSystemMessage(Component.literal("§8[Maintenance] Scheduled upkeep: sector " + (RNG.nextInt(20) + 1)));
            player.sendSystemMessage(Component.literal("§8[Maintenance] ETA: unknown."));
            level.playSound(null, player.blockPosition(),
                ModSounds.CUSTODIAN_WORK.get(), SoundSource.AMBIENT, 1.0f, 0.7f);
        }
    }

    /** Spawns an EchoHunter at the player's last recorded location. */
    public static class EchoReplay extends BaseEvent {
        public EchoReplay(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            player.getCapability(com.someonewasalreadyhere.system.echo.EchoCapability.ECHO_HISTORY)
                .ifPresent(history -> history.getRandomRecord().ifPresent(record -> {
                    var entityType = com.someonewasalreadyhere.core.registry.ModEntities.ECHO_HUNTER.get();
                    var entity = entityType.create(level);
                    if (entity == null) return;
                    entity.moveTo(record.pos().getX() + 0.5, record.pos().getY(), record.pos().getZ() + 0.5, 0, 0);
                    level.addFreshEntity(entity);
                    player.sendSystemMessage(Component.literal("§8[Echo] Something replays at a familiar location."));
                }));
        }
    }

    /** Sends fake "sky texture failed" messages to simulate visual glitch. */
    public static class SkyFailure extends BaseEvent {
        public SkyFailure(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            player.sendSystemMessage(Component.literal("§c[Render] Sky mesh failed to load."));
            player.sendSystemMessage(Component.literal("§c[Render] Attempting fallback."));
            player.sendSystemMessage(Component.literal("§c[Render] Fallback not found."));
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 1, false, false));
        }
    }

    /** Drastically increases silence in the player's chunk. */
    public static class SilenceSurge extends BaseEvent {
        public SilenceSurge(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            var chunkPos = new net.minecraft.world.level.ChunkPos(player.blockPosition());
            for (int dx = -3; dx <= 3; dx++)
                for (int dz = -3; dz <= 3; dz++)
                    com.someonewasalreadyhere.system.silence.SilenceSystem.silenceLevels
                        .put(new net.minecraft.world.level.ChunkPos(chunkPos.x + dx, chunkPos.z + dz), 0.95f);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 2, false, false));
            player.sendSystemMessage(Component.literal("§8[??] The quiet becomes total."));
        }
    }

    /** Places a short tunnel of stone bricks ahead of the player, then removes it. */
    public static class HallwayExtension extends BaseEvent {
        public HallwayExtension(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            BlockPos origin = player.blockPosition().relative(player.getDirection(), 5);
            List<BlockPos> placed = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                BlockPos floor = origin.relative(player.getDirection(), i);
                BlockPos ceil = floor.above(2);
                for (int w = -1; w <= 1; w++) {
                    BlockPos side = floor.relative(player.getDirection().getClockWise(), w);
                    if (level.getBlockState(side).isAir()) {
                        level.setBlockAndUpdate(side, Blocks.STONE_BRICKS.defaultBlockState());
                        placed.add(side);
                    }
                }
            }
            player.sendSystemMessage(Component.literal("§8[??] The hallway is longer than it was."));
            // Schedule removal after 300 ticks
            EventScheduler.scheduleDelayed(player, "torch_collapse", 300);
        }
    }

    /** Forces sunset time and adds orange tint message. */
    public static class FalseSunset extends BaseEvent {
        public FalseSunset(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setDayTime(12000);
            player.sendSystemMessage(Component.literal("§6[??] Sunset again. Already."));
        }
    }

    /** Triggers rain but sends the player a message that it's not raining. */
    public static class SilentRain extends BaseEvent {
        public SilentRain(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setWeatherParameters(0, 6000, true, false);
            player.sendSystemMessage(Component.literal("§8[??] There is no rain."));
            player.sendSystemMessage(Component.literal("§8[??] Do not worry about the sounds."));
        }
    }

    /** Removes torches in a radius, simulating power loss. */
    public static class PowerLoss extends BaseEvent {
        public PowerLoss(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            BlockPos origin = player.blockPosition();
            int removed = 0;
            for (int dx = -8; dx <= 8; dx++) {
                for (int dy = -4; dy <= 4; dy++) {
                    for (int dz = -8; dz <= 8; dz++) {
                        BlockPos pos = origin.offset(dx, dy, dz);
                        BlockState s = level.getBlockState(pos);
                        if (s.is(Blocks.TORCH) || s.is(Blocks.WALL_TORCH) || s.is(Blocks.LANTERN)) {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            removed++;
                            if (removed >= 20) break;
                        }
                    }
                }
            }
            player.sendSystemMessage(Component.literal("§8[System] Light source failure. " + removed + " unit(s) offline."));
        }
    }

    /** Sends the player's name back to them with random corruption. */
    public static class NameGlitch extends BaseEvent {
        private static final char[] GLITCH_CHARS = {'█', '▒', '░', '?', '✕', '0'};
        public NameGlitch(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            String name = player.getName().getString();
            StringBuilder glitched = new StringBuilder();
            for (char c : name.toCharArray()) {
                glitched.append(RNG.nextInt(3) == 0
                    ? GLITCH_CHARS[RNG.nextInt(GLITCH_CHARS.length)]
                    : c);
            }
            player.sendSystemMessage(Component.literal("§8[??] Identity: " + glitched));
            player.sendSystemMessage(Component.literal("§8[??] Cross-reference: partial."));
        }
    }

    /** Freezes all non-player entities in range briefly. */
    public static class ThePause extends BaseEvent {
        public ThePause(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(32),
                e -> !(e instanceof Player))
                .forEach(e -> e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 127, false, false)));
            player.sendSystemMessage(Component.literal("§8[??] Everything stopped. Just for a moment."));
        }
    }

    /** Sends a fake join message from a randomly named player. */
    public static class FalsePlayerJoin extends BaseEvent {
        private static final String[] NAMES = {"Witness_04", "Archive_Node", "Residual_8", "Observer_Unit"};
        public FalsePlayerJoin(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            String fakeName = NAMES[RNG.nextInt(NAMES.length)];
            for (var sp : level.getServer().getPlayerList().getPlayers()) {
                sp.sendSystemMessage(Component.literal("§e" + fakeName + " joined the game"));
            }
        }
    }

    /** Replaces nearby torches with air. */
    public static class TorchCollapse extends BaseEvent {
        public TorchCollapse(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            BlockPos origin = player.blockPosition();
            for (int dx = -6; dx <= 6; dx++) {
                for (int dy = -3; dy <= 3; dy++) {
                    for (int dz = -6; dz <= 6; dz++) {
                        BlockPos pos = origin.offset(dx, dy, dz);
                        if (level.getBlockState(pos).is(Blocks.TORCH)) {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
            player.sendSystemMessage(Component.literal("§8[??] The lights went out."));
        }
    }

    /** Sends fake chunk loading error messages. */
    public static class FakeChunkCorruption extends BaseEvent {
        public FakeChunkCorruption(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            int cx = (int)(player.getX() / 16);
            int cz = (int)(player.getZ() / 16);
            player.sendSystemMessage(Component.literal("§c[IO] Chunk [" + cx + "," + cz + "] read error."));
            player.sendSystemMessage(Component.literal("§c[IO] Integrity check: FAILED."));
            player.sendSystemMessage(Component.literal("§c[IO] Attempting regeneration... ABORTED."));
        }
    }

    /** Places an unlit tunnel segment ahead of the player. */
    public static class UnfinishedTunnel extends BaseEvent {
        public UnfinishedTunnel(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            BlockPos base = player.blockPosition().relative(player.getDirection(), 8);
            for (int i = 0; i < 8; i++) {
                BlockPos b = base.relative(player.getDirection(), i);
                level.setBlockAndUpdate(b, Blocks.AIR.defaultBlockState());
                level.setBlockAndUpdate(b.above(), Blocks.AIR.defaultBlockState());
                level.setBlockAndUpdate(b.below(), Blocks.COBBLESTONE.defaultBlockState());
                for (int s = -1; s <= 1; s++) {
                    level.setBlockAndUpdate(b.relative(player.getDirection().getClockWise(), s).above(2),
                        Blocks.COBBLESTONE.defaultBlockState());
                }
            }
            player.sendSystemMessage(Component.literal("§8[??] A tunnel that wasn't there before."));
        }
    }

    /** Applies extreme slowness to all mobs near the player. */
    public static class MobFreeze extends BaseEvent {
        public MobFreeze(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            int frozen = 0;
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(24),
                e -> !(e instanceof Player))) {
                e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 127, false, false));
                frozen++;
            }
            player.sendSystemMessage(Component.literal("§8[??] They all stopped moving. (" + frozen + " affected)"));
        }
    }

    /** Suppresses ambient sounds by setting silence to max. */
    public static class MissingSound extends BaseEvent {
        public MissingSound(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            var chunkPos = new net.minecraft.world.level.ChunkPos(player.blockPosition());
            com.someonewasalreadyhere.system.silence.SilenceSystem.silenceLevels.put(chunkPos, 1.0f);
            player.sendSystemMessage(Component.literal("§8[Audio] Stream missing: ambient.ogg"));
            player.sendSystemMessage(Component.literal("§8[Audio] Substituting silence."));
        }
    }

    /** Applies silence surge AND slowness. */
    public static class ForcedSilence extends BaseEvent {
        public ForcedSilence(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            new SilenceSurge(player, level).start();
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 1, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 600, 0, false, false));
            player.sendSystemMessage(Component.literal("§8[System] Forced silence protocol engaged."));
        }
    }

    /** Drops all light-emitting blocks in radius by 1 level (removes some). */
    public static class LightFailure extends BaseEvent {
        public LightFailure(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            new PowerLoss(player, level).start();
            player.sendSystemMessage(Component.literal("§8[??] The dark has been here longer than you have."));
        }
    }

    /** Spawns a mob with a randomised name tag near the player. */
    public static class FalseMob extends BaseEvent {
        private static final String[] NAMES = {"_", " ", "...", "You", "Error", "N/A"};
        public FalseMob(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            var zombie = net.minecraft.world.entity.EntityType.ZOMBIE.create(level);
            if (zombie == null) return;
            zombie.moveTo(player.getX() + 5, player.getY(), player.getZ(), RNG.nextFloat() * 360, 0);
            zombie.setCustomName(Component.literal(NAMES[RNG.nextInt(NAMES.length)]));
            zombie.setCustomNameVisible(true);
            zombie.setInvisible(true);
            level.addFreshEntity(zombie);
            player.sendSystemMessage(Component.literal("§8[??] Something was here. It still might be."));
        }
    }

    /** Teleports the player back to a recent echo position. */
    public static class TheReturn extends BaseEvent {
        public TheReturn(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            player.getCapability(com.someonewasalreadyhere.system.echo.EchoCapability.ECHO_HISTORY)
                .ifPresent(history -> history.getRandomRecord().ifPresent(record -> {
                    player.teleportTo(record.pos().getX() + 0.5, record.pos().getY(), record.pos().getZ() + 0.5);
                    player.sendSystemMessage(Component.literal("§8[??] You were here before. You're here again."));
                    AwarenessWorldData.get(level).addAwareness(30);
                }));
        }
    }

    /** Repeats sunset time twice. */
    public static class RepeatedSunset extends BaseEvent {
        public RepeatedSunset(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setDayTime(12000);
            EventScheduler.scheduleDelayed(player, "false_sunset", 200);
            player.sendSystemMessage(Component.literal("§6[??] The sun set twice today."));
        }
    }

    /** Sends message about a second moon and adds darkness. */
    public static class DuplicateMoon extends BaseEvent {
        public DuplicateMoon(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setDayTime(18000);
            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 300, 0, false, false));
            player.sendSystemMessage(Component.literal("§8[??] There are two moons tonight. Don't look at the second one."));
        }
    }

    /** Loses 200 in-game ticks silently. */
    public static class LostTime extends BaseEvent {
        public LostTime(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setDayTime(level.getDayTime() + 3000);
            player.sendSystemMessage(Component.literal("§8[??] Time skipped. No record of what you did."));
            player.sendSystemMessage(Component.literal("§8[??] " + (RNG.nextInt(4) + 1) + " hour(s) unaccounted for."));
        }
    }

    /** Replaces dirt near player with a slightly off pattern (gravel/coarse dirt). */
    public static class VillageShift extends BaseEvent {
        public VillageShift(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            BlockPos origin = player.blockPosition();
            int changed = 0;
            for (int dx = -12; dx <= 12 && changed < 8; dx++) {
                for (int dz = -12; dz <= 12 && changed < 8; dz++) {
                    BlockPos pos = origin.offset(dx, -1, dz);
                    if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
                        level.setBlockAndUpdate(pos, RNG.nextBoolean()
                            ? Blocks.COARSE_DIRT.defaultBlockState()
                            : Blocks.GRAVEL.defaultBlockState());
                        changed++;
                    }
                }
            }
            player.sendSystemMessage(Component.literal("§8[??] The village wasn't laid out this way before."));
        }
    }

    /** Starts rain but no cloud particles — sends contradicting message. */
    public static class RainWithoutClouds extends BaseEvent {
        public RainWithoutClouds(ServerPlayer p, ServerLevel l) { super(p, l); }
        @Override protected void onStart() {
            level.setWeatherParameters(0, 6000, true, false);
            player.sendSystemMessage(Component.literal("§8[??] It's raining."));
            player.sendSystemMessage(Component.literal("§8[??] There are no clouds."));
            player.sendSystemMessage(Component.literal("§8[??] Do not investigate."));
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // ALWAYS-ON PASSIVE LISTENERS
    // ─────────────────────────────────────────────────────────────────────────

    /** Second footsteps — plays an extra footstep sound slightly offset. */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.player.level().isClientSide()) return;
        if (!(event.player instanceof ServerPlayer sp)) return;
        if (!(sp.level() instanceof ServerLevel sl)) return;

        long gt = sl.getGameTime();
        int awareness = AwarenessWorldData.get(sl).getAwareness();

        // Double-footstep: only when moving, every ~25 ticks, awareness > 100
        if (awareness > 100 && gt % 25 == 0 && sp.getDeltaMovement().horizontalDistanceSqr() > 0.001) {
            if (RNG.nextInt(100) < awareness / 20) {
                BlockPos offsetPos = sp.blockPosition().offset(RNG.nextInt(5) - 2, 0, RNG.nextInt(5) - 2);
                sl.playSound(null, offsetPos, ModSounds.FOOTSTEP_DOUBLE.get(), SoundSource.PLAYERS, 0.4f, 0.8f + RNG.nextFloat() * 0.4f);
            }
        }

        // Cave whisper: underground + low light
        if (gt % 600 == 0 && sp.getY() < 40 && sl.getLightLevelDependentMagicValue(sp.blockPosition()) < 5) {
            if (awareness > 200 && RNG.nextInt(100) < 30) {
                sl.playSound(null, sp.blockPosition(), ModSounds.CAVE_BREATH.get(), SoundSource.AMBIENT, 0.8f, 0.7f + RNG.nextFloat() * 0.6f);
            }
        }

        // Record movement for echo history
        if (gt % 40 == 0 && sp.getDeltaMovement().horizontalDistanceSqr() > 0.01) {
            EchoEventManager.recordAction(sp, sp.blockPosition(), "walked");
        }
    }

    /** Inventory drift — randomly shifts one item in the hotbar after sleeping. */
    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        if (!(sp.level() instanceof ServerLevel sl)) return;

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness < 300) return;

        // Shuffle two random hotbar slots
        int slotA = RNG.nextInt(9);
        int slotB = RNG.nextInt(9);
        if (slotA == slotB) return;

        ItemStack tmp = sp.getInventory().getItem(slotA);
        sp.getInventory().setItem(slotA, sp.getInventory().getItem(slotB));
        sp.getInventory().setItem(slotB, tmp);
    }

    /** Door cycle — periodically toggles a nearby door. */
    @SubscribeEvent
    public static void onLevelTickForDoors(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (!(event.level instanceof ServerLevel sl)) return;
        if (sl.getGameTime() % 800 != 0) return;

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness < 150) return;

        for (ServerPlayer sp : sl.getServer().getPlayerList().getPlayers()) {
            BlockPos origin = sp.blockPosition();
            for (int dx = -8; dx <= 8; dx++) {
                for (int dy = -2; dy <= 2; dy++) {
                    for (int dz = -8; dz <= 8; dz++) {
                        BlockPos pos = origin.offset(dx, dy, dz);
                        BlockState state = sl.getBlockState(pos);
                        if (state.getBlock() instanceof net.minecraft.world.level.block.DoorBlock door) {
                            if (RNG.nextInt(100) < 10) {
                                door.setOpen(null, sl, state, pos, !state.getValue(net.minecraft.world.level.block.DoorBlock.OPEN));
                            }
                        }
                    }
                }
            }
        }
    }

    /** Echo mining — plays a delayed block-break sound when player mines. */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel sl)) return;
        if (!(event.getPlayer() instanceof ServerPlayer sp)) return;

        EchoEventManager.recordAction(sp, event.getPos(), "mined:" + event.getState().getBlock().getDescriptionId());

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness > 300 && RNG.nextInt(100) < 20) {
            // Delayed echo of the break sound at a random nearby pos
            BlockPos echoPos = event.getPos().offset(RNG.nextInt(7) - 3, 0, RNG.nextInt(7) - 3);
            EventScheduler.scheduleDelayed(sp, "missing_sound", 20 + RNG.nextInt(40));
            sl.playSound(null, echoPos, event.getState().getSoundType().getBreakSound(),
                SoundSource.BLOCKS, 0.5f, 0.6f + RNG.nextFloat() * 0.4f);
        }
    }

    /** Chest disturbance — randomly opens/closes a nearby chest sound. */
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getLevel() instanceof ServerLevel sl)) return;
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;

        EchoEventManager.recordAction(sp, event.getPos(), "interacted");

        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness > 400 && RNG.nextInt(100) < 5) {
            // Play a chest open sound at a nearby position
            BlockPos nearbyChest = sp.blockPosition().offset(RNG.nextInt(11) - 5, 0, RNG.nextInt(11) - 5);
            sl.playSound(null, nearbyChest,
                net.minecraft.sounds.SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.6f, 1.0f);
        }
    }

    /** Shadow path — places temporary cobblestone footprints. */
    @SubscribeEvent
    public static void onPlayerTickShadow(TickEvent.PlayerTickEvent shadowEvent) {
        if (shadowEvent.phase != TickEvent.Phase.END) return;
        if (shadowEvent.player.level().isClientSide()) return;
        if (!(shadowEvent.player instanceof ServerPlayer sp)) return;
        if (!(sp.level() instanceof ServerLevel sl)) return;

        if (sl.getGameTime() % 120 != 0) return;
        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness < 500) return;

        // Place cobblestone 10-20 blocks away along the player's last direction
        BlockPos shadow = sp.blockPosition().relative(sp.getDirection(), 15 + RNG.nextInt(10));
        BlockState below = sl.getBlockState(shadow.below());
        BlockState at = sl.getBlockState(shadow);

        if (below.isSolidRender(sl, shadow.below()) && at.isAir()) {
            sl.setBlockAndUpdate(shadow, Blocks.COBBLESTONE.defaultBlockState());
            // Schedule removal
            long removeTime = sl.getGameTime() + 200;
            BlockPos finalShadow = shadow;
            sl.getServer().tell(new net.minecraft.server.TickTask(
                (int)(removeTime - sl.getGameTime()),
                () -> {
                    if (sl.getBlockState(finalShadow).is(Blocks.COBBLESTONE)) {
                        sl.setBlockAndUpdate(finalShadow, Blocks.AIR.defaultBlockState());
                    }
                }
            ));
        }
    }

    /** Echo build — copies a small 3×3×3 structure ahead and places it 20 blocks away. */
    @SubscribeEvent
    public static void onPlayerTickEchoBuild(TickEvent.PlayerTickEvent echoBuildEvent) {
        if (echoBuildEvent.phase != TickEvent.Phase.END) return;
        if (echoBuildEvent.player.level().isClientSide()) return;
        if (!(echoBuildEvent.player instanceof ServerPlayer sp)) return;
        if (!(sp.level() instanceof ServerLevel sl)) return;

        if (sl.getGameTime() % 3000 != 0) return;
        int awareness = AwarenessWorldData.get(sl).getAwareness();
        if (awareness < 600) return;
        if (RNG.nextInt(100) >= 20) return;

        BlockPos src = sp.blockPosition();
        BlockPos dst = src.relative(sp.getDirection(), 25);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 2; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockState state = sl.getBlockState(src.offset(dx, dy, dz));
                    if (!state.isAir()) {
                        sl.setBlockAndUpdate(dst.offset(dx, dy, dz), state);
                    }
                }
            }
        }
        sp.sendSystemMessage(Component.literal("§8[Echo] A duplicate appeared ahead."));
    }
}
