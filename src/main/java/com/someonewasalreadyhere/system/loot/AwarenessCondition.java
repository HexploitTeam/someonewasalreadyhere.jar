package com.someonewasalreadyhere.system.loot;

import com.google.gson.*;
import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import com.someonewasalreadyhere.system.awareness.AwarenessWorldData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.lang.reflect.Type;

/**
 * Custom loot condition that checks the current global awareness level.
 * JSON syntax:
 * {
 *   "condition": "someonewasalreadyhere:awareness_check",
 *   "min": 0,
 *   "max": 1000
 * }
 */
public class AwarenessCondition implements LootItemCondition {

    public static final LootItemConditionType TYPE = new LootItemConditionType(new Serializer());

    private final int min;
    private final int max;

    public AwarenessCondition(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    @Override
    public boolean test(LootContext ctx) {
        if (!ctx.hasParam(LootContextParams.ORIGIN)) return false;
        var level = ctx.getLevel();
        if (!(level instanceof ServerLevel sl)) return false;
        int awareness = AwarenessWorldData.get(sl).getAwareness();
        return awareness >= min && awareness <= max;
    }

    // ─── Serializer ───────────────────────────────────────────────────────────

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<AwarenessCondition> {

        @Override
        public void serialize(JsonObject json, AwarenessCondition value, JsonSerializationContext ctx) {
            json.addProperty("min", value.min);
            json.addProperty("max", value.max);
        }

        @Override
        public AwarenessCondition deserialize(JsonObject json, JsonDeserializationContext ctx) {
            int min = json.has("min") ? json.get("min").getAsInt() : 0;
            int max = json.has("max") ? json.get("max").getAsInt() : 1000;
            return new AwarenessCondition(min, max);
        }
    }

    // ─── Builder ─────────────────────────────────────────────────────────────

    public static AwarenessCondition.Builder awareness(int min, int max) {
        return new Builder(min, max);
    }

    public static class Builder implements LootItemCondition.Builder {
        private final int min, max;
        public Builder(int min, int max) { this.min = min; this.max = max; }
        @Override public LootItemCondition build() { return new AwarenessCondition(min, max); }
    }
}
