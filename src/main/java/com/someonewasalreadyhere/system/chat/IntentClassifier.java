package com.someonewasalreadyhere.system.chat;

import java.util.Map;

public class IntentClassifier {

    // keyword → weight per intent
    private static final Map<String, Map<IntentProfile.Intent, Double>> KEYWORDS = Map.ofEntries(
        // FEAR
        Map.entry("scared",     Map.of(IntentProfile.Intent.FEAR, 2.0)),
        Map.entry("afraid",     Map.of(IntentProfile.Intent.FEAR, 2.0)),
        Map.entry("horror",     Map.of(IntentProfile.Intent.FEAR, 1.5)),
        Map.entry("terrified",  Map.of(IntentProfile.Intent.FEAR, 2.5)),
        Map.entry("help",       Map.of(IntentProfile.Intent.FEAR, 1.0)),
        Map.entry("monster",    Map.of(IntentProfile.Intent.FEAR, 1.0)),
        Map.entry("creepy",     Map.of(IntentProfile.Intent.FEAR, 1.0)),

        // LORE_INTEREST
        Map.entry("lore",       Map.of(IntentProfile.Intent.LORE_INTEREST, 2.0)),
        Map.entry("story",      Map.of(IntentProfile.Intent.LORE_INTEREST, 1.5)),
        Map.entry("what is",    Map.of(IntentProfile.Intent.LORE_INTEREST, 1.0)),
        Map.entry("archive",    Map.of(IntentProfile.Intent.LORE_INTEREST, 2.0)),
        Map.entry("custodian",  Map.of(IntentProfile.Intent.LORE_INTEREST, 2.0)),
        Map.entry("echo",       Map.of(IntentProfile.Intent.LORE_INTEREST, 1.5)),
        Map.entry("who",        Map.of(IntentProfile.Intent.LORE_INTEREST, 1.0)),

        // ESCAPE_INTENT
        Map.entry("leave",      Map.of(IntentProfile.Intent.ESCAPE_INTENT, 2.0)),
        Map.entry("escape",     Map.of(IntentProfile.Intent.ESCAPE_INTENT, 2.5)),
        Map.entry("exit",       Map.of(IntentProfile.Intent.ESCAPE_INTENT, 2.0)),
        Map.entry("quit",       Map.of(IntentProfile.Intent.ESCAPE_INTENT, 1.5)),
        Map.entry("run",        Map.of(IntentProfile.Intent.ESCAPE_INTENT, 1.0)),
        Map.entry("get out",    Map.of(IntentProfile.Intent.ESCAPE_INTENT, 2.5)),

        // REALITY_DENIAL
        Map.entry("not real",   Map.of(IntentProfile.Intent.REALITY_DENIAL, 3.0)),
        Map.entry("fake",       Map.of(IntentProfile.Intent.REALITY_DENIAL, 2.0)),
        Map.entry("game",       Map.of(IntentProfile.Intent.REALITY_DENIAL, 1.5)),
        Map.entry("mod",        Map.of(IntentProfile.Intent.REALITY_DENIAL, 1.5)),
        Map.entry("just blocks",Map.of(IntentProfile.Intent.REALITY_DENIAL, 2.0)),
        Map.entry("npc",        Map.of(IntentProfile.Intent.REALITY_DENIAL, 1.0)),

        // SYSTEM_REFERENCE
        Map.entry("awareness",  Map.of(IntentProfile.Intent.SYSTEM_REFERENCE, 3.0)),
        Map.entry("silence",    Map.of(IntentProfile.Intent.SYSTEM_REFERENCE, 2.0)),
        Map.entry("phantom",    Map.of(IntentProfile.Intent.SYSTEM_REFERENCE, 2.5)),
        Map.entry("someonewasalreadyhere", Map.of(IntentProfile.Intent.SYSTEM_REFERENCE, 3.0)),
        Map.entry("observer",   Map.of(IntentProfile.Intent.SYSTEM_REFERENCE, 2.0))
    );

    public static IntentProfile classify(String message) {
        String lower = message.toLowerCase();
        IntentProfile profile = new IntentProfile();

        for (var entry : KEYWORDS.entrySet()) {
            if (lower.contains(entry.getKey())) {
                entry.getValue().forEach(profile::addScore);
            }
        }

        // Default unknown weight if nothing matched
        if (profile.getPrimary() == IntentProfile.Intent.UNKNOWN) {
            profile.addScore(IntentProfile.Intent.UNKNOWN, 1.0);
        }

        profile.normalize();
        return profile;
    }
}
