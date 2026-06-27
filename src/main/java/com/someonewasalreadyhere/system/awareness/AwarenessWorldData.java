package com.someonewasalreadyhere.system.awareness;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class AwarenessWorldData extends SavedData {

    private static final String DATA_NAME = SomeoneWasAlreadyHere.MODID + "_awareness";
    private static final int MAX_AWARENESS = 1000;
    private static final int DECAY_INTERVAL = 1200; // ticks (1 minute)

    private int awareness = 0;
    private int decayTimer = 0;

    // ─── SavedData lifecycle ──────────────────────────────────────────────────

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("awareness", awareness);
        tag.putInt("decayTimer", decayTimer);
        return tag;
    }

    public static AwarenessWorldData load(CompoundTag tag) {
        AwarenessWorldData data = new AwarenessWorldData();
        data.awareness = tag.getInt("awareness");
        data.decayTimer = tag.getInt("decayTimer");
        return data;
    }

    public static AwarenessWorldData get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();
        return storage.computeIfAbsent(AwarenessWorldData::load, AwarenessWorldData::new, DATA_NAME);
    }

    // ─── API ──────────────────────────────────────────────────────────────────

    public int getAwareness() {
        return awareness;
    }

    public void setAwareness(int value) {
        this.awareness = Math.min(MAX_AWARENESS, Math.max(0, value));
        setDirty();
    }

    /**
     * Add (or subtract) awareness. Clamps to [0, MAX_AWARENESS].
     * A positive delta means more awareness. Negative means calming down.
     */
    public void addAwareness(int delta) {
        setAwareness(awareness + delta);
    }

    /**
     * Called every server tick by AwarenessEventHandler.
     * Decays awareness by 1 every DECAY_INTERVAL ticks.
     */
    public void tick(ServerLevel level) {
        decayTimer++;
        if (decayTimer >= DECAY_INTERVAL) {
            decayTimer = 0;
            if (awareness > 0) {
                awareness = Math.max(0, awareness - 1);
                setDirty();
            }
        }
    }

    /** Threshold helpers for other systems to reference. */
    public boolean isLow()    { return awareness < 200; }
    public boolean isMedium() { return awareness >= 200 && awareness < 500; }
    public boolean isHigh()   { return awareness >= 500 && awareness < 750; }
    public boolean isCritical() { return awareness >= 750; }

    /** 0.0 – 1.0 normalised fraction. */
    public float getNormalized() {
        return awareness / (float) MAX_AWARENESS;
    }
}
