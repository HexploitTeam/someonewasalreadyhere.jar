package com.someonewasalreadyhere.system.loot;

import com.someonewasalreadyhere.SomeoneWasAlreadyHere;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS =
        DeferredRegister.create(ForgeRegistries.Keys.LOOT_CONDITION_TYPES, SomeoneWasAlreadyHere.MODID);

    public static final RegistryObject<LootItemConditionType> AWARENESS_CHECK =
        LOOT_CONDITIONS.register("awareness_check", () -> AwarenessCondition.TYPE);
}
