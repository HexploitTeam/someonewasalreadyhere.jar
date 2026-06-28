package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import com.someonewasalreadyhere.system.echo.EchoEventManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Random;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SomeoneWasAlreadyHere.MODID);

    // ─── Registrations ────────────────────────────────────────────────────────

    public static final RegistryObject<EntityType<ObserverEntity>> OBSERVER =
        ENTITIES.register("observer", () -> EntityType.Builder
            .<ObserverEntity>of(ObserverEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f).build("observer"));

    public static final RegistryObject<EntityType<EchoHunterEntity>> ECHO_HUNTER =
        ENTITIES.register("echo_hunter", () -> EntityType.Builder
            .<EchoHunterEntity>of(EchoHunterEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f).build("echo_hunter"));

    public static final RegistryObject<EntityType<CaretakerEntity>> CARETAKER =
        ENTITIES.register("caretaker", () -> EntityType.Builder
            .<CaretakerEntity>of(CaretakerEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("caretaker"));

    public static final RegistryObject<EntityType<ArchiveHoundEntity>> ARCHIVE_HOUND =
        ENTITIES.register("archive_hound", () -> EntityType.Builder
            .<ArchiveHoundEntity>of(ArchiveHoundEntity::new, MobCategory.MONSTER)
            .sized(1.4f, 0.85f).build("archive_hound"));

    public static final RegistryObject<EntityType<PhantomPlayerEntity>> PHANTOM_PLAYER =
        ENTITIES.register("phantom_player", () -> EntityType.Builder
            .<PhantomPlayerEntity>of(PhantomPlayerEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.8f).build("phantom_player"));

    public static final RegistryObject<EntityType<ArchivistEntity>> ARCHIVIST =
        ENTITIES.register("archivist", () -> EntityType.Builder
            .<ArchivistEntity>of(ArchivistEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 2.4f).build("archivist"));

    public static final RegistryObject<EntityType<HollowMimicEntity>> HOLLOW_MIMIC =
        ENTITIES.register("hollow_mimic", () -> EntityType.Builder
            .<HollowMimicEntity>of(HollowMimicEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f).build("hollow_mimic"));

    public static final RegistryObject<EntityType<WindowWatcherEntity>> WINDOW_WATCHER =
        ENTITIES.register("window_watcher", () -> EntityType.Builder
            .<WindowWatcherEntity>of(WindowWatcherEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.8f).build("window_watcher"));

    public static final RegistryObject<EntityType<SurveyorEntity>> SURVEYOR =
        ENTITIES.register("surveyor", () -> EntityType.Builder
            .<SurveyorEntity>of(SurveyorEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("surveyor"));

    public static final RegistryObject<EntityType<StaticShepherdEntity>> STATIC_SHEPHERD =
        ENTITIES.register("static_shepherd", () -> EntityType.Builder
            .<StaticShepherdEntity>of(StaticShepherdEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("static_shepherd"));

    public static final RegistryObject<EntityType<SleeperEntity>> SLEEPER =
        ENTITIES.register("sleeper", () -> EntityType.Builder
            .<SleeperEntity>of(SleeperEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 0.4f).build("sleeper"));

    public static final RegistryObject<EntityType<TunnelListenerEntity>> TUNNEL_LISTENER =
        ENTITIES.register("tunnel_listener", () -> EntityType.Builder
            .<TunnelListenerEntity>of(TunnelListenerEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("tunnel_listener"));

    public static final RegistryObject<EntityType<MaintenanceDroneEntity>> MAINTENANCE_DRONE =
        ENTITIES.register("maintenance_drone", () -> EntityType.Builder
            .<MaintenanceDroneEntity>of(MaintenanceDroneEntity::new, MobCategory.CREATURE)
            .sized(0.5f, 0.5f).build("maintenance_drone"));

    public static final RegistryObject<EntityType<IncompleteEntity>> INCOMPLETE =
        ENTITIES.register("incomplete", () -> EntityType.Builder
            .<IncompleteEntity>of(IncompleteEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.2f).build("incomplete"));

    public static final RegistryObject<EntityType<ForgottenMinerEntity>> FORGOTTEN_MINER =
        ENTITIES.register("forgotten_miner", () -> EntityType.Builder
            .<ForgottenMinerEntity>of(ForgottenMinerEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("forgotten_miner"));

    public static final RegistryObject<EntityType<MirrorWalkerEntity>> MIRROR_WALKER =
        ENTITIES.register("mirror_walker", () -> EntityType.Builder
            .<MirrorWalkerEntity>of(MirrorWalkerEntity::new, MobCategory.MONSTER)
            .sized(0.6f, 1.95f).build("mirror_walker"));

    public static final RegistryObject<EntityType<QuietHostEntity>> QUIET_HOST =
        ENTITIES.register("quiet_host", () -> EntityType.Builder
            .<QuietHostEntity>of(QuietHostEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("quiet_host"));

    public static final RegistryObject<EntityType<ObserverPrimeEntity>> OBSERVER_PRIME =
        ENTITIES.register("observer_prime", () -> EntityType.Builder
            .<ObserverPrimeEntity>of(ObserverPrimeEntity::new, MobCategory.MONSTER)
            .sized(1.2f, 3.0f).build("observer_prime"));

    public static final RegistryObject<EntityType<ResidualEchoEntity>> RESIDUAL_ECHO =
        ENTITIES.register("residual_echo", () -> EntityType.Builder
            .<ResidualEchoEntity>of(ResidualEchoEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.8f).build("residual_echo"));

    public static final RegistryObject<EntityType<CartographerEntity>> CARTOGRAPHER =
        ENTITIES.register("cartographer", () -> EntityType.Builder
            .<CartographerEntity>of(CartographerEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f).build("cartographer"));

    public static final RegistryObject<EntityType<CuratorEntity>> CURATOR =
        ENTITIES.register("curator", () -> EntityType.Builder
            .<CuratorEntity>of(CuratorEntity::new, MobCategory.MONSTER)
            .sized(0.8f, 2.2f).build("curator"));

    // ─────────────────────────────────────────────────────────────────────────
    // ENTITY CLASSES
    // ─────────────────────────────────────────────────────────────────────────

    private static final Random RNG = new Random();

    // ── 1. Observer ─────────────────────────────────────────────────────────
    /**
     * Passive stalker. Does not attack. Watches from a distance, raises
     * awareness while it has line-of-sight to a player. Despawns when looked at
     * for more than 3 seconds (like a real observer removing itself when noticed).
     */
    public static class ObserverEntity extends Monster {
        private int stareTimer = 0;

        public ObserverEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
            this.setInvisible(true);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 64.0f));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            Player nearest = level().getNearestPlayer(this, 64);
            if (nearest == null) return;

            if (!(level() instanceof ServerLevel sl)) return;
            AwarenessWorldData.get(sl).addAwareness(1);

            // If the player is looking directly at this entity, count up
            if (nearest.hasLineOfSight(this)) {
                Vec3 toEntity = this.position().subtract(nearest.getEyePosition()).normalize();
                Vec3 lookVec = nearest.getLookAngle();
                double dot = toEntity.dot(lookVec);
                if (dot > 0.97) {
                    stareTimer++;
                    if (stareTimer > 60) {
                        // Discard silently when directly observed for 3s
                        this.discard();
                    }
                } else {
                    stareTimer = 0;
                }
            }
        }

        @Override
        public boolean isInvulnerable() { return true; }
    }

    // ── 2. EchoHunter ───────────────────────────────────────────────────────
    /**
     * Aggressive. Spawns at locations the player has previously visited.
     * Sprints toward the player; deals damage on contact. Drops Memory Shard.
     */
    public static class EchoHunterEntity extends Monster {
        public EchoHunterEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public boolean hurt(DamageSource source, float amount) {
            boolean result = super.hurt(source, amount);
            // Raise awareness when hurt
            if (!level().isClientSide && level() instanceof ServerLevel sl) {
                AwarenessWorldData.get(sl).addAwareness(10);
            }
            return result;
        }
    }

    // ── 3. Caretaker ────────────────────────────────────────────────────────
    /**
     * Non-hostile custodial entity. Wanders and occasionally repairs blocks
     * nearby (fills holes, replaces broken blocks). Plays custodian work sound.
     */
    public static class CaretakerEntity extends PathfinderMob {
        private int repairTimer = 0;

        public CaretakerEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.7));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0f));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            repairTimer++;
            if (repairTimer < 80) return;
            repairTimer = 0;

            // Attempt to fill a nearby air-below-surface hole
            BlockPos pos = this.blockPosition();
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos check = pos.offset(dx, -1, dz);
                    if (sl.getBlockState(check).isAir()) {
                        BlockPos above = check.above();
                        if (!sl.getBlockState(above).isAir()) {
                            sl.setBlockAndUpdate(check, Blocks.STONE.defaultBlockState());
                            sl.playSound(null, check, ModSounds.CUSTODIAN_WORK.get(),
                                SoundSource.AMBIENT, 0.5f, 1.0f);
                            break;
                        }
                    }
                }
            }
        }
    }

    // ── 4. ArchiveHound ─────────────────────────────────────────────────────
    /**
     * Fast-moving hostile. Tracks players by echo history. When killed, raises
     * awareness by 50 and sends a system message.
     */
    public static class ArchiveHoundEntity extends net.minecraft.world.entity.animal.Wolf {
        public ArchiveHoundEntity(EntityType<? extends net.minecraft.world.entity.animal.Wolf> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5, true));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public void die(DamageSource cause) {
            super.die(cause);
            if (!level().isClientSide && level() instanceof ServerLevel sl) {
                AwarenessWorldData.get(sl).addAwareness(50);
                // Notify nearby players
                for (ServerPlayer sp : sl.getServer().getPlayerList().getPlayers()) {
                    if (sp.distanceToSqr(this) < 2048) {
                        sp.sendSystemMessage(Component.literal("§8[Archive] One unit terminated. Record updated."));
                    }
                }
            }
        }
    }

    // ── 5. PhantomPlayer ────────────────────────────────────────────────────
    /**
     * Mimics a player's previous position and heading. Does not attack but
     * triggers thick fog and render-distance reduction on clients nearby.
     * Triggers PhantomSyncManager on join/leave.
     */
    public static class PhantomPlayerEntity extends PathfinderMob {
        private String mimicName = "Player";

        public PhantomPlayerEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
            this.setInvisible(false);
        }

        public void setMimicName(String name) {
            this.mimicName = name;
            this.setCustomName(Component.literal(name));
            this.setCustomNameVisible(false);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.7));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0f));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            // Passive awareness drain near players
            for (var p : sl.players()) {
                if (p.distanceToSqr(this) < 256) {
                    AwarenessWorldData.get(sl).addAwareness(1);
                }
            }
        }

        @Override
        public boolean isInvulnerable() { return true; }
    }

    // ── 6. Archivist ────────────────────────────────────────────────────────
    /**
     * Boss-tier entity. Only becomes hostile above 750 awareness. Slow but
     * powerful. Applies Wither on hit. Can detect the player through walls.
     * Ignores players carrying an ArchiveBadge.
     */
    public static class ArchivistEntity extends Monster {
        public ArchivistEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.8, false));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 20.0f));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public boolean hurt(DamageSource source, float amount) {
            // Archivist barely takes damage — reduce by 80%
            return super.hurt(source, amount * 0.2f);
        }

        @Override
        public boolean doHurtTarget(Entity target) {
            boolean hit = super.doHurtTarget(target);
            if (hit && target instanceof Player player) {
                // Check badge immunity
                boolean hasBadge = player.getInventory().items.stream()
                    .anyMatch(stack -> stack.getItem() instanceof ModItems.ArchiveBadge);
                if (!hasBadge) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                }
            }
            return hit;
        }
    }

    // ── 7. HollowMimic ──────────────────────────────────────────────────────
    /**
     * Disguises itself as a block until a player comes within 5 blocks,
     * then attacks. Becomes invisible and repositions when damaged.
     */
    public static class HollowMimicEntity extends Monster {
        private boolean disguised = true;
        private int repoTimer = 0;

        public HollowMimicEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
            this.setInvisible(true);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            Player nearest = level().getNearestPlayer(this, 64);
            if (nearest == null) return;

            double dist = nearest.distanceToSqr(this);
            if (disguised && dist < 25) {
                // Reveal
                disguised = false;
                this.setInvisible(false);
                nearest.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 80, 0));
            }

            if (!disguised && repoTimer++ > 100 && dist < 16) {
                repoTimer = 0;
                // Blink to random nearby position
                this.setInvisible(true);
                disguised = true;
                double nx = this.getX() + (RNG.nextDouble() - 0.5) * 16;
                double nz = this.getZ() + (RNG.nextDouble() - 0.5) * 16;
                this.teleportTo(nx, this.getY(), nz);
            }
        }
    }

    // ── 8. WindowWatcher ────────────────────────────────────────────────────
    /**
     * Positions itself just outside windows (glass blocks). Does not enter
     * buildings. Causes static sound when player looks at it.
     */
    public static class WindowWatcherEntity extends PathfinderMob {
        private int soundTimer = 0;

        public WindowWatcherEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.4));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 20.0f));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            soundTimer++;
            if (soundTimer < 60) return;
            soundTimer = 0;

            Player nearest = level().getNearestPlayer(this, 20);
            if (nearest == null) return;

            // Play static if player is looking at us
            Vec3 toUs = this.position().subtract(nearest.getEyePosition()).normalize();
            if (toUs.dot(nearest.getLookAngle()) > 0.95) {
                sl.playSound(null, nearest.blockPosition(),
                    ModSounds.STATIC_HUM.get(), SoundSource.AMBIENT, 0.6f, 1.2f);
                AwarenessWorldData.get(sl).addAwareness(2);
            }
        }

        @Override
        public boolean isInvulnerable() { return true; }
    }

    // ── 9. Surveyor ─────────────────────────────────────────────────────────
    /**
     * Passive. Records player movements. Every 5 minutes (6000 ticks), sends
     * a cryptic observation report to the player as a system message.
     */
    public static class SurveyorEntity extends PathfinderMob {
        private int reportTimer = 0;
        private static final String[] REPORTS = {
            "Subject has walked %d blocks since last check.",
            "Block interaction logged: %d events.",
            "Sleep avoidance pattern: confirmed.",
            "Tool degradation rate: nominal.",
            "Pathfinding anomaly at sector %d."
        };

        public SurveyorEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 64.0f));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            reportTimer++;
            if (reportTimer < 6000) return;
            reportTimer = 0;

            for (ServerPlayer sp : sl.getServer().getPlayerList().getPlayers()) {
                if (sp.distanceToSqr(this) < 4096) {
                    String report = REPORTS[RNG.nextInt(REPORTS.length)];
                    sp.sendSystemMessage(Component.literal("§8[Survey Log] " +
                        String.format(report, RNG.nextInt(500))));
                }
            }
        }
    }

    // ── 10. StaticShepherd ──────────────────────────────────────────────────
    /**
     * Herds passive mobs away from the player. Any passive mob within 16 blocks
     * of the player gets steered toward the Shepherd instead.
     */
    public static class StaticShepherdEntity extends PathfinderMob {
        private int steerTimer = 0;

        public StaticShepherdEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.7));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            steerTimer++;
            if (steerTimer < 40) return;
            steerTimer = 0;

            // Attract passive mobs within 32 blocks to follow this entity
            List<Mob> nearby = level().getEntitiesOfClass(Mob.class,
                this.getBoundingBox().inflate(32),
                e -> e != this && !(e instanceof Player) && !(e instanceof Monster));
            for (Mob mob : nearby) {
                if (mob == this || mob instanceof Player || mob instanceof Monster) continue;
                Vec3 toShepherd = this.position().subtract(mob.position()).normalize().scale(0.1);
                mob.setDeltaMovement(mob.getDeltaMovement().add(toShepherd));
            }
        }
    }

    // ── 11. Sleeper ─────────────────────────────────────────────────────────
    /**
     * Appears to lie on the ground motionless. Wakes when player approaches
     * within 4 blocks and teleports to a random nearby location.
     */
    public static class SleeperEntity extends PathfinderMob {
        private boolean awoken = false;

        public SleeperEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
            this.setNoAi(true);
        }

        @Override
        protected void registerGoals() {
            // No goals until awoken
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || awoken) return;

            Player nearest = level().getNearestPlayer(this, 4);
            if (nearest == null) return;

            awoken = true;
            this.setNoAi(false);
            // Teleport to random position 10–20 blocks away
            double angle = RNG.nextDouble() * Math.PI * 2;
            double dist = 10 + RNG.nextDouble() * 10;
            double nx = nearest.getX() + Math.cos(angle) * dist;
            double nz = nearest.getZ() + Math.sin(angle) * dist;
            this.teleportTo(nx, nearest.getY(), nz);

            if (level() instanceof ServerLevel sl) {
                nearest.sendSystemMessage(Component.literal("§8[??] Something stood up."));
                AwarenessWorldData.get(sl).addAwareness(20);
            }
            this.discard();
        }
    }

    // ── 12. TunnelListener ──────────────────────────────────────────────────
    /**
     * Underground entity. Listens for block-break events (handled by
     * TunnelListenerSoundListener). Moves toward mining sounds. Surfaces
     * when the player breaks 3+ blocks near it within 10 seconds.
     */
    public static class TunnelListenerEntity extends PathfinderMob {
        private int breakCount = 0;
        private long lastBreakTime = 0;

        public TunnelListenerEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.7));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12.0f));
        }

        public void onBlockBrokenNearby(BlockPos pos) {
            long now = this.level().getGameTime();
            if (now - lastBreakTime > 200) breakCount = 0;
            lastBreakTime = now;
            breakCount++;

            // Move toward the break position
            this.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.0);

            if (breakCount >= 3 && level() instanceof ServerLevel sl) {
                // Surface and warn
                Player nearest = level().getNearestPlayer(this, 32);
                if (nearest != null) {
                    nearest.sendSystemMessage(Component.literal("§8[??] Something heard you in the walls."));
                    AwarenessWorldData.get(sl).addAwareness(25);
                }
                breakCount = 0;
            }
        }
    }

    // ── 13. MaintenanceDrone ─────────────────────────────────────────────────
    /**
     * Flying mob. Patrols the area, plays static sound periodically.
     * Does not attack but raises awareness on proximity.
     */
    public static class MaintenanceDroneEntity extends FlyingMob {
        private int soundTimer = 0;
        private Vec3 moveTarget = Vec3.ZERO;

        public MaintenanceDroneEntity(EntityType<? extends FlyingMob> type, Level level) {
            super(type, level);
            this.moveControl = new net.minecraft.world.entity.ai.control.FlyingMoveControl(this, 20, true);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            // Random floating movement
            if (this.navigation.isDone() || moveTarget.equals(Vec3.ZERO)) {
                moveTarget = this.position().add(
                    (RNG.nextDouble() - 0.5) * 16,
                    RNG.nextDouble() * 4 - 2,
                    (RNG.nextDouble() - 0.5) * 16
                );
                this.navigation.moveTo(moveTarget.x, moveTarget.y, moveTarget.z, 0.8);
            }

            soundTimer++;
            if (soundTimer >= 100) {
                soundTimer = 0;
                level().playSound(null, this.blockPosition(),
                    ModSounds.STATIC_HUM.get(), SoundSource.AMBIENT, 0.5f, 1.5f);

                if (level() instanceof ServerLevel sl) {
                    Player nearest = level().getNearestPlayer(this, 16);
                    if (nearest != null) {
                        AwarenessWorldData.get(sl).addAwareness(3);
                    }
                }
            }
        }

        @Override
        public MobType getMobType() { return MobType.UNDEFINED; }

        @Override
        protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}
    }

    // ── 14. Incomplete ──────────────────────────────────────────────────────
    /**
     * A half-formed entity. Wanders aimlessly. Takes no actions but its
     * mere presence causes players nearby to receive brief nausea.
     */
    public static class IncompleteEntity extends PathfinderMob {
        private int nauseaTimer = 0;

        public IncompleteEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.4));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            nauseaTimer++;
            if (nauseaTimer < 60) return;
            nauseaTimer = 0;

            Player nearest = level().getNearestPlayer(this, 10);
            if (nearest != null) {
                nearest.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, false, false));
            }
        }

        @Override
        public boolean isInvulnerable() { return true; }
    }

    // ── 15. ForgottenMiner ──────────────────────────────────────────────────
    /**
     * Wanders underground. Occasionally breaks a stone block at random.
     * Drops nothing. Leaves behind a signature pattern of tunnels.
     */
    public static class ForgottenMinerEntity extends PathfinderMob {
        private int mineTimer = 0;

        public ForgottenMinerEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            mineTimer++;
            if (mineTimer < 80) return;
            mineTimer = 0;

            // Break one stone block directly ahead
            BlockPos target = this.blockPosition().relative(this.getDirection());
            BlockState state = level().getBlockState(target);
            if (state.is(Blocks.STONE) || state.is(Blocks.COBBLESTONE) || state.is(Blocks.DEEPSLATE)) {
                level().destroyBlock(target, false);
                level().playSound(null, target, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }

    // ── 16. MirrorWalker ────────────────────────────────────────────────────
    /**
     * Copies the player's movement direction and mirrors it. Attacks when
     * the player stands still for more than 5 seconds.
     */
    public static class MirrorWalkerEntity extends Monster {
        private long playerStillSince = -1;

        public MirrorWalkerEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.7));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            Player nearest = level().getNearestPlayer(this, 48);
            if (nearest == null) return;

            // Mirror the player's movement
            Vec3 playerMotion = nearest.getDeltaMovement();
            this.setDeltaMovement(-playerMotion.x, playerMotion.y, -playerMotion.z);

            // Track stillness
            boolean playerStill = playerMotion.horizontalDistanceSqr() < 0.001;
            if (playerStill) {
                if (playerStillSince < 0) playerStillSince = level().getGameTime();
                if (level().getGameTime() - playerStillSince > 100) {
                    this.setTarget(nearest);
                }
            } else {
                playerStillSince = -1;
            }
        }
    }

    // ── 17. QuietHost ───────────────────────────────────────────────────────
    /**
     * Passive until the player makes a sound (plays a sound event).
     * After that, slowly approaches, applying silence aura. Does not attack.
     */
    public static class QuietHostEntity extends PathfinderMob {
        private boolean activated = false;
        private int silenceTimer = 0;

        public QuietHostEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 16.0f));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            Player nearest = level().getNearestPlayer(this, 32);
            if (nearest == null) return;

            if (!activated) {
                // Check: if the player has moved significantly, activate
                if (nearest.getDeltaMovement().horizontalDistanceSqr() > 0.01) {
                    activated = true;
                }
            } else {
                // Slowly move toward the player
                this.getNavigation().moveTo(nearest, 0.5);

                // Apply silence in nearby chunks
                silenceTimer++;
                if (silenceTimer >= 40) {
                    silenceTimer = 0;
                    var chunkPos = new net.minecraft.world.level.ChunkPos(this.blockPosition());
                    com.someonewasalreadyhere.system.silence.SilenceSystem.silenceLevels
                        .merge(chunkPos, 0.05f, (a, b) -> Math.min(1.0f, a + b));
                    AwarenessWorldData.get(sl).addAwareness(1);
                }
            }
        }

        @Override
        public boolean isInvulnerable() { return true; }
    }

    // ── 18. ObserverPrime ───────────────────────────────────────────────────
    /**
     * Elite boss. Only spawns above 900 awareness. Combines Observer stalking,
     * Archivist damage, and MirrorWalker mobility. Causes server-wide effects.
     */
    public static class ObserverPrimeEntity extends Monster {
        private int globalTick = 0;

        public ObserverPrimeEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 128.0f));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            globalTick++;
            if (globalTick % 200 == 0) {
                // Every 10 seconds, announce presence globally
                for (ServerPlayer sp : sl.getServer().getPlayerList().getPlayers()) {
                    sp.sendSystemMessage(Component.literal("§c[Archive Prime] Observation cycle active."));
                    sp.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 100, 0, false, false));
                }
                AwarenessWorldData.get(sl).addAwareness(30);
            }
        }

        @Override
        public boolean doHurtTarget(Entity target) {
            boolean hit = super.doHurtTarget(target);
            if (hit && target instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 300, 2));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
            }
            return hit;
        }
    }

    // ── 19. ResidualEcho ────────────────────────────────────────────────────
    /**
     * Ghost-like. Replays the player's movements from their echo history.
     * Visually mirroring the player; fades (despawns) after 60 seconds.
     */
    public static class ResidualEchoEntity extends PathfinderMob {
        private int lifetime = 0;
        private static final int MAX_LIFETIME = 1200; // 60 seconds

        public ResidualEchoEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
            this.setInvisible(false);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.5));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide) return;

            lifetime++;
            if (lifetime >= MAX_LIFETIME) {
                this.discard();
                return;
            }

            // Fade effect: become increasingly transparent (simulated by playing echo sound)
            if (lifetime % 100 == 0) {
                level().playSound(null, this.blockPosition(),
                    ModSounds.ECHO_WHISPER.get(), SoundSource.AMBIENT,
                    0.3f * (1.0f - lifetime / (float) MAX_LIFETIME), 1.0f);
            }
        }

        @Override
        public boolean isInvulnerable() { return true; }
    }

    // ── 20. Cartographer ────────────────────────────────────────────────────
    /**
     * Wandering entity that "maps" the area. Periodically generates fake map
     * markers (sends chat messages about coordinates). Passive; ignores players.
     */
    public static class CartographerEntity extends PathfinderMob {
        private int mapTimer = 0;
        private static final String[] MAP_NOTES = {
            "Corridor deviation noted at %s.",
            "Landmark removed from record: %s.",
            "New structure detected at %s. Unverified.",
            "Block anomaly logged near %s.",
            "Path obstruction at %s. Routing update pending."
        };

        public CartographerEntity(EntityType<? extends PathfinderMob> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.7));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            mapTimer++;
            if (mapTimer < 1200) return;
            mapTimer = 0;

            String note = MAP_NOTES[RNG.nextInt(MAP_NOTES.length)];
            String coords = this.blockPosition().toShortString();
            for (ServerPlayer sp : sl.getServer().getPlayerList().getPlayers()) {
                if (sp.distanceToSqr(this) < 4096) {
                    sp.sendSystemMessage(Component.literal("§8[Cartography] " + String.format(note, coords)));
                }
            }
        }
    }

    // ── 21. Curator ─────────────────────────────────────────────────────────
    /**
     * Strong boss-type entity that "curates" the world — removes blocks
     * it deems incorrect. Hostile only at high awareness. Methodical movement.
     */
    public static class CuratorEntity extends Monster {
        private int curateTimer = 0;

        public CuratorEntity(EntityType<? extends Monster> type, Level level) {
            super(type, level);
        }

        @Override
        protected void registerGoals() {
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.9, false));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0f));
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }

        @Override
        public void tick() {
            super.tick();
            if (level().isClientSide || !(level() instanceof ServerLevel sl)) return;

            curateTimer++;
            if (curateTimer < 100) return;
            curateTimer = 0;

            // Remove player-placed blocks within 6 blocks (simulated by removing non-natural blocks)
            BlockPos origin = this.blockPosition();
            for (int dx = -3; dx <= 3; dx++) {
                for (int dy = -1; dy <= 2; dy++) {
                    for (int dz = -3; dz <= 3; dz++) {
                        BlockPos pos = origin.offset(dx, dy, dz);
                        BlockState state = sl.getBlockState(pos);
                        // Remove planks, crafting tables, etc. as "incorrect" blocks
                        if (state.is(Blocks.OAK_PLANKS) || state.is(Blocks.CRAFTING_TABLE)
                            || state.is(Blocks.CHEST) || state.is(Blocks.FURNACE)) {
                            sl.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }

            AwarenessWorldData.get(sl).addAwareness(5);
        }

        @Override
        public boolean doHurtTarget(Entity target) {
            boolean hit = super.doHurtTarget(target);
            if (hit && target instanceof Player p) {
                p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 150, 1));
            }
            return hit;
        }
    }
}
