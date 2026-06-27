package com.someonewasalreadyhere.system.echo;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public record EchoRecord(BlockPos pos, String action, long time) {

    public CompoundTag toNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());
        tag.putString("action", action);
        tag.putLong("time", time);
        return tag;
    }

    public static EchoRecord fromNBT(CompoundTag tag) {
        return new EchoRecord(
            new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")),
            tag.getString("action"),
            tag.getLong("time")
        );
    }
}
