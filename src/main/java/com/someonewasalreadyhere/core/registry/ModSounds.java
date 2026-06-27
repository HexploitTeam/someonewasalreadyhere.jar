package com.someonewasalreadyhere.core.registry;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SomeoneWasAlreadyHere.MODID);

    public static final RegistryObject<SoundEvent> SILENCE_AMBIENT = SOUNDS.register("silence_ambient",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SomeoneWasAlreadyHere.MODID, "silence_ambient")));

    public static final RegistryObject<SoundEvent> CUSTODIAN_WORK = SOUNDS.register("custodian_work",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SomeoneWasAlreadyHere.MODID, "custodian_work")));

    public static final RegistryObject<SoundEvent> ECHO_WHISPER = SOUNDS.register("echo_whisper",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SomeoneWasAlreadyHere.MODID, "echo_whisper")));

    public static final RegistryObject<SoundEvent> CAVE_BREATH = SOUNDS.register("cave_breath",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SomeoneWasAlreadyHere.MODID, "cave_breath")));

    public static final RegistryObject<SoundEvent> STATIC_HUM = SOUNDS.register("static_hum",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SomeoneWasAlreadyHere.MODID, "static_hum")));

    public static final RegistryObject<SoundEvent> FOOTSTEP_DOUBLE = SOUNDS.register("footstep_double",
        () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SomeoneWasAlreadyHere.MODID, "footstep_double")));
}
