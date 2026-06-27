package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {

    public static final ResourceKey<Level> THE_ARCHIVE = ResourceKey.create(
        Registries.DIMENSION,
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_archive")
    );

    public static final ResourceKey<Level> THE_QUIET = ResourceKey.create(
        Registries.DIMENSION,
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_quiet")
    );

    public static final ResourceKey<DimensionType> THE_ARCHIVE_TYPE = ResourceKey.create(
        Registries.DIMENSION_TYPE,
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_archive")
    );

    public static final ResourceKey<DimensionType> THE_QUIET_TYPE = ResourceKey.create(
        Registries.DIMENSION_TYPE,
        new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_quiet")
    );
}
