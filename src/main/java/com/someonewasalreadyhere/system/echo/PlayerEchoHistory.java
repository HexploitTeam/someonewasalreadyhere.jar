package com.someonewasalreadyhere.system.echo;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PlayerEchoHistory implements INBTSerializable<CompoundTag> {

    private static final int MAX_RECORDS = 200;
    private static final Random RNG = new Random();

    private final List<EchoRecord> records = new ArrayList<>();

    public void addRecord(BlockPos pos, String action, long time) {
        if (records.size() >= MAX_RECORDS) {
            records.remove(0);
        }
        records.add(new EchoRecord(pos, action, time));
    }

    public List<EchoRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public Optional<EchoRecord> getRandomRecord() {
        if (records.isEmpty()) return Optional.empty();
        return Optional.of(records.get(RNG.nextInt(records.size())));
    }

    public Optional<EchoRecord> getMostRecent() {
        if (records.isEmpty()) return Optional.empty();
        return Optional.of(records.get(records.size() - 1));
    }

    public int size() { return records.size(); }
    public boolean isEmpty() { return records.isEmpty(); }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        for (EchoRecord r : records) {
            list.add(r.toNBT());
        }
        tag.put("records", list);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        records.clear();
        ListTag list = tag.getList("records", 10);
        for (int i = 0; i < list.size(); i++) {
            records.add(EchoRecord.fromNBT(list.getCompound(i)));
        }
    }
}
