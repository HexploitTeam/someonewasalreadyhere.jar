package com.someonewasalreadyhere.system.chat;

import java.util.EnumMap;
import java.util.Map;

public class IntentProfile {

    public enum Intent {
        FEAR,
        LORE_INTEREST,
        ESCAPE_INTENT,
        REALITY_DENIAL,
        SYSTEM_REFERENCE,
        UNKNOWN
    }

    private final Map<Intent, Double> scores = new EnumMap<>(Intent.class);

    public IntentProfile() {
        for (Intent i : Intent.values()) scores.put(i, 0.0);
    }

    public void addScore(Intent intent, double amount) {
        scores.merge(intent, amount, Double::sum);
    }

    /** Normalize all values so they sum to 1.0. */
    public void normalize() {
        double total = scores.values().stream().mapToDouble(Double::doubleValue).sum();
        if (total == 0) return;
        scores.replaceAll((k, v) -> v / total);
    }

    public Intent getPrimary() {
        return scores.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Intent.UNKNOWN);
    }

    public double getScore(Intent intent) {
        return scores.getOrDefault(intent, 0.0);
    }

    public Map<Intent, Double> getScores() {
        return Map.copyOf(scores);
    }
}
