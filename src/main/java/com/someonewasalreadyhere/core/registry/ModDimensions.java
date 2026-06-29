package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {

    // Dimension keys — these resolve lazily at runtime from the level's dimension registry
    public static final ResourceLocation THE_ARCHIVE = new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_archive");
    public static final ResourceLocation THE_QUIET     = new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_quiet");

    public static final ResourceLocation THE_ARCHIVE_TYPE = new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_archive");
    public static final ResourceLocation THE_QUIET_TYPE   = new ResourceLocation(SomeoneWasAlreadyHere.MODID, "the_quiet");
}
