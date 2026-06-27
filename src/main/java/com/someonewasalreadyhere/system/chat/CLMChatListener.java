package com.someonewasalreadyhere.system.chat;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import com.someonewasalreadyhere.system.event.EventScheduler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = SomeoneWasAlreadyHere.MODID)
public class CLMChatListener {

    private static final Random RNG = new Random();

    private static final String[] HALLUCINATIONS = {
        "§8[??] You didn't say that.",
        "§8[??] We already talked about this.",
        "§8[??] That message was sent 4 times.",
        "§8[??] Your words are being recorded.",
        "§8[??] Something else also said that.",
        "§8[??] The archive has a copy of this conversation.",
        "§8[??] It heard you."
    };

    private static final String[] FALSE_LOGS = {
        "§c[LOG] PLAYER disconnected unexpectedly.",
        "§c[LOG] Entity limit exceeded — purging residuals.",
        "§c[LOG] Chunk 0,0 failed integrity check.",
        "§c[LOG] Save file inconsistency detected. Attempting repair.",
        "§c[LOG] Observer presence confirmed in sector 7.",
        "§c[LOG] Memory echo triggered at player location."
    };

    @SubscribeEvent
    public static void onChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String message = event.getRawText();

        if (!(player.level() instanceof ServerLevel sl)) return;
        AwarenessWorldData data = AwarenessWorldData.get(sl);

        IntentProfile profile = IntentClassifier.classify(message);
        IntentProfile.Intent primary = profile.getPrimary();

        // Raise awareness based on intent
        switch (primary) {
            case FEAR           -> data.addAwareness(15);
            case LORE_INTEREST  -> data.addAwareness(10);
            case ESCAPE_INTENT  -> data.addAwareness(25);
            case REALITY_DENIAL -> data.addAwareness(30);
            case SYSTEM_REFERENCE -> {
                data.addAwareness(40);
                player.sendSystemMessage(Component.literal(
                    "§8[System] Referencing internal structures increases instability."
                ));
            }
            default -> data.addAwareness(2);
        }

        int awareness = data.getAwareness();

        // Hallucinate at high awareness (chance scales with awareness)
        if (awareness > 400 && RNG.nextInt(1000) < awareness / 2) {
            String hallucination = HALLUCINATIONS[RNG.nextInt(HALLUCINATIONS.length)];
            player.sendSystemMessage(Component.literal(hallucination));
        }

        // Inject false system log at critical awareness
        if (data.isCritical() && RNG.nextInt(100) < 20) {
            String log = FALSE_LOGS[RNG.nextInt(FALSE_LOGS.length)];
            for (ServerPlayer p : sl.getServer().getPlayerList().getPlayers()) {
                p.sendSystemMessage(Component.literal(log));
            }
        }

        // Schedule events triggered by specific intents
        if (primary == IntentProfile.Intent.ESCAPE_INTENT && awareness > 300) {
            EventScheduler.scheduleDelayed(player, "door_cycle", 40);
        }

        if (primary == IntentProfile.Intent.REALITY_DENIAL && awareness > 500) {
            EventScheduler.scheduleDelayed(player, "name_glitch", 20);
        }

        if (primary == IntentProfile.Intent.FEAR && RNG.nextInt(100) < 15) {
            EventScheduler.scheduleDelayed(player, "cave_whisper", 60);
        }

        // Respond in character when player addresses the system directly
        if (primary == IntentProfile.Intent.SYSTEM_REFERENCE) {
            respondInCharacter(player, message, awareness);
        }
    }

    private static void respondInCharacter(ServerPlayer player, String message, int awareness) {
        String lower = message.toLowerCase();

        if (lower.contains("who are you") || lower.contains("what are you")) {
            player.sendSystemMessage(Component.literal("§8[Archive] Classification: Custodial. Purpose: ongoing."));
        } else if (lower.contains("awareness")) {
            player.sendSystemMessage(Component.literal("§8[Archive] Current index: " + awareness + ". Trajectory: ascending."));
        } else if (lower.contains("stop") || lower.contains("leave me alone")) {
            player.sendSystemMessage(Component.literal("§8[Archive] Request logged. Processing time: indefinite."));
        } else if (lower.contains("hello") || lower.contains("hi")) {
            player.sendSystemMessage(Component.literal("§8[Archive] We noticed you " + (RNG.nextInt(10) + 1) + " hours before you noticed us."));
        } else {
            player.sendSystemMessage(Component.literal("§8[Archive] Cross-referenced. No matching record. Flagged."));
        }
    }
}
