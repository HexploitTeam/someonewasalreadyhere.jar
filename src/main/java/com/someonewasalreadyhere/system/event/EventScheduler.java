package com.someonewasalreadyhere.system.event;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class EventScheduler {

    private record ScheduledEntry(ServerPlayer player, String eventId, long triggerTime) {}

    private static final Map<ServerLevel, List<ScheduledEntry>> QUEUE = new WeakHashMap<>();
    private static final Random RNG = new Random();

    // 26 event IDs
    private static final String[] ALL_EVENTS = {
        "false_morning", "maintenance_window", "echo_replay", "sky_failure",
        "silence_surge", "hallway_extension", "false_sunset", "silent_rain",
        "power_loss", "name_glitch", "the_pause", "false_player_join",
        "torch_collapse", "fake_chunk_corruption", "unfinished_tunnel",
        "mob_freeze", "missing_sound", "forced_silence", "light_failure",
        "false_mob", "the_return", "repeated_sunset", "duplicate_moon",
        "lost_time", "village_shift", "rain_without_clouds"
    };

    // ─── Scheduling ───────────────────────────────────────────────────────────

    public static void schedule(ServerPlayer player, String eventId) {
        scheduleDelayed(player, eventId, 0);
    }

    public static void scheduleDelayed(ServerPlayer player, String eventId, int delayTicks) {
        if (!(player.level() instanceof ServerLevel sl)) return;
        long triggerTime = sl.getGameTime() + delayTicks;
        QUEUE.computeIfAbsent(sl, k -> new ArrayList<>())
            .add(new ScheduledEntry(player, eventId, triggerTime));
        SomeoneWasAlreadyHere.LOGGER.debug("[Events] Scheduled '{}' in {} ticks", eventId, delayTicks);
    }

    public static void randomEvent(ServerLevel level, int awareness) {
        List<ServerPlayer> players = level.getServer().getPlayerList().getPlayers();
        if (players.isEmpty()) return;

        ServerPlayer target = players.get(RNG.nextInt(players.size()));
        String eventId = ALL_EVENTS[RNG.nextInt(ALL_EVENTS.length)];
        scheduleDelayed(target, eventId, RNG.nextInt(100));
    }

    // ─── Tick ─────────────────────────────────────────────────────────────────

    public static void tick(ServerLevel level) {
        List<ScheduledEntry> queue = QUEUE.get(level);
        if (queue == null || queue.isEmpty()) return;

        long now = level.getGameTime();
        Iterator<ScheduledEntry> iter = queue.iterator();

        while (iter.hasNext()) {
            ScheduledEntry entry = iter.next();
            if (now >= entry.triggerTime()) {
                iter.remove();
                trigger(entry.player(), entry.eventId(), level);
            }
        }
    }

    // ─── Trigger ──────────────────────────────────────────────────────────────

    private static void trigger(ServerPlayer player, String eventId, ServerLevel level) {
        SomeoneWasAlreadyHere.LOGGER.debug("[Events] Triggering '{}'", eventId);
        ModEvents.BaseEvent evt = switch (eventId) {
            case "false_morning"        -> new ModEvents.FalseMorning(player, level);
            case "maintenance_window"   -> new ModEvents.MaintenanceWindow(player, level);
            case "echo_replay"          -> new ModEvents.EchoReplay(player, level);
            case "sky_failure"          -> new ModEvents.SkyFailure(player, level);
            case "silence_surge"        -> new ModEvents.SilenceSurge(player, level);
            case "hallway_extension"    -> new ModEvents.HallwayExtension(player, level);
            case "false_sunset"         -> new ModEvents.FalseSunset(player, level);
            case "silent_rain"          -> new ModEvents.SilentRain(player, level);
            case "power_loss"           -> new ModEvents.PowerLoss(player, level);
            case "name_glitch"          -> new ModEvents.NameGlitch(player, level);
            case "the_pause"            -> new ModEvents.ThePause(player, level);
            case "false_player_join"    -> new ModEvents.FalsePlayerJoin(player, level);
            case "torch_collapse"       -> new ModEvents.TorchCollapse(player, level);
            case "fake_chunk_corruption"-> new ModEvents.FakeChunkCorruption(player, level);
            case "unfinished_tunnel"    -> new ModEvents.UnfinishedTunnel(player, level);
            case "mob_freeze"           -> new ModEvents.MobFreeze(player, level);
            case "missing_sound"        -> new ModEvents.MissingSound(player, level);
            case "forced_silence"       -> new ModEvents.ForcedSilence(player, level);
            case "light_failure"        -> new ModEvents.LightFailure(player, level);
            case "false_mob"            -> new ModEvents.FalseMob(player, level);
            case "the_return"           -> new ModEvents.TheReturn(player, level);
            case "repeated_sunset"      -> new ModEvents.RepeatedSunset(player, level);
            case "duplicate_moon"       -> new ModEvents.DuplicateMoon(player, level);
            case "lost_time"            -> new ModEvents.LostTime(player, level);
            case "village_shift"        -> new ModEvents.VillageShift(player, level);
            case "rain_without_clouds"  -> new ModEvents.RainWithoutClouds(player, level);
            default -> {
                SomeoneWasAlreadyHere.LOGGER.warn("[Events] Unknown event ID: {}", eventId);
                yield null;
            }
        };

        if (evt != null) evt.start();
    }
}
